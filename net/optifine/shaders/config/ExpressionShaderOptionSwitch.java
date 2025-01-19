/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import net.optifine.expr.ExpressionType;
/*    */ import net.optifine.expr.IExpressionBool;
/*    */ 
/*    */ public class ExpressionShaderOptionSwitch implements IExpressionBool {
/*    */   private ShaderOptionSwitch shaderOption;
/*    */   
/*    */   public ExpressionShaderOptionSwitch(ShaderOptionSwitch shaderOption) {
/* 10 */     this.shaderOption = shaderOption;
/*    */   }
/*    */   
/*    */   public boolean eval() {
/* 14 */     return ShaderOptionSwitch.isTrue(this.shaderOption.getValue());
/*    */   }
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 18 */     return ExpressionType.BOOL;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 22 */     return (String)this.shaderOption;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ExpressionShaderOptionSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */