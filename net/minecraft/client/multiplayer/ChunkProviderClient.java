/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.EmptyChunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkProviderClient
/*     */   implements IChunkProvider
/*     */ {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private Chunk blankChunk;
/*     */ 
/*     */   
/*  28 */   private LongHashMap<Chunk> chunkMapping = new LongHashMap();
/*  29 */   private List<Chunk> chunkListing = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private World worldObj;
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkProviderClient(World worldIn) {
/*  37 */     this.blankChunk = (Chunk)new EmptyChunk(worldIn, 0, 0);
/*  38 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadChunk(int x, int z) {
/*  53 */     Chunk chunk = provideChunk(x, z);
/*     */     
/*  55 */     if (!chunk.isEmpty()) {
/*  56 */       chunk.onChunkUnload();
/*     */     }
/*     */     
/*  59 */     this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(x, z));
/*  60 */     this.chunkListing.remove(chunk);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(int chunkX, int chunkZ) {
/*  70 */     Chunk chunk = new Chunk(this.worldObj, chunkX, chunkZ);
/*  71 */     this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ), chunk);
/*  72 */     this.chunkListing.add(chunk);
/*  73 */     chunk.setChunkLoaded(true);
/*  74 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/*  82 */     Chunk chunk = (Chunk)this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(x, z));
/*  83 */     return (chunk == null) ? this.blankChunk : chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 105 */     long i = System.currentTimeMillis();
/*     */     
/* 107 */     for (Chunk chunk : this.chunkListing) {
/* 108 */       chunk.func_150804_b((System.currentTimeMillis() - i > 5L));
/*     */     }
/*     */     
/* 111 */     if (System.currentTimeMillis() - i > 100L) {
/* 112 */       logger.info("Warning: Clientside chunk ticking took {} ms", new Object[] { Long.valueOf(System.currentTimeMillis() - i) });
/*     */     }
/*     */     
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 139 */     return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 147 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 151 */     return this.chunkListing.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 158 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\ChunkProviderClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */