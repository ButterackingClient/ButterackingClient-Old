/*    */ package net.optifine.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.culling.Frustum;
/*    */ import net.minecraft.client.renderer.culling.ICamera;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ public class AabbFrame extends AxisAlignedBB {
/*  8 */   private int frameCount = -1;
/*    */   private boolean inFrustumFully = false;
/*    */   
/*    */   public AabbFrame(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 12 */     super(x1, y1, z1, x2, y2, z2);
/*    */   }
/*    */   
/*    */   public boolean isBoundingBoxInFrustumFully(ICamera camera, int frameCount) {
/* 16 */     if (this.frameCount != frameCount) {
/* 17 */       this.inFrustumFully = (camera instanceof Frustum) ? ((Frustum)camera).isBoxInFrustumFully(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ) : false;
/* 18 */       this.frameCount = frameCount;
/*    */     } 
/*    */     
/* 21 */     return this.inFrustumFully;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\AabbFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */