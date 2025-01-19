/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityDropParticleFX
/*     */   extends EntityFX
/*     */ {
/*     */   private Material materialType;
/*     */   private int bobTimer;
/*     */   
/*     */   protected EntityDropParticleFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, Material p_i1203_8_) {
/*  23 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/*  24 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/*     */     
/*  26 */     if (p_i1203_8_ == Material.water) {
/*  27 */       this.particleRed = 0.0F;
/*  28 */       this.particleGreen = 0.0F;
/*  29 */       this.particleBlue = 1.0F;
/*     */     } else {
/*  31 */       this.particleRed = 1.0F;
/*  32 */       this.particleGreen = 0.0F;
/*  33 */       this.particleBlue = 0.0F;
/*     */     } 
/*     */     
/*  36 */     setParticleTextureIndex(113);
/*  37 */     setSize(0.01F, 0.01F);
/*  38 */     this.particleGravity = 0.06F;
/*  39 */     this.materialType = p_i1203_8_;
/*  40 */     this.bobTimer = 40;
/*  41 */     this.particleMaxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
/*  42 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  46 */     return (this.materialType == Material.water) ? super.getBrightnessForRender(partialTicks) : 257;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/*  53 */     return (this.materialType == Material.water) ? super.getBrightness(partialTicks) : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  60 */     this.prevPosX = this.posX;
/*  61 */     this.prevPosY = this.posY;
/*  62 */     this.prevPosZ = this.posZ;
/*     */     
/*  64 */     if (this.materialType == Material.water) {
/*  65 */       this.particleRed = 0.2F;
/*  66 */       this.particleGreen = 0.3F;
/*  67 */       this.particleBlue = 1.0F;
/*     */     } else {
/*  69 */       this.particleRed = 1.0F;
/*  70 */       this.particleGreen = 16.0F / (40 - this.bobTimer + 16);
/*  71 */       this.particleBlue = 4.0F / (40 - this.bobTimer + 8);
/*     */     } 
/*     */     
/*  74 */     this.motionY -= this.particleGravity;
/*     */     
/*  76 */     if (this.bobTimer-- > 0) {
/*  77 */       this.motionX *= 0.02D;
/*  78 */       this.motionY *= 0.02D;
/*  79 */       this.motionZ *= 0.02D;
/*  80 */       setParticleTextureIndex(113);
/*     */     } else {
/*  82 */       setParticleTextureIndex(112);
/*     */     } 
/*     */     
/*  85 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  86 */     this.motionX *= 0.9800000190734863D;
/*  87 */     this.motionY *= 0.9800000190734863D;
/*  88 */     this.motionZ *= 0.9800000190734863D;
/*     */     
/*  90 */     if (this.particleMaxAge-- <= 0) {
/*  91 */       setDead();
/*     */     }
/*     */     
/*  94 */     if (this.onGround) {
/*  95 */       if (this.materialType == Material.water) {
/*  96 */         setDead();
/*  97 */         this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } else {
/*  99 */         setParticleTextureIndex(114);
/*     */       } 
/*     */       
/* 102 */       this.motionX *= 0.699999988079071D;
/* 103 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */     
/* 106 */     BlockPos blockpos = new BlockPos(this);
/* 107 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 108 */     Material material = iblockstate.getBlock().getMaterial();
/*     */     
/* 110 */     if (material.isLiquid() || material.isSolid()) {
/* 111 */       double d0 = 0.0D;
/*     */       
/* 113 */       if (iblockstate.getBlock() instanceof BlockLiquid) {
/* 114 */         d0 = BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue());
/*     */       }
/*     */       
/* 117 */       double d1 = (MathHelper.floor_double(this.posY) + 1) - d0;
/*     */       
/* 119 */       if (this.posY < d1)
/* 120 */         setDead(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class LavaFactory
/*     */     implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 127 */       return new EntityDropParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, Material.lava);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WaterFactory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 133 */       return new EntityDropParticleFX(worldIn, xCoordIn, yCoordIn, zCoordIn, Material.water);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityDropParticleFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */