/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpUtils {
/*     */   public static final String SERVER_URL = "http://s.optifine.net";
/*  17 */   private static String playerItemsUrl = null;
/*     */   public static final String POST_URL = "http://optifine.net";
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/*     */     byte[] abyte1;
/*  22 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */     
/*     */     try {
/*  26 */       URL url = new URL(urlStr);
/*  27 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  28 */       httpurlconnection.setDoInput(true);
/*  29 */       httpurlconnection.setDoOutput(false);
/*  30 */       httpurlconnection.connect();
/*     */       
/*  32 */       if (httpurlconnection.getResponseCode() / 100 != 2) {
/*  33 */         if (httpurlconnection.getErrorStream() != null) {
/*  34 */           Config.readAll(httpurlconnection.getErrorStream());
/*     */         }
/*     */         
/*  37 */         throw new IOException("HTTP response: " + httpurlconnection.getResponseCode());
/*     */       } 
/*     */       
/*  40 */       InputStream inputstream = httpurlconnection.getInputStream();
/*  41 */       byte[] abyte = new byte[httpurlconnection.getContentLength()];
/*  42 */       int i = 0;
/*     */       
/*     */       do {
/*  45 */         int j = inputstream.read(abyte, i, abyte.length - i);
/*     */         
/*  47 */         if (j < 0) {
/*  48 */           throw new IOException("Input stream closed: " + urlStr);
/*     */         }
/*     */         
/*  51 */         i += j;
/*     */       }
/*  53 */       while (i < abyte.length);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  58 */       abyte1 = abyte;
/*     */     } finally {
/*  60 */       if (httpurlconnection != null) {
/*  61 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/*  65 */     return abyte1;
/*     */   }
/*     */   public static String post(String urlStr, Map headers, byte[] content) throws IOException {
/*     */     String s3;
/*  69 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */     
/*     */     try {
/*  73 */       URL url = new URL(urlStr);
/*  74 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  75 */       httpurlconnection.setRequestMethod("POST");
/*     */       
/*  77 */       if (headers != null) {
/*  78 */         for (Object s : headers.keySet()) {
/*  79 */           String s1 = (String)headers.get(s);
/*  80 */           httpurlconnection.setRequestProperty((String)s, s1);
/*     */         } 
/*     */       }
/*     */       
/*  84 */       httpurlconnection.setRequestProperty("Content-Type", "text/plain");
/*  85 */       httpurlconnection.setRequestProperty("Content-Length", content.length);
/*  86 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/*  87 */       httpurlconnection.setUseCaches(false);
/*  88 */       httpurlconnection.setDoInput(true);
/*  89 */       httpurlconnection.setDoOutput(true);
/*  90 */       OutputStream outputstream = httpurlconnection.getOutputStream();
/*  91 */       outputstream.write(content);
/*  92 */       outputstream.flush();
/*  93 */       outputstream.close();
/*  94 */       InputStream inputstream = httpurlconnection.getInputStream();
/*  95 */       InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
/*  96 */       BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/*  97 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s2;
/* 100 */       while ((s2 = bufferedreader.readLine()) != null) {
/* 101 */         stringbuffer.append(s2);
/* 102 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 105 */       bufferedreader.close();
/* 106 */       s3 = stringbuffer.toString();
/*     */     } finally {
/* 108 */       if (httpurlconnection != null) {
/* 109 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return s3;
/*     */   }
/*     */   
/*     */   public static synchronized String getPlayerItemsUrl() {
/* 117 */     if (playerItemsUrl == null) {
/*     */       try {
/* 119 */         boolean flag = Config.parseBoolean(System.getProperty("player.models.local"), false);
/*     */         
/* 121 */         if (flag) {
/* 122 */           File file1 = (Minecraft.getMinecraft()).mcDataDir;
/* 123 */           File file2 = new File(file1, "playermodels");
/* 124 */           playerItemsUrl = file2.toURI().toURL().toExternalForm();
/*     */         } 
/* 126 */       } catch (Exception exception) {
/* 127 */         Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */       
/* 130 */       if (playerItemsUrl == null) {
/* 131 */         playerItemsUrl = "http://s.optifine.net";
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return playerItemsUrl;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */