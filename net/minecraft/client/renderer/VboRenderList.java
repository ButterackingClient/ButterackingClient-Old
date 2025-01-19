/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ import net.optifine.render.VboRegion;
/*    */ import net.optifine.shaders.ShadersRender;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class VboRenderList extends ChunkRenderContainer {
/*    */   private double viewEntityX;
/*    */   private double viewEntityY;
/*    */   private double viewEntityZ;
/*    */   
/*    */   public void renderChunkLayer(EnumWorldBlockLayer layer) {
/* 17 */     if (this.initialized) {
/* 18 */       if (!Config.isRenderRegions()) {
/* 19 */         for (RenderChunk renderchunk1 : this.renderChunks) {
/* 20 */           VertexBuffer vertexbuffer1 = renderchunk1.getVertexBufferByLayer(layer.ordinal());
/* 21 */           GlStateManager.pushMatrix();
/* 22 */           preRenderChunk(renderchunk1);
/* 23 */           renderchunk1.multModelviewMatrix();
/* 24 */           vertexbuffer1.bindBuffer();
/* 25 */           setupArrayPointers();
/* 26 */           vertexbuffer1.drawArrays(7);
/* 27 */           GlStateManager.popMatrix();
/*    */         } 
/*    */       } else {
/* 30 */         int i = Integer.MIN_VALUE;
/* 31 */         int j = Integer.MIN_VALUE;
/* 32 */         VboRegion vboregion = null;
/*    */         
/* 34 */         for (RenderChunk renderchunk : this.renderChunks) {
/* 35 */           VertexBuffer vertexbuffer = renderchunk.getVertexBufferByLayer(layer.ordinal());
/* 36 */           VboRegion vboregion1 = vertexbuffer.getVboRegion();
/*    */           
/* 38 */           if (vboregion1 != vboregion || i != renderchunk.regionX || j != renderchunk.regionZ) {
/* 39 */             if (vboregion != null) {
/* 40 */               drawRegion(i, j, vboregion);
/*    */             }
/*    */             
/* 43 */             i = renderchunk.regionX;
/* 44 */             j = renderchunk.regionZ;
/* 45 */             vboregion = vboregion1;
/*    */           } 
/*    */           
/* 48 */           vertexbuffer.drawArrays(7);
/*    */         } 
/*    */         
/* 51 */         if (vboregion != null) {
/* 52 */           drawRegion(i, j, vboregion);
/*    */         }
/*    */       } 
/*    */       
/* 56 */       OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/* 57 */       GlStateManager.resetColor();
/* 58 */       this.renderChunks.clear();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setupArrayPointers() {
/* 63 */     if (Config.isShaders()) {
/* 64 */       ShadersRender.setupArrayPointersVbo();
/*    */     } else {
/* 66 */       GL11.glVertexPointer(3, 5126, 28, 0L);
/* 67 */       GL11.glColorPointer(4, 5121, 28, 12L);
/* 68 */       GL11.glTexCoordPointer(2, 5126, 28, 16L);
/* 69 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 70 */       GL11.glTexCoordPointer(2, 5122, 28, 24L);
/* 71 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
/* 76 */     this.viewEntityX = viewEntityXIn;
/* 77 */     this.viewEntityY = viewEntityYIn;
/* 78 */     this.viewEntityZ = viewEntityZIn;
/* 79 */     super.initialize(viewEntityXIn, viewEntityYIn, viewEntityZIn);
/*    */   }
/*    */   
/*    */   private void drawRegion(int p_drawRegion_1_, int p_drawRegion_2_, VboRegion p_drawRegion_3_) {
/* 83 */     GlStateManager.pushMatrix();
/* 84 */     preRenderRegion(p_drawRegion_1_, 0, p_drawRegion_2_);
/* 85 */     p_drawRegion_3_.finishDraw(this);
/* 86 */     GlStateManager.popMatrix();
/*    */   }
/*    */   
/*    */   public void preRenderRegion(int p_preRenderRegion_1_, int p_preRenderRegion_2_, int p_preRenderRegion_3_) {
/* 90 */     GlStateManager.translate((float)(p_preRenderRegion_1_ - this.viewEntityX), (float)(p_preRenderRegion_2_ - this.viewEntityY), (float)(p_preRenderRegion_3_ - this.viewEntityZ));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\VboRenderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */