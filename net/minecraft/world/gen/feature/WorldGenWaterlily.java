/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenWaterlily
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 11 */     for (int i = 0; i < 10; i++) {
/* 12 */       int j = position.getX() + rand.nextInt(8) - rand.nextInt(8);
/* 13 */       int k = position.getY() + rand.nextInt(4) - rand.nextInt(4);
/* 14 */       int l = position.getZ() + rand.nextInt(8) - rand.nextInt(8);
/*    */       
/* 16 */       if (worldIn.isAirBlock(new BlockPos(j, k, l)) && Blocks.waterlily.canPlaceBlockAt(worldIn, new BlockPos(j, k, l))) {
/* 17 */         worldIn.setBlockState(new BlockPos(j, k, l), Blocks.waterlily.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 21 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenWaterlily.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */