/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IChatComponent
/*     */   extends Iterable<IChatComponent>
/*     */ {
/*     */   IChatComponent setChatStyle(ChatStyle paramChatStyle);
/*     */   
/*     */   ChatStyle getChatStyle();
/*     */   
/*     */   IChatComponent appendText(String paramString);
/*     */   
/*     */   IChatComponent appendSibling(IChatComponent paramIChatComponent);
/*     */   
/*     */   String getUnformattedTextForChat();
/*     */   
/*     */   String getUnformattedText();
/*     */   
/*     */   String getFormattedText();
/*     */   
/*     */   List<IChatComponent> getSiblings();
/*     */   
/*     */   IChatComponent createCopy();
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<IChatComponent>, JsonSerializer<IChatComponent>
/*     */   {
/*     */     private static final Gson GSON;
/*     */     
/*     */     public IChatComponent deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*     */       IChatComponent ichatcomponent;
/*  61 */       if (p_deserialize_1_.isJsonPrimitive())
/*  62 */         return new ChatComponentText(p_deserialize_1_.getAsString()); 
/*  63 */       if (!p_deserialize_1_.isJsonObject()) {
/*  64 */         if (p_deserialize_1_.isJsonArray()) {
/*  65 */           JsonArray jsonarray1 = p_deserialize_1_.getAsJsonArray();
/*  66 */           IChatComponent ichatcomponent1 = null;
/*     */           
/*  68 */           for (JsonElement jsonelement : jsonarray1) {
/*  69 */             IChatComponent ichatcomponent2 = deserialize(jsonelement, jsonelement.getClass(), p_deserialize_3_);
/*     */             
/*  71 */             if (ichatcomponent1 == null) {
/*  72 */               ichatcomponent1 = ichatcomponent2; continue;
/*     */             } 
/*  74 */             ichatcomponent1.appendSibling(ichatcomponent2);
/*     */           } 
/*     */ 
/*     */           
/*  78 */           return ichatcomponent1;
/*     */         } 
/*  80 */         throw new JsonParseException("Don't know how to turn " + p_deserialize_1_.toString() + " into a Component");
/*     */       } 
/*     */       
/*  83 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*     */ 
/*     */       
/*  86 */       if (jsonobject.has("text")) {
/*  87 */         ichatcomponent = new ChatComponentText(jsonobject.get("text").getAsString());
/*  88 */       } else if (jsonobject.has("translate")) {
/*  89 */         String s = jsonobject.get("translate").getAsString();
/*     */         
/*  91 */         if (jsonobject.has("with")) {
/*  92 */           JsonArray jsonarray = jsonobject.getAsJsonArray("with");
/*  93 */           Object[] aobject = new Object[jsonarray.size()];
/*     */           
/*  95 */           for (int i = 0; i < aobject.length; i++) {
/*  96 */             aobject[i] = deserialize(jsonarray.get(i), p_deserialize_2_, p_deserialize_3_);
/*     */             
/*  98 */             if (aobject[i] instanceof ChatComponentText) {
/*  99 */               ChatComponentText chatcomponenttext = (ChatComponentText)aobject[i];
/*     */               
/* 101 */               if (chatcomponenttext.getChatStyle().isEmpty() && chatcomponenttext.getSiblings().isEmpty()) {
/* 102 */                 aobject[i] = chatcomponenttext.getChatComponentText_TextValue();
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 107 */           ichatcomponent = new ChatComponentTranslation(s, aobject);
/*     */         } else {
/* 109 */           ichatcomponent = new ChatComponentTranslation(s, new Object[0]);
/*     */         } 
/* 111 */       } else if (jsonobject.has("score")) {
/* 112 */         JsonObject jsonobject1 = jsonobject.getAsJsonObject("score");
/*     */         
/* 114 */         if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
/* 115 */           throw new JsonParseException("A score component needs a least a name and an objective");
/*     */         }
/*     */         
/* 118 */         ichatcomponent = new ChatComponentScore(JsonUtils.getString(jsonobject1, "name"), JsonUtils.getString(jsonobject1, "objective"));
/*     */         
/* 120 */         if (jsonobject1.has("value")) {
/* 121 */           ((ChatComponentScore)ichatcomponent).setValue(JsonUtils.getString(jsonobject1, "value"));
/*     */         }
/*     */       } else {
/* 124 */         if (!jsonobject.has("selector")) {
/* 125 */           throw new JsonParseException("Don't know how to turn " + p_deserialize_1_.toString() + " into a Component");
/*     */         }
/*     */         
/* 128 */         ichatcomponent = new ChatComponentSelector(JsonUtils.getString(jsonobject, "selector"));
/*     */       } 
/*     */       
/* 131 */       if (jsonobject.has("extra")) {
/* 132 */         JsonArray jsonarray2 = jsonobject.getAsJsonArray("extra");
/*     */         
/* 134 */         if (jsonarray2.size() <= 0) {
/* 135 */           throw new JsonParseException("Unexpected empty array of components");
/*     */         }
/*     */         
/* 138 */         for (int j = 0; j < jsonarray2.size(); j++) {
/* 139 */           ichatcomponent.appendSibling(deserialize(jsonarray2.get(j), p_deserialize_2_, p_deserialize_3_));
/*     */         }
/*     */       } 
/*     */       
/* 143 */       ichatcomponent.setChatStyle((ChatStyle)p_deserialize_3_.deserialize(p_deserialize_1_, ChatStyle.class));
/* 144 */       return ichatcomponent;
/*     */     }
/*     */ 
/*     */     
/*     */     private void serializeChatStyle(ChatStyle style, JsonObject object, JsonSerializationContext ctx) {
/* 149 */       JsonElement jsonelement = ctx.serialize(style);
/*     */       
/* 151 */       if (jsonelement.isJsonObject()) {
/* 152 */         JsonObject jsonobject = (JsonObject)jsonelement;
/*     */         
/* 154 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/* 155 */           object.add(entry.getKey(), entry.getValue());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public JsonElement serialize(IChatComponent p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 161 */       if (p_serialize_1_ instanceof ChatComponentText && p_serialize_1_.getChatStyle().isEmpty() && p_serialize_1_.getSiblings().isEmpty()) {
/* 162 */         return (JsonElement)new JsonPrimitive(((ChatComponentText)p_serialize_1_).getChatComponentText_TextValue());
/*     */       }
/* 164 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 166 */       if (!p_serialize_1_.getChatStyle().isEmpty()) {
/* 167 */         serializeChatStyle(p_serialize_1_.getChatStyle(), jsonobject, p_serialize_3_);
/*     */       }
/*     */       
/* 170 */       if (!p_serialize_1_.getSiblings().isEmpty()) {
/* 171 */         JsonArray jsonarray = new JsonArray();
/*     */         
/* 173 */         for (IChatComponent ichatcomponent : p_serialize_1_.getSiblings()) {
/* 174 */           jsonarray.add(serialize(ichatcomponent, ichatcomponent.getClass(), p_serialize_3_));
/*     */         }
/*     */         
/* 177 */         jsonobject.add("extra", (JsonElement)jsonarray);
/*     */       } 
/*     */       
/* 180 */       if (p_serialize_1_ instanceof ChatComponentText) {
/* 181 */         jsonobject.addProperty("text", ((ChatComponentText)p_serialize_1_).getChatComponentText_TextValue());
/* 182 */       } else if (p_serialize_1_ instanceof ChatComponentTranslation) {
/* 183 */         ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation)p_serialize_1_;
/* 184 */         jsonobject.addProperty("translate", chatcomponenttranslation.getKey());
/*     */         
/* 186 */         if (chatcomponenttranslation.getFormatArgs() != null && (chatcomponenttranslation.getFormatArgs()).length > 0) {
/* 187 */           JsonArray jsonarray1 = new JsonArray(); byte b; int i;
/*     */           Object[] arrayOfObject;
/* 189 */           for (i = (arrayOfObject = chatcomponenttranslation.getFormatArgs()).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/* 190 */             if (object instanceof IChatComponent) {
/* 191 */               jsonarray1.add(serialize((IChatComponent)object, object.getClass(), p_serialize_3_));
/*     */             } else {
/* 193 */               jsonarray1.add((JsonElement)new JsonPrimitive(String.valueOf(object)));
/*     */             } 
/*     */             b++; }
/*     */           
/* 197 */           jsonobject.add("with", (JsonElement)jsonarray1);
/*     */         } 
/* 199 */       } else if (p_serialize_1_ instanceof ChatComponentScore) {
/* 200 */         ChatComponentScore chatcomponentscore = (ChatComponentScore)p_serialize_1_;
/* 201 */         JsonObject jsonobject1 = new JsonObject();
/* 202 */         jsonobject1.addProperty("name", chatcomponentscore.getName());
/* 203 */         jsonobject1.addProperty("objective", chatcomponentscore.getObjective());
/* 204 */         jsonobject1.addProperty("value", chatcomponentscore.getUnformattedTextForChat());
/* 205 */         jsonobject.add("score", (JsonElement)jsonobject1);
/*     */       } else {
/* 207 */         if (!(p_serialize_1_ instanceof ChatComponentSelector)) {
/* 208 */           throw new IllegalArgumentException("Don't know how to serialize " + p_serialize_1_ + " as a Component");
/*     */         }
/*     */         
/* 211 */         ChatComponentSelector chatcomponentselector = (ChatComponentSelector)p_serialize_1_;
/* 212 */         jsonobject.addProperty("selector", chatcomponentselector.getSelector());
/*     */       } 
/*     */       
/* 215 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public static String componentToJson(IChatComponent component) {
/* 220 */       return GSON.toJson(component);
/*     */     }
/*     */     
/*     */     public static IChatComponent jsonToComponent(String json) {
/* 224 */       return (IChatComponent)GSON.fromJson(json, IChatComponent.class);
/*     */     }
/*     */     
/*     */     static {
/* 228 */       GsonBuilder gsonbuilder = new GsonBuilder();
/* 229 */       gsonbuilder.registerTypeHierarchyAdapter(IChatComponent.class, new Serializer());
/* 230 */       gsonbuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
/* 231 */       gsonbuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
/* 232 */       GSON = gsonbuilder.create();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\IChatComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */