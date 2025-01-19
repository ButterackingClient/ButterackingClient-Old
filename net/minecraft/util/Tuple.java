/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class Tuple<A, B> {
/*    */   private A a;
/*    */   private B b;
/*    */   
/*    */   public Tuple(A aIn, B bIn) {
/*  8 */     this.a = aIn;
/*  9 */     this.b = bIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public A getFirst() {
/* 16 */     return this.a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public B getSecond() {
/* 23 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Tuple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */