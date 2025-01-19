/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenSand
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block block;
/*    */   private int radius;
/*    */   
/*    */   public WorldGenSand(Block p_i45462_1_, int p_i45462_2_) {
/* 20 */     this.block = p_i45462_1_;
/* 21 */     this.radius = p_i45462_2_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 25 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.water) {
/* 26 */       return false;
/*    */     }
/* 28 */     int i = rand.nextInt(this.radius - 2) + 2;
/* 29 */     int j = 2;
/*    */     
/* 31 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/* 32 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/* 33 */         int i1 = k - position.getX();
/* 34 */         int j1 = l - position.getZ();
/*    */         
/* 36 */         if (i1 * i1 + j1 * j1 <= i * i) {
/* 37 */           for (int k1 = position.getY() - j; k1 <= position.getY() + j; k1++) {
/* 38 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 39 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 41 */             if (block == Blocks.dirt || block == Blocks.grass) {
/* 42 */               worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenSand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */