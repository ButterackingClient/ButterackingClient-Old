/*    */ package net.minecraft.client.renderer.culling;
/*    */ 
/*    */ public class ClippingHelper {
/*  4 */   public float[][] frustum = new float[6][4];
/*  5 */   public float[] projectionMatrix = new float[16];
/*  6 */   public float[] modelviewMatrix = new float[16];
/*  7 */   public float[] clippingMatrix = new float[16];
/*    */   public boolean disabled = false;
/*    */   
/*    */   private float dot(float[] p_dot_1_, float p_dot_2_, float p_dot_3_, float p_dot_4_) {
/* 11 */     return p_dot_1_[0] * p_dot_2_ + p_dot_1_[1] * p_dot_3_ + p_dot_1_[2] * p_dot_4_ + p_dot_1_[3];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBoxInFrustum(double p_78553_1_, double p_78553_3_, double p_78553_5_, double p_78553_7_, double p_78553_9_, double p_78553_11_) {
/* 18 */     if (this.disabled) {
/* 19 */       return true;
/*    */     }
/* 21 */     float f = (float)p_78553_1_;
/* 22 */     float f1 = (float)p_78553_3_;
/* 23 */     float f2 = (float)p_78553_5_;
/* 24 */     float f3 = (float)p_78553_7_;
/* 25 */     float f4 = (float)p_78553_9_;
/* 26 */     float f5 = (float)p_78553_11_;
/*    */     
/* 28 */     for (int i = 0; i < 6; i++) {
/* 29 */       float[] afloat = this.frustum[i];
/* 30 */       float f6 = afloat[0];
/* 31 */       float f7 = afloat[1];
/* 32 */       float f8 = afloat[2];
/* 33 */       float f9 = afloat[3];
/*    */       
/* 35 */       if (f6 * f + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f5 + f9 <= 0.0F) {
/* 36 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBoxInFrustumFully(double p_isBoxInFrustumFully_1_, double p_isBoxInFrustumFully_3_, double p_isBoxInFrustumFully_5_, double p_isBoxInFrustumFully_7_, double p_isBoxInFrustumFully_9_, double p_isBoxInFrustumFully_11_) {
/* 45 */     if (this.disabled) {
/* 46 */       return true;
/*    */     }
/* 48 */     float f = (float)p_isBoxInFrustumFully_1_;
/* 49 */     float f1 = (float)p_isBoxInFrustumFully_3_;
/* 50 */     float f2 = (float)p_isBoxInFrustumFully_5_;
/* 51 */     float f3 = (float)p_isBoxInFrustumFully_7_;
/* 52 */     float f4 = (float)p_isBoxInFrustumFully_9_;
/* 53 */     float f5 = (float)p_isBoxInFrustumFully_11_;
/*    */     
/* 55 */     for (int i = 0; i < 6; i++) {
/* 56 */       float[] afloat = this.frustum[i];
/* 57 */       float f6 = afloat[0];
/* 58 */       float f7 = afloat[1];
/* 59 */       float f8 = afloat[2];
/* 60 */       float f9 = afloat[3];
/*    */       
/* 62 */       if (i < 4) {
/* 63 */         if (f6 * f + f7 * f1 + f8 * f2 + f9 <= 0.0F || f6 * f3 + f7 * f1 + f8 * f2 + f9 <= 0.0F || f6 * f + f7 * f4 + f8 * f2 + f9 <= 0.0F || f6 * f3 + f7 * f4 + f8 * f2 + f9 <= 0.0F || f6 * f + f7 * f1 + f8 * f5 + f9 <= 0.0F || f6 * f3 + f7 * f1 + f8 * f5 + f9 <= 0.0F || f6 * f + f7 * f4 + f8 * f5 + f9 <= 0.0F || f6 * f3 + f7 * f4 + f8 * f5 + f9 <= 0.0F) {
/* 64 */           return false;
/*    */         }
/* 66 */       } else if (f6 * f + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f2 + f9 <= 0.0F && f6 * f + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f1 + f8 * f5 + f9 <= 0.0F && f6 * f + f7 * f4 + f8 * f5 + f9 <= 0.0F && f6 * f3 + f7 * f4 + f8 * f5 + f9 <= 0.0F) {
/* 67 */         return false;
/*    */       } 
/*    */     } 
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\culling\ClippingHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */