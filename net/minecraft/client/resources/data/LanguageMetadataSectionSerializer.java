/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.resources.Language;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class LanguageMetadataSectionSerializer
/*    */   extends BaseMetadataSectionSerializer<LanguageMetadataSection>
/*    */ {
/*    */   public LanguageMetadataSection deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 18 */     JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 19 */     Set<Language> set = Sets.newHashSet();
/*    */     
/* 21 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/* 22 */       String s = entry.getKey();
/* 23 */       JsonObject jsonobject1 = JsonUtils.getJsonObject(entry.getValue(), "language");
/* 24 */       String s1 = JsonUtils.getString(jsonobject1, "region");
/* 25 */       String s2 = JsonUtils.getString(jsonobject1, "name");
/* 26 */       boolean flag = JsonUtils.getBoolean(jsonobject1, "bidirectional", false);
/*    */       
/* 28 */       if (s1.isEmpty()) {
/* 29 */         throw new JsonParseException("Invalid language->'" + s + "'->region: empty value");
/*    */       }
/*    */       
/* 32 */       if (s2.isEmpty()) {
/* 33 */         throw new JsonParseException("Invalid language->'" + s + "'->name: empty value");
/*    */       }
/*    */       
/* 36 */       if (!set.add(new Language(s, s1, s2, flag))) {
/* 37 */         throw new JsonParseException("Duplicate language->'" + s + "' defined");
/*    */       }
/*    */     } 
/*    */     
/* 41 */     return new LanguageMetadataSection(set);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSectionName() {
/* 48 */     return "language";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\LanguageMetadataSectionSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */