/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.render.VboRegion;
/*     */ 
/*     */ public class ViewFrustum
/*     */ {
/*     */   protected final RenderGlobal renderGlobal;
/*     */   protected final World world;
/*     */   protected int countChunksY;
/*     */   protected int countChunksX;
/*     */   protected int countChunksZ;
/*     */   public RenderChunk[] renderChunks;
/*  24 */   private Map<ChunkCoordIntPair, VboRegion[]> mapVboRegions = (Map)new HashMap<>();
/*     */   
/*     */   public ViewFrustum(World worldIn, int renderDistanceChunks, RenderGlobal p_i46246_3_, IRenderChunkFactory renderChunkFactory) {
/*  27 */     this.renderGlobal = p_i46246_3_;
/*  28 */     this.world = worldIn;
/*  29 */     setCountChunksXYZ(renderDistanceChunks);
/*  30 */     createRenderChunks(renderChunkFactory);
/*     */   }
/*     */   
/*     */   protected void createRenderChunks(IRenderChunkFactory renderChunkFactory) {
/*  34 */     int i = this.countChunksX * this.countChunksY * this.countChunksZ;
/*  35 */     this.renderChunks = new RenderChunk[i];
/*  36 */     int j = 0;
/*     */     
/*  38 */     for (int k = 0; k < this.countChunksX; k++) {
/*  39 */       for (int l = 0; l < this.countChunksY; l++) {
/*  40 */         for (int i1 = 0; i1 < this.countChunksZ; i1++) {
/*  41 */           int j1 = (i1 * this.countChunksY + l) * this.countChunksX + k;
/*  42 */           BlockPos blockpos = new BlockPos(k * 16, l * 16, i1 * 16);
/*  43 */           this.renderChunks[j1] = renderChunkFactory.makeRenderChunk(this.world, this.renderGlobal, blockpos, j++);
/*     */           
/*  45 */           if (Config.isVbo() && Config.isRenderRegions()) {
/*  46 */             updateVboRegion(this.renderChunks[j1]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     for (int k1 = 0; k1 < this.renderChunks.length; k1++) {
/*  53 */       RenderChunk renderchunk1 = this.renderChunks[k1];
/*     */       
/*  55 */       for (int l1 = 0; l1 < EnumFacing.VALUES.length; l1++) {
/*  56 */         EnumFacing enumfacing = EnumFacing.VALUES[l1];
/*  57 */         BlockPos blockpos1 = renderchunk1.getBlockPosOffset16(enumfacing);
/*  58 */         RenderChunk renderchunk = getRenderChunk(blockpos1);
/*  59 */         renderchunk1.setRenderChunkNeighbour(enumfacing, renderchunk);
/*     */       } 
/*     */     }  } public void deleteGlResources() {
/*     */     byte b;
/*     */     int i;
/*     */     RenderChunk[] arrayOfRenderChunk;
/*  65 */     for (i = (arrayOfRenderChunk = this.renderChunks).length, b = 0; b < i; ) { RenderChunk renderchunk = arrayOfRenderChunk[b];
/*  66 */       renderchunk.deleteGlResources();
/*     */       b++; }
/*     */     
/*  69 */     deleteVboRegions();
/*     */   }
/*     */   
/*     */   protected void setCountChunksXYZ(int renderDistanceChunks) {
/*  73 */     int i = renderDistanceChunks * 2 + 1;
/*  74 */     this.countChunksX = i;
/*  75 */     this.countChunksY = 16;
/*  76 */     this.countChunksZ = i;
/*     */   }
/*     */   
/*     */   public void updateChunkPositions(double viewEntityX, double viewEntityZ) {
/*  80 */     int i = MathHelper.floor_double(viewEntityX) - 8;
/*  81 */     int j = MathHelper.floor_double(viewEntityZ) - 8;
/*  82 */     int k = this.countChunksX * 16;
/*     */     
/*  84 */     for (int l = 0; l < this.countChunksX; l++) {
/*  85 */       int i1 = func_178157_a(i, k, l);
/*     */       
/*  87 */       for (int j1 = 0; j1 < this.countChunksZ; j1++) {
/*  88 */         int k1 = func_178157_a(j, k, j1);
/*     */         
/*  90 */         for (int l1 = 0; l1 < this.countChunksY; l1++) {
/*  91 */           int i2 = l1 * 16;
/*  92 */           RenderChunk renderchunk = this.renderChunks[(j1 * this.countChunksY + l1) * this.countChunksX + l];
/*  93 */           BlockPos blockpos = renderchunk.getPosition();
/*     */           
/*  95 */           if (blockpos.getX() != i1 || blockpos.getY() != i2 || blockpos.getZ() != k1) {
/*  96 */             BlockPos blockpos1 = new BlockPos(i1, i2, k1);
/*     */             
/*  98 */             if (!blockpos1.equals(renderchunk.getPosition())) {
/*  99 */               renderchunk.setPosition(blockpos1);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int func_178157_a(int p_178157_1_, int p_178157_2_, int p_178157_3_) {
/* 108 */     int i = p_178157_3_ * 16;
/* 109 */     int j = i - p_178157_1_ + p_178157_2_ / 2;
/*     */     
/* 111 */     if (j < 0) {
/* 112 */       j -= p_178157_2_ - 1;
/*     */     }
/*     */     
/* 115 */     return i - j / p_178157_2_ * p_178157_2_;
/*     */   }
/*     */   
/*     */   public void markBlocksForUpdate(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
/* 119 */     int i = MathHelper.bucketInt(fromX, 16);
/* 120 */     int j = MathHelper.bucketInt(fromY, 16);
/* 121 */     int k = MathHelper.bucketInt(fromZ, 16);
/* 122 */     int l = MathHelper.bucketInt(toX, 16);
/* 123 */     int i1 = MathHelper.bucketInt(toY, 16);
/* 124 */     int j1 = MathHelper.bucketInt(toZ, 16);
/*     */     
/* 126 */     for (int k1 = i; k1 <= l; k1++) {
/* 127 */       int l1 = k1 % this.countChunksX;
/*     */       
/* 129 */       if (l1 < 0) {
/* 130 */         l1 += this.countChunksX;
/*     */       }
/*     */       
/* 133 */       for (int i2 = j; i2 <= i1; i2++) {
/* 134 */         int j2 = i2 % this.countChunksY;
/*     */         
/* 136 */         if (j2 < 0) {
/* 137 */           j2 += this.countChunksY;
/*     */         }
/*     */         
/* 140 */         for (int k2 = k; k2 <= j1; k2++) {
/* 141 */           int l2 = k2 % this.countChunksZ;
/*     */           
/* 143 */           if (l2 < 0) {
/* 144 */             l2 += this.countChunksZ;
/*     */           }
/*     */           
/* 147 */           int i3 = (l2 * this.countChunksY + j2) * this.countChunksX + l1;
/* 148 */           RenderChunk renderchunk = this.renderChunks[i3];
/* 149 */           renderchunk.setNeedsUpdate(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public RenderChunk getRenderChunk(BlockPos pos) {
/* 156 */     int i = pos.getX() >> 4;
/* 157 */     int j = pos.getY() >> 4;
/* 158 */     int k = pos.getZ() >> 4;
/*     */     
/* 160 */     if (j >= 0 && j < this.countChunksY) {
/* 161 */       i %= this.countChunksX;
/*     */       
/* 163 */       if (i < 0) {
/* 164 */         i += this.countChunksX;
/*     */       }
/*     */       
/* 167 */       k %= this.countChunksZ;
/*     */       
/* 169 */       if (k < 0) {
/* 170 */         k += this.countChunksZ;
/*     */       }
/*     */       
/* 173 */       int l = (k * this.countChunksY + j) * this.countChunksX + i;
/* 174 */       return this.renderChunks[l];
/*     */     } 
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateVboRegion(RenderChunk p_updateVboRegion_1_) {
/* 181 */     BlockPos blockpos = p_updateVboRegion_1_.getPosition();
/* 182 */     int i = blockpos.getX() >> 8 << 8;
/* 183 */     int j = blockpos.getZ() >> 8 << 8;
/* 184 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
/* 185 */     EnumWorldBlockLayer[] aenumworldblocklayer = RenderChunk.ENUM_WORLD_BLOCK_LAYERS;
/* 186 */     VboRegion[] avboregion = this.mapVboRegions.get(chunkcoordintpair);
/*     */     
/* 188 */     if (avboregion == null) {
/* 189 */       avboregion = new VboRegion[aenumworldblocklayer.length];
/*     */       
/* 191 */       for (int k = 0; k < aenumworldblocklayer.length; k++) {
/* 192 */         avboregion[k] = new VboRegion(aenumworldblocklayer[k]);
/*     */       }
/*     */       
/* 195 */       this.mapVboRegions.put(chunkcoordintpair, avboregion);
/*     */     } 
/*     */     
/* 198 */     for (int l = 0; l < aenumworldblocklayer.length; l++) {
/* 199 */       VboRegion vboregion = avboregion[l];
/*     */       
/* 201 */       if (vboregion != null) {
/* 202 */         p_updateVboRegion_1_.getVertexBufferByLayer(l).setVboRegion(vboregion);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteVboRegions() {
/* 208 */     for (ChunkCoordIntPair chunkcoordintpair : this.mapVboRegions.keySet()) {
/* 209 */       VboRegion[] avboregion = this.mapVboRegions.get(chunkcoordintpair);
/*     */       
/* 211 */       for (int i = 0; i < avboregion.length; i++) {
/* 212 */         VboRegion vboregion = avboregion[i];
/*     */         
/* 214 */         if (vboregion != null) {
/* 215 */           vboregion.deleteGlBuffers();
/*     */         }
/*     */         
/* 218 */         avboregion[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 222 */     this.mapVboRegions.clear();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\ViewFrustum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */