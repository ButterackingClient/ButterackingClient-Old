/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderman;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class LayerEndermanEyes implements LayerRenderer<EntityEnderman> {
/* 12 */   private static final ResourceLocation RES_ENDERMAN_EYES = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
/*    */   private final RenderEnderman endermanRenderer;
/*    */   
/*    */   public LayerEndermanEyes(RenderEnderman endermanRendererIn) {
/* 16 */     this.endermanRenderer = endermanRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityEnderman entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 20 */     this.endermanRenderer.bindTexture(RES_ENDERMAN_EYES);
/* 21 */     GlStateManager.enableBlend();
/* 22 */     GlStateManager.disableAlpha();
/* 23 */     GlStateManager.blendFunc(1, 1);
/* 24 */     GlStateManager.disableLighting();
/* 25 */     GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
/* 26 */     int i = 61680;
/* 27 */     int j = i % 65536;
/* 28 */     int k = i / 65536;
/* 29 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 30 */     GlStateManager.enableLighting();
/* 31 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 33 */     if (Config.isShaders()) {
/* 34 */       Shaders.beginSpiderEyes();
/*    */     }
/*    */     
/* 37 */     (Config.getRenderGlobal()).renderOverlayEyes = true;
/* 38 */     this.endermanRenderer.getMainModel().render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 39 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 41 */     if (Config.isShaders()) {
/* 42 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 45 */     this.endermanRenderer.setLightmap((EntityLiving)entitylivingbaseIn, partialTicks);
/* 46 */     GlStateManager.depthMask(true);
/* 47 */     GlStateManager.disableBlend();
/* 48 */     GlStateManager.enableAlpha();
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerEndermanEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */