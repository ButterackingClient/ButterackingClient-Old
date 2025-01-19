/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityGuardian;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class MobAppearance extends EntityFX {
/*    */   private EntityLivingBase entity;
/*    */   
/*    */   protected MobAppearance(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 18 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 19 */     this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
/* 20 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/* 21 */     this.particleGravity = 0.0F;
/* 22 */     this.particleMaxAge = 30;
/*    */   }
/*    */   
/*    */   public int getFXLayer() {
/* 26 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 33 */     super.onUpdate();
/*    */     
/* 35 */     if (this.entity == null) {
/* 36 */       EntityGuardian entityguardian = new EntityGuardian(this.worldObj);
/* 37 */       entityguardian.setElder();
/* 38 */       this.entity = (EntityLivingBase)entityguardian;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 46 */     if (this.entity != null) {
/* 47 */       RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 48 */       rendermanager.setRenderPosition(EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
/* 49 */       float f = 0.42553192F;
/* 50 */       float f1 = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 51 */       GlStateManager.depthMask(true);
/* 52 */       GlStateManager.enableBlend();
/* 53 */       GlStateManager.enableDepth();
/* 54 */       GlStateManager.blendFunc(770, 771);
/* 55 */       float f2 = 240.0F;
/* 56 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f2, f2);
/* 57 */       GlStateManager.pushMatrix();
/* 58 */       float f3 = 0.05F + 0.5F * MathHelper.sin(f1 * 3.1415927F);
/* 59 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f3);
/* 60 */       GlStateManager.translate(0.0F, 1.8F, 0.0F);
/* 61 */       GlStateManager.rotate(180.0F - entityIn.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 62 */       GlStateManager.rotate(60.0F - 150.0F * f1 - entityIn.rotationPitch, 1.0F, 0.0F, 0.0F);
/* 63 */       GlStateManager.translate(0.0F, -0.4F, -1.5F);
/* 64 */       GlStateManager.scale(f, f, f);
/* 65 */       this.entity.rotationYaw = this.entity.prevRotationYaw = 0.0F;
/* 66 */       this.entity.rotationYawHead = this.entity.prevRotationYawHead = 0.0F;
/* 67 */       rendermanager.renderEntityWithPosYaw((Entity)this.entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
/* 68 */       GlStateManager.popMatrix();
/* 69 */       GlStateManager.enableDepth();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 75 */       return new MobAppearance(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\MobAppearance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */