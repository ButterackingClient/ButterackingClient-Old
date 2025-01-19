/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityAIMoveToBlock
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityCreature theEntity;
/*     */   private final double movementSpeed;
/*     */   protected int runDelay;
/*     */   private int timeoutCounter;
/*     */   private int field_179490_f;
/*  21 */   protected BlockPos destinationBlock = BlockPos.ORIGIN;
/*     */   private boolean isAboveDestination;
/*     */   private int searchLength;
/*     */   
/*     */   public EntityAIMoveToBlock(EntityCreature creature, double speedIn, int length) {
/*  26 */     this.theEntity = creature;
/*  27 */     this.movementSpeed = speedIn;
/*  28 */     this.searchLength = length;
/*  29 */     setMutexBits(5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  36 */     if (this.runDelay > 0) {
/*  37 */       this.runDelay--;
/*  38 */       return false;
/*     */     } 
/*  40 */     this.runDelay = 200 + this.theEntity.getRNG().nextInt(200);
/*  41 */     return searchForDestination();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  49 */     return (this.timeoutCounter >= -this.field_179490_f && this.timeoutCounter <= 1200 && shouldMoveTo(this.theEntity.worldObj, this.destinationBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  56 */     this.theEntity.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, this.movementSpeed);
/*  57 */     this.timeoutCounter = 0;
/*  58 */     this.field_179490_f = this.theEntity.getRNG().nextInt(this.theEntity.getRNG().nextInt(1200) + 1200) + 1200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  71 */     if (this.theEntity.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0D) {
/*  72 */       this.isAboveDestination = false;
/*  73 */       this.timeoutCounter++;
/*     */       
/*  75 */       if (this.timeoutCounter % 40 == 0) {
/*  76 */         this.theEntity.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, this.movementSpeed);
/*     */       }
/*     */     } else {
/*  79 */       this.isAboveDestination = true;
/*  80 */       this.timeoutCounter--;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean getIsAboveDestination() {
/*  85 */     return this.isAboveDestination;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean searchForDestination() {
/*  94 */     int i = this.searchLength;
/*  95 */     int j = 1;
/*  96 */     BlockPos blockpos = new BlockPos((Entity)this.theEntity);
/*     */     
/*  98 */     for (int k = 0; k <= 1; k = (k > 0) ? -k : (1 - k)) {
/*  99 */       for (int l = 0; l < i; l++) {
/* 100 */         for (int i1 = 0; i1 <= l; i1 = (i1 > 0) ? -i1 : (1 - i1)) {
/* 101 */           for (int j1 = (i1 < l && i1 > -l) ? l : 0; j1 <= l; j1 = (j1 > 0) ? -j1 : (1 - j1)) {
/* 102 */             BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);
/*     */             
/* 104 */             if (this.theEntity.isWithinHomeDistanceFromPosition(blockpos1) && shouldMoveTo(this.theEntity.worldObj, blockpos1)) {
/* 105 */               this.destinationBlock = blockpos1;
/* 106 */               return true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract boolean shouldMoveTo(World paramWorld, BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIMoveToBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */