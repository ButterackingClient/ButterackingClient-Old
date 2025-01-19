/*     */ package net.minecraft.util;
/*     */ 
/*     */ public class LongHashMap<V> {
/*   4 */   private transient Entry<V>[] hashArray = (Entry<V>[])new Entry[4096];
/*     */ 
/*     */ 
/*     */   
/*     */   private transient int numHashElements;
/*     */ 
/*     */ 
/*     */   
/*     */   private int mask;
/*     */ 
/*     */   
/*  15 */   private int capacity = 3072;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   private final float percentUseable = 0.75F;
/*     */ 
/*     */   
/*     */   private volatile transient int modCount;
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHashMap() {
/*  28 */     this.mask = this.hashArray.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getHashedKey(long originalKey) {
/*  35 */     return (int)(originalKey ^ originalKey >>> 27L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(int integer) {
/*  42 */     integer = integer ^ integer >>> 20 ^ integer >>> 12;
/*  43 */     return integer ^ integer >>> 7 ^ integer >>> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getHashIndex(int p_76158_0_, int p_76158_1_) {
/*  50 */     return p_76158_0_ & p_76158_1_;
/*     */   }
/*     */   
/*     */   public int getNumHashElements() {
/*  54 */     return this.numHashElements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V getValueByKey(long p_76164_1_) {
/*  61 */     int i = getHashedKey(p_76164_1_);
/*     */     
/*  63 */     for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
/*  64 */       if (entry.key == p_76164_1_) {
/*  65 */         return entry.value;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   public boolean containsItem(long p_76161_1_) {
/*  73 */     return (getEntry(p_76161_1_) != null);
/*     */   }
/*     */   
/*     */   final Entry<V> getEntry(long p_76160_1_) {
/*  77 */     int i = getHashedKey(p_76160_1_);
/*     */     
/*  79 */     for (Entry<V> entry = this.hashArray[getHashIndex(i, this.mask)]; entry != null; entry = entry.nextEntry) {
/*  80 */       if (entry.key == p_76160_1_) {
/*  81 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long p_76163_1_, V p_76163_3_) {
/*  92 */     int i = getHashedKey(p_76163_1_);
/*  93 */     int j = getHashIndex(i, this.mask);
/*     */     
/*  95 */     for (Entry<V> entry = this.hashArray[j]; entry != null; entry = entry.nextEntry) {
/*  96 */       if (entry.key == p_76163_1_) {
/*  97 */         entry.value = p_76163_3_;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 102 */     this.modCount++;
/* 103 */     createKey(i, p_76163_1_, p_76163_3_, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resizeTable(int p_76153_1_) {
/* 110 */     Entry<V>[] arrayOfEntry = this.hashArray;
/* 111 */     int i = arrayOfEntry.length;
/*     */     
/* 113 */     if (i == 1073741824) {
/* 114 */       this.capacity = Integer.MAX_VALUE;
/*     */     } else {
/* 116 */       Entry[] entry1 = new Entry[p_76153_1_];
/* 117 */       copyHashTableTo((Entry<V>[])entry1);
/* 118 */       this.hashArray = (Entry<V>[])entry1;
/* 119 */       this.mask = this.hashArray.length - 1;
/* 120 */       float f = p_76153_1_;
/* 121 */       getClass();
/* 122 */       this.capacity = (int)(f * 0.75F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyHashTableTo(Entry[] p_76154_1_) {
/* 130 */     Entry<V>[] arrayOfEntry = this.hashArray;
/* 131 */     int i = p_76154_1_.length;
/*     */     
/* 133 */     for (int j = 0; j < arrayOfEntry.length; j++) {
/* 134 */       Entry<V> entry1 = arrayOfEntry[j];
/*     */       
/* 136 */       if (entry1 != null) {
/* 137 */         Entry<V> entry2; arrayOfEntry[j] = null;
/*     */         
/*     */         do {
/* 140 */           entry2 = entry1.nextEntry;
/* 141 */           int k = getHashIndex(entry1.hash, i - 1);
/* 142 */           entry1.nextEntry = p_76154_1_[k];
/* 143 */           p_76154_1_[k] = entry1;
/* 144 */           entry1 = entry2;
/*     */         }
/* 146 */         while (entry2 != null);
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
/*     */   public V remove(long p_76159_1_) {
/* 158 */     Entry<V> entry = removeKey(p_76159_1_);
/* 159 */     return (entry == null) ? null : entry.value;
/*     */   }
/*     */   
/*     */   final Entry<V> removeKey(long p_76152_1_) {
/* 163 */     int i = getHashedKey(p_76152_1_);
/* 164 */     int j = getHashIndex(i, this.mask);
/* 165 */     Entry<V> entry = this.hashArray[j];
/*     */     
/*     */     Entry<V> entry1;
/*     */     
/* 169 */     for (entry1 = entry; entry1 != null; entry1 = entry2) {
/* 170 */       Entry<V> entry2 = entry1.nextEntry;
/*     */       
/* 172 */       if (entry1.key == p_76152_1_) {
/* 173 */         this.modCount++;
/* 174 */         this.numHashElements--;
/*     */         
/* 176 */         if (entry == entry1) {
/* 177 */           this.hashArray[j] = entry2;
/*     */         } else {
/* 179 */           entry.nextEntry = entry2;
/*     */         } 
/*     */         
/* 182 */         return entry1;
/*     */       } 
/*     */       
/* 185 */       entry = entry1;
/*     */     } 
/*     */     
/* 188 */     return entry1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createKey(int p_76156_1_, long p_76156_2_, V p_76156_4_, int p_76156_5_) {
/* 195 */     Entry<V> entry = this.hashArray[p_76156_5_];
/* 196 */     this.hashArray[p_76156_5_] = new Entry<>(p_76156_1_, p_76156_2_, p_76156_4_, entry);
/*     */     
/* 198 */     if (this.numHashElements++ >= this.capacity) {
/* 199 */       resizeTable(2 * this.hashArray.length);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getKeyDistribution() {
/* 204 */     int i = 0;
/*     */     
/* 206 */     for (int j = 0; j < this.hashArray.length; j++) {
/* 207 */       if (this.hashArray[j] != null) {
/* 208 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     return 1.0D * i / this.numHashElements;
/*     */   }
/*     */   
/*     */   static class Entry<V> {
/*     */     final long key;
/*     */     V value;
/*     */     Entry<V> nextEntry;
/*     */     final int hash;
/*     */     
/*     */     Entry(int p_i1553_1_, long p_i1553_2_, V p_i1553_4_, Entry<V> p_i1553_5_) {
/* 222 */       this.value = p_i1553_4_;
/* 223 */       this.nextEntry = p_i1553_5_;
/* 224 */       this.key = p_i1553_2_;
/* 225 */       this.hash = p_i1553_1_;
/*     */     }
/*     */     
/*     */     public final long getKey() {
/* 229 */       return this.key;
/*     */     }
/*     */     
/*     */     public final V getValue() {
/* 233 */       return this.value;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object p_equals_1_) {
/* 237 */       if (!(p_equals_1_ instanceof Entry)) {
/* 238 */         return false;
/*     */       }
/* 240 */       Entry<V> entry = (Entry<V>)p_equals_1_;
/* 241 */       Object object = Long.valueOf(getKey());
/* 242 */       Object object1 = Long.valueOf(entry.getKey());
/*     */       
/* 244 */       if (object == object1 || (object != null && object.equals(object1))) {
/* 245 */         Object object2 = getValue();
/* 246 */         Object object3 = entry.getValue();
/*     */         
/* 248 */         if (object2 == object3 || (object2 != null && object2.equals(object3))) {
/* 249 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 253 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 258 */       return LongHashMap.getHashedKey(this.key);
/*     */     }
/*     */     
/*     */     public final String toString() {
/* 262 */       return String.valueOf(getKey()) + "=" + getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\LongHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */