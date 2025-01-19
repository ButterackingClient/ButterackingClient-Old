/*    */ package client.gui;
/*    */ 
/*    */ import client.Client;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SplashProgress
/*    */ {
/*    */   private static final int MAX = 7;
/* 21 */   private static int PROGRESS = 0;
/* 22 */   private static String CURRENT = "";
/*    */   private static ResourceLocation splash;
/*    */   
/*    */   public static void update() {
/* 26 */     if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) {
/*    */       return;
/*    */     }
/* 29 */     drawSplash(Minecraft.getMinecraft().getTextureManager());
/*    */   }
/*    */   private static UnicodeFontRenderer ufr;
/*    */   public static void setProgress(int givenProgress, String givenTEXT) {
/* 33 */     PROGRESS = givenProgress;
/* 34 */     CURRENT = givenTEXT;
/* 35 */     update();
/*    */   }
/*    */   
/*    */   public static void drawSplash(TextureManager tm) {
/* 39 */     ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
/* 40 */     int scaleFactor = scaledResolution.getScaleFactor();
/* 41 */     Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor, true);
/* 42 */     framebuffer.bindFramebuffer(false);
/* 43 */     GlStateManager.matrixMode(5889);
/* 44 */     GlStateManager.loadIdentity();
/* 45 */     GlStateManager.ortho(0.0D, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
/* 46 */     GlStateManager.matrixMode(5888);
/* 47 */     GlStateManager.loadIdentity();
/* 48 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 49 */     GlStateManager.disableLighting();
/* 50 */     GlStateManager.disableFog();
/* 51 */     GlStateManager.disableDepth();
/* 52 */     GlStateManager.enableTexture2D();
/* 53 */     if (splash == null) {
/* 54 */       splash = new ResourceLocation("client/main_menu.jpg");
/*    */     }
/* 56 */     tm.bindTexture(splash);
/* 57 */     GlStateManager.resetColor();
/* 58 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 59 */     Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, 1920, 1080, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 1920.0F, 1080.0F);
/* 60 */     drawProgress();
/* 61 */     framebuffer.unbindFramebuffer();
/* 62 */     framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);
/* 63 */     GlStateManager.enableAlpha();
/* 64 */     GlStateManager.alphaFunc(516, 0.1F);
/* 65 */     Minecraft.getMinecraft().updateDisplay();
/*    */   }
/*    */   
/*    */   private static void drawProgress() {
/* 69 */     if ((Minecraft.getMinecraft()).gameSettings == null || Minecraft.getMinecraft().getTextureManager() == null) {
/*    */       return;
/*    */     }
/* 72 */     if (ufr == null) {
/* 73 */       ufr = UnicodeFontRenderer.getFontOnPC("Arial", 20);
/*    */     }
/* 75 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/* 76 */     double nProgress = PROGRESS;
/* 77 */     double calc = nProgress / 7.0D * sr.getScaledWidth();
/* 78 */     Gui.drawRect(0, sr.getScaledHeight() - 35, sr.getScaledWidth(), sr.getScaledHeight(), (new Color(0, 0, 0, 120)).getRGB());
/* 79 */     GlStateManager.resetColor();
/* 80 */     resetTexturestate();
/* 81 */     ufr.drawString(CURRENT, 20.0F, (sr.getScaledHeight() - 25), -1);
/* 82 */     String step = String.valueOf(PROGRESS) + "/" + '\007';
/* 83 */     ufr.drawString(step, (sr.getScaledWidth() - 20 - ufr.getStringWidth(step)), (sr.getScaledHeight() - 25), -505290241);
/* 84 */     GlStateManager.resetColor();
/* 85 */     resetTexturestate();
/* 86 */     Gui.drawRect(0, sr.getScaledHeight() - 2, (int)calc, sr.getScaledHeight(), (new Color((Client.getInstance()).splashRed, (Client.getInstance()).splashGreen, (Client.getInstance()).splashBlue)).getRGB());
/* 87 */     Gui.drawRect(0, sr.getScaledHeight() - 2, sr.getScaledWidth(), sr.getScaledHeight(), (new Color(0, 0, 0, 10)).getRGB());
/*    */   }
/*    */   
/*    */   private static void resetTexturestate() {
/* 91 */     (GlStateManager.textureState[GlStateManager.activeTextureUnit]).textureName = -1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\gui\SplashProgress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */