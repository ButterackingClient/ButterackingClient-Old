/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ 
/*    */ public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon> {
/*    */   public void doRenderLayer(EntityDragon entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 14 */     if (entitylivingbaseIn.deathTicks > 0) {
/* 15 */       Tessellator tessellator = Tessellator.getInstance();
/* 16 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 17 */       RenderHelper.disableStandardItemLighting();
/* 18 */       float f = (entitylivingbaseIn.deathTicks + partialTicks) / 200.0F;
/* 19 */       float f1 = 0.0F;
/*    */       
/* 21 */       if (f > 0.8F) {
/* 22 */         f1 = (f - 0.8F) / 0.2F;
/*    */       }
/*    */       
/* 25 */       Random random = new Random(432L);
/* 26 */       GlStateManager.disableTexture2D();
/* 27 */       GlStateManager.shadeModel(7425);
/* 28 */       GlStateManager.enableBlend();
/* 29 */       GlStateManager.blendFunc(770, 1);
/* 30 */       GlStateManager.disableAlpha();
/* 31 */       GlStateManager.enableCull();
/* 32 */       GlStateManager.depthMask(false);
/* 33 */       GlStateManager.pushMatrix();
/* 34 */       GlStateManager.translate(0.0F, -1.0F, -2.0F);
/*    */       
/* 36 */       for (int i = 0; i < (f + f * f) / 2.0F * 60.0F; i++) {
/* 37 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 38 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 39 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
/* 40 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 41 */         GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 42 */         GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
/* 43 */         float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
/* 44 */         float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
/* 45 */         worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 46 */         worldrenderer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
/* 47 */         worldrenderer.pos(-0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
/* 48 */         worldrenderer.pos(0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
/* 49 */         worldrenderer.pos(0.0D, f2, (1.0F * f3)).color(255, 0, 255, 0).endVertex();
/* 50 */         worldrenderer.pos(-0.866D * f3, f2, (-0.5F * f3)).color(255, 0, 255, 0).endVertex();
/* 51 */         tessellator.draw();
/*    */       } 
/*    */       
/* 54 */       GlStateManager.popMatrix();
/* 55 */       GlStateManager.depthMask(true);
/* 56 */       GlStateManager.disableCull();
/* 57 */       GlStateManager.disableBlend();
/* 58 */       GlStateManager.shadeModel(7424);
/* 59 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 60 */       GlStateManager.enableTexture2D();
/* 61 */       GlStateManager.enableAlpha();
/* 62 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerEnderDragonDeath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */