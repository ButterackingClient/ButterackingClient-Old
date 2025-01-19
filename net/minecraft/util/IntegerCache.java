/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class IntegerCache {
/*  4 */   private static final Integer[] CACHE = new Integer[65535];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Integer getInteger(int value) {
/* 10 */     return (value >= 0 && value < CACHE.length) ? CACHE[value] : new Integer(value);
/*    */   }
/*    */   
/*    */   static {
/* 14 */     int i = 0;
/*    */     
/* 16 */     for (int j = CACHE.length; i < j; i++)
/* 17 */       CACHE[i] = Integer.valueOf(i); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\IntegerCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */