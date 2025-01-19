/*    */ package net.optifine.http;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class FileDownloadThread extends Thread {
/*  6 */   private String urlString = null;
/*  7 */   private IFileDownloadListener listener = null;
/*    */   
/*    */   public FileDownloadThread(String urlString, IFileDownloadListener listener) {
/* 10 */     this.urlString = urlString;
/* 11 */     this.listener = listener;
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     try {
/* 16 */       byte[] abyte = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
/* 17 */       this.listener.fileDownloadFinished(this.urlString, abyte, null);
/* 18 */     } catch (Exception exception) {
/* 19 */       this.listener.fileDownloadFinished(this.urlString, null, exception);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getUrlString() {
/* 24 */     return this.urlString;
/*    */   }
/*    */   
/*    */   public IFileDownloadListener getListener() {
/* 28 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\FileDownloadThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */