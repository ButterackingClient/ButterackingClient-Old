/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ 
/*     */ public class BiomeGenMesa extends BiomeGenBase {
/*     */   private IBlockState[] field_150621_aC;
/*     */   private long field_150622_aD;
/*     */   private NoiseGeneratorPerlin field_150623_aE;
/*     */   private NoiseGeneratorPerlin field_150624_aF;
/*     */   private NoiseGeneratorPerlin field_150625_aG;
/*     */   private boolean field_150626_aH;
/*     */   private boolean field_150620_aI;
/*     */   
/*     */   public BiomeGenMesa(int id, boolean p_i45380_2_, boolean p_i45380_3_) {
/*  29 */     super(id);
/*  30 */     this.field_150626_aH = p_i45380_2_;
/*  31 */     this.field_150620_aI = p_i45380_3_;
/*  32 */     setDisableRain();
/*  33 */     setTemperatureRainfall(2.0F, 0.0F);
/*  34 */     this.spawnableCreatureList.clear();
/*  35 */     this.topBlock = Blocks.sand.getDefaultState().withProperty((IProperty)BlockSand.VARIANT, (Comparable)BlockSand.EnumType.RED_SAND);
/*  36 */     this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
/*  37 */     this.theBiomeDecorator.treesPerChunk = -999;
/*  38 */     this.theBiomeDecorator.deadBushPerChunk = 20;
/*  39 */     this.theBiomeDecorator.reedsPerChunk = 3;
/*  40 */     this.theBiomeDecorator.cactiPerChunk = 5;
/*  41 */     this.theBiomeDecorator.flowersPerChunk = 0;
/*  42 */     this.spawnableCreatureList.clear();
/*     */     
/*  44 */     if (p_i45380_3_) {
/*  45 */       this.theBiomeDecorator.treesPerChunk = 5;
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  50 */     return (WorldGenAbstractTree)this.worldGeneratorTrees;
/*     */   }
/*     */   
/*     */   public int getFoliageColorAtPos(BlockPos pos) {
/*  54 */     return 10387789;
/*     */   }
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/*  58 */     return 9470285;
/*     */   }
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  62 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/*  66 */     if (this.field_150621_aC == null || this.field_150622_aD != worldIn.getSeed()) {
/*  67 */       func_150619_a(worldIn.getSeed());
/*     */     }
/*     */     
/*  70 */     if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != worldIn.getSeed()) {
/*  71 */       Random random = new Random(this.field_150622_aD);
/*  72 */       this.field_150623_aE = new NoiseGeneratorPerlin(random, 4);
/*  73 */       this.field_150624_aF = new NoiseGeneratorPerlin(random, 1);
/*     */     } 
/*     */     
/*  76 */     this.field_150622_aD = worldIn.getSeed();
/*  77 */     double d4 = 0.0D;
/*     */     
/*  79 */     if (this.field_150626_aH) {
/*  80 */       int i = (x & 0xFFFFFFF0) + (z & 0xF);
/*  81 */       int j = (z & 0xFFFFFFF0) + (x & 0xF);
/*  82 */       double d0 = Math.min(Math.abs(noiseVal), this.field_150623_aE.func_151601_a(i * 0.25D, j * 0.25D));
/*     */       
/*  84 */       if (d0 > 0.0D) {
/*  85 */         double d1 = 0.001953125D;
/*  86 */         double d2 = Math.abs(this.field_150624_aF.func_151601_a(i * d1, j * d1));
/*  87 */         d4 = d0 * d0 * 2.5D;
/*  88 */         double d3 = Math.ceil(d2 * 50.0D) + 14.0D;
/*     */         
/*  90 */         if (d4 > d3) {
/*  91 */           d4 = d3;
/*     */         }
/*     */         
/*  94 */         d4 += 64.0D;
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     int j1 = x & 0xF;
/*  99 */     int k1 = z & 0xF;
/* 100 */     int l1 = worldIn.getSeaLevel();
/* 101 */     IBlockState iblockstate = Blocks.stained_hardened_clay.getDefaultState();
/* 102 */     IBlockState iblockstate3 = this.fillerBlock;
/* 103 */     int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
/* 104 */     boolean flag = (Math.cos(noiseVal / 3.0D * Math.PI) > 0.0D);
/* 105 */     int l = -1;
/* 106 */     boolean flag1 = false;
/*     */     
/* 108 */     for (int i1 = 255; i1 >= 0; i1--) {
/* 109 */       if (chunkPrimerIn.getBlockState(k1, i1, j1).getBlock().getMaterial() == Material.air && i1 < (int)d4) {
/* 110 */         chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.stone.getDefaultState());
/*     */       }
/*     */       
/* 113 */       if (i1 <= rand.nextInt(5)) {
/* 114 */         chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.bedrock.getDefaultState());
/*     */       } else {
/* 116 */         IBlockState iblockstate1 = chunkPrimerIn.getBlockState(k1, i1, j1);
/*     */         
/* 118 */         if (iblockstate1.getBlock().getMaterial() == Material.air) {
/* 119 */           l = -1;
/* 120 */         } else if (iblockstate1.getBlock() == Blocks.stone) {
/* 121 */           if (l == -1) {
/* 122 */             flag1 = false;
/*     */             
/* 124 */             if (k <= 0) {
/* 125 */               iblockstate = null;
/* 126 */               iblockstate3 = Blocks.stone.getDefaultState();
/* 127 */             } else if (i1 >= l1 - 4 && i1 <= l1 + 1) {
/* 128 */               iblockstate = Blocks.stained_hardened_clay.getDefaultState();
/* 129 */               iblockstate3 = this.fillerBlock;
/*     */             } 
/*     */             
/* 132 */             if (i1 < l1 && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air)) {
/* 133 */               iblockstate = Blocks.water.getDefaultState();
/*     */             }
/*     */             
/* 136 */             l = k + Math.max(0, i1 - l1);
/*     */             
/* 138 */             if (i1 < l1 - 1) {
/* 139 */               chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate3);
/*     */               
/* 141 */               if (iblockstate3.getBlock() == Blocks.stained_hardened_clay) {
/* 142 */                 chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate3.getBlock().getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE));
/*     */               }
/* 144 */             } else if (this.field_150620_aI && i1 > 86 + k * 2) {
/* 145 */               if (flag) {
/* 146 */                 chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT));
/*     */               } else {
/* 148 */                 chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.grass.getDefaultState());
/*     */               } 
/* 150 */             } else if (i1 <= l1 + 3 + k) {
/* 151 */               chunkPrimerIn.setBlockState(k1, i1, j1, this.topBlock);
/* 152 */               flag1 = true;
/*     */             } else {
/*     */               IBlockState iblockstate4;
/*     */               
/* 156 */               if (i1 >= 64 && i1 <= 127) {
/* 157 */                 if (flag) {
/* 158 */                   iblockstate4 = Blocks.hardened_clay.getDefaultState();
/*     */                 } else {
/* 160 */                   iblockstate4 = func_180629_a(x, i1, z);
/*     */                 } 
/*     */               } else {
/* 163 */                 iblockstate4 = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE);
/*     */               } 
/*     */               
/* 166 */               chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate4);
/*     */             } 
/* 168 */           } else if (l > 0) {
/* 169 */             l--;
/*     */             
/* 171 */             if (flag1) {
/* 172 */               chunkPrimerIn.setBlockState(k1, i1, j1, Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE));
/*     */             } else {
/* 174 */               IBlockState iblockstate2 = func_180629_a(x, i1, z);
/* 175 */               chunkPrimerIn.setBlockState(k1, i1, j1, iblockstate2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void func_150619_a(long p_150619_1_) {
/* 184 */     this.field_150621_aC = new IBlockState[64];
/* 185 */     Arrays.fill((Object[])this.field_150621_aC, Blocks.hardened_clay.getDefaultState());
/* 186 */     Random random = new Random(p_150619_1_);
/* 187 */     this.field_150625_aG = new NoiseGeneratorPerlin(random, 1);
/*     */     
/* 189 */     for (int l1 = 0; l1 < 64; l1++) {
/* 190 */       l1 += random.nextInt(5) + 1;
/*     */       
/* 192 */       if (l1 < 64) {
/* 193 */         this.field_150621_aC[l1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE);
/*     */       }
/*     */     } 
/*     */     
/* 197 */     int i2 = random.nextInt(4) + 2;
/*     */     
/* 199 */     for (int i = 0; i < i2; i++) {
/* 200 */       int j = random.nextInt(3) + 1;
/* 201 */       int k = random.nextInt(64);
/*     */       
/* 203 */       for (int l = 0; k + l < 64 && l < j; l++) {
/* 204 */         this.field_150621_aC[k + l] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.YELLOW);
/*     */       }
/*     */     } 
/*     */     
/* 208 */     int j2 = random.nextInt(4) + 2;
/*     */     
/* 210 */     for (int k2 = 0; k2 < j2; k2++) {
/* 211 */       int i3 = random.nextInt(3) + 2;
/* 212 */       int l3 = random.nextInt(64);
/*     */       
/* 214 */       for (int i1 = 0; l3 + i1 < 64 && i1 < i3; i1++) {
/* 215 */         this.field_150621_aC[l3 + i1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.BROWN);
/*     */       }
/*     */     } 
/*     */     
/* 219 */     int l2 = random.nextInt(4) + 2;
/*     */     
/* 221 */     for (int j3 = 0; j3 < l2; j3++) {
/* 222 */       int i4 = random.nextInt(3) + 1;
/* 223 */       int k4 = random.nextInt(64);
/*     */       
/* 225 */       for (int j1 = 0; k4 + j1 < 64 && j1 < i4; j1++) {
/* 226 */         this.field_150621_aC[k4 + j1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.RED);
/*     */       }
/*     */     } 
/*     */     
/* 230 */     int k3 = random.nextInt(3) + 3;
/* 231 */     int j4 = 0;
/*     */     
/* 233 */     for (int l4 = 0; l4 < k3; l4++) {
/* 234 */       int i5 = 1;
/* 235 */       j4 += random.nextInt(16) + 4;
/*     */       
/* 237 */       for (int k1 = 0; j4 + k1 < 64 && k1 < i5; k1++) {
/* 238 */         this.field_150621_aC[j4 + k1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.WHITE);
/*     */         
/* 240 */         if (j4 + k1 > 1 && random.nextBoolean()) {
/* 241 */           this.field_150621_aC[j4 + k1 - 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.SILVER);
/*     */         }
/*     */         
/* 244 */         if (j4 + k1 < 63 && random.nextBoolean()) {
/* 245 */           this.field_150621_aC[j4 + k1 + 1] = Blocks.stained_hardened_clay.getDefaultState().withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.SILVER);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private IBlockState func_180629_a(int p_180629_1_, int p_180629_2_, int p_180629_3_) {
/* 252 */     int i = (int)Math.round(this.field_150625_aG.func_151601_a(p_180629_1_ * 1.0D / 512.0D, p_180629_1_ * 1.0D / 512.0D) * 2.0D);
/* 253 */     return this.field_150621_aC[(p_180629_2_ + i + 64) % 64];
/*     */   }
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 257 */     boolean flag = (this.biomeID == BiomeGenBase.mesa.biomeID);
/* 258 */     BiomeGenMesa biomegenmesa = new BiomeGenMesa(p_180277_1_, flag, this.field_150620_aI);
/*     */     
/* 260 */     if (!flag) {
/* 261 */       biomegenmesa.setHeight(height_LowHills);
/* 262 */       biomegenmesa.setBiomeName(String.valueOf(this.biomeName) + " M");
/*     */     } else {
/* 264 */       biomegenmesa.setBiomeName(String.valueOf(this.biomeName) + " (Bryce)");
/*     */     } 
/*     */     
/* 267 */     biomegenmesa.func_150557_a(this.color, true);
/* 268 */     return biomegenmesa;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenMesa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */