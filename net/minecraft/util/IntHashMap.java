/*     */ package net.minecraft.util;
/*     */ 
/*     */ public class IntHashMap<V> {
/*   4 */   private transient Entry<V>[] slots = (Entry<V>[])new Entry[16];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int count;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  14 */   private int threshold = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  19 */   private final float growFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int computeHash(int integer) {
/*  25 */     integer = integer ^ integer >>> 20 ^ integer >>> 12;
/*  26 */     return integer ^ integer >>> 7 ^ integer >>> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getSlotIndex(int hash, int slotCount) {
/*  33 */     return hash & slotCount - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V lookup(int p_76041_1_) {
/*  40 */     int i = computeHash(p_76041_1_);
/*     */     
/*  42 */     for (Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry) {
/*  43 */       if (entry.hashEntry == p_76041_1_) {
/*  44 */         return entry.valueEntry;
/*     */       }
/*     */     } 
/*     */     
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsItem(int p_76037_1_) {
/*  55 */     return (lookupEntry(p_76037_1_) != null);
/*     */   }
/*     */   
/*     */   final Entry<V> lookupEntry(int p_76045_1_) {
/*  59 */     int i = computeHash(p_76045_1_);
/*     */     
/*  61 */     for (Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry) {
/*  62 */       if (entry.hashEntry == p_76045_1_) {
/*  63 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKey(int p_76038_1_, V p_76038_2_) {
/*  74 */     int i = computeHash(p_76038_1_);
/*  75 */     int j = getSlotIndex(i, this.slots.length);
/*     */     
/*  77 */     for (Entry<V> entry = this.slots[j]; entry != null; entry = entry.nextEntry) {
/*  78 */       if (entry.hashEntry == p_76038_1_) {
/*  79 */         entry.valueEntry = p_76038_2_;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  84 */     insert(i, p_76038_1_, p_76038_2_, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow(int p_76047_1_) {
/*  91 */     Entry<V>[] arrayOfEntry = this.slots;
/*  92 */     int i = arrayOfEntry.length;
/*     */     
/*  94 */     if (i == 1073741824) {
/*  95 */       this.threshold = Integer.MAX_VALUE;
/*     */     } else {
/*  97 */       Entry[] entry1 = new Entry[p_76047_1_];
/*  98 */       copyTo((Entry<V>[])entry1);
/*  99 */       this.slots = (Entry<V>[])entry1;
/* 100 */       this.threshold = (int)(p_76047_1_ * 0.75F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyTo(Entry[] p_76048_1_) {
/* 108 */     Entry<V>[] arrayOfEntry = this.slots;
/* 109 */     int i = p_76048_1_.length;
/*     */     
/* 111 */     for (int j = 0; j < arrayOfEntry.length; j++) {
/* 112 */       Entry<V> entry1 = arrayOfEntry[j];
/*     */       
/* 114 */       if (entry1 != null) {
/* 115 */         Entry<V> entry2; arrayOfEntry[j] = null;
/*     */         
/*     */         do {
/* 118 */           entry2 = entry1.nextEntry;
/* 119 */           int k = getSlotIndex(entry1.slotHash, i);
/* 120 */           entry1.nextEntry = p_76048_1_[k];
/* 121 */           p_76048_1_[k] = entry1;
/* 122 */           entry1 = entry2;
/*     */         }
/* 124 */         while (entry2 != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V removeObject(int p_76049_1_) {
/* 136 */     Entry<V> entry = removeEntry(p_76049_1_);
/* 137 */     return (entry == null) ? null : entry.valueEntry;
/*     */   }
/*     */   
/*     */   final Entry<V> removeEntry(int p_76036_1_) {
/* 141 */     int i = computeHash(p_76036_1_);
/* 142 */     int j = getSlotIndex(i, this.slots.length);
/* 143 */     Entry<V> entry = this.slots[j];
/*     */     
/*     */     Entry<V> entry1;
/*     */     
/* 147 */     for (entry1 = entry; entry1 != null; entry1 = entry2) {
/* 148 */       Entry<V> entry2 = entry1.nextEntry;
/*     */       
/* 150 */       if (entry1.hashEntry == p_76036_1_) {
/* 151 */         this.count--;
/*     */         
/* 153 */         if (entry == entry1) {
/* 154 */           this.slots[j] = entry2;
/*     */         } else {
/* 156 */           entry.nextEntry = entry2;
/*     */         } 
/*     */         
/* 159 */         return entry1;
/*     */       } 
/*     */       
/* 162 */       entry = entry1;
/*     */     } 
/*     */     
/* 165 */     return entry1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMap() {
/* 172 */     Entry<V>[] arrayOfEntry = this.slots;
/*     */     
/* 174 */     for (int i = 0; i < arrayOfEntry.length; i++) {
/* 175 */       arrayOfEntry[i] = null;
/*     */     }
/*     */     
/* 178 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void insert(int p_76040_1_, int p_76040_2_, V p_76040_3_, int p_76040_4_) {
/* 185 */     Entry<V> entry = this.slots[p_76040_4_];
/* 186 */     this.slots[p_76040_4_] = new Entry<>(p_76040_1_, p_76040_2_, p_76040_3_, entry);
/*     */     
/* 188 */     if (this.count++ >= this.threshold)
/* 189 */       grow(2 * this.slots.length); 
/*     */   }
/*     */   
/*     */   static class Entry<V>
/*     */   {
/*     */     final int hashEntry;
/*     */     V valueEntry;
/*     */     Entry<V> nextEntry;
/*     */     final int slotHash;
/*     */     
/*     */     Entry(int p_i1552_1_, int p_i1552_2_, V p_i1552_3_, Entry<V> p_i1552_4_) {
/* 200 */       this.valueEntry = p_i1552_3_;
/* 201 */       this.nextEntry = p_i1552_4_;
/* 202 */       this.hashEntry = p_i1552_2_;
/* 203 */       this.slotHash = p_i1552_1_;
/*     */     }
/*     */     
/*     */     public final int getHash() {
/* 207 */       return this.hashEntry;
/*     */     }
/*     */     
/*     */     public final V getValue() {
/* 211 */       return this.valueEntry;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object p_equals_1_) {
/* 215 */       if (!(p_equals_1_ instanceof Entry)) {
/* 216 */         return false;
/*     */       }
/* 218 */       Entry<V> entry = (Entry<V>)p_equals_1_;
/* 219 */       Object object = Integer.valueOf(getHash());
/* 220 */       Object object1 = Integer.valueOf(entry.getHash());
/*     */       
/* 222 */       if (object == object1 || (object != null && object.equals(object1))) {
/* 223 */         Object object2 = getValue();
/* 224 */         Object object3 = entry.getValue();
/*     */         
/* 226 */         if (object2 == object3 || (object2 != null && object2.equals(object3))) {
/* 227 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 231 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 236 */       return IntHashMap.computeHash(this.hashEntry);
/*     */     }
/*     */     
/*     */     public final String toString() {
/* 240 */       return String.valueOf(getHash()) + "=" + getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\IntHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */