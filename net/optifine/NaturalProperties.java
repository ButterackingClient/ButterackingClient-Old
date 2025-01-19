/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class NaturalProperties
/*     */ {
/*  13 */   public int rotation = 1;
/*     */   public boolean flip = false;
/*  15 */   private Map[] quadMaps = new Map[8];
/*     */   
/*     */   public NaturalProperties(String type) {
/*  18 */     if (type.equals("4")) {
/*  19 */       this.rotation = 4;
/*  20 */     } else if (type.equals("2")) {
/*  21 */       this.rotation = 2;
/*  22 */     } else if (type.equals("F")) {
/*  23 */       this.flip = true;
/*  24 */     } else if (type.equals("4F")) {
/*  25 */       this.rotation = 4;
/*  26 */       this.flip = true;
/*  27 */     } else if (type.equals("2F")) {
/*  28 */       this.rotation = 2;
/*  29 */       this.flip = true;
/*     */     } else {
/*  31 */       Config.warn("NaturalTextures: Unknown type: " + type);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/*  36 */     return (this.rotation != 2 && this.rotation != 4) ? this.flip : true;
/*     */   }
/*     */   
/*     */   public synchronized BakedQuad getQuad(BakedQuad quadIn, int rotate, boolean flipU) {
/*  40 */     int i = rotate;
/*     */     
/*  42 */     if (flipU) {
/*  43 */       i = rotate | 0x4;
/*     */     }
/*     */     
/*  46 */     if (i > 0 && i < this.quadMaps.length) {
/*  47 */       Map<Object, Object> map = this.quadMaps[i];
/*     */       
/*  49 */       if (map == null) {
/*  50 */         map = new IdentityHashMap<>(1);
/*  51 */         this.quadMaps[i] = map;
/*     */       } 
/*     */       
/*  54 */       BakedQuad bakedquad = (BakedQuad)map.get(quadIn);
/*     */       
/*  56 */       if (bakedquad == null) {
/*  57 */         bakedquad = makeQuad(quadIn, rotate, flipU);
/*  58 */         map.put(quadIn, bakedquad);
/*     */       } 
/*     */       
/*  61 */       return bakedquad;
/*     */     } 
/*  63 */     return quadIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private BakedQuad makeQuad(BakedQuad quad, int rotate, boolean flipU) {
/*  68 */     int[] aint = quad.getVertexData();
/*  69 */     int i = quad.getTintIndex();
/*  70 */     EnumFacing enumfacing = quad.getFace();
/*  71 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/*  73 */     if (!isFullSprite(quad)) {
/*  74 */       rotate = 0;
/*     */     }
/*     */     
/*  77 */     aint = transformVertexData(aint, rotate, flipU);
/*  78 */     BakedQuad bakedquad = new BakedQuad(aint, i, enumfacing, textureatlassprite);
/*  79 */     return bakedquad;
/*     */   }
/*     */   
/*     */   private int[] transformVertexData(int[] vertexData, int rotate, boolean flipU) {
/*  83 */     int[] aint = (int[])vertexData.clone();
/*  84 */     int i = 4 - rotate;
/*     */     
/*  86 */     if (flipU) {
/*  87 */       i += 3;
/*     */     }
/*     */     
/*  90 */     i %= 4;
/*  91 */     int j = aint.length / 4;
/*     */     
/*  93 */     for (int k = 0; k < 4; k++) {
/*  94 */       int l = k * j;
/*  95 */       int i1 = i * j;
/*  96 */       aint[i1 + 4] = vertexData[l + 4];
/*  97 */       aint[i1 + 4 + 1] = vertexData[l + 4 + 1];
/*     */       
/*  99 */       if (flipU) {
/* 100 */         i--;
/*     */         
/* 102 */         if (i < 0) {
/* 103 */           i = 3;
/*     */         }
/*     */       } else {
/* 106 */         i++;
/*     */         
/* 108 */         if (i > 3) {
/* 109 */           i = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     return aint;
/*     */   }
/*     */   
/*     */   private boolean isFullSprite(BakedQuad quad) {
/* 118 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/* 119 */     float f = textureatlassprite.getMinU();
/* 120 */     float f1 = textureatlassprite.getMaxU();
/* 121 */     float f2 = f1 - f;
/* 122 */     float f3 = f2 / 256.0F;
/* 123 */     float f4 = textureatlassprite.getMinV();
/* 124 */     float f5 = textureatlassprite.getMaxV();
/* 125 */     float f6 = f5 - f4;
/* 126 */     float f7 = f6 / 256.0F;
/* 127 */     int[] aint = quad.getVertexData();
/* 128 */     int i = aint.length / 4;
/*     */     
/* 130 */     for (int j = 0; j < 4; j++) {
/* 131 */       int k = j * i;
/* 132 */       float f8 = Float.intBitsToFloat(aint[k + 4]);
/* 133 */       float f9 = Float.intBitsToFloat(aint[k + 4 + 1]);
/*     */       
/* 135 */       if (!equalsDelta(f8, f, f3) && !equalsDelta(f8, f1, f3)) {
/* 136 */         return false;
/*     */       }
/*     */       
/* 139 */       if (!equalsDelta(f9, f4, f7) && !equalsDelta(f9, f5, f7)) {
/* 140 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   private boolean equalsDelta(float x1, float x2, float deltaMax) {
/* 148 */     float f = MathHelper.abs(x1 - x2);
/* 149 */     return (f < deltaMax);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\NaturalProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */