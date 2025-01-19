/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.expr.ExpressionParser;
/*    */ import net.optifine.expr.IExpressionFloat;
/*    */ import net.optifine.expr.ParseException;
/*    */ 
/*    */ public class ModelVariableUpdater {
/*    */   private String modelVariableName;
/*    */   private String expressionText;
/*    */   private ModelVariableFloat modelVariable;
/*    */   private IExpressionFloat expression;
/*    */   
/*    */   public boolean initialize(IModelResolver mr) {
/* 15 */     this.modelVariable = mr.getModelVariable(this.modelVariableName);
/*    */     
/* 17 */     if (this.modelVariable == null) {
/* 18 */       Config.warn("Model variable not found: " + this.modelVariableName);
/* 19 */       return false;
/*    */     } 
/*    */     try {
/* 22 */       ExpressionParser expressionparser = new ExpressionParser(mr);
/* 23 */       this.expression = expressionparser.parseFloat(this.expressionText);
/* 24 */       return true;
/* 25 */     } catch (ParseException parseexception) {
/* 26 */       Config.warn("Error parsing expression: " + this.expressionText);
/* 27 */       Config.warn(String.valueOf(parseexception.getClass().getName()) + ": " + parseexception.getMessage());
/* 28 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelVariableUpdater(String modelVariableName, String expressionText) {
/* 34 */     this.modelVariableName = modelVariableName;
/* 35 */     this.expressionText = expressionText;
/*    */   }
/*    */   
/*    */   public void update() {
/* 39 */     float f = this.expression.eval();
/* 40 */     this.modelVariable.setValue(f);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\anim\ModelVariableUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */