/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderSpider;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySpider;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class LayerSpiderEyes implements LayerRenderer<EntitySpider> {
/* 12 */   private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
/*    */   private final RenderSpider spiderRenderer;
/*    */   
/*    */   public LayerSpiderEyes(RenderSpider spiderRendererIn) {
/* 16 */     this.spiderRenderer = spiderRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntitySpider entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 20 */     this.spiderRenderer.bindTexture(SPIDER_EYES);
/* 21 */     GlStateManager.enableBlend();
/* 22 */     GlStateManager.disableAlpha();
/* 23 */     GlStateManager.blendFunc(1, 1);
/*    */     
/* 25 */     if (entitylivingbaseIn.isInvisible()) {
/* 26 */       GlStateManager.depthMask(false);
/*    */     } else {
/* 28 */       GlStateManager.depthMask(true);
/*    */     } 
/*    */     
/* 31 */     int i = 61680;
/* 32 */     int j = i % 65536;
/* 33 */     int k = i / 65536;
/* 34 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 35 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 37 */     if (Config.isShaders()) {
/* 38 */       Shaders.beginSpiderEyes();
/*    */     }
/*    */     
/* 41 */     (Config.getRenderGlobal()).renderOverlayEyes = true;
/* 42 */     this.spiderRenderer.getMainModel().render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 43 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 45 */     if (Config.isShaders()) {
/* 46 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 49 */     i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
/* 50 */     j = i % 65536;
/* 51 */     k = i / 65536;
/* 52 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 53 */     this.spiderRenderer.setLightmap((EntityLiving)entitylivingbaseIn, partialTicks);
/* 54 */     GlStateManager.disableBlend();
/* 55 */     GlStateManager.enableAlpha();
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerSpiderEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */