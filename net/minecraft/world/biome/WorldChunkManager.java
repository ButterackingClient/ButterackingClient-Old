/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.gen.layer.GenLayer;
/*     */ import net.minecraft.world.gen.layer.IntCache;
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
/*     */ public class WorldChunkManager
/*     */ {
/*     */   private GenLayer genBiomes;
/*     */   private GenLayer biomeIndexLayer;
/*     */   private BiomeCache biomeCache;
/*     */   private List<BiomeGenBase> biomesToSpawnIn;
/*     */   private String generatorOptions;
/*     */   
/*     */   protected WorldChunkManager() {
/*  33 */     this.biomeCache = new BiomeCache(this);
/*  34 */     this.generatorOptions = "";
/*  35 */     this.biomesToSpawnIn = Lists.newArrayList();
/*  36 */     this.biomesToSpawnIn.add(BiomeGenBase.forest);
/*  37 */     this.biomesToSpawnIn.add(BiomeGenBase.plains);
/*  38 */     this.biomesToSpawnIn.add(BiomeGenBase.taiga);
/*  39 */     this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
/*  40 */     this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
/*  41 */     this.biomesToSpawnIn.add(BiomeGenBase.jungle);
/*  42 */     this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
/*     */   }
/*     */   
/*     */   public WorldChunkManager(long seed, WorldType worldTypeIn, String options) {
/*  46 */     this();
/*  47 */     this.generatorOptions = options;
/*  48 */     GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, worldTypeIn, options);
/*  49 */     this.genBiomes = agenlayer[0];
/*  50 */     this.biomeIndexLayer = agenlayer[1];
/*     */   }
/*     */   
/*     */   public WorldChunkManager(World worldIn) {
/*  54 */     this(worldIn.getSeed(), worldIn.getWorldInfo().getTerrainType(), worldIn.getWorldInfo().getGeneratorOptions());
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase> getBiomesToSpawnIn() {
/*  58 */     return this.biomesToSpawnIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase getBiomeGenerator(BlockPos pos) {
/*  65 */     return getBiomeGenerator(pos, null);
/*     */   }
/*     */   
/*     */   public BiomeGenBase getBiomeGenerator(BlockPos pos, BiomeGenBase biomeGenBaseIn) {
/*  69 */     return this.biomeCache.func_180284_a(pos.getX(), pos.getZ(), biomeGenBaseIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getRainfall(float[] listToReuse, int x, int z, int width, int length) {
/*  76 */     IntCache.resetIntCache();
/*     */     
/*  78 */     if (listToReuse == null || listToReuse.length < width * length) {
/*  79 */       listToReuse = new float[width * length];
/*     */     }
/*     */     
/*  82 */     int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
/*     */     
/*  84 */     for (int i = 0; i < width * length; i++) {
/*     */       try {
/*  86 */         float f = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad).getIntRainfall() / 65536.0F;
/*     */         
/*  88 */         if (f > 1.0F) {
/*  89 */           f = 1.0F;
/*     */         }
/*     */         
/*  92 */         listToReuse[i] = f;
/*  93 */       } catch (Throwable throwable) {
/*  94 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/*  95 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("DownfallBlock");
/*  96 */         crashreportcategory.addCrashSection("biome id", Integer.valueOf(i));
/*  97 */         crashreportcategory.addCrashSection("downfalls[] size", Integer.valueOf(listToReuse.length));
/*  98 */         crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/*  99 */         crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 100 */         crashreportcategory.addCrashSection("w", Integer.valueOf(width));
/* 101 */         crashreportcategory.addCrashSection("h", Integer.valueOf(length));
/* 102 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return listToReuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTemperatureAtHeight(float p_76939_1_, int p_76939_2_) {
/* 113 */     return p_76939_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int height) {
/* 120 */     IntCache.resetIntCache();
/*     */     
/* 122 */     if (biomes == null || biomes.length < width * height) {
/* 123 */       biomes = new BiomeGenBase[width * height];
/*     */     }
/*     */     
/* 126 */     int[] aint = this.genBiomes.getInts(x, z, width, height);
/*     */     
/*     */     try {
/* 129 */       for (int i = 0; i < width * height; i++) {
/* 130 */         biomes[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
/*     */       }
/*     */       
/* 133 */       return biomes;
/* 134 */     } catch (Throwable throwable) {
/* 135 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 136 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
/* 137 */       crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
/* 138 */       crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/* 139 */       crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 140 */       crashreportcategory.addCrashSection("w", Integer.valueOf(width));
/* 141 */       crashreportcategory.addCrashSection("h", Integer.valueOf(height));
/* 142 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] oldBiomeList, int x, int z, int width, int depth) {
/* 151 */     return getBiomeGenAt(oldBiomeList, x, z, width, depth, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
/* 159 */     IntCache.resetIntCache();
/*     */     
/* 161 */     if (listToReuse == null || listToReuse.length < width * length) {
/* 162 */       listToReuse = new BiomeGenBase[width * length];
/*     */     }
/*     */     
/* 165 */     if (cacheFlag && width == 16 && length == 16 && (x & 0xF) == 0 && (z & 0xF) == 0) {
/* 166 */       BiomeGenBase[] abiomegenbase = this.biomeCache.getCachedBiomes(x, z);
/* 167 */       System.arraycopy(abiomegenbase, 0, listToReuse, 0, width * length);
/* 168 */       return listToReuse;
/*     */     } 
/* 170 */     int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
/*     */     
/* 172 */     for (int i = 0; i < width * length; i++) {
/* 173 */       listToReuse[i] = BiomeGenBase.getBiomeFromBiomeList(aint[i], BiomeGenBase.field_180279_ad);
/*     */     }
/*     */     
/* 176 */     return listToReuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List<BiomeGenBase> p_76940_4_) {
/* 184 */     IntCache.resetIntCache();
/* 185 */     int i = p_76940_1_ - p_76940_3_ >> 2;
/* 186 */     int j = p_76940_2_ - p_76940_3_ >> 2;
/* 187 */     int k = p_76940_1_ + p_76940_3_ >> 2;
/* 188 */     int l = p_76940_2_ + p_76940_3_ >> 2;
/* 189 */     int i1 = k - i + 1;
/* 190 */     int j1 = l - j + 1;
/* 191 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/*     */     
/*     */     try {
/* 194 */       for (int k1 = 0; k1 < i1 * j1; k1++) {
/* 195 */         BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k1]);
/*     */         
/* 197 */         if (!p_76940_4_.contains(biomegenbase)) {
/* 198 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 202 */       return true;
/* 203 */     } catch (Throwable throwable) {
/* 204 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 205 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
/* 206 */       crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
/* 207 */       crashreportcategory.addCrashSection("x", Integer.valueOf(p_76940_1_));
/* 208 */       crashreportcategory.addCrashSection("z", Integer.valueOf(p_76940_2_));
/* 209 */       crashreportcategory.addCrashSection("radius", Integer.valueOf(p_76940_3_));
/* 210 */       crashreportcategory.addCrashSection("allowed", p_76940_4_);
/* 211 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */   
/*     */   public BlockPos findBiomePosition(int x, int z, int range, List<BiomeGenBase> biomes, Random random) {
/* 216 */     IntCache.resetIntCache();
/* 217 */     int i = x - range >> 2;
/* 218 */     int j = z - range >> 2;
/* 219 */     int k = x + range >> 2;
/* 220 */     int l = z + range >> 2;
/* 221 */     int i1 = k - i + 1;
/* 222 */     int j1 = l - j + 1;
/* 223 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/* 224 */     BlockPos blockpos = null;
/* 225 */     int k1 = 0;
/*     */     
/* 227 */     for (int l1 = 0; l1 < i1 * j1; l1++) {
/* 228 */       int i2 = i + l1 % i1 << 2;
/* 229 */       int j2 = j + l1 / i1 << 2;
/* 230 */       BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[l1]);
/*     */       
/* 232 */       if (biomes.contains(biomegenbase) && (blockpos == null || random.nextInt(k1 + 1) == 0)) {
/* 233 */         blockpos = new BlockPos(i2, 0, j2);
/* 234 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     return blockpos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupCache() {
/* 245 */     this.biomeCache.cleanupCache();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\WorldChunkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */