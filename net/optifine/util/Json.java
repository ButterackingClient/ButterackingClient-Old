/*    */ package net.optifine.util;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ 
/*    */ public class Json {
/*    */   public static float getFloat(JsonObject obj, String field, float def) {
/* 10 */     JsonElement jsonelement = obj.get(field);
/* 11 */     return (jsonelement == null) ? def : jsonelement.getAsFloat();
/*    */   }
/*    */   
/*    */   public static boolean getBoolean(JsonObject obj, String field, boolean def) {
/* 15 */     JsonElement jsonelement = obj.get(field);
/* 16 */     return (jsonelement == null) ? def : jsonelement.getAsBoolean();
/*    */   }
/*    */   
/*    */   public static String getString(JsonObject jsonObj, String field) {
/* 20 */     return getString(jsonObj, field, null);
/*    */   }
/*    */   
/*    */   public static String getString(JsonObject jsonObj, String field, String def) {
/* 24 */     JsonElement jsonelement = jsonObj.get(field);
/* 25 */     return (jsonelement == null) ? def : jsonelement.getAsString();
/*    */   }
/*    */   
/*    */   public static float[] parseFloatArray(JsonElement jsonElement, int len) {
/* 29 */     return parseFloatArray(jsonElement, len, null);
/*    */   }
/*    */   
/*    */   public static float[] parseFloatArray(JsonElement jsonElement, int len, float[] def) {
/* 33 */     if (jsonElement == null) {
/* 34 */       return def;
/*    */     }
/* 36 */     JsonArray jsonarray = jsonElement.getAsJsonArray();
/*    */     
/* 38 */     if (jsonarray.size() != len) {
/* 39 */       throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + len + ", array: " + jsonarray);
/*    */     }
/* 41 */     float[] afloat = new float[jsonarray.size()];
/*    */     
/* 43 */     for (int i = 0; i < afloat.length; i++) {
/* 44 */       afloat[i] = jsonarray.get(i).getAsFloat();
/*    */     }
/*    */     
/* 47 */     return afloat;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int[] parseIntArray(JsonElement jsonElement, int len) {
/* 53 */     return parseIntArray(jsonElement, len, null);
/*    */   }
/*    */   
/*    */   public static int[] parseIntArray(JsonElement jsonElement, int len, int[] def) {
/* 57 */     if (jsonElement == null) {
/* 58 */       return def;
/*    */     }
/* 60 */     JsonArray jsonarray = jsonElement.getAsJsonArray();
/*    */     
/* 62 */     if (jsonarray.size() != len) {
/* 63 */       throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + len + ", array: " + jsonarray);
/*    */     }
/* 65 */     int[] aint = new int[jsonarray.size()];
/*    */     
/* 67 */     for (int i = 0; i < aint.length; i++) {
/* 68 */       aint[i] = jsonarray.get(i).getAsInt();
/*    */     }
/*    */     
/* 71 */     return aint;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\Json.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */