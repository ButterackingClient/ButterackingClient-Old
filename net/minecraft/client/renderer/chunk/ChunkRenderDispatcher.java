/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListenableFutureTask;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.VertexBufferUploader;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.WorldVertexBufferUploader;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ChunkRenderDispatcher
/*     */ {
/*  32 */   private static final Logger logger = LogManager.getLogger();
/*  33 */   private static final ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkRenderDispatcher() {
/*  45 */     this(-1);
/*     */   }
/*     */ 
/*     */   
/*  49 */   private final List<ChunkRenderWorker> listThreadedWorkers = Lists.newArrayList();
/*  50 */   private final BlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates = Queues.newArrayBlockingQueue(100); private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;
/*  51 */   private final WorldVertexBufferUploader worldVertexUploader = new WorldVertexBufferUploader();
/*  52 */   private final VertexBufferUploader vertexUploader = new VertexBufferUploader(); private final ChunkRenderWorker renderWorker;
/*  53 */   private final Queue<ListenableFutureTask<?>> queueChunkUploads = Queues.newArrayDeque(); private final int countRenderBuilders;
/*  54 */   private List<RegionRenderCacheBuilder> listPausedBuilders = new ArrayList<>(); public ChunkRenderDispatcher(int p_i4_1_) {
/*  55 */     int i = Math.max(1, (int)(Runtime.getRuntime().maxMemory() * 0.3D) / 10485760);
/*  56 */     int j = Math.max(1, MathHelper.clamp_int(Runtime.getRuntime().availableProcessors() - 2, 1, i / 5));
/*     */     
/*  58 */     if (p_i4_1_ < 0) {
/*  59 */       this.countRenderBuilders = MathHelper.clamp_int(j * 8, 1, i);
/*     */     } else {
/*  61 */       this.countRenderBuilders = p_i4_1_;
/*     */     } 
/*     */     
/*  64 */     for (int k = 0; k < j; k++) {
/*  65 */       ChunkRenderWorker chunkrenderworker = new ChunkRenderWorker(this);
/*  66 */       Thread thread = threadFactory.newThread(chunkrenderworker);
/*  67 */       thread.start();
/*  68 */       this.listThreadedWorkers.add(chunkrenderworker);
/*     */     } 
/*     */     
/*  71 */     this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(this.countRenderBuilders);
/*     */     
/*  73 */     for (int l = 0; l < this.countRenderBuilders; l++) {
/*  74 */       this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
/*     */     }
/*     */     
/*  77 */     this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
/*     */   }
/*     */   
/*     */   public String getDebugInfo() {
/*  81 */     return String.format("pC: %03d, pU: %1d, aB: %1d", new Object[] { Integer.valueOf(this.queueChunkUpdates.size()), Integer.valueOf(this.queueChunkUploads.size()), Integer.valueOf(this.queueFreeRenderBuilders.size()) });
/*     */   }
/*     */   public boolean runChunkUploads(long p_178516_1_) {
/*     */     long i;
/*  85 */     boolean flag = false;
/*     */     
/*     */     do {
/*  88 */       boolean flag1 = false;
/*  89 */       ListenableFutureTask listenablefuturetask = null;
/*     */       
/*  91 */       synchronized (this.queueChunkUploads) {
/*  92 */         listenablefuturetask = this.queueChunkUploads.poll();
/*     */       } 
/*     */       
/*  95 */       if (listenablefuturetask != null) {
/*  96 */         listenablefuturetask.run();
/*  97 */         flag1 = true;
/*  98 */         flag = true;
/*     */       } 
/*     */       
/* 101 */       if (p_178516_1_ == 0L || !flag1) {
/*     */         break;
/*     */       }
/*     */       
/* 105 */       i = p_178516_1_ - System.nanoTime();
/*     */     }
/* 107 */     while (i >= 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     return flag;
/*     */   }
/*     */   public boolean updateChunkLater(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 116 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */     
/*     */     try {
/* 120 */       final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/* 121 */       chunkcompiletaskgenerator.addFinishRunnable(new Runnable() {
/*     */             public void run() {
/* 123 */               ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */             }
/*     */           });
/* 126 */       boolean flag1 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/*     */       
/* 128 */       if (!flag1) {
/* 129 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */       
/* 132 */       flag = flag1;
/*     */     } finally {
/* 134 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 137 */     return flag;
/*     */   }
/*     */   public boolean updateChunkNow(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 141 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */     
/*     */     try {
/* 145 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/*     */       
/*     */       try {
/* 148 */         this.renderWorker.processTask(chunkcompiletaskgenerator);
/* 149 */       } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */       
/* 153 */       flag = true;
/*     */     } finally {
/* 155 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 158 */     return flag;
/*     */   }
/*     */   
/*     */   public void stopChunkUpdates() {
/* 162 */     clearChunkUpdates(); do {
/*     */     
/* 164 */     } while (runChunkUploads(0L));
/*     */ 
/*     */ 
/*     */     
/* 168 */     List<RegionRenderCacheBuilder> list = Lists.newArrayList();
/*     */     
/* 170 */     while (list.size() != this.countRenderBuilders) {
/*     */       try {
/* 172 */         list.add(allocateRenderBuilder());
/* 173 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     this.queueFreeRenderBuilders.addAll(list);
/*     */   }
/*     */   
/*     */   public void freeRenderBuilder(RegionRenderCacheBuilder p_178512_1_) {
/* 182 */     this.queueFreeRenderBuilders.add(p_178512_1_);
/*     */   }
/*     */   
/*     */   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
/* 186 */     return this.queueFreeRenderBuilders.take();
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
/* 190 */     return this.queueChunkUpdates.take();
/*     */   }
/*     */   public boolean updateTransparencyLater(RenderChunk chunkRenderer) {
/*     */     boolean flag1;
/* 194 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/* 198 */     try { final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskTransparency();
/*     */       
/* 200 */       if (chunkcompiletaskgenerator != null) {
/* 201 */         chunkcompiletaskgenerator.addFinishRunnable(new Runnable() {
/*     */               public void run() {
/* 203 */                 ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */               }
/*     */             });
/* 206 */         boolean flag2 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/* 207 */         return flag2;
/*     */       } 
/*     */       
/* 210 */       boolean flag = true; }
/*     */     finally
/*     */     
/* 213 */     { chunkRenderer.getLockCompileTask().unlock(); }  chunkRenderer.getLockCompileTask().unlock();
/*     */ 
/*     */     
/* 216 */     return flag1;
/*     */   }
/*     */   
/*     */   public ListenableFuture<Object> uploadChunk(final EnumWorldBlockLayer player, final WorldRenderer p_178503_2_, final RenderChunk chunkRenderer, final CompiledChunk compiledChunkIn) {
/* 220 */     if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/* 221 */       if (OpenGlHelper.useVbo()) {
/* 222 */         uploadVertexBuffer(p_178503_2_, chunkRenderer.getVertexBufferByLayer(player.ordinal()));
/*     */       } else {
/* 224 */         uploadDisplayList(p_178503_2_, ((ListedRenderChunk)chunkRenderer).getDisplayList(player, compiledChunkIn), chunkRenderer);
/*     */       } 
/*     */       
/* 227 */       p_178503_2_.setTranslation(0.0D, 0.0D, 0.0D);
/* 228 */       return Futures.immediateFuture(null);
/*     */     } 
/* 230 */     ListenableFutureTask<Object> listenablefuturetask = ListenableFutureTask.create(new Runnable() {
/*     */           public void run() {
/* 232 */             ChunkRenderDispatcher.this.uploadChunk(player, p_178503_2_, chunkRenderer, compiledChunkIn);
/*     */           }
/* 234 */         }null);
/*     */     
/* 236 */     synchronized (this.queueChunkUploads) {
/* 237 */       this.queueChunkUploads.add(listenablefuturetask);
/* 238 */       return (ListenableFuture<Object>)listenablefuturetask;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadDisplayList(WorldRenderer p_178510_1_, int p_178510_2_, RenderChunk chunkRenderer) {
/* 244 */     GL11.glNewList(p_178510_2_, 4864);
/* 245 */     GlStateManager.pushMatrix();
/* 246 */     chunkRenderer.multModelviewMatrix();
/* 247 */     this.worldVertexUploader.draw(p_178510_1_);
/* 248 */     GlStateManager.popMatrix();
/* 249 */     GL11.glEndList();
/*     */   }
/*     */   
/*     */   private void uploadVertexBuffer(WorldRenderer p_178506_1_, VertexBuffer vertexBufferIn) {
/* 253 */     this.vertexUploader.setVertexBuffer(vertexBufferIn);
/* 254 */     this.vertexUploader.draw(p_178506_1_);
/*     */   }
/*     */   
/*     */   public void clearChunkUpdates() {
/* 258 */     while (!this.queueChunkUpdates.isEmpty()) {
/* 259 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.queueChunkUpdates.poll();
/*     */       
/* 261 */       if (chunkcompiletaskgenerator != null) {
/* 262 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasChunkUpdates() {
/* 268 */     return (this.queueChunkUpdates.isEmpty() && this.queueChunkUploads.isEmpty());
/*     */   }
/*     */   
/*     */   public void pauseChunkUpdates() {
/* 272 */     while (this.listPausedBuilders.size() != this.countRenderBuilders) {
/*     */       try {
/* 274 */         runChunkUploads(Long.MAX_VALUE);
/* 275 */         RegionRenderCacheBuilder regionrendercachebuilder = this.queueFreeRenderBuilders.poll(100L, TimeUnit.MILLISECONDS);
/*     */         
/* 277 */         if (regionrendercachebuilder != null) {
/* 278 */           this.listPausedBuilders.add(regionrendercachebuilder);
/*     */         }
/* 280 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeChunkUpdates() {
/* 287 */     this.queueFreeRenderBuilders.addAll(this.listPausedBuilders);
/* 288 */     this.listPausedBuilders.clear();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\ChunkRenderDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */