/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class VertexBufferUploader extends WorldVertexBufferUploader {
/*  7 */   private VertexBuffer vertexBuffer = null;
/*    */   
/*    */   public void draw(WorldRenderer p_181679_1_) {
/* 10 */     if (p_181679_1_.getDrawMode() == 7 && Config.isQuadsToTriangles()) {
/* 11 */       p_181679_1_.quadsToTriangles();
/* 12 */       this.vertexBuffer.setDrawMode(p_181679_1_.getDrawMode());
/*    */     } 
/*    */     
/* 15 */     this.vertexBuffer.bufferData(p_181679_1_.getByteBuffer());
/* 16 */     p_181679_1_.reset();
/*    */   }
/*    */   
/*    */   public void setVertexBuffer(VertexBuffer vertexBufferIn) {
/* 20 */     this.vertexBuffer = vertexBufferIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\VertexBufferUploader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */