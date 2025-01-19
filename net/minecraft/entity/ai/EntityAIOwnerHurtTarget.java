/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ 
/*    */ public class EntityAIOwnerHurtTarget
/*    */   extends EntityAITarget {
/*    */   EntityTameable theEntityTameable;
/*    */   
/*    */   public EntityAIOwnerHurtTarget(EntityTameable theEntityTameableIn) {
/* 12 */     super((EntityCreature)theEntityTameableIn, false);
/* 13 */     this.theEntityTameable = theEntityTameableIn;
/* 14 */     setMutexBits(1);
/*    */   }
/*    */   
/*    */   EntityLivingBase theTarget;
/*    */   private int field_142050_e;
/*    */   
/*    */   public boolean shouldExecute() {
/* 21 */     if (!this.theEntityTameable.isTamed()) {
/* 22 */       return false;
/*    */     }
/* 24 */     EntityLivingBase entitylivingbase = this.theEntityTameable.getOwner();
/*    */     
/* 26 */     if (entitylivingbase == null) {
/* 27 */       return false;
/*    */     }
/* 29 */     this.theTarget = entitylivingbase.getLastAttacker();
/* 30 */     int i = entitylivingbase.getLastAttackerTime();
/* 31 */     return (i != this.field_142050_e && isSuitableTarget(this.theTarget, false) && this.theEntityTameable.shouldAttackEntity(this.theTarget, entitylivingbase));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 40 */     this.taskOwner.setAttackTarget(this.theTarget);
/* 41 */     EntityLivingBase entitylivingbase = this.theEntityTameable.getOwner();
/*    */     
/* 43 */     if (entitylivingbase != null) {
/* 44 */       this.field_142050_e = entitylivingbase.getLastAttackerTime();
/*    */     }
/*    */     
/* 47 */     super.startExecuting();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIOwnerHurtTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */