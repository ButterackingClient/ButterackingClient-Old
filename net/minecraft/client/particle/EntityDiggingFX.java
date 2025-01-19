/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityDiggingFX extends EntityFX {
/*     */   private IBlockState sourceState;
/*     */   
/*     */   protected EntityDiggingFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
/*  17 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  18 */     this.sourceState = state;
/*  19 */     setParticleIcon(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
/*  20 */     this.particleGravity = (state.getBlock()).blockParticleGravity;
/*  21 */     this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
/*  22 */     this.particleScale /= 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos sourcePos;
/*     */   
/*     */   public EntityDiggingFX setBlockPos(BlockPos pos) {
/*  29 */     this.sourcePos = pos;
/*     */     
/*  31 */     if (this.sourceState.getBlock() == Blocks.grass) {
/*  32 */       return this;
/*     */     }
/*  34 */     int i = this.sourceState.getBlock().colorMultiplier((IBlockAccess)this.worldObj, pos);
/*  35 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  36 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  37 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDiggingFX func_174845_l() {
/*  43 */     this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
/*  44 */     Block block = this.sourceState.getBlock();
/*     */     
/*  46 */     if (block == Blocks.grass) {
/*  47 */       return this;
/*     */     }
/*  49 */     int i = block.getRenderColor(this.sourceState);
/*  50 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  51 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  52 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/*  58 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  65 */     float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/*  66 */     float f1 = f + 0.015609375F;
/*  67 */     float f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/*  68 */     float f3 = f2 + 0.015609375F;
/*  69 */     float f4 = 0.1F * this.particleScale;
/*     */     
/*  71 */     if (this.particleIcon != null) {
/*  72 */       f = this.particleIcon.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/*  73 */       f1 = this.particleIcon.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/*  74 */       f2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*  75 */       f3 = this.particleIcon.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } 
/*     */     
/*  78 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/*  79 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/*  80 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*  81 */     int i = getBrightnessForRender(partialTicks);
/*  82 */     int j = i >> 16 & 0xFFFF;
/*  83 */     int k = i & 0xFFFF;
/*  84 */     worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  85 */     worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  86 */     worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*  87 */     worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  91 */     int i = super.getBrightnessForRender(partialTicks);
/*  92 */     int j = 0;
/*     */     
/*  94 */     if (this.worldObj.isBlockLoaded(this.sourcePos)) {
/*  95 */       j = this.worldObj.getCombinedLight(this.sourcePos, 0);
/*     */     }
/*     */     
/*  98 */     return (i == 0) ? j : i;
/*     */   }
/*     */   
/*     */   public static class Factory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 103 */       return (new EntityDiggingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(p_178902_15_[0]))).func_174845_l();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityDiggingFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */