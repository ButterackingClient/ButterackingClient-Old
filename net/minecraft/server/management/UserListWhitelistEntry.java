/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UserListWhitelistEntry
/*    */   extends UserListEntry<GameProfile> {
/*    */   public UserListWhitelistEntry(GameProfile profile) {
/* 10 */     super(profile);
/*    */   }
/*    */   
/*    */   public UserListWhitelistEntry(JsonObject json) {
/* 14 */     super(gameProfileFromJsonObject(json), json);
/*    */   }
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 18 */     if (getValue() != null) {
/* 19 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 20 */       data.addProperty("name", getValue().getName());
/* 21 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static GameProfile gameProfileFromJsonObject(JsonObject json) {
/* 26 */     if (json.has("uuid") && json.has("name")) {
/* 27 */       UUID uuid; String s = json.get("uuid").getAsString();
/*    */ 
/*    */       
/*    */       try {
/* 31 */         uuid = UUID.fromString(s);
/* 32 */       } catch (Throwable var4) {
/* 33 */         return null;
/*    */       } 
/*    */       
/* 36 */       return new GameProfile(uuid, json.get("name").getAsString());
/*    */     } 
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\UserListWhitelistEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */