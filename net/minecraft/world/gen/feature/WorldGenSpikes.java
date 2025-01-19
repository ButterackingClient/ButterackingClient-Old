/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenSpikes
/*    */   extends WorldGenerator {
/*    */   private Block baseBlockRequired;
/*    */   
/*    */   public WorldGenSpikes(Block p_i45464_1_) {
/* 16 */     this.baseBlockRequired = p_i45464_1_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 20 */     if (worldIn.isAirBlock(position) && worldIn.getBlockState(position.down()).getBlock() == this.baseBlockRequired) {
/* 21 */       int i = rand.nextInt(32) + 6;
/* 22 */       int j = rand.nextInt(4) + 1;
/* 23 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*    */       
/* 25 */       for (int k = position.getX() - j; k <= position.getX() + j; k++) {
/* 26 */         for (int l = position.getZ() - j; l <= position.getZ() + j; l++) {
/* 27 */           int i1 = k - position.getX();
/* 28 */           int j1 = l - position.getZ();
/*    */           
/* 30 */           if (i1 * i1 + j1 * j1 <= j * j + 1 && worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k, position.getY() - 1, l)).getBlock() != this.baseBlockRequired) {
/* 31 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 36 */       for (int l1 = position.getY(); l1 < position.getY() + i && l1 < 256; l1++) {
/* 37 */         for (int i2 = position.getX() - j; i2 <= position.getX() + j; i2++) {
/* 38 */           for (int j2 = position.getZ() - j; j2 <= position.getZ() + j; j2++) {
/* 39 */             int k2 = i2 - position.getX();
/* 40 */             int k1 = j2 - position.getZ();
/*    */             
/* 42 */             if (k2 * k2 + k1 * k1 <= j * j + 1) {
/* 43 */               worldIn.setBlockState(new BlockPos(i2, l1, j2), Blocks.obsidian.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 49 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal(worldIn);
/* 50 */       entityEnderCrystal.setLocationAndAngles((position.getX() + 0.5F), (position.getY() + i), (position.getZ() + 0.5F), rand.nextFloat() * 360.0F, 0.0F);
/* 51 */       worldIn.spawnEntityInWorld((Entity)entityEnderCrystal);
/* 52 */       worldIn.setBlockState(position.up(i), Blocks.bedrock.getDefaultState(), 2);
/* 53 */       return true;
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenSpikes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */