/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.LongHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeCache
/*    */ {
/*    */   private final WorldChunkManager chunkManager;
/*    */   private long lastCleanupTime;
/* 20 */   private LongHashMap<Block> cacheMap = new LongHashMap();
/* 21 */   private List<Block> cache = Lists.newArrayList();
/*    */   
/*    */   public BiomeCache(WorldChunkManager chunkManagerIn) {
/* 24 */     this.chunkManager = chunkManagerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getBiomeCacheBlock(int x, int z) {
/* 31 */     x >>= 4;
/* 32 */     z >>= 4;
/* 33 */     long i = x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32L;
/* 34 */     Block biomecache$block = (Block)this.cacheMap.getValueByKey(i);
/*    */     
/* 36 */     if (biomecache$block == null) {
/* 37 */       biomecache$block = new Block(x, z);
/* 38 */       this.cacheMap.add(i, biomecache$block);
/* 39 */       this.cache.add(biomecache$block);
/*    */     } 
/*    */     
/* 42 */     biomecache$block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
/* 43 */     return biomecache$block;
/*    */   }
/*    */   
/*    */   public BiomeGenBase func_180284_a(int x, int z, BiomeGenBase p_180284_3_) {
/* 47 */     BiomeGenBase biomegenbase = getBiomeCacheBlock(x, z).getBiomeGenAt(x, z);
/* 48 */     return (biomegenbase == null) ? p_180284_3_ : biomegenbase;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void cleanupCache() {
/* 55 */     long i = MinecraftServer.getCurrentTimeMillis();
/* 56 */     long j = i - this.lastCleanupTime;
/*    */     
/* 58 */     if (j > 7500L || j < 0L) {
/* 59 */       this.lastCleanupTime = i;
/*    */       
/* 61 */       for (int k = 0; k < this.cache.size(); k++) {
/* 62 */         Block biomecache$block = this.cache.get(k);
/* 63 */         long l = i - biomecache$block.lastAccessTime;
/*    */         
/* 65 */         if (l > 30000L || l < 0L) {
/* 66 */           this.cache.remove(k--);
/* 67 */           long i1 = biomecache$block.xPosition & 0xFFFFFFFFL | (biomecache$block.zPosition & 0xFFFFFFFFL) << 32L;
/* 68 */           this.cacheMap.remove(i1);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BiomeGenBase[] getCachedBiomes(int x, int z) {
/* 78 */     return (getBiomeCacheBlock(x, z)).biomes;
/*    */   }
/*    */   
/*    */   public class Block {
/* 82 */     public float[] rainfallValues = new float[256];
/* 83 */     public BiomeGenBase[] biomes = new BiomeGenBase[256];
/*    */     public int xPosition;
/*    */     public int zPosition;
/*    */     public long lastAccessTime;
/*    */     
/*    */     public Block(int x, int z) {
/* 89 */       this.xPosition = x;
/* 90 */       this.zPosition = z;
/* 91 */       BiomeCache.this.chunkManager.getRainfall(this.rainfallValues, x << 4, z << 4, 16, 16);
/* 92 */       BiomeCache.this.chunkManager.getBiomeGenAt(this.biomes, x << 4, z << 4, 16, 16, false);
/*    */     }
/*    */     
/*    */     public BiomeGenBase getBiomeGenAt(int x, int z) {
/* 96 */       return this.biomes[x & 0xF | (z & 0xF) << 4];
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */