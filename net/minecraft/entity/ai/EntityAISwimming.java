/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ 
/*    */ public class EntityAISwimming extends EntityAIBase {
/*    */   private EntityLiving theEntity;
/*    */   
/*    */   public EntityAISwimming(EntityLiving entitylivingIn) {
/* 10 */     this.theEntity = entitylivingIn;
/* 11 */     setMutexBits(4);
/* 12 */     ((PathNavigateGround)entitylivingIn.getNavigator()).setCanSwim(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 19 */     return !(!this.theEntity.isInWater() && !this.theEntity.isInLava());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 26 */     if (this.theEntity.getRNG().nextFloat() < 0.8F)
/* 27 */       this.theEntity.getJumpHelper().setJumping(); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAISwimming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */