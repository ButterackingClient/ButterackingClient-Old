/*     */ package net.minecraft.client.stream;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.stream.GuiTwitchUserMode;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import tv.twitch.AuthToken;
/*     */ import tv.twitch.ErrorCode;
/*     */ import tv.twitch.broadcast.EncodingCpuUsage;
/*     */ import tv.twitch.broadcast.FrameBuffer;
/*     */ import tv.twitch.broadcast.GameInfo;
/*     */ import tv.twitch.broadcast.IngestList;
/*     */ import tv.twitch.broadcast.IngestServer;
/*     */ import tv.twitch.broadcast.StreamInfo;
/*     */ import tv.twitch.broadcast.VideoParams;
/*     */ import tv.twitch.chat.ChatRawMessage;
/*     */ import tv.twitch.chat.ChatTokenizedMessage;
/*     */ import tv.twitch.chat.ChatUserInfo;
/*     */ import tv.twitch.chat.ChatUserMode;
/*     */ import tv.twitch.chat.ChatUserSubscription;
/*     */ 
/*     */ public class TwitchStream
/*     */   implements BroadcastController.BroadcastListener, ChatController.ChatListener, IngestServerTester.IngestTestListener, IStream
/*     */ {
/*  55 */   private static final Logger LOGGER = LogManager.getLogger();
/*  56 */   public static final Marker STREAM_MARKER = MarkerManager.getMarker("STREAM");
/*     */   
/*     */   private final BroadcastController broadcastController;
/*     */   
/*     */   private final ChatController chatController;
/*     */   
/*     */   private String field_176029_e;
/*     */   
/*     */   private final Minecraft mc;
/*  65 */   private final IChatComponent twitchComponent = (IChatComponent)new ChatComponentText("Twitch");
/*  66 */   private final Map<String, ChatUserInfo> field_152955_g = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private Framebuffer framebuffer;
/*     */   
/*     */   private boolean field_152957_i;
/*     */   
/*  73 */   private int targetFPS = 30;
/*  74 */   private long field_152959_k = 0L;
/*     */   private boolean field_152960_l = false;
/*     */   private boolean loggedIn;
/*     */   private boolean field_152962_n;
/*     */   private boolean field_152963_o;
/*  79 */   private IStream.AuthFailureReason authFailureReason = IStream.AuthFailureReason.ERROR;
/*     */   private static boolean field_152965_q;
/*     */   
/*     */   public TwitchStream(Minecraft mcIn, final Property streamProperty) {
/*  83 */     this.mc = mcIn;
/*  84 */     this.broadcastController = new BroadcastController();
/*  85 */     this.chatController = new ChatController();
/*  86 */     this.broadcastController.setBroadcastListener(this);
/*  87 */     this.chatController.func_152990_a(this);
/*  88 */     this.broadcastController.func_152842_a("nmt37qblda36pvonovdkbopzfzw3wlq");
/*  89 */     this.chatController.func_152984_a("nmt37qblda36pvonovdkbopzfzw3wlq");
/*  90 */     this.twitchComponent.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
/*     */     
/*  92 */     if (streamProperty != null && !Strings.isNullOrEmpty(streamProperty.getValue()) && OpenGlHelper.framebufferSupported) {
/*  93 */       Thread thread = new Thread("Twitch authenticator") {
/*     */           public void run() {
/*     */             try {
/*  96 */               URL url = new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(streamProperty.getValue(), "UTF-8"));
/*  97 */               String s = HttpUtil.get(url);
/*  98 */               JsonObject jsonobject = JsonUtils.getJsonObject((new JsonParser()).parse(s), "Response");
/*  99 */               JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "token");
/*     */               
/* 101 */               if (JsonUtils.getBoolean(jsonobject1, "valid")) {
/* 102 */                 String s1 = JsonUtils.getString(jsonobject1, "user_name");
/* 103 */                 TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Authenticated with twitch; username is {}", new Object[] { s1 });
/* 104 */                 AuthToken authtoken = new AuthToken();
/* 105 */                 authtoken.data = streamProperty.getValue();
/* 106 */                 TwitchStream.this.broadcastController.func_152818_a(s1, authtoken);
/* 107 */                 TwitchStream.this.chatController.func_152998_c(s1);
/* 108 */                 TwitchStream.this.chatController.func_152994_a(authtoken);
/* 109 */                 Runtime.getRuntime().addShutdownHook(new Thread("Twitch shutdown hook") {
/*     */                       public void run() {
/* 111 */                         TwitchStream.null.access$0(TwitchStream.null.this).shutdownStream();
/*     */                       }
/*     */                     });
/* 114 */                 TwitchStream.this.broadcastController.func_152817_A();
/* 115 */                 TwitchStream.this.chatController.func_175984_n();
/*     */               } else {
/* 117 */                 TwitchStream.this.authFailureReason = IStream.AuthFailureReason.INVALID_TOKEN;
/* 118 */                 TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, "Given twitch access token is invalid");
/*     */               } 
/* 120 */             } catch (IOException ioexception) {
/* 121 */               TwitchStream.this.authFailureReason = IStream.AuthFailureReason.ERROR;
/* 122 */               TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, "Could not authenticate with twitch", ioexception);
/*     */             } 
/*     */           }
/*     */         };
/* 126 */       thread.setDaemon(true);
/* 127 */       thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdownStream() {
/* 135 */     LOGGER.debug(STREAM_MARKER, "Shutdown streaming");
/* 136 */     this.broadcastController.statCallback();
/* 137 */     this.chatController.func_175988_p();
/*     */   }
/*     */   
/*     */   public void func_152935_j() {
/* 141 */     int i = this.mc.gameSettings.streamChatEnabled;
/* 142 */     boolean flag = (this.field_176029_e != null && this.chatController.func_175990_d(this.field_176029_e));
/* 143 */     boolean flag1 = (this.chatController.func_153000_j() == ChatController.ChatState.Initialized && (this.field_176029_e == null || this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected));
/*     */     
/* 145 */     if (i == 2) {
/* 146 */       if (flag) {
/* 147 */         LOGGER.debug(STREAM_MARKER, "Disconnecting from twitch chat per user options");
/* 148 */         this.chatController.func_175991_l(this.field_176029_e);
/*     */       } 
/* 150 */     } else if (i == 1) {
/* 151 */       if (flag1 && this.broadcastController.func_152849_q()) {
/* 152 */         LOGGER.debug(STREAM_MARKER, "Connecting to twitch chat per user options");
/* 153 */         func_152942_I();
/*     */       } 
/* 155 */     } else if (i == 0) {
/* 156 */       if (flag && !isBroadcasting()) {
/* 157 */         LOGGER.debug(STREAM_MARKER, "Disconnecting from twitch chat as user is no longer streaming");
/* 158 */         this.chatController.func_175991_l(this.field_176029_e);
/* 159 */       } else if (flag1 && isBroadcasting()) {
/* 160 */         LOGGER.debug(STREAM_MARKER, "Connecting to twitch chat as user is streaming");
/* 161 */         func_152942_I();
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     this.broadcastController.func_152821_H();
/* 166 */     this.chatController.func_152997_n();
/*     */   }
/*     */   
/*     */   protected void func_152942_I() {
/* 170 */     ChatController.ChatState chatcontroller$chatstate = this.chatController.func_153000_j();
/* 171 */     String s = (this.broadcastController.getChannelInfo()).name;
/* 172 */     this.field_176029_e = s;
/*     */     
/* 174 */     if (chatcontroller$chatstate != ChatController.ChatState.Initialized) {
/* 175 */       LOGGER.warn("Invalid twitch chat state {}", new Object[] { chatcontroller$chatstate });
/* 176 */     } else if (this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected) {
/* 177 */       this.chatController.func_152986_d(s);
/*     */     } else {
/* 179 */       LOGGER.warn("Invalid twitch chat state {}", new Object[] { chatcontroller$chatstate });
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_152922_k() {
/* 184 */     if (this.broadcastController.isBroadcasting() && !this.broadcastController.isBroadcastPaused()) {
/* 185 */       long i = System.nanoTime();
/* 186 */       long j = (1000000000 / this.targetFPS);
/* 187 */       long k = i - this.field_152959_k;
/* 188 */       boolean flag = (k >= j);
/*     */       
/* 190 */       if (flag) {
/* 191 */         FrameBuffer framebuffer = this.broadcastController.func_152822_N();
/* 192 */         Framebuffer framebuffer1 = this.mc.getFramebuffer();
/* 193 */         this.framebuffer.bindFramebuffer(true);
/* 194 */         GlStateManager.matrixMode(5889);
/* 195 */         GlStateManager.pushMatrix();
/* 196 */         GlStateManager.loadIdentity();
/* 197 */         GlStateManager.ortho(0.0D, this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight, 0.0D, 1000.0D, 3000.0D);
/* 198 */         GlStateManager.matrixMode(5888);
/* 199 */         GlStateManager.pushMatrix();
/* 200 */         GlStateManager.loadIdentity();
/* 201 */         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 202 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 203 */         GlStateManager.viewport(0, 0, this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight);
/* 204 */         GlStateManager.enableTexture2D();
/* 205 */         GlStateManager.disableAlpha();
/* 206 */         GlStateManager.disableBlend();
/* 207 */         float f = this.framebuffer.framebufferWidth;
/* 208 */         float f1 = this.framebuffer.framebufferHeight;
/* 209 */         float f2 = framebuffer1.framebufferWidth / framebuffer1.framebufferTextureWidth;
/* 210 */         float f3 = framebuffer1.framebufferHeight / framebuffer1.framebufferTextureHeight;
/* 211 */         framebuffer1.bindFramebufferTexture();
/* 212 */         GL11.glTexParameterf(3553, 10241, 9729.0F);
/* 213 */         GL11.glTexParameterf(3553, 10240, 9729.0F);
/* 214 */         Tessellator tessellator = Tessellator.getInstance();
/* 215 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 216 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 217 */         worldrenderer.pos(0.0D, f1, 0.0D).tex(0.0D, f3).endVertex();
/* 218 */         worldrenderer.pos(f, f1, 0.0D).tex(f2, f3).endVertex();
/* 219 */         worldrenderer.pos(f, 0.0D, 0.0D).tex(f2, 0.0D).endVertex();
/* 220 */         worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 221 */         tessellator.draw();
/* 222 */         framebuffer1.unbindFramebufferTexture();
/* 223 */         GlStateManager.popMatrix();
/* 224 */         GlStateManager.matrixMode(5889);
/* 225 */         GlStateManager.popMatrix();
/* 226 */         GlStateManager.matrixMode(5888);
/* 227 */         this.broadcastController.captureFramebuffer(framebuffer);
/* 228 */         this.framebuffer.unbindFramebuffer();
/* 229 */         this.broadcastController.submitStreamFrame(framebuffer);
/* 230 */         this.field_152959_k = i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean func_152936_l() {
/* 236 */     return this.broadcastController.func_152849_q();
/*     */   }
/*     */   
/*     */   public boolean isReadyToBroadcast() {
/* 240 */     return this.broadcastController.isReadyToBroadcast();
/*     */   }
/*     */   
/*     */   public boolean isBroadcasting() {
/* 244 */     return this.broadcastController.isBroadcasting();
/*     */   }
/*     */   
/*     */   public void func_152911_a(Metadata p_152911_1_, long p_152911_2_) {
/* 248 */     if (isBroadcasting() && this.field_152957_i) {
/* 249 */       long i = this.broadcastController.getStreamTime();
/*     */       
/* 251 */       if (!this.broadcastController.func_152840_a(p_152911_1_.func_152810_c(), i + p_152911_2_, p_152911_1_.func_152809_a(), p_152911_1_.func_152806_b())) {
/* 252 */         LOGGER.warn(STREAM_MARKER, "Couldn't send stream metadata action at {}: {}", new Object[] { Long.valueOf(i + p_152911_2_), p_152911_1_ });
/*     */       } else {
/* 254 */         LOGGER.debug(STREAM_MARKER, "Sent stream metadata action at {}: {}", new Object[] { Long.valueOf(i + p_152911_2_), p_152911_1_ });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_176026_a(Metadata p_176026_1_, long p_176026_2_, long p_176026_4_) {
/* 260 */     if (isBroadcasting() && this.field_152957_i) {
/* 261 */       long i = this.broadcastController.getStreamTime();
/* 262 */       String s = p_176026_1_.func_152809_a();
/* 263 */       String s1 = p_176026_1_.func_152806_b();
/* 264 */       long j = this.broadcastController.func_177946_b(p_176026_1_.func_152810_c(), i + p_176026_2_, s, s1);
/*     */       
/* 266 */       if (j < 0L) {
/* 267 */         LOGGER.warn(STREAM_MARKER, "Could not send stream metadata sequence from {} to {}: {}", new Object[] { Long.valueOf(i + p_176026_2_), Long.valueOf(i + p_176026_4_), p_176026_1_ });
/* 268 */       } else if (this.broadcastController.func_177947_a(p_176026_1_.func_152810_c(), i + p_176026_4_, j, s, s1)) {
/* 269 */         LOGGER.debug(STREAM_MARKER, "Sent stream metadata sequence from {} to {}: {}", new Object[] { Long.valueOf(i + p_176026_2_), Long.valueOf(i + p_176026_4_), p_176026_1_ });
/*     */       } else {
/* 271 */         LOGGER.warn(STREAM_MARKER, "Half-sent stream metadata sequence from {} to {}: {}", new Object[] { Long.valueOf(i + p_176026_2_), Long.valueOf(i + p_176026_4_), p_176026_1_ });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPaused() {
/* 277 */     return this.broadcastController.isBroadcastPaused();
/*     */   }
/*     */   
/*     */   public void requestCommercial() {
/* 281 */     if (this.broadcastController.requestCommercial()) {
/* 282 */       LOGGER.debug(STREAM_MARKER, "Requested commercial from Twitch");
/*     */     } else {
/* 284 */       LOGGER.warn(STREAM_MARKER, "Could not request commercial from Twitch");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/* 292 */     this.broadcastController.func_152847_F();
/* 293 */     this.field_152962_n = true;
/* 294 */     updateStreamVolume();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unpause() {
/* 301 */     this.broadcastController.func_152854_G();
/* 302 */     this.field_152962_n = false;
/* 303 */     updateStreamVolume();
/*     */   }
/*     */   
/*     */   public void updateStreamVolume() {
/* 307 */     if (isBroadcasting()) {
/* 308 */       float f = this.mc.gameSettings.streamGameVolume;
/* 309 */       boolean flag = !(!this.field_152962_n && f > 0.0F);
/* 310 */       this.broadcastController.setPlaybackDeviceVolume(flag ? 0.0F : f);
/* 311 */       this.broadcastController.setRecordingDeviceVolume(func_152929_G() ? 0.0F : this.mc.gameSettings.streamMicVolume);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_152930_t() {
/* 316 */     GameSettings gamesettings = this.mc.gameSettings;
/* 317 */     VideoParams videoparams = this.broadcastController.func_152834_a(formatStreamKbps(gamesettings.streamKbps), formatStreamFps(gamesettings.streamFps), formatStreamBps(gamesettings.streamBytesPerPixel), this.mc.displayWidth / this.mc.displayHeight);
/*     */     
/* 319 */     switch (gamesettings.streamCompression) {
/*     */       case 0:
/* 321 */         videoparams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
/*     */         break;
/*     */       
/*     */       case 1:
/* 325 */         videoparams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
/*     */         break;
/*     */       
/*     */       case 2:
/* 329 */         videoparams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
/*     */         break;
/*     */     } 
/* 332 */     if (this.framebuffer == null) {
/* 333 */       this.framebuffer = new Framebuffer(videoparams.outputWidth, videoparams.outputHeight, false);
/*     */     } else {
/* 335 */       this.framebuffer.createBindFramebuffer(videoparams.outputWidth, videoparams.outputHeight);
/*     */     } 
/*     */     
/* 338 */     if (gamesettings.streamPreferredServer != null && gamesettings.streamPreferredServer.length() > 0) {
/* 339 */       byte b; int i; IngestServer[] arrayOfIngestServer; for (i = (arrayOfIngestServer = func_152925_v()).length, b = 0; b < i; ) { IngestServer ingestserver = arrayOfIngestServer[b];
/* 340 */         if (ingestserver.serverUrl.equals(gamesettings.streamPreferredServer)) {
/* 341 */           this.broadcastController.setIngestServer(ingestserver);
/*     */           break;
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/* 347 */     this.targetFPS = videoparams.targetFps;
/* 348 */     this.field_152957_i = gamesettings.streamSendMetadata;
/* 349 */     this.broadcastController.func_152836_a(videoparams);
/* 350 */     LOGGER.info(STREAM_MARKER, "Streaming at {}/{} at {} kbps to {}", new Object[] { Integer.valueOf(videoparams.outputWidth), Integer.valueOf(videoparams.outputHeight), Integer.valueOf(videoparams.maxKbps), (this.broadcastController.getIngestServer()).serverUrl });
/* 351 */     this.broadcastController.func_152828_a(null, "Minecraft", null);
/*     */   }
/*     */   
/*     */   public void stopBroadcasting() {
/* 355 */     if (this.broadcastController.stopBroadcasting()) {
/* 356 */       LOGGER.info(STREAM_MARKER, "Stopped streaming to Twitch");
/*     */     } else {
/* 358 */       LOGGER.warn(STREAM_MARKER, "Could not stop streaming to Twitch");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_152900_a(ErrorCode p_152900_1_, AuthToken p_152900_2_) {}
/*     */   
/*     */   public void func_152897_a(ErrorCode p_152897_1_) {
/* 366 */     if (ErrorCode.succeeded(p_152897_1_)) {
/* 367 */       LOGGER.debug(STREAM_MARKER, "Login attempt successful");
/* 368 */       this.loggedIn = true;
/*     */     } else {
/* 370 */       LOGGER.warn(STREAM_MARKER, "Login attempt unsuccessful: {} (error code {})", new Object[] { ErrorCode.getString(p_152897_1_), Integer.valueOf(p_152897_1_.getValue()) });
/* 371 */       this.loggedIn = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_152898_a(ErrorCode p_152898_1_, GameInfo[] p_152898_2_) {}
/*     */   
/*     */   public void func_152891_a(BroadcastController.BroadcastState p_152891_1_) {
/* 379 */     LOGGER.debug(STREAM_MARKER, "Broadcast state changed to {}", new Object[] { p_152891_1_ });
/*     */     
/* 381 */     if (p_152891_1_ == BroadcastController.BroadcastState.Initialized) {
/* 382 */       this.broadcastController.func_152827_a(BroadcastController.BroadcastState.Authenticated);
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_152895_a() {
/* 387 */     LOGGER.info(STREAM_MARKER, "Logged out of twitch");
/*     */   }
/*     */   
/*     */   public void func_152894_a(StreamInfo p_152894_1_) {
/* 391 */     LOGGER.debug(STREAM_MARKER, "Stream info updated; {} viewers on stream ID {}", new Object[] { Integer.valueOf(p_152894_1_.viewers), Long.valueOf(p_152894_1_.streamId) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_152896_a(IngestList p_152896_1_) {}
/*     */   
/*     */   public void func_152893_b(ErrorCode p_152893_1_) {
/* 398 */     LOGGER.warn(STREAM_MARKER, "Issue submitting frame: {} (Error code {})", new Object[] { ErrorCode.getString(p_152893_1_), Integer.valueOf(p_152893_1_.getValue()) });
/* 399 */     this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((IChatComponent)new ChatComponentText("Issue streaming frame: " + p_152893_1_ + " (" + ErrorCode.getString(p_152893_1_) + ")"), 2);
/*     */   }
/*     */   
/*     */   public void func_152899_b() {
/* 403 */     updateStreamVolume();
/* 404 */     LOGGER.info(STREAM_MARKER, "Broadcast to Twitch has started");
/*     */   }
/*     */   
/*     */   public void func_152901_c() {
/* 408 */     LOGGER.info(STREAM_MARKER, "Broadcast to Twitch has stopped");
/*     */   }
/*     */   
/*     */   public void func_152892_c(ErrorCode p_152892_1_) {
/* 412 */     if (p_152892_1_ == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
/* 413 */       ChatComponentTranslation chatComponentTranslation1 = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link", new Object[0]);
/* 414 */       chatComponentTranslation1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
/* 415 */       chatComponentTranslation1.getChatStyle().setUnderlined(Boolean.valueOf(true));
/* 416 */       ChatComponentTranslation chatComponentTranslation2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", new Object[] { chatComponentTranslation1 });
/* 417 */       chatComponentTranslation2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
/* 418 */       this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatComponentTranslation2);
/*     */     } else {
/* 420 */       ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stream.unavailable.unknown.chat", new Object[] { ErrorCode.getString(p_152892_1_) });
/* 421 */       chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
/* 422 */       this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatComponentTranslation);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_152907_a(IngestServerTester p_152907_1_, IngestServerTester.IngestTestState p_152907_2_) {
/* 427 */     LOGGER.debug(STREAM_MARKER, "Ingest test state changed to {}", new Object[] { p_152907_2_ });
/*     */     
/* 429 */     if (p_152907_2_ == IngestServerTester.IngestTestState.Finished) {
/* 430 */       this.field_152960_l = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static int formatStreamFps(float p_152948_0_) {
/* 435 */     return MathHelper.floor_float(10.0F + p_152948_0_ * 50.0F);
/*     */   }
/*     */   
/*     */   public static int formatStreamKbps(float p_152946_0_) {
/* 439 */     return MathHelper.floor_float(230.0F + p_152946_0_ * 3270.0F);
/*     */   }
/*     */   
/*     */   public static float formatStreamBps(float p_152947_0_) {
/* 443 */     return 0.1F + p_152947_0_ * 0.1F;
/*     */   }
/*     */   
/*     */   public IngestServer[] func_152925_v() {
/* 447 */     return this.broadcastController.getIngestList().getServers();
/*     */   }
/*     */   
/*     */   public void func_152909_x() {
/* 451 */     IngestServerTester ingestservertester = this.broadcastController.func_152838_J();
/*     */     
/* 453 */     if (ingestservertester != null) {
/* 454 */       ingestservertester.func_153042_a(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public IngestServerTester func_152932_y() {
/* 459 */     return this.broadcastController.isReady();
/*     */   }
/*     */   
/*     */   public boolean func_152908_z() {
/* 463 */     return this.broadcastController.isIngestTesting();
/*     */   }
/*     */   
/*     */   public int func_152920_A() {
/* 467 */     return isBroadcasting() ? (this.broadcastController.getStreamInfo()).viewers : 0;
/*     */   }
/*     */   
/*     */   public void func_176023_d(ErrorCode p_176023_1_) {
/* 471 */     if (ErrorCode.failed(p_176023_1_)) {
/* 472 */       LOGGER.error(STREAM_MARKER, "Chat failed to initialize");
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_176022_e(ErrorCode p_176022_1_) {
/* 477 */     if (ErrorCode.failed(p_176022_1_))
/* 478 */       LOGGER.error(STREAM_MARKER, "Chat failed to shutdown"); 
/*     */   }
/*     */   public void func_176017_a(ChatController.ChatState p_176017_1_) {}
/*     */   
/*     */   public void func_180605_a(String p_180605_1_, ChatRawMessage[] p_180605_2_) {
/*     */     byte b;
/*     */     int i;
/*     */     ChatRawMessage[] arrayOfChatRawMessage;
/* 486 */     for (i = (arrayOfChatRawMessage = p_180605_2_).length, b = 0; b < i; ) { ChatRawMessage chatrawmessage = arrayOfChatRawMessage[b];
/* 487 */       func_176027_a(chatrawmessage.userName, chatrawmessage);
/*     */       
/* 489 */       if (func_176028_a(chatrawmessage.modes, chatrawmessage.subscriptions, this.mc.gameSettings.streamChatUserFilter)) {
/* 490 */         ChatComponentText chatComponentText1 = new ChatComponentText(chatrawmessage.userName);
/* 491 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.stream." + (chatrawmessage.action ? "emote" : "text"), new Object[] { this.twitchComponent, chatComponentText1, EnumChatFormatting.getTextWithoutFormattingCodes(chatrawmessage.message) });
/*     */         
/* 493 */         if (chatrawmessage.action) {
/* 494 */           chatComponentTranslation.getChatStyle().setItalic(Boolean.valueOf(true));
/*     */         }
/*     */         
/* 497 */         ChatComponentText chatComponentText2 = new ChatComponentText("");
/* 498 */         chatComponentText2.appendSibling((IChatComponent)new ChatComponentTranslation("stream.userinfo.chatTooltip", new Object[0]));
/*     */         
/* 500 */         for (IChatComponent ichatcomponent3 : GuiTwitchUserMode.func_152328_a(chatrawmessage.modes, chatrawmessage.subscriptions, null)) {
/* 501 */           chatComponentText2.appendText("\n");
/* 502 */           chatComponentText2.appendSibling(ichatcomponent3);
/*     */         } 
/*     */         
/* 505 */         chatComponentText1.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (IChatComponent)chatComponentText2));
/* 506 */         chatComponentText1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.TWITCH_USER_INFO, chatrawmessage.userName));
/* 507 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatComponentTranslation);
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public void func_176025_a(String p_176025_1_, ChatTokenizedMessage[] p_176025_2_) {}
/*     */   
/*     */   private void func_176027_a(String p_176027_1_, ChatRawMessage p_176027_2_) {
/* 516 */     ChatUserInfo chatuserinfo = this.field_152955_g.get(p_176027_1_);
/*     */     
/* 518 */     if (chatuserinfo == null) {
/* 519 */       chatuserinfo = new ChatUserInfo();
/* 520 */       chatuserinfo.displayName = p_176027_1_;
/* 521 */       this.field_152955_g.put(p_176027_1_, chatuserinfo);
/*     */     } 
/*     */     
/* 524 */     chatuserinfo.subscriptions = p_176027_2_.subscriptions;
/* 525 */     chatuserinfo.modes = p_176027_2_.modes;
/* 526 */     chatuserinfo.nameColorARGB = p_176027_2_.nameColorARGB;
/*     */   }
/*     */   
/*     */   private boolean func_176028_a(Set<ChatUserMode> p_176028_1_, Set<ChatUserSubscription> p_176028_2_, int p_176028_3_) {
/* 530 */     return p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED) ? false : (p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) ? true : (p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) ? true : (p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF) ? true : ((p_176028_3_ == 0) ? true : ((p_176028_3_ == 1) ? p_176028_2_.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) : false))))); } public void func_176018_a(String p_176018_1_, ChatUserInfo[] p_176018_2_, ChatUserInfo[] p_176018_3_, ChatUserInfo[] p_176018_4_) {
/*     */     byte b;
/*     */     int i;
/*     */     ChatUserInfo[] arrayOfChatUserInfo;
/* 534 */     for (i = (arrayOfChatUserInfo = p_176018_3_).length, b = 0; b < i; ) { ChatUserInfo chatuserinfo = arrayOfChatUserInfo[b];
/* 535 */       this.field_152955_g.remove(chatuserinfo.displayName);
/*     */       b++; }
/*     */     
/* 538 */     for (i = (arrayOfChatUserInfo = p_176018_4_).length, b = 0; b < i; ) { ChatUserInfo chatuserinfo1 = arrayOfChatUserInfo[b];
/* 539 */       this.field_152955_g.put(chatuserinfo1.displayName, chatuserinfo1);
/*     */       b++; }
/*     */     
/* 542 */     for (i = (arrayOfChatUserInfo = p_176018_2_).length, b = 0; b < i; ) { ChatUserInfo chatuserinfo2 = arrayOfChatUserInfo[b];
/* 543 */       this.field_152955_g.put(chatuserinfo2.displayName, chatuserinfo2);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   public void func_180606_a(String p_180606_1_) {
/* 548 */     LOGGER.debug(STREAM_MARKER, "Chat connected");
/*     */   }
/*     */   
/*     */   public void func_180607_b(String p_180607_1_) {
/* 552 */     LOGGER.debug(STREAM_MARKER, "Chat disconnected");
/* 553 */     this.field_152955_g.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_176019_a(String p_176019_1_, String p_176019_2_) {}
/*     */ 
/*     */   
/*     */   public void func_176021_d() {}
/*     */ 
/*     */   
/*     */   public void func_176024_e() {}
/*     */ 
/*     */   
/*     */   public void func_176016_c(String p_176016_1_) {}
/*     */ 
/*     */   
/*     */   public void func_176020_d(String p_176020_1_) {}
/*     */   
/*     */   public boolean func_152927_B() {
/* 572 */     return (this.field_176029_e != null && this.field_176029_e.equals((this.broadcastController.getChannelInfo()).name));
/*     */   }
/*     */   
/*     */   public String func_152921_C() {
/* 576 */     return this.field_176029_e;
/*     */   }
/*     */   
/*     */   public ChatUserInfo func_152926_a(String p_152926_1_) {
/* 580 */     return this.field_152955_g.get(p_152926_1_);
/*     */   }
/*     */   
/*     */   public void func_152917_b(String p_152917_1_) {
/* 584 */     this.chatController.func_175986_a(this.field_176029_e, p_152917_1_);
/*     */   }
/*     */   
/*     */   public boolean func_152928_D() {
/* 588 */     return (field_152965_q && this.broadcastController.func_152858_b());
/*     */   }
/*     */   
/*     */   public ErrorCode func_152912_E() {
/* 592 */     return !field_152965_q ? ErrorCode.TTV_EC_OS_TOO_OLD : this.broadcastController.getErrorCode();
/*     */   }
/*     */   
/*     */   public boolean func_152913_F() {
/* 596 */     return this.loggedIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void muteMicrophone(boolean p_152910_1_) {
/* 603 */     this.field_152963_o = p_152910_1_;
/* 604 */     updateStreamVolume();
/*     */   }
/*     */   
/*     */   public boolean func_152929_G() {
/* 608 */     boolean flag = (this.mc.gameSettings.streamMicToggleBehavior == 1);
/* 609 */     return !(!this.field_152962_n && this.mc.gameSettings.streamMicVolume > 0.0F && flag == this.field_152963_o);
/*     */   }
/*     */   
/*     */   public IStream.AuthFailureReason func_152918_H() {
/* 613 */     return this.authFailureReason;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/* 618 */       if (Util.getOSType() == Util.EnumOS.WINDOWS) {
/* 619 */         System.loadLibrary("avutil-ttv-51");
/* 620 */         System.loadLibrary("swresample-ttv-0");
/* 621 */         System.loadLibrary("libmp3lame-ttv");
/*     */         
/* 623 */         if (System.getProperty("os.arch").contains("64")) {
/* 624 */           System.loadLibrary("libmfxsw64");
/*     */         } else {
/* 626 */           System.loadLibrary("libmfxsw32");
/*     */         } 
/*     */       } 
/*     */       
/* 630 */       field_152965_q = true;
/* 631 */     } catch (Throwable var1) {
/* 632 */       field_152965_q = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\TwitchStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */