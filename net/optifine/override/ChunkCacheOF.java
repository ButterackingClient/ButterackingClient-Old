/*     */ package net.optifine.override;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.ArrayCache;
/*     */ 
/*     */ public class ChunkCacheOF
/*     */   implements IBlockAccess {
/*     */   private final ChunkCache chunkCache;
/*     */   private final int posX;
/*     */   private final int posY;
/*     */   private final int posZ;
/*     */   private final int sizeX;
/*     */   private final int sizeY;
/*     */   private final int sizeZ;
/*     */   private final int sizeXY;
/*     */   private int[] combinedLights;
/*     */   private IBlockState[] blockStates;
/*     */   private final int arraySize;
/*  30 */   private final boolean dynamicLights = Config.isDynamicLights();
/*  31 */   private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
/*  32 */   private static final ArrayCache cacheBlockStates = new ArrayCache(IBlockState.class, 16);
/*     */   
/*     */   public ChunkCacheOF(ChunkCache chunkCache, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  35 */     this.chunkCache = chunkCache;
/*  36 */     int i = posFromIn.getX() - subIn >> 4;
/*  37 */     int j = posFromIn.getY() - subIn >> 4;
/*  38 */     int k = posFromIn.getZ() - subIn >> 4;
/*  39 */     int l = posToIn.getX() + subIn >> 4;
/*  40 */     int i1 = posToIn.getY() + subIn >> 4;
/*  41 */     int j1 = posToIn.getZ() + subIn >> 4;
/*  42 */     this.sizeX = l - i + 1 << 4;
/*  43 */     this.sizeY = i1 - j + 1 << 4;
/*  44 */     this.sizeZ = j1 - k + 1 << 4;
/*  45 */     this.sizeXY = this.sizeX * this.sizeY;
/*  46 */     this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
/*  47 */     this.posX = i << 4;
/*  48 */     this.posY = j << 4;
/*  49 */     this.posZ = k << 4;
/*     */   }
/*     */   
/*     */   private int getPositionIndex(BlockPos pos) {
/*  53 */     int i = pos.getX() - this.posX;
/*     */     
/*  55 */     if (i >= 0 && i < this.sizeX) {
/*  56 */       int j = pos.getY() - this.posY;
/*     */       
/*  58 */       if (j >= 0 && j < this.sizeY) {
/*  59 */         int k = pos.getZ() - this.posZ;
/*  60 */         return (k >= 0 && k < this.sizeZ) ? (k * this.sizeXY + j * this.sizeX + i) : -1;
/*     */       } 
/*  62 */       return -1;
/*     */     } 
/*     */     
/*  65 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  70 */     int i = getPositionIndex(pos);
/*     */     
/*  72 */     if (i >= 0 && i < this.arraySize && this.combinedLights != null) {
/*  73 */       int j = this.combinedLights[i];
/*     */       
/*  75 */       if (j == -1) {
/*  76 */         j = getCombinedLightRaw(pos, lightValue);
/*  77 */         this.combinedLights[i] = j;
/*     */       } 
/*     */       
/*  80 */       return j;
/*     */     } 
/*  82 */     return getCombinedLightRaw(pos, lightValue);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getCombinedLightRaw(BlockPos pos, int lightValue) {
/*  87 */     int i = this.chunkCache.getCombinedLight(pos, lightValue);
/*     */     
/*  89 */     if (this.dynamicLights && !getBlockState(pos).getBlock().isOpaqueCube()) {
/*  90 */       i = DynamicLights.getCombinedLight(pos, i);
/*     */     }
/*     */     
/*  93 */     return i;
/*     */   }
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/*  97 */     int i = getPositionIndex(pos);
/*     */     
/*  99 */     if (i >= 0 && i < this.arraySize && this.blockStates != null) {
/* 100 */       IBlockState iblockstate = this.blockStates[i];
/*     */       
/* 102 */       if (iblockstate == null) {
/* 103 */         iblockstate = this.chunkCache.getBlockState(pos);
/* 104 */         this.blockStates[i] = iblockstate;
/*     */       } 
/*     */       
/* 107 */       return iblockstate;
/*     */     } 
/* 109 */     return this.chunkCache.getBlockState(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderStart() {
/* 114 */     if (this.combinedLights == null) {
/* 115 */       this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize);
/*     */     }
/*     */     
/* 118 */     Arrays.fill(this.combinedLights, -1);
/*     */     
/* 120 */     if (this.blockStates == null) {
/* 121 */       this.blockStates = (IBlockState[])cacheBlockStates.allocate(this.arraySize);
/*     */     }
/*     */     
/* 124 */     Arrays.fill((Object[])this.blockStates, (Object)null);
/*     */   }
/*     */   
/*     */   public void renderFinish() {
/* 128 */     cacheCombinedLights.free(this.combinedLights);
/* 129 */     this.combinedLights = null;
/* 130 */     cacheBlockStates.free(this.blockStates);
/* 131 */     this.blockStates = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedLevelsInChunkCache() {
/* 138 */     return this.chunkCache.extendedLevelsInChunkCache();
/*     */   }
/*     */   
/*     */   public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
/* 142 */     return this.chunkCache.getBiomeGenForCoords(pos);
/*     */   }
/*     */   
/*     */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 146 */     return this.chunkCache.getStrongPower(pos, direction);
/*     */   }
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/* 150 */     return this.chunkCache.getTileEntity(pos);
/*     */   }
/*     */   
/*     */   public WorldType getWorldType() {
/* 154 */     return this.chunkCache.getWorldType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAirBlock(BlockPos pos) {
/* 162 */     return this.chunkCache.isAirBlock(pos);
/*     */   }
/*     */   
/*     */   public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
/* 166 */     return Reflector.callBoolean(this.chunkCache, Reflector.ForgeChunkCache_isSideSolid, new Object[] { pos, side, Boolean.valueOf(_default) });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\override\ChunkCacheOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */