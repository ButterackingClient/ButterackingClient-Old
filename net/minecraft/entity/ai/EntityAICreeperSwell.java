/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAICreeperSwell
/*    */   extends EntityAIBase
/*    */ {
/*    */   EntityCreeper swellingCreeper;
/*    */   EntityLivingBase creeperAttackTarget;
/*    */   
/*    */   public EntityAICreeperSwell(EntityCreeper entitycreeperIn) {
/* 18 */     this.swellingCreeper = entitycreeperIn;
/* 19 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 26 */     EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
/* 27 */     return !(this.swellingCreeper.getCreeperState() <= 0 && (entitylivingbase == null || this.swellingCreeper.getDistanceSqToEntity((Entity)entitylivingbase) >= 9.0D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 34 */     this.swellingCreeper.getNavigator().clearPathEntity();
/* 35 */     this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 42 */     this.creeperAttackTarget = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 49 */     if (this.creeperAttackTarget == null) {
/* 50 */       this.swellingCreeper.setCreeperState(-1);
/* 51 */     } else if (this.swellingCreeper.getDistanceSqToEntity((Entity)this.creeperAttackTarget) > 49.0D) {
/* 52 */       this.swellingCreeper.setCreeperState(-1);
/* 53 */     } else if (!this.swellingCreeper.getEntitySenses().canSee((Entity)this.creeperAttackTarget)) {
/* 54 */       this.swellingCreeper.setCreeperState(-1);
/*    */     } else {
/* 56 */       this.swellingCreeper.setCreeperState(1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAICreeperSwell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */