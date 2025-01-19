/*    */ package net.minecraft.entity;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityFlying extends EntityLiving {
/*    */   public EntityFlying(World worldIn) {
/* 10 */     super(worldIn);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fall(float distance, float damageMultiplier) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {}
/*    */ 
/*    */   
/*    */   public void moveEntityWithHeading(float strafe, float forward) {
/* 23 */     if (isInWater()) {
/* 24 */       moveFlying(strafe, forward, 0.02F);
/* 25 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 26 */       this.motionX *= 0.800000011920929D;
/* 27 */       this.motionY *= 0.800000011920929D;
/* 28 */       this.motionZ *= 0.800000011920929D;
/* 29 */     } else if (isInLava()) {
/* 30 */       moveFlying(strafe, forward, 0.02F);
/* 31 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 32 */       this.motionX *= 0.5D;
/* 33 */       this.motionY *= 0.5D;
/* 34 */       this.motionZ *= 0.5D;
/*    */     } else {
/* 36 */       float f = 0.91F;
/*    */       
/* 38 */       if (this.onGround) {
/* 39 */         f = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*    */       }
/*    */       
/* 42 */       float f1 = 0.16277136F / f * f * f;
/* 43 */       moveFlying(strafe, forward, this.onGround ? (0.1F * f1) : 0.02F);
/* 44 */       f = 0.91F;
/*    */       
/* 46 */       if (this.onGround) {
/* 47 */         f = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*    */       }
/*    */       
/* 50 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 51 */       this.motionX *= f;
/* 52 */       this.motionY *= f;
/* 53 */       this.motionZ *= f;
/*    */     } 
/*    */     
/* 56 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 57 */     double d1 = this.posX - this.prevPosX;
/* 58 */     double d0 = this.posZ - this.prevPosZ;
/* 59 */     float f2 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
/*    */     
/* 61 */     if (f2 > 1.0F) {
/* 62 */       f2 = 1.0F;
/*    */     }
/*    */     
/* 65 */     this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
/* 66 */     this.limbSwing += this.limbSwingAmount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOnLadder() {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityFlying.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */