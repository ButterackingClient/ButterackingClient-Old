/*     */ package net.minecraft.profiler;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerUsageSnooper
/*     */ {
/*  19 */   private final Map<String, Object> snooperStats = Maps.newHashMap();
/*  20 */   private final Map<String, Object> clientStats = Maps.newHashMap();
/*  21 */   private final String uniqueID = UUID.randomUUID().toString();
/*     */ 
/*     */ 
/*     */   
/*     */   private final URL serverUrl;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IPlayerUsage playerStatsCollector;
/*     */ 
/*     */   
/*  32 */   private final Timer threadTrigger = new Timer("Snooper Timer", true);
/*  33 */   private final Object syncLock = new Object();
/*     */   
/*     */   private final long minecraftStartTimeMilis;
/*     */   
/*     */   private boolean isRunning;
/*     */   
/*     */   private int selfCounter;
/*     */ 
/*     */   
/*     */   public PlayerUsageSnooper(String side, IPlayerUsage playerStatCollector, long startTime) {
/*     */     try {
/*  44 */       this.serverUrl = new URL("http://snoop.minecraft.net/" + side + "?version=" + '\002');
/*  45 */     } catch (MalformedURLException var6) {
/*  46 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/*  49 */     this.playerStatsCollector = playerStatCollector;
/*  50 */     this.minecraftStartTimeMilis = startTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startSnooper() {
/*  57 */     if (!this.isRunning) {
/*  58 */       this.isRunning = true;
/*  59 */       addOSData();
/*  60 */       this.threadTrigger.schedule(new TimerTask() {
/*     */             public void run() {
/*  62 */               if (PlayerUsageSnooper.this.playerStatsCollector.isSnooperEnabled()) {
/*     */                 Map<String, Object> map;
/*     */                 
/*  65 */                 synchronized (PlayerUsageSnooper.this.syncLock) {
/*  66 */                   map = Maps.newHashMap(PlayerUsageSnooper.this.clientStats);
/*     */                   
/*  68 */                   if (PlayerUsageSnooper.this.selfCounter == 0) {
/*  69 */                     map.putAll(PlayerUsageSnooper.this.snooperStats);
/*     */                   }
/*     */                   
/*  72 */                   PlayerUsageSnooper.this.selfCounter = PlayerUsageSnooper.this.selfCounter + 1; map.put("snooper_count", Integer.valueOf(PlayerUsageSnooper.this.selfCounter));
/*  73 */                   map.put("snooper_token", PlayerUsageSnooper.this.uniqueID);
/*     */                 } 
/*     */                 
/*  76 */                 HttpUtil.postMap(PlayerUsageSnooper.this.serverUrl, map, true);
/*     */               } 
/*     */             }
/*  79 */           }0L, 900000L);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOSData() {
/*  87 */     addJvmArgsToSnooper();
/*  88 */     addClientStat("snooper_token", this.uniqueID);
/*  89 */     addStatToSnooper("snooper_token", this.uniqueID);
/*  90 */     addStatToSnooper("os_name", System.getProperty("os.name"));
/*  91 */     addStatToSnooper("os_version", System.getProperty("os.version"));
/*  92 */     addStatToSnooper("os_architecture", System.getProperty("os.arch"));
/*  93 */     addStatToSnooper("java_version", System.getProperty("java.version"));
/*  94 */     addClientStat("version", "1.8.9");
/*  95 */     this.playerStatsCollector.addServerTypeToSnooper(this);
/*     */   }
/*     */   
/*     */   private void addJvmArgsToSnooper() {
/*  99 */     RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/* 100 */     List<String> list = runtimemxbean.getInputArguments();
/* 101 */     int i = 0;
/*     */     
/* 103 */     for (String s : list) {
/* 104 */       if (s.startsWith("-X")) {
/* 105 */         addClientStat("jvm_arg[" + i++ + "]", s);
/*     */       }
/*     */     } 
/*     */     
/* 109 */     addClientStat("jvm_args", Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public void addMemoryStatsToSnooper() {
/* 113 */     addStatToSnooper("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
/* 114 */     addStatToSnooper("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
/* 115 */     addStatToSnooper("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
/* 116 */     addStatToSnooper("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
/* 117 */     this.playerStatsCollector.addServerStatsToSnooper(this);
/*     */   }
/*     */   
/*     */   public void addClientStat(String statName, Object statValue) {
/* 121 */     synchronized (this.syncLock) {
/* 122 */       this.clientStats.put(statName, statValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addStatToSnooper(String statName, Object statValue) {
/* 127 */     synchronized (this.syncLock) {
/* 128 */       this.snooperStats.put(statName, statValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<String, String> getCurrentStats() {
/* 133 */     Map<String, String> map = Maps.newLinkedHashMap();
/*     */     
/* 135 */     synchronized (this.syncLock) {
/* 136 */       addMemoryStatsToSnooper();
/*     */       
/* 138 */       for (Map.Entry<String, Object> entry : this.snooperStats.entrySet()) {
/* 139 */         map.put(entry.getKey(), entry.getValue().toString());
/*     */       }
/*     */       
/* 142 */       for (Map.Entry<String, Object> entry1 : this.clientStats.entrySet()) {
/* 143 */         map.put(entry1.getKey(), entry1.getValue().toString());
/*     */       }
/*     */       
/* 146 */       return map;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSnooperRunning() {
/* 151 */     return this.isRunning;
/*     */   }
/*     */   
/*     */   public void stopSnooper() {
/* 155 */     this.threadTrigger.cancel();
/*     */   }
/*     */   
/*     */   public String getUniqueID() {
/* 159 */     return this.uniqueID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMinecraftStartTimeMillis() {
/* 166 */     return this.minecraftStartTimeMilis;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\profiler\PlayerUsageSnooper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */