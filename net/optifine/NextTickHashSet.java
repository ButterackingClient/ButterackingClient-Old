/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ 
/*     */ public class NextTickHashSet
/*     */   extends TreeSet
/*     */ {
/*  18 */   private LongHashMap longHashMap = new LongHashMap();
/*  19 */   private int minX = Integer.MIN_VALUE;
/*  20 */   private int minZ = Integer.MIN_VALUE;
/*  21 */   private int maxX = Integer.MIN_VALUE;
/*  22 */   private int maxZ = Integer.MIN_VALUE;
/*     */   private static final int UNDEFINED = -2147483648;
/*     */   
/*     */   public NextTickHashSet(Set oldSet) {
/*  26 */     for (Object object : oldSet) {
/*  27 */       add(object);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean contains(Object obj) {
/*  32 */     if (!(obj instanceof NextTickListEntry)) {
/*  33 */       return false;
/*     */     }
/*  35 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*  36 */     Set set = getSubSet(nextticklistentry, false);
/*  37 */     return (set == null) ? false : set.contains(nextticklistentry);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Object obj) {
/*  42 */     if (!(obj instanceof NextTickListEntry)) {
/*  43 */       return false;
/*     */     }
/*  45 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*     */     
/*  47 */     if (nextticklistentry == null) {
/*  48 */       return false;
/*     */     }
/*  50 */     Set<NextTickListEntry> set = getSubSet(nextticklistentry, true);
/*  51 */     boolean flag = set.add(nextticklistentry);
/*  52 */     boolean flag1 = super.add(obj);
/*     */     
/*  54 */     if (flag != flag1) {
/*  55 */       throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
/*     */     }
/*  57 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object obj) {
/*  64 */     if (!(obj instanceof NextTickListEntry)) {
/*  65 */       return false;
/*     */     }
/*  67 */     NextTickListEntry nextticklistentry = (NextTickListEntry)obj;
/*  68 */     Set set = getSubSet(nextticklistentry, false);
/*     */     
/*  70 */     if (set == null) {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean flag = set.remove(nextticklistentry);
/*  74 */     boolean flag1 = super.remove(nextticklistentry);
/*     */     
/*  76 */     if (flag != flag1) {
/*  77 */       throw new IllegalStateException("Added: " + flag + ", addedParent: " + flag1);
/*     */     }
/*  79 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set getSubSet(NextTickListEntry entry, boolean autoCreate) {
/*  86 */     if (entry == null) {
/*  87 */       return null;
/*     */     }
/*  89 */     BlockPos blockpos = entry.position;
/*  90 */     int i = blockpos.getX() >> 4;
/*  91 */     int j = blockpos.getZ() >> 4;
/*  92 */     return getSubSet(i, j, autoCreate);
/*     */   }
/*     */ 
/*     */   
/*     */   private Set getSubSet(int cx, int cz, boolean autoCreate) {
/*  97 */     long i = ChunkCoordIntPair.chunkXZ2Int(cx, cz);
/*  98 */     HashSet hashset = (HashSet)this.longHashMap.getValueByKey(i);
/*     */     
/* 100 */     if (hashset == null && autoCreate) {
/* 101 */       hashset = new HashSet();
/* 102 */       this.longHashMap.add(i, hashset);
/*     */     } 
/*     */     
/* 105 */     return hashset;
/*     */   }
/*     */   
/*     */   public Iterator iterator() {
/* 109 */     if (this.minX == Integer.MIN_VALUE)
/* 110 */       return super.iterator(); 
/* 111 */     if (size() <= 0) {
/* 112 */       return (Iterator)Iterators.emptyIterator();
/*     */     }
/* 114 */     int i = this.minX >> 4;
/* 115 */     int j = this.minZ >> 4;
/* 116 */     int k = this.maxX >> 4;
/* 117 */     int l = this.maxZ >> 4;
/* 118 */     List<Iterator> list = new ArrayList();
/*     */     
/* 120 */     for (int i1 = i; i1 <= k; i1++) {
/* 121 */       for (int j1 = j; j1 <= l; j1++) {
/* 122 */         Set set = getSubSet(i1, j1, false);
/*     */         
/* 124 */         if (set != null) {
/* 125 */           list.add(set.iterator());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     if (list.size() <= 0)
/* 131 */       return (Iterator)Iterators.emptyIterator(); 
/* 132 */     if (list.size() == 1) {
/* 133 */       return list.get(0);
/*     */     }
/* 135 */     return Iterators.concat(list.iterator());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIteratorLimits(int minX, int minZ, int maxX, int maxZ) {
/* 141 */     this.minX = Math.min(minX, maxX);
/* 142 */     this.minZ = Math.min(minZ, maxZ);
/* 143 */     this.maxX = Math.max(minX, maxX);
/* 144 */     this.maxZ = Math.max(minZ, maxZ);
/*     */   }
/*     */   
/*     */   public void clearIteratorLimits() {
/* 148 */     this.minX = Integer.MIN_VALUE;
/* 149 */     this.minZ = Integer.MIN_VALUE;
/* 150 */     this.maxX = Integer.MIN_VALUE;
/* 151 */     this.maxZ = Integer.MIN_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\NextTickHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */