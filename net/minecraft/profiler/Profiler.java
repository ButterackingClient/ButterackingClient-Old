/*     */ package net.minecraft.profiler;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lagometer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Profiler
/*     */ {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*  18 */   private final List<String> sectionList = Lists.newArrayList();
/*  19 */   private final List<Long> timestampList = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean profilingEnabled;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   private String profilingSection = "";
/*  30 */   private final Map<String, Long> profilingMap = Maps.newHashMap();
/*     */   public boolean profilerGlobalEnabled = true;
/*     */   private boolean profilerLocalEnabled;
/*     */   private static final String SCHEDULED_EXECUTABLES = "scheduledExecutables";
/*     */   private static final String TICK = "tick";
/*     */   private static final String PRE_RENDER_ERRORS = "preRenderErrors";
/*     */   private static final String RENDER = "render";
/*     */   private static final String DISPLAY = "display";
/*  38 */   private static final int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
/*  39 */   private static final int HASH_TICK = "tick".hashCode();
/*  40 */   private static final int HASH_PRE_RENDER_ERRORS = "preRenderErrors".hashCode();
/*  41 */   private static final int HASH_RENDER = "render".hashCode();
/*  42 */   private static final int HASH_DISPLAY = "display".hashCode();
/*     */   
/*     */   public Profiler() {
/*  45 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearProfiling() {
/*  52 */     this.profilingMap.clear();
/*  53 */     this.profilingSection = "";
/*  54 */     this.sectionList.clear();
/*  55 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startSection(String name) {
/*  62 */     if (Lagometer.isActive()) {
/*  63 */       int i = name.hashCode();
/*     */       
/*  65 */       if (i == HASH_SCHEDULED_EXECUTABLES && name.equals("scheduledExecutables")) {
/*  66 */         Lagometer.timerScheduledExecutables.start();
/*  67 */       } else if (i == HASH_TICK && name.equals("tick") && Config.isMinecraftThread()) {
/*  68 */         Lagometer.timerScheduledExecutables.end();
/*  69 */         Lagometer.timerTick.start();
/*  70 */       } else if (i == HASH_PRE_RENDER_ERRORS && name.equals("preRenderErrors")) {
/*  71 */         Lagometer.timerTick.end();
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     if (Config.isFastRender()) {
/*  76 */       int j = name.hashCode();
/*     */       
/*  78 */       if (j == HASH_RENDER && name.equals("render")) {
/*  79 */         GlStateManager.clearEnabled = false;
/*  80 */       } else if (j == HASH_DISPLAY && name.equals("display")) {
/*  81 */         GlStateManager.clearEnabled = true;
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     if (this.profilerLocalEnabled && 
/*  86 */       this.profilingEnabled) {
/*  87 */       if (this.profilingSection.length() > 0) {
/*  88 */         this.profilingSection = String.valueOf(this.profilingSection) + ".";
/*     */       }
/*     */       
/*  91 */       this.profilingSection = String.valueOf(this.profilingSection) + name;
/*  92 */       this.sectionList.add(this.profilingSection);
/*  93 */       this.timestampList.add(Long.valueOf(System.nanoTime()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endSection() {
/* 102 */     if (this.profilerLocalEnabled && 
/* 103 */       this.profilingEnabled) {
/* 104 */       long i = System.nanoTime();
/* 105 */       long j = ((Long)this.timestampList.remove(this.timestampList.size() - 1)).longValue();
/* 106 */       this.sectionList.remove(this.sectionList.size() - 1);
/* 107 */       long k = i - j;
/*     */       
/* 109 */       if (this.profilingMap.containsKey(this.profilingSection)) {
/* 110 */         this.profilingMap.put(this.profilingSection, Long.valueOf(((Long)this.profilingMap.get(this.profilingSection)).longValue() + k));
/*     */       } else {
/* 112 */         this.profilingMap.put(this.profilingSection, Long.valueOf(k));
/*     */       } 
/*     */       
/* 115 */       if (k > 100000000L) {
/* 116 */         logger.warn("Something's taking too long! '" + this.profilingSection + "' took aprox " + (k / 1000000.0D) + " ms");
/*     */       }
/*     */       
/* 119 */       this.profilingSection = !this.sectionList.isEmpty() ? this.sectionList.get(this.sectionList.size() - 1) : "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Result> getProfilingData(String profilerName) {
/* 125 */     if (!this.profilingEnabled) {
/* 126 */       return null;
/*     */     }
/* 128 */     long i = this.profilingMap.containsKey("root") ? ((Long)this.profilingMap.get("root")).longValue() : 0L;
/* 129 */     long j = this.profilingMap.containsKey(profilerName) ? ((Long)this.profilingMap.get(profilerName)).longValue() : -1L;
/* 130 */     List<Result> list = Lists.newArrayList();
/*     */     
/* 132 */     if (profilerName.length() > 0) {
/* 133 */       profilerName = String.valueOf(profilerName) + ".";
/*     */     }
/*     */     
/* 136 */     long k = 0L;
/*     */     
/* 138 */     for (String s : this.profilingMap.keySet()) {
/* 139 */       if (s.length() > profilerName.length() && s.startsWith(profilerName) && s.indexOf(".", profilerName.length() + 1) < 0) {
/* 140 */         k += ((Long)this.profilingMap.get(s)).longValue();
/*     */       }
/*     */     } 
/*     */     
/* 144 */     float f = (float)k;
/*     */     
/* 146 */     if (k < j) {
/* 147 */       k = j;
/*     */     }
/*     */     
/* 150 */     if (i < k) {
/* 151 */       i = k;
/*     */     }
/*     */     
/* 154 */     for (String s1 : this.profilingMap.keySet()) {
/* 155 */       if (s1.length() > profilerName.length() && s1.startsWith(profilerName) && s1.indexOf(".", profilerName.length() + 1) < 0) {
/* 156 */         long l = ((Long)this.profilingMap.get(s1)).longValue();
/* 157 */         double d0 = l * 100.0D / k;
/* 158 */         double d1 = l * 100.0D / i;
/* 159 */         String s2 = s1.substring(profilerName.length());
/* 160 */         list.add(new Result(s2, d0, d1));
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     for (String s3 : this.profilingMap.keySet()) {
/* 165 */       this.profilingMap.put(s3, Long.valueOf(((Long)this.profilingMap.get(s3)).longValue() * 950L / 1000L));
/*     */     }
/*     */     
/* 168 */     if ((float)k > f) {
/* 169 */       list.add(new Result("unspecified", ((float)k - f) * 100.0D / k, ((float)k - f) * 100.0D / i));
/*     */     }
/*     */     
/* 172 */     Collections.sort(list);
/* 173 */     list.add(0, new Result(profilerName, 100.0D, k * 100.0D / i));
/* 174 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endStartSection(String name) {
/* 182 */     if (this.profilerLocalEnabled) {
/* 183 */       endSection();
/* 184 */       startSection(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getNameOfLastSection() {
/* 189 */     return (this.sectionList.size() == 0) ? "[UNKNOWN]" : this.sectionList.get(this.sectionList.size() - 1);
/*     */   }
/*     */   
/*     */   public void startSection(Class<?> p_startSection_1_) {
/* 193 */     if (this.profilingEnabled)
/* 194 */       startSection(p_startSection_1_.getSimpleName()); 
/*     */   }
/*     */   
/*     */   public static final class Result
/*     */     implements Comparable<Result> {
/*     */     public double field_76332_a;
/*     */     public double field_76330_b;
/*     */     public String field_76331_c;
/*     */     
/*     */     public Result(String profilerName, double usePercentage, double totalUsePercentage) {
/* 204 */       this.field_76331_c = profilerName;
/* 205 */       this.field_76332_a = usePercentage;
/* 206 */       this.field_76330_b = totalUsePercentage;
/*     */     }
/*     */     
/*     */     public int compareTo(Result p_compareTo_1_) {
/* 210 */       return (p_compareTo_1_.field_76332_a < this.field_76332_a) ? -1 : ((p_compareTo_1_.field_76332_a > this.field_76332_a) ? 1 : p_compareTo_1_.field_76331_c.compareTo(this.field_76331_c));
/*     */     }
/*     */     
/*     */     public int getColor() {
/* 214 */       return (this.field_76331_c.hashCode() & 0xAAAAAA) + 4473924;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\profiler\Profiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */