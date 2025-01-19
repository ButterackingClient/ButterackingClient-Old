/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenGlowStone2
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 13 */     if (!worldIn.isAirBlock(position))
/* 14 */       return false; 
/* 15 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.netherrack) {
/* 16 */       return false;
/*    */     }
/* 18 */     worldIn.setBlockState(position, Blocks.glowstone.getDefaultState(), 2);
/*    */     
/* 20 */     for (int i = 0; i < 1500; i++) {
/* 21 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 23 */       if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
/* 24 */         int j = 0; byte b; int k;
/*    */         EnumFacing[] arrayOfEnumFacing;
/* 26 */         for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 27 */           if (worldIn.getBlockState(blockpos.offset(enumfacing)).getBlock() == Blocks.glowstone) {
/* 28 */             j++;
/*    */           }
/*    */           
/* 31 */           if (j > 1) {
/*    */             break;
/*    */           }
/*    */           b++; }
/*    */         
/* 36 */         if (j == 1) {
/* 37 */           worldIn.setBlockState(blockpos, Blocks.glowstone.getDefaultState(), 2);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenGlowStone2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */