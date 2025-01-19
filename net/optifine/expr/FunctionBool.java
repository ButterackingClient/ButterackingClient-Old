/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class FunctionBool implements IExpressionBool {
/*    */   private FunctionType type;
/*    */   private IExpression[] arguments;
/*    */   
/*    */   public FunctionBool(FunctionType type, IExpression[] arguments) {
/*  8 */     this.type = type;
/*  9 */     this.arguments = arguments;
/*    */   }
/*    */   
/*    */   public boolean eval() {
/* 13 */     return this.type.evalBool(this.arguments);
/*    */   }
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 17 */     return ExpressionType.BOOL;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 21 */     return this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\FunctionBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */