/*     */ package net.optifine.expr;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.shaders.uniform.Smoother;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public enum FunctionType
/*     */ {
/*  14 */   PLUS(10, ExpressionType.FLOAT, "+", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  15 */   MINUS(10, ExpressionType.FLOAT, "-", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  16 */   MUL(11, ExpressionType.FLOAT, "*", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  17 */   DIV(11, ExpressionType.FLOAT, "/", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  18 */   MOD(11, ExpressionType.FLOAT, "%", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  19 */   NEG(12, ExpressionType.FLOAT, "neg", new ExpressionType[] { ExpressionType.FLOAT }),
/*  20 */   PI(ExpressionType.FLOAT, "pi", new ExpressionType[0]),
/*  21 */   SIN(ExpressionType.FLOAT, "sin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  22 */   COS(ExpressionType.FLOAT, "cos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  23 */   ASIN(ExpressionType.FLOAT, "asin", new ExpressionType[] { ExpressionType.FLOAT }),
/*  24 */   ACOS(ExpressionType.FLOAT, "acos", new ExpressionType[] { ExpressionType.FLOAT }),
/*  25 */   TAN(ExpressionType.FLOAT, "tan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  26 */   ATAN(ExpressionType.FLOAT, "atan", new ExpressionType[] { ExpressionType.FLOAT }),
/*  27 */   ATAN2(ExpressionType.FLOAT, "atan2", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  28 */   TORAD(ExpressionType.FLOAT, "torad", new ExpressionType[] { ExpressionType.FLOAT }),
/*  29 */   TODEG(ExpressionType.FLOAT, "todeg", new ExpressionType[] { ExpressionType.FLOAT }),
/*  30 */   MIN(ExpressionType.FLOAT, "min", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  31 */   MAX(ExpressionType.FLOAT, "max", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT })),
/*  32 */   CLAMP(ExpressionType.FLOAT, "clamp", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  33 */   ABS(ExpressionType.FLOAT, "abs", new ExpressionType[] { ExpressionType.FLOAT }),
/*  34 */   FLOOR(ExpressionType.FLOAT, "floor", new ExpressionType[] { ExpressionType.FLOAT }),
/*  35 */   CEIL(ExpressionType.FLOAT, "ceil", new ExpressionType[] { ExpressionType.FLOAT }),
/*  36 */   EXP(ExpressionType.FLOAT, "exp", new ExpressionType[] { ExpressionType.FLOAT }),
/*  37 */   FRAC(ExpressionType.FLOAT, "frac", new ExpressionType[] { ExpressionType.FLOAT }),
/*  38 */   LOG(ExpressionType.FLOAT, "log", new ExpressionType[] { ExpressionType.FLOAT }),
/*  39 */   POW(ExpressionType.FLOAT, "pow", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  40 */   RANDOM(ExpressionType.FLOAT, "random", new ExpressionType[0]),
/*  41 */   ROUND(ExpressionType.FLOAT, "round", new ExpressionType[] { ExpressionType.FLOAT }),
/*  42 */   SIGNUM(ExpressionType.FLOAT, "signum", new ExpressionType[] { ExpressionType.FLOAT }),
/*  43 */   SQRT(ExpressionType.FLOAT, "sqrt", new ExpressionType[] { ExpressionType.FLOAT }),
/*  44 */   FMOD(ExpressionType.FLOAT, "fmod", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  45 */   TIME(ExpressionType.FLOAT, "time", new ExpressionType[0]),
/*  46 */   IF(ExpressionType.FLOAT, "if", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.BOOL, ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT })),
/*  47 */   NOT(12, ExpressionType.BOOL, "!", new ExpressionType[] { ExpressionType.BOOL }),
/*  48 */   AND(3, ExpressionType.BOOL, "&&", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL }),
/*  49 */   OR(2, ExpressionType.BOOL, "||", new ExpressionType[] { ExpressionType.BOOL, ExpressionType.BOOL }),
/*  50 */   GREATER(8, ExpressionType.BOOL, ">", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  51 */   GREATER_OR_EQUAL(8, ExpressionType.BOOL, ">=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  52 */   SMALLER(8, ExpressionType.BOOL, "<", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  53 */   SMALLER_OR_EQUAL(8, ExpressionType.BOOL, "<=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  54 */   EQUAL(7, ExpressionType.BOOL, "==", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  55 */   NOT_EQUAL(7, ExpressionType.BOOL, "!=", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  56 */   BETWEEN(7, ExpressionType.BOOL, "between", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  57 */   EQUALS(7, ExpressionType.BOOL, "equals", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  58 */   IN(ExpressionType.BOOL, "in", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).last(new ExpressionType[] { ExpressionType.FLOAT })),
/*  59 */   SMOOTH(ExpressionType.FLOAT, "smooth", (new ParametersVariable()).first(new ExpressionType[] { ExpressionType.FLOAT }).repeat(new ExpressionType[] { ExpressionType.FLOAT }).maxCount(4)),
/*  60 */   TRUE(ExpressionType.BOOL, "true", new ExpressionType[0]),
/*  61 */   FALSE(ExpressionType.BOOL, "false", new ExpressionType[0]),
/*  62 */   VEC2(ExpressionType.FLOAT_ARRAY, "vec2", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  63 */   VEC3(ExpressionType.FLOAT_ARRAY, "vec3", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }),
/*  64 */   VEC4(ExpressionType.FLOAT_ARRAY, "vec4", new ExpressionType[] { ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT }); private int precedence; private ExpressionType expressionType; private String name;
/*     */   private IParameters parameters;
/*     */   public static FunctionType[] VALUES;
/*     */   private static final Map<Integer, Float> mapSmooth;
/*     */   
/*     */   static {
/*  70 */     VALUES = values();
/*  71 */     mapSmooth = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FunctionType(int precedence, ExpressionType expressionType, String name, IParameters parameters) {
/*  86 */     this.precedence = precedence;
/*  87 */     this.expressionType = expressionType;
/*  88 */     this.name = name;
/*  89 */     this.parameters = parameters;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  93 */     return this.name;
/*     */   }
/*     */   
/*     */   public int getPrecedence() {
/*  97 */     return this.precedence;
/*     */   }
/*     */   
/*     */   public ExpressionType getExpressionType() {
/* 101 */     return this.expressionType;
/*     */   }
/*     */   
/*     */   public IParameters getParameters() {
/* 105 */     return this.parameters;
/*     */   }
/*     */   
/*     */   public int getParameterCount(IExpression[] arguments) {
/* 109 */     return (this.parameters.getParameterTypes(arguments)).length;
/*     */   }
/*     */   
/*     */   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
/* 113 */     return this.parameters.getParameterTypes(arguments); } public float evalFloat(IExpression[] args) { float f, f1, f2, f3; Minecraft minecraft;
/*     */     WorldClient worldClient;
/*     */     int i, k, j;
/*     */     float f4, f5, f6, f7;
/* 117 */     switch (this) {
/*     */       case PLUS:
/* 119 */         return evalFloat(args, 0) + evalFloat(args, 1);
/*     */       
/*     */       case MINUS:
/* 122 */         return evalFloat(args, 0) - evalFloat(args, 1);
/*     */       
/*     */       case MUL:
/* 125 */         return evalFloat(args, 0) * evalFloat(args, 1);
/*     */       
/*     */       case DIV:
/* 128 */         return evalFloat(args, 0) / evalFloat(args, 1);
/*     */       
/*     */       case MOD:
/* 131 */         f = evalFloat(args, 0);
/* 132 */         f1 = evalFloat(args, 1);
/* 133 */         return f - f1 * (int)(f / f1);
/*     */       
/*     */       case NEG:
/* 136 */         return -evalFloat(args, 0);
/*     */       
/*     */       case PI:
/* 139 */         return MathHelper.PI;
/*     */       
/*     */       case SIN:
/* 142 */         return MathHelper.sin(evalFloat(args, 0));
/*     */       
/*     */       case COS:
/* 145 */         return MathHelper.cos(evalFloat(args, 0));
/*     */       
/*     */       case ASIN:
/* 148 */         return MathUtils.asin(evalFloat(args, 0));
/*     */       
/*     */       case ACOS:
/* 151 */         return MathUtils.acos(evalFloat(args, 0));
/*     */       
/*     */       case TAN:
/* 154 */         return (float)Math.tan(evalFloat(args, 0));
/*     */       
/*     */       case ATAN:
/* 157 */         return (float)Math.atan(evalFloat(args, 0));
/*     */       
/*     */       case ATAN2:
/* 160 */         return (float)MathHelper.atan2(evalFloat(args, 0), evalFloat(args, 1));
/*     */       
/*     */       case TORAD:
/* 163 */         return MathUtils.toRad(evalFloat(args, 0));
/*     */       
/*     */       case TODEG:
/* 166 */         return MathUtils.toDeg(evalFloat(args, 0));
/*     */       
/*     */       case MIN:
/* 169 */         return getMin(args);
/*     */       
/*     */       case MAX:
/* 172 */         return getMax(args);
/*     */       
/*     */       case CLAMP:
/* 175 */         return MathHelper.clamp_float(evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2));
/*     */       
/*     */       case null:
/* 178 */         return MathHelper.abs(evalFloat(args, 0));
/*     */       
/*     */       case EXP:
/* 181 */         return (float)Math.exp(evalFloat(args, 0));
/*     */       
/*     */       case FLOOR:
/* 184 */         return MathHelper.floor_float(evalFloat(args, 0));
/*     */       
/*     */       case CEIL:
/* 187 */         return MathHelper.ceiling_float_int(evalFloat(args, 0));
/*     */       
/*     */       case FRAC:
/* 190 */         return (float)MathHelper.func_181162_h(evalFloat(args, 0));
/*     */       
/*     */       case LOG:
/* 193 */         return (float)Math.log(evalFloat(args, 0));
/*     */       
/*     */       case POW:
/* 196 */         return (float)Math.pow(evalFloat(args, 0), evalFloat(args, 1));
/*     */       
/*     */       case RANDOM:
/* 199 */         return (float)Math.random();
/*     */       
/*     */       case ROUND:
/* 202 */         return Math.round(evalFloat(args, 0));
/*     */       
/*     */       case SIGNUM:
/* 205 */         return Math.signum(evalFloat(args, 0));
/*     */       
/*     */       case SQRT:
/* 208 */         return MathHelper.sqrt_float(evalFloat(args, 0));
/*     */       
/*     */       case FMOD:
/* 211 */         f2 = evalFloat(args, 0);
/* 212 */         f3 = evalFloat(args, 1);
/* 213 */         return f2 - f3 * MathHelper.floor_float(f2 / f3);
/*     */       
/*     */       case TIME:
/* 216 */         minecraft = Minecraft.getMinecraft();
/* 217 */         worldClient = minecraft.theWorld;
/*     */         
/* 219 */         if (worldClient == null) {
/* 220 */           return 0.0F;
/*     */         }
/*     */         
/* 223 */         return (float)(worldClient.getTotalWorldTime() % 24000L) + Config.renderPartialTicks;
/*     */       
/*     */       case IF:
/* 226 */         i = (args.length - 1) / 2;
/*     */         
/* 228 */         for (k = 0; k < i; k++) {
/* 229 */           int l = k * 2;
/*     */           
/* 231 */           if (evalBool(args, l)) {
/* 232 */             return evalFloat(args, l + 1);
/*     */           }
/*     */         } 
/*     */         
/* 236 */         return evalFloat(args, i * 2);
/*     */       
/*     */       case SMOOTH:
/* 239 */         j = (int)evalFloat(args, 0);
/* 240 */         f4 = evalFloat(args, 1);
/* 241 */         f5 = (args.length > 2) ? evalFloat(args, 2) : 1.0F;
/* 242 */         f6 = (args.length > 3) ? evalFloat(args, 3) : f5;
/* 243 */         f7 = Smoother.getSmoothValue(j, f4, f5, f6);
/* 244 */         return f7;
/*     */     } 
/*     */     
/* 247 */     Config.warn("Unknown function type: " + this);
/* 248 */     return 0.0F; }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMin(IExpression[] exprs) {
/* 253 */     if (exprs.length == 2) {
/* 254 */       return Math.min(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/* 256 */     float f = evalFloat(exprs, 0);
/*     */     
/* 258 */     for (int i = 1; i < exprs.length; i++) {
/* 259 */       float f1 = evalFloat(exprs, i);
/*     */       
/* 261 */       if (f1 < f) {
/* 262 */         f = f1;
/*     */       }
/*     */     } 
/*     */     
/* 266 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getMax(IExpression[] exprs) {
/* 271 */     if (exprs.length == 2) {
/* 272 */       return Math.max(evalFloat(exprs, 0), evalFloat(exprs, 1));
/*     */     }
/* 274 */     float f = evalFloat(exprs, 0);
/*     */     
/* 276 */     for (int i = 1; i < exprs.length; i++) {
/* 277 */       float f1 = evalFloat(exprs, i);
/*     */       
/* 279 */       if (f1 > f) {
/* 280 */         f = f1;
/*     */       }
/*     */     } 
/*     */     
/* 284 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float evalFloat(IExpression[] exprs, int index) {
/* 289 */     IExpressionFloat iexpressionfloat = (IExpressionFloat)exprs[index];
/* 290 */     float f = iexpressionfloat.eval();
/* 291 */     return f;
/*     */   } public boolean evalBool(IExpression[] args) {
/*     */     float f, f1, f2, f3;
/*     */     int i;
/* 295 */     switch (this) {
/*     */       case TRUE:
/* 297 */         return true;
/*     */       
/*     */       case FALSE:
/* 300 */         return false;
/*     */       
/*     */       case NOT:
/* 303 */         return !evalBool(args, 0);
/*     */       
/*     */       case AND:
/* 306 */         return (evalBool(args, 0) && evalBool(args, 1));
/*     */       
/*     */       case OR:
/* 309 */         return !(!evalBool(args, 0) && !evalBool(args, 1));
/*     */       
/*     */       case GREATER:
/* 312 */         return (evalFloat(args, 0) > evalFloat(args, 1));
/*     */       
/*     */       case GREATER_OR_EQUAL:
/* 315 */         return (evalFloat(args, 0) >= evalFloat(args, 1));
/*     */       
/*     */       case SMALLER:
/* 318 */         return (evalFloat(args, 0) < evalFloat(args, 1));
/*     */       
/*     */       case SMALLER_OR_EQUAL:
/* 321 */         return (evalFloat(args, 0) <= evalFloat(args, 1));
/*     */       
/*     */       case EQUAL:
/* 324 */         return (evalFloat(args, 0) == evalFloat(args, 1));
/*     */       
/*     */       case NOT_EQUAL:
/* 327 */         return (evalFloat(args, 0) != evalFloat(args, 1));
/*     */       
/*     */       case BETWEEN:
/* 330 */         f = evalFloat(args, 0);
/* 331 */         return (f >= evalFloat(args, 1) && f <= evalFloat(args, 2));
/*     */       
/*     */       case EQUALS:
/* 334 */         f1 = evalFloat(args, 0) - evalFloat(args, 1);
/* 335 */         f2 = evalFloat(args, 2);
/* 336 */         return (Math.abs(f1) <= f2);
/*     */       
/*     */       case IN:
/* 339 */         f3 = evalFloat(args, 0);
/*     */         
/* 341 */         for (i = 1; i < args.length; i++) {
/* 342 */           float f4 = evalFloat(args, i);
/*     */           
/* 344 */           if (f3 == f4) {
/* 345 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 349 */         return false;
/*     */     } 
/*     */     
/* 352 */     Config.warn("Unknown function type: " + this);
/* 353 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean evalBool(IExpression[] exprs, int index) {
/* 358 */     IExpressionBool iexpressionbool = (IExpressionBool)exprs[index];
/* 359 */     boolean flag = iexpressionbool.eval();
/* 360 */     return flag;
/*     */   }
/*     */   
/*     */   public float[] evalFloatArray(IExpression[] args) {
/* 364 */     switch (this) {
/*     */       case VEC2:
/* 366 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1) };
/*     */       case VEC3:
/* 368 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2) };
/*     */       case VEC4:
/* 370 */         return new float[] { evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2), evalFloat(args, 3) };
/*     */     } 
/* 372 */     Config.warn("Unknown function type: " + this);
/* 373 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FunctionType parse(String str) {
/* 378 */     for (int i = 0; i < VALUES.length; i++) {
/* 379 */       FunctionType functiontype = VALUES[i];
/*     */       
/* 381 */       if (functiontype.getName().equals(str)) {
/* 382 */         return functiontype;
/*     */       }
/*     */     } 
/*     */     
/* 386 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\FunctionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */