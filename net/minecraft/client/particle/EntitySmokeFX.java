/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySmokeFX extends EntityFX {
/*    */   float smokeParticleScale;
/*    */   
/*    */   private EntitySmokeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46347_8_, double p_i46347_10_, double p_i46347_12_) {
/* 12 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46347_8_, p_i46347_10_, p_i46347_12_, 1.0F);
/*    */   }
/*    */   
/*    */   protected EntitySmokeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46348_8_, double p_i46348_10_, double p_i46348_12_, float p_i46348_14_) {
/* 16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 17 */     this.motionX *= 0.10000000149011612D;
/* 18 */     this.motionY *= 0.10000000149011612D;
/* 19 */     this.motionZ *= 0.10000000149011612D;
/* 20 */     this.motionX += p_i46348_8_;
/* 21 */     this.motionY += p_i46348_10_;
/* 22 */     this.motionZ += p_i46348_12_;
/* 23 */     this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D);
/* 24 */     this.particleScale *= 0.75F;
/* 25 */     this.particleScale *= p_i46348_14_;
/* 26 */     this.smokeParticleScale = this.particleScale;
/* 27 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/* 28 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i46348_14_);
/* 29 */     this.noClip = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 36 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 37 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 38 */     this.particleScale = this.smokeParticleScale * f;
/* 39 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 46 */     this.prevPosX = this.posX;
/* 47 */     this.prevPosY = this.posY;
/* 48 */     this.prevPosZ = this.posZ;
/*    */     
/* 50 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 51 */       setDead();
/*    */     }
/*    */     
/* 54 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 55 */     this.motionY += 0.004D;
/* 56 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 58 */     if (this.posY == this.prevPosY) {
/* 59 */       this.motionX *= 1.1D;
/* 60 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 63 */     this.motionX *= 0.9599999785423279D;
/* 64 */     this.motionY *= 0.9599999785423279D;
/* 65 */     this.motionZ *= 0.9599999785423279D;
/*    */     
/* 67 */     if (this.onGround) {
/* 68 */       this.motionX *= 0.699999988079071D;
/* 69 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 75 */       return new EntitySmokeFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntitySmokeFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */