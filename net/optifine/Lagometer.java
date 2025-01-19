/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiIngame;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.MemoryMonitor;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Lagometer {
/*     */   private static Minecraft mc;
/*     */   private static GameSettings gameSettings;
/*     */   private static Profiler profiler;
/*     */   public static boolean active = false;
/*  21 */   public static TimerNano timerTick = new TimerNano();
/*  22 */   public static TimerNano timerScheduledExecutables = new TimerNano();
/*  23 */   public static TimerNano timerChunkUpload = new TimerNano();
/*  24 */   public static TimerNano timerChunkUpdate = new TimerNano();
/*  25 */   public static TimerNano timerVisibility = new TimerNano();
/*  26 */   public static TimerNano timerTerrain = new TimerNano();
/*  27 */   public static TimerNano timerServer = new TimerNano();
/*  28 */   private static long[] timesFrame = new long[512];
/*  29 */   private static long[] timesTick = new long[512];
/*  30 */   private static long[] timesScheduledExecutables = new long[512];
/*  31 */   private static long[] timesChunkUpload = new long[512];
/*  32 */   private static long[] timesChunkUpdate = new long[512];
/*  33 */   private static long[] timesVisibility = new long[512];
/*  34 */   private static long[] timesTerrain = new long[512];
/*  35 */   private static long[] timesServer = new long[512];
/*  36 */   private static boolean[] gcs = new boolean[512];
/*  37 */   private static int numRecordedFrameTimes = 0;
/*  38 */   private static long prevFrameTimeNano = -1L;
/*  39 */   private static long renderTimeNano = 0L;
/*     */   
/*     */   public static void updateLagometer() {
/*  42 */     if (mc == null) {
/*  43 */       mc = Minecraft.getMinecraft();
/*  44 */       gameSettings = mc.gameSettings;
/*  45 */       profiler = mc.mcProfiler;
/*     */     } 
/*     */     
/*  48 */     if (gameSettings.showDebugProfilerChart && (gameSettings.ofLagometer || gameSettings.lastServer)) {
/*  49 */       active = true;
/*  50 */       long timeNowNano = System.nanoTime();
/*     */       
/*  52 */       if (prevFrameTimeNano == -1L) {
/*  53 */         prevFrameTimeNano = timeNowNano;
/*     */       } else {
/*  55 */         int j = numRecordedFrameTimes & timesFrame.length - 1;
/*  56 */         numRecordedFrameTimes++;
/*  57 */         boolean flag = MemoryMonitor.isGcEvent();
/*  58 */         timesFrame[j] = timeNowNano - prevFrameTimeNano - renderTimeNano;
/*  59 */         timesTick[j] = timerTick.timeNano;
/*  60 */         timesScheduledExecutables[j] = timerScheduledExecutables.timeNano;
/*  61 */         timesChunkUpload[j] = timerChunkUpload.timeNano;
/*  62 */         timesChunkUpdate[j] = timerChunkUpdate.timeNano;
/*  63 */         timesVisibility[j] = timerVisibility.timeNano;
/*  64 */         timesTerrain[j] = timerTerrain.timeNano;
/*  65 */         timesServer[j] = timerServer.timeNano;
/*  66 */         gcs[j] = flag;
/*  67 */         timerTick.reset();
/*  68 */         timerScheduledExecutables.reset();
/*  69 */         timerVisibility.reset();
/*  70 */         timerChunkUpdate.reset();
/*  71 */         timerChunkUpload.reset();
/*  72 */         timerTerrain.reset();
/*  73 */         timerServer.reset();
/*  74 */         prevFrameTimeNano = System.nanoTime();
/*     */       } 
/*     */     } else {
/*  77 */       active = false;
/*  78 */       prevFrameTimeNano = -1L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void showLagometer(ScaledResolution scaledResolution) {
/*  83 */     if (gameSettings != null && (
/*  84 */       gameSettings.ofLagometer || gameSettings.lastServer)) {
/*  85 */       long i = System.nanoTime();
/*  86 */       GlStateManager.clear(256);
/*  87 */       GlStateManager.matrixMode(5889);
/*  88 */       GlStateManager.pushMatrix();
/*  89 */       GlStateManager.enableColorMaterial();
/*  90 */       GlStateManager.loadIdentity();
/*  91 */       GlStateManager.ortho(0.0D, mc.displayWidth, mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
/*  92 */       GlStateManager.matrixMode(5888);
/*  93 */       GlStateManager.pushMatrix();
/*  94 */       GlStateManager.loadIdentity();
/*  95 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*  96 */       GL11.glLineWidth(1.0F);
/*  97 */       GlStateManager.disableTexture2D();
/*  98 */       Tessellator tessellator = Tessellator.getInstance();
/*  99 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 100 */       worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
/*     */       
/* 102 */       for (int j = 0; j < timesFrame.length; j++) {
/* 103 */         int k = (j - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
/* 104 */         k += 155;
/* 105 */         float f = mc.displayHeight;
/* 106 */         long l = 0L;
/*     */         
/* 108 */         if (gcs[j]) {
/* 109 */           renderTime(j, timesFrame[j], k, k / 2, 0, f, worldrenderer);
/*     */         } else {
/* 111 */           renderTime(j, timesFrame[j], k, k, k, f, worldrenderer);
/* 112 */           f -= (float)renderTime(j, timesServer[j], k / 2, k / 2, k / 2, f, worldrenderer);
/* 113 */           f -= (float)renderTime(j, timesTerrain[j], 0, k, 0, f, worldrenderer);
/* 114 */           f -= (float)renderTime(j, timesVisibility[j], k, k, 0, f, worldrenderer);
/* 115 */           f -= (float)renderTime(j, timesChunkUpdate[j], k, 0, 0, f, worldrenderer);
/* 116 */           f -= (float)renderTime(j, timesChunkUpload[j], k, 0, k, f, worldrenderer);
/* 117 */           f -= (float)renderTime(j, timesScheduledExecutables[j], 0, 0, k, f, worldrenderer);
/* 118 */           float f2 = f - (float)renderTime(j, timesTick[j], 0, k, k, f, worldrenderer);
/*     */         } 
/*     */       } 
/*     */       
/* 122 */       renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, mc.displayHeight, worldrenderer);
/* 123 */       renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, mc.displayHeight, worldrenderer);
/* 124 */       tessellator.draw();
/* 125 */       GlStateManager.enableTexture2D();
/* 126 */       int j2 = mc.displayHeight - 80;
/* 127 */       int k2 = mc.displayHeight - 160;
/* 128 */       mc.fontRendererObj.drawString("30", 2, k2 + 1, -8947849);
/* 129 */       mc.fontRendererObj.drawString("30", 1, k2, -3881788);
/* 130 */       mc.fontRendererObj.drawString("60", 2, j2 + 1, -8947849);
/* 131 */       mc.fontRendererObj.drawString("60", 1, j2, -3881788);
/* 132 */       GlStateManager.matrixMode(5889);
/* 133 */       GlStateManager.popMatrix();
/* 134 */       GlStateManager.matrixMode(5888);
/* 135 */       GlStateManager.popMatrix();
/* 136 */       GlStateManager.enableTexture2D();
/* 137 */       float f1 = 1.0F - (float)((System.currentTimeMillis() - MemoryMonitor.getStartTimeMs()) / 1000.0D);
/* 138 */       f1 = Config.limit(f1, 0.0F, 1.0F);
/* 139 */       int l2 = (int)(170.0F + f1 * 85.0F);
/* 140 */       int i1 = (int)(100.0F + f1 * 55.0F);
/* 141 */       int j1 = (int)(10.0F + f1 * 10.0F);
/* 142 */       int k1 = l2 << 16 | i1 << 8 | j1;
/* 143 */       int l1 = 512 / scaledResolution.getScaleFactor() + 2;
/* 144 */       int i2 = mc.displayHeight / scaledResolution.getScaleFactor() - 8;
/* 145 */       GuiIngame guiingame = mc.ingameGUI;
/* 146 */       GuiIngame.drawRect(l1 - 1, i2 - 1, l1 + 50, i2 + 10, -1605349296);
/* 147 */       mc.fontRendererObj.drawString(" " + MemoryMonitor.getAllocationRateMb() + " MB/s", l1, i2, k1);
/* 148 */       renderTimeNano = System.nanoTime() - i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static long renderTime(int frameNum, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator) {
/* 154 */     long i = time / 200000L;
/*     */     
/* 156 */     if (i < 3L) {
/* 157 */       return 0L;
/*     */     }
/* 159 */     tessellator.pos((frameNum + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 160 */     tessellator.pos((frameNum + 0.5F), (baseHeight + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 161 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long renderTimeDivider(int frameStart, int frameEnd, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator) {
/* 166 */     long i = time / 200000L;
/*     */     
/* 168 */     if (i < 3L) {
/* 169 */       return 0L;
/*     */     }
/* 171 */     tessellator.pos((frameStart + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 172 */     tessellator.pos((frameEnd + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).color(r, g, b, 255).endVertex();
/* 173 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 178 */     return active;
/*     */   }
/*     */   
/*     */   public static class TimerNano {
/* 182 */     public long timeStartNano = 0L;
/* 183 */     public long timeNano = 0L;
/*     */     
/*     */     public void start() {
/* 186 */       if (Lagometer.active && 
/* 187 */         this.timeStartNano == 0L) {
/* 188 */         this.timeStartNano = System.nanoTime();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 194 */       if (Lagometer.active && 
/* 195 */         this.timeStartNano != 0L) {
/* 196 */         this.timeNano += System.nanoTime() - this.timeStartNano;
/* 197 */         this.timeStartNano = 0L;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void reset() {
/* 203 */       this.timeNano = 0L;
/* 204 */       this.timeStartNano = 0L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\Lagometer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */