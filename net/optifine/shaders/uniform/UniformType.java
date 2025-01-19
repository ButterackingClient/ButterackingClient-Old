/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ import net.optifine.expr.IExpressionFloat;
/*     */ import net.optifine.expr.IExpressionFloatArray;
/*     */ 
/*     */ public enum UniformType {
/*  10 */   BOOL,
/*  11 */   INT,
/*  12 */   FLOAT,
/*  13 */   VEC2,
/*  14 */   VEC3,
/*  15 */   VEC4;
/*     */   
/*     */   public ShaderUniformBase makeShaderUniform(String name) {
/*  18 */     switch (this) {
/*     */       case null:
/*  20 */         return new ShaderUniform1i(name);
/*     */       
/*     */       case INT:
/*  23 */         return new ShaderUniform1i(name);
/*     */       
/*     */       case FLOAT:
/*  26 */         return new ShaderUniform1f(name);
/*     */       
/*     */       case VEC2:
/*  29 */         return new ShaderUniform2f(name);
/*     */       
/*     */       case VEC3:
/*  32 */         return new ShaderUniform3f(name);
/*     */       
/*     */       case VEC4:
/*  35 */         return new ShaderUniform4f(name);
/*     */     } 
/*     */     
/*  38 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUniform(IExpression expression, ShaderUniformBase uniform) {
/*  43 */     switch (this) {
/*     */       case null:
/*  45 */         updateUniformBool((IExpressionBool)expression, (ShaderUniform1i)uniform);
/*     */         return;
/*     */       
/*     */       case INT:
/*  49 */         updateUniformInt((IExpressionFloat)expression, (ShaderUniform1i)uniform);
/*     */         return;
/*     */       
/*     */       case FLOAT:
/*  53 */         updateUniformFloat((IExpressionFloat)expression, (ShaderUniform1f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC2:
/*  57 */         updateUniformFloat2((IExpressionFloatArray)expression, (ShaderUniform2f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC3:
/*  61 */         updateUniformFloat3((IExpressionFloatArray)expression, (ShaderUniform3f)uniform);
/*     */         return;
/*     */       
/*     */       case VEC4:
/*  65 */         updateUniformFloat4((IExpressionFloatArray)expression, (ShaderUniform4f)uniform);
/*     */         return;
/*     */     } 
/*     */     
/*  69 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformBool(IExpressionBool expression, ShaderUniform1i uniform) {
/*  74 */     boolean flag = expression.eval();
/*  75 */     int i = flag ? 1 : 0;
/*  76 */     uniform.setValue(i);
/*     */   }
/*     */   
/*     */   private void updateUniformInt(IExpressionFloat expression, ShaderUniform1i uniform) {
/*  80 */     int i = (int)expression.eval();
/*  81 */     uniform.setValue(i);
/*     */   }
/*     */   
/*     */   private void updateUniformFloat(IExpressionFloat expression, ShaderUniform1f uniform) {
/*  85 */     float f = expression.eval();
/*  86 */     uniform.setValue(f);
/*     */   }
/*     */   
/*     */   private void updateUniformFloat2(IExpressionFloatArray expression, ShaderUniform2f uniform) {
/*  90 */     float[] afloat = expression.eval();
/*     */     
/*  92 */     if (afloat.length != 2) {
/*  93 */       throw new RuntimeException("Value length is not 2, length: " + afloat.length);
/*     */     }
/*  95 */     uniform.setValue(afloat[0], afloat[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformFloat3(IExpressionFloatArray expression, ShaderUniform3f uniform) {
/* 100 */     float[] afloat = expression.eval();
/*     */     
/* 102 */     if (afloat.length != 3) {
/* 103 */       throw new RuntimeException("Value length is not 3, length: " + afloat.length);
/*     */     }
/* 105 */     uniform.setValue(afloat[0], afloat[1], afloat[2]);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateUniformFloat4(IExpressionFloatArray expression, ShaderUniform4f uniform) {
/* 110 */     float[] afloat = expression.eval();
/*     */     
/* 112 */     if (afloat.length != 4) {
/* 113 */       throw new RuntimeException("Value length is not 4, length: " + afloat.length);
/*     */     }
/* 115 */     uniform.setValue(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesExpressionType(ExpressionType expressionType) {
/* 120 */     switch (this) {
/*     */       case null:
/* 122 */         return (expressionType == ExpressionType.BOOL);
/*     */       
/*     */       case INT:
/* 125 */         return (expressionType == ExpressionType.FLOAT);
/*     */       
/*     */       case FLOAT:
/* 128 */         return (expressionType == ExpressionType.FLOAT);
/*     */       
/*     */       case VEC2:
/*     */       case VEC3:
/*     */       case VEC4:
/* 133 */         return (expressionType == ExpressionType.FLOAT_ARRAY);
/*     */     } 
/*     */     
/* 136 */     throw new RuntimeException("Unknown uniform type: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static UniformType parse(String type) {
/* 141 */     UniformType[] auniformtype = values();
/*     */     
/* 143 */     for (int i = 0; i < auniformtype.length; i++) {
/* 144 */       UniformType uniformtype = auniformtype[i];
/*     */       
/* 146 */       if (uniformtype.name().toLowerCase().equals(type)) {
/* 147 */         return uniformtype;
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\UniformType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */