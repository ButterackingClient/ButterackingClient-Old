/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ 
/*    */ public class EntityAIMoveTowardsTarget
/*    */   extends EntityAIBase
/*    */ {
/*    */   private EntityCreature theEntity;
/*    */   private EntityLivingBase targetEntity;
/*    */   private double movePosX;
/*    */   private double movePosY;
/*    */   private double movePosZ;
/*    */   private double speed;
/*    */   private float maxTargetDistance;
/*    */   
/*    */   public EntityAIMoveTowardsTarget(EntityCreature creature, double speedIn, float targetMaxDistance) {
/* 21 */     this.theEntity = creature;
/* 22 */     this.speed = speedIn;
/* 23 */     this.maxTargetDistance = targetMaxDistance;
/* 24 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 31 */     this.targetEntity = this.theEntity.getAttackTarget();
/*    */     
/* 33 */     if (this.targetEntity == null)
/* 34 */       return false; 
/* 35 */     if (this.targetEntity.getDistanceSqToEntity((Entity)this.theEntity) > (this.maxTargetDistance * this.maxTargetDistance)) {
/* 36 */       return false;
/*    */     }
/* 38 */     Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ));
/*    */     
/* 40 */     if (vec3 == null) {
/* 41 */       return false;
/*    */     }
/* 43 */     this.movePosX = vec3.xCoord;
/* 44 */     this.movePosY = vec3.yCoord;
/* 45 */     this.movePosZ = vec3.zCoord;
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 55 */     return (!this.theEntity.getNavigator().noPath() && this.targetEntity.isEntityAlive() && this.targetEntity.getDistanceSqToEntity((Entity)this.theEntity) < (this.maxTargetDistance * this.maxTargetDistance));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 62 */     this.targetEntity = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 69 */     this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIMoveTowardsTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */