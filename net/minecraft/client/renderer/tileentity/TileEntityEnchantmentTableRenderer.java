/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBook;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class TileEntityEnchantmentTableRenderer
/*    */   extends TileEntitySpecialRenderer<TileEntityEnchantmentTable>
/*    */ {
/* 14 */   private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
/* 15 */   private ModelBook field_147541_c = new ModelBook();
/*    */   
/*    */   public void renderTileEntityAt(TileEntityEnchantmentTable te, double x, double y, double z, float partialTicks, int destroyStage) {
/* 18 */     GlStateManager.pushMatrix();
/* 19 */     GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F, (float)z + 0.5F);
/* 20 */     float f = te.tickCount + partialTicks;
/* 21 */     GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
/*    */     
/*    */     float f1;
/* 24 */     for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= 3.1415927F; f1 -= 6.2831855F);
/*    */ 
/*    */ 
/*    */     
/* 28 */     while (f1 < -3.1415927F) {
/* 29 */       f1 += 6.2831855F;
/*    */     }
/*    */     
/* 32 */     float f2 = te.bookRotationPrev + f1 * partialTicks;
/* 33 */     GlStateManager.rotate(-f2 * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
/* 34 */     GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
/* 35 */     bindTexture(TEXTURE_BOOK);
/* 36 */     float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
/* 37 */     float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
/* 38 */     f3 = (f3 - MathHelper.truncateDoubleToInt(f3)) * 1.6F - 0.3F;
/* 39 */     f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
/*    */     
/* 41 */     if (f3 < 0.0F) {
/* 42 */       f3 = 0.0F;
/*    */     }
/*    */     
/* 45 */     if (f4 < 0.0F) {
/* 46 */       f4 = 0.0F;
/*    */     }
/*    */     
/* 49 */     if (f3 > 1.0F) {
/* 50 */       f3 = 1.0F;
/*    */     }
/*    */     
/* 53 */     if (f4 > 1.0F) {
/* 54 */       f4 = 1.0F;
/*    */     }
/*    */     
/* 57 */     float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
/* 58 */     GlStateManager.enableCull();
/* 59 */     this.field_147541_c.render(null, f, f3, f4, f5, 0.0F, 0.0625F);
/* 60 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityEnchantmentTableRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */