/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockBreakable extends Block {
/*    */   private boolean ignoreSimilarity;
/*    */   
/*    */   protected BlockBreakable(Material materialIn, boolean ignoreSimilarityIn) {
/* 15 */     this(materialIn, ignoreSimilarityIn, materialIn.getMaterialMapColor());
/*    */   }
/*    */   
/*    */   protected BlockBreakable(Material p_i46393_1_, boolean p_i46393_2_, MapColor p_i46393_3_) {
/* 19 */     super(p_i46393_1_, p_i46393_3_);
/* 20 */     this.ignoreSimilarity = p_i46393_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 31 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 32 */     Block block = iblockstate.getBlock();
/*    */     
/* 34 */     if (this == Blocks.glass || this == Blocks.stained_glass) {
/* 35 */       if (worldIn.getBlockState(pos.offset(side.getOpposite())) != iblockstate) {
/* 36 */         return true;
/*    */       }
/*    */       
/* 39 */       if (block == this) {
/* 40 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 44 */     return (!this.ignoreSimilarity && block == this) ? false : super.shouldSideBeRendered(worldIn, pos, side);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockBreakable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */