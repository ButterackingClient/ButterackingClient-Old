/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class EntityAIWander extends EntityAIBase {
/*    */   private EntityCreature entity;
/*    */   private double xPosition;
/*    */   private double yPosition;
/*    */   private double zPosition;
/*    */   private double speed;
/*    */   private int executionChance;
/*    */   private boolean mustUpdate;
/*    */   
/*    */   public EntityAIWander(EntityCreature creatureIn, double speedIn) {
/* 16 */     this(creatureIn, speedIn, 120);
/*    */   }
/*    */   
/*    */   public EntityAIWander(EntityCreature creatureIn, double speedIn, int chance) {
/* 20 */     this.entity = creatureIn;
/* 21 */     this.speed = speedIn;
/* 22 */     this.executionChance = chance;
/* 23 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 30 */     if (!this.mustUpdate) {
/* 31 */       if (this.entity.getAge() >= 100) {
/* 32 */         return false;
/*    */       }
/*    */       
/* 35 */       if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
/* 36 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 40 */     Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
/*    */     
/* 42 */     if (vec3 == null) {
/* 43 */       return false;
/*    */     }
/* 45 */     this.xPosition = vec3.xCoord;
/* 46 */     this.yPosition = vec3.yCoord;
/* 47 */     this.zPosition = vec3.zCoord;
/* 48 */     this.mustUpdate = false;
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 57 */     return !this.entity.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 64 */     this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void makeUpdate() {
/* 71 */     this.mustUpdate = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExecutionChance(int newchance) {
/* 78 */     this.executionChance = newchance;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIWander.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */