/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ 
/*    */ public class EntityAIRestrictSun extends EntityAIBase {
/*    */   private EntityCreature theEntity;
/*    */   
/*    */   public EntityAIRestrictSun(EntityCreature creature) {
/* 10 */     this.theEntity = creature;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 17 */     return this.theEntity.worldObj.isDaytime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 24 */     ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 31 */     ((PathNavigateGround)this.theEntity.getNavigator()).setAvoidSun(false);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIRestrictSun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */