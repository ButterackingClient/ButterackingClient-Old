/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityFootStepFX extends EntityFX {
/* 15 */   private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation("textures/particle/footprint.png");
/*    */   private int footstepAge;
/*    */   private int footstepMaxAge;
/*    */   private TextureManager currentFootSteps;
/*    */   
/*    */   protected EntityFootStepFX(TextureManager currentFootStepsIn, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 21 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 22 */     this.currentFootSteps = currentFootStepsIn;
/* 23 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/* 24 */     this.footstepMaxAge = 200;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 31 */     float f = (this.footstepAge + partialTicks) / this.footstepMaxAge;
/* 32 */     f *= f;
/* 33 */     float f1 = 2.0F - f * 2.0F;
/*    */     
/* 35 */     if (f1 > 1.0F) {
/* 36 */       f1 = 1.0F;
/*    */     }
/*    */     
/* 39 */     f1 *= 0.2F;
/* 40 */     GlStateManager.disableLighting();
/* 41 */     float f2 = 0.125F;
/* 42 */     float f3 = (float)(this.posX - interpPosX);
/* 43 */     float f4 = (float)(this.posY - interpPosY);
/* 44 */     float f5 = (float)(this.posZ - interpPosZ);
/* 45 */     float f6 = this.worldObj.getLightBrightness(new BlockPos(this));
/* 46 */     this.currentFootSteps.bindTexture(FOOTPRINT_TEXTURE);
/* 47 */     GlStateManager.enableBlend();
/* 48 */     GlStateManager.blendFunc(770, 771);
/* 49 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 50 */     worldRendererIn.pos((f3 - 0.125F), f4, (f5 + 0.125F)).tex(0.0D, 1.0D).color(f6, f6, f6, f1).endVertex();
/* 51 */     worldRendererIn.pos((f3 + 0.125F), f4, (f5 + 0.125F)).tex(1.0D, 1.0D).color(f6, f6, f6, f1).endVertex();
/* 52 */     worldRendererIn.pos((f3 + 0.125F), f4, (f5 - 0.125F)).tex(1.0D, 0.0D).color(f6, f6, f6, f1).endVertex();
/* 53 */     worldRendererIn.pos((f3 - 0.125F), f4, (f5 - 0.125F)).tex(0.0D, 0.0D).color(f6, f6, f6, f1).endVertex();
/* 54 */     Tessellator.getInstance().draw();
/* 55 */     GlStateManager.disableBlend();
/* 56 */     GlStateManager.enableLighting();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 63 */     this.footstepAge++;
/*    */     
/* 65 */     if (this.footstepAge == this.footstepMaxAge) {
/* 66 */       setDead();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getFXLayer() {
/* 71 */     return 3;
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 76 */       return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityFootStepFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */