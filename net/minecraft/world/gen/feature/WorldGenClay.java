/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenClay
/*    */   extends WorldGenerator {
/* 12 */   private Block field_150546_a = Blocks.clay;
/*    */ 
/*    */   
/*    */   private int numberOfBlocks;
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldGenClay(int p_i2011_1_) {
/* 20 */     this.numberOfBlocks = p_i2011_1_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 24 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.water) {
/* 25 */       return false;
/*    */     }
/* 27 */     int i = rand.nextInt(this.numberOfBlocks - 2) + 2;
/* 28 */     int j = 1;
/*    */     
/* 30 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/* 31 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/* 32 */         int i1 = k - position.getX();
/* 33 */         int j1 = l - position.getZ();
/*    */         
/* 35 */         if (i1 * i1 + j1 * j1 <= i * i) {
/* 36 */           for (int k1 = position.getY() - j; k1 <= position.getY() + j; k1++) {
/* 37 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 38 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 40 */             if (block == Blocks.dirt || block == Blocks.clay) {
/* 41 */               worldIn.setBlockState(blockpos, this.field_150546_a.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenClay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */