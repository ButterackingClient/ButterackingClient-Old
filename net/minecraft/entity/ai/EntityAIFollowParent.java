/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityAnimal;
/*    */ 
/*    */ 
/*    */ public class EntityAIFollowParent
/*    */   extends EntityAIBase
/*    */ {
/*    */   EntityAnimal childAnimal;
/*    */   EntityAnimal parentAnimal;
/*    */   double moveSpeed;
/*    */   private int delayCounter;
/*    */   
/*    */   public EntityAIFollowParent(EntityAnimal animal, double speed) {
/* 17 */     this.childAnimal = animal;
/* 18 */     this.moveSpeed = speed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 25 */     if (this.childAnimal.getGrowingAge() >= 0) {
/* 26 */       return false;
/*    */     }
/* 28 */     List<EntityAnimal> list = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D));
/* 29 */     EntityAnimal entityanimal = null;
/* 30 */     double d0 = Double.MAX_VALUE;
/*    */     
/* 32 */     for (EntityAnimal entityanimal1 : list) {
/* 33 */       if (entityanimal1.getGrowingAge() >= 0) {
/* 34 */         double d1 = this.childAnimal.getDistanceSqToEntity((Entity)entityanimal1);
/*    */         
/* 36 */         if (d1 <= d0) {
/* 37 */           d0 = d1;
/* 38 */           entityanimal = entityanimal1;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 43 */     if (entityanimal == null)
/* 44 */       return false; 
/* 45 */     if (d0 < 9.0D) {
/* 46 */       return false;
/*    */     }
/* 48 */     this.parentAnimal = entityanimal;
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 58 */     if (this.childAnimal.getGrowingAge() >= 0)
/* 59 */       return false; 
/* 60 */     if (!this.parentAnimal.isEntityAlive()) {
/* 61 */       return false;
/*    */     }
/* 63 */     double d0 = this.childAnimal.getDistanceSqToEntity((Entity)this.parentAnimal);
/* 64 */     return (d0 >= 9.0D && d0 <= 256.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 72 */     this.delayCounter = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 79 */     this.parentAnimal = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 86 */     if (--this.delayCounter <= 0) {
/* 87 */       this.delayCounter = 10;
/* 88 */       this.childAnimal.getNavigator().tryMoveToEntityLiving((Entity)this.parentAnimal, this.moveSpeed);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIFollowParent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */