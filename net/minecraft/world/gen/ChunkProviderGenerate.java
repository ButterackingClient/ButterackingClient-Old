/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.SpawnerAnimals;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.feature.WorldGenDungeons;
/*     */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*     */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*     */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*     */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*     */ import net.minecraft.world.gen.structure.MapGenVillage;
/*     */ import net.minecraft.world.gen.structure.StructureOceanMonument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderGenerate
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Random rand;
/*     */   private NoiseGeneratorOctaves field_147431_j;
/*     */   private NoiseGeneratorOctaves field_147432_k;
/*     */   private NoiseGeneratorOctaves field_147429_l;
/*     */   private NoiseGeneratorPerlin field_147430_m;
/*     */   public NoiseGeneratorOctaves noiseGen5;
/*     */   public NoiseGeneratorOctaves noiseGen6;
/*     */   public NoiseGeneratorOctaves mobSpawnerNoise;
/*     */   private World worldObj;
/*     */   private final boolean mapFeaturesEnabled;
/*     */   private WorldType field_177475_o;
/*     */   private final double[] field_147434_q;
/*     */   private final float[] parabolicField;
/*     */   private ChunkProviderSettings settings;
/*  63 */   private Block oceanBlockTmpl = (Block)Blocks.water;
/*  64 */   private double[] stoneNoise = new double[256];
/*  65 */   private MapGenBase caveGenerator = new MapGenCaves();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private MapGenStronghold strongholdGenerator = new MapGenStronghold();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private MapGenVillage villageGenerator = new MapGenVillage();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
/*  81 */   private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private MapGenBase ravineGenerator = new MapGenRavine();
/*  87 */   private StructureOceanMonument oceanMonumentGenerator = new StructureOceanMonument();
/*     */   
/*     */   private BiomeGenBase[] biomesForGeneration;
/*     */   
/*     */   double[] mainNoiseArray;
/*     */   
/*     */   double[] lowerLimitNoiseArray;
/*     */   
/*     */   double[] upperLimitNoiseArray;
/*     */   double[] depthNoiseArray;
/*     */   
/*     */   public ChunkProviderGenerate(World worldIn, long seed, boolean generateStructures, String structuresJson) {
/*  99 */     this.worldObj = worldIn;
/* 100 */     this.mapFeaturesEnabled = generateStructures;
/* 101 */     this.field_177475_o = worldIn.getWorldInfo().getTerrainType();
/* 102 */     this.rand = new Random(seed);
/* 103 */     this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
/* 104 */     this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
/* 105 */     this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
/* 106 */     this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
/* 107 */     this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
/* 108 */     this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
/* 109 */     this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
/* 110 */     this.field_147434_q = new double[825];
/* 111 */     this.parabolicField = new float[25];
/*     */     
/* 113 */     for (int i = -2; i <= 2; i++) {
/* 114 */       for (int j = -2; j <= 2; j++) {
/* 115 */         float f = 10.0F / MathHelper.sqrt_float((i * i + j * j) + 0.2F);
/* 116 */         this.parabolicField[i + 2 + (j + 2) * 5] = f;
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     if (structuresJson != null) {
/* 121 */       this.settings = ChunkProviderSettings.Factory.jsonToFactory(structuresJson).func_177864_b();
/* 122 */       this.oceanBlockTmpl = this.settings.useLavaOceans ? (Block)Blocks.lava : (Block)Blocks.water;
/* 123 */       worldIn.setSeaLevel(this.settings.seaLevel);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
/* 131 */     this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
/* 132 */     func_147423_a(x * 4, 0, z * 4);
/*     */     
/* 134 */     for (int i = 0; i < 4; i++) {
/* 135 */       int j = i * 5;
/* 136 */       int k = (i + 1) * 5;
/*     */       
/* 138 */       for (int l = 0; l < 4; l++) {
/* 139 */         int i1 = (j + l) * 33;
/* 140 */         int j1 = (j + l + 1) * 33;
/* 141 */         int k1 = (k + l) * 33;
/* 142 */         int l1 = (k + l + 1) * 33;
/*     */         
/* 144 */         for (int i2 = 0; i2 < 32; i2++) {
/* 145 */           double d0 = 0.125D;
/* 146 */           double d1 = this.field_147434_q[i1 + i2];
/* 147 */           double d2 = this.field_147434_q[j1 + i2];
/* 148 */           double d3 = this.field_147434_q[k1 + i2];
/* 149 */           double d4 = this.field_147434_q[l1 + i2];
/* 150 */           double d5 = (this.field_147434_q[i1 + i2 + 1] - d1) * d0;
/* 151 */           double d6 = (this.field_147434_q[j1 + i2 + 1] - d2) * d0;
/* 152 */           double d7 = (this.field_147434_q[k1 + i2 + 1] - d3) * d0;
/* 153 */           double d8 = (this.field_147434_q[l1 + i2 + 1] - d4) * d0;
/*     */           
/* 155 */           for (int j2 = 0; j2 < 8; j2++) {
/* 156 */             double d9 = 0.25D;
/* 157 */             double d10 = d1;
/* 158 */             double d11 = d2;
/* 159 */             double d12 = (d3 - d1) * d9;
/* 160 */             double d13 = (d4 - d2) * d9;
/*     */             
/* 162 */             for (int k2 = 0; k2 < 4; k2++) {
/* 163 */               double d14 = 0.25D;
/* 164 */               double d16 = (d11 - d10) * d14;
/* 165 */               double lvt_45_1_ = d10 - d16;
/*     */               
/* 167 */               for (int l2 = 0; l2 < 4; l2++) {
/* 168 */                 if ((lvt_45_1_ += d16) > 0.0D) {
/* 169 */                   primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.stone.getDefaultState());
/* 170 */                 } else if (i2 * 8 + j2 < this.settings.seaLevel) {
/* 171 */                   primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, this.oceanBlockTmpl.getDefaultState());
/*     */                 } 
/*     */               } 
/*     */               
/* 175 */               d10 += d12;
/* 176 */               d11 += d13;
/*     */             } 
/*     */             
/* 179 */             d1 += d5;
/* 180 */             d2 += d6;
/* 181 */             d3 += d7;
/* 182 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replaceBlocksForBiome(int x, int z, ChunkPrimer primer, BiomeGenBase[] biomeGens) {
/* 194 */     double d0 = 0.03125D;
/* 195 */     this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (x * 16), (z * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);
/*     */     
/* 197 */     for (int i = 0; i < 16; i++) {
/* 198 */       for (int j = 0; j < 16; j++) {
/* 199 */         BiomeGenBase biomegenbase = biomeGens[j + i * 16];
/* 200 */         biomegenbase.genTerrainBlocks(this.worldObj, this.rand, primer, x * 16 + i, z * 16 + j, this.stoneNoise[j + i * 16]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 210 */     this.rand.setSeed(x * 341873128712L + z * 132897987541L);
/* 211 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 212 */     setBlocksInChunk(x, z, chunkprimer);
/* 213 */     this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
/* 214 */     replaceBlocksForBiome(x, z, chunkprimer, this.biomesForGeneration);
/*     */     
/* 216 */     if (this.settings.useCaves) {
/* 217 */       this.caveGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 220 */     if (this.settings.useRavines) {
/* 221 */       this.ravineGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 224 */     if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
/* 225 */       this.mineshaftGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 228 */     if (this.settings.useVillages && this.mapFeaturesEnabled) {
/* 229 */       this.villageGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 232 */     if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
/* 233 */       this.strongholdGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 236 */     if (this.settings.useTemples && this.mapFeaturesEnabled) {
/* 237 */       this.scatteredFeatureGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 240 */     if (this.settings.useMonuments && this.mapFeaturesEnabled) {
/* 241 */       this.oceanMonumentGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 244 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 245 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 247 */     for (int i = 0; i < abyte.length; i++) {
/* 248 */       abyte[i] = (byte)(this.biomesForGeneration[i]).biomeID;
/*     */     }
/*     */     
/* 251 */     chunk.generateSkylightMap();
/* 252 */     return chunk;
/*     */   }
/*     */   
/*     */   private void func_147423_a(int x, int y, int z) {
/* 256 */     this.depthNoiseArray = this.noiseGen6.generateNoiseOctaves(this.depthNoiseArray, x, z, 5, 5, this.settings.depthNoiseScaleX, this.settings.depthNoiseScaleZ, this.settings.depthNoiseScaleExponent);
/* 257 */     float f = this.settings.coordinateScale;
/* 258 */     float f1 = this.settings.heightScale;
/* 259 */     this.mainNoiseArray = this.field_147429_l.generateNoiseOctaves(this.mainNoiseArray, x, y, z, 5, 33, 5, (f / this.settings.mainNoiseScaleX), (f1 / this.settings.mainNoiseScaleY), (f / this.settings.mainNoiseScaleZ));
/* 260 */     this.lowerLimitNoiseArray = this.field_147431_j.generateNoiseOctaves(this.lowerLimitNoiseArray, x, y, z, 5, 33, 5, f, f1, f);
/* 261 */     this.upperLimitNoiseArray = this.field_147432_k.generateNoiseOctaves(this.upperLimitNoiseArray, x, y, z, 5, 33, 5, f, f1, f);
/* 262 */     z = 0;
/* 263 */     x = 0;
/* 264 */     int i = 0;
/* 265 */     int j = 0;
/*     */     
/* 267 */     for (int k = 0; k < 5; k++) {
/* 268 */       for (int l = 0; l < 5; l++) {
/* 269 */         float f2 = 0.0F;
/* 270 */         float f3 = 0.0F;
/* 271 */         float f4 = 0.0F;
/* 272 */         int i1 = 2;
/* 273 */         BiomeGenBase biomegenbase = this.biomesForGeneration[k + 2 + (l + 2) * 10];
/*     */         
/* 275 */         for (int j1 = -i1; j1 <= i1; j1++) {
/* 276 */           for (int k1 = -i1; k1 <= i1; k1++) {
/* 277 */             BiomeGenBase biomegenbase1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
/* 278 */             float f5 = this.settings.biomeDepthOffSet + biomegenbase1.minHeight * this.settings.biomeDepthWeight;
/* 279 */             float f6 = this.settings.biomeScaleOffset + biomegenbase1.maxHeight * this.settings.biomeScaleWeight;
/*     */             
/* 281 */             if (this.field_177475_o == WorldType.AMPLIFIED && f5 > 0.0F) {
/* 282 */               f5 = 1.0F + f5 * 2.0F;
/* 283 */               f6 = 1.0F + f6 * 4.0F;
/*     */             } 
/*     */             
/* 286 */             float f7 = this.parabolicField[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
/*     */             
/* 288 */             if (biomegenbase1.minHeight > biomegenbase.minHeight) {
/* 289 */               f7 /= 2.0F;
/*     */             }
/*     */             
/* 292 */             f2 += f6 * f7;
/* 293 */             f3 += f5 * f7;
/* 294 */             f4 += f7;
/*     */           } 
/*     */         } 
/*     */         
/* 298 */         f2 /= f4;
/* 299 */         f3 /= f4;
/* 300 */         f2 = f2 * 0.9F + 0.1F;
/* 301 */         f3 = (f3 * 4.0F - 1.0F) / 8.0F;
/* 302 */         double d7 = this.depthNoiseArray[j] / 8000.0D;
/*     */         
/* 304 */         if (d7 < 0.0D) {
/* 305 */           d7 = -d7 * 0.3D;
/*     */         }
/*     */         
/* 308 */         d7 = d7 * 3.0D - 2.0D;
/*     */         
/* 310 */         if (d7 < 0.0D) {
/* 311 */           d7 /= 2.0D;
/*     */           
/* 313 */           if (d7 < -1.0D) {
/* 314 */             d7 = -1.0D;
/*     */           }
/*     */           
/* 317 */           d7 /= 1.4D;
/* 318 */           d7 /= 2.0D;
/*     */         } else {
/* 320 */           if (d7 > 1.0D) {
/* 321 */             d7 = 1.0D;
/*     */           }
/*     */           
/* 324 */           d7 /= 8.0D;
/*     */         } 
/*     */         
/* 327 */         j++;
/* 328 */         double d8 = f3;
/* 329 */         double d9 = f2;
/* 330 */         d8 += d7 * 0.2D;
/* 331 */         d8 = d8 * this.settings.baseSize / 8.0D;
/* 332 */         double d0 = this.settings.baseSize + d8 * 4.0D;
/*     */         
/* 334 */         for (int l1 = 0; l1 < 33; l1++) {
/* 335 */           double d1 = (l1 - d0) * this.settings.stretchY * 128.0D / 256.0D / d9;
/*     */           
/* 337 */           if (d1 < 0.0D) {
/* 338 */             d1 *= 4.0D;
/*     */           }
/*     */           
/* 341 */           double d2 = this.lowerLimitNoiseArray[i] / this.settings.lowerLimitScale;
/* 342 */           double d3 = this.upperLimitNoiseArray[i] / this.settings.upperLimitScale;
/* 343 */           double d4 = (this.mainNoiseArray[i] / 10.0D + 1.0D) / 2.0D;
/* 344 */           double d5 = MathHelper.denormalizeClamp(d2, d3, d4) - d1;
/*     */           
/* 346 */           if (l1 > 29) {
/* 347 */             double d6 = ((l1 - 29) / 3.0F);
/* 348 */             d5 = d5 * (1.0D - d6) + -10.0D * d6;
/*     */           } 
/*     */           
/* 351 */           this.field_147434_q[i] = d5;
/* 352 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 362 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 369 */     BlockFalling.fallInstantly = true;
/* 370 */     int i = x * 16;
/* 371 */     int j = z * 16;
/* 372 */     BlockPos blockpos = new BlockPos(i, 0, j);
/* 373 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16));
/* 374 */     this.rand.setSeed(this.worldObj.getSeed());
/* 375 */     long k = this.rand.nextLong() / 2L * 2L + 1L;
/* 376 */     long l = this.rand.nextLong() / 2L * 2L + 1L;
/* 377 */     this.rand.setSeed(x * k + z * l ^ this.worldObj.getSeed());
/* 378 */     boolean flag = false;
/* 379 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*     */     
/* 381 */     if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
/* 382 */       this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 385 */     if (this.settings.useVillages && this.mapFeaturesEnabled) {
/* 386 */       flag = this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 389 */     if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
/* 390 */       this.strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 393 */     if (this.settings.useTemples && this.mapFeaturesEnabled) {
/* 394 */       this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 397 */     if (this.settings.useMonuments && this.mapFeaturesEnabled) {
/* 398 */       this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, chunkcoordintpair);
/*     */     }
/*     */     
/* 401 */     if (biomegenbase != BiomeGenBase.desert && biomegenbase != BiomeGenBase.desertHills && this.settings.useWaterLakes && !flag && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
/* 402 */       int i1 = this.rand.nextInt(16) + 8;
/* 403 */       int j1 = this.rand.nextInt(256);
/* 404 */       int k1 = this.rand.nextInt(16) + 8;
/* 405 */       (new WorldGenLakes((Block)Blocks.water)).generate(this.worldObj, this.rand, blockpos.add(i1, j1, k1));
/*     */     } 
/*     */     
/* 408 */     if (!flag && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes) {
/* 409 */       int i2 = this.rand.nextInt(16) + 8;
/* 410 */       int l2 = this.rand.nextInt(this.rand.nextInt(248) + 8);
/* 411 */       int k3 = this.rand.nextInt(16) + 8;
/*     */       
/* 413 */       if (l2 < this.worldObj.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0) {
/* 414 */         (new WorldGenLakes((Block)Blocks.lava)).generate(this.worldObj, this.rand, blockpos.add(i2, l2, k3));
/*     */       }
/*     */     } 
/*     */     
/* 418 */     if (this.settings.useDungeons) {
/* 419 */       for (int j2 = 0; j2 < this.settings.dungeonChance; j2++) {
/* 420 */         int i3 = this.rand.nextInt(16) + 8;
/* 421 */         int l3 = this.rand.nextInt(256);
/* 422 */         int l1 = this.rand.nextInt(16) + 8;
/* 423 */         (new WorldGenDungeons()).generate(this.worldObj, this.rand, blockpos.add(i3, l3, l1));
/*     */       } 
/*     */     }
/*     */     
/* 427 */     biomegenbase.decorate(this.worldObj, this.rand, new BlockPos(i, 0, j));
/* 428 */     SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, i + 8, j + 8, 16, 16, this.rand);
/* 429 */     blockpos = blockpos.add(8, 0, 8);
/*     */     
/* 431 */     for (int k2 = 0; k2 < 16; k2++) {
/* 432 */       for (int j3 = 0; j3 < 16; j3++) {
/* 433 */         BlockPos blockpos1 = this.worldObj.getPrecipitationHeight(blockpos.add(k2, 0, j3));
/* 434 */         BlockPos blockpos2 = blockpos1.down();
/*     */         
/* 436 */         if (this.worldObj.canBlockFreezeWater(blockpos2)) {
/* 437 */           this.worldObj.setBlockState(blockpos2, Blocks.ice.getDefaultState(), 2);
/*     */         }
/*     */         
/* 440 */         if (this.worldObj.canSnowAt(blockpos1, true)) {
/* 441 */           this.worldObj.setBlockState(blockpos1, Blocks.snow_layer.getDefaultState(), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 446 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 450 */     boolean flag = false;
/*     */     
/* 452 */     if (this.settings.useMonuments && this.mapFeaturesEnabled && chunkIn.getInhabitedTime() < 3600L) {
/* 453 */       flag |= this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, new ChunkCoordIntPair(x, z));
/*     */     }
/*     */     
/* 456 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 464 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 478 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 485 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 492 */     return "RandomLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 496 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
/*     */     
/* 498 */     if (this.mapFeaturesEnabled) {
/* 499 */       if (creatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.func_175798_a(pos)) {
/* 500 */         return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
/*     */       }
/*     */       
/* 503 */       if (creatureType == EnumCreatureType.MONSTER && this.settings.useMonuments && this.oceanMonumentGenerator.isPositionInStructure(this.worldObj, pos)) {
/* 504 */         return this.oceanMonumentGenerator.getScatteredFeatureSpawnList();
/*     */       }
/*     */     } 
/*     */     
/* 508 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 512 */     return ("Stronghold".equals(structureName) && this.strongholdGenerator != null) ? this.strongholdGenerator.getClosestStrongholdPos(worldIn, position) : null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 516 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 520 */     if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
/* 521 */       this.mineshaftGenerator.generate(this, this.worldObj, x, z, null);
/*     */     }
/*     */     
/* 524 */     if (this.settings.useVillages && this.mapFeaturesEnabled) {
/* 525 */       this.villageGenerator.generate(this, this.worldObj, x, z, null);
/*     */     }
/*     */     
/* 528 */     if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
/* 529 */       this.strongholdGenerator.generate(this, this.worldObj, x, z, null);
/*     */     }
/*     */     
/* 532 */     if (this.settings.useTemples && this.mapFeaturesEnabled) {
/* 533 */       this.scatteredFeatureGenerator.generate(this, this.worldObj, x, z, null);
/*     */     }
/*     */     
/* 536 */     if (this.settings.useMonuments && this.mapFeaturesEnabled) {
/* 537 */       this.oceanMonumentGenerator.generate(this, this.worldObj, x, z, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 542 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\ChunkProviderGenerate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */