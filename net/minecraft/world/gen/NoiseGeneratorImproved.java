/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseGeneratorImproved
/*     */   extends NoiseGenerator
/*     */ {
/*     */   private int[] permutations;
/*     */   public double xCoord;
/*     */   public double yCoord;
/*     */   public double zCoord;
/*  16 */   private static final double[] field_152381_e = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  17 */   private static final double[] field_152382_f = new double[] { 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D };
/*  18 */   private static final double[] field_152383_g = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*  19 */   private static final double[] field_152384_h = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  20 */   private static final double[] field_152385_i = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*     */   
/*     */   public NoiseGeneratorImproved() {
/*  23 */     this(new Random());
/*     */   }
/*     */   
/*     */   public NoiseGeneratorImproved(Random p_i45469_1_) {
/*  27 */     this.permutations = new int[512];
/*  28 */     this.xCoord = p_i45469_1_.nextDouble() * 256.0D;
/*  29 */     this.yCoord = p_i45469_1_.nextDouble() * 256.0D;
/*  30 */     this.zCoord = p_i45469_1_.nextDouble() * 256.0D;
/*     */     
/*  32 */     for (int i = 0; i < 256; this.permutations[i] = i++);
/*     */ 
/*     */ 
/*     */     
/*  36 */     for (int l = 0; l < 256; l++) {
/*  37 */       int j = p_i45469_1_.nextInt(256 - l) + l;
/*  38 */       int k = this.permutations[l];
/*  39 */       this.permutations[l] = this.permutations[j];
/*  40 */       this.permutations[j] = k;
/*  41 */       this.permutations[l + 256] = this.permutations[l];
/*     */     } 
/*     */   }
/*     */   
/*     */   public final double lerp(double p_76311_1_, double p_76311_3_, double p_76311_5_) {
/*  46 */     return p_76311_3_ + p_76311_1_ * (p_76311_5_ - p_76311_3_);
/*     */   }
/*     */   
/*     */   public final double func_76309_a(int p_76309_1_, double p_76309_2_, double p_76309_4_) {
/*  50 */     int i = p_76309_1_ & 0xF;
/*  51 */     return field_152384_h[i] * p_76309_2_ + field_152385_i[i] * p_76309_4_;
/*     */   }
/*     */   
/*     */   public final double grad(int p_76310_1_, double p_76310_2_, double p_76310_4_, double p_76310_6_) {
/*  55 */     int i = p_76310_1_ & 0xF;
/*  56 */     return field_152381_e[i] * p_76310_2_ + field_152382_f[i] * p_76310_4_ + field_152383_g[i] * p_76310_6_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populateNoiseArray(double[] noiseArray, double xOffset, double yOffset, double zOffset, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale, double noiseScale) {
/*  63 */     if (ySize == 1) {
/*  64 */       int i5 = 0;
/*  65 */       int j5 = 0;
/*  66 */       int j = 0;
/*  67 */       int k5 = 0;
/*  68 */       double d14 = 0.0D;
/*  69 */       double d15 = 0.0D;
/*  70 */       int l5 = 0;
/*  71 */       double d16 = 1.0D / noiseScale;
/*     */       
/*  73 */       for (int j2 = 0; j2 < xSize; j2++) {
/*  74 */         double d17 = xOffset + j2 * xScale + this.xCoord;
/*  75 */         int i6 = (int)d17;
/*     */         
/*  77 */         if (d17 < i6) {
/*  78 */           i6--;
/*     */         }
/*     */         
/*  81 */         int k2 = i6 & 0xFF;
/*  82 */         d17 -= i6;
/*  83 */         double d18 = d17 * d17 * d17 * (d17 * (d17 * 6.0D - 15.0D) + 10.0D);
/*     */         
/*  85 */         for (int j6 = 0; j6 < zSize; j6++) {
/*  86 */           double d19 = zOffset + j6 * zScale + this.zCoord;
/*  87 */           int k6 = (int)d19;
/*     */           
/*  89 */           if (d19 < k6) {
/*  90 */             k6--;
/*     */           }
/*     */           
/*  93 */           int l6 = k6 & 0xFF;
/*  94 */           d19 -= k6;
/*  95 */           double d20 = d19 * d19 * d19 * (d19 * (d19 * 6.0D - 15.0D) + 10.0D);
/*  96 */           i5 = this.permutations[k2] + 0;
/*  97 */           j5 = this.permutations[i5] + l6;
/*  98 */           j = this.permutations[k2 + 1] + 0;
/*  99 */           k5 = this.permutations[j] + l6;
/* 100 */           d14 = lerp(d18, func_76309_a(this.permutations[j5], d17, d19), grad(this.permutations[k5], d17 - 1.0D, 0.0D, d19));
/* 101 */           d15 = lerp(d18, grad(this.permutations[j5 + 1], d17, 0.0D, d19 - 1.0D), grad(this.permutations[k5 + 1], d17 - 1.0D, 0.0D, d19 - 1.0D));
/* 102 */           double d21 = lerp(d20, d14, d15);
/* 103 */           int i7 = l5++;
/* 104 */           noiseArray[i7] = noiseArray[i7] + d21 * d16;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 108 */       int i = 0;
/* 109 */       double d0 = 1.0D / noiseScale;
/* 110 */       int k = -1;
/* 111 */       int l = 0;
/* 112 */       int i1 = 0;
/* 113 */       int j1 = 0;
/* 114 */       int k1 = 0;
/* 115 */       int l1 = 0;
/* 116 */       int i2 = 0;
/* 117 */       double d1 = 0.0D;
/* 118 */       double d2 = 0.0D;
/* 119 */       double d3 = 0.0D;
/* 120 */       double d4 = 0.0D;
/*     */       
/* 122 */       for (int l2 = 0; l2 < xSize; l2++) {
/* 123 */         double d5 = xOffset + l2 * xScale + this.xCoord;
/* 124 */         int i3 = (int)d5;
/*     */         
/* 126 */         if (d5 < i3) {
/* 127 */           i3--;
/*     */         }
/*     */         
/* 130 */         int j3 = i3 & 0xFF;
/* 131 */         d5 -= i3;
/* 132 */         double d6 = d5 * d5 * d5 * (d5 * (d5 * 6.0D - 15.0D) + 10.0D);
/*     */         
/* 134 */         for (int k3 = 0; k3 < zSize; k3++) {
/* 135 */           double d7 = zOffset + k3 * zScale + this.zCoord;
/* 136 */           int l3 = (int)d7;
/*     */           
/* 138 */           if (d7 < l3) {
/* 139 */             l3--;
/*     */           }
/*     */           
/* 142 */           int i4 = l3 & 0xFF;
/* 143 */           d7 -= l3;
/* 144 */           double d8 = d7 * d7 * d7 * (d7 * (d7 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 146 */           for (int j4 = 0; j4 < ySize; j4++) {
/* 147 */             double d9 = yOffset + j4 * yScale + this.yCoord;
/* 148 */             int k4 = (int)d9;
/*     */             
/* 150 */             if (d9 < k4) {
/* 151 */               k4--;
/*     */             }
/*     */             
/* 154 */             int l4 = k4 & 0xFF;
/* 155 */             d9 -= k4;
/* 156 */             double d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0D - 15.0D) + 10.0D);
/*     */             
/* 158 */             if (j4 == 0 || l4 != k) {
/* 159 */               k = l4;
/* 160 */               l = this.permutations[j3] + l4;
/* 161 */               i1 = this.permutations[l] + i4;
/* 162 */               j1 = this.permutations[l + 1] + i4;
/* 163 */               k1 = this.permutations[j3 + 1] + l4;
/* 164 */               l1 = this.permutations[k1] + i4;
/* 165 */               i2 = this.permutations[k1 + 1] + i4;
/* 166 */               d1 = lerp(d6, grad(this.permutations[i1], d5, d9, d7), grad(this.permutations[l1], d5 - 1.0D, d9, d7));
/* 167 */               d2 = lerp(d6, grad(this.permutations[j1], d5, d9 - 1.0D, d7), grad(this.permutations[i2], d5 - 1.0D, d9 - 1.0D, d7));
/* 168 */               d3 = lerp(d6, grad(this.permutations[i1 + 1], d5, d9, d7 - 1.0D), grad(this.permutations[l1 + 1], d5 - 1.0D, d9, d7 - 1.0D));
/* 169 */               d4 = lerp(d6, grad(this.permutations[j1 + 1], d5, d9 - 1.0D, d7 - 1.0D), grad(this.permutations[i2 + 1], d5 - 1.0D, d9 - 1.0D, d7 - 1.0D));
/*     */             } 
/*     */             
/* 172 */             double d11 = lerp(d10, d1, d2);
/* 173 */             double d12 = lerp(d10, d3, d4);
/* 174 */             double d13 = lerp(d8, d11, d12);
/* 175 */             int j7 = i++;
/* 176 */             noiseArray[j7] = noiseArray[j7] + d13 * d0;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\NoiseGeneratorImproved.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */