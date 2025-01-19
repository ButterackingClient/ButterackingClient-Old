/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public class MathHelper
/*     */ {
/*   9 */   public static final float SQRT_2 = sqrt_float(2.0F);
/*     */   private static final int SIN_BITS = 12;
/*     */   private static final int SIN_MASK = 4095;
/*     */   private static final int SIN_COUNT = 4096;
/*     */   private static final int SIN_COUNT_D4 = 1024;
/*  14 */   public static final float PI = MathUtils.roundToFloat(Math.PI);
/*  15 */   public static final float PI2 = MathUtils.roundToFloat(6.283185307179586D);
/*  16 */   public static final float PId2 = MathUtils.roundToFloat(1.5707963267948966D);
/*  17 */   private static final float radToIndex = MathUtils.roundToFloat(651.8986469044033D);
/*  18 */   public static final float deg2Rad = MathUtils.roundToFloat(0.017453292519943295D);
/*  19 */   private static final float[] SIN_TABLE_FAST = new float[4096];
/*     */ 
/*     */   
/*     */   public static boolean fastMath = false;
/*     */ 
/*     */   
/*  25 */   private static final float[] SIN_TABLE = new float[65536];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float sin(float p_76126_0_) {
/*  43 */     return fastMath ? SIN_TABLE_FAST[(int)(p_76126_0_ * radToIndex) & 0xFFF] : SIN_TABLE[(int)(p_76126_0_ * 10430.378F) & 0xFFFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cos(float value) {
/*  50 */     return fastMath ? SIN_TABLE_FAST[(int)(value * radToIndex + 1024.0F) & 0xFFF] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 0xFFFF];
/*     */   }
/*     */   
/*     */   public static float sqrt_float(float value) {
/*  54 */     return (float)Math.sqrt(value);
/*     */   }
/*     */   
/*     */   public static float sqrt_double(double value) {
/*  58 */     return (float)Math.sqrt(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor_float(float value) {
/*  65 */     int i = (int)value;
/*  66 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int truncateDoubleToInt(double value) {
/*  73 */     return (int)(value + 1024.0D) - 1024;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor_double(double value) {
/*  80 */     int i = (int)value;
/*  81 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long floor_double_long(double value) {
/*  88 */     long i = (long)value;
/*  89 */     return (value < i) ? (i - 1L) : i;
/*     */   }
/*     */   
/*     */   public static int func_154353_e(double value) {
/*  93 */     return (int)((value >= 0.0D) ? value : (-value + 1.0D));
/*     */   }
/*     */   
/*     */   public static float abs(float value) {
/*  97 */     return (value >= 0.0F) ? value : -value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int abs_int(int value) {
/* 104 */     return (value >= 0) ? value : -value;
/*     */   }
/*     */   
/*     */   public static int ceiling_float_int(float value) {
/* 108 */     int i = (int)value;
/* 109 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */   
/*     */   public static int ceiling_double_int(double value) {
/* 113 */     int i = (int)value;
/* 114 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int clamp_int(int num, int min, int max) {
/* 122 */     return (num < min) ? min : ((num > max) ? max : num);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float clamp_float(float num, float min, float max) {
/* 130 */     return (num < min) ? min : ((num > max) ? max : num);
/*     */   }
/*     */   
/*     */   public static double clamp_double(double num, double min, double max) {
/* 134 */     return (num < min) ? min : ((num > max) ? max : num);
/*     */   }
/*     */   
/*     */   public static double denormalizeClamp(double lowerBnd, double upperBnd, double slide) {
/* 138 */     return (slide < 0.0D) ? lowerBnd : ((slide > 1.0D) ? upperBnd : (lowerBnd + (upperBnd - lowerBnd) * slide));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double abs_max(double p_76132_0_, double p_76132_2_) {
/* 145 */     if (p_76132_0_ < 0.0D) {
/* 146 */       p_76132_0_ = -p_76132_0_;
/*     */     }
/*     */     
/* 149 */     if (p_76132_2_ < 0.0D) {
/* 150 */       p_76132_2_ = -p_76132_2_;
/*     */     }
/*     */     
/* 153 */     return (p_76132_0_ > p_76132_2_) ? p_76132_0_ : p_76132_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int bucketInt(int p_76137_0_, int p_76137_1_) {
/* 160 */     return (p_76137_0_ < 0) ? (-((-p_76137_0_ - 1) / p_76137_1_) - 1) : (p_76137_0_ / p_76137_1_);
/*     */   }
/*     */   
/*     */   public static int getRandomIntegerInRange(Random p_76136_0_, int p_76136_1_, int p_76136_2_) {
/* 164 */     return (p_76136_1_ >= p_76136_2_) ? p_76136_1_ : (p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_);
/*     */   }
/*     */   
/*     */   public static float randomFloatClamp(Random p_151240_0_, float p_151240_1_, float p_151240_2_) {
/* 168 */     return (p_151240_1_ >= p_151240_2_) ? p_151240_1_ : (p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_);
/*     */   }
/*     */   
/*     */   public static double getRandomDoubleInRange(Random p_82716_0_, double p_82716_1_, double p_82716_3_) {
/* 172 */     return (p_82716_1_ >= p_82716_3_) ? p_82716_1_ : (p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_);
/*     */   }
/*     */   
/*     */   public static double average(long[] values) {
/* 176 */     long i = 0L; byte b; int j;
/*     */     long[] arrayOfLong;
/* 178 */     for (j = (arrayOfLong = values).length, b = 0; b < j; ) { long l = arrayOfLong[b];
/* 179 */       i += l;
/*     */       b++; }
/*     */     
/* 182 */     return i / values.length;
/*     */   }
/*     */   
/*     */   public static boolean epsilonEquals(float p_180185_0_, float p_180185_1_) {
/* 186 */     return (abs(p_180185_1_ - p_180185_0_) < 1.0E-5F);
/*     */   }
/*     */   
/*     */   public static int normalizeAngle(int p_180184_0_, int p_180184_1_) {
/* 190 */     return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float wrapAngleTo180_float(float value) {
/* 197 */     value %= 360.0F;
/*     */     
/* 199 */     if (value >= 180.0F) {
/* 200 */       value -= 360.0F;
/*     */     }
/*     */     
/* 203 */     if (value < -180.0F) {
/* 204 */       value += 360.0F;
/*     */     }
/*     */     
/* 207 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double wrapAngleTo180_double(double value) {
/* 214 */     value %= 360.0D;
/*     */     
/* 216 */     if (value >= 180.0D) {
/* 217 */       value -= 360.0D;
/*     */     }
/*     */     
/* 220 */     if (value < -180.0D) {
/* 221 */       value += 360.0D;
/*     */     }
/*     */     
/* 224 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseIntWithDefault(String p_82715_0_, int p_82715_1_) {
/*     */     try {
/* 232 */       return Integer.parseInt(p_82715_0_);
/* 233 */     } catch (Throwable var3) {
/* 234 */       return p_82715_1_;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseIntWithDefaultAndMax(String p_82714_0_, int p_82714_1_, int p_82714_2_) {
/* 242 */     return Math.max(p_82714_2_, parseIntWithDefault(p_82714_0_, p_82714_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double parseDoubleWithDefault(String p_82712_0_, double p_82712_1_) {
/*     */     try {
/* 250 */       return Double.parseDouble(p_82712_0_);
/* 251 */     } catch (Throwable var4) {
/* 252 */       return p_82712_1_;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double parseDoubleWithDefaultAndMax(String p_82713_0_, double p_82713_1_, double p_82713_3_) {
/* 257 */     return Math.max(p_82713_3_, parseDoubleWithDefault(p_82713_0_, p_82713_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int roundUpToPowerOfTwo(int value) {
/* 264 */     int i = value - 1;
/* 265 */     i |= i >> 1;
/* 266 */     i |= i >> 2;
/* 267 */     i |= i >> 4;
/* 268 */     i |= i >> 8;
/* 269 */     i |= i >> 16;
/* 270 */     return i + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isPowerOfTwo(int value) {
/* 277 */     return (value != 0 && (value & value - 1) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateLogBaseTwoDeBruijn(int value) {
/* 286 */     value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
/* 287 */     return multiplyDeBruijnBitPosition[(int)(value * 125613361L >> 27L) & 0x1F];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateLogBaseTwo(int value) {
/* 295 */     return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
/*     */   }
/*     */   
/*     */   public static int roundUp(int p_154354_0_, int p_154354_1_) {
/* 299 */     if (p_154354_1_ == 0)
/* 300 */       return 0; 
/* 301 */     if (p_154354_0_ == 0) {
/* 302 */       return p_154354_1_;
/*     */     }
/* 304 */     if (p_154354_0_ < 0) {
/* 305 */       p_154354_1_ *= -1;
/*     */     }
/*     */     
/* 308 */     int i = p_154354_0_ % p_154354_1_;
/* 309 */     return (i == 0) ? p_154354_0_ : (p_154354_0_ + p_154354_1_ - i);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_180183_b(float p_180183_0_, float p_180183_1_, float p_180183_2_) {
/* 314 */     return func_180181_b(floor_float(p_180183_0_ * 255.0F), floor_float(p_180183_1_ * 255.0F), floor_float(p_180183_2_ * 255.0F));
/*     */   }
/*     */   
/*     */   public static int func_180181_b(int p_180181_0_, int p_180181_1_, int p_180181_2_) {
/* 318 */     int i = (p_180181_0_ << 8) + p_180181_1_;
/* 319 */     i = (i << 8) + p_180181_2_;
/* 320 */     return i;
/*     */   }
/*     */   
/*     */   public static int func_180188_d(int p_180188_0_, int p_180188_1_) {
/* 324 */     int i = (p_180188_0_ & 0xFF0000) >> 16;
/* 325 */     int j = (p_180188_1_ & 0xFF0000) >> 16;
/* 326 */     int k = (p_180188_0_ & 0xFF00) >> 8;
/* 327 */     int l = (p_180188_1_ & 0xFF00) >> 8;
/* 328 */     int i1 = (p_180188_0_ & 0xFF) >> 0;
/* 329 */     int j1 = (p_180188_1_ & 0xFF) >> 0;
/* 330 */     int k1 = (int)(i * j / 255.0F);
/* 331 */     int l1 = (int)(k * l / 255.0F);
/* 332 */     int i2 = (int)(i1 * j1 / 255.0F);
/* 333 */     return p_180188_0_ & 0xFF000000 | k1 << 16 | l1 << 8 | i2;
/*     */   }
/*     */   
/*     */   public static double func_181162_h(double p_181162_0_) {
/* 337 */     return p_181162_0_ - Math.floor(p_181162_0_);
/*     */   }
/*     */   
/*     */   public static long getPositionRandom(Vec3i pos) {
/* 341 */     return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */   
/*     */   public static long getCoordinateRandom(int x, int y, int z) {
/* 345 */     long i = (x * 3129871) ^ z * 116129781L ^ y;
/* 346 */     i = i * i * 42317861L + i * 11L;
/* 347 */     return i;
/*     */   }
/*     */   
/*     */   public static UUID getRandomUuid(Random rand) {
/* 351 */     long i = rand.nextLong() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
/* 352 */     long j = rand.nextLong() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
/* 353 */     return new UUID(i, j);
/*     */   }
/*     */   
/*     */   public static double func_181160_c(double p_181160_0_, double p_181160_2_, double p_181160_4_) {
/* 357 */     return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_);
/*     */   }
/*     */   
/*     */   public static double atan2(double p_181159_0_, double p_181159_2_) {
/* 361 */     double d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_;
/*     */     
/* 363 */     if (Double.isNaN(d0)) {
/* 364 */       return Double.NaN;
/*     */     }
/* 366 */     boolean flag = (p_181159_0_ < 0.0D);
/*     */     
/* 368 */     if (flag) {
/* 369 */       p_181159_0_ = -p_181159_0_;
/*     */     }
/*     */     
/* 372 */     boolean flag1 = (p_181159_2_ < 0.0D);
/*     */     
/* 374 */     if (flag1) {
/* 375 */       p_181159_2_ = -p_181159_2_;
/*     */     }
/*     */     
/* 378 */     boolean flag2 = (p_181159_0_ > p_181159_2_);
/*     */     
/* 380 */     if (flag2) {
/* 381 */       double d1 = p_181159_2_;
/* 382 */       p_181159_2_ = p_181159_0_;
/* 383 */       p_181159_0_ = d1;
/*     */     } 
/*     */     
/* 386 */     double d9 = func_181161_i(d0);
/* 387 */     p_181159_2_ *= d9;
/* 388 */     p_181159_0_ *= d9;
/* 389 */     double d2 = field_181163_d + p_181159_0_;
/* 390 */     int i = (int)Double.doubleToRawLongBits(d2);
/* 391 */     double d3 = field_181164_e[i];
/* 392 */     double d4 = field_181165_f[i];
/* 393 */     double d5 = d2 - field_181163_d;
/* 394 */     double d6 = p_181159_0_ * d4 - p_181159_2_ * d5;
/* 395 */     double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
/* 396 */     double d8 = d3 + d7;
/*     */     
/* 398 */     if (flag2) {
/* 399 */       d8 = 1.5707963267948966D - d8;
/*     */     }
/*     */     
/* 402 */     if (flag1) {
/* 403 */       d8 = Math.PI - d8;
/*     */     }
/*     */     
/* 406 */     if (flag) {
/* 407 */       d8 = -d8;
/*     */     }
/*     */     
/* 410 */     return d8;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double func_181161_i(double p_181161_0_) {
/* 415 */     double d0 = 0.5D * p_181161_0_;
/* 416 */     long i = Double.doubleToRawLongBits(p_181161_0_);
/* 417 */     i = 6910469410427058090L - (i >> 1L);
/* 418 */     p_181161_0_ = Double.longBitsToDouble(i);
/* 419 */     p_181161_0_ *= 1.5D - d0 * p_181161_0_ * p_181161_0_;
/* 420 */     return p_181161_0_;
/*     */   }
/*     */   public static int hsvToRGB(float p_181758_0_, float p_181758_1_, float p_181758_2_) {
/*     */     float f4, f5, f6;
/* 424 */     int j, k, l, i = (int)(p_181758_0_ * 6.0F) % 6;
/* 425 */     float f = p_181758_0_ * 6.0F - i;
/* 426 */     float f1 = p_181758_2_ * (1.0F - p_181758_1_);
/* 427 */     float f2 = p_181758_2_ * (1.0F - f * p_181758_1_);
/* 428 */     float f3 = p_181758_2_ * (1.0F - (1.0F - f) * p_181758_1_);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     switch (i) {
/*     */       case 0:
/* 435 */         f4 = p_181758_2_;
/* 436 */         f5 = f3;
/* 437 */         f6 = f1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 474 */         j = clamp_int((int)(f4 * 255.0F), 0, 255);
/* 475 */         k = clamp_int((int)(f5 * 255.0F), 0, 255);
/* 476 */         l = clamp_int((int)(f6 * 255.0F), 0, 255);
/* 477 */         return j << 16 | k << 8 | l;case 1: f4 = f2; f5 = p_181758_2_; f6 = f1; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 2: f4 = f1; f5 = p_181758_2_; f6 = f3; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 3: f4 = f1; f5 = f2; f6 = p_181758_2_; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 4: f4 = f3; f5 = f1; f6 = p_181758_2_; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 5: f4 = p_181758_2_; f5 = f1; f6 = f2; j = clamp_int((int)(f4 * 255.0F), 0, 255); k = clamp_int((int)(f5 * 255.0F), 0, 255); l = clamp_int((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;
/*     */     } 
/*     */     throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + p_181758_0_ + ", " + p_181758_1_ + ", " + p_181758_2_);
/*     */   } static {
/* 481 */     for (int i = 0; i < 65536; i++) {
/* 482 */       SIN_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0D / 65536.0D);
/*     */     }
/*     */     
/* 485 */     for (int j = 0; j < SIN_TABLE_FAST.length; j++)
/* 486 */       SIN_TABLE_FAST[j] = MathUtils.roundToFloat(Math.sin(j * Math.PI * 2.0D / 4096.0D)); 
/*     */   }
/*     */   
/* 489 */   private static final int[] multiplyDeBruijnBitPosition = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
/* 490 */   private static final double field_181163_d = Double.longBitsToDouble(4805340802404319232L);
/* 491 */   private static final double[] field_181164_e = new double[257];
/* 492 */   private static final double[] field_181165_f = new double[257];
/*     */   static {
/* 494 */     for (int k = 0; k < 257; k++) {
/* 495 */       double d0 = k / 256.0D;
/* 496 */       double d1 = Math.asin(d0);
/* 497 */       field_181165_f[k] = Math.cos(d1);
/* 498 */       field_181164_e[k] = d1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MathHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */