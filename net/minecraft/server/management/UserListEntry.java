/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ 
/*    */ public class UserListEntry<T> {
/*    */   private final T value;
/*    */   
/*    */   public UserListEntry(T valueIn) {
/*  9 */     this.value = valueIn;
/*    */   }
/*    */   
/*    */   protected UserListEntry(T valueIn, JsonObject json) {
/* 13 */     this.value = valueIn;
/*    */   }
/*    */   
/*    */   T getValue() {
/* 17 */     return this.value;
/*    */   }
/*    */   
/*    */   boolean hasBanExpired() {
/* 21 */     return false;
/*    */   }
/*    */   
/*    */   protected void onSerialization(JsonObject data) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\UserListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */