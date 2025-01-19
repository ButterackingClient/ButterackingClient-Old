/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLever;
/*    */ import net.minecraft.block.BlockTorch;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BetterSnow
/*    */ {
/* 29 */   private static IBakedModel modelSnowLayer = null;
/*    */   
/*    */   public static void update() {
/* 32 */     modelSnowLayer = Config.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(Blocks.snow_layer.getDefaultState());
/*    */   }
/*    */   
/*    */   public static IBakedModel getModelSnowLayer() {
/* 36 */     return modelSnowLayer;
/*    */   }
/*    */   
/*    */   public static IBlockState getStateSnowLayer() {
/* 40 */     return Blocks.snow_layer.getDefaultState();
/*    */   }
/*    */   
/*    */   public static boolean shouldRender(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos) {
/* 44 */     Block block = blockState.getBlock();
/* 45 */     return !checkBlock(block, blockState) ? false : hasSnowNeighbours(blockAccess, blockPos);
/*    */   }
/*    */   
/*    */   private static boolean hasSnowNeighbours(IBlockAccess blockAccess, BlockPos pos) {
/* 49 */     Block block = Blocks.snow_layer;
/* 50 */     return (blockAccess.getBlockState(pos.north()).getBlock() != block && blockAccess.getBlockState(pos.south()).getBlock() != block && blockAccess.getBlockState(pos.west()).getBlock() != block && blockAccess.getBlockState(pos.east()).getBlock() != block) ? false : blockAccess.getBlockState(pos.down()).getBlock().isOpaqueCube();
/*    */   }
/*    */   
/*    */   private static boolean checkBlock(Block block, IBlockState blockState) {
/* 54 */     if (block.isFullCube())
/* 55 */       return false; 
/* 56 */     if (block.isOpaqueCube())
/* 57 */       return false; 
/* 58 */     if (block instanceof net.minecraft.block.BlockSnow)
/* 59 */       return false; 
/* 60 */     if (!(block instanceof net.minecraft.block.BlockBush) || (!(block instanceof net.minecraft.block.BlockDoublePlant) && !(block instanceof net.minecraft.block.BlockFlower) && !(block instanceof net.minecraft.block.BlockMushroom) && !(block instanceof net.minecraft.block.BlockSapling) && !(block instanceof net.minecraft.block.BlockTallGrass))) {
/* 61 */       if (!(block instanceof net.minecraft.block.BlockFence) && !(block instanceof net.minecraft.block.BlockFenceGate) && !(block instanceof net.minecraft.block.BlockFlowerPot) && !(block instanceof net.minecraft.block.BlockPane) && !(block instanceof net.minecraft.block.BlockReed) && !(block instanceof net.minecraft.block.BlockWall)) {
/* 62 */         if (block instanceof net.minecraft.block.BlockRedstoneTorch && blockState.getValue((IProperty)BlockTorch.FACING) == EnumFacing.UP) {
/* 63 */           return true;
/*    */         }
/* 65 */         if (block instanceof BlockLever) {
/* 66 */           Object object = blockState.getValue((IProperty)BlockLever.FACING);
/*    */           
/* 68 */           if (object == BlockLever.EnumOrientation.UP_X || object == BlockLever.EnumOrientation.UP_Z) {
/* 69 */             return true;
/*    */           }
/*    */         } 
/*    */         
/* 73 */         return false;
/*    */       } 
/*    */       
/* 76 */       return true;
/*    */     } 
/*    */     
/* 79 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\BetterSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */