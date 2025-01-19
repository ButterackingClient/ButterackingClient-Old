/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAILeapAtTarget
/*    */   extends EntityAIBase
/*    */ {
/*    */   EntityLiving leaper;
/*    */   EntityLivingBase leapTarget;
/*    */   float leapMotionY;
/*    */   
/*    */   public EntityAILeapAtTarget(EntityLiving leapingEntity, float leapMotionYIn) {
/* 24 */     this.leaper = leapingEntity;
/* 25 */     this.leapMotionY = leapMotionYIn;
/* 26 */     setMutexBits(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 33 */     this.leapTarget = this.leaper.getAttackTarget();
/*    */     
/* 35 */     if (this.leapTarget == null) {
/* 36 */       return false;
/*    */     }
/* 38 */     double d0 = this.leaper.getDistanceSqToEntity((Entity)this.leapTarget);
/* 39 */     return (d0 >= 4.0D && d0 <= 16.0D) ? (!this.leaper.onGround ? false : ((this.leaper.getRNG().nextInt(5) == 0))) : false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 47 */     return !this.leaper.onGround;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 54 */     double d0 = this.leapTarget.posX - this.leaper.posX;
/* 55 */     double d1 = this.leapTarget.posZ - this.leaper.posZ;
/* 56 */     float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
/* 57 */     this.leaper.motionX += d0 / f * 0.5D * 0.800000011920929D + this.leaper.motionX * 0.20000000298023224D;
/* 58 */     this.leaper.motionZ += d1 / f * 0.5D * 0.800000011920929D + this.leaper.motionZ * 0.20000000298023224D;
/* 59 */     this.leaper.motionY = this.leapMotionY;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAILeapAtTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */