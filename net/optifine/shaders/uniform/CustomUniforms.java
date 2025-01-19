/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionCached;
/*    */ 
/*    */ public class CustomUniforms
/*    */ {
/*    */   private CustomUniform[] uniforms;
/*    */   private IExpressionCached[] expressionsCached;
/*    */   
/*    */   public CustomUniforms(CustomUniform[] uniforms, Map<String, IExpression> mapExpressions) {
/* 15 */     this.uniforms = uniforms;
/* 16 */     List<IExpressionCached> list = new ArrayList<>();
/*    */     
/* 18 */     for (String s : mapExpressions.keySet()) {
/* 19 */       IExpression iexpression = mapExpressions.get(s);
/*    */       
/* 21 */       if (iexpression instanceof IExpressionCached) {
/* 22 */         IExpressionCached iexpressioncached = (IExpressionCached)iexpression;
/* 23 */         list.add(iexpressioncached);
/*    */       } 
/*    */     } 
/*    */     
/* 27 */     this.expressionsCached = list.<IExpressionCached>toArray(new IExpressionCached[list.size()]);
/*    */   }
/*    */   
/*    */   public void setProgram(int program) {
/* 31 */     for (int i = 0; i < this.uniforms.length; i++) {
/* 32 */       CustomUniform customuniform = this.uniforms[i];
/* 33 */       customuniform.setProgram(program);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void update() {
/* 38 */     resetCache();
/*    */     
/* 40 */     for (int i = 0; i < this.uniforms.length; i++) {
/* 41 */       CustomUniform customuniform = this.uniforms[i];
/* 42 */       customuniform.update();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void resetCache() {
/* 47 */     for (int i = 0; i < this.expressionsCached.length; i++) {
/* 48 */       IExpressionCached iexpressioncached = this.expressionsCached[i];
/* 49 */       iexpressioncached.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void reset() {
/* 54 */     for (int i = 0; i < this.uniforms.length; i++) {
/* 55 */       CustomUniform customuniform = this.uniforms[i];
/* 56 */       customuniform.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\CustomUniforms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */