/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderEnd
/*     */   implements IChunkProvider
/*     */ {
/*     */   private Random endRNG;
/*     */   private NoiseGeneratorOctaves noiseGen1;
/*     */   private NoiseGeneratorOctaves noiseGen2;
/*     */   private NoiseGeneratorOctaves noiseGen3;
/*     */   public NoiseGeneratorOctaves noiseGen4;
/*     */   public NoiseGeneratorOctaves noiseGen5;
/*     */   private World endWorld;
/*     */   private double[] densities;
/*     */   private BiomeGenBase[] biomesForGeneration;
/*     */   double[] noiseData1;
/*     */   double[] noiseData2;
/*     */   double[] noiseData3;
/*     */   double[] noiseData4;
/*     */   double[] noiseData5;
/*     */   
/*     */   public ChunkProviderEnd(World worldIn, long seed) {
/*  41 */     this.endWorld = worldIn;
/*  42 */     this.endRNG = new Random(seed);
/*  43 */     this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*  44 */     this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*  45 */     this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
/*  46 */     this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
/*  47 */     this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
/*     */   }
/*     */   
/*     */   public void func_180520_a(int p_180520_1_, int p_180520_2_, ChunkPrimer p_180520_3_) {
/*  51 */     int i = 2;
/*  52 */     int j = i + 1;
/*  53 */     int k = 33;
/*  54 */     int l = i + 1;
/*  55 */     this.densities = initializeNoiseField(this.densities, p_180520_1_ * i, 0, p_180520_2_ * i, j, k, l);
/*     */     
/*  57 */     for (int i1 = 0; i1 < i; i1++) {
/*  58 */       for (int j1 = 0; j1 < i; j1++) {
/*  59 */         for (int k1 = 0; k1 < 32; k1++) {
/*  60 */           double d0 = 0.25D;
/*  61 */           double d1 = this.densities[((i1 + 0) * l + j1 + 0) * k + k1 + 0];
/*  62 */           double d2 = this.densities[((i1 + 0) * l + j1 + 1) * k + k1 + 0];
/*  63 */           double d3 = this.densities[((i1 + 1) * l + j1 + 0) * k + k1 + 0];
/*  64 */           double d4 = this.densities[((i1 + 1) * l + j1 + 1) * k + k1 + 0];
/*  65 */           double d5 = (this.densities[((i1 + 0) * l + j1 + 0) * k + k1 + 1] - d1) * d0;
/*  66 */           double d6 = (this.densities[((i1 + 0) * l + j1 + 1) * k + k1 + 1] - d2) * d0;
/*  67 */           double d7 = (this.densities[((i1 + 1) * l + j1 + 0) * k + k1 + 1] - d3) * d0;
/*  68 */           double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * k + k1 + 1] - d4) * d0;
/*     */           
/*  70 */           for (int l1 = 0; l1 < 4; l1++) {
/*  71 */             double d9 = 0.125D;
/*  72 */             double d10 = d1;
/*  73 */             double d11 = d2;
/*  74 */             double d12 = (d3 - d1) * d9;
/*  75 */             double d13 = (d4 - d2) * d9;
/*     */             
/*  77 */             for (int i2 = 0; i2 < 8; i2++) {
/*  78 */               double d14 = 0.125D;
/*  79 */               double d15 = d10;
/*  80 */               double d16 = (d11 - d10) * d14;
/*     */               
/*  82 */               for (int j2 = 0; j2 < 8; j2++) {
/*  83 */                 IBlockState iblockstate = null;
/*     */                 
/*  85 */                 if (d15 > 0.0D) {
/*  86 */                   iblockstate = Blocks.end_stone.getDefaultState();
/*     */                 }
/*     */                 
/*  89 */                 int k2 = i2 + i1 * 8;
/*  90 */                 int l2 = l1 + k1 * 4;
/*  91 */                 int i3 = j2 + j1 * 8;
/*  92 */                 p_180520_3_.setBlockState(k2, l2, i3, iblockstate);
/*  93 */                 d15 += d16;
/*     */               } 
/*     */               
/*  96 */               d10 += d12;
/*  97 */               d11 += d13;
/*     */             } 
/*     */             
/* 100 */             d1 += d5;
/* 101 */             d2 += d6;
/* 102 */             d3 += d7;
/* 103 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_180519_a(ChunkPrimer p_180519_1_) {
/* 111 */     for (int i = 0; i < 16; i++) {
/* 112 */       for (int j = 0; j < 16; j++) {
/* 113 */         int k = 1;
/* 114 */         int l = -1;
/* 115 */         IBlockState iblockstate = Blocks.end_stone.getDefaultState();
/* 116 */         IBlockState iblockstate1 = Blocks.end_stone.getDefaultState();
/*     */         
/* 118 */         for (int i1 = 127; i1 >= 0; i1--) {
/* 119 */           IBlockState iblockstate2 = p_180519_1_.getBlockState(i, i1, j);
/*     */           
/* 121 */           if (iblockstate2.getBlock().getMaterial() == Material.air) {
/* 122 */             l = -1;
/* 123 */           } else if (iblockstate2.getBlock() == Blocks.stone) {
/* 124 */             if (l == -1) {
/* 125 */               if (k <= 0) {
/* 126 */                 iblockstate = Blocks.air.getDefaultState();
/* 127 */                 iblockstate1 = Blocks.end_stone.getDefaultState();
/*     */               } 
/*     */               
/* 130 */               l = k;
/*     */               
/* 132 */               if (i1 >= 0) {
/* 133 */                 p_180519_1_.setBlockState(i, i1, j, iblockstate);
/*     */               } else {
/* 135 */                 p_180519_1_.setBlockState(i, i1, j, iblockstate1);
/*     */               } 
/* 137 */             } else if (l > 0) {
/* 138 */               l--;
/* 139 */               p_180519_1_.setBlockState(i, i1, j, iblockstate1);
/*     */             } 
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
/*     */   public Chunk provideChunk(int x, int z) {
/* 152 */     this.endRNG.setSeed(x * 341873128712L + z * 132897987541L);
/* 153 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 154 */     this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
/* 155 */     func_180520_a(x, z, chunkprimer);
/* 156 */     func_180519_a(chunkprimer);
/* 157 */     Chunk chunk = new Chunk(this.endWorld, chunkprimer, x, z);
/* 158 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 160 */     for (int i = 0; i < abyte.length; i++) {
/* 161 */       abyte[i] = (byte)(this.biomesForGeneration[i]).biomeID;
/*     */     }
/*     */     
/* 164 */     chunk.generateSkylightMap();
/* 165 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] initializeNoiseField(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_) {
/* 173 */     if (p_73187_1_ == null) {
/* 174 */       p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
/*     */     }
/*     */     
/* 177 */     double d0 = 684.412D;
/* 178 */     double d1 = 684.412D;
/* 179 */     this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121D, 1.121D, 0.5D);
/* 180 */     this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0D, 200.0D, 0.5D);
/* 181 */     d0 *= 2.0D;
/* 182 */     this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
/* 183 */     this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
/* 184 */     this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
/* 185 */     int i = 0;
/*     */     
/* 187 */     for (int j = 0; j < p_73187_5_; j++) {
/* 188 */       for (int k = 0; k < p_73187_7_; k++) {
/* 189 */         float f = (j + p_73187_2_) / 1.0F;
/* 190 */         float f1 = (k + p_73187_4_) / 1.0F;
/* 191 */         float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;
/*     */         
/* 193 */         if (f2 > 80.0F) {
/* 194 */           f2 = 80.0F;
/*     */         }
/*     */         
/* 197 */         if (f2 < -100.0F) {
/* 198 */           f2 = -100.0F;
/*     */         }
/*     */         
/* 201 */         for (int l = 0; l < p_73187_6_; l++) {
/* 202 */           double d2 = 0.0D;
/* 203 */           double d3 = this.noiseData2[i] / 512.0D;
/* 204 */           double d4 = this.noiseData3[i] / 512.0D;
/* 205 */           double d5 = (this.noiseData1[i] / 10.0D + 1.0D) / 2.0D;
/*     */           
/* 207 */           if (d5 < 0.0D) {
/* 208 */             d2 = d3;
/* 209 */           } else if (d5 > 1.0D) {
/* 210 */             d2 = d4;
/*     */           } else {
/* 212 */             d2 = d3 + (d4 - d3) * d5;
/*     */           } 
/*     */           
/* 215 */           d2 -= 8.0D;
/* 216 */           d2 += f2;
/* 217 */           int i1 = 2;
/*     */           
/* 219 */           if (l > p_73187_6_ / 2 - i1) {
/* 220 */             double d6 = ((l - p_73187_6_ / 2 - i1) / 64.0F);
/* 221 */             d6 = MathHelper.clamp_double(d6, 0.0D, 1.0D);
/* 222 */             d2 = d2 * (1.0D - d6) + -3000.0D * d6;
/*     */           } 
/*     */           
/* 225 */           i1 = 8;
/*     */           
/* 227 */           if (l < i1) {
/* 228 */             double d7 = ((i1 - l) / (i1 - 1.0F));
/* 229 */             d2 = d2 * (1.0D - d7) + -30.0D * d7;
/*     */           } 
/*     */           
/* 232 */           p_73187_1_[i] = d2;
/* 233 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     return p_73187_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 245 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 252 */     BlockFalling.fallInstantly = true;
/* 253 */     BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
/* 254 */     this.endWorld.getBiomeGenForCoords(blockpos.add(16, 0, 16)).decorate(this.endWorld, this.endWorld.rand, blockpos);
/* 255 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 259 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 267 */     return true;
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
/* 281 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 288 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 295 */     return "RandomLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 299 */     return this.endWorld.getBiomeGenForCoords(pos).getSpawnableList(creatureType);
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 303 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 307 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 314 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\ChunkProviderEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */