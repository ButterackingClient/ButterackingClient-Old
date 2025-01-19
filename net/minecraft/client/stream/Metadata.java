/*    */ package net.minecraft.client.stream;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.Gson;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Metadata
/*    */ {
/* 10 */   private static final Gson field_152811_a = new Gson();
/*    */   private final String name;
/*    */   private String description;
/*    */   private Map<String, String> payload;
/*    */   
/*    */   public Metadata(String p_i46345_1_, String p_i46345_2_) {
/* 16 */     this.name = p_i46345_1_;
/* 17 */     this.description = p_i46345_2_;
/*    */   }
/*    */   
/*    */   public Metadata(String p_i1030_1_) {
/* 21 */     this(p_i1030_1_, null);
/*    */   }
/*    */   
/*    */   public void func_152807_a(String p_152807_1_) {
/* 25 */     this.description = p_152807_1_;
/*    */   }
/*    */   
/*    */   public String func_152809_a() {
/* 29 */     return (this.description == null) ? this.name : this.description;
/*    */   }
/*    */   
/*    */   public void func_152808_a(String p_152808_1_, String p_152808_2_) {
/* 33 */     if (this.payload == null) {
/* 34 */       this.payload = Maps.newHashMap();
/*    */     }
/*    */     
/* 37 */     if (this.payload.size() > 50)
/* 38 */       throw new IllegalArgumentException("Metadata payload is full, cannot add more to it!"); 
/* 39 */     if (p_152808_1_ == null)
/* 40 */       throw new IllegalArgumentException("Metadata payload key cannot be null!"); 
/* 41 */     if (p_152808_1_.length() > 255)
/* 42 */       throw new IllegalArgumentException("Metadata payload key is too long!"); 
/* 43 */     if (p_152808_2_ == null)
/* 44 */       throw new IllegalArgumentException("Metadata payload value cannot be null!"); 
/* 45 */     if (p_152808_2_.length() > 255) {
/* 46 */       throw new IllegalArgumentException("Metadata payload value is too long!");
/*    */     }
/* 48 */     this.payload.put(p_152808_1_, p_152808_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_152806_b() {
/* 53 */     return (this.payload != null && !this.payload.isEmpty()) ? field_152811_a.toJson(this.payload) : null;
/*    */   }
/*    */   
/*    */   public String func_152810_c() {
/* 57 */     return this.name;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     return Objects.toStringHelper(this).add("name", this.name).add("description", this.description).add("data", func_152806_b()).toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\Metadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */