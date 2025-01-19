/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDoor;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class EntityAIBreakDoor extends EntityAIDoorInteract {
/* 10 */   private int previousBreakProgress = -1; private int breakingTime;
/*    */   
/*    */   public EntityAIBreakDoor(EntityLiving entityIn) {
/* 13 */     super(entityIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 20 */     if (!super.shouldExecute())
/* 21 */       return false; 
/* 22 */     if (!this.theEntity.worldObj.getGameRules().getBoolean("mobGriefing")) {
/* 23 */       return false;
/*    */     }
/* 25 */     BlockDoor blockdoor = this.doorBlock;
/* 26 */     return !BlockDoor.isOpen((IBlockAccess)this.theEntity.worldObj, this.doorPosition);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 34 */     super.startExecuting();
/* 35 */     this.breakingTime = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 42 */     double d0 = this.theEntity.getDistanceSq(this.doorPosition);
/*    */ 
/*    */     
/* 45 */     if (this.breakingTime <= 240) {
/* 46 */       BlockDoor blockdoor = this.doorBlock;
/*    */       
/* 48 */       if (!BlockDoor.isOpen((IBlockAccess)this.theEntity.worldObj, this.doorPosition) && d0 < 4.0D) {
/* 49 */         boolean bool = true;
/* 50 */         return bool;
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     boolean flag = false;
/* 55 */     return flag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 62 */     super.resetTask();
/* 63 */     this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, -1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 70 */     super.updateTask();
/*    */     
/* 72 */     if (this.theEntity.getRNG().nextInt(20) == 0) {
/* 73 */       this.theEntity.worldObj.playAuxSFX(1010, this.doorPosition, 0);
/*    */     }
/*    */     
/* 76 */     this.breakingTime++;
/* 77 */     int i = (int)(this.breakingTime / 240.0F * 10.0F);
/*    */     
/* 79 */     if (i != this.previousBreakProgress) {
/* 80 */       this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.doorPosition, i);
/* 81 */       this.previousBreakProgress = i;
/*    */     } 
/*    */     
/* 84 */     if (this.breakingTime == 240 && this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/* 85 */       this.theEntity.worldObj.setBlockToAir(this.doorPosition);
/* 86 */       this.theEntity.worldObj.playAuxSFX(1012, this.doorPosition, 0);
/* 87 */       this.theEntity.worldObj.playAuxSFX(2001, this.doorPosition, Block.getIdFromBlock((Block)this.doorBlock));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIBreakDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */