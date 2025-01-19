/*      */ package net.minecraft.world;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.BlockSlab;
/*      */ import net.minecraft.block.BlockSnow;
/*      */ import net.minecraft.block.BlockStairs;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.IntHashMap;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.village.VillageCollection;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.MapStorage;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ 
/*      */ public abstract class World
/*      */   implements IBlockAccess
/*      */ {
/*   59 */   private int seaLevel = 63;
/*      */ 
/*      */   
/*      */   protected boolean scheduledUpdatesAreImmediate;
/*      */ 
/*      */   
/*   65 */   public final List<Entity> loadedEntityList = Lists.newArrayList();
/*   66 */   protected final List<Entity> unloadedEntityList = Lists.newArrayList();
/*   67 */   public final List<TileEntity> loadedTileEntityList = Lists.newArrayList();
/*   68 */   public final List<TileEntity> tickableTileEntities = Lists.newArrayList();
/*   69 */   private final List<TileEntity> addedTileEntityList = Lists.newArrayList();
/*   70 */   private final List<TileEntity> tileEntitiesToBeRemoved = Lists.newArrayList();
/*   71 */   public final List<EntityPlayer> playerEntities = Lists.newArrayList();
/*   72 */   public final List<Entity> weatherEffects = Lists.newArrayList();
/*   73 */   protected final IntHashMap<Entity> entitiesById = new IntHashMap();
/*   74 */   private long cloudColour = 16777215L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skylightSubtracted;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   86 */   protected int updateLCG = (new Random()).nextInt();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   91 */   protected final int DIST_HASH_MAGIC = 1013904223;
/*      */ 
/*      */   
/*      */   protected float prevRainingStrength;
/*      */ 
/*      */   
/*      */   protected float rainingStrength;
/*      */ 
/*      */   
/*      */   protected float prevThunderingStrength;
/*      */   
/*      */   protected float thunderingStrength;
/*      */   
/*      */   private int lastLightningBolt;
/*      */   
/*  106 */   public final Random rand = new Random();
/*      */ 
/*      */   
/*      */   public final WorldProvider provider;
/*      */ 
/*      */   
/*  112 */   protected List<IWorldAccess> worldAccesses = Lists.newArrayList();
/*      */ 
/*      */   
/*      */   protected IChunkProvider chunkProvider;
/*      */ 
/*      */   
/*      */   protected final ISaveHandler saveHandler;
/*      */ 
/*      */   
/*      */   protected WorldInfo worldInfo;
/*      */ 
/*      */   
/*      */   protected boolean findingSpawnPoint;
/*      */ 
/*      */   
/*      */   protected MapStorage mapStorage;
/*      */   
/*      */   protected VillageCollection villageCollectionObj;
/*      */   
/*      */   public final Profiler theProfiler;
/*      */   
/*  133 */   private final Calendar theCalendar = Calendar.getInstance();
/*  134 */   protected Scoreboard worldScoreboard = new Scoreboard();
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isRemote;
/*      */ 
/*      */   
/*  141 */   protected Set<ChunkCoordIntPair> activeChunkSet = Sets.newHashSet();
/*      */ 
/*      */ 
/*      */   
/*      */   private int ambientTickCountdown;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean spawnHostileMobs;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean spawnPeacefulMobs;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean processingLoadedTiles;
/*      */ 
/*      */ 
/*      */   
/*      */   private final WorldBorder worldBorder;
/*      */ 
/*      */   
/*      */   int[] lightUpdateBlockList;
/*      */ 
/*      */ 
/*      */   
/*      */   protected World(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
/*  169 */     this.ambientTickCountdown = this.rand.nextInt(12000);
/*  170 */     this.spawnHostileMobs = true;
/*  171 */     this.spawnPeacefulMobs = true;
/*  172 */     this.lightUpdateBlockList = new int[32768];
/*  173 */     this.saveHandler = saveHandlerIn;
/*  174 */     this.theProfiler = profilerIn;
/*  175 */     this.worldInfo = info;
/*  176 */     this.provider = providerIn;
/*  177 */     this.isRemote = client;
/*  178 */     this.worldBorder = providerIn.getWorldBorder();
/*      */   }
/*      */   
/*      */   public World init() {
/*  182 */     return this;
/*      */   }
/*      */   
/*      */   public BiomeGenBase getBiomeGenForCoords(final BlockPos pos) {
/*  186 */     if (isBlockLoaded(pos)) {
/*  187 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*      */       
/*      */       try {
/*  190 */         return chunk.getBiome(pos, this.provider.getWorldChunkManager());
/*  191 */       } catch (Throwable throwable) {
/*  192 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting biome");
/*  193 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Coordinates of biome request");
/*  194 */         crashreportcategory.addCrashSectionCallable("Location", new Callable<String>() {
/*      */               public String call() throws Exception {
/*  196 */                 return CrashReportCategory.getCoordinateInfo(pos);
/*      */               }
/*      */             });
/*  199 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*  202 */     return this.provider.getWorldChunkManager().getBiomeGenerator(pos, BiomeGenBase.plains);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldChunkManager getWorldChunkManager() {
/*  207 */     return this.provider.getWorldChunkManager();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract IChunkProvider createChunkProvider();
/*      */ 
/*      */   
/*      */   public void initialize(WorldSettings settings) {
/*  216 */     this.worldInfo.setServerInitialized(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialSpawnLocation() {
/*  223 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*      */   }
/*      */ 
/*      */   
/*      */   public Block getGroundAboveSeaLevel(BlockPos pos) {
/*      */     BlockPos blockpos;
/*  229 */     for (blockpos = new BlockPos(pos.getX(), getSeaLevel(), pos.getZ()); !isAirBlock(blockpos.up()); blockpos = blockpos.up());
/*      */ 
/*      */ 
/*      */     
/*  233 */     return getBlockState(blockpos).getBlock();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValid(BlockPos pos) {
/*  240 */     return (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000 && pos.getY() >= 0 && pos.getY() < 256);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAirBlock(BlockPos pos) {
/*  248 */     return (getBlockState(pos).getBlock().getMaterial() == Material.air);
/*      */   }
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos) {
/*  252 */     return isBlockLoaded(pos, true);
/*      */   }
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos, boolean allowEmpty) {
/*  256 */     return !isValid(pos) ? false : isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, allowEmpty);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius) {
/*  260 */     return isAreaLoaded(center, radius, true);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty) {
/*  264 */     return isAreaLoaded(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius, allowEmpty);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to) {
/*  268 */     return isAreaLoaded(from, to, true);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to, boolean allowEmpty) {
/*  272 */     return isAreaLoaded(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ(), allowEmpty);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box) {
/*  276 */     return isAreaLoaded(box, true);
/*      */   }
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box, boolean allowEmpty) {
/*  280 */     return isAreaLoaded(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, allowEmpty);
/*      */   }
/*      */   
/*      */   private boolean isAreaLoaded(int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd, boolean allowEmpty) {
/*  284 */     if (yEnd >= 0 && yStart < 256) {
/*  285 */       xStart >>= 4;
/*  286 */       zStart >>= 4;
/*  287 */       xEnd >>= 4;
/*  288 */       zEnd >>= 4;
/*      */       
/*  290 */       for (int i = xStart; i <= xEnd; i++) {
/*  291 */         for (int j = zStart; j <= zEnd; j++) {
/*  292 */           if (!isChunkLoaded(i, j, allowEmpty)) {
/*  293 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  298 */       return true;
/*      */     } 
/*  300 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
/*  305 */     return (this.chunkProvider.chunkExists(x, z) && (allowEmpty || !this.chunkProvider.provideChunk(x, z).isEmpty()));
/*      */   }
/*      */   
/*      */   public Chunk getChunkFromBlockCoords(BlockPos pos) {
/*  309 */     return getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ) {
/*  316 */     return this.chunkProvider.provideChunk(chunkX, chunkZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/*  325 */     if (!isValid(pos))
/*  326 */       return false; 
/*  327 */     if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/*  328 */       return false;
/*      */     }
/*  330 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  331 */     Block block = newState.getBlock();
/*  332 */     IBlockState iblockstate = chunk.setBlockState(pos, newState);
/*      */     
/*  334 */     if (iblockstate == null) {
/*  335 */       return false;
/*      */     }
/*  337 */     Block block1 = iblockstate.getBlock();
/*      */     
/*  339 */     if (block.getLightOpacity() != block1.getLightOpacity() || block.getLightValue() != block1.getLightValue()) {
/*  340 */       this.theProfiler.startSection("checkLight");
/*  341 */       checkLight(pos);
/*  342 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  345 */     if ((flags & 0x2) != 0 && (!this.isRemote || (flags & 0x4) == 0) && chunk.isPopulated()) {
/*  346 */       markBlockForUpdate(pos);
/*      */     }
/*      */     
/*  349 */     if (!this.isRemote && (flags & 0x1) != 0) {
/*  350 */       notifyNeighborsRespectDebug(pos, iblockstate.getBlock());
/*      */       
/*  352 */       if (block.hasComparatorInputOverride()) {
/*  353 */         updateComparatorOutputLevel(pos, block);
/*      */       }
/*      */     } 
/*      */     
/*  357 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockToAir(BlockPos pos) {
/*  363 */     return setBlockState(pos, Blocks.air.getDefaultState(), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
/*  370 */     IBlockState iblockstate = getBlockState(pos);
/*  371 */     Block block = iblockstate.getBlock();
/*      */     
/*  373 */     if (block.getMaterial() == Material.air) {
/*  374 */       return false;
/*      */     }
/*  376 */     playAuxSFX(2001, pos, Block.getStateId(iblockstate));
/*      */     
/*  378 */     if (dropBlock) {
/*  379 */       block.dropBlockAsItem(this, pos, iblockstate, 0);
/*      */     }
/*      */     
/*  382 */     return setBlockState(pos, Blocks.air.getDefaultState(), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState state) {
/*  390 */     return setBlockState(pos, state, 3);
/*      */   }
/*      */   
/*      */   public void markBlockForUpdate(BlockPos pos) {
/*  394 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  395 */       ((IWorldAccess)this.worldAccesses.get(i)).markBlockForUpdate(pos);
/*      */     }
/*      */   }
/*      */   
/*      */   public void notifyNeighborsRespectDebug(BlockPos pos, Block blockType) {
/*  400 */     if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
/*  401 */       notifyNeighborsOfStateChange(pos, blockType);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2) {
/*  409 */     if (x2 > z2) {
/*  410 */       int i = z2;
/*  411 */       z2 = x2;
/*  412 */       x2 = i;
/*      */     } 
/*      */     
/*  415 */     if (!this.provider.getHasNoSky()) {
/*  416 */       for (int j = x2; j <= z2; j++) {
/*  417 */         checkLightFor(EnumSkyBlock.SKY, new BlockPos(x1, j, z1));
/*      */       }
/*      */     }
/*      */     
/*  421 */     markBlockRangeForRenderUpdate(x1, x2, z1, x1, z2, z1);
/*      */   }
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax) {
/*  425 */     markBlockRangeForRenderUpdate(rangeMin.getX(), rangeMin.getY(), rangeMin.getZ(), rangeMax.getX(), rangeMax.getY(), rangeMax.getZ());
/*      */   }
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/*  429 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  430 */       ((IWorldAccess)this.worldAccesses.get(i)).markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType) {
/*  435 */     notifyBlockOfStateChange(pos.west(), blockType);
/*  436 */     notifyBlockOfStateChange(pos.east(), blockType);
/*  437 */     notifyBlockOfStateChange(pos.down(), blockType);
/*  438 */     notifyBlockOfStateChange(pos.up(), blockType);
/*  439 */     notifyBlockOfStateChange(pos.north(), blockType);
/*  440 */     notifyBlockOfStateChange(pos.south(), blockType);
/*      */   }
/*      */   
/*      */   public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide) {
/*  444 */     if (skipSide != EnumFacing.WEST) {
/*  445 */       notifyBlockOfStateChange(pos.west(), blockType);
/*      */     }
/*      */     
/*  448 */     if (skipSide != EnumFacing.EAST) {
/*  449 */       notifyBlockOfStateChange(pos.east(), blockType);
/*      */     }
/*      */     
/*  452 */     if (skipSide != EnumFacing.DOWN) {
/*  453 */       notifyBlockOfStateChange(pos.down(), blockType);
/*      */     }
/*      */     
/*  456 */     if (skipSide != EnumFacing.UP) {
/*  457 */       notifyBlockOfStateChange(pos.up(), blockType);
/*      */     }
/*      */     
/*  460 */     if (skipSide != EnumFacing.NORTH) {
/*  461 */       notifyBlockOfStateChange(pos.north(), blockType);
/*      */     }
/*      */     
/*  464 */     if (skipSide != EnumFacing.SOUTH) {
/*  465 */       notifyBlockOfStateChange(pos.south(), blockType);
/*      */     }
/*      */   }
/*      */   
/*      */   public void notifyBlockOfStateChange(BlockPos pos, final Block blockIn) {
/*  470 */     if (!this.isRemote) {
/*  471 */       IBlockState iblockstate = getBlockState(pos);
/*      */       
/*      */       try {
/*  474 */         iblockstate.getBlock().onNeighborBlockChange(this, pos, iblockstate, blockIn);
/*  475 */       } catch (Throwable throwable) {
/*  476 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while updating neighbours");
/*  477 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being updated");
/*  478 */         crashreportcategory.addCrashSectionCallable("Source block type", new Callable<String>() {
/*      */               public String call() throws Exception {
/*      */                 try {
/*  481 */                   return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(Block.getIdFromBlock(this.val$blockIn)), this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName() });
/*  482 */                 } catch (Throwable var2) {
/*  483 */                   return "ID #" + Block.getIdFromBlock(blockIn);
/*      */                 } 
/*      */               }
/*      */             });
/*  487 */         CrashReportCategory.addBlockInfo(crashreportcategory, pos, iblockstate);
/*  488 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isBlockTickPending(BlockPos pos, Block blockType) {
/*  494 */     return false;
/*      */   }
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  498 */     return getChunkFromBlockCoords(pos).canSeeSky(pos);
/*      */   }
/*      */   
/*      */   public boolean canBlockSeeSky(BlockPos pos) {
/*  502 */     if (pos.getY() >= getSeaLevel()) {
/*  503 */       return canSeeSky(pos);
/*      */     }
/*  505 */     BlockPos blockpos = new BlockPos(pos.getX(), getSeaLevel(), pos.getZ());
/*      */     
/*  507 */     if (!canSeeSky(blockpos)) {
/*  508 */       return false;
/*      */     }
/*  510 */     for (blockpos = blockpos.down(); blockpos.getY() > pos.getY(); blockpos = blockpos.down()) {
/*  511 */       Block block = getBlockState(blockpos).getBlock();
/*      */       
/*  513 */       if (block.getLightOpacity() > 0 && !block.getMaterial().isLiquid()) {
/*  514 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  518 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLight(BlockPos pos) {
/*  524 */     if (pos.getY() < 0) {
/*  525 */       return 0;
/*      */     }
/*  527 */     if (pos.getY() >= 256) {
/*  528 */       pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */     }
/*      */     
/*  531 */     return getChunkFromBlockCoords(pos).getLightSubtracted(pos, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightFromNeighbors(BlockPos pos) {
/*  536 */     return getLight(pos, true);
/*      */   }
/*      */   
/*      */   public int getLight(BlockPos pos, boolean checkNeighbors) {
/*  540 */     if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000) {
/*  541 */       if (checkNeighbors && getBlockState(pos).getBlock().getUseNeighborBrightness()) {
/*  542 */         int i1 = getLight(pos.up(), false);
/*  543 */         int i = getLight(pos.east(), false);
/*  544 */         int j = getLight(pos.west(), false);
/*  545 */         int k = getLight(pos.south(), false);
/*  546 */         int l = getLight(pos.north(), false);
/*      */         
/*  548 */         if (i > i1) {
/*  549 */           i1 = i;
/*      */         }
/*      */         
/*  552 */         if (j > i1) {
/*  553 */           i1 = j;
/*      */         }
/*      */         
/*  556 */         if (k > i1) {
/*  557 */           i1 = k;
/*      */         }
/*      */         
/*  560 */         if (l > i1) {
/*  561 */           i1 = l;
/*      */         }
/*      */         
/*  564 */         return i1;
/*  565 */       }  if (pos.getY() < 0) {
/*  566 */         return 0;
/*      */       }
/*  568 */       if (pos.getY() >= 256) {
/*  569 */         pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */       }
/*      */       
/*  572 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*  573 */       return chunk.getLightSubtracted(pos, this.skylightSubtracted);
/*      */     } 
/*      */     
/*  576 */     return 15;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getHeight(BlockPos pos) {
/*      */     int i;
/*  586 */     if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000) {
/*  587 */       if (isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, true)) {
/*  588 */         i = getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4).getHeightValue(pos.getX() & 0xF, pos.getZ() & 0xF);
/*      */       } else {
/*  590 */         i = 0;
/*      */       } 
/*      */     } else {
/*  593 */       i = getSeaLevel() + 1;
/*      */     } 
/*      */     
/*  596 */     return new BlockPos(pos.getX(), i, pos.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChunksLowestHorizon(int x, int z) {
/*  603 */     if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000) {
/*  604 */       if (!isChunkLoaded(x >> 4, z >> 4, true)) {
/*  605 */         return 0;
/*      */       }
/*  607 */       Chunk chunk = getChunkFromChunkCoords(x >> 4, z >> 4);
/*  608 */       return chunk.getLowestHeight();
/*      */     } 
/*      */     
/*  611 */     return getSeaLevel() + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
/*  616 */     if (this.provider.getHasNoSky() && type == EnumSkyBlock.SKY) {
/*  617 */       return 0;
/*      */     }
/*  619 */     if (pos.getY() < 0) {
/*  620 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  623 */     if (!isValid(pos))
/*  624 */       return type.defaultLightValue; 
/*  625 */     if (!isBlockLoaded(pos))
/*  626 */       return type.defaultLightValue; 
/*  627 */     if (getBlockState(pos).getBlock().getUseNeighborBrightness()) {
/*  628 */       int i1 = getLightFor(type, pos.up());
/*  629 */       int i = getLightFor(type, pos.east());
/*  630 */       int j = getLightFor(type, pos.west());
/*  631 */       int k = getLightFor(type, pos.south());
/*  632 */       int l = getLightFor(type, pos.north());
/*      */       
/*  634 */       if (i > i1) {
/*  635 */         i1 = i;
/*      */       }
/*      */       
/*  638 */       if (j > i1) {
/*  639 */         i1 = j;
/*      */       }
/*      */       
/*  642 */       if (k > i1) {
/*  643 */         i1 = k;
/*      */       }
/*      */       
/*  646 */       if (l > i1) {
/*  647 */         i1 = l;
/*      */       }
/*      */       
/*  650 */       return i1;
/*      */     } 
/*  652 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  653 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock type, BlockPos pos) {
/*  659 */     if (pos.getY() < 0) {
/*  660 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  663 */     if (!isValid(pos))
/*  664 */       return type.defaultLightValue; 
/*  665 */     if (!isBlockLoaded(pos)) {
/*  666 */       return type.defaultLightValue;
/*      */     }
/*  668 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  669 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue) {
/*  674 */     if (isValid(pos) && 
/*  675 */       isBlockLoaded(pos)) {
/*  676 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*  677 */       chunk.setLightFor(type, pos, lightValue);
/*  678 */       notifyLightSet(pos);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/*  684 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  685 */       ((IWorldAccess)this.worldAccesses.get(i)).notifyLightSet(pos);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  690 */     int i = getLightFromNeighborsFor(EnumSkyBlock.SKY, pos);
/*  691 */     int j = getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos);
/*      */     
/*  693 */     if (j < lightValue) {
/*  694 */       j = lightValue;
/*      */     }
/*      */     
/*  697 */     return i << 20 | j << 4;
/*      */   }
/*      */   
/*      */   public float getLightBrightness(BlockPos pos) {
/*  701 */     return this.provider.getLightBrightnessTable()[getLightFromNeighbors(pos)];
/*      */   }
/*      */   
/*      */   public IBlockState getBlockState(BlockPos pos) {
/*  705 */     if (!isValid(pos)) {
/*  706 */       return Blocks.air.getDefaultState();
/*      */     }
/*  708 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  709 */     return chunk.getBlockState(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDaytime() {
/*  717 */     return (this.skylightSubtracted < 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_) {
/*  724 */     return rayTraceBlocks(p_72933_1_, p_72933_2_, false, false, false);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 start, Vec3 end, boolean stopOnLiquid) {
/*  728 */     return rayTraceBlocks(start, end, stopOnLiquid, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTraceBlocks(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/*  736 */     if (!Double.isNaN(vec31.xCoord) && !Double.isNaN(vec31.yCoord) && !Double.isNaN(vec31.zCoord)) {
/*  737 */       if (!Double.isNaN(vec32.xCoord) && !Double.isNaN(vec32.yCoord) && !Double.isNaN(vec32.zCoord)) {
/*  738 */         int i = MathHelper.floor_double(vec32.xCoord);
/*  739 */         int j = MathHelper.floor_double(vec32.yCoord);
/*  740 */         int k = MathHelper.floor_double(vec32.zCoord);
/*  741 */         int l = MathHelper.floor_double(vec31.xCoord);
/*  742 */         int i1 = MathHelper.floor_double(vec31.yCoord);
/*  743 */         int j1 = MathHelper.floor_double(vec31.zCoord);
/*  744 */         BlockPos blockpos = new BlockPos(l, i1, j1);
/*  745 */         IBlockState iblockstate = getBlockState(blockpos);
/*  746 */         Block block = iblockstate.getBlock();
/*      */         
/*  748 */         if ((!ignoreBlockWithoutBoundingBox || block.getCollisionBoundingBox(this, blockpos, iblockstate) != null) && block.canCollideCheck(iblockstate, stopOnLiquid)) {
/*  749 */           MovingObjectPosition movingobjectposition = block.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */           
/*  751 */           if (movingobjectposition != null) {
/*  752 */             return movingobjectposition;
/*      */           }
/*      */         } 
/*      */         
/*  756 */         MovingObjectPosition movingobjectposition2 = null;
/*  757 */         int k1 = 200;
/*      */         
/*  759 */         while (k1-- >= 0) {
/*  760 */           EnumFacing enumfacing; if (Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord)) {
/*  761 */             return null;
/*      */           }
/*      */           
/*  764 */           if (l == i && i1 == j && j1 == k) {
/*  765 */             return returnLastUncollidableBlock ? movingobjectposition2 : null;
/*      */           }
/*      */           
/*  768 */           boolean flag2 = true;
/*  769 */           boolean flag = true;
/*  770 */           boolean flag1 = true;
/*  771 */           double d0 = 999.0D;
/*  772 */           double d1 = 999.0D;
/*  773 */           double d2 = 999.0D;
/*      */           
/*  775 */           if (i > l) {
/*  776 */             d0 = l + 1.0D;
/*  777 */           } else if (i < l) {
/*  778 */             d0 = l + 0.0D;
/*      */           } else {
/*  780 */             flag2 = false;
/*      */           } 
/*      */           
/*  783 */           if (j > i1) {
/*  784 */             d1 = i1 + 1.0D;
/*  785 */           } else if (j < i1) {
/*  786 */             d1 = i1 + 0.0D;
/*      */           } else {
/*  788 */             flag = false;
/*      */           } 
/*      */           
/*  791 */           if (k > j1) {
/*  792 */             d2 = j1 + 1.0D;
/*  793 */           } else if (k < j1) {
/*  794 */             d2 = j1 + 0.0D;
/*      */           } else {
/*  796 */             flag1 = false;
/*      */           } 
/*      */           
/*  799 */           double d3 = 999.0D;
/*  800 */           double d4 = 999.0D;
/*  801 */           double d5 = 999.0D;
/*  802 */           double d6 = vec32.xCoord - vec31.xCoord;
/*  803 */           double d7 = vec32.yCoord - vec31.yCoord;
/*  804 */           double d8 = vec32.zCoord - vec31.zCoord;
/*      */           
/*  806 */           if (flag2) {
/*  807 */             d3 = (d0 - vec31.xCoord) / d6;
/*      */           }
/*      */           
/*  810 */           if (flag) {
/*  811 */             d4 = (d1 - vec31.yCoord) / d7;
/*      */           }
/*      */           
/*  814 */           if (flag1) {
/*  815 */             d5 = (d2 - vec31.zCoord) / d8;
/*      */           }
/*      */           
/*  818 */           if (d3 == -0.0D) {
/*  819 */             d3 = -1.0E-4D;
/*      */           }
/*      */           
/*  822 */           if (d4 == -0.0D) {
/*  823 */             d4 = -1.0E-4D;
/*      */           }
/*      */           
/*  826 */           if (d5 == -0.0D) {
/*  827 */             d5 = -1.0E-4D;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  832 */           if (d3 < d4 && d3 < d5) {
/*  833 */             enumfacing = (i > l) ? EnumFacing.WEST : EnumFacing.EAST;
/*  834 */             vec31 = new Vec3(d0, vec31.yCoord + d7 * d3, vec31.zCoord + d8 * d3);
/*  835 */           } else if (d4 < d5) {
/*  836 */             enumfacing = (j > i1) ? EnumFacing.DOWN : EnumFacing.UP;
/*  837 */             vec31 = new Vec3(vec31.xCoord + d6 * d4, d1, vec31.zCoord + d8 * d4);
/*      */           } else {
/*  839 */             enumfacing = (k > j1) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/*  840 */             vec31 = new Vec3(vec31.xCoord + d6 * d5, vec31.yCoord + d7 * d5, d2);
/*      */           } 
/*      */           
/*  843 */           l = MathHelper.floor_double(vec31.xCoord) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
/*  844 */           i1 = MathHelper.floor_double(vec31.yCoord) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
/*  845 */           j1 = MathHelper.floor_double(vec31.zCoord) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
/*  846 */           blockpos = new BlockPos(l, i1, j1);
/*  847 */           IBlockState iblockstate1 = getBlockState(blockpos);
/*  848 */           Block block1 = iblockstate1.getBlock();
/*      */           
/*  850 */           if (!ignoreBlockWithoutBoundingBox || block1.getCollisionBoundingBox(this, blockpos, iblockstate1) != null) {
/*  851 */             if (block1.canCollideCheck(iblockstate1, stopOnLiquid)) {
/*  852 */               MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */               
/*  854 */               if (movingobjectposition1 != null)
/*  855 */                 return movingobjectposition1; 
/*      */               continue;
/*      */             } 
/*  858 */             movingobjectposition2 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec31, enumfacing, blockpos);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  863 */         return returnLastUncollidableBlock ? movingobjectposition2 : null;
/*      */       } 
/*  865 */       return null;
/*      */     } 
/*      */     
/*  868 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundAtEntity(Entity entityIn, String name, float volume, float pitch) {
/*  877 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  878 */       ((IWorldAccess)this.worldAccesses.get(i)).playSound(name, entityIn.posX, entityIn.posY, entityIn.posZ, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundToNearExcept(EntityPlayer player, String name, float volume, float pitch) {
/*  886 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  887 */       ((IWorldAccess)this.worldAccesses.get(i)).playSoundToNearExcept(player, name, player.posX, player.posY, player.posZ, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch) {
/*  897 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  898 */       ((IWorldAccess)this.worldAccesses.get(i)).playSound(soundName, x, y, z, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void playRecord(BlockPos pos, String name) {
/*  909 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  910 */       ((IWorldAccess)this.worldAccesses.get(i)).playRecord(name, pos);
/*      */     }
/*      */   }
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175688_14_) {
/*  915 */     spawnParticle(particleType.getParticleID(), particleType.getShouldIgnoreRange(), xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175688_14_);
/*      */   }
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175682_15_) {
/*  919 */     spawnParticle(particleType.getParticleID(), particleType.getShouldIgnoreRange() | p_175682_2_, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_175682_15_);
/*      */   }
/*      */   
/*      */   private void spawnParticle(int particleID, boolean p_175720_2_, double xCood, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175720_15_) {
/*  923 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  924 */       ((IWorldAccess)this.worldAccesses.get(i)).spawnParticle(particleID, p_175720_2_, xCood, yCoord, zCoord, xOffset, yOffset, zOffset, p_175720_15_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addWeatherEffect(Entity entityIn) {
/*  932 */     this.weatherEffects.add(entityIn);
/*  933 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean spawnEntityInWorld(Entity entityIn) {
/*  940 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/*  941 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*  942 */     boolean flag = entityIn.forceSpawn;
/*      */     
/*  944 */     if (entityIn instanceof EntityPlayer) {
/*  945 */       flag = true;
/*      */     }
/*      */     
/*  948 */     if (!flag && !isChunkLoaded(i, j, true)) {
/*  949 */       return false;
/*      */     }
/*  951 */     if (entityIn instanceof EntityPlayer) {
/*  952 */       EntityPlayer entityplayer = (EntityPlayer)entityIn;
/*  953 */       this.playerEntities.add(entityplayer);
/*  954 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/*  957 */     getChunkFromChunkCoords(i, j).addEntity(entityIn);
/*  958 */     this.loadedEntityList.add(entityIn);
/*  959 */     onEntityAdded(entityIn);
/*  960 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEntityAdded(Entity entityIn) {
/*  965 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  966 */       ((IWorldAccess)this.worldAccesses.get(i)).onEntityAdded(entityIn);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void onEntityRemoved(Entity entityIn) {
/*  971 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/*  972 */       ((IWorldAccess)this.worldAccesses.get(i)).onEntityRemoved(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/*  980 */     if (entityIn.riddenByEntity != null) {
/*  981 */       entityIn.riddenByEntity.mountEntity(null);
/*      */     }
/*      */     
/*  984 */     if (entityIn.ridingEntity != null) {
/*  985 */       entityIn.mountEntity(null);
/*      */     }
/*      */     
/*  988 */     entityIn.setDead();
/*      */     
/*  990 */     if (entityIn instanceof EntityPlayer) {
/*  991 */       this.playerEntities.remove(entityIn);
/*  992 */       updateAllPlayersSleepingFlag();
/*  993 */       onEntityRemoved(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePlayerEntityDangerously(Entity entityIn) {
/* 1001 */     entityIn.setDead();
/*      */     
/* 1003 */     if (entityIn instanceof EntityPlayer) {
/* 1004 */       this.playerEntities.remove(entityIn);
/* 1005 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/* 1008 */     int i = entityIn.chunkCoordX;
/* 1009 */     int j = entityIn.chunkCoordZ;
/*      */     
/* 1011 */     if (entityIn.addedToChunk && isChunkLoaded(i, j, true)) {
/* 1012 */       getChunkFromChunkCoords(i, j).removeEntity(entityIn);
/*      */     }
/*      */     
/* 1015 */     this.loadedEntityList.remove(entityIn);
/* 1016 */     onEntityRemoved(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addWorldAccess(IWorldAccess worldAccess) {
/* 1023 */     this.worldAccesses.add(worldAccess);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeWorldAccess(IWorldAccess worldAccess) {
/* 1030 */     this.worldAccesses.remove(worldAccess);
/*      */   }
/*      */   
/*      */   public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity entityIn, AxisAlignedBB bb) {
/* 1034 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 1035 */     int i = MathHelper.floor_double(bb.minX);
/* 1036 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1037 */     int k = MathHelper.floor_double(bb.minY);
/* 1038 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1039 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1040 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1041 */     WorldBorder worldborder = getWorldBorder();
/* 1042 */     boolean flag = entityIn.isOutsideBorder();
/* 1043 */     boolean flag1 = isInsideBorder(worldborder, entityIn);
/* 1044 */     IBlockState iblockstate = Blocks.stone.getDefaultState();
/* 1045 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1047 */     for (int k1 = i; k1 < j; k1++) {
/* 1048 */       for (int l1 = i1; l1 < j1; l1++) {
/* 1049 */         if (isBlockLoaded((BlockPos)blockpos$mutableblockpos.set(k1, 64, l1))) {
/* 1050 */           for (int i2 = k - 1; i2 < l; i2++) {
/* 1051 */             blockpos$mutableblockpos.set(k1, i2, l1);
/*      */             
/* 1053 */             if (flag && flag1) {
/* 1054 */               entityIn.setOutsideBorder(false);
/* 1055 */             } else if (!flag && !flag1) {
/* 1056 */               entityIn.setOutsideBorder(true);
/*      */             } 
/*      */             
/* 1059 */             IBlockState iblockstate1 = iblockstate;
/*      */             
/* 1061 */             if (worldborder.contains((BlockPos)blockpos$mutableblockpos) || !flag1) {
/* 1062 */               iblockstate1 = getBlockState((BlockPos)blockpos$mutableblockpos);
/*      */             }
/*      */             
/* 1065 */             iblockstate1.getBlock().addCollisionBoxesToList(this, (BlockPos)blockpos$mutableblockpos, iblockstate1, bb, list, entityIn);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1071 */     double d0 = 0.25D;
/* 1072 */     List<Entity> list1 = getEntitiesWithinAABBExcludingEntity(entityIn, bb.expand(d0, d0, d0));
/*      */     
/* 1074 */     for (int j2 = 0; j2 < list1.size(); j2++) {
/* 1075 */       if (entityIn.riddenByEntity != list1 && entityIn.ridingEntity != list1) {
/* 1076 */         AxisAlignedBB axisalignedbb = ((Entity)list1.get(j2)).getCollisionBoundingBox();
/*      */         
/* 1078 */         if (axisalignedbb != null && axisalignedbb.intersectsWith(bb)) {
/* 1079 */           list.add(axisalignedbb);
/*      */         }
/*      */         
/* 1082 */         axisalignedbb = entityIn.getCollisionBox(list1.get(j2));
/*      */         
/* 1084 */         if (axisalignedbb != null && axisalignedbb.intersectsWith(bb)) {
/* 1085 */           list.add(axisalignedbb);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1090 */     return list;
/*      */   }
/*      */   
/*      */   public boolean isInsideBorder(WorldBorder worldBorderIn, Entity entityIn) {
/* 1094 */     double d0 = worldBorderIn.minX();
/* 1095 */     double d1 = worldBorderIn.minZ();
/* 1096 */     double d2 = worldBorderIn.maxX();
/* 1097 */     double d3 = worldBorderIn.maxZ();
/*      */     
/* 1099 */     if (entityIn.isOutsideBorder()) {
/* 1100 */       d0++;
/* 1101 */       d1++;
/* 1102 */       d2--;
/* 1103 */       d3--;
/*      */     } else {
/* 1105 */       d0--;
/* 1106 */       d1--;
/* 1107 */       d2++;
/* 1108 */       d3++;
/*      */     } 
/*      */     
/* 1111 */     return (entityIn.posX > d0 && entityIn.posX < d2 && entityIn.posZ > d1 && entityIn.posZ < d3);
/*      */   }
/*      */   
/*      */   public List<AxisAlignedBB> getCollisionBoxes(AxisAlignedBB bb) {
/* 1115 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 1116 */     int i = MathHelper.floor_double(bb.minX);
/* 1117 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1118 */     int k = MathHelper.floor_double(bb.minY);
/* 1119 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1120 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1121 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1122 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1124 */     for (int k1 = i; k1 < j; k1++) {
/* 1125 */       for (int l1 = i1; l1 < j1; l1++) {
/* 1126 */         if (isBlockLoaded((BlockPos)blockpos$mutableblockpos.set(k1, 64, l1))) {
/* 1127 */           for (int i2 = k - 1; i2 < l; i2++) {
/* 1128 */             IBlockState iblockstate; blockpos$mutableblockpos.set(k1, i2, l1);
/*      */ 
/*      */             
/* 1131 */             if (k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000) {
/* 1132 */               iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/*      */             } else {
/* 1134 */               iblockstate = Blocks.bedrock.getDefaultState();
/*      */             } 
/*      */             
/* 1137 */             iblockstate.getBlock().addCollisionBoxesToList(this, (BlockPos)blockpos$mutableblockpos, iblockstate, bb, list, null);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1143 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int calculateSkylightSubtracted(float p_72967_1_) {
/* 1150 */     float f = getCelestialAngle(p_72967_1_);
/* 1151 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1152 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1153 */     f1 = 1.0F - f1;
/* 1154 */     f1 = (float)(f1 * (1.0D - (getRainStrength(p_72967_1_) * 5.0F) / 16.0D));
/* 1155 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(p_72967_1_) * 5.0F) / 16.0D));
/* 1156 */     f1 = 1.0F - f1;
/* 1157 */     return (int)(f1 * 11.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSunBrightness(float p_72971_1_) {
/* 1164 */     float f = getCelestialAngle(p_72971_1_);
/* 1165 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.2F;
/* 1166 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1167 */     f1 = 1.0F - f1;
/* 1168 */     f1 = (float)(f1 * (1.0D - (getRainStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1169 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1170 */     return f1 * 0.8F + 0.2F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getSkyColor(Entity entityIn, float partialTicks) {
/* 1177 */     float f = getCelestialAngle(partialTicks);
/* 1178 */     float f1 = MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1179 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1180 */     int i = MathHelper.floor_double(entityIn.posX);
/* 1181 */     int j = MathHelper.floor_double(entityIn.posY);
/* 1182 */     int k = MathHelper.floor_double(entityIn.posZ);
/* 1183 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1184 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(blockpos);
/* 1185 */     float f2 = biomegenbase.getFloatTemperature(blockpos);
/* 1186 */     int l = biomegenbase.getSkyColorByTemp(f2);
/* 1187 */     float f3 = (l >> 16 & 0xFF) / 255.0F;
/* 1188 */     float f4 = (l >> 8 & 0xFF) / 255.0F;
/* 1189 */     float f5 = (l & 0xFF) / 255.0F;
/* 1190 */     f3 *= f1;
/* 1191 */     f4 *= f1;
/* 1192 */     f5 *= f1;
/* 1193 */     float f6 = getRainStrength(partialTicks);
/*      */     
/* 1195 */     if (f6 > 0.0F) {
/* 1196 */       float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
/* 1197 */       float f8 = 1.0F - f6 * 0.75F;
/* 1198 */       f3 = f3 * f8 + f7 * (1.0F - f8);
/* 1199 */       f4 = f4 * f8 + f7 * (1.0F - f8);
/* 1200 */       f5 = f5 * f8 + f7 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1203 */     float f10 = getThunderStrength(partialTicks);
/*      */     
/* 1205 */     if (f10 > 0.0F) {
/* 1206 */       float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
/* 1207 */       float f9 = 1.0F - f10 * 0.75F;
/* 1208 */       f3 = f3 * f9 + f11 * (1.0F - f9);
/* 1209 */       f4 = f4 * f9 + f11 * (1.0F - f9);
/* 1210 */       f5 = f5 * f9 + f11 * (1.0F - f9);
/*      */     } 
/*      */     
/* 1213 */     if (this.lastLightningBolt > 0) {
/* 1214 */       float f12 = this.lastLightningBolt - partialTicks;
/*      */       
/* 1216 */       if (f12 > 1.0F) {
/* 1217 */         f12 = 1.0F;
/*      */       }
/*      */       
/* 1220 */       f12 *= 0.45F;
/* 1221 */       f3 = f3 * (1.0F - f12) + 0.8F * f12;
/* 1222 */       f4 = f4 * (1.0F - f12) + 0.8F * f12;
/* 1223 */       f5 = f5 * (1.0F - f12) + 1.0F * f12;
/*      */     } 
/*      */     
/* 1226 */     return new Vec3(f3, f4, f5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngle(float partialTicks) {
/* 1233 */     return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), partialTicks);
/*      */   }
/*      */   
/*      */   public int getMoonPhase() {
/* 1237 */     return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCurrentMoonPhaseFactor() {
/* 1244 */     return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngleRadians(float partialTicks) {
/* 1251 */     float f = getCelestialAngle(partialTicks);
/* 1252 */     return f * 3.1415927F * 2.0F;
/*      */   }
/*      */   
/*      */   public Vec3 getCloudColour(float partialTicks) {
/* 1256 */     float f = getCelestialAngle(partialTicks);
/* 1257 */     float f1 = MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F;
/* 1258 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1259 */     float f2 = (float)(this.cloudColour >> 16L & 0xFFL) / 255.0F;
/* 1260 */     float f3 = (float)(this.cloudColour >> 8L & 0xFFL) / 255.0F;
/* 1261 */     float f4 = (float)(this.cloudColour & 0xFFL) / 255.0F;
/* 1262 */     float f5 = getRainStrength(partialTicks);
/*      */     
/* 1264 */     if (f5 > 0.0F) {
/* 1265 */       float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
/* 1266 */       float f7 = 1.0F - f5 * 0.95F;
/* 1267 */       f2 = f2 * f7 + f6 * (1.0F - f7);
/* 1268 */       f3 = f3 * f7 + f6 * (1.0F - f7);
/* 1269 */       f4 = f4 * f7 + f6 * (1.0F - f7);
/*      */     } 
/*      */     
/* 1272 */     f2 *= f1 * 0.9F + 0.1F;
/* 1273 */     f3 *= f1 * 0.9F + 0.1F;
/* 1274 */     f4 *= f1 * 0.85F + 0.15F;
/* 1275 */     float f9 = getThunderStrength(partialTicks);
/*      */     
/* 1277 */     if (f9 > 0.0F) {
/* 1278 */       float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
/* 1279 */       float f8 = 1.0F - f9 * 0.95F;
/* 1280 */       f2 = f2 * f8 + f10 * (1.0F - f8);
/* 1281 */       f3 = f3 * f8 + f10 * (1.0F - f8);
/* 1282 */       f4 = f4 * f8 + f10 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1285 */     return new Vec3(f2, f3, f4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getFogColor(float partialTicks) {
/* 1292 */     float f = getCelestialAngle(partialTicks);
/* 1293 */     return this.provider.getFogColor(f, partialTicks);
/*      */   }
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/* 1297 */     return getChunkFromBlockCoords(pos).getPrecipitationHeight(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
/* 1304 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*      */ 
/*      */ 
/*      */     
/* 1308 */     for (BlockPos blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos1) {
/* 1309 */       BlockPos blockpos1 = blockpos.down();
/* 1310 */       Material material = chunk.getBlock(blockpos1).getMaterial();
/*      */       
/* 1312 */       if (material.blocksMovement() && material != Material.leaves) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 1317 */     return blockpos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getStarBrightness(float partialTicks) {
/* 1324 */     float f = getCelestialAngle(partialTicks);
/* 1325 */     float f1 = 1.0F - MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.25F;
/* 1326 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 1327 */     return f1 * f1 * 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */   
/*      */   public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */   
/*      */   public void updateEntities() {
/* 1343 */     this.theProfiler.startSection("entities");
/* 1344 */     this.theProfiler.startSection("global");
/*      */     
/* 1346 */     for (int i = 0; i < this.weatherEffects.size(); i++) {
/* 1347 */       Entity entity = this.weatherEffects.get(i);
/*      */       
/*      */       try {
/* 1350 */         entity.ticksExisted++;
/* 1351 */         entity.onUpdate();
/* 1352 */       } catch (Throwable throwable2) {
/* 1353 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
/* 1354 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being ticked");
/*      */         
/* 1356 */         if (entity == null) {
/* 1357 */           crashreportcategory.addCrashSection("Entity", "~~NULL~~");
/*      */         } else {
/* 1359 */           entity.addEntityCrashInfo(crashreportcategory);
/*      */         } 
/*      */         
/* 1362 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1365 */       if (entity.isDead) {
/* 1366 */         this.weatherEffects.remove(i--);
/*      */       }
/*      */     } 
/*      */     
/* 1370 */     this.theProfiler.endStartSection("remove");
/* 1371 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*      */     
/* 1373 */     for (int k = 0; k < this.unloadedEntityList.size(); k++) {
/* 1374 */       Entity entity1 = this.unloadedEntityList.get(k);
/* 1375 */       int j = entity1.chunkCoordX;
/* 1376 */       int l1 = entity1.chunkCoordZ;
/*      */       
/* 1378 */       if (entity1.addedToChunk && isChunkLoaded(j, l1, true)) {
/* 1379 */         getChunkFromChunkCoords(j, l1).removeEntity(entity1);
/*      */       }
/*      */     } 
/*      */     
/* 1383 */     for (int l = 0; l < this.unloadedEntityList.size(); l++) {
/* 1384 */       onEntityRemoved(this.unloadedEntityList.get(l));
/*      */     }
/*      */     
/* 1387 */     this.unloadedEntityList.clear();
/* 1388 */     this.theProfiler.endStartSection("regular");
/*      */     
/* 1390 */     for (int i1 = 0; i1 < this.loadedEntityList.size(); i1++) {
/* 1391 */       Entity entity2 = this.loadedEntityList.get(i1);
/*      */       
/* 1393 */       if (entity2.ridingEntity != null) {
/* 1394 */         if (!entity2.ridingEntity.isDead && entity2.ridingEntity.riddenByEntity == entity2) {
/*      */           continue;
/*      */         }
/*      */         
/* 1398 */         entity2.ridingEntity.riddenByEntity = null;
/* 1399 */         entity2.ridingEntity = null;
/*      */       } 
/*      */       
/* 1402 */       this.theProfiler.startSection("tick");
/*      */       
/* 1404 */       if (!entity2.isDead) {
/*      */         try {
/* 1406 */           updateEntity(entity2);
/* 1407 */         } catch (Throwable throwable1) {
/* 1408 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable1, "Ticking entity");
/* 1409 */           CrashReportCategory crashreportcategory2 = crashreport1.makeCategory("Entity being ticked");
/* 1410 */           entity2.addEntityCrashInfo(crashreportcategory2);
/* 1411 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */       
/* 1415 */       this.theProfiler.endSection();
/* 1416 */       this.theProfiler.startSection("remove");
/*      */       
/* 1418 */       if (entity2.isDead) {
/* 1419 */         int k1 = entity2.chunkCoordX;
/* 1420 */         int i2 = entity2.chunkCoordZ;
/*      */         
/* 1422 */         if (entity2.addedToChunk && isChunkLoaded(k1, i2, true)) {
/* 1423 */           getChunkFromChunkCoords(k1, i2).removeEntity(entity2);
/*      */         }
/*      */         
/* 1426 */         this.loadedEntityList.remove(i1--);
/* 1427 */         onEntityRemoved(entity2);
/*      */       } 
/*      */       
/* 1430 */       this.theProfiler.endSection();
/*      */       continue;
/*      */     } 
/* 1433 */     this.theProfiler.endStartSection("blockEntities");
/* 1434 */     this.processingLoadedTiles = true;
/* 1435 */     Iterator<TileEntity> iterator = this.tickableTileEntities.iterator();
/*      */     
/* 1437 */     while (iterator.hasNext()) {
/* 1438 */       TileEntity tileentity = iterator.next();
/*      */       
/* 1440 */       if (!tileentity.isInvalid() && tileentity.hasWorldObj()) {
/* 1441 */         BlockPos blockpos = tileentity.getPos();
/*      */         
/* 1443 */         if (isBlockLoaded(blockpos) && this.worldBorder.contains(blockpos)) {
/*      */           try {
/* 1445 */             ((ITickable)tileentity).update();
/* 1446 */           } catch (Throwable throwable) {
/* 1447 */             CrashReport crashreport2 = CrashReport.makeCrashReport(throwable, "Ticking block entity");
/* 1448 */             CrashReportCategory crashreportcategory1 = crashreport2.makeCategory("Block entity being ticked");
/* 1449 */             tileentity.addInfoToCrashReport(crashreportcategory1);
/* 1450 */             throw new ReportedException(crashreport2);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1455 */       if (tileentity.isInvalid()) {
/* 1456 */         iterator.remove();
/* 1457 */         this.loadedTileEntityList.remove(tileentity);
/*      */         
/* 1459 */         if (isBlockLoaded(tileentity.getPos())) {
/* 1460 */           getChunkFromBlockCoords(tileentity.getPos()).removeTileEntity(tileentity.getPos());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1465 */     this.processingLoadedTiles = false;
/*      */     
/* 1467 */     if (!this.tileEntitiesToBeRemoved.isEmpty()) {
/* 1468 */       this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
/* 1469 */       this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
/* 1470 */       this.tileEntitiesToBeRemoved.clear();
/*      */     } 
/*      */     
/* 1473 */     this.theProfiler.endStartSection("pendingBlockEntities");
/*      */     
/* 1475 */     if (!this.addedTileEntityList.isEmpty()) {
/* 1476 */       for (int j1 = 0; j1 < this.addedTileEntityList.size(); j1++) {
/* 1477 */         TileEntity tileentity1 = this.addedTileEntityList.get(j1);
/*      */         
/* 1479 */         if (!tileentity1.isInvalid()) {
/* 1480 */           if (!this.loadedTileEntityList.contains(tileentity1)) {
/* 1481 */             addTileEntity(tileentity1);
/*      */           }
/*      */           
/* 1484 */           if (isBlockLoaded(tileentity1.getPos())) {
/* 1485 */             getChunkFromBlockCoords(tileentity1.getPos()).addTileEntity(tileentity1.getPos(), tileentity1);
/*      */           }
/*      */           
/* 1488 */           markBlockForUpdate(tileentity1.getPos());
/*      */         } 
/*      */       } 
/*      */       
/* 1492 */       this.addedTileEntityList.clear();
/*      */     } 
/*      */     
/* 1495 */     this.theProfiler.endSection();
/* 1496 */     this.theProfiler.endSection();
/*      */   }
/*      */   
/*      */   public boolean addTileEntity(TileEntity tile) {
/* 1500 */     boolean flag = this.loadedTileEntityList.add(tile);
/*      */     
/* 1502 */     if (flag && tile instanceof ITickable) {
/* 1503 */       this.tickableTileEntities.add(tile);
/*      */     }
/*      */     
/* 1506 */     return flag;
/*      */   }
/*      */   
/*      */   public void addTileEntities(Collection<TileEntity> tileEntityCollection) {
/* 1510 */     if (this.processingLoadedTiles) {
/* 1511 */       this.addedTileEntityList.addAll(tileEntityCollection);
/*      */     } else {
/* 1513 */       for (TileEntity tileentity : tileEntityCollection) {
/* 1514 */         this.loadedTileEntityList.add(tileentity);
/*      */         
/* 1516 */         if (tileentity instanceof ITickable) {
/* 1517 */           this.tickableTileEntities.add(tileentity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntity(Entity ent) {
/* 1527 */     updateEntityWithOptionalForce(ent, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
/* 1535 */     int i = MathHelper.floor_double(entityIn.posX);
/* 1536 */     int j = MathHelper.floor_double(entityIn.posZ);
/* 1537 */     int k = 32;
/*      */     
/* 1539 */     if (!forceUpdate || isAreaLoaded(i - k, 0, j - k, i + k, 0, j + k, true)) {
/* 1540 */       entityIn.lastTickPosX = entityIn.posX;
/* 1541 */       entityIn.lastTickPosY = entityIn.posY;
/* 1542 */       entityIn.lastTickPosZ = entityIn.posZ;
/* 1543 */       entityIn.prevRotationYaw = entityIn.rotationYaw;
/* 1544 */       entityIn.prevRotationPitch = entityIn.rotationPitch;
/*      */       
/* 1546 */       if (forceUpdate && entityIn.addedToChunk) {
/* 1547 */         entityIn.ticksExisted++;
/*      */         
/* 1549 */         if (entityIn.ridingEntity != null) {
/* 1550 */           entityIn.updateRidden();
/*      */         } else {
/* 1552 */           entityIn.onUpdate();
/*      */         } 
/*      */       } 
/*      */       
/* 1556 */       this.theProfiler.startSection("chunkCheck");
/*      */       
/* 1558 */       if (Double.isNaN(entityIn.posX) || Double.isInfinite(entityIn.posX)) {
/* 1559 */         entityIn.posX = entityIn.lastTickPosX;
/*      */       }
/*      */       
/* 1562 */       if (Double.isNaN(entityIn.posY) || Double.isInfinite(entityIn.posY)) {
/* 1563 */         entityIn.posY = entityIn.lastTickPosY;
/*      */       }
/*      */       
/* 1566 */       if (Double.isNaN(entityIn.posZ) || Double.isInfinite(entityIn.posZ)) {
/* 1567 */         entityIn.posZ = entityIn.lastTickPosZ;
/*      */       }
/*      */       
/* 1570 */       if (Double.isNaN(entityIn.rotationPitch) || Double.isInfinite(entityIn.rotationPitch)) {
/* 1571 */         entityIn.rotationPitch = entityIn.prevRotationPitch;
/*      */       }
/*      */       
/* 1574 */       if (Double.isNaN(entityIn.rotationYaw) || Double.isInfinite(entityIn.rotationYaw)) {
/* 1575 */         entityIn.rotationYaw = entityIn.prevRotationYaw;
/*      */       }
/*      */       
/* 1578 */       int l = MathHelper.floor_double(entityIn.posX / 16.0D);
/* 1579 */       int i1 = MathHelper.floor_double(entityIn.posY / 16.0D);
/* 1580 */       int j1 = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*      */       
/* 1582 */       if (!entityIn.addedToChunk || entityIn.chunkCoordX != l || entityIn.chunkCoordY != i1 || entityIn.chunkCoordZ != j1) {
/* 1583 */         if (entityIn.addedToChunk && isChunkLoaded(entityIn.chunkCoordX, entityIn.chunkCoordZ, true)) {
/* 1584 */           getChunkFromChunkCoords(entityIn.chunkCoordX, entityIn.chunkCoordZ).removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */         }
/*      */         
/* 1587 */         if (isChunkLoaded(l, j1, true)) {
/* 1588 */           entityIn.addedToChunk = true;
/* 1589 */           getChunkFromChunkCoords(l, j1).addEntity(entityIn);
/*      */         } else {
/* 1591 */           entityIn.addedToChunk = false;
/*      */         } 
/*      */       } 
/*      */       
/* 1595 */       this.theProfiler.endSection();
/*      */       
/* 1597 */       if (forceUpdate && entityIn.addedToChunk && entityIn.riddenByEntity != null) {
/* 1598 */         if (!entityIn.riddenByEntity.isDead && entityIn.riddenByEntity.ridingEntity == entityIn) {
/* 1599 */           updateEntity(entityIn.riddenByEntity);
/*      */         } else {
/* 1601 */           entityIn.riddenByEntity.ridingEntity = null;
/* 1602 */           entityIn.riddenByEntity = null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb) {
/* 1612 */     return checkNoEntityCollision(bb, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb, Entity entityIn) {
/* 1619 */     List<Entity> list = getEntitiesWithinAABBExcludingEntity(null, bb);
/*      */     
/* 1621 */     for (int i = 0; i < list.size(); i++) {
/* 1622 */       Entity entity = list.get(i);
/*      */       
/* 1624 */       if (!entity.isDead && entity.preventEntitySpawning && entity != entityIn && (entityIn == null || (entityIn.ridingEntity != entity && entityIn.riddenByEntity != entity))) {
/* 1625 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1629 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkBlockCollision(AxisAlignedBB bb) {
/* 1636 */     int i = MathHelper.floor_double(bb.minX);
/* 1637 */     int j = MathHelper.floor_double(bb.maxX);
/* 1638 */     int k = MathHelper.floor_double(bb.minY);
/* 1639 */     int l = MathHelper.floor_double(bb.maxY);
/* 1640 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1641 */     int j1 = MathHelper.floor_double(bb.maxZ);
/* 1642 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1644 */     for (int k1 = i; k1 <= j; k1++) {
/* 1645 */       for (int l1 = k; l1 <= l; l1++) {
/* 1646 */         for (int i2 = i1; i2 <= j1; i2++) {
/* 1647 */           Block block = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock();
/*      */           
/* 1649 */           if (block.getMaterial() != Material.air) {
/* 1650 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1656 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnyLiquid(AxisAlignedBB bb) {
/* 1663 */     int i = MathHelper.floor_double(bb.minX);
/* 1664 */     int j = MathHelper.floor_double(bb.maxX);
/* 1665 */     int k = MathHelper.floor_double(bb.minY);
/* 1666 */     int l = MathHelper.floor_double(bb.maxY);
/* 1667 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1668 */     int j1 = MathHelper.floor_double(bb.maxZ);
/* 1669 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1671 */     for (int k1 = i; k1 <= j; k1++) {
/* 1672 */       for (int l1 = k; l1 <= l; l1++) {
/* 1673 */         for (int i2 = i1; i2 <= j1; i2++) {
/* 1674 */           Block block = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock();
/*      */           
/* 1676 */           if (block.getMaterial().isLiquid()) {
/* 1677 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1683 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isFlammableWithin(AxisAlignedBB bb) {
/* 1687 */     int i = MathHelper.floor_double(bb.minX);
/* 1688 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1689 */     int k = MathHelper.floor_double(bb.minY);
/* 1690 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1691 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1692 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/*      */     
/* 1694 */     if (isAreaLoaded(i, k, i1, j, l, j1, true)) {
/* 1695 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1697 */       for (int k1 = i; k1 < j; k1++) {
/* 1698 */         for (int l1 = k; l1 < l; l1++) {
/* 1699 */           for (int i2 = i1; i2 < j1; i2++) {
/* 1700 */             Block block = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock();
/*      */             
/* 1702 */             if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava) {
/* 1703 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1710 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleMaterialAcceleration(AxisAlignedBB bb, Material materialIn, Entity entityIn) {
/* 1717 */     int i = MathHelper.floor_double(bb.minX);
/* 1718 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1719 */     int k = MathHelper.floor_double(bb.minY);
/* 1720 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1721 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1722 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/*      */     
/* 1724 */     if (!isAreaLoaded(i, k, i1, j, l, j1, true)) {
/* 1725 */       return false;
/*      */     }
/* 1727 */     boolean flag = false;
/* 1728 */     Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);
/* 1729 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1731 */     for (int k1 = i; k1 < j; k1++) {
/* 1732 */       for (int l1 = k; l1 < l; l1++) {
/* 1733 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1734 */           blockpos$mutableblockpos.set(k1, l1, i2);
/* 1735 */           IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/* 1736 */           Block block = iblockstate.getBlock();
/*      */           
/* 1738 */           if (block.getMaterial() == materialIn) {
/* 1739 */             double d0 = ((l1 + 1) - BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue()));
/*      */             
/* 1741 */             if (l >= d0) {
/* 1742 */               flag = true;
/* 1743 */               vec3 = block.modifyAcceleration(this, (BlockPos)blockpos$mutableblockpos, entityIn, vec3);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1750 */     if (vec3.lengthVector() > 0.0D && entityIn.isPushedByWater()) {
/* 1751 */       vec3 = vec3.normalize();
/* 1752 */       double d1 = 0.014D;
/* 1753 */       entityIn.motionX += vec3.xCoord * d1;
/* 1754 */       entityIn.motionY += vec3.yCoord * d1;
/* 1755 */       entityIn.motionZ += vec3.zCoord * d1;
/*      */     } 
/*      */     
/* 1758 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMaterialInBB(AxisAlignedBB bb, Material materialIn) {
/* 1766 */     int i = MathHelper.floor_double(bb.minX);
/* 1767 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1768 */     int k = MathHelper.floor_double(bb.minY);
/* 1769 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1770 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1771 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1772 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1774 */     for (int k1 = i; k1 < j; k1++) {
/* 1775 */       for (int l1 = k; l1 < l; l1++) {
/* 1776 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1777 */           if (getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2)).getBlock().getMaterial() == materialIn) {
/* 1778 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1784 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAABBInMaterial(AxisAlignedBB bb, Material materialIn) {
/* 1791 */     int i = MathHelper.floor_double(bb.minX);
/* 1792 */     int j = MathHelper.floor_double(bb.maxX + 1.0D);
/* 1793 */     int k = MathHelper.floor_double(bb.minY);
/* 1794 */     int l = MathHelper.floor_double(bb.maxY + 1.0D);
/* 1795 */     int i1 = MathHelper.floor_double(bb.minZ);
/* 1796 */     int j1 = MathHelper.floor_double(bb.maxZ + 1.0D);
/* 1797 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */     
/* 1799 */     for (int k1 = i; k1 < j; k1++) {
/* 1800 */       for (int l1 = k; l1 < l; l1++) {
/* 1801 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1802 */           IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, l1, i2));
/* 1803 */           Block block = iblockstate.getBlock();
/*      */           
/* 1805 */           if (block.getMaterial() == materialIn) {
/* 1806 */             int j2 = ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue();
/* 1807 */             double d0 = (l1 + 1);
/*      */             
/* 1809 */             if (j2 < 8) {
/* 1810 */               d0 = (l1 + 1) - j2 / 8.0D;
/*      */             }
/*      */             
/* 1813 */             if (d0 >= bb.minY) {
/* 1814 */               return true;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1821 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion createExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
/* 1828 */     return newExplosion(entityIn, x, y, z, strength, false, isSmoking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion newExplosion(Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
/* 1835 */     Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
/* 1836 */     explosion.doExplosionA();
/* 1837 */     explosion.doExplosionB(true);
/* 1838 */     return explosion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBlockDensity(Vec3 vec, AxisAlignedBB bb) {
/* 1845 */     double d0 = 1.0D / ((bb.maxX - bb.minX) * 2.0D + 1.0D);
/* 1846 */     double d1 = 1.0D / ((bb.maxY - bb.minY) * 2.0D + 1.0D);
/* 1847 */     double d2 = 1.0D / ((bb.maxZ - bb.minZ) * 2.0D + 1.0D);
/* 1848 */     double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
/* 1849 */     double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
/*      */     
/* 1851 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/* 1852 */       int i = 0;
/* 1853 */       int j = 0;
/*      */       
/* 1855 */       for (float f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/* 1856 */         for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/* 1857 */           for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/* 1858 */             double d5 = bb.minX + (bb.maxX - bb.minX) * f;
/* 1859 */             double d6 = bb.minY + (bb.maxY - bb.minY) * f1;
/* 1860 */             double d7 = bb.minZ + (bb.maxZ - bb.minZ) * f2;
/*      */             
/* 1862 */             if (rayTraceBlocks(new Vec3(d5 + d3, d6, d7 + d4), vec) == null) {
/* 1863 */               i++;
/*      */             }
/*      */             
/* 1866 */             j++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1871 */       return i / j;
/*      */     } 
/* 1873 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side) {
/* 1881 */     pos = pos.offset(side);
/*      */     
/* 1883 */     if (getBlockState(pos).getBlock() == Blocks.fire) {
/* 1884 */       playAuxSFXAtEntity(player, 1004, pos, 0);
/* 1885 */       setBlockToAir(pos);
/* 1886 */       return true;
/*      */     } 
/* 1888 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugLoadedEntities() {
/* 1896 */     return "All: " + this.loadedEntityList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProviderName() {
/* 1903 */     return this.chunkProvider.makeString();
/*      */   }
/*      */   
/*      */   public TileEntity getTileEntity(BlockPos pos) {
/* 1907 */     if (!isValid(pos)) {
/* 1908 */       return null;
/*      */     }
/* 1910 */     TileEntity tileentity = null;
/*      */     
/* 1912 */     if (this.processingLoadedTiles) {
/* 1913 */       for (int i = 0; i < this.addedTileEntityList.size(); i++) {
/* 1914 */         TileEntity tileentity1 = this.addedTileEntityList.get(i);
/*      */         
/* 1916 */         if (!tileentity1.isInvalid() && tileentity1.getPos().equals(pos)) {
/* 1917 */           tileentity = tileentity1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1923 */     if (tileentity == null) {
/* 1924 */       tileentity = getChunkFromBlockCoords(pos).getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
/*      */     }
/*      */     
/* 1927 */     if (tileentity == null) {
/* 1928 */       for (int j = 0; j < this.addedTileEntityList.size(); j++) {
/* 1929 */         TileEntity tileentity2 = this.addedTileEntityList.get(j);
/*      */         
/* 1931 */         if (!tileentity2.isInvalid() && tileentity2.getPos().equals(pos)) {
/* 1932 */           tileentity = tileentity2;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1938 */     return tileentity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTileEntity(BlockPos pos, TileEntity tileEntityIn) {
/* 1943 */     if (tileEntityIn != null && !tileEntityIn.isInvalid()) {
/* 1944 */       if (this.processingLoadedTiles) {
/* 1945 */         tileEntityIn.setPos(pos);
/* 1946 */         Iterator<TileEntity> iterator = this.addedTileEntityList.iterator();
/*      */         
/* 1948 */         while (iterator.hasNext()) {
/* 1949 */           TileEntity tileentity = iterator.next();
/*      */           
/* 1951 */           if (tileentity.getPos().equals(pos)) {
/* 1952 */             tileentity.invalidate();
/* 1953 */             iterator.remove();
/*      */           } 
/*      */         } 
/*      */         
/* 1957 */         this.addedTileEntityList.add(tileEntityIn);
/*      */       } else {
/* 1959 */         addTileEntity(tileEntityIn);
/* 1960 */         getChunkFromBlockCoords(pos).addTileEntity(pos, tileEntityIn);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/* 1966 */     TileEntity tileentity = getTileEntity(pos);
/*      */     
/* 1968 */     if (tileentity != null && this.processingLoadedTiles) {
/* 1969 */       tileentity.invalidate();
/* 1970 */       this.addedTileEntityList.remove(tileentity);
/*      */     } else {
/* 1972 */       if (tileentity != null) {
/* 1973 */         this.addedTileEntityList.remove(tileentity);
/* 1974 */         this.loadedTileEntityList.remove(tileentity);
/* 1975 */         this.tickableTileEntities.remove(tileentity);
/*      */       } 
/*      */       
/* 1978 */       getChunkFromBlockCoords(pos).removeTileEntity(pos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markTileEntityForRemoval(TileEntity tileEntityIn) {
/* 1986 */     this.tileEntitiesToBeRemoved.add(tileEntityIn);
/*      */   }
/*      */   
/*      */   public boolean isBlockFullCube(BlockPos pos) {
/* 1990 */     IBlockState iblockstate = getBlockState(pos);
/* 1991 */     AxisAlignedBB axisalignedbb = iblockstate.getBlock().getCollisionBoundingBox(this, pos, iblockstate);
/* 1992 */     return (axisalignedbb != null && axisalignedbb.getAverageEdgeLength() >= 1.0D);
/*      */   }
/*      */   
/*      */   public static boolean doesBlockHaveSolidTopSurface(IBlockAccess blockAccess, BlockPos pos) {
/* 1996 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 1997 */     Block block = iblockstate.getBlock();
/* 1998 */     return (block.getMaterial().isOpaque() && block.isFullCube()) ? true : ((block instanceof BlockStairs) ? ((iblockstate.getValue((IProperty)BlockStairs.HALF) == BlockStairs.EnumHalf.TOP)) : ((block instanceof BlockSlab) ? ((iblockstate.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP)) : ((block instanceof net.minecraft.block.BlockHopper) ? true : ((block instanceof BlockSnow) ? ((((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue() == 7)) : false))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
/* 2005 */     if (!isValid(pos)) {
/* 2006 */       return _default;
/*      */     }
/* 2008 */     Chunk chunk = this.chunkProvider.provideChunk(pos);
/*      */     
/* 2010 */     if (chunk.isEmpty()) {
/* 2011 */       return _default;
/*      */     }
/* 2013 */     Block block = getBlockState(pos).getBlock();
/* 2014 */     return (block.getMaterial().isOpaque() && block.isFullCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void calculateInitialSkylight() {
/* 2023 */     int i = calculateSkylightSubtracted(1.0F);
/*      */     
/* 2025 */     if (i != this.skylightSubtracted) {
/* 2026 */       this.skylightSubtracted = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowedSpawnTypes(boolean hostile, boolean peaceful) {
/* 2034 */     this.spawnHostileMobs = hostile;
/* 2035 */     this.spawnPeacefulMobs = peaceful;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/* 2042 */     updateWeather();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculateInitialWeather() {
/* 2049 */     if (this.worldInfo.isRaining()) {
/* 2050 */       this.rainingStrength = 1.0F;
/*      */       
/* 2052 */       if (this.worldInfo.isThundering()) {
/* 2053 */         this.thunderingStrength = 1.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateWeather() {
/* 2062 */     if (!this.provider.getHasNoSky() && 
/* 2063 */       !this.isRemote) {
/* 2064 */       int i = this.worldInfo.getCleanWeatherTime();
/*      */       
/* 2066 */       if (i > 0) {
/* 2067 */         i--;
/* 2068 */         this.worldInfo.setCleanWeatherTime(i);
/* 2069 */         this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
/* 2070 */         this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
/*      */       } 
/*      */       
/* 2073 */       int j = this.worldInfo.getThunderTime();
/*      */       
/* 2075 */       if (j <= 0) {
/* 2076 */         if (this.worldInfo.isThundering()) {
/* 2077 */           this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
/*      */         } else {
/* 2079 */           this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 2082 */         j--;
/* 2083 */         this.worldInfo.setThunderTime(j);
/*      */         
/* 2085 */         if (j <= 0) {
/* 2086 */           this.worldInfo.setThundering(!this.worldInfo.isThundering());
/*      */         }
/*      */       } 
/*      */       
/* 2090 */       this.prevThunderingStrength = this.thunderingStrength;
/*      */       
/* 2092 */       if (this.worldInfo.isThundering()) {
/* 2093 */         this.thunderingStrength = (float)(this.thunderingStrength + 0.01D);
/*      */       } else {
/* 2095 */         this.thunderingStrength = (float)(this.thunderingStrength - 0.01D);
/*      */       } 
/*      */       
/* 2098 */       this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
/* 2099 */       int k = this.worldInfo.getRainTime();
/*      */       
/* 2101 */       if (k <= 0) {
/* 2102 */         if (this.worldInfo.isRaining()) {
/* 2103 */           this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
/*      */         } else {
/* 2105 */           this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
/*      */         } 
/*      */       } else {
/* 2108 */         k--;
/* 2109 */         this.worldInfo.setRainTime(k);
/*      */         
/* 2111 */         if (k <= 0) {
/* 2112 */           this.worldInfo.setRaining(!this.worldInfo.isRaining());
/*      */         }
/*      */       } 
/*      */       
/* 2116 */       this.prevRainingStrength = this.rainingStrength;
/*      */       
/* 2118 */       if (this.worldInfo.isRaining()) {
/* 2119 */         this.rainingStrength = (float)(this.rainingStrength + 0.01D);
/*      */       } else {
/* 2121 */         this.rainingStrength = (float)(this.rainingStrength - 0.01D);
/*      */       } 
/*      */       
/* 2124 */       this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setActivePlayerChunksAndCheckLight() {
/* 2130 */     this.activeChunkSet.clear();
/* 2131 */     this.theProfiler.startSection("buildList");
/*      */     
/* 2133 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2134 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/* 2135 */       int j = MathHelper.floor_double(entityplayer.posX / 16.0D);
/* 2136 */       int k = MathHelper.floor_double(entityplayer.posZ / 16.0D);
/* 2137 */       int l = getRenderDistanceChunks();
/*      */       
/* 2139 */       for (int i1 = -l; i1 <= l; i1++) {
/* 2140 */         for (int j1 = -l; j1 <= l; j1++) {
/* 2141 */           this.activeChunkSet.add(new ChunkCoordIntPair(i1 + j, j1 + k));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2146 */     this.theProfiler.endSection();
/*      */     
/* 2148 */     if (this.ambientTickCountdown > 0) {
/* 2149 */       this.ambientTickCountdown--;
/*      */     }
/*      */     
/* 2152 */     this.theProfiler.startSection("playerCheckLight");
/*      */     
/* 2154 */     if (!this.playerEntities.isEmpty()) {
/* 2155 */       int k1 = this.rand.nextInt(this.playerEntities.size());
/* 2156 */       EntityPlayer entityplayer1 = this.playerEntities.get(k1);
/* 2157 */       int l1 = MathHelper.floor_double(entityplayer1.posX) + this.rand.nextInt(11) - 5;
/* 2158 */       int i2 = MathHelper.floor_double(entityplayer1.posY) + this.rand.nextInt(11) - 5;
/* 2159 */       int j2 = MathHelper.floor_double(entityplayer1.posZ) + this.rand.nextInt(11) - 5;
/* 2160 */       checkLight(new BlockPos(l1, i2, j2));
/*      */     } 
/*      */     
/* 2163 */     this.theProfiler.endSection();
/*      */   }
/*      */   
/*      */   protected abstract int getRenderDistanceChunks();
/*      */   
/*      */   protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk chunkIn) {
/* 2169 */     this.theProfiler.endStartSection("moodSound");
/*      */     
/* 2171 */     if (this.ambientTickCountdown == 0 && !this.isRemote) {
/* 2172 */       this.updateLCG = this.updateLCG * 3 + 1013904223;
/* 2173 */       int i = this.updateLCG >> 2;
/* 2174 */       int j = i & 0xF;
/* 2175 */       int k = i >> 8 & 0xF;
/* 2176 */       int l = i >> 16 & 0xFF;
/* 2177 */       BlockPos blockpos = new BlockPos(j, l, k);
/* 2178 */       Block block = chunkIn.getBlock(blockpos);
/* 2179 */       j += p_147467_1_;
/* 2180 */       k += p_147467_2_;
/*      */       
/* 2182 */       if (block.getMaterial() == Material.air && getLight(blockpos) <= this.rand.nextInt(8) && getLightFor(EnumSkyBlock.SKY, blockpos) <= 0) {
/* 2183 */         EntityPlayer entityplayer = getClosestPlayer(j + 0.5D, l + 0.5D, k + 0.5D, 8.0D);
/*      */         
/* 2185 */         if (entityplayer != null && entityplayer.getDistanceSq(j + 0.5D, l + 0.5D, k + 0.5D) > 4.0D) {
/* 2186 */           playSoundEffect(j + 0.5D, l + 0.5D, k + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
/* 2187 */           this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2192 */     this.theProfiler.endStartSection("checkLight");
/* 2193 */     chunkIn.enqueueRelightChecks();
/*      */   }
/*      */   
/*      */   protected void updateBlocks() {
/* 2197 */     setActivePlayerChunksAndCheckLight();
/*      */   }
/*      */   
/*      */   public void forceBlockUpdateTick(Block blockType, BlockPos pos, Random random) {
/* 2201 */     this.scheduledUpdatesAreImmediate = true;
/* 2202 */     blockType.updateTick(this, pos, getBlockState(pos), random);
/* 2203 */     this.scheduledUpdatesAreImmediate = false;
/*      */   }
/*      */   
/*      */   public boolean canBlockFreezeWater(BlockPos pos) {
/* 2207 */     return canBlockFreeze(pos, false);
/*      */   }
/*      */   
/*      */   public boolean canBlockFreezeNoWater(BlockPos pos) {
/* 2211 */     return canBlockFreeze(pos, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj) {
/* 2218 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 2219 */     float f = biomegenbase.getFloatTemperature(pos);
/*      */     
/* 2221 */     if (f > 0.15F) {
/* 2222 */       return false;
/*      */     }
/* 2224 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/* 2225 */       IBlockState iblockstate = getBlockState(pos);
/* 2226 */       Block block = iblockstate.getBlock();
/*      */       
/* 2228 */       if ((block == Blocks.water || block == Blocks.flowing_water) && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/* 2229 */         if (!noWaterAdj) {
/* 2230 */           return true;
/*      */         }
/*      */         
/* 2233 */         boolean flag = (isWater(pos.west()) && isWater(pos.east()) && isWater(pos.north()) && isWater(pos.south()));
/*      */         
/* 2235 */         if (!flag) {
/* 2236 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2241 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isWater(BlockPos pos) {
/* 2246 */     return (getBlockState(pos).getBlock().getMaterial() == Material.water);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSnowAt(BlockPos pos, boolean checkLight) {
/* 2253 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 2254 */     float f = biomegenbase.getFloatTemperature(pos);
/*      */     
/* 2256 */     if (f > 0.15F)
/* 2257 */       return false; 
/* 2258 */     if (!checkLight) {
/* 2259 */       return true;
/*      */     }
/* 2261 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/* 2262 */       Block block = getBlockState(pos).getBlock();
/*      */       
/* 2264 */       if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, pos)) {
/* 2265 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2269 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkLight(BlockPos pos) {
/* 2274 */     boolean flag = false;
/*      */     
/* 2276 */     if (!this.provider.getHasNoSky()) {
/* 2277 */       flag |= checkLightFor(EnumSkyBlock.SKY, pos);
/*      */     }
/*      */     
/* 2280 */     flag |= checkLightFor(EnumSkyBlock.BLOCK, pos);
/* 2281 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRawLight(BlockPos pos, EnumSkyBlock lightType) {
/* 2288 */     if (lightType == EnumSkyBlock.SKY && canSeeSky(pos)) {
/* 2289 */       return 15;
/*      */     }
/* 2291 */     Block block = getBlockState(pos).getBlock();
/* 2292 */     int i = (lightType == EnumSkyBlock.SKY) ? 0 : block.getLightValue();
/* 2293 */     int j = block.getLightOpacity();
/*      */     
/* 2295 */     if (j >= 15 && block.getLightValue() > 0) {
/* 2296 */       j = 1;
/*      */     }
/*      */     
/* 2299 */     if (j < 1) {
/* 2300 */       j = 1;
/*      */     }
/*      */     
/* 2303 */     if (j >= 15)
/* 2304 */       return 0; 
/* 2305 */     if (i >= 14)
/* 2306 */       return i;  byte b; int k;
/*      */     EnumFacing[] arrayOfEnumFacing;
/* 2308 */     for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 2309 */       BlockPos blockpos = pos.offset(enumfacing);
/* 2310 */       int m = getLightFor(lightType, blockpos) - j;
/*      */       
/* 2312 */       if (m > i) {
/* 2313 */         i = m;
/*      */       }
/*      */       
/* 2316 */       if (i >= 14) {
/* 2317 */         return i;
/*      */       }
/*      */       b++; }
/*      */     
/* 2321 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos) {
/* 2327 */     if (!isAreaLoaded(pos, 17, false)) {
/* 2328 */       return false;
/*      */     }
/* 2330 */     int i = 0;
/* 2331 */     int j = 0;
/* 2332 */     this.theProfiler.startSection("getBrightness");
/* 2333 */     int k = getLightFor(lightType, pos);
/* 2334 */     int l = getRawLight(pos, lightType);
/* 2335 */     int i1 = pos.getX();
/* 2336 */     int j1 = pos.getY();
/* 2337 */     int k1 = pos.getZ();
/*      */     
/* 2339 */     if (l > k) {
/* 2340 */       this.lightUpdateBlockList[j++] = 133152;
/* 2341 */     } else if (l < k) {
/* 2342 */       this.lightUpdateBlockList[j++] = 0x20820 | k << 18;
/*      */       
/* 2344 */       while (i < j) {
/* 2345 */         int l1 = this.lightUpdateBlockList[i++];
/* 2346 */         int i2 = (l1 & 0x3F) - 32 + i1;
/* 2347 */         int j2 = (l1 >> 6 & 0x3F) - 32 + j1;
/* 2348 */         int k2 = (l1 >> 12 & 0x3F) - 32 + k1;
/* 2349 */         int l2 = l1 >> 18 & 0xF;
/* 2350 */         BlockPos blockpos = new BlockPos(i2, j2, k2);
/* 2351 */         int i3 = getLightFor(lightType, blockpos);
/*      */         
/* 2353 */         if (i3 == l2) {
/* 2354 */           setLightFor(lightType, blockpos, 0);
/*      */           
/* 2356 */           if (l2 > 0) {
/* 2357 */             int j3 = MathHelper.abs_int(i2 - i1);
/* 2358 */             int k3 = MathHelper.abs_int(j2 - j1);
/* 2359 */             int l3 = MathHelper.abs_int(k2 - k1);
/*      */             
/* 2361 */             if (j3 + k3 + l3 < 17) {
/* 2362 */               BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(); byte b; int m;
/*      */               EnumFacing[] arrayOfEnumFacing;
/* 2364 */               for (m = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < m; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 2365 */                 int i4 = i2 + enumfacing.getFrontOffsetX();
/* 2366 */                 int j4 = j2 + enumfacing.getFrontOffsetY();
/* 2367 */                 int k4 = k2 + enumfacing.getFrontOffsetZ();
/* 2368 */                 blockpos$mutableblockpos.set(i4, j4, k4);
/* 2369 */                 int l4 = Math.max(1, getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getLightOpacity());
/* 2370 */                 i3 = getLightFor(lightType, (BlockPos)blockpos$mutableblockpos);
/*      */                 
/* 2372 */                 if (i3 == l2 - l4 && j < this.lightUpdateBlockList.length) {
/* 2373 */                   this.lightUpdateBlockList[j++] = i4 - i1 + 32 | j4 - j1 + 32 << 6 | k4 - k1 + 32 << 12 | l2 - l4 << 18;
/*      */                 }
/*      */                 b++; }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2381 */       i = 0;
/*      */     } 
/*      */     
/* 2384 */     this.theProfiler.endSection();
/* 2385 */     this.theProfiler.startSection("checkedPosition < toCheckCount");
/*      */     
/* 2387 */     while (i < j) {
/* 2388 */       int i5 = this.lightUpdateBlockList[i++];
/* 2389 */       int j5 = (i5 & 0x3F) - 32 + i1;
/* 2390 */       int k5 = (i5 >> 6 & 0x3F) - 32 + j1;
/* 2391 */       int l5 = (i5 >> 12 & 0x3F) - 32 + k1;
/* 2392 */       BlockPos blockpos1 = new BlockPos(j5, k5, l5);
/* 2393 */       int i6 = getLightFor(lightType, blockpos1);
/* 2394 */       int j6 = getRawLight(blockpos1, lightType);
/*      */       
/* 2396 */       if (j6 != i6) {
/* 2397 */         setLightFor(lightType, blockpos1, j6);
/*      */         
/* 2399 */         if (j6 > i6) {
/* 2400 */           int k6 = Math.abs(j5 - i1);
/* 2401 */           int l6 = Math.abs(k5 - j1);
/* 2402 */           int i7 = Math.abs(l5 - k1);
/* 2403 */           boolean flag = (j < this.lightUpdateBlockList.length - 6);
/*      */           
/* 2405 */           if (k6 + l6 + i7 < 17 && flag) {
/* 2406 */             if (getLightFor(lightType, blockpos1.west()) < j6) {
/* 2407 */               this.lightUpdateBlockList[j++] = j5 - 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2410 */             if (getLightFor(lightType, blockpos1.east()) < j6) {
/* 2411 */               this.lightUpdateBlockList[j++] = j5 + 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2414 */             if (getLightFor(lightType, blockpos1.down()) < j6) {
/* 2415 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2418 */             if (getLightFor(lightType, blockpos1.up()) < j6) {
/* 2419 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 + 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2422 */             if (getLightFor(lightType, blockpos1.north()) < j6) {
/* 2423 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - 1 - k1 + 32 << 12);
/*      */             }
/*      */             
/* 2426 */             if (getLightFor(lightType, blockpos1.south()) < j6) {
/* 2427 */               this.lightUpdateBlockList[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 + 1 - k1 + 32 << 12);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2434 */     this.theProfiler.endSection();
/* 2435 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tickUpdates(boolean p_72955_1_) {
/* 2443 */     return false;
/*      */   }
/*      */   
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
/* 2447 */     return null;
/*      */   }
/*      */   
/*      */   public List<NextTickListEntry> func_175712_a(StructureBoundingBox structureBB, boolean p_175712_2_) {
/* 2451 */     return null;
/*      */   }
/*      */   
/*      */   public List<Entity> getEntitiesWithinAABBExcludingEntity(Entity entityIn, AxisAlignedBB bb) {
/* 2455 */     return getEntitiesInAABBexcluding(entityIn, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */   
/*      */   public List<Entity> getEntitiesInAABBexcluding(Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate) {
/* 2459 */     List<Entity> list = Lists.newArrayList();
/* 2460 */     int i = MathHelper.floor_double((boundingBox.minX - 2.0D) / 16.0D);
/* 2461 */     int j = MathHelper.floor_double((boundingBox.maxX + 2.0D) / 16.0D);
/* 2462 */     int k = MathHelper.floor_double((boundingBox.minZ - 2.0D) / 16.0D);
/* 2463 */     int l = MathHelper.floor_double((boundingBox.maxZ + 2.0D) / 16.0D);
/*      */     
/* 2465 */     for (int i1 = i; i1 <= j; i1++) {
/* 2466 */       for (int j1 = k; j1 <= l; j1++) {
/* 2467 */         if (isChunkLoaded(i1, j1, true)) {
/* 2468 */           getChunkFromChunkCoords(i1, j1).getEntitiesWithinAABBForEntity(entityIn, boundingBox, list, predicate);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2473 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> getEntities(Class<? extends T> entityType, Predicate<? super T> filter) {
/* 2477 */     List<T> list = Lists.newArrayList();
/*      */     
/* 2479 */     for (Entity entity : this.loadedEntityList) {
/* 2480 */       if (entityType.isAssignableFrom(entity.getClass()) && filter.apply(entity)) {
/* 2481 */         list.add((T)entity);
/*      */       }
/*      */     } 
/*      */     
/* 2485 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> getPlayers(Class<? extends T> playerType, Predicate<? super T> filter) {
/* 2489 */     List<T> list = Lists.newArrayList();
/*      */     
/* 2491 */     for (Entity entity : this.playerEntities) {
/* 2492 */       if (playerType.isAssignableFrom(entity.getClass()) && filter.apply(entity)) {
/* 2493 */         list.add((T)entity);
/*      */       }
/*      */     } 
/*      */     
/* 2497 */     return list;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> classEntity, AxisAlignedBB bb) {
/* 2501 */     return getEntitiesWithinAABB(classEntity, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, Predicate<? super T> filter) {
/* 2505 */     int i = MathHelper.floor_double((aabb.minX - 2.0D) / 16.0D);
/* 2506 */     int j = MathHelper.floor_double((aabb.maxX + 2.0D) / 16.0D);
/* 2507 */     int k = MathHelper.floor_double((aabb.minZ - 2.0D) / 16.0D);
/* 2508 */     int l = MathHelper.floor_double((aabb.maxZ + 2.0D) / 16.0D);
/* 2509 */     List<T> list = Lists.newArrayList();
/*      */     
/* 2511 */     for (int i1 = i; i1 <= j; i1++) {
/* 2512 */       for (int j1 = k; j1 <= l; j1++) {
/* 2513 */         if (isChunkLoaded(i1, j1, true)) {
/* 2514 */           getChunkFromChunkCoords(i1, j1).getEntitiesOfTypeWithinAAAB(clazz, aabb, list, filter);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2519 */     return list;
/*      */   }
/*      */   public <T extends Entity> T findNearestEntityWithinAABB(Class<? extends T> entityType, AxisAlignedBB aabb, T closestTo) {
/*      */     Entity entity;
/* 2523 */     List<T> list = getEntitiesWithinAABB(entityType, aabb);
/* 2524 */     T t = null;
/* 2525 */     double d0 = Double.MAX_VALUE;
/*      */     
/* 2527 */     for (int i = 0; i < list.size(); i++) {
/* 2528 */       Entity entity1 = (Entity)list.get(i);
/*      */       
/* 2530 */       if (entity1 != closestTo && EntitySelectors.NOT_SPECTATING.apply(entity1)) {
/* 2531 */         double d1 = closestTo.getDistanceSqToEntity(entity1);
/*      */         
/* 2533 */         if (d1 <= d0) {
/* 2534 */           entity = entity1;
/* 2535 */           d0 = d1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2540 */     return (T)entity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getEntityByID(int id) {
/* 2547 */     return (Entity)this.entitiesById.lookup(id);
/*      */   }
/*      */   
/*      */   public List<Entity> getLoadedEntityList() {
/* 2551 */     return this.loadedEntityList;
/*      */   }
/*      */   
/*      */   public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {
/* 2555 */     if (isBlockLoaded(pos)) {
/* 2556 */       getChunkFromBlockCoords(pos).setChunkModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countEntities(Class<?> entityType) {
/* 2564 */     int i = 0;
/*      */     
/* 2566 */     for (Entity entity : this.loadedEntityList) {
/* 2567 */       if ((!(entity instanceof EntityLiving) || !((EntityLiving)entity).isNoDespawnRequired()) && entityType.isAssignableFrom(entity.getClass())) {
/* 2568 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 2572 */     return i;
/*      */   }
/*      */   
/*      */   public void loadEntities(Collection<Entity> entityCollection) {
/* 2576 */     this.loadedEntityList.addAll(entityCollection);
/*      */     
/* 2578 */     for (Entity entity : entityCollection) {
/* 2579 */       onEntityAdded(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void unloadEntities(Collection<Entity> entityCollection) {
/* 2584 */     this.unloadedEntityList.addAll(entityCollection);
/*      */   }
/*      */   
/*      */   public boolean canBlockBePlaced(Block blockIn, BlockPos pos, boolean p_175716_3_, EnumFacing side, Entity entityIn, ItemStack itemStackIn) {
/* 2588 */     Block block = getBlockState(pos).getBlock();
/* 2589 */     AxisAlignedBB axisalignedbb = p_175716_3_ ? null : blockIn.getCollisionBoundingBox(this, pos, blockIn.getDefaultState());
/* 2590 */     return (axisalignedbb != null && !checkNoEntityCollision(axisalignedbb, entityIn)) ? false : ((block.getMaterial() == Material.circuits && blockIn == Blocks.anvil) ? true : ((block.getMaterial().isReplaceable() && blockIn.canReplace(this, pos, side, itemStackIn))));
/*      */   }
/*      */   
/*      */   public int getSeaLevel() {
/* 2594 */     return this.seaLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeaLevel(int p_181544_1_) {
/* 2601 */     this.seaLevel = p_181544_1_;
/*      */   }
/*      */   
/*      */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 2605 */     IBlockState iblockstate = getBlockState(pos);
/* 2606 */     return iblockstate.getBlock().getStrongPower(this, pos, iblockstate, direction);
/*      */   }
/*      */   
/*      */   public WorldType getWorldType() {
/* 2610 */     return this.worldInfo.getTerrainType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStrongPower(BlockPos pos) {
/* 2617 */     int i = 0;
/* 2618 */     i = Math.max(i, getStrongPower(pos.down(), EnumFacing.DOWN));
/*      */     
/* 2620 */     if (i >= 15) {
/* 2621 */       return i;
/*      */     }
/* 2623 */     i = Math.max(i, getStrongPower(pos.up(), EnumFacing.UP));
/*      */     
/* 2625 */     if (i >= 15) {
/* 2626 */       return i;
/*      */     }
/* 2628 */     i = Math.max(i, getStrongPower(pos.north(), EnumFacing.NORTH));
/*      */     
/* 2630 */     if (i >= 15) {
/* 2631 */       return i;
/*      */     }
/* 2633 */     i = Math.max(i, getStrongPower(pos.south(), EnumFacing.SOUTH));
/*      */     
/* 2635 */     if (i >= 15) {
/* 2636 */       return i;
/*      */     }
/* 2638 */     i = Math.max(i, getStrongPower(pos.west(), EnumFacing.WEST));
/*      */     
/* 2640 */     if (i >= 15) {
/* 2641 */       return i;
/*      */     }
/* 2643 */     i = Math.max(i, getStrongPower(pos.east(), EnumFacing.EAST));
/* 2644 */     return (i >= 15) ? i : i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSidePowered(BlockPos pos, EnumFacing side) {
/* 2653 */     return (getRedstonePower(pos, side) > 0);
/*      */   }
/*      */   
/*      */   public int getRedstonePower(BlockPos pos, EnumFacing facing) {
/* 2657 */     IBlockState iblockstate = getBlockState(pos);
/* 2658 */     Block block = iblockstate.getBlock();
/* 2659 */     return block.isNormalCube() ? getStrongPower(pos) : block.getWeakPower(this, pos, iblockstate, facing);
/*      */   }
/*      */   
/*      */   public boolean isBlockPowered(BlockPos pos) {
/* 2663 */     return (getRedstonePower(pos.down(), EnumFacing.DOWN) > 0) ? true : ((getRedstonePower(pos.up(), EnumFacing.UP) > 0) ? true : ((getRedstonePower(pos.north(), EnumFacing.NORTH) > 0) ? true : ((getRedstonePower(pos.south(), EnumFacing.SOUTH) > 0) ? true : ((getRedstonePower(pos.west(), EnumFacing.WEST) > 0) ? true : ((getRedstonePower(pos.east(), EnumFacing.EAST) > 0))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int isBlockIndirectlyGettingPowered(BlockPos pos) {
/* 2671 */     int i = 0; byte b; int j;
/*      */     EnumFacing[] arrayOfEnumFacing;
/* 2673 */     for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 2674 */       int k = getRedstonePower(pos.offset(enumfacing), enumfacing);
/*      */       
/* 2676 */       if (k >= 15) {
/* 2677 */         return 15;
/*      */       }
/*      */       
/* 2680 */       if (k > i) {
/* 2681 */         i = k;
/*      */       }
/*      */       b++; }
/*      */     
/* 2685 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance) {
/* 2693 */     return getClosestPlayer(entityIn.posX, entityIn.posY, entityIn.posZ, distance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getClosestPlayer(double x, double y, double z, double distance) {
/* 2701 */     double d0 = -1.0D;
/* 2702 */     EntityPlayer entityplayer = null;
/*      */     
/* 2704 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2705 */       EntityPlayer entityplayer1 = this.playerEntities.get(i);
/*      */       
/* 2707 */       if (EntitySelectors.NOT_SPECTATING.apply(entityplayer1)) {
/* 2708 */         double d1 = entityplayer1.getDistanceSq(x, y, z);
/*      */         
/* 2710 */         if ((distance < 0.0D || d1 < distance * distance) && (d0 == -1.0D || d1 < d0)) {
/* 2711 */           d0 = d1;
/* 2712 */           entityplayer = entityplayer1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2717 */     return entityplayer;
/*      */   }
/*      */   
/*      */   public boolean isAnyPlayerWithinRangeAt(double x, double y, double z, double range) {
/* 2721 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2722 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 2724 */       if (EntitySelectors.NOT_SPECTATING.apply(entityplayer)) {
/* 2725 */         double d0 = entityplayer.getDistanceSq(x, y, z);
/*      */         
/* 2727 */         if (range < 0.0D || d0 < range * range) {
/* 2728 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2733 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayer getPlayerEntityByName(String name) {
/* 2740 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2741 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 2743 */       if (name.equals(entityplayer.getName())) {
/* 2744 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 2748 */     return null;
/*      */   }
/*      */   
/*      */   public EntityPlayer getPlayerEntityByUUID(UUID uuid) {
/* 2752 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/* 2753 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*      */       
/* 2755 */       if (uuid.equals(entityplayer.getUniqueID())) {
/* 2756 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 2760 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendQuittingDisconnectingPacket() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSessionLock() throws MinecraftException {
/* 2773 */     this.saveHandler.checkSessionLock();
/*      */   }
/*      */   
/*      */   public void setTotalWorldTime(long worldTime) {
/* 2777 */     this.worldInfo.setWorldTotalTime(worldTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSeed() {
/* 2784 */     return this.worldInfo.getSeed();
/*      */   }
/*      */   
/*      */   public long getTotalWorldTime() {
/* 2788 */     return this.worldInfo.getWorldTotalTime();
/*      */   }
/*      */   
/*      */   public long getWorldTime() {
/* 2792 */     return this.worldInfo.getWorldTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldTime(long time) {
/* 2799 */     this.worldInfo.setWorldTime(time);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getSpawnPoint() {
/* 2806 */     BlockPos blockpos = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
/*      */     
/* 2808 */     if (!getWorldBorder().contains(blockpos)) {
/* 2809 */       blockpos = getHeight(new BlockPos(getWorldBorder().getCenterX(), 0.0D, getWorldBorder().getCenterZ()));
/*      */     }
/*      */     
/* 2812 */     return blockpos;
/*      */   }
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos) {
/* 2816 */     this.worldInfo.setSpawn(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void joinEntityInSurroundings(Entity entityIn) {
/* 2823 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/* 2824 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/* 2825 */     int k = 2;
/*      */     
/* 2827 */     for (int l = i - k; l <= i + k; l++) {
/* 2828 */       for (int i1 = j - k; i1 <= j + k; i1++) {
/* 2829 */         getChunkFromChunkCoords(l, i1);
/*      */       }
/*      */     } 
/*      */     
/* 2833 */     if (!this.loadedEntityList.contains(entityIn)) {
/* 2834 */       this.loadedEntityList.add(entityIn);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
/* 2839 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityState(Entity entityIn, byte state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChunkProvider getChunkProvider() {
/* 2852 */     return this.chunkProvider;
/*      */   }
/*      */   
/*      */   public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
/* 2856 */     blockIn.onBlockEventReceived(this, pos, getBlockState(pos), eventID, eventParam);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISaveHandler getSaveHandler() {
/* 2863 */     return this.saveHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldInfo getWorldInfo() {
/* 2870 */     return this.worldInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameRules getGameRules() {
/* 2877 */     return this.worldInfo.getGameRulesInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAllPlayersSleepingFlag() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public float getThunderStrength(float delta) {
/* 2887 */     return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * delta) * getRainStrength(delta);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThunderStrength(float strength) {
/* 2894 */     this.prevThunderingStrength = strength;
/* 2895 */     this.thunderingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRainStrength(float delta) {
/* 2902 */     return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * delta;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRainStrength(float strength) {
/* 2909 */     this.prevRainingStrength = strength;
/* 2910 */     this.rainingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isThundering() {
/* 2917 */     return (getThunderStrength(1.0F) > 0.9D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRaining() {
/* 2924 */     return (getRainStrength(1.0F) > 0.2D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRainingAt(BlockPos strikePosition) {
/* 2931 */     if (!isRaining())
/* 2932 */       return false; 
/* 2933 */     if (!canSeeSky(strikePosition))
/* 2934 */       return false; 
/* 2935 */     if (getPrecipitationHeight(strikePosition).getY() > strikePosition.getY()) {
/* 2936 */       return false;
/*      */     }
/* 2938 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(strikePosition);
/* 2939 */     return biomegenbase.getEnableSnow() ? false : (canSnowAt(strikePosition, false) ? false : biomegenbase.canRain());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockinHighHumidity(BlockPos pos) {
/* 2944 */     BiomeGenBase biomegenbase = getBiomeGenForCoords(pos);
/* 2945 */     return biomegenbase.isHighHumidity();
/*      */   }
/*      */   
/*      */   public MapStorage getMapStorage() {
/* 2949 */     return this.mapStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemData(String dataID, WorldSavedData worldSavedDataIn) {
/* 2957 */     this.mapStorage.setData(dataID, worldSavedDataIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldSavedData loadItemData(Class<? extends WorldSavedData> clazz, String dataID) {
/* 2965 */     return this.mapStorage.loadData(clazz, dataID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUniqueDataId(String key) {
/* 2973 */     return this.mapStorage.getUniqueDataId(key);
/*      */   }
/*      */   
/*      */   public void playBroadcastSound(int p_175669_1_, BlockPos pos, int p_175669_3_) {
/* 2977 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/* 2978 */       ((IWorldAccess)this.worldAccesses.get(i)).broadcastSound(p_175669_1_, pos, p_175669_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   public void playAuxSFX(int p_175718_1_, BlockPos pos, int p_175718_3_) {
/* 2983 */     playAuxSFXAtEntity(null, p_175718_1_, pos, p_175718_3_);
/*      */   }
/*      */   
/*      */   public void playAuxSFXAtEntity(EntityPlayer player, int sfxType, BlockPos pos, int p_180498_4_) {
/*      */     try {
/* 2988 */       for (int i = 0; i < this.worldAccesses.size(); i++) {
/* 2989 */         ((IWorldAccess)this.worldAccesses.get(i)).playAuxSFX(player, sfxType, pos, p_180498_4_);
/*      */       }
/* 2991 */     } catch (Throwable throwable) {
/* 2992 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Playing level event");
/* 2993 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Level event being played");
/* 2994 */       crashreportcategory.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(pos));
/* 2995 */       crashreportcategory.addCrashSection("Event source", player);
/* 2996 */       crashreportcategory.addCrashSection("Event type", Integer.valueOf(sfxType));
/* 2997 */       crashreportcategory.addCrashSection("Event data", Integer.valueOf(p_180498_4_));
/* 2998 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/* 3006 */     return 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActualHeight() {
/* 3013 */     return this.provider.getHasNoSky() ? 128 : 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_) {
/* 3020 */     long i = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + getWorldInfo().getSeed() + p_72843_3_;
/* 3021 */     this.rand.setSeed(i);
/* 3022 */     return this.rand;
/*      */   }
/*      */   
/*      */   public BlockPos getStrongholdPos(String name, BlockPos pos) {
/* 3026 */     return getChunkProvider().getStrongholdGen(this, name, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extendedLevelsInChunkCache() {
/* 3033 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getHorizon() {
/* 3040 */     return (this.worldInfo.getTerrainType() == WorldType.FLAT) ? 0.0D : 63.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 3047 */     CrashReportCategory crashreportcategory = report.makeCategoryDepth("Affected level", 1);
/* 3048 */     crashreportcategory.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo.getWorldName());
/* 3049 */     crashreportcategory.addCrashSectionCallable("All players", new Callable<String>() {
/*      */           public String call() {
/* 3051 */             return String.valueOf(World.this.playerEntities.size()) + " total; " + World.this.playerEntities.toString();
/*      */           }
/*      */         });
/* 3054 */     crashreportcategory.addCrashSectionCallable("Chunk stats", new Callable<String>() {
/*      */           public String call() {
/* 3056 */             return World.this.chunkProvider.makeString();
/*      */           }
/*      */         });
/*      */     
/*      */     try {
/* 3061 */       this.worldInfo.addToCrashReport(crashreportcategory);
/* 3062 */     } catch (Throwable throwable) {
/* 3063 */       crashreportcategory.addCrashSectionThrowable("Level Data Unobtainable", throwable);
/*      */     } 
/*      */     
/* 3066 */     return crashreportcategory;
/*      */   }
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 3070 */     for (int i = 0; i < this.worldAccesses.size(); i++) {
/* 3071 */       IWorldAccess iworldaccess = this.worldAccesses.get(i);
/* 3072 */       iworldaccess.sendBlockBreakProgress(breakerId, pos, progress);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getCurrentDate() {
/* 3080 */     if (getTotalWorldTime() % 600L == 0L) {
/* 3081 */       this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
/*      */     }
/*      */     
/* 3084 */     return this.theCalendar;
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {}
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 3091 */     return this.worldScoreboard;
/*      */   }
/*      */   
/*      */   public void updateComparatorOutputLevel(BlockPos pos, Block blockIn) {
/* 3095 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 3096 */       BlockPos blockpos = pos.offset(enumfacing);
/*      */       
/* 3098 */       if (isBlockLoaded(blockpos)) {
/* 3099 */         IBlockState iblockstate = getBlockState(blockpos);
/*      */         
/* 3101 */         if (Blocks.unpowered_comparator.isAssociated(iblockstate.getBlock())) {
/* 3102 */           iblockstate.getBlock().onNeighborBlockChange(this, blockpos, iblockstate, blockIn); continue;
/* 3103 */         }  if (iblockstate.getBlock().isNormalCube()) {
/* 3104 */           blockpos = blockpos.offset(enumfacing);
/* 3105 */           iblockstate = getBlockState(blockpos);
/*      */           
/* 3107 */           if (Blocks.unpowered_comparator.isAssociated(iblockstate.getBlock())) {
/* 3108 */             iblockstate.getBlock().onNeighborBlockChange(this, blockpos, iblockstate, blockIn);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
/* 3116 */     long i = 0L;
/* 3117 */     float f = 0.0F;
/*      */     
/* 3119 */     if (isBlockLoaded(pos)) {
/* 3120 */       f = getCurrentMoonPhaseFactor();
/* 3121 */       i = getChunkFromBlockCoords(pos).getInhabitedTime();
/*      */     } 
/*      */     
/* 3124 */     return new DifficultyInstance(getDifficulty(), getWorldTime(), i, f);
/*      */   }
/*      */   
/*      */   public EnumDifficulty getDifficulty() {
/* 3128 */     return getWorldInfo().getDifficulty();
/*      */   }
/*      */   
/*      */   public int getSkylightSubtracted() {
/* 3132 */     return this.skylightSubtracted;
/*      */   }
/*      */   
/*      */   public void setSkylightSubtracted(int newSkylightSubtracted) {
/* 3136 */     this.skylightSubtracted = newSkylightSubtracted;
/*      */   }
/*      */   
/*      */   public int getLastLightningBolt() {
/* 3140 */     return this.lastLightningBolt;
/*      */   }
/*      */   
/*      */   public void setLastLightningBolt(int lastLightningBoltIn) {
/* 3144 */     this.lastLightningBolt = lastLightningBoltIn;
/*      */   }
/*      */   
/*      */   public boolean isFindingSpawnPoint() {
/* 3148 */     return this.findingSpawnPoint;
/*      */   }
/*      */   
/*      */   public VillageCollection getVillageCollection() {
/* 3152 */     return this.villageCollectionObj;
/*      */   }
/*      */   
/*      */   public WorldBorder getWorldBorder() {
/* 3156 */     return this.worldBorder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpawnChunk(int x, int z) {
/* 3163 */     BlockPos blockpos = getSpawnPoint();
/* 3164 */     int i = x * 16 + 8 - blockpos.getX();
/* 3165 */     int j = z * 16 + 8 - blockpos.getZ();
/* 3166 */     int k = 128;
/* 3167 */     return (i >= -k && i <= k && j >= -k && j <= k);
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\World.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */