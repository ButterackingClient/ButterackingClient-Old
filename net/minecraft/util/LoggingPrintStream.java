/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LoggingPrintStream
/*    */   extends PrintStream {
/* 10 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   private final String domain;
/*    */   
/*    */   public LoggingPrintStream(String domainIn, OutputStream outStream) {
/* 14 */     super(outStream);
/* 15 */     this.domain = domainIn;
/*    */   }
/*    */   
/*    */   public void println(String p_println_1_) {
/* 19 */     logString(p_println_1_);
/*    */   }
/*    */   
/*    */   public void println(Object p_println_1_) {
/* 23 */     logString(String.valueOf(p_println_1_));
/*    */   }
/*    */   
/*    */   private void logString(String string) {
/* 27 */     StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
/* 28 */     StackTraceElement stacktraceelement = astacktraceelement[Math.min(3, astacktraceelement.length)];
/* 29 */     LOGGER.info("[{}]@.({}:{}): {}", new Object[] { this.domain, stacktraceelement.getFileName(), Integer.valueOf(stacktraceelement.getLineNumber()), string });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\LoggingPrintStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */