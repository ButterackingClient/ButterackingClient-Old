/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ public class IntCache
/*    */ {
/*  8 */   private static int intCacheSize = 256;
/*  9 */   private static List<int[]> freeSmallArrays = Lists.newArrayList();
/* 10 */   private static List<int[]> inUseSmallArrays = Lists.newArrayList();
/* 11 */   private static List<int[]> freeLargeArrays = Lists.newArrayList();
/* 12 */   private static List<int[]> inUseLargeArrays = Lists.newArrayList();
/*    */   
/*    */   public static synchronized int[] getIntCache(int p_76445_0_) {
/* 15 */     if (p_76445_0_ <= 256) {
/* 16 */       if (freeSmallArrays.isEmpty()) {
/* 17 */         int[] aint4 = new int[256];
/* 18 */         inUseSmallArrays.add(aint4);
/* 19 */         return aint4;
/*    */       } 
/* 21 */       int[] aint3 = freeSmallArrays.remove(freeSmallArrays.size() - 1);
/* 22 */       inUseSmallArrays.add(aint3);
/* 23 */       return aint3;
/*    */     } 
/* 25 */     if (p_76445_0_ > intCacheSize) {
/* 26 */       intCacheSize = p_76445_0_;
/* 27 */       freeLargeArrays.clear();
/* 28 */       inUseLargeArrays.clear();
/* 29 */       int[] aint2 = new int[intCacheSize];
/* 30 */       inUseLargeArrays.add(aint2);
/* 31 */       return aint2;
/* 32 */     }  if (freeLargeArrays.isEmpty()) {
/* 33 */       int[] aint1 = new int[intCacheSize];
/* 34 */       inUseLargeArrays.add(aint1);
/* 35 */       return aint1;
/*    */     } 
/* 37 */     int[] aint = freeLargeArrays.remove(freeLargeArrays.size() - 1);
/* 38 */     inUseLargeArrays.add(aint);
/* 39 */     return aint;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void resetIntCache() {
/* 47 */     if (!freeLargeArrays.isEmpty()) {
/* 48 */       freeLargeArrays.remove(freeLargeArrays.size() - 1);
/*    */     }
/*    */     
/* 51 */     if (!freeSmallArrays.isEmpty()) {
/* 52 */       freeSmallArrays.remove(freeSmallArrays.size() - 1);
/*    */     }
/*    */     
/* 55 */     freeLargeArrays.addAll(inUseLargeArrays);
/* 56 */     freeSmallArrays.addAll(inUseSmallArrays);
/* 57 */     inUseLargeArrays.clear();
/* 58 */     inUseSmallArrays.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized String getCacheSizes() {
/* 66 */     return "cache: " + freeLargeArrays.size() + ", tcache: " + freeSmallArrays.size() + ", allocated: " + inUseLargeArrays.size() + ", tallocated: " + inUseSmallArrays.size();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\IntCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */