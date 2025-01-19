/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class MathUtils {
/*    */   public static final float PI = 3.1415927F;
/*    */   public static final float PI2 = 6.2831855F;
/*    */   public static final float PId2 = 1.5707964F;
/*  9 */   private static final float[] ASIN_TABLE = new float[65536];
/*    */   
/*    */   public static float asin(float value) {
/* 12 */     return ASIN_TABLE[(int)((value + 1.0F) * 32767.5D) & 0xFFFF];
/*    */   }
/*    */   
/*    */   public static float acos(float value) {
/* 16 */     return 1.5707964F - ASIN_TABLE[(int)((value + 1.0F) * 32767.5D) & 0xFFFF];
/*    */   }
/*    */   
/*    */   public static int getAverage(int[] vals) {
/* 20 */     if (vals.length <= 0) {
/* 21 */       return 0;
/*    */     }
/* 23 */     int i = getSum(vals);
/* 24 */     int j = i / vals.length;
/* 25 */     return j;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getSum(int[] vals) {
/* 30 */     if (vals.length <= 0) {
/* 31 */       return 0;
/*    */     }
/* 33 */     int i = 0;
/*    */     
/* 35 */     for (int j = 0; j < vals.length; j++) {
/* 36 */       int k = vals[j];
/* 37 */       i += k;
/*    */     } 
/*    */     
/* 40 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int roundDownToPowerOfTwo(int val) {
/* 45 */     int i = MathHelper.roundUpToPowerOfTwo(val);
/* 46 */     return (val == i) ? i : (i / 2);
/*    */   }
/*    */   
/*    */   public static boolean equalsDelta(float f1, float f2, float delta) {
/* 50 */     return (Math.abs(f1 - f2) <= delta);
/*    */   }
/*    */   
/*    */   public static float toDeg(float angle) {
/* 54 */     return angle * 180.0F / MathHelper.PI;
/*    */   }
/*    */   
/*    */   public static float toRad(float angle) {
/* 58 */     return angle / 180.0F * MathHelper.PI;
/*    */   }
/*    */   
/*    */   public static float roundToFloat(double d) {
/* 62 */     return (float)(Math.round(d * 1.0E8D) / 1.0E8D);
/*    */   }
/*    */   
/*    */   static {
/* 66 */     for (int i = 0; i < 65536; i++) {
/* 67 */       ASIN_TABLE[i] = (float)Math.asin(i / 32767.5D - 1.0D);
/*    */     }
/*    */     
/* 70 */     for (int j = -1; j < 2; j++)
/* 71 */       ASIN_TABLE[(int)((j + 1.0D) * 32767.5D) & 0xFFFF] = (float)Math.asin(j); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */