/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockPistonBase;
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityPiston;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class TileEntityPistonRenderer extends TileEntitySpecialRenderer<TileEntityPiston> {
/* 22 */   private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
/*    */   
/*    */   public void renderTileEntityAt(TileEntityPiston te, double x, double y, double z, float partialTicks, int destroyStage) {
/* 25 */     BlockPos blockpos = te.getPos();
/* 26 */     IBlockState iblockstate = te.getPistonState();
/* 27 */     Block block = iblockstate.getBlock();
/*    */     
/* 29 */     if (block.getMaterial() != Material.air && te.getProgress(partialTicks) < 1.0F) {
/* 30 */       Tessellator tessellator = Tessellator.getInstance();
/* 31 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 32 */       bindTexture(TextureMap.locationBlocksTexture);
/* 33 */       RenderHelper.disableStandardItemLighting();
/* 34 */       GlStateManager.blendFunc(770, 771);
/* 35 */       GlStateManager.enableBlend();
/* 36 */       GlStateManager.disableCull();
/*    */       
/* 38 */       if (Minecraft.isAmbientOcclusionEnabled()) {
/* 39 */         GlStateManager.shadeModel(7425);
/*    */       } else {
/* 41 */         GlStateManager.shadeModel(7424);
/*    */       } 
/*    */       
/* 44 */       worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 45 */       worldrenderer.setTranslation(((float)x - blockpos.getX() + te.getOffsetX(partialTicks)), ((float)y - blockpos.getY() + te.getOffsetY(partialTicks)), ((float)z - blockpos.getZ() + te.getOffsetZ(partialTicks)));
/* 46 */       World world = getWorld();
/*    */       
/* 48 */       if (block == Blocks.piston_head && te.getProgress(partialTicks) < 0.5F) {
/* 49 */         iblockstate = iblockstate.withProperty((IProperty)BlockPistonExtension.SHORT, Boolean.valueOf(true));
/* 50 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate, (IBlockAccess)world, blockpos), iblockstate, blockpos, worldrenderer, true);
/* 51 */       } else if (te.shouldPistonHeadBeRendered() && !te.isExtending()) {
/* 52 */         BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = (block == Blocks.sticky_piston) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/* 53 */         IBlockState iblockstate1 = Blocks.piston_head.getDefaultState().withProperty((IProperty)BlockPistonExtension.TYPE, (Comparable)blockpistonextension$enumpistontype).withProperty((IProperty)BlockPistonExtension.FACING, iblockstate.getValue((IProperty)BlockPistonBase.FACING));
/* 54 */         iblockstate1 = iblockstate1.withProperty((IProperty)BlockPistonExtension.SHORT, Boolean.valueOf((te.getProgress(partialTicks) >= 0.5F)));
/* 55 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate1, (IBlockAccess)world, blockpos), iblockstate1, blockpos, worldrenderer, true);
/* 56 */         worldrenderer.setTranslation(((float)x - blockpos.getX()), ((float)y - blockpos.getY()), ((float)z - blockpos.getZ()));
/* 57 */         iblockstate.withProperty((IProperty)BlockPistonBase.EXTENDED, Boolean.valueOf(true));
/* 58 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate, (IBlockAccess)world, blockpos), iblockstate, blockpos, worldrenderer, true);
/*    */       } else {
/* 60 */         this.blockRenderer.getBlockModelRenderer().renderModel((IBlockAccess)world, this.blockRenderer.getModelFromBlockState(iblockstate, (IBlockAccess)world, blockpos), iblockstate, blockpos, worldrenderer, false);
/*    */       } 
/*    */       
/* 63 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 64 */       tessellator.draw();
/* 65 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityPistonRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */