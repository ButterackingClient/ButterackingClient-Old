/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.Mipmaps;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureUtil
/*     */ {
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*  26 */   private static final IntBuffer dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
/*  27 */   public static final DynamicTexture missingTexture = new DynamicTexture(16, 16);
/*  28 */   public static final int[] missingTextureData = missingTexture.getTextureData();
/*     */   private static final int[] mipmapBuffer;
/*  30 */   private static int[] dataArray = new int[4194304];
/*     */   
/*     */   public static int glGenTextures() {
/*  33 */     return GlStateManager.generateTexture();
/*     */   }
/*     */   
/*     */   public static void deleteTexture(int textureId) {
/*  37 */     GlStateManager.deleteTexture(textureId);
/*     */   }
/*     */   
/*     */   public static int uploadTextureImage(int p_110987_0_, BufferedImage p_110987_1_) {
/*  41 */     return uploadTextureImageAllocate(p_110987_0_, p_110987_1_, false, false);
/*     */   }
/*     */   
/*     */   public static void uploadTexture(int textureId, int[] p_110988_1_, int p_110988_2_, int p_110988_3_) {
/*  45 */     bindTexture(textureId);
/*  46 */     uploadTextureSub(0, p_110988_1_, p_110988_2_, p_110988_3_, 0, 0, false, false, false);
/*     */   }
/*     */   
/*     */   public static int[][] generateMipmapData(int p_147949_0_, int p_147949_1_, int[][] p_147949_2_) {
/*  50 */     int[][] aint = new int[p_147949_0_ + 1][];
/*  51 */     aint[0] = p_147949_2_[0];
/*     */     
/*  53 */     if (p_147949_0_ > 0) {
/*  54 */       boolean flag = false;
/*     */       
/*  56 */       for (int i = 0; i < (p_147949_2_[0]).length; i++) {
/*  57 */         if (p_147949_2_[0][i] >> 24 == 0) {
/*  58 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  63 */       for (int l1 = 1; l1 <= p_147949_0_; l1++) {
/*  64 */         if (p_147949_2_[l1] != null) {
/*  65 */           aint[l1] = p_147949_2_[l1];
/*     */         } else {
/*  67 */           int[] aint1 = aint[l1 - 1];
/*  68 */           int[] aint2 = new int[aint1.length >> 2];
/*  69 */           int j = p_147949_1_ >> l1;
/*  70 */           int k = aint2.length / j;
/*  71 */           int l = j << 1;
/*     */           
/*  73 */           for (int i1 = 0; i1 < j; i1++) {
/*  74 */             for (int j1 = 0; j1 < k; j1++) {
/*  75 */               int k1 = 2 * (i1 + j1 * l);
/*  76 */               aint2[i1 + j1 * j] = blendColors(aint1[k1 + 0], aint1[k1 + 1], aint1[k1 + 0 + l], aint1[k1 + 1 + l], flag);
/*     */             } 
/*     */           } 
/*     */           
/*  80 */           aint[l1] = aint2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return aint;
/*     */   }
/*     */   
/*     */   private static int blendColors(int p_147943_0_, int p_147943_1_, int p_147943_2_, int p_147943_3_, boolean p_147943_4_) {
/*  89 */     return Mipmaps.alphaBlend(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_);
/*     */   }
/*     */   
/*     */   private static int blendColorComponent(int p_147944_0_, int p_147944_1_, int p_147944_2_, int p_147944_3_, int p_147944_4_) {
/*  93 */     float f = (float)Math.pow(((p_147944_0_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/*  94 */     float f1 = (float)Math.pow(((p_147944_1_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/*  95 */     float f2 = (float)Math.pow(((p_147944_2_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/*  96 */     float f3 = (float)Math.pow(((p_147944_3_ >> p_147944_4_ & 0xFF) / 255.0F), 2.2D);
/*  97 */     float f4 = (float)Math.pow((f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D);
/*  98 */     return (int)(f4 * 255.0D);
/*     */   }
/*     */   
/*     */   public static void uploadTextureMipmap(int[][] p_147955_0_, int p_147955_1_, int p_147955_2_, int p_147955_3_, int p_147955_4_, boolean p_147955_5_, boolean p_147955_6_) {
/* 102 */     for (int i = 0; i < p_147955_0_.length; i++) {
/* 103 */       int[] aint = p_147955_0_[i];
/* 104 */       uploadTextureSub(i, aint, p_147955_1_ >> i, p_147955_2_ >> i, p_147955_3_ >> i, p_147955_4_ >> i, p_147955_5_, p_147955_6_, (p_147955_0_.length > 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void uploadTextureSub(int p_147947_0_, int[] p_147947_1_, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean p_147947_7_, boolean p_147947_8_) {
/* 109 */     int i = 4194304 / p_147947_2_;
/* 110 */     setTextureBlurMipmap(p_147947_6_, p_147947_8_);
/* 111 */     setTextureClamped(p_147947_7_);
/*     */ 
/*     */     
/* 114 */     for (int k = 0; k < p_147947_2_ * p_147947_3_; k += p_147947_2_ * j) {
/* 115 */       int l = k / p_147947_2_;
/* 116 */       int j = Math.min(i, p_147947_3_ - l);
/* 117 */       int i1 = p_147947_2_ * j;
/* 118 */       copyToBufferPos(p_147947_1_, k, i1);
/* 119 */       GL11.glTexSubImage2D(3553, p_147947_0_, p_147947_4_, p_147947_5_ + l, p_147947_2_, j, 32993, 33639, dataBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int uploadTextureImageAllocate(int p_110989_0_, BufferedImage p_110989_1_, boolean p_110989_2_, boolean p_110989_3_) {
/* 124 */     allocateTexture(p_110989_0_, p_110989_1_.getWidth(), p_110989_1_.getHeight());
/* 125 */     return uploadTextureImageSub(p_110989_0_, p_110989_1_, 0, 0, p_110989_2_, p_110989_3_);
/*     */   }
/*     */   
/*     */   public static void allocateTexture(int p_110991_0_, int p_110991_1_, int p_110991_2_) {
/* 129 */     allocateTextureImpl(p_110991_0_, 0, p_110991_1_, p_110991_2_);
/*     */   }
/*     */   
/*     */   public static void allocateTextureImpl(int p_180600_0_, int p_180600_1_, int p_180600_2_, int p_180600_3_) {
/* 133 */     Object<TextureUtil> object = (Object<TextureUtil>)TextureUtil.class;
/*     */     
/* 135 */     if (Reflector.SplashScreen.exists()) {
/* 136 */       object = (Object<TextureUtil>)Reflector.SplashScreen.getTargetClass();
/*     */     }
/*     */     
/* 139 */     synchronized (object) {
/* 140 */       deleteTexture(p_180600_0_);
/* 141 */       bindTexture(p_180600_0_);
/*     */     } 
/*     */     
/* 144 */     if (p_180600_1_ >= 0) {
/* 145 */       GL11.glTexParameteri(3553, 33085, p_180600_1_);
/* 146 */       GL11.glTexParameterf(3553, 33082, 0.0F);
/* 147 */       GL11.glTexParameterf(3553, 33083, p_180600_1_);
/* 148 */       GL11.glTexParameterf(3553, 34049, 0.0F);
/*     */     } 
/*     */     
/* 151 */     for (int i = 0; i <= p_180600_1_; i++) {
/* 152 */       GL11.glTexImage2D(3553, i, 6408, p_180600_2_ >> i, p_180600_3_ >> i, 0, 32993, 33639, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int uploadTextureImageSub(int textureId, BufferedImage p_110995_1_, int p_110995_2_, int p_110995_3_, boolean p_110995_4_, boolean p_110995_5_) {
/* 157 */     bindTexture(textureId);
/* 158 */     uploadTextureImageSubImpl(p_110995_1_, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
/* 159 */     return textureId;
/*     */   }
/*     */   
/*     */   private static void uploadTextureImageSubImpl(BufferedImage p_110993_0_, int p_110993_1_, int p_110993_2_, boolean p_110993_3_, boolean p_110993_4_) {
/* 163 */     int i = p_110993_0_.getWidth();
/* 164 */     int j = p_110993_0_.getHeight();
/* 165 */     int k = 4194304 / i;
/* 166 */     int[] aint = dataArray;
/* 167 */     setTextureBlurred(p_110993_3_);
/* 168 */     setTextureClamped(p_110993_4_);
/*     */     
/* 170 */     for (int l = 0; l < i * j; l += i * k) {
/* 171 */       int i1 = l / i;
/* 172 */       int j1 = Math.min(k, j - i1);
/* 173 */       int k1 = i * j1;
/* 174 */       p_110993_0_.getRGB(0, i1, i, j1, aint, 0, i);
/* 175 */       copyToBuffer(aint, k1);
/* 176 */       GL11.glTexSubImage2D(3553, 0, p_110993_1_, p_110993_2_ + i1, i, j1, 32993, 33639, dataBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setTextureClamped(boolean p_110997_0_) {
/* 181 */     if (p_110997_0_) {
/* 182 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 183 */       GL11.glTexParameteri(3553, 10243, 33071);
/*     */     } else {
/* 185 */       GL11.glTexParameteri(3553, 10242, 10497);
/* 186 */       GL11.glTexParameteri(3553, 10243, 10497);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setTextureBlurred(boolean p_147951_0_) {
/* 191 */     setTextureBlurMipmap(p_147951_0_, false);
/*     */   }
/*     */   
/*     */   public static void setTextureBlurMipmap(boolean p_147954_0_, boolean p_147954_1_) {
/* 195 */     if (p_147954_0_) {
/* 196 */       GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9987 : 9729);
/* 197 */       GL11.glTexParameteri(3553, 10240, 9729);
/*     */     } else {
/* 199 */       int i = Config.getMipmapType();
/* 200 */       GL11.glTexParameteri(3553, 10241, p_147954_1_ ? i : 9728);
/* 201 */       GL11.glTexParameteri(3553, 10240, 9728);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void copyToBuffer(int[] p_110990_0_, int p_110990_1_) {
/* 206 */     copyToBufferPos(p_110990_0_, 0, p_110990_1_);
/*     */   }
/*     */   
/*     */   private static void copyToBufferPos(int[] p_110994_0_, int p_110994_1_, int p_110994_2_) {
/* 210 */     int[] aint = p_110994_0_;
/*     */     
/* 212 */     if ((Minecraft.getMinecraft()).gameSettings.anaglyph) {
/* 213 */       aint = updateAnaglyph(p_110994_0_);
/*     */     }
/*     */     
/* 216 */     dataBuffer.clear();
/* 217 */     dataBuffer.put(aint, p_110994_1_, p_110994_2_);
/* 218 */     dataBuffer.position(0).limit(p_110994_2_);
/*     */   }
/*     */   
/*     */   static void bindTexture(int p_94277_0_) {
/* 222 */     GlStateManager.bindTexture(p_94277_0_);
/*     */   }
/*     */   
/*     */   public static int[] readImageData(IResourceManager resourceManager, ResourceLocation imageLocation) throws IOException {
/* 226 */     BufferedImage bufferedimage = readBufferedImage(resourceManager.getResource(imageLocation).getInputStream());
/*     */     
/* 228 */     if (bufferedimage == null) {
/* 229 */       return null;
/*     */     }
/* 231 */     int i = bufferedimage.getWidth();
/* 232 */     int j = bufferedimage.getHeight();
/* 233 */     int[] aint = new int[i * j];
/* 234 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/* 235 */     return aint;
/*     */   }
/*     */   
/*     */   public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
/*     */     BufferedImage bufferedimage;
/* 240 */     if (imageStream == null) {
/* 241 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 246 */       bufferedimage = ImageIO.read(imageStream);
/*     */     } finally {
/* 248 */       IOUtils.closeQuietly(imageStream);
/*     */     } 
/*     */     
/* 251 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] updateAnaglyph(int[] p_110985_0_) {
/* 256 */     int[] aint = new int[p_110985_0_.length];
/*     */     
/* 258 */     for (int i = 0; i < p_110985_0_.length; i++) {
/* 259 */       aint[i] = anaglyphColor(p_110985_0_[i]);
/*     */     }
/*     */     
/* 262 */     return aint;
/*     */   }
/*     */   
/*     */   public static int anaglyphColor(int p_177054_0_) {
/* 266 */     int i = p_177054_0_ >> 24 & 0xFF;
/* 267 */     int j = p_177054_0_ >> 16 & 0xFF;
/* 268 */     int k = p_177054_0_ >> 8 & 0xFF;
/* 269 */     int l = p_177054_0_ & 0xFF;
/* 270 */     int i1 = (j * 30 + k * 59 + l * 11) / 100;
/* 271 */     int j1 = (j * 30 + k * 70) / 100;
/* 272 */     int k1 = (j * 30 + l * 70) / 100;
/* 273 */     return i << 24 | i1 << 16 | j1 << 8 | k1;
/*     */   }
/*     */   
/*     */   public static void processPixelValues(int[] p_147953_0_, int p_147953_1_, int p_147953_2_) {
/* 277 */     int[] aint = new int[p_147953_1_];
/* 278 */     int i = p_147953_2_ / 2;
/*     */     
/* 280 */     for (int j = 0; j < i; j++) {
/* 281 */       System.arraycopy(p_147953_0_, j * p_147953_1_, aint, 0, p_147953_1_);
/* 282 */       System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_0_, j * p_147953_1_, p_147953_1_);
/* 283 */       System.arraycopy(aint, 0, p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_1_);
/*     */     } 
/*     */   }
/*     */   
/*     */   static {
/* 288 */     int i = -16777216;
/* 289 */     int j = -524040;
/* 290 */     int[] aint = { -524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040 };
/* 291 */     int[] aint1 = { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
/* 292 */     int k = aint.length;
/*     */     
/* 294 */     for (int l = 0; l < 16; l++) {
/* 295 */       System.arraycopy((l < k) ? aint : aint1, 0, missingTextureData, 16 * l, k);
/* 296 */       System.arraycopy((l < k) ? aint1 : aint, 0, missingTextureData, 16 * l + k, k);
/*     */     } 
/*     */     
/* 299 */     missingTexture.updateDynamicTexture();
/* 300 */     mipmapBuffer = new int[4];
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\TextureUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */