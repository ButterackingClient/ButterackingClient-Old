/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class Barrier extends EntityFX {
/*    */   protected Barrier(World worldIn, double p_i46286_2_, double p_i46286_4_, double p_i46286_6_, Item p_i46286_8_) {
/* 12 */     super(worldIn, p_i46286_2_, p_i46286_4_, p_i46286_6_, 0.0D, 0.0D, 0.0D);
/* 13 */     setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(p_i46286_8_));
/* 14 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 15 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/* 16 */     this.particleGravity = 0.0F;
/* 17 */     this.particleMaxAge = 80;
/*    */   }
/*    */   
/*    */   public int getFXLayer() {
/* 21 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 28 */     float f = this.particleIcon.getMinU();
/* 29 */     float f1 = this.particleIcon.getMaxU();
/* 30 */     float f2 = this.particleIcon.getMinV();
/* 31 */     float f3 = this.particleIcon.getMaxV();
/* 32 */     float f4 = 0.5F;
/* 33 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 34 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 35 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 36 */     int i = getBrightnessForRender(partialTicks);
/* 37 */     int j = i >> 16 & 0xFFFF;
/* 38 */     int k = i & 0xFFFF;
/* 39 */     worldRendererIn.pos((f5 - rotationX * 0.5F - rotationXY * 0.5F), (f6 - rotationZ * 0.5F), (f7 - rotationYZ * 0.5F - rotationXZ * 0.5F)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 40 */     worldRendererIn.pos((f5 - rotationX * 0.5F + rotationXY * 0.5F), (f6 + rotationZ * 0.5F), (f7 - rotationYZ * 0.5F + rotationXZ * 0.5F)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 41 */     worldRendererIn.pos((f5 + rotationX * 0.5F + rotationXY * 0.5F), (f6 + rotationZ * 0.5F), (f7 + rotationYZ * 0.5F + rotationXZ * 0.5F)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 42 */     worldRendererIn.pos((f5 + rotationX * 0.5F - rotationXY * 0.5F), (f6 - rotationZ * 0.5F), (f7 + rotationYZ * 0.5F - rotationXZ * 0.5F)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 47 */       return new Barrier(worldIn, xCoordIn, yCoordIn, zCoordIn, Item.getItemFromBlock(Blocks.barrier));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\Barrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */