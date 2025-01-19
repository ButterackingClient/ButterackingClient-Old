/*     */ package net.minecraft.util;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timer
/*     */ {
/*     */   float ticksPerSecond;
/*     */   private double lastHRTime;
/*     */   public int elapsedTicks;
/*     */   public float renderPartialTicks;
/*  31 */   public float timerSpeed = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float elapsedPartialTicks;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastSyncSysClock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastSyncHRClock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long counter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private double timeSyncAdjustment = 1.0D;
/*     */   
/*     */   public Timer(float tps) {
/*  59 */     this.ticksPerSecond = tps;
/*  60 */     this.lastSyncSysClock = Minecraft.getSystemTime();
/*  61 */     this.lastSyncHRClock = System.nanoTime() / 1000000L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTimer() {
/*  68 */     long i = Minecraft.getSystemTime();
/*  69 */     long j = i - this.lastSyncSysClock;
/*  70 */     long k = System.nanoTime() / 1000000L;
/*  71 */     double d0 = k / 1000.0D;
/*     */     
/*  73 */     if (j <= 1000L && j >= 0L) {
/*  74 */       this.counter += j;
/*     */       
/*  76 */       if (this.counter > 1000L) {
/*  77 */         long l = k - this.lastSyncHRClock;
/*  78 */         double d1 = this.counter / l;
/*  79 */         this.timeSyncAdjustment += (d1 - this.timeSyncAdjustment) * 0.20000000298023224D;
/*  80 */         this.lastSyncHRClock = k;
/*  81 */         this.counter = 0L;
/*     */       } 
/*     */       
/*  84 */       if (this.counter < 0L) {
/*  85 */         this.lastSyncHRClock = k;
/*     */       }
/*     */     } else {
/*  88 */       this.lastHRTime = d0;
/*     */     } 
/*     */     
/*  91 */     this.lastSyncSysClock = i;
/*  92 */     double d2 = (d0 - this.lastHRTime) * this.timeSyncAdjustment;
/*  93 */     this.lastHRTime = d0;
/*  94 */     d2 = MathHelper.clamp_double(d2, 0.0D, 1.0D);
/*  95 */     this.elapsedPartialTicks = (float)(this.elapsedPartialTicks + d2 * this.timerSpeed * this.ticksPerSecond);
/*  96 */     this.elapsedTicks = (int)this.elapsedPartialTicks;
/*  97 */     this.elapsedPartialTicks -= this.elapsedTicks;
/*     */     
/*  99 */     if (this.elapsedTicks > 10) {
/* 100 */       this.elapsedTicks = 10;
/*     */     }
/*     */     
/* 103 */     this.renderPartialTicks = this.elapsedPartialTicks;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */