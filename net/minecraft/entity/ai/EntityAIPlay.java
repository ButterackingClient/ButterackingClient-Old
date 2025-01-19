/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class EntityAIPlay
/*    */   extends EntityAIBase {
/*    */   private EntityVillager villagerObj;
/*    */   private EntityLivingBase targetVillager;
/*    */   
/*    */   public EntityAIPlay(EntityVillager villagerObjIn, double speedIn) {
/* 16 */     this.villagerObj = villagerObjIn;
/* 17 */     this.speed = speedIn;
/* 18 */     setMutexBits(1);
/*    */   }
/*    */   
/*    */   private double speed;
/*    */   private int playTime;
/*    */   
/*    */   public boolean shouldExecute() {
/* 25 */     if (this.villagerObj.getGrowingAge() >= 0)
/* 26 */       return false; 
/* 27 */     if (this.villagerObj.getRNG().nextInt(400) != 0) {
/* 28 */       return false;
/*    */     }
/* 30 */     List<EntityVillager> list = this.villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
/* 31 */     double d0 = Double.MAX_VALUE;
/*    */     
/* 33 */     for (EntityVillager entityvillager : list) {
/* 34 */       if (entityvillager != this.villagerObj && !entityvillager.isPlaying() && entityvillager.getGrowingAge() < 0) {
/* 35 */         double d1 = entityvillager.getDistanceSqToEntity((Entity)this.villagerObj);
/*    */         
/* 37 */         if (d1 <= d0) {
/* 38 */           d0 = d1;
/* 39 */           this.targetVillager = (EntityLivingBase)entityvillager;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     if (this.targetVillager == null) {
/* 45 */       Vec3 vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.villagerObj, 16, 3);
/*    */       
/* 47 */       if (vec3 == null) {
/* 48 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 60 */     return (this.playTime > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 67 */     if (this.targetVillager != null) {
/* 68 */       this.villagerObj.setPlaying(true);
/*    */     }
/*    */     
/* 71 */     this.playTime = 1000;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 78 */     this.villagerObj.setPlaying(false);
/* 79 */     this.targetVillager = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 86 */     this.playTime--;
/*    */     
/* 88 */     if (this.targetVillager != null) {
/* 89 */       if (this.villagerObj.getDistanceSqToEntity((Entity)this.targetVillager) > 4.0D) {
/* 90 */         this.villagerObj.getNavigator().tryMoveToEntityLiving((Entity)this.targetVillager, this.speed);
/*    */       }
/* 92 */     } else if (this.villagerObj.getNavigator().noPath()) {
/* 93 */       Vec3 vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.villagerObj, 16, 3);
/*    */       
/* 95 */       if (vec3 == null) {
/*    */         return;
/*    */       }
/*    */       
/* 99 */       this.villagerObj.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIPlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */