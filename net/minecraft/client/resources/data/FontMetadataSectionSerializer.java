/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class FontMetadataSectionSerializer
/*    */   extends BaseMetadataSectionSerializer<FontMetadataSection>
/*    */ {
/*    */   public FontMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 15 */     JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 16 */     float[] afloat = new float[256];
/* 17 */     float[] afloat1 = new float[256];
/* 18 */     float[] afloat2 = new float[256];
/* 19 */     float f = 1.0F;
/* 20 */     float f1 = 0.0F;
/* 21 */     float f2 = 0.0F;
/*    */     
/* 23 */     if (jsonobject.has("characters")) {
/* 24 */       if (!jsonobject.get("characters").isJsonObject()) {
/* 25 */         throw new JsonParseException("Invalid font->characters: expected object, was " + jsonobject.get("characters"));
/*    */       }
/*    */       
/* 28 */       JsonObject jsonobject1 = jsonobject.getAsJsonObject("characters");
/*    */       
/* 30 */       if (jsonobject1.has("default")) {
/* 31 */         if (!jsonobject1.get("default").isJsonObject()) {
/* 32 */           throw new JsonParseException("Invalid font->characters->default: expected object, was " + jsonobject1.get("default"));
/*    */         }
/*    */         
/* 35 */         JsonObject jsonobject2 = jsonobject1.getAsJsonObject("default");
/* 36 */         f = JsonUtils.getFloat(jsonobject2, "width", f);
/* 37 */         Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f, "Invalid default width");
/* 38 */         f1 = JsonUtils.getFloat(jsonobject2, "spacing", f1);
/* 39 */         Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f1, "Invalid default spacing");
/* 40 */         f2 = JsonUtils.getFloat(jsonobject2, "left", f1);
/* 41 */         Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f2, "Invalid default left");
/*    */       } 
/*    */       
/* 44 */       for (int i = 0; i < 256; i++) {
/* 45 */         JsonElement jsonelement = jsonobject1.get(Integer.toString(i));
/* 46 */         float f3 = f;
/* 47 */         float f4 = f1;
/* 48 */         float f5 = f2;
/*    */         
/* 50 */         if (jsonelement != null) {
/* 51 */           JsonObject jsonobject3 = JsonUtils.getJsonObject(jsonelement, "characters[" + i + "]");
/* 52 */           f3 = JsonUtils.getFloat(jsonobject3, "width", f);
/* 53 */           Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f3, "Invalid width");
/* 54 */           f4 = JsonUtils.getFloat(jsonobject3, "spacing", f1);
/* 55 */           Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f4, "Invalid spacing");
/* 56 */           f5 = JsonUtils.getFloat(jsonobject3, "left", f2);
/* 57 */           Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, f5, "Invalid left");
/*    */         } 
/*    */         
/* 60 */         afloat[i] = f3;
/* 61 */         afloat1[i] = f4;
/* 62 */         afloat2[i] = f5;
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     return new FontMetadataSection(afloat, afloat2, afloat1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSectionName() {
/* 73 */     return "font";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\FontMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */