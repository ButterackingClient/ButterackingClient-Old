/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class LightMap {
/*   7 */   private CustomColormap lightMapRgb = null;
/*   8 */   private float[][] sunRgbs = new float[16][3];
/*   9 */   private float[][] torchRgbs = new float[16][3];
/*     */   
/*     */   public LightMap(CustomColormap lightMapRgb) {
/*  12 */     this.lightMapRgb = lightMapRgb;
/*     */   }
/*     */   
/*     */   public CustomColormap getColormap() {
/*  16 */     return this.lightMapRgb;
/*     */   }
/*     */   
/*     */   public boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision) {
/*  20 */     if (this.lightMapRgb == null) {
/*  21 */       return false;
/*     */     }
/*  23 */     int i = this.lightMapRgb.getHeight();
/*     */     
/*  25 */     if (nightvision && i < 64) {
/*  26 */       return false;
/*     */     }
/*  28 */     int j = this.lightMapRgb.getWidth();
/*     */     
/*  30 */     if (j < 16) {
/*  31 */       warn("Invalid lightmap width: " + j);
/*  32 */       this.lightMapRgb = null;
/*  33 */       return false;
/*     */     } 
/*  35 */     int k = 0;
/*     */     
/*  37 */     if (nightvision) {
/*  38 */       k = j * 16 * 2;
/*     */     }
/*     */     
/*  41 */     float f = 1.1666666F * (world.getSunBrightness(1.0F) - 0.2F);
/*     */     
/*  43 */     if (world.getLastLightningBolt() > 0) {
/*  44 */       f = 1.0F;
/*     */     }
/*     */     
/*  47 */     f = Config.limitTo1(f);
/*  48 */     float f1 = f * (j - 1);
/*  49 */     float f2 = Config.limitTo1(torchFlickerX + 0.5F) * (j - 1);
/*  50 */     float f3 = Config.limitTo1((Config.getGameSettings()).saturation);
/*  51 */     boolean flag = (f3 > 1.0E-4F);
/*  52 */     float[][] afloat = this.lightMapRgb.getColorsRgb();
/*  53 */     getLightMapColumn(afloat, f1, k, j, this.sunRgbs);
/*  54 */     getLightMapColumn(afloat, f2, k + 16 * j, j, this.torchRgbs);
/*  55 */     float[] afloat1 = new float[3];
/*     */     
/*  57 */     for (int l = 0; l < 16; l++) {
/*  58 */       for (int i1 = 0; i1 < 16; i1++) {
/*  59 */         for (int j1 = 0; j1 < 3; j1++) {
/*  60 */           float f4 = Config.limitTo1(this.sunRgbs[l][j1] + this.torchRgbs[i1][j1]);
/*     */           
/*  62 */           if (flag) {
/*  63 */             float f5 = 1.0F - f4;
/*  64 */             f5 = 1.0F - f5 * f5 * f5 * f5;
/*  65 */             f4 = f3 * f5 + (1.0F - f3) * f4;
/*     */           } 
/*     */           
/*  68 */           afloat1[j1] = f4;
/*     */         } 
/*     */         
/*  71 */         int k1 = (int)(afloat1[0] * 255.0F);
/*  72 */         int l1 = (int)(afloat1[1] * 255.0F);
/*  73 */         int i2 = (int)(afloat1[2] * 255.0F);
/*  74 */         lmColors[l * 16 + i1] = 0xFF000000 | k1 << 16 | l1 << 8 | i2;
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getLightMapColumn(float[][] origMap, float x, int offset, int width, float[][] colRgb) {
/*  85 */     int i = (int)Math.floor(x);
/*  86 */     int j = (int)Math.ceil(x);
/*     */     
/*  88 */     if (i == j) {
/*  89 */       for (int i1 = 0; i1 < 16; i1++) {
/*  90 */         float[] afloat3 = origMap[offset + i1 * width + i];
/*  91 */         float[] afloat4 = colRgb[i1];
/*     */         
/*  93 */         for (int j1 = 0; j1 < 3; j1++) {
/*  94 */           afloat4[j1] = afloat3[j1];
/*     */         }
/*     */       } 
/*     */     } else {
/*  98 */       float f = 1.0F - x - i;
/*  99 */       float f1 = 1.0F - j - x;
/*     */       
/* 101 */       for (int k = 0; k < 16; k++) {
/* 102 */         float[] afloat = origMap[offset + k * width + i];
/* 103 */         float[] afloat1 = origMap[offset + k * width + j];
/* 104 */         float[] afloat2 = colRgb[k];
/*     */         
/* 106 */         for (int l = 0; l < 3; l++) {
/* 107 */           afloat2[l] = afloat[l] * f + afloat1[l] * f1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void dbg(String str) {
/* 114 */     Config.dbg("CustomColors: " + str);
/*     */   }
/*     */   
/*     */   private static void warn(String str) {
/* 118 */     Config.warn("CustomColors: " + str);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\LightMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */