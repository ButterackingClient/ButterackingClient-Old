/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpPipeline
/*     */ {
/*  15 */   private static Map mapConnections = new HashMap<>();
/*     */   public static final String HEADER_USER_AGENT = "User-Agent";
/*     */   public static final String HEADER_HOST = "Host";
/*     */   public static final String HEADER_ACCEPT = "Accept";
/*     */   public static final String HEADER_LOCATION = "Location";
/*     */   public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
/*     */   public static final String HEADER_CONNECTION = "Connection";
/*     */   public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
/*     */   public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
/*     */   public static final String HEADER_VALUE_CHUNKED = "chunked";
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener) throws IOException {
/*  27 */     addRequest(urlStr, listener, Proxy.NO_PROXY);
/*     */   }
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener, Proxy proxy) throws IOException {
/*  31 */     HttpRequest httprequest = makeRequest(urlStr, proxy);
/*  32 */     HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, listener);
/*  33 */     addRequest(httppipelinerequest);
/*     */   }
/*     */   
/*     */   public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
/*  37 */     URL url = new URL(urlStr);
/*     */     
/*  39 */     if (!url.getProtocol().equals("http")) {
/*  40 */       throw new IOException("Only protocol http is supported: " + url);
/*     */     }
/*  42 */     String s = url.getFile();
/*  43 */     String s1 = url.getHost();
/*  44 */     int i = url.getPort();
/*     */     
/*  46 */     if (i <= 0) {
/*  47 */       i = 80;
/*     */     }
/*     */     
/*  50 */     String s2 = "GET";
/*  51 */     String s3 = "HTTP/1.1";
/*  52 */     Map<String, String> map = new LinkedHashMap<>();
/*  53 */     map.put("User-Agent", "Java/" + System.getProperty("java.version"));
/*  54 */     map.put("Host", s1);
/*  55 */     map.put("Accept", "text/html, image/gif, image/png");
/*  56 */     map.put("Connection", "keep-alive");
/*  57 */     byte[] abyte = new byte[0];
/*  58 */     HttpRequest httprequest = new HttpRequest(s1, i, proxy, s2, s, s3, map, abyte);
/*  59 */     return httprequest;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRequest(HttpPipelineRequest pr) {
/*  64 */     HttpRequest httprequest = pr.getHttpRequest();
/*     */     
/*  66 */     for (HttpPipelineConnection httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy()); !httppipelineconnection.addRequest(pr); httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy())) {
/*  67 */       removeConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy(), httppipelineconnection);
/*     */     }
/*     */   }
/*     */   
/*     */   private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
/*  72 */     String s = makeConnectionKey(host, port, proxy);
/*  73 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/*  75 */     if (httppipelineconnection == null) {
/*  76 */       httppipelineconnection = new HttpPipelineConnection(host, port, proxy);
/*  77 */       mapConnections.put(s, httppipelineconnection);
/*     */     } 
/*     */     
/*  80 */     return httppipelineconnection;
/*     */   }
/*     */   
/*     */   private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
/*  84 */     String s = makeConnectionKey(host, port, proxy);
/*  85 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/*  87 */     if (httppipelineconnection == hpc) {
/*  88 */       mapConnections.remove(s);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String makeConnectionKey(String host, int port, Proxy proxy) {
/*  93 */     String s = String.valueOf(host) + ":" + port + "-" + proxy;
/*  94 */     return s;
/*     */   }
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/*  98 */     return get(urlStr, Proxy.NO_PROXY);
/*     */   }
/*     */   
/*     */   public static byte[] get(String urlStr, Proxy proxy) throws IOException {
/* 102 */     if (urlStr.startsWith("file:")) {
/* 103 */       URL url = new URL(urlStr);
/* 104 */       InputStream inputstream = url.openStream();
/* 105 */       byte[] abyte = Config.readAll(inputstream);
/* 106 */       return abyte;
/*     */     } 
/* 108 */     HttpRequest httprequest = makeRequest(urlStr, proxy);
/* 109 */     HttpResponse httpresponse = executeRequest(httprequest);
/*     */     
/* 111 */     if (httpresponse.getStatus() / 100 != 2) {
/* 112 */       throw new IOException("HTTP response: " + httpresponse.getStatus());
/*     */     }
/* 114 */     return httpresponse.getBody();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpResponse executeRequest(HttpRequest req) throws IOException {
/* 120 */     final Map<String, Object> map = new HashMap<>();
/* 121 */     String s = "Response";
/* 122 */     String s1 = "Exception";
/* 123 */     HttpListener httplistener = new HttpListener() {
/*     */         public void finished(HttpRequest req, HttpResponse resp) {
/* 125 */           synchronized (map) {
/* 126 */             map.put("Response", resp);
/* 127 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */         
/*     */         public void failed(HttpRequest req, Exception e) {
/* 132 */           synchronized (map) {
/* 133 */             map.put("Exception", e);
/* 134 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 139 */     synchronized (map) {
/* 140 */       HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(req, httplistener);
/* 141 */       addRequest(httppipelinerequest);
/*     */       
/*     */       try {
/* 144 */         map.wait();
/* 145 */       } catch (InterruptedException var10) {
/* 146 */         throw new InterruptedIOException("Interrupted");
/*     */       } 
/*     */       
/* 149 */       Exception exception = (Exception)map.get("Exception");
/*     */       
/* 151 */       if (exception != null) {
/* 152 */         if (exception instanceof IOException)
/* 153 */           throw (IOException)exception; 
/* 154 */         if (exception instanceof RuntimeException) {
/* 155 */           throw (RuntimeException)exception;
/*     */         }
/* 157 */         throw new RuntimeException(exception.getMessage(), exception);
/*     */       } 
/*     */       
/* 160 */       HttpResponse httpresponse = (HttpResponse)map.get("Response");
/*     */       
/* 162 */       if (httpresponse == null) {
/* 163 */         throw new IOException("Response is null");
/*     */       }
/* 165 */       return httpresponse;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasActiveRequests() {
/* 172 */     for (Object httppipelineconnection0 : mapConnections.values()) {
/* 173 */       HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)httppipelineconnection0;
/* 174 */       if (httppipelineconnection.hasActiveRequests()) {
/* 175 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */