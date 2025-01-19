/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.EmptyChunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkProviderServer
/*     */   implements IChunkProvider
/*     */ {
/*  31 */   private static final Logger logger = LogManager.getLogger();
/*  32 */   private Set<Long> droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */ 
/*     */ 
/*     */   
/*     */   private Chunk dummyChunk;
/*     */ 
/*     */ 
/*     */   
/*     */   private IChunkProvider serverChunkGenerator;
/*     */ 
/*     */ 
/*     */   
/*     */   private IChunkLoader chunkLoader;
/*     */ 
/*     */   
/*     */   public boolean chunkLoadOverride = true;
/*     */ 
/*     */   
/*  50 */   private LongHashMap<Chunk> id2ChunkMap = new LongHashMap();
/*  51 */   private List<Chunk> loadedChunks = Lists.newArrayList();
/*     */   private WorldServer worldObj;
/*     */   
/*     */   public ChunkProviderServer(WorldServer p_i1520_1_, IChunkLoader p_i1520_2_, IChunkProvider p_i1520_3_) {
/*  55 */     this.dummyChunk = (Chunk)new EmptyChunk((World)p_i1520_1_, 0, 0);
/*  56 */     this.worldObj = p_i1520_1_;
/*  57 */     this.chunkLoader = p_i1520_2_;
/*  58 */     this.serverChunkGenerator = p_i1520_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/*  65 */     return this.id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(x, z));
/*     */   }
/*     */   
/*     */   public List<Chunk> func_152380_a() {
/*  69 */     return this.loadedChunks;
/*     */   }
/*     */   
/*     */   public void dropChunk(int x, int z) {
/*  73 */     if (this.worldObj.provider.canRespawnHere()) {
/*  74 */       if (!this.worldObj.isSpawnChunk(x, z)) {
/*  75 */         this.droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(x, z)));
/*     */       }
/*     */     } else {
/*  78 */       this.droppedChunksSet.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(x, z)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadAllChunks() {
/*  86 */     for (Chunk chunk : this.loadedChunks) {
/*  87 */       dropChunk(chunk.xPosition, chunk.zPosition);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(int chunkX, int chunkZ) {
/*  98 */     long i = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
/*  99 */     this.droppedChunksSet.remove(Long.valueOf(i));
/* 100 */     Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(i);
/*     */     
/* 102 */     if (chunk == null) {
/* 103 */       chunk = loadChunkFromFile(chunkX, chunkZ);
/*     */       
/* 105 */       if (chunk == null) {
/* 106 */         if (this.serverChunkGenerator == null) {
/* 107 */           chunk = this.dummyChunk;
/*     */         } else {
/*     */           try {
/* 110 */             chunk = this.serverChunkGenerator.provideChunk(chunkX, chunkZ);
/* 111 */           } catch (Throwable throwable) {
/* 112 */             CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception generating new chunk");
/* 113 */             CrashReportCategory crashreportcategory = crashreport.makeCategory("Chunk to be generated");
/* 114 */             crashreportcategory.addCrashSection("Location", String.format("%d,%d", new Object[] { Integer.valueOf(chunkX), Integer.valueOf(chunkZ) }));
/* 115 */             crashreportcategory.addCrashSection("Position hash", Long.valueOf(i));
/* 116 */             crashreportcategory.addCrashSection("Generator", this.serverChunkGenerator.makeString());
/* 117 */             throw new ReportedException(crashreport);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 122 */       this.id2ChunkMap.add(i, chunk);
/* 123 */       this.loadedChunks.add(chunk);
/* 124 */       chunk.onChunkLoad();
/* 125 */       chunk.populateChunk(this, this, chunkX, chunkZ);
/*     */     } 
/*     */     
/* 128 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 136 */     Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(x, z));
/* 137 */     return (chunk == null) ? ((!this.worldObj.isFindingSpawnPoint() && !this.chunkLoadOverride) ? this.dummyChunk : loadChunk(x, z)) : chunk;
/*     */   }
/*     */   
/*     */   private Chunk loadChunkFromFile(int x, int z) {
/* 141 */     if (this.chunkLoader == null) {
/* 142 */       return null;
/*     */     }
/*     */     try {
/* 145 */       Chunk chunk = this.chunkLoader.loadChunk((World)this.worldObj, x, z);
/*     */       
/* 147 */       if (chunk != null) {
/* 148 */         chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
/*     */         
/* 150 */         if (this.serverChunkGenerator != null) {
/* 151 */           this.serverChunkGenerator.recreateStructures(chunk, x, z);
/*     */         }
/*     */       } 
/*     */       
/* 155 */       return chunk;
/* 156 */     } catch (Exception exception) {
/* 157 */       logger.error("Couldn't load chunk", exception);
/* 158 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void saveChunkExtraData(Chunk chunkIn) {
/* 164 */     if (this.chunkLoader != null) {
/*     */       try {
/* 166 */         this.chunkLoader.saveExtraChunkData((World)this.worldObj, chunkIn);
/* 167 */       } catch (Exception exception) {
/* 168 */         logger.error("Couldn't save entities", exception);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveChunkData(Chunk chunkIn) {
/* 174 */     if (this.chunkLoader != null) {
/*     */       try {
/* 176 */         chunkIn.setLastSaveTime(this.worldObj.getTotalWorldTime());
/* 177 */         this.chunkLoader.saveChunk((World)this.worldObj, chunkIn);
/* 178 */       } catch (IOException ioexception) {
/* 179 */         logger.error("Couldn't save chunk", ioexception);
/* 180 */       } catch (MinecraftException minecraftexception) {
/* 181 */         logger.error("Couldn't save chunk; already in use by another instance of Minecraft?", (Throwable)minecraftexception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 190 */     Chunk chunk = provideChunk(x, z);
/*     */     
/* 192 */     if (!chunk.isTerrainPopulated()) {
/* 193 */       chunk.func_150809_p();
/*     */       
/* 195 */       if (this.serverChunkGenerator != null) {
/* 196 */         this.serverChunkGenerator.populate(chunkProvider, x, z);
/* 197 */         chunk.setChunkModified();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 203 */     if (this.serverChunkGenerator != null && this.serverChunkGenerator.populateChunk(chunkProvider, chunkIn, x, z)) {
/* 204 */       Chunk chunk = provideChunk(x, z);
/* 205 */       chunk.setChunkModified();
/* 206 */       return true;
/*     */     } 
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 217 */     int i = 0;
/* 218 */     List<Chunk> list = Lists.newArrayList(this.loadedChunks);
/*     */     
/* 220 */     for (int j = 0; j < list.size(); j++) {
/* 221 */       Chunk chunk = list.get(j);
/*     */       
/* 223 */       if (saveAllChunks) {
/* 224 */         saveChunkExtraData(chunk);
/*     */       }
/*     */       
/* 227 */       if (chunk.needsSaving(saveAllChunks)) {
/* 228 */         saveChunkData(chunk);
/* 229 */         chunk.setModified(false);
/* 230 */         i++;
/*     */         
/* 232 */         if (i == 24 && !saveAllChunks) {
/* 233 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/* 246 */     if (this.chunkLoader != null) {
/* 247 */       this.chunkLoader.saveExtraData();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 255 */     if (!this.worldObj.disableLevelSaving) {
/* 256 */       for (int i = 0; i < 100; i++) {
/* 257 */         if (!this.droppedChunksSet.isEmpty()) {
/* 258 */           Long olong = this.droppedChunksSet.iterator().next();
/* 259 */           Chunk chunk = (Chunk)this.id2ChunkMap.getValueByKey(olong.longValue());
/*     */           
/* 261 */           if (chunk != null) {
/* 262 */             chunk.onChunkUnload();
/* 263 */             saveChunkData(chunk);
/* 264 */             saveChunkExtraData(chunk);
/* 265 */             this.id2ChunkMap.remove(olong.longValue());
/* 266 */             this.loadedChunks.remove(chunk);
/*     */           } 
/*     */           
/* 269 */           this.droppedChunksSet.remove(olong);
/*     */         } 
/*     */       } 
/*     */       
/* 273 */       if (this.chunkLoader != null) {
/* 274 */         this.chunkLoader.chunkTick();
/*     */       }
/*     */     } 
/*     */     
/* 278 */     return this.serverChunkGenerator.unloadQueuedChunks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 285 */     return !this.worldObj.disableLevelSaving;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 292 */     return "ServerChunkCache: " + this.id2ChunkMap.getNumHashElements() + " Drop: " + this.droppedChunksSet.size();
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 296 */     return this.serverChunkGenerator.getPossibleCreatures(creatureType, pos);
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 300 */     return this.serverChunkGenerator.getStrongholdGen(worldIn, structureName, position);
/*     */   }
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 304 */     return this.id2ChunkMap.getNumHashElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 311 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\ChunkProviderServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */