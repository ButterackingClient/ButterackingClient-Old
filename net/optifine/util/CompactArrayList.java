/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class CompactArrayList {
/*     */   private ArrayList list;
/*     */   private int initialCapacity;
/*     */   private float loadFactor;
/*     */   private int countValid;
/*     */   
/*     */   public CompactArrayList() {
/*  12 */     this(10, 0.75F);
/*     */   }
/*     */   
/*     */   public CompactArrayList(int initialCapacity) {
/*  16 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */   
/*     */   public CompactArrayList(int initialCapacity, float loadFactor) {
/*  20 */     this.list = null;
/*  21 */     this.initialCapacity = 0;
/*  22 */     this.loadFactor = 1.0F;
/*  23 */     this.countValid = 0;
/*  24 */     this.list = new ArrayList(initialCapacity);
/*  25 */     this.initialCapacity = initialCapacity;
/*  26 */     this.loadFactor = loadFactor;
/*     */   }
/*     */   
/*     */   public void add(int index, Object element) {
/*  30 */     if (element != null) {
/*  31 */       this.countValid++;
/*     */     }
/*     */     
/*  34 */     this.list.add(index, element);
/*     */   }
/*     */   
/*     */   public boolean add(Object element) {
/*  38 */     if (element != null) {
/*  39 */       this.countValid++;
/*     */     }
/*     */     
/*  42 */     return this.list.add(element);
/*     */   }
/*     */   
/*     */   public Object set(int index, Object element) {
/*  46 */     Object object = this.list.set(index, element);
/*     */     
/*  48 */     if (element != object) {
/*  49 */       if (object == null) {
/*  50 */         this.countValid++;
/*     */       }
/*     */       
/*  53 */       if (element == null) {
/*  54 */         this.countValid--;
/*     */       }
/*     */     } 
/*     */     
/*  58 */     return object;
/*     */   }
/*     */   
/*     */   public Object remove(int index) {
/*  62 */     Object object = this.list.remove(index);
/*     */     
/*  64 */     if (object != null) {
/*  65 */       this.countValid--;
/*     */     }
/*     */     
/*  68 */     return object;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  72 */     this.list.clear();
/*  73 */     this.countValid = 0;
/*     */   }
/*     */   
/*     */   public void compact() {
/*  77 */     if (this.countValid <= 0 && this.list.size() <= 0) {
/*  78 */       clear();
/*  79 */     } else if (this.list.size() > this.initialCapacity) {
/*  80 */       float f = this.countValid * 1.0F / this.list.size();
/*     */       
/*  82 */       if (f <= this.loadFactor) {
/*  83 */         int i = 0;
/*     */         
/*  85 */         for (int j = 0; j < this.list.size(); j++) {
/*  86 */           Object object = this.list.get(j);
/*     */           
/*  88 */           if (object != null) {
/*  89 */             if (j != i) {
/*  90 */               this.list.set(i, object);
/*     */             }
/*     */             
/*  93 */             i++;
/*     */           } 
/*     */         } 
/*     */         
/*  97 */         for (int k = this.list.size() - 1; k >= i; k--) {
/*  98 */           this.list.remove(k);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(Object elem) {
/* 105 */     return this.list.contains(elem);
/*     */   }
/*     */   
/*     */   public Object get(int index) {
/* 109 */     return this.list.get(index);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 113 */     return this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public int size() {
/* 117 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public int getCountValid() {
/* 121 */     return this.countValid;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\CompactArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */