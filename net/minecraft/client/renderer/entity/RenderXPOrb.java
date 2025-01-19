/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.CustomColors;
/*    */ 
/*    */ public class RenderXPOrb extends Render<EntityXPOrb> {
/* 15 */   private static final ResourceLocation experienceOrbTextures = new ResourceLocation("textures/entity/experience_orb.png");
/*    */   
/*    */   public RenderXPOrb(RenderManager renderManagerIn) {
/* 18 */     super(renderManagerIn);
/* 19 */     this.shadowSize = 0.15F;
/* 20 */     this.shadowOpaque = 0.75F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 27 */     GlStateManager.pushMatrix();
/* 28 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 29 */     bindEntityTexture(entity);
/* 30 */     int i = entity.getTextureByXP();
/* 31 */     float f = (i % 4 * 16 + 0) / 64.0F;
/* 32 */     float f1 = (i % 4 * 16 + 16) / 64.0F;
/* 33 */     float f2 = (i / 4 * 16 + 0) / 64.0F;
/* 34 */     float f3 = (i / 4 * 16 + 16) / 64.0F;
/* 35 */     float f4 = 1.0F;
/* 36 */     float f5 = 0.5F;
/* 37 */     float f6 = 0.25F;
/* 38 */     int j = entity.getBrightnessForRender(partialTicks);
/* 39 */     int k = j % 65536;
/* 40 */     int l = j / 65536;
/* 41 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/* 42 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 43 */     float f7 = 255.0F;
/* 44 */     float f8 = (entity.xpColor + partialTicks) / 2.0F;
/*    */     
/* 46 */     if (Config.isCustomColors()) {
/* 47 */       f8 = CustomColors.getXpOrbTimer(f8);
/*    */     }
/*    */     
/* 50 */     l = (int)((MathHelper.sin(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
/* 51 */     int i1 = 255;
/* 52 */     int j1 = (int)((MathHelper.sin(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
/* 53 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 54 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 55 */     float f9 = 0.3F;
/* 56 */     GlStateManager.scale(0.3F, 0.3F, 0.3F);
/* 57 */     Tessellator tessellator = Tessellator.getInstance();
/* 58 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 59 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/* 60 */     int k1 = l;
/* 61 */     int l1 = 255;
/* 62 */     int i2 = j1;
/*    */     
/* 64 */     if (Config.isCustomColors()) {
/* 65 */       int j2 = CustomColors.getXpOrbColor(f8);
/*    */       
/* 67 */       if (j2 >= 0) {
/* 68 */         k1 = j2 >> 16 & 0xFF;
/* 69 */         l1 = j2 >> 8 & 0xFF;
/* 70 */         i2 = j2 >> 0 & 0xFF;
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     worldrenderer.pos((0.0F - f5), (0.0F - f6), 0.0D).tex(f, f3).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 75 */     worldrenderer.pos((f4 - f5), (0.0F - f6), 0.0D).tex(f1, f3).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 76 */     worldrenderer.pos((f4 - f5), (1.0F - f6), 0.0D).tex(f1, f2).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 77 */     worldrenderer.pos((0.0F - f5), (1.0F - f6), 0.0D).tex(f, f2).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 78 */     tessellator.draw();
/* 79 */     GlStateManager.disableBlend();
/* 80 */     GlStateManager.disableRescaleNormal();
/* 81 */     GlStateManager.popMatrix();
/* 82 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityXPOrb entity) {
/* 89 */     return experienceOrbTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderXPOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */