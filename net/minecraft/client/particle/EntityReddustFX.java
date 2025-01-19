/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityReddustFX extends EntityFX {
/*    */   float reddustParticleScale;
/*    */   
/*    */   protected EntityReddustFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float p_i46349_8_, float p_i46349_9_, float p_i46349_10_) {
/* 12 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, 1.0F, p_i46349_8_, p_i46349_9_, p_i46349_10_);
/*    */   }
/*    */   
/*    */   protected EntityReddustFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float p_i46350_8_, float p_i46350_9_, float p_i46350_10_, float p_i46350_11_) {
/* 16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 17 */     this.motionX *= 0.10000000149011612D;
/* 18 */     this.motionY *= 0.10000000149011612D;
/* 19 */     this.motionZ *= 0.10000000149011612D;
/*    */     
/* 21 */     if (p_i46350_9_ == 0.0F) {
/* 22 */       p_i46350_9_ = 1.0F;
/*    */     }
/*    */     
/* 25 */     float f = (float)Math.random() * 0.4F + 0.6F;
/* 26 */     this.particleRed = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_9_ * f;
/* 27 */     this.particleGreen = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_10_ * f;
/* 28 */     this.particleBlue = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_11_ * f;
/* 29 */     this.particleScale *= 0.75F;
/* 30 */     this.particleScale *= p_i46350_8_;
/* 31 */     this.reddustParticleScale = this.particleScale;
/* 32 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/* 33 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i46350_8_);
/* 34 */     this.noClip = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 41 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 42 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 43 */     this.particleScale = this.reddustParticleScale * f;
/* 44 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 51 */     this.prevPosX = this.posX;
/* 52 */     this.prevPosY = this.posY;
/* 53 */     this.prevPosZ = this.posZ;
/*    */     
/* 55 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 56 */       setDead();
/*    */     }
/*    */     
/* 59 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 60 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 62 */     if (this.posY == this.prevPosY) {
/* 63 */       this.motionX *= 1.1D;
/* 64 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 67 */     this.motionX *= 0.9599999785423279D;
/* 68 */     this.motionY *= 0.9599999785423279D;
/* 69 */     this.motionZ *= 0.9599999785423279D;
/*    */     
/* 71 */     if (this.onGround) {
/* 72 */       this.motionX *= 0.699999988079071D;
/* 73 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 79 */       return new EntityReddustFX(worldIn, xCoordIn, yCoordIn, zCoordIn, (float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityReddustFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */