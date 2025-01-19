/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.optifine.Log;
/*     */ 
/*     */ public class ReflectorMethod
/*     */   implements IResolvable {
/*     */   private ReflectorClass reflectorClass;
/*     */   private String targetMethodName;
/*     */   private Class[] targetMethodParameterTypes;
/*     */   private boolean checked;
/*     */   private Method targetMethod;
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName) {
/*  17 */     this(reflectorClass, targetMethodName, null);
/*     */   }
/*     */   
/*     */   public ReflectorMethod(ReflectorClass reflectorClass, String targetMethodName, Class[] targetMethodParameterTypes) {
/*  21 */     this.reflectorClass = null;
/*  22 */     this.targetMethodName = null;
/*  23 */     this.targetMethodParameterTypes = null;
/*  24 */     this.checked = false;
/*  25 */     this.targetMethod = null;
/*  26 */     this.reflectorClass = reflectorClass;
/*  27 */     this.targetMethodName = targetMethodName;
/*  28 */     this.targetMethodParameterTypes = targetMethodParameterTypes;
/*  29 */     ReflectorResolver.register(this);
/*     */   }
/*     */   
/*     */   public Method getTargetMethod() {
/*  33 */     if (this.checked) {
/*  34 */       return this.targetMethod;
/*     */     }
/*  36 */     this.checked = true;
/*  37 */     Class oclass = this.reflectorClass.getTargetClass();
/*     */     
/*  39 */     if (oclass == null) {
/*  40 */       return null;
/*     */     }
/*     */     try {
/*  43 */       if (this.targetMethodParameterTypes == null) {
/*  44 */         Method[] amethod = getMethods(oclass, this.targetMethodName);
/*     */         
/*  46 */         if (amethod.length <= 0) {
/*  47 */           Log.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  48 */           return null;
/*     */         } 
/*     */         
/*  51 */         if (amethod.length > 1) {
/*  52 */           Log.warn("(Reflector) More than one method found: " + oclass.getName() + "." + this.targetMethodName);
/*     */           
/*  54 */           for (int i = 0; i < amethod.length; i++) {
/*  55 */             Method method = amethod[i];
/*  56 */             Log.warn("(Reflector)  - " + method);
/*     */           } 
/*     */           
/*  59 */           return null;
/*     */         } 
/*     */         
/*  62 */         this.targetMethod = amethod[0];
/*     */       } else {
/*  64 */         this.targetMethod = getMethod(oclass, this.targetMethodName, this.targetMethodParameterTypes);
/*     */       } 
/*     */       
/*  67 */       if (this.targetMethod == null) {
/*  68 */         Log.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  69 */         return null;
/*     */       } 
/*  71 */       this.targetMethod.setAccessible(true);
/*  72 */       return this.targetMethod;
/*     */     }
/*  74 */     catch (Throwable throwable) {
/*  75 */       throwable.printStackTrace();
/*  76 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/*  83 */     return this.checked ? ((this.targetMethod != null)) : ((getTargetMethod() != null));
/*     */   }
/*     */   
/*     */   public Class getReturnType() {
/*  87 */     Method method = getTargetMethod();
/*  88 */     return (method == null) ? null : method.getReturnType();
/*     */   }
/*     */   
/*     */   public void deactivate() {
/*  92 */     this.checked = true;
/*  93 */     this.targetMethod = null;
/*     */   }
/*     */   
/*     */   public Object call(Object... params) {
/*  97 */     return Reflector.call(this, params);
/*     */   }
/*     */   
/*     */   public boolean callBoolean(Object... params) {
/* 101 */     return Reflector.callBoolean(this, params);
/*     */   }
/*     */   
/*     */   public int callInt(Object... params) {
/* 105 */     return Reflector.callInt(this, params);
/*     */   }
/*     */   
/*     */   public float callFloat(Object... params) {
/* 109 */     return Reflector.callFloat(this, params);
/*     */   }
/*     */   
/*     */   public double callDouble(Object... params) {
/* 113 */     return Reflector.callDouble(this, params);
/*     */   }
/*     */   
/*     */   public String callString(Object... params) {
/* 117 */     return Reflector.callString(this, params);
/*     */   }
/*     */   
/*     */   public Object call(Object param) {
/* 121 */     return Reflector.call(this, new Object[] { param });
/*     */   }
/*     */   
/*     */   public boolean callBoolean(Object param) {
/* 125 */     return Reflector.callBoolean(this, new Object[] { param });
/*     */   }
/*     */   
/*     */   public int callInt(Object param) {
/* 129 */     return Reflector.callInt(this, new Object[] { param });
/*     */   }
/*     */   
/*     */   public float callFloat(Object param) {
/* 133 */     return Reflector.callFloat(this, new Object[] { param });
/*     */   }
/*     */   
/*     */   public double callDouble(Object param) {
/* 137 */     return Reflector.callDouble(this, new Object[] { param });
/*     */   }
/*     */   
/*     */   public String callString1(Object param) {
/* 141 */     return Reflector.callString(this, new Object[] { param });
/*     */   }
/*     */   
/*     */   public void callVoid(Object... params) {
/* 145 */     Reflector.callVoid(this, params);
/*     */   }
/*     */   
/*     */   public static Method getMethod(Class cls, String methodName, Class[] paramTypes) {
/* 149 */     Method[] amethod = cls.getDeclaredMethods();
/*     */     
/* 151 */     for (int i = 0; i < amethod.length; i++) {
/* 152 */       Method method = amethod[i];
/*     */       
/* 154 */       if (method.getName().equals(methodName)) {
/* 155 */         Class[] aclass = method.getParameterTypes();
/*     */         
/* 157 */         if (Reflector.matchesTypes(paramTypes, aclass)) {
/* 158 */           return method;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     return null;
/*     */   }
/*     */   
/*     */   public static Method[] getMethods(Class cls, String methodName) {
/* 167 */     List<Method> list = new ArrayList();
/* 168 */     Method[] amethod = cls.getDeclaredMethods();
/*     */     
/* 170 */     for (int i = 0; i < amethod.length; i++) {
/* 171 */       Method method = amethod[i];
/*     */       
/* 173 */       if (method.getName().equals(methodName)) {
/* 174 */         list.add(method);
/*     */       }
/*     */     } 
/*     */     
/* 178 */     Method[] amethod1 = list.<Method>toArray(new Method[list.size()]);
/* 179 */     return amethod1;
/*     */   }
/*     */   
/*     */   public void resolve() {
/* 183 */     Method method = getTargetMethod();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */