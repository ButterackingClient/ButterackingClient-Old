/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class LayerDeadmau5Head
/*    */   implements LayerRenderer<AbstractClientPlayer> {
/*    */   public LayerDeadmau5Head(RenderPlayer playerRendererIn) {
/* 11 */     this.playerRenderer = playerRendererIn;
/*    */   }
/*    */   private final RenderPlayer playerRenderer;
/*    */   public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 15 */     if (entitylivingbaseIn.getName().equals("deadmau5") && entitylivingbaseIn.hasSkin() && !entitylivingbaseIn.isInvisible()) {
/* 16 */       this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationSkin());
/*    */       
/* 18 */       for (int i = 0; i < 2; i++) {
/* 19 */         float f = entitylivingbaseIn.prevRotationYaw + (entitylivingbaseIn.rotationYaw - entitylivingbaseIn.prevRotationYaw) * partialTicks - entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
/* 20 */         float f1 = entitylivingbaseIn.prevRotationPitch + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch) * partialTicks;
/* 21 */         GlStateManager.pushMatrix();
/* 22 */         GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
/* 23 */         GlStateManager.rotate(f1, 1.0F, 0.0F, 0.0F);
/* 24 */         GlStateManager.translate(0.375F * (i * 2 - 1), 0.0F, 0.0F);
/* 25 */         GlStateManager.translate(0.0F, -0.375F, 0.0F);
/* 26 */         GlStateManager.rotate(-f1, 1.0F, 0.0F, 0.0F);
/* 27 */         GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
/* 28 */         float f2 = 1.3333334F;
/* 29 */         GlStateManager.scale(f2, f2, f2);
/* 30 */         this.playerRenderer.getMainModel().renderDeadmau5Head(0.0625F);
/* 31 */         GlStateManager.popMatrix();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerDeadmau5Head.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */