/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public abstract class SMCLog {
/*  7 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   private static final String PREFIX = "[Shaders] ";
/*    */   
/*    */   public static void severe(String message) {
/* 11 */     LOGGER.error("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void warning(String message) {
/* 15 */     LOGGER.warn("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void info(String message) {
/* 19 */     LOGGER.info("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void fine(String message) {
/* 23 */     LOGGER.debug("[Shaders] " + message);
/*    */   }
/*    */   
/*    */   public static void severe(String format, Object... args) {
/* 27 */     String s = String.format(format, args);
/* 28 */     LOGGER.error("[Shaders] " + s);
/*    */   }
/*    */   
/*    */   public static void warning(String format, Object... args) {
/* 32 */     String s = String.format(format, args);
/* 33 */     LOGGER.warn("[Shaders] " + s);
/*    */   }
/*    */   
/*    */   public static void info(String format, Object... args) {
/* 37 */     String s = String.format(format, args);
/* 38 */     LOGGER.info("[Shaders] " + s);
/*    */   }
/*    */   
/*    */   public static void fine(String format, Object... args) {
/* 42 */     String s = String.format(format, args);
/* 43 */     LOGGER.debug("[Shaders] " + s);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\SMCLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */