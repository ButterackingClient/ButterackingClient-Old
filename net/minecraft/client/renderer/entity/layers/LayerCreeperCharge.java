/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelCreeper;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderCreeper;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
/* 10 */   private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
/*    */   private final RenderCreeper creeperRenderer;
/* 12 */   private final ModelCreeper creeperModel = new ModelCreeper(2.0F);
/*    */   
/*    */   public LayerCreeperCharge(RenderCreeper creeperRendererIn) {
/* 15 */     this.creeperRenderer = creeperRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityCreeper entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 19 */     if (entitylivingbaseIn.getPowered()) {
/* 20 */       boolean flag = entitylivingbaseIn.isInvisible();
/* 21 */       GlStateManager.depthMask(!flag);
/* 22 */       this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
/* 23 */       GlStateManager.matrixMode(5890);
/* 24 */       GlStateManager.loadIdentity();
/* 25 */       float f = entitylivingbaseIn.ticksExisted + partialTicks;
/* 26 */       GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
/* 27 */       GlStateManager.matrixMode(5888);
/* 28 */       GlStateManager.enableBlend();
/* 29 */       float f1 = 0.5F;
/* 30 */       GlStateManager.color(f1, f1, f1, 1.0F);
/* 31 */       GlStateManager.disableLighting();
/* 32 */       GlStateManager.blendFunc(1, 1);
/* 33 */       this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
/* 34 */       this.creeperModel.render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 35 */       GlStateManager.matrixMode(5890);
/* 36 */       GlStateManager.loadIdentity();
/* 37 */       GlStateManager.matrixMode(5888);
/* 38 */       GlStateManager.enableLighting();
/* 39 */       GlStateManager.disableBlend();
/* 40 */       GlStateManager.depthMask(flag);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerCreeperCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */