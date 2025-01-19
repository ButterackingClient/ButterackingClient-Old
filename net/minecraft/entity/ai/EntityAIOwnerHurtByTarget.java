/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ 
/*    */ public class EntityAIOwnerHurtByTarget
/*    */   extends EntityAITarget {
/*    */   EntityTameable theDefendingTameable;
/*    */   
/*    */   public EntityAIOwnerHurtByTarget(EntityTameable theDefendingTameableIn) {
/* 12 */     super((EntityCreature)theDefendingTameableIn, false);
/* 13 */     this.theDefendingTameable = theDefendingTameableIn;
/* 14 */     setMutexBits(1);
/*    */   }
/*    */   
/*    */   EntityLivingBase theOwnerAttacker;
/*    */   private int field_142051_e;
/*    */   
/*    */   public boolean shouldExecute() {
/* 21 */     if (!this.theDefendingTameable.isTamed()) {
/* 22 */       return false;
/*    */     }
/* 24 */     EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
/*    */     
/* 26 */     if (entitylivingbase == null) {
/* 27 */       return false;
/*    */     }
/* 29 */     this.theOwnerAttacker = entitylivingbase.getAITarget();
/* 30 */     int i = entitylivingbase.getRevengeTimer();
/* 31 */     return (i != this.field_142051_e && isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, entitylivingbase));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 40 */     this.taskOwner.setAttackTarget(this.theOwnerAttacker);
/* 41 */     EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
/*    */     
/* 43 */     if (entitylivingbase != null) {
/* 44 */       this.field_142051_e = entitylivingbase.getRevengeTimer();
/*    */     }
/*    */     
/* 47 */     super.startExecuting();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIOwnerHurtByTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */