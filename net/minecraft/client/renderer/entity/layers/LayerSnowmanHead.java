/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.entity.RenderSnowMan;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySnowman;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class LayerSnowmanHead
/*    */   implements LayerRenderer<EntitySnowman> {
/*    */   public LayerSnowmanHead(RenderSnowMan snowManRendererIn) {
/* 15 */     this.snowManRenderer = snowManRendererIn;
/*    */   }
/*    */   private final RenderSnowMan snowManRenderer;
/*    */   public void doRenderLayer(EntitySnowman entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 19 */     if (!entitylivingbaseIn.isInvisible()) {
/* 20 */       GlStateManager.pushMatrix();
/* 21 */       (this.snowManRenderer.getMainModel()).head.postRender(0.0625F);
/* 22 */       float f = 0.625F;
/* 23 */       GlStateManager.translate(0.0F, -0.34375F, 0.0F);
/* 24 */       GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 25 */       GlStateManager.scale(f, -f, -f);
/* 26 */       Minecraft.getMinecraft().getItemRenderer().renderItem((EntityLivingBase)entitylivingbaseIn, new ItemStack(Blocks.pumpkin, 1), ItemCameraTransforms.TransformType.HEAD);
/* 27 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerSnowmanHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */