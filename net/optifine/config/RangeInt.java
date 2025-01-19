/*    */ package net.optifine.config;
/*    */ 
/*    */ public class RangeInt {
/*    */   private int min;
/*    */   private int max;
/*    */   
/*    */   public RangeInt(int min, int max) {
/*  8 */     this.min = Math.min(min, max);
/*  9 */     this.max = Math.max(min, max);
/*    */   }
/*    */   
/*    */   public boolean isInRange(int val) {
/* 13 */     return (val < this.min) ? false : ((val <= this.max));
/*    */   }
/*    */   
/*    */   public int getMin() {
/* 17 */     return this.min;
/*    */   }
/*    */   
/*    */   public int getMax() {
/* 21 */     return this.max;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 25 */     return "min: " + this.min + ", max: " + this.max;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\RangeInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */