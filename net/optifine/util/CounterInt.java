/*    */ package net.optifine.util;
/*    */ 
/*    */ public class CounterInt {
/*    */   private int startValue;
/*    */   private int value;
/*    */   
/*    */   public CounterInt(int startValue) {
/*  8 */     this.startValue = startValue;
/*  9 */     this.value = startValue;
/*    */   }
/*    */   
/*    */   public synchronized int nextValue() {
/* 13 */     int i = this.value++;
/* 14 */     return i;
/*    */   }
/*    */   
/*    */   public synchronized void reset() {
/* 18 */     this.value = this.startValue;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\CounterInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */