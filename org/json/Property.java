/*    */ package org.json;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.Properties;
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
/*    */ public class Property
/*    */ {
/*    */   public static JSONObject toJSONObject(Properties properties) throws JSONException {
/* 25 */     JSONObject jo = new JSONObject();
/* 26 */     if (properties != null && !properties.isEmpty()) {
/* 27 */       Enumeration<?> enumProperties = properties.propertyNames();
/* 28 */       while (enumProperties.hasMoreElements()) {
/* 29 */         String name = (String)enumProperties.nextElement();
/* 30 */         jo.put(name, properties.getProperty(name));
/*    */       } 
/*    */     } 
/* 33 */     return jo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Properties toProperties(JSONObject jo) throws JSONException {
/* 43 */     Properties properties = new Properties();
/* 44 */     if (jo != null)
/*    */     {
/* 46 */       for (String key : jo.keySet()) {
/* 47 */         Object value = jo.opt(key);
/* 48 */         if (!JSONObject.NULL.equals(value)) {
/* 49 */           properties.put(key, value.toString());
/*    */         }
/*    */       } 
/*    */     }
/* 53 */     return properties;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */