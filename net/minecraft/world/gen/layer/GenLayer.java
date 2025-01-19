/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.gen.ChunkProviderSettings;
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
/*     */ public abstract class GenLayer
/*     */ {
/*     */   private long worldGenSeed;
/*     */   protected GenLayer parent;
/*     */   private long chunkSeed;
/*     */   protected long baseSeed;
/*     */   
/*     */   public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType p_180781_2_, String p_180781_3_) {
/*  35 */     GenLayer genlayer = new GenLayerIsland(1L);
/*  36 */     genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
/*  37 */     GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
/*  38 */     GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
/*  39 */     GenLayerAddIsland genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
/*  40 */     genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
/*  41 */     genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
/*  42 */     GenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
/*  43 */     GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
/*  44 */     GenLayerAddIsland genlayeraddisland2 = new GenLayerAddIsland(3L, genlayeraddsnow);
/*  45 */     GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland2, GenLayerEdge.Mode.COOL_WARM);
/*  46 */     genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
/*  47 */     genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
/*  48 */     GenLayerZoom genlayerzoom1 = new GenLayerZoom(2002L, genlayeredge);
/*  49 */     genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
/*  50 */     GenLayerAddIsland genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
/*  51 */     GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland3);
/*  52 */     GenLayerDeepOcean genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);
/*  53 */     GenLayer genlayer4 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
/*  54 */     ChunkProviderSettings chunkprovidersettings = null;
/*  55 */     int i = 4;
/*  56 */     int j = i;
/*     */     
/*  58 */     if (p_180781_2_ == WorldType.CUSTOMIZED && p_180781_3_.length() > 0) {
/*  59 */       chunkprovidersettings = ChunkProviderSettings.Factory.jsonToFactory(p_180781_3_).func_177864_b();
/*  60 */       i = chunkprovidersettings.biomeSize;
/*  61 */       j = chunkprovidersettings.riverSize;
/*     */     } 
/*     */     
/*  64 */     if (p_180781_2_ == WorldType.LARGE_BIOMES) {
/*  65 */       i = 6;
/*     */     }
/*     */     
/*  68 */     GenLayer lvt_8_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
/*  69 */     GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, lvt_8_1_);
/*  70 */     GenLayerBiome lvt_9_1_ = new GenLayerBiome(200L, genlayer4, p_180781_2_, p_180781_3_);
/*  71 */     GenLayer genlayer6 = GenLayerZoom.magnify(1000L, lvt_9_1_, 2);
/*  72 */     GenLayerBiomeEdge genlayerbiomeedge = new GenLayerBiomeEdge(1000L, genlayer6);
/*  73 */     GenLayer lvt_10_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
/*  74 */     GenLayer genlayerhills = new GenLayerHills(1000L, genlayerbiomeedge, lvt_10_1_);
/*  75 */     GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
/*  76 */     genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, j);
/*  77 */     GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer5);
/*  78 */     GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
/*  79 */     genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);
/*     */     
/*  81 */     for (int k = 0; k < i; k++) {
/*  82 */       genlayerhills = new GenLayerZoom((1000 + k), genlayerhills);
/*     */       
/*  84 */       if (k == 0) {
/*  85 */         genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
/*     */       }
/*     */       
/*  88 */       if (k == 1 || i == 1) {
/*  89 */         genlayerhills = new GenLayerShore(1000L, genlayerhills);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
/*  94 */     GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
/*  95 */     GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
/*  96 */     genlayerrivermix.initWorldGenSeed(seed);
/*  97 */     genlayer3.initWorldGenSeed(seed);
/*  98 */     return new GenLayer[] { genlayerrivermix, genlayer3, genlayerrivermix };
/*     */   }
/*     */   
/*     */   public GenLayer(long p_i2125_1_) {
/* 102 */     this.baseSeed = p_i2125_1_;
/* 103 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 104 */     this.baseSeed += p_i2125_1_;
/* 105 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 106 */     this.baseSeed += p_i2125_1_;
/* 107 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 108 */     this.baseSeed += p_i2125_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWorldGenSeed(long seed) {
/* 116 */     this.worldGenSeed = seed;
/*     */     
/* 118 */     if (this.parent != null) {
/* 119 */       this.parent.initWorldGenSeed(seed);
/*     */     }
/*     */     
/* 122 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 123 */     this.worldGenSeed += this.baseSeed;
/* 124 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 125 */     this.worldGenSeed += this.baseSeed;
/* 126 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 127 */     this.worldGenSeed += this.baseSeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initChunkSeed(long p_75903_1_, long p_75903_3_) {
/* 134 */     this.chunkSeed = this.worldGenSeed;
/* 135 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 136 */     this.chunkSeed += p_75903_1_;
/* 137 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 138 */     this.chunkSeed += p_75903_3_;
/* 139 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 140 */     this.chunkSeed += p_75903_1_;
/* 141 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 142 */     this.chunkSeed += p_75903_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int nextInt(int p_75902_1_) {
/* 149 */     int i = (int)((this.chunkSeed >> 24L) % p_75902_1_);
/*     */     
/* 151 */     if (i < 0) {
/* 152 */       i += p_75902_1_;
/*     */     }
/*     */     
/* 155 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 156 */     this.chunkSeed += this.worldGenSeed;
/* 157 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int[] getInts(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean biomesEqualOrMesaPlateau(int biomeIDA, int biomeIDB) {
/* 167 */     if (biomeIDA == biomeIDB)
/* 168 */       return true; 
/* 169 */     if (biomeIDA != BiomeGenBase.mesaPlateau_F.biomeID && biomeIDA != BiomeGenBase.mesaPlateau.biomeID) {
/* 170 */       final BiomeGenBase biomegenbase = BiomeGenBase.getBiome(biomeIDA);
/* 171 */       final BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(biomeIDB);
/*     */       
/*     */       try {
/* 174 */         return (biomegenbase != null && biomegenbase1 != null) ? biomegenbase.isEqualTo(biomegenbase1) : false;
/* 175 */       } catch (Throwable throwable) {
/* 176 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Comparing biomes");
/* 177 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Biomes being compared");
/* 178 */         crashreportcategory.addCrashSection("Biome A ID", Integer.valueOf(biomeIDA));
/* 179 */         crashreportcategory.addCrashSection("Biome B ID", Integer.valueOf(biomeIDB));
/* 180 */         crashreportcategory.addCrashSectionCallable("Biome A", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 182 */                 return String.valueOf(biomegenbase);
/*     */               }
/*     */             });
/* 185 */         crashreportcategory.addCrashSectionCallable("Biome B", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 187 */                 return String.valueOf(biomegenbase1);
/*     */               }
/*     */             });
/* 190 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/* 193 */     return !(biomeIDB != BiomeGenBase.mesaPlateau_F.biomeID && biomeIDB != BiomeGenBase.mesaPlateau.biomeID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isBiomeOceanic(int p_151618_0_) {
/* 201 */     return !(p_151618_0_ != BiomeGenBase.ocean.biomeID && p_151618_0_ != BiomeGenBase.deepOcean.biomeID && p_151618_0_ != BiomeGenBase.frozenOcean.biomeID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int selectRandom(int... p_151619_1_) {
/* 208 */     return p_151619_1_[nextInt(p_151619_1_.length)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_) {
/* 215 */     return (p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_) ? p_151617_2_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_) ? p_151617_1_ : ((p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_) ? p_151617_2_ : ((p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_) ? p_151617_2_ : ((p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_) ? p_151617_3_ : selectRandom(new int[] { p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_ }))))))))));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */