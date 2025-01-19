/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenReed
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 12 */     for (int i = 0; i < 20; i++) {
/* 13 */       BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
/*    */       
/* 15 */       if (worldIn.isAirBlock(blockpos)) {
/* 16 */         BlockPos blockpos1 = blockpos.down();
/*    */         
/* 18 */         if (worldIn.getBlockState(blockpos1.west()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos1.east()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos1.north()).getBlock().getMaterial() == Material.water || worldIn.getBlockState(blockpos1.south()).getBlock().getMaterial() == Material.water) {
/* 19 */           int j = 2 + rand.nextInt(rand.nextInt(3) + 1);
/*    */           
/* 21 */           for (int k = 0; k < j; k++) {
/* 22 */             if (Blocks.reeds.canBlockStay(worldIn, blockpos)) {
/* 23 */               worldIn.setBlockState(blockpos.up(k), Blocks.reeds.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */