/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEventData;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EntityTracker;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*     */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*     */ import net.minecraft.network.play.server.S27PacketExplosion;
/*     */ import net.minecraft.network.play.server.S2APacketParticles;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.ScoreboardSaveData;
/*     */ import net.minecraft.scoreboard.ServerScoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.IThreadListener;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.village.VillageCollection;
/*     */ import net.minecraft.village.VillageSiege;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import net.minecraft.world.gen.ChunkProviderServer;
/*     */ import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.MapStorage;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldServer
/*     */   extends World
/*     */   implements IThreadListener
/*     */ {
/*  77 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final MinecraftServer mcServer;
/*     */   private final EntityTracker theEntityTracker;
/*     */   private final PlayerManager thePlayerManager;
/*  81 */   private final Set<NextTickListEntry> pendingTickListEntriesHashSet = Sets.newHashSet();
/*  82 */   private final TreeSet<NextTickListEntry> pendingTickListEntriesTreeSet = new TreeSet<>();
/*  83 */   private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ChunkProviderServer theChunkProviderServer;
/*     */ 
/*     */   
/*     */   public boolean disableLevelSaving;
/*     */ 
/*     */   
/*     */   private boolean allPlayersSleeping;
/*     */ 
/*     */   
/*     */   private int updateEntityTick;
/*     */ 
/*     */   
/*     */   private final Teleporter worldTeleporter;
/*     */ 
/*     */   
/* 101 */   private final SpawnerAnimals mobSpawner = new SpawnerAnimals();
/* 102 */   protected final VillageSiege villageSiege = new VillageSiege(this);
/* 103 */   private ServerBlockEventList[] blockEventQueue = new ServerBlockEventList[] { new ServerBlockEventList(null), new ServerBlockEventList(null) };
/*     */   private int blockEventCacheIndex;
/* 105 */   private static final List<WeightedRandomChestContent> bonusChestContent = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10) });
/* 106 */   private List<NextTickListEntry> pendingTickListEntriesThisTick = Lists.newArrayList();
/*     */   
/*     */   public WorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn) {
/* 109 */     super(saveHandlerIn, info, WorldProvider.getProviderForDimension(dimensionId), profilerIn, false);
/* 110 */     this.mcServer = server;
/* 111 */     this.theEntityTracker = new EntityTracker(this);
/* 112 */     this.thePlayerManager = new PlayerManager(this);
/* 113 */     this.provider.registerWorld(this);
/* 114 */     this.chunkProvider = createChunkProvider();
/* 115 */     this.worldTeleporter = new Teleporter(this);
/* 116 */     calculateInitialSkylight();
/* 117 */     calculateInitialWeather();
/* 118 */     getWorldBorder().setSize(server.getMaxWorldSize());
/*     */   }
/*     */   
/*     */   public World init() {
/* 122 */     this.mapStorage = new MapStorage(this.saveHandler);
/* 123 */     String s = VillageCollection.fileNameForProvider(this.provider);
/* 124 */     VillageCollection villagecollection = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, s);
/*     */     
/* 126 */     if (villagecollection == null) {
/* 127 */       this.villageCollectionObj = new VillageCollection(this);
/* 128 */       this.mapStorage.setData(s, (WorldSavedData)this.villageCollectionObj);
/*     */     } else {
/* 130 */       this.villageCollectionObj = villagecollection;
/* 131 */       this.villageCollectionObj.setWorldsForAll(this);
/*     */     } 
/*     */     
/* 134 */     this.worldScoreboard = (Scoreboard)new ServerScoreboard(this.mcServer);
/* 135 */     ScoreboardSaveData scoreboardsavedata = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
/*     */     
/* 137 */     if (scoreboardsavedata == null) {
/* 138 */       scoreboardsavedata = new ScoreboardSaveData();
/* 139 */       this.mapStorage.setData("scoreboard", (WorldSavedData)scoreboardsavedata);
/*     */     } 
/*     */     
/* 142 */     scoreboardsavedata.setScoreboard(this.worldScoreboard);
/* 143 */     ((ServerScoreboard)this.worldScoreboard).func_96547_a(scoreboardsavedata);
/* 144 */     getWorldBorder().setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
/* 145 */     getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
/* 146 */     getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
/* 147 */     getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
/* 148 */     getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
/*     */     
/* 150 */     if (this.worldInfo.getBorderLerpTime() > 0L) {
/* 151 */       getWorldBorder().setTransition(this.worldInfo.getBorderSize(), this.worldInfo.getBorderLerpTarget(), this.worldInfo.getBorderLerpTime());
/*     */     } else {
/* 153 */       getWorldBorder().setTransition(this.worldInfo.getBorderSize());
/*     */     } 
/*     */     
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 163 */     super.tick();
/*     */     
/* 165 */     if (getWorldInfo().isHardcoreModeEnabled() && getDifficulty() != EnumDifficulty.HARD) {
/* 166 */       getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/*     */     }
/*     */     
/* 169 */     this.provider.getWorldChunkManager().cleanupCache();
/*     */     
/* 171 */     if (areAllPlayersAsleep()) {
/* 172 */       if (getGameRules().getBoolean("doDaylightCycle")) {
/* 173 */         long i = this.worldInfo.getWorldTime() + 24000L;
/* 174 */         this.worldInfo.setWorldTime(i - i % 24000L);
/*     */       } 
/*     */       
/* 177 */       wakeAllPlayers();
/*     */     } 
/*     */     
/* 180 */     this.theProfiler.startSection("mobSpawner");
/*     */     
/* 182 */     if (getGameRules().getBoolean("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
/* 183 */       this.mobSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, (this.worldInfo.getWorldTotalTime() % 400L == 0L));
/*     */     }
/*     */     
/* 186 */     this.theProfiler.endStartSection("chunkSource");
/* 187 */     this.chunkProvider.unloadQueuedChunks();
/* 188 */     int j = calculateSkylightSubtracted(1.0F);
/*     */     
/* 190 */     if (j != getSkylightSubtracted()) {
/* 191 */       setSkylightSubtracted(j);
/*     */     }
/*     */     
/* 194 */     this.worldInfo.setWorldTotalTime(this.worldInfo.getWorldTotalTime() + 1L);
/*     */     
/* 196 */     if (getGameRules().getBoolean("doDaylightCycle")) {
/* 197 */       this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
/*     */     }
/*     */     
/* 200 */     this.theProfiler.endStartSection("tickPending");
/* 201 */     tickUpdates(false);
/* 202 */     this.theProfiler.endStartSection("tickBlocks");
/* 203 */     updateBlocks();
/* 204 */     this.theProfiler.endStartSection("chunkMap");
/* 205 */     this.thePlayerManager.updatePlayerInstances();
/* 206 */     this.theProfiler.endStartSection("village");
/* 207 */     this.villageCollectionObj.tick();
/* 208 */     this.villageSiege.tick();
/* 209 */     this.theProfiler.endStartSection("portalForcer");
/* 210 */     this.worldTeleporter.removeStalePortalLocations(getTotalWorldTime());
/* 211 */     this.theProfiler.endSection();
/* 212 */     sendQueuedBlockEvents();
/*     */   }
/*     */   
/*     */   public BiomeGenBase.SpawnListEntry getSpawnListEntryForTypeAt(EnumCreatureType creatureType, BlockPos pos) {
/* 216 */     List<BiomeGenBase.SpawnListEntry> list = getChunkProvider().getPossibleCreatures(creatureType, pos);
/* 217 */     return (list != null && !list.isEmpty()) ? (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, list) : null;
/*     */   }
/*     */   
/*     */   public boolean canCreatureTypeSpawnHere(EnumCreatureType creatureType, BiomeGenBase.SpawnListEntry spawnListEntry, BlockPos pos) {
/* 221 */     List<BiomeGenBase.SpawnListEntry> list = getChunkProvider().getPossibleCreatures(creatureType, pos);
/* 222 */     return (list != null && !list.isEmpty()) ? list.contains(spawnListEntry) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAllPlayersSleepingFlag() {
/* 229 */     this.allPlayersSleeping = false;
/*     */     
/* 231 */     if (!this.playerEntities.isEmpty()) {
/* 232 */       int i = 0;
/* 233 */       int j = 0;
/*     */       
/* 235 */       for (EntityPlayer entityplayer : this.playerEntities) {
/* 236 */         if (entityplayer.isSpectator()) {
/* 237 */           i++; continue;
/* 238 */         }  if (entityplayer.isPlayerSleeping()) {
/* 239 */           j++;
/*     */         }
/*     */       } 
/*     */       
/* 243 */       this.allPlayersSleeping = (j > 0 && j >= this.playerEntities.size() - i);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void wakeAllPlayers() {
/* 248 */     this.allPlayersSleeping = false;
/*     */     
/* 250 */     for (EntityPlayer entityplayer : this.playerEntities) {
/* 251 */       if (entityplayer.isPlayerSleeping()) {
/* 252 */         entityplayer.wakeUpPlayer(false, false, true);
/*     */       }
/*     */     } 
/*     */     
/* 256 */     resetRainAndThunder();
/*     */   }
/*     */   
/*     */   private void resetRainAndThunder() {
/* 260 */     this.worldInfo.setRainTime(0);
/* 261 */     this.worldInfo.setRaining(false);
/* 262 */     this.worldInfo.setThunderTime(0);
/* 263 */     this.worldInfo.setThundering(false);
/*     */   }
/*     */   
/*     */   public boolean areAllPlayersAsleep() {
/* 267 */     if (this.allPlayersSleeping && !this.isRemote) {
/* 268 */       for (EntityPlayer entityplayer : this.playerEntities) {
/* 269 */         if (entityplayer.isSpectator() || !entityplayer.isPlayerFullyAsleep()) {
/* 270 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 274 */       return true;
/*     */     } 
/* 276 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInitialSpawnLocation() {
/* 284 */     if (this.worldInfo.getSpawnY() <= 0) {
/* 285 */       this.worldInfo.setSpawnY(getSeaLevel() + 1);
/*     */     }
/*     */     
/* 288 */     int i = this.worldInfo.getSpawnX();
/* 289 */     int j = this.worldInfo.getSpawnZ();
/* 290 */     int k = 0;
/*     */     
/* 292 */     while (getGroundAboveSeaLevel(new BlockPos(i, 0, j)).getMaterial() == Material.air) {
/* 293 */       i += this.rand.nextInt(8) - this.rand.nextInt(8);
/* 294 */       j += this.rand.nextInt(8) - this.rand.nextInt(8);
/* 295 */       k++;
/*     */       
/* 297 */       if (k == 10000) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 302 */     this.worldInfo.setSpawnX(i);
/* 303 */     this.worldInfo.setSpawnZ(j);
/*     */   }
/*     */   
/*     */   protected void updateBlocks() {
/* 307 */     super.updateBlocks();
/*     */     
/* 309 */     if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/* 310 */       for (ChunkCoordIntPair chunkcoordintpair1 : this.activeChunkSet) {
/* 311 */         getChunkFromChunkCoords(chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos).func_150804_b(false);
/*     */       }
/*     */     } else {
/* 314 */       int i = 0;
/* 315 */       int j = 0;
/*     */       
/* 317 */       for (ChunkCoordIntPair chunkcoordintpair : this.activeChunkSet) {
/* 318 */         int k = chunkcoordintpair.chunkXPos * 16;
/* 319 */         int l = chunkcoordintpair.chunkZPos * 16;
/* 320 */         this.theProfiler.startSection("getChunk");
/* 321 */         Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/* 322 */         playMoodSoundAndCheckLight(k, l, chunk);
/* 323 */         this.theProfiler.endStartSection("tickChunk");
/* 324 */         chunk.func_150804_b(false);
/* 325 */         this.theProfiler.endStartSection("thunder");
/*     */         
/* 327 */         if (this.rand.nextInt(100000) == 0 && isRaining() && isThundering()) {
/* 328 */           this.updateLCG = this.updateLCG * 3 + 1013904223;
/* 329 */           int i1 = this.updateLCG >> 2;
/* 330 */           BlockPos blockpos = adjustPosToNearbyEntity(new BlockPos(k + (i1 & 0xF), 0, l + (i1 >> 8 & 0xF)));
/*     */           
/* 332 */           if (isRainingAt(blockpos)) {
/* 333 */             addWeatherEffect((Entity)new EntityLightningBolt(this, blockpos.getX(), blockpos.getY(), blockpos.getZ()));
/*     */           }
/*     */         } 
/*     */         
/* 337 */         this.theProfiler.endStartSection("iceandsnow");
/*     */         
/* 339 */         if (this.rand.nextInt(16) == 0) {
/* 340 */           this.updateLCG = this.updateLCG * 3 + 1013904223;
/* 341 */           int k2 = this.updateLCG >> 2;
/* 342 */           BlockPos blockpos2 = getPrecipitationHeight(new BlockPos(k + (k2 & 0xF), 0, l + (k2 >> 8 & 0xF)));
/* 343 */           BlockPos blockpos1 = blockpos2.down();
/*     */           
/* 345 */           if (canBlockFreezeNoWater(blockpos1)) {
/* 346 */             setBlockState(blockpos1, Blocks.ice.getDefaultState());
/*     */           }
/*     */           
/* 349 */           if (isRaining() && canSnowAt(blockpos2, true)) {
/* 350 */             setBlockState(blockpos2, Blocks.snow_layer.getDefaultState());
/*     */           }
/*     */           
/* 353 */           if (isRaining() && getBiomeGenForCoords(blockpos1).canRain()) {
/* 354 */             getBlockState(blockpos1).getBlock().fillWithRain(this, blockpos1);
/*     */           }
/*     */         } 
/*     */         
/* 358 */         this.theProfiler.endStartSection("tickBlocks");
/* 359 */         int l2 = getGameRules().getInt("randomTickSpeed");
/*     */         
/* 361 */         if (l2 > 0) {
/* 362 */           byte b; int m; ExtendedBlockStorage[] arrayOfExtendedBlockStorage; for (m = (arrayOfExtendedBlockStorage = chunk.getBlockStorageArray()).length, b = 0; b < m; ) { ExtendedBlockStorage extendedblockstorage = arrayOfExtendedBlockStorage[b];
/* 363 */             if (extendedblockstorage != null && extendedblockstorage.getNeedsRandomTick()) {
/* 364 */               for (int j1 = 0; j1 < l2; j1++) {
/* 365 */                 this.updateLCG = this.updateLCG * 3 + 1013904223;
/* 366 */                 int k1 = this.updateLCG >> 2;
/* 367 */                 int l1 = k1 & 0xF;
/* 368 */                 int i2 = k1 >> 8 & 0xF;
/* 369 */                 int j2 = k1 >> 16 & 0xF;
/* 370 */                 j++;
/* 371 */                 IBlockState iblockstate = extendedblockstorage.get(l1, j2, i2);
/* 372 */                 Block block = iblockstate.getBlock();
/*     */                 
/* 374 */                 if (block.getTickRandomly()) {
/* 375 */                   i++;
/* 376 */                   block.randomTick(this, new BlockPos(l1 + k, j2 + extendedblockstorage.getYLocation(), i2 + l), iblockstate, this.rand);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */             b++; }
/*     */         
/*     */         } 
/* 383 */         this.theProfiler.endSection();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BlockPos adjustPosToNearbyEntity(BlockPos pos) {
/* 389 */     BlockPos blockpos = getPrecipitationHeight(pos);
/* 390 */     AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos, new BlockPos(blockpos.getX(), getHeight(), blockpos.getZ()))).expand(3.0D, 3.0D, 3.0D);
/* 391 */     List<EntityLivingBase> list = getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, new Predicate<EntityLivingBase>() {
/*     */           public boolean apply(EntityLivingBase p_apply_1_) {
/* 393 */             return (p_apply_1_ != null && p_apply_1_.isEntityAlive() && WorldServer.this.canSeeSky(p_apply_1_.getPosition()));
/*     */           }
/*     */         });
/* 396 */     return !list.isEmpty() ? ((EntityLivingBase)list.get(this.rand.nextInt(list.size()))).getPosition() : blockpos;
/*     */   }
/*     */   
/*     */   public boolean isBlockTickPending(BlockPos pos, Block blockType) {
/* 400 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockType);
/* 401 */     return this.pendingTickListEntriesThisTick.contains(nextticklistentry);
/*     */   }
/*     */   
/*     */   public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {
/* 405 */     updateBlockTick(pos, blockIn, delay, 0);
/*     */   }
/*     */   
/*     */   public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {
/* 409 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
/* 410 */     int i = 0;
/*     */     
/* 412 */     if (this.scheduledUpdatesAreImmediate && blockIn.getMaterial() != Material.air) {
/* 413 */       if (blockIn.requiresUpdates()) {
/* 414 */         i = 8;
/*     */         
/* 416 */         if (isAreaLoaded(nextticklistentry.position.add(-i, -i, -i), nextticklistentry.position.add(i, i, i))) {
/* 417 */           IBlockState iblockstate = getBlockState(nextticklistentry.position);
/*     */           
/* 419 */           if (iblockstate.getBlock().getMaterial() != Material.air && iblockstate.getBlock() == nextticklistentry.getBlock()) {
/* 420 */             iblockstate.getBlock().updateTick(this, nextticklistentry.position, iblockstate, this.rand);
/*     */           }
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 427 */       delay = 1;
/*     */     } 
/*     */     
/* 430 */     if (isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
/* 431 */       if (blockIn.getMaterial() != Material.air) {
/* 432 */         nextticklistentry.setScheduledTime(delay + this.worldInfo.getWorldTotalTime());
/* 433 */         nextticklistentry.setPriority(priority);
/*     */       } 
/*     */       
/* 436 */       if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
/* 437 */         this.pendingTickListEntriesHashSet.add(nextticklistentry);
/* 438 */         this.pendingTickListEntriesTreeSet.add(nextticklistentry);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {
/* 444 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
/* 445 */     nextticklistentry.setPriority(priority);
/*     */     
/* 447 */     if (blockIn.getMaterial() != Material.air) {
/* 448 */       nextticklistentry.setScheduledTime(delay + this.worldInfo.getWorldTotalTime());
/*     */     }
/*     */     
/* 451 */     if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
/* 452 */       this.pendingTickListEntriesHashSet.add(nextticklistentry);
/* 453 */       this.pendingTickListEntriesTreeSet.add(nextticklistentry);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntities() {
/* 461 */     if (this.playerEntities.isEmpty()) {
/* 462 */       if (this.updateEntityTick++ >= 1200) {
/*     */         return;
/*     */       }
/*     */     } else {
/* 466 */       resetUpdateEntityTick();
/*     */     } 
/*     */     
/* 469 */     super.updateEntities();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetUpdateEntityTick() {
/* 476 */     this.updateEntityTick = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tickUpdates(boolean p_72955_1_) {
/* 483 */     if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/* 484 */       return false;
/*     */     }
/* 486 */     int i = this.pendingTickListEntriesTreeSet.size();
/*     */     
/* 488 */     if (i != this.pendingTickListEntriesHashSet.size()) {
/* 489 */       throw new IllegalStateException("TickNextTick list out of synch");
/*     */     }
/* 491 */     if (i > 1000) {
/* 492 */       i = 1000;
/*     */     }
/*     */     
/* 495 */     this.theProfiler.startSection("cleaning");
/*     */     
/* 497 */     for (int j = 0; j < i; j++) {
/* 498 */       NextTickListEntry nextticklistentry = this.pendingTickListEntriesTreeSet.first();
/*     */       
/* 500 */       if (!p_72955_1_ && nextticklistentry.scheduledTime > this.worldInfo.getWorldTotalTime()) {
/*     */         break;
/*     */       }
/*     */       
/* 504 */       this.pendingTickListEntriesTreeSet.remove(nextticklistentry);
/* 505 */       this.pendingTickListEntriesHashSet.remove(nextticklistentry);
/* 506 */       this.pendingTickListEntriesThisTick.add(nextticklistentry);
/*     */     } 
/*     */     
/* 509 */     this.theProfiler.endSection();
/* 510 */     this.theProfiler.startSection("ticking");
/* 511 */     Iterator<NextTickListEntry> iterator = this.pendingTickListEntriesThisTick.iterator();
/*     */     
/* 513 */     while (iterator.hasNext()) {
/* 514 */       NextTickListEntry nextticklistentry1 = iterator.next();
/* 515 */       iterator.remove();
/* 516 */       int k = 0;
/*     */       
/* 518 */       if (isAreaLoaded(nextticklistentry1.position.add(-k, -k, -k), nextticklistentry1.position.add(k, k, k))) {
/* 519 */         IBlockState iblockstate = getBlockState(nextticklistentry1.position);
/*     */         
/* 521 */         if (iblockstate.getBlock().getMaterial() != Material.air && Block.isEqualTo(iblockstate.getBlock(), nextticklistentry1.getBlock()))
/*     */           try {
/* 523 */             iblockstate.getBlock().updateTick(this, nextticklistentry1.position, iblockstate, this.rand);
/* 524 */           } catch (Throwable throwable) {
/* 525 */             CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while ticking a block");
/* 526 */             CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being ticked");
/* 527 */             CrashReportCategory.addBlockInfo(crashreportcategory, nextticklistentry1.position, iblockstate);
/* 528 */             throw new ReportedException(crashreport);
/*     */           }  
/*     */         continue;
/*     */       } 
/* 532 */       scheduleUpdate(nextticklistentry1.position, nextticklistentry1.getBlock(), 0);
/*     */     } 
/*     */ 
/*     */     
/* 536 */     this.theProfiler.endSection();
/* 537 */     this.pendingTickListEntriesThisTick.clear();
/* 538 */     return !this.pendingTickListEntriesTreeSet.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
/* 544 */     ChunkCoordIntPair chunkcoordintpair = chunkIn.getChunkCoordIntPair();
/* 545 */     int i = (chunkcoordintpair.chunkXPos << 4) - 2;
/* 546 */     int j = i + 16 + 2;
/* 547 */     int k = (chunkcoordintpair.chunkZPos << 4) - 2;
/* 548 */     int l = k + 16 + 2;
/* 549 */     return func_175712_a(new StructureBoundingBox(i, 0, k, j, 256, l), p_72920_2_);
/*     */   }
/*     */   
/*     */   public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBB, boolean p_175712_2_) {
/* 553 */     List<NextTickListEntry> list = null;
/*     */     
/* 555 */     for (int i = 0; i < 2; i++) {
/*     */       Iterator<NextTickListEntry> iterator;
/*     */       
/* 558 */       if (i == 0) {
/* 559 */         iterator = this.pendingTickListEntriesTreeSet.iterator();
/*     */       } else {
/* 561 */         iterator = this.pendingTickListEntriesThisTick.iterator();
/*     */       } 
/*     */       
/* 564 */       while (iterator.hasNext()) {
/* 565 */         NextTickListEntry nextticklistentry = iterator.next();
/* 566 */         BlockPos blockpos = nextticklistentry.position;
/*     */         
/* 568 */         if (blockpos.getX() >= structureBB.minX && blockpos.getX() < structureBB.maxX && blockpos.getZ() >= structureBB.minZ && blockpos.getZ() < structureBB.maxZ) {
/* 569 */           if (p_175712_2_) {
/* 570 */             this.pendingTickListEntriesHashSet.remove(nextticklistentry);
/* 571 */             iterator.remove();
/*     */           } 
/*     */           
/* 574 */           if (list == null) {
/* 575 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 578 */           list.add(nextticklistentry);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 583 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
/* 591 */     if (!canSpawnAnimals() && (entityIn instanceof net.minecraft.entity.passive.EntityAnimal || entityIn instanceof net.minecraft.entity.passive.EntityWaterMob)) {
/* 592 */       entityIn.setDead();
/*     */     }
/*     */     
/* 595 */     if (!canSpawnNPCs() && entityIn instanceof net.minecraft.entity.INpc) {
/* 596 */       entityIn.setDead();
/*     */     }
/*     */     
/* 599 */     super.updateEntityWithOptionalForce(entityIn, forceUpdate);
/*     */   }
/*     */   
/*     */   private boolean canSpawnNPCs() {
/* 603 */     return this.mcServer.getCanSpawnNPCs();
/*     */   }
/*     */   
/*     */   private boolean canSpawnAnimals() {
/* 607 */     return this.mcServer.getCanSpawnAnimals();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IChunkProvider createChunkProvider() {
/* 614 */     IChunkLoader ichunkloader = this.saveHandler.getChunkLoader(this.provider);
/* 615 */     this.theChunkProviderServer = new ChunkProviderServer(this, ichunkloader, this.provider.createChunkGenerator());
/* 616 */     return (IChunkProvider)this.theChunkProviderServer;
/*     */   }
/*     */   
/*     */   public List<TileEntity> getTileEntitiesIn(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 620 */     List<TileEntity> list = Lists.newArrayList();
/*     */     
/* 622 */     for (int i = 0; i < this.loadedTileEntityList.size(); i++) {
/* 623 */       TileEntity tileentity = this.loadedTileEntityList.get(i);
/* 624 */       BlockPos blockpos = tileentity.getPos();
/*     */       
/* 626 */       if (blockpos.getX() >= minX && blockpos.getY() >= minY && blockpos.getZ() >= minZ && blockpos.getX() < maxX && blockpos.getY() < maxY && blockpos.getZ() < maxZ) {
/* 627 */         list.add(tileentity);
/*     */       }
/*     */     } 
/*     */     
/* 631 */     return list;
/*     */   }
/*     */   
/*     */   public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
/* 635 */     return (!this.mcServer.isBlockProtected(this, pos, player) && getWorldBorder().contains(pos));
/*     */   }
/*     */   
/*     */   public void initialize(WorldSettings settings) {
/* 639 */     if (!this.worldInfo.isInitialized()) {
/*     */       try {
/* 641 */         createSpawnPosition(settings);
/*     */         
/* 643 */         if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/* 644 */           setDebugWorldSettings();
/*     */         }
/*     */         
/* 647 */         super.initialize(settings);
/* 648 */       } catch (Throwable throwable) {
/* 649 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception initializing level");
/*     */         
/*     */         try {
/* 652 */           addWorldInfoToCrashReport(crashreport);
/* 653 */         } catch (Throwable throwable1) {}
/*     */ 
/*     */ 
/*     */         
/* 657 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */       
/* 660 */       this.worldInfo.setServerInitialized(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setDebugWorldSettings() {
/* 665 */     this.worldInfo.setMapFeaturesEnabled(false);
/* 666 */     this.worldInfo.setAllowCommands(true);
/* 667 */     this.worldInfo.setRaining(false);
/* 668 */     this.worldInfo.setThundering(false);
/* 669 */     this.worldInfo.setCleanWeatherTime(1000000000);
/* 670 */     this.worldInfo.setWorldTime(6000L);
/* 671 */     this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
/* 672 */     this.worldInfo.setHardcore(false);
/* 673 */     this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
/* 674 */     this.worldInfo.setDifficultyLocked(true);
/* 675 */     getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createSpawnPosition(WorldSettings settings) {
/* 682 */     if (!this.provider.canRespawnHere()) {
/* 683 */       this.worldInfo.setSpawn(BlockPos.ORIGIN.up(this.provider.getAverageGroundLevel()));
/* 684 */     } else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/* 685 */       this.worldInfo.setSpawn(BlockPos.ORIGIN.up());
/*     */     } else {
/* 687 */       this.findingSpawnPoint = true;
/* 688 */       WorldChunkManager worldchunkmanager = this.provider.getWorldChunkManager();
/* 689 */       List<BiomeGenBase> list = worldchunkmanager.getBiomesToSpawnIn();
/* 690 */       Random random = new Random(getSeed());
/* 691 */       BlockPos blockpos = worldchunkmanager.findBiomePosition(0, 0, 256, list, random);
/* 692 */       int i = 0;
/* 693 */       int j = this.provider.getAverageGroundLevel();
/* 694 */       int k = 0;
/*     */       
/* 696 */       if (blockpos != null) {
/* 697 */         i = blockpos.getX();
/* 698 */         k = blockpos.getZ();
/*     */       } else {
/* 700 */         logger.warn("Unable to find spawn biome");
/*     */       } 
/*     */       
/* 703 */       int l = 0;
/*     */       
/* 705 */       while (!this.provider.canCoordinateBeSpawn(i, k)) {
/* 706 */         i += random.nextInt(64) - random.nextInt(64);
/* 707 */         k += random.nextInt(64) - random.nextInt(64);
/* 708 */         l++;
/*     */         
/* 710 */         if (l == 1000) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 715 */       this.worldInfo.setSpawn(new BlockPos(i, j, k));
/* 716 */       this.findingSpawnPoint = false;
/*     */       
/* 718 */       if (settings.isBonusChestEnabled()) {
/* 719 */         createBonusChest();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBonusChest() {
/* 728 */     WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest(bonusChestContent, 10);
/*     */     
/* 730 */     for (int i = 0; i < 10; i++) {
/* 731 */       int j = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
/* 732 */       int k = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
/* 733 */       BlockPos blockpos = getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)).up();
/*     */       
/* 735 */       if (worldgeneratorbonuschest.generate(this, this.rand, blockpos)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getSpawnCoordinate() {
/* 745 */     return this.provider.getSpawnCoordinate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllChunks(boolean p_73044_1_, IProgressUpdate progressCallback) throws MinecraftException {
/* 752 */     if (this.chunkProvider.canSave()) {
/* 753 */       if (progressCallback != null) {
/* 754 */         progressCallback.displaySavingString("Saving level");
/*     */       }
/*     */       
/* 757 */       saveLevel();
/*     */       
/* 759 */       if (progressCallback != null) {
/* 760 */         progressCallback.displayLoadingString("Saving chunks");
/*     */       }
/*     */       
/* 763 */       this.chunkProvider.saveChunks(p_73044_1_, progressCallback);
/*     */       
/* 765 */       for (Chunk chunk : Lists.newArrayList(this.theChunkProviderServer.func_152380_a())) {
/* 766 */         if (chunk != null && !this.thePlayerManager.hasPlayerInstance(chunk.xPosition, chunk.zPosition)) {
/* 767 */           this.theChunkProviderServer.dropChunk(chunk.xPosition, chunk.zPosition);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChunkData() {
/* 777 */     if (this.chunkProvider.canSave()) {
/* 778 */       this.chunkProvider.saveExtraData();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveLevel() throws MinecraftException {
/* 786 */     checkSessionLock();
/* 787 */     this.worldInfo.setBorderSize(getWorldBorder().getDiameter());
/* 788 */     this.worldInfo.getBorderCenterX(getWorldBorder().getCenterX());
/* 789 */     this.worldInfo.getBorderCenterZ(getWorldBorder().getCenterZ());
/* 790 */     this.worldInfo.setBorderSafeZone(getWorldBorder().getDamageBuffer());
/* 791 */     this.worldInfo.setBorderDamagePerBlock(getWorldBorder().getDamageAmount());
/* 792 */     this.worldInfo.setBorderWarningDistance(getWorldBorder().getWarningDistance());
/* 793 */     this.worldInfo.setBorderWarningTime(getWorldBorder().getWarningTime());
/* 794 */     this.worldInfo.setBorderLerpTarget(getWorldBorder().getTargetSize());
/* 795 */     this.worldInfo.setBorderLerpTime(getWorldBorder().getTimeUntilTarget());
/* 796 */     this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
/* 797 */     this.mapStorage.saveAllData();
/*     */   }
/*     */   
/*     */   protected void onEntityAdded(Entity entityIn) {
/* 801 */     super.onEntityAdded(entityIn);
/* 802 */     this.entitiesById.addKey(entityIn.getEntityId(), entityIn);
/* 803 */     this.entitiesByUuid.put(entityIn.getUniqueID(), entityIn);
/* 804 */     Entity[] aentity = entityIn.getParts();
/*     */     
/* 806 */     if (aentity != null) {
/* 807 */       for (int i = 0; i < aentity.length; i++) {
/* 808 */         this.entitiesById.addKey(aentity[i].getEntityId(), aentity[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onEntityRemoved(Entity entityIn) {
/* 814 */     super.onEntityRemoved(entityIn);
/* 815 */     this.entitiesById.removeObject(entityIn.getEntityId());
/* 816 */     this.entitiesByUuid.remove(entityIn.getUniqueID());
/* 817 */     Entity[] aentity = entityIn.getParts();
/*     */     
/* 819 */     if (aentity != null) {
/* 820 */       for (int i = 0; i < aentity.length; i++) {
/* 821 */         this.entitiesById.removeObject(aentity[i].getEntityId());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addWeatherEffect(Entity entityIn) {
/* 830 */     if (super.addWeatherEffect(entityIn)) {
/* 831 */       this.mcServer.getConfigurationManager().sendToAllNear(entityIn.posX, entityIn.posY, entityIn.posZ, 512.0D, this.provider.getDimensionId(), (Packet)new S2CPacketSpawnGlobalEntity(entityIn));
/* 832 */       return true;
/*     */     } 
/* 834 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityState(Entity entityIn, byte state) {
/* 842 */     getEntityTracker().func_151248_b(entityIn, (Packet)new S19PacketEntityStatus(entityIn, state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Explosion newExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
/* 849 */     Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
/* 850 */     explosion.doExplosionA();
/* 851 */     explosion.doExplosionB(false);
/*     */     
/* 853 */     if (!isSmoking) {
/* 854 */       explosion.clearAffectedBlockPositions();
/*     */     }
/*     */     
/* 857 */     for (EntityPlayer entityplayer : this.playerEntities) {
/* 858 */       if (entityplayer.getDistanceSq(x, y, z) < 4096.0D) {
/* 859 */         ((EntityPlayerMP)entityplayer).playerNetServerHandler.sendPacket((Packet)new S27PacketExplosion(x, y, z, strength, explosion.getAffectedBlockPositions(), explosion.getPlayerKnockbackMap().get(entityplayer)));
/*     */       }
/*     */     } 
/*     */     
/* 863 */     return explosion;
/*     */   }
/*     */   
/*     */   public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
/* 867 */     BlockEventData blockeventdata = new BlockEventData(pos, blockIn, eventID, eventParam);
/*     */     
/* 869 */     for (BlockEventData blockeventdata1 : this.blockEventQueue[this.blockEventCacheIndex]) {
/* 870 */       if (blockeventdata1.equals(blockeventdata)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 875 */     this.blockEventQueue[this.blockEventCacheIndex].add(blockeventdata);
/*     */   }
/*     */   
/*     */   private void sendQueuedBlockEvents() {
/* 879 */     while (!this.blockEventQueue[this.blockEventCacheIndex].isEmpty()) {
/* 880 */       int i = this.blockEventCacheIndex;
/* 881 */       this.blockEventCacheIndex ^= 0x1;
/*     */       
/* 883 */       for (BlockEventData blockeventdata : this.blockEventQueue[i]) {
/* 884 */         if (fireBlockEvent(blockeventdata)) {
/* 885 */           this.mcServer.getConfigurationManager().sendToAllNear(blockeventdata.getPosition().getX(), blockeventdata.getPosition().getY(), blockeventdata.getPosition().getZ(), 64.0D, this.provider.getDimensionId(), (Packet)new S24PacketBlockAction(blockeventdata.getPosition(), blockeventdata.getBlock(), blockeventdata.getEventID(), blockeventdata.getEventParameter()));
/*     */         }
/*     */       } 
/*     */       
/* 889 */       this.blockEventQueue[i].clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean fireBlockEvent(BlockEventData event) {
/* 894 */     IBlockState iblockstate = getBlockState(event.getPosition());
/* 895 */     return (iblockstate.getBlock() == event.getBlock()) ? iblockstate.getBlock().onBlockEventReceived(this, event.getPosition(), iblockstate, event.getEventID(), event.getEventParameter()) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 902 */     this.saveHandler.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateWeather() {
/* 909 */     boolean flag = isRaining();
/* 910 */     super.updateWeather();
/*     */     
/* 912 */     if (this.prevRainingStrength != this.rainingStrength) {
/* 913 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension((Packet)new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionId());
/*     */     }
/*     */     
/* 916 */     if (this.prevThunderingStrength != this.thunderingStrength) {
/* 917 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension((Packet)new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionId());
/*     */     }
/*     */     
/* 920 */     if (flag != isRaining()) {
/* 921 */       if (flag) {
/* 922 */         this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(2, 0.0F));
/*     */       } else {
/* 924 */         this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(1, 0.0F));
/*     */       } 
/*     */       
/* 927 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(7, this.rainingStrength));
/* 928 */       this.mcServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(8, this.thunderingStrength));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getRenderDistanceChunks() {
/* 933 */     return this.mcServer.getConfigurationManager().getViewDistance();
/*     */   }
/*     */   
/*     */   public MinecraftServer getMinecraftServer() {
/* 937 */     return this.mcServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityTracker getEntityTracker() {
/* 944 */     return this.theEntityTracker;
/*     */   }
/*     */   
/*     */   public PlayerManager getPlayerManager() {
/* 948 */     return this.thePlayerManager;
/*     */   }
/*     */   
/*     */   public Teleporter getDefaultTeleporter() {
/* 952 */     return this.worldTeleporter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... particleArguments) {
/* 959 */     spawnParticle(particleType, false, xCoord, yCoord, zCoord, numberOfParticles, xOffset, yOffset, zOffset, particleSpeed, particleArguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnParticle(EnumParticleTypes particleType, boolean longDistance, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... particleArguments) {
/* 966 */     S2APacketParticles s2APacketParticles = new S2APacketParticles(particleType, longDistance, (float)xCoord, (float)yCoord, (float)zCoord, (float)xOffset, (float)yOffset, (float)zOffset, (float)particleSpeed, numberOfParticles, particleArguments);
/*     */     
/* 968 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 969 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)this.playerEntities.get(i);
/* 970 */       BlockPos blockpos = entityplayermp.getPosition();
/* 971 */       double d0 = blockpos.distanceSq(xCoord, yCoord, zCoord);
/*     */       
/* 973 */       if (d0 <= 256.0D || (longDistance && d0 <= 65536.0D)) {
/* 974 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s2APacketParticles);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Entity getEntityFromUuid(UUID uuid) {
/* 980 */     return this.entitiesByUuid.get(uuid);
/*     */   }
/*     */   
/*     */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 984 */     return this.mcServer.addScheduledTask(runnableToSchedule);
/*     */   }
/*     */   
/*     */   public boolean isCallingFromMinecraftThread() {
/* 988 */     return this.mcServer.isCallingFromMinecraftThread();
/*     */   }
/*     */   
/*     */   static class ServerBlockEventList extends ArrayList<BlockEventData> {
/*     */     private ServerBlockEventList() {}
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */