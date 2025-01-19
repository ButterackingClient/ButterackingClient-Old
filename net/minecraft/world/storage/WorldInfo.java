/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ public class WorldInfo {
/*  15 */   public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
/*     */ 
/*     */   
/*     */   private long randomSeed;
/*     */ 
/*     */   
/*  21 */   private WorldType terrainType = WorldType.DEFAULT;
/*  22 */   private String generatorOptions = "";
/*     */ 
/*     */ 
/*     */   
/*     */   private int spawnX;
/*     */ 
/*     */ 
/*     */   
/*     */   private int spawnY;
/*     */ 
/*     */ 
/*     */   
/*     */   private int spawnZ;
/*     */ 
/*     */ 
/*     */   
/*     */   private long totalTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private long worldTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastTimePlayed;
/*     */ 
/*     */ 
/*     */   
/*     */   private long sizeOnDisk;
/*     */ 
/*     */ 
/*     */   
/*     */   private NBTTagCompound playerTag;
/*     */ 
/*     */ 
/*     */   
/*     */   private int dimension;
/*     */ 
/*     */ 
/*     */   
/*     */   private String levelName;
/*     */ 
/*     */ 
/*     */   
/*     */   private int saveVersion;
/*     */ 
/*     */ 
/*     */   
/*     */   private int cleanWeatherTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean raining;
/*     */ 
/*     */ 
/*     */   
/*     */   private int rainTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean thundering;
/*     */ 
/*     */ 
/*     */   
/*     */   private int thunderTime;
/*     */ 
/*     */   
/*     */   private WorldSettings.GameType theGameType;
/*     */ 
/*     */   
/*     */   private boolean mapFeaturesEnabled;
/*     */ 
/*     */   
/*     */   private boolean hardcore;
/*     */ 
/*     */   
/*     */   private boolean allowCommands;
/*     */ 
/*     */   
/*     */   private boolean initialized;
/*     */ 
/*     */   
/*     */   private EnumDifficulty difficulty;
/*     */ 
/*     */   
/*     */   private boolean difficultyLocked;
/*     */ 
/*     */   
/* 110 */   private double borderCenterX = 0.0D;
/* 111 */   private double borderCenterZ = 0.0D;
/* 112 */   private double borderSize = 6.0E7D;
/* 113 */   private long borderSizeLerpTime = 0L;
/* 114 */   private double borderSizeLerpTarget = 0.0D;
/* 115 */   private double borderSafeZone = 5.0D;
/* 116 */   private double borderDamagePerBlock = 0.2D;
/* 117 */   private int borderWarningDistance = 5;
/* 118 */   private int borderWarningTime = 15;
/* 119 */   private GameRules theGameRules = new GameRules();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldInfo(NBTTagCompound nbt) {
/* 125 */     this.randomSeed = nbt.getLong("RandomSeed");
/*     */     
/* 127 */     if (nbt.hasKey("generatorName", 8)) {
/* 128 */       String s = nbt.getString("generatorName");
/* 129 */       this.terrainType = WorldType.parseWorldType(s);
/*     */       
/* 131 */       if (this.terrainType == null) {
/* 132 */         this.terrainType = WorldType.DEFAULT;
/* 133 */       } else if (this.terrainType.isVersioned()) {
/* 134 */         int i = 0;
/*     */         
/* 136 */         if (nbt.hasKey("generatorVersion", 99)) {
/* 137 */           i = nbt.getInteger("generatorVersion");
/*     */         }
/*     */         
/* 140 */         this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(i);
/*     */       } 
/*     */       
/* 143 */       if (nbt.hasKey("generatorOptions", 8)) {
/* 144 */         this.generatorOptions = nbt.getString("generatorOptions");
/*     */       }
/*     */     } 
/*     */     
/* 148 */     this.theGameType = WorldSettings.GameType.getByID(nbt.getInteger("GameType"));
/*     */     
/* 150 */     if (nbt.hasKey("MapFeatures", 99)) {
/* 151 */       this.mapFeaturesEnabled = nbt.getBoolean("MapFeatures");
/*     */     } else {
/* 153 */       this.mapFeaturesEnabled = true;
/*     */     } 
/*     */     
/* 156 */     this.spawnX = nbt.getInteger("SpawnX");
/* 157 */     this.spawnY = nbt.getInteger("SpawnY");
/* 158 */     this.spawnZ = nbt.getInteger("SpawnZ");
/* 159 */     this.totalTime = nbt.getLong("Time");
/*     */     
/* 161 */     if (nbt.hasKey("DayTime", 99)) {
/* 162 */       this.worldTime = nbt.getLong("DayTime");
/*     */     } else {
/* 164 */       this.worldTime = this.totalTime;
/*     */     } 
/*     */     
/* 167 */     this.lastTimePlayed = nbt.getLong("LastPlayed");
/* 168 */     this.sizeOnDisk = nbt.getLong("SizeOnDisk");
/* 169 */     this.levelName = nbt.getString("LevelName");
/* 170 */     this.saveVersion = nbt.getInteger("version");
/* 171 */     this.cleanWeatherTime = nbt.getInteger("clearWeatherTime");
/* 172 */     this.rainTime = nbt.getInteger("rainTime");
/* 173 */     this.raining = nbt.getBoolean("raining");
/* 174 */     this.thunderTime = nbt.getInteger("thunderTime");
/* 175 */     this.thundering = nbt.getBoolean("thundering");
/* 176 */     this.hardcore = nbt.getBoolean("hardcore");
/*     */     
/* 178 */     if (nbt.hasKey("initialized", 99)) {
/* 179 */       this.initialized = nbt.getBoolean("initialized");
/*     */     } else {
/* 181 */       this.initialized = true;
/*     */     } 
/*     */     
/* 184 */     if (nbt.hasKey("allowCommands", 99)) {
/* 185 */       this.allowCommands = nbt.getBoolean("allowCommands");
/*     */     } else {
/* 187 */       this.allowCommands = (this.theGameType == WorldSettings.GameType.CREATIVE);
/*     */     } 
/*     */     
/* 190 */     if (nbt.hasKey("Player", 10)) {
/* 191 */       this.playerTag = nbt.getCompoundTag("Player");
/* 192 */       this.dimension = this.playerTag.getInteger("Dimension");
/*     */     } 
/*     */     
/* 195 */     if (nbt.hasKey("GameRules", 10)) {
/* 196 */       this.theGameRules.readFromNBT(nbt.getCompoundTag("GameRules"));
/*     */     }
/*     */     
/* 199 */     if (nbt.hasKey("Difficulty", 99)) {
/* 200 */       this.difficulty = EnumDifficulty.getDifficultyEnum(nbt.getByte("Difficulty"));
/*     */     }
/*     */     
/* 203 */     if (nbt.hasKey("DifficultyLocked", 1)) {
/* 204 */       this.difficultyLocked = nbt.getBoolean("DifficultyLocked");
/*     */     }
/*     */     
/* 207 */     if (nbt.hasKey("BorderCenterX", 99)) {
/* 208 */       this.borderCenterX = nbt.getDouble("BorderCenterX");
/*     */     }
/*     */     
/* 211 */     if (nbt.hasKey("BorderCenterZ", 99)) {
/* 212 */       this.borderCenterZ = nbt.getDouble("BorderCenterZ");
/*     */     }
/*     */     
/* 215 */     if (nbt.hasKey("BorderSize", 99)) {
/* 216 */       this.borderSize = nbt.getDouble("BorderSize");
/*     */     }
/*     */     
/* 219 */     if (nbt.hasKey("BorderSizeLerpTime", 99)) {
/* 220 */       this.borderSizeLerpTime = nbt.getLong("BorderSizeLerpTime");
/*     */     }
/*     */     
/* 223 */     if (nbt.hasKey("BorderSizeLerpTarget", 99)) {
/* 224 */       this.borderSizeLerpTarget = nbt.getDouble("BorderSizeLerpTarget");
/*     */     }
/*     */     
/* 227 */     if (nbt.hasKey("BorderSafeZone", 99)) {
/* 228 */       this.borderSafeZone = nbt.getDouble("BorderSafeZone");
/*     */     }
/*     */     
/* 231 */     if (nbt.hasKey("BorderDamagePerBlock", 99)) {
/* 232 */       this.borderDamagePerBlock = nbt.getDouble("BorderDamagePerBlock");
/*     */     }
/*     */     
/* 235 */     if (nbt.hasKey("BorderWarningBlocks", 99)) {
/* 236 */       this.borderWarningDistance = nbt.getInteger("BorderWarningBlocks");
/*     */     }
/*     */     
/* 239 */     if (nbt.hasKey("BorderWarningTime", 99)) {
/* 240 */       this.borderWarningTime = nbt.getInteger("BorderWarningTime");
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldInfo(WorldSettings settings, String name) {
/* 245 */     populateFromWorldSettings(settings);
/* 246 */     this.levelName = name;
/* 247 */     this.difficulty = DEFAULT_DIFFICULTY;
/* 248 */     this.initialized = false;
/*     */   }
/*     */   
/*     */   public void populateFromWorldSettings(WorldSettings settings) {
/* 252 */     this.randomSeed = settings.getSeed();
/* 253 */     this.theGameType = settings.getGameType();
/* 254 */     this.mapFeaturesEnabled = settings.isMapFeaturesEnabled();
/* 255 */     this.hardcore = settings.getHardcoreEnabled();
/* 256 */     this.terrainType = settings.getTerrainType();
/* 257 */     this.generatorOptions = settings.getWorldName();
/* 258 */     this.allowCommands = settings.areCommandsAllowed();
/*     */   }
/*     */   
/*     */   public WorldInfo(WorldInfo worldInformation) {
/* 262 */     this.randomSeed = worldInformation.randomSeed;
/* 263 */     this.terrainType = worldInformation.terrainType;
/* 264 */     this.generatorOptions = worldInformation.generatorOptions;
/* 265 */     this.theGameType = worldInformation.theGameType;
/* 266 */     this.mapFeaturesEnabled = worldInformation.mapFeaturesEnabled;
/* 267 */     this.spawnX = worldInformation.spawnX;
/* 268 */     this.spawnY = worldInformation.spawnY;
/* 269 */     this.spawnZ = worldInformation.spawnZ;
/* 270 */     this.totalTime = worldInformation.totalTime;
/* 271 */     this.worldTime = worldInformation.worldTime;
/* 272 */     this.lastTimePlayed = worldInformation.lastTimePlayed;
/* 273 */     this.sizeOnDisk = worldInformation.sizeOnDisk;
/* 274 */     this.playerTag = worldInformation.playerTag;
/* 275 */     this.dimension = worldInformation.dimension;
/* 276 */     this.levelName = worldInformation.levelName;
/* 277 */     this.saveVersion = worldInformation.saveVersion;
/* 278 */     this.rainTime = worldInformation.rainTime;
/* 279 */     this.raining = worldInformation.raining;
/* 280 */     this.thunderTime = worldInformation.thunderTime;
/* 281 */     this.thundering = worldInformation.thundering;
/* 282 */     this.hardcore = worldInformation.hardcore;
/* 283 */     this.allowCommands = worldInformation.allowCommands;
/* 284 */     this.initialized = worldInformation.initialized;
/* 285 */     this.theGameRules = worldInformation.theGameRules;
/* 286 */     this.difficulty = worldInformation.difficulty;
/* 287 */     this.difficultyLocked = worldInformation.difficultyLocked;
/* 288 */     this.borderCenterX = worldInformation.borderCenterX;
/* 289 */     this.borderCenterZ = worldInformation.borderCenterZ;
/* 290 */     this.borderSize = worldInformation.borderSize;
/* 291 */     this.borderSizeLerpTime = worldInformation.borderSizeLerpTime;
/* 292 */     this.borderSizeLerpTarget = worldInformation.borderSizeLerpTarget;
/* 293 */     this.borderSafeZone = worldInformation.borderSafeZone;
/* 294 */     this.borderDamagePerBlock = worldInformation.borderDamagePerBlock;
/* 295 */     this.borderWarningTime = worldInformation.borderWarningTime;
/* 296 */     this.borderWarningDistance = worldInformation.borderWarningDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBTTagCompound() {
/* 303 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 304 */     updateTagCompound(nbttagcompound, this.playerTag);
/* 305 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound cloneNBTCompound(NBTTagCompound nbt) {
/* 312 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 313 */     updateTagCompound(nbttagcompound, nbt);
/* 314 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   private void updateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt) {
/* 318 */     nbt.setLong("RandomSeed", this.randomSeed);
/* 319 */     nbt.setString("generatorName", this.terrainType.getWorldTypeName());
/* 320 */     nbt.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
/* 321 */     nbt.setString("generatorOptions", this.generatorOptions);
/* 322 */     nbt.setInteger("GameType", this.theGameType.getID());
/* 323 */     nbt.setBoolean("MapFeatures", this.mapFeaturesEnabled);
/* 324 */     nbt.setInteger("SpawnX", this.spawnX);
/* 325 */     nbt.setInteger("SpawnY", this.spawnY);
/* 326 */     nbt.setInteger("SpawnZ", this.spawnZ);
/* 327 */     nbt.setLong("Time", this.totalTime);
/* 328 */     nbt.setLong("DayTime", this.worldTime);
/* 329 */     nbt.setLong("SizeOnDisk", this.sizeOnDisk);
/* 330 */     nbt.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
/* 331 */     nbt.setString("LevelName", this.levelName);
/* 332 */     nbt.setInteger("version", this.saveVersion);
/* 333 */     nbt.setInteger("clearWeatherTime", this.cleanWeatherTime);
/* 334 */     nbt.setInteger("rainTime", this.rainTime);
/* 335 */     nbt.setBoolean("raining", this.raining);
/* 336 */     nbt.setInteger("thunderTime", this.thunderTime);
/* 337 */     nbt.setBoolean("thundering", this.thundering);
/* 338 */     nbt.setBoolean("hardcore", this.hardcore);
/* 339 */     nbt.setBoolean("allowCommands", this.allowCommands);
/* 340 */     nbt.setBoolean("initialized", this.initialized);
/* 341 */     nbt.setDouble("BorderCenterX", this.borderCenterX);
/* 342 */     nbt.setDouble("BorderCenterZ", this.borderCenterZ);
/* 343 */     nbt.setDouble("BorderSize", this.borderSize);
/* 344 */     nbt.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
/* 345 */     nbt.setDouble("BorderSafeZone", this.borderSafeZone);
/* 346 */     nbt.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
/* 347 */     nbt.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
/* 348 */     nbt.setDouble("BorderWarningBlocks", this.borderWarningDistance);
/* 349 */     nbt.setDouble("BorderWarningTime", this.borderWarningTime);
/*     */     
/* 351 */     if (this.difficulty != null) {
/* 352 */       nbt.setByte("Difficulty", (byte)this.difficulty.getDifficultyId());
/*     */     }
/*     */     
/* 355 */     nbt.setBoolean("DifficultyLocked", this.difficultyLocked);
/* 356 */     nbt.setTag("GameRules", (NBTBase)this.theGameRules.writeToNBT());
/*     */     
/* 358 */     if (playerNbt != null) {
/* 359 */       nbt.setTag("Player", (NBTBase)playerNbt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSeed() {
/* 367 */     return this.randomSeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnX() {
/* 374 */     return this.spawnX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnY() {
/* 381 */     return this.spawnY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnZ() {
/* 388 */     return this.spawnZ;
/*     */   }
/*     */   
/*     */   public long getWorldTotalTime() {
/* 392 */     return this.totalTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWorldTime() {
/* 399 */     return this.worldTime;
/*     */   }
/*     */   
/*     */   public long getSizeOnDisk() {
/* 403 */     return this.sizeOnDisk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getPlayerNBTTagCompound() {
/* 410 */     return this.playerTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnX(int x) {
/* 417 */     this.spawnX = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnY(int y) {
/* 424 */     this.spawnY = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnZ(int z) {
/* 431 */     this.spawnZ = z;
/*     */   }
/*     */   
/*     */   public void setWorldTotalTime(long time) {
/* 435 */     this.totalTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {
/* 442 */     this.worldTime = time;
/*     */   }
/*     */   
/*     */   public void setSpawn(BlockPos spawnPoint) {
/* 446 */     this.spawnX = spawnPoint.getX();
/* 447 */     this.spawnY = spawnPoint.getY();
/* 448 */     this.spawnZ = spawnPoint.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWorldName() {
/* 455 */     return this.levelName;
/*     */   }
/*     */   
/*     */   public void setWorldName(String worldName) {
/* 459 */     this.levelName = worldName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaveVersion() {
/* 466 */     return this.saveVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaveVersion(int version) {
/* 473 */     this.saveVersion = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastTimePlayed() {
/* 480 */     return this.lastTimePlayed;
/*     */   }
/*     */   
/*     */   public int getCleanWeatherTime() {
/* 484 */     return this.cleanWeatherTime;
/*     */   }
/*     */   
/*     */   public void setCleanWeatherTime(int cleanWeatherTimeIn) {
/* 488 */     this.cleanWeatherTime = cleanWeatherTimeIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/* 495 */     return this.thundering;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThundering(boolean thunderingIn) {
/* 502 */     this.thundering = thunderingIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThunderTime() {
/* 509 */     return this.thunderTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThunderTime(int time) {
/* 516 */     this.thunderTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRaining() {
/* 523 */     return this.raining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRaining(boolean isRaining) {
/* 530 */     this.raining = isRaining;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRainTime() {
/* 537 */     return this.rainTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRainTime(int time) {
/* 544 */     this.rainTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/* 551 */     return this.theGameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMapFeaturesEnabled() {
/* 558 */     return this.mapFeaturesEnabled;
/*     */   }
/*     */   
/*     */   public void setMapFeaturesEnabled(boolean enabled) {
/* 562 */     this.mapFeaturesEnabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType type) {
/* 569 */     this.theGameType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcoreModeEnabled() {
/* 576 */     return this.hardcore;
/*     */   }
/*     */   
/*     */   public void setHardcore(boolean hardcoreIn) {
/* 580 */     this.hardcore = hardcoreIn;
/*     */   }
/*     */   
/*     */   public WorldType getTerrainType() {
/* 584 */     return this.terrainType;
/*     */   }
/*     */   
/*     */   public void setTerrainType(WorldType type) {
/* 588 */     this.terrainType = type;
/*     */   }
/*     */   
/*     */   public String getGeneratorOptions() {
/* 592 */     return this.generatorOptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areCommandsAllowed() {
/* 599 */     return this.allowCommands;
/*     */   }
/*     */   
/*     */   public void setAllowCommands(boolean allow) {
/* 603 */     this.allowCommands = allow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 610 */     return this.initialized;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerInitialized(boolean initializedIn) {
/* 617 */     this.initialized = initializedIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameRules getGameRulesInstance() {
/* 624 */     return this.theGameRules;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderCenterX() {
/* 631 */     return this.borderCenterX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderCenterZ() {
/* 638 */     return this.borderCenterZ;
/*     */   }
/*     */   
/*     */   public double getBorderSize() {
/* 642 */     return this.borderSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderSize(double size) {
/* 649 */     this.borderSize = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getBorderLerpTime() {
/* 656 */     return this.borderSizeLerpTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderLerpTime(long time) {
/* 663 */     this.borderSizeLerpTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderLerpTarget() {
/* 670 */     return this.borderSizeLerpTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderLerpTarget(double lerpSize) {
/* 677 */     this.borderSizeLerpTarget = lerpSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getBorderCenterZ(double posZ) {
/* 684 */     this.borderCenterZ = posZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getBorderCenterX(double posX) {
/* 691 */     this.borderCenterX = posX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderSafeZone() {
/* 698 */     return this.borderSafeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderSafeZone(double amount) {
/* 705 */     this.borderSafeZone = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getBorderDamagePerBlock() {
/* 712 */     return this.borderDamagePerBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderDamagePerBlock(double damage) {
/* 719 */     this.borderDamagePerBlock = damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBorderWarningDistance() {
/* 726 */     return this.borderWarningDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBorderWarningTime() {
/* 733 */     return this.borderWarningTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderWarningDistance(int amountOfBlocks) {
/* 740 */     this.borderWarningDistance = amountOfBlocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderWarningTime(int ticks) {
/* 747 */     this.borderWarningTime = ticks;
/*     */   }
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 751 */     return this.difficulty;
/*     */   }
/*     */   
/*     */   public void setDifficulty(EnumDifficulty newDifficulty) {
/* 755 */     this.difficulty = newDifficulty;
/*     */   }
/*     */   
/*     */   public boolean isDifficultyLocked() {
/* 759 */     return this.difficultyLocked;
/*     */   }
/*     */   
/*     */   public void setDifficultyLocked(boolean locked) {
/* 763 */     this.difficultyLocked = locked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToCrashReport(CrashReportCategory category) {
/* 770 */     category.addCrashSectionCallable("Level seed", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 772 */             return String.valueOf(WorldInfo.this.getSeed());
/*     */           }
/*     */         });
/* 775 */     category.addCrashSectionCallable("Level generator", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 777 */             return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(WorldInfo.access$0(this.this$0).getWorldTypeID()), WorldInfo.access$0(this.this$0).getWorldTypeName(), Integer.valueOf(WorldInfo.access$0(this.this$0).getGeneratorVersion()), Boolean.valueOf(WorldInfo.access$1(this.this$0)) });
/*     */           }
/*     */         });
/* 780 */     category.addCrashSectionCallable("Level generator options", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 782 */             return WorldInfo.this.generatorOptions;
/*     */           }
/*     */         });
/* 785 */     category.addCrashSectionCallable("Level spawn location", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 787 */             return CrashReportCategory.getCoordinateInfo(WorldInfo.this.spawnX, WorldInfo.this.spawnY, WorldInfo.this.spawnZ);
/*     */           }
/*     */         });
/* 790 */     category.addCrashSectionCallable("Level time", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 792 */             return String.format("%d game time, %d day time", new Object[] { Long.valueOf(WorldInfo.access$6(this.this$0)), Long.valueOf(WorldInfo.access$7(this.this$0)) });
/*     */           }
/*     */         });
/* 795 */     category.addCrashSectionCallable("Level dimension", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 797 */             return String.valueOf(WorldInfo.this.dimension);
/*     */           }
/*     */         });
/* 800 */     category.addCrashSectionCallable("Level storage version", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 802 */             String s = "Unknown?";
/*     */             
/*     */             try {
/* 805 */               switch (WorldInfo.this.saveVersion) {
/*     */                 case 19132:
/* 807 */                   s = "McRegion";
/*     */                   break;
/*     */                 
/*     */                 case 19133:
/* 811 */                   s = "Anvil"; break;
/*     */               } 
/* 813 */             } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */             
/* 817 */             return String.format("0x%05X - %s", new Object[] { Integer.valueOf(WorldInfo.access$9(this.this$0)), s });
/*     */           }
/*     */         });
/* 820 */     category.addCrashSectionCallable("Level weather", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 822 */             return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(WorldInfo.access$10(this.this$0)), Boolean.valueOf(WorldInfo.access$11(this.this$0)), Integer.valueOf(WorldInfo.access$12(this.this$0)), Boolean.valueOf(WorldInfo.access$13(this.this$0)) });
/*     */           }
/*     */         });
/* 825 */     category.addCrashSectionCallable("Level game mode", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 827 */             return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { WorldInfo.access$14(this.this$0).getName(), Integer.valueOf(WorldInfo.access$14(this.this$0).getID()), Boolean.valueOf(WorldInfo.access$15(this.this$0)), Boolean.valueOf(WorldInfo.access$16(this.this$0)) });
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected WorldInfo() {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\WorldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */