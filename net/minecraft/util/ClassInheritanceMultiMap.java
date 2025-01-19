/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.optifine.util.IteratorCache;
/*     */ 
/*     */ public class ClassInheritanceMultiMap<T>
/*     */   extends AbstractSet<T>
/*     */ {
/*  19 */   private static final Set<Class<?>> field_181158_a = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*  20 */   private final Map<Class<?>, List<T>> map = Maps.newHashMap();
/*  21 */   private final Set<Class<?>> knownKeys = Sets.newIdentityHashSet();
/*     */   private final Class<T> baseClass;
/*  23 */   private final List<T> values = Lists.newArrayList();
/*     */   public boolean empty;
/*     */   
/*     */   public ClassInheritanceMultiMap(Class<T> baseClassIn) {
/*  27 */     this.baseClass = baseClassIn;
/*  28 */     this.knownKeys.add(baseClassIn);
/*  29 */     this.map.put(baseClassIn, this.values);
/*     */     
/*  31 */     for (Class<?> oclass : field_181158_a) {
/*  32 */       createLookup(oclass);
/*     */     }
/*     */     
/*  35 */     this.empty = (this.values.size() == 0);
/*     */   }
/*     */   
/*     */   protected void createLookup(Class<?> clazz) {
/*  39 */     field_181158_a.add(clazz);
/*  40 */     int i = this.values.size();
/*     */     
/*  42 */     for (int j = 0; j < i; j++) {
/*  43 */       T t = this.values.get(j);
/*     */       
/*  45 */       if (clazz.isAssignableFrom(t.getClass())) {
/*  46 */         addForClass(t, clazz);
/*     */       }
/*     */     } 
/*     */     
/*  50 */     this.knownKeys.add(clazz);
/*     */   }
/*     */   
/*     */   protected Class<?> initializeClassLookup(Class<?> clazz) {
/*  54 */     if (this.baseClass.isAssignableFrom(clazz)) {
/*  55 */       if (!this.knownKeys.contains(clazz)) {
/*  56 */         createLookup(clazz);
/*     */       }
/*     */       
/*  59 */       return clazz;
/*     */     } 
/*  61 */     throw new IllegalArgumentException("Don't know how to search for " + clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(T p_add_1_) {
/*  66 */     for (Class<?> oclass : this.knownKeys) {
/*  67 */       if (oclass.isAssignableFrom(p_add_1_.getClass())) {
/*  68 */         addForClass(p_add_1_, oclass);
/*     */       }
/*     */     } 
/*     */     
/*  72 */     this.empty = (this.values.size() == 0);
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   private void addForClass(T value, Class<?> parentClass) {
/*  77 */     List<T> list = this.map.get(parentClass);
/*     */     
/*  79 */     if (list == null) {
/*  80 */       this.map.put(parentClass, Lists.newArrayList(new Object[] { value }));
/*     */     } else {
/*  82 */       list.add(value);
/*     */     } 
/*     */     
/*  85 */     this.empty = (this.values.size() == 0);
/*     */   }
/*     */   
/*     */   public boolean remove(Object p_remove_1_) {
/*  89 */     T t = (T)p_remove_1_;
/*  90 */     boolean flag = false;
/*     */     
/*  92 */     for (Class<?> oclass : this.knownKeys) {
/*  93 */       if (oclass.isAssignableFrom(t.getClass())) {
/*  94 */         List<T> list = this.map.get(oclass);
/*     */         
/*  96 */         if (list != null && list.remove(t)) {
/*  97 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     this.empty = (this.values.size() == 0);
/* 103 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean contains(Object p_contains_1_) {
/* 107 */     return Iterators.contains(getByClass(p_contains_1_.getClass()).iterator(), p_contains_1_);
/*     */   }
/*     */   
/*     */   public <S> Iterable<S> getByClass(final Class<S> clazz) {
/* 111 */     return new Iterable<S>() {
/*     */         public Iterator<S> iterator() {
/* 113 */           List<T> list = (List<T>)ClassInheritanceMultiMap.this.map.get(ClassInheritanceMultiMap.this.initializeClassLookup(clazz));
/*     */           
/* 115 */           if (list == null) {
/* 116 */             return (Iterator<S>)Iterators.emptyIterator();
/*     */           }
/* 118 */           Iterator<T> iterator = list.iterator();
/* 119 */           return (Iterator<S>)Iterators.filter(iterator, clazz);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 126 */     return this.values.isEmpty() ? (Iterator<T>)Iterators.emptyIterator() : IteratorCache.getReadOnly(this.values);
/*     */   }
/*     */   
/*     */   public int size() {
/* 130 */     return this.values.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 134 */     return this.empty;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ClassInheritanceMultiMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */