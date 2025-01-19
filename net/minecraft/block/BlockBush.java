/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockBush
/*    */   extends Block {
/*    */   protected BlockBush() {
/* 17 */     this(Material.plants);
/*    */   }
/*    */   
/*    */   protected BlockBush(Material materialIn) {
/* 21 */     this(materialIn, materialIn.getMaterialMapColor());
/*    */   }
/*    */   
/*    */   protected BlockBush(Material p_i46452_1_, MapColor p_i46452_2_) {
/* 25 */     super(p_i46452_1_, p_i46452_2_);
/* 26 */     setTickRandomly(true);
/* 27 */     float f = 0.2F;
/* 28 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
/* 29 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */   
/*    */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 33 */     return (super.canPlaceBlockAt(worldIn, pos) && canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canPlaceBlockOn(Block ground) {
/* 40 */     return !(ground != Blocks.grass && ground != Blocks.dirt && ground != Blocks.farmland);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 47 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/* 48 */     checkAndDropBlock(worldIn, pos, state);
/*    */   }
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 52 */     checkAndDropBlock(worldIn, pos, state);
/*    */   }
/*    */   
/*    */   protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 56 */     if (!canBlockStay(worldIn, pos, state)) {
/* 57 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 58 */       worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 3);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 63 */     return canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock());
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 67 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 74 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isFullCube() {
/* 78 */     return false;
/*    */   }
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 82 */     return EnumWorldBlockLayer.CUTOUT;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockBush.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */