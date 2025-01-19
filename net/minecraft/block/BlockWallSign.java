/*    */ package net.minecraft.block;
/*    */ import com.google.common.base.Predicate;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyDirection;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockWallSign extends BlockSign {
/* 13 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*    */   
/*    */   public BlockWallSign() {
/* 16 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 21 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 22 */     float f = 0.28125F;
/* 23 */     float f1 = 0.78125F;
/* 24 */     float f2 = 0.0F;
/* 25 */     float f3 = 1.0F;
/* 26 */     float f4 = 0.125F;
/* 27 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 29 */     switch (enumfacing) {
/*    */       case NORTH:
/* 31 */         setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
/*    */         break;
/*    */       
/*    */       case SOUTH:
/* 35 */         setBlockBounds(f2, f, 0.0F, f3, f1, f4);
/*    */         break;
/*    */       
/*    */       case WEST:
/* 39 */         setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
/*    */         break;
/*    */       
/*    */       case EAST:
/* 43 */         setBlockBounds(0.0F, f, f2, f4, f1, f3);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 51 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*    */     
/* 53 */     if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().getMaterial().isSolid()) {
/* 54 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 55 */       worldIn.setBlockToAir(pos);
/*    */     } 
/*    */     
/* 58 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 65 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*    */     
/* 67 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
/* 68 */       enumfacing = EnumFacing.NORTH;
/*    */     }
/*    */     
/* 71 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 78 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*    */   }
/*    */   
/*    */   protected BlockState createBlockState() {
/* 82 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockWallSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */