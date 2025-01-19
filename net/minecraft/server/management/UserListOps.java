/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ public class UserListOps
/*    */   extends UserList<GameProfile, UserListOpsEntry> {
/*    */   public UserListOps(File saveFile) {
/* 10 */     super(saveFile);
/*    */   }
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 14 */     return new UserListOpsEntry(entryData);
/*    */   }
/*    */   
/*    */   public String[] getKeys() {
/* 18 */     String[] astring = new String[getValues().size()];
/* 19 */     int i = 0;
/*    */     
/* 21 */     for (UserListOpsEntry userlistopsentry : getValues().values()) {
/* 22 */       astring[i++] = userlistopsentry.getValue().getName();
/*    */     }
/*    */     
/* 25 */     return astring;
/*    */   }
/*    */   
/*    */   public boolean bypassesPlayerLimit(GameProfile profile) {
/* 29 */     UserListOpsEntry userlistopsentry = getEntry(profile);
/* 30 */     return (userlistopsentry != null) ? userlistopsentry.bypassesPlayerLimit() : false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 37 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getGameProfileFromName(String username) {
/* 44 */     for (UserListOpsEntry userlistopsentry : getValues().values()) {
/* 45 */       if (username.equalsIgnoreCase(userlistopsentry.getValue().getName())) {
/* 46 */         return userlistopsentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\UserListOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */