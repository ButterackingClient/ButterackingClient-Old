/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAIOcelotAttack
/*    */   extends EntityAIBase {
/*    */   World theWorld;
/*    */   EntityLiving theEntity;
/*    */   
/*    */   public EntityAIOcelotAttack(EntityLiving theEntityIn) {
/* 14 */     this.theEntity = theEntityIn;
/* 15 */     this.theWorld = theEntityIn.worldObj;
/* 16 */     setMutexBits(3);
/*    */   }
/*    */   
/*    */   EntityLivingBase theVictim;
/*    */   int attackCountdown;
/*    */   
/*    */   public boolean shouldExecute() {
/* 23 */     EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/*    */     
/* 25 */     if (entitylivingbase == null) {
/* 26 */       return false;
/*    */     }
/* 28 */     this.theVictim = entitylivingbase;
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 37 */     return !this.theVictim.isEntityAlive() ? false : ((this.theEntity.getDistanceSqToEntity((Entity)this.theVictim) > 225.0D) ? false : (!(this.theEntity.getNavigator().noPath() && !shouldExecute())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 44 */     this.theVictim = null;
/* 45 */     this.theEntity.getNavigator().clearPathEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 52 */     this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)this.theVictim, 30.0F, 30.0F);
/* 53 */     double d0 = (this.theEntity.width * 2.0F * this.theEntity.width * 2.0F);
/* 54 */     double d1 = this.theEntity.getDistanceSq(this.theVictim.posX, (this.theVictim.getEntityBoundingBox()).minY, this.theVictim.posZ);
/* 55 */     double d2 = 0.8D;
/*    */     
/* 57 */     if (d1 > d0 && d1 < 16.0D) {
/* 58 */       d2 = 1.33D;
/* 59 */     } else if (d1 < 225.0D) {
/* 60 */       d2 = 0.6D;
/*    */     } 
/*    */     
/* 63 */     this.theEntity.getNavigator().tryMoveToEntityLiving((Entity)this.theVictim, d2);
/* 64 */     this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
/*    */     
/* 66 */     if (d1 <= d0 && 
/* 67 */       this.attackCountdown <= 0) {
/* 68 */       this.attackCountdown = 20;
/* 69 */       this.theEntity.attackEntityAsMob((Entity)this.theVictim);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIOcelotAttack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */