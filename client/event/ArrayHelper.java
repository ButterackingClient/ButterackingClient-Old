/*     */ package client.event;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class ArrayHelper<T>
/*     */   implements Iterable<T> {
/*     */   private T[] elements;
/*     */   
/*     */   public ArrayHelper(Object[] array) {
/*  10 */     this.elements = (T[])array;
/*     */   }
/*     */   
/*     */   public ArrayHelper() {
/*  14 */     this.elements = (T[])new Object[0];
/*     */   }
/*     */   
/*     */   public void add(T t) {
/*  18 */     if (t != null) {
/*  19 */       Object[] array = new Object[size() + 1];
/*  20 */       for (int i = 0; i < array.length; i++) {
/*  21 */         if (i < size()) {
/*  22 */           array[i] = get(i);
/*     */         } else {
/*     */           
/*  25 */           array[i] = t;
/*     */         } 
/*     */       } 
/*  28 */       set((T[])array);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(T t) {
/*     */     Object[] array;
/*  34 */     for (int lenght = (array = (Object[])array()).length, i = 0; i < lenght; i++) {
/*  35 */       T entry = (T)array[i];
/*  36 */       if (entry.equals(t)) {
/*  37 */         return true;
/*     */       }
/*     */     } 
/*  40 */     return false;
/*     */   }
/*     */   
/*     */   public void remove(T t) {
/*  44 */     if (contains(t)) {
/*  45 */       Object[] array = new Object[size() - 1];
/*  46 */       boolean b = true;
/*  47 */       for (int i = 0; i < size(); i++) {
/*  48 */         if (b && get(i).equals(t)) {
/*  49 */           b = false;
/*     */         } else {
/*     */           
/*  52 */           array[b ? i : (i - 1)] = get(i);
/*     */         } 
/*     */       } 
/*  55 */       set((T[])array);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T[] array() {
/*  60 */     return this.elements;
/*     */   }
/*     */   
/*     */   public int size() {
/*  64 */     return (array()).length;
/*     */   }
/*     */   
/*     */   public void set(Object[] array) {
/*  68 */     this.elements = (T[])array;
/*     */   }
/*     */   
/*     */   public T get(int index) {
/*  72 */     return array()[index];
/*     */   }
/*     */   
/*     */   public void clear() {
/*  76 */     this.elements = (T[])new Object[0];
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  80 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/*  85 */     return new Iterator<T>() {
/*  86 */         private int index = 0;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/*  90 */           return (this.index < ArrayHelper.this.size() && ArrayHelper.this.get(this.index) != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public T next() {
/*  95 */           return ArrayHelper.this.get(this.index++);
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 100 */           ArrayHelper.this.remove(ArrayHelper.this.get(this.index));
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\event\ArrayHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */