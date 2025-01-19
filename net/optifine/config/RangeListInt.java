/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class RangeListInt {
/*  6 */   private RangeInt[] ranges = new RangeInt[0];
/*    */ 
/*    */   
/*    */   public RangeListInt() {}
/*    */   
/*    */   public RangeListInt(RangeInt ri) {
/* 12 */     addRange(ri);
/*    */   }
/*    */   
/*    */   public void addRange(RangeInt ri) {
/* 16 */     this.ranges = (RangeInt[])Config.addObjectToArray((Object[])this.ranges, ri);
/*    */   }
/*    */   
/*    */   public boolean isInRange(int val) {
/* 20 */     for (int i = 0; i < this.ranges.length; i++) {
/* 21 */       RangeInt rangeint = this.ranges[i];
/*    */       
/* 23 */       if (rangeint.isInRange(val)) {
/* 24 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public int getCountRanges() {
/* 32 */     return this.ranges.length;
/*    */   }
/*    */   
/*    */   public RangeInt getRange(int i) {
/* 36 */     return this.ranges[i];
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     StringBuffer stringbuffer = new StringBuffer();
/* 41 */     stringbuffer.append("[");
/*    */     
/* 43 */     for (int i = 0; i < this.ranges.length; i++) {
/* 44 */       RangeInt rangeint = this.ranges[i];
/*    */       
/* 46 */       if (i > 0) {
/* 47 */         stringbuffer.append(", ");
/*    */       }
/*    */       
/* 50 */       stringbuffer.append(rangeint.toString());
/*    */     } 
/*    */     
/* 53 */     stringbuffer.append("]");
/* 54 */     return stringbuffer.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\RangeListInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */