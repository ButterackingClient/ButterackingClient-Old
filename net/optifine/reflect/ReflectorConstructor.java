/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import net.optifine.Log;
/*    */ import net.optifine.util.ArrayUtils;
/*    */ 
/*    */ public class ReflectorConstructor
/*    */   implements IResolvable {
/*  9 */   private ReflectorClass reflectorClass = null;
/* 10 */   private Class[] parameterTypes = null;
/*    */   private boolean checked = false;
/* 12 */   private Constructor targetConstructor = null;
/*    */   
/*    */   public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
/* 15 */     this.reflectorClass = reflectorClass;
/* 16 */     this.parameterTypes = parameterTypes;
/* 17 */     ReflectorResolver.register(this);
/*    */   }
/*    */   
/*    */   public Constructor getTargetConstructor() {
/* 21 */     if (this.checked) {
/* 22 */       return this.targetConstructor;
/*    */     }
/* 24 */     this.checked = true;
/* 25 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 27 */     if (oclass == null) {
/* 28 */       return null;
/*    */     }
/*    */     try {
/* 31 */       this.targetConstructor = findConstructor(oclass, this.parameterTypes);
/*    */       
/* 33 */       if (this.targetConstructor == null) {
/* 34 */         Log.dbg("(Reflector) Constructor not present: " + oclass.getName() + ", params: " + ArrayUtils.arrayToString((Object[])this.parameterTypes));
/*    */       }
/*    */       
/* 37 */       if (this.targetConstructor != null) {
/* 38 */         this.targetConstructor.setAccessible(true);
/*    */       }
/* 40 */     } catch (Throwable throwable) {
/* 41 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 44 */     return this.targetConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Constructor findConstructor(Class cls, Class[] paramTypes) {
/* 50 */     Constructor[] aconstructor = (Constructor[])cls.getDeclaredConstructors();
/*    */     
/* 52 */     for (int i = 0; i < aconstructor.length; i++) {
/* 53 */       Constructor constructor = aconstructor[i];
/* 54 */       Class[] aclass = constructor.getParameterTypes();
/*    */       
/* 56 */       if (Reflector.matchesTypes(paramTypes, aclass)) {
/* 57 */         return constructor;
/*    */       }
/*    */     } 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public boolean exists() {
/* 65 */     return this.checked ? ((this.targetConstructor != null)) : ((getTargetConstructor() != null));
/*    */   }
/*    */   
/*    */   public void deactivate() {
/* 69 */     this.checked = true;
/* 70 */     this.targetConstructor = null;
/*    */   }
/*    */   
/*    */   public Object newInstance(Object... params) {
/* 74 */     return Reflector.newInstance(this, params);
/*    */   }
/*    */   
/*    */   public void resolve() {
/* 78 */     Constructor constructor = getTargetConstructor();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */