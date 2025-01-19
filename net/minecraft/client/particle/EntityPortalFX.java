/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityPortalFX extends EntityFX {
/*    */   private float portalParticleScale;
/*    */   private double portalPosX;
/*    */   private double portalPosY;
/*    */   private double portalPosZ;
/*    */   
/*    */   protected EntityPortalFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 14 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 15 */     this.motionX = xSpeedIn;
/* 16 */     this.motionY = ySpeedIn;
/* 17 */     this.motionZ = zSpeedIn;
/* 18 */     this.portalPosX = this.posX = xCoordIn;
/* 19 */     this.portalPosY = this.posY = yCoordIn;
/* 20 */     this.portalPosZ = this.posZ = zCoordIn;
/* 21 */     float f = this.rand.nextFloat() * 0.6F + 0.4F;
/* 22 */     this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
/* 23 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
/* 24 */     this.particleGreen *= 0.3F;
/* 25 */     this.particleRed *= 0.9F;
/* 26 */     this.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
/* 27 */     this.noClip = true;
/* 28 */     setParticleTextureIndex((int)(Math.random() * 8.0D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 35 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 36 */     f = 1.0F - f;
/* 37 */     f *= f;
/* 38 */     f = 1.0F - f;
/* 39 */     this.particleScale = this.portalParticleScale * f;
/* 40 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 44 */     int i = super.getBrightnessForRender(partialTicks);
/* 45 */     float f = this.particleAge / this.particleMaxAge;
/* 46 */     f *= f;
/* 47 */     f *= f;
/* 48 */     int j = i & 0xFF;
/* 49 */     int k = i >> 16 & 0xFF;
/* 50 */     k += (int)(f * 15.0F * 16.0F);
/*    */     
/* 52 */     if (k > 240) {
/* 53 */       k = 240;
/*    */     }
/*    */     
/* 56 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBrightness(float partialTicks) {
/* 63 */     float f = super.getBrightness(partialTicks);
/* 64 */     float f1 = this.particleAge / this.particleMaxAge;
/* 65 */     f1 = f1 * f1 * f1 * f1;
/* 66 */     return f * (1.0F - f1) + f1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 73 */     this.prevPosX = this.posX;
/* 74 */     this.prevPosY = this.posY;
/* 75 */     this.prevPosZ = this.posZ;
/* 76 */     float f = this.particleAge / this.particleMaxAge;
/* 77 */     f = -f + f * f * 2.0F;
/* 78 */     f = 1.0F - f;
/* 79 */     this.posX = this.portalPosX + this.motionX * f;
/* 80 */     this.posY = this.portalPosY + this.motionY * f + (1.0F - f);
/* 81 */     this.posZ = this.portalPosZ + this.motionZ * f;
/*    */     
/* 83 */     if (this.particleAge++ >= this.particleMaxAge)
/* 84 */       setDead(); 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 90 */       return new EntityPortalFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityPortalFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */