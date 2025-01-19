/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.InputStream;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
/*  28 */   private static final Logger logger = LogManager.getLogger();
/*  29 */   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
/*  30 */   private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
/*  31 */   private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
/*     */   private final GuiMultiplayer owner;
/*     */   private final Minecraft mc;
/*     */   private final ServerData server;
/*     */   private final ResourceLocation serverIcon;
/*     */   private String field_148299_g;
/*     */   private DynamicTexture field_148305_h;
/*     */   private long field_148298_f;
/*     */   
/*     */   protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData serverIn) {
/*  41 */     this.owner = p_i45048_1_;
/*  42 */     this.server = serverIn;
/*  43 */     this.mc = Minecraft.getMinecraft();
/*  44 */     this.serverIcon = new ResourceLocation("servers/" + serverIn.serverIP + "/icon");
/*  45 */     this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
/*     */   } public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*     */     int l;
/*     */     String s1;
/*  49 */     if (!this.server.field_78841_f) {
/*  50 */       this.server.field_78841_f = true;
/*  51 */       this.server.pingToServer = -2L;
/*  52 */       this.server.serverMOTD = "";
/*  53 */       this.server.populationInfo = "";
/*  54 */       field_148302_b.submit(new Runnable() {
/*     */             public void run() {
/*     */               try {
/*  57 */                 ServerListEntryNormal.this.owner.getOldServerPinger().ping(ServerListEntryNormal.this.server);
/*  58 */               } catch (UnknownHostException var2) {
/*  59 */                 ServerListEntryNormal.this.server.pingToServer = -1L;
/*  60 */                 ServerListEntryNormal.this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
/*  61 */               } catch (Exception var3) {
/*  62 */                 ServerListEntryNormal.this.server.pingToServer = -1L;
/*  63 */                 ServerListEntryNormal.this.server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  69 */     boolean flag = (this.server.version > 47);
/*  70 */     boolean flag1 = (this.server.version < 47);
/*  71 */     boolean flag2 = !(!flag && !flag1);
/*  72 */     this.mc.fontRendererObj.drawString(this.server.serverName, x + 32 + 3, y + 1, 16777215);
/*  73 */     List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(this.server.serverMOTD, listWidth - 32 - 2);
/*     */     
/*  75 */     for (int i = 0; i < Math.min(list.size(), 2); i++) {
/*  76 */       this.mc.fontRendererObj.drawString(list.get(i), x + 32 + 3, y + 12 + this.mc.fontRendererObj.FONT_HEIGHT * i, 8421504);
/*     */     }
/*     */     
/*  79 */     String s2 = flag2 ? (EnumChatFormatting.DARK_RED + this.server.gameVersion) : this.server.populationInfo;
/*  80 */     int j = this.mc.fontRendererObj.getStringWidth(s2);
/*  81 */     this.mc.fontRendererObj.drawString(s2, x + listWidth - j - 15 - 2, y + 1, 8421504);
/*  82 */     int k = 0;
/*  83 */     String s = null;
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (flag2) {
/*  88 */       l = 5;
/*  89 */       s1 = flag ? "Client out of date!" : "Server out of date!";
/*  90 */       s = this.server.playerList;
/*  91 */     } else if (this.server.field_78841_f && this.server.pingToServer != -2L) {
/*  92 */       if (this.server.pingToServer < 0L) {
/*  93 */         l = 5;
/*  94 */       } else if (this.server.pingToServer < 150L) {
/*  95 */         l = 0;
/*  96 */       } else if (this.server.pingToServer < 300L) {
/*  97 */         l = 1;
/*  98 */       } else if (this.server.pingToServer < 600L) {
/*  99 */         l = 2;
/* 100 */       } else if (this.server.pingToServer < 1000L) {
/* 101 */         l = 3;
/*     */       } else {
/* 103 */         l = 4;
/*     */       } 
/*     */       
/* 106 */       if (this.server.pingToServer < 0L) {
/* 107 */         s1 = "(no connection)";
/*     */       } else {
/* 109 */         s1 = String.valueOf(this.server.pingToServer) + "ms";
/* 110 */         s = this.server.playerList;
/*     */       } 
/*     */     } else {
/* 113 */       k = 1;
/* 114 */       l = (int)(Minecraft.getSystemTime() / 100L + (slotIndex * 2) & 0x7L);
/*     */       
/* 116 */       if (l > 4) {
/* 117 */         l = 8 - l;
/*     */       }
/*     */       
/* 120 */       s1 = "Pinging...";
/*     */     } 
/*     */     
/* 123 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 124 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/* 125 */     Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, (k * 10), (176 + l * 8), 10, 8, 256.0F, 256.0F);
/*     */     
/* 127 */     if (this.server.getBase64EncodedIconData() != null && !this.server.getBase64EncodedIconData().equals(this.field_148299_g)) {
/* 128 */       this.field_148299_g = this.server.getBase64EncodedIconData();
/* 129 */       prepareServerIcon();
/* 130 */       this.owner.getServerList().saveServerList();
/*     */     } 
/*     */     
/* 133 */     if (this.field_148305_h != null) {
/* 134 */       drawTextureAt(x, y, this.serverIcon);
/*     */     } else {
/* 136 */       drawTextureAt(x, y, UNKNOWN_SERVER);
/*     */     } 
/*     */     
/* 139 */     int i1 = mouseX - x;
/* 140 */     int j1 = mouseY - y;
/*     */     
/* 142 */     if (i1 >= listWidth - 15 && i1 <= listWidth - 5 && j1 >= 0 && j1 <= 8) {
/* 143 */       this.owner.setHoveringText(s1);
/* 144 */     } else if (i1 >= listWidth - j - 15 - 2 && i1 <= listWidth - 15 - 2 && j1 >= 0 && j1 <= 8) {
/* 145 */       this.owner.setHoveringText(s);
/*     */     } 
/*     */     
/* 148 */     if (this.mc.gameSettings.touchscreen || isSelected) {
/* 149 */       this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
/* 150 */       Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
/* 151 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 152 */       int k1 = mouseX - x;
/* 153 */       int l1 = mouseY - y;
/*     */       
/* 155 */       if (func_178013_b()) {
/* 156 */         if (k1 < 32 && k1 > 16) {
/* 157 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         } else {
/* 159 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */       
/* 163 */       if (this.owner.func_175392_a(this, slotIndex)) {
/* 164 */         if (k1 < 16 && l1 < 16) {
/* 165 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         } else {
/* 167 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */       
/* 171 */       if (this.owner.func_175394_b(this, slotIndex)) {
/* 172 */         if (k1 < 16 && l1 > 16) {
/* 173 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         } else {
/* 175 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawTextureAt(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
/* 182 */     this.mc.getTextureManager().bindTexture(p_178012_3_);
/* 183 */     GlStateManager.enableBlend();
/* 184 */     Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/* 185 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   private boolean func_178013_b() {
/* 189 */     return true;
/*     */   }
/*     */   
/*     */   private void prepareServerIcon() {
/* 193 */     if (this.server.getBase64EncodedIconData() == null) {
/* 194 */       this.mc.getTextureManager().deleteTexture(this.serverIcon);
/* 195 */       this.field_148305_h = null;
/*     */     } else {
/* 197 */       BufferedImage bufferedimage; ByteBuf bytebuf = Unpooled.copiedBuffer(this.server.getBase64EncodedIconData(), Charsets.UTF_8);
/* 198 */       ByteBuf bytebuf1 = Base64.decode(bytebuf);
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 203 */         bufferedimage = TextureUtil.readBufferedImage((InputStream)new ByteBufInputStream(bytebuf1));
/* 204 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/* 205 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*     */       }
/* 207 */       catch (Throwable throwable) {
/* 208 */         logger.error("Invalid icon for server " + this.server.serverName + " (" + this.server.serverIP + ")", throwable);
/* 209 */         this.server.setBase64EncodedIconData(null);
/*     */       } finally {
/* 211 */         bytebuf.release();
/* 212 */         bytebuf1.release();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 218 */       if (this.field_148305_h == null) {
/* 219 */         this.field_148305_h = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
/* 220 */         this.mc.getTextureManager().loadTexture(this.serverIcon, (ITextureObject)this.field_148305_h);
/*     */       } 
/*     */       
/* 223 */       bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.field_148305_h.getTextureData(), 0, bufferedimage.getWidth());
/* 224 */       this.field_148305_h.updateDynamicTexture();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 232 */     if (p_148278_5_ <= 32) {
/* 233 */       if (p_148278_5_ < 32 && p_148278_5_ > 16 && func_178013_b()) {
/* 234 */         this.owner.selectServer(slotIndex);
/* 235 */         this.owner.connectToSelected();
/* 236 */         return true;
/*     */       } 
/*     */       
/* 239 */       if (p_148278_5_ < 16 && p_148278_6_ < 16 && this.owner.func_175392_a(this, slotIndex)) {
/* 240 */         this.owner.func_175391_a(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 241 */         return true;
/*     */       } 
/*     */       
/* 244 */       if (p_148278_5_ < 16 && p_148278_6_ > 16 && this.owner.func_175394_b(this, slotIndex)) {
/* 245 */         this.owner.func_175393_b(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 246 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     this.owner.selectServer(slotIndex);
/*     */     
/* 252 */     if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
/* 253 */       this.owner.connectToSelected();
/*     */     }
/*     */     
/* 256 */     this.field_148298_f = Minecraft.getSystemTime();
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */   
/*     */   public ServerData getServerData() {
/* 270 */     return this.server;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\ServerListEntryNormal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */