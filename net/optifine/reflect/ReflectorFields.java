/*    */ package net.optifine.reflect;
/*    */ 
/*    */ public class ReflectorFields {
/*    */   private ReflectorClass reflectorClass;
/*    */   private Class fieldType;
/*    */   private int fieldCount;
/*    */   private ReflectorField[] reflectorFields;
/*    */   
/*    */   public ReflectorFields(ReflectorClass reflectorClass, Class fieldType, int fieldCount) {
/* 10 */     this.reflectorClass = reflectorClass;
/* 11 */     this.fieldType = fieldType;
/*    */     
/* 13 */     if (reflectorClass.exists() && 
/* 14 */       fieldType != null) {
/* 15 */       this.reflectorFields = new ReflectorField[fieldCount];
/*    */       
/* 17 */       for (int i = 0; i < this.reflectorFields.length; i++) {
/* 18 */         this.reflectorFields[i] = new ReflectorField(reflectorClass, fieldType, i);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass getReflectorClass() {
/* 25 */     return this.reflectorClass;
/*    */   }
/*    */   
/*    */   public Class getFieldType() {
/* 29 */     return this.fieldType;
/*    */   }
/*    */   
/*    */   public int getFieldCount() {
/* 33 */     return this.fieldCount;
/*    */   }
/*    */   
/*    */   public ReflectorField getReflectorField(int index) {
/* 37 */     return (index >= 0 && index < this.reflectorFields.length) ? this.reflectorFields[index] : null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */