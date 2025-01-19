/*     */ package net.minecraft.world.border;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ 
/*     */ 
/*     */ public class WorldBorder
/*     */ {
/*  13 */   private final List<IBorderListener> listeners = Lists.newArrayList();
/*  14 */   private double centerX = 0.0D;
/*  15 */   private double centerZ = 0.0D;
/*  16 */   private double startDiameter = 6.0E7D;
/*     */   private double endDiameter;
/*     */   private long endTime;
/*     */   private long startTime;
/*     */   private int worldSize;
/*     */   private double damageAmount;
/*     */   private double damageBuffer;
/*     */   private int warningTime;
/*     */   private int warningDistance;
/*     */   
/*     */   public WorldBorder() {
/*  27 */     this.endDiameter = this.startDiameter;
/*  28 */     this.worldSize = 29999984;
/*  29 */     this.damageAmount = 0.2D;
/*  30 */     this.damageBuffer = 5.0D;
/*  31 */     this.warningTime = 15;
/*  32 */     this.warningDistance = 5;
/*     */   }
/*     */   
/*     */   public boolean contains(BlockPos pos) {
/*  36 */     return ((pos.getX() + 1) > minX() && pos.getX() < maxX() && (pos.getZ() + 1) > minZ() && pos.getZ() < maxZ());
/*     */   }
/*     */   
/*     */   public boolean contains(ChunkCoordIntPair range) {
/*  40 */     return (range.getXEnd() > minX() && range.getXStart() < maxX() && range.getZEnd() > minZ() && range.getZStart() < maxZ());
/*     */   }
/*     */   
/*     */   public boolean contains(AxisAlignedBB bb) {
/*  44 */     return (bb.maxX > minX() && bb.minX < maxX() && bb.maxZ > minZ() && bb.minZ < maxZ());
/*     */   }
/*     */   
/*     */   public double getClosestDistance(Entity entityIn) {
/*  48 */     return getClosestDistance(entityIn.posX, entityIn.posZ);
/*     */   }
/*     */   
/*     */   public double getClosestDistance(double x, double z) {
/*  52 */     double d0 = z - minZ();
/*  53 */     double d1 = maxZ() - z;
/*  54 */     double d2 = x - minX();
/*  55 */     double d3 = maxX() - x;
/*  56 */     double d4 = Math.min(d2, d3);
/*  57 */     d4 = Math.min(d4, d0);
/*  58 */     return Math.min(d4, d1);
/*     */   }
/*     */   
/*     */   public EnumBorderStatus getStatus() {
/*  62 */     return (this.endDiameter < this.startDiameter) ? EnumBorderStatus.SHRINKING : ((this.endDiameter > this.startDiameter) ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY);
/*     */   }
/*     */   
/*     */   public double minX() {
/*  66 */     double d0 = getCenterX() - getDiameter() / 2.0D;
/*     */     
/*  68 */     if (d0 < -this.worldSize) {
/*  69 */       d0 = -this.worldSize;
/*     */     }
/*     */     
/*  72 */     return d0;
/*     */   }
/*     */   
/*     */   public double minZ() {
/*  76 */     double d0 = getCenterZ() - getDiameter() / 2.0D;
/*     */     
/*  78 */     if (d0 < -this.worldSize) {
/*  79 */       d0 = -this.worldSize;
/*     */     }
/*     */     
/*  82 */     return d0;
/*     */   }
/*     */   
/*     */   public double maxX() {
/*  86 */     double d0 = getCenterX() + getDiameter() / 2.0D;
/*     */     
/*  88 */     if (d0 > this.worldSize) {
/*  89 */       d0 = this.worldSize;
/*     */     }
/*     */     
/*  92 */     return d0;
/*     */   }
/*     */   
/*     */   public double maxZ() {
/*  96 */     double d0 = getCenterZ() + getDiameter() / 2.0D;
/*     */     
/*  98 */     if (d0 > this.worldSize) {
/*  99 */       d0 = this.worldSize;
/*     */     }
/*     */     
/* 102 */     return d0;
/*     */   }
/*     */   
/*     */   public double getCenterX() {
/* 106 */     return this.centerX;
/*     */   }
/*     */   
/*     */   public double getCenterZ() {
/* 110 */     return this.centerZ;
/*     */   }
/*     */   
/*     */   public void setCenter(double x, double z) {
/* 114 */     this.centerX = x;
/* 115 */     this.centerZ = z;
/*     */     
/* 117 */     for (IBorderListener iborderlistener : getListeners()) {
/* 118 */       iborderlistener.onCenterChanged(this, x, z);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getDiameter() {
/* 123 */     if (getStatus() != EnumBorderStatus.STATIONARY) {
/* 124 */       double d0 = ((float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime));
/*     */       
/* 126 */       if (d0 < 1.0D) {
/* 127 */         return this.startDiameter + (this.endDiameter - this.startDiameter) * d0;
/*     */       }
/*     */       
/* 130 */       setTransition(this.endDiameter);
/*     */     } 
/*     */     
/* 133 */     return this.startDiameter;
/*     */   }
/*     */   
/*     */   public long getTimeUntilTarget() {
/* 137 */     return (getStatus() != EnumBorderStatus.STATIONARY) ? (this.endTime - System.currentTimeMillis()) : 0L;
/*     */   }
/*     */   
/*     */   public double getTargetSize() {
/* 141 */     return this.endDiameter;
/*     */   }
/*     */   
/*     */   public void setTransition(double newSize) {
/* 145 */     this.startDiameter = newSize;
/* 146 */     this.endDiameter = newSize;
/* 147 */     this.endTime = System.currentTimeMillis();
/* 148 */     this.startTime = this.endTime;
/*     */     
/* 150 */     for (IBorderListener iborderlistener : getListeners()) {
/* 151 */       iborderlistener.onSizeChanged(this, newSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTransition(double oldSize, double newSize, long time) {
/* 156 */     this.startDiameter = oldSize;
/* 157 */     this.endDiameter = newSize;
/* 158 */     this.startTime = System.currentTimeMillis();
/* 159 */     this.endTime = this.startTime + time;
/*     */     
/* 161 */     for (IBorderListener iborderlistener : getListeners()) {
/* 162 */       iborderlistener.onTransitionStarted(this, oldSize, newSize, time);
/*     */     }
/*     */   }
/*     */   
/*     */   protected List<IBorderListener> getListeners() {
/* 167 */     return Lists.newArrayList(this.listeners);
/*     */   }
/*     */   
/*     */   public void addListener(IBorderListener listener) {
/* 171 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void setSize(int size) {
/* 175 */     this.worldSize = size;
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 179 */     return this.worldSize;
/*     */   }
/*     */   
/*     */   public double getDamageBuffer() {
/* 183 */     return this.damageBuffer;
/*     */   }
/*     */   
/*     */   public void setDamageBuffer(double bufferSize) {
/* 187 */     this.damageBuffer = bufferSize;
/*     */     
/* 189 */     for (IBorderListener iborderlistener : getListeners()) {
/* 190 */       iborderlistener.onDamageBufferChanged(this, bufferSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getDamageAmount() {
/* 195 */     return this.damageAmount;
/*     */   }
/*     */   
/*     */   public void setDamageAmount(double newAmount) {
/* 199 */     this.damageAmount = newAmount;
/*     */     
/* 201 */     for (IBorderListener iborderlistener : getListeners()) {
/* 202 */       iborderlistener.onDamageAmountChanged(this, newAmount);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getResizeSpeed() {
/* 207 */     return (this.endTime == this.startTime) ? 0.0D : (Math.abs(this.startDiameter - this.endDiameter) / (this.endTime - this.startTime));
/*     */   }
/*     */   
/*     */   public int getWarningTime() {
/* 211 */     return this.warningTime;
/*     */   }
/*     */   
/*     */   public void setWarningTime(int warningTime) {
/* 215 */     this.warningTime = warningTime;
/*     */     
/* 217 */     for (IBorderListener iborderlistener : getListeners()) {
/* 218 */       iborderlistener.onWarningTimeChanged(this, warningTime);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWarningDistance() {
/* 223 */     return this.warningDistance;
/*     */   }
/*     */   
/*     */   public void setWarningDistance(int warningDistance) {
/* 227 */     this.warningDistance = warningDistance;
/*     */     
/* 229 */     for (IBorderListener iborderlistener : getListeners())
/* 230 */       iborderlistener.onWarningDistanceChanged(this, warningDistance); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\border\WorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */