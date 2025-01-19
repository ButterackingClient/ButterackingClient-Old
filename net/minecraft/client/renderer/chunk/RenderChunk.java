/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCache;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.ViewFrustum;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.CustomBlockLayers;
/*     */ import net.optifine.override.ChunkCacheOF;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.render.AabbFrame;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ 
/*     */ 
/*     */ public class RenderChunk
/*     */ {
/*     */   private final World world;
/*     */   private final RenderGlobal renderGlobal;
/*     */   public static int renderChunksUpdated;
/*     */   private BlockPos position;
/*  55 */   public CompiledChunk compiledChunk = CompiledChunk.DUMMY;
/*  56 */   private final ReentrantLock lockCompileTask = new ReentrantLock();
/*  57 */   private final ReentrantLock lockCompiledChunk = new ReentrantLock();
/*  58 */   private ChunkCompileTaskGenerator compileTask = null;
/*  59 */   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
/*     */   private final int index;
/*  61 */   private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
/*  62 */   private final VertexBuffer[] vertexBuffers = new VertexBuffer[(EnumWorldBlockLayer.values()).length];
/*     */   public AxisAlignedBB boundingBox;
/*  64 */   private int frameIndex = -1;
/*     */   private boolean needsUpdate = true;
/*  66 */   private EnumMap<EnumFacing, BlockPos> mapEnumFacing = null;
/*  67 */   private BlockPos[] positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
/*  68 */   public static final EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
/*  69 */   private final EnumWorldBlockLayer[] blockLayersSingle = new EnumWorldBlockLayer[1];
/*  70 */   private final boolean isMipmaps = Config.isMipmaps();
/*  71 */   private final boolean fixBlockLayer = !Reflector.BetterFoliageClient.exists();
/*     */   private boolean playerUpdate = false;
/*     */   public int regionX;
/*     */   public int regionZ;
/*  75 */   private final RenderChunk[] renderChunksOfset16 = new RenderChunk[6];
/*     */   private boolean renderChunksOffset16Updated = false;
/*     */   private Chunk chunk;
/*  78 */   private RenderChunk[] renderChunkNeighbours = new RenderChunk[EnumFacing.VALUES.length];
/*  79 */   private RenderChunk[] renderChunkNeighboursValid = new RenderChunk[EnumFacing.VALUES.length];
/*     */   private boolean renderChunkNeighboursUpated = false;
/*  81 */   private RenderGlobal.ContainerLocalRenderInformation renderInfo = new RenderGlobal.ContainerLocalRenderInformation(this, null, 0);
/*     */   public AabbFrame boundingBoxParent;
/*     */   
/*     */   public RenderChunk(World worldIn, RenderGlobal renderGlobalIn, BlockPos blockPosIn, int indexIn) {
/*  85 */     this.world = worldIn;
/*  86 */     this.renderGlobal = renderGlobalIn;
/*  87 */     this.index = indexIn;
/*     */     
/*  89 */     if (!blockPosIn.equals(getPosition())) {
/*  90 */       setPosition(blockPosIn);
/*     */     }
/*     */     
/*  93 */     if (OpenGlHelper.useVbo()) {
/*  94 */       for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++) {
/*  95 */         this.vertexBuffers[i] = new VertexBuffer(DefaultVertexFormats.BLOCK);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean setFrameIndex(int frameIndexIn) {
/* 101 */     if (this.frameIndex == frameIndexIn) {
/* 102 */       return false;
/*     */     }
/* 104 */     this.frameIndex = frameIndexIn;
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexBuffer getVertexBufferByLayer(int layer) {
/* 110 */     return this.vertexBuffers[layer];
/*     */   }
/*     */   
/*     */   public void setPosition(BlockPos pos) {
/* 114 */     stopCompileTask();
/* 115 */     this.position = pos;
/* 116 */     int i = 8;
/* 117 */     this.regionX = pos.getX() >> i << i;
/* 118 */     this.regionZ = pos.getZ() >> i << i;
/* 119 */     this.boundingBox = new AxisAlignedBB(pos, pos.add(16, 16, 16));
/* 120 */     initModelviewMatrix();
/*     */     
/* 122 */     for (int j = 0; j < this.positionOffsets16.length; j++) {
/* 123 */       this.positionOffsets16[j] = null;
/*     */     }
/*     */     
/* 126 */     this.renderChunksOffset16Updated = false;
/* 127 */     this.renderChunkNeighboursUpated = false;
/*     */     
/* 129 */     for (int k = 0; k < this.renderChunkNeighbours.length; k++) {
/* 130 */       RenderChunk renderchunk = this.renderChunkNeighbours[k];
/*     */       
/* 132 */       if (renderchunk != null) {
/* 133 */         renderchunk.renderChunkNeighboursUpated = false;
/*     */       }
/*     */     } 
/*     */     
/* 137 */     this.chunk = null;
/* 138 */     this.boundingBoxParent = null;
/*     */   }
/*     */   
/*     */   public void resortTransparency(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 142 */     CompiledChunk compiledchunk = generator.getCompiledChunk();
/*     */     
/* 144 */     if (compiledchunk.getState() != null && !compiledchunk.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
/* 145 */       WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
/* 146 */       preRenderBlocks(worldrenderer, this.position);
/* 147 */       worldrenderer.setVertexState(compiledchunk.getState());
/* 148 */       postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, x, y, z, worldrenderer, compiledchunk);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 153 */     CompiledChunk compiledchunk = new CompiledChunk();
/* 154 */     int i = 1;
/* 155 */     BlockPos blockpos = new BlockPos((Vec3i)this.position);
/* 156 */     BlockPos blockpos1 = blockpos.add(15, 15, 15);
/* 157 */     generator.getLock().lock();
/*     */     
/*     */     try {
/* 160 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*     */         return;
/*     */       }
/*     */       
/* 164 */       generator.setCompiledChunk(compiledchunk);
/*     */     } finally {
/* 166 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/* 169 */     VisGraph lvt_10_1_ = new VisGraph();
/* 170 */     HashSet<TileEntity> lvt_11_1_ = Sets.newHashSet();
/*     */     
/* 172 */     if (!isChunkRegionEmpty(blockpos)) {
/* 173 */       renderChunksUpdated++;
/* 174 */       ChunkCacheOF chunkcacheof = makeChunkCacheOF(blockpos);
/* 175 */       chunkcacheof.renderStart();
/* 176 */       boolean[] aboolean = new boolean[ENUM_WORLD_BLOCK_LAYERS.length];
/* 177 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 178 */       boolean flag = Reflector.ForgeBlock_canRenderInLayer.exists();
/* 179 */       boolean flag1 = Reflector.ForgeHooksClient_setRenderLayer.exists();
/*     */       
/* 181 */       for (Object blockposm0 : BlockPosM.getAllInBoxMutable(blockpos, blockpos1)) {
/* 182 */         EnumWorldBlockLayer[] aenumworldblocklayer; BlockPosM blockposm = (BlockPosM)blockposm0;
/* 183 */         IBlockState iblockstate = chunkcacheof.getBlockState((BlockPos)blockposm);
/* 184 */         Block block = iblockstate.getBlock();
/*     */         
/* 186 */         if (block.isOpaqueCube()) {
/* 187 */           lvt_10_1_.func_178606_a((BlockPos)blockposm);
/*     */         }
/*     */         
/* 190 */         if (ReflectorForge.blockHasTileEntity(iblockstate)) {
/* 191 */           TileEntity tileentity = chunkcacheof.getTileEntity(new BlockPos((Vec3i)blockposm));
/* 192 */           TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.getSpecialRenderer(tileentity);
/*     */           
/* 194 */           if (tileentity != null && tileentityspecialrenderer != null) {
/* 195 */             compiledchunk.addTileEntity(tileentity);
/*     */             
/* 197 */             if (tileentityspecialrenderer.forceTileEntityRender()) {
/* 198 */               lvt_11_1_.add(tileentity);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 205 */         if (flag) {
/* 206 */           aenumworldblocklayer = ENUM_WORLD_BLOCK_LAYERS;
/*     */         } else {
/* 208 */           aenumworldblocklayer = this.blockLayersSingle;
/* 209 */           aenumworldblocklayer[0] = block.getBlockLayer();
/*     */         } 
/*     */         
/* 212 */         for (int k = 0; k < aenumworldblocklayer.length; k++) {
/* 213 */           EnumWorldBlockLayer enumworldblocklayer = aenumworldblocklayer[k];
/*     */           
/* 215 */           if (flag) {
/* 216 */             boolean flag2 = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInLayer, new Object[] { enumworldblocklayer });
/*     */             
/* 218 */             if (!flag2) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */           
/* 223 */           if (flag1) {
/* 224 */             Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { enumworldblocklayer });
/*     */           }
/*     */           
/* 227 */           enumworldblocklayer = fixBlockLayer(iblockstate, enumworldblocklayer);
/* 228 */           int m = enumworldblocklayer.ordinal();
/*     */           
/* 230 */           if (block.getRenderType() != -1) {
/* 231 */             WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(m);
/* 232 */             worldrenderer.setBlockLayer(enumworldblocklayer);
/* 233 */             RenderEnv renderenv = worldrenderer.getRenderEnv(iblockstate, (BlockPos)blockposm);
/* 234 */             renderenv.setRegionRenderCacheBuilder(generator.getRegionRenderCacheBuilder());
/*     */             
/* 236 */             if (!compiledchunk.isLayerStarted(enumworldblocklayer)) {
/* 237 */               compiledchunk.setLayerStarted(enumworldblocklayer);
/* 238 */               preRenderBlocks(worldrenderer, blockpos);
/*     */             } 
/*     */             
/* 241 */             aboolean[m] = aboolean[m] | blockrendererdispatcher.renderBlock(iblockstate, (BlockPos)blockposm, (IBlockAccess)chunkcacheof, worldrenderer);
/*     */             
/* 243 */             if (renderenv.isOverlaysRendered()) {
/* 244 */               postRenderOverlays(generator.getRegionRenderCacheBuilder(), compiledchunk, aboolean);
/* 245 */               renderenv.setOverlaysRendered(false);
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 250 */         if (flag1)
/* 251 */           Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { null }); 
/*     */       }  byte b;
/*     */       int j;
/*     */       EnumWorldBlockLayer[] arrayOfEnumWorldBlockLayer;
/* 255 */       for (j = (arrayOfEnumWorldBlockLayer = ENUM_WORLD_BLOCK_LAYERS).length, b = 0; b < j; ) { EnumWorldBlockLayer enumworldblocklayer1 = arrayOfEnumWorldBlockLayer[b];
/* 256 */         if (aboolean[enumworldblocklayer1.ordinal()]) {
/* 257 */           compiledchunk.setLayerUsed(enumworldblocklayer1);
/*     */         }
/*     */         
/* 260 */         if (compiledchunk.isLayerStarted(enumworldblocklayer1)) {
/* 261 */           if (Config.isShaders()) {
/* 262 */             SVertexBuilder.calcNormalChunkLayer(generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer1));
/*     */           }
/*     */           
/* 265 */           WorldRenderer worldrenderer1 = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer1);
/* 266 */           postRenderBlocks(enumworldblocklayer1, x, y, z, worldrenderer1, compiledchunk);
/*     */           
/* 268 */           if (worldrenderer1.animatedSprites != null) {
/* 269 */             compiledchunk.setAnimatedSprites(enumworldblocklayer1, (BitSet)worldrenderer1.animatedSprites.clone());
/*     */           }
/*     */         } else {
/* 272 */           compiledchunk.setAnimatedSprites(enumworldblocklayer1, null);
/*     */         } 
/*     */         b++; }
/*     */       
/* 276 */       chunkcacheof.renderFinish();
/*     */     } 
/*     */     
/* 279 */     compiledchunk.setVisibility(lvt_10_1_.computeVisibility());
/* 280 */     this.lockCompileTask.lock();
/*     */     
/*     */     try {
/* 283 */       Set<TileEntity> set = Sets.newHashSet(lvt_11_1_);
/* 284 */       Set<TileEntity> set1 = Sets.newHashSet(this.setTileEntities);
/* 285 */       set.removeAll(this.setTileEntities);
/* 286 */       set1.removeAll(lvt_11_1_);
/* 287 */       this.setTileEntities.clear();
/* 288 */       this.setTileEntities.addAll(lvt_11_1_);
/* 289 */       this.renderGlobal.updateTileEntities(set1, set);
/*     */     } finally {
/* 291 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finishCompileTask() {
/* 296 */     this.lockCompileTask.lock();
/*     */     
/*     */     try {
/* 299 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
/* 300 */         this.compileTask.finish();
/* 301 */         this.compileTask = null;
/*     */       } 
/*     */     } finally {
/* 304 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ReentrantLock getLockCompileTask() {
/* 309 */     return this.lockCompileTask;
/*     */   }
/*     */   public ChunkCompileTaskGenerator makeCompileTaskChunk() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator;
/* 313 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 317 */       finishCompileTask();
/* 318 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
/* 319 */       chunkcompiletaskgenerator = this.compileTask;
/*     */     } finally {
/* 321 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */     
/* 324 */     return chunkcompiletaskgenerator;
/*     */   }
/*     */   public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator1;
/* 328 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */     
/* 332 */     try { if (this.compileTask != null && this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING) {
/* 333 */         ChunkCompileTaskGenerator chunkcompiletaskgenerator2 = null;
/* 334 */         return chunkcompiletaskgenerator2;
/*     */       } 
/*     */       
/* 337 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
/* 338 */         this.compileTask.finish();
/* 339 */         this.compileTask = null;
/*     */       } 
/*     */       
/* 342 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY);
/* 343 */       this.compileTask.setCompiledChunk(this.compiledChunk);
/* 344 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.compileTask; }
/*     */     finally
/*     */     
/* 347 */     { this.lockCompileTask.unlock(); }  this.lockCompileTask.unlock();
/*     */ 
/*     */     
/* 350 */     return chunkcompiletaskgenerator1;
/*     */   }
/*     */   
/*     */   private void preRenderBlocks(WorldRenderer worldRendererIn, BlockPos pos) {
/* 354 */     worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/*     */     
/* 356 */     if (Config.isRenderRegions()) {
/* 357 */       int i = 8;
/* 358 */       int j = pos.getX() >> i << i;
/* 359 */       int k = pos.getY() >> i << i;
/* 360 */       int l = pos.getZ() >> i << i;
/* 361 */       j = this.regionX;
/* 362 */       l = this.regionZ;
/* 363 */       worldRendererIn.setTranslation(-j, -k, -l);
/*     */     } else {
/* 365 */       worldRendererIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void postRenderBlocks(EnumWorldBlockLayer layer, float x, float y, float z, WorldRenderer worldRendererIn, CompiledChunk compiledChunkIn) {
/* 370 */     if (layer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunkIn.isLayerEmpty(layer)) {
/* 371 */       worldRendererIn.sortVertexData(x, y, z);
/* 372 */       compiledChunkIn.setState(worldRendererIn.getVertexState());
/*     */     } 
/*     */     
/* 375 */     worldRendererIn.finishDrawing();
/*     */   }
/*     */   
/*     */   private void initModelviewMatrix() {
/* 379 */     GlStateManager.pushMatrix();
/* 380 */     GlStateManager.loadIdentity();
/* 381 */     float f = 1.000001F;
/* 382 */     GlStateManager.translate(-8.0F, -8.0F, -8.0F);
/* 383 */     GlStateManager.scale(f, f, f);
/* 384 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/* 385 */     GlStateManager.getFloat(2982, this.modelviewMatrix);
/* 386 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public void multModelviewMatrix() {
/* 390 */     GlStateManager.multMatrix(this.modelviewMatrix);
/*     */   }
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/* 394 */     return this.compiledChunk;
/*     */   }
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/* 398 */     this.lockCompiledChunk.lock();
/*     */     
/*     */     try {
/* 401 */       this.compiledChunk = compiledChunkIn;
/*     */     } finally {
/* 403 */       this.lockCompiledChunk.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stopCompileTask() {
/* 408 */     finishCompileTask();
/* 409 */     this.compiledChunk = CompiledChunk.DUMMY;
/*     */   }
/*     */   
/*     */   public void deleteGlResources() {
/* 413 */     stopCompileTask();
/*     */     
/* 415 */     for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++) {
/* 416 */       if (this.vertexBuffers[i] != null) {
/* 417 */         this.vertexBuffers[i].deleteGlBuffers();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public BlockPos getPosition() {
/* 423 */     return this.position;
/*     */   }
/*     */   
/*     */   public void setNeedsUpdate(boolean needsUpdateIn) {
/* 427 */     this.needsUpdate = needsUpdateIn;
/*     */     
/* 429 */     if (needsUpdateIn) {
/* 430 */       if (isWorldPlayerUpdate()) {
/* 431 */         this.playerUpdate = true;
/*     */       }
/*     */     } else {
/* 434 */       this.playerUpdate = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isNeedsUpdate() {
/* 439 */     return this.needsUpdate;
/*     */   }
/*     */   
/*     */   public BlockPos getBlockPosOffset16(EnumFacing p_181701_1_) {
/* 443 */     return getPositionOffset16(p_181701_1_);
/*     */   }
/*     */   
/*     */   public BlockPos getPositionOffset16(EnumFacing p_getPositionOffset16_1_) {
/* 447 */     int i = p_getPositionOffset16_1_.getIndex();
/* 448 */     BlockPos blockpos = this.positionOffsets16[i];
/*     */     
/* 450 */     if (blockpos == null) {
/* 451 */       blockpos = getPosition().offset(p_getPositionOffset16_1_, 16);
/* 452 */       this.positionOffsets16[i] = blockpos;
/*     */     } 
/*     */     
/* 455 */     return blockpos;
/*     */   }
/*     */   
/*     */   private boolean isWorldPlayerUpdate() {
/* 459 */     if (this.world instanceof WorldClient) {
/* 460 */       WorldClient worldclient = (WorldClient)this.world;
/* 461 */       return worldclient.isPlayerUpdate();
/*     */     } 
/* 463 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 468 */     return this.playerUpdate;
/*     */   }
/*     */   
/*     */   protected RegionRenderCache createRegionRenderCache(World p_createRegionRenderCache_1_, BlockPos p_createRegionRenderCache_2_, BlockPos p_createRegionRenderCache_3_, int p_createRegionRenderCache_4_) {
/* 472 */     return new RegionRenderCache(p_createRegionRenderCache_1_, p_createRegionRenderCache_2_, p_createRegionRenderCache_3_, p_createRegionRenderCache_4_);
/*     */   }
/*     */   
/*     */   private EnumWorldBlockLayer fixBlockLayer(IBlockState p_fixBlockLayer_1_, EnumWorldBlockLayer p_fixBlockLayer_2_) {
/* 476 */     if (CustomBlockLayers.isActive()) {
/* 477 */       EnumWorldBlockLayer enumworldblocklayer = CustomBlockLayers.getRenderLayer(p_fixBlockLayer_1_);
/*     */       
/* 479 */       if (enumworldblocklayer != null) {
/* 480 */         return enumworldblocklayer;
/*     */       }
/*     */     } 
/*     */     
/* 484 */     if (!this.fixBlockLayer) {
/* 485 */       return p_fixBlockLayer_2_;
/*     */     }
/* 487 */     if (this.isMipmaps) {
/* 488 */       if (p_fixBlockLayer_2_ == EnumWorldBlockLayer.CUTOUT) {
/* 489 */         Block block = p_fixBlockLayer_1_.getBlock();
/*     */         
/* 491 */         if (block instanceof net.minecraft.block.BlockRedstoneWire) {
/* 492 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 495 */         if (block instanceof net.minecraft.block.BlockCactus) {
/* 496 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 499 */         return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */       } 
/* 501 */     } else if (p_fixBlockLayer_2_ == EnumWorldBlockLayer.CUTOUT_MIPPED) {
/* 502 */       return EnumWorldBlockLayer.CUTOUT;
/*     */     } 
/*     */     
/* 505 */     return p_fixBlockLayer_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderOverlays(RegionRenderCacheBuilder p_postRenderOverlays_1_, CompiledChunk p_postRenderOverlays_2_, boolean[] p_postRenderOverlays_3_) {
/* 510 */     postRenderOverlay(EnumWorldBlockLayer.CUTOUT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 511 */     postRenderOverlay(EnumWorldBlockLayer.CUTOUT_MIPPED, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 512 */     postRenderOverlay(EnumWorldBlockLayer.TRANSLUCENT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/*     */   }
/*     */   
/*     */   private void postRenderOverlay(EnumWorldBlockLayer p_postRenderOverlay_1_, RegionRenderCacheBuilder p_postRenderOverlay_2_, CompiledChunk p_postRenderOverlay_3_, boolean[] p_postRenderOverlay_4_) {
/* 516 */     WorldRenderer worldrenderer = p_postRenderOverlay_2_.getWorldRendererByLayer(p_postRenderOverlay_1_);
/*     */     
/* 518 */     if (worldrenderer.isDrawing()) {
/* 519 */       p_postRenderOverlay_3_.setLayerStarted(p_postRenderOverlay_1_);
/* 520 */       p_postRenderOverlay_4_[p_postRenderOverlay_1_.ordinal()] = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private ChunkCacheOF makeChunkCacheOF(BlockPos p_makeChunkCacheOF_1_) {
/* 525 */     BlockPos blockpos = p_makeChunkCacheOF_1_.add(-1, -1, -1);
/* 526 */     BlockPos blockpos1 = p_makeChunkCacheOF_1_.add(16, 16, 16);
/* 527 */     RegionRenderCache regionRenderCache = createRegionRenderCache(this.world, blockpos, blockpos1, 1);
/*     */     
/* 529 */     if (Reflector.MinecraftForgeClient_onRebuildChunk.exists()) {
/* 530 */       Reflector.call(Reflector.MinecraftForgeClient_onRebuildChunk, new Object[] { this.world, p_makeChunkCacheOF_1_, regionRenderCache });
/*     */     }
/*     */     
/* 533 */     ChunkCacheOF chunkcacheof = new ChunkCacheOF((ChunkCache)regionRenderCache, blockpos, blockpos1, 1);
/* 534 */     return chunkcacheof;
/*     */   }
/*     */   
/*     */   public RenderChunk getRenderChunkOffset16(ViewFrustum p_getRenderChunkOffset16_1_, EnumFacing p_getRenderChunkOffset16_2_) {
/* 538 */     if (!this.renderChunksOffset16Updated) {
/* 539 */       for (int i = 0; i < EnumFacing.VALUES.length; i++) {
/* 540 */         EnumFacing enumfacing = EnumFacing.VALUES[i];
/* 541 */         BlockPos blockpos = getBlockPosOffset16(enumfacing);
/* 542 */         this.renderChunksOfset16[i] = p_getRenderChunkOffset16_1_.getRenderChunk(blockpos);
/*     */       } 
/*     */       
/* 545 */       this.renderChunksOffset16Updated = true;
/*     */     } 
/*     */     
/* 548 */     return this.renderChunksOfset16[p_getRenderChunkOffset16_2_.ordinal()];
/*     */   }
/*     */   
/*     */   public Chunk getChunk() {
/* 552 */     return getChunk(this.position);
/*     */   }
/*     */   
/*     */   private Chunk getChunk(BlockPos p_getChunk_1_) {
/* 556 */     Chunk chunk = this.chunk;
/*     */     
/* 558 */     if (chunk != null && chunk.isLoaded()) {
/* 559 */       return chunk;
/*     */     }
/* 561 */     chunk = this.world.getChunkFromBlockCoords(p_getChunk_1_);
/* 562 */     this.chunk = chunk;
/* 563 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChunkRegionEmpty() {
/* 568 */     return isChunkRegionEmpty(this.position);
/*     */   }
/*     */   
/*     */   private boolean isChunkRegionEmpty(BlockPos p_isChunkRegionEmpty_1_) {
/* 572 */     int i = p_isChunkRegionEmpty_1_.getY();
/* 573 */     int j = i + 15;
/* 574 */     return getChunk(p_isChunkRegionEmpty_1_).getAreLevelsEmpty(i, j);
/*     */   }
/*     */   
/*     */   public void setRenderChunkNeighbour(EnumFacing p_setRenderChunkNeighbour_1_, RenderChunk p_setRenderChunkNeighbour_2_) {
/* 578 */     this.renderChunkNeighbours[p_setRenderChunkNeighbour_1_.ordinal()] = p_setRenderChunkNeighbour_2_;
/* 579 */     this.renderChunkNeighboursValid[p_setRenderChunkNeighbour_1_.ordinal()] = p_setRenderChunkNeighbour_2_;
/*     */   }
/*     */   
/*     */   public RenderChunk getRenderChunkNeighbour(EnumFacing p_getRenderChunkNeighbour_1_) {
/* 583 */     if (!this.renderChunkNeighboursUpated) {
/* 584 */       updateRenderChunkNeighboursValid();
/*     */     }
/*     */     
/* 587 */     return this.renderChunkNeighboursValid[p_getRenderChunkNeighbour_1_.ordinal()];
/*     */   }
/*     */   
/*     */   public RenderGlobal.ContainerLocalRenderInformation getRenderInfo() {
/* 591 */     return this.renderInfo;
/*     */   }
/*     */   
/*     */   private void updateRenderChunkNeighboursValid() {
/* 595 */     int i = getPosition().getX();
/* 596 */     int j = getPosition().getZ();
/* 597 */     int k = EnumFacing.NORTH.ordinal();
/* 598 */     int l = EnumFacing.SOUTH.ordinal();
/* 599 */     int i1 = EnumFacing.WEST.ordinal();
/* 600 */     int j1 = EnumFacing.EAST.ordinal();
/* 601 */     this.renderChunkNeighboursValid[k] = (this.renderChunkNeighbours[k].getPosition().getZ() == j - 16) ? this.renderChunkNeighbours[k] : null;
/* 602 */     this.renderChunkNeighboursValid[l] = (this.renderChunkNeighbours[l].getPosition().getZ() == j + 16) ? this.renderChunkNeighbours[l] : null;
/* 603 */     this.renderChunkNeighboursValid[i1] = (this.renderChunkNeighbours[i1].getPosition().getX() == i - 16) ? this.renderChunkNeighbours[i1] : null;
/* 604 */     this.renderChunkNeighboursValid[j1] = (this.renderChunkNeighbours[j1].getPosition().getX() == i + 16) ? this.renderChunkNeighbours[j1] : null;
/* 605 */     this.renderChunkNeighboursUpated = true;
/*     */   }
/*     */   
/*     */   public boolean isBoundingBoxInFrustum(ICamera p_isBoundingBoxInFrustum_1_, int p_isBoundingBoxInFrustum_2_) {
/* 609 */     return getBoundingBoxParent().isBoundingBoxInFrustumFully(p_isBoundingBoxInFrustum_1_, p_isBoundingBoxInFrustum_2_) ? true : p_isBoundingBoxInFrustum_1_.isBoundingBoxInFrustum(this.boundingBox);
/*     */   }
/*     */   
/*     */   public AabbFrame getBoundingBoxParent() {
/* 613 */     if (this.boundingBoxParent == null) {
/* 614 */       BlockPos blockpos = getPosition();
/* 615 */       int i = blockpos.getX();
/* 616 */       int j = blockpos.getY();
/* 617 */       int k = blockpos.getZ();
/* 618 */       int l = 5;
/* 619 */       int i1 = i >> l << l;
/* 620 */       int j1 = j >> l << l;
/* 621 */       int k1 = k >> l << l;
/*     */       
/* 623 */       if (i1 != i || j1 != j || k1 != k) {
/* 624 */         AabbFrame aabbframe = this.renderGlobal.getRenderChunk(new BlockPos(i1, j1, k1)).getBoundingBoxParent();
/*     */         
/* 626 */         if (aabbframe != null && aabbframe.minX == i1 && aabbframe.minY == j1 && aabbframe.minZ == k1) {
/* 627 */           this.boundingBoxParent = aabbframe;
/*     */         }
/*     */       } 
/*     */       
/* 631 */       if (this.boundingBoxParent == null) {
/* 632 */         int l1 = 1 << l;
/* 633 */         this.boundingBoxParent = new AabbFrame(i1, j1, k1, (i1 + l1), (j1 + l1), (k1 + l1));
/*     */       } 
/*     */     } 
/*     */     
/* 637 */     return this.boundingBoxParent;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 641 */     return "pos: " + getPosition() + ", frameIndex: " + this.frameIndex;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\RenderChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */