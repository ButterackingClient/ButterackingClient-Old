/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class EntityAIMoveTowardsRestriction extends EntityAIBase {
/*    */   private EntityCreature theEntity;
/*    */   private double movePosX;
/*    */   private double movePosY;
/*    */   private double movePosZ;
/*    */   private double movementSpeed;
/*    */   
/*    */   public EntityAIMoveTowardsRestriction(EntityCreature creatureIn, double speedIn) {
/* 15 */     this.theEntity = creatureIn;
/* 16 */     this.movementSpeed = speedIn;
/* 17 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 24 */     if (this.theEntity.isWithinHomeDistanceCurrentPosition()) {
/* 25 */       return false;
/*    */     }
/* 27 */     BlockPos blockpos = this.theEntity.getHomePosition();
/* 28 */     Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
/*    */     
/* 30 */     if (vec3 == null) {
/* 31 */       return false;
/*    */     }
/* 33 */     this.movePosX = vec3.xCoord;
/* 34 */     this.movePosY = vec3.yCoord;
/* 35 */     this.movePosZ = vec3.zCoord;
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 45 */     return !this.theEntity.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 52 */     this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIMoveTowardsRestriction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */