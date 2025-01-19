/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySpellParticleFX
/*     */   extends EntityFX {
/*  11 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  16 */   private int baseSpellTextureIndex = 128;
/*     */   
/*     */   protected EntitySpellParticleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1229_8_, double p_i1229_10_, double p_i1229_12_) {
/*  19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.5D - RANDOM.nextDouble(), p_i1229_10_, 0.5D - RANDOM.nextDouble());
/*  20 */     this.motionY *= 0.20000000298023224D;
/*     */     
/*  22 */     if (p_i1229_8_ == 0.0D && p_i1229_12_ == 0.0D) {
/*  23 */       this.motionX *= 0.10000000149011612D;
/*  24 */       this.motionZ *= 0.10000000149011612D;
/*     */     } 
/*     */     
/*  27 */     this.particleScale *= 0.75F;
/*  28 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*  29 */     this.noClip = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  36 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/*  37 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/*  38 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  45 */     this.prevPosX = this.posX;
/*  46 */     this.prevPosY = this.posY;
/*  47 */     this.prevPosZ = this.posZ;
/*     */     
/*  49 */     if (this.particleAge++ >= this.particleMaxAge) {
/*  50 */       setDead();
/*     */     }
/*     */     
/*  53 */     setParticleTextureIndex(this.baseSpellTextureIndex + 7 - this.particleAge * 8 / this.particleMaxAge);
/*  54 */     this.motionY += 0.004D;
/*  55 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/*  57 */     if (this.posY == this.prevPosY) {
/*  58 */       this.motionX *= 1.1D;
/*  59 */       this.motionZ *= 1.1D;
/*     */     } 
/*     */     
/*  62 */     this.motionX *= 0.9599999785423279D;
/*  63 */     this.motionY *= 0.9599999785423279D;
/*  64 */     this.motionZ *= 0.9599999785423279D;
/*     */     
/*  66 */     if (this.onGround) {
/*  67 */       this.motionX *= 0.699999988079071D;
/*  68 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseSpellTextureIndex(int baseSpellTextureIndexIn) {
/*  76 */     this.baseSpellTextureIndex = baseSpellTextureIndexIn;
/*     */   }
/*     */   
/*     */   public static class AmbientMobFactory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  81 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  82 */       entityfx.setAlphaF(0.15F);
/*  83 */       entityfx.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/*  84 */       return entityfx;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Factory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  90 */       return new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class InstantFactory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  96 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  97 */       ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
/*  98 */       return entityfx;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MobFactory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 104 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 105 */       entityfx.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/* 106 */       return entityfx;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WitchFactory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 112 */       EntityFX entityfx = new EntitySpellParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 113 */       ((EntitySpellParticleFX)entityfx).setBaseSpellTextureIndex(144);
/* 114 */       float f = worldIn.rand.nextFloat() * 0.5F + 0.35F;
/* 115 */       entityfx.setRBGColorF(1.0F * f, 0.0F * f, 1.0F * f);
/* 116 */       return entityfx;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntitySpellParticleFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */