/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class SoundListSerializer
/*    */   implements JsonDeserializer<SoundList>
/*    */ {
/*    */   public SoundList deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 17 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "entry");
/* 18 */     SoundList soundlist = new SoundList();
/* 19 */     soundlist.setReplaceExisting(JsonUtils.getBoolean(jsonobject, "replace", false));
/* 20 */     SoundCategory soundcategory = SoundCategory.getCategory(JsonUtils.getString(jsonobject, "category", SoundCategory.MASTER.getCategoryName()));
/* 21 */     soundlist.setSoundCategory(soundcategory);
/* 22 */     Validate.notNull(soundcategory, "Invalid category", new Object[0]);
/*    */     
/* 24 */     if (jsonobject.has("sounds")) {
/* 25 */       JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "sounds");
/*    */       
/* 27 */       for (int i = 0; i < jsonarray.size(); i++) {
/* 28 */         JsonElement jsonelement = jsonarray.get(i);
/* 29 */         SoundList.SoundEntry soundlist$soundentry = new SoundList.SoundEntry();
/*    */         
/* 31 */         if (JsonUtils.isString(jsonelement)) {
/* 32 */           soundlist$soundentry.setSoundEntryName(JsonUtils.getString(jsonelement, "sound"));
/*    */         } else {
/* 34 */           JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement, "sound");
/* 35 */           soundlist$soundentry.setSoundEntryName(JsonUtils.getString(jsonobject1, "name"));
/*    */           
/* 37 */           if (jsonobject1.has("type")) {
/* 38 */             SoundList.SoundEntry.Type soundlist$soundentry$type = SoundList.SoundEntry.Type.getType(JsonUtils.getString(jsonobject1, "type"));
/* 39 */             Validate.notNull(soundlist$soundentry$type, "Invalid type", new Object[0]);
/* 40 */             soundlist$soundentry.setSoundEntryType(soundlist$soundentry$type);
/*    */           } 
/*    */           
/* 43 */           if (jsonobject1.has("volume")) {
/* 44 */             float f = JsonUtils.getFloat(jsonobject1, "volume");
/* 45 */             Validate.isTrue((f > 0.0F), "Invalid volume", new Object[0]);
/* 46 */             soundlist$soundentry.setSoundEntryVolume(f);
/*    */           } 
/*    */           
/* 49 */           if (jsonobject1.has("pitch")) {
/* 50 */             float f1 = JsonUtils.getFloat(jsonobject1, "pitch");
/* 51 */             Validate.isTrue((f1 > 0.0F), "Invalid pitch", new Object[0]);
/* 52 */             soundlist$soundentry.setSoundEntryPitch(f1);
/*    */           } 
/*    */           
/* 55 */           if (jsonobject1.has("weight")) {
/* 56 */             int j = JsonUtils.getInt(jsonobject1, "weight");
/* 57 */             Validate.isTrue((j > 0), "Invalid weight", new Object[0]);
/* 58 */             soundlist$soundentry.setSoundEntryWeight(j);
/*    */           } 
/*    */           
/* 61 */           if (jsonobject1.has("stream")) {
/* 62 */             soundlist$soundentry.setStreaming(JsonUtils.getBoolean(jsonobject1, "stream"));
/*    */           }
/*    */         } 
/*    */         
/* 66 */         soundlist.getSoundList().add(soundlist$soundentry);
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     return soundlist;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\SoundListSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */