/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ 
/*    */ public class BiomeGenOcean
/*    */   extends BiomeGenBase {
/*    */   public BiomeGenOcean(int id) {
/* 10 */     super(id);
/* 11 */     this.spawnableCreatureList.clear();
/*    */   }
/*    */   
/*    */   public BiomeGenBase.TempCategory getTempCategory() {
/* 15 */     return BiomeGenBase.TempCategory.OCEAN;
/*    */   }
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 19 */     super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenOcean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */