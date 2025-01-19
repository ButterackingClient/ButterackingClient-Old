/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import net.optifine.Log;
/*    */ 
/*    */ public class ReflectorClass implements IResolvable {
/*  6 */   private String targetClassName = null;
/*    */   private boolean checked = false;
/*  8 */   private Class targetClass = null;
/*    */   
/*    */   public ReflectorClass(String targetClassName) {
/* 11 */     this.targetClassName = targetClassName;
/* 12 */     ReflectorResolver.register(this);
/*    */   }
/*    */   
/*    */   public ReflectorClass(Class targetClass) {
/* 16 */     this.targetClass = targetClass;
/* 17 */     this.targetClassName = targetClass.getName();
/* 18 */     this.checked = true;
/*    */   }
/*    */   
/*    */   public Class getTargetClass() {
/* 22 */     if (this.checked) {
/* 23 */       return this.targetClass;
/*    */     }
/* 25 */     this.checked = true;
/*    */     
/*    */     try {
/* 28 */       this.targetClass = Class.forName(this.targetClassName);
/* 29 */     } catch (ClassNotFoundException var2) {
/* 30 */       Log.log("(Reflector) Class not present: " + this.targetClassName);
/* 31 */     } catch (Throwable throwable) {
/* 32 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 35 */     return this.targetClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 40 */     return (getTargetClass() != null);
/*    */   }
/*    */   
/*    */   public String getTargetClassName() {
/* 44 */     return this.targetClassName;
/*    */   }
/*    */   
/*    */   public boolean isInstance(Object obj) {
/* 48 */     return (getTargetClass() == null) ? false : getTargetClass().isInstance(obj);
/*    */   }
/*    */   
/*    */   public ReflectorField makeField(String name) {
/* 52 */     return new ReflectorField(this, name);
/*    */   }
/*    */   
/*    */   public ReflectorMethod makeMethod(String name) {
/* 56 */     return new ReflectorMethod(this, name);
/*    */   }
/*    */   
/*    */   public ReflectorMethod makeMethod(String name, Class[] paramTypes) {
/* 60 */     return new ReflectorMethod(this, name, paramTypes);
/*    */   }
/*    */   
/*    */   public void resolve() {
/* 64 */     Class oclass = getTargetClass();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */