/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.nio.IntBuffer;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.src.Config;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ScreenShotHelper
/*     */ {
/*  26 */   private static final Logger logger = LogManager.getLogger();
/*  27 */   private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntBuffer pixelBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] pixelValues;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IChatComponent saveScreenshot(File gameDirectory, int width, int height, Framebuffer buffer) {
/*  44 */     return saveScreenshot(gameDirectory, null, width, height, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IChatComponent saveScreenshot(File gameDirectory, String screenshotName, int width, int height, Framebuffer buffer) {
/*     */     try {
/*  53 */       File file1 = new File(gameDirectory, "screenshots");
/*  54 */       file1.mkdir();
/*  55 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  56 */       int i = (Config.getGameSettings()).particleSetting;
/*  57 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/*  58 */       int j = scaledresolution.getScaleFactor();
/*  59 */       int k = Config.getScreenshotSize();
/*  60 */       boolean flag = (OpenGlHelper.isFramebufferEnabled() && k > 1);
/*     */       
/*  62 */       if (flag) {
/*  63 */         (Config.getGameSettings()).particleSetting = j * k;
/*  64 */         resize(width * k, height * k);
/*  65 */         GlStateManager.pushMatrix();
/*  66 */         GlStateManager.clear(16640);
/*  67 */         minecraft.getFramebuffer().bindFramebuffer(true);
/*  68 */         minecraft.entityRenderer.updateCameraAndRender(Config.renderPartialTicks, System.nanoTime());
/*     */       } 
/*     */       
/*  71 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*  72 */         width = buffer.framebufferTextureWidth;
/*  73 */         height = buffer.framebufferTextureHeight;
/*     */       } 
/*     */       
/*  76 */       int l = width * height;
/*     */       
/*  78 */       if (pixelBuffer == null || pixelBuffer.capacity() < l) {
/*  79 */         pixelBuffer = BufferUtils.createIntBuffer(l);
/*  80 */         pixelValues = new int[l];
/*     */       } 
/*     */       
/*  83 */       GL11.glPixelStorei(3333, 1);
/*  84 */       GL11.glPixelStorei(3317, 1);
/*  85 */       pixelBuffer.clear();
/*     */       
/*  87 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*  88 */         GlStateManager.bindTexture(buffer.framebufferTexture);
/*  89 */         GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
/*     */       } else {
/*  91 */         GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
/*     */       } 
/*     */       
/*  94 */       pixelBuffer.get(pixelValues);
/*  95 */       TextureUtil.processPixelValues(pixelValues, width, height);
/*  96 */       BufferedImage bufferedimage = null;
/*     */       
/*  98 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*  99 */         bufferedimage = new BufferedImage(buffer.framebufferWidth, buffer.framebufferHeight, 1);
/* 100 */         int i1 = buffer.framebufferTextureHeight - buffer.framebufferHeight;
/*     */         
/* 102 */         for (int j1 = i1; j1 < buffer.framebufferTextureHeight; j1++) {
/* 103 */           for (int k1 = 0; k1 < buffer.framebufferWidth; k1++) {
/* 104 */             bufferedimage.setRGB(k1, j1 - i1, pixelValues[j1 * buffer.framebufferTextureWidth + k1]);
/*     */           }
/*     */         } 
/*     */       } else {
/* 108 */         bufferedimage = new BufferedImage(width, height, 1);
/* 109 */         bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
/*     */       } 
/*     */       
/* 112 */       if (flag) {
/* 113 */         minecraft.getFramebuffer().unbindFramebuffer();
/* 114 */         GlStateManager.popMatrix();
/* 115 */         (Config.getGameSettings()).particleSetting = i;
/* 116 */         resize(width, height);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (screenshotName == null) {
/* 122 */         file2 = getTimestampedPNGFileForDirectory(file1);
/*     */       } else {
/* 124 */         file2 = new File(file1, screenshotName);
/*     */       } 
/*     */       
/* 127 */       File file2 = file2.getCanonicalFile();
/* 128 */       ImageIO.write(bufferedimage, "png", file2);
/* 129 */       IChatComponent ichatcomponent = new ChatComponentText(file2.getName());
/* 130 */       ichatcomponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
/* 131 */       ichatcomponent.getChatStyle().setUnderlined(Boolean.valueOf(true));
/* 132 */       return new ChatComponentTranslation("screenshot.success", new Object[] { ichatcomponent });
/* 133 */     } catch (Exception exception) {
/* 134 */       logger.warn("Couldn't save screenshot", exception);
/* 135 */       return new ChatComponentTranslation("screenshot.failure", new Object[] { exception.getMessage() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
/* 146 */     String s = dateFormat.format(new Date()).toString();
/* 147 */     int i = 1;
/*     */     
/*     */     while (true) {
/* 150 */       File file1 = new File(gameDirectory, String.valueOf(s) + ((i == 1) ? "" : ("_" + i)) + ".png");
/*     */       
/* 152 */       if (!file1.exists()) {
/* 153 */         return file1;
/*     */       }
/*     */       
/* 156 */       i++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void resize(int p_resize_0_, int p_resize_1_) {
/* 161 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 162 */     minecraft.displayWidth = Math.max(1, p_resize_0_);
/* 163 */     minecraft.displayHeight = Math.max(1, p_resize_1_);
/*     */     
/* 165 */     if (minecraft.currentScreen != null) {
/* 166 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 167 */       minecraft.currentScreen.onResize(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*     */     } 
/*     */     
/* 170 */     updateFramebufferSize();
/*     */   }
/*     */   
/*     */   private static void updateFramebufferSize() {
/* 174 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 175 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*     */     
/* 177 */     if (minecraft.entityRenderer != null)
/* 178 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ScreenShotHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */