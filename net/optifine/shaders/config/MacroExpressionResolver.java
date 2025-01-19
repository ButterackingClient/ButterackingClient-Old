/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.expr.ConstantFloat;
/*    */ import net.optifine.expr.FunctionBool;
/*    */ import net.optifine.expr.FunctionType;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionResolver;
/*    */ 
/*    */ public class MacroExpressionResolver
/*    */   implements IExpressionResolver {
/* 13 */   private Map<String, String> mapMacroValues = null;
/*    */   
/*    */   public MacroExpressionResolver(Map<String, String> mapMacroValues) {
/* 16 */     this.mapMacroValues = mapMacroValues;
/*    */   }
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 20 */     String s = "defined_";
/*    */     
/* 22 */     if (name.startsWith(s)) {
/* 23 */       String s2 = name.substring(s.length());
/* 24 */       return this.mapMacroValues.containsKey(s2) ? (IExpression)new FunctionBool(FunctionType.TRUE, null) : (IExpression)new FunctionBool(FunctionType.FALSE, null);
/*    */     } 
/* 26 */     while (this.mapMacroValues.containsKey(name)) {
/* 27 */       String s1 = this.mapMacroValues.get(name);
/*    */       
/* 29 */       if (s1 == null || s1.equals(name)) {
/*    */         break;
/*    */       }
/*    */       
/* 33 */       name = s1;
/*    */     } 
/*    */     
/* 36 */     int i = Config.parseInt(name, -2147483648);
/*    */     
/* 38 */     if (i == Integer.MIN_VALUE) {
/* 39 */       Config.warn("Unknown macro value: " + name);
/* 40 */       return (IExpression)new ConstantFloat(0.0F);
/*    */     } 
/* 42 */     return (IExpression)new ConstantFloat(i);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\MacroExpressionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */