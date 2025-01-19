/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class FieldLocatorFixed implements IFieldLocator {
/*    */   private Field field;
/*    */   
/*    */   public FieldLocatorFixed(Field field) {
/*  9 */     this.field = field;
/*    */   }
/*    */   
/*    */   public Field getField() {
/* 13 */     return this.field;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\FieldLocatorFixed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */