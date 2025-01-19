/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderman;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ 
/*    */ public class LayerHeldBlock
/*    */   implements LayerRenderer<EntityEnderman> {
/*    */   public LayerHeldBlock(RenderEnderman endermanRendererIn) {
/* 17 */     this.endermanRenderer = endermanRendererIn;
/*    */   }
/*    */   private final RenderEnderman endermanRenderer;
/*    */   public void doRenderLayer(EntityEnderman entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 21 */     IBlockState iblockstate = entitylivingbaseIn.getHeldBlockState();
/*    */     
/* 23 */     if (iblockstate.getBlock().getMaterial() != Material.air) {
/* 24 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 25 */       GlStateManager.enableRescaleNormal();
/* 26 */       GlStateManager.pushMatrix();
/* 27 */       GlStateManager.translate(0.0F, 0.6875F, -0.75F);
/* 28 */       GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 29 */       GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 30 */       GlStateManager.translate(0.25F, 0.1875F, 0.25F);
/* 31 */       float f = 0.5F;
/* 32 */       GlStateManager.scale(-f, -f, f);
/* 33 */       int i = entitylivingbaseIn.getBrightnessForRender(partialTicks);
/* 34 */       int j = i % 65536;
/* 35 */       int k = i / 65536;
/* 36 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 37 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 38 */       this.endermanRenderer.bindTexture(TextureMap.locationBlocksTexture);
/* 39 */       blockrendererdispatcher.renderBlockBrightness(iblockstate, 1.0F);
/* 40 */       GlStateManager.popMatrix();
/* 41 */       GlStateManager.disableRescaleNormal();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerHeldBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */