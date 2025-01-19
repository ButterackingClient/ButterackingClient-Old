/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class Matrix4f extends Matrix4f {
/*    */   public Matrix4f(float[] p_i46413_1_) {
/*  5 */     this.m00 = p_i46413_1_[0];
/*  6 */     this.m01 = p_i46413_1_[1];
/*  7 */     this.m02 = p_i46413_1_[2];
/*  8 */     this.m03 = p_i46413_1_[3];
/*  9 */     this.m10 = p_i46413_1_[4];
/* 10 */     this.m11 = p_i46413_1_[5];
/* 11 */     this.m12 = p_i46413_1_[6];
/* 12 */     this.m13 = p_i46413_1_[7];
/* 13 */     this.m20 = p_i46413_1_[8];
/* 14 */     this.m21 = p_i46413_1_[9];
/* 15 */     this.m22 = p_i46413_1_[10];
/* 16 */     this.m23 = p_i46413_1_[11];
/* 17 */     this.m30 = p_i46413_1_[12];
/* 18 */     this.m31 = p_i46413_1_[13];
/* 19 */     this.m32 = p_i46413_1_[14];
/* 20 */     this.m33 = p_i46413_1_[15];
/*    */   }
/*    */   
/*    */   public Matrix4f() {
/* 24 */     this.m00 = this.m01 = this.m02 = this.m03 = this.m10 = this.m11 = this.m12 = this.m13 = this.m20 = this.m21 = this.m22 = this.m23 = this.m30 = this.m31 = this.m32 = this.m33 = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Matrix4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */