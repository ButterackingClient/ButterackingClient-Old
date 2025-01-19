/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelIronGolem;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderIronGolem;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class LayerIronGolemFlower
/*    */   implements LayerRenderer<EntityIronGolem> {
/*    */   public LayerIronGolemFlower(RenderIronGolem ironGolemRendererIn) {
/* 17 */     this.ironGolemRenderer = ironGolemRendererIn;
/*    */   }
/*    */   private final RenderIronGolem ironGolemRenderer;
/*    */   public void doRenderLayer(EntityIronGolem entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 21 */     if (entitylivingbaseIn.getHoldRoseTick() != 0) {
/* 22 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 23 */       GlStateManager.enableRescaleNormal();
/* 24 */       GlStateManager.pushMatrix();
/* 25 */       GlStateManager.rotate(5.0F + 180.0F * ((ModelIronGolem)this.ironGolemRenderer.getMainModel()).ironGolemRightArm.rotateAngleX / 3.1415927F, 1.0F, 0.0F, 0.0F);
/* 26 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 27 */       GlStateManager.translate(-0.9375F, -0.625F, -0.9375F);
/* 28 */       float f = 0.5F;
/* 29 */       GlStateManager.scale(f, -f, f);
/* 30 */       int i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
/* 31 */       int j = i % 65536;
/* 32 */       int k = i / 65536;
/* 33 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 34 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 35 */       this.ironGolemRenderer.bindTexture(TextureMap.locationBlocksTexture);
/* 36 */       blockrendererdispatcher.renderBlockBrightness(Blocks.red_flower.getDefaultState(), 1.0F);
/* 37 */       GlStateManager.popMatrix();
/* 38 */       GlStateManager.disableRescaleNormal();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 43 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerIronGolemFlower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */