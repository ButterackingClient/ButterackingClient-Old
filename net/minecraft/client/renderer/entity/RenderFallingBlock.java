/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityFallingBlock;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class RenderFallingBlock extends Render<EntityFallingBlock> {
/*    */   public RenderFallingBlock(RenderManager renderManagerIn) {
/* 20 */     super(renderManagerIn);
/* 21 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityFallingBlock entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 28 */     if (entity.getBlock() != null) {
/* 29 */       bindTexture(TextureMap.locationBlocksTexture);
/* 30 */       IBlockState iblockstate = entity.getBlock();
/* 31 */       Block block = iblockstate.getBlock();
/* 32 */       BlockPos blockpos = new BlockPos((Entity)entity);
/* 33 */       World world = entity.getWorldObj();
/*    */       
/* 35 */       if (iblockstate != world.getBlockState(blockpos) && block.getRenderType() != -1 && 
/* 36 */         block.getRenderType() == 3) {
/* 37 */         GlStateManager.pushMatrix();
/* 38 */         GlStateManager.translate((float)x, (float)y, (float)z);
/* 39 */         GlStateManager.disableLighting();
/* 40 */         Tessellator tessellator = Tessellator.getInstance();
/* 41 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 42 */         worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 43 */         int i = blockpos.getX();
/* 44 */         int j = blockpos.getY();
/* 45 */         int k = blockpos.getZ();
/* 46 */         worldrenderer.setTranslation((-i - 0.5F), -j, (-k - 0.5F));
/* 47 */         BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 48 */         IBakedModel ibakedmodel = blockrendererdispatcher.getModelFromBlockState(iblockstate, (IBlockAccess)world, null);
/* 49 */         blockrendererdispatcher.getBlockModelRenderer().renderModel((IBlockAccess)world, ibakedmodel, iblockstate, blockpos, worldrenderer, false);
/* 50 */         worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 51 */         tessellator.draw();
/* 52 */         GlStateManager.enableLighting();
/* 53 */         GlStateManager.popMatrix();
/* 54 */         super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFallingBlock entity) {
/* 64 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderFallingBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */