/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class WorldGenAbstractTree
/*    */   extends WorldGenerator {
/*    */   public WorldGenAbstractTree(boolean notify) {
/* 13 */     super(notify);
/*    */   }
/*    */   
/*    */   protected boolean func_150523_a(Block p_150523_1_) {
/* 17 */     Material material = p_150523_1_.getMaterial();
/* 18 */     return !(material != Material.air && material != Material.leaves && p_150523_1_ != Blocks.grass && p_150523_1_ != Blocks.dirt && p_150523_1_ != Blocks.log && p_150523_1_ != Blocks.log2 && p_150523_1_ != Blocks.sapling && p_150523_1_ != Blocks.vine);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_180711_a(World worldIn, Random p_180711_2_, BlockPos p_180711_3_) {}
/*    */   
/*    */   protected void func_175921_a(World worldIn, BlockPos pos) {
/* 25 */     if (worldIn.getBlockState(pos).getBlock() != Blocks.dirt)
/* 26 */       setBlockAndNotifyAdequately(worldIn, pos, Blocks.dirt.getDefaultState()); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenAbstractTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */