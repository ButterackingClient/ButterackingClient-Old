/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.ArrayDeque;
/*    */ 
/*    */ public class ArrayCache {
/*  7 */   private Class elementClass = null;
/*  8 */   private int maxCacheSize = 0;
/*  9 */   private ArrayDeque cache = new ArrayDeque();
/*    */   
/*    */   public ArrayCache(Class elementClass, int maxCacheSize) {
/* 12 */     this.elementClass = elementClass;
/* 13 */     this.maxCacheSize = maxCacheSize;
/*    */   }
/*    */   
/*    */   public synchronized Object allocate(int size) {
/* 17 */     Object object = this.cache.pollLast();
/*    */     
/* 19 */     if (object == null || Array.getLength(object) < size) {
/* 20 */       object = Array.newInstance(this.elementClass, size);
/*    */     }
/*    */     
/* 23 */     return object;
/*    */   }
/*    */   
/*    */   public synchronized void free(Object arr) {
/* 27 */     if (arr != null) {
/* 28 */       Class<?> oclass = arr.getClass();
/*    */       
/* 30 */       if (oclass.getComponentType() != this.elementClass)
/* 31 */         throw new IllegalArgumentException("Wrong component type"); 
/* 32 */       if (this.cache.size() < this.maxCacheSize)
/* 33 */         this.cache.add(arr); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\ArrayCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */