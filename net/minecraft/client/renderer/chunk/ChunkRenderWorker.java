/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ChunkRenderWorker
/*     */   implements Runnable
/*     */ {
/*  21 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final ChunkRenderDispatcher chunkRenderDispatcher;
/*     */   private final RegionRenderCacheBuilder regionRenderCacheBuilder;
/*     */   
/*     */   public ChunkRenderWorker(ChunkRenderDispatcher p_i46201_1_) {
/*  26 */     this(p_i46201_1_, null);
/*     */   }
/*     */   
/*     */   public ChunkRenderWorker(ChunkRenderDispatcher chunkRenderDispatcherIn, RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
/*  30 */     this.chunkRenderDispatcher = chunkRenderDispatcherIn;
/*  31 */     this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/*     */       while (true)
/*  37 */         processTask(this.chunkRenderDispatcher.getNextChunkUpdate()); 
/*  38 */     } catch (InterruptedException var3) {
/*  39 */       LOGGER.debug("Stopping due to interrupt");
/*     */       return;
/*  41 */     } catch (Throwable throwable) {
/*  42 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Batching chunks");
/*  43 */       Minecraft.getMinecraft().crashed(Minecraft.getMinecraft().addGraphicsAndWorldToCrashReport(crashreport));
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processTask(final ChunkCompileTaskGenerator generator) throws InterruptedException {
/*  50 */     generator.getLock().lock();
/*     */     
/*     */     try {
/*  53 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
/*  54 */         if (!generator.isFinished()) {
/*  55 */           LOGGER.warn("Chunk render task was " + generator.getStatus() + " when I expected it to be pending; ignoring task");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  61 */       generator.setStatus(ChunkCompileTaskGenerator.Status.COMPILING);
/*     */     } finally {
/*  63 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/*  66 */     Entity lvt_2_1_ = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/*  68 */     if (lvt_2_1_ == null) {
/*  69 */       generator.finish();
/*     */     } else {
/*  71 */       generator.setRegionRenderCacheBuilder(getRegionRenderCacheBuilder());
/*  72 */       float f = (float)lvt_2_1_.posX;
/*  73 */       float f1 = (float)lvt_2_1_.posY + lvt_2_1_.getEyeHeight();
/*  74 */       float f2 = (float)lvt_2_1_.posZ;
/*  75 */       ChunkCompileTaskGenerator.Type chunkcompiletaskgenerator$type = generator.getType();
/*     */       
/*  77 */       if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
/*  78 */         generator.getRenderChunk().rebuildChunk(f, f1, f2, generator);
/*  79 */       } else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
/*  80 */         generator.getRenderChunk().resortTransparency(f, f1, f2, generator);
/*     */       } 
/*     */       
/*  83 */       generator.getLock().lock();
/*     */       
/*     */       try {
/*  86 */         if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*  87 */           if (!generator.isFinished()) {
/*  88 */             LOGGER.warn("Chunk render task was " + generator.getStatus() + " when I expected it to be compiling; aborting task");
/*     */           }
/*     */           
/*  91 */           freeRenderBuilder(generator);
/*     */           
/*     */           return;
/*     */         } 
/*  95 */         generator.setStatus(ChunkCompileTaskGenerator.Status.UPLOADING);
/*     */       } finally {
/*  97 */         generator.getLock().unlock();
/*     */       } 
/*     */       
/* 100 */       final CompiledChunk lvt_7_1_ = generator.getCompiledChunk();
/* 101 */       ArrayList<ListenableFuture<Object>> lvt_8_1_ = Lists.newArrayList();
/*     */       
/* 103 */       if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
/* 104 */         byte b; int i; EnumWorldBlockLayer[] arrayOfEnumWorldBlockLayer; for (i = (arrayOfEnumWorldBlockLayer = EnumWorldBlockLayer.values()).length, b = 0; b < i; ) { EnumWorldBlockLayer enumworldblocklayer = arrayOfEnumWorldBlockLayer[b];
/* 105 */           if (lvt_7_1_.isLayerStarted(enumworldblocklayer))
/* 106 */             lvt_8_1_.add(this.chunkRenderDispatcher.uploadChunk(enumworldblocklayer, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer), generator.getRenderChunk(), lvt_7_1_)); 
/*     */           b++; }
/*     */       
/* 109 */       } else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
/* 110 */         lvt_8_1_.add(this.chunkRenderDispatcher.uploadChunk(EnumWorldBlockLayer.TRANSLUCENT, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT), generator.getRenderChunk(), lvt_7_1_));
/*     */       } 
/*     */       
/* 113 */       final ListenableFuture<List<Object>> listenablefuture = Futures.allAsList(lvt_8_1_);
/* 114 */       generator.addFinishRunnable(new Runnable() {
/*     */             public void run() {
/* 116 */               listenablefuture.cancel(false);
/*     */             }
/*     */           });
/* 119 */       Futures.addCallback(listenablefuture, new FutureCallback<List<Object>>() {
/*     */             public void onSuccess(List<Object> p_onSuccess_1_) {
/* 121 */               ChunkRenderWorker.this.freeRenderBuilder(generator);
/* 122 */               generator.getLock().lock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/*     */               
/*     */               } finally {
/* 135 */                 generator.getLock().unlock();
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 140 */               generator.getRenderChunk().setCompiledChunk(lvt_7_1_);
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 144 */               ChunkRenderWorker.this.freeRenderBuilder(generator);
/*     */               
/* 146 */               if (!(p_onFailure_1_ instanceof java.util.concurrent.CancellationException) && !(p_onFailure_1_ instanceof InterruptedException)) {
/* 147 */                 Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(p_onFailure_1_, "Rendering chunk"));
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
/* 155 */     return (this.regionRenderCacheBuilder != null) ? this.regionRenderCacheBuilder : this.chunkRenderDispatcher.allocateRenderBuilder();
/*     */   }
/*     */   
/*     */   private void freeRenderBuilder(ChunkCompileTaskGenerator taskGenerator) {
/* 159 */     if (this.regionRenderCacheBuilder == null)
/* 160 */       this.chunkRenderDispatcher.freeRenderBuilder(taskGenerator.getRegionRenderCacheBuilder()); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\ChunkRenderWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */