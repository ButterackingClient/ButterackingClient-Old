/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityExplodeFX extends EntityFX {
/*    */   protected EntityExplodeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/*  7 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  8 */     this.motionX = xSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/*  9 */     this.motionY = ySpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 10 */     this.motionZ = zSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 11 */     this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
/* 12 */     this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
/* 13 */     this.particleMaxAge = (int)(16.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 20 */     this.prevPosX = this.posX;
/* 21 */     this.prevPosY = this.posY;
/* 22 */     this.prevPosZ = this.posZ;
/*    */     
/* 24 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 25 */       setDead();
/*    */     }
/*    */     
/* 28 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 29 */     this.motionY += 0.004D;
/* 30 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 31 */     this.motionX *= 0.8999999761581421D;
/* 32 */     this.motionY *= 0.8999999761581421D;
/* 33 */     this.motionZ *= 0.8999999761581421D;
/*    */     
/* 35 */     if (this.onGround) {
/* 36 */       this.motionX *= 0.699999988079071D;
/* 37 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 43 */       return new EntityExplodeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityExplodeFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */