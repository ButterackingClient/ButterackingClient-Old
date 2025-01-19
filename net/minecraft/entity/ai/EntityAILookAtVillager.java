/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ 
/*    */ public class EntityAILookAtVillager
/*    */   extends EntityAIBase {
/*    */   private EntityIronGolem theGolem;
/*    */   
/*    */   public EntityAILookAtVillager(EntityIronGolem theGolemIn) {
/* 12 */     this.theGolem = theGolemIn;
/* 13 */     setMutexBits(3);
/*    */   }
/*    */   
/*    */   private EntityVillager theVillager;
/*    */   private int lookTime;
/*    */   
/*    */   public boolean shouldExecute() {
/* 20 */     if (!this.theGolem.worldObj.isDaytime())
/* 21 */       return false; 
/* 22 */     if (this.theGolem.getRNG().nextInt(8000) != 0) {
/* 23 */       return false;
/*    */     }
/* 25 */     this.theVillager = (EntityVillager)this.theGolem.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.theGolem.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D), (Entity)this.theGolem);
/* 26 */     return (this.theVillager != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 34 */     return (this.lookTime > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 41 */     this.lookTime = 400;
/* 42 */     this.theGolem.setHoldingRose(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 49 */     this.theGolem.setHoldingRose(false);
/* 50 */     this.theVillager = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 57 */     this.theGolem.getLookHelper().setLookPositionWithEntity((Entity)this.theVillager, 30.0F, 30.0F);
/* 58 */     this.lookTime--;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAILookAtVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */