/*    */ package net.optifine;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class Log {
/*  7 */   private static final Logger LOGGER = LogManager.getLogger();
/*  8 */   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
/*    */   
/*    */   public static void detail(String s) {
/* 11 */     if (logDetail) {
/* 12 */       LOGGER.info("[OptiFine] " + s);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void dbg(String s) {
/* 17 */     LOGGER.info("[OptiFine] " + s);
/*    */   }
/*    */   
/*    */   public static void warn(String s) {
/* 21 */     LOGGER.warn("[OptiFine] " + s);
/*    */   }
/*    */   
/*    */   public static void warn(String s, Throwable t) {
/* 25 */     LOGGER.warn("[OptiFine] " + s, t);
/*    */   }
/*    */   
/*    */   public static void error(String s) {
/* 29 */     LOGGER.error("[OptiFine] " + s);
/*    */   }
/*    */   
/*    */   public static void error(String s, Throwable t) {
/* 33 */     LOGGER.error("[OptiFine] " + s, t);
/*    */   }
/*    */   
/*    */   public static void log(String s) {
/* 37 */     dbg(s);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */