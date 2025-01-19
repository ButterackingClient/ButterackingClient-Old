/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class EntityFlameFX
/*    */   extends EntityFX
/*    */ {
/*    */   private float flameScale;
/*    */   
/*    */   protected EntityFlameFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 15 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 16 */     this.motionX = this.motionX * 0.009999999776482582D + xSpeedIn;
/* 17 */     this.motionY = this.motionY * 0.009999999776482582D + ySpeedIn;
/* 18 */     this.motionZ = this.motionZ * 0.009999999776482582D + zSpeedIn;
/* 19 */     this.posX += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 20 */     this.posY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 21 */     this.posZ += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 22 */     this.flameScale = this.particleScale;
/* 23 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 24 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
/* 25 */     this.noClip = true;
/* 26 */     setParticleTextureIndex(48);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 33 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 34 */     this.particleScale = this.flameScale * (1.0F - f * f * 0.5F);
/* 35 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 39 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 40 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 41 */     int i = super.getBrightnessForRender(partialTicks);
/* 42 */     int j = i & 0xFF;
/* 43 */     int k = i >> 16 & 0xFF;
/* 44 */     j += (int)(f * 15.0F * 16.0F);
/*    */     
/* 46 */     if (j > 240) {
/* 47 */       j = 240;
/*    */     }
/*    */     
/* 50 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBrightness(float partialTicks) {
/* 57 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 58 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 59 */     float f1 = super.getBrightness(partialTicks);
/* 60 */     return f1 * f + 1.0F - f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 67 */     this.prevPosX = this.posX;
/* 68 */     this.prevPosY = this.posY;
/* 69 */     this.prevPosZ = this.posZ;
/*    */     
/* 71 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 72 */       setDead();
/*    */     }
/*    */     
/* 75 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 76 */     this.motionX *= 0.9599999785423279D;
/* 77 */     this.motionY *= 0.9599999785423279D;
/* 78 */     this.motionZ *= 0.9599999785423279D;
/*    */     
/* 80 */     if (this.onGround) {
/* 81 */       this.motionX *= 0.699999988079071D;
/* 82 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 88 */       return new EntityFlameFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityFlameFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */