/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayDeque;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class CacheObjectArray
/*     */ {
/*  10 */   private static ArrayDeque<int[]> arrays = (ArrayDeque)new ArrayDeque<>();
/*  11 */   private static int maxCacheSize = 10;
/*     */   
/*     */   private static synchronized int[] allocateArray(int size) {
/*  14 */     int[] aint = arrays.pollLast();
/*     */     
/*  16 */     if (aint == null || aint.length < size) {
/*  17 */       aint = new int[size];
/*     */     }
/*     */     
/*  20 */     return aint;
/*     */   }
/*     */   
/*     */   public static synchronized void freeArray(int[] ints) {
/*  24 */     if (arrays.size() < maxCacheSize) {
/*  25 */       arrays.add(ints);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  30 */     int i = 4096;
/*  31 */     int j = 500000;
/*  32 */     testNew(i, j);
/*  33 */     testClone(i, j);
/*  34 */     testNewObj(i, j);
/*  35 */     testCloneObj(i, j);
/*  36 */     testNewObjDyn(IBlockState.class, i, j);
/*  37 */     long k = testNew(i, j);
/*  38 */     long l = testClone(i, j);
/*  39 */     long i1 = testNewObj(i, j);
/*  40 */     long j1 = testCloneObj(i, j);
/*  41 */     long k1 = testNewObjDyn(IBlockState.class, i, j);
/*  42 */     Config.dbg("New: " + k);
/*  43 */     Config.dbg("Clone: " + l);
/*  44 */     Config.dbg("NewObj: " + i1);
/*  45 */     Config.dbg("CloneObj: " + j1);
/*  46 */     Config.dbg("NewObjDyn: " + k1);
/*     */   }
/*     */   
/*     */   private static long testClone(int size, int count) {
/*  50 */     long i = System.currentTimeMillis();
/*  51 */     int[] aint = new int[size];
/*     */     
/*  53 */     for (int j = 0; j < count; j++) {
/*  54 */       int[] arrayOfInt = (int[])aint.clone();
/*     */     }
/*     */     
/*  57 */     long k = System.currentTimeMillis();
/*  58 */     return k - i;
/*     */   }
/*     */   
/*     */   private static long testNew(int size, int count) {
/*  62 */     long i = System.currentTimeMillis();
/*     */     
/*  64 */     for (int j = 0; j < count; j++) {
/*  65 */       int[] arrayOfInt = (int[])Array.newInstance(int.class, size);
/*     */     }
/*     */     
/*  68 */     long k = System.currentTimeMillis();
/*  69 */     return k - i;
/*     */   }
/*     */   
/*     */   private static long testCloneObj(int size, int count) {
/*  73 */     long i = System.currentTimeMillis();
/*  74 */     IBlockState[] aiblockstate = new IBlockState[size];
/*     */     
/*  76 */     for (int j = 0; j < count; j++) {
/*  77 */       IBlockState[] arrayOfIBlockState = (IBlockState[])aiblockstate.clone();
/*     */     }
/*     */     
/*  80 */     long k = System.currentTimeMillis();
/*  81 */     return k - i;
/*     */   }
/*     */   
/*     */   private static long testNewObj(int size, int count) {
/*  85 */     long i = System.currentTimeMillis();
/*     */     
/*  87 */     for (int j = 0; j < count; j++) {
/*  88 */       IBlockState[] arrayOfIBlockState = new IBlockState[size];
/*     */     }
/*     */     
/*  91 */     long k = System.currentTimeMillis();
/*  92 */     return k - i;
/*     */   }
/*     */   
/*     */   private static long testNewObjDyn(Class<?> cls, int size, int count) {
/*  96 */     long i = System.currentTimeMillis();
/*     */     
/*  98 */     for (int j = 0; j < count; j++) {
/*  99 */       Object[] arrayOfObject = (Object[])Array.newInstance(cls, size);
/*     */     }
/*     */     
/* 102 */     long k = System.currentTimeMillis();
/* 103 */     return k - i;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\CacheObjectArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */