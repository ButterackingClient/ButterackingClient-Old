/*     */ package net.minecraft.client.resources.data;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public class AnimationMetadataSectionSerializer
/*     */   extends BaseMetadataSectionSerializer<AnimationMetadataSection>
/*     */   implements JsonSerializer<AnimationMetadataSection> {
/*     */   public AnimationMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  21 */     List<AnimationFrame> list = Lists.newArrayList();
/*  22 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "metadata section");
/*  23 */     int i = JsonUtils.getInt(jsonobject, "frametime", 1);
/*     */     
/*  25 */     if (i != 1) {
/*  26 */       Validate.inclusiveBetween(1L, 2147483647L, i, "Invalid default frame time");
/*     */     }
/*     */     
/*  29 */     if (jsonobject.has("frames")) {
/*     */       try {
/*  31 */         JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "frames");
/*     */         
/*  33 */         for (int j = 0; j < jsonarray.size(); j++) {
/*  34 */           JsonElement jsonelement = jsonarray.get(j);
/*  35 */           AnimationFrame animationframe = parseAnimationFrame(j, jsonelement);
/*     */           
/*  37 */           if (animationframe != null) {
/*  38 */             list.add(animationframe);
/*     */           }
/*     */         } 
/*  41 */       } catch (ClassCastException classcastexception) {
/*  42 */         throw new JsonParseException("Invalid animation->frames: expected array, was " + jsonobject.get("frames"), classcastexception);
/*     */       } 
/*     */     }
/*     */     
/*  46 */     int k = JsonUtils.getInt(jsonobject, "width", -1);
/*  47 */     int l = JsonUtils.getInt(jsonobject, "height", -1);
/*     */     
/*  49 */     if (k != -1) {
/*  50 */       Validate.inclusiveBetween(1L, 2147483647L, k, "Invalid width");
/*     */     }
/*     */     
/*  53 */     if (l != -1) {
/*  54 */       Validate.inclusiveBetween(1L, 2147483647L, l, "Invalid height");
/*     */     }
/*     */     
/*  57 */     boolean flag = JsonUtils.getBoolean(jsonobject, "interpolate", false);
/*  58 */     return new AnimationMetadataSection(list, k, l, i, flag);
/*     */   }
/*     */   
/*     */   private AnimationFrame parseAnimationFrame(int p_110492_1_, JsonElement p_110492_2_) {
/*  62 */     if (p_110492_2_.isJsonPrimitive())
/*  63 */       return new AnimationFrame(JsonUtils.getInt(p_110492_2_, "frames[" + p_110492_1_ + "]")); 
/*  64 */     if (p_110492_2_.isJsonObject()) {
/*  65 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_110492_2_, "frames[" + p_110492_1_ + "]");
/*  66 */       int i = JsonUtils.getInt(jsonobject, "time", -1);
/*     */       
/*  68 */       if (jsonobject.has("time")) {
/*  69 */         Validate.inclusiveBetween(1L, 2147483647L, i, "Invalid frame time");
/*     */       }
/*     */       
/*  72 */       int j = JsonUtils.getInt(jsonobject, "index");
/*  73 */       Validate.inclusiveBetween(0L, 2147483647L, j, "Invalid frame index");
/*  74 */       return new AnimationFrame(j, i);
/*     */     } 
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement serialize(AnimationMetadataSection p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/*  81 */     JsonObject jsonobject = new JsonObject();
/*  82 */     jsonobject.addProperty("frametime", Integer.valueOf(p_serialize_1_.getFrameTime()));
/*     */     
/*  84 */     if (p_serialize_1_.getFrameWidth() != -1) {
/*  85 */       jsonobject.addProperty("width", Integer.valueOf(p_serialize_1_.getFrameWidth()));
/*     */     }
/*     */     
/*  88 */     if (p_serialize_1_.getFrameHeight() != -1) {
/*  89 */       jsonobject.addProperty("height", Integer.valueOf(p_serialize_1_.getFrameHeight()));
/*     */     }
/*     */     
/*  92 */     if (p_serialize_1_.getFrameCount() > 0) {
/*  93 */       JsonArray jsonarray = new JsonArray();
/*     */       
/*  95 */       for (int i = 0; i < p_serialize_1_.getFrameCount(); i++) {
/*  96 */         if (p_serialize_1_.frameHasTime(i)) {
/*  97 */           JsonObject jsonobject1 = new JsonObject();
/*  98 */           jsonobject1.addProperty("index", Integer.valueOf(p_serialize_1_.getFrameIndex(i)));
/*  99 */           jsonobject1.addProperty("time", Integer.valueOf(p_serialize_1_.getFrameTimeSingle(i)));
/* 100 */           jsonarray.add((JsonElement)jsonobject1);
/*     */         } else {
/* 102 */           jsonarray.add((JsonElement)new JsonPrimitive(Integer.valueOf(p_serialize_1_.getFrameIndex(i))));
/*     */         } 
/*     */       } 
/*     */       
/* 106 */       jsonobject.add("frames", (JsonElement)jsonarray);
/*     */     } 
/*     */     
/* 109 */     return (JsonElement)jsonobject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSectionName() {
/* 116 */     return "animation";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\AnimationMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */