/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class Cartesian
/*     */ {
/*     */   public static <T> Iterable<T[]> cartesianProduct(Class<T> clazz, Iterable<? extends Iterable<? extends T>> sets) {
/*  17 */     return new Product<>(clazz, toArray(Iterable.class, (Iterable)sets), null);
/*     */   }
/*     */   
/*     */   public static <T> Iterable<List<T>> cartesianProduct(Iterable<? extends Iterable<? extends T>> sets) {
/*  21 */     return arraysAsLists(cartesianProduct(Object.class, sets));
/*     */   }
/*     */   
/*     */   private static <T> Iterable<List<T>> arraysAsLists(Iterable<Object[]> arrays) {
/*  25 */     return Iterables.transform(arrays, new GetList(null));
/*     */   }
/*     */   
/*     */   private static <T> T[] toArray(Class<? super T> clazz, Iterable<? extends T> it) {
/*  29 */     List<T> list = Lists.newArrayList();
/*     */     
/*  31 */     for (T t : it) {
/*  32 */       list.add(t);
/*     */     }
/*     */     
/*  35 */     return list.toArray(createArray(clazz, list.size()));
/*     */   }
/*     */   
/*     */   private static <T> T[] createArray(Class<? super T> p_179319_0_, int p_179319_1_) {
/*  39 */     return (T[])Array.newInstance(p_179319_0_, p_179319_1_);
/*     */   }
/*     */   
/*     */   static class GetList<T>
/*     */     implements Function<Object[], List<T>> {
/*     */     private GetList() {}
/*     */     
/*     */     public List<T> apply(Object[] p_apply_1_) {
/*  47 */       return Arrays.asList((T[])p_apply_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Product<T> implements Iterable<T[]> {
/*     */     private final Class<T> clazz;
/*     */     private final Iterable<? extends T>[] iterables;
/*     */     
/*     */     private Product(Class<T> clazz, Iterable[] iterables) {
/*  56 */       this.clazz = clazz;
/*  57 */       this.iterables = (Iterable<? extends T>[])iterables;
/*     */     }
/*     */     
/*     */     public Iterator<T[]> iterator() {
/*  61 */       return (this.iterables.length <= 0) ? Collections.<T[]>singletonList(Cartesian.createArray(this.clazz, 0)).iterator() : (Iterator<T[]>)new ProductIterator(this.clazz, (Iterable[])this.iterables, null);
/*     */     }
/*     */     
/*     */     static class ProductIterator<T> extends UnmodifiableIterator<T[]> {
/*     */       private int index;
/*     */       private final Iterable<? extends T>[] iterables;
/*     */       private final Iterator<? extends T>[] iterators;
/*     */       private final T[] results;
/*     */       
/*     */       private ProductIterator(Class<T> clazz, Iterable[] iterables) {
/*  71 */         this.index = -2;
/*  72 */         this.iterables = (Iterable<? extends T>[])iterables;
/*  73 */         this.iterators = (Iterator<? extends T>[])Cartesian.createArray((Class)Iterator.class, this.iterables.length);
/*     */         
/*  75 */         for (int i = 0; i < this.iterables.length; i++) {
/*  76 */           this.iterators[i] = iterables[i].iterator();
/*     */         }
/*     */         
/*  79 */         this.results = Cartesian.createArray(clazz, this.iterators.length);
/*     */       }
/*     */       
/*     */       private void endOfData() {
/*  83 */         this.index = -1;
/*  84 */         Arrays.fill((Object[])this.iterators, (Object)null);
/*  85 */         Arrays.fill((Object[])this.results, (Object)null);
/*     */       }
/*     */       
/*     */       public boolean hasNext() {
/*  89 */         if (this.index == -2) {
/*  90 */           this.index = 0; byte b; int i;
/*     */           Iterator<? extends T>[] arrayOfIterator;
/*  92 */           for (i = (arrayOfIterator = this.iterators).length, b = 0; b < i; ) { Iterator<? extends T> iterator1 = arrayOfIterator[b];
/*  93 */             if (!iterator1.hasNext()) {
/*  94 */               endOfData();
/*     */               break;
/*     */             } 
/*     */             b++; }
/*     */           
/*  99 */           return true;
/*     */         } 
/* 101 */         if (this.index >= this.iterators.length) {
/* 102 */           for (this.index = this.iterators.length - 1; this.index >= 0; this.index--) {
/* 103 */             Iterator<? extends T> iterator = this.iterators[this.index];
/*     */             
/* 105 */             if (iterator.hasNext()) {
/*     */               break;
/*     */             }
/*     */             
/* 109 */             if (this.index == 0) {
/* 110 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/* 114 */             iterator = this.iterables[this.index].iterator();
/* 115 */             this.iterators[this.index] = iterator;
/*     */             
/* 117 */             if (!iterator.hasNext()) {
/* 118 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 124 */         return (this.index >= 0);
/*     */       }
/*     */ 
/*     */       
/*     */       public T[] next() {
/* 129 */         if (!hasNext()) {
/* 130 */           throw new NoSuchElementException();
/*     */         }
/* 132 */         while (this.index < this.iterators.length) {
/* 133 */           this.results[this.index] = this.iterators[this.index].next();
/* 134 */           this.index++;
/*     */         } 
/*     */         
/* 137 */         return (T[])this.results.clone();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Cartesian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */