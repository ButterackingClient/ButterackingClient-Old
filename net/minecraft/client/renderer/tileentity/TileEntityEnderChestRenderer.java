/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer<TileEntityEnderChest> {
/*  9 */   private static final ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
/* 10 */   private ModelChest field_147521_c = new ModelChest();
/*    */   
/*    */   public void renderTileEntityAt(TileEntityEnderChest te, double x, double y, double z, float partialTicks, int destroyStage) {
/* 13 */     int i = 0;
/*    */     
/* 15 */     if (te.hasWorldObj()) {
/* 16 */       i = te.getBlockMetadata();
/*    */     }
/*    */     
/* 19 */     if (destroyStage >= 0) {
/* 20 */       bindTexture(DESTROY_STAGES[destroyStage]);
/* 21 */       GlStateManager.matrixMode(5890);
/* 22 */       GlStateManager.pushMatrix();
/* 23 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/* 24 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/* 25 */       GlStateManager.matrixMode(5888);
/*    */     } else {
/* 27 */       bindTexture(ENDER_CHEST_TEXTURE);
/*    */     } 
/*    */     
/* 30 */     GlStateManager.pushMatrix();
/* 31 */     GlStateManager.enableRescaleNormal();
/* 32 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 33 */     GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
/* 34 */     GlStateManager.scale(1.0F, -1.0F, -1.0F);
/* 35 */     GlStateManager.translate(0.5F, 0.5F, 0.5F);
/* 36 */     int j = 0;
/*    */     
/* 38 */     if (i == 2) {
/* 39 */       j = 180;
/*    */     }
/*    */     
/* 42 */     if (i == 3) {
/* 43 */       j = 0;
/*    */     }
/*    */     
/* 46 */     if (i == 4) {
/* 47 */       j = 90;
/*    */     }
/*    */     
/* 50 */     if (i == 5) {
/* 51 */       j = -90;
/*    */     }
/*    */     
/* 54 */     GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
/* 55 */     GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/* 56 */     float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
/* 57 */     f = 1.0F - f;
/* 58 */     f = 1.0F - f * f * f;
/* 59 */     this.field_147521_c.chestLid.rotateAngleX = -(f * 3.1415927F / 2.0F);
/* 60 */     this.field_147521_c.renderAll();
/* 61 */     GlStateManager.disableRescaleNormal();
/* 62 */     GlStateManager.popMatrix();
/* 63 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 65 */     if (destroyStage >= 0) {
/* 66 */       GlStateManager.matrixMode(5890);
/* 67 */       GlStateManager.popMatrix();
/* 68 */       GlStateManager.matrixMode(5888);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityEnderChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */