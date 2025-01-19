/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityHeartFX extends EntityFX {
/*    */   float particleScaleOverTime;
/*    */   
/*    */   protected EntityHeartFX(World worldIn, double p_i1211_2_, double p_i1211_4_, double p_i1211_6_, double p_i1211_8_, double p_i1211_10_, double p_i1211_12_) {
/* 12 */     this(worldIn, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0F);
/*    */   }
/*    */   
/*    */   protected EntityHeartFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46354_8_, double p_i46354_10_, double p_i46354_12_, float scale) {
/* 16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 17 */     this.motionX *= 0.009999999776482582D;
/* 18 */     this.motionY *= 0.009999999776482582D;
/* 19 */     this.motionZ *= 0.009999999776482582D;
/* 20 */     this.motionY += 0.1D;
/* 21 */     this.particleScale *= 0.75F;
/* 22 */     this.particleScale *= scale;
/* 23 */     this.particleScaleOverTime = this.particleScale;
/* 24 */     this.particleMaxAge = 16;
/* 25 */     this.noClip = false;
/* 26 */     setParticleTextureIndex(80);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 33 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 34 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 35 */     this.particleScale = this.particleScaleOverTime * f;
/* 36 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 43 */     this.prevPosX = this.posX;
/* 44 */     this.prevPosY = this.posY;
/* 45 */     this.prevPosZ = this.posZ;
/*    */     
/* 47 */     if (this.particleAge++ >= this.particleMaxAge) {
/* 48 */       setDead();
/*    */     }
/*    */     
/* 51 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 53 */     if (this.posY == this.prevPosY) {
/* 54 */       this.motionX *= 1.1D;
/* 55 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 58 */     this.motionX *= 0.8600000143051147D;
/* 59 */     this.motionY *= 0.8600000143051147D;
/* 60 */     this.motionZ *= 0.8600000143051147D;
/*    */     
/* 62 */     if (this.onGround) {
/* 63 */       this.motionX *= 0.699999988079071D;
/* 64 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class AngryVillagerFactory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 70 */       EntityFX entityfx = new EntityHeartFX(worldIn, xCoordIn, yCoordIn + 0.5D, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 71 */       entityfx.setParticleTextureIndex(81);
/* 72 */       entityfx.setRBGColorF(1.0F, 1.0F, 1.0F);
/* 73 */       return entityfx;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 79 */       return new EntityHeartFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityHeartFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */