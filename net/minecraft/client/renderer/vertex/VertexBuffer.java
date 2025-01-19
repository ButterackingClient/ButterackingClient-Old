/*    */ package net.minecraft.client.renderer.vertex;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.optifine.render.VboRange;
/*    */ import net.optifine.render.VboRegion;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class VertexBuffer
/*    */ {
/*    */   private int glBufferId;
/*    */   private final VertexFormat vertexFormat;
/*    */   private int count;
/*    */   private VboRegion vboRegion;
/*    */   private VboRange vboRange;
/*    */   private int drawMode;
/*    */   
/*    */   public VertexBuffer(VertexFormat vertexFormatIn) {
/* 19 */     this.vertexFormat = vertexFormatIn;
/* 20 */     this.glBufferId = OpenGlHelper.glGenBuffers();
/*    */   }
/*    */   
/*    */   public void bindBuffer() {
/* 24 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
/*    */   }
/*    */   
/*    */   public void bufferData(ByteBuffer p_181722_1_) {
/* 28 */     if (this.vboRegion != null) {
/* 29 */       this.vboRegion.bufferData(p_181722_1_, this.vboRange);
/*    */     } else {
/* 31 */       bindBuffer();
/* 32 */       OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, p_181722_1_, 35044);
/* 33 */       unbindBuffer();
/* 34 */       this.count = p_181722_1_.limit() / this.vertexFormat.getNextOffset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawArrays(int mode) {
/* 39 */     if (this.drawMode > 0) {
/* 40 */       mode = this.drawMode;
/*    */     }
/*    */     
/* 43 */     if (this.vboRegion != null) {
/* 44 */       this.vboRegion.drawArrays(mode, this.vboRange);
/*    */     } else {
/* 46 */       GL11.glDrawArrays(mode, 0, this.count);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void unbindBuffer() {
/* 51 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/*    */   }
/*    */   
/*    */   public void deleteGlBuffers() {
/* 55 */     if (this.glBufferId >= 0) {
/* 56 */       OpenGlHelper.glDeleteBuffers(this.glBufferId);
/* 57 */       this.glBufferId = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setVboRegion(VboRegion p_setVboRegion_1_) {
/* 62 */     if (p_setVboRegion_1_ != null) {
/* 63 */       deleteGlBuffers();
/* 64 */       this.vboRegion = p_setVboRegion_1_;
/* 65 */       this.vboRange = new VboRange();
/*    */     } 
/*    */   }
/*    */   
/*    */   public VboRegion getVboRegion() {
/* 70 */     return this.vboRegion;
/*    */   }
/*    */   
/*    */   public VboRange getVboRange() {
/* 74 */     return this.vboRange;
/*    */   }
/*    */   
/*    */   public int getDrawMode() {
/* 78 */     return this.drawMode;
/*    */   }
/*    */   
/*    */   public void setDrawMode(int p_setDrawMode_1_) {
/* 82 */     this.drawMode = p_setDrawMode_1_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\vertex\VertexBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */