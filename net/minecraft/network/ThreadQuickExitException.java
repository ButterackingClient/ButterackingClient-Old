/*    */ package net.minecraft.network;
/*    */ 
/*    */ public final class ThreadQuickExitException extends RuntimeException {
/*  4 */   public static final ThreadQuickExitException INSTANCE = new ThreadQuickExitException();
/*    */   
/*    */   private ThreadQuickExitException() {
/*  7 */     setStackTrace(new StackTraceElement[0]);
/*    */   }
/*    */   
/*    */   public synchronized Throwable fillInStackTrace() {
/* 11 */     setStackTrace(new StackTraceElement[0]);
/* 12 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\ThreadQuickExitException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */