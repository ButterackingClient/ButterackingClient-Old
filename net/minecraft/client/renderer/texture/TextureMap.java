/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.StitcherException;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*     */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.BetterGrass;
/*     */ import net.optifine.ConnectedTextures;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import net.optifine.util.CounterInt;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class TextureMap
/*     */   extends AbstractTexture
/*     */   implements ITickableTextureObject
/*     */ {
/*  45 */   private static final boolean ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
/*  46 */   private static final Logger logger = LogManager.getLogger();
/*  47 */   public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
/*  48 */   public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
/*     */   private final List<TextureAtlasSprite> listAnimatedSprites;
/*     */   private final Map<String, TextureAtlasSprite> mapRegisteredSprites;
/*     */   private final Map<String, TextureAtlasSprite> mapUploadedSprites;
/*     */   private final String basePath;
/*     */   private final IIconCreator iconCreator;
/*     */   private int mipmapLevels;
/*     */   private final TextureAtlasSprite missingImage;
/*     */   private boolean skipFirst;
/*     */   private TextureAtlasSprite[] iconGrid;
/*     */   private int iconGridSize;
/*     */   private int iconGridCountX;
/*     */   private int iconGridCountY;
/*     */   private double iconGridSizeU;
/*     */   private double iconGridSizeV;
/*     */   private CounterInt counterIndexInMap;
/*     */   public int atlasWidth;
/*     */   public int atlasHeight;
/*     */   private int countAnimationsActive;
/*     */   private int frameCountAnimations;
/*     */   
/*     */   public TextureMap(String p_i46099_1_) {
/*  70 */     this(p_i46099_1_, (IIconCreator)null);
/*     */   }
/*     */   
/*     */   public TextureMap(String p_i5_1_, boolean p_i5_2_) {
/*  74 */     this(p_i5_1_, (IIconCreator)null, p_i5_2_);
/*     */   }
/*     */   
/*     */   public TextureMap(String p_i46100_1_, IIconCreator iconCreatorIn) {
/*  78 */     this(p_i46100_1_, iconCreatorIn, false);
/*     */   }
/*     */   
/*     */   public TextureMap(String p_i6_1_, IIconCreator p_i6_2_, boolean p_i6_3_) {
/*  82 */     this.skipFirst = false;
/*  83 */     this.iconGrid = null;
/*  84 */     this.iconGridSize = -1;
/*  85 */     this.iconGridCountX = -1;
/*  86 */     this.iconGridCountY = -1;
/*  87 */     this.iconGridSizeU = -1.0D;
/*  88 */     this.iconGridSizeV = -1.0D;
/*  89 */     this.counterIndexInMap = new CounterInt(0);
/*  90 */     this.atlasWidth = 0;
/*  91 */     this.atlasHeight = 0;
/*  92 */     this.listAnimatedSprites = Lists.newArrayList();
/*  93 */     this.mapRegisteredSprites = Maps.newHashMap();
/*  94 */     this.mapUploadedSprites = Maps.newHashMap();
/*  95 */     this.missingImage = new TextureAtlasSprite("missingno");
/*  96 */     this.basePath = p_i6_1_;
/*  97 */     this.iconCreator = p_i6_2_;
/*  98 */     this.skipFirst = (p_i6_3_ && ENABLE_SKIP);
/*     */   }
/*     */   
/*     */   private void initMissingImage() {
/* 102 */     int i = getMinSpriteSize();
/* 103 */     int[] aint = getMissingImageData(i);
/* 104 */     this.missingImage.setIconWidth(i);
/* 105 */     this.missingImage.setIconHeight(i);
/* 106 */     int[][] aint1 = new int[this.mipmapLevels + 1][];
/* 107 */     aint1[0] = aint;
/* 108 */     this.missingImage.setFramesTextureData(Lists.newArrayList((Object[])new int[][][] { aint1 }));
/* 109 */     this.missingImage.setIndexInMap(this.counterIndexInMap.nextValue());
/*     */   }
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 113 */     if (this.iconCreator != null) {
/* 114 */       loadSprites(resourceManager, this.iconCreator);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadSprites(IResourceManager resourceManager, IIconCreator p_174943_2_) {
/* 119 */     this.mapRegisteredSprites.clear();
/* 120 */     this.counterIndexInMap.reset();
/* 121 */     p_174943_2_.registerSprites(this);
/*     */     
/* 123 */     if (this.mipmapLevels >= 4) {
/* 124 */       this.mipmapLevels = detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
/* 125 */       Config.log("Mipmap levels: " + this.mipmapLevels);
/*     */     } 
/*     */     
/* 128 */     initMissingImage();
/* 129 */     deleteGlTexture();
/* 130 */     loadTextureAtlas(resourceManager);
/*     */   }
/*     */   
/*     */   public void loadTextureAtlas(IResourceManager resourceManager) {
/* 134 */     Config.dbg("Multitexture: " + Config.isMultiTexture());
/*     */     
/* 136 */     if (Config.isMultiTexture()) {
/* 137 */       for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values()) {
/* 138 */         textureatlassprite.deleteSpriteTexture();
/*     */       }
/*     */     }
/*     */     
/* 142 */     ConnectedTextures.updateIcons(this);
/* 143 */     CustomItems.updateIcons(this);
/* 144 */     BetterGrass.updateIcons(this);
/* 145 */     int i2 = TextureUtils.getGLMaximumTextureSize();
/* 146 */     Stitcher stitcher = new Stitcher(i2, i2, true, 0, this.mipmapLevels);
/* 147 */     this.mapUploadedSprites.clear();
/* 148 */     this.listAnimatedSprites.clear();
/* 149 */     int i = Integer.MAX_VALUE;
/* 150 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] { this });
/* 151 */     int j = getMinSpriteSize();
/* 152 */     this.iconGridSize = j;
/* 153 */     int k = 1 << this.mipmapLevels;
/* 154 */     int l = 0;
/* 155 */     int i1 = 0;
/* 156 */     Iterator<Map.Entry<String, TextureAtlasSprite>> iterator = this.mapRegisteredSprites.entrySet().iterator();
/*     */ 
/*     */     
/* 159 */     while (iterator.hasNext()) {
/* 160 */       Map.Entry<String, TextureAtlasSprite> entry = iterator.next();
/*     */       
/* 162 */       if (!this.skipFirst) {
/* 163 */         TextureAtlasSprite textureatlassprite3 = entry.getValue();
/* 164 */         ResourceLocation resourcelocation1 = new ResourceLocation(textureatlassprite3.getIconName());
/* 165 */         ResourceLocation resourcelocation2 = completeResourceLocation(resourcelocation1, 0);
/* 166 */         textureatlassprite3.updateIndexInMap(this.counterIndexInMap);
/*     */         
/* 168 */         if (textureatlassprite3.hasCustomLoader(resourceManager, resourcelocation1)) {
/* 169 */           if (!textureatlassprite3.load(resourceManager, resourcelocation1)) {
/* 170 */             i = Math.min(i, Math.min(textureatlassprite3.getIconWidth(), textureatlassprite3.getIconHeight()));
/* 171 */             stitcher.addSprite(textureatlassprite3);
/* 172 */             Config.detail("Custom loader (skipped): " + textureatlassprite3);
/* 173 */             i1++;
/*     */           } 
/*     */           
/* 176 */           Config.detail("Custom loader: " + textureatlassprite3);
/* 177 */           l++;
/*     */           
/*     */           continue;
/*     */         } 
/*     */         try {
/* 182 */           IResource iresource = resourceManager.getResource(resourcelocation2);
/* 183 */           BufferedImage[] abufferedimage = new BufferedImage[1 + this.mipmapLevels];
/* 184 */           abufferedimage[0] = TextureUtil.readBufferedImage(iresource.getInputStream());
/* 185 */           int k3 = abufferedimage[0].getWidth();
/* 186 */           int l3 = abufferedimage[0].getHeight();
/*     */           
/* 188 */           if (k3 < 1 || l3 < 1) {
/* 189 */             Config.warn("Invalid sprite size: " + textureatlassprite3);
/*     */             
/*     */             continue;
/*     */           } 
/* 193 */           if (k3 < j || this.mipmapLevels > 0) {
/* 194 */             int i4 = (this.mipmapLevels > 0) ? TextureUtils.scaleToGrid(k3, j) : TextureUtils.scaleToMin(k3, j);
/*     */             
/* 196 */             if (i4 != k3) {
/* 197 */               if (!TextureUtils.isPowerOfTwo(k3)) {
/* 198 */                 Config.log("Scaled non power of 2: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
/*     */               } else {
/* 200 */                 Config.log("Scaled too small texture: " + textureatlassprite3.getIconName() + ", " + k3 + " -> " + i4);
/*     */               } 
/*     */               
/* 203 */               int j1 = l3 * i4 / k3;
/* 204 */               abufferedimage[0] = TextureUtils.scaleImage(abufferedimage[0], i4);
/*     */             } 
/*     */           } 
/*     */           
/* 208 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*     */           
/* 210 */           if (texturemetadatasection != null) {
/* 211 */             List<Integer> list1 = texturemetadatasection.getListMipmaps();
/*     */             
/* 213 */             if (!list1.isEmpty()) {
/* 214 */               int k1 = abufferedimage[0].getWidth();
/* 215 */               int l1 = abufferedimage[0].getHeight();
/*     */               
/* 217 */               if (MathHelper.roundUpToPowerOfTwo(k1) != k1 || MathHelper.roundUpToPowerOfTwo(l1) != l1) {
/* 218 */                 throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
/*     */               }
/*     */             } 
/*     */             
/* 222 */             Iterator<Integer> iterator1 = list1.iterator();
/*     */             
/* 224 */             while (iterator1.hasNext()) {
/* 225 */               int j4 = ((Integer)iterator1.next()).intValue();
/*     */               
/* 227 */               if (j4 > 0 && j4 < abufferedimage.length - 1 && abufferedimage[j4] == null) {
/* 228 */                 ResourceLocation resourcelocation = completeResourceLocation(resourcelocation1, j4);
/*     */                 
/*     */                 try {
/* 231 */                   abufferedimage[j4] = TextureUtil.readBufferedImage(resourceManager.getResource(resourcelocation).getInputStream());
/* 232 */                 } catch (IOException ioexception) {
/* 233 */                   logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(j4), resourcelocation, ioexception });
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 239 */           AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)iresource.getMetadata("animation");
/* 240 */           textureatlassprite3.loadSprite(abufferedimage, animationmetadatasection);
/* 241 */         } catch (RuntimeException runtimeexception) {
/* 242 */           logger.error("Unable to parse metadata from " + resourcelocation2, runtimeexception);
/* 243 */           ReflectorForge.FMLClientHandler_trackBrokenTexture(resourcelocation2, runtimeexception.getMessage());
/*     */           continue;
/* 245 */         } catch (IOException ioexception1) {
/* 246 */           logger.error("Using missing texture, unable to load " + resourcelocation2 + ", " + ioexception1.getClass().getName());
/* 247 */           ReflectorForge.FMLClientHandler_trackMissingTexture(resourcelocation2);
/*     */           
/*     */           continue;
/*     */         } 
/* 251 */         i = Math.min(i, Math.min(textureatlassprite3.getIconWidth(), textureatlassprite3.getIconHeight()));
/* 252 */         int j3 = Math.min(Integer.lowestOneBit(textureatlassprite3.getIconWidth()), Integer.lowestOneBit(textureatlassprite3.getIconHeight()));
/*     */         
/* 254 */         if (j3 < k) {
/* 255 */           logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { resourcelocation2, Integer.valueOf(textureatlassprite3.getIconWidth()), Integer.valueOf(textureatlassprite3.getIconHeight()), Integer.valueOf(MathHelper.calculateLogBaseTwo(k)), Integer.valueOf(MathHelper.calculateLogBaseTwo(j3)) });
/* 256 */           k = j3;
/*     */         } 
/*     */         
/* 259 */         stitcher.addSprite(textureatlassprite3);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 264 */     if (l > 0) {
/* 265 */       Config.dbg("Custom loader sprites: " + l);
/*     */     }
/*     */     
/* 268 */     if (i1 > 0) {
/* 269 */       Config.dbg("Custom loader sprites (skipped): " + i1);
/*     */     }
/*     */     
/* 272 */     int j2 = Math.min(i, k);
/* 273 */     int k2 = MathHelper.calculateLogBaseTwo(j2);
/*     */     
/* 275 */     if (k2 < 0) {
/* 276 */       k2 = 0;
/*     */     }
/*     */     
/* 279 */     if (k2 < this.mipmapLevels) {
/* 280 */       logger.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { this.basePath, Integer.valueOf(this.mipmapLevels), Integer.valueOf(k2), Integer.valueOf(j2) });
/* 281 */       this.mipmapLevels = k2;
/*     */     } 
/*     */     
/* 284 */     for (TextureAtlasSprite textureatlassprite1 : this.mapRegisteredSprites.values()) {
/* 285 */       if (this.skipFirst) {
/*     */         break;
/*     */       }
/*     */       
/*     */       try {
/* 290 */         textureatlassprite1.generateMipmaps(this.mipmapLevels);
/* 291 */       } catch (Throwable throwable1) {
/* 292 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Applying mipmap");
/* 293 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Sprite being mipmapped");
/* 294 */         crashreportcategory.addCrashSectionCallable("Sprite name", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 296 */                 return textureatlassprite1.getIconName();
/*     */               }
/*     */             });
/* 299 */         crashreportcategory.addCrashSectionCallable("Sprite size", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 301 */                 return String.valueOf(textureatlassprite1.getIconWidth()) + " x " + textureatlassprite1.getIconHeight();
/*     */               }
/*     */             });
/* 304 */         crashreportcategory.addCrashSectionCallable("Sprite frames", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 306 */                 return String.valueOf(textureatlassprite1.getFrameCount()) + " frames";
/*     */               }
/*     */             });
/* 309 */         crashreportcategory.addCrashSection("Mipmap levels", Integer.valueOf(this.mipmapLevels));
/* 310 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     this.missingImage.generateMipmaps(this.mipmapLevels);
/* 315 */     stitcher.addSprite(this.missingImage);
/* 316 */     this.skipFirst = false;
/*     */     
/*     */     try {
/* 319 */       stitcher.doStitch();
/* 320 */     } catch (StitcherException stitcherexception) {
/* 321 */       throw stitcherexception;
/*     */     } 
/*     */     
/* 324 */     logger.info("Created: {}x{} {}-atlas", new Object[] { Integer.valueOf(stitcher.getCurrentWidth()), Integer.valueOf(stitcher.getCurrentHeight()), this.basePath });
/*     */     
/* 326 */     if (Config.isShaders()) {
/* 327 */       ShadersTex.allocateTextureMap(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), stitcher, this);
/*     */     } else {
/* 329 */       TextureUtil.allocateTextureImpl(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     } 
/*     */     
/* 332 */     Map<String, TextureAtlasSprite> map = Maps.newHashMap(this.mapRegisteredSprites);
/*     */     
/* 334 */     for (TextureAtlasSprite textureatlassprite2 : stitcher.getStichSlots()) {
/* 335 */       String s = textureatlassprite2.getIconName();
/* 336 */       map.remove(s);
/* 337 */       this.mapUploadedSprites.put(s, textureatlassprite2);
/*     */       
/*     */       try {
/* 340 */         if (Config.isShaders()) {
/* 341 */           ShadersTex.uploadTexSubForLoadAtlas(this, textureatlassprite2.getIconName(), textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*     */         } else {
/* 343 */           TextureUtil.uploadTextureMipmap(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*     */         } 
/* 345 */       } catch (Throwable throwable) {
/* 346 */         CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
/* 347 */         CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Texture being stitched together");
/* 348 */         crashreportcategory1.addCrashSection("Atlas path", this.basePath);
/* 349 */         crashreportcategory1.addCrashSection("Sprite", textureatlassprite2);
/* 350 */         throw new ReportedException(crashreport1);
/*     */       } 
/*     */       
/* 353 */       if (textureatlassprite2.hasAnimationMetadata()) {
/* 354 */         textureatlassprite2.setAnimationIndex(this.listAnimatedSprites.size());
/* 355 */         this.listAnimatedSprites.add(textureatlassprite2);
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     for (TextureAtlasSprite textureatlassprite4 : map.values()) {
/* 360 */       textureatlassprite4.copyFrom(this.missingImage);
/*     */     }
/*     */     
/* 363 */     Config.log("Animated sprites: " + this.listAnimatedSprites.size());
/*     */     
/* 365 */     if (Config.isMultiTexture()) {
/* 366 */       int l2 = stitcher.getCurrentWidth();
/* 367 */       int i3 = stitcher.getCurrentHeight();
/*     */       
/* 369 */       for (TextureAtlasSprite textureatlassprite5 : stitcher.getStichSlots()) {
/* 370 */         textureatlassprite5.sheetWidth = l2;
/* 371 */         textureatlassprite5.sheetHeight = i3;
/* 372 */         textureatlassprite5.mipmapLevels = this.mipmapLevels;
/* 373 */         TextureAtlasSprite textureatlassprite6 = textureatlassprite5.spriteSingle;
/*     */         
/* 375 */         if (textureatlassprite6 != null) {
/* 376 */           if (textureatlassprite6.getIconWidth() <= 0) {
/* 377 */             textureatlassprite6.setIconWidth(textureatlassprite5.getIconWidth());
/* 378 */             textureatlassprite6.setIconHeight(textureatlassprite5.getIconHeight());
/* 379 */             textureatlassprite6.initSprite(textureatlassprite5.getIconWidth(), textureatlassprite5.getIconHeight(), 0, 0, false);
/* 380 */             textureatlassprite6.clearFramesTextureData();
/* 381 */             List<int[][]> list = textureatlassprite5.getFramesTextureData();
/* 382 */             textureatlassprite6.setFramesTextureData(list);
/* 383 */             textureatlassprite6.setAnimationMetadata(textureatlassprite5.getAnimationMetadata());
/*     */           } 
/*     */           
/* 386 */           textureatlassprite6.sheetWidth = l2;
/* 387 */           textureatlassprite6.sheetHeight = i3;
/* 388 */           textureatlassprite6.mipmapLevels = this.mipmapLevels;
/* 389 */           textureatlassprite6.setAnimationIndex(textureatlassprite5.getAnimationIndex());
/* 390 */           textureatlassprite5.bindSpriteTexture();
/* 391 */           boolean flag1 = false;
/* 392 */           boolean flag = true;
/*     */           
/*     */           try {
/* 395 */             TextureUtil.uploadTextureMipmap(textureatlassprite6.getFrameTextureData(0), textureatlassprite6.getIconWidth(), textureatlassprite6.getIconHeight(), textureatlassprite6.getOriginX(), textureatlassprite6.getOriginY(), flag1, flag);
/* 396 */           } catch (Exception exception) {
/* 397 */             Config.dbg("Error uploading sprite single: " + textureatlassprite6 + ", parent: " + textureatlassprite5);
/* 398 */             exception.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 403 */       Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
/*     */     } 
/*     */     
/* 406 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] { this });
/* 407 */     updateIconGrid(stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     
/* 409 */     if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
/* 410 */       Config.dbg("Exporting texture map: " + this.basePath);
/* 411 */       TextureUtils.saveGlTexture("debug/" + this.basePath.replaceAll("/", "_"), getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation completeResourceLocation(ResourceLocation p_completeResourceLocation_1_) {
/* 419 */     return completeResourceLocation(p_completeResourceLocation_1_, 0);
/*     */   }
/*     */   
/*     */   public ResourceLocation completeResourceLocation(ResourceLocation location, int p_147634_2_) {
/* 423 */     return isAbsoluteLocation(location) ? new ResourceLocation(location.getResourceDomain(), String.valueOf(location.getResourcePath()) + ".png") : ((p_147634_2_ == 0) ? new ResourceLocation(location.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, location.getResourcePath(), ".png" })) : new ResourceLocation(location.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", new Object[] { this.basePath, location.getResourcePath(), Integer.valueOf(p_147634_2_), ".png" })));
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getAtlasSprite(String iconName) {
/* 427 */     TextureAtlasSprite textureatlassprite = this.mapUploadedSprites.get(iconName);
/*     */     
/* 429 */     if (textureatlassprite == null) {
/* 430 */       textureatlassprite = this.missingImage;
/*     */     }
/*     */     
/* 433 */     return textureatlassprite;
/*     */   }
/*     */   
/*     */   public void updateAnimations() {
/* 437 */     boolean flag = false;
/* 438 */     boolean flag1 = false;
/* 439 */     TextureUtil.bindTexture(getGlTextureId());
/* 440 */     int i = 0;
/*     */     
/* 442 */     for (TextureAtlasSprite textureatlassprite : this.listAnimatedSprites) {
/* 443 */       if (isTerrainAnimationActive(textureatlassprite)) {
/* 444 */         textureatlassprite.updateAnimation();
/*     */         
/* 446 */         if (textureatlassprite.isAnimationActive()) {
/* 447 */           i++;
/*     */         }
/*     */         
/* 450 */         if (textureatlassprite.spriteNormal != null) {
/* 451 */           flag = true;
/*     */         }
/*     */         
/* 454 */         if (textureatlassprite.spriteSpecular != null) {
/* 455 */           flag1 = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 460 */     if (Config.isMultiTexture()) {
/* 461 */       for (TextureAtlasSprite textureatlassprite2 : this.listAnimatedSprites) {
/* 462 */         if (isTerrainAnimationActive(textureatlassprite2)) {
/* 463 */           TextureAtlasSprite textureatlassprite1 = textureatlassprite2.spriteSingle;
/*     */           
/* 465 */           if (textureatlassprite1 != null) {
/* 466 */             if (textureatlassprite2 == TextureUtils.iconClock || textureatlassprite2 == TextureUtils.iconCompass) {
/* 467 */               textureatlassprite1.frameCounter = textureatlassprite2.frameCounter;
/*     */             }
/*     */             
/* 470 */             textureatlassprite2.bindSpriteTexture();
/* 471 */             textureatlassprite1.updateAnimation();
/*     */             
/* 473 */             if (textureatlassprite1.isAnimationActive()) {
/* 474 */               i++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 480 */       TextureUtil.bindTexture(getGlTextureId());
/*     */     } 
/*     */     
/* 483 */     if (Config.isShaders()) {
/* 484 */       if (flag) {
/* 485 */         TextureUtil.bindTexture((getMultiTexID()).norm);
/*     */         
/* 487 */         for (TextureAtlasSprite textureatlassprite3 : this.listAnimatedSprites) {
/* 488 */           if (textureatlassprite3.spriteNormal != null && isTerrainAnimationActive(textureatlassprite3)) {
/* 489 */             if (textureatlassprite3 == TextureUtils.iconClock || textureatlassprite3 == TextureUtils.iconCompass) {
/* 490 */               textureatlassprite3.spriteNormal.frameCounter = textureatlassprite3.frameCounter;
/*     */             }
/*     */             
/* 493 */             textureatlassprite3.spriteNormal.updateAnimation();
/*     */             
/* 495 */             if (textureatlassprite3.spriteNormal.isAnimationActive()) {
/* 496 */               i++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 502 */       if (flag1) {
/* 503 */         TextureUtil.bindTexture((getMultiTexID()).spec);
/*     */         
/* 505 */         for (TextureAtlasSprite textureatlassprite4 : this.listAnimatedSprites) {
/* 506 */           if (textureatlassprite4.spriteSpecular != null && isTerrainAnimationActive(textureatlassprite4)) {
/* 507 */             if (textureatlassprite4 == TextureUtils.iconClock || textureatlassprite4 == TextureUtils.iconCompass) {
/* 508 */               textureatlassprite4.spriteNormal.frameCounter = textureatlassprite4.frameCounter;
/*     */             }
/*     */             
/* 511 */             textureatlassprite4.spriteSpecular.updateAnimation();
/*     */             
/* 513 */             if (textureatlassprite4.spriteSpecular.isAnimationActive()) {
/* 514 */               i++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 520 */       if (flag || flag1) {
/* 521 */         TextureUtil.bindTexture(getGlTextureId());
/*     */       }
/*     */     } 
/*     */     
/* 525 */     int j = (Config.getMinecraft()).entityRenderer.frameCount;
/*     */     
/* 527 */     if (j != this.frameCountAnimations) {
/* 528 */       this.countAnimationsActive = i;
/* 529 */       this.frameCountAnimations = j;
/*     */     } 
/*     */     
/* 532 */     if (SmartAnimations.isActive()) {
/* 533 */       SmartAnimations.resetSpritesRendered();
/*     */     }
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite registerSprite(ResourceLocation location) {
/* 538 */     if (location == null) {
/* 539 */       throw new IllegalArgumentException("Location cannot be null!");
/*     */     }
/* 541 */     TextureAtlasSprite textureatlassprite = this.mapRegisteredSprites.get(location.toString());
/*     */     
/* 543 */     if (textureatlassprite == null) {
/* 544 */       textureatlassprite = TextureAtlasSprite.makeAtlasSprite(location);
/* 545 */       this.mapRegisteredSprites.put(location.toString(), textureatlassprite);
/* 546 */       textureatlassprite.updateIndexInMap(this.counterIndexInMap);
/*     */       
/* 548 */       if (Config.isEmissiveTextures()) {
/* 549 */         checkEmissive(location, textureatlassprite);
/*     */       }
/*     */     } 
/*     */     
/* 553 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 558 */     updateAnimations();
/*     */   }
/*     */   
/*     */   public void setMipmapLevels(int mipmapLevelsIn) {
/* 562 */     this.mipmapLevels = mipmapLevelsIn;
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getMissingSprite() {
/* 566 */     return this.missingImage;
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getTextureExtry(String p_getTextureExtry_1_) {
/* 570 */     return this.mapRegisteredSprites.get(p_getTextureExtry_1_);
/*     */   }
/*     */   
/*     */   public boolean setTextureEntry(String p_setTextureEntry_1_, TextureAtlasSprite p_setTextureEntry_2_) {
/* 574 */     if (!this.mapRegisteredSprites.containsKey(p_setTextureEntry_1_)) {
/* 575 */       this.mapRegisteredSprites.put(p_setTextureEntry_1_, p_setTextureEntry_2_);
/* 576 */       p_setTextureEntry_2_.updateIndexInMap(this.counterIndexInMap);
/* 577 */       return true;
/*     */     } 
/* 579 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTextureEntry(TextureAtlasSprite p_setTextureEntry_1_) {
/* 584 */     return setTextureEntry(p_setTextureEntry_1_.getIconName(), p_setTextureEntry_1_);
/*     */   }
/*     */   
/*     */   public String getBasePath() {
/* 588 */     return this.basePath;
/*     */   }
/*     */   
/*     */   public int getMipmapLevels() {
/* 592 */     return this.mipmapLevels;
/*     */   }
/*     */   
/*     */   private boolean isAbsoluteLocation(ResourceLocation p_isAbsoluteLocation_1_) {
/* 596 */     String s = p_isAbsoluteLocation_1_.getResourcePath();
/* 597 */     return isAbsoluteLocationPath(s);
/*     */   }
/*     */   
/*     */   private boolean isAbsoluteLocationPath(String p_isAbsoluteLocationPath_1_) {
/* 601 */     String s = p_isAbsoluteLocationPath_1_.toLowerCase();
/* 602 */     return !(!s.startsWith("mcpatcher/") && !s.startsWith("optifine/"));
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getSpriteSafe(String p_getSpriteSafe_1_) {
/* 606 */     ResourceLocation resourcelocation = new ResourceLocation(p_getSpriteSafe_1_);
/* 607 */     return this.mapRegisteredSprites.get(resourcelocation.toString());
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getRegisteredSprite(ResourceLocation p_getRegisteredSprite_1_) {
/* 611 */     return this.mapRegisteredSprites.get(p_getRegisteredSprite_1_.toString());
/*     */   }
/*     */   
/*     */   private boolean isTerrainAnimationActive(TextureAtlasSprite p_isTerrainAnimationActive_1_) {
/* 615 */     return (p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterFlow) ? ((p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaFlow) ? ((p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer0 && p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer1) ? ((p_isTerrainAnimationActive_1_ == TextureUtils.iconPortal) ? Config.isAnimatedPortal() : ((p_isTerrainAnimationActive_1_ != TextureUtils.iconClock && p_isTerrainAnimationActive_1_ != TextureUtils.iconCompass) ? Config.isAnimatedTerrain() : true)) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
/*     */   }
/*     */   
/*     */   public int getCountRegisteredSprites() {
/* 619 */     return this.counterIndexInMap.getValue();
/*     */   }
/*     */   
/*     */   private int detectMaxMipmapLevel(Map p_detectMaxMipmapLevel_1_, IResourceManager p_detectMaxMipmapLevel_2_) {
/* 623 */     int i = detectMinimumSpriteSize(p_detectMaxMipmapLevel_1_, p_detectMaxMipmapLevel_2_, 20);
/*     */     
/* 625 */     if (i < 16) {
/* 626 */       i = 16;
/*     */     }
/*     */     
/* 629 */     i = MathHelper.roundUpToPowerOfTwo(i);
/*     */     
/* 631 */     if (i > 16) {
/* 632 */       Config.log("Sprite size: " + i);
/*     */     }
/*     */     
/* 635 */     int j = MathHelper.calculateLogBaseTwo(i);
/*     */     
/* 637 */     if (j < 4) {
/* 638 */       j = 4;
/*     */     }
/*     */     
/* 641 */     return j;
/*     */   }
/*     */   
/*     */   private int detectMinimumSpriteSize(Map p_detectMinimumSpriteSize_1_, IResourceManager p_detectMinimumSpriteSize_2_, int p_detectMinimumSpriteSize_3_) {
/* 645 */     Map<Object, Object> map = new HashMap<>();
/*     */     
/* 647 */     for (Object entry0 : p_detectMinimumSpriteSize_1_.entrySet()) {
/* 648 */       Map.Entry entry = (Map.Entry)entry0;
/* 649 */       TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)entry.getValue();
/* 650 */       ResourceLocation resourcelocation = new ResourceLocation(textureatlassprite.getIconName());
/* 651 */       ResourceLocation resourcelocation1 = completeResourceLocation(resourcelocation);
/*     */       
/* 653 */       if (!textureatlassprite.hasCustomLoader(p_detectMinimumSpriteSize_2_, resourcelocation)) {
/*     */         try {
/* 655 */           IResource iresource = p_detectMinimumSpriteSize_2_.getResource(resourcelocation1);
/*     */           
/* 657 */           if (iresource != null) {
/* 658 */             InputStream inputstream = iresource.getInputStream();
/*     */             
/* 660 */             if (inputstream != null) {
/* 661 */               Dimension dimension = TextureUtils.getImageSize(inputstream, "png");
/* 662 */               inputstream.close();
/*     */               
/* 664 */               if (dimension != null) {
/* 665 */                 int i = dimension.width;
/* 666 */                 int j = MathHelper.roundUpToPowerOfTwo(i);
/*     */                 
/* 668 */                 if (!map.containsKey(Integer.valueOf(j))) {
/* 669 */                   map.put(Integer.valueOf(j), Integer.valueOf(1)); continue;
/*     */                 } 
/* 671 */                 int k = ((Integer)map.get(Integer.valueOf(j))).intValue();
/* 672 */                 map.put(Integer.valueOf(j), Integer.valueOf(k + 1));
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/* 677 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 683 */     int l = 0;
/* 684 */     Set<?> set = map.keySet();
/* 685 */     Set set1 = new TreeSet(set);
/*     */ 
/*     */     
/* 688 */     for (Iterator<Integer> iterator = set1.iterator(); iterator.hasNext(); l += i) {
/* 689 */       int j1 = ((Integer)iterator.next()).intValue();
/* 690 */       int i = ((Integer)map.get(Integer.valueOf(j1))).intValue();
/*     */     } 
/*     */     
/* 693 */     int i1 = 16;
/* 694 */     int k1 = 0;
/* 695 */     int l1 = l * p_detectMinimumSpriteSize_3_ / 100;
/* 696 */     Iterator<Integer> iterator1 = set1.iterator();
/*     */     
/* 698 */     while (iterator1.hasNext()) {
/* 699 */       int i2 = ((Integer)iterator1.next()).intValue();
/* 700 */       int j2 = ((Integer)map.get(Integer.valueOf(i2))).intValue();
/* 701 */       k1 += j2;
/*     */       
/* 703 */       if (i2 > i1) {
/* 704 */         i1 = i2;
/*     */       }
/*     */       
/* 707 */       if (k1 > l1) {
/* 708 */         return i1;
/*     */       }
/*     */     } 
/*     */     
/* 712 */     return i1;
/*     */   }
/*     */   
/*     */   private int getMinSpriteSize() {
/* 716 */     int i = 1 << this.mipmapLevels;
/*     */     
/* 718 */     if (i < 8) {
/* 719 */       i = 8;
/*     */     }
/*     */     
/* 722 */     return i;
/*     */   }
/*     */   
/*     */   private int[] getMissingImageData(int p_getMissingImageData_1_) {
/* 726 */     BufferedImage bufferedimage = new BufferedImage(16, 16, 2);
/* 727 */     bufferedimage.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
/* 728 */     BufferedImage bufferedimage1 = TextureUtils.scaleImage(bufferedimage, p_getMissingImageData_1_);
/* 729 */     int[] aint = new int[p_getMissingImageData_1_ * p_getMissingImageData_1_];
/* 730 */     bufferedimage1.getRGB(0, 0, p_getMissingImageData_1_, p_getMissingImageData_1_, aint, 0, p_getMissingImageData_1_);
/* 731 */     return aint;
/*     */   }
/*     */   
/*     */   public boolean isTextureBound() {
/* 735 */     int i = GlStateManager.getBoundTexture();
/* 736 */     int j = getGlTextureId();
/* 737 */     return (i == j);
/*     */   }
/*     */   
/*     */   private void updateIconGrid(int p_updateIconGrid_1_, int p_updateIconGrid_2_) {
/* 741 */     this.iconGridCountX = -1;
/* 742 */     this.iconGridCountY = -1;
/* 743 */     this.iconGrid = null;
/*     */     
/* 745 */     if (this.iconGridSize > 0) {
/* 746 */       this.iconGridCountX = p_updateIconGrid_1_ / this.iconGridSize;
/* 747 */       this.iconGridCountY = p_updateIconGrid_2_ / this.iconGridSize;
/* 748 */       this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
/* 749 */       this.iconGridSizeU = 1.0D / this.iconGridCountX;
/* 750 */       this.iconGridSizeV = 1.0D / this.iconGridCountY;
/*     */       
/* 752 */       for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values()) {
/* 753 */         double d0 = 0.5D / p_updateIconGrid_1_;
/* 754 */         double d1 = 0.5D / p_updateIconGrid_2_;
/* 755 */         double d2 = Math.min(textureatlassprite.getMinU(), textureatlassprite.getMaxU()) + d0;
/* 756 */         double d3 = Math.min(textureatlassprite.getMinV(), textureatlassprite.getMaxV()) + d1;
/* 757 */         double d4 = Math.max(textureatlassprite.getMinU(), textureatlassprite.getMaxU()) - d0;
/* 758 */         double d5 = Math.max(textureatlassprite.getMinV(), textureatlassprite.getMaxV()) - d1;
/* 759 */         int i = (int)(d2 / this.iconGridSizeU);
/* 760 */         int j = (int)(d3 / this.iconGridSizeV);
/* 761 */         int k = (int)(d4 / this.iconGridSizeU);
/* 762 */         int l = (int)(d5 / this.iconGridSizeV);
/*     */         
/* 764 */         for (int i1 = i; i1 <= k; i1++) {
/* 765 */           if (i1 >= 0 && i1 < this.iconGridCountX) {
/* 766 */             for (int j1 = j; j1 <= l; j1++) {
/* 767 */               if (j1 >= 0 && j1 < this.iconGridCountX) {
/* 768 */                 int k1 = j1 * this.iconGridCountX + i1;
/* 769 */                 this.iconGrid[k1] = textureatlassprite;
/*     */               } else {
/* 771 */                 Config.warn("Invalid grid V: " + j1 + ", icon: " + textureatlassprite.getIconName());
/*     */               } 
/*     */             } 
/*     */           } else {
/* 775 */             Config.warn("Invalid grid U: " + i1 + ", icon: " + textureatlassprite.getIconName());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getIconByUV(double p_getIconByUV_1_, double p_getIconByUV_3_) {
/* 783 */     if (this.iconGrid == null) {
/* 784 */       return null;
/*     */     }
/* 786 */     int i = (int)(p_getIconByUV_1_ / this.iconGridSizeU);
/* 787 */     int j = (int)(p_getIconByUV_3_ / this.iconGridSizeV);
/* 788 */     int k = j * this.iconGridCountX + i;
/* 789 */     return (k >= 0 && k <= this.iconGrid.length) ? this.iconGrid[k] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkEmissive(ResourceLocation p_checkEmissive_1_, TextureAtlasSprite p_checkEmissive_2_) {
/* 794 */     String s = EmissiveTextures.getSuffixEmissive();
/*     */     
/* 796 */     if (s != null && 
/* 797 */       !p_checkEmissive_1_.getResourcePath().endsWith(s)) {
/* 798 */       ResourceLocation resourcelocation = new ResourceLocation(p_checkEmissive_1_.getResourceDomain(), String.valueOf(p_checkEmissive_1_.getResourcePath()) + s);
/* 799 */       ResourceLocation resourcelocation1 = completeResourceLocation(resourcelocation);
/*     */       
/* 801 */       if (Config.hasResource(resourcelocation1)) {
/* 802 */         TextureAtlasSprite textureatlassprite = registerSprite(resourcelocation);
/* 803 */         textureatlassprite.isEmissive = true;
/* 804 */         p_checkEmissive_2_.spriteEmissive = textureatlassprite;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountAnimations() {
/* 811 */     return this.listAnimatedSprites.size();
/*     */   }
/*     */   
/*     */   public int getCountAnimationsActive() {
/* 815 */     return this.countAnimationsActive;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\TextureMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */