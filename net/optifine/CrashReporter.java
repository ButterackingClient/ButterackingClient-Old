/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.http.FileUploadThread;
/*     */ import net.optifine.http.IFileUploadListener;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class CrashReporter
/*     */ {
/*     */   public static void onCrashReport(CrashReport crashReport, CrashReportCategory category) {
/*     */     try {
/*  17 */       Throwable throwable = crashReport.getCrashCause();
/*     */       
/*  19 */       if (throwable == null) {
/*     */         return;
/*     */       }
/*     */       
/*  23 */       if (throwable.getClass().getName().contains(".fml.client.SplashProgress")) {
/*     */         return;
/*     */       }
/*     */       
/*  27 */       if (throwable.getClass() == Throwable.class) {
/*     */         return;
/*     */       }
/*     */       
/*  31 */       extendCrashReport(category);
/*  32 */       GameSettings gamesettings = Config.getGameSettings();
/*     */       
/*  34 */       if (gamesettings == null) {
/*     */         return;
/*     */       }
/*     */       
/*  38 */       if (!gamesettings.snooperEnabled) {
/*     */         return;
/*     */       }
/*     */       
/*  42 */       String s = "http://optifine.net/crashReport";
/*  43 */       String s1 = makeReport(crashReport);
/*  44 */       byte[] abyte = s1.getBytes("ASCII");
/*  45 */       IFileUploadListener ifileuploadlistener = new IFileUploadListener()
/*     */         {
/*     */           public void fileUploadFinished(String url, byte[] content, Throwable exception) {}
/*     */         };
/*  49 */       Map<Object, Object> map = new HashMap<>();
/*  50 */       map.put("OF-Version", Config.getVersion());
/*  51 */       map.put("OF-Summary", makeSummary(crashReport));
/*  52 */       FileUploadThread fileuploadthread = new FileUploadThread(s, map, abyte, ifileuploadlistener);
/*  53 */       fileuploadthread.setPriority(10);
/*  54 */       fileuploadthread.start();
/*  55 */       Thread.sleep(1000L);
/*  56 */     } catch (Exception exception) {
/*  57 */       Config.dbg(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String makeReport(CrashReport crashReport) {
/*  62 */     StringBuffer stringbuffer = new StringBuffer();
/*  63 */     stringbuffer.append("OptiFineVersion: " + Config.getVersion() + "\n");
/*  64 */     stringbuffer.append("Summary: " + makeSummary(crashReport) + "\n");
/*  65 */     stringbuffer.append("\n");
/*  66 */     stringbuffer.append(crashReport.getCompleteReport());
/*  67 */     stringbuffer.append("\n");
/*  68 */     return stringbuffer.toString();
/*     */   }
/*     */   
/*     */   private static String makeSummary(CrashReport crashReport) {
/*  72 */     Throwable throwable = crashReport.getCrashCause();
/*     */     
/*  74 */     if (throwable == null) {
/*  75 */       return "Unknown";
/*     */     }
/*  77 */     StackTraceElement[] astacktraceelement = throwable.getStackTrace();
/*  78 */     String s = "unknown";
/*     */     
/*  80 */     if (astacktraceelement.length > 0) {
/*  81 */       s = astacktraceelement[0].toString().trim();
/*     */     }
/*     */     
/*  84 */     String s1 = String.valueOf(throwable.getClass().getName()) + ": " + throwable.getMessage() + " (" + crashReport.getDescription() + ")" + " [" + s + "]";
/*  85 */     return s1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void extendCrashReport(CrashReportCategory cat) {
/*  90 */     cat.addCrashSection("OptiFine Version", Config.getVersion());
/*  91 */     cat.addCrashSection("OptiFine Build", Config.getBuild());
/*     */     
/*  93 */     if (Config.getGameSettings() != null) {
/*  94 */       cat.addCrashSection("Render Distance Chunks", Config.getChunkViewDistance());
/*  95 */       cat.addCrashSection("Mipmaps", Config.getMipmapLevels());
/*  96 */       cat.addCrashSection("Anisotropic Filtering", Config.getAnisotropicFilterLevel());
/*  97 */       cat.addCrashSection("Antialiasing", Config.getAntialiasingLevel());
/*  98 */       cat.addCrashSection("Multitexture", Config.isMultiTexture());
/*     */     } 
/*     */     
/* 101 */     cat.addCrashSection("Shaders", Shaders.getShaderPackName());
/* 102 */     cat.addCrashSection("OpenGlVersion", Config.openGlVersion);
/* 103 */     cat.addCrashSection("OpenGlRenderer", Config.openGlRenderer);
/* 104 */     cat.addCrashSection("OpenGlVendor", Config.openGlVendor);
/* 105 */     cat.addCrashSection("CpuCount", Config.getAvailableProcessors());
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CrashReporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */