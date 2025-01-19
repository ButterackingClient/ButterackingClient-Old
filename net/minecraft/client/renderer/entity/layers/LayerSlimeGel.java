/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSlime;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSlime;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ 
/*    */ public class LayerSlimeGel implements LayerRenderer<EntitySlime> {
/* 11 */   private final ModelBase slimeModel = (ModelBase)new ModelSlime(0); private final RenderSlime slimeRenderer;
/*    */   
/*    */   public LayerSlimeGel(RenderSlime slimeRendererIn) {
/* 14 */     this.slimeRenderer = slimeRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntitySlime entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 18 */     if (!entitylivingbaseIn.isInvisible()) {
/* 19 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 20 */       GlStateManager.enableNormalize();
/* 21 */       GlStateManager.enableBlend();
/* 22 */       GlStateManager.blendFunc(770, 771);
/* 23 */       this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
/* 24 */       this.slimeModel.render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 25 */       GlStateManager.disableBlend();
/* 26 */       GlStateManager.disableNormalize();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerSlimeGel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */