/*    */ package net.optifine.http;
/*    */ 
/*    */ public class HttpPipelineRequest {
/*  4 */   private HttpRequest httpRequest = null;
/*  5 */   private HttpListener httpListener = null;
/*    */   private boolean closed = false;
/*    */   
/*    */   public HttpPipelineRequest(HttpRequest httpRequest, HttpListener httpListener) {
/*  9 */     this.httpRequest = httpRequest;
/* 10 */     this.httpListener = httpListener;
/*    */   }
/*    */   
/*    */   public HttpRequest getHttpRequest() {
/* 14 */     return this.httpRequest;
/*    */   }
/*    */   
/*    */   public HttpListener getHttpListener() {
/* 18 */     return this.httpListener;
/*    */   }
/*    */   
/*    */   public boolean isClosed() {
/* 22 */     return this.closed;
/*    */   }
/*    */   
/*    */   public void setClosed(boolean closed) {
/* 26 */     this.closed = closed;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpPipelineRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */