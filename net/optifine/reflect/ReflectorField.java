/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class ReflectorField implements IResolvable {
/*    */   private IFieldLocator fieldLocator;
/*    */   private boolean checked;
/*    */   private Field targetField;
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, String targetFieldName) {
/* 11 */     this(new FieldLocatorName(reflectorClass, targetFieldName));
/*    */   }
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType) {
/* 15 */     this(reflectorClass, targetFieldType, 0);
/*    */   }
/*    */   
/*    */   public ReflectorField(ReflectorClass reflectorClass, Class targetFieldType, int targetFieldIndex) {
/* 19 */     this(new FieldLocatorType(reflectorClass, targetFieldType, targetFieldIndex));
/*    */   }
/*    */   
/*    */   public ReflectorField(Field field) {
/* 23 */     this(new FieldLocatorFixed(field));
/*    */   }
/*    */   
/*    */   public ReflectorField(IFieldLocator fieldLocator) {
/* 27 */     this.fieldLocator = null;
/* 28 */     this.checked = false;
/* 29 */     this.targetField = null;
/* 30 */     this.fieldLocator = fieldLocator;
/* 31 */     ReflectorResolver.register(this);
/*    */   }
/*    */   
/*    */   public Field getTargetField() {
/* 35 */     if (this.checked) {
/* 36 */       return this.targetField;
/*    */     }
/* 38 */     this.checked = true;
/* 39 */     this.targetField = this.fieldLocator.getField();
/*    */     
/* 41 */     if (this.targetField != null) {
/* 42 */       this.targetField.setAccessible(true);
/*    */     }
/*    */     
/* 45 */     return this.targetField;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 50 */     return Reflector.getFieldValue((Object)null, this);
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 54 */     Reflector.setFieldValue(null, this, value);
/*    */   }
/*    */   
/*    */   public void setValue(Object obj, Object value) {
/* 58 */     Reflector.setFieldValue(obj, this, value);
/*    */   }
/*    */   
/*    */   public boolean exists() {
/* 62 */     return (getTargetField() != null);
/*    */   }
/*    */   
/*    */   public void resolve() {
/* 66 */     Field field = getTargetField();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */