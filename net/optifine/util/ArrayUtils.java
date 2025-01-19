/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ArrayUtils {
/*     */   public static boolean contains(Object[] arr, Object val) {
/*  11 */     if (arr == null) {
/*  12 */       return false;
/*     */     }
/*  14 */     for (int i = 0; i < arr.length; i++) {
/*  15 */       Object object = arr[i];
/*     */       
/*  17 */       if (object == val) {
/*  18 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  22 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] addIntsToArray(int[] intArray, int[] copyFrom) {
/*  27 */     if (intArray != null && copyFrom != null) {
/*  28 */       int i = intArray.length;
/*  29 */       int j = i + copyFrom.length;
/*  30 */       int[] aint = new int[j];
/*  31 */       System.arraycopy(intArray, 0, aint, 0, i);
/*     */       
/*  33 */       for (int k = 0; k < copyFrom.length; k++) {
/*  34 */         aint[k + i] = copyFrom[k];
/*     */       }
/*     */       
/*  37 */       return aint;
/*     */     } 
/*  39 */     throw new NullPointerException("The given array is NULL");
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] addIntToArray(int[] intArray, int intValue) {
/*  44 */     return addIntsToArray(intArray, new int[] { intValue });
/*     */   }
/*     */   
/*     */   public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
/*  48 */     if (arr == null)
/*  49 */       throw new NullPointerException("The given array is NULL"); 
/*  50 */     if (objs.length == 0) {
/*  51 */       return arr;
/*     */     }
/*  53 */     int i = arr.length;
/*  54 */     int j = i + objs.length;
/*  55 */     Object[] aobject = (Object[])Array.newInstance(arr.getClass().getComponentType(), j);
/*  56 */     System.arraycopy(arr, 0, aobject, 0, i);
/*  57 */     System.arraycopy(objs, 0, aobject, i, objs.length);
/*  58 */     return aobject;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] addObjectToArray(Object[] arr, Object obj) {
/*  63 */     if (arr == null) {
/*  64 */       throw new NullPointerException("The given array is NULL");
/*     */     }
/*  66 */     int i = arr.length;
/*  67 */     int j = i + 1;
/*  68 */     Object[] aobject = (Object[])Array.newInstance(arr.getClass().getComponentType(), j);
/*  69 */     System.arraycopy(arr, 0, aobject, 0, i);
/*  70 */     aobject[i] = obj;
/*  71 */     return aobject;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] addObjectToArray(Object[] arr, Object obj, int index) {
/*  76 */     List<Object> list = new ArrayList(Arrays.asList(arr));
/*  77 */     list.add(index, obj);
/*  78 */     Object[] aobject = (Object[])Array.newInstance(arr.getClass().getComponentType(), list.size());
/*  79 */     return list.toArray(aobject);
/*     */   }
/*     */   
/*     */   public static String arrayToString(boolean[] arr, String separator) {
/*  83 */     if (arr == null) {
/*  84 */       return "";
/*     */     }
/*  86 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/*  88 */     for (int i = 0; i < arr.length; i++) {
/*  89 */       boolean flag = arr[i];
/*     */       
/*  91 */       if (i > 0) {
/*  92 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/*  95 */       stringbuffer.append(String.valueOf(flag));
/*     */     } 
/*     */     
/*  98 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(float[] arr) {
/* 103 */     return arrayToString(arr, ", ");
/*     */   }
/*     */   
/*     */   public static String arrayToString(float[] arr, String separator) {
/* 107 */     if (arr == null) {
/* 108 */       return "";
/*     */     }
/* 110 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 112 */     for (int i = 0; i < arr.length; i++) {
/* 113 */       float f = arr[i];
/*     */       
/* 115 */       if (i > 0) {
/* 116 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 119 */       stringbuffer.append(String.valueOf(f));
/*     */     } 
/*     */     
/* 122 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(float[] arr, String separator, String format) {
/* 127 */     if (arr == null) {
/* 128 */       return "";
/*     */     }
/* 130 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 132 */     for (int i = 0; i < arr.length; i++) {
/* 133 */       float f = arr[i];
/*     */       
/* 135 */       if (i > 0) {
/* 136 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 139 */       stringbuffer.append(String.format(format, new Object[] { Float.valueOf(f) }));
/*     */     } 
/*     */     
/* 142 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(int[] arr) {
/* 147 */     return arrayToString(arr, ", ");
/*     */   }
/*     */   
/*     */   public static String arrayToString(int[] arr, String separator) {
/* 151 */     if (arr == null) {
/* 152 */       return "";
/*     */     }
/* 154 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 156 */     for (int i = 0; i < arr.length; i++) {
/* 157 */       int j = arr[i];
/*     */       
/* 159 */       if (i > 0) {
/* 160 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 163 */       stringbuffer.append(String.valueOf(j));
/*     */     } 
/*     */     
/* 166 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToHexString(int[] arr, String separator) {
/* 171 */     if (arr == null) {
/* 172 */       return "";
/*     */     }
/* 174 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 176 */     for (int i = 0; i < arr.length; i++) {
/* 177 */       int j = arr[i];
/*     */       
/* 179 */       if (i > 0) {
/* 180 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 183 */       stringbuffer.append("0x");
/* 184 */       stringbuffer.append(Integer.toHexString(j));
/*     */     } 
/*     */     
/* 187 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String arrayToString(Object[] arr) {
/* 192 */     return arrayToString(arr, ", ");
/*     */   }
/*     */   
/*     */   public static String arrayToString(Object[] arr, String separator) {
/* 196 */     if (arr == null) {
/* 197 */       return "";
/*     */     }
/* 199 */     StringBuffer stringbuffer = new StringBuffer(arr.length * 5);
/*     */     
/* 201 */     for (int i = 0; i < arr.length; i++) {
/* 202 */       Object object = arr[i];
/*     */       
/* 204 */       if (i > 0) {
/* 205 */         stringbuffer.append(separator);
/*     */       }
/*     */       
/* 208 */       stringbuffer.append(String.valueOf(object));
/*     */     } 
/*     */     
/* 211 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] collectionToArray(Collection coll, Class<?> elementClass) {
/* 216 */     if (coll == null)
/* 217 */       return null; 
/* 218 */     if (elementClass == null)
/* 219 */       return null; 
/* 220 */     if (elementClass.isPrimitive()) {
/* 221 */       throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + elementClass);
/*     */     }
/* 223 */     Object[] aobject = (Object[])Array.newInstance(elementClass, coll.size());
/* 224 */     return coll.toArray(aobject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsOne(int val, int[] vals) {
/* 229 */     for (int i = 0; i < vals.length; i++) {
/* 230 */       if (vals[i] == val) {
/* 231 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean equalsOne(Object a, Object[] bs) {
/* 239 */     if (bs == null) {
/* 240 */       return false;
/*     */     }
/* 242 */     for (int i = 0; i < bs.length; i++) {
/* 243 */       Object object = bs[i];
/*     */       
/* 245 */       if (equals(a, object)) {
/* 246 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 250 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equals(Object o1, Object o2) {
/* 255 */     return (o1 == o2) ? true : ((o1 == null) ? false : o1.equals(o2));
/*     */   }
/*     */   
/*     */   public static boolean isSameOne(Object a, Object[] bs) {
/* 259 */     if (bs == null) {
/* 260 */       return false;
/*     */     }
/* 262 */     for (int i = 0; i < bs.length; i++) {
/* 263 */       Object object = bs[i];
/*     */       
/* 265 */       if (a == object) {
/* 266 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object[] removeObjectFromArray(Object[] arr, Object obj) {
/* 275 */     List list = new ArrayList(Arrays.asList(arr));
/* 276 */     list.remove(obj);
/* 277 */     Object[] aobject = collectionToArray(list, arr.getClass().getComponentType());
/* 278 */     return aobject;
/*     */   }
/*     */   
/*     */   public static int[] toPrimitive(Integer[] arr) {
/* 282 */     if (arr == null)
/* 283 */       return null; 
/* 284 */     if (arr.length == 0) {
/* 285 */       return new int[0];
/*     */     }
/* 287 */     int[] aint = new int[arr.length];
/*     */     
/* 289 */     for (int i = 0; i < aint.length; i++) {
/* 290 */       aint[i] = arr[i].intValue();
/*     */     }
/*     */     
/* 293 */     return aint;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\ArrayUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */