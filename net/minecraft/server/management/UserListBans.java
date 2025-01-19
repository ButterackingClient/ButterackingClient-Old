/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ public class UserListBans
/*    */   extends UserList<GameProfile, UserListBansEntry> {
/*    */   public UserListBans(File bansFile) {
/* 10 */     super(bansFile);
/*    */   }
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 14 */     return new UserListBansEntry(entryData);
/*    */   }
/*    */   
/*    */   public boolean isBanned(GameProfile profile) {
/* 18 */     return hasEntry(profile);
/*    */   }
/*    */   
/*    */   public String[] getKeys() {
/* 22 */     String[] astring = new String[getValues().size()];
/* 23 */     int i = 0;
/*    */     
/* 25 */     for (UserListBansEntry userlistbansentry : getValues().values()) {
/* 26 */       astring[i++] = userlistbansentry.getValue().getName();
/*    */     }
/*    */     
/* 29 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 36 */     return obj.getId().toString();
/*    */   }
/*    */   
/*    */   public GameProfile isUsernameBanned(String username) {
/* 40 */     for (UserListBansEntry userlistbansentry : getValues().values()) {
/* 41 */       if (username.equalsIgnoreCase(userlistbansentry.getValue().getName())) {
/* 42 */         return userlistbansentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\UserListBans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */