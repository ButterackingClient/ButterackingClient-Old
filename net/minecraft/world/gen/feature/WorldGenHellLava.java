/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenHellLava
/*    */   extends WorldGenerator {
/*    */   private final Block field_150553_a;
/*    */   private final boolean field_94524_b;
/*    */   
/*    */   public WorldGenHellLava(Block p_i45453_1_, boolean p_i45453_2_) {
/* 16 */     this.field_150553_a = p_i45453_1_;
/* 17 */     this.field_94524_b = p_i45453_2_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 21 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.netherrack)
/* 22 */       return false; 
/* 23 */     if (worldIn.getBlockState(position).getBlock().getMaterial() != Material.air && worldIn.getBlockState(position).getBlock() != Blocks.netherrack) {
/* 24 */       return false;
/*    */     }
/* 26 */     int i = 0;
/*    */     
/* 28 */     if (worldIn.getBlockState(position.west()).getBlock() == Blocks.netherrack) {
/* 29 */       i++;
/*    */     }
/*    */     
/* 32 */     if (worldIn.getBlockState(position.east()).getBlock() == Blocks.netherrack) {
/* 33 */       i++;
/*    */     }
/*    */     
/* 36 */     if (worldIn.getBlockState(position.north()).getBlock() == Blocks.netherrack) {
/* 37 */       i++;
/*    */     }
/*    */     
/* 40 */     if (worldIn.getBlockState(position.south()).getBlock() == Blocks.netherrack) {
/* 41 */       i++;
/*    */     }
/*    */     
/* 44 */     if (worldIn.getBlockState(position.down()).getBlock() == Blocks.netherrack) {
/* 45 */       i++;
/*    */     }
/*    */     
/* 48 */     int j = 0;
/*    */     
/* 50 */     if (worldIn.isAirBlock(position.west())) {
/* 51 */       j++;
/*    */     }
/*    */     
/* 54 */     if (worldIn.isAirBlock(position.east())) {
/* 55 */       j++;
/*    */     }
/*    */     
/* 58 */     if (worldIn.isAirBlock(position.north())) {
/* 59 */       j++;
/*    */     }
/*    */     
/* 62 */     if (worldIn.isAirBlock(position.south())) {
/* 63 */       j++;
/*    */     }
/*    */     
/* 66 */     if (worldIn.isAirBlock(position.down())) {
/* 67 */       j++;
/*    */     }
/*    */     
/* 70 */     if ((!this.field_94524_b && i == 4 && j == 1) || i == 5) {
/* 71 */       worldIn.setBlockState(position, this.field_150553_a.getDefaultState(), 2);
/* 72 */       worldIn.forceBlockUpdateTick(this.field_150553_a, position, rand);
/*    */     } 
/*    */     
/* 75 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenHellLava.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */