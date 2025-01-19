/*      */ package net.minecraft.world.chunk;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Queues;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.ITileEntityProvider;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ClassInheritanceMultiMap;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.EnumSkyBlock;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*      */ import net.minecraft.world.gen.ChunkProviderDebug;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class Chunk
/*      */ {
/*   41 */   private static final Logger logger = LogManager.getLogger();
/*      */ 
/*      */ 
/*      */   
/*      */   private final ExtendedBlockStorage[] storageArrays;
/*      */ 
/*      */ 
/*      */   
/*      */   private final byte[] blockBiomeArray;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] precipitationHeightMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean[] updateSkylightColumns;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isChunkLoaded;
/*      */ 
/*      */ 
/*      */   
/*      */   private final World worldObj;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] heightMap;
/*      */ 
/*      */ 
/*      */   
/*      */   public final int xPosition;
/*      */ 
/*      */ 
/*      */   
/*      */   public final int zPosition;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isGapLightingUpdated;
/*      */ 
/*      */ 
/*      */   
/*      */   private final Map<BlockPos, TileEntity> chunkTileEntityMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private final ClassInheritanceMultiMap<Entity>[] entityLists;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isTerrainPopulated;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLightPopulated;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean field_150815_m;
/*      */ 
/*      */   
/*      */   private boolean isModified;
/*      */ 
/*      */   
/*      */   private boolean hasEntities;
/*      */ 
/*      */   
/*      */   private long lastSaveTime;
/*      */ 
/*      */   
/*      */   private int heightMapMinimum;
/*      */ 
/*      */   
/*      */   private long inhabitedTime;
/*      */ 
/*      */   
/*      */   private int queuedLightChecks;
/*      */ 
/*      */   
/*      */   private ConcurrentLinkedQueue<BlockPos> tileEntityPosQueue;
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk(World worldIn, int x, int z) {
/*  127 */     this.storageArrays = new ExtendedBlockStorage[16];
/*  128 */     this.blockBiomeArray = new byte[256];
/*  129 */     this.precipitationHeightMap = new int[256];
/*  130 */     this.updateSkylightColumns = new boolean[256];
/*  131 */     this.chunkTileEntityMap = Maps.newHashMap();
/*  132 */     this.queuedLightChecks = 4096;
/*  133 */     this.tileEntityPosQueue = Queues.newConcurrentLinkedQueue();
/*  134 */     this.entityLists = (ClassInheritanceMultiMap<Entity>[])new ClassInheritanceMultiMap[16];
/*  135 */     this.worldObj = worldIn;
/*  136 */     this.xPosition = x;
/*  137 */     this.zPosition = z;
/*  138 */     this.heightMap = new int[256];
/*      */     
/*  140 */     for (int i = 0; i < this.entityLists.length; i++) {
/*  141 */       this.entityLists[i] = new ClassInheritanceMultiMap(Entity.class);
/*      */     }
/*      */     
/*  144 */     Arrays.fill(this.precipitationHeightMap, -999);
/*  145 */     Arrays.fill(this.blockBiomeArray, (byte)-1);
/*      */   }
/*      */   
/*      */   public Chunk(World worldIn, ChunkPrimer primer, int x, int z) {
/*  149 */     this(worldIn, x, z);
/*  150 */     int i = 256;
/*  151 */     boolean flag = !worldIn.provider.getHasNoSky();
/*      */     
/*  153 */     for (int j = 0; j < 16; j++) {
/*  154 */       for (int k = 0; k < 16; k++) {
/*  155 */         for (int l = 0; l < i; l++) {
/*  156 */           int i1 = j * i * 16 | k * i | l;
/*  157 */           IBlockState iblockstate = primer.getBlockState(i1);
/*      */           
/*  159 */           if (iblockstate.getBlock().getMaterial() != Material.air) {
/*  160 */             int j1 = l >> 4;
/*      */             
/*  162 */             if (this.storageArrays[j1] == null) {
/*  163 */               this.storageArrays[j1] = new ExtendedBlockStorage(j1 << 4, flag);
/*      */             }
/*      */             
/*  166 */             this.storageArrays[j1].set(j, l & 0xF, k, iblockstate);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAtLocation(int x, int z) {
/*  177 */     return (x == this.xPosition && z == this.zPosition);
/*      */   }
/*      */   
/*      */   public int getHeight(BlockPos pos) {
/*  181 */     return getHeightValue(pos.getX() & 0xF, pos.getZ() & 0xF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeightValue(int x, int z) {
/*  188 */     return this.heightMap[z << 4 | x];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTopFilledSegment() {
/*  195 */     for (int i = this.storageArrays.length - 1; i >= 0; i--) {
/*  196 */       if (this.storageArrays[i] != null) {
/*  197 */         return this.storageArrays[i].getYLocation();
/*      */       }
/*      */     } 
/*      */     
/*  201 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtendedBlockStorage[] getBlockStorageArray() {
/*  208 */     return this.storageArrays;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateHeightMap() {
/*  215 */     int i = getTopFilledSegment();
/*  216 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  218 */     for (int j = 0; j < 16; j++) {
/*  219 */       for (int k = 0; k < 16; k++) {
/*  220 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  222 */         for (int l = i + 16; l > 0; l--) {
/*  223 */           Block block = getBlock0(j, l - 1, k);
/*      */           
/*  225 */           if (block.getLightOpacity() != 0) {
/*  226 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  228 */             if (l < this.heightMapMinimum) {
/*  229 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  238 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void generateSkylightMap() {
/*  245 */     int i = getTopFilledSegment();
/*  246 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  248 */     for (int j = 0; j < 16; j++) {
/*  249 */       for (int k = 0; k < 16; k++) {
/*  250 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  252 */         for (int l = i + 16; l > 0; l--) {
/*  253 */           if (getBlockLightOpacity(j, l - 1, k) != 0) {
/*  254 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  256 */             if (l < this.heightMapMinimum) {
/*  257 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/*  264 */         if (!this.worldObj.provider.getHasNoSky()) {
/*  265 */           int k1 = 15;
/*  266 */           int i1 = i + 16 - 1;
/*      */           
/*      */           do {
/*  269 */             int j1 = getBlockLightOpacity(j, i1, k);
/*      */             
/*  271 */             if (j1 == 0 && k1 != 15) {
/*  272 */               j1 = 1;
/*      */             }
/*      */             
/*  275 */             k1 -= j1;
/*      */             
/*  277 */             if (k1 <= 0)
/*  278 */               continue;  ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  280 */             if (extendedblockstorage == null)
/*  281 */               continue;  extendedblockstorage.setExtSkylightValue(j, i1 & 0xF, k, k1);
/*  282 */             this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + j, i1, (this.zPosition << 4) + k));
/*      */ 
/*      */ 
/*      */             
/*  286 */             --i1;
/*      */           }
/*  288 */           while (i1 > 0 && k1 > 0);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  296 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateSkylightOcclusion(int x, int z) {
/*  303 */     this.updateSkylightColumns[x + z * 16] = true;
/*  304 */     this.isGapLightingUpdated = true;
/*      */   }
/*      */   
/*      */   private void recheckGaps(boolean p_150803_1_) {
/*  308 */     this.worldObj.theProfiler.startSection("recheckGaps");
/*      */     
/*  310 */     if (this.worldObj.isAreaLoaded(new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
/*  311 */       for (int i = 0; i < 16; i++) {
/*  312 */         for (int j = 0; j < 16; j++) {
/*  313 */           if (this.updateSkylightColumns[i + j * 16]) {
/*  314 */             this.updateSkylightColumns[i + j * 16] = false;
/*  315 */             int k = getHeightValue(i, j);
/*  316 */             int l = this.xPosition * 16 + i;
/*  317 */             int i1 = this.zPosition * 16 + j;
/*  318 */             int j1 = Integer.MAX_VALUE;
/*      */             
/*  320 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  321 */               j1 = Math.min(j1, this.worldObj.getChunksLowestHorizon(l + enumfacing.getFrontOffsetX(), i1 + enumfacing.getFrontOffsetZ()));
/*      */             }
/*      */             
/*  324 */             checkSkylightNeighborHeight(l, i1, j1);
/*      */             
/*  326 */             for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/*  327 */               checkSkylightNeighborHeight(l + enumfacing1.getFrontOffsetX(), i1 + enumfacing1.getFrontOffsetZ(), k);
/*      */             }
/*      */             
/*  330 */             if (p_150803_1_) {
/*  331 */               this.worldObj.theProfiler.endSection();
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  338 */       this.isGapLightingUpdated = false;
/*      */     } 
/*      */     
/*  341 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSkylightNeighborHeight(int x, int z, int maxValue) {
/*  348 */     int i = this.worldObj.getHeight(new BlockPos(x, 0, z)).getY();
/*      */     
/*  350 */     if (i > maxValue) {
/*  351 */       updateSkylightNeighborHeight(x, z, maxValue, i + 1);
/*  352 */     } else if (i < maxValue) {
/*  353 */       updateSkylightNeighborHeight(x, z, i, maxValue + 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateSkylightNeighborHeight(int x, int z, int startY, int endY) {
/*  358 */     if (endY > startY && this.worldObj.isAreaLoaded(new BlockPos(x, 0, z), 16)) {
/*  359 */       for (int i = startY; i < endY; i++) {
/*  360 */         this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(x, i, z));
/*      */       }
/*      */       
/*  363 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void relightBlock(int x, int y, int z) {
/*  371 */     int i = this.heightMap[z << 4 | x] & 0xFF;
/*  372 */     int j = i;
/*      */     
/*  374 */     if (y > i) {
/*  375 */       j = y;
/*      */     }
/*      */     
/*  378 */     while (j > 0 && getBlockLightOpacity(x, j - 1, z) == 0) {
/*  379 */       j--;
/*      */     }
/*      */     
/*  382 */     if (j != i) {
/*  383 */       this.worldObj.markBlocksDirtyVertical(x + this.xPosition * 16, z + this.zPosition * 16, j, i);
/*  384 */       this.heightMap[z << 4 | x] = j;
/*  385 */       int k = this.xPosition * 16 + x;
/*  386 */       int l = this.zPosition * 16 + z;
/*      */       
/*  388 */       if (!this.worldObj.provider.getHasNoSky()) {
/*  389 */         if (j < i) {
/*  390 */           for (int j1 = j; j1 < i; j1++) {
/*  391 */             ExtendedBlockStorage extendedblockstorage2 = this.storageArrays[j1 >> 4];
/*      */             
/*  393 */             if (extendedblockstorage2 != null) {
/*  394 */               extendedblockstorage2.setExtSkylightValue(x, j1 & 0xF, z, 15);
/*  395 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, j1, (this.zPosition << 4) + z));
/*      */             } 
/*      */           } 
/*      */         } else {
/*  399 */           for (int i1 = i; i1 < j; i1++) {
/*  400 */             ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  402 */             if (extendedblockstorage != null) {
/*  403 */               extendedblockstorage.setExtSkylightValue(x, i1 & 0xF, z, 0);
/*  404 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, i1, (this.zPosition << 4) + z));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  409 */         int k1 = 15;
/*      */         
/*  411 */         while (j > 0 && k1 > 0) {
/*  412 */           j--;
/*  413 */           int i2 = getBlockLightOpacity(x, j, z);
/*      */           
/*  415 */           if (i2 == 0) {
/*  416 */             i2 = 1;
/*      */           }
/*      */           
/*  419 */           k1 -= i2;
/*      */           
/*  421 */           if (k1 < 0) {
/*  422 */             k1 = 0;
/*      */           }
/*      */           
/*  425 */           ExtendedBlockStorage extendedblockstorage1 = this.storageArrays[j >> 4];
/*      */           
/*  427 */           if (extendedblockstorage1 != null) {
/*  428 */             extendedblockstorage1.setExtSkylightValue(x, j & 0xF, z, k1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  433 */       int l1 = this.heightMap[z << 4 | x];
/*  434 */       int j2 = i;
/*  435 */       int k2 = l1;
/*      */       
/*  437 */       if (l1 < i) {
/*  438 */         j2 = l1;
/*  439 */         k2 = i;
/*      */       } 
/*      */       
/*  442 */       if (l1 < this.heightMapMinimum) {
/*  443 */         this.heightMapMinimum = l1;
/*      */       }
/*      */       
/*  446 */       if (!this.worldObj.provider.getHasNoSky()) {
/*  447 */         for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  448 */           updateSkylightNeighborHeight(k + enumfacing.getFrontOffsetX(), l + enumfacing.getFrontOffsetZ(), j2, k2);
/*      */         }
/*      */         
/*  451 */         updateSkylightNeighborHeight(k, l, j2, k2);
/*      */       } 
/*      */       
/*  454 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getBlockLightOpacity(BlockPos pos) {
/*  459 */     return getBlock(pos).getLightOpacity();
/*      */   }
/*      */   
/*      */   private int getBlockLightOpacity(int x, int y, int z) {
/*  463 */     return getBlock0(x, y, z).getLightOpacity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Block getBlock0(int x, int y, int z) {
/*  470 */     Block block = Blocks.air;
/*      */     
/*  472 */     if (y >= 0 && y >> 4 < this.storageArrays.length) {
/*  473 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4];
/*      */       
/*  475 */       if (extendedblockstorage != null) {
/*      */         try {
/*  477 */           block = extendedblockstorage.getBlockByExtId(x, y & 0xF, z);
/*  478 */         } catch (Throwable throwable) {
/*  479 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block");
/*  480 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  485 */     return block;
/*      */   }
/*      */   
/*      */   public Block getBlock(final int x, final int y, final int z) {
/*      */     try {
/*  490 */       return getBlock0(x & 0xF, y, z & 0xF);
/*  491 */     } catch (ReportedException reportedexception) {
/*  492 */       CrashReportCategory crashreportcategory = reportedexception.getCrashReport().makeCategory("Block being got");
/*  493 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>() {
/*      */             public String call() throws Exception {
/*  495 */               return CrashReportCategory.getCoordinateInfo(new BlockPos(Chunk.this.xPosition * 16 + x, y, Chunk.this.zPosition * 16 + z));
/*      */             }
/*      */           });
/*  498 */       throw reportedexception;
/*      */     } 
/*      */   }
/*      */   
/*      */   public Block getBlock(final BlockPos pos) {
/*      */     try {
/*  504 */       return getBlock0(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*  505 */     } catch (ReportedException reportedexception) {
/*  506 */       CrashReportCategory crashreportcategory = reportedexception.getCrashReport().makeCategory("Block being got");
/*  507 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>() {
/*      */             public String call() throws Exception {
/*  509 */               return CrashReportCategory.getCoordinateInfo(pos);
/*      */             }
/*      */           });
/*  512 */       throw reportedexception;
/*      */     } 
/*      */   }
/*      */   
/*      */   public IBlockState getBlockState(final BlockPos pos) {
/*  517 */     if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
/*  518 */       IBlockState iblockstate = null;
/*      */       
/*  520 */       if (pos.getY() == 60) {
/*  521 */         iblockstate = Blocks.barrier.getDefaultState();
/*      */       }
/*      */       
/*  524 */       if (pos.getY() == 70) {
/*  525 */         iblockstate = ChunkProviderDebug.func_177461_b(pos.getX(), pos.getZ());
/*      */       }
/*      */       
/*  528 */       return (iblockstate == null) ? Blocks.air.getDefaultState() : iblockstate;
/*      */     } 
/*      */     try {
/*  531 */       if (pos.getY() >= 0 && pos.getY() >> 4 < this.storageArrays.length) {
/*  532 */         ExtendedBlockStorage extendedblockstorage = this.storageArrays[pos.getY() >> 4];
/*      */         
/*  534 */         if (extendedblockstorage != null) {
/*  535 */           int j = pos.getX() & 0xF;
/*  536 */           int k = pos.getY() & 0xF;
/*  537 */           int i = pos.getZ() & 0xF;
/*  538 */           return extendedblockstorage.get(j, k, i);
/*      */         } 
/*      */       } 
/*      */       
/*  542 */       return Blocks.air.getDefaultState();
/*  543 */     } catch (Throwable throwable) {
/*  544 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block state");
/*  545 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being got");
/*  546 */       crashreportcategory.addCrashSectionCallable("Location", new Callable<String>() {
/*      */             public String call() throws Exception {
/*  548 */               return CrashReportCategory.getCoordinateInfo(pos);
/*      */             }
/*      */           });
/*  551 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getBlockMetadata(int x, int y, int z) {
/*  560 */     if (y >> 4 >= this.storageArrays.length) {
/*  561 */       return 0;
/*      */     }
/*  563 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4];
/*  564 */     return (extendedblockstorage != null) ? extendedblockstorage.getExtBlockMetadata(x, y & 0xF, z) : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlockMetadata(BlockPos pos) {
/*  569 */     return getBlockMetadata(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*      */   }
/*      */   
/*      */   public IBlockState setBlockState(BlockPos pos, IBlockState state) {
/*  573 */     int i = pos.getX() & 0xF;
/*  574 */     int j = pos.getY();
/*  575 */     int k = pos.getZ() & 0xF;
/*  576 */     int l = k << 4 | i;
/*      */     
/*  578 */     if (j >= this.precipitationHeightMap[l] - 1) {
/*  579 */       this.precipitationHeightMap[l] = -999;
/*      */     }
/*      */     
/*  582 */     int i1 = this.heightMap[l];
/*  583 */     IBlockState iblockstate = getBlockState(pos);
/*      */     
/*  585 */     if (iblockstate == state) {
/*  586 */       return null;
/*      */     }
/*  588 */     Block block = state.getBlock();
/*  589 */     Block block1 = iblockstate.getBlock();
/*  590 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*  591 */     boolean flag = false;
/*      */     
/*  593 */     if (extendedblockstorage == null) {
/*  594 */       if (block == Blocks.air) {
/*  595 */         return null;
/*      */       }
/*      */       
/*  598 */       extendedblockstorage = this.storageArrays[j >> 4] = new ExtendedBlockStorage(j >> 4 << 4, !this.worldObj.provider.getHasNoSky());
/*  599 */       flag = (j >= i1);
/*      */     } 
/*      */     
/*  602 */     extendedblockstorage.set(i, j & 0xF, k, state);
/*      */     
/*  604 */     if (block1 != block) {
/*  605 */       if (!this.worldObj.isRemote) {
/*  606 */         block1.breakBlock(this.worldObj, pos, iblockstate);
/*  607 */       } else if (block1 instanceof ITileEntityProvider) {
/*  608 */         this.worldObj.removeTileEntity(pos);
/*      */       } 
/*      */     }
/*      */     
/*  612 */     if (extendedblockstorage.getBlockByExtId(i, j & 0xF, k) != block) {
/*  613 */       return null;
/*      */     }
/*  615 */     if (flag) {
/*  616 */       generateSkylightMap();
/*      */     } else {
/*  618 */       int j1 = block.getLightOpacity();
/*  619 */       int k1 = block1.getLightOpacity();
/*      */       
/*  621 */       if (j1 > 0) {
/*  622 */         if (j >= i1) {
/*  623 */           relightBlock(i, j + 1, k);
/*      */         }
/*  625 */       } else if (j == i1 - 1) {
/*  626 */         relightBlock(i, j, k);
/*      */       } 
/*      */       
/*  629 */       if (j1 != k1 && (j1 < k1 || getLightFor(EnumSkyBlock.SKY, pos) > 0 || getLightFor(EnumSkyBlock.BLOCK, pos) > 0)) {
/*  630 */         propagateSkylightOcclusion(i, k);
/*      */       }
/*      */     } 
/*      */     
/*  634 */     if (block1 instanceof ITileEntityProvider) {
/*  635 */       TileEntity tileentity = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  637 */       if (tileentity != null) {
/*  638 */         tileentity.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  642 */     if (!this.worldObj.isRemote && block1 != block) {
/*  643 */       block.onBlockAdded(this.worldObj, pos, state);
/*      */     }
/*      */     
/*  646 */     if (block instanceof ITileEntityProvider) {
/*  647 */       TileEntity tileentity1 = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  649 */       if (tileentity1 == null) {
/*  650 */         tileentity1 = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, block.getMetaFromState(state));
/*  651 */         this.worldObj.setTileEntity(pos, tileentity1);
/*      */       } 
/*      */       
/*  654 */       if (tileentity1 != null) {
/*  655 */         tileentity1.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  659 */     this.isModified = true;
/*  660 */     return iblockstate;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock p_177413_1_, BlockPos pos) {
/*  666 */     int i = pos.getX() & 0xF;
/*  667 */     int j = pos.getY();
/*  668 */     int k = pos.getZ() & 0xF;
/*  669 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*  670 */     return (extendedblockstorage == null) ? (canSeeSky(pos) ? p_177413_1_.defaultLightValue : 0) : ((p_177413_1_ == EnumSkyBlock.SKY) ? (this.worldObj.provider.getHasNoSky() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k)) : ((p_177413_1_ == EnumSkyBlock.BLOCK) ? extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k) : p_177413_1_.defaultLightValue));
/*      */   }
/*      */   
/*      */   public void setLightFor(EnumSkyBlock p_177431_1_, BlockPos pos, int value) {
/*  674 */     int i = pos.getX() & 0xF;
/*  675 */     int j = pos.getY();
/*  676 */     int k = pos.getZ() & 0xF;
/*  677 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  679 */     if (extendedblockstorage == null) {
/*  680 */       extendedblockstorage = this.storageArrays[j >> 4] = new ExtendedBlockStorage(j >> 4 << 4, !this.worldObj.provider.getHasNoSky());
/*  681 */       generateSkylightMap();
/*      */     } 
/*      */     
/*  684 */     this.isModified = true;
/*      */     
/*  686 */     if (p_177431_1_ == EnumSkyBlock.SKY) {
/*  687 */       if (!this.worldObj.provider.getHasNoSky()) {
/*  688 */         extendedblockstorage.setExtSkylightValue(i, j & 0xF, k, value);
/*      */       }
/*  690 */     } else if (p_177431_1_ == EnumSkyBlock.BLOCK) {
/*  691 */       extendedblockstorage.setExtBlocklightValue(i, j & 0xF, k, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getLightSubtracted(BlockPos pos, int amount) {
/*  696 */     int i = pos.getX() & 0xF;
/*  697 */     int j = pos.getY();
/*  698 */     int k = pos.getZ() & 0xF;
/*  699 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  701 */     if (extendedblockstorage == null) {
/*  702 */       return (!this.worldObj.provider.getHasNoSky() && amount < EnumSkyBlock.SKY.defaultLightValue) ? (EnumSkyBlock.SKY.defaultLightValue - amount) : 0;
/*      */     }
/*  704 */     int l = this.worldObj.provider.getHasNoSky() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k);
/*  705 */     l -= amount;
/*  706 */     int i1 = extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k);
/*      */     
/*  708 */     if (i1 > l) {
/*  709 */       l = i1;
/*      */     }
/*      */     
/*  712 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEntity(Entity entityIn) {
/*  720 */     this.hasEntities = true;
/*  721 */     int i = MathHelper.floor_double(entityIn.posX / 16.0D);
/*  722 */     int j = MathHelper.floor_double(entityIn.posZ / 16.0D);
/*      */     
/*  724 */     if (i != this.xPosition || j != this.zPosition) {
/*  725 */       logger.warn("Wrong location! (" + i + ", " + j + ") should be (" + this.xPosition + ", " + this.zPosition + "), " + entityIn, new Object[] { entityIn });
/*  726 */       entityIn.setDead();
/*      */     } 
/*      */     
/*  729 */     int k = MathHelper.floor_double(entityIn.posY / 16.0D);
/*      */     
/*  731 */     if (k < 0) {
/*  732 */       k = 0;
/*      */     }
/*      */     
/*  735 */     if (k >= this.entityLists.length) {
/*  736 */       k = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  739 */     entityIn.addedToChunk = true;
/*  740 */     entityIn.chunkCoordX = this.xPosition;
/*  741 */     entityIn.chunkCoordY = k;
/*  742 */     entityIn.chunkCoordZ = this.zPosition;
/*  743 */     this.entityLists[k].add(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/*  750 */     removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntityAtIndex(Entity entityIn, int p_76608_2_) {
/*  757 */     if (p_76608_2_ < 0) {
/*  758 */       p_76608_2_ = 0;
/*      */     }
/*      */     
/*  761 */     if (p_76608_2_ >= this.entityLists.length) {
/*  762 */       p_76608_2_ = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  765 */     this.entityLists[p_76608_2_].remove(entityIn);
/*      */   }
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  769 */     int i = pos.getX() & 0xF;
/*  770 */     int j = pos.getY();
/*  771 */     int k = pos.getZ() & 0xF;
/*  772 */     return (j >= this.heightMap[k << 4 | i]);
/*      */   }
/*      */   
/*      */   private TileEntity createNewTileEntity(BlockPos pos) {
/*  776 */     Block block = getBlock(pos);
/*  777 */     return !block.hasTileEntity() ? null : ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, getBlockMetadata(pos));
/*      */   }
/*      */   
/*      */   public TileEntity getTileEntity(BlockPos pos, EnumCreateEntityType p_177424_2_) {
/*  781 */     TileEntity tileentity = this.chunkTileEntityMap.get(pos);
/*      */     
/*  783 */     if (tileentity == null) {
/*  784 */       if (p_177424_2_ == EnumCreateEntityType.IMMEDIATE) {
/*  785 */         tileentity = createNewTileEntity(pos);
/*  786 */         this.worldObj.setTileEntity(pos, tileentity);
/*  787 */       } else if (p_177424_2_ == EnumCreateEntityType.QUEUED) {
/*  788 */         this.tileEntityPosQueue.add(pos);
/*      */       } 
/*  790 */     } else if (tileentity.isInvalid()) {
/*  791 */       this.chunkTileEntityMap.remove(pos);
/*  792 */       return null;
/*      */     } 
/*      */     
/*  795 */     return tileentity;
/*      */   }
/*      */   
/*      */   public void addTileEntity(TileEntity tileEntityIn) {
/*  799 */     addTileEntity(tileEntityIn.getPos(), tileEntityIn);
/*      */     
/*  801 */     if (this.isChunkLoaded) {
/*  802 */       this.worldObj.addTileEntity(tileEntityIn);
/*      */     }
/*      */   }
/*      */   
/*      */   public void addTileEntity(BlockPos pos, TileEntity tileEntityIn) {
/*  807 */     tileEntityIn.setWorldObj(this.worldObj);
/*  808 */     tileEntityIn.setPos(pos);
/*      */     
/*  810 */     if (getBlock(pos) instanceof ITileEntityProvider) {
/*  811 */       if (this.chunkTileEntityMap.containsKey(pos)) {
/*  812 */         ((TileEntity)this.chunkTileEntityMap.get(pos)).invalidate();
/*      */       }
/*      */       
/*  815 */       tileEntityIn.validate();
/*  816 */       this.chunkTileEntityMap.put(pos, tileEntityIn);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/*  821 */     if (this.isChunkLoaded) {
/*  822 */       TileEntity tileentity = this.chunkTileEntityMap.remove(pos);
/*      */       
/*  824 */       if (tileentity != null) {
/*  825 */         tileentity.invalidate();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {
/*  834 */     this.isChunkLoaded = true;
/*  835 */     this.worldObj.addTileEntities(this.chunkTileEntityMap.values());
/*      */     
/*  837 */     for (int i = 0; i < this.entityLists.length; i++) {
/*  838 */       for (Entity entity : this.entityLists[i]) {
/*  839 */         entity.onChunkLoad();
/*      */       }
/*      */       
/*  842 */       this.worldObj.loadEntities((Collection)this.entityLists[i]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkUnload() {
/*  850 */     this.isChunkLoaded = false;
/*      */     
/*  852 */     for (TileEntity tileentity : this.chunkTileEntityMap.values()) {
/*  853 */       this.worldObj.markTileEntityForRemoval(tileentity);
/*      */     }
/*      */     
/*  856 */     for (int i = 0; i < this.entityLists.length; i++) {
/*  857 */       this.worldObj.unloadEntities((Collection)this.entityLists[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChunkModified() {
/*  865 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getEntitiesWithinAABBForEntity(Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill, Predicate<? super Entity> p_177414_4_) {
/*  872 */     int i = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
/*  873 */     int j = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
/*  874 */     i = MathHelper.clamp_int(i, 0, this.entityLists.length - 1);
/*  875 */     j = MathHelper.clamp_int(j, 0, this.entityLists.length - 1);
/*      */     
/*  877 */     for (int k = i; k <= j; k++) {
/*  878 */       if (!this.entityLists[k].isEmpty()) {
/*  879 */         for (Entity entity : this.entityLists[k]) {
/*  880 */           if (entity.getEntityBoundingBox().intersectsWith(aabb) && entity != entityIn) {
/*  881 */             if (p_177414_4_ == null || p_177414_4_.apply(entity)) {
/*  882 */               listToFill.add(entity);
/*      */             }
/*      */             
/*  885 */             Entity[] aentity = entity.getParts();
/*      */             
/*  887 */             if (aentity != null) {
/*  888 */               for (int l = 0; l < aentity.length; l++) {
/*  889 */                 entity = aentity[l];
/*      */                 
/*  891 */                 if (entity != entityIn && entity.getEntityBoundingBox().intersectsWith(aabb) && (p_177414_4_ == null || p_177414_4_.apply(entity))) {
/*  892 */                   listToFill.add(entity);
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T extends Entity> void getEntitiesOfTypeWithinAAAB(Class<? extends T> entityClass, AxisAlignedBB aabb, List<T> listToFill, Predicate<? super T> p_177430_4_) {
/*  903 */     int i = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
/*  904 */     int j = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
/*  905 */     i = MathHelper.clamp_int(i, 0, this.entityLists.length - 1);
/*  906 */     j = MathHelper.clamp_int(j, 0, this.entityLists.length - 1);
/*      */     
/*  908 */     for (int k = i; k <= j; k++) {
/*  909 */       for (Entity entity : this.entityLists[k].getByClass(entityClass)) {
/*  910 */         if (entity.getEntityBoundingBox().intersectsWith(aabb) && (p_177430_4_ == null || p_177430_4_.apply(entity))) {
/*  911 */           listToFill.add((T)entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needsSaving(boolean p_76601_1_) {
/*  921 */     if (p_76601_1_) {
/*  922 */       if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified) {
/*  923 */         return true;
/*      */       }
/*  925 */     } else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
/*  926 */       return true;
/*      */     } 
/*      */     
/*  929 */     return this.isModified;
/*      */   }
/*      */   
/*      */   public Random getRandomWithSeed(long seed) {
/*  933 */     return new Random(this.worldObj.getSeed() + (this.xPosition * this.xPosition * 4987142) + (this.xPosition * 5947611) + (this.zPosition * this.zPosition) * 4392871L + (this.zPosition * 389711) ^ seed);
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  937 */     return false;
/*      */   }
/*      */   
/*      */   public void populateChunk(IChunkProvider p_76624_1_, IChunkProvider p_76624_2_, int x, int z) {
/*  941 */     boolean flag = p_76624_1_.chunkExists(x, z - 1);
/*  942 */     boolean flag1 = p_76624_1_.chunkExists(x + 1, z);
/*  943 */     boolean flag2 = p_76624_1_.chunkExists(x, z + 1);
/*  944 */     boolean flag3 = p_76624_1_.chunkExists(x - 1, z);
/*  945 */     boolean flag4 = p_76624_1_.chunkExists(x - 1, z - 1);
/*  946 */     boolean flag5 = p_76624_1_.chunkExists(x + 1, z + 1);
/*  947 */     boolean flag6 = p_76624_1_.chunkExists(x - 1, z + 1);
/*  948 */     boolean flag7 = p_76624_1_.chunkExists(x + 1, z - 1);
/*      */     
/*  950 */     if (flag1 && flag2 && flag5) {
/*  951 */       if (!this.isTerrainPopulated) {
/*  952 */         p_76624_1_.populate(p_76624_2_, x, z);
/*      */       } else {
/*  954 */         p_76624_1_.populateChunk(p_76624_2_, this, x, z);
/*      */       } 
/*      */     }
/*      */     
/*  958 */     if (flag3 && flag2 && flag6) {
/*  959 */       Chunk chunk = p_76624_1_.provideChunk(x - 1, z);
/*      */       
/*  961 */       if (!chunk.isTerrainPopulated) {
/*  962 */         p_76624_1_.populate(p_76624_2_, x - 1, z);
/*      */       } else {
/*  964 */         p_76624_1_.populateChunk(p_76624_2_, chunk, x - 1, z);
/*      */       } 
/*      */     } 
/*      */     
/*  968 */     if (flag && flag1 && flag7) {
/*  969 */       Chunk chunk1 = p_76624_1_.provideChunk(x, z - 1);
/*      */       
/*  971 */       if (!chunk1.isTerrainPopulated) {
/*  972 */         p_76624_1_.populate(p_76624_2_, x, z - 1);
/*      */       } else {
/*  974 */         p_76624_1_.populateChunk(p_76624_2_, chunk1, x, z - 1);
/*      */       } 
/*      */     } 
/*      */     
/*  978 */     if (flag4 && flag && flag3) {
/*  979 */       Chunk chunk2 = p_76624_1_.provideChunk(x - 1, z - 1);
/*      */       
/*  981 */       if (!chunk2.isTerrainPopulated) {
/*  982 */         p_76624_1_.populate(p_76624_2_, x - 1, z - 1);
/*      */       } else {
/*  984 */         p_76624_1_.populateChunk(p_76624_2_, chunk2, x - 1, z - 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/*  990 */     int i = pos.getX() & 0xF;
/*  991 */     int j = pos.getZ() & 0xF;
/*  992 */     int k = i | j << 4;
/*  993 */     BlockPos blockpos = new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */     
/*  995 */     if (blockpos.getY() == -999) {
/*  996 */       int l = getTopFilledSegment() + 15;
/*  997 */       blockpos = new BlockPos(pos.getX(), l, pos.getZ());
/*  998 */       int i1 = -1;
/*      */       
/* 1000 */       while (blockpos.getY() > 0 && i1 == -1) {
/* 1001 */         Block block = getBlock(blockpos);
/* 1002 */         Material material = block.getMaterial();
/*      */         
/* 1004 */         if (!material.blocksMovement() && !material.isLiquid()) {
/* 1005 */           blockpos = blockpos.down(); continue;
/*      */         } 
/* 1007 */         i1 = blockpos.getY() + 1;
/*      */       } 
/*      */ 
/*      */       
/* 1011 */       this.precipitationHeightMap[k] = i1;
/*      */     } 
/*      */     
/* 1014 */     return new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */   }
/*      */   
/*      */   public void func_150804_b(boolean p_150804_1_) {
/* 1018 */     if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !p_150804_1_) {
/* 1019 */       recheckGaps(this.worldObj.isRemote);
/*      */     }
/*      */     
/* 1022 */     this.field_150815_m = true;
/*      */     
/* 1024 */     if (!this.isLightPopulated && this.isTerrainPopulated) {
/* 1025 */       func_150809_p();
/*      */     }
/*      */     
/* 1028 */     while (!this.tileEntityPosQueue.isEmpty()) {
/* 1029 */       BlockPos blockpos = this.tileEntityPosQueue.poll();
/*      */       
/* 1031 */       if (getTileEntity(blockpos, EnumCreateEntityType.CHECK) == null && getBlock(blockpos).hasTileEntity()) {
/* 1032 */         TileEntity tileentity = createNewTileEntity(blockpos);
/* 1033 */         this.worldObj.setTileEntity(blockpos, tileentity);
/* 1034 */         this.worldObj.markBlockRangeForRenderUpdate(blockpos, blockpos);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isPopulated() {
/* 1040 */     return (this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChunkCoordIntPair getChunkCoordIntPair() {
/* 1047 */     return new ChunkCoordIntPair(this.xPosition, this.zPosition);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAreLevelsEmpty(int startY, int endY) {
/* 1055 */     if (startY < 0) {
/* 1056 */       startY = 0;
/*      */     }
/*      */     
/* 1059 */     if (endY >= 256) {
/* 1060 */       endY = 255;
/*      */     }
/*      */     
/* 1063 */     for (int i = startY; i <= endY; i += 16) {
/* 1064 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[i >> 4];
/*      */       
/* 1066 */       if (extendedblockstorage != null && !extendedblockstorage.isEmpty()) {
/* 1067 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1071 */     return true;
/*      */   }
/*      */   
/*      */   public void setStorageArrays(ExtendedBlockStorage[] newStorageArrays) {
/* 1075 */     if (this.storageArrays.length != newStorageArrays.length) {
/* 1076 */       logger.warn("Could not set level chunk sections, array length is " + newStorageArrays.length + " instead of " + this.storageArrays.length);
/*      */     } else {
/* 1078 */       for (int i = 0; i < this.storageArrays.length; i++) {
/* 1079 */         this.storageArrays[i] = newStorageArrays[i];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillChunk(byte[] p_177439_1_, int p_177439_2_, boolean p_177439_3_) {
/* 1088 */     int i = 0;
/* 1089 */     boolean flag = !this.worldObj.provider.getHasNoSky();
/*      */     
/* 1091 */     for (int j = 0; j < this.storageArrays.length; j++) {
/* 1092 */       if ((p_177439_2_ & 1 << j) != 0) {
/* 1093 */         if (this.storageArrays[j] == null) {
/* 1094 */           this.storageArrays[j] = new ExtendedBlockStorage(j << 4, flag);
/*      */         }
/*      */         
/* 1097 */         char[] achar = this.storageArrays[j].getData();
/*      */         
/* 1099 */         for (int k = 0; k < achar.length; k++) {
/* 1100 */           achar[k] = (char)((p_177439_1_[i + 1] & 0xFF) << 8 | p_177439_1_[i] & 0xFF);
/* 1101 */           i += 2;
/*      */         } 
/* 1103 */       } else if (p_177439_3_ && this.storageArrays[j] != null) {
/* 1104 */         this.storageArrays[j] = null;
/*      */       } 
/*      */     } 
/*      */     int l;
/* 1108 */     for (l = 0; l < this.storageArrays.length; l++) {
/* 1109 */       if ((p_177439_2_ & 1 << l) != 0 && this.storageArrays[l] != null) {
/* 1110 */         NibbleArray nibblearray = this.storageArrays[l].getBlocklightArray();
/* 1111 */         System.arraycopy(p_177439_1_, i, nibblearray.getData(), 0, (nibblearray.getData()).length);
/* 1112 */         i += (nibblearray.getData()).length;
/*      */       } 
/*      */     } 
/*      */     
/* 1116 */     if (flag) {
/* 1117 */       for (int i1 = 0; i1 < this.storageArrays.length; i1++) {
/* 1118 */         if ((p_177439_2_ & 1 << i1) != 0 && this.storageArrays[i1] != null) {
/* 1119 */           NibbleArray nibblearray1 = this.storageArrays[i1].getSkylightArray();
/* 1120 */           System.arraycopy(p_177439_1_, i, nibblearray1.getData(), 0, (nibblearray1.getData()).length);
/* 1121 */           i += (nibblearray1.getData()).length;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1126 */     if (p_177439_3_) {
/* 1127 */       System.arraycopy(p_177439_1_, i, this.blockBiomeArray, 0, this.blockBiomeArray.length);
/* 1128 */       l = i + this.blockBiomeArray.length;
/*      */     } 
/*      */     
/* 1131 */     for (int j1 = 0; j1 < this.storageArrays.length; j1++) {
/* 1132 */       if (this.storageArrays[j1] != null && (p_177439_2_ & 1 << j1) != 0) {
/* 1133 */         this.storageArrays[j1].removeInvalidBlocks();
/*      */       }
/*      */     } 
/*      */     
/* 1137 */     this.isLightPopulated = true;
/* 1138 */     this.isTerrainPopulated = true;
/* 1139 */     generateHeightMap();
/*      */     
/* 1141 */     for (TileEntity tileentity : this.chunkTileEntityMap.values()) {
/* 1142 */       tileentity.updateContainingBlockInfo();
/*      */     }
/*      */   }
/*      */   
/*      */   public BiomeGenBase getBiome(BlockPos pos, WorldChunkManager chunkManager) {
/* 1147 */     int i = pos.getX() & 0xF;
/* 1148 */     int j = pos.getZ() & 0xF;
/* 1149 */     int k = this.blockBiomeArray[j << 4 | i] & 0xFF;
/*      */     
/* 1151 */     if (k == 255) {
/* 1152 */       BiomeGenBase biomegenbase = chunkManager.getBiomeGenerator(pos, BiomeGenBase.plains);
/* 1153 */       k = biomegenbase.biomeID;
/* 1154 */       this.blockBiomeArray[j << 4 | i] = (byte)(k & 0xFF);
/*      */     } 
/*      */     
/* 1157 */     BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(k);
/* 1158 */     return (biomegenbase1 == null) ? BiomeGenBase.plains : biomegenbase1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBiomeArray() {
/* 1165 */     return this.blockBiomeArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBiomeArray(byte[] biomeArray) {
/* 1173 */     if (this.blockBiomeArray.length != biomeArray.length) {
/* 1174 */       logger.warn("Could not set level chunk biomes, array length is " + biomeArray.length + " instead of " + this.blockBiomeArray.length);
/*      */     } else {
/* 1176 */       for (int i = 0; i < this.blockBiomeArray.length; i++) {
/* 1177 */         this.blockBiomeArray[i] = biomeArray[i];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetRelightChecks() {
/* 1186 */     this.queuedLightChecks = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enqueueRelightChecks() {
/* 1195 */     BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */     
/* 1197 */     for (int i = 0; i < 8; i++) {
/* 1198 */       if (this.queuedLightChecks >= 4096) {
/*      */         return;
/*      */       }
/*      */       
/* 1202 */       int j = this.queuedLightChecks % 16;
/* 1203 */       int k = this.queuedLightChecks / 16 % 16;
/* 1204 */       int l = this.queuedLightChecks / 256;
/* 1205 */       this.queuedLightChecks++;
/*      */       
/* 1207 */       for (int i1 = 0; i1 < 16; i1++) {
/* 1208 */         BlockPos blockpos1 = blockpos.add(k, (j << 4) + i1, l);
/* 1209 */         boolean flag = !(i1 != 0 && i1 != 15 && k != 0 && k != 15 && l != 0 && l != 15);
/*      */         
/* 1211 */         if ((this.storageArrays[j] == null && flag) || (this.storageArrays[j] != null && this.storageArrays[j].getBlockByExtId(k, i1, l).getMaterial() == Material.air)) {
/* 1212 */           byte b; int m; EnumFacing[] arrayOfEnumFacing; for (m = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < m; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 1213 */             BlockPos blockpos2 = blockpos1.offset(enumfacing);
/*      */             
/* 1215 */             if (this.worldObj.getBlockState(blockpos2).getBlock().getLightValue() > 0) {
/* 1216 */               this.worldObj.checkLight(blockpos2);
/*      */             }
/*      */             b++; }
/*      */           
/* 1220 */           this.worldObj.checkLight(blockpos1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void func_150809_p() {
/* 1227 */     this.isTerrainPopulated = true;
/* 1228 */     this.isLightPopulated = true;
/* 1229 */     BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */     
/* 1231 */     if (!this.worldObj.provider.getHasNoSky()) {
/* 1232 */       if (this.worldObj.isAreaLoaded(blockpos.add(-1, 0, -1), blockpos.add(16, this.worldObj.getSeaLevel(), 16))) {
/*      */         int i;
/*      */         
/* 1235 */         label31: for (i = 0; i < 16; i++) {
/* 1236 */           for (int j = 0; j < 16; j++) {
/* 1237 */             if (!func_150811_f(i, j)) {
/* 1238 */               this.isLightPopulated = false;
/*      */               
/*      */               break label31;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1244 */         if (this.isLightPopulated) {
/* 1245 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 1246 */             int k = (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? 16 : 1;
/* 1247 */             this.worldObj.getChunkFromBlockCoords(blockpos.offset(enumfacing, k)).func_180700_a(enumfacing.getOpposite());
/*      */           } 
/*      */           
/* 1250 */           func_177441_y();
/*      */         } 
/*      */       } else {
/* 1253 */         this.isLightPopulated = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void func_177441_y() {
/* 1259 */     for (int i = 0; i < this.updateSkylightColumns.length; i++) {
/* 1260 */       this.updateSkylightColumns[i] = true;
/*      */     }
/*      */     
/* 1263 */     recheckGaps(false);
/*      */   }
/*      */   
/*      */   private void func_180700_a(EnumFacing facing) {
/* 1267 */     if (this.isTerrainPopulated) {
/* 1268 */       if (facing == EnumFacing.EAST) {
/* 1269 */         for (int i = 0; i < 16; i++) {
/* 1270 */           func_150811_f(15, i);
/*      */         }
/* 1272 */       } else if (facing == EnumFacing.WEST) {
/* 1273 */         for (int j = 0; j < 16; j++) {
/* 1274 */           func_150811_f(0, j);
/*      */         }
/* 1276 */       } else if (facing == EnumFacing.SOUTH) {
/* 1277 */         for (int k = 0; k < 16; k++) {
/* 1278 */           func_150811_f(k, 15);
/*      */         }
/* 1280 */       } else if (facing == EnumFacing.NORTH) {
/* 1281 */         for (int l = 0; l < 16; l++) {
/* 1282 */           func_150811_f(l, 0);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean func_150811_f(int x, int z) {
/* 1289 */     int i = getTopFilledSegment();
/* 1290 */     boolean flag = false;
/* 1291 */     boolean flag1 = false;
/* 1292 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos((this.xPosition << 4) + x, 0, (this.zPosition << 4) + z);
/*      */     
/* 1294 */     for (int j = i + 16 - 1; j > this.worldObj.getSeaLevel() || (j > 0 && !flag1); j--) {
/* 1295 */       blockpos$mutableblockpos.set(blockpos$mutableblockpos.getX(), j, blockpos$mutableblockpos.getZ());
/* 1296 */       int k = getBlockLightOpacity((BlockPos)blockpos$mutableblockpos);
/*      */       
/* 1298 */       if (k == 255 && blockpos$mutableblockpos.getY() < this.worldObj.getSeaLevel()) {
/* 1299 */         flag1 = true;
/*      */       }
/*      */       
/* 1302 */       if (!flag && k > 0) {
/* 1303 */         flag = true;
/* 1304 */       } else if (flag && k == 0 && !this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos)) {
/* 1305 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1309 */     for (int l = blockpos$mutableblockpos.getY(); l > 0; l--) {
/* 1310 */       blockpos$mutableblockpos.set(blockpos$mutableblockpos.getX(), l, blockpos$mutableblockpos.getZ());
/*      */       
/* 1312 */       if (getBlock((BlockPos)blockpos$mutableblockpos).getLightValue() > 0) {
/* 1313 */         this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/*      */     
/* 1317 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isLoaded() {
/* 1321 */     return this.isChunkLoaded;
/*      */   }
/*      */   
/*      */   public void setChunkLoaded(boolean loaded) {
/* 1325 */     this.isChunkLoaded = loaded;
/*      */   }
/*      */   
/*      */   public World getWorld() {
/* 1329 */     return this.worldObj;
/*      */   }
/*      */   
/*      */   public int[] getHeightMap() {
/* 1333 */     return this.heightMap;
/*      */   }
/*      */   
/*      */   public void setHeightMap(int[] newHeightMap) {
/* 1337 */     if (this.heightMap.length != newHeightMap.length) {
/* 1338 */       logger.warn("Could not set level chunk heightmap, array length is " + newHeightMap.length + " instead of " + this.heightMap.length);
/*      */     } else {
/* 1340 */       for (int i = 0; i < this.heightMap.length; i++) {
/* 1341 */         this.heightMap[i] = newHeightMap[i];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public Map<BlockPos, TileEntity> getTileEntityMap() {
/* 1347 */     return this.chunkTileEntityMap;
/*      */   }
/*      */   
/*      */   public ClassInheritanceMultiMap<Entity>[] getEntityLists() {
/* 1351 */     return this.entityLists;
/*      */   }
/*      */   
/*      */   public boolean isTerrainPopulated() {
/* 1355 */     return this.isTerrainPopulated;
/*      */   }
/*      */   
/*      */   public void setTerrainPopulated(boolean terrainPopulated) {
/* 1359 */     this.isTerrainPopulated = terrainPopulated;
/*      */   }
/*      */   
/*      */   public boolean isLightPopulated() {
/* 1363 */     return this.isLightPopulated;
/*      */   }
/*      */   
/*      */   public void setLightPopulated(boolean lightPopulated) {
/* 1367 */     this.isLightPopulated = lightPopulated;
/*      */   }
/*      */   
/*      */   public void setModified(boolean modified) {
/* 1371 */     this.isModified = modified;
/*      */   }
/*      */   
/*      */   public void setHasEntities(boolean hasEntitiesIn) {
/* 1375 */     this.hasEntities = hasEntitiesIn;
/*      */   }
/*      */   
/*      */   public void setLastSaveTime(long saveTime) {
/* 1379 */     this.lastSaveTime = saveTime;
/*      */   }
/*      */   
/*      */   public int getLowestHeight() {
/* 1383 */     return this.heightMapMinimum;
/*      */   }
/*      */   
/*      */   public long getInhabitedTime() {
/* 1387 */     return this.inhabitedTime;
/*      */   }
/*      */   
/*      */   public void setInhabitedTime(long newInhabitedTime) {
/* 1391 */     this.inhabitedTime = newInhabitedTime;
/*      */   }
/*      */   
/*      */   public enum EnumCreateEntityType {
/* 1395 */     IMMEDIATE,
/* 1396 */     QUEUED,
/* 1397 */     CHECK;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\Chunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */