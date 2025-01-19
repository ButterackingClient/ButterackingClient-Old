/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.model.ModelBox;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class LayerArrow
/*    */   implements LayerRenderer<EntityLivingBase> {
/*    */   private final RendererLivingEntity field_177168_a;
/*    */   
/*    */   public LayerArrow(RendererLivingEntity p_i46124_1_) {
/* 19 */     this.field_177168_a = p_i46124_1_;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 23 */     int i = entitylivingbaseIn.getArrowCountInEntity();
/*    */     
/* 25 */     if (i > 0) {
/* 26 */       EntityArrow entityArrow = new EntityArrow(entitylivingbaseIn.worldObj, entitylivingbaseIn.posX, entitylivingbaseIn.posY, entitylivingbaseIn.posZ);
/* 27 */       Random random = new Random(entitylivingbaseIn.getEntityId());
/* 28 */       RenderHelper.disableStandardItemLighting();
/*    */       
/* 30 */       for (int j = 0; j < i; j++) {
/* 31 */         GlStateManager.pushMatrix();
/* 32 */         ModelRenderer modelrenderer = this.field_177168_a.getMainModel().getRandomModelBox(random);
/* 33 */         ModelBox modelbox = modelrenderer.cubeList.get(random.nextInt(modelrenderer.cubeList.size()));
/* 34 */         modelrenderer.postRender(0.0625F);
/* 35 */         float f = random.nextFloat();
/* 36 */         float f1 = random.nextFloat();
/* 37 */         float f2 = random.nextFloat();
/* 38 */         float f3 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f) / 16.0F;
/* 39 */         float f4 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f1) / 16.0F;
/* 40 */         float f5 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f2) / 16.0F;
/* 41 */         GlStateManager.translate(f3, f4, f5);
/* 42 */         f = f * 2.0F - 1.0F;
/* 43 */         f1 = f1 * 2.0F - 1.0F;
/* 44 */         f2 = f2 * 2.0F - 1.0F;
/* 45 */         f *= -1.0F;
/* 46 */         f1 *= -1.0F;
/* 47 */         f2 *= -1.0F;
/* 48 */         float f6 = MathHelper.sqrt_float(f * f + f2 * f2);
/* 49 */         ((Entity)entityArrow).prevRotationYaw = ((Entity)entityArrow).rotationYaw = (float)(Math.atan2(f, f2) * 180.0D / Math.PI);
/* 50 */         ((Entity)entityArrow).prevRotationPitch = ((Entity)entityArrow).rotationPitch = (float)(Math.atan2(f1, f6) * 180.0D / Math.PI);
/* 51 */         double d0 = 0.0D;
/* 52 */         double d1 = 0.0D;
/* 53 */         double d2 = 0.0D;
/* 54 */         this.field_177168_a.getRenderManager().renderEntityWithPosYaw((Entity)entityArrow, d0, d1, d2, 0.0F, partialTicks);
/* 55 */         GlStateManager.popMatrix();
/*    */       } 
/*    */       
/* 58 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 63 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */