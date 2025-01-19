/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ 
/*    */ public class BiomeGenSwamp
/*    */   extends BiomeGenBase {
/*    */   protected BiomeGenSwamp(int id) {
/* 16 */     super(id);
/* 17 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 18 */     this.theBiomeDecorator.flowersPerChunk = 1;
/* 19 */     this.theBiomeDecorator.deadBushPerChunk = 1;
/* 20 */     this.theBiomeDecorator.mushroomsPerChunk = 8;
/* 21 */     this.theBiomeDecorator.reedsPerChunk = 10;
/* 22 */     this.theBiomeDecorator.clayPerChunk = 1;
/* 23 */     this.theBiomeDecorator.waterlilyPerChunk = 4;
/* 24 */     this.theBiomeDecorator.sandPerChunk2 = 0;
/* 25 */     this.theBiomeDecorator.sandPerChunk = 0;
/* 26 */     this.theBiomeDecorator.grassPerChunk = 5;
/* 27 */     this.waterColorMultiplier = 14745518;
/* 28 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntitySlime.class, 1, 1, 1));
/*    */   }
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 32 */     return (WorldGenAbstractTree)this.worldGeneratorSwamp;
/*    */   }
/*    */   
/*    */   public int getGrassColorAtPos(BlockPos pos) {
/* 36 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
/* 37 */     return (d0 < -0.1D) ? 5011004 : 6975545;
/*    */   }
/*    */   
/*    */   public int getFoliageColorAtPos(BlockPos pos) {
/* 41 */     return 6975545;
/*    */   }
/*    */   
/*    */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 45 */     return BlockFlower.EnumFlowerType.BLUE_ORCHID;
/*    */   }
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 49 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(x * 0.25D, z * 0.25D);
/*    */     
/* 51 */     if (d0 > 0.0D) {
/* 52 */       int i = x & 0xF;
/* 53 */       int j = z & 0xF;
/*    */       
/* 55 */       for (int k = 255; k >= 0; k--) {
/* 56 */         if (chunkPrimerIn.getBlockState(j, k, i).getBlock().getMaterial() != Material.air) {
/* 57 */           if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.water) {
/* 58 */             chunkPrimerIn.setBlockState(j, k, i, Blocks.water.getDefaultState());
/*    */             
/* 60 */             if (d0 < 0.12D) {
/* 61 */               chunkPrimerIn.setBlockState(j, k + 1, i, Blocks.waterlily.getDefaultState());
/*    */             }
/*    */           } 
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenSwamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */