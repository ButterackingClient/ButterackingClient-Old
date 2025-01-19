/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseFilter
/*    */ {
/*    */   private float field_76336_a;
/*    */   private float field_76334_b;
/*    */   private float field_76335_c;
/*    */   
/*    */   public float smooth(float p_76333_1_, float p_76333_2_) {
/* 12 */     this.field_76336_a += p_76333_1_;
/* 13 */     p_76333_1_ = (this.field_76336_a - this.field_76334_b) * p_76333_2_;
/* 14 */     this.field_76335_c += (p_76333_1_ - this.field_76335_c) * 0.5F;
/*    */     
/* 16 */     if ((p_76333_1_ > 0.0F && p_76333_1_ > this.field_76335_c) || (p_76333_1_ < 0.0F && p_76333_1_ < this.field_76335_c)) {
/* 17 */       p_76333_1_ = this.field_76335_c;
/*    */     }
/*    */     
/* 20 */     this.field_76334_b += p_76333_1_;
/* 21 */     return p_76333_1_;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 25 */     this.field_76336_a = 0.0F;
/* 26 */     this.field_76334_b = 0.0F;
/* 27 */     this.field_76335_c = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MouseFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */