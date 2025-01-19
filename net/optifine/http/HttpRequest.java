/*    */ package net.optifine.http;
/*    */ 
/*    */ import java.net.Proxy;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HttpRequest {
/*  8 */   private String host = null;
/*  9 */   private int port = 0;
/* 10 */   private Proxy proxy = Proxy.NO_PROXY;
/* 11 */   private String method = null;
/* 12 */   private String file = null;
/* 13 */   private String http = null;
/* 14 */   private Map<String, String> headers = new LinkedHashMap<>();
/* 15 */   private byte[] body = null;
/* 16 */   private int redirects = 0;
/*    */   public static final String METHOD_GET = "GET";
/*    */   public static final String METHOD_HEAD = "HEAD";
/*    */   public static final String METHOD_POST = "POST";
/*    */   public static final String HTTP_1_0 = "HTTP/1.0";
/*    */   public static final String HTTP_1_1 = "HTTP/1.1";
/*    */   
/*    */   public HttpRequest(String host, int port, Proxy proxy, String method, String file, String http, Map<String, String> headers, byte[] body) {
/* 24 */     this.host = host;
/* 25 */     this.port = port;
/* 26 */     this.proxy = proxy;
/* 27 */     this.method = method;
/* 28 */     this.file = file;
/* 29 */     this.http = http;
/* 30 */     this.headers = headers;
/* 31 */     this.body = body;
/*    */   }
/*    */   
/*    */   public String getHost() {
/* 35 */     return this.host;
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 39 */     return this.port;
/*    */   }
/*    */   
/*    */   public String getMethod() {
/* 43 */     return this.method;
/*    */   }
/*    */   
/*    */   public String getFile() {
/* 47 */     return this.file;
/*    */   }
/*    */   
/*    */   public String getHttp() {
/* 51 */     return this.http;
/*    */   }
/*    */   
/*    */   public Map<String, String> getHeaders() {
/* 55 */     return this.headers;
/*    */   }
/*    */   
/*    */   public byte[] getBody() {
/* 59 */     return this.body;
/*    */   }
/*    */   
/*    */   public int getRedirects() {
/* 63 */     return this.redirects;
/*    */   }
/*    */   
/*    */   public void setRedirects(int redirects) {
/* 67 */     this.redirects = redirects;
/*    */   }
/*    */   
/*    */   public Proxy getProxy() {
/* 71 */     return this.proxy;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */