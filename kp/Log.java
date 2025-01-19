/*    */ package kp;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ 
/*    */ public class Log
/*    */ {
/* 10 */   private static final PrintStream printstream = new PrintStream(new FileOutputStream(FileDescriptor.out));
/*    */ 
/*    */   
/*    */   public static void i(Object s) {
/* 14 */     printstream.print("[KoreanPatch] ");
/* 15 */     printstream.println(s);
/*    */   }
/*    */   
/*    */   public static void warn(Object s) {
/* 19 */     printstream.print("[KoreanPatch][WARNING] ");
/* 20 */     printstream.println(s);
/*    */   }
/*    */   
/*    */   public static void error(Object s) {
/* 24 */     printstream.print("[KoreanPatch][ERROR] ");
/* 25 */     printstream.println(s);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */