/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIMate
/*     */   extends EntityAIBase
/*     */ {
/*     */   private EntityAnimal theAnimal;
/*     */   World theWorld;
/*     */   private EntityAnimal targetMate;
/*     */   int spawnBabyDelay;
/*     */   double moveSpeed;
/*     */   
/*     */   public EntityAIMate(EntityAnimal animal, double speedIn) {
/*  32 */     this.theAnimal = animal;
/*  33 */     this.theWorld = animal.worldObj;
/*  34 */     this.moveSpeed = speedIn;
/*  35 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  42 */     if (!this.theAnimal.isInLove()) {
/*  43 */       return false;
/*     */     }
/*  45 */     this.targetMate = getNearbyMate();
/*  46 */     return (this.targetMate != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  54 */     return (this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  61 */     this.targetMate = null;
/*  62 */     this.spawnBabyDelay = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  69 */     this.theAnimal.getLookHelper().setLookPositionWithEntity((Entity)this.targetMate, 10.0F, this.theAnimal.getVerticalFaceSpeed());
/*  70 */     this.theAnimal.getNavigator().tryMoveToEntityLiving((Entity)this.targetMate, this.moveSpeed);
/*  71 */     this.spawnBabyDelay++;
/*     */     
/*  73 */     if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity((Entity)this.targetMate) < 9.0D) {
/*  74 */       spawnBaby();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityAnimal getNearbyMate() {
/*  83 */     float f = 8.0F;
/*  84 */     List<EntityAnimal> list = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand(f, f, f));
/*  85 */     double d0 = Double.MAX_VALUE;
/*  86 */     EntityAnimal entityanimal = null;
/*     */     
/*  88 */     for (EntityAnimal entityanimal1 : list) {
/*  89 */       if (this.theAnimal.canMateWith(entityanimal1) && this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1) < d0) {
/*  90 */         entityanimal = entityanimal1;
/*  91 */         d0 = this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1);
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return entityanimal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnBaby() {
/* 102 */     EntityAgeable entityageable = this.theAnimal.createChild((EntityAgeable)this.targetMate);
/*     */     
/* 104 */     if (entityageable != null) {
/* 105 */       EntityPlayer entityplayer = this.theAnimal.getPlayerInLove();
/*     */       
/* 107 */       if (entityplayer == null && this.targetMate.getPlayerInLove() != null) {
/* 108 */         entityplayer = this.targetMate.getPlayerInLove();
/*     */       }
/*     */       
/* 111 */       if (entityplayer != null) {
/* 112 */         entityplayer.triggerAchievement(StatList.animalsBredStat);
/*     */         
/* 114 */         if (this.theAnimal instanceof net.minecraft.entity.passive.EntityCow) {
/* 115 */           entityplayer.triggerAchievement((StatBase)AchievementList.breedCow);
/*     */         }
/*     */       } 
/*     */       
/* 119 */       this.theAnimal.setGrowingAge(6000);
/* 120 */       this.targetMate.setGrowingAge(6000);
/* 121 */       this.theAnimal.resetInLove();
/* 122 */       this.targetMate.resetInLove();
/* 123 */       entityageable.setGrowingAge(-24000);
/* 124 */       entityageable.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
/* 125 */       this.theWorld.spawnEntityInWorld((Entity)entityageable);
/* 126 */       Random random = this.theAnimal.getRNG();
/*     */       
/* 128 */       for (int i = 0; i < 7; i++) {
/* 129 */         double d0 = random.nextGaussian() * 0.02D;
/* 130 */         double d1 = random.nextGaussian() * 0.02D;
/* 131 */         double d2 = random.nextGaussian() * 0.02D;
/* 132 */         double d3 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 133 */         double d4 = 0.5D + random.nextDouble() * this.theAnimal.height;
/* 134 */         double d5 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 135 */         this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + d3, this.theAnimal.posY + d4, this.theAnimal.posZ + d5, d0, d1, d2, new int[0]);
/*     */       } 
/*     */       
/* 138 */       if (this.theWorld.getGameRules().getBoolean("doMobLoot"))
/* 139 */         this.theWorld.spawnEntityInWorld((Entity)new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, random.nextInt(7) + 1)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIMate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */