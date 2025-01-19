/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenIcePath
/*    */   extends WorldGenerator {
/* 11 */   private Block block = Blocks.packed_ice;
/*    */   private int basePathWidth;
/*    */   
/*    */   public WorldGenIcePath(int p_i45454_1_) {
/* 15 */     this.basePathWidth = p_i45454_1_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 19 */     while (worldIn.isAirBlock(position) && position.getY() > 2) {
/* 20 */       position = position.down();
/*    */     }
/*    */     
/* 23 */     if (worldIn.getBlockState(position).getBlock() != Blocks.snow) {
/* 24 */       return false;
/*    */     }
/* 26 */     int i = rand.nextInt(this.basePathWidth - 2) + 2;
/* 27 */     int j = 1;
/*    */     
/* 29 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/* 30 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/* 31 */         int i1 = k - position.getX();
/* 32 */         int j1 = l - position.getZ();
/*    */         
/* 34 */         if (i1 * i1 + j1 * j1 <= i * i) {
/* 35 */           for (int k1 = position.getY() - j; k1 <= position.getY() + j; k1++) {
/* 36 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 37 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 39 */             if (block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice) {
/* 40 */               worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenIcePath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */