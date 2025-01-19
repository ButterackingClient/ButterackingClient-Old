/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityCrit2FX extends EntityFX {
/*    */   float field_174839_a;
/*    */   
/*    */   protected EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46284_8_, double p_i46284_10_, double p_i46284_12_) {
/* 12 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46284_8_, p_i46284_10_, p_i46284_12_, 1.0F);
/*    */   }
/*    */   
/*    */   protected EntityCrit2FX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46285_8_, double p_i46285_10_, double p_i46285_12_, float p_i46285_14_) {
/* 16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 17 */     this.motionX *= 0.10000000149011612D;
/* 18 */     this.motionY *= 0.10000000149011612D;
/* 19 */     this.motionZ *= 0.10000000149011612D;
/* 20 */     this.motionX += p_i46285_8_ * 0.4D;
/* 21 */     this.motionY += p_i46285_10_ * 0.4D;
/* 22 */     this.motionZ += p_i46285_12_ * 0.4D;
/* 23 */     this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
/* 24 */     this.particleScale *= 0.75F;
/* 25 */     this.particleScale *= p_i46285_14_;
/* 26 */     this.field_174839_a = this.particleScale;
/* 27 */     this.particleMaxAge = (int)(6.0D / (Math.random() * 0.8D + 0.6D));
/* 28 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i46285_14_);
/* 29 */     this.noClip = false;
/* 30 */     setParticleTextureIndex(65);
/* 31 */     onUpdate();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 38 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 39 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 40 */     this.particleScale = this.field_174839_a * f;
/* 41 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 48 */     this.prevPosX = this.posX;
/* 49 */     this.prevPosY = this.posY;
/* 50 */     this.prevPosZ = this.posZ;
/*    */     
/* 52 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 53 */       setDead();
/*    */     }
/*    */     
/* 56 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 57 */     this.particleGreen = (float)(this.particleGreen * 0.96D);
/* 58 */     this.particleBlue = (float)(this.particleBlue * 0.9D);
/* 59 */     this.motionX *= 0.699999988079071D;
/* 60 */     this.motionY *= 0.699999988079071D;
/* 61 */     this.motionZ *= 0.699999988079071D;
/* 62 */     this.motionY -= 0.019999999552965164D;
/*    */     
/* 64 */     if (this.onGround) {
/* 65 */       this.motionX *= 0.699999988079071D;
/* 66 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 72 */       return new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class MagicFactory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 78 */       EntityFX entityfx = new EntityCrit2FX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 79 */       entityfx.setRBGColorF(entityfx.getRedColorF() * 0.3F, entityfx.getGreenColorF() * 0.8F, entityfx.getBlueColorF());
/* 80 */       entityfx.nextTextureIndexX();
/* 81 */       return entityfx;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityCrit2FX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */