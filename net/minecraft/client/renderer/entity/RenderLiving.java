/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public abstract class RenderLiving<T extends EntityLiving> extends RendererLivingEntity<T> {
/*     */   public RenderLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
/*  18 */     super(rendermanagerIn, modelbaseIn, shadowsizeIn);
/*     */   }
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/*  22 */     return (super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || (entity.hasCustomName() && entity == this.renderManager.pointedEntity)));
/*     */   }
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  26 */     if (super.shouldRender(livingEntity, camera, camX, camY, camZ))
/*  27 */       return true; 
/*  28 */     if (livingEntity.getLeashed() && livingEntity.getLeashedToEntity() != null) {
/*  29 */       Entity entity = livingEntity.getLeashedToEntity();
/*  30 */       return camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox());
/*     */     } 
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  40 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*  41 */     renderLeash(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */   
/*     */   public void setLightmap(T entityLivingIn, float partialTicks) {
/*  45 */     int i = entityLivingIn.getBrightnessForRender(partialTicks);
/*  46 */     int j = i % 65536;
/*  47 */     int k = i / 65536;
/*  48 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double interpolateValue(double start, double end, double pct) {
/*  55 */     return start + (end - start) * pct;
/*     */   }
/*     */   
/*     */   protected void renderLeash(T entityLivingIn, double x, double y, double z, float entityYaw, float partialTicks) {
/*  59 */     if (!Config.isShaders() || !Shaders.isShadowPass) {
/*  60 */       Entity entity = entityLivingIn.getLeashedToEntity();
/*     */       
/*  62 */       if (entity != null) {
/*  63 */         y -= (1.6D - ((EntityLiving)entityLivingIn).height) * 0.5D;
/*  64 */         Tessellator tessellator = Tessellator.getInstance();
/*  65 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  66 */         double d0 = interpolateValue(entity.prevRotationYaw, entity.rotationYaw, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  67 */         double d1 = interpolateValue(entity.prevRotationPitch, entity.rotationPitch, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  68 */         double d2 = Math.cos(d0);
/*  69 */         double d3 = Math.sin(d0);
/*  70 */         double d4 = Math.sin(d1);
/*     */         
/*  72 */         if (entity instanceof net.minecraft.entity.EntityHanging) {
/*  73 */           d2 = 0.0D;
/*  74 */           d3 = 0.0D;
/*  75 */           d4 = -1.0D;
/*     */         } 
/*     */         
/*  78 */         double d5 = Math.cos(d1);
/*  79 */         double d6 = interpolateValue(entity.prevPosX, entity.posX, partialTicks) - d2 * 0.7D - d3 * 0.5D * d5;
/*  80 */         double d7 = interpolateValue(entity.prevPosY + entity.getEyeHeight() * 0.7D, entity.posY + entity.getEyeHeight() * 0.7D, partialTicks) - d4 * 0.5D - 0.25D;
/*  81 */         double d8 = interpolateValue(entity.prevPosZ, entity.posZ, partialTicks) - d3 * 0.7D + d2 * 0.5D * d5;
/*  82 */         double d9 = interpolateValue(((EntityLiving)entityLivingIn).prevRenderYawOffset, ((EntityLiving)entityLivingIn).renderYawOffset, partialTicks) * 0.01745329238474369D + 1.5707963267948966D;
/*  83 */         d2 = Math.cos(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/*  84 */         d3 = Math.sin(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/*  85 */         double d10 = interpolateValue(((EntityLiving)entityLivingIn).prevPosX, ((EntityLiving)entityLivingIn).posX, partialTicks) + d2;
/*  86 */         double d11 = interpolateValue(((EntityLiving)entityLivingIn).prevPosY, ((EntityLiving)entityLivingIn).posY, partialTicks);
/*  87 */         double d12 = interpolateValue(((EntityLiving)entityLivingIn).prevPosZ, ((EntityLiving)entityLivingIn).posZ, partialTicks) + d3;
/*  88 */         x += d2;
/*  89 */         z += d3;
/*  90 */         double d13 = (float)(d6 - d10);
/*  91 */         double d14 = (float)(d7 - d11);
/*  92 */         double d15 = (float)(d8 - d12);
/*  93 */         GlStateManager.disableTexture2D();
/*  94 */         GlStateManager.disableLighting();
/*  95 */         GlStateManager.disableCull();
/*     */         
/*  97 */         if (Config.isShaders()) {
/*  98 */           Shaders.beginLeash();
/*     */         }
/*     */         
/* 101 */         int i = 24;
/* 102 */         double d16 = 0.025D;
/* 103 */         worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 105 */         for (int j = 0; j <= 24; j++) {
/* 106 */           float f = 0.5F;
/* 107 */           float f1 = 0.4F;
/* 108 */           float f2 = 0.3F;
/*     */           
/* 110 */           if (j % 2 == 0) {
/* 111 */             f *= 0.7F;
/* 112 */             f1 *= 0.7F;
/* 113 */             f2 *= 0.7F;
/*     */           } 
/*     */           
/* 116 */           float f3 = j / 24.0F;
/* 117 */           worldrenderer.pos(x + d13 * f3 + 0.0D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F), z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
/* 118 */           worldrenderer.pos(x + d13 * f3 + 0.025D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F) + 0.025D, z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 121 */         tessellator.draw();
/* 122 */         worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 124 */         for (int k = 0; k <= 24; k++) {
/* 125 */           float f4 = 0.5F;
/* 126 */           float f5 = 0.4F;
/* 127 */           float f6 = 0.3F;
/*     */           
/* 129 */           if (k % 2 == 0) {
/* 130 */             f4 *= 0.7F;
/* 131 */             f5 *= 0.7F;
/* 132 */             f6 *= 0.7F;
/*     */           } 
/*     */           
/* 135 */           float f7 = k / 24.0F;
/* 136 */           worldrenderer.pos(x + d13 * f7 + 0.0D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F) + 0.025D, z + d15 * f7).color(f4, f5, f6, 1.0F).endVertex();
/* 137 */           worldrenderer.pos(x + d13 * f7 + 0.025D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F), z + d15 * f7 + 0.025D).color(f4, f5, f6, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 140 */         tessellator.draw();
/*     */         
/* 142 */         if (Config.isShaders()) {
/* 143 */           Shaders.endLeash();
/*     */         }
/*     */         
/* 146 */         GlStateManager.enableLighting();
/* 147 */         GlStateManager.enableTexture2D();
/* 148 */         GlStateManager.enableCull();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */