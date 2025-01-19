/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenLiquids
/*    */   extends WorldGenerator {
/*    */   private Block block;
/*    */   
/*    */   public WorldGenLiquids(Block p_i45465_1_) {
/* 15 */     this.block = p_i45465_1_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 19 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.stone)
/* 20 */       return false; 
/* 21 */     if (worldIn.getBlockState(position.down()).getBlock() != Blocks.stone)
/* 22 */       return false; 
/* 23 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.air && worldIn.getBlockState(position).getBlock() != Blocks.stone) {
/* 24 */       return false;
/*    */     }
/* 26 */     int i = 0;
/*    */     
/* 28 */     if (worldIn.getBlockState(position.west()).getBlock() == Blocks.stone) {
/* 29 */       i++;
/*    */     }
/*    */     
/* 32 */     if (worldIn.getBlockState(position.east()).getBlock() == Blocks.stone) {
/* 33 */       i++;
/*    */     }
/*    */     
/* 36 */     if (worldIn.getBlockState(position.north()).getBlock() == Blocks.stone) {
/* 37 */       i++;
/*    */     }
/*    */     
/* 40 */     if (worldIn.getBlockState(position.south()).getBlock() == Blocks.stone) {
/* 41 */       i++;
/*    */     }
/*    */     
/* 44 */     int j = 0;
/*    */     
/* 46 */     if (worldIn.isAirBlock(position.west())) {
/* 47 */       j++;
/*    */     }
/*    */     
/* 50 */     if (worldIn.isAirBlock(position.east())) {
/* 51 */       j++;
/*    */     }
/*    */     
/* 54 */     if (worldIn.isAirBlock(position.north())) {
/* 55 */       j++;
/*    */     }
/*    */     
/* 58 */     if (worldIn.isAirBlock(position.south())) {
/* 59 */       j++;
/*    */     }
/*    */     
/* 62 */     if (i == 3 && j == 1) {
/* 63 */       worldIn.setBlockState(position, this.block.getDefaultState(), 2);
/* 64 */       worldIn.forceBlockUpdateTick(this.block, position, rand);
/*    */     } 
/*    */     
/* 67 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenLiquids.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */