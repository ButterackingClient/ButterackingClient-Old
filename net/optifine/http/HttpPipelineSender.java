/*    */ package net.optifine.http;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Proxy;
/*    */ import java.net.Socket;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HttpPipelineSender extends Thread {
/* 12 */   private HttpPipelineConnection httpPipelineConnection = null;
/*    */   private static final String CRLF = "\r\n";
/* 14 */   private static Charset ASCII = Charset.forName("ASCII");
/*    */   
/*    */   public HttpPipelineSender(HttpPipelineConnection httpPipelineConnection) {
/* 17 */     super("HttpPipelineSender");
/* 18 */     this.httpPipelineConnection = httpPipelineConnection;
/*    */   }
/*    */   
/*    */   public void run() {
/* 22 */     HttpPipelineRequest httppipelinerequest = null;
/*    */     
/*    */     try {
/* 25 */       connect();
/*    */       
/* 27 */       while (!Thread.interrupted()) {
/* 28 */         httppipelinerequest = this.httpPipelineConnection.getNextRequestSend();
/* 29 */         HttpRequest httprequest = httppipelinerequest.getHttpRequest();
/* 30 */         OutputStream outputstream = this.httpPipelineConnection.getOutputStream();
/* 31 */         writeRequest(httprequest, outputstream);
/* 32 */         this.httpPipelineConnection.onRequestSent(httppipelinerequest);
/*    */       } 
/* 34 */     } catch (InterruptedException var4) {
/*    */       return;
/* 36 */     } catch (Exception exception) {
/* 37 */       this.httpPipelineConnection.onExceptionSend(httppipelinerequest, exception);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void connect() throws IOException {
/* 42 */     String s = this.httpPipelineConnection.getHost();
/* 43 */     int i = this.httpPipelineConnection.getPort();
/* 44 */     Proxy proxy = this.httpPipelineConnection.getProxy();
/* 45 */     Socket socket = new Socket(proxy);
/* 46 */     socket.connect(new InetSocketAddress(s, i), 5000);
/* 47 */     this.httpPipelineConnection.setSocket(socket);
/*    */   }
/*    */   
/*    */   private void writeRequest(HttpRequest req, OutputStream out) throws IOException {
/* 51 */     write(out, String.valueOf(req.getMethod()) + " " + req.getFile() + " " + req.getHttp() + "\r\n");
/* 52 */     Map<String, String> map = req.getHeaders();
/*    */     
/* 54 */     for (String s : map.keySet()) {
/* 55 */       String s1 = req.getHeaders().get(s);
/* 56 */       write(out, String.valueOf(s) + ": " + s1 + "\r\n");
/*    */     } 
/*    */     
/* 59 */     write(out, "\r\n");
/*    */   }
/*    */   
/*    */   private void write(OutputStream out, String str) throws IOException {
/* 63 */     byte[] abyte = str.getBytes(ASCII);
/* 64 */     out.write(abyte);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpPipelineSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */