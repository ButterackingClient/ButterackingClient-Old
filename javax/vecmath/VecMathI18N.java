/*    */ package javax.vecmath;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class VecMathI18N
/*    */ {
/*    */   static String getString(String key) {
/*    */     String s;
/*    */     try {
/* 37 */       s = ResourceBundle.getBundle("javax.vecmath.ExceptionStrings").getString(key);
/* 38 */     } catch (MissingResourceException e) {
/* 39 */       System.err.println("VecMathI18N: Error looking up: " + key);
/* 40 */       s = key;
/*    */     } 
/* 42 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\VecMathI18N.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */