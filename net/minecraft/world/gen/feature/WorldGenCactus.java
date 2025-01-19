/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenCactus
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 11 */     for (int i = 0; i < 10; i++) {
/* 12 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 14 */       if (worldIn.isAirBlock(blockpos)) {
/* 15 */         int j = 1 + rand.nextInt(rand.nextInt(3) + 1);
/*    */         
/* 17 */         for (int k = 0; k < j; k++) {
/* 18 */           if (Blocks.cactus.canBlockStay(worldIn, blockpos)) {
/* 19 */             worldIn.setBlockState(blockpos.up(k), Blocks.cactus.getDefaultState(), 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenCactus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */