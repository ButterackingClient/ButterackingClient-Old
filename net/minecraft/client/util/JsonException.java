/*    */ package net.minecraft.client.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ public class JsonException
/*    */   extends IOException
/*    */ {
/* 12 */   private final List<Entry> field_151383_a = Lists.newArrayList();
/*    */   private final String exceptionMessage;
/*    */   
/*    */   public JsonException(String message) {
/* 16 */     this.field_151383_a.add(new Entry(null));
/* 17 */     this.exceptionMessage = message;
/*    */   }
/*    */   
/*    */   public JsonException(String message, Throwable cause) {
/* 21 */     super(cause);
/* 22 */     this.field_151383_a.add(new Entry(null));
/* 23 */     this.exceptionMessage = message;
/*    */   }
/*    */   
/*    */   public void func_151380_a(String p_151380_1_) {
/* 27 */     ((Entry)this.field_151383_a.get(0)).func_151373_a(p_151380_1_);
/*    */   }
/*    */   
/*    */   public void func_151381_b(String p_151381_1_) {
/* 31 */     (this.field_151383_a.get(0)).field_151376_a = p_151381_1_;
/* 32 */     this.field_151383_a.add(0, new Entry(null));
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 36 */     return "Invalid " + ((Entry)this.field_151383_a.get(this.field_151383_a.size() - 1)).toString() + ": " + this.exceptionMessage;
/*    */   }
/*    */   
/*    */   public static JsonException func_151379_a(Exception p_151379_0_) {
/* 40 */     if (p_151379_0_ instanceof JsonException) {
/* 41 */       return (JsonException)p_151379_0_;
/*    */     }
/* 43 */     String s = p_151379_0_.getMessage();
/*    */     
/* 45 */     if (p_151379_0_ instanceof java.io.FileNotFoundException) {
/* 46 */       s = "File not found";
/*    */     }
/*    */     
/* 49 */     return new JsonException(s, p_151379_0_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Entry
/*    */   {
/* 58 */     private String field_151376_a = null;
/* 59 */     private final List<String> field_151375_b = Lists.newArrayList();
/*    */ 
/*    */     
/*    */     private void func_151373_a(String p_151373_1_) {
/* 63 */       this.field_151375_b.add(0, p_151373_1_);
/*    */     }
/*    */     
/*    */     public String func_151372_b() {
/* 67 */       return StringUtils.join(this.field_151375_b, "->");
/*    */     }
/*    */     
/*    */     public String toString() {
/* 71 */       return (this.field_151376_a != null) ? (!this.field_151375_b.isEmpty() ? (String.valueOf(this.field_151376_a) + " " + func_151372_b()) : this.field_151376_a) : (!this.field_151375_b.isEmpty() ? ("(Unknown file) " + func_151372_b()) : "(Unknown file)");
/*    */     }
/*    */     
/*    */     private Entry() {}
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\clien\\util\JsonException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */