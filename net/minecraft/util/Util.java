/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static EnumOS getOSType() {
/* 10 */     String s = System.getProperty("os.name").toLowerCase();
/* 11 */     return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
/*    */   }
/*    */   
/*    */   public static <V> V runTask(FutureTask<V> task, Logger logger) {
/*    */     try {
/* 16 */       task.run();
/* 17 */       return task.get();
/* 18 */     } catch (ExecutionException executionexception) {
/* 19 */       logger.fatal("Error executing task", executionexception);
/*    */       
/* 21 */       if (executionexception.getCause() instanceof OutOfMemoryError) {
/* 22 */         OutOfMemoryError outofmemoryerror = (OutOfMemoryError)executionexception.getCause();
/* 23 */         throw outofmemoryerror;
/*    */       } 
/* 25 */     } catch (InterruptedException interruptedexception) {
/* 26 */       logger.fatal("Error executing task", interruptedexception);
/*    */     } 
/*    */     
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public enum EnumOS {
/* 33 */     LINUX,
/* 34 */     SOLARIS,
/* 35 */     WINDOWS,
/* 36 */     OSX,
/* 37 */     UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */