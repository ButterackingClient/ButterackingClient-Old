/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyInteger;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockStandingSign extends BlockSign {
/* 11 */   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
/*    */   
/*    */   public BlockStandingSign() {
/* 14 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)ROTATION, Integer.valueOf(0)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 21 */     if (!worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid()) {
/* 22 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 23 */       worldIn.setBlockToAir(pos);
/*    */     } 
/*    */     
/* 26 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 33 */     return getDefaultState().withProperty((IProperty)ROTATION, Integer.valueOf(meta));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 40 */     return ((Integer)state.getValue((IProperty)ROTATION)).intValue();
/*    */   }
/*    */   
/*    */   protected BlockState createBlockState() {
/* 44 */     return new BlockState(this, new IProperty[] { (IProperty)ROTATION });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockStandingSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */