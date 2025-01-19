/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenIceSpike
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 14 */     while (worldIn.isAirBlock(position) && position.getY() > 2) {
/* 15 */       position = position.down();
/*    */     }
/*    */     
/* 18 */     if (worldIn.getBlockState(position).getBlock() != Blocks.snow) {
/* 19 */       return false;
/*    */     }
/* 21 */     position = position.up(rand.nextInt(4));
/* 22 */     int i = rand.nextInt(4) + 7;
/* 23 */     int j = i / 4 + rand.nextInt(2);
/*    */     
/* 25 */     if (j > 1 && rand.nextInt(60) == 0) {
/* 26 */       position = position.up(10 + rand.nextInt(30));
/*    */     }
/*    */     
/* 29 */     for (int k = 0; k < i; k++) {
/* 30 */       float f = (1.0F - k / i) * j;
/* 31 */       int l = MathHelper.ceiling_float_int(f);
/*    */       
/* 33 */       for (int i1 = -l; i1 <= l; i1++) {
/* 34 */         float f1 = MathHelper.abs_int(i1) - 0.25F;
/*    */         
/* 36 */         for (int j1 = -l; j1 <= l; j1++) {
/* 37 */           float f2 = MathHelper.abs_int(j1) - 0.25F;
/*    */           
/* 39 */           if (((i1 == 0 && j1 == 0) || f1 * f1 + f2 * f2 <= f * f) && ((i1 != -l && i1 != l && j1 != -l && j1 != l) || rand.nextFloat() <= 0.75F)) {
/* 40 */             Block block = worldIn.getBlockState(position.add(i1, k, j1)).getBlock();
/*    */             
/* 42 */             if (block.getMaterial() == Material.air || block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice) {
/* 43 */               setBlockAndNotifyAdequately(worldIn, position.add(i1, k, j1), Blocks.packed_ice.getDefaultState());
/*    */             }
/*    */             
/* 46 */             if (k != 0 && l > 1) {
/* 47 */               block = worldIn.getBlockState(position.add(i1, -k, j1)).getBlock();
/*    */               
/* 49 */               if (block.getMaterial() == Material.air || block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice) {
/* 50 */                 setBlockAndNotifyAdequately(worldIn, position.add(i1, -k, j1), Blocks.packed_ice.getDefaultState());
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     int k1 = j - 1;
/*    */     
/* 60 */     if (k1 < 0) {
/* 61 */       k1 = 0;
/* 62 */     } else if (k1 > 1) {
/* 63 */       k1 = 1;
/*    */     } 
/*    */     
/* 66 */     for (int l1 = -k1; l1 <= k1; l1++) {
/* 67 */       for (int i2 = -k1; i2 <= k1; i2++) {
/* 68 */         BlockPos blockpos = position.add(l1, -1, i2);
/* 69 */         int j2 = 50;
/*    */         
/* 71 */         if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
/* 72 */           j2 = rand.nextInt(5);
/*    */         }
/*    */         
/* 75 */         while (blockpos.getY() > 50) {
/* 76 */           Block block1 = worldIn.getBlockState(blockpos).getBlock();
/*    */           
/* 78 */           if (block1.getMaterial() != Material.air && block1 != Blocks.dirt && block1 != Blocks.snow && block1 != Blocks.ice && block1 != Blocks.packed_ice) {
/*    */             break;
/*    */           }
/*    */           
/* 82 */           setBlockAndNotifyAdequately(worldIn, blockpos, Blocks.packed_ice.getDefaultState());
/* 83 */           blockpos = blockpos.down();
/* 84 */           j2--;
/*    */           
/* 86 */           if (j2 <= 0) {
/* 87 */             blockpos = blockpos.down(rand.nextInt(5) + 1);
/* 88 */             j2 = rand.nextInt(5);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenIceSpike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */