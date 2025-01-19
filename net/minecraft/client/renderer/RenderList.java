/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import java.nio.IntBuffer;
/*    */ import net.minecraft.client.renderer.chunk.ListedRenderChunk;
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderList
/*    */   extends ChunkRenderContainer {
/*    */   private double viewEntityX;
/*    */   private double viewEntityY;
/*    */   private double viewEntityZ;
/* 15 */   IntBuffer bufferLists = GLAllocation.createDirectIntBuffer(16);
/*    */   
/*    */   public void renderChunkLayer(EnumWorldBlockLayer layer) {
/* 18 */     if (this.initialized) {
/* 19 */       if (!Config.isRenderRegions()) {
/* 20 */         for (RenderChunk renderchunk1 : this.renderChunks) {
/* 21 */           ListedRenderChunk listedrenderchunk1 = (ListedRenderChunk)renderchunk1;
/* 22 */           GlStateManager.pushMatrix();
/* 23 */           preRenderChunk(renderchunk1);
/* 24 */           GL11.glCallList(listedrenderchunk1.getDisplayList(layer, listedrenderchunk1.getCompiledChunk()));
/* 25 */           GlStateManager.popMatrix();
/*    */         } 
/*    */       } else {
/* 28 */         int i = Integer.MIN_VALUE;
/* 29 */         int j = Integer.MIN_VALUE;
/*    */         
/* 31 */         for (RenderChunk renderchunk : this.renderChunks) {
/* 32 */           ListedRenderChunk listedrenderchunk = (ListedRenderChunk)renderchunk;
/*    */           
/* 34 */           if (i != renderchunk.regionX || j != renderchunk.regionZ) {
/* 35 */             if (this.bufferLists.position() > 0) {
/* 36 */               drawRegion(i, j, this.bufferLists);
/*    */             }
/*    */             
/* 39 */             i = renderchunk.regionX;
/* 40 */             j = renderchunk.regionZ;
/*    */           } 
/*    */           
/* 43 */           if (this.bufferLists.position() >= this.bufferLists.capacity()) {
/* 44 */             IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(this.bufferLists.capacity() * 2);
/* 45 */             this.bufferLists.flip();
/* 46 */             intbuffer.put(this.bufferLists);
/* 47 */             this.bufferLists = intbuffer;
/*    */           } 
/*    */           
/* 50 */           this.bufferLists.put(listedrenderchunk.getDisplayList(layer, listedrenderchunk.getCompiledChunk()));
/*    */         } 
/*    */         
/* 53 */         if (this.bufferLists.position() > 0) {
/* 54 */           drawRegion(i, j, this.bufferLists);
/*    */         }
/*    */       } 
/*    */       
/* 58 */       if (Config.isMultiTexture()) {
/* 59 */         GlStateManager.bindCurrentTexture();
/*    */       }
/*    */       
/* 62 */       GlStateManager.resetColor();
/* 63 */       this.renderChunks.clear();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/* 68 */     this.viewEntityX = viewEntityXIn;
/* 69 */     this.viewEntityY = viewEntityYIn;
/* 70 */     this.viewEntityZ = viewEntityZIn;
/* 71 */     super.initialize(viewEntityXIn, viewEntityYIn, viewEntityZIn);
/*    */   }
/*    */   
/*    */   private void drawRegion(int p_drawRegion_1_, int p_drawRegion_2_, IntBuffer p_drawRegion_3_) {
/* 75 */     GlStateManager.pushMatrix();
/* 76 */     preRenderRegion(p_drawRegion_1_, 0, p_drawRegion_2_);
/* 77 */     p_drawRegion_3_.flip();
/* 78 */     GlStateManager.callLists(p_drawRegion_3_);
/* 79 */     p_drawRegion_3_.clear();
/* 80 */     GlStateManager.popMatrix();
/*    */   }
/*    */   
/*    */   public void preRenderRegion(int p_preRenderRegion_1_, int p_preRenderRegion_2_, int p_preRenderRegion_3_) {
/* 84 */     GlStateManager.translate((float)(p_preRenderRegion_1_ - this.viewEntityX), (float)(p_preRenderRegion_2_ - this.viewEntityY), (float)(p_preRenderRegion_3_ - this.viewEntityZ));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\RenderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */