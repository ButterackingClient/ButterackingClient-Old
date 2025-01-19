/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class StatCollector {
/*  4 */   private static StringTranslate localizedName = StringTranslate.getInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 10 */   private static StringTranslate fallbackTranslator = new StringTranslate();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String translateToLocal(String key) {
/* 16 */     return localizedName.translateKey(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String translateToLocalFormatted(String key, Object... format) {
/* 23 */     return localizedName.translateKeyFormat(key, format);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String translateToFallback(String key) {
/* 31 */     return fallbackTranslator.translateKey(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean canTranslate(String key) {
/* 38 */     return localizedName.isKeyTranslated(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long getLastTranslationUpdateTimeInMilliseconds() {
/* 45 */     return localizedName.getLastUpdateTimeInMilliseconds();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\StatCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */