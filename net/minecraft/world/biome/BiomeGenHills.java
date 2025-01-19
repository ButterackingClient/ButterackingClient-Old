/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockSilverfish;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*    */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*    */ 
/*    */ public class BiomeGenHills extends BiomeGenBase {
/* 16 */   private WorldGenerator theWorldGenerator = (WorldGenerator)new WorldGenMinable(Blocks.monster_egg.getDefaultState().withProperty((IProperty)BlockSilverfish.VARIANT, (Comparable)BlockSilverfish.EnumType.STONE), 9);
/* 17 */   private WorldGenTaiga2 field_150634_aD = new WorldGenTaiga2(false);
/* 18 */   private int field_150635_aE = 0;
/* 19 */   private int field_150636_aF = 1;
/* 20 */   private int field_150637_aG = 2;
/*    */   private int field_150638_aH;
/*    */   
/*    */   protected BiomeGenHills(int id, boolean p_i45373_2_) {
/* 24 */     super(id);
/* 25 */     this.field_150638_aH = this.field_150635_aE;
/*    */     
/* 27 */     if (p_i45373_2_) {
/* 28 */       this.theBiomeDecorator.treesPerChunk = 3;
/* 29 */       this.field_150638_aH = this.field_150636_aF;
/*    */     } 
/*    */   }
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 34 */     return (rand.nextInt(3) > 0) ? (WorldGenAbstractTree)this.field_150634_aD : super.genBigTreeChance(rand);
/*    */   }
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 38 */     super.decorate(worldIn, rand, pos);
/* 39 */     int i = 3 + rand.nextInt(6);
/*    */     
/* 41 */     for (int j = 0; j < i; j++) {
/* 42 */       int k = rand.nextInt(16);
/* 43 */       int l = rand.nextInt(28) + 4;
/* 44 */       int i1 = rand.nextInt(16);
/* 45 */       BlockPos blockpos = pos.add(k, l, i1);
/*    */       
/* 47 */       if (worldIn.getBlockState(blockpos).getBlock() == Blocks.stone) {
/* 48 */         worldIn.setBlockState(blockpos, Blocks.emerald_ore.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 52 */     for (i = 0; i < 7; i++) {
/* 53 */       int j1 = rand.nextInt(16);
/* 54 */       int k1 = rand.nextInt(64);
/* 55 */       int l1 = rand.nextInt(16);
/* 56 */       this.theWorldGenerator.generate(worldIn, rand, pos.add(j1, k1, l1));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 61 */     this.topBlock = Blocks.grass.getDefaultState();
/* 62 */     this.fillerBlock = Blocks.dirt.getDefaultState();
/*    */     
/* 64 */     if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.field_150638_aH == this.field_150637_aG) {
/* 65 */       this.topBlock = Blocks.gravel.getDefaultState();
/* 66 */       this.fillerBlock = Blocks.gravel.getDefaultState();
/* 67 */     } else if (noiseVal > 1.0D && this.field_150638_aH != this.field_150636_aF) {
/* 68 */       this.topBlock = Blocks.stone.getDefaultState();
/* 69 */       this.fillerBlock = Blocks.stone.getDefaultState();
/*    */     } 
/*    */     
/* 72 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private BiomeGenHills mutateHills(BiomeGenBase p_150633_1_) {
/* 79 */     this.field_150638_aH = this.field_150637_aG;
/* 80 */     func_150557_a(p_150633_1_.color, true);
/* 81 */     setBiomeName(String.valueOf(p_150633_1_.biomeName) + " M");
/* 82 */     setHeight(new BiomeGenBase.Height(p_150633_1_.minHeight, p_150633_1_.maxHeight));
/* 83 */     setTemperatureRainfall(p_150633_1_.temperature, p_150633_1_.rainfall);
/* 84 */     return this;
/*    */   }
/*    */   
/*    */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 88 */     return (new BiomeGenHills(p_180277_1_, false)).mutateHills(this);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenHills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */