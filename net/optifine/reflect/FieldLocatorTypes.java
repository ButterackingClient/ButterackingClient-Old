/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.optifine.Log;
/*    */ 
/*    */ public class FieldLocatorTypes
/*    */   implements IFieldLocator {
/* 12 */   private Field field = null;
/*    */   
/*    */   public FieldLocatorTypes(Class cls, Class[] preTypes, Class<?> type, Class[] postTypes, String errorName) {
/* 15 */     Field[] afield = cls.getDeclaredFields();
/* 16 */     List<Class<?>> list = new ArrayList<>();
/*    */     
/* 18 */     for (int i = 0; i < afield.length; i++) {
/* 19 */       Field field = afield[i];
/* 20 */       list.add(field.getType());
/*    */     } 
/*    */     
/* 23 */     List<Class<?>> list1 = new ArrayList<>();
/* 24 */     list1.addAll(Arrays.asList(preTypes));
/* 25 */     list1.add(type);
/* 26 */     list1.addAll(Arrays.asList(postTypes));
/* 27 */     int l = Collections.indexOfSubList(list, list1);
/*    */     
/* 29 */     if (l < 0) {
/* 30 */       Log.log("(Reflector) Field not found: " + errorName);
/*    */     } else {
/* 32 */       int j = Collections.indexOfSubList(list.subList(l + 1, list.size()), list1);
/*    */       
/* 34 */       if (j >= 0) {
/* 35 */         Log.log("(Reflector) More than one match found for field: " + errorName);
/*    */       } else {
/* 37 */         int k = l + preTypes.length;
/* 38 */         this.field = afield[k];
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public Field getField() {
/* 44 */     return this.field;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\FieldLocatorTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */