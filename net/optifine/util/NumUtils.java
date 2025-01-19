/*    */ package net.optifine.util;
/*    */ 
/*    */ public class NumUtils {
/*    */   public static float limit(float val, float min, float max) {
/*  5 */     return (val < min) ? min : ((val > max) ? max : val);
/*    */   }
/*    */   
/*    */   public static int mod(int x, int y) {
/*  9 */     int i = x % y;
/*    */     
/* 11 */     if (i < 0) {
/* 12 */       i += y;
/*    */     }
/*    */     
/* 15 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\NumUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */