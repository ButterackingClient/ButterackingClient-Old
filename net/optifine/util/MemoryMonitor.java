/*    */ package net.optifine.util;
/*    */ 
/*    */ public class MemoryMonitor {
/*  4 */   private static long startTimeMs = System.currentTimeMillis();
/*  5 */   private static long startMemory = getMemoryUsed();
/*  6 */   private static long lastTimeMs = startTimeMs;
/*  7 */   private static long lastMemory = startMemory;
/*    */   private static boolean gcEvent = false;
/*  9 */   private static int memBytesSec = 0;
/* 10 */   private static long MB = 1048576L;
/*    */   
/*    */   public static void update() {
/* 13 */     long i = System.currentTimeMillis();
/* 14 */     long j = getMemoryUsed();
/* 15 */     gcEvent = (j < lastMemory);
/*    */     
/* 17 */     if (gcEvent) {
/* 18 */       long k = lastTimeMs - startTimeMs;
/* 19 */       long l = lastMemory - startMemory;
/* 20 */       double d0 = k / 1000.0D;
/* 21 */       int i1 = (int)(l / d0);
/*    */       
/* 23 */       if (i1 > 0) {
/* 24 */         memBytesSec = i1;
/*    */       }
/*    */       
/* 27 */       startTimeMs = i;
/* 28 */       startMemory = j;
/*    */     } 
/*    */     
/* 31 */     lastTimeMs = i;
/* 32 */     lastMemory = j;
/*    */   }
/*    */   
/*    */   private static long getMemoryUsed() {
/* 36 */     Runtime runtime = Runtime.getRuntime();
/* 37 */     return runtime.totalMemory() - runtime.freeMemory();
/*    */   }
/*    */   
/*    */   public static long getStartTimeMs() {
/* 41 */     return startTimeMs;
/*    */   }
/*    */   
/*    */   public static long getStartMemoryMb() {
/* 45 */     return startMemory / MB;
/*    */   }
/*    */   
/*    */   public static boolean isGcEvent() {
/* 49 */     return gcEvent;
/*    */   }
/*    */   
/*    */   public static long getAllocationRateMb() {
/* 53 */     return memBytesSec / MB;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\MemoryMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */