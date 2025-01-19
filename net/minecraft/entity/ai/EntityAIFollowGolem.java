/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ 
/*    */ public class EntityAIFollowGolem extends EntityAIBase {
/*    */   private EntityVillager theVillager;
/*    */   private EntityIronGolem theGolem;
/*    */   private int takeGolemRoseTick;
/*    */   private boolean tookGolemRose;
/*    */   
/*    */   public EntityAIFollowGolem(EntityVillager theVillagerIn) {
/* 15 */     this.theVillager = theVillagerIn;
/* 16 */     setMutexBits(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 23 */     if (this.theVillager.getGrowingAge() >= 0)
/* 24 */       return false; 
/* 25 */     if (!this.theVillager.worldObj.isDaytime()) {
/* 26 */       return false;
/*    */     }
/* 28 */     List<EntityIronGolem> list = this.theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, this.theVillager.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));
/*    */     
/* 30 */     if (list.isEmpty()) {
/* 31 */       return false;
/*    */     }
/* 33 */     for (EntityIronGolem entityirongolem : list) {
/* 34 */       if (entityirongolem.getHoldRoseTick() > 0) {
/* 35 */         this.theGolem = entityirongolem;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 40 */     return (this.theGolem != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 49 */     return (this.theGolem.getHoldRoseTick() > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 56 */     this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
/* 57 */     this.tookGolemRose = false;
/* 58 */     this.theGolem.getNavigator().clearPathEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 65 */     this.theGolem = null;
/* 66 */     this.theVillager.getNavigator().clearPathEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 73 */     this.theVillager.getLookHelper().setLookPositionWithEntity((Entity)this.theGolem, 30.0F, 30.0F);
/*    */     
/* 75 */     if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
/* 76 */       this.theVillager.getNavigator().tryMoveToEntityLiving((Entity)this.theGolem, 0.5D);
/* 77 */       this.tookGolemRose = true;
/*    */     } 
/*    */     
/* 80 */     if (this.tookGolemRose && this.theVillager.getDistanceSqToEntity((Entity)this.theGolem) < 4.0D) {
/* 81 */       this.theGolem.setHoldingRose(false);
/* 82 */       this.theVillager.getNavigator().clearPathEntity();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIFollowGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */