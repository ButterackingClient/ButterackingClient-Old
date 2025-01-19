/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import client.Client;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderTNTPrimed extends Render<EntityTNTPrimed> {
/*    */   public RenderTNTPrimed(RenderManager renderManagerIn) {
/* 16 */     super(renderManagerIn);
/* 17 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 24 */     (Client.getInstance()).hudManager.tntTimer.renderTag(this, entity, x, y, z, partialTicks);
/* 25 */     BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 26 */     GlStateManager.pushMatrix();
/* 27 */     GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);
/*    */     
/* 29 */     if (entity.fuse - partialTicks + 1.0F < 10.0F) {
/* 30 */       float f = 1.0F - (entity.fuse - partialTicks + 1.0F) / 10.0F;
/* 31 */       f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 32 */       f *= f;
/* 33 */       f *= f;
/* 34 */       float f1 = 1.0F + f * 0.3F;
/* 35 */       GlStateManager.scale(f1, f1, f1);
/*    */     } 
/*    */     
/* 38 */     float f2 = (1.0F - (entity.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
/* 39 */     bindEntityTexture(entity);
/* 40 */     GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/* 41 */     blockrendererdispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), entity.getBrightness(partialTicks));
/* 42 */     GlStateManager.translate(0.0F, 0.0F, 1.0F);
/*    */     
/* 44 */     if (entity.fuse / 5 % 2 == 0) {
/* 45 */       GlStateManager.disableTexture2D();
/* 46 */       GlStateManager.disableLighting();
/* 47 */       GlStateManager.enableBlend();
/* 48 */       GlStateManager.blendFunc(770, 772);
/* 49 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
/* 50 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 51 */       GlStateManager.enablePolygonOffset();
/* 52 */       blockrendererdispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
/* 53 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 54 */       GlStateManager.disablePolygonOffset();
/* 55 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 56 */       GlStateManager.disableBlend();
/* 57 */       GlStateManager.enableLighting();
/* 58 */       GlStateManager.enableTexture2D();
/*    */     } 
/*    */     
/* 61 */     GlStateManager.popMatrix();
/* 62 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityTNTPrimed entity) {
/* 69 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderTNTPrimed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */