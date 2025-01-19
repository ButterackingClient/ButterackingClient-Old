/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelPig;
/*    */ import net.minecraft.client.renderer.entity.RenderPig;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class LayerSaddle implements LayerRenderer<EntityPig> {
/*  9 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
/*    */   private final RenderPig pigRenderer;
/* 11 */   private final ModelPig pigModel = new ModelPig(0.5F);
/*    */   
/*    */   public LayerSaddle(RenderPig pigRendererIn) {
/* 14 */     this.pigRenderer = pigRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityPig entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 18 */     if (entitylivingbaseIn.getSaddled()) {
/* 19 */       this.pigRenderer.bindTexture(TEXTURE);
/* 20 */       this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
/* 21 */       this.pigModel.render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 26 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerSaddle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */