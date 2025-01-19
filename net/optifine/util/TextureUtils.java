/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.BetterGrass;
/*     */ import net.optifine.BetterSnow;
/*     */ import net.optifine.CustomBlockLayers;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.CustomGuis;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.CustomLoadingScreens;
/*     */ import net.optifine.CustomPanorama;
/*     */ import net.optifine.CustomSky;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.NaturalTextures;
/*     */ import net.optifine.RandomEntities;
/*     */ import net.optifine.SmartLeaves;
/*     */ import net.optifine.TextureAnimations;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import net.optifine.shaders.MultiTexID;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureUtils
/*     */ {
/*     */   public static final String texGrassTop = "grass_top";
/*     */   public static final String texStone = "stone";
/*     */   public static final String texDirt = "dirt";
/*     */   public static final String texCoarseDirt = "coarse_dirt";
/*     */   public static final String texGrassSide = "grass_side";
/*     */   public static final String texStoneslabSide = "stone_slab_side";
/*     */   public static final String texStoneslabTop = "stone_slab_top";
/*     */   public static final String texBedrock = "bedrock";
/*     */   public static final String texSand = "sand";
/*     */   public static final String texGravel = "gravel";
/*     */   public static final String texLogOak = "log_oak";
/*     */   public static final String texLogBigOak = "log_big_oak";
/*     */   public static final String texLogAcacia = "log_acacia";
/*     */   public static final String texLogSpruce = "log_spruce";
/*     */   public static final String texLogBirch = "log_birch";
/*     */   public static final String texLogJungle = "log_jungle";
/*     */   public static final String texLogOakTop = "log_oak_top";
/*     */   public static final String texLogBigOakTop = "log_big_oak_top";
/*     */   public static final String texLogAcaciaTop = "log_acacia_top";
/*     */   public static final String texLogSpruceTop = "log_spruce_top";
/*     */   public static final String texLogBirchTop = "log_birch_top";
/*     */   public static final String texLogJungleTop = "log_jungle_top";
/*     */   public static final String texLeavesOak = "leaves_oak";
/*     */   public static final String texLeavesBigOak = "leaves_big_oak";
/*     */   public static final String texLeavesAcacia = "leaves_acacia";
/*     */   public static final String texLeavesBirch = "leaves_birch";
/*     */   public static final String texLeavesSpuce = "leaves_spruce";
/*     */   public static final String texLeavesJungle = "leaves_jungle";
/*     */   public static final String texGoldOre = "gold_ore";
/*     */   public static final String texIronOre = "iron_ore";
/*     */   public static final String texCoalOre = "coal_ore";
/*     */   public static final String texObsidian = "obsidian";
/*     */   public static final String texGrassSideOverlay = "grass_side_overlay";
/*     */   public static final String texSnow = "snow";
/*     */   public static final String texGrassSideSnowed = "grass_side_snowed";
/*     */   public static final String texMyceliumSide = "mycelium_side";
/*     */   public static final String texMyceliumTop = "mycelium_top";
/*     */   public static final String texDiamondOre = "diamond_ore";
/*     */   public static final String texRedstoneOre = "redstone_ore";
/*     */   public static final String texLapisOre = "lapis_ore";
/*     */   public static final String texCactusSide = "cactus_side";
/*     */   public static final String texClay = "clay";
/*     */   public static final String texFarmlandWet = "farmland_wet";
/*     */   public static final String texFarmlandDry = "farmland_dry";
/*     */   public static final String texNetherrack = "netherrack";
/*     */   public static final String texSoulSand = "soul_sand";
/*     */   public static final String texGlowstone = "glowstone";
/*     */   public static final String texLeavesSpruce = "leaves_spruce";
/*     */   public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
/*     */   public static final String texEndStone = "end_stone";
/*     */   public static final String texSandstoneTop = "sandstone_top";
/*     */   public static final String texSandstoneBottom = "sandstone_bottom";
/*     */   public static final String texRedstoneLampOff = "redstone_lamp_off";
/*     */   public static final String texRedstoneLampOn = "redstone_lamp_on";
/*     */   public static final String texWaterStill = "water_still";
/*     */   public static final String texWaterFlow = "water_flow";
/*     */   public static final String texLavaStill = "lava_still";
/*     */   public static final String texLavaFlow = "lava_flow";
/*     */   public static final String texFireLayer0 = "fire_layer_0";
/*     */   public static final String texFireLayer1 = "fire_layer_1";
/*     */   public static final String texPortal = "portal";
/*     */   public static final String texGlass = "glass";
/*     */   public static final String texGlassPaneTop = "glass_pane_top";
/*     */   public static final String texCompass = "compass";
/*     */   public static final String texClock = "clock";
/*     */   public static TextureAtlasSprite iconGrassTop;
/*     */   public static TextureAtlasSprite iconGrassSide;
/*     */   public static TextureAtlasSprite iconGrassSideOverlay;
/*     */   public static TextureAtlasSprite iconSnow;
/*     */   public static TextureAtlasSprite iconGrassSideSnowed;
/*     */   public static TextureAtlasSprite iconMyceliumSide;
/*     */   public static TextureAtlasSprite iconMyceliumTop;
/*     */   public static TextureAtlasSprite iconWaterStill;
/*     */   public static TextureAtlasSprite iconWaterFlow;
/*     */   public static TextureAtlasSprite iconLavaStill;
/*     */   public static TextureAtlasSprite iconLavaFlow;
/*     */   public static TextureAtlasSprite iconPortal;
/*     */   public static TextureAtlasSprite iconFireLayer0;
/*     */   public static TextureAtlasSprite iconFireLayer1;
/*     */   public static TextureAtlasSprite iconGlass;
/*     */   public static TextureAtlasSprite iconGlassPaneTop;
/*     */   public static TextureAtlasSprite iconCompass;
/*     */   public static TextureAtlasSprite iconClock;
/*     */   public static final String SPRITE_PREFIX_BLOCKS = "minecraft:blocks/";
/*     */   public static final String SPRITE_PREFIX_ITEMS = "minecraft:items/";
/* 142 */   private static IntBuffer staticBuffer = GLAllocation.createDirectIntBuffer(256);
/*     */   
/*     */   public static void update() {
/* 145 */     TextureMap texturemap = getTextureMapBlocks();
/*     */     
/* 147 */     if (texturemap != null) {
/* 148 */       String s = "minecraft:blocks/";
/* 149 */       iconGrassTop = texturemap.getSpriteSafe(String.valueOf(s) + "grass_top");
/* 150 */       iconGrassSide = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side");
/* 151 */       iconGrassSideOverlay = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side_overlay");
/* 152 */       iconSnow = texturemap.getSpriteSafe(String.valueOf(s) + "snow");
/* 153 */       iconGrassSideSnowed = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side_snowed");
/* 154 */       iconMyceliumSide = texturemap.getSpriteSafe(String.valueOf(s) + "mycelium_side");
/* 155 */       iconMyceliumTop = texturemap.getSpriteSafe(String.valueOf(s) + "mycelium_top");
/* 156 */       iconWaterStill = texturemap.getSpriteSafe(String.valueOf(s) + "water_still");
/* 157 */       iconWaterFlow = texturemap.getSpriteSafe(String.valueOf(s) + "water_flow");
/* 158 */       iconLavaStill = texturemap.getSpriteSafe(String.valueOf(s) + "lava_still");
/* 159 */       iconLavaFlow = texturemap.getSpriteSafe(String.valueOf(s) + "lava_flow");
/* 160 */       iconFireLayer0 = texturemap.getSpriteSafe(String.valueOf(s) + "fire_layer_0");
/* 161 */       iconFireLayer1 = texturemap.getSpriteSafe(String.valueOf(s) + "fire_layer_1");
/* 162 */       iconPortal = texturemap.getSpriteSafe(String.valueOf(s) + "portal");
/* 163 */       iconGlass = texturemap.getSpriteSafe(String.valueOf(s) + "glass");
/* 164 */       iconGlassPaneTop = texturemap.getSpriteSafe(String.valueOf(s) + "glass_pane_top");
/* 165 */       String s1 = "minecraft:items/";
/* 166 */       iconCompass = texturemap.getSpriteSafe(String.valueOf(s1) + "compass");
/* 167 */       iconClock = texturemap.getSpriteSafe(String.valueOf(s1) + "clock");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static BufferedImage fixTextureDimensions(String name, BufferedImage bi) {
/* 172 */     if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
/* 173 */       int i = bi.getWidth();
/* 174 */       int j = bi.getHeight();
/*     */       
/* 176 */       if (i == j * 2) {
/* 177 */         BufferedImage bufferedimage = new BufferedImage(i, j * 2, 2);
/* 178 */         Graphics2D graphics2d = bufferedimage.createGraphics();
/* 179 */         graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 180 */         graphics2d.drawImage(bi, 0, 0, i, j, null);
/* 181 */         return bufferedimage;
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     return bi;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int ceilPowerOfTwo(int val) {
/* 191 */     for (int i = 1; i < val; i *= 2);
/*     */ 
/*     */ 
/*     */     
/* 195 */     return i;
/*     */   }
/*     */   
/*     */   public static int getPowerOfTwo(int val) {
/* 199 */     int i = 1;
/*     */ 
/*     */     
/* 202 */     for (int j = 0; i < val; j++) {
/* 203 */       i *= 2;
/*     */     }
/*     */     
/* 206 */     return j;
/*     */   }
/*     */   
/*     */   public static int twoToPower(int power) {
/* 210 */     int i = 1;
/*     */     
/* 212 */     for (int j = 0; j < power; j++) {
/* 213 */       i *= 2;
/*     */     }
/*     */     
/* 216 */     return i;
/*     */   }
/*     */   
/*     */   public static ITextureObject getTexture(ResourceLocation loc) {
/* 220 */     ITextureObject itextureobject = Config.getTextureManager().getTexture(loc);
/*     */     
/* 222 */     if (itextureobject != null)
/* 223 */       return itextureobject; 
/* 224 */     if (!Config.hasResource(loc)) {
/* 225 */       return null;
/*     */     }
/* 227 */     SimpleTexture simpletexture = new SimpleTexture(loc);
/* 228 */     Config.getTextureManager().loadTexture(loc, (ITextureObject)simpletexture);
/* 229 */     return (ITextureObject)simpletexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded(IResourceManager rm) {
/* 234 */     if (getTextureMapBlocks() != null) {
/* 235 */       Config.dbg("*** Reloading custom textures ***");
/* 236 */       CustomSky.reset();
/* 237 */       TextureAnimations.reset();
/* 238 */       update();
/* 239 */       NaturalTextures.update();
/* 240 */       BetterGrass.update();
/* 241 */       BetterSnow.update();
/* 242 */       TextureAnimations.update();
/* 243 */       CustomColors.update();
/* 244 */       CustomSky.update();
/* 245 */       RandomEntities.update();
/* 246 */       CustomItems.updateModels();
/* 247 */       CustomEntityModels.update();
/* 248 */       Shaders.resourcesReloaded();
/* 249 */       Lang.resourcesReloaded();
/* 250 */       Config.updateTexturePackClouds();
/* 251 */       SmartLeaves.updateLeavesModels();
/* 252 */       CustomPanorama.update();
/* 253 */       CustomGuis.update();
/* 254 */       LayerMooshroomMushroom.update();
/* 255 */       CustomLoadingScreens.update();
/* 256 */       CustomBlockLayers.update();
/* 257 */       Config.getTextureManager().tick();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static TextureMap getTextureMapBlocks() {
/* 262 */     return Minecraft.getMinecraft().getTextureMapBlocks();
/*     */   }
/*     */   
/*     */   public static void registerResourceListener() {
/* 266 */     IResourceManager iresourcemanager = Config.getResourceManager();
/*     */     
/* 268 */     if (iresourcemanager instanceof IReloadableResourceManager) {
/* 269 */       IReloadableResourceManager ireloadableresourcemanager = (IReloadableResourceManager)iresourcemanager;
/* 270 */       IResourceManagerReloadListener iresourcemanagerreloadlistener = new IResourceManagerReloadListener() {
/*     */           public void onResourceManagerReload(IResourceManager var1) {
/* 272 */             TextureUtils.resourcesReloaded(var1);
/*     */           }
/*     */         };
/* 275 */       ireloadableresourcemanager.registerReloadListener(iresourcemanagerreloadlistener);
/*     */     } 
/*     */     
/* 278 */     ITickableTextureObject itickabletextureobject = new ITickableTextureObject() {
/*     */         public void tick() {
/* 280 */           TextureAnimations.updateAnimations();
/*     */         }
/*     */ 
/*     */         
/*     */         public void loadTexture(IResourceManager var1) throws IOException {}
/*     */         
/*     */         public int getGlTextureId() {
/* 287 */           return 0;
/*     */         }
/*     */ 
/*     */         
/*     */         public void setBlurMipmap(boolean p_174936_1, boolean p_174936_2) {}
/*     */ 
/*     */         
/*     */         public void restoreLastBlurMipmap() {}
/*     */         
/*     */         public MultiTexID getMultiTexID() {
/* 297 */           return null;
/*     */         }
/*     */       };
/* 300 */     ResourceLocation resourcelocation = new ResourceLocation("optifine/TickableTextures");
/* 301 */     Config.getTextureManager().loadTickableTexture(resourcelocation, itickabletextureobject);
/*     */   }
/*     */   
/*     */   public static ResourceLocation fixResourceLocation(ResourceLocation loc, String basePath) {
/* 305 */     if (!loc.getResourceDomain().equals("minecraft")) {
/* 306 */       return loc;
/*     */     }
/* 308 */     String s = loc.getResourcePath();
/* 309 */     String s1 = fixResourcePath(s, basePath);
/*     */     
/* 311 */     if (s1 != s) {
/* 312 */       loc = new ResourceLocation(loc.getResourceDomain(), s1);
/*     */     }
/*     */     
/* 315 */     return loc;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fixResourcePath(String path, String basePath) {
/* 320 */     String s = "assets/minecraft/";
/*     */     
/* 322 */     if (path.startsWith(s)) {
/* 323 */       path = path.substring(s.length());
/* 324 */       return path;
/* 325 */     }  if (path.startsWith("./")) {
/* 326 */       path = path.substring(2);
/*     */       
/* 328 */       if (!basePath.endsWith("/")) {
/* 329 */         basePath = String.valueOf(basePath) + "/";
/*     */       }
/*     */       
/* 332 */       path = String.valueOf(basePath) + path;
/* 333 */       return path;
/*     */     } 
/* 335 */     if (path.startsWith("/~")) {
/* 336 */       path = path.substring(1);
/*     */     }
/*     */     
/* 339 */     String s1 = "mcpatcher/";
/*     */     
/* 341 */     if (path.startsWith("~/")) {
/* 342 */       path = path.substring(2);
/* 343 */       path = String.valueOf(s1) + path;
/* 344 */       return path;
/* 345 */     }  if (path.startsWith("/")) {
/* 346 */       path = String.valueOf(s1) + path.substring(1);
/* 347 */       return path;
/*     */     } 
/* 349 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBasePath(String path) {
/* 355 */     int i = path.lastIndexOf('/');
/* 356 */     return (i < 0) ? "" : path.substring(0, i);
/*     */   }
/*     */   
/*     */   public static void applyAnisotropicLevel() {
/* 360 */     if ((GLContext.getCapabilities()).GL_EXT_texture_filter_anisotropic) {
/* 361 */       float f = GL11.glGetFloat(34047);
/* 362 */       float f1 = Config.getAnisotropicFilterLevel();
/* 363 */       f1 = Math.min(f1, f);
/* 364 */       GL11.glTexParameterf(3553, 34046, f1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void bindTexture(int glTexId) {
/* 369 */     GlStateManager.bindTexture(glTexId);
/*     */   }
/*     */   
/*     */   public static boolean isPowerOfTwo(int x) {
/* 373 */     int i = MathHelper.roundUpToPowerOfTwo(x);
/* 374 */     return (i == x);
/*     */   }
/*     */   
/*     */   public static BufferedImage scaleImage(BufferedImage bi, int w2) {
/* 378 */     int i = bi.getWidth();
/* 379 */     int j = bi.getHeight();
/* 380 */     int k = j * w2 / i;
/* 381 */     BufferedImage bufferedimage = new BufferedImage(w2, k, 2);
/* 382 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 383 */     Object object = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*     */     
/* 385 */     if (w2 < i || w2 % i != 0) {
/* 386 */       object = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
/*     */     }
/*     */     
/* 389 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, object);
/* 390 */     graphics2d.drawImage(bi, 0, 0, w2, k, null);
/* 391 */     return bufferedimage;
/*     */   }
/*     */   
/*     */   public static int scaleToGrid(int size, int sizeGrid) {
/* 395 */     if (size == sizeGrid) {
/* 396 */       return size;
/*     */     }
/*     */ 
/*     */     
/* 400 */     for (int i = size / sizeGrid * sizeGrid; i < size; i += sizeGrid);
/*     */ 
/*     */ 
/*     */     
/* 404 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int scaleToMin(int size, int sizeMin) {
/* 409 */     if (size >= sizeMin) {
/* 410 */       return size;
/*     */     }
/*     */ 
/*     */     
/* 414 */     for (int i = sizeMin / size * size; i < sizeMin; i += size);
/*     */ 
/*     */ 
/*     */     
/* 418 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Dimension getImageSize(InputStream in, String suffix) {
/* 423 */     Iterator<ImageReader> iterator = ImageIO.getImageReadersBySuffix(suffix);
/*     */ 
/*     */     
/* 426 */     while (iterator.hasNext()) {
/* 427 */       Dimension dimension; ImageReader imagereader = iterator.next();
/*     */ 
/*     */       
/*     */       try {
/* 431 */         ImageInputStream imageinputstream = ImageIO.createImageInputStream(in);
/* 432 */         imagereader.setInput(imageinputstream);
/* 433 */         int i = imagereader.getWidth(imagereader.getMinIndex());
/* 434 */         int j = imagereader.getHeight(imagereader.getMinIndex());
/* 435 */         dimension = new Dimension(i, j);
/* 436 */       } catch (IOException var11) {
/*     */         continue;
/*     */       } finally {
/* 439 */         imagereader.dispose();
/*     */       } 
/*     */       
/* 442 */       return dimension;
/*     */     } 
/*     */     
/* 445 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void dbgMipmaps(TextureAtlasSprite textureatlassprite) {
/* 450 */     int[][] aint = textureatlassprite.getFrameTextureData(0);
/*     */     
/* 452 */     for (int i = 0; i < aint.length; i++) {
/* 453 */       int[] aint1 = aint[i];
/*     */       
/* 455 */       if (aint1 == null) {
/* 456 */         Config.dbg(i + ": " + aint1);
/*     */       } else {
/* 458 */         Config.dbg(i + ": " + aint1.length);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void saveGlTexture(String name, int textureId, int mipmapLevels, int width, int height) {
/* 464 */     bindTexture(textureId);
/* 465 */     GL11.glPixelStorei(3333, 1);
/* 466 */     GL11.glPixelStorei(3317, 1);
/* 467 */     File file1 = new File(name);
/* 468 */     File file2 = file1.getParentFile();
/*     */     
/* 470 */     if (file2 != null) {
/* 471 */       file2.mkdirs();
/*     */     }
/*     */     
/* 474 */     for (int i = 0; i < 16; i++) {
/* 475 */       File file3 = new File(String.valueOf(name) + "_" + i + ".png");
/* 476 */       file3.delete();
/*     */     } 
/*     */     
/* 479 */     for (int i1 = 0; i1 <= mipmapLevels; i1++) {
/* 480 */       File file4 = new File(String.valueOf(name) + "_" + i1 + ".png");
/* 481 */       int j = width >> i1;
/* 482 */       int k = height >> i1;
/* 483 */       int l = j * k;
/* 484 */       IntBuffer intbuffer = BufferUtils.createIntBuffer(l);
/* 485 */       int[] aint = new int[l];
/* 486 */       GL11.glGetTexImage(3553, i1, 32993, 33639, intbuffer);
/* 487 */       intbuffer.get(aint);
/* 488 */       BufferedImage bufferedimage = new BufferedImage(j, k, 2);
/* 489 */       bufferedimage.setRGB(0, 0, j, k, aint, 0, j);
/*     */       
/*     */       try {
/* 492 */         ImageIO.write(bufferedimage, "png", file4);
/* 493 */         Config.dbg("Exported: " + file4);
/* 494 */       } catch (Exception exception) {
/* 495 */         Config.warn("Error writing: " + file4);
/* 496 */         Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void generateCustomMipmaps(TextureAtlasSprite tas, int mipmaps) {
/* 502 */     int i = tas.getIconWidth();
/* 503 */     int j = tas.getIconHeight();
/*     */     
/* 505 */     if (tas.getFrameCount() < 1) {
/* 506 */       List<int[][]> list = (List)new ArrayList<>();
/* 507 */       int[][] aint = new int[mipmaps + 1][];
/* 508 */       int[] aint1 = new int[i * j];
/* 509 */       aint[0] = aint1;
/* 510 */       list.add(aint);
/* 511 */       tas.setFramesTextureData(list);
/*     */     } 
/*     */     
/* 514 */     List<int[][]> list1 = (List)new ArrayList<>();
/* 515 */     int l = tas.getFrameCount();
/*     */     
/* 517 */     for (int i1 = 0; i1 < l; i1++) {
/* 518 */       int[] aint2 = getFrameData(tas, i1, 0);
/*     */       
/* 520 */       if (aint2 == null || aint2.length < 1) {
/* 521 */         aint2 = new int[i * j];
/*     */       }
/*     */       
/* 524 */       if (aint2.length != i * j) {
/* 525 */         int k = (int)Math.round(Math.sqrt(aint2.length));
/*     */         
/* 527 */         if (k * k != aint2.length) {
/* 528 */           aint2 = new int[1];
/* 529 */           k = 1;
/*     */         } 
/*     */         
/* 532 */         BufferedImage bufferedimage = new BufferedImage(k, k, 2);
/* 533 */         bufferedimage.setRGB(0, 0, k, k, aint2, 0, k);
/* 534 */         BufferedImage bufferedimage1 = scaleImage(bufferedimage, i);
/* 535 */         int[] aint3 = new int[i * j];
/* 536 */         bufferedimage1.getRGB(0, 0, i, j, aint3, 0, i);
/* 537 */         aint2 = aint3;
/*     */       } 
/*     */       
/* 540 */       int[][] aint4 = new int[mipmaps + 1][];
/* 541 */       aint4[0] = aint2;
/* 542 */       list1.add(aint4);
/*     */     } 
/*     */     
/* 545 */     tas.setFramesTextureData(list1);
/* 546 */     tas.generateMipmaps(mipmaps);
/*     */   }
/*     */   
/*     */   public static int[] getFrameData(TextureAtlasSprite tas, int frame, int level) {
/* 550 */     List<int[][]> list = tas.getFramesTextureData();
/*     */     
/* 552 */     if (list.size() <= frame) {
/* 553 */       return null;
/*     */     }
/* 555 */     int[][] aint = list.get(frame);
/*     */     
/* 557 */     if (aint != null && aint.length > level) {
/* 558 */       int[] aint1 = aint[level];
/* 559 */       return aint1;
/*     */     } 
/* 561 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGLMaximumTextureSize() {
/* 567 */     for (int i = 65536; i > 0; i >>= 1) {
/* 568 */       GlStateManager.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
/* 569 */       int j = GL11.glGetError();
/* 570 */       int k = GlStateManager.glGetTexLevelParameteri(32868, 0, 4096);
/*     */       
/* 572 */       if (k != 0) {
/* 573 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 577 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\TextureUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */