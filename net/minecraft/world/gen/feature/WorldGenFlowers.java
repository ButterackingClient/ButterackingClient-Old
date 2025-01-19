/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenFlowers
/*    */   extends WorldGenerator {
/*    */   private BlockFlower flower;
/*    */   private IBlockState field_175915_b;
/*    */   
/*    */   public WorldGenFlowers(BlockFlower p_i45632_1_, BlockFlower.EnumFlowerType p_i45632_2_) {
/* 15 */     setGeneratedBlock(p_i45632_1_, p_i45632_2_);
/*    */   }
/*    */   
/*    */   public void setGeneratedBlock(BlockFlower p_175914_1_, BlockFlower.EnumFlowerType p_175914_2_) {
/* 19 */     this.flower = p_175914_1_;
/* 20 */     this.field_175915_b = p_175914_1_.getDefaultState().withProperty(p_175914_1_.getTypeProperty(), (Comparable)p_175914_2_);
/*    */   }
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 24 */     for (int i = 0; i < 64; i++) {
/* 25 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 27 */       if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.field_175915_b)) {
/* 28 */         worldIn.setBlockState(blockpos, this.field_175915_b, 2);
/*    */       }
/*    */     } 
/*    */     
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenFlowers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */