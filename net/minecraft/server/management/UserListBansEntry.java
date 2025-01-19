/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UserListBansEntry
/*    */   extends BanEntry<GameProfile> {
/*    */   public UserListBansEntry(GameProfile profile) {
/* 11 */     this(profile, null, null, null, null);
/*    */   }
/*    */   
/*    */   public UserListBansEntry(GameProfile profile, Date startDate, String banner, Date endDate, String banReason) {
/* 15 */     super(profile, endDate, banner, endDate, banReason);
/*    */   }
/*    */   
/*    */   public UserListBansEntry(JsonObject json) {
/* 19 */     super(toGameProfile(json), json);
/*    */   }
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 23 */     if (getValue() != null) {
/* 24 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 25 */       data.addProperty("name", getValue().getName());
/* 26 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static GameProfile toGameProfile(JsonObject json) {
/* 35 */     if (json.has("uuid") && json.has("name")) {
/* 36 */       UUID uuid; String s = json.get("uuid").getAsString();
/*    */ 
/*    */       
/*    */       try {
/* 40 */         uuid = UUID.fromString(s);
/* 41 */       } catch (Throwable var4) {
/* 42 */         return null;
/*    */       } 
/*    */       
/* 45 */       return new GameProfile(uuid, json.get("name").getAsString());
/*    */     } 
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\UserListBansEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */