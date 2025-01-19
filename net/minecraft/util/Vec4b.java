/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class Vec4b {
/*    */   private byte field_176117_a;
/*    */   private byte field_176115_b;
/*    */   private byte field_176116_c;
/*    */   private byte field_176114_d;
/*    */   
/*    */   public Vec4b(byte p_i45555_1_, byte p_i45555_2_, byte p_i45555_3_, byte p_i45555_4_) {
/* 10 */     this.field_176117_a = p_i45555_1_;
/* 11 */     this.field_176115_b = p_i45555_2_;
/* 12 */     this.field_176116_c = p_i45555_3_;
/* 13 */     this.field_176114_d = p_i45555_4_;
/*    */   }
/*    */   
/*    */   public Vec4b(Vec4b p_i45556_1_) {
/* 17 */     this.field_176117_a = p_i45556_1_.field_176117_a;
/* 18 */     this.field_176115_b = p_i45556_1_.field_176115_b;
/* 19 */     this.field_176116_c = p_i45556_1_.field_176116_c;
/* 20 */     this.field_176114_d = p_i45556_1_.field_176114_d;
/*    */   }
/*    */   
/*    */   public byte func_176110_a() {
/* 24 */     return this.field_176117_a;
/*    */   }
/*    */   
/*    */   public byte func_176112_b() {
/* 28 */     return this.field_176115_b;
/*    */   }
/*    */   
/*    */   public byte func_176113_c() {
/* 32 */     return this.field_176116_c;
/*    */   }
/*    */   
/*    */   public byte func_176111_d() {
/* 36 */     return this.field_176114_d;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 40 */     if (this == p_equals_1_)
/* 41 */       return true; 
/* 42 */     if (!(p_equals_1_ instanceof Vec4b)) {
/* 43 */       return false;
/*    */     }
/* 45 */     Vec4b vec4b = (Vec4b)p_equals_1_;
/* 46 */     return (this.field_176117_a != vec4b.field_176117_a) ? false : ((this.field_176114_d != vec4b.field_176114_d) ? false : ((this.field_176115_b != vec4b.field_176115_b) ? false : ((this.field_176116_c == vec4b.field_176116_c))));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     int i = this.field_176117_a;
/* 52 */     i = 31 * i + this.field_176115_b;
/* 53 */     i = 31 * i + this.field_176116_c;
/* 54 */     i = 31 * i + this.field_176114_d;
/* 55 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Vec4b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */