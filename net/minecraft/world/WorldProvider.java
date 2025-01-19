/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.ChunkProviderDebug;
/*     */ import net.minecraft.world.gen.ChunkProviderFlat;
/*     */ import net.minecraft.world.gen.ChunkProviderGenerate;
/*     */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*     */ 
/*     */ public abstract class WorldProvider {
/*  18 */   public static final float[] moonPhaseFactors = new float[] { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
/*     */ 
/*     */ 
/*     */   
/*     */   protected World worldObj;
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType terrainType;
/*     */ 
/*     */ 
/*     */   
/*     */   private String generatorSettings;
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldChunkManager worldChunkMgr;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isHellWorld;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasNoSky;
/*     */ 
/*     */   
/*  45 */   protected final float[] lightBrightnessTable = new float[16];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int dimensionId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final float[] colorsSunriseSunset = new float[4];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registerWorld(World worldIn) {
/*  61 */     this.worldObj = worldIn;
/*  62 */     this.terrainType = worldIn.getWorldInfo().getTerrainType();
/*  63 */     this.generatorSettings = worldIn.getWorldInfo().getGeneratorOptions();
/*  64 */     registerWorldChunkManager();
/*  65 */     generateLightBrightnessTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateLightBrightnessTable() {
/*  72 */     float f = 0.0F;
/*     */     
/*  74 */     for (int i = 0; i <= 15; i++) {
/*  75 */       float f1 = 1.0F - i / 15.0F;
/*  76 */       this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerWorldChunkManager() {
/*  84 */     WorldType worldtype = this.worldObj.getWorldInfo().getTerrainType();
/*     */     
/*  86 */     if (worldtype == WorldType.FLAT) {
/*  87 */       FlatGeneratorInfo flatgeneratorinfo = FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions());
/*  88 */       this.worldChunkMgr = (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.getBiomeFromBiomeList(flatgeneratorinfo.getBiome(), BiomeGenBase.field_180279_ad), 0.5F);
/*  89 */     } else if (worldtype == WorldType.DEBUG_WORLD) {
/*  90 */       this.worldChunkMgr = (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.plains, 0.0F);
/*     */     } else {
/*  92 */       this.worldChunkMgr = new WorldChunkManager(this.worldObj);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChunkProvider createChunkGenerator() {
/* 100 */     return (this.terrainType == WorldType.FLAT) ? (IChunkProvider)new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings) : ((this.terrainType == WorldType.DEBUG_WORLD) ? (IChunkProvider)new ChunkProviderDebug(this.worldObj) : ((this.terrainType == WorldType.CUSTOMIZED) ? (IChunkProvider)new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings) : (IChunkProvider)new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCoordinateBeSpawn(int x, int z) {
/* 107 */     return (this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)) == Blocks.grass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calculateCelestialAngle(long worldTime, float partialTicks) {
/* 114 */     int i = (int)(worldTime % 24000L);
/* 115 */     float f = (i + partialTicks) / 24000.0F - 0.25F;
/*     */     
/* 117 */     if (f < 0.0F) {
/* 118 */       f++;
/*     */     }
/*     */     
/* 121 */     if (f > 1.0F) {
/* 122 */       f--;
/*     */     }
/*     */     
/* 125 */     f = 1.0F - (float)((Math.cos(f * Math.PI) + 1.0D) / 2.0D);
/* 126 */     f += (f - f) / 3.0F;
/* 127 */     return f;
/*     */   }
/*     */   
/*     */   public int getMoonPhase(long worldTime) {
/* 131 */     return (int)(worldTime / 24000L % 8L + 8L) % 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSurfaceWorld() {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
/* 145 */     float f = 0.4F;
/* 146 */     float f1 = MathHelper.cos(celestialAngle * 3.1415927F * 2.0F) - 0.0F;
/* 147 */     float f2 = -0.0F;
/*     */     
/* 149 */     if (f1 >= f2 - f && f1 <= f2 + f) {
/* 150 */       float f3 = (f1 - f2) / f * 0.5F + 0.5F;
/* 151 */       float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * 3.1415927F)) * 0.99F;
/* 152 */       f4 *= f4;
/* 153 */       this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
/* 154 */       this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
/* 155 */       this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
/* 156 */       this.colorsSunriseSunset[3] = f4;
/* 157 */       return this.colorsSunriseSunset;
/*     */     } 
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
/* 167 */     float f = MathHelper.cos(p_76562_1_ * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 168 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 169 */     float f1 = 0.7529412F;
/* 170 */     float f2 = 0.84705883F;
/* 171 */     float f3 = 1.0F;
/* 172 */     f1 *= f * 0.94F + 0.06F;
/* 173 */     f2 *= f * 0.94F + 0.06F;
/* 174 */     f3 *= f * 0.91F + 0.09F;
/* 175 */     return new Vec3(f1, f2, f3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRespawnHere() {
/* 182 */     return true;
/*     */   }
/*     */   
/*     */   public static WorldProvider getProviderForDimension(int dimension) {
/* 186 */     return (dimension == -1) ? new WorldProviderHell() : ((dimension == 0) ? new WorldProviderSurface() : ((dimension == 1) ? new WorldProviderEnd() : null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCloudHeight() {
/* 193 */     return 128.0F;
/*     */   }
/*     */   
/*     */   public boolean isSkyColored() {
/* 197 */     return true;
/*     */   }
/*     */   
/*     */   public BlockPos getSpawnCoordinate() {
/* 201 */     return null;
/*     */   }
/*     */   
/*     */   public int getAverageGroundLevel() {
/* 205 */     return (this.terrainType == WorldType.FLAT) ? 4 : (this.worldObj.getSeaLevel() + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVoidFogYFactor() {
/* 214 */     return (this.terrainType == WorldType.FLAT) ? 1.0D : 0.03125D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesXZShowFog(int x, int z) {
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getDimensionName();
/*     */ 
/*     */   
/*     */   public abstract String getInternalNameSuffix();
/*     */ 
/*     */   
/*     */   public WorldChunkManager getWorldChunkManager() {
/* 232 */     return this.worldChunkMgr;
/*     */   }
/*     */   
/*     */   public boolean doesWaterVaporize() {
/* 236 */     return this.isHellWorld;
/*     */   }
/*     */   
/*     */   public boolean getHasNoSky() {
/* 240 */     return this.hasNoSky;
/*     */   }
/*     */   
/*     */   public float[] getLightBrightnessTable() {
/* 244 */     return this.lightBrightnessTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimensionId() {
/* 251 */     return this.dimensionId;
/*     */   }
/*     */   
/*     */   public WorldBorder getWorldBorder() {
/* 255 */     return new WorldBorder();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */