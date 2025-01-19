/*    */ package net.optifine.expr;
/*    */ 
/*    */ import net.optifine.shaders.uniform.Smoother;
/*    */ 
/*    */ public class FunctionFloat implements IExpressionFloat {
/*    */   private FunctionType type;
/*    */   private IExpression[] arguments;
/*  8 */   private int smoothId = -1;
/*    */   
/*    */   public FunctionFloat(FunctionType type, IExpression[] arguments) {
/* 11 */     this.type = type;
/* 12 */     this.arguments = arguments;
/*    */   }
/*    */   
/*    */   public float eval() {
/* 16 */     IExpression iexpression, aiexpression[] = this.arguments;
/*    */     
/* 18 */     switch (this.type) {
/*    */       case SMOOTH:
/* 20 */         iexpression = aiexpression[0];
/*    */         
/* 22 */         if (!(iexpression instanceof ConstantFloat)) {
/* 23 */           float f = evalFloat(aiexpression, 0);
/* 24 */           float f1 = (aiexpression.length > 1) ? evalFloat(aiexpression, 1) : 1.0F;
/* 25 */           float f2 = (aiexpression.length > 2) ? evalFloat(aiexpression, 2) : f1;
/*    */           
/* 27 */           if (this.smoothId < 0) {
/* 28 */             this.smoothId = Smoother.getNextId();
/*    */           }
/*    */           
/* 31 */           float f3 = Smoother.getSmoothValue(this.smoothId, f, f1, f2);
/* 32 */           return f3;
/*    */         } 
/*    */         break;
/*    */     } 
/* 36 */     return this.type.evalFloat(this.arguments);
/*    */   }
/*    */ 
/*    */   
/*    */   private static float evalFloat(IExpression[] exprs, int index) {
/* 41 */     IExpressionFloat iexpressionfloat = (IExpressionFloat)exprs[index];
/* 42 */     float f = iexpressionfloat.eval();
/* 43 */     return f;
/*    */   }
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 47 */     return ExpressionType.FLOAT;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     return this.type + "()";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\FunctionFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */