/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ 
/*     */ 
/*     */ public class ChunkCompileTaskGenerator
/*     */ {
/*     */   private final RenderChunk renderChunk;
/*  12 */   private final ReentrantLock lock = new ReentrantLock();
/*  13 */   private final List<Runnable> listFinishRunnables = Lists.newArrayList();
/*     */   private final Type type;
/*     */   private RegionRenderCacheBuilder regionRenderCacheBuilder;
/*     */   private CompiledChunk compiledChunk;
/*  17 */   private Status status = Status.PENDING;
/*     */   private boolean finished;
/*     */   
/*     */   public ChunkCompileTaskGenerator(RenderChunk renderChunkIn, Type typeIn) {
/*  21 */     this.renderChunk = renderChunkIn;
/*  22 */     this.type = typeIn;
/*     */   }
/*     */   
/*     */   public Status getStatus() {
/*  26 */     return this.status;
/*     */   }
/*     */   
/*     */   public RenderChunk getRenderChunk() {
/*  30 */     return this.renderChunk;
/*     */   }
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/*  34 */     return this.compiledChunk;
/*     */   }
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/*  38 */     this.compiledChunk = compiledChunkIn;
/*     */   }
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/*  42 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
/*  46 */     this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
/*     */   }
/*     */   
/*     */   public void setStatus(Status statusIn) {
/*  50 */     this.lock.lock();
/*     */     
/*     */     try {
/*  53 */       this.status = statusIn;
/*     */     } finally {
/*  55 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void finish() {
/*  60 */     this.lock.lock();
/*     */     
/*     */     try {
/*  63 */       if (this.type == Type.REBUILD_CHUNK && this.status != Status.DONE) {
/*  64 */         this.renderChunk.setNeedsUpdate(true);
/*     */       }
/*     */       
/*  67 */       this.finished = true;
/*  68 */       this.status = Status.DONE;
/*     */       
/*  70 */       for (Runnable runnable : this.listFinishRunnables) {
/*  71 */         runnable.run();
/*     */       }
/*     */     } finally {
/*  74 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addFinishRunnable(Runnable p_178539_1_) {
/*  79 */     this.lock.lock();
/*     */     
/*     */     try {
/*  82 */       this.listFinishRunnables.add(p_178539_1_);
/*     */       
/*  84 */       if (this.finished) {
/*  85 */         p_178539_1_.run();
/*     */       }
/*     */     } finally {
/*  88 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public ReentrantLock getLock() {
/*  93 */     return this.lock;
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  97 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 101 */     return this.finished;
/*     */   }
/*     */   
/*     */   public enum Status {
/* 105 */     PENDING,
/* 106 */     COMPILING,
/* 107 */     UPLOADING,
/* 108 */     DONE;
/*     */   }
/*     */   
/*     */   public enum Type {
/* 112 */     REBUILD_CHUNK,
/* 113 */     RESORT_TRANSPARENCY;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\ChunkCompileTaskGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */