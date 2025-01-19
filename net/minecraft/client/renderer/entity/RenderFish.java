/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RenderFish extends Render<EntityFishHook> {
/*  14 */   private static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");
/*     */   
/*     */   public RenderFish(RenderManager renderManagerIn) {
/*  17 */     super(renderManagerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityFishHook entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  24 */     GlStateManager.pushMatrix();
/*  25 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*  26 */     GlStateManager.enableRescaleNormal();
/*  27 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  28 */     bindEntityTexture(entity);
/*  29 */     Tessellator tessellator = Tessellator.getInstance();
/*  30 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  31 */     int i = 1;
/*  32 */     int j = 2;
/*  33 */     float f = 0.0625F;
/*  34 */     float f1 = 0.125F;
/*  35 */     float f2 = 0.125F;
/*  36 */     float f3 = 0.1875F;
/*  37 */     float f4 = 1.0F;
/*  38 */     float f5 = 0.5F;
/*  39 */     float f6 = 0.5F;
/*  40 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  41 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  42 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*  43 */     worldrenderer.pos(-0.5D, -0.5D, 0.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  44 */     worldrenderer.pos(0.5D, -0.5D, 0.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  45 */     worldrenderer.pos(0.5D, 0.5D, 0.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  46 */     worldrenderer.pos(-0.5D, 0.5D, 0.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  47 */     tessellator.draw();
/*  48 */     GlStateManager.disableRescaleNormal();
/*  49 */     GlStateManager.popMatrix();
/*     */     
/*  51 */     if (entity.angler != null) {
/*  52 */       float f7 = entity.angler.getSwingProgress(partialTicks);
/*  53 */       float f8 = MathHelper.sin(MathHelper.sqrt_float(f7) * 3.1415927F);
/*  54 */       Vec3 vec3 = new Vec3(-0.36D, 0.03D, 0.35D);
/*  55 */       vec3 = vec3.rotatePitch(-(entity.angler.prevRotationPitch + (entity.angler.rotationPitch - entity.angler.prevRotationPitch) * partialTicks) * 3.1415927F / 180.0F);
/*  56 */       vec3 = vec3.rotateYaw(-(entity.angler.prevRotationYaw + (entity.angler.rotationYaw - entity.angler.prevRotationYaw) * partialTicks) * 3.1415927F / 180.0F);
/*  57 */       vec3 = vec3.rotateYaw(f8 * 0.5F);
/*  58 */       vec3 = vec3.rotatePitch(-f8 * 0.7F);
/*  59 */       double d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * partialTicks + vec3.xCoord;
/*  60 */       double d1 = entity.angler.prevPosY + (entity.angler.posY - entity.angler.prevPosY) * partialTicks + vec3.yCoord;
/*  61 */       double d2 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * partialTicks + vec3.zCoord;
/*  62 */       double d3 = entity.angler.getEyeHeight();
/*     */       
/*  64 */       if ((this.renderManager.options != null && this.renderManager.options.showDebugInfo > 0) || entity.angler != (Minecraft.getMinecraft()).thePlayer) {
/*  65 */         float f9 = (entity.angler.prevRenderYawOffset + (entity.angler.renderYawOffset - entity.angler.prevRenderYawOffset) * partialTicks) * 3.1415927F / 180.0F;
/*  66 */         double d4 = MathHelper.sin(f9);
/*  67 */         double d6 = MathHelper.cos(f9);
/*  68 */         double d8 = 0.35D;
/*  69 */         double d10 = 0.8D;
/*  70 */         d0 = entity.angler.prevPosX + (entity.angler.posX - entity.angler.prevPosX) * partialTicks - d6 * 0.35D - d4 * 0.8D;
/*  71 */         d1 = entity.angler.prevPosY + d3 + (entity.angler.posY - entity.angler.prevPosY) * partialTicks - 0.45D;
/*  72 */         d2 = entity.angler.prevPosZ + (entity.angler.posZ - entity.angler.prevPosZ) * partialTicks - d4 * 0.35D + d6 * 0.8D;
/*  73 */         d3 = entity.angler.isSneaking() ? -0.1875D : 0.0D;
/*     */       } 
/*     */       
/*  76 */       double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  77 */       double d5 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + 0.25D;
/*  78 */       double d7 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  79 */       double d9 = (float)(d0 - d13);
/*  80 */       double d11 = (float)(d1 - d5) + d3;
/*  81 */       double d12 = (float)(d2 - d7);
/*  82 */       GlStateManager.disableTexture2D();
/*  83 */       GlStateManager.disableLighting();
/*  84 */       worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/*  85 */       int k = 16;
/*     */       
/*  87 */       for (int l = 0; l <= 16; l++) {
/*  88 */         float f10 = l / 16.0F;
/*  89 */         worldrenderer.pos(x + d9 * f10, y + d11 * (f10 * f10 + f10) * 0.5D + 0.25D, z + d12 * f10).color(0, 0, 0, 255).endVertex();
/*     */       } 
/*     */       
/*  92 */       tessellator.draw();
/*  93 */       GlStateManager.enableLighting();
/*  94 */       GlStateManager.enableTexture2D();
/*  95 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityFishHook entity) {
/* 103 */     return FISH_PARTICLES;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderFish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */