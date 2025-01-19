/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.client.renderer.ViewFrustum;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.optifine.BlockPosM;
/*    */ 
/*    */ public class IteratorRenderChunks
/*    */   implements Iterator<RenderChunk> {
/*    */   private ViewFrustum viewFrustum;
/*    */   private Iterator3d Iterator3d;
/* 13 */   private BlockPosM posBlock = new BlockPosM(0, 0, 0);
/*    */   
/*    */   public IteratorRenderChunks(ViewFrustum viewFrustum, BlockPos posStart, BlockPos posEnd, int width, int height) {
/* 16 */     this.viewFrustum = viewFrustum;
/* 17 */     this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 21 */     return this.Iterator3d.hasNext();
/*    */   }
/*    */   
/*    */   public RenderChunk next() {
/* 25 */     BlockPos blockpos = this.Iterator3d.next();
/* 26 */     this.posBlock.setXyz(blockpos.getX() << 4, blockpos.getY() << 4, blockpos.getZ() << 4);
/* 27 */     RenderChunk renderchunk = this.viewFrustum.getRenderChunk((BlockPos)this.posBlock);
/* 28 */     return renderchunk;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 32 */     throw new RuntimeException("Not implemented");
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\IteratorRenderChunks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */