/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import net.optifine.Log;
/*    */ 
/*    */ public class FieldLocatorType
/*    */   implements IFieldLocator {
/*    */   private ReflectorClass reflectorClass;
/*    */   private Class targetFieldType;
/*    */   private int targetFieldIndex;
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType) {
/* 13 */     this(reflectorClass, targetFieldType, 0);
/*    */   }
/*    */   
/*    */   public FieldLocatorType(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/* 17 */     this.reflectorClass = null;
/* 18 */     this.targetFieldType = null;
/* 19 */     this.reflectorClass = reflectorClass;
/* 20 */     this.targetFieldType = targetFieldType;
/* 21 */     this.targetFieldIndex = targetFieldIndex;
/*    */   }
/*    */   
/*    */   public Field getField() {
/* 25 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 27 */     if (oclass == null) {
/* 28 */       return null;
/*    */     }
/*    */     try {
/* 31 */       Field[] afield = oclass.getDeclaredFields();
/* 32 */       int i = 0;
/*    */       
/* 34 */       for (int j = 0; j < afield.length; j++) {
/* 35 */         Field field = afield[j];
/*    */         
/* 37 */         if (field.getType() == this.targetFieldType) {
/* 38 */           if (i == this.targetFieldIndex) {
/* 39 */             field.setAccessible(true);
/* 40 */             return field;
/*    */           } 
/*    */           
/* 43 */           i++;
/*    */         } 
/*    */       } 
/*    */       
/* 47 */       Log.log("(Reflector) Field not present: " + oclass.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
/* 48 */       return null;
/* 49 */     } catch (SecurityException securityexception) {
/* 50 */       securityexception.printStackTrace();
/* 51 */       return null;
/* 52 */     } catch (Throwable throwable) {
/* 53 */       throwable.printStackTrace();
/* 54 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\FieldLocatorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */