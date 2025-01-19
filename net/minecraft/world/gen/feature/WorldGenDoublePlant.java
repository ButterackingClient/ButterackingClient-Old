/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDoublePlant;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenDoublePlant
/*    */   extends WorldGenerator {
/*    */   private BlockDoublePlant.EnumPlantType field_150549_a;
/*    */   
/*    */   public void setPlantType(BlockDoublePlant.EnumPlantType p_180710_1_) {
/* 14 */     this.field_150549_a = p_180710_1_;
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 18 */     boolean flag = false;
/*    */     
/* 20 */     for (int i = 0; i < 64; i++) {
/* 21 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 23 */       if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 254) && Blocks.double_plant.canPlaceBlockAt(worldIn, blockpos)) {
/* 24 */         Blocks.double_plant.placeAt(worldIn, blockpos, this.field_150549_a, 2);
/* 25 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 29 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenDoublePlant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */