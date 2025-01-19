/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockHelper;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.feature.WorldGenFire;
/*     */ import net.minecraft.world.gen.feature.WorldGenGlowStone1;
/*     */ import net.minecraft.world.gen.feature.WorldGenGlowStone2;
/*     */ import net.minecraft.world.gen.feature.WorldGenHellLava;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import net.minecraft.world.gen.structure.MapGenNetherBridge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkProviderHell
/*     */   implements IChunkProvider
/*     */ {
/*     */   private final World worldObj;
/*     */   private final boolean field_177466_i;
/*     */   private final Random hellRNG;
/*  40 */   private double[] slowsandNoise = new double[256];
/*  41 */   private double[] gravelNoise = new double[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private double[] netherrackExclusivityNoise = new double[256];
/*     */ 
/*     */   
/*     */   private double[] noiseField;
/*     */ 
/*     */   
/*     */   private final NoiseGeneratorOctaves netherNoiseGen1;
/*     */ 
/*     */   
/*     */   private final NoiseGeneratorOctaves netherNoiseGen2;
/*     */   
/*     */   private final NoiseGeneratorOctaves netherNoiseGen3;
/*     */   
/*     */   private final NoiseGeneratorOctaves slowsandGravelNoiseGen;
/*     */   
/*     */   private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
/*     */   
/*     */   public final NoiseGeneratorOctaves netherNoiseGen6;
/*     */   
/*     */   public final NoiseGeneratorOctaves netherNoiseGen7;
/*     */   
/*  67 */   private final WorldGenFire field_177470_t = new WorldGenFire();
/*  68 */   private final WorldGenGlowStone1 field_177469_u = new WorldGenGlowStone1();
/*  69 */   private final WorldGenGlowStone2 field_177468_v = new WorldGenGlowStone2();
/*  70 */   private final WorldGenerator field_177467_w = (WorldGenerator)new WorldGenMinable(Blocks.quartz_ore.getDefaultState(), 14, (Predicate)BlockHelper.forBlock(Blocks.netherrack));
/*  71 */   private final WorldGenHellLava field_177473_x = new WorldGenHellLava((Block)Blocks.flowing_lava, true);
/*  72 */   private final WorldGenHellLava field_177472_y = new WorldGenHellLava((Block)Blocks.flowing_lava, false);
/*  73 */   private final GeneratorBushFeature field_177471_z = new GeneratorBushFeature(Blocks.brown_mushroom);
/*  74 */   private final GeneratorBushFeature field_177465_A = new GeneratorBushFeature(Blocks.red_mushroom);
/*  75 */   private final MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
/*  76 */   private final MapGenBase netherCaveGenerator = new MapGenCavesHell();
/*     */   double[] noiseData1;
/*     */   double[] noiseData2;
/*     */   double[] noiseData3;
/*     */   double[] noiseData4;
/*     */   double[] noiseData5;
/*     */   
/*     */   public ChunkProviderHell(World worldIn, boolean p_i45637_2_, long seed) {
/*  84 */     this.worldObj = worldIn;
/*  85 */     this.field_177466_i = p_i45637_2_;
/*  86 */     this.hellRNG = new Random(seed);
/*  87 */     this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  88 */     this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  89 */     this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
/*  90 */     this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
/*  91 */     this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
/*  92 */     this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
/*  93 */     this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
/*  94 */     worldIn.setSeaLevel(63);
/*     */   }
/*     */   
/*     */   public void func_180515_a(int p_180515_1_, int p_180515_2_, ChunkPrimer p_180515_3_) {
/*  98 */     int i = 4;
/*  99 */     int j = this.worldObj.getSeaLevel() / 2 + 1;
/* 100 */     int k = i + 1;
/* 101 */     int l = 17;
/* 102 */     int i1 = i + 1;
/* 103 */     this.noiseField = initializeNoiseField(this.noiseField, p_180515_1_ * i, 0, p_180515_2_ * i, k, l, i1);
/*     */     
/* 105 */     for (int j1 = 0; j1 < i; j1++) {
/* 106 */       for (int k1 = 0; k1 < i; k1++) {
/* 107 */         for (int l1 = 0; l1 < 16; l1++) {
/* 108 */           double d0 = 0.125D;
/* 109 */           double d1 = this.noiseField[((j1 + 0) * i1 + k1 + 0) * l + l1 + 0];
/* 110 */           double d2 = this.noiseField[((j1 + 0) * i1 + k1 + 1) * l + l1 + 0];
/* 111 */           double d3 = this.noiseField[((j1 + 1) * i1 + k1 + 0) * l + l1 + 0];
/* 112 */           double d4 = this.noiseField[((j1 + 1) * i1 + k1 + 1) * l + l1 + 0];
/* 113 */           double d5 = (this.noiseField[((j1 + 0) * i1 + k1 + 0) * l + l1 + 1] - d1) * d0;
/* 114 */           double d6 = (this.noiseField[((j1 + 0) * i1 + k1 + 1) * l + l1 + 1] - d2) * d0;
/* 115 */           double d7 = (this.noiseField[((j1 + 1) * i1 + k1 + 0) * l + l1 + 1] - d3) * d0;
/* 116 */           double d8 = (this.noiseField[((j1 + 1) * i1 + k1 + 1) * l + l1 + 1] - d4) * d0;
/*     */           
/* 118 */           for (int i2 = 0; i2 < 8; i2++) {
/* 119 */             double d9 = 0.25D;
/* 120 */             double d10 = d1;
/* 121 */             double d11 = d2;
/* 122 */             double d12 = (d3 - d1) * d9;
/* 123 */             double d13 = (d4 - d2) * d9;
/*     */             
/* 125 */             for (int j2 = 0; j2 < 4; j2++) {
/* 126 */               double d14 = 0.25D;
/* 127 */               double d15 = d10;
/* 128 */               double d16 = (d11 - d10) * d14;
/*     */               
/* 130 */               for (int k2 = 0; k2 < 4; k2++) {
/* 131 */                 IBlockState iblockstate = null;
/*     */                 
/* 133 */                 if (l1 * 8 + i2 < j) {
/* 134 */                   iblockstate = Blocks.lava.getDefaultState();
/*     */                 }
/*     */                 
/* 137 */                 if (d15 > 0.0D) {
/* 138 */                   iblockstate = Blocks.netherrack.getDefaultState();
/*     */                 }
/*     */                 
/* 141 */                 int l2 = j2 + j1 * 4;
/* 142 */                 int i3 = i2 + l1 * 8;
/* 143 */                 int j3 = k2 + k1 * 4;
/* 144 */                 p_180515_3_.setBlockState(l2, i3, j3, iblockstate);
/* 145 */                 d15 += d16;
/*     */               } 
/*     */               
/* 148 */               d10 += d12;
/* 149 */               d11 += d13;
/*     */             } 
/*     */             
/* 152 */             d1 += d5;
/* 153 */             d2 += d6;
/* 154 */             d3 += d7;
/* 155 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_180516_b(int p_180516_1_, int p_180516_2_, ChunkPrimer p_180516_3_) {
/* 163 */     int i = this.worldObj.getSeaLevel() + 1;
/* 164 */     double d0 = 0.03125D;
/* 165 */     this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_180516_1_ * 16, p_180516_2_ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
/* 166 */     this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_180516_1_ * 16, 109, p_180516_2_ * 16, 16, 1, 16, d0, 1.0D, d0);
/* 167 */     this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, p_180516_1_ * 16, p_180516_2_ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);
/*     */     
/* 169 */     for (int j = 0; j < 16; j++) {
/* 170 */       for (int k = 0; k < 16; k++) {
/* 171 */         boolean flag = (this.slowsandNoise[j + k * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D);
/* 172 */         boolean flag1 = (this.gravelNoise[j + k * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D);
/* 173 */         int l = (int)(this.netherrackExclusivityNoise[j + k * 16] / 3.0D + 3.0D + this.hellRNG.nextDouble() * 0.25D);
/* 174 */         int i1 = -1;
/* 175 */         IBlockState iblockstate = Blocks.netherrack.getDefaultState();
/* 176 */         IBlockState iblockstate1 = Blocks.netherrack.getDefaultState();
/*     */         
/* 178 */         for (int j1 = 127; j1 >= 0; j1--) {
/* 179 */           if (j1 < 127 - this.hellRNG.nextInt(5) && j1 > this.hellRNG.nextInt(5)) {
/* 180 */             IBlockState iblockstate2 = p_180516_3_.getBlockState(k, j1, j);
/*     */             
/* 182 */             if (iblockstate2.getBlock() != null && iblockstate2.getBlock().getMaterial() != Material.air) {
/* 183 */               if (iblockstate2.getBlock() == Blocks.netherrack) {
/* 184 */                 if (i1 == -1) {
/* 185 */                   if (l <= 0) {
/* 186 */                     iblockstate = null;
/* 187 */                     iblockstate1 = Blocks.netherrack.getDefaultState();
/* 188 */                   } else if (j1 >= i - 4 && j1 <= i + 1) {
/* 189 */                     iblockstate = Blocks.netherrack.getDefaultState();
/* 190 */                     iblockstate1 = Blocks.netherrack.getDefaultState();
/*     */                     
/* 192 */                     if (flag1) {
/* 193 */                       iblockstate = Blocks.gravel.getDefaultState();
/* 194 */                       iblockstate1 = Blocks.netherrack.getDefaultState();
/*     */                     } 
/*     */                     
/* 197 */                     if (flag) {
/* 198 */                       iblockstate = Blocks.soul_sand.getDefaultState();
/* 199 */                       iblockstate1 = Blocks.soul_sand.getDefaultState();
/*     */                     } 
/*     */                   } 
/*     */                   
/* 203 */                   if (j1 < i && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air)) {
/* 204 */                     iblockstate = Blocks.lava.getDefaultState();
/*     */                   }
/*     */                   
/* 207 */                   i1 = l;
/*     */                   
/* 209 */                   if (j1 >= i - 1) {
/* 210 */                     p_180516_3_.setBlockState(k, j1, j, iblockstate);
/*     */                   } else {
/* 212 */                     p_180516_3_.setBlockState(k, j1, j, iblockstate1);
/*     */                   } 
/* 214 */                 } else if (i1 > 0) {
/* 215 */                   i1--;
/* 216 */                   p_180516_3_.setBlockState(k, j1, j, iblockstate1);
/*     */                 } 
/*     */               }
/*     */             } else {
/* 220 */               i1 = -1;
/*     */             } 
/*     */           } else {
/* 223 */             p_180516_3_.setBlockState(k, j1, j, Blocks.bedrock.getDefaultState());
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
/* 235 */     this.hellRNG.setSeed(x * 341873128712L + z * 132897987541L);
/* 236 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 237 */     func_180515_a(x, z, chunkprimer);
/* 238 */     func_180516_b(x, z, chunkprimer);
/* 239 */     this.netherCaveGenerator.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     
/* 241 */     if (this.field_177466_i) {
/* 242 */       this.genNetherBridge.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 245 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 246 */     BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, x * 16, z * 16, 16, 16);
/* 247 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 249 */     for (int i = 0; i < abyte.length; i++) {
/* 250 */       abyte[i] = (byte)(abiomegenbase[i]).biomeID;
/*     */     }
/*     */     
/* 253 */     chunk.resetRelightChecks();
/* 254 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] initializeNoiseField(double[] p_73164_1_, int p_73164_2_, int p_73164_3_, int p_73164_4_, int p_73164_5_, int p_73164_6_, int p_73164_7_) {
/* 262 */     if (p_73164_1_ == null) {
/* 263 */       p_73164_1_ = new double[p_73164_5_ * p_73164_6_ * p_73164_7_];
/*     */     }
/*     */     
/* 266 */     double d0 = 684.412D;
/* 267 */     double d1 = 2053.236D;
/* 268 */     this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 1.0D, 0.0D, 1.0D);
/* 269 */     this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 100.0D, 0.0D, 100.0D);
/* 270 */     this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
/* 271 */     this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
/* 272 */     this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
/* 273 */     int i = 0;
/* 274 */     double[] adouble = new double[p_73164_6_];
/*     */     
/* 276 */     for (int j = 0; j < p_73164_6_; j++) {
/* 277 */       adouble[j] = Math.cos(j * Math.PI * 6.0D / p_73164_6_) * 2.0D;
/* 278 */       double d2 = j;
/*     */       
/* 280 */       if (j > p_73164_6_ / 2) {
/* 281 */         d2 = (p_73164_6_ - 1 - j);
/*     */       }
/*     */       
/* 284 */       if (d2 < 4.0D) {
/* 285 */         d2 = 4.0D - d2;
/* 286 */         adouble[j] = adouble[j] - d2 * d2 * d2 * 10.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     for (int l = 0; l < p_73164_5_; l++) {
/* 291 */       for (int i1 = 0; i1 < p_73164_7_; i1++) {
/* 292 */         double d3 = 0.0D;
/*     */         
/* 294 */         for (int k = 0; k < p_73164_6_; k++) {
/* 295 */           double d4 = 0.0D;
/* 296 */           double d5 = adouble[k];
/* 297 */           double d6 = this.noiseData2[i] / 512.0D;
/* 298 */           double d7 = this.noiseData3[i] / 512.0D;
/* 299 */           double d8 = (this.noiseData1[i] / 10.0D + 1.0D) / 2.0D;
/*     */           
/* 301 */           if (d8 < 0.0D) {
/* 302 */             d4 = d6;
/* 303 */           } else if (d8 > 1.0D) {
/* 304 */             d4 = d7;
/*     */           } else {
/* 306 */             d4 = d6 + (d7 - d6) * d8;
/*     */           } 
/*     */           
/* 309 */           d4 -= d5;
/*     */           
/* 311 */           if (k > p_73164_6_ - 4) {
/* 312 */             double d9 = ((k - p_73164_6_ - 4) / 3.0F);
/* 313 */             d4 = d4 * (1.0D - d9) + -10.0D * d9;
/*     */           } 
/*     */           
/* 316 */           if (k < d3) {
/* 317 */             double d10 = (d3 - k) / 4.0D;
/* 318 */             d10 = MathHelper.clamp_double(d10, 0.0D, 1.0D);
/* 319 */             d4 = d4 * (1.0D - d10) + -10.0D * d10;
/*     */           } 
/*     */           
/* 322 */           p_73164_1_[i] = d4;
/* 323 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 328 */     return p_73164_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 335 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 342 */     BlockFalling.fallInstantly = true;
/* 343 */     BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
/* 344 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/* 345 */     this.genNetherBridge.generateStructure(this.worldObj, this.hellRNG, chunkcoordintpair);
/*     */     
/* 347 */     for (int i = 0; i < 8; i++) {
/* 348 */       this.field_177472_y.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 351 */     for (int j = 0; j < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1; j++) {
/* 352 */       this.field_177470_t.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 355 */     for (int k = 0; k < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1); k++) {
/* 356 */       this.field_177469_u.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(120) + 4, this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 359 */     for (int l = 0; l < 10; l++) {
/* 360 */       this.field_177468_v.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 363 */     if (this.hellRNG.nextBoolean()) {
/* 364 */       this.field_177471_z.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 367 */     if (this.hellRNG.nextBoolean()) {
/* 368 */       this.field_177465_A.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16) + 8, this.hellRNG.nextInt(128), this.hellRNG.nextInt(16) + 8));
/*     */     }
/*     */     
/* 371 */     for (int i1 = 0; i1 < 16; i1++) {
/* 372 */       this.field_177467_w.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
/*     */     }
/*     */     
/* 375 */     for (int j1 = 0; j1 < 16; j1++) {
/* 376 */       this.field_177473_x.generate(this.worldObj, this.hellRNG, blockpos.add(this.hellRNG.nextInt(16), this.hellRNG.nextInt(108) + 10, this.hellRNG.nextInt(16)));
/*     */     }
/*     */     
/* 379 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 391 */     return true;
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
/* 405 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 412 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 419 */     return "HellRandomLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 423 */     if (creatureType == EnumCreatureType.MONSTER) {
/* 424 */       if (this.genNetherBridge.func_175795_b(pos)) {
/* 425 */         return this.genNetherBridge.getSpawnList();
/*     */       }
/*     */       
/* 428 */       if (this.genNetherBridge.isPositionInStructure(this.worldObj, pos) && this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.nether_brick) {
/* 429 */         return this.genNetherBridge.getSpawnList();
/*     */       }
/*     */     } 
/*     */     
/* 433 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
/* 434 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 438 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 442 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 446 */     this.genNetherBridge.generate(this, this.worldObj, x, z, null);
/*     */   }
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 450 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\ChunkProviderHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */