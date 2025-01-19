/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.optifine.render.RenderEnv;
/*     */ 
/*     */ public class ConnectedTexturesCompact
/*     */ {
/*     */   private static final int COMPACT_NONE = 0;
/*     */   private static final int COMPACT_ALL = 1;
/*     */   private static final int COMPACT_V = 2;
/*     */   private static final int COMPACT_H = 3;
/*     */   private static final int COMPACT_HV = 4;
/*     */   
/*     */   public static BakedQuad[] getConnectedTextureCtmCompact(int ctmIndex, ConnectedProperties cp, int side, BakedQuad quad, RenderEnv renderEnv) {
/*  18 */     if (cp.ctmTileIndexes != null && ctmIndex >= 0 && ctmIndex < cp.ctmTileIndexes.length) {
/*  19 */       int i = cp.ctmTileIndexes[ctmIndex];
/*     */       
/*  21 */       if (i >= 0 && i <= cp.tileIcons.length) {
/*  22 */         return getQuadsCompact(i, cp.tileIcons, quad, renderEnv);
/*     */       }
/*     */     } 
/*     */     
/*  26 */     switch (ctmIndex) {
/*     */       case 1:
/*  28 */         return getQuadsCompactH(0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 2:
/*  31 */         return getQuadsCompact(3, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 3:
/*  34 */         return getQuadsCompactH(3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 4:
/*  37 */         return getQuadsCompact4(0, 3, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 5:
/*  40 */         return getQuadsCompact4(3, 0, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 6:
/*  43 */         return getQuadsCompact4(2, 4, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 7:
/*  46 */         return getQuadsCompact4(3, 3, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 8:
/*  49 */         return getQuadsCompact4(4, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 9:
/*  52 */         return getQuadsCompact4(4, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 10:
/*  55 */         return getQuadsCompact4(1, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 11:
/*  58 */         return getQuadsCompact4(1, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 12:
/*  61 */         return getQuadsCompactV(0, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 13:
/*  64 */         return getQuadsCompact4(0, 3, 2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 14:
/*  67 */         return getQuadsCompactV(3, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 15:
/*  70 */         return getQuadsCompact4(3, 0, 1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 16:
/*  73 */         return getQuadsCompact4(2, 4, 0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 17:
/*  76 */         return getQuadsCompact4(4, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 18:
/*  79 */         return getQuadsCompact4(4, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 19:
/*  82 */         return getQuadsCompact4(4, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 20:
/*  85 */         return getQuadsCompact4(1, 4, 4, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 21:
/*  88 */         return getQuadsCompact4(4, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 22:
/*  91 */         return getQuadsCompact4(4, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 23:
/*  94 */         return getQuadsCompact4(4, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 24:
/*  97 */         return getQuadsCompact(2, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 25:
/* 100 */         return getQuadsCompactH(2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 26:
/* 103 */         return getQuadsCompact(1, cp.tileIcons, quad, renderEnv);
/*     */       
/*     */       case 27:
/* 106 */         return getQuadsCompactH(1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 28:
/* 109 */         return getQuadsCompact4(2, 4, 2, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 29:
/* 112 */         return getQuadsCompact4(3, 3, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 30:
/* 115 */         return getQuadsCompact4(2, 1, 2, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 31:
/* 118 */         return getQuadsCompact4(3, 3, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 32:
/* 121 */         return getQuadsCompact4(1, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 33:
/* 124 */         return getQuadsCompact4(1, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 34:
/* 127 */         return getQuadsCompact4(4, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 35:
/* 130 */         return getQuadsCompact4(1, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 36:
/* 133 */         return getQuadsCompactV(2, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 37:
/* 136 */         return getQuadsCompact4(2, 1, 0, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 38:
/* 139 */         return getQuadsCompactV(1, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 39:
/* 142 */         return getQuadsCompact4(1, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 40:
/* 145 */         return getQuadsCompact4(4, 1, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 41:
/* 148 */         return getQuadsCompact4(1, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 42:
/* 151 */         return getQuadsCompact4(1, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 43:
/* 154 */         return getQuadsCompact4(4, 2, 1, 2, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 44:
/* 157 */         return getQuadsCompact4(1, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 45:
/* 160 */         return getQuadsCompact4(4, 1, 1, 1, cp.tileIcons, side, quad, renderEnv);
/*     */       
/*     */       case 46:
/* 163 */         return getQuadsCompact(4, cp.tileIcons, quad, renderEnv);
/*     */     } 
/*     */     
/* 166 */     return getQuadsCompact(0, cp.tileIcons, quad, renderEnv);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactH(int indexLeft, int indexRight, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 171 */     return getQuadsCompact(Dir.LEFT, indexLeft, Dir.RIGHT, indexRight, sprites, side, quad, renderEnv);
/*     */   }
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactV(int indexUp, int indexDown, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 175 */     return getQuadsCompact(Dir.UP, indexUp, Dir.DOWN, indexDown, sprites, side, quad, renderEnv);
/*     */   }
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact4(int upLeft, int upRight, int downLeft, int downRight, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 179 */     return (upLeft == upRight) ? ((downLeft == downRight) ? getQuadsCompact(Dir.UP, upLeft, Dir.DOWN, downLeft, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.UP, upLeft, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv)) : ((downLeft == downRight) ? getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN, downLeft, sprites, side, quad, renderEnv) : ((upLeft == downLeft) ? ((upRight == downRight) ? getQuadsCompact(Dir.LEFT, upLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv)) : ((upRight == downRight) ? getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.DOWN_LEFT, downLeft, Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(Dir.UP_LEFT, upLeft, Dir.UP_RIGHT, upRight, Dir.DOWN_LEFT, downLeft, Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv))));
/*     */   }
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(int index, TextureAtlasSprite[] sprites, BakedQuad quad, RenderEnv renderEnv) {
/* 183 */     TextureAtlasSprite textureatlassprite = sprites[index];
/* 184 */     return ConnectedTextures.getQuads(textureatlassprite, quad, renderEnv);
/*     */   }
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 188 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 189 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 190 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1);
/*     */   }
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, Dir dir3, int index3, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 194 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 195 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 196 */     BakedQuad bakedquad2 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
/* 197 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2);
/*     */   }
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir dir1, int index1, Dir dir2, int index2, Dir dir3, int index3, Dir dir4, int index4, TextureAtlasSprite[] sprites, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 201 */     BakedQuad bakedquad = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
/* 202 */     BakedQuad bakedquad1 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
/* 203 */     BakedQuad bakedquad2 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
/* 204 */     BakedQuad bakedquad3 = getQuadCompact(sprites[index4], dir4, side, quad, renderEnv);
/* 205 */     return renderEnv.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2, bakedquad3);
/*     */   }
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite sprite, Dir dir, int side, BakedQuad quad, RenderEnv renderEnv) {
/* 209 */     switch (dir) {
/*     */       case UP:
/* 211 */         return getQuadCompact(sprite, dir, 0, 0, 16, 8, side, quad, renderEnv);
/*     */       
/*     */       case UP_RIGHT:
/* 214 */         return getQuadCompact(sprite, dir, 8, 0, 16, 8, side, quad, renderEnv);
/*     */       
/*     */       case RIGHT:
/* 217 */         return getQuadCompact(sprite, dir, 8, 0, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN_RIGHT:
/* 220 */         return getQuadCompact(sprite, dir, 8, 8, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case null:
/* 223 */         return getQuadCompact(sprite, dir, 0, 8, 16, 16, side, quad, renderEnv);
/*     */       
/*     */       case DOWN_LEFT:
/* 226 */         return getQuadCompact(sprite, dir, 0, 8, 8, 16, side, quad, renderEnv);
/*     */       
/*     */       case LEFT:
/* 229 */         return getQuadCompact(sprite, dir, 0, 0, 8, 16, side, quad, renderEnv);
/*     */       
/*     */       case UP_LEFT:
/* 232 */         return getQuadCompact(sprite, dir, 0, 0, 8, 8, side, quad, renderEnv);
/*     */     } 
/*     */     
/* 235 */     return quad;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite sprite, Dir dir, int x1, int y1, int x2, int y2, int side, BakedQuad quadIn, RenderEnv renderEnv) {
/* 240 */     Map[][] amap = ConnectedTextures.getSpriteQuadCompactMaps();
/*     */     
/* 242 */     if (amap == null) {
/* 243 */       return quadIn;
/*     */     }
/* 245 */     int i = sprite.getIndexInMap();
/*     */     
/* 247 */     if (i >= 0 && i < amap.length) {
/* 248 */       Map[] amap1 = amap[i];
/*     */       
/* 250 */       if (amap1 == null) {
/* 251 */         amap1 = new Map[Dir.VALUES.length];
/* 252 */         amap[i] = amap1;
/*     */       } 
/*     */       
/* 255 */       Map<BakedQuad, BakedQuad> map = amap1[dir.ordinal()];
/*     */       
/* 257 */       if (map == null) {
/* 258 */         map = new IdentityHashMap<>(1);
/* 259 */         amap1[dir.ordinal()] = map;
/*     */       } 
/*     */       
/* 262 */       BakedQuad bakedquad = map.get(quadIn);
/*     */       
/* 264 */       if (bakedquad == null) {
/* 265 */         bakedquad = makeSpriteQuadCompact(quadIn, sprite, side, x1, y1, x2, y2);
/* 266 */         map.put(quadIn, bakedquad);
/*     */       } 
/*     */       
/* 269 */       return bakedquad;
/*     */     } 
/* 271 */     return quadIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad makeSpriteQuadCompact(BakedQuad quad, TextureAtlasSprite sprite, int side, int x1, int y1, int x2, int y2) {
/* 277 */     int[] aint = (int[])quad.getVertexData().clone();
/* 278 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/* 280 */     for (int i = 0; i < 4; i++) {
/* 281 */       fixVertexCompact(aint, i, textureatlassprite, sprite, side, x1, y1, x2, y2);
/*     */     }
/*     */     
/* 284 */     BakedQuad bakedquad = new BakedQuad(aint, quad.getTintIndex(), quad.getFace(), sprite);
/* 285 */     return bakedquad;
/*     */   }
/*     */   private static void fixVertexCompact(int[] data, int vertex, TextureAtlasSprite spriteFrom, TextureAtlasSprite spriteTo, int side, int x1, int y1, int x2, int y2) {
/*     */     float f5, f6;
/* 289 */     int i = data.length / 4;
/* 290 */     int j = i * vertex;
/* 291 */     float f = Float.intBitsToFloat(data[j + 4]);
/* 292 */     float f1 = Float.intBitsToFloat(data[j + 4 + 1]);
/* 293 */     double d0 = spriteFrom.getSpriteU16(f);
/* 294 */     double d1 = spriteFrom.getSpriteV16(f1);
/* 295 */     float f2 = Float.intBitsToFloat(data[j + 0]);
/* 296 */     float f3 = Float.intBitsToFloat(data[j + 1]);
/* 297 */     float f4 = Float.intBitsToFloat(data[j + 2]);
/*     */ 
/*     */ 
/*     */     
/* 301 */     switch (side) {
/*     */       case 0:
/* 303 */         f5 = f2;
/* 304 */         f6 = 1.0F - f4;
/*     */         break;
/*     */       
/*     */       case 1:
/* 308 */         f5 = f2;
/* 309 */         f6 = f4;
/*     */         break;
/*     */       
/*     */       case 2:
/* 313 */         f5 = 1.0F - f2;
/* 314 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 3:
/* 318 */         f5 = f2;
/* 319 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 4:
/* 323 */         f5 = f4;
/* 324 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 5:
/* 328 */         f5 = 1.0F - f4;
/* 329 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 336 */     float f7 = 15.968F;
/* 337 */     float f8 = 15.968F;
/*     */     
/* 339 */     if (d0 < x1) {
/* 340 */       f5 = (float)(f5 + (x1 - d0) / f7);
/* 341 */       d0 = x1;
/*     */     } 
/*     */     
/* 344 */     if (d0 > x2) {
/* 345 */       f5 = (float)(f5 - (d0 - x2) / f7);
/* 346 */       d0 = x2;
/*     */     } 
/*     */     
/* 349 */     if (d1 < y1) {
/* 350 */       f6 = (float)(f6 + (y1 - d1) / f8);
/* 351 */       d1 = y1;
/*     */     } 
/*     */     
/* 354 */     if (d1 > y2) {
/* 355 */       f6 = (float)(f6 - (d1 - y2) / f8);
/* 356 */       d1 = y2;
/*     */     } 
/*     */     
/* 359 */     switch (side) {
/*     */       case 0:
/* 361 */         f2 = f5;
/* 362 */         f4 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 1:
/* 366 */         f2 = f5;
/* 367 */         f4 = f6;
/*     */         break;
/*     */       
/*     */       case 2:
/* 371 */         f2 = 1.0F - f5;
/* 372 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 3:
/* 376 */         f2 = f5;
/* 377 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 4:
/* 381 */         f4 = f5;
/* 382 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 5:
/* 386 */         f4 = 1.0F - f5;
/* 387 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 394 */     data[j + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(d0));
/* 395 */     data[j + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(d1));
/* 396 */     data[j + 0] = Float.floatToRawIntBits(f2);
/* 397 */     data[j + 1] = Float.floatToRawIntBits(f3);
/* 398 */     data[j + 2] = Float.floatToRawIntBits(f4);
/*     */   }
/*     */   
/*     */   private enum Dir {
/* 402 */     UP,
/* 403 */     UP_RIGHT,
/* 404 */     RIGHT,
/* 405 */     DOWN_RIGHT,
/* 406 */     DOWN,
/* 407 */     DOWN_LEFT,
/* 408 */     LEFT,
/* 409 */     UP_LEFT;
/*     */     
/* 411 */     public static final Dir[] VALUES = values();
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\ConnectedTexturesCompact.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */