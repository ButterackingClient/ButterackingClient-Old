/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*     */ import net.minecraft.client.renderer.texture.Stitcher;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ShadersTex
/*     */ {
/*     */   public static final int initialBufferSize = 1048576;
/*  35 */   public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
/*  36 */   public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
/*  37 */   public static int[] intArray = new int[1048576];
/*     */   public static final int defBaseTexColor = 0;
/*     */   public static final int defNormTexColor = -8421377;
/*     */   public static final int defSpecTexColor = 0;
/*  41 */   public static Map<Integer, MultiTexID> multiTexMap = new HashMap<>();
/*     */   
/*     */   public static IntBuffer getIntBuffer(int size) {
/*  44 */     if (intBuffer.capacity() < size) {
/*  45 */       int i = roundUpPOT(size);
/*  46 */       byteBuffer = BufferUtils.createByteBuffer(i * 4);
/*  47 */       intBuffer = byteBuffer.asIntBuffer();
/*     */     } 
/*     */     
/*  50 */     return intBuffer;
/*     */   }
/*     */   
/*     */   public static int[] getIntArray(int size) {
/*  54 */     if (intArray == null) {
/*  55 */       intArray = new int[1048576];
/*     */     }
/*     */     
/*  58 */     if (intArray.length < size) {
/*  59 */       intArray = new int[roundUpPOT(size)];
/*     */     }
/*     */     
/*  62 */     return intArray;
/*     */   }
/*     */   
/*     */   public static int roundUpPOT(int x) {
/*  66 */     int i = x - 1;
/*  67 */     i |= i >> 1;
/*  68 */     i |= i >> 2;
/*  69 */     i |= i >> 4;
/*  70 */     i |= i >> 8;
/*  71 */     i |= i >> 16;
/*  72 */     return i + 1;
/*     */   }
/*     */   
/*     */   public static int log2(int x) {
/*  76 */     int i = 0;
/*     */     
/*  78 */     if ((x & 0xFFFF0000) != 0) {
/*  79 */       i += 16;
/*  80 */       x >>= 16;
/*     */     } 
/*     */     
/*  83 */     if ((x & 0xFF00) != 0) {
/*  84 */       i += 8;
/*  85 */       x >>= 8;
/*     */     } 
/*     */     
/*  88 */     if ((x & 0xF0) != 0) {
/*  89 */       i += 4;
/*  90 */       x >>= 4;
/*     */     } 
/*     */     
/*  93 */     if ((x & 0x6) != 0) {
/*  94 */       i += 2;
/*  95 */       x >>= 2;
/*     */     } 
/*     */     
/*  98 */     if ((x & 0x2) != 0) {
/*  99 */       i++;
/*     */     }
/*     */     
/* 102 */     return i;
/*     */   }
/*     */   
/*     */   public static IntBuffer fillIntBuffer(int size, int value) {
/* 106 */     int[] aint = getIntArray(size);
/* 107 */     IntBuffer intbuffer = getIntBuffer(size);
/* 108 */     Arrays.fill(intArray, 0, size, value);
/* 109 */     intBuffer.put(intArray, 0, size);
/* 110 */     return intBuffer;
/*     */   }
/*     */   
/*     */   public static int[] createAIntImage(int size) {
/* 114 */     int[] aint = new int[size * 3];
/* 115 */     Arrays.fill(aint, 0, size, 0);
/* 116 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 117 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 118 */     return aint;
/*     */   }
/*     */   
/*     */   public static int[] createAIntImage(int size, int color) {
/* 122 */     int[] aint = new int[size * 3];
/* 123 */     Arrays.fill(aint, 0, size, color);
/* 124 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 125 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 126 */     return aint;
/*     */   }
/*     */   
/*     */   public static MultiTexID getMultiTexID(AbstractTexture tex) {
/* 130 */     MultiTexID multitexid = tex.multiTex;
/*     */     
/* 132 */     if (multitexid == null) {
/* 133 */       int i = tex.getGlTextureId();
/* 134 */       multitexid = multiTexMap.get(Integer.valueOf(i));
/*     */       
/* 136 */       if (multitexid == null) {
/* 137 */         multitexid = new MultiTexID(i, GL11.glGenTextures(), GL11.glGenTextures());
/* 138 */         multiTexMap.put(Integer.valueOf(i), multitexid);
/*     */       } 
/*     */       
/* 141 */       tex.multiTex = multitexid;
/*     */     } 
/*     */     
/* 144 */     return multitexid;
/*     */   }
/*     */   
/*     */   public static void deleteTextures(AbstractTexture atex, int texid) {
/* 148 */     MultiTexID multitexid = atex.multiTex;
/*     */     
/* 150 */     if (multitexid != null) {
/* 151 */       atex.multiTex = null;
/* 152 */       multiTexMap.remove(Integer.valueOf(multitexid.base));
/* 153 */       GlStateManager.deleteTexture(multitexid.norm);
/* 154 */       GlStateManager.deleteTexture(multitexid.spec);
/*     */       
/* 156 */       if (multitexid.base != texid) {
/* 157 */         SMCLog.warning("Error : MultiTexID.base mismatch: " + multitexid.base + ", texid: " + texid);
/* 158 */         GlStateManager.deleteTexture(multitexid.base);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void bindNSTextures(int normTex, int specTex) {
/* 164 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/* 165 */       GlStateManager.setActiveTexture(33986);
/* 166 */       GlStateManager.bindTexture(normTex);
/* 167 */       GlStateManager.setActiveTexture(33987);
/* 168 */       GlStateManager.bindTexture(specTex);
/* 169 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void bindNSTextures(MultiTexID multiTex) {
/* 174 */     bindNSTextures(multiTex.norm, multiTex.spec);
/*     */   }
/*     */   
/*     */   public static void bindTextures(int baseTex, int normTex, int specTex) {
/* 178 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/* 179 */       GlStateManager.setActiveTexture(33986);
/* 180 */       GlStateManager.bindTexture(normTex);
/* 181 */       GlStateManager.setActiveTexture(33987);
/* 182 */       GlStateManager.bindTexture(specTex);
/* 183 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 186 */     GlStateManager.bindTexture(baseTex);
/*     */   }
/*     */   
/*     */   public static void bindTextures(MultiTexID multiTex) {
/* 190 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/* 191 */       if (Shaders.configNormalMap) {
/* 192 */         GlStateManager.setActiveTexture(33986);
/* 193 */         GlStateManager.bindTexture(multiTex.norm);
/*     */       } 
/*     */       
/* 196 */       if (Shaders.configSpecularMap) {
/* 197 */         GlStateManager.setActiveTexture(33987);
/* 198 */         GlStateManager.bindTexture(multiTex.spec);
/*     */       } 
/*     */       
/* 201 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 204 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */   
/*     */   public static void bindTexture(ITextureObject tex) {
/* 208 */     int i = tex.getGlTextureId();
/* 209 */     bindTextures(tex.getMultiTexID());
/*     */     
/* 211 */     if (GlStateManager.getActiveTextureUnit() == 33984) {
/* 212 */       int j = Shaders.atlasSizeX;
/* 213 */       int k = Shaders.atlasSizeY;
/*     */       
/* 215 */       if (tex instanceof TextureMap) {
/* 216 */         Shaders.atlasSizeX = ((TextureMap)tex).atlasWidth;
/* 217 */         Shaders.atlasSizeY = ((TextureMap)tex).atlasHeight;
/*     */       } else {
/* 219 */         Shaders.atlasSizeX = 0;
/* 220 */         Shaders.atlasSizeY = 0;
/*     */       } 
/*     */       
/* 223 */       if (Shaders.atlasSizeX != j || Shaders.atlasSizeY != k) {
/* 224 */         Shaders.uniform_atlasSize.setValue(Shaders.atlasSizeX, Shaders.atlasSizeY);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void bindTextures(int baseTex) {
/* 230 */     MultiTexID multitexid = multiTexMap.get(Integer.valueOf(baseTex));
/* 231 */     bindTextures(multitexid);
/*     */   }
/*     */   
/*     */   public static void initDynamicTexture(int texID, int width, int height, DynamicTexture tex) {
/* 235 */     MultiTexID multitexid = tex.getMultiTexID();
/* 236 */     int[] aint = tex.getTextureData();
/* 237 */     int i = width * height;
/* 238 */     Arrays.fill(aint, i, i * 2, -8421377);
/* 239 */     Arrays.fill(aint, i * 2, i * 3, 0);
/* 240 */     TextureUtil.allocateTexture(multitexid.base, width, height);
/* 241 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 242 */     TextureUtil.setTextureClamped(false);
/* 243 */     TextureUtil.allocateTexture(multitexid.norm, width, height);
/* 244 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 245 */     TextureUtil.setTextureClamped(false);
/* 246 */     TextureUtil.allocateTexture(multitexid.spec, width, height);
/* 247 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 248 */     TextureUtil.setTextureClamped(false);
/* 249 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */   
/*     */   public static void updateDynamicTexture(int texID, int[] src, int width, int height, DynamicTexture tex) {
/* 253 */     MultiTexID multitexid = tex.getMultiTexID();
/* 254 */     GlStateManager.bindTexture(multitexid.base);
/* 255 */     updateDynTexSubImage1(src, width, height, 0, 0, 0);
/* 256 */     GlStateManager.bindTexture(multitexid.norm);
/* 257 */     updateDynTexSubImage1(src, width, height, 0, 0, 1);
/* 258 */     GlStateManager.bindTexture(multitexid.spec);
/* 259 */     updateDynTexSubImage1(src, width, height, 0, 0, 2);
/* 260 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */   
/*     */   public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page) {
/* 264 */     int i = width * height;
/* 265 */     IntBuffer intbuffer = getIntBuffer(i);
/* 266 */     intbuffer.clear();
/* 267 */     int j = page * i;
/*     */     
/* 269 */     if (src.length >= j + i) {
/* 270 */       intbuffer.put(src, j, i).position(0).limit(i);
/* 271 */       GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 272 */       intbuffer.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ITextureObject createDefaultTexture() {
/* 277 */     DynamicTexture dynamictexture = new DynamicTexture(1, 1);
/* 278 */     dynamictexture.getTextureData()[0] = -1;
/* 279 */     dynamictexture.updateDynamicTexture();
/* 280 */     return (ITextureObject)dynamictexture;
/*     */   }
/*     */   
/*     */   public static void allocateTextureMap(int texID, int mipmapLevels, int width, int height, Stitcher stitcher, TextureMap tex) {
/* 284 */     SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
/* 285 */     tex.atlasWidth = width;
/* 286 */     tex.atlasHeight = height;
/* 287 */     MultiTexID multitexid = getMultiTexID((AbstractTexture)tex);
/* 288 */     TextureUtil.allocateTextureImpl(multitexid.base, mipmapLevels, width, height);
/*     */     
/* 290 */     if (Shaders.configNormalMap) {
/* 291 */       TextureUtil.allocateTextureImpl(multitexid.norm, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 294 */     if (Shaders.configSpecularMap) {
/* 295 */       TextureUtil.allocateTextureImpl(multitexid.spec, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 298 */     GlStateManager.bindTexture(texID);
/*     */   }
/*     */   
/*     */   public static void uploadTexSubForLoadAtlas(TextureMap textureMap, String iconName, int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 302 */     MultiTexID multitexid = textureMap.multiTex;
/* 303 */     TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
/* 304 */     boolean flag = false;
/*     */     
/* 306 */     if (Shaders.configNormalMap) {
/* 307 */       int[][] aint = readImageAndMipmaps(textureMap, String.valueOf(iconName) + "_n", width, height, data.length, flag, -8421377);
/* 308 */       GlStateManager.bindTexture(multitexid.norm);
/* 309 */       TextureUtil.uploadTextureMipmap(aint, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 312 */     if (Shaders.configSpecularMap) {
/* 313 */       int[][] aint1 = readImageAndMipmaps(textureMap, String.valueOf(iconName) + "_s", width, height, data.length, flag, 0);
/* 314 */       GlStateManager.bindTexture(multitexid.spec);
/* 315 */       TextureUtil.uploadTextureMipmap(aint1, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 318 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */   
/*     */   public static int[][] readImageAndMipmaps(TextureMap updatingTextureMap, String name, int width, int height, int numLevels, boolean border, int defColor) {
/* 322 */     MultiTexID multitexid = updatingTextureMap.multiTex;
/* 323 */     int[][] aint = new int[numLevels][];
/*     */     
/* 325 */     int[] aint1 = new int[width * height];
/* 326 */     boolean flag = false;
/* 327 */     BufferedImage bufferedimage = readImage(updatingTextureMap.completeResourceLocation(new ResourceLocation(name)));
/*     */     
/* 329 */     if (bufferedimage != null) {
/* 330 */       int i = bufferedimage.getWidth();
/* 331 */       int j = bufferedimage.getHeight();
/*     */       
/* 333 */       if (i + (border ? 16 : 0) == width) {
/* 334 */         flag = true;
/* 335 */         bufferedimage.getRGB(0, 0, i, i, aint1, 0, i);
/*     */       } 
/*     */     } 
/*     */     
/* 339 */     if (!flag) {
/* 340 */       Arrays.fill(aint1, defColor);
/*     */     }
/*     */     
/* 343 */     GlStateManager.bindTexture(multitexid.spec);
/* 344 */     aint = genMipmapsSimple(aint.length - 1, width, aint);
/* 345 */     return aint;
/*     */   }
/*     */   
/*     */   public static BufferedImage readImage(ResourceLocation resLoc) {
/*     */     try {
/* 350 */       if (!Config.hasResource(resLoc)) {
/* 351 */         return null;
/*     */       }
/* 353 */       InputStream inputstream = Config.getResourceStream(resLoc);
/*     */       
/* 355 */       if (inputstream == null) {
/* 356 */         return null;
/*     */       }
/* 358 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/* 359 */       inputstream.close();
/* 360 */       return bufferedimage;
/*     */     
/*     */     }
/* 363 */     catch (IOException var3) {
/* 364 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data) {
/* 369 */     for (int i = 1; i <= maxLevel; i++) {
/* 370 */       if (data[i] == null) {
/* 371 */         int j = width >> i;
/* 372 */         int k = j * 2;
/* 373 */         int[] aint = data[i - 1];
/* 374 */         int[] aint1 = data[i] = new int[j * j];
/*     */         
/* 376 */         for (int i1 = 0; i1 < j; i1++) {
/* 377 */           for (int l = 0; l < j; l++) {
/* 378 */             int j1 = i1 * 2 * k + l * 2;
/* 379 */             aint1[i1 * j + l] = blend4Simple(aint[j1], aint[j1 + 1], aint[j1 + k], aint[j1 + k + 1]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     return data;
/*     */   }
/*     */   
/*     */   public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page) {
/* 389 */     int i = width * height;
/* 390 */     IntBuffer intbuffer = getIntBuffer(i);
/* 391 */     int j = src.length;
/* 392 */     int k = 0;
/* 393 */     int l = width;
/* 394 */     int i1 = height;
/* 395 */     int j1 = posX;
/*     */     
/* 397 */     for (int k1 = posY; l > 0 && i1 > 0 && k < j; k++) {
/* 398 */       int l1 = l * i1;
/* 399 */       int[] aint = src[k];
/* 400 */       intbuffer.clear();
/*     */       
/* 402 */       if (aint.length >= l1 * (page + 1)) {
/* 403 */         intbuffer.put(aint, l1 * page, l1).position(0).limit(l1);
/* 404 */         GL11.glTexSubImage2D(3553, k, j1, k1, l, i1, 32993, 33639, intbuffer);
/*     */       } 
/*     */       
/* 407 */       l >>= 1;
/* 408 */       i1 >>= 1;
/* 409 */       j1 >>= 1;
/* 410 */       k1 >>= 1;
/*     */     } 
/*     */     
/* 413 */     intbuffer.clear();
/*     */   }
/*     */   
/*     */   public static int blend4Alpha(int c0, int c1, int c2, int c3) {
/* 417 */     int k1, i = c0 >>> 24 & 0xFF;
/* 418 */     int j = c1 >>> 24 & 0xFF;
/* 419 */     int k = c2 >>> 24 & 0xFF;
/* 420 */     int l = c3 >>> 24 & 0xFF;
/* 421 */     int i1 = i + j + k + l;
/* 422 */     int j1 = (i1 + 2) / 4;
/*     */ 
/*     */     
/* 425 */     if (i1 != 0) {
/* 426 */       k1 = i1;
/*     */     } else {
/* 428 */       k1 = 4;
/* 429 */       i = 1;
/* 430 */       j = 1;
/* 431 */       k = 1;
/* 432 */       l = 1;
/*     */     } 
/*     */     
/* 435 */     int l1 = (k1 + 1) / 2;
/* 436 */     int i2 = j1 << 24 | ((c0 >>> 16 & 0xFF) * i + (c1 >>> 16 & 0xFF) * j + (c2 >>> 16 & 0xFF) * k + (c3 >>> 16 & 0xFF) * l + l1) / k1 << 16 | ((c0 >>> 8 & 0xFF) * i + (c1 >>> 8 & 0xFF) * j + (c2 >>> 8 & 0xFF) * k + (c3 >>> 8 & 0xFF) * l + l1) / k1 << 8 | ((c0 >>> 0 & 0xFF) * i + (c1 >>> 0 & 0xFF) * j + (c2 >>> 0 & 0xFF) * k + (c3 >>> 0 & 0xFF) * l + l1) / k1 << 0;
/* 437 */     return i2;
/*     */   }
/*     */   
/*     */   public static int blend4Simple(int c0, int c1, int c2, int c3) {
/* 441 */     int i = ((c0 >>> 24 & 0xFF) + (c1 >>> 24 & 0xFF) + (c2 >>> 24 & 0xFF) + (c3 >>> 24 & 0xFF) + 2) / 4 << 24 | ((c0 >>> 16 & 0xFF) + (c1 >>> 16 & 0xFF) + (c2 >>> 16 & 0xFF) + (c3 >>> 16 & 0xFF) + 2) / 4 << 16 | ((c0 >>> 8 & 0xFF) + (c1 >>> 8 & 0xFF) + (c2 >>> 8 & 0xFF) + (c3 >>> 8 & 0xFF) + 2) / 4 << 8 | ((c0 >>> 0 & 0xFF) + (c1 >>> 0 & 0xFF) + (c2 >>> 0 & 0xFF) + (c3 >>> 0 & 0xFF) + 2) / 4 << 0;
/* 442 */     return i;
/*     */   }
/*     */   
/*     */   public static void genMipmapAlpha(int[] aint, int offset, int width, int height) {
/* 446 */     Math.min(width, height);
/* 447 */     int o2 = offset;
/* 448 */     int w2 = width;
/* 449 */     int h2 = height;
/* 450 */     int o1 = 0;
/* 451 */     int w1 = 0;
/* 452 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 455 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/* 456 */       o1 = o2 + w2 * h2;
/* 457 */       w1 = w2 / 2;
/* 458 */       h1 = h2 / 2;
/*     */       
/* 460 */       for (int l1 = 0; l1 < h1; l1++) {
/* 461 */         int i2 = o1 + l1 * w1;
/* 462 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 464 */         for (int k2 = 0; k2 < w1; k2++) {
/* 465 */           aint[i2 + k2] = blend4Alpha(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 469 */       i++;
/* 470 */       w2 = w1;
/* 471 */       h2 = h1;
/*     */     } 
/*     */     
/* 474 */     while (i > 0) {
/* 475 */       i--;
/* 476 */       w2 = width >> i;
/* 477 */       h2 = height >> i;
/* 478 */       o2 = o1 - w2 * h2;
/* 479 */       int l2 = o2;
/*     */       
/* 481 */       for (int i3 = 0; i3 < h2; i3++) {
/* 482 */         for (int j3 = 0; j3 < w2; j3++) {
/* 483 */           if (aint[l2] == 0) {
/* 484 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 487 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 491 */       o1 = o2;
/* 492 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void genMipmapSimple(int[] aint, int offset, int width, int height) {
/* 497 */     Math.min(width, height);
/* 498 */     int o2 = offset;
/* 499 */     int w2 = width;
/* 500 */     int h2 = height;
/* 501 */     int o1 = 0;
/* 502 */     int w1 = 0;
/* 503 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 506 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/* 507 */       o1 = o2 + w2 * h2;
/* 508 */       w1 = w2 / 2;
/* 509 */       h1 = h2 / 2;
/*     */       
/* 511 */       for (int l1 = 0; l1 < h1; l1++) {
/* 512 */         int i2 = o1 + l1 * w1;
/* 513 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 515 */         for (int k2 = 0; k2 < w1; k2++) {
/* 516 */           aint[i2 + k2] = blend4Simple(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 520 */       i++;
/* 521 */       w2 = w1;
/* 522 */       h2 = h1;
/*     */     } 
/*     */     
/* 525 */     while (i > 0) {
/* 526 */       i--;
/* 527 */       w2 = width >> i;
/* 528 */       h2 = height >> i;
/* 529 */       o2 = o1 - w2 * h2;
/* 530 */       int l2 = o2;
/*     */       
/* 532 */       for (int i3 = 0; i3 < h2; i3++) {
/* 533 */         for (int j3 = 0; j3 < w2; j3++) {
/* 534 */           if (aint[l2] == 0) {
/* 535 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 538 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 542 */       o1 = o2;
/* 543 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isSemiTransparent(int[] aint, int width, int height) {
/* 548 */     int i = width * height;
/*     */     
/* 550 */     if (aint[0] >>> 24 == 255 && aint[i - 1] == 0) {
/* 551 */       return true;
/*     */     }
/* 553 */     for (int j = 0; j < i; j++) {
/* 554 */       int k = aint[j] >>> 24;
/*     */       
/* 556 */       if (k != 0 && k != 255) {
/* 557 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 561 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateSubTex1(int[] src, int width, int height, int posX, int posY) {
/* 566 */     int i = 0;
/* 567 */     int j = width;
/* 568 */     int k = height;
/* 569 */     int l = posX;
/*     */     
/* 571 */     for (int i1 = posY; j > 0 && k > 0; i1 /= 2) {
/* 572 */       GL11.glCopyTexSubImage2D(3553, i, l, i1, 0, 0, j, k);
/* 573 */       i++;
/* 574 */       j /= 2;
/* 575 */       k /= 2;
/* 576 */       l /= 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setupTexture(MultiTexID multiTex, int[] src, int width, int height, boolean linear, boolean clamp) {
/* 581 */     int i = linear ? 9729 : 9728;
/* 582 */     int j = clamp ? 33071 : 10497;
/* 583 */     int k = width * height;
/* 584 */     IntBuffer intbuffer = getIntBuffer(k);
/* 585 */     intbuffer.clear();
/* 586 */     intbuffer.put(src, 0, k).position(0).limit(k);
/* 587 */     GlStateManager.bindTexture(multiTex.base);
/* 588 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 589 */     GL11.glTexParameteri(3553, 10241, i);
/* 590 */     GL11.glTexParameteri(3553, 10240, i);
/* 591 */     GL11.glTexParameteri(3553, 10242, j);
/* 592 */     GL11.glTexParameteri(3553, 10243, j);
/* 593 */     intbuffer.put(src, k, k).position(0).limit(k);
/* 594 */     GlStateManager.bindTexture(multiTex.norm);
/* 595 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 596 */     GL11.glTexParameteri(3553, 10241, i);
/* 597 */     GL11.glTexParameteri(3553, 10240, i);
/* 598 */     GL11.glTexParameteri(3553, 10242, j);
/* 599 */     GL11.glTexParameteri(3553, 10243, j);
/* 600 */     intbuffer.put(src, k * 2, k).position(0).limit(k);
/* 601 */     GlStateManager.bindTexture(multiTex.spec);
/* 602 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 603 */     GL11.glTexParameteri(3553, 10241, i);
/* 604 */     GL11.glTexParameteri(3553, 10240, i);
/* 605 */     GL11.glTexParameteri(3553, 10242, j);
/* 606 */     GL11.glTexParameteri(3553, 10243, j);
/* 607 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */   
/*     */   public static void updateSubImage(MultiTexID multiTex, int[] src, int width, int height, int posX, int posY, boolean linear, boolean clamp) {
/* 611 */     int i = width * height;
/* 612 */     IntBuffer intbuffer = getIntBuffer(i);
/* 613 */     intbuffer.clear();
/* 614 */     intbuffer.put(src, 0, i);
/* 615 */     intbuffer.position(0).limit(i);
/* 616 */     GlStateManager.bindTexture(multiTex.base);
/* 617 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 618 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 619 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 620 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 621 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 623 */     if (src.length == i * 3) {
/* 624 */       intbuffer.clear();
/* 625 */       intbuffer.put(src, i, i).position(0);
/* 626 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 629 */     GlStateManager.bindTexture(multiTex.norm);
/* 630 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 631 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 632 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 633 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 634 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 636 */     if (src.length == i * 3) {
/* 637 */       intbuffer.clear();
/* 638 */       intbuffer.put(src, i * 2, i);
/* 639 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 642 */     GlStateManager.bindTexture(multiTex.spec);
/* 643 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 644 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 645 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 646 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 647 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 648 */     GlStateManager.setActiveTexture(33984);
/*     */   }
/*     */   
/*     */   public static ResourceLocation getNSMapLocation(ResourceLocation location, String mapName) {
/* 652 */     if (location == null) {
/* 653 */       return null;
/*     */     }
/* 655 */     String s = location.getResourcePath();
/* 656 */     String[] astring = s.split(".png");
/* 657 */     String s1 = astring[0];
/* 658 */     return new ResourceLocation(location.getResourceDomain(), String.valueOf(s1) + "_" + mapName + ".png");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadNSMap(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint) {
/* 663 */     if (Shaders.configNormalMap) {
/* 664 */       loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, -8421377);
/*     */     }
/*     */     
/* 667 */     if (Shaders.configSpecularMap) {
/* 668 */       loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadNSMap1(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset, int defaultColor) {
/* 673 */     if (!loadNSMapFile(manager, location, width, height, aint, offset)) {
/* 674 */       Arrays.fill(aint, offset, offset + width * height, defaultColor);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean loadNSMapFile(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset) {
/* 679 */     if (location == null) {
/* 680 */       return false;
/*     */     }
/*     */     try {
/* 683 */       IResource iresource = manager.getResource(location);
/* 684 */       BufferedImage bufferedimage = ImageIO.read(iresource.getInputStream());
/*     */       
/* 686 */       if (bufferedimage == null)
/* 687 */         return false; 
/* 688 */       if (bufferedimage.getWidth() == width && bufferedimage.getHeight() == height) {
/* 689 */         bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
/* 690 */         return true;
/*     */       } 
/* 692 */       return false;
/*     */     }
/* 694 */     catch (IOException var8) {
/* 695 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int loadSimpleTexture(int textureID, BufferedImage bufferedimage, boolean linear, boolean clamp, IResourceManager resourceManager, ResourceLocation location, MultiTexID multiTex) {
/* 701 */     int i = bufferedimage.getWidth();
/* 702 */     int j = bufferedimage.getHeight();
/* 703 */     int k = i * j;
/* 704 */     int[] aint = getIntArray(k * 3);
/* 705 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/* 706 */     loadNSMap(resourceManager, location, i, j, aint);
/* 707 */     setupTexture(multiTex, aint, i, j, linear, clamp);
/* 708 */     return textureID;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void mergeImage(int[] aint, int dstoff, int srcoff, int size) {}
/*     */   
/*     */   public static int blendColor(int color1, int color2, int factor1) {
/* 715 */     int i = 255 - factor1;
/* 716 */     return ((color1 >>> 24 & 0xFF) * factor1 + (color2 >>> 24 & 0xFF) * i) / 255 << 24 | ((color1 >>> 16 & 0xFF) * factor1 + (color2 >>> 16 & 0xFF) * i) / 255 << 16 | ((color1 >>> 8 & 0xFF) * factor1 + (color2 >>> 8 & 0xFF) * i) / 255 << 8 | ((color1 >>> 0 & 0xFF) * factor1 + (color2 >>> 0 & 0xFF) * i) / 255 << 0;
/*     */   }
/*     */   
/*     */   public static void loadLayeredTexture(LayeredTexture tex, IResourceManager manager, List list) {
/* 720 */     int i = 0;
/* 721 */     int j = 0;
/* 722 */     int k = 0;
/* 723 */     int[] aint = null;
/*     */     
/* 725 */     for (Object s : list) {
/* 726 */       if (s != null) {
/*     */         try {
/* 728 */           ResourceLocation resourcelocation = new ResourceLocation((String)s);
/* 729 */           InputStream inputstream = manager.getResource(resourcelocation).getInputStream();
/* 730 */           BufferedImage bufferedimage = ImageIO.read(inputstream);
/*     */           
/* 732 */           if (k == 0) {
/* 733 */             i = bufferedimage.getWidth();
/* 734 */             j = bufferedimage.getHeight();
/* 735 */             k = i * j;
/* 736 */             aint = createAIntImage(k, 0);
/*     */           } 
/*     */           
/* 739 */           int[] aint1 = getIntArray(k * 3);
/* 740 */           bufferedimage.getRGB(0, 0, i, j, aint1, 0, i);
/* 741 */           loadNSMap(manager, resourcelocation, i, j, aint1);
/*     */           
/* 743 */           for (int l = 0; l < k; l++) {
/* 744 */             int i1 = aint1[l] >>> 24 & 0xFF;
/* 745 */             aint[k * 0 + l] = blendColor(aint1[k * 0 + l], aint[k * 0 + l], i1);
/* 746 */             aint[k * 1 + l] = blendColor(aint1[k * 1 + l], aint[k * 1 + l], i1);
/* 747 */             aint[k * 2 + l] = blendColor(aint1[k * 2 + l], aint[k * 2 + l], i1);
/*     */           } 
/* 749 */         } catch (IOException ioexception) {
/* 750 */           ioexception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 755 */     setupTexture(tex.getMultiTexID(), aint, i, j, false, false);
/*     */   }
/*     */   
/*     */   public static void updateTextureMinMagFilter() {
/* 759 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 760 */     ITextureObject itextureobject = texturemanager.getTexture(TextureMap.locationBlocksTexture);
/*     */     
/* 762 */     if (itextureobject != null) {
/* 763 */       MultiTexID multitexid = itextureobject.getMultiTexID();
/* 764 */       GlStateManager.bindTexture(multitexid.base);
/* 765 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
/* 766 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
/* 767 */       GlStateManager.bindTexture(multitexid.norm);
/* 768 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
/* 769 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
/* 770 */       GlStateManager.bindTexture(multitexid.spec);
/* 771 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
/* 772 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
/* 773 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex) {
/* 778 */     int i = src.length;
/* 779 */     int[][] aint = new int[i][];
/*     */     
/* 781 */     for (int j = 0; j < i; j++) {
/* 782 */       int[] aint1 = src[j];
/*     */       
/* 784 */       if (aint1 != null) {
/* 785 */         int k = (width >> j) * (height >> j);
/* 786 */         int[] aint2 = new int[k * 3];
/* 787 */         aint[j] = aint2;
/* 788 */         int l = aint1.length / 3;
/* 789 */         int i1 = k * frameIndex;
/* 790 */         int j1 = 0;
/* 791 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 792 */         i1 += l;
/* 793 */         j1 += k;
/* 794 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 795 */         i1 += l;
/* 796 */         j1 += k;
/* 797 */         System.arraycopy(aint1, i1, aint2, j1, k);
/*     */       } 
/*     */     } 
/*     */     
/* 801 */     return aint;
/*     */   }
/*     */   
/*     */   public static int[][] prepareAF(TextureAtlasSprite tas, int[][] src, int width, int height) {
/* 805 */     boolean flag = true;
/* 806 */     return src;
/*     */   }
/*     */   
/*     */   public static void fixTransparentColor(TextureAtlasSprite tas, int[] aint) {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ShadersTex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */