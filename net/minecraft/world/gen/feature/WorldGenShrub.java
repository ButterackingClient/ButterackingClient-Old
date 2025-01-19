/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenShrub
/*    */   extends WorldGenTrees {
/*    */   private final IBlockState leavesMetadata;
/*    */   private final IBlockState woodMetadata;
/*    */   
/*    */   public WorldGenShrub(IBlockState p_i46450_1_, IBlockState p_i46450_2_) {
/* 17 */     super(false);
/* 18 */     this.woodMetadata = p_i46450_1_;
/* 19 */     this.leavesMetadata = p_i46450_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*    */     Block block;
/* 25 */     while (((block = worldIn.getBlockState(position).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && position.getY() > 0) {
/* 26 */       position = position.down();
/*    */     }
/*    */     
/* 29 */     Block block1 = worldIn.getBlockState(position).getBlock();
/*    */     
/* 31 */     if (block1 == Blocks.dirt || block1 == Blocks.grass) {
/* 32 */       position = position.up();
/* 33 */       setBlockAndNotifyAdequately(worldIn, position, this.woodMetadata);
/*    */       
/* 35 */       for (int i = position.getY(); i <= position.getY() + 2; i++) {
/* 36 */         int j = i - position.getY();
/* 37 */         int k = 2 - j;
/*    */         
/* 39 */         for (int l = position.getX() - k; l <= position.getX() + k; l++) {
/* 40 */           int i1 = l - position.getX();
/*    */           
/* 42 */           for (int j1 = position.getZ() - k; j1 <= position.getZ() + k; j1++) {
/* 43 */             int k1 = j1 - position.getZ();
/*    */             
/* 45 */             if (Math.abs(i1) != k || Math.abs(k1) != k || rand.nextInt(2) != 0) {
/* 46 */               BlockPos blockpos = new BlockPos(l, i, j1);
/*    */               
/* 48 */               if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock()) {
/* 49 */                 setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenShrub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */