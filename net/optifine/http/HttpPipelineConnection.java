/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpPipelineConnection
/*     */ {
/*     */   private String host;
/*     */   private int port;
/*     */   private Proxy proxy;
/*     */   private List<HttpPipelineRequest> listRequests;
/*     */   private List<HttpPipelineRequest> listRequestsSend;
/*     */   private Socket socket;
/*     */   private InputStream inputStream;
/*     */   private OutputStream outputStream;
/*     */   private HttpPipelineSender httpPipelineSender;
/*     */   private HttpPipelineReceiver httpPipelineReceiver;
/*     */   private int countRequests;
/*     */   private boolean responseReceived;
/*     */   private long keepaliveTimeoutMs;
/*     */   private int keepaliveMaxCount;
/*     */   private long timeLastActivityMs;
/*     */   private boolean terminated;
/*     */   private static final String LF = "\n";
/*     */   public static final int TIMEOUT_CONNECT_MS = 5000;
/*     */   public static final int TIMEOUT_READ_MS = 5000;
/*  36 */   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
/*     */   
/*     */   public HttpPipelineConnection(String host, int port) {
/*  39 */     this(host, port, Proxy.NO_PROXY);
/*     */   }
/*     */   
/*     */   public HttpPipelineConnection(String host, int port, Proxy proxy) {
/*  43 */     this.host = null;
/*  44 */     this.port = 0;
/*  45 */     this.proxy = Proxy.NO_PROXY;
/*  46 */     this.listRequests = new LinkedList<>();
/*  47 */     this.listRequestsSend = new LinkedList<>();
/*  48 */     this.socket = null;
/*  49 */     this.inputStream = null;
/*  50 */     this.outputStream = null;
/*  51 */     this.httpPipelineSender = null;
/*  52 */     this.httpPipelineReceiver = null;
/*  53 */     this.countRequests = 0;
/*  54 */     this.responseReceived = false;
/*  55 */     this.keepaliveTimeoutMs = 5000L;
/*  56 */     this.keepaliveMaxCount = 1000;
/*  57 */     this.timeLastActivityMs = System.currentTimeMillis();
/*  58 */     this.terminated = false;
/*  59 */     this.host = host;
/*  60 */     this.port = port;
/*  61 */     this.proxy = proxy;
/*  62 */     this.httpPipelineSender = new HttpPipelineSender(this);
/*  63 */     this.httpPipelineSender.start();
/*  64 */     this.httpPipelineReceiver = new HttpPipelineReceiver(this);
/*  65 */     this.httpPipelineReceiver.start();
/*     */   }
/*     */   
/*     */   public synchronized boolean addRequest(HttpPipelineRequest pr) {
/*  69 */     if (isClosed()) {
/*  70 */       return false;
/*     */     }
/*  72 */     addRequest(pr, this.listRequests);
/*  73 */     addRequest(pr, this.listRequestsSend);
/*  74 */     this.countRequests++;
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addRequest(HttpPipelineRequest pr, List<HttpPipelineRequest> list) {
/*  80 */     list.add(pr);
/*  81 */     notifyAll();
/*     */   }
/*     */   
/*     */   public synchronized void setSocket(Socket s) throws IOException {
/*  85 */     if (!this.terminated) {
/*  86 */       if (this.socket != null) {
/*  87 */         throw new IllegalArgumentException("Already connected");
/*     */       }
/*  89 */       this.socket = s;
/*  90 */       this.socket.setTcpNoDelay(true);
/*  91 */       this.inputStream = this.socket.getInputStream();
/*  92 */       this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
/*  93 */       onActivity();
/*  94 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
/* 100 */     while (this.outputStream == null) {
/* 101 */       checkTimeout();
/* 102 */       wait(1000L);
/*     */     } 
/*     */     
/* 105 */     return this.outputStream;
/*     */   }
/*     */   
/*     */   public synchronized InputStream getInputStream() throws IOException, InterruptedException {
/* 109 */     while (this.inputStream == null) {
/* 110 */       checkTimeout();
/* 111 */       wait(1000L);
/*     */     } 
/*     */     
/* 114 */     return this.inputStream;
/*     */   }
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
/* 118 */     if (this.listRequestsSend.size() <= 0 && this.outputStream != null) {
/* 119 */       this.outputStream.flush();
/*     */     }
/*     */     
/* 122 */     return getNextRequest(this.listRequestsSend, true);
/*     */   }
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
/* 126 */     return getNextRequest(this.listRequests, false);
/*     */   }
/*     */   
/*     */   private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> list, boolean remove) throws InterruptedException {
/* 130 */     while (list.size() <= 0) {
/* 131 */       checkTimeout();
/* 132 */       wait(1000L);
/*     */     } 
/*     */     
/* 135 */     onActivity();
/*     */     
/* 137 */     if (remove) {
/* 138 */       return list.remove(0);
/*     */     }
/* 140 */     return list.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkTimeout() {
/* 145 */     if (this.socket != null) {
/* 146 */       long i = this.keepaliveTimeoutMs;
/*     */       
/* 148 */       if (this.listRequests.size() > 0) {
/* 149 */         i = 5000L;
/*     */       }
/*     */       
/* 152 */       long j = System.currentTimeMillis();
/*     */       
/* 154 */       if (j > this.timeLastActivityMs + i) {
/* 155 */         terminate(new InterruptedException("Timeout " + i));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onActivity() {
/* 161 */     this.timeLastActivityMs = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized void onRequestSent(HttpPipelineRequest pr) {
/* 165 */     if (!this.terminated) {
/* 166 */       onActivity();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void onResponseReceived(HttpPipelineRequest pr, HttpResponse resp) {
/* 171 */     if (!this.terminated) {
/* 172 */       this.responseReceived = true;
/* 173 */       onActivity();
/*     */       
/* 175 */       if (this.listRequests.size() > 0 && this.listRequests.get(0) == pr) {
/* 176 */         this.listRequests.remove(0);
/* 177 */         pr.setClosed(true);
/* 178 */         String s = resp.getHeader("Location");
/*     */         
/* 180 */         if (resp.getStatus() / 100 == 3 && s != null && pr.getHttpRequest().getRedirects() < 5) {
/*     */           try {
/* 182 */             s = normalizeUrl(s, pr.getHttpRequest());
/* 183 */             HttpRequest httprequest = HttpPipeline.makeRequest(s, pr.getHttpRequest().getProxy());
/* 184 */             httprequest.setRedirects(pr.getHttpRequest().getRedirects() + 1);
/* 185 */             HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, pr.getHttpListener());
/* 186 */             HttpPipeline.addRequest(httppipelinerequest);
/* 187 */           } catch (IOException ioexception) {
/* 188 */             pr.getHttpListener().failed(pr.getHttpRequest(), ioexception);
/*     */           } 
/*     */         } else {
/* 191 */           HttpListener httplistener = pr.getHttpListener();
/* 192 */           httplistener.finished(pr.getHttpRequest(), resp);
/*     */         } 
/*     */         
/* 195 */         checkResponseHeader(resp);
/*     */       } else {
/* 197 */         throw new IllegalArgumentException("Response out of order: " + pr);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String normalizeUrl(String url, HttpRequest hr) {
/* 203 */     if (patternFullUrl.matcher(url).matches())
/* 204 */       return url; 
/* 205 */     if (url.startsWith("//")) {
/* 206 */       return "http:" + url;
/*     */     }
/* 208 */     String s = hr.getHost();
/*     */     
/* 210 */     if (hr.getPort() != 80) {
/* 211 */       s = String.valueOf(s) + ":" + hr.getPort();
/*     */     }
/*     */     
/* 214 */     if (url.startsWith("/")) {
/* 215 */       return "http://" + s + url;
/*     */     }
/* 217 */     String s1 = hr.getFile();
/* 218 */     int i = s1.lastIndexOf("/");
/* 219 */     return (i >= 0) ? ("http://" + s + s1.substring(0, i + 1) + url) : ("http://" + s + "/" + url);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkResponseHeader(HttpResponse resp) {
/* 225 */     String s = resp.getHeader("Connection");
/*     */     
/* 227 */     if (s != null && !s.toLowerCase().equals("keep-alive")) {
/* 228 */       terminate(new EOFException("Connection not keep-alive"));
/*     */     }
/*     */     
/* 231 */     String s1 = resp.getHeader("Keep-Alive");
/*     */     
/* 233 */     if (s1 != null) {
/* 234 */       String[] astring = Config.tokenize(s1, ",;");
/*     */       
/* 236 */       for (int i = 0; i < astring.length; i++) {
/* 237 */         String s2 = astring[i];
/* 238 */         String[] astring1 = split(s2, '=');
/*     */         
/* 240 */         if (astring1.length >= 2) {
/* 241 */           if (astring1[0].equals("timeout")) {
/* 242 */             int j = Config.parseInt(astring1[1], -1);
/*     */             
/* 244 */             if (j > 0) {
/* 245 */               this.keepaliveTimeoutMs = (j * 1000);
/*     */             }
/*     */           } 
/*     */           
/* 249 */           if (astring1[0].equals("max")) {
/* 250 */             int k = Config.parseInt(astring1[1], -1);
/*     */             
/* 252 */             if (k > 0) {
/* 253 */               this.keepaliveMaxCount = k;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String[] split(String str, char separator) {
/* 262 */     int i = str.indexOf(separator);
/*     */     
/* 264 */     if (i < 0) {
/* 265 */       return new String[] { str };
/*     */     }
/* 267 */     String s = str.substring(0, i);
/* 268 */     String s1 = str.substring(i + 1);
/* 269 */     return new String[] { s, s1 };
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionSend(HttpPipelineRequest pr, Exception e) {
/* 274 */     terminate(e);
/*     */   }
/*     */   
/*     */   public synchronized void onExceptionReceive(HttpPipelineRequest pr, Exception e) {
/* 278 */     terminate(e);
/*     */   }
/*     */   
/*     */   private synchronized void terminate(Exception e) {
/* 282 */     if (!this.terminated) {
/* 283 */       this.terminated = true;
/* 284 */       terminateRequests(e);
/*     */       
/* 286 */       if (this.httpPipelineSender != null) {
/* 287 */         this.httpPipelineSender.interrupt();
/*     */       }
/*     */       
/* 290 */       if (this.httpPipelineReceiver != null) {
/* 291 */         this.httpPipelineReceiver.interrupt();
/*     */       }
/*     */       
/*     */       try {
/* 295 */         if (this.socket != null) {
/* 296 */           this.socket.close();
/*     */         }
/* 298 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */       
/* 302 */       this.socket = null;
/* 303 */       this.inputStream = null;
/* 304 */       this.outputStream = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void terminateRequests(Exception e) {
/* 309 */     if (this.listRequests.size() > 0) {
/* 310 */       if (!this.responseReceived) {
/* 311 */         HttpPipelineRequest httppipelinerequest = this.listRequests.remove(0);
/* 312 */         httppipelinerequest.getHttpListener().failed(httppipelinerequest.getHttpRequest(), e);
/* 313 */         httppipelinerequest.setClosed(true);
/*     */       } 
/*     */       
/* 316 */       while (this.listRequests.size() > 0) {
/* 317 */         HttpPipelineRequest httppipelinerequest1 = this.listRequests.remove(0);
/* 318 */         HttpPipeline.addRequest(httppipelinerequest1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized boolean isClosed() {
/* 324 */     return this.terminated ? true : ((this.countRequests >= this.keepaliveMaxCount));
/*     */   }
/*     */   
/*     */   public int getCountRequests() {
/* 328 */     return this.countRequests;
/*     */   }
/*     */   
/*     */   public synchronized boolean hasActiveRequests() {
/* 332 */     return (this.listRequests.size() > 0);
/*     */   }
/*     */   
/*     */   public String getHost() {
/* 336 */     return this.host;
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 340 */     return this.port;
/*     */   }
/*     */   
/*     */   public Proxy getProxy() {
/* 344 */     return this.proxy;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpPipelineConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */