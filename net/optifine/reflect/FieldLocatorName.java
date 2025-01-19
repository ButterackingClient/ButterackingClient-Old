/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import net.optifine.Log;
/*    */ 
/*    */ public class FieldLocatorName
/*    */   implements IFieldLocator {
/*  8 */   private ReflectorClass reflectorClass = null;
/*  9 */   private String targetFieldName = null;
/*    */   
/*    */   public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
/* 12 */     this.reflectorClass = reflectorClass;
/* 13 */     this.targetFieldName = targetFieldName;
/*    */   }
/*    */   
/*    */   public Field getField() {
/* 17 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 19 */     if (oclass == null) {
/* 20 */       return null;
/*    */     }
/*    */     try {
/* 23 */       Field field = getDeclaredField(oclass, this.targetFieldName);
/* 24 */       field.setAccessible(true);
/* 25 */       return field;
/* 26 */     } catch (NoSuchFieldException var3) {
/* 27 */       Log.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
/* 28 */       return null;
/* 29 */     } catch (SecurityException securityexception) {
/* 30 */       securityexception.printStackTrace();
/* 31 */       return null;
/* 32 */     } catch (Throwable throwable) {
/* 33 */       throwable.printStackTrace();
/* 34 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private Field getDeclaredField(Class<Object> cls, String name) throws NoSuchFieldException {
/* 40 */     Field[] afield = cls.getDeclaredFields();
/*    */     
/* 42 */     for (int i = 0; i < afield.length; i++) {
/* 43 */       Field field = afield[i];
/*    */       
/* 45 */       if (field.getName().equals(name)) {
/* 46 */         return field;
/*    */       }
/*    */     } 
/*    */     
/* 50 */     if (cls == Object.class) {
/* 51 */       throw new NoSuchFieldException(name);
/*    */     }
/* 53 */     return getDeclaredField(cls.getSuperclass(), name);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\FieldLocatorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */