/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
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
/*    */ 
/*    */ 
/*    */ public class DestroyBlockProgress
/*    */ {
/*    */   private final int miningPlayerEntId;
/*    */   private final BlockPos position;
/*    */   private int partialBlockProgress;
/*    */   private int createdAtCloudUpdateTick;
/*    */   
/*    */   public DestroyBlockProgress(int miningPlayerEntIdIn, BlockPos positionIn) {
/* 24 */     this.miningPlayerEntId = miningPlayerEntIdIn;
/* 25 */     this.position = positionIn;
/*    */   }
/*    */   
/*    */   public BlockPos getPosition() {
/* 29 */     return this.position;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPartialBlockDamage(int damage) {
/* 37 */     if (damage > 10) {
/* 38 */       damage = 10;
/*    */     }
/*    */     
/* 41 */     this.partialBlockProgress = damage;
/*    */   }
/*    */   
/*    */   public int getPartialBlockDamage() {
/* 45 */     return this.partialBlockProgress;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCloudUpdateTick(int createdAtCloudUpdateTickIn) {
/* 52 */     this.createdAtCloudUpdateTick = createdAtCloudUpdateTickIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCreationCloudUpdateTick() {
/* 59 */     return this.createdAtCloudUpdateTick;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\DestroyBlockProgress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */