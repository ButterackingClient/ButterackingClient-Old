/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.optifine.expr.ConstantFloat;
/*    */ import net.optifine.expr.IExpression;
/*    */ import net.optifine.expr.IExpressionResolver;
/*    */ import net.optifine.shaders.SMCLog;
/*    */ 
/*    */ public class ShaderExpressionResolver
/*    */   implements IExpressionResolver {
/* 13 */   private Map<String, IExpression> mapExpressions = new HashMap<>();
/*    */   
/*    */   public ShaderExpressionResolver(Map<String, IExpression> map) {
/* 16 */     registerExpressions();
/*    */     
/* 18 */     for (String s : map.keySet()) {
/* 19 */       IExpression iexpression = map.get(s);
/* 20 */       registerExpression(s, iexpression);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void registerExpressions() {
/* 25 */     ShaderParameterFloat[] ashaderparameterfloat = ShaderParameterFloat.values();
/*    */     
/* 27 */     for (int i = 0; i < ashaderparameterfloat.length; i++) {
/* 28 */       ShaderParameterFloat shaderparameterfloat = ashaderparameterfloat[i];
/* 29 */       addParameterFloat(this.mapExpressions, shaderparameterfloat);
/*    */     } 
/*    */     
/* 32 */     ShaderParameterBool[] ashaderparameterbool = ShaderParameterBool.values();
/*    */     
/* 34 */     for (int k = 0; k < ashaderparameterbool.length; k++) {
/* 35 */       ShaderParameterBool shaderparameterbool = ashaderparameterbool[k];
/* 36 */       this.mapExpressions.put(shaderparameterbool.getName(), shaderparameterbool);
/*    */     } 
/*    */     
/* 39 */     for (BiomeGenBase biomegenbase : BiomeGenBase.BIOME_ID_MAP.values()) {
/* 40 */       String s = biomegenbase.biomeName.trim();
/* 41 */       s = "BIOME_" + s.toUpperCase().replace(' ', '_');
/* 42 */       int j = biomegenbase.biomeID;
/* 43 */       ConstantFloat constantFloat = new ConstantFloat(j);
/* 44 */       registerExpression(s, (IExpression)constantFloat);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void addParameterFloat(Map<String, IExpression> map, ShaderParameterFloat spf) {
/* 49 */     String[] astring = spf.getIndexNames1();
/*    */     
/* 51 */     if (astring == null) {
/* 52 */       map.put(spf.getName(), new ShaderParameterIndexed(spf));
/*    */     } else {
/* 54 */       for (int i = 0; i < astring.length; i++) {
/* 55 */         String s = astring[i];
/* 56 */         String[] astring1 = spf.getIndexNames2();
/*    */         
/* 58 */         if (astring1 == null) {
/* 59 */           map.put(String.valueOf(spf.getName()) + "." + s, new ShaderParameterIndexed(spf, i));
/*    */         } else {
/* 61 */           for (int j = 0; j < astring1.length; j++) {
/* 62 */             String s1 = astring1[j];
/* 63 */             map.put(String.valueOf(spf.getName()) + "." + s + "." + s1, new ShaderParameterIndexed(spf, i, j));
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean registerExpression(String name, IExpression expr) {
/* 71 */     if (this.mapExpressions.containsKey(name)) {
/* 72 */       SMCLog.warning("Expression already defined: " + name);
/* 73 */       return false;
/*    */     } 
/* 75 */     this.mapExpressions.put(name, expr);
/* 76 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public IExpression getExpression(String name) {
/* 81 */     return this.mapExpressions.get(name);
/*    */   }
/*    */   
/*    */   public boolean hasExpression(String name) {
/* 85 */     return this.mapExpressions.containsKey(name);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderExpressionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */