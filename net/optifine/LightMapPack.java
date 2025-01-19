/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class LightMapPack {
/*     */   private LightMap lightMap;
/*     */   private LightMap lightMapRain;
/*     */   private LightMap lightMapThunder;
/*   9 */   private int[] colorBuffer1 = new int[0];
/*  10 */   private int[] colorBuffer2 = new int[0];
/*     */   
/*     */   public LightMapPack(LightMap lightMap, LightMap lightMapRain, LightMap lightMapThunder) {
/*  13 */     if (lightMapRain != null || lightMapThunder != null) {
/*  14 */       if (lightMapRain == null) {
/*  15 */         lightMapRain = lightMap;
/*     */       }
/*     */       
/*  18 */       if (lightMapThunder == null) {
/*  19 */         lightMapThunder = lightMapRain;
/*     */       }
/*     */     } 
/*     */     
/*  23 */     this.lightMap = lightMap;
/*  24 */     this.lightMapRain = lightMapRain;
/*  25 */     this.lightMapThunder = lightMapThunder;
/*     */   }
/*     */   
/*     */   public boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision, float partialTicks) {
/*  29 */     if (this.lightMapRain == null && this.lightMapThunder == null) {
/*  30 */       return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision);
/*     */     }
/*  32 */     int i = world.provider.getDimensionId();
/*     */     
/*  34 */     if (i != 1 && i != -1) {
/*  35 */       float f = world.getRainStrength(partialTicks);
/*  36 */       float f1 = world.getThunderStrength(partialTicks);
/*  37 */       float f2 = 1.0E-4F;
/*  38 */       boolean flag = (f > f2);
/*  39 */       boolean flag1 = (f1 > f2);
/*     */       
/*  41 */       if (!flag && !flag1) {
/*  42 */         return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision);
/*     */       }
/*  44 */       if (f > 0.0F) {
/*  45 */         f1 /= f;
/*     */       }
/*     */       
/*  48 */       float f3 = 1.0F - f;
/*  49 */       float f4 = f - f1;
/*     */       
/*  51 */       if (this.colorBuffer1.length != lmColors.length) {
/*  52 */         this.colorBuffer1 = new int[lmColors.length];
/*  53 */         this.colorBuffer2 = new int[lmColors.length];
/*     */       } 
/*     */       
/*  56 */       int j = 0;
/*  57 */       int[][] aint = { lmColors, this.colorBuffer1, this.colorBuffer2 };
/*  58 */       float[] afloat = new float[3];
/*     */       
/*  60 */       if (f3 > f2 && this.lightMap.updateLightmap(world, torchFlickerX, aint[j], nightvision)) {
/*  61 */         afloat[j] = f3;
/*  62 */         j++;
/*     */       } 
/*     */       
/*  65 */       if (f4 > f2 && this.lightMapRain != null && this.lightMapRain.updateLightmap(world, torchFlickerX, aint[j], nightvision)) {
/*  66 */         afloat[j] = f4;
/*  67 */         j++;
/*     */       } 
/*     */       
/*  70 */       if (f1 > f2 && this.lightMapThunder != null && this.lightMapThunder.updateLightmap(world, torchFlickerX, aint[j], nightvision)) {
/*  71 */         afloat[j] = f1;
/*  72 */         j++;
/*     */       } 
/*     */       
/*  75 */       return (j == 2) ? blend(aint[0], afloat[0], aint[1], afloat[1]) : ((j == 3) ? blend(aint[0], afloat[0], aint[1], afloat[1], aint[2], afloat[2]) : true);
/*     */     } 
/*     */     
/*  78 */     return this.lightMap.updateLightmap(world, torchFlickerX, lmColors, nightvision);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean blend(int[] cols0, float br0, int[] cols1, float br1) {
/*  84 */     if (cols1.length != cols0.length) {
/*  85 */       return false;
/*     */     }
/*  87 */     for (int i = 0; i < cols0.length; i++) {
/*  88 */       int j = cols0[i];
/*  89 */       int k = j >> 16 & 0xFF;
/*  90 */       int l = j >> 8 & 0xFF;
/*  91 */       int i1 = j & 0xFF;
/*  92 */       int j1 = cols1[i];
/*  93 */       int k1 = j1 >> 16 & 0xFF;
/*  94 */       int l1 = j1 >> 8 & 0xFF;
/*  95 */       int i2 = j1 & 0xFF;
/*  96 */       int j2 = (int)(k * br0 + k1 * br1);
/*  97 */       int k2 = (int)(l * br0 + l1 * br1);
/*  98 */       int l2 = (int)(i1 * br0 + i2 * br1);
/*  99 */       cols0[i] = 0xFF000000 | j2 << 16 | k2 << 8 | l2;
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean blend(int[] cols0, float br0, int[] cols1, float br1, int[] cols2, float br2) {
/* 107 */     if (cols1.length == cols0.length && cols2.length == cols0.length) {
/* 108 */       for (int i = 0; i < cols0.length; i++) {
/* 109 */         int j = cols0[i];
/* 110 */         int k = j >> 16 & 0xFF;
/* 111 */         int l = j >> 8 & 0xFF;
/* 112 */         int i1 = j & 0xFF;
/* 113 */         int j1 = cols1[i];
/* 114 */         int k1 = j1 >> 16 & 0xFF;
/* 115 */         int l1 = j1 >> 8 & 0xFF;
/* 116 */         int i2 = j1 & 0xFF;
/* 117 */         int j2 = cols2[i];
/* 118 */         int k2 = j2 >> 16 & 0xFF;
/* 119 */         int l2 = j2 >> 8 & 0xFF;
/* 120 */         int i3 = j2 & 0xFF;
/* 121 */         int j3 = (int)(k * br0 + k1 * br1 + k2 * br2);
/* 122 */         int k3 = (int)(l * br0 + l1 * br1 + l2 * br2);
/* 123 */         int l3 = (int)(i1 * br0 + i2 * br1 + i3 * br2);
/* 124 */         cols0[i] = 0xFF000000 | j3 << 16 | k3 << 8 | l3;
/*     */       } 
/*     */       
/* 127 */       return true;
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\LightMapPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */