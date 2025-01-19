/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public abstract class BanEntry<T>
/*    */   extends UserListEntry<T> {
/* 10 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*    */   protected final Date banStartDate;
/*    */   protected final String bannedBy;
/*    */   protected final Date banEndDate;
/*    */   protected final String reason;
/*    */   
/*    */   public BanEntry(T valueIn, Date startDate, String banner, Date endDate, String banReason) {
/* 17 */     super(valueIn);
/* 18 */     this.banStartDate = (startDate == null) ? new Date() : startDate;
/* 19 */     this.bannedBy = (banner == null) ? "(Unknown)" : banner;
/* 20 */     this.banEndDate = endDate;
/* 21 */     this.reason = (banReason == null) ? "Banned by an operator." : banReason;
/*    */   }
/*    */   
/*    */   protected BanEntry(T valueIn, JsonObject json) {
/* 25 */     super(valueIn, json);
/*    */     
/*    */     Date date, date1;
/*    */     try {
/* 29 */       date = json.has("created") ? dateFormat.parse(json.get("created").getAsString()) : new Date();
/* 30 */     } catch (ParseException var7) {
/* 31 */       date = new Date();
/*    */     } 
/*    */     
/* 34 */     this.banStartDate = date;
/* 35 */     this.bannedBy = json.has("source") ? json.get("source").getAsString() : "(Unknown)";
/*    */ 
/*    */     
/*    */     try {
/* 39 */       date1 = json.has("expires") ? dateFormat.parse(json.get("expires").getAsString()) : null;
/* 40 */     } catch (ParseException var6) {
/* 41 */       date1 = null;
/*    */     } 
/*    */     
/* 44 */     this.banEndDate = date1;
/* 45 */     this.reason = json.has("reason") ? json.get("reason").getAsString() : "Banned by an operator.";
/*    */   }
/*    */   
/*    */   public Date getBanEndDate() {
/* 49 */     return this.banEndDate;
/*    */   }
/*    */   
/*    */   public String getBanReason() {
/* 53 */     return this.reason;
/*    */   }
/*    */   
/*    */   boolean hasBanExpired() {
/* 57 */     return (this.banEndDate == null) ? false : this.banEndDate.before(new Date());
/*    */   }
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 61 */     data.addProperty("created", dateFormat.format(this.banStartDate));
/* 62 */     data.addProperty("source", this.bannedBy);
/* 63 */     data.addProperty("expires", (this.banEndDate == null) ? "forever" : dateFormat.format(this.banEndDate));
/* 64 */     data.addProperty("reason", this.reason);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\BanEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */