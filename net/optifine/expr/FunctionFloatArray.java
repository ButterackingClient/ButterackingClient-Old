/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class FunctionFloatArray implements IExpressionFloatArray {
/*    */   private FunctionType type;
/*    */   private IExpression[] arguments;
/*    */   
/*    */   public FunctionFloatArray(FunctionType type, IExpression[] arguments) {
/*  8 */     this.type = type;
/*  9 */     this.arguments = arguments;
/*    */   }
/*    */   
/*    */   public float[] eval() {
/* 13 */     return this.type.evalFloatArray(this.arguments);
/*    */   }
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 17 */     return ExpressionType.FLOAT_ARRAY;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 21 */     return this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\FunctionFloatArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */