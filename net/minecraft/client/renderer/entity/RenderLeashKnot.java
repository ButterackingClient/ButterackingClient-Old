/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelLeashKnot;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderLeashKnot extends Render<EntityLeashKnot> {
/*  9 */   private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
/* 10 */   private ModelLeashKnot leashKnotModel = new ModelLeashKnot();
/*    */   
/*    */   public RenderLeashKnot(RenderManager renderManagerIn) {
/* 13 */     super(renderManagerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityLeashKnot entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 20 */     GlStateManager.pushMatrix();
/* 21 */     GlStateManager.disableCull();
/* 22 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 23 */     float f = 0.0625F;
/* 24 */     GlStateManager.enableRescaleNormal();
/* 25 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 26 */     GlStateManager.enableAlpha();
/* 27 */     bindEntityTexture(entity);
/* 28 */     this.leashKnotModel.render((Entity)entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f);
/* 29 */     GlStateManager.popMatrix();
/* 30 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityLeashKnot entity) {
/* 37 */     return leashKnotTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderLeashKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */