/*    */ package net.minecraft.client.renderer.culling;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ClippingHelperImpl
/*    */   extends ClippingHelper {
/* 10 */   private static ClippingHelperImpl instance = new ClippingHelperImpl();
/* 11 */   private FloatBuffer projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
/* 12 */   private FloatBuffer modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(16);
/* 13 */   private FloatBuffer field_78564_h = GLAllocation.createDirectFloatBuffer(16);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ClippingHelper getInstance() {
/* 19 */     instance.init();
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   private void normalize(float[] p_180547_1_) {
/* 24 */     float f = MathHelper.sqrt_float(p_180547_1_[0] * p_180547_1_[0] + p_180547_1_[1] * p_180547_1_[1] + p_180547_1_[2] * p_180547_1_[2]);
/* 25 */     p_180547_1_[0] = p_180547_1_[0] / f;
/* 26 */     p_180547_1_[1] = p_180547_1_[1] / f;
/* 27 */     p_180547_1_[2] = p_180547_1_[2] / f;
/* 28 */     p_180547_1_[3] = p_180547_1_[3] / f;
/*    */   }
/*    */   
/*    */   public void init() {
/* 32 */     this.projectionMatrixBuffer.clear();
/* 33 */     this.modelviewMatrixBuffer.clear();
/* 34 */     this.field_78564_h.clear();
/* 35 */     GlStateManager.getFloat(2983, this.projectionMatrixBuffer);
/* 36 */     GlStateManager.getFloat(2982, this.modelviewMatrixBuffer);
/* 37 */     float[] afloat = this.projectionMatrix;
/* 38 */     float[] afloat1 = this.modelviewMatrix;
/* 39 */     this.projectionMatrixBuffer.flip().limit(16);
/* 40 */     this.projectionMatrixBuffer.get(afloat);
/* 41 */     this.modelviewMatrixBuffer.flip().limit(16);
/* 42 */     this.modelviewMatrixBuffer.get(afloat1);
/* 43 */     this.clippingMatrix[0] = afloat1[0] * afloat[0] + afloat1[1] * afloat[4] + afloat1[2] * afloat[8] + afloat1[3] * afloat[12];
/* 44 */     this.clippingMatrix[1] = afloat1[0] * afloat[1] + afloat1[1] * afloat[5] + afloat1[2] * afloat[9] + afloat1[3] * afloat[13];
/* 45 */     this.clippingMatrix[2] = afloat1[0] * afloat[2] + afloat1[1] * afloat[6] + afloat1[2] * afloat[10] + afloat1[3] * afloat[14];
/* 46 */     this.clippingMatrix[3] = afloat1[0] * afloat[3] + afloat1[1] * afloat[7] + afloat1[2] * afloat[11] + afloat1[3] * afloat[15];
/* 47 */     this.clippingMatrix[4] = afloat1[4] * afloat[0] + afloat1[5] * afloat[4] + afloat1[6] * afloat[8] + afloat1[7] * afloat[12];
/* 48 */     this.clippingMatrix[5] = afloat1[4] * afloat[1] + afloat1[5] * afloat[5] + afloat1[6] * afloat[9] + afloat1[7] * afloat[13];
/* 49 */     this.clippingMatrix[6] = afloat1[4] * afloat[2] + afloat1[5] * afloat[6] + afloat1[6] * afloat[10] + afloat1[7] * afloat[14];
/* 50 */     this.clippingMatrix[7] = afloat1[4] * afloat[3] + afloat1[5] * afloat[7] + afloat1[6] * afloat[11] + afloat1[7] * afloat[15];
/* 51 */     this.clippingMatrix[8] = afloat1[8] * afloat[0] + afloat1[9] * afloat[4] + afloat1[10] * afloat[8] + afloat1[11] * afloat[12];
/* 52 */     this.clippingMatrix[9] = afloat1[8] * afloat[1] + afloat1[9] * afloat[5] + afloat1[10] * afloat[9] + afloat1[11] * afloat[13];
/* 53 */     this.clippingMatrix[10] = afloat1[8] * afloat[2] + afloat1[9] * afloat[6] + afloat1[10] * afloat[10] + afloat1[11] * afloat[14];
/* 54 */     this.clippingMatrix[11] = afloat1[8] * afloat[3] + afloat1[9] * afloat[7] + afloat1[10] * afloat[11] + afloat1[11] * afloat[15];
/* 55 */     this.clippingMatrix[12] = afloat1[12] * afloat[0] + afloat1[13] * afloat[4] + afloat1[14] * afloat[8] + afloat1[15] * afloat[12];
/* 56 */     this.clippingMatrix[13] = afloat1[12] * afloat[1] + afloat1[13] * afloat[5] + afloat1[14] * afloat[9] + afloat1[15] * afloat[13];
/* 57 */     this.clippingMatrix[14] = afloat1[12] * afloat[2] + afloat1[13] * afloat[6] + afloat1[14] * afloat[10] + afloat1[15] * afloat[14];
/* 58 */     this.clippingMatrix[15] = afloat1[12] * afloat[3] + afloat1[13] * afloat[7] + afloat1[14] * afloat[11] + afloat1[15] * afloat[15];
/* 59 */     float[] afloat2 = this.frustum[0];
/* 60 */     afloat2[0] = this.clippingMatrix[3] - this.clippingMatrix[0];
/* 61 */     afloat2[1] = this.clippingMatrix[7] - this.clippingMatrix[4];
/* 62 */     afloat2[2] = this.clippingMatrix[11] - this.clippingMatrix[8];
/* 63 */     afloat2[3] = this.clippingMatrix[15] - this.clippingMatrix[12];
/* 64 */     normalize(afloat2);
/* 65 */     float[] afloat3 = this.frustum[1];
/* 66 */     afloat3[0] = this.clippingMatrix[3] + this.clippingMatrix[0];
/* 67 */     afloat3[1] = this.clippingMatrix[7] + this.clippingMatrix[4];
/* 68 */     afloat3[2] = this.clippingMatrix[11] + this.clippingMatrix[8];
/* 69 */     afloat3[3] = this.clippingMatrix[15] + this.clippingMatrix[12];
/* 70 */     normalize(afloat3);
/* 71 */     float[] afloat4 = this.frustum[2];
/* 72 */     afloat4[0] = this.clippingMatrix[3] + this.clippingMatrix[1];
/* 73 */     afloat4[1] = this.clippingMatrix[7] + this.clippingMatrix[5];
/* 74 */     afloat4[2] = this.clippingMatrix[11] + this.clippingMatrix[9];
/* 75 */     afloat4[3] = this.clippingMatrix[15] + this.clippingMatrix[13];
/* 76 */     normalize(afloat4);
/* 77 */     float[] afloat5 = this.frustum[3];
/* 78 */     afloat5[0] = this.clippingMatrix[3] - this.clippingMatrix[1];
/* 79 */     afloat5[1] = this.clippingMatrix[7] - this.clippingMatrix[5];
/* 80 */     afloat5[2] = this.clippingMatrix[11] - this.clippingMatrix[9];
/* 81 */     afloat5[3] = this.clippingMatrix[15] - this.clippingMatrix[13];
/* 82 */     normalize(afloat5);
/* 83 */     float[] afloat6 = this.frustum[4];
/* 84 */     afloat6[0] = this.clippingMatrix[3] - this.clippingMatrix[2];
/* 85 */     afloat6[1] = this.clippingMatrix[7] - this.clippingMatrix[6];
/* 86 */     afloat6[2] = this.clippingMatrix[11] - this.clippingMatrix[10];
/* 87 */     afloat6[3] = this.clippingMatrix[15] - this.clippingMatrix[14];
/* 88 */     normalize(afloat6);
/* 89 */     float[] afloat7 = this.frustum[5];
/* 90 */     afloat7[0] = this.clippingMatrix[3] + this.clippingMatrix[2];
/* 91 */     afloat7[1] = this.clippingMatrix[7] + this.clippingMatrix[6];
/* 92 */     afloat7[2] = this.clippingMatrix[11] + this.clippingMatrix[10];
/* 93 */     afloat7[3] = this.clippingMatrix[15] + this.clippingMatrix[14];
/* 94 */     normalize(afloat7);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\culling\ClippingHelperImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */