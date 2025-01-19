/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ 
/*    */ 
/*    */ public class EntityAISit
/*    */   extends EntityAIBase
/*    */ {
/*    */   private EntityTameable theEntity;
/*    */   private boolean isSitting;
/*    */   
/*    */   public EntityAISit(EntityTameable entityIn) {
/* 15 */     this.theEntity = entityIn;
/* 16 */     setMutexBits(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 23 */     if (!this.theEntity.isTamed())
/* 24 */       return false; 
/* 25 */     if (this.theEntity.isInWater())
/* 26 */       return false; 
/* 27 */     if (!this.theEntity.onGround) {
/* 28 */       return false;
/*    */     }
/* 30 */     EntityLivingBase entitylivingbase = this.theEntity.getOwner();
/* 31 */     return (entitylivingbase == null) ? true : ((this.theEntity.getDistanceSqToEntity((Entity)entitylivingbase) < 144.0D && entitylivingbase.getAITarget() != null) ? false : this.isSitting);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 39 */     this.theEntity.getNavigator().clearPathEntity();
/* 40 */     this.theEntity.setSitting(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 47 */     this.theEntity.setSitting(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSitting(boolean sitting) {
/* 54 */     this.isSitting = sitting;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAISit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */