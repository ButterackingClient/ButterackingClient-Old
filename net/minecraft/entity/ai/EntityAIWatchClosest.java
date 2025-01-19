/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAIWatchClosest
/*    */   extends EntityAIBase
/*    */ {
/*    */   protected EntityLiving theWatcher;
/*    */   protected Entity closestEntity;
/*    */   protected float maxDistanceForPlayer;
/*    */   private int lookTime;
/*    */   private float chance;
/*    */   protected Class<? extends Entity> watchedClass;
/*    */   
/*    */   public EntityAIWatchClosest(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance) {
/* 24 */     this.theWatcher = entitylivingIn;
/* 25 */     this.watchedClass = watchTargetClass;
/* 26 */     this.maxDistanceForPlayer = maxDistance;
/* 27 */     this.chance = 0.02F;
/* 28 */     setMutexBits(2);
/*    */   }
/*    */   
/*    */   public EntityAIWatchClosest(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance, float chanceIn) {
/* 32 */     this.theWatcher = entitylivingIn;
/* 33 */     this.watchedClass = watchTargetClass;
/* 34 */     this.maxDistanceForPlayer = maxDistance;
/* 35 */     this.chance = chanceIn;
/* 36 */     setMutexBits(2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 43 */     if (this.theWatcher.getRNG().nextFloat() >= this.chance) {
/* 44 */       return false;
/*    */     }
/* 46 */     if (this.theWatcher.getAttackTarget() != null) {
/* 47 */       this.closestEntity = (Entity)this.theWatcher.getAttackTarget();
/*    */     }
/*    */     
/* 50 */     if (this.watchedClass == EntityPlayer.class) {
/* 51 */       this.closestEntity = (Entity)this.theWatcher.worldObj.getClosestPlayerToEntity((Entity)this.theWatcher, this.maxDistanceForPlayer);
/*    */     } else {
/* 53 */       this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.getEntityBoundingBox().expand(this.maxDistanceForPlayer, 3.0D, this.maxDistanceForPlayer), (Entity)this.theWatcher);
/*    */     } 
/*    */     
/* 56 */     return (this.closestEntity != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 64 */     return !this.closestEntity.isEntityAlive() ? false : ((this.theWatcher.getDistanceSqToEntity(this.closestEntity) > (this.maxDistanceForPlayer * this.maxDistanceForPlayer)) ? false : ((this.lookTime > 0)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 71 */     this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 78 */     this.closestEntity = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 85 */     this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, this.theWatcher.getVerticalFaceSpeed());
/* 86 */     this.lookTime--;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIWatchClosest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */