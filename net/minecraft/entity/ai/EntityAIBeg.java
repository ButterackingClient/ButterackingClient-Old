/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAIBeg
/*    */   extends EntityAIBase
/*    */ {
/*    */   private EntityWolf theWolf;
/*    */   private EntityPlayer thePlayer;
/*    */   
/*    */   public EntityAIBeg(EntityWolf wolf, float minDistance) {
/* 17 */     this.theWolf = wolf;
/* 18 */     this.worldObject = wolf.worldObj;
/* 19 */     this.minPlayerDistance = minDistance;
/* 20 */     setMutexBits(2);
/*    */   }
/*    */   private World worldObject;
/*    */   private float minPlayerDistance;
/*    */   private int timeoutCounter;
/*    */   
/*    */   public boolean shouldExecute() {
/* 27 */     this.thePlayer = this.worldObject.getClosestPlayerToEntity((Entity)this.theWolf, this.minPlayerDistance);
/* 28 */     return (this.thePlayer == null) ? false : hasPlayerGotBoneInHand(this.thePlayer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 35 */     return !this.thePlayer.isEntityAlive() ? false : ((this.theWolf.getDistanceSqToEntity((Entity)this.thePlayer) > (this.minPlayerDistance * this.minPlayerDistance)) ? false : ((this.timeoutCounter > 0 && hasPlayerGotBoneInHand(this.thePlayer))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 42 */     this.theWolf.setBegging(true);
/* 43 */     this.timeoutCounter = 40 + this.theWolf.getRNG().nextInt(40);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 50 */     this.theWolf.setBegging(false);
/* 51 */     this.thePlayer = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 58 */     this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, this.theWolf.getVerticalFaceSpeed());
/* 59 */     this.timeoutCounter--;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean hasPlayerGotBoneInHand(EntityPlayer player) {
/* 66 */     ItemStack itemstack = player.inventory.getCurrentItem();
/* 67 */     return (itemstack == null) ? false : ((!this.theWolf.isTamed() && itemstack.getItem() == Items.bone) ? true : this.theWolf.isBreedingItem(itemstack));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIBeg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */