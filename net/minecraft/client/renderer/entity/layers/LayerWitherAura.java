/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelWither;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWither;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityWither;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class LayerWitherAura implements LayerRenderer<EntityWither> {
/* 11 */   private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
/*    */   private final RenderWither witherRenderer;
/* 13 */   private final ModelWither witherModel = new ModelWither(0.5F);
/*    */   
/*    */   public LayerWitherAura(RenderWither witherRendererIn) {
/* 16 */     this.witherRenderer = witherRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityWither entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 20 */     if (entitylivingbaseIn.isArmored()) {
/* 21 */       GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
/* 22 */       this.witherRenderer.bindTexture(WITHER_ARMOR);
/* 23 */       GlStateManager.matrixMode(5890);
/* 24 */       GlStateManager.loadIdentity();
/* 25 */       float f = entitylivingbaseIn.ticksExisted + partialTicks;
/* 26 */       float f1 = MathHelper.cos(f * 0.02F) * 3.0F;
/* 27 */       float f2 = f * 0.01F;
/* 28 */       GlStateManager.translate(f1, f2, 0.0F);
/* 29 */       GlStateManager.matrixMode(5888);
/* 30 */       GlStateManager.enableBlend();
/* 31 */       float f3 = 0.5F;
/* 32 */       GlStateManager.color(f3, f3, f3, 1.0F);
/* 33 */       GlStateManager.disableLighting();
/* 34 */       GlStateManager.blendFunc(1, 1);
/* 35 */       this.witherModel.setLivingAnimations((EntityLivingBase)entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks);
/* 36 */       this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
/* 37 */       this.witherModel.render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/* 38 */       GlStateManager.matrixMode(5890);
/* 39 */       GlStateManager.loadIdentity();
/* 40 */       GlStateManager.matrixMode(5888);
/* 41 */       GlStateManager.enableLighting();
/* 42 */       GlStateManager.disableBlend();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerWitherAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */