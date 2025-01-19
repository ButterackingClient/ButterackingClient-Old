/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionResolver;
/*    */ 
/*    */ public class ShaderOptionResolver
/*    */   implements IExpressionResolver {
/* 10 */   private Map<String, ExpressionShaderOptionSwitch> mapOptions = new HashMap<>();
/*    */   
/*    */   public ShaderOptionResolver(ShaderOption[] options) {
/* 13 */     for (int i = 0; i < options.length; i++) {
/* 14 */       ShaderOption shaderoption = options[i];
/*    */       
/* 16 */       if (shaderoption instanceof ShaderOptionSwitch) {
/* 17 */         ShaderOptionSwitch shaderoptionswitch = (ShaderOptionSwitch)shaderoption;
/* 18 */         ExpressionShaderOptionSwitch expressionshaderoptionswitch = new ExpressionShaderOptionSwitch(shaderoptionswitch);
/* 19 */         this.mapOptions.put(shaderoption.getName(), expressionshaderoptionswitch);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 25 */     ExpressionShaderOptionSwitch expressionshaderoptionswitch = this.mapOptions.get(name);
/* 26 */     return (IExpression)expressionshaderoptionswitch;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOptionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */