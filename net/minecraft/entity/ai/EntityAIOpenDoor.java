/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAIOpenDoor
/*    */   extends EntityAIDoorInteract
/*    */ {
/*    */   boolean closeDoor;
/*    */   int closeDoorTemporisation;
/*    */   
/*    */   public EntityAIOpenDoor(EntityLiving entitylivingIn, boolean shouldClose) {
/* 17 */     super(entitylivingIn);
/* 18 */     this.theEntity = entitylivingIn;
/* 19 */     this.closeDoor = shouldClose;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 26 */     return (this.closeDoor && this.closeDoorTemporisation > 0 && super.continueExecuting());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 33 */     this.closeDoorTemporisation = 20;
/* 34 */     this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 41 */     if (this.closeDoor) {
/* 42 */       this.doorBlock.toggleDoor(this.theEntity.worldObj, this.doorPosition, false);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 50 */     this.closeDoorTemporisation--;
/* 51 */     super.updateTask();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIOpenDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */