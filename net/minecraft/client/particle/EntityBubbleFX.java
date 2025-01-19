/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityBubbleFX extends EntityFX {
/*    */   protected EntityBubbleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/*  9 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 10 */     this.particleRed = 1.0F;
/* 11 */     this.particleGreen = 1.0F;
/* 12 */     this.particleBlue = 1.0F;
/* 13 */     setParticleTextureIndex(32);
/* 14 */     setSize(0.02F, 0.02F);
/* 15 */     this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
/* 16 */     this.motionX = xSpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 17 */     this.motionY = ySpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 18 */     this.motionZ = zSpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 19 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     this.prevPosX = this.posX;
/* 27 */     this.prevPosY = this.posY;
/* 28 */     this.prevPosZ = this.posZ;
/* 29 */     this.motionY += 0.002D;
/* 30 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 31 */     this.motionX *= 0.8500000238418579D;
/* 32 */     this.motionY *= 0.8500000238418579D;
/* 33 */     this.motionZ *= 0.8500000238418579D;
/*    */     
/* 35 */     if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
/* 36 */       setDead();
/*    */     }
/*    */     
/* 39 */     if (this.particleMaxAge-- <= 0)
/* 40 */       setDead(); 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 46 */       return new EntityBubbleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityBubbleFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */