/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IntegerCache;
/*     */ 
/*     */ public class VisGraph
/*     */ {
/*  14 */   private static final int field_178616_a = (int)Math.pow(16.0D, 0.0D);
/*  15 */   private static final int field_178614_b = (int)Math.pow(16.0D, 1.0D);
/*  16 */   private static final int field_178615_c = (int)Math.pow(16.0D, 2.0D);
/*  17 */   private final BitSet field_178612_d = new BitSet(4096);
/*  18 */   private static final int[] field_178613_e = new int[1352];
/*  19 */   private int field_178611_f = 4096;
/*     */   
/*     */   public void func_178606_a(BlockPos pos) {
/*  22 */     this.field_178612_d.set(getIndex(pos), true);
/*  23 */     this.field_178611_f--;
/*     */   }
/*     */   
/*     */   private static int getIndex(BlockPos pos) {
/*  27 */     return getIndex(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
/*     */   }
/*     */   
/*     */   private static int getIndex(int x, int y, int z) {
/*  31 */     return x << 0 | y << 8 | z << 4;
/*     */   }
/*     */   
/*     */   public SetVisibility computeVisibility() {
/*  35 */     SetVisibility setvisibility = new SetVisibility();
/*     */     
/*  37 */     if (4096 - this.field_178611_f < 256) {
/*  38 */       setvisibility.setAllVisible(true);
/*  39 */     } else if (this.field_178611_f == 0) {
/*  40 */       setvisibility.setAllVisible(false);
/*     */     } else {
/*  42 */       byte b; int i; int[] arrayOfInt; for (i = (arrayOfInt = field_178613_e).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*  43 */         if (!this.field_178612_d.get(j)) {
/*  44 */           setvisibility.setManyVisible(func_178604_a(j));
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/*  49 */     return setvisibility;
/*     */   }
/*     */   
/*     */   public Set<EnumFacing> func_178609_b(BlockPos pos) {
/*  53 */     return func_178604_a(getIndex(pos));
/*     */   }
/*     */   
/*     */   private Set<EnumFacing> func_178604_a(int p_178604_1_) {
/*  57 */     Set<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/*  58 */     Queue<Integer> queue = new ArrayDeque<>(384);
/*  59 */     queue.add(IntegerCache.getInteger(p_178604_1_));
/*  60 */     this.field_178612_d.set(p_178604_1_, true);
/*     */     
/*  62 */     while (!queue.isEmpty()) {
/*  63 */       int i = ((Integer)queue.poll()).intValue();
/*  64 */       func_178610_a(i, set); byte b; int j;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  66 */       for (j = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  67 */         int k = func_178603_a(i, enumfacing);
/*     */         
/*  69 */         if (k >= 0 && !this.field_178612_d.get(k)) {
/*  70 */           this.field_178612_d.set(k, true);
/*  71 */           queue.add(IntegerCache.getInteger(k));
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*  76 */     return set;
/*     */   }
/*     */   
/*     */   private void func_178610_a(int p_178610_1_, Set<EnumFacing> p_178610_2_) {
/*  80 */     int i = p_178610_1_ >> 0 & 0xF;
/*     */     
/*  82 */     if (i == 0) {
/*  83 */       p_178610_2_.add(EnumFacing.WEST);
/*  84 */     } else if (i == 15) {
/*  85 */       p_178610_2_.add(EnumFacing.EAST);
/*     */     } 
/*     */     
/*  88 */     int j = p_178610_1_ >> 8 & 0xF;
/*     */     
/*  90 */     if (j == 0) {
/*  91 */       p_178610_2_.add(EnumFacing.DOWN);
/*  92 */     } else if (j == 15) {
/*  93 */       p_178610_2_.add(EnumFacing.UP);
/*     */     } 
/*     */     
/*  96 */     int k = p_178610_1_ >> 4 & 0xF;
/*     */     
/*  98 */     if (k == 0) {
/*  99 */       p_178610_2_.add(EnumFacing.NORTH);
/* 100 */     } else if (k == 15) {
/* 101 */       p_178610_2_.add(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int func_178603_a(int p_178603_1_, EnumFacing p_178603_2_) {
/* 106 */     switch (p_178603_2_) {
/*     */       case null:
/* 108 */         if ((p_178603_1_ >> 8 & 0xF) == 0) {
/* 109 */           return -1;
/*     */         }
/*     */         
/* 112 */         return p_178603_1_ - field_178615_c;
/*     */       
/*     */       case UP:
/* 115 */         if ((p_178603_1_ >> 8 & 0xF) == 15) {
/* 116 */           return -1;
/*     */         }
/*     */         
/* 119 */         return p_178603_1_ + field_178615_c;
/*     */       
/*     */       case NORTH:
/* 122 */         if ((p_178603_1_ >> 4 & 0xF) == 0) {
/* 123 */           return -1;
/*     */         }
/*     */         
/* 126 */         return p_178603_1_ - field_178614_b;
/*     */       
/*     */       case SOUTH:
/* 129 */         if ((p_178603_1_ >> 4 & 0xF) == 15) {
/* 130 */           return -1;
/*     */         }
/*     */         
/* 133 */         return p_178603_1_ + field_178614_b;
/*     */       
/*     */       case WEST:
/* 136 */         if ((p_178603_1_ >> 0 & 0xF) == 0) {
/* 137 */           return -1;
/*     */         }
/*     */         
/* 140 */         return p_178603_1_ - field_178616_a;
/*     */       
/*     */       case EAST:
/* 143 */         if ((p_178603_1_ >> 0 & 0xF) == 15) {
/* 144 */           return -1;
/*     */         }
/*     */         
/* 147 */         return p_178603_1_ + field_178616_a;
/*     */     } 
/*     */     
/* 150 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 155 */     int i = 0;
/* 156 */     int j = 15;
/* 157 */     int k = 0;
/*     */     
/* 159 */     for (int l = 0; l < 16; l++) {
/* 160 */       for (int i1 = 0; i1 < 16; i1++) {
/* 161 */         for (int j1 = 0; j1 < 16; j1++) {
/* 162 */           if (l == 0 || l == 15 || i1 == 0 || i1 == 15 || j1 == 0 || j1 == 15)
/* 163 */             field_178613_e[k++] = getIndex(l, i1, j1); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\VisGraph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */