/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityBreakingFX extends EntityFX {
/*    */   protected EntityBreakingFX(World worldIn, double posXIn, double posYIn, double posZIn, Item p_i1195_8_) {
/* 13 */     this(worldIn, posXIn, posYIn, posZIn, p_i1195_8_, 0);
/*    */   }
/*    */   
/*    */   protected EntityBreakingFX(World worldIn, double posXIn, double posYIn, double posZIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, Item p_i1197_14_, int p_i1197_15_) {
/* 17 */     this(worldIn, posXIn, posYIn, posZIn, p_i1197_14_, p_i1197_15_);
/* 18 */     this.motionX *= 0.10000000149011612D;
/* 19 */     this.motionY *= 0.10000000149011612D;
/* 20 */     this.motionZ *= 0.10000000149011612D;
/* 21 */     this.motionX += xSpeedIn;
/* 22 */     this.motionY += ySpeedIn;
/* 23 */     this.motionZ += zSpeedIn;
/*    */   }
/*    */   
/*    */   protected EntityBreakingFX(World worldIn, double posXIn, double posYIn, double posZIn, Item p_i1196_8_, int p_i1196_9_) {
/* 27 */     super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);
/* 28 */     setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(p_i1196_8_, p_i1196_9_));
/* 29 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 30 */     this.particleGravity = Blocks.snow.blockParticleGravity;
/* 31 */     this.particleScale /= 2.0F;
/*    */   }
/*    */   
/*    */   public int getFXLayer() {
/* 35 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 42 */     float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/* 43 */     float f1 = f + 0.015609375F;
/* 44 */     float f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/* 45 */     float f3 = f2 + 0.015609375F;
/* 46 */     float f4 = 0.1F * this.particleScale;
/*    */     
/* 48 */     if (this.particleIcon != null) {
/* 49 */       f = this.particleIcon.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/* 50 */       f1 = this.particleIcon.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/* 51 */       f2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/* 52 */       f3 = this.particleIcon.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*    */     } 
/*    */     
/* 55 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 56 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 57 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 58 */     int i = getBrightnessForRender(partialTicks);
/* 59 */     int j = i >> 16 & 0xFFFF;
/* 60 */     int k = i & 0xFFFF;
/* 61 */     worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 62 */     worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 63 */     worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 64 */     worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 69 */       int i = (p_178902_15_.length > 1) ? p_178902_15_[1] : 0;
/* 70 */       return new EntityBreakingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Item.getItemById(p_178902_15_[0]), i);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SlimeFactory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 76 */       return new EntityBreakingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.slime_ball);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SnowballFactory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 82 */       return new EntityBreakingFX(worldIn, xCoordIn, yCoordIn, zCoordIn, Items.snowball);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityBreakingFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */