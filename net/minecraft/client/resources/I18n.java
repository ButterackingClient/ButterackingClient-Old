/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class I18n {
/*    */   private static Locale i18nLocale;
/*    */   
/*    */   static void setLocale(Locale i18nLocaleIn) {
/*  9 */     i18nLocale = i18nLocaleIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String format(String translateKey, Object... parameters) {
/* 16 */     return i18nLocale.formatMessage(translateKey, parameters);
/*    */   }
/*    */   
/*    */   public static Map getLocaleProperties() {
/* 20 */     return i18nLocale.properties;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\I18n.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */