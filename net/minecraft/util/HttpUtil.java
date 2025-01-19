/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListeningExecutorService;
/*     */ import com.google.common.util.concurrent.MoreExecutors;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpUtil
/*     */ {
/*  34 */   public static final ListeningExecutorService field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final AtomicInteger downloadThreadsStarted = new AtomicInteger(0);
/*  40 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildPostString(Map<String, Object> data) {
/*  46 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  48 */     for (Map.Entry<String, Object> entry : data.entrySet()) {
/*  49 */       if (stringbuilder.length() > 0) {
/*  50 */         stringbuilder.append('&');
/*     */       }
/*     */       
/*     */       try {
/*  54 */         stringbuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
/*  55 */       } catch (UnsupportedEncodingException unsupportedencodingexception1) {
/*  56 */         unsupportedencodingexception1.printStackTrace();
/*     */       } 
/*     */       
/*  59 */       if (entry.getValue() != null) {
/*  60 */         stringbuilder.append('=');
/*     */         
/*     */         try {
/*  63 */           stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
/*  64 */         } catch (UnsupportedEncodingException unsupportedencodingexception) {
/*  65 */           unsupportedencodingexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String postMap(URL url, Map<String, Object> data, boolean skipLoggingErrors) {
/*  77 */     return post(url, buildPostString(data), skipLoggingErrors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String post(URL url, String content, boolean skipLoggingErrors) {
/*     */     try {
/*  85 */       Proxy proxy = (MinecraftServer.getServer() == null) ? null : MinecraftServer.getServer().getServerProxy();
/*     */       
/*  87 */       if (proxy == null) {
/*  88 */         proxy = Proxy.NO_PROXY;
/*     */       }
/*     */       
/*  91 */       HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(proxy);
/*  92 */       httpurlconnection.setRequestMethod("POST");
/*  93 */       httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*  94 */       httpurlconnection.setRequestProperty("Content-Length", (content.getBytes()).length);
/*  95 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/*  96 */       httpurlconnection.setUseCaches(false);
/*  97 */       httpurlconnection.setDoInput(true);
/*  98 */       httpurlconnection.setDoOutput(true);
/*  99 */       DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
/* 100 */       dataoutputstream.writeBytes(content);
/* 101 */       dataoutputstream.flush();
/* 102 */       dataoutputstream.close();
/* 103 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 104 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s;
/* 107 */       while ((s = bufferedreader.readLine()) != null) {
/* 108 */         stringbuffer.append(s);
/* 109 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 112 */       bufferedreader.close();
/* 113 */       return stringbuffer.toString();
/* 114 */     } catch (Exception exception) {
/* 115 */       if (!skipLoggingErrors) {
/* 116 */         logger.error("Could not post to " + url, exception);
/*     */       }
/*     */       
/* 119 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ListenableFuture<Object> downloadResourcePack(final File saveFile, final String packUrl, final Map<String, String> p_180192_2_, final int maxSize, final IProgressUpdate p_180192_4_, final Proxy p_180192_5_) {
/* 124 */     ListenableFuture<?> listenablefuture = field_180193_a.submit(new Runnable() {
/*     */           public void run() {
/* 126 */             HttpURLConnection httpurlconnection = null;
/* 127 */             InputStream inputstream = null;
/* 128 */             OutputStream outputstream = null;
/*     */             
/* 130 */             if (p_180192_4_ != null) {
/* 131 */               p_180192_4_.resetProgressAndMessage("Downloading Resource Pack");
/* 132 */               p_180192_4_.displayLoadingString("Making Request...");
/*     */             } 
/*     */ 
/*     */             
/*     */             try {
/* 137 */               byte[] abyte = new byte[4096];
/* 138 */               URL url = new URL(packUrl);
/* 139 */               httpurlconnection = (HttpURLConnection)url.openConnection(p_180192_5_);
/* 140 */               float f = 0.0F;
/* 141 */               float f1 = p_180192_2_.entrySet().size();
/*     */               
/* 143 */               for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)p_180192_2_.entrySet()) {
/* 144 */                 httpurlconnection.setRequestProperty(entry.getKey(), entry.getValue());
/*     */                 
/* 146 */                 if (p_180192_4_ != null) {
/* 147 */                   p_180192_4_.setLoadingProgress((int)(++f / f1 * 100.0F));
/*     */                 }
/*     */               } 
/*     */               
/* 151 */               inputstream = httpurlconnection.getInputStream();
/* 152 */               f1 = httpurlconnection.getContentLength();
/* 153 */               int i = httpurlconnection.getContentLength();
/*     */               
/* 155 */               if (p_180192_4_ != null) {
/* 156 */                 p_180192_4_.displayLoadingString(String.format("Downloading file (%.2f MB)...", new Object[] { Float.valueOf(f1 / 1000.0F / 1000.0F) }));
/*     */               }
/*     */               
/* 159 */               if (saveFile.exists()) {
/* 160 */                 long j = saveFile.length();
/*     */                 
/* 162 */                 if (j == i) {
/* 163 */                   if (p_180192_4_ != null) {
/* 164 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 170 */                 HttpUtil.logger.warn("Deleting " + saveFile + " as it does not match what we currently have (" + i + " vs our " + j + ").");
/* 171 */                 FileUtils.deleteQuietly(saveFile);
/* 172 */               } else if (saveFile.getParentFile() != null) {
/* 173 */                 saveFile.getParentFile().mkdirs();
/*     */               } 
/*     */               
/* 176 */               outputstream = new DataOutputStream(new FileOutputStream(saveFile));
/*     */               
/* 178 */               if (maxSize > 0 && f1 > maxSize) {
/* 179 */                 if (p_180192_4_ != null) {
/* 180 */                   p_180192_4_.setDoneWorking();
/*     */                 }
/*     */                 
/* 183 */                 throw new IOException("Filesize is bigger than maximum allowed (file is " + f + ", limit is " + maxSize + ")");
/*     */               } 
/*     */               
/* 186 */               int k = 0;
/*     */               
/* 188 */               while ((k = inputstream.read(abyte)) >= 0) {
/* 189 */                 f += k;
/*     */                 
/* 191 */                 if (p_180192_4_ != null) {
/* 192 */                   p_180192_4_.setLoadingProgress((int)(f / f1 * 100.0F));
/*     */                 }
/*     */                 
/* 195 */                 if (maxSize > 0 && f > maxSize) {
/* 196 */                   if (p_180192_4_ != null) {
/* 197 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/* 200 */                   throw new IOException("Filesize was bigger than maximum allowed (got >= " + f + ", limit was " + maxSize + ")");
/*     */                 } 
/*     */                 
/* 203 */                 if (Thread.interrupted()) {
/* 204 */                   HttpUtil.logger.error("INTERRUPTED");
/*     */                   
/* 206 */                   if (p_180192_4_ != null) {
/* 207 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 213 */                 outputstream.write(abyte, 0, k);
/*     */               } 
/*     */               
/* 216 */               if (p_180192_4_ != null) {
/* 217 */                 p_180192_4_.setDoneWorking();
/*     */                 return;
/*     */               } 
/* 220 */             } catch (Throwable throwable) {
/* 221 */               throwable.printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */             finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 239 */               IOUtils.closeQuietly(inputstream);
/* 240 */               IOUtils.closeQuietly(outputstream);
/*     */             } 
/*     */           }
/*     */         });
/* 244 */     return (ListenableFuture)listenablefuture;
/*     */   }
/*     */   
/*     */   public static int getSuitableLanPort() throws IOException {
/* 248 */     ServerSocket serversocket = null;
/* 249 */     int i = -1;
/*     */     
/*     */     try {
/* 252 */       serversocket = new ServerSocket(0);
/* 253 */       i = serversocket.getLocalPort();
/*     */     } finally {
/*     */       try {
/* 256 */         if (serversocket != null) {
/* 257 */           serversocket.close();
/*     */         }
/* 259 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 264 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(URL url) throws IOException {
/* 271 */     HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
/* 272 */     httpurlconnection.setRequestMethod("GET");
/* 273 */     BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 274 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*     */     String s;
/* 277 */     while ((s = bufferedreader.readLine()) != null) {
/* 278 */       stringbuilder.append(s);
/* 279 */       stringbuilder.append('\r');
/*     */     } 
/*     */     
/* 282 */     bufferedreader.close();
/* 283 */     return stringbuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\HttpUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */