/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class GlErrors {
/*    */   private static boolean frameStarted = false;
/*  7 */   private static long timeCheckStartMs = -1L;
/*  8 */   private static int countErrors = 0;
/*  9 */   private static int countErrorsSuppressed = 0;
/*    */   private static boolean suppressed = false;
/*    */   private static boolean oneErrorEnabled = false;
/*    */   private static final long CHECK_INTERVAL_MS = 3000L;
/*    */   private static final int CHECK_ERROR_MAX = 10;
/*    */   
/*    */   public static void frameStart() {
/* 16 */     frameStarted = true;
/*    */     
/* 18 */     if (timeCheckStartMs < 0L) {
/* 19 */       timeCheckStartMs = System.currentTimeMillis();
/*    */     }
/*    */     
/* 22 */     if (System.currentTimeMillis() > timeCheckStartMs + 3000L) {
/* 23 */       if (countErrorsSuppressed > 0) {
/* 24 */         Config.error("Suppressed " + countErrors + " OpenGL errors");
/*    */       }
/*    */       
/* 27 */       suppressed = (countErrors > 10);
/* 28 */       timeCheckStartMs = System.currentTimeMillis();
/* 29 */       countErrors = 0;
/* 30 */       countErrorsSuppressed = 0;
/* 31 */       oneErrorEnabled = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isEnabled(int error) {
/* 36 */     if (!frameStarted) {
/* 37 */       return true;
/*    */     }
/* 39 */     countErrors++;
/*    */     
/* 41 */     if (oneErrorEnabled) {
/* 42 */       oneErrorEnabled = false;
/* 43 */       return true;
/*    */     } 
/* 45 */     if (suppressed) {
/* 46 */       countErrorsSuppressed++;
/*    */     }
/*    */     
/* 49 */     return !suppressed;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\GlErrors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */