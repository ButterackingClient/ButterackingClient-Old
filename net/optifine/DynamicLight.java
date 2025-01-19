/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class DynamicLight
/*     */ {
/*  20 */   private Entity entity = null;
/*  21 */   private double offsetY = 0.0D;
/*  22 */   private double lastPosX = -2.147483648E9D;
/*  23 */   private double lastPosY = -2.147483648E9D;
/*  24 */   private double lastPosZ = -2.147483648E9D;
/*  25 */   private int lastLightLevel = 0;
/*     */   private boolean underwater = false;
/*  27 */   private long timeCheckMs = 0L;
/*  28 */   private Set<BlockPos> setLitChunkPos = new HashSet<>();
/*  29 */   private BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();
/*     */   
/*     */   public DynamicLight(Entity entity) {
/*  32 */     this.entity = entity;
/*  33 */     this.offsetY = entity.getEyeHeight();
/*     */   }
/*     */   
/*     */   public void update(RenderGlobal renderGlobal) {
/*  37 */     if (Config.isDynamicLightsFast()) {
/*  38 */       long i = System.currentTimeMillis();
/*     */       
/*  40 */       if (i < this.timeCheckMs + 500L) {
/*     */         return;
/*     */       }
/*     */       
/*  44 */       this.timeCheckMs = i;
/*     */     } 
/*     */     
/*  47 */     double d6 = this.entity.posX - 0.5D;
/*  48 */     double d0 = this.entity.posY - 0.5D + this.offsetY;
/*  49 */     double d1 = this.entity.posZ - 0.5D;
/*  50 */     int j = DynamicLights.getLightLevel(this.entity);
/*  51 */     double d2 = d6 - this.lastPosX;
/*  52 */     double d3 = d0 - this.lastPosY;
/*  53 */     double d4 = d1 - this.lastPosZ;
/*  54 */     double d5 = 0.1D;
/*     */     
/*  56 */     if (Math.abs(d2) > d5 || Math.abs(d3) > d5 || Math.abs(d4) > d5 || this.lastLightLevel != j) {
/*  57 */       this.lastPosX = d6;
/*  58 */       this.lastPosY = d0;
/*  59 */       this.lastPosZ = d1;
/*  60 */       this.lastLightLevel = j;
/*  61 */       this.underwater = false;
/*  62 */       WorldClient worldClient = renderGlobal.getWorld();
/*     */       
/*  64 */       if (worldClient != null) {
/*  65 */         this.blockPosMutable.set(MathHelper.floor_double(d6), MathHelper.floor_double(d0), MathHelper.floor_double(d1));
/*  66 */         IBlockState iblockstate = worldClient.getBlockState((BlockPos)this.blockPosMutable);
/*  67 */         Block block = iblockstate.getBlock();
/*  68 */         this.underwater = (block == Blocks.water);
/*     */       } 
/*     */       
/*  71 */       Set<BlockPos> set = new HashSet<>();
/*     */       
/*  73 */       if (j > 0) {
/*  74 */         EnumFacing enumfacing2 = ((MathHelper.floor_double(d6) & 0xF) >= 8) ? EnumFacing.EAST : EnumFacing.WEST;
/*  75 */         EnumFacing enumfacing = ((MathHelper.floor_double(d0) & 0xF) >= 8) ? EnumFacing.UP : EnumFacing.DOWN;
/*  76 */         EnumFacing enumfacing1 = ((MathHelper.floor_double(d1) & 0xF) >= 8) ? EnumFacing.SOUTH : EnumFacing.NORTH;
/*  77 */         BlockPos blockpos = new BlockPos(d6, d0, d1);
/*  78 */         RenderChunk renderchunk = renderGlobal.getRenderChunk(blockpos);
/*  79 */         BlockPos blockpos1 = getChunkPos(renderchunk, blockpos, enumfacing2);
/*  80 */         RenderChunk renderchunk1 = renderGlobal.getRenderChunk(blockpos1);
/*  81 */         BlockPos blockpos2 = getChunkPos(renderchunk, blockpos, enumfacing1);
/*  82 */         RenderChunk renderchunk2 = renderGlobal.getRenderChunk(blockpos2);
/*  83 */         BlockPos blockpos3 = getChunkPos(renderchunk1, blockpos1, enumfacing1);
/*  84 */         RenderChunk renderchunk3 = renderGlobal.getRenderChunk(blockpos3);
/*  85 */         BlockPos blockpos4 = getChunkPos(renderchunk, blockpos, enumfacing);
/*  86 */         RenderChunk renderchunk4 = renderGlobal.getRenderChunk(blockpos4);
/*  87 */         BlockPos blockpos5 = getChunkPos(renderchunk4, blockpos4, enumfacing2);
/*  88 */         RenderChunk renderchunk5 = renderGlobal.getRenderChunk(blockpos5);
/*  89 */         BlockPos blockpos6 = getChunkPos(renderchunk4, blockpos4, enumfacing1);
/*  90 */         RenderChunk renderchunk6 = renderGlobal.getRenderChunk(blockpos6);
/*  91 */         BlockPos blockpos7 = getChunkPos(renderchunk5, blockpos5, enumfacing1);
/*  92 */         RenderChunk renderchunk7 = renderGlobal.getRenderChunk(blockpos7);
/*  93 */         updateChunkLight(renderchunk, this.setLitChunkPos, set);
/*  94 */         updateChunkLight(renderchunk1, this.setLitChunkPos, set);
/*  95 */         updateChunkLight(renderchunk2, this.setLitChunkPos, set);
/*  96 */         updateChunkLight(renderchunk3, this.setLitChunkPos, set);
/*  97 */         updateChunkLight(renderchunk4, this.setLitChunkPos, set);
/*  98 */         updateChunkLight(renderchunk5, this.setLitChunkPos, set);
/*  99 */         updateChunkLight(renderchunk6, this.setLitChunkPos, set);
/* 100 */         updateChunkLight(renderchunk7, this.setLitChunkPos, set);
/*     */       } 
/*     */       
/* 103 */       updateLitChunks(renderGlobal);
/* 104 */       this.setLitChunkPos = set;
/*     */     } 
/*     */   }
/*     */   
/*     */   private BlockPos getChunkPos(RenderChunk renderChunk, BlockPos pos, EnumFacing facing) {
/* 109 */     return (renderChunk != null) ? renderChunk.getBlockPosOffset16(facing) : pos.offset(facing, 16);
/*     */   }
/*     */   
/*     */   private void updateChunkLight(RenderChunk renderChunk, Set<BlockPos> setPrevPos, Set<BlockPos> setNewPos) {
/* 113 */     if (renderChunk != null) {
/* 114 */       CompiledChunk compiledchunk = renderChunk.getCompiledChunk();
/*     */       
/* 116 */       if (compiledchunk != null && !compiledchunk.isEmpty()) {
/* 117 */         renderChunk.setNeedsUpdate(true);
/*     */       }
/*     */       
/* 120 */       BlockPos blockpos = renderChunk.getPosition();
/*     */       
/* 122 */       if (setPrevPos != null) {
/* 123 */         setPrevPos.remove(blockpos);
/*     */       }
/*     */       
/* 126 */       if (setNewPos != null) {
/* 127 */         setNewPos.add(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateLitChunks(RenderGlobal renderGlobal) {
/* 133 */     for (BlockPos blockpos : this.setLitChunkPos) {
/* 134 */       RenderChunk renderchunk = renderGlobal.getRenderChunk(blockpos);
/* 135 */       updateChunkLight(renderchunk, null, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Entity getEntity() {
/* 140 */     return this.entity;
/*     */   }
/*     */   
/*     */   public double getLastPosX() {
/* 144 */     return this.lastPosX;
/*     */   }
/*     */   
/*     */   public double getLastPosY() {
/* 148 */     return this.lastPosY;
/*     */   }
/*     */   
/*     */   public double getLastPosZ() {
/* 152 */     return this.lastPosZ;
/*     */   }
/*     */   
/*     */   public int getLastLightLevel() {
/* 156 */     return this.lastLightLevel;
/*     */   }
/*     */   
/*     */   public boolean isUnderwater() {
/* 160 */     return this.underwater;
/*     */   }
/*     */   
/*     */   public double getOffsetY() {
/* 164 */     return this.offsetY;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 168 */     return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\DynamicLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */