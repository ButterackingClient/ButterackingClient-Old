/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/*     */ import net.minecraft.world.gen.feature.WorldGenSwamp;
/*     */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public abstract class BiomeGenBase
/*     */ {
/*  54 */   private static final Logger logger = LogManager.getLogger();
/*  55 */   protected static final Height height_Default = new Height(0.1F, 0.2F);
/*  56 */   protected static final Height height_ShallowWaters = new Height(-0.5F, 0.0F);
/*  57 */   protected static final Height height_Oceans = new Height(-1.0F, 0.1F);
/*  58 */   protected static final Height height_DeepOceans = new Height(-1.8F, 0.1F);
/*  59 */   protected static final Height height_LowPlains = new Height(0.125F, 0.05F);
/*  60 */   protected static final Height height_MidPlains = new Height(0.2F, 0.2F);
/*  61 */   protected static final Height height_LowHills = new Height(0.45F, 0.3F);
/*  62 */   protected static final Height height_HighPlateaus = new Height(1.5F, 0.025F);
/*  63 */   protected static final Height height_MidHills = new Height(1.0F, 0.5F);
/*  64 */   protected static final Height height_Shores = new Height(0.0F, 0.025F);
/*  65 */   protected static final Height height_RockyWaters = new Height(0.1F, 0.8F);
/*  66 */   protected static final Height height_LowIslands = new Height(0.2F, 0.3F);
/*  67 */   protected static final Height height_PartiallySubmerged = new Height(-0.2F, 0.1F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final BiomeGenBase[] biomeList = new BiomeGenBase[256];
/*  73 */   public static final Set<BiomeGenBase> explorationBiomesList = Sets.newHashSet();
/*  74 */   public static final Map<String, BiomeGenBase> BIOME_ID_MAP = Maps.newHashMap();
/*  75 */   public static final BiomeGenBase ocean = (new BiomeGenOcean(0)).setColor(112).setBiomeName("Ocean").setHeight(height_Oceans);
/*  76 */   public static final BiomeGenBase plains = (new BiomeGenPlains(1)).setColor(9286496).setBiomeName("Plains");
/*  77 */   public static final BiomeGenBase desert = (new BiomeGenDesert(2)).setColor(16421912).setBiomeName("Desert").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowPlains);
/*  78 */   public static final BiomeGenBase extremeHills = (new BiomeGenHills(3, false)).setColor(6316128).setBiomeName("Extreme Hills").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
/*  79 */   public static final BiomeGenBase forest = (new BiomeGenForest(4, 0)).setColor(353825).setBiomeName("Forest");
/*  80 */   public static final BiomeGenBase taiga = (new BiomeGenTaiga(5, 0)).setColor(747097).setBiomeName("Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_MidPlains);
/*  81 */   public static final BiomeGenBase swampland = (new BiomeGenSwamp(6)).setColor(522674).setBiomeName("Swampland").setFillerBlockMetadata(9154376).setHeight(height_PartiallySubmerged).setTemperatureRainfall(0.8F, 0.9F);
/*  82 */   public static final BiomeGenBase river = (new BiomeGenRiver(7)).setColor(255).setBiomeName("River").setHeight(height_ShallowWaters);
/*  83 */   public static final BiomeGenBase hell = (new BiomeGenHell(8)).setColor(16711680).setBiomeName("Hell").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final BiomeGenBase sky = (new BiomeGenEnd(9)).setColor(8421631).setBiomeName("The End").setDisableRain();
/*  89 */   public static final BiomeGenBase frozenOcean = (new BiomeGenOcean(10)).setColor(9474208).setBiomeName("FrozenOcean").setEnableSnow().setHeight(height_Oceans).setTemperatureRainfall(0.0F, 0.5F);
/*  90 */   public static final BiomeGenBase frozenRiver = (new BiomeGenRiver(11)).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setHeight(height_ShallowWaters).setTemperatureRainfall(0.0F, 0.5F);
/*  91 */   public static final BiomeGenBase icePlains = (new BiomeGenSnow(12, false)).setColor(16777215).setBiomeName("Ice Plains").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F).setHeight(height_LowPlains);
/*  92 */   public static final BiomeGenBase iceMountains = (new BiomeGenSnow(13, false)).setColor(10526880).setBiomeName("Ice Mountains").setEnableSnow().setHeight(height_LowHills).setTemperatureRainfall(0.0F, 0.5F);
/*  93 */   public static final BiomeGenBase mushroomIsland = (new BiomeGenMushroomIsland(14)).setColor(16711935).setBiomeName("MushroomIsland").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_LowIslands);
/*  94 */   public static final BiomeGenBase mushroomIslandShore = (new BiomeGenMushroomIsland(15)).setColor(10486015).setBiomeName("MushroomIslandShore").setTemperatureRainfall(0.9F, 1.0F).setHeight(height_Shores);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final BiomeGenBase beach = (new BiomeGenBeach(16)).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8F, 0.4F).setHeight(height_Shores);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final BiomeGenBase desertHills = (new BiomeGenDesert(17)).setColor(13786898).setBiomeName("DesertHills").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(height_LowHills);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final BiomeGenBase forestHills = (new BiomeGenForest(18, 0)).setColor(2250012).setBiomeName("ForestHills").setHeight(height_LowHills);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final BiomeGenBase taigaHills = (new BiomeGenTaiga(19, 0)).setColor(1456435).setBiomeName("TaigaHills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25F, 0.8F).setHeight(height_LowHills);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static final BiomeGenBase extremeHillsEdge = (new BiomeGenHills(20, true)).setColor(7501978).setBiomeName("Extreme Hills Edge").setHeight(height_MidHills.attenuate()).setTemperatureRainfall(0.2F, 0.3F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final BiomeGenBase jungle = (new BiomeGenJungle(21, false)).setColor(5470985).setBiomeName("Jungle").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F);
/* 125 */   public static final BiomeGenBase jungleHills = (new BiomeGenJungle(22, false)).setColor(2900485).setBiomeName("JungleHills").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.9F).setHeight(height_LowHills);
/* 126 */   public static final BiomeGenBase jungleEdge = (new BiomeGenJungle(23, true)).setColor(6458135).setBiomeName("JungleEdge").setFillerBlockMetadata(5470985).setTemperatureRainfall(0.95F, 0.8F);
/* 127 */   public static final BiomeGenBase deepOcean = (new BiomeGenOcean(24)).setColor(48).setBiomeName("Deep Ocean").setHeight(height_DeepOceans);
/* 128 */   public static final BiomeGenBase stoneBeach = (new BiomeGenStoneBeach(25)).setColor(10658436).setBiomeName("Stone Beach").setTemperatureRainfall(0.2F, 0.3F).setHeight(height_RockyWaters);
/* 129 */   public static final BiomeGenBase coldBeach = (new BiomeGenBeach(26)).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05F, 0.3F).setHeight(height_Shores).setEnableSnow();
/* 130 */   public static final BiomeGenBase birchForest = (new BiomeGenForest(27, 2)).setBiomeName("Birch Forest").setColor(3175492);
/* 131 */   public static final BiomeGenBase birchForestHills = (new BiomeGenForest(28, 2)).setBiomeName("Birch Forest Hills").setColor(2055986).setHeight(height_LowHills);
/* 132 */   public static final BiomeGenBase roofedForest = (new BiomeGenForest(29, 3)).setColor(4215066).setBiomeName("Roofed Forest");
/* 133 */   public static final BiomeGenBase coldTaiga = (new BiomeGenTaiga(30, 0)).setColor(3233098).setBiomeName("Cold Taiga").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_MidPlains).func_150563_c(16777215);
/* 134 */   public static final BiomeGenBase coldTaigaHills = (new BiomeGenTaiga(31, 0)).setColor(2375478).setBiomeName("Cold Taiga Hills").setFillerBlockMetadata(5159473).setEnableSnow().setTemperatureRainfall(-0.5F, 0.4F).setHeight(height_LowHills).func_150563_c(16777215);
/* 135 */   public static final BiomeGenBase megaTaiga = (new BiomeGenTaiga(32, 1)).setColor(5858897).setBiomeName("Mega Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_MidPlains);
/* 136 */   public static final BiomeGenBase megaTaigaHills = (new BiomeGenTaiga(33, 1)).setColor(4542270).setBiomeName("Mega Taiga Hills").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.3F, 0.8F).setHeight(height_LowHills);
/* 137 */   public static final BiomeGenBase extremeHillsPlus = (new BiomeGenHills(34, true)).setColor(5271632).setBiomeName("Extreme Hills+").setHeight(height_MidHills).setTemperatureRainfall(0.2F, 0.3F);
/* 138 */   public static final BiomeGenBase savanna = (new BiomeGenSavanna(35)).setColor(12431967).setBiomeName("Savanna").setTemperatureRainfall(1.2F, 0.0F).setDisableRain().setHeight(height_LowPlains);
/* 139 */   public static final BiomeGenBase savannaPlateau = (new BiomeGenSavanna(36)).setColor(10984804).setBiomeName("Savanna Plateau").setTemperatureRainfall(1.0F, 0.0F).setDisableRain().setHeight(height_HighPlateaus);
/* 140 */   public static final BiomeGenBase mesa = (new BiomeGenMesa(37, false, false)).setColor(14238997).setBiomeName("Mesa");
/* 141 */   public static final BiomeGenBase mesaPlateau_F = (new BiomeGenMesa(38, false, true)).setColor(11573093).setBiomeName("Mesa Plateau F").setHeight(height_HighPlateaus);
/* 142 */   public static final BiomeGenBase mesaPlateau = (new BiomeGenMesa(39, false, false)).setColor(13274213).setBiomeName("Mesa Plateau").setHeight(height_HighPlateaus);
/* 143 */   public static final BiomeGenBase field_180279_ad = ocean;
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
/* 154 */   public IBlockState topBlock = Blocks.grass.getDefaultState();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public IBlockState fillerBlock = Blocks.dirt.getDefaultState();
/* 160 */   public int fillerBlockMetadata = 5169201;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase(int id) {
/* 227 */     this.minHeight = height_Default.rootHeight;
/* 228 */     this.maxHeight = height_Default.variation;
/* 229 */     this.temperature = 0.5F;
/* 230 */     this.rainfall = 0.5F;
/* 231 */     this.waterColorMultiplier = 16777215;
/* 232 */     this.spawnableMonsterList = Lists.newArrayList();
/* 233 */     this.spawnableCreatureList = Lists.newArrayList();
/* 234 */     this.spawnableWaterCreatureList = Lists.newArrayList();
/* 235 */     this.spawnableCaveCreatureList = Lists.newArrayList();
/* 236 */     this.enableRain = true;
/* 237 */     this.worldGeneratorTrees = new WorldGenTrees(false);
/* 238 */     this.worldGeneratorBigTree = new WorldGenBigTree(false);
/* 239 */     this.worldGeneratorSwamp = new WorldGenSwamp();
/* 240 */     this.biomeID = id;
/* 241 */     biomeList[id] = this;
/* 242 */     this.theBiomeDecorator = createBiomeDecorator();
/* 243 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntitySheep.class, 12, 4, 4));
/* 244 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityRabbit.class, 10, 3, 3));
/* 245 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityPig.class, 10, 4, 4));
/* 246 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityChicken.class, 10, 4, 4));
/* 247 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityCow.class, 8, 4, 4));
/* 248 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySpider.class, 100, 4, 4));
/* 249 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityZombie.class, 100, 4, 4));
/* 250 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySkeleton.class, 100, 4, 4));
/* 251 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityCreeper.class, 100, 4, 4));
/* 252 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySlime.class, 100, 4, 4));
/* 253 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityEnderman.class, 10, 1, 4));
/* 254 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityWitch.class, 5, 1, 1));
/* 255 */     this.spawnableWaterCreatureList.add(new SpawnListEntry((Class)EntitySquid.class, 10, 4, 4));
/* 256 */     this.spawnableCaveCreatureList.add(new SpawnListEntry((Class)EntityBat.class, 10, 8, 8));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeDecorator createBiomeDecorator() {
/* 263 */     return new BiomeDecorator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setTemperatureRainfall(float temperatureIn, float rainfallIn) {
/* 270 */     if (temperatureIn > 0.1F && temperatureIn < 0.2F) {
/* 271 */       throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
/*     */     }
/* 273 */     this.temperature = temperatureIn;
/* 274 */     this.rainfall = rainfallIn;
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final BiomeGenBase setHeight(Height heights) {
/* 280 */     this.minHeight = heights.rootHeight;
/* 281 */     this.maxHeight = heights.variation;
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setDisableRain() {
/* 289 */     this.enableRain = false;
/* 290 */     return this;
/*     */   }
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 294 */     return (rand.nextInt(10) == 0) ? (WorldGenAbstractTree)this.worldGeneratorBigTree : (WorldGenAbstractTree)this.worldGeneratorTrees;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenerator getRandomWorldGenForGrass(Random rand) {
/* 301 */     return (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
/*     */   }
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 305 */     return (rand.nextInt(3) > 0) ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeGenBase setEnableSnow() {
/* 312 */     this.enableSnow = true;
/* 313 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeGenBase setBiomeName(String name) {
/* 317 */     this.biomeName = name;
/* 318 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeGenBase setFillerBlockMetadata(int meta) {
/* 322 */     this.fillerBlockMetadata = meta;
/* 323 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeGenBase setColor(int colorIn) {
/* 327 */     func_150557_a(colorIn, false);
/* 328 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeGenBase func_150563_c(int p_150563_1_) {
/* 332 */     this.field_150609_ah = p_150563_1_;
/* 333 */     return this;
/*     */   }
/*     */   
/*     */   protected BiomeGenBase func_150557_a(int colorIn, boolean p_150557_2_) {
/* 337 */     this.color = colorIn;
/*     */     
/* 339 */     if (p_150557_2_) {
/* 340 */       this.field_150609_ah = (colorIn & 0xFEFEFE) >> 1;
/*     */     } else {
/* 342 */       this.field_150609_ah = colorIn;
/*     */     } 
/*     */     
/* 345 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkyColorByTemp(float p_76731_1_) {
/* 352 */     p_76731_1_ /= 3.0F;
/* 353 */     p_76731_1_ = MathHelper.clamp_float(p_76731_1_, -1.0F, 1.0F);
/* 354 */     return MathHelper.hsvToRGB(0.62222224F - p_76731_1_ * 0.05F, 0.5F + p_76731_1_ * 0.1F, 1.0F);
/*     */   }
/*     */   
/*     */   public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
/* 358 */     switch (creatureType) {
/*     */       case MONSTER:
/* 360 */         return this.spawnableMonsterList;
/*     */       
/*     */       case CREATURE:
/* 363 */         return this.spawnableCreatureList;
/*     */       
/*     */       case WATER_CREATURE:
/* 366 */         return this.spawnableWaterCreatureList;
/*     */       
/*     */       case null:
/* 369 */         return this.spawnableCaveCreatureList;
/*     */     } 
/*     */     
/* 372 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getEnableSnow() {
/* 380 */     return isSnowyBiome();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRain() {
/* 387 */     return isSnowyBiome() ? false : this.enableRain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHighHumidity() {
/* 394 */     return (this.rainfall > 0.85F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpawningChance() {
/* 401 */     return 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getIntRainfall() {
/* 408 */     return (int)(this.rainfall * 65536.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getFloatRainfall() {
/* 415 */     return this.rainfall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getFloatTemperature(BlockPos pos) {
/* 422 */     if (pos.getY() > 64) {
/* 423 */       float f = (float)(temperatureNoise.func_151601_a(pos.getX() * 1.0D / 8.0D, pos.getZ() * 1.0D / 8.0D) * 4.0D);
/* 424 */       return this.temperature - (f + pos.getY() - 64.0F) * 0.05F / 30.0F;
/*     */     } 
/* 426 */     return this.temperature;
/*     */   }
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 431 */     this.theBiomeDecorator.decorate(worldIn, rand, this, pos);
/*     */   }
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/* 435 */     double d0 = MathHelper.clamp_float(getFloatTemperature(pos), 0.0F, 1.0F);
/* 436 */     double d1 = MathHelper.clamp_float(getFloatRainfall(), 0.0F, 1.0F);
/* 437 */     return ColorizerGrass.getGrassColor(d0, d1);
/*     */   }
/*     */   
/*     */   public int getFoliageColorAtPos(BlockPos pos) {
/* 441 */     double d0 = MathHelper.clamp_float(getFloatTemperature(pos), 0.0F, 1.0F);
/* 442 */     double d1 = MathHelper.clamp_float(getFloatRainfall(), 0.0F, 1.0F);
/* 443 */     return ColorizerFoliage.getFoliageColor(d0, d1);
/*     */   }
/*     */   
/*     */   public boolean isSnowyBiome() {
/* 447 */     return this.enableSnow;
/*     */   }
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 451 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*     */   }
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
/*     */   public final void generateBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 465 */     int i = worldIn.getSeaLevel();
/* 466 */     IBlockState iblockstate = this.topBlock;
/* 467 */     IBlockState iblockstate1 = this.fillerBlock;
/* 468 */     int j = -1;
/* 469 */     int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
/* 470 */     int l = x & 0xF;
/* 471 */     int i1 = z & 0xF;
/* 472 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 474 */     for (int j1 = 255; j1 >= 0; j1--) {
/* 475 */       if (j1 <= rand.nextInt(5)) {
/* 476 */         chunkPrimerIn.setBlockState(i1, j1, l, Blocks.bedrock.getDefaultState());
/*     */       } else {
/* 478 */         IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
/*     */         
/* 480 */         if (iblockstate2.getBlock().getMaterial() == Material.air) {
/* 481 */           j = -1;
/* 482 */         } else if (iblockstate2.getBlock() == Blocks.stone) {
/* 483 */           if (j == -1) {
/* 484 */             if (k <= 0) {
/* 485 */               iblockstate = null;
/* 486 */               iblockstate1 = Blocks.stone.getDefaultState();
/* 487 */             } else if (j1 >= i - 4 && j1 <= i + 1) {
/* 488 */               iblockstate = this.topBlock;
/* 489 */               iblockstate1 = this.fillerBlock;
/*     */             } 
/*     */             
/* 492 */             if (j1 < i && (iblockstate == null || iblockstate.getBlock().getMaterial() == Material.air)) {
/* 493 */               if (getFloatTemperature((BlockPos)blockpos$mutableblockpos.set(x, j1, z)) < 0.15F) {
/* 494 */                 iblockstate = Blocks.ice.getDefaultState();
/*     */               } else {
/* 496 */                 iblockstate = Blocks.water.getDefaultState();
/*     */               } 
/*     */             }
/*     */             
/* 500 */             j = k;
/*     */             
/* 502 */             if (j1 >= i - 1) {
/* 503 */               chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
/* 504 */             } else if (j1 < i - 7 - k) {
/* 505 */               iblockstate = null;
/* 506 */               iblockstate1 = Blocks.stone.getDefaultState();
/* 507 */               chunkPrimerIn.setBlockState(i1, j1, l, Blocks.gravel.getDefaultState());
/*     */             } else {
/* 509 */               chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
/*     */             } 
/* 511 */           } else if (j > 0) {
/* 512 */             j--;
/* 513 */             chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
/*     */             
/* 515 */             if (j == 0 && iblockstate1.getBlock() == Blocks.sand) {
/* 516 */               j = rand.nextInt(4) + Math.max(0, j1 - 63);
/* 517 */               iblockstate1 = (iblockstate1.getValue((IProperty)BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState();
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
/*     */   protected BiomeGenBase createMutation() {
/* 530 */     return createMutatedBiome(this.biomeID + 128);
/*     */   }
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 534 */     return new BiomeGenMutated(p_180277_1_, this);
/*     */   }
/*     */   
/*     */   public Class<? extends BiomeGenBase> getBiomeClass() {
/* 538 */     return (Class)getClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqualTo(BiomeGenBase biome) {
/* 545 */     return (biome == this) ? true : ((biome == null) ? false : ((getBiomeClass() == biome.getBiomeClass())));
/*     */   }
/*     */   
/*     */   public TempCategory getTempCategory() {
/* 549 */     return (this.temperature < 0.2D) ? TempCategory.COLD : ((this.temperature < 1.0D) ? TempCategory.MEDIUM : TempCategory.WARM);
/*     */   }
/*     */   
/*     */   public static BiomeGenBase[] getBiomeGenArray() {
/* 553 */     return biomeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BiomeGenBase getBiome(int id) {
/* 560 */     return getBiomeFromBiomeList(id, null);
/*     */   }
/*     */   
/*     */   public static BiomeGenBase getBiomeFromBiomeList(int biomeId, BiomeGenBase biome) {
/* 564 */     if (biomeId >= 0 && biomeId <= biomeList.length) {
/* 565 */       BiomeGenBase biomegenbase = biomeList[biomeId];
/* 566 */       return (biomegenbase == null) ? biome : biomegenbase;
/*     */     } 
/* 568 */     logger.warn("Biome ID is out of bounds: " + biomeId + ", defaulting to 0 (Ocean)");
/* 569 */     return ocean;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 574 */     plains.createMutation();
/* 575 */     desert.createMutation();
/* 576 */     forest.createMutation();
/* 577 */     taiga.createMutation();
/* 578 */     swampland.createMutation();
/* 579 */     icePlains.createMutation();
/* 580 */     jungle.createMutation();
/* 581 */     jungleEdge.createMutation();
/* 582 */     coldTaiga.createMutation();
/* 583 */     savanna.createMutation();
/* 584 */     savannaPlateau.createMutation();
/* 585 */     mesa.createMutation();
/* 586 */     mesaPlateau_F.createMutation();
/* 587 */     mesaPlateau.createMutation();
/* 588 */     birchForest.createMutation();
/* 589 */     birchForestHills.createMutation();
/* 590 */     roofedForest.createMutation();
/* 591 */     megaTaiga.createMutation();
/* 592 */     extremeHills.createMutation();
/* 593 */     extremeHillsPlus.createMutation();
/* 594 */     megaTaiga.createMutatedBiome(megaTaigaHills.biomeID + 128).setBiomeName("Redwood Taiga Hills M"); byte b; int i;
/*     */     BiomeGenBase[] arrayOfBiomeGenBase;
/* 596 */     for (i = (arrayOfBiomeGenBase = biomeList).length, b = 0; b < i; ) { BiomeGenBase biomegenbase = arrayOfBiomeGenBase[b];
/* 597 */       if (biomegenbase != null) {
/* 598 */         if (BIOME_ID_MAP.containsKey(biomegenbase.biomeName)) {
/* 599 */           throw new Error("Biome \"" + biomegenbase.biomeName + "\" is defined as both ID " + ((BiomeGenBase)BIOME_ID_MAP.get(biomegenbase.biomeName)).biomeID + " and " + biomegenbase.biomeID);
/*     */         }
/*     */         
/* 602 */         BIOME_ID_MAP.put(biomegenbase.biomeName, biomegenbase);
/*     */         
/* 604 */         if (biomegenbase.biomeID < 128) {
/* 605 */           explorationBiomesList.add(biomegenbase);
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */     
/* 610 */     explorationBiomesList.remove(hell);
/* 611 */     explorationBiomesList.remove(sky);
/* 612 */     explorationBiomesList.remove(frozenOcean);
/* 613 */     explorationBiomesList.remove(extremeHillsEdge);
/* 614 */   } protected static final NoiseGeneratorPerlin temperatureNoise = new NoiseGeneratorPerlin(new Random(1234L), 1);
/* 615 */   protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), 1);
/* 616 */   protected static final WorldGenDoublePlant DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant(); public String biomeName; public int color; public int field_150609_ah; public float minHeight; public float maxHeight; public float temperature; public float rainfall; public int waterColorMultiplier; public BiomeDecorator theBiomeDecorator; protected List<SpawnListEntry> spawnableMonsterList; protected List<SpawnListEntry> spawnableCreatureList; protected List<SpawnListEntry> spawnableWaterCreatureList; protected List<SpawnListEntry> spawnableCaveCreatureList; protected boolean enableSnow; protected boolean enableRain; public final int biomeID;
/*     */   protected WorldGenTrees worldGeneratorTrees;
/*     */   protected WorldGenBigTree worldGeneratorBigTree;
/*     */   protected WorldGenSwamp worldGeneratorSwamp;
/*     */   
/*     */   public static class Height { public float rootHeight;
/*     */     
/*     */     public Height(float rootHeightIn, float variationIn) {
/* 624 */       this.rootHeight = rootHeightIn;
/* 625 */       this.variation = variationIn;
/*     */     }
/*     */     public float variation;
/*     */     public Height attenuate() {
/* 629 */       return new Height(this.rootHeight * 0.8F, this.variation * 0.6F);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class SpawnListEntry extends WeightedRandom.Item {
/*     */     public Class<? extends EntityLiving> entityClass;
/*     */     public int minGroupCount;
/*     */     public int maxGroupCount;
/*     */     
/*     */     public SpawnListEntry(Class<? extends EntityLiving> entityclassIn, int weight, int groupCountMin, int groupCountMax) {
/* 639 */       super(weight);
/* 640 */       this.entityClass = entityclassIn;
/* 641 */       this.minGroupCount = groupCountMin;
/* 642 */       this.maxGroupCount = groupCountMax;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 646 */       return String.valueOf(this.entityClass.getSimpleName()) + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum TempCategory {
/* 651 */     OCEAN,
/* 652 */     COLD,
/* 653 */     MEDIUM,
/* 654 */     WARM;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */