/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.ForwardingSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class JsonSerializableSet
/*    */   extends ForwardingSet<String> implements IJsonSerializable {
/* 12 */   private final Set<String> underlyingSet = Sets.newHashSet();
/*    */   
/*    */   public void fromJson(JsonElement json) {
/* 15 */     if (json.isJsonArray()) {
/* 16 */       for (JsonElement jsonelement : json.getAsJsonArray()) {
/* 17 */         add(jsonelement.getAsString());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement getSerializableElement() {
/* 26 */     JsonArray jsonarray = new JsonArray();
/*    */     
/* 28 */     for (String s : this) {
/* 29 */       jsonarray.add((JsonElement)new JsonPrimitive(s));
/*    */     }
/*    */     
/* 32 */     return (JsonElement)jsonarray;
/*    */   }
/*    */   
/*    */   protected Set<String> delegate() {
/* 36 */     return this.underlyingSet;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\JsonSerializableSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */