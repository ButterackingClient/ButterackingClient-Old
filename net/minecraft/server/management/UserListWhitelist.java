/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ public class UserListWhitelist
/*    */   extends UserList<GameProfile, UserListWhitelistEntry> {
/*    */   public UserListWhitelist(File p_i1132_1_) {
/* 10 */     super(p_i1132_1_);
/*    */   }
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 14 */     return new UserListWhitelistEntry(entryData);
/*    */   }
/*    */   
/*    */   public String[] getKeys() {
/* 18 */     String[] astring = new String[getValues().size()];
/* 19 */     int i = 0;
/*    */     
/* 21 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values()) {
/* 22 */       astring[i++] = userlistwhitelistentry.getValue().getName();
/*    */     }
/*    */     
/* 25 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 32 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getBannedProfile(String name) {
/* 39 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values()) {
/* 40 */       if (name.equalsIgnoreCase(userlistwhitelistentry.getValue().getName())) {
/* 41 */         return userlistwhitelistentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\UserListWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */