/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
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
/*     */ public class EntityAITempt
/*     */   extends EntityAIBase
/*     */ {
/*     */   private EntityCreature temptedEntity;
/*     */   private double speed;
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   private double pitch;
/*     */   private double yaw;
/*     */   private EntityPlayer temptingPlayer;
/*     */   private int delayTemptCounter;
/*     */   private boolean isRunning;
/*     */   private Item temptItem;
/*     */   private boolean scaredByPlayerMovement;
/*     */   private boolean avoidWater;
/*     */   
/*     */   public EntityAITempt(EntityCreature temptedEntityIn, double speedIn, Item temptItemIn, boolean scaredByPlayerMovementIn) {
/*  65 */     this.temptedEntity = temptedEntityIn;
/*  66 */     this.speed = speedIn;
/*  67 */     this.temptItem = temptItemIn;
/*  68 */     this.scaredByPlayerMovement = scaredByPlayerMovementIn;
/*  69 */     setMutexBits(3);
/*     */     
/*  71 */     if (!(temptedEntityIn.getNavigator() instanceof PathNavigateGround)) {
/*  72 */       throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  80 */     if (this.delayTemptCounter > 0) {
/*  81 */       this.delayTemptCounter--;
/*  82 */       return false;
/*     */     } 
/*  84 */     this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity((Entity)this.temptedEntity, 10.0D);
/*     */     
/*  86 */     if (this.temptingPlayer == null) {
/*  87 */       return false;
/*     */     }
/*  89 */     ItemStack itemstack = this.temptingPlayer.getCurrentEquippedItem();
/*  90 */     return (itemstack == null) ? false : ((itemstack.getItem() == this.temptItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  99 */     if (this.scaredByPlayerMovement) {
/* 100 */       if (this.temptedEntity.getDistanceSqToEntity((Entity)this.temptingPlayer) < 36.0D) {
/* 101 */         if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) {
/* 102 */           return false;
/*     */         }
/*     */         
/* 105 */         if (Math.abs(this.temptingPlayer.rotationPitch - this.pitch) > 5.0D || Math.abs(this.temptingPlayer.rotationYaw - this.yaw) > 5.0D) {
/* 106 */           return false;
/*     */         }
/*     */       } else {
/* 109 */         this.targetX = this.temptingPlayer.posX;
/* 110 */         this.targetY = this.temptingPlayer.posY;
/* 111 */         this.targetZ = this.temptingPlayer.posZ;
/*     */       } 
/*     */       
/* 114 */       this.pitch = this.temptingPlayer.rotationPitch;
/* 115 */       this.yaw = this.temptingPlayer.rotationYaw;
/*     */     } 
/*     */     
/* 118 */     return shouldExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 125 */     this.targetX = this.temptingPlayer.posX;
/* 126 */     this.targetY = this.temptingPlayer.posY;
/* 127 */     this.targetZ = this.temptingPlayer.posZ;
/* 128 */     this.isRunning = true;
/* 129 */     this.avoidWater = ((PathNavigateGround)this.temptedEntity.getNavigator()).getAvoidsWater();
/* 130 */     ((PathNavigateGround)this.temptedEntity.getNavigator()).setAvoidsWater(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 137 */     this.temptingPlayer = null;
/* 138 */     this.temptedEntity.getNavigator().clearPathEntity();
/* 139 */     this.delayTemptCounter = 100;
/* 140 */     this.isRunning = false;
/* 141 */     ((PathNavigateGround)this.temptedEntity.getNavigator()).setAvoidsWater(this.avoidWater);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 148 */     this.temptedEntity.getLookHelper().setLookPositionWithEntity((Entity)this.temptingPlayer, 30.0F, this.temptedEntity.getVerticalFaceSpeed());
/*     */     
/* 150 */     if (this.temptedEntity.getDistanceSqToEntity((Entity)this.temptingPlayer) < 6.25D) {
/* 151 */       this.temptedEntity.getNavigator().clearPathEntity();
/*     */     } else {
/* 153 */       this.temptedEntity.getNavigator().tryMoveToEntityLiving((Entity)this.temptingPlayer, this.speed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 161 */     return this.isRunning;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAITempt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */