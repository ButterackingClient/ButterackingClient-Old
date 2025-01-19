/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.optifine.SmartAnimations;
/*    */ 
/*    */ public class Tessellator {
/*    */   private WorldRenderer worldRenderer;
/*  7 */   private WorldVertexBufferUploader vboUploader = new WorldVertexBufferUploader();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 12 */   private static final Tessellator instance = new Tessellator(2097152);
/*    */   
/*    */   public static Tessellator getInstance() {
/* 15 */     return instance;
/*    */   }
/*    */   
/*    */   public Tessellator(int bufferSize) {
/* 19 */     this.worldRenderer = new WorldRenderer(bufferSize);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw() {
/* 26 */     if (this.worldRenderer.animatedSprites != null) {
/* 27 */       SmartAnimations.spritesRendered(this.worldRenderer.animatedSprites);
/*    */     }
/*    */     
/* 30 */     this.worldRenderer.finishDrawing();
/* 31 */     this.vboUploader.draw(this.worldRenderer);
/*    */   }
/*    */   
/*    */   public WorldRenderer getWorldRenderer() {
/* 35 */     return this.worldRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\Tessellator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */