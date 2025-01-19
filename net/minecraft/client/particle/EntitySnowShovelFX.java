/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySnowShovelFX extends EntityFX {
/*    */   float snowDigParticleScale;
/*    */   
/*    */   protected EntitySnowShovelFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 12 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.0F);
/*    */   }
/*    */   
/*    */   protected EntitySnowShovelFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float p_i1228_14_) {
/* 16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 17 */     this.motionX *= 0.10000000149011612D;
/* 18 */     this.motionY *= 0.10000000149011612D;
/* 19 */     this.motionZ *= 0.10000000149011612D;
/* 20 */     this.motionX += xSpeedIn;
/* 21 */     this.motionY += ySpeedIn;
/* 22 */     this.motionZ += zSpeedIn;
/* 23 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F - (float)(Math.random() * 0.30000001192092896D);
/* 24 */     this.particleScale *= 0.75F;
/* 25 */     this.particleScale *= p_i1228_14_;
/* 26 */     this.snowDigParticleScale = this.particleScale;
/* 27 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/* 28 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i1228_14_);
/* 29 */     this.noClip = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 36 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 37 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 38 */     this.particleScale = this.snowDigParticleScale * f;
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
/* 55 */     this.motionY -= 0.03D;
/* 56 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 57 */     this.motionX *= 0.9900000095367432D;
/* 58 */     this.motionY *= 0.9900000095367432D;
/* 59 */     this.motionZ *= 0.9900000095367432D;
/*    */     
/* 61 */     if (this.onGround) {
/* 62 */       this.motionX *= 0.699999988079071D;
/* 63 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 69 */       return new EntitySnowShovelFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntitySnowShovelFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */