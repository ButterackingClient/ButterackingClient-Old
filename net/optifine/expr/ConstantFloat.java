/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class ConstantFloat implements IExpressionFloat {
/*    */   private float value;
/*    */   
/*    */   public ConstantFloat(float value) {
/*  7 */     this.value = value;
/*    */   }
/*    */   
/*    */   public float eval() {
/* 11 */     return this.value;
/*    */   }
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 15 */     return ExpressionType.FLOAT;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 19 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\ConstantFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */