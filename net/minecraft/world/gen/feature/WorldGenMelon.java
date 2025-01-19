/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenMelon
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 11 */     for (int i = 0; i < 64; i++) {
/* 12 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 14 */       if (Blocks.melon_block.canPlaceBlockAt(worldIn, blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.grass) {
/* 15 */         worldIn.setBlockState(blockpos, Blocks.melon_block.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 19 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenMelon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */