/*     */ package net.minecraft.client.gui.stream;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.stream.IStream;
/*     */ import net.minecraft.client.stream.NullStream;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Session;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import tv.twitch.ErrorCode;
/*     */ 
/*     */ public class GuiStreamUnavailable
/*     */   extends GuiScreen
/*     */ {
/*  28 */   private static final Logger field_152322_a = LogManager.getLogger();
/*     */   private final IChatComponent field_152324_f;
/*     */   private final GuiScreen parentScreen;
/*     */   private final Reason field_152326_h;
/*     */   private final List<ChatComponentTranslation> field_152327_i;
/*     */   private final List<String> field_152323_r;
/*     */   
/*     */   public GuiStreamUnavailable(GuiScreen p_i1070_1_, Reason p_i1070_2_) {
/*  36 */     this(p_i1070_1_, p_i1070_2_, (List<ChatComponentTranslation>)null);
/*     */   }
/*     */   
/*     */   public GuiStreamUnavailable(GuiScreen parentScreenIn, Reason p_i46311_2_, List<ChatComponentTranslation> p_i46311_3_) {
/*  40 */     this.field_152324_f = (IChatComponent)new ChatComponentTranslation("stream.unavailable.title", new Object[0]);
/*  41 */     this.field_152323_r = Lists.newArrayList();
/*  42 */     this.parentScreen = parentScreenIn;
/*  43 */     this.field_152326_h = p_i46311_2_;
/*  44 */     this.field_152327_i = p_i46311_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  52 */     if (this.field_152323_r.isEmpty()) {
/*  53 */       this.field_152323_r.addAll(this.fontRendererObj.listFormattedStringToWidth(this.field_152326_h.func_152561_a().getFormattedText(), (int)(width * 0.75F)));
/*     */       
/*  55 */       if (this.field_152327_i != null) {
/*  56 */         this.field_152323_r.add("");
/*     */         
/*  58 */         for (ChatComponentTranslation chatcomponenttranslation : this.field_152327_i) {
/*  59 */           this.field_152323_r.add(chatcomponenttranslation.getUnformattedTextForChat());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     if (this.field_152326_h.func_152559_b() != null) {
/*  65 */       this.buttonList.add(new GuiButton(0, width / 2 - 155, height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  66 */       this.buttonList.add(new GuiButton(1, width / 2 - 155 + 160, height - 50, 150, 20, I18n.format(this.field_152326_h.func_152559_b().getFormattedText(), new Object[0])));
/*     */     } else {
/*  68 */       this.buttonList.add(new GuiButton(0, width / 2 - 75, height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  82 */     drawDefaultBackground();
/*  83 */     int i = Math.max((int)(height * 0.85D / 2.0D - ((this.field_152323_r.size() * this.fontRendererObj.FONT_HEIGHT) / 2.0F)), 50);
/*  84 */     drawCenteredString(this.fontRendererObj, this.field_152324_f.getFormattedText(), width / 2, i - this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
/*     */     
/*  86 */     for (String s : this.field_152323_r) {
/*  87 */       drawCenteredString(this.fontRendererObj, s, width / 2, i, 10526880);
/*  88 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */     
/*  91 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 100 */     if (button.enabled) {
/* 101 */       if (button.id == 1) {
/* 102 */         switch (this.field_152326_h) {
/*     */           case null:
/*     */           case FAILED_TWITCH_AUTH:
/* 105 */             func_152320_a("https://account.mojang.com/me/settings");
/*     */             break;
/*     */           
/*     */           case ACCOUNT_NOT_MIGRATED:
/* 109 */             func_152320_a("https://account.mojang.com/migrate");
/*     */             break;
/*     */           
/*     */           case UNSUPPORTED_OS_MAC:
/* 113 */             func_152320_a("http://www.apple.com/osx/");
/*     */             break;
/*     */           
/*     */           case LIBRARY_FAILURE:
/*     */           case INITIALIZATION_FAILURE:
/*     */           case UNKNOWN:
/* 119 */             func_152320_a("http://bugs.mojang.com/browse/MC");
/*     */             break;
/*     */         } 
/*     */       }
/* 123 */       this.mc.displayGuiScreen(this.parentScreen);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void func_152320_a(String p_152320_1_) {
/*     */     try {
/* 129 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 130 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 131 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI(p_152320_1_) });
/* 132 */     } catch (Throwable throwable) {
/* 133 */       field_152322_a.error("Couldn't open link", throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void func_152321_a(GuiScreen p_152321_0_) {
/* 138 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 139 */     IStream istream = minecraft.getTwitchStream();
/*     */     
/* 141 */     if (!OpenGlHelper.framebufferSupported) {
/* 142 */       List<ChatComponentTranslation> list = Lists.newArrayList();
/* 143 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.version", new Object[] { GL11.glGetString(7938) }));
/* 144 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.blend", new Object[] { Boolean.valueOf((GLContext.getCapabilities()).GL_EXT_blend_func_separate) }));
/* 145 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.arb", new Object[] { Boolean.valueOf((GLContext.getCapabilities()).GL_ARB_framebuffer_object) }));
/* 146 */       list.add(new ChatComponentTranslation("stream.unavailable.no_fbo.ext", new Object[] { Boolean.valueOf((GLContext.getCapabilities()).GL_EXT_framebuffer_object) }));
/* 147 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.NO_FBO, list));
/* 148 */     } else if (istream instanceof NullStream) {
/* 149 */       if (((NullStream)istream).func_152937_a().getMessage().contains("Can't load AMD 64-bit .dll on a IA 32-bit platform")) {
/* 150 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.LIBRARY_ARCH_MISMATCH));
/*     */       } else {
/* 152 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.LIBRARY_FAILURE));
/*     */       } 
/* 154 */     } else if (!istream.func_152928_D() && istream.func_152912_E() == ErrorCode.TTV_EC_OS_TOO_OLD) {
/* 155 */       switch (Util.getOSType()) {
/*     */         case WINDOWS:
/* 157 */           minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNSUPPORTED_OS_WINDOWS));
/*     */           return;
/*     */         
/*     */         case OSX:
/* 161 */           minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNSUPPORTED_OS_MAC));
/*     */           return;
/*     */       } 
/*     */       
/* 165 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNSUPPORTED_OS_OTHER));
/*     */     }
/* 167 */     else if (!minecraft.getTwitchDetails().containsKey("twitch_access_token")) {
/* 168 */       if (minecraft.getSession().getSessionType() == Session.Type.LEGACY) {
/* 169 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.ACCOUNT_NOT_MIGRATED));
/*     */       } else {
/* 171 */         minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.ACCOUNT_NOT_BOUND));
/*     */       } 
/* 173 */     } else if (!istream.func_152913_F()) {
/* 174 */       switch (istream.func_152918_H()) {
/*     */         case INVALID_TOKEN:
/* 176 */           minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.FAILED_TWITCH_AUTH));
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/* 181 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.FAILED_TWITCH_AUTH_ERROR));
/*     */     }
/* 183 */     else if (istream.func_152912_E() != null) {
/* 184 */       List<ChatComponentTranslation> list1 = Arrays.asList(new ChatComponentTranslation[] { new ChatComponentTranslation("stream.unavailable.initialization_failure.extra", new Object[] { ErrorCode.getString(istream.func_152912_E()) }) });
/* 185 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.INITIALIZATION_FAILURE, list1));
/*     */     } else {
/* 187 */       minecraft.displayGuiScreen(new GuiStreamUnavailable(p_152321_0_, Reason.UNKNOWN));
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum Reason {
/* 192 */     NO_FBO((String)new ChatComponentTranslation("stream.unavailable.no_fbo", new Object[0])),
/* 193 */     LIBRARY_ARCH_MISMATCH((String)new ChatComponentTranslation("stream.unavailable.library_arch_mismatch", new Object[0])),
/* 194 */     LIBRARY_FAILURE((String)new ChatComponentTranslation("stream.unavailable.library_failure", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
/* 195 */     UNSUPPORTED_OS_WINDOWS((String)new ChatComponentTranslation("stream.unavailable.not_supported.windows", new Object[0])),
/* 196 */     UNSUPPORTED_OS_MAC((String)new ChatComponentTranslation("stream.unavailable.not_supported.mac", new Object[0]), new ChatComponentTranslation("stream.unavailable.not_supported.mac.okay", new Object[0])),
/* 197 */     UNSUPPORTED_OS_OTHER((String)new ChatComponentTranslation("stream.unavailable.not_supported.other", new Object[0])),
/* 198 */     ACCOUNT_NOT_MIGRATED((String)new ChatComponentTranslation("stream.unavailable.account_not_migrated", new Object[0]), new ChatComponentTranslation("stream.unavailable.account_not_migrated.okay", new Object[0])),
/* 199 */     ACCOUNT_NOT_BOUND((String)new ChatComponentTranslation("stream.unavailable.account_not_bound", new Object[0]), new ChatComponentTranslation("stream.unavailable.account_not_bound.okay", new Object[0])),
/* 200 */     FAILED_TWITCH_AUTH((String)new ChatComponentTranslation("stream.unavailable.failed_auth", new Object[0]), new ChatComponentTranslation("stream.unavailable.failed_auth.okay", new Object[0])),
/* 201 */     FAILED_TWITCH_AUTH_ERROR((String)new ChatComponentTranslation("stream.unavailable.failed_auth_error", new Object[0])),
/* 202 */     INITIALIZATION_FAILURE((String)new ChatComponentTranslation("stream.unavailable.initialization_failure", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
/* 203 */     UNKNOWN((String)new ChatComponentTranslation("stream.unavailable.unknown", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0]));
/*     */ 
/*     */     
/*     */     private final IChatComponent field_152574_m;
/*     */ 
/*     */     
/*     */     private final IChatComponent field_152575_n;
/*     */ 
/*     */     
/*     */     Reason(IChatComponent p_i1067_3_, IChatComponent p_i1067_4_) {
/* 213 */       this.field_152574_m = p_i1067_3_;
/* 214 */       this.field_152575_n = p_i1067_4_;
/*     */     }
/*     */     
/*     */     public IChatComponent func_152561_a() {
/* 218 */       return this.field_152574_m;
/*     */     }
/*     */     
/*     */     public IChatComponent func_152559_b() {
/* 222 */       return this.field_152575_n;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\stream\GuiStreamUnavailable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */