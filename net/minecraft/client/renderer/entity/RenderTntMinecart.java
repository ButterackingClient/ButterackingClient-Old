/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecartTNT;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class RenderTntMinecart extends RenderMinecart<EntityMinecartTNT> {
/*    */   public RenderTntMinecart(RenderManager renderManagerIn) {
/* 13 */     super(renderManagerIn);
/*    */   }
/*    */   
/*    */   protected void func_180560_a(EntityMinecartTNT minecart, float partialTicks, IBlockState state) {
/* 17 */     int i = minecart.getFuseTicks();
/*    */     
/* 19 */     if (i > -1 && i - partialTicks + 1.0F < 10.0F) {
/* 20 */       float f = 1.0F - (i - partialTicks + 1.0F) / 10.0F;
/* 21 */       f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 22 */       f *= f;
/* 23 */       f *= f;
/* 24 */       float f1 = 1.0F + f * 0.3F;
/* 25 */       GlStateManager.scale(f1, f1, f1);
/*    */     } 
/*    */     
/* 28 */     super.func_180560_a(minecart, partialTicks, state);
/*    */     
/* 30 */     if (i > -1 && i / 5 % 2 == 0) {
/* 31 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 32 */       GlStateManager.disableTexture2D();
/* 33 */       GlStateManager.disableLighting();
/* 34 */       GlStateManager.enableBlend();
/* 35 */       GlStateManager.blendFunc(770, 772);
/* 36 */       GlStateManager.color(1.0F, 1.0F, 1.0F, (1.0F - (i - partialTicks + 1.0F) / 100.0F) * 0.8F);
/* 37 */       GlStateManager.pushMatrix();
/* 38 */       blockrendererdispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
/* 39 */       GlStateManager.popMatrix();
/* 40 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 41 */       GlStateManager.disableBlend();
/* 42 */       GlStateManager.enableLighting();
/* 43 */       GlStateManager.enableTexture2D();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderTntMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */