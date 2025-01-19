/*    */ package net.optifine.util;
/*    */ 
/*    */ public class SmoothFloat {
/*    */   private float valueLast;
/*    */   private float timeFadeUpSec;
/*    */   private float timeFadeDownSec;
/*    */   private long timeLastMs;
/*    */   
/*    */   public SmoothFloat(float valueLast, float timeFadeSec) {
/* 10 */     this(valueLast, timeFadeSec, timeFadeSec);
/*    */   }
/*    */   
/*    */   public SmoothFloat(float valueLast, float timeFadeUpSec, float timeFadeDownSec) {
/* 14 */     this.valueLast = valueLast;
/* 15 */     this.timeFadeUpSec = timeFadeUpSec;
/* 16 */     this.timeFadeDownSec = timeFadeDownSec;
/* 17 */     this.timeLastMs = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public float getValueLast() {
/* 21 */     return this.valueLast;
/*    */   }
/*    */   
/*    */   public float getTimeFadeUpSec() {
/* 25 */     return this.timeFadeUpSec;
/*    */   }
/*    */   
/*    */   public float getTimeFadeDownSec() {
/* 29 */     return this.timeFadeDownSec;
/*    */   }
/*    */   
/*    */   public long getTimeLastMs() {
/* 33 */     return this.timeLastMs;
/*    */   }
/*    */   
/*    */   public float getSmoothValue(float value, float timeFadeUpSec, float timeFadeDownSec) {
/* 37 */     this.timeFadeUpSec = timeFadeUpSec;
/* 38 */     this.timeFadeDownSec = timeFadeDownSec;
/* 39 */     return getSmoothValue(value);
/*    */   }
/*    */   
/*    */   public float getSmoothValue(float value) {
/* 43 */     long i = System.currentTimeMillis();
/* 44 */     float f = this.valueLast;
/* 45 */     long j = this.timeLastMs;
/* 46 */     float f1 = (float)(i - j) / 1000.0F;
/* 47 */     float f2 = (value >= f) ? this.timeFadeUpSec : this.timeFadeDownSec;
/* 48 */     float f3 = getSmoothValue(f, value, f1, f2);
/* 49 */     this.valueLast = f3;
/* 50 */     this.timeLastMs = i;
/* 51 */     return f3;
/*    */   }
/*    */   public static float getSmoothValue(float valPrev, float value, float timeDeltaSec, float timeFadeSec) {
/*    */     float f1;
/* 55 */     if (timeDeltaSec <= 0.0F) {
/* 56 */       return valPrev;
/*    */     }
/* 58 */     float f = value - valPrev;
/*    */ 
/*    */     
/* 61 */     if (timeFadeSec > 0.0F && timeDeltaSec < timeFadeSec && Math.abs(f) > 1.0E-6F) {
/* 62 */       float f2 = timeFadeSec / timeDeltaSec;
/* 63 */       float f3 = 4.61F;
/* 64 */       float f4 = 0.13F;
/* 65 */       float f5 = 10.0F;
/* 66 */       float f6 = f3 - 1.0F / (f4 + f2 / f5);
/* 67 */       float f7 = timeDeltaSec / timeFadeSec * f6;
/* 68 */       f7 = NumUtils.limit(f7, 0.0F, 1.0F);
/* 69 */       f1 = valPrev + f * f7;
/*    */     } else {
/* 71 */       f1 = value;
/*    */     } 
/*    */     
/* 74 */     return f1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\SmoothFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */