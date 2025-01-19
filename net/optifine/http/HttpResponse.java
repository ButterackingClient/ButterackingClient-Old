/*    */ package net.optifine.http;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HttpResponse {
/*  7 */   private int status = 0;
/*  8 */   private String statusLine = null;
/*  9 */   private Map<String, String> headers = new LinkedHashMap<>();
/* 10 */   private byte[] body = null;
/*    */   
/*    */   public HttpResponse(int status, String statusLine, Map<String, String> headers, byte[] body) {
/* 13 */     this.status = status;
/* 14 */     this.statusLine = statusLine;
/* 15 */     this.headers = headers;
/* 16 */     this.body = body;
/*    */   }
/*    */   
/*    */   public int getStatus() {
/* 20 */     return this.status;
/*    */   }
/*    */   
/*    */   public String getStatusLine() {
/* 24 */     return this.statusLine;
/*    */   }
/*    */   
/*    */   public Map getHeaders() {
/* 28 */     return this.headers;
/*    */   }
/*    */   
/*    */   public String getHeader(String key) {
/* 32 */     return this.headers.get(key);
/*    */   }
/*    */   
/*    */   public byte[] getBody() {
/* 36 */     return this.body;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */