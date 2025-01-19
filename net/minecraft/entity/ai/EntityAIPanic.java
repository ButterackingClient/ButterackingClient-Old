/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class EntityAIPanic extends EntityAIBase {
/*    */   private EntityCreature theEntityCreature;
/*    */   protected double speed;
/*    */   private double randPosX;
/*    */   private double randPosY;
/*    */   private double randPosZ;
/*    */   
/*    */   public EntityAIPanic(EntityCreature creature, double speedIn) {
/* 14 */     this.theEntityCreature = creature;
/* 15 */     this.speed = speedIn;
/* 16 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 23 */     if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning()) {
/* 24 */       return false;
/*    */     }
/* 26 */     Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
/*    */     
/* 28 */     if (vec3 == null) {
/* 29 */       return false;
/*    */     }
/* 31 */     this.randPosX = vec3.xCoord;
/* 32 */     this.randPosY = vec3.yCoord;
/* 33 */     this.randPosZ = vec3.zCoord;
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 43 */     this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 50 */     return !this.theEntityCreature.getNavigator().noPath();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIPanic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */