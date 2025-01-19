/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.File;
/*    */ import java.net.SocketAddress;
/*    */ 
/*    */ public class BanList
/*    */   extends UserList<String, IPBanEntry> {
/*    */   public BanList(File bansFile) {
/* 10 */     super(bansFile);
/*    */   }
/*    */   
/*    */   protected UserListEntry<String> createEntry(JsonObject entryData) {
/* 14 */     return new IPBanEntry(entryData);
/*    */   }
/*    */   
/*    */   public boolean isBanned(SocketAddress address) {
/* 18 */     String s = addressToString(address);
/* 19 */     return hasEntry(s);
/*    */   }
/*    */   
/*    */   public IPBanEntry getBanEntry(SocketAddress address) {
/* 23 */     String s = addressToString(address);
/* 24 */     return getEntry(s);
/*    */   }
/*    */   
/*    */   private String addressToString(SocketAddress address) {
/* 28 */     String s = address.toString();
/*    */     
/* 30 */     if (s.contains("/")) {
/* 31 */       s = s.substring(s.indexOf('/') + 1);
/*    */     }
/*    */     
/* 34 */     if (s.contains(":")) {
/* 35 */       s = s.substring(0, s.indexOf(':'));
/*    */     }
/*    */     
/* 38 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\BanList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */