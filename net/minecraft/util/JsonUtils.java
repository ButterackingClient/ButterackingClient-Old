/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ public class JsonUtils
/*     */ {
/*     */   public static boolean isString(JsonObject p_151205_0_, String p_151205_1_) {
/*  14 */     return !isJsonPrimitive(p_151205_0_, p_151205_1_) ? false : p_151205_0_.getAsJsonPrimitive(p_151205_1_).isString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isString(JsonElement p_151211_0_) {
/*  21 */     return !p_151211_0_.isJsonPrimitive() ? false : p_151211_0_.getAsJsonPrimitive().isString();
/*     */   }
/*     */   
/*     */   public static boolean isBoolean(JsonObject p_180199_0_, String p_180199_1_) {
/*  25 */     return !isJsonPrimitive(p_180199_0_, p_180199_1_) ? false : p_180199_0_.getAsJsonPrimitive(p_180199_1_).isBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJsonArray(JsonObject p_151202_0_, String p_151202_1_) {
/*  32 */     return !hasField(p_151202_0_, p_151202_1_) ? false : p_151202_0_.get(p_151202_1_).isJsonArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJsonPrimitive(JsonObject p_151201_0_, String p_151201_1_) {
/*  40 */     return !hasField(p_151201_0_, p_151201_1_) ? false : p_151201_0_.get(p_151201_1_).isJsonPrimitive();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasField(JsonObject p_151204_0_, String p_151204_1_) {
/*  47 */     return (p_151204_0_ == null) ? false : ((p_151204_0_.get(p_151204_1_) != null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonElement p_151206_0_, String p_151206_1_) {
/*  55 */     if (p_151206_0_.isJsonPrimitive()) {
/*  56 */       return p_151206_0_.getAsString();
/*     */     }
/*  58 */     throw new JsonSyntaxException("Expected " + p_151206_1_ + " to be a string, was " + toString(p_151206_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject p_151200_0_, String p_151200_1_) {
/*  66 */     if (p_151200_0_.has(p_151200_1_)) {
/*  67 */       return getString(p_151200_0_.get(p_151200_1_), p_151200_1_);
/*     */     }
/*  69 */     throw new JsonSyntaxException("Missing " + p_151200_1_ + ", expected to find a string");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getString(JsonObject p_151219_0_, String p_151219_1_, String p_151219_2_) {
/*  78 */     return p_151219_0_.has(p_151219_1_) ? getString(p_151219_0_.get(p_151219_1_), p_151219_1_) : p_151219_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonElement p_151216_0_, String p_151216_1_) {
/*  86 */     if (p_151216_0_.isJsonPrimitive()) {
/*  87 */       return p_151216_0_.getAsBoolean();
/*     */     }
/*  89 */     throw new JsonSyntaxException("Expected " + p_151216_1_ + " to be a Boolean, was " + toString(p_151216_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonObject p_151212_0_, String p_151212_1_) {
/*  97 */     if (p_151212_0_.has(p_151212_1_)) {
/*  98 */       return getBoolean(p_151212_0_.get(p_151212_1_), p_151212_1_);
/*     */     }
/* 100 */     throw new JsonSyntaxException("Missing " + p_151212_1_ + ", expected to find a Boolean");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getBoolean(JsonObject p_151209_0_, String p_151209_1_, boolean p_151209_2_) {
/* 109 */     return p_151209_0_.has(p_151209_1_) ? getBoolean(p_151209_0_.get(p_151209_1_), p_151209_1_) : p_151209_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonElement p_151220_0_, String p_151220_1_) {
/* 117 */     if (p_151220_0_.isJsonPrimitive() && p_151220_0_.getAsJsonPrimitive().isNumber()) {
/* 118 */       return p_151220_0_.getAsFloat();
/*     */     }
/* 120 */     throw new JsonSyntaxException("Expected " + p_151220_1_ + " to be a Float, was " + toString(p_151220_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonObject p_151217_0_, String p_151217_1_) {
/* 128 */     if (p_151217_0_.has(p_151217_1_)) {
/* 129 */       return getFloat(p_151217_0_.get(p_151217_1_), p_151217_1_);
/*     */     }
/* 131 */     throw new JsonSyntaxException("Missing " + p_151217_1_ + ", expected to find a Float");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getFloat(JsonObject p_151221_0_, String p_151221_1_, float p_151221_2_) {
/* 140 */     return p_151221_0_.has(p_151221_1_) ? getFloat(p_151221_0_.get(p_151221_1_), p_151221_1_) : p_151221_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonElement p_151215_0_, String p_151215_1_) {
/* 148 */     if (p_151215_0_.isJsonPrimitive() && p_151215_0_.getAsJsonPrimitive().isNumber()) {
/* 149 */       return p_151215_0_.getAsInt();
/*     */     }
/* 151 */     throw new JsonSyntaxException("Expected " + p_151215_1_ + " to be a Int, was " + toString(p_151215_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonObject p_151203_0_, String p_151203_1_) {
/* 159 */     if (p_151203_0_.has(p_151203_1_)) {
/* 160 */       return getInt(p_151203_0_.get(p_151203_1_), p_151203_1_);
/*     */     }
/* 162 */     throw new JsonSyntaxException("Missing " + p_151203_1_ + ", expected to find a Int");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(JsonObject p_151208_0_, String p_151208_1_, int p_151208_2_) {
/* 171 */     return p_151208_0_.has(p_151208_1_) ? getInt(p_151208_0_.get(p_151208_1_), p_151208_1_) : p_151208_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonElement p_151210_0_, String p_151210_1_) {
/* 179 */     if (p_151210_0_.isJsonObject()) {
/* 180 */       return p_151210_0_.getAsJsonObject();
/*     */     }
/* 182 */     throw new JsonSyntaxException("Expected " + p_151210_1_ + " to be a JsonObject, was " + toString(p_151210_0_));
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonObject base, String key) {
/* 187 */     if (base.has(key)) {
/* 188 */       return getJsonObject(base.get(key), key);
/*     */     }
/* 190 */     throw new JsonSyntaxException("Missing " + key + ", expected to find a JsonObject");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject getJsonObject(JsonObject p_151218_0_, String p_151218_1_, JsonObject p_151218_2_) {
/* 199 */     return p_151218_0_.has(p_151218_1_) ? getJsonObject(p_151218_0_.get(p_151218_1_), p_151218_1_) : p_151218_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonElement p_151207_0_, String p_151207_1_) {
/* 207 */     if (p_151207_0_.isJsonArray()) {
/* 208 */       return p_151207_0_.getAsJsonArray();
/*     */     }
/* 210 */     throw new JsonSyntaxException("Expected " + p_151207_1_ + " to be a JsonArray, was " + toString(p_151207_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonObject p_151214_0_, String p_151214_1_) {
/* 218 */     if (p_151214_0_.has(p_151214_1_)) {
/* 219 */       return getJsonArray(p_151214_0_.get(p_151214_1_), p_151214_1_);
/*     */     }
/* 221 */     throw new JsonSyntaxException("Missing " + p_151214_1_ + ", expected to find a JsonArray");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonArray getJsonArray(JsonObject p_151213_0_, String p_151213_1_, JsonArray p_151213_2_) {
/* 230 */     return p_151213_0_.has(p_151213_1_) ? getJsonArray(p_151213_0_.get(p_151213_1_), p_151213_1_) : p_151213_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(JsonElement p_151222_0_) {
/* 237 */     String s = StringUtils.abbreviateMiddle(String.valueOf(p_151222_0_), "...", 10);
/*     */     
/* 239 */     if (p_151222_0_ == null)
/* 240 */       return "null (missing)"; 
/* 241 */     if (p_151222_0_.isJsonNull())
/* 242 */       return "null (json)"; 
/* 243 */     if (p_151222_0_.isJsonArray())
/* 244 */       return "an array (" + s + ")"; 
/* 245 */     if (p_151222_0_.isJsonObject()) {
/* 246 */       return "an object (" + s + ")";
/*     */     }
/* 248 */     if (p_151222_0_.isJsonPrimitive()) {
/* 249 */       JsonPrimitive jsonprimitive = p_151222_0_.getAsJsonPrimitive();
/*     */       
/* 251 */       if (jsonprimitive.isNumber()) {
/* 252 */         return "a number (" + s + ")";
/*     */       }
/*     */       
/* 255 */       if (jsonprimitive.isBoolean()) {
/* 256 */         return "a boolean (" + s + ")";
/*     */       }
/*     */     } 
/*     */     
/* 260 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\JsonUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */