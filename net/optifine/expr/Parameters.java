/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class Parameters implements IParameters {
/*    */   private ExpressionType[] parameterTypes;
/*    */   
/*    */   public Parameters(ExpressionType[] parameterTypes) {
/*  7 */     this.parameterTypes = parameterTypes;
/*    */   }
/*    */   
/*    */   public ExpressionType[] getParameterTypes(IExpression[] params) {
/* 11 */     return this.parameterTypes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\Parameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */