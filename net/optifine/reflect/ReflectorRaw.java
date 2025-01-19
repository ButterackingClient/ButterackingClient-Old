/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ReflectorRaw {
/*     */   public static Field getField(Class cls, Class<?> fieldType) {
/*     */     try {
/*  12 */       Field[] afield = cls.getDeclaredFields();
/*     */       
/*  14 */       for (int i = 0; i < afield.length; i++) {
/*  15 */         Field field = afield[i];
/*     */         
/*  17 */         if (field.getType() == fieldType) {
/*  18 */           field.setAccessible(true);
/*  19 */           return field;
/*     */         } 
/*     */       } 
/*     */       
/*  23 */       return null;
/*  24 */     } catch (Exception var5) {
/*  25 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Field[] getFields(Class cls, Class fieldType) {
/*     */     try {
/*  31 */       Field[] afield = cls.getDeclaredFields();
/*  32 */       return getFields(afield, fieldType);
/*  33 */     } catch (Exception var3) {
/*  34 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Field[] getFields(Field[] fields, Class<?> fieldType) {
/*     */     try {
/*  40 */       List<Field> list = new ArrayList();
/*     */       
/*  42 */       for (int i = 0; i < fields.length; i++) {
/*  43 */         Field field = fields[i];
/*     */         
/*  45 */         if (field.getType() == fieldType) {
/*  46 */           field.setAccessible(true);
/*  47 */           list.add(field);
/*     */         } 
/*     */       } 
/*     */       
/*  51 */       Field[] afield = list.<Field>toArray(new Field[list.size()]);
/*  52 */       return afield;
/*  53 */     } catch (Exception var5) {
/*  54 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Field[] getFieldsAfter(Class cls, Field field, Class fieldType) {
/*     */     try {
/*  60 */       Field[] afield = cls.getDeclaredFields();
/*  61 */       List<Field> list = Arrays.asList(afield);
/*  62 */       int i = list.indexOf(field);
/*     */       
/*  64 */       if (i < 0) {
/*  65 */         return new Field[0];
/*     */       }
/*  67 */       List<Field> list1 = list.subList(i + 1, list.size());
/*  68 */       Field[] afield1 = list1.<Field>toArray(new Field[list1.size()]);
/*  69 */       return getFields(afield1, fieldType);
/*     */     }
/*  71 */     catch (Exception var8) {
/*  72 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Field[] getFields(Object obj, Field[] fields, Class<?> fieldType, Object value) {
/*     */     try {
/*  78 */       List<Field> list = new ArrayList<>();
/*     */       
/*  80 */       for (int i = 0; i < fields.length; i++) {
/*  81 */         Field field = fields[i];
/*     */         
/*  83 */         if (field.getType() == fieldType) {
/*  84 */           boolean flag = Modifier.isStatic(field.getModifiers());
/*     */           
/*  86 */           if ((obj != null || flag) && (obj == null || !flag)) {
/*  87 */             field.setAccessible(true);
/*  88 */             Object object = field.get(obj);
/*     */             
/*  90 */             if (object == value) {
/*  91 */               list.add(field);
/*  92 */             } else if (object != null && value != null && object.equals(value)) {
/*  93 */               list.add(field);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  99 */       Field[] afield = list.<Field>toArray(new Field[list.size()]);
/* 100 */       return afield;
/* 101 */     } catch (Exception var9) {
/* 102 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Field getField(Class cls, Class fieldType, int index) {
/* 107 */     Field[] afield = getFields(cls, fieldType);
/* 108 */     return (index >= 0 && index < afield.length) ? afield[index] : null;
/*     */   }
/*     */   
/*     */   public static Field getFieldAfter(Class cls, Field field, Class fieldType, int index) {
/* 112 */     Field[] afield = getFieldsAfter(cls, field, fieldType);
/* 113 */     return (index >= 0 && index < afield.length) ? afield[index] : null;
/*     */   }
/*     */   
/*     */   public static Object getFieldValue(Object obj, Class cls, Class fieldType) {
/* 117 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType);
/* 118 */     return (reflectorfield == null) ? null : (!reflectorfield.exists() ? null : Reflector.getFieldValue(obj, reflectorfield));
/*     */   }
/*     */   
/*     */   public static Object getFieldValue(Object obj, Class cls, Class fieldType, int index) {
/* 122 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType, index);
/* 123 */     return (reflectorfield == null) ? null : (!reflectorfield.exists() ? null : Reflector.getFieldValue(obj, reflectorfield));
/*     */   }
/*     */   
/*     */   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, Object value) {
/* 127 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType);
/* 128 */     return (reflectorfield == null) ? false : (!reflectorfield.exists() ? false : Reflector.setFieldValue(obj, reflectorfield, value));
/*     */   }
/*     */   
/*     */   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, int index, Object value) {
/* 132 */     ReflectorField reflectorfield = getReflectorField(cls, fieldType, index);
/* 133 */     return (reflectorfield == null) ? false : (!reflectorfield.exists() ? false : Reflector.setFieldValue(obj, reflectorfield, value));
/*     */   }
/*     */   
/*     */   public static ReflectorField getReflectorField(Class cls, Class fieldType) {
/* 137 */     Field field = getField(cls, fieldType);
/*     */     
/* 139 */     if (field == null) {
/* 140 */       return null;
/*     */     }
/* 142 */     ReflectorClass reflectorclass = new ReflectorClass(cls);
/* 143 */     return new ReflectorField(reflectorclass, field.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class cls, Class fieldType, int index) {
/* 148 */     Field field = getField(cls, fieldType, index);
/*     */     
/* 150 */     if (field == null) {
/* 151 */       return null;
/*     */     }
/* 153 */     ReflectorClass reflectorclass = new ReflectorClass(cls);
/* 154 */     return new ReflectorField(reflectorclass, field.getName());
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\ReflectorRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */