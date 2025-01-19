/*    */ package net.optifine.http;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FileUploadThread extends Thread {
/*    */   private String urlString;
/*    */   private Map headers;
/*    */   private byte[] content;
/*    */   private IFileUploadListener listener;
/*    */   
/*    */   public FileUploadThread(String urlString, Map headers, byte[] content, IFileUploadListener listener) {
/* 12 */     this.urlString = urlString;
/* 13 */     this.headers = headers;
/* 14 */     this.content = content;
/* 15 */     this.listener = listener;
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     try {
/* 20 */       HttpUtils.post(this.urlString, this.headers, this.content);
/* 21 */       this.listener.fileUploadFinished(this.urlString, this.content, null);
/* 22 */     } catch (Exception exception) {
/* 23 */       this.listener.fileUploadFinished(this.urlString, this.content, exception);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getUrlString() {
/* 28 */     return this.urlString;
/*    */   }
/*    */   
/*    */   public byte[] getContent() {
/* 32 */     return this.content;
/*    */   }
/*    */   
/*    */   public IFileUploadListener getListener() {
/* 36 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\FileUploadThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */