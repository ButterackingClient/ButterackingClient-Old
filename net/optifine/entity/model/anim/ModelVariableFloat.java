/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.optifine.expr.ExpressionType;
/*    */ import net.optifine.expr.IExpressionFloat;
/*    */ 
/*    */ public class ModelVariableFloat implements IExpressionFloat {
/*    */   private String name;
/*    */   private ModelRenderer modelRenderer;
/*    */   private ModelVariableType enumModelVariable;
/*    */   
/*    */   public ModelVariableFloat(String name, ModelRenderer modelRenderer, ModelVariableType enumModelVariable) {
/* 13 */     this.name = name;
/* 14 */     this.modelRenderer = modelRenderer;
/* 15 */     this.enumModelVariable = enumModelVariable;
/*    */   }
/*    */   
/*    */   public ExpressionType getExpressionType() {
/* 19 */     return ExpressionType.FLOAT;
/*    */   }
/*    */   
/*    */   public float eval() {
/* 23 */     return getValue();
/*    */   }
/*    */   
/*    */   public float getValue() {
/* 27 */     return this.enumModelVariable.getFloat(this.modelRenderer);
/*    */   }
/*    */   
/*    */   public void setValue(float value) {
/* 31 */     this.enumModelVariable.setFloat(this.modelRenderer, value);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\anim\ModelVariableFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */