/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityLavaFX extends EntityFX {
/*    */   private float lavaParticleScale;
/*    */   
/*    */   protected EntityLavaFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 13 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 14 */     this.motionX *= 0.800000011920929D;
/* 15 */     this.motionY *= 0.800000011920929D;
/* 16 */     this.motionZ *= 0.800000011920929D;
/* 17 */     this.motionY = (this.rand.nextFloat() * 0.4F + 0.05F);
/* 18 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 19 */     this.particleScale *= this.rand.nextFloat() * 2.0F + 0.2F;
/* 20 */     this.lavaParticleScale = this.particleScale;
/* 21 */     this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/* 22 */     this.noClip = false;
/* 23 */     setParticleTextureIndex(49);
/*    */   }
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 27 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 28 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 29 */     int i = super.getBrightnessForRender(partialTicks);
/* 30 */     int j = 240;
/* 31 */     int k = i >> 16 & 0xFF;
/* 32 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBrightness(float partialTicks) {
/* 39 */     return 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 46 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 47 */     this.particleScale = this.lavaParticleScale * (1.0F - f * f);
/* 48 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 55 */     this.prevPosX = this.posX;
/* 56 */     this.prevPosY = this.posY;
/* 57 */     this.prevPosZ = this.posZ;
/*    */     
/* 59 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 60 */       setDead();
/*    */     }
/*    */     
/* 63 */     float f = this.particleAge / this.particleMaxAge;
/*    */     
/* 65 */     if (this.rand.nextFloat() > f) {
/* 66 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, new int[0]);
/*    */     }
/*    */     
/* 69 */     this.motionY -= 0.03D;
/* 70 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 71 */     this.motionX *= 0.9990000128746033D;
/* 72 */     this.motionY *= 0.9990000128746033D;
/* 73 */     this.motionZ *= 0.9990000128746033D;
/*    */     
/* 75 */     if (this.onGround) {
/* 76 */       this.motionX *= 0.699999988079071D;
/* 77 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 83 */       return new EntityLavaFX(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityLavaFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */