/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.data.AnimationFrame;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.CounterInt;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ public class TextureAtlasSprite
/*     */ {
/*     */   private final String iconName;
/*  27 */   protected List<int[][]> framesTextureData = Lists.newArrayList();
/*     */   protected int[][] interpolatedFrameData;
/*     */   private AnimationMetadataSection animationMetadata;
/*     */   protected boolean rotated;
/*     */   protected int originX;
/*     */   protected int originY;
/*     */   protected int width;
/*     */   protected int height;
/*     */   private float minU;
/*     */   private float maxU;
/*     */   private float minV;
/*     */   private float maxV;
/*     */   protected int frameCounter;
/*     */   protected int tickCounter;
/*  41 */   private static String locationNameClock = "builtin/clock";
/*  42 */   private static String locationNameCompass = "builtin/compass";
/*  43 */   private int indexInMap = -1;
/*     */   public float baseU;
/*     */   public float baseV;
/*     */   public int sheetWidth;
/*     */   public int sheetHeight;
/*  48 */   public int glSpriteTextureId = -1;
/*  49 */   public TextureAtlasSprite spriteSingle = null;
/*     */   public boolean isSpriteSingle = false;
/*  51 */   public int mipmapLevels = 0;
/*  52 */   public TextureAtlasSprite spriteNormal = null;
/*  53 */   public TextureAtlasSprite spriteSpecular = null;
/*     */   public boolean isShadersSprite = false;
/*     */   public boolean isEmissive = false;
/*  56 */   public TextureAtlasSprite spriteEmissive = null;
/*  57 */   private int animationIndex = -1;
/*     */   private boolean animationActive = false;
/*     */   
/*     */   private TextureAtlasSprite(String p_i7_1_, boolean p_i7_2_) {
/*  61 */     this.iconName = p_i7_1_;
/*  62 */     this.isSpriteSingle = p_i7_2_;
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite(String spriteName) {
/*  66 */     this.iconName = spriteName;
/*     */     
/*  68 */     if (Config.isMultiTexture()) {
/*  69 */       this.spriteSingle = new TextureAtlasSprite(String.valueOf(getIconName()) + ".spriteSingle", true);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation spriteResourceLocation) {
/*  74 */     String s = spriteResourceLocation.toString();
/*  75 */     return locationNameClock.equals(s) ? new TextureClock(s) : (locationNameCompass.equals(s) ? new TextureCompass(s) : new TextureAtlasSprite(s));
/*     */   }
/*     */   
/*     */   public static void setLocationNameClock(String clockName) {
/*  79 */     locationNameClock = clockName;
/*     */   }
/*     */   
/*     */   public static void setLocationNameCompass(String compassName) {
/*  83 */     locationNameCompass = compassName;
/*     */   }
/*     */   
/*     */   public void initSprite(int inX, int inY, int originInX, int originInY, boolean rotatedIn) {
/*  87 */     this.originX = originInX;
/*  88 */     this.originY = originInY;
/*  89 */     this.rotated = rotatedIn;
/*  90 */     float f = (float)(0.009999999776482582D / inX);
/*  91 */     float f1 = (float)(0.009999999776482582D / inY);
/*  92 */     this.minU = originInX / (float)inX + f;
/*  93 */     this.maxU = (originInX + this.width) / (float)inX - f;
/*  94 */     this.minV = originInY / inY + f1;
/*  95 */     this.maxV = (originInY + this.height) / inY - f1;
/*  96 */     this.baseU = Math.min(this.minU, this.maxU);
/*  97 */     this.baseV = Math.min(this.minV, this.maxV);
/*     */     
/*  99 */     if (this.spriteSingle != null) {
/* 100 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */     
/* 103 */     if (this.spriteNormal != null) {
/* 104 */       this.spriteNormal.copyFrom(this);
/*     */     }
/*     */     
/* 107 */     if (this.spriteSpecular != null) {
/* 108 */       this.spriteSpecular.copyFrom(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void copyFrom(TextureAtlasSprite atlasSpirit) {
/* 113 */     this.originX = atlasSpirit.originX;
/* 114 */     this.originY = atlasSpirit.originY;
/* 115 */     this.width = atlasSpirit.width;
/* 116 */     this.height = atlasSpirit.height;
/* 117 */     this.rotated = atlasSpirit.rotated;
/* 118 */     this.minU = atlasSpirit.minU;
/* 119 */     this.maxU = atlasSpirit.maxU;
/* 120 */     this.minV = atlasSpirit.minV;
/* 121 */     this.maxV = atlasSpirit.maxV;
/*     */     
/* 123 */     if (atlasSpirit != Config.getTextureMap().getMissingSprite()) {
/* 124 */       this.indexInMap = atlasSpirit.indexInMap;
/*     */     }
/*     */     
/* 127 */     this.baseU = atlasSpirit.baseU;
/* 128 */     this.baseV = atlasSpirit.baseV;
/* 129 */     this.sheetWidth = atlasSpirit.sheetWidth;
/* 130 */     this.sheetHeight = atlasSpirit.sheetHeight;
/* 131 */     this.glSpriteTextureId = atlasSpirit.glSpriteTextureId;
/* 132 */     this.mipmapLevels = atlasSpirit.mipmapLevels;
/*     */     
/* 134 */     if (this.spriteSingle != null) {
/* 135 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */     
/* 138 */     this.animationIndex = atlasSpirit.animationIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 145 */     return this.originX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/* 152 */     return this.originY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 159 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 166 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinU() {
/* 173 */     return this.minU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxU() {
/* 180 */     return this.maxU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedU(double u) {
/* 187 */     float f = this.maxU - this.minU;
/* 188 */     return this.minU + f * (float)u / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinV() {
/* 195 */     return this.minV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxV() {
/* 202 */     return this.maxV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedV(double v) {
/* 209 */     float f = this.maxV - this.minV;
/* 210 */     return this.minV + f * (float)v / 16.0F;
/*     */   }
/*     */   
/*     */   public String getIconName() {
/* 214 */     return this.iconName;
/*     */   }
/*     */   
/*     */   public void updateAnimation() {
/* 218 */     if (this.animationMetadata != null) {
/* 219 */       this.animationActive = SmartAnimations.isActive() ? SmartAnimations.isSpriteRendered(this.animationIndex) : true;
/* 220 */       this.tickCounter++;
/*     */       
/* 222 */       if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
/* 223 */         int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 224 */         int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 225 */         this.frameCounter = (this.frameCounter + 1) % j;
/* 226 */         this.tickCounter = 0;
/* 227 */         int k = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 228 */         boolean flag = false;
/* 229 */         boolean flag1 = this.isSpriteSingle;
/*     */         
/* 231 */         if (!this.animationActive) {
/*     */           return;
/*     */         }
/*     */         
/* 235 */         if (i != k && k >= 0 && k < this.framesTextureData.size()) {
/* 236 */           TextureUtil.uploadTextureMipmap(this.framesTextureData.get(k), this.width, this.height, this.originX, this.originY, flag, flag1);
/*     */         }
/* 238 */       } else if (this.animationMetadata.isInterpolate()) {
/* 239 */         if (!this.animationActive) {
/*     */           return;
/*     */         }
/*     */         
/* 243 */         updateAnimationInterpolated();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateAnimationInterpolated() {
/* 249 */     double d0 = 1.0D - this.tickCounter / this.animationMetadata.getFrameTimeSingle(this.frameCounter);
/* 250 */     int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 251 */     int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 252 */     int k = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % j);
/*     */     
/* 254 */     if (i != k && k >= 0 && k < this.framesTextureData.size()) {
/* 255 */       int[][] aint = this.framesTextureData.get(i);
/* 256 */       int[][] aint1 = this.framesTextureData.get(k);
/*     */       
/* 258 */       if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != aint.length) {
/* 259 */         this.interpolatedFrameData = new int[aint.length][];
/*     */       }
/*     */       
/* 262 */       for (int l = 0; l < aint.length; l++) {
/* 263 */         if (this.interpolatedFrameData[l] == null) {
/* 264 */           this.interpolatedFrameData[l] = new int[(aint[l]).length];
/*     */         }
/*     */         
/* 267 */         if (l < aint1.length && (aint1[l]).length == (aint[l]).length) {
/* 268 */           for (int i1 = 0; i1 < (aint[l]).length; i1++) {
/* 269 */             int j1 = aint[l][i1];
/* 270 */             int k1 = aint1[l][i1];
/* 271 */             int l1 = (int)(((j1 & 0xFF0000) >> 16) * d0 + ((k1 & 0xFF0000) >> 16) * (1.0D - d0));
/* 272 */             int i2 = (int)(((j1 & 0xFF00) >> 8) * d0 + ((k1 & 0xFF00) >> 8) * (1.0D - d0));
/* 273 */             int j2 = (int)((j1 & 0xFF) * d0 + (k1 & 0xFF) * (1.0D - d0));
/* 274 */             this.interpolatedFrameData[l][i1] = j1 & 0xFF000000 | l1 << 16 | i2 << 8 | j2;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 279 */       TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int[][] getFrameTextureData(int index) {
/* 284 */     return this.framesTextureData.get(index);
/*     */   }
/*     */   
/*     */   public int getFrameCount() {
/* 288 */     return this.framesTextureData.size();
/*     */   }
/*     */   
/*     */   public void setIconWidth(int newWidth) {
/* 292 */     this.width = newWidth;
/*     */     
/* 294 */     if (this.spriteSingle != null) {
/* 295 */       this.spriteSingle.setIconWidth(this.width);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setIconHeight(int newHeight) {
/* 300 */     this.height = newHeight;
/*     */     
/* 302 */     if (this.spriteSingle != null) {
/* 303 */       this.spriteSingle.setIconHeight(this.height);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadSprite(BufferedImage[] images, AnimationMetadataSection meta) throws IOException {
/* 308 */     resetSprite();
/* 309 */     int i = images[0].getWidth();
/* 310 */     int j = images[0].getHeight();
/* 311 */     this.width = i;
/* 312 */     this.height = j;
/*     */     
/* 314 */     if (this.spriteSingle != null) {
/* 315 */       this.spriteSingle.width = this.width;
/* 316 */       this.spriteSingle.height = this.height;
/*     */     } 
/*     */     
/* 319 */     int[][] aint = new int[images.length][];
/*     */     
/* 321 */     for (int k = 0; k < images.length; k++) {
/* 322 */       BufferedImage bufferedimage = images[k];
/*     */       
/* 324 */       if (bufferedimage != null) {
/* 325 */         if (this.width >> k != bufferedimage.getWidth()) {
/* 326 */           bufferedimage = TextureUtils.scaleImage(bufferedimage, this.width >> k);
/*     */         }
/*     */         
/* 329 */         if (k > 0 && (bufferedimage.getWidth() != i >> k || bufferedimage.getHeight() != j >> k)) {
/* 330 */           throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[] { Integer.valueOf(k), Integer.valueOf(bufferedimage.getWidth()), Integer.valueOf(bufferedimage.getHeight()), Integer.valueOf(i >> k), Integer.valueOf(j >> k) }));
/*     */         }
/*     */         
/* 333 */         aint[k] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
/* 334 */         bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), aint[k], 0, bufferedimage.getWidth());
/*     */       } 
/*     */     } 
/*     */     
/* 338 */     if (meta == null) {
/* 339 */       if (j != i) {
/* 340 */         throw new RuntimeException("broken aspect ratio and not an animation");
/*     */       }
/*     */       
/* 343 */       this.framesTextureData.add(aint);
/*     */     } else {
/* 345 */       int j1 = j / i;
/* 346 */       int l1 = i;
/* 347 */       int l = i;
/* 348 */       this.height = this.width;
/*     */       
/* 350 */       if (meta.getFrameCount() > 0) {
/* 351 */         Iterator<Integer> iterator = meta.getFrameIndexSet().iterator();
/*     */         
/* 353 */         while (iterator.hasNext()) {
/* 354 */           int i1 = ((Integer)iterator.next()).intValue();
/*     */           
/* 356 */           if (i1 >= j1) {
/* 357 */             throw new RuntimeException("invalid frameindex " + i1);
/*     */           }
/*     */           
/* 360 */           allocateFrameTextureData(i1);
/* 361 */           this.framesTextureData.set(i1, getFrameTextureData(aint, l1, l, i1));
/*     */         } 
/*     */         
/* 364 */         this.animationMetadata = meta;
/*     */       } else {
/* 366 */         List<AnimationFrame> list = Lists.newArrayList();
/*     */         
/* 368 */         for (int j2 = 0; j2 < j1; j2++) {
/* 369 */           this.framesTextureData.add(getFrameTextureData(aint, l1, l, j2));
/* 370 */           list.add(new AnimationFrame(j2, -1));
/*     */         } 
/*     */         
/* 373 */         this.animationMetadata = new AnimationMetadataSection(list, this.width, this.height, meta.getFrameTime(), meta.isInterpolate());
/*     */       } 
/*     */     } 
/*     */     
/* 377 */     if (!this.isShadersSprite) {
/* 378 */       if (Config.isShaders()) {
/* 379 */         loadShadersSprites();
/*     */       }
/*     */       
/* 382 */       for (int k1 = 0; k1 < this.framesTextureData.size(); k1++) {
/* 383 */         int[][] aint1 = this.framesTextureData.get(k1);
/*     */         
/* 385 */         if (aint1 != null && !this.iconName.startsWith("minecraft:blocks/leaves_")) {
/* 386 */           for (int i2 = 0; i2 < aint1.length; i2++) {
/* 387 */             int[] aint2 = aint1[i2];
/* 388 */             fixTransparentColor(aint2);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 393 */       if (this.spriteSingle != null) {
/* 394 */         this.spriteSingle.loadSprite(images, meta);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void generateMipmaps(int level) {
/* 400 */     List<int[][]> list = Lists.newArrayList();
/*     */     
/* 402 */     for (int i = 0; i < this.framesTextureData.size(); i++) {
/* 403 */       final int[][] aint = this.framesTextureData.get(i);
/*     */       
/* 405 */       if (aint != null) {
/*     */         try {
/* 407 */           list.add(TextureUtil.generateMipmapData(level, this.width, aint));
/* 408 */         } catch (Throwable throwable) {
/* 409 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
/* 410 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Frame being iterated");
/* 411 */           crashreportcategory.addCrashSection("Frame index", Integer.valueOf(i));
/* 412 */           crashreportcategory.addCrashSectionCallable("Frame sizes", new Callable<String>() {
/*     */                 public String call() throws Exception {
/* 414 */                   StringBuilder stringbuilder = new StringBuilder(); byte b;
/*     */                   int i, arrayOfInt[][];
/* 416 */                   for (i = (arrayOfInt = aint).length, b = 0; b < i; ) { int[] aint1 = arrayOfInt[b];
/* 417 */                     if (stringbuilder.length() > 0) {
/* 418 */                       stringbuilder.append(", ");
/*     */                     }
/*     */                     
/* 421 */                     stringbuilder.append((aint1 == null) ? "null" : Integer.valueOf(aint1.length));
/*     */                     b++; }
/*     */                   
/* 424 */                   return stringbuilder.toString();
/*     */                 }
/*     */               });
/* 427 */           throw new ReportedException(crashreport);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 432 */     setFramesTextureData(list);
/*     */     
/* 434 */     if (this.spriteSingle != null) {
/* 435 */       this.spriteSingle.generateMipmaps(level);
/*     */     }
/*     */   }
/*     */   
/*     */   private void allocateFrameTextureData(int index) {
/* 440 */     if (this.framesTextureData.size() <= index) {
/* 441 */       for (int i = this.framesTextureData.size(); i <= index; i++) {
/* 442 */         this.framesTextureData.add(null);
/*     */       }
/*     */     }
/*     */     
/* 446 */     if (this.spriteSingle != null) {
/* 447 */       this.spriteSingle.allocateFrameTextureData(index);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int[][] getFrameTextureData(int[][] data, int rows, int columns, int p_147962_3_) {
/* 452 */     int[][] aint = new int[data.length][];
/*     */     
/* 454 */     for (int i = 0; i < data.length; i++) {
/* 455 */       int[] aint1 = data[i];
/*     */       
/* 457 */       if (aint1 != null) {
/* 458 */         aint[i] = new int[(rows >> i) * (columns >> i)];
/* 459 */         System.arraycopy(aint1, p_147962_3_ * (aint[i]).length, aint[i], 0, (aint[i]).length);
/*     */       } 
/*     */     } 
/*     */     
/* 463 */     return aint;
/*     */   }
/*     */   
/*     */   public void clearFramesTextureData() {
/* 467 */     this.framesTextureData.clear();
/*     */     
/* 469 */     if (this.spriteSingle != null) {
/* 470 */       this.spriteSingle.clearFramesTextureData();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasAnimationMetadata() {
/* 475 */     return (this.animationMetadata != null);
/*     */   }
/*     */   
/*     */   public void setFramesTextureData(List<int[][]> newFramesTextureData) {
/* 479 */     this.framesTextureData = newFramesTextureData;
/*     */     
/* 481 */     if (this.spriteSingle != null) {
/* 482 */       this.spriteSingle.setFramesTextureData(newFramesTextureData);
/*     */     }
/*     */   }
/*     */   
/*     */   private void resetSprite() {
/* 487 */     this.animationMetadata = null;
/* 488 */     setFramesTextureData(Lists.newArrayList());
/* 489 */     this.frameCounter = 0;
/* 490 */     this.tickCounter = 0;
/*     */     
/* 492 */     if (this.spriteSingle != null) {
/* 493 */       this.spriteSingle.resetSprite();
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString() {
/* 498 */     return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
/*     */   }
/*     */   
/*     */   public boolean hasCustomLoader(IResourceManager p_hasCustomLoader_1_, ResourceLocation p_hasCustomLoader_2_) {
/* 502 */     return false;
/*     */   }
/*     */   
/*     */   public boolean load(IResourceManager p_load_1_, ResourceLocation p_load_2_) {
/* 506 */     return true;
/*     */   }
/*     */   
/*     */   public int getIndexInMap() {
/* 510 */     return this.indexInMap;
/*     */   }
/*     */   
/*     */   public void setIndexInMap(int p_setIndexInMap_1_) {
/* 514 */     this.indexInMap = p_setIndexInMap_1_;
/*     */   }
/*     */   
/*     */   public void updateIndexInMap(CounterInt p_updateIndexInMap_1_) {
/* 518 */     if (this.indexInMap < 0) {
/* 519 */       this.indexInMap = p_updateIndexInMap_1_.nextValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getAnimationIndex() {
/* 524 */     return this.animationIndex;
/*     */   }
/*     */   
/*     */   public void setAnimationIndex(int p_setAnimationIndex_1_) {
/* 528 */     this.animationIndex = p_setAnimationIndex_1_;
/*     */     
/* 530 */     if (this.spriteNormal != null) {
/* 531 */       this.spriteNormal.setAnimationIndex(p_setAnimationIndex_1_);
/*     */     }
/*     */     
/* 534 */     if (this.spriteSpecular != null) {
/* 535 */       this.spriteSpecular.setAnimationIndex(p_setAnimationIndex_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isAnimationActive() {
/* 540 */     return this.animationActive;
/*     */   }
/*     */   
/*     */   private void fixTransparentColor(int[] p_fixTransparentColor_1_) {
/* 544 */     if (p_fixTransparentColor_1_ != null) {
/* 545 */       long i = 0L;
/* 546 */       long j = 0L;
/* 547 */       long k = 0L;
/* 548 */       long l = 0L;
/*     */       
/* 550 */       for (int i1 = 0; i1 < p_fixTransparentColor_1_.length; i1++) {
/* 551 */         int j1 = p_fixTransparentColor_1_[i1];
/* 552 */         int k1 = j1 >> 24 & 0xFF;
/*     */         
/* 554 */         if (k1 >= 16) {
/* 555 */           int l1 = j1 >> 16 & 0xFF;
/* 556 */           int i2 = j1 >> 8 & 0xFF;
/* 557 */           int j2 = j1 & 0xFF;
/* 558 */           i += l1;
/* 559 */           j += i2;
/* 560 */           k += j2;
/* 561 */           l++;
/*     */         } 
/*     */       } 
/*     */       
/* 565 */       if (l > 0L) {
/* 566 */         int l2 = (int)(i / l);
/* 567 */         int i3 = (int)(j / l);
/* 568 */         int j3 = (int)(k / l);
/* 569 */         int k3 = l2 << 16 | i3 << 8 | j3;
/*     */         
/* 571 */         for (int l3 = 0; l3 < p_fixTransparentColor_1_.length; l3++) {
/* 572 */           int i4 = p_fixTransparentColor_1_[l3];
/* 573 */           int k2 = i4 >> 24 & 0xFF;
/*     */           
/* 575 */           if (k2 <= 16) {
/* 576 */             p_fixTransparentColor_1_[l3] = k3;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getSpriteU16(float p_getSpriteU16_1_) {
/* 584 */     float f = this.maxU - this.minU;
/* 585 */     return ((p_getSpriteU16_1_ - this.minU) / f * 16.0F);
/*     */   }
/*     */   
/*     */   public double getSpriteV16(float p_getSpriteV16_1_) {
/* 589 */     float f = this.maxV - this.minV;
/* 590 */     return ((p_getSpriteV16_1_ - this.minV) / f * 16.0F);
/*     */   }
/*     */   
/*     */   public void bindSpriteTexture() {
/* 594 */     if (this.glSpriteTextureId < 0) {
/* 595 */       this.glSpriteTextureId = TextureUtil.glGenTextures();
/* 596 */       TextureUtil.allocateTextureImpl(this.glSpriteTextureId, this.mipmapLevels, this.width, this.height);
/* 597 */       TextureUtils.applyAnisotropicLevel();
/*     */     } 
/*     */     
/* 600 */     TextureUtils.bindTexture(this.glSpriteTextureId);
/*     */   }
/*     */   
/*     */   public void deleteSpriteTexture() {
/* 604 */     if (this.glSpriteTextureId >= 0) {
/* 605 */       TextureUtil.deleteTexture(this.glSpriteTextureId);
/* 606 */       this.glSpriteTextureId = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float toSingleU(float p_toSingleU_1_) {
/* 611 */     p_toSingleU_1_ -= this.baseU;
/* 612 */     float f = this.sheetWidth / this.width;
/* 613 */     p_toSingleU_1_ *= f;
/* 614 */     return p_toSingleU_1_;
/*     */   }
/*     */   
/*     */   public float toSingleV(float p_toSingleV_1_) {
/* 618 */     p_toSingleV_1_ -= this.baseV;
/* 619 */     float f = this.sheetHeight / this.height;
/* 620 */     p_toSingleV_1_ *= f;
/* 621 */     return p_toSingleV_1_;
/*     */   }
/*     */   
/*     */   public List<int[][]> getFramesTextureData() {
/* 625 */     List<int[][]> list = (List)new ArrayList<>();
/* 626 */     list.addAll(this.framesTextureData);
/* 627 */     return list;
/*     */   }
/*     */   
/*     */   public AnimationMetadataSection getAnimationMetadata() {
/* 631 */     return this.animationMetadata;
/*     */   }
/*     */   
/*     */   public void setAnimationMetadata(AnimationMetadataSection p_setAnimationMetadata_1_) {
/* 635 */     this.animationMetadata = p_setAnimationMetadata_1_;
/*     */   }
/*     */   
/*     */   private void loadShadersSprites() {
/* 639 */     if (Shaders.configNormalMap) {
/* 640 */       String s = String.valueOf(this.iconName) + "_n";
/* 641 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 642 */       resourcelocation = Config.getTextureMap().completeResourceLocation(resourcelocation);
/*     */       
/* 644 */       if (Config.hasResource(resourcelocation)) {
/* 645 */         this.spriteNormal = new TextureAtlasSprite(s);
/* 646 */         this.spriteNormal.isShadersSprite = true;
/* 647 */         this.spriteNormal.copyFrom(this);
/* 648 */         this.spriteNormal.generateMipmaps(this.mipmapLevels);
/*     */       } 
/*     */     } 
/*     */     
/* 652 */     if (Shaders.configSpecularMap) {
/* 653 */       String s1 = String.valueOf(this.iconName) + "_s";
/* 654 */       ResourceLocation resourcelocation1 = new ResourceLocation(s1);
/* 655 */       resourcelocation1 = Config.getTextureMap().completeResourceLocation(resourcelocation1);
/*     */       
/* 657 */       if (Config.hasResource(resourcelocation1)) {
/* 658 */         this.spriteSpecular = new TextureAtlasSprite(s1);
/* 659 */         this.spriteSpecular.isShadersSprite = true;
/* 660 */         this.spriteSpecular.copyFrom(this);
/* 661 */         this.spriteSpecular.generateMipmaps(this.mipmapLevels);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\TextureAtlasSprite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */