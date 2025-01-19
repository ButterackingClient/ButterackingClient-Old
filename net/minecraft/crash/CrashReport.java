/*     */ package net.minecraft.crash;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ import net.optifine.CrashReporter;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReport
/*     */ {
/*  27 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String description;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Throwable cause;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
/*  43 */   private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   private File crashReportFile;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean firstCategoryInCrashReport = true;
/*     */ 
/*     */   
/*  54 */   private StackTraceElement[] stacktrace = new StackTraceElement[0];
/*     */   private boolean reported = false;
/*     */   
/*     */   public CrashReport(String descriptionIn, Throwable causeThrowable) {
/*  58 */     this.description = descriptionIn;
/*  59 */     this.cause = causeThrowable;
/*  60 */     populateEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateEnvironment() {
/*  68 */     this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable<String>() {
/*     */           public String call() {
/*  70 */             return "1.8.9";
/*     */           }
/*     */         });
/*  73 */     this.theReportCategory.addCrashSectionCallable("Operating System", new Callable<String>() {
/*     */           public String call() {
/*  75 */             return String.valueOf(System.getProperty("os.name")) + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
/*     */           }
/*     */         });
/*  78 */     this.theReportCategory.addCrashSectionCallable("Java Version", new Callable<String>() {
/*     */           public String call() {
/*  80 */             return String.valueOf(System.getProperty("java.version")) + ", " + System.getProperty("java.vendor");
/*     */           }
/*     */         });
/*  83 */     this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable<String>() {
/*     */           public String call() {
/*  85 */             return String.valueOf(System.getProperty("java.vm.name")) + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
/*     */           }
/*     */         });
/*  88 */     this.theReportCategory.addCrashSectionCallable("Memory", new Callable<String>() {
/*     */           public String call() {
/*  90 */             Runtime runtime = Runtime.getRuntime();
/*  91 */             long i = runtime.maxMemory();
/*  92 */             long j = runtime.totalMemory();
/*  93 */             long k = runtime.freeMemory();
/*  94 */             long l = i / 1024L / 1024L;
/*  95 */             long i1 = j / 1024L / 1024L;
/*  96 */             long j1 = k / 1024L / 1024L;
/*  97 */             return String.valueOf(k) + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
/*     */           }
/*     */         });
/* 100 */     this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable<String>() {
/*     */           public String call() {
/* 102 */             RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/* 103 */             List<String> list = runtimemxbean.getInputArguments();
/* 104 */             int i = 0;
/* 105 */             StringBuilder stringbuilder = new StringBuilder();
/*     */             
/* 107 */             for (String s : list) {
/* 108 */               if (s.startsWith("-X")) {
/* 109 */                 if (i++ > 0) {
/* 110 */                   stringbuilder.append(" ");
/*     */                 }
/*     */                 
/* 113 */                 stringbuilder.append(s);
/*     */               } 
/*     */             } 
/*     */             
/* 117 */             return String.format("%d total; %s", new Object[] { Integer.valueOf(i), stringbuilder.toString() });
/*     */           }
/*     */         });
/* 120 */     this.theReportCategory.addCrashSectionCallable("IntCache", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 122 */             return IntCache.getCacheSizes();
/*     */           }
/*     */         });
/*     */     
/* 126 */     if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
/* 127 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 128 */       Reflector.callString(object, Reflector.FMLCommonHandler_enhanceCrashReport, new Object[] { this, this.theReportCategory });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 136 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCrashCause() {
/* 143 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSectionsInStringBuilder(StringBuilder builder) {
/* 150 */     if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0) {
/* 151 */       this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])((CrashReportCategory)this.crashReportSections.get(0)).getStackTrace(), 0, 1);
/*     */     }
/*     */     
/* 154 */     if (this.stacktrace != null && this.stacktrace.length > 0) {
/* 155 */       builder.append("-- Head --\n");
/* 156 */       builder.append("Stacktrace:\n"); byte b; int i;
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 158 */       for (i = (arrayOfStackTraceElement = this.stacktrace).length, b = 0; b < i; ) { StackTraceElement stacktraceelement = arrayOfStackTraceElement[b];
/* 159 */         builder.append("\t").append("at ").append(stacktraceelement.toString());
/* 160 */         builder.append("\n");
/*     */         b++; }
/*     */       
/* 163 */       builder.append("\n");
/*     */     } 
/*     */     
/* 166 */     for (CrashReportCategory crashreportcategory : this.crashReportSections) {
/* 167 */       crashreportcategory.appendToStringBuilder(builder);
/* 168 */       builder.append("\n\n");
/*     */     } 
/*     */     
/* 171 */     this.theReportCategory.appendToStringBuilder(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCauseStackTraceOrString() {
/* 178 */     StringWriter stringwriter = null;
/* 179 */     PrintWriter printwriter = null;
/* 180 */     Throwable throwable = this.cause;
/*     */     
/* 182 */     if (throwable.getMessage() == null) {
/* 183 */       if (throwable instanceof NullPointerException) {
/* 184 */         throwable = new NullPointerException(this.description);
/* 185 */       } else if (throwable instanceof StackOverflowError) {
/* 186 */         throwable = new StackOverflowError(this.description);
/* 187 */       } else if (throwable instanceof OutOfMemoryError) {
/* 188 */         throwable = new OutOfMemoryError(this.description);
/*     */       } 
/*     */       
/* 191 */       throwable.setStackTrace(this.cause.getStackTrace());
/*     */     } 
/*     */     
/* 194 */     String s = throwable.toString();
/*     */     
/*     */     try {
/* 197 */       stringwriter = new StringWriter();
/* 198 */       printwriter = new PrintWriter(stringwriter);
/* 199 */       throwable.printStackTrace(printwriter);
/* 200 */       s = stringwriter.toString();
/*     */     } finally {
/* 202 */       IOUtils.closeQuietly(stringwriter);
/* 203 */       IOUtils.closeQuietly(printwriter);
/*     */     } 
/*     */     
/* 206 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompleteReport() {
/* 213 */     if (!this.reported) {
/* 214 */       this.reported = true;
/* 215 */       CrashReporter.onCrashReport(this, this.theReportCategory);
/*     */     } 
/*     */     
/* 218 */     StringBuilder stringbuilder = new StringBuilder();
/* 219 */     stringbuilder.append("---- Minecraft Crash Report ----\n");
/* 220 */     Reflector.call(Reflector.BlamingTransformer_onCrash, new Object[] { stringbuilder });
/* 221 */     Reflector.call(Reflector.CoreModManager_onCrash, new Object[] { stringbuilder });
/* 222 */     stringbuilder.append("// ");
/* 223 */     stringbuilder.append(getWittyComment());
/* 224 */     stringbuilder.append("\n\n");
/* 225 */     stringbuilder.append("Time: ");
/* 226 */     stringbuilder.append((new SimpleDateFormat()).format(new Date()));
/* 227 */     stringbuilder.append("\n");
/* 228 */     stringbuilder.append("Description: ");
/* 229 */     stringbuilder.append(this.description);
/* 230 */     stringbuilder.append("\n\n");
/* 231 */     stringbuilder.append(getCauseStackTraceOrString());
/* 232 */     stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 234 */     for (int i = 0; i < 87; i++) {
/* 235 */       stringbuilder.append("-");
/*     */     }
/*     */     
/* 238 */     stringbuilder.append("\n\n");
/* 239 */     getSectionsInStringBuilder(stringbuilder);
/* 240 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 247 */     return this.crashReportFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveToFile(File toFile) {
/* 254 */     if (this.crashReportFile != null) {
/* 255 */       return false;
/*     */     }
/* 257 */     if (toFile.getParentFile() != null) {
/* 258 */       toFile.getParentFile().mkdirs();
/*     */     }
/*     */     
/*     */     try {
/* 262 */       FileWriter filewriter = new FileWriter(toFile);
/* 263 */       filewriter.write(getCompleteReport());
/* 264 */       filewriter.close();
/* 265 */       this.crashReportFile = toFile;
/* 266 */       return true;
/* 267 */     } catch (Throwable throwable) {
/* 268 */       logger.error("Could not save crash report to " + toFile, throwable);
/* 269 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CrashReportCategory getCategory() {
/* 275 */     return this.theReportCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategory(String name) {
/* 282 */     return makeCategoryDepth(name, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength) {
/* 289 */     CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);
/*     */     
/* 291 */     if (this.firstCategoryInCrashReport) {
/* 292 */       int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
/* 293 */       StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
/* 294 */       StackTraceElement stacktraceelement = null;
/* 295 */       StackTraceElement stacktraceelement1 = null;
/* 296 */       int j = astacktraceelement.length - i;
/*     */       
/* 298 */       if (j < 0) {
/* 299 */         System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
/*     */       }
/*     */       
/* 302 */       if (astacktraceelement != null && j >= 0 && j < astacktraceelement.length) {
/* 303 */         stacktraceelement = astacktraceelement[j];
/*     */         
/* 305 */         if (astacktraceelement.length + 1 - i < astacktraceelement.length) {
/* 306 */           stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
/*     */         }
/*     */       } 
/*     */       
/* 310 */       this.firstCategoryInCrashReport = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement1);
/*     */       
/* 312 */       if (i > 0 && !this.crashReportSections.isEmpty()) {
/* 313 */         CrashReportCategory crashreportcategory1 = this.crashReportSections.get(this.crashReportSections.size() - 1);
/* 314 */         crashreportcategory1.trimStackTraceEntriesFromBottom(i);
/* 315 */       } else if (astacktraceelement != null && astacktraceelement.length >= i && j >= 0 && j < astacktraceelement.length) {
/* 316 */         this.stacktrace = new StackTraceElement[j];
/* 317 */         System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
/*     */       } else {
/* 319 */         this.firstCategoryInCrashReport = false;
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     this.crashReportSections.add(crashreportcategory);
/* 324 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 331 */     String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
/*     */     
/*     */     try {
/* 334 */       return astring[(int)(System.nanoTime() % astring.length)];
/* 335 */     } catch (Throwable var2) {
/* 336 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn) {
/*     */     CrashReport crashreport;
/* 346 */     if (causeIn instanceof ReportedException) {
/* 347 */       crashreport = ((ReportedException)causeIn).getCrashReport();
/*     */     } else {
/* 349 */       crashreport = new CrashReport(descriptionIn, causeIn);
/*     */     } 
/*     */     
/* 352 */     return crashreport;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\crash\CrashReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */