/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldChunkManagerHell
/*    */   extends WorldChunkManager
/*    */ {
/*    */   private BiomeGenBase biomeGenerator;
/*    */   private float rainfall;
/*    */   
/*    */   public WorldChunkManagerHell(BiomeGenBase p_i45374_1_, float p_i45374_2_) {
/* 21 */     this.biomeGenerator = p_i45374_1_;
/* 22 */     this.rainfall = p_i45374_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase getBiomeGenerator(BlockPos pos) {
/* 29 */     return this.biomeGenerator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int height) {
/* 36 */     if (biomes == null || biomes.length < width * height) {
/* 37 */       biomes = new BiomeGenBase[width * height];
/*    */     }
/*    */     
/* 40 */     Arrays.fill((Object[])biomes, 0, width * height, this.biomeGenerator);
/* 41 */     return biomes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
/* 48 */     if (listToReuse == null || listToReuse.length < width * length) {
/* 49 */       listToReuse = new float[width * length];
/*    */     }
/*    */     
/* 52 */     Arrays.fill(listToReuse, 0, width * length, this.rainfall);
/* 53 */     return listToReuse;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
/* 61 */     if (oldBiomeList == null || oldBiomeList.length < width * depth) {
/* 62 */       oldBiomeList = new BiomeGenBase[width * depth];
/*    */     }
/*    */     
/* 65 */     Arrays.fill((Object[])oldBiomeList, 0, width * depth, this.biomeGenerator);
/* 66 */     return oldBiomeList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
/* 74 */     return loadBlockGeneratorData(listToReuse, x, z, width, length);
/*    */   }
/*    */   
/*    */   public BlockPos findBiomePosition(int x, int z, int range, List<BiomeGenBase> biomes, Random random) {
/* 78 */     return biomes.contains(this.biomeGenerator) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1)) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List<BiomeGenBase> p_76940_4_) {
/* 85 */     return p_76940_4_.contains(this.biomeGenerator);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\WorldChunkManagerHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */