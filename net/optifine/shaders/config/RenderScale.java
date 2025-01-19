/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ public class RenderScale {
/*  4 */   private float scale = 1.0F;
/*  5 */   private float offsetX = 0.0F;
/*  6 */   private float offsetY = 0.0F;
/*    */   
/*    */   public RenderScale(float scale, float offsetX, float offsetY) {
/*  9 */     this.scale = scale;
/* 10 */     this.offsetX = offsetX;
/* 11 */     this.offsetY = offsetY;
/*    */   }
/*    */   
/*    */   public float getScale() {
/* 15 */     return this.scale;
/*    */   }
/*    */   
/*    */   public float getOffsetX() {
/* 19 */     return this.offsetX;
/*    */   }
/*    */   
/*    */   public float getOffsetY() {
/* 23 */     return this.offsetY;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 27 */     return this.scale + ", " + this.offsetX + ", " + this.offsetY;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\RenderScale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */