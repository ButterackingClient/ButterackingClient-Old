/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAuraFX extends EntityFX {
/*    */   protected EntityAuraFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double speedIn) {
/*  7 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, speedIn);
/*  8 */     float f = this.rand.nextFloat() * 0.1F + 0.2F;
/*  9 */     this.particleRed = f;
/* 10 */     this.particleGreen = f;
/* 11 */     this.particleBlue = f;
/* 12 */     setParticleTextureIndex(0);
/* 13 */     setSize(0.02F, 0.02F);
/* 14 */     this.particleScale *= this.rand.nextFloat() * 0.6F + 0.5F;
/* 15 */     this.motionX *= 0.019999999552965164D;
/* 16 */     this.motionY *= 0.019999999552965164D;
/* 17 */     this.motionZ *= 0.019999999552965164D;
/* 18 */     this.particleMaxAge = (int)(20.0D / (Math.random() * 0.8D + 0.2D));
/* 19 */     this.noClip = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     this.prevPosX = this.posX;
/* 27 */     this.prevPosY = this.posY;
/* 28 */     this.prevPosZ = this.posZ;
/* 29 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 30 */     this.motionX *= 0.99D;
/* 31 */     this.motionY *= 0.99D;
/* 32 */     this.motionZ *= 0.99D;
/*    */     
/* 34 */     if (this.particleMaxAge-- <= 0)
/* 35 */       setDead(); 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 41 */       return new EntityAuraFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class HappyVillagerFactory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 47 */       EntityFX entityfx = new EntityAuraFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 48 */       entityfx.setParticleTextureIndex(82);
/* 49 */       entityfx.setRBGColorF(1.0F, 1.0F, 1.0F);
/* 50 */       return entityfx;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityAuraFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */