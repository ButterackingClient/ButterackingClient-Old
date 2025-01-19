/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import net.optifine.expr.ExpressionType;
/*    */ import net.optifine.expr.IExpressionFloat;
/*    */ 
/*    */ public class ShaderParameterIndexed implements IExpressionFloat {
/*    */   private ShaderParameterFloat type;
/*    */   private int index1;
/*    */   private int index2;
/*    */   
/*    */   public ShaderParameterIndexed(ShaderParameterFloat type) {
/* 12 */     this(type, 0, 0);
/*    */   }
/*    */   
/*    */   public ShaderParameterIndexed(ShaderParameterFloat type, int index1) {
/* 16 */     this(type, index1, 0);
/*    */   }
/*    */   
/*    */   public ShaderParameterIndexed(ShaderParameterFloat type, int index1, int index2) {
/* 20 */     this.type = type;
/* 21 */     this.index1 = index1;
/* 22 */     this.index2 = index2;
/*    */   }
/*    */   
/*    */   public float eval() {
/* 26 */     return this.type.eval(this.index1, this.index2);
/*    */   }
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 30 */     return ExpressionType.FLOAT;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 34 */     return (this.type.getIndexNames1() == null) ? (String)this.type : ((this.type.getIndexNames2() == null) ? (this.type + "." + this.type.getIndexNames1()[this.index1]) : (this.type + "." + this.type.getIndexNames1()[this.index1] + "." + this.type.getIndexNames2()[this.index2]));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderParameterIndexed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */