/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenDesertWells;
/*    */ 
/*    */ public class BiomeGenDesert
/*    */   extends BiomeGenBase {
/*    */   public BiomeGenDesert(int id) {
/* 12 */     super(id);
/* 13 */     this.spawnableCreatureList.clear();
/* 14 */     this.topBlock = Blocks.sand.getDefaultState();
/* 15 */     this.fillerBlock = Blocks.sand.getDefaultState();
/* 16 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 17 */     this.theBiomeDecorator.deadBushPerChunk = 2;
/* 18 */     this.theBiomeDecorator.reedsPerChunk = 50;
/* 19 */     this.theBiomeDecorator.cactiPerChunk = 10;
/* 20 */     this.spawnableCreatureList.clear();
/*    */   }
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 24 */     super.decorate(worldIn, rand, pos);
/*    */     
/* 26 */     if (rand.nextInt(1000) == 0) {
/* 27 */       int i = rand.nextInt(16) + 8;
/* 28 */       int j = rand.nextInt(16) + 8;
/* 29 */       BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
/* 30 */       (new WorldGenDesertWells()).generate(worldIn, rand, blockpos);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenDesert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */