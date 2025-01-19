/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.DynamicLights;
/*     */ 
/*     */ public class RegionRenderCache
/*     */   extends ChunkCache {
/*  18 */   private static final IBlockState DEFAULT_STATE = Blocks.air.getDefaultState();
/*     */   private final BlockPos position;
/*     */   private int[] combinedLights;
/*     */   private IBlockState[] blockStates;
/*  22 */   private static ArrayDeque<int[]> cacheLights = (ArrayDeque)new ArrayDeque<>();
/*  23 */   private static ArrayDeque<IBlockState[]> cacheStates = (ArrayDeque)new ArrayDeque<>();
/*  24 */   private static int maxCacheSize = Config.limit(Runtime.getRuntime().availableProcessors(), 1, 32);
/*     */   
/*     */   public RegionRenderCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  27 */     super(worldIn, posFromIn, posToIn, subIn);
/*  28 */     this.position = posFromIn.subtract(new Vec3i(subIn, subIn, subIn));
/*  29 */     int i = 8000;
/*  30 */     this.combinedLights = allocateLights(8000);
/*  31 */     Arrays.fill(this.combinedLights, -1);
/*  32 */     this.blockStates = allocateStates(8000);
/*     */   }
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/*  36 */     int i = (pos.getX() >> 4) - this.chunkX;
/*  37 */     int j = (pos.getZ() >> 4) - this.chunkZ;
/*  38 */     return this.chunkArray[i][j].getTileEntity(pos, Chunk.EnumCreateEntityType.QUEUED);
/*     */   }
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  42 */     int i = getPositionIndex(pos);
/*  43 */     int j = this.combinedLights[i];
/*     */     
/*  45 */     if (j == -1) {
/*  46 */       j = super.getCombinedLight(pos, lightValue);
/*     */       
/*  48 */       if (Config.isDynamicLights() && !getBlockState(pos).getBlock().isOpaqueCube()) {
/*  49 */         j = DynamicLights.getCombinedLight(pos, j);
/*     */       }
/*     */       
/*  52 */       this.combinedLights[i] = j;
/*     */     } 
/*     */     
/*  55 */     return j;
/*     */   }
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/*  59 */     int i = getPositionIndex(pos);
/*  60 */     IBlockState iblockstate = this.blockStates[i];
/*     */     
/*  62 */     if (iblockstate == null) {
/*  63 */       iblockstate = getBlockStateRaw(pos);
/*  64 */       this.blockStates[i] = iblockstate;
/*     */     } 
/*     */     
/*  67 */     return iblockstate;
/*     */   }
/*     */   
/*     */   private IBlockState getBlockStateRaw(BlockPos pos) {
/*  71 */     return super.getBlockState(pos);
/*     */   }
/*     */   
/*     */   private int getPositionIndex(BlockPos p_175630_1_) {
/*  75 */     int i = p_175630_1_.getX() - this.position.getX();
/*  76 */     int j = p_175630_1_.getY() - this.position.getY();
/*  77 */     int k = p_175630_1_.getZ() - this.position.getZ();
/*  78 */     return i * 400 + k * 20 + j;
/*     */   }
/*     */   
/*     */   public void freeBuffers() {
/*  82 */     freeLights(this.combinedLights);
/*  83 */     freeStates(this.blockStates);
/*     */   }
/*     */   
/*     */   private static int[] allocateLights(int p_allocateLights_0_) {
/*  87 */     synchronized (cacheLights) {
/*  88 */       int[] aint = cacheLights.pollLast();
/*     */       
/*  90 */       if (aint == null || aint.length < p_allocateLights_0_) {
/*  91 */         aint = new int[p_allocateLights_0_];
/*     */       }
/*     */       
/*  94 */       return aint;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void freeLights(int[] p_freeLights_0_) {
/*  99 */     synchronized (cacheLights) {
/* 100 */       if (cacheLights.size() < maxCacheSize) {
/* 101 */         cacheLights.add(p_freeLights_0_);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IBlockState[] allocateStates(int p_allocateStates_0_) {
/* 107 */     synchronized (cacheStates) {
/* 108 */       IBlockState[] aiblockstate = cacheStates.pollLast();
/*     */       
/* 110 */       if (aiblockstate != null && aiblockstate.length >= p_allocateStates_0_) {
/* 111 */         Arrays.fill((Object[])aiblockstate, (Object)null);
/*     */       } else {
/* 113 */         aiblockstate = new IBlockState[p_allocateStates_0_];
/*     */       } 
/*     */       
/* 116 */       return aiblockstate;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void freeStates(IBlockState[] p_freeStates_0_) {
/* 121 */     synchronized (cacheStates) {
/* 122 */       if (cacheStates.size() < maxCacheSize)
/* 123 */         cacheStates.add(p_freeStates_0_); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\RegionRenderCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */