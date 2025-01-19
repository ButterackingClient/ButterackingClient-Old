/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.village.Village;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAIDefendVillage
/*    */   extends EntityAITarget
/*    */ {
/*    */   EntityIronGolem irongolem;
/*    */   EntityLivingBase villageAgressorTarget;
/*    */   
/*    */   public EntityAIDefendVillage(EntityIronGolem ironGolemIn) {
/* 17 */     super((EntityCreature)ironGolemIn, false, true);
/* 18 */     this.irongolem = ironGolemIn;
/* 19 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 26 */     Village village = this.irongolem.getVillage();
/*    */     
/* 28 */     if (village == null) {
/* 29 */       return false;
/*    */     }
/* 31 */     this.villageAgressorTarget = village.findNearestVillageAggressor((EntityLivingBase)this.irongolem);
/*    */     
/* 33 */     if (this.villageAgressorTarget instanceof net.minecraft.entity.monster.EntityCreeper)
/* 34 */       return false; 
/* 35 */     if (!isSuitableTarget(this.villageAgressorTarget, false)) {
/* 36 */       if (this.taskOwner.getRNG().nextInt(20) == 0) {
/* 37 */         this.villageAgressorTarget = (EntityLivingBase)village.getNearestTargetPlayer((EntityLivingBase)this.irongolem);
/* 38 */         return isSuitableTarget(this.villageAgressorTarget, false);
/*    */       } 
/* 40 */       return false;
/*    */     } 
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 52 */     this.irongolem.setAttackTarget(this.villageAgressorTarget);
/* 53 */     super.startExecuting();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIDefendVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */