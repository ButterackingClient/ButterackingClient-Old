/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockLeavesBase extends Block {
/*    */   protected boolean fancyGraphics;
/*    */   
/*    */   protected BlockLeavesBase(Material materialIn, boolean fancyGraphics) {
/* 12 */     super(materialIn);
/* 13 */     this.fancyGraphics = fancyGraphics;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 20 */     return false;
/*    */   }
/*    */   
/*    */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 24 */     return (!this.fancyGraphics && worldIn.getBlockState(pos).getBlock() == this) ? false : super.shouldSideBeRendered(worldIn, pos, side);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockLeavesBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */