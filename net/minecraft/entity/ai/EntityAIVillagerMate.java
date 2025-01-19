/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIVillagerMate
/*     */   extends EntityAIBase
/*     */ {
/*     */   private EntityVillager villagerObj;
/*     */   private EntityVillager mate;
/*     */   
/*     */   public EntityAIVillagerMate(EntityVillager villagerIn) {
/*  17 */     this.villagerObj = villagerIn;
/*  18 */     this.worldObj = villagerIn.worldObj;
/*  19 */     setMutexBits(3);
/*     */   }
/*     */   private World worldObj;
/*     */   private int matingTimeout;
/*     */   Village villageObj;
/*     */   
/*     */   public boolean shouldExecute() {
/*  26 */     if (this.villagerObj.getGrowingAge() != 0)
/*  27 */       return false; 
/*  28 */     if (this.villagerObj.getRNG().nextInt(500) != 0) {
/*  29 */       return false;
/*     */     }
/*  31 */     this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos((Entity)this.villagerObj), 0);
/*     */     
/*  33 */     if (this.villageObj == null)
/*  34 */       return false; 
/*  35 */     if (checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getIsWillingToMate(true)) {
/*  36 */       Entity entity = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), (Entity)this.villagerObj);
/*     */       
/*  38 */       if (entity == null) {
/*  39 */         return false;
/*     */       }
/*  41 */       this.mate = (EntityVillager)entity;
/*  42 */       return (this.mate.getGrowingAge() == 0 && this.mate.getIsWillingToMate(true));
/*     */     } 
/*     */     
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  54 */     this.matingTimeout = 300;
/*  55 */     this.villagerObj.setMating(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  62 */     this.villageObj = null;
/*  63 */     this.mate = null;
/*  64 */     this.villagerObj.setMating(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  71 */     return (this.matingTimeout >= 0 && checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getGrowingAge() == 0 && this.villagerObj.getIsWillingToMate(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  78 */     this.matingTimeout--;
/*  79 */     this.villagerObj.getLookHelper().setLookPositionWithEntity((Entity)this.mate, 10.0F, 30.0F);
/*     */     
/*  81 */     if (this.villagerObj.getDistanceSqToEntity((Entity)this.mate) > 2.25D) {
/*  82 */       this.villagerObj.getNavigator().tryMoveToEntityLiving((Entity)this.mate, 0.25D);
/*  83 */     } else if (this.matingTimeout == 0 && this.mate.isMating()) {
/*  84 */       giveBirth();
/*     */     } 
/*     */     
/*  87 */     if (this.villagerObj.getRNG().nextInt(35) == 0) {
/*  88 */       this.worldObj.setEntityState((Entity)this.villagerObj, (byte)12);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean checkSufficientDoorsPresentForNewVillager() {
/*  93 */     if (!this.villageObj.isMatingSeason()) {
/*  94 */       return false;
/*     */     }
/*  96 */     int i = (int)(this.villageObj.getNumVillageDoors() * 0.35D);
/*  97 */     return (this.villageObj.getNumVillagers() < i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void giveBirth() {
/* 102 */     EntityVillager entityvillager = this.villagerObj.createChild((EntityAgeable)this.mate);
/* 103 */     this.mate.setGrowingAge(6000);
/* 104 */     this.villagerObj.setGrowingAge(6000);
/* 105 */     this.mate.setIsWillingToMate(false);
/* 106 */     this.villagerObj.setIsWillingToMate(false);
/* 107 */     entityvillager.setGrowingAge(-24000);
/* 108 */     entityvillager.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
/* 109 */     this.worldObj.spawnEntityInWorld((Entity)entityvillager);
/* 110 */     this.worldObj.setEntityState((Entity)entityvillager, (byte)12);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIVillagerMate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */