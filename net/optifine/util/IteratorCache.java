/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class IteratorCache {
/*  9 */   private static Deque<IteratorReusable<Object>> dequeIterators = new ArrayDeque<>();
/*    */   
/*    */   public static Iterator<Object> getReadOnly(List list) {
/* 12 */     synchronized (dequeIterators) {
/* 13 */       IteratorReusable<Object> iteratorreusable = dequeIterators.pollFirst();
/*    */       
/* 15 */       if (iteratorreusable == null) {
/* 16 */         iteratorreusable = new IteratorReadOnly();
/*    */       }
/*    */       
/* 19 */       iteratorreusable.setList(list);
/* 20 */       return iteratorreusable;
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void finished(IteratorReusable<Object> iterator) {
/* 25 */     synchronized (dequeIterators) {
/* 26 */       if (dequeIterators.size() <= 1000) {
/* 27 */         iterator.setList(null);
/* 28 */         dequeIterators.addLast(iterator);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   static {
/* 34 */     for (int i = 0; i < 1000; i++) {
/* 35 */       IteratorReadOnly iteratorcache$iteratorreadonly = new IteratorReadOnly();
/* 36 */       dequeIterators.add(iteratorcache$iteratorreadonly);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class IteratorReadOnly implements IteratorReusable<Object> {
/*    */     private List<Object> list;
/*    */     private int index;
/*    */     private boolean hasNext;
/*    */     
/*    */     public void setList(List<Object> list) {
/* 46 */       if (this.hasNext) {
/* 47 */         throw new RuntimeException("Iterator still used, oldList: " + this.list + ", newList: " + list);
/*    */       }
/* 49 */       this.list = list;
/* 50 */       this.index = 0;
/* 51 */       this.hasNext = (list != null && this.index < list.size());
/*    */     }
/*    */ 
/*    */     
/*    */     public Object next() {
/* 56 */       if (!this.hasNext) {
/* 57 */         return null;
/*    */       }
/* 59 */       Object object = this.list.get(this.index);
/* 60 */       this.index++;
/* 61 */       this.hasNext = (this.index < this.list.size());
/* 62 */       return object;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean hasNext() {
/* 67 */       if (!this.hasNext) {
/* 68 */         IteratorCache.finished(this);
/* 69 */         return false;
/*    */       } 
/* 71 */       return this.hasNext;
/*    */     }
/*    */ 
/*    */     
/*    */     public void remove() {
/* 76 */       throw new UnsupportedOperationException("remove");
/*    */     }
/*    */   }
/*    */   
/*    */   public static interface IteratorReusable<E> extends Iterator<E> {
/*    */     void setList(List<E> param1List);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\IteratorCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */