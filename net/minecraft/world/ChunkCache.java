/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkCache
/*     */   implements IBlockAccess
/*     */ {
/*     */   protected int chunkX;
/*     */   protected int chunkZ;
/*     */   protected Chunk[][] chunkArray;
/*     */   protected boolean hasExtendedLevels;
/*     */   protected World worldObj;
/*     */   
/*     */   public ChunkCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
/*  28 */     this.worldObj = worldIn;
/*  29 */     this.chunkX = posFromIn.getX() - subIn >> 4;
/*  30 */     this.chunkZ = posFromIn.getZ() - subIn >> 4;
/*  31 */     int i = posToIn.getX() + subIn >> 4;
/*  32 */     int j = posToIn.getZ() + subIn >> 4;
/*  33 */     this.chunkArray = new Chunk[i - this.chunkX + 1][j - this.chunkZ + 1];
/*  34 */     this.hasExtendedLevels = true;
/*     */     
/*  36 */     for (int k = this.chunkX; k <= i; k++) {
/*  37 */       for (int l = this.chunkZ; l <= j; l++) {
/*  38 */         this.chunkArray[k - this.chunkX][l - this.chunkZ] = worldIn.getChunkFromChunkCoords(k, l);
/*     */       }
/*     */     } 
/*     */     
/*  42 */     for (int i1 = posFromIn.getX() >> 4; i1 <= posToIn.getX() >> 4; i1++) {
/*  43 */       for (int j1 = posFromIn.getZ() >> 4; j1 <= posToIn.getZ() >> 4; j1++) {
/*  44 */         Chunk chunk = this.chunkArray[i1 - this.chunkX][j1 - this.chunkZ];
/*     */         
/*  46 */         if (chunk != null && !chunk.getAreLevelsEmpty(posFromIn.getY(), posToIn.getY())) {
/*  47 */           this.hasExtendedLevels = false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedLevelsInChunkCache() {
/*  57 */     return this.hasExtendedLevels;
/*     */   }
/*     */   
/*     */   public TileEntity getTileEntity(BlockPos pos) {
/*  61 */     int i = (pos.getX() >> 4) - this.chunkX;
/*  62 */     int j = (pos.getZ() >> 4) - this.chunkZ;
/*  63 */     return this.chunkArray[i][j].getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
/*     */   }
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  67 */     int i = getLightForExt(EnumSkyBlock.SKY, pos);
/*  68 */     int j = getLightForExt(EnumSkyBlock.BLOCK, pos);
/*     */     
/*  70 */     if (j < lightValue) {
/*  71 */       j = lightValue;
/*     */     }
/*     */     
/*  74 */     return i << 20 | j << 4;
/*     */   }
/*     */   
/*     */   public IBlockState getBlockState(BlockPos pos) {
/*  78 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*  79 */       int i = (pos.getX() >> 4) - this.chunkX;
/*  80 */       int j = (pos.getZ() >> 4) - this.chunkZ;
/*     */       
/*  82 */       if (i >= 0 && i < this.chunkArray.length && j >= 0 && j < (this.chunkArray[i]).length) {
/*  83 */         Chunk chunk = this.chunkArray[i][j];
/*     */         
/*  85 */         if (chunk != null) {
/*  86 */           return chunk.getBlockState(pos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     return Blocks.air.getDefaultState();
/*     */   }
/*     */   
/*     */   public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
/*  95 */     return this.worldObj.getBiomeGenForCoords(pos);
/*     */   }
/*     */   
/*     */   private int getLightForExt(EnumSkyBlock p_175629_1_, BlockPos pos) {
/*  99 */     if (p_175629_1_ == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky())
/* 100 */       return 0; 
/* 101 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/* 102 */       if (getBlockState(pos).getBlock().getUseNeighborBrightness()) {
/* 103 */         int l = 0; byte b; int k;
/*     */         EnumFacing[] arrayOfEnumFacing;
/* 105 */         for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 106 */           int m = getLightFor(p_175629_1_, pos.offset(enumfacing));
/*     */           
/* 108 */           if (m > l) {
/* 109 */             l = m;
/*     */           }
/*     */           
/* 112 */           if (l >= 15) {
/* 113 */             return l;
/*     */           }
/*     */           b++; }
/*     */         
/* 117 */         return l;
/*     */       } 
/* 119 */       int i = (pos.getX() >> 4) - this.chunkX;
/* 120 */       int j = (pos.getZ() >> 4) - this.chunkZ;
/* 121 */       return this.chunkArray[i][j].getLightFor(p_175629_1_, pos);
/*     */     } 
/*     */     
/* 124 */     return p_175629_1_.defaultLightValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAirBlock(BlockPos pos) {
/* 133 */     return (getBlockState(pos).getBlock().getMaterial() == Material.air);
/*     */   }
/*     */   
/*     */   public int getLightFor(EnumSkyBlock p_175628_1_, BlockPos pos) {
/* 137 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/* 138 */       int i = (pos.getX() >> 4) - this.chunkX;
/* 139 */       int j = (pos.getZ() >> 4) - this.chunkZ;
/* 140 */       return this.chunkArray[i][j].getLightFor(p_175628_1_, pos);
/*     */     } 
/* 142 */     return p_175628_1_.defaultLightValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 147 */     IBlockState iblockstate = getBlockState(pos);
/* 148 */     return iblockstate.getBlock().getStrongPower(this, pos, iblockstate, direction);
/*     */   }
/*     */   
/*     */   public WorldType getWorldType() {
/* 152 */     return this.worldObj.getWorldType();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\ChunkCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */