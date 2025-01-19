/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UserListOpsEntry
/*    */   extends UserListEntry<GameProfile> {
/*    */   private final int permissionLevel;
/*    */   private final boolean bypassesPlayerLimit;
/*    */   
/*    */   public UserListOpsEntry(GameProfile player, int permissionLevelIn, boolean bypassesPlayerLimitIn) {
/* 13 */     super(player);
/* 14 */     this.permissionLevel = permissionLevelIn;
/* 15 */     this.bypassesPlayerLimit = bypassesPlayerLimitIn;
/*    */   }
/*    */   
/*    */   public UserListOpsEntry(JsonObject p_i1150_1_) {
/* 19 */     super(constructProfile(p_i1150_1_), p_i1150_1_);
/* 20 */     this.permissionLevel = p_i1150_1_.has("level") ? p_i1150_1_.get("level").getAsInt() : 0;
/* 21 */     this.bypassesPlayerLimit = (p_i1150_1_.has("bypassesPlayerLimit") && p_i1150_1_.get("bypassesPlayerLimit").getAsBoolean());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPermissionLevel() {
/* 28 */     return this.permissionLevel;
/*    */   }
/*    */   
/*    */   public boolean bypassesPlayerLimit() {
/* 32 */     return this.bypassesPlayerLimit;
/*    */   }
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 36 */     if (getValue() != null) {
/* 37 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 38 */       data.addProperty("name", getValue().getName());
/* 39 */       super.onSerialization(data);
/* 40 */       data.addProperty("level", Integer.valueOf(this.permissionLevel));
/* 41 */       data.addProperty("bypassesPlayerLimit", Boolean.valueOf(this.bypassesPlayerLimit));
/*    */     } 
/*    */   }
/*    */   
/*    */   private static GameProfile constructProfile(JsonObject p_152643_0_) {
/* 46 */     if (p_152643_0_.has("uuid") && p_152643_0_.has("name")) {
/* 47 */       UUID uuid; String s = p_152643_0_.get("uuid").getAsString();
/*    */ 
/*    */       
/*    */       try {
/* 51 */         uuid = UUID.fromString(s);
/* 52 */       } catch (Throwable var4) {
/* 53 */         return null;
/*    */       } 
/*    */       
/* 56 */       return new GameProfile(uuid, p_152643_0_.get("name").getAsString());
/*    */     } 
/* 58 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\UserListOpsEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */