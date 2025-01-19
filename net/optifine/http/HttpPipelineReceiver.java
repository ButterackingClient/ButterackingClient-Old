/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpPipelineReceiver
/*     */   extends Thread {
/*  14 */   private HttpPipelineConnection httpPipelineConnection = null;
/*  15 */   private static final Charset ASCII = Charset.forName("ASCII");
/*     */   private static final String HEADER_CONTENT_LENGTH = "Content-Length";
/*     */   private static final char CR = '\r';
/*     */   private static final char LF = '\n';
/*     */   
/*     */   public HttpPipelineReceiver(HttpPipelineConnection httpPipelineConnection) {
/*  21 */     super("HttpPipelineReceiver");
/*  22 */     this.httpPipelineConnection = httpPipelineConnection;
/*     */   }
/*     */   
/*     */   public void run() {
/*  26 */     while (!Thread.interrupted()) {
/*  27 */       HttpPipelineRequest httppipelinerequest = null;
/*     */       
/*     */       try {
/*  30 */         httppipelinerequest = this.httpPipelineConnection.getNextRequestReceive();
/*  31 */         InputStream inputstream = this.httpPipelineConnection.getInputStream();
/*  32 */         HttpResponse httpresponse = readResponse(inputstream);
/*  33 */         this.httpPipelineConnection.onResponseReceived(httppipelinerequest, httpresponse);
/*  34 */       } catch (InterruptedException var4) {
/*     */         return;
/*  36 */       } catch (Exception exception) {
/*  37 */         this.httpPipelineConnection.onExceptionReceive(httppipelinerequest, exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private HttpResponse readResponse(InputStream in) throws IOException {
/*  43 */     String s = readLine(in);
/*  44 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/*  46 */     if (astring.length < 3) {
/*  47 */       throw new IOException("Invalid status line: " + s);
/*     */     }
/*  49 */     String s1 = astring[0];
/*  50 */     int i = Config.parseInt(astring[1], 0);
/*  51 */     String s2 = astring[2];
/*  52 */     Map<String, String> map = new LinkedHashMap<>();
/*     */     
/*     */     while (true) {
/*  55 */       String s3 = readLine(in);
/*     */       
/*  57 */       if (s3.length() <= 0) {
/*  58 */         byte[] abyte = null;
/*  59 */         String s6 = map.get("Content-Length");
/*     */         
/*  61 */         if (s6 != null) {
/*  62 */           int k = Config.parseInt(s6, -1);
/*     */           
/*  64 */           if (k > 0) {
/*  65 */             abyte = new byte[k];
/*  66 */             readFull(abyte, in);
/*     */           } 
/*     */         } else {
/*  69 */           String s7 = map.get("Transfer-Encoding");
/*     */           
/*  71 */           if (Config.equals(s7, "chunked")) {
/*  72 */             abyte = readContentChunked(in);
/*     */           }
/*     */         } 
/*     */         
/*  76 */         return new HttpResponse(i, s, map, abyte);
/*     */       } 
/*     */       
/*  79 */       int j = s3.indexOf(":");
/*     */       
/*  81 */       if (j > 0) {
/*  82 */         String s4 = s3.substring(0, j).trim();
/*  83 */         String s5 = s3.substring(j + 1).trim();
/*  84 */         map.put(s4, s5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] readContentChunked(InputStream in) throws IOException {
/*     */     int i;
/*  91 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/*     */     
/*     */     do {
/*  94 */       String s = readLine(in);
/*  95 */       String[] astring = Config.tokenize(s, "; ");
/*  96 */       i = Integer.parseInt(astring[0], 16);
/*  97 */       byte[] abyte = new byte[i];
/*  98 */       readFull(abyte, in);
/*  99 */       bytearrayoutputstream.write(abyte);
/* 100 */       readLine(in);
/*     */     }
/* 102 */     while (i != 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     return bytearrayoutputstream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFull(byte[] buf, InputStream in) throws IOException {
/* 113 */     for (int i = 0; i < buf.length; i += j) {
/* 114 */       int j = in.read(buf, i, buf.length - i);
/*     */       
/* 116 */       if (j < 0) {
/* 117 */         throw new EOFException();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String readLine(InputStream in) throws IOException {
/* 123 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 124 */     int i = -1;
/* 125 */     boolean flag = false;
/*     */     
/*     */     while (true) {
/* 128 */       int j = in.read();
/*     */       
/* 130 */       if (j < 0) {
/*     */         break;
/*     */       }
/*     */       
/* 134 */       bytearrayoutputstream.write(j);
/*     */       
/* 136 */       if (i == 13 && j == 10) {
/* 137 */         flag = true;
/*     */         
/*     */         break;
/*     */       } 
/* 141 */       i = j;
/*     */     } 
/*     */     
/* 144 */     byte[] abyte = bytearrayoutputstream.toByteArray();
/* 145 */     String s = new String(abyte, ASCII);
/*     */     
/* 147 */     if (flag) {
/* 148 */       s = s.substring(0, s.length() - 2);
/*     */     }
/*     */     
/* 151 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpPipelineReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */