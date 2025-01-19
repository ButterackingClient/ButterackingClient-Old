/*    */ package net.optifine;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import net.minecraft.client.ClientBrandRetriever;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class VersionCheckThread
/*    */   extends Thread {
/*    */   public VersionCheckThread() {
/* 12 */     super("VersionCheck");
/*    */   }
/*    */   
/*    */   public void run() {
/* 16 */     HttpURLConnection httpurlconnection = null;
/*    */     
/*    */     try {
/* 19 */       Config.dbg("Checking for new version");
/* 20 */       URL url = new URL("http://optifine.net/version/1.8.9/HD_U.txt");
/* 21 */       httpurlconnection = (HttpURLConnection)url.openConnection();
/*    */       
/* 23 */       if ((Config.getGameSettings()).snooperEnabled) {
/* 24 */         httpurlconnection.setRequestProperty("OF-MC-Version", "1.8.9");
/* 25 */         httpurlconnection.setRequestProperty("OF-MC-Brand", ClientBrandRetriever.getClientModName());
/* 26 */         httpurlconnection.setRequestProperty("OF-Edition", "HD_U");
/* 27 */         httpurlconnection.setRequestProperty("OF-Release", "M5");
/* 28 */         httpurlconnection.setRequestProperty("OF-Java-Version", System.getProperty("java.version"));
/* 29 */         httpurlconnection.setRequestProperty("OF-CpuCount", Config.getAvailableProcessors());
/* 30 */         httpurlconnection.setRequestProperty("OF-OpenGL-Version", Config.openGlVersion);
/* 31 */         httpurlconnection.setRequestProperty("OF-OpenGL-Vendor", Config.openGlVendor);
/*    */       } 
/*    */       
/* 34 */       httpurlconnection.setDoInput(true);
/* 35 */       httpurlconnection.setDoOutput(false);
/* 36 */       httpurlconnection.connect();
/*    */       
/*    */       try {
/* 39 */         InputStream inputstream = httpurlconnection.getInputStream();
/* 40 */         String s = Config.readInputStream(inputstream);
/* 41 */         inputstream.close();
/* 42 */         String[] astring = Config.tokenize(s, "\n\r");
/*    */         
/* 44 */         if (astring.length >= 1) {
/* 45 */           String s1 = astring[0].trim();
/* 46 */           Config.dbg("Version found: " + s1);
/*    */           
/* 48 */           if (Config.compareRelease(s1, "M5") <= 0) {
/*    */             return;
/*    */           }
/*    */           
/* 52 */           Config.setNewRelease(s1);
/*    */           return;
/*    */         } 
/*    */       } finally {
/* 56 */         if (httpurlconnection != null) {
/* 57 */           httpurlconnection.disconnect();
/*    */         }
/*    */       } 
/* 60 */     } catch (Exception exception) {
/* 61 */       Config.dbg(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\VersionCheckThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */