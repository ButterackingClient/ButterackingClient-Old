/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGeneratorSimplex {
/*   6 */   private static int[][] field_151611_e = new int[][] { { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
/*   7 */   public static final double field_151614_a = Math.sqrt(3.0D);
/*     */   private int[] field_151608_f;
/*     */   public double field_151612_b;
/*     */   public double field_151613_c;
/*     */   public double field_151610_d;
/*  12 */   private static final double field_151609_g = 0.5D * (field_151614_a - 1.0D);
/*  13 */   private static final double field_151615_h = (3.0D - field_151614_a) / 6.0D;
/*     */   
/*     */   public NoiseGeneratorSimplex() {
/*  16 */     this(new Random());
/*     */   }
/*     */   
/*     */   public NoiseGeneratorSimplex(Random p_i45471_1_) {
/*  20 */     this.field_151608_f = new int[512];
/*  21 */     this.field_151612_b = p_i45471_1_.nextDouble() * 256.0D;
/*  22 */     this.field_151613_c = p_i45471_1_.nextDouble() * 256.0D;
/*  23 */     this.field_151610_d = p_i45471_1_.nextDouble() * 256.0D;
/*     */     
/*  25 */     for (int i = 0; i < 256; this.field_151608_f[i] = i++);
/*     */ 
/*     */ 
/*     */     
/*  29 */     for (int l = 0; l < 256; l++) {
/*  30 */       int j = p_i45471_1_.nextInt(256 - l) + l;
/*  31 */       int k = this.field_151608_f[l];
/*  32 */       this.field_151608_f[l] = this.field_151608_f[j];
/*  33 */       this.field_151608_f[j] = k;
/*  34 */       this.field_151608_f[l + 256] = this.field_151608_f[l];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int func_151607_a(double p_151607_0_) {
/*  39 */     return (p_151607_0_ > 0.0D) ? (int)p_151607_0_ : ((int)p_151607_0_ - 1);
/*     */   }
/*     */   
/*     */   private static double func_151604_a(int[] p_151604_0_, double p_151604_1_, double p_151604_3_) {
/*  43 */     return p_151604_0_[0] * p_151604_1_ + p_151604_0_[1] * p_151604_3_;
/*     */   }
/*     */   public double func_151605_a(double p_151605_1_, double p_151605_3_) {
/*     */     int k, l;
/*  47 */     double d0, d1, d2, d3 = 0.5D * (field_151614_a - 1.0D);
/*  48 */     double d4 = (p_151605_1_ + p_151605_3_) * d3;
/*  49 */     int i = func_151607_a(p_151605_1_ + d4);
/*  50 */     int j = func_151607_a(p_151605_3_ + d4);
/*  51 */     double d5 = (3.0D - field_151614_a) / 6.0D;
/*  52 */     double d6 = (i + j) * d5;
/*  53 */     double d7 = i - d6;
/*  54 */     double d8 = j - d6;
/*  55 */     double d9 = p_151605_1_ - d7;
/*  56 */     double d10 = p_151605_3_ - d8;
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (d9 > d10) {
/*  61 */       k = 1;
/*  62 */       l = 0;
/*     */     } else {
/*  64 */       k = 0;
/*  65 */       l = 1;
/*     */     } 
/*     */     
/*  68 */     double d11 = d9 - k + d5;
/*  69 */     double d12 = d10 - l + d5;
/*  70 */     double d13 = d9 - 1.0D + 2.0D * d5;
/*  71 */     double d14 = d10 - 1.0D + 2.0D * d5;
/*  72 */     int i1 = i & 0xFF;
/*  73 */     int j1 = j & 0xFF;
/*  74 */     int k1 = this.field_151608_f[i1 + this.field_151608_f[j1]] % 12;
/*  75 */     int l1 = this.field_151608_f[i1 + k + this.field_151608_f[j1 + l]] % 12;
/*  76 */     int i2 = this.field_151608_f[i1 + 1 + this.field_151608_f[j1 + 1]] % 12;
/*  77 */     double d15 = 0.5D - d9 * d9 - d10 * d10;
/*     */ 
/*     */     
/*  80 */     if (d15 < 0.0D) {
/*  81 */       d0 = 0.0D;
/*     */     } else {
/*  83 */       d15 *= d15;
/*  84 */       d0 = d15 * d15 * func_151604_a(field_151611_e[k1], d9, d10);
/*     */     } 
/*     */     
/*  87 */     double d16 = 0.5D - d11 * d11 - d12 * d12;
/*     */ 
/*     */     
/*  90 */     if (d16 < 0.0D) {
/*  91 */       d1 = 0.0D;
/*     */     } else {
/*  93 */       d16 *= d16;
/*  94 */       d1 = d16 * d16 * func_151604_a(field_151611_e[l1], d11, d12);
/*     */     } 
/*     */     
/*  97 */     double d17 = 0.5D - d13 * d13 - d14 * d14;
/*     */ 
/*     */     
/* 100 */     if (d17 < 0.0D) {
/* 101 */       d2 = 0.0D;
/*     */     } else {
/* 103 */       d17 *= d17;
/* 104 */       d2 = d17 * d17 * func_151604_a(field_151611_e[i2], d13, d14);
/*     */     } 
/*     */     
/* 107 */     return 70.0D * (d0 + d1 + d2);
/*     */   }
/*     */   
/*     */   public void func_151606_a(double[] p_151606_1_, double p_151606_2_, double p_151606_4_, int p_151606_6_, int p_151606_7_, double p_151606_8_, double p_151606_10_, double p_151606_12_) {
/* 111 */     int i = 0;
/*     */     
/* 113 */     for (int j = 0; j < p_151606_7_; j++) {
/* 114 */       double d0 = (p_151606_4_ + j) * p_151606_10_ + this.field_151613_c;
/*     */       
/* 116 */       for (int k = 0; k < p_151606_6_; k++) {
/* 117 */         int j1, k1; double d2, d3, d4, d1 = (p_151606_2_ + k) * p_151606_8_ + this.field_151612_b;
/* 118 */         double d5 = (d1 + d0) * field_151609_g;
/* 119 */         int l = func_151607_a(d1 + d5);
/* 120 */         int i1 = func_151607_a(d0 + d5);
/* 121 */         double d6 = (l + i1) * field_151615_h;
/* 122 */         double d7 = l - d6;
/* 123 */         double d8 = i1 - d6;
/* 124 */         double d9 = d1 - d7;
/* 125 */         double d10 = d0 - d8;
/*     */ 
/*     */ 
/*     */         
/* 129 */         if (d9 > d10) {
/* 130 */           j1 = 1;
/* 131 */           k1 = 0;
/*     */         } else {
/* 133 */           j1 = 0;
/* 134 */           k1 = 1;
/*     */         } 
/*     */         
/* 137 */         double d11 = d9 - j1 + field_151615_h;
/* 138 */         double d12 = d10 - k1 + field_151615_h;
/* 139 */         double d13 = d9 - 1.0D + 2.0D * field_151615_h;
/* 140 */         double d14 = d10 - 1.0D + 2.0D * field_151615_h;
/* 141 */         int l1 = l & 0xFF;
/* 142 */         int i2 = i1 & 0xFF;
/* 143 */         int j2 = this.field_151608_f[l1 + this.field_151608_f[i2]] % 12;
/* 144 */         int k2 = this.field_151608_f[l1 + j1 + this.field_151608_f[i2 + k1]] % 12;
/* 145 */         int l2 = this.field_151608_f[l1 + 1 + this.field_151608_f[i2 + 1]] % 12;
/* 146 */         double d15 = 0.5D - d9 * d9 - d10 * d10;
/*     */ 
/*     */         
/* 149 */         if (d15 < 0.0D) {
/* 150 */           d2 = 0.0D;
/*     */         } else {
/* 152 */           d15 *= d15;
/* 153 */           d2 = d15 * d15 * func_151604_a(field_151611_e[j2], d9, d10);
/*     */         } 
/*     */         
/* 156 */         double d16 = 0.5D - d11 * d11 - d12 * d12;
/*     */ 
/*     */         
/* 159 */         if (d16 < 0.0D) {
/* 160 */           d3 = 0.0D;
/*     */         } else {
/* 162 */           d16 *= d16;
/* 163 */           d3 = d16 * d16 * func_151604_a(field_151611_e[k2], d11, d12);
/*     */         } 
/*     */         
/* 166 */         double d17 = 0.5D - d13 * d13 - d14 * d14;
/*     */ 
/*     */         
/* 169 */         if (d17 < 0.0D) {
/* 170 */           d4 = 0.0D;
/*     */         } else {
/* 172 */           d17 *= d17;
/* 173 */           d4 = d17 * d17 * func_151604_a(field_151611_e[l2], d13, d14);
/*     */         } 
/*     */         
/* 176 */         int i3 = i++;
/* 177 */         p_151606_1_[i3] = p_151606_1_[i3] + 70.0D * (d2 + d3 + d4) * p_151606_12_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\NoiseGeneratorSimplex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */