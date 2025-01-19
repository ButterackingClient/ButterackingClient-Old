/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.crash.CrashReport;
/*    */ 
/*    */ 
/*    */ public class ReportedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final CrashReport theReportedExceptionCrashReport;
/*    */   
/*    */   public ReportedException(CrashReport report) {
/* 12 */     this.theReportedExceptionCrashReport = report;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CrashReport getCrashReport() {
/* 19 */     return this.theReportedExceptionCrashReport;
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 23 */     return this.theReportedExceptionCrashReport.getCrashCause();
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 27 */     return this.theReportedExceptionCrashReport.getDescription();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ReportedException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */