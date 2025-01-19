/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.BitSet;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.optifine.SmartAnimations;
/*    */ 
/*    */ 
/*    */ public abstract class ChunkRenderContainer
/*    */ {
/*    */   private double viewEntityX;
/*    */   private double viewEntityY;
/*    */   private double viewEntityZ;
/* 17 */   protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity(17424);
/*    */   protected boolean initialized;
/*    */   private BitSet animatedSpritesRendered;
/* 20 */   private final BitSet animatedSpritesCached = new BitSet();
/*    */   
/*    */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/* 23 */     this.initialized = true;
/* 24 */     this.renderChunks.clear();
/* 25 */     this.viewEntityX = viewEntityXIn;
/* 26 */     this.viewEntityY = viewEntityYIn;
/* 27 */     this.viewEntityZ = viewEntityZIn;
/*    */     
/* 29 */     if (SmartAnimations.isActive()) {
/* 30 */       if (this.animatedSpritesRendered != null) {
/* 31 */         SmartAnimations.spritesRendered(this.animatedSpritesRendered);
/*    */       } else {
/* 33 */         this.animatedSpritesRendered = this.animatedSpritesCached;
/*    */       } 
/*    */       
/* 36 */       this.animatedSpritesRendered.clear();
/* 37 */     } else if (this.animatedSpritesRendered != null) {
/* 38 */       SmartAnimations.spritesRendered(this.animatedSpritesRendered);
/* 39 */       this.animatedSpritesRendered = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void preRenderChunk(RenderChunk renderChunkIn) {
/* 44 */     BlockPos blockpos = renderChunkIn.getPosition();
/* 45 */     GlStateManager.translate((float)(blockpos.getX() - this.viewEntityX), (float)(blockpos.getY() - this.viewEntityY), (float)(blockpos.getZ() - this.viewEntityZ));
/*    */   }
/*    */   
/*    */   public void addRenderChunk(RenderChunk renderChunkIn, EnumWorldBlockLayer layer) {
/* 49 */     this.renderChunks.add(renderChunkIn);
/*    */     
/* 51 */     if (this.animatedSpritesRendered != null) {
/* 52 */       BitSet bitset = renderChunkIn.compiledChunk.getAnimatedSprites(layer);
/*    */       
/* 54 */       if (bitset != null)
/* 55 */         this.animatedSpritesRendered.or(bitset); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void renderChunkLayer(EnumWorldBlockLayer paramEnumWorldBlockLayer);
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\ChunkRenderContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */