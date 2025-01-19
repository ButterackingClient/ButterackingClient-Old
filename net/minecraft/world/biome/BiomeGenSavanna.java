/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDirt;
/*    */ import net.minecraft.block.BlockDoublePlant;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*    */ 
/*    */ public class BiomeGenSavanna extends BiomeGenBase {
/* 16 */   private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);
/*    */   
/*    */   protected BiomeGenSavanna(int id) {
/* 19 */     super(id);
/* 20 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityHorse.class, 1, 2, 6));
/* 21 */     this.theBiomeDecorator.treesPerChunk = 1;
/* 22 */     this.theBiomeDecorator.flowersPerChunk = 4;
/* 23 */     this.theBiomeDecorator.grassPerChunk = 20;
/*    */   }
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 27 */     return (rand.nextInt(5) > 0) ? (WorldGenAbstractTree)field_150627_aC : (WorldGenAbstractTree)this.worldGeneratorTrees;
/*    */   }
/*    */   
/*    */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 31 */     BiomeGenBase biomegenbase = new Mutated(p_180277_1_, this);
/* 32 */     biomegenbase.temperature = (this.temperature + 1.0F) * 0.5F;
/* 33 */     biomegenbase.minHeight = this.minHeight * 0.5F + 0.3F;
/* 34 */     biomegenbase.maxHeight = this.maxHeight * 0.5F + 1.2F;
/* 35 */     return biomegenbase;
/*    */   }
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 39 */     DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
/*    */     
/* 41 */     for (int i = 0; i < 7; i++) {
/* 42 */       int j = rand.nextInt(16) + 8;
/* 43 */       int k = rand.nextInt(16) + 8;
/* 44 */       int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
/* 45 */       DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
/*    */     } 
/*    */     
/* 48 */     super.decorate(worldIn, rand, pos);
/*    */   }
/*    */   
/*    */   public static class Mutated extends BiomeGenMutated {
/*    */     public Mutated(int p_i45382_1_, BiomeGenBase p_i45382_2_) {
/* 53 */       super(p_i45382_1_, p_i45382_2_);
/* 54 */       this.theBiomeDecorator.treesPerChunk = 2;
/* 55 */       this.theBiomeDecorator.flowersPerChunk = 2;
/* 56 */       this.theBiomeDecorator.grassPerChunk = 5;
/*    */     }
/*    */     
/*    */     public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 60 */       this.topBlock = Blocks.grass.getDefaultState();
/* 61 */       this.fillerBlock = Blocks.dirt.getDefaultState();
/*    */       
/* 63 */       if (noiseVal > 1.75D) {
/* 64 */         this.topBlock = Blocks.stone.getDefaultState();
/* 65 */         this.fillerBlock = Blocks.stone.getDefaultState();
/* 66 */       } else if (noiseVal > -0.5D) {
/* 67 */         this.topBlock = Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT);
/*    */       } 
/*    */       
/* 70 */       generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */     }
/*    */     
/*    */     public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 74 */       this.theBiomeDecorator.decorate(worldIn, rand, this, pos);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenSavanna.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */