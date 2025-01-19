/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class IPBanEntry
/*    */   extends BanEntry<String> {
/*    */   public IPBanEntry(String valueIn) {
/*  9 */     this(valueIn, null, null, null, null);
/*    */   }
/*    */   
/*    */   public IPBanEntry(String valueIn, Date startDate, String banner, Date endDate, String banReason) {
/* 13 */     super(valueIn, startDate, banner, endDate, banReason);
/*    */   }
/*    */   
/*    */   public IPBanEntry(JsonObject json) {
/* 17 */     super(getIPFromJson(json), json);
/*    */   }
/*    */   
/*    */   private static String getIPFromJson(JsonObject json) {
/* 21 */     return json.has("ip") ? json.get("ip").getAsString() : null;
/*    */   }
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 25 */     if (getValue() != null) {
/* 26 */       data.addProperty("ip", getValue());
/* 27 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\IPBanEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */