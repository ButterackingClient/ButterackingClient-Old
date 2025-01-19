/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.shaders.SMCLog;
/*    */ 
/*    */ public class CustomUniform {
/*    */   private String name;
/*    */   private UniformType type;
/*    */   private IExpression expression;
/*    */   private ShaderUniformBase shaderUniform;
/*    */   
/*    */   public CustomUniform(String name, UniformType type, IExpression expression) {
/* 13 */     this.name = name;
/* 14 */     this.type = type;
/* 15 */     this.expression = expression;
/* 16 */     this.shaderUniform = type.makeShaderUniform(name);
/*    */   }
/*    */   
/*    */   public void setProgram(int program) {
/* 20 */     this.shaderUniform.setProgram(program);
/*    */   }
/*    */   
/*    */   public void update() {
/* 24 */     if (this.shaderUniform.isDefined()) {
/*    */       try {
/* 26 */         this.type.updateUniform(this.expression, this.shaderUniform);
/* 27 */       } catch (RuntimeException runtimeexception) {
/* 28 */         SMCLog.severe("Error updating custom uniform: " + this.shaderUniform.getName());
/* 29 */         SMCLog.severe(String.valueOf(runtimeexception.getClass().getName()) + ": " + runtimeexception.getMessage());
/* 30 */         this.shaderUniform.disable();
/* 31 */         SMCLog.severe("Custom uniform disabled: " + this.shaderUniform.getName());
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void reset() {
/* 37 */     this.shaderUniform.reset();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 41 */     return this.name;
/*    */   }
/*    */   
/*    */   public UniformType getType() {
/* 45 */     return this.type;
/*    */   }
/*    */   
/*    */   public IExpression getExpression() {
/* 49 */     return this.expression;
/*    */   }
/*    */   
/*    */   public ShaderUniformBase getShaderUniform() {
/* 53 */     return this.shaderUniform;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     return String.valueOf(this.type.name().toLowerCase()) + " " + this.name;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\CustomUniform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */