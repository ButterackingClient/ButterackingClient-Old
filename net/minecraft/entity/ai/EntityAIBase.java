/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityAIBase
/*    */ {
/*    */   private int mutexBits;
/*    */   
/*    */   public abstract boolean shouldExecute();
/*    */   
/*    */   public boolean continueExecuting() {
/* 19 */     return shouldExecute();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInterruptible() {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMutexBits(int mutexBitsIn) {
/* 53 */     this.mutexBits = mutexBitsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMutexBits() {
/* 61 */     return this.mutexBits;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */