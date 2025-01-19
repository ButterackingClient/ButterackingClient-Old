/*      */ package javax.vecmath;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Matrix4f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = -8405036035410109353L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m03;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m13;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   public float m23;
/*      */   public float m30;
/*      */   public float m31;
/*      */   public float m32;
/*      */   public float m33;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
/*  149 */     this.m00 = m00;
/*  150 */     this.m01 = m01;
/*  151 */     this.m02 = m02;
/*  152 */     this.m03 = m03;
/*      */     
/*  154 */     this.m10 = m10;
/*  155 */     this.m11 = m11;
/*  156 */     this.m12 = m12;
/*  157 */     this.m13 = m13;
/*      */     
/*  159 */     this.m20 = m20;
/*  160 */     this.m21 = m21;
/*  161 */     this.m22 = m22;
/*  162 */     this.m23 = m23;
/*      */     
/*  164 */     this.m30 = m30;
/*  165 */     this.m31 = m31;
/*  166 */     this.m32 = m32;
/*  167 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(float[] v) {
/*  178 */     this.m00 = v[0];
/*  179 */     this.m01 = v[1];
/*  180 */     this.m02 = v[2];
/*  181 */     this.m03 = v[3];
/*      */     
/*  183 */     this.m10 = v[4];
/*  184 */     this.m11 = v[5];
/*  185 */     this.m12 = v[6];
/*  186 */     this.m13 = v[7];
/*      */     
/*  188 */     this.m20 = v[8];
/*  189 */     this.m21 = v[9];
/*  190 */     this.m22 = v[10];
/*  191 */     this.m23 = v[11];
/*      */     
/*  193 */     this.m30 = v[12];
/*  194 */     this.m31 = v[13];
/*  195 */     this.m32 = v[14];
/*  196 */     this.m33 = v[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(Quat4f q1, Vector3f t1, float s) {
/*  211 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/*  212 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  213 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  215 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  216 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/*  217 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  219 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  220 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  221 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/*  223 */     this.m03 = t1.x;
/*  224 */     this.m13 = t1.y;
/*  225 */     this.m23 = t1.z;
/*      */     
/*  227 */     this.m30 = 0.0F;
/*  228 */     this.m31 = 0.0F;
/*  229 */     this.m32 = 0.0F;
/*  230 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(Matrix4d m1) {
/*  241 */     this.m00 = (float)m1.m00;
/*  242 */     this.m01 = (float)m1.m01;
/*  243 */     this.m02 = (float)m1.m02;
/*  244 */     this.m03 = (float)m1.m03;
/*      */     
/*  246 */     this.m10 = (float)m1.m10;
/*  247 */     this.m11 = (float)m1.m11;
/*  248 */     this.m12 = (float)m1.m12;
/*  249 */     this.m13 = (float)m1.m13;
/*      */     
/*  251 */     this.m20 = (float)m1.m20;
/*  252 */     this.m21 = (float)m1.m21;
/*  253 */     this.m22 = (float)m1.m22;
/*  254 */     this.m23 = (float)m1.m23;
/*      */     
/*  256 */     this.m30 = (float)m1.m30;
/*  257 */     this.m31 = (float)m1.m31;
/*  258 */     this.m32 = (float)m1.m32;
/*  259 */     this.m33 = (float)m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(Matrix4f m1) {
/*  271 */     this.m00 = m1.m00;
/*  272 */     this.m01 = m1.m01;
/*  273 */     this.m02 = m1.m02;
/*  274 */     this.m03 = m1.m03;
/*      */     
/*  276 */     this.m10 = m1.m10;
/*  277 */     this.m11 = m1.m11;
/*  278 */     this.m12 = m1.m12;
/*  279 */     this.m13 = m1.m13;
/*      */     
/*  281 */     this.m20 = m1.m20;
/*  282 */     this.m21 = m1.m21;
/*  283 */     this.m22 = m1.m22;
/*  284 */     this.m23 = m1.m23;
/*      */     
/*  286 */     this.m30 = m1.m30;
/*  287 */     this.m31 = m1.m31;
/*  288 */     this.m32 = m1.m32;
/*  289 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(Matrix3f m1, Vector3f t1, float s) {
/*  305 */     this.m00 = m1.m00 * s;
/*  306 */     this.m01 = m1.m01 * s;
/*  307 */     this.m02 = m1.m02 * s;
/*  308 */     this.m03 = t1.x;
/*      */     
/*  310 */     this.m10 = m1.m10 * s;
/*  311 */     this.m11 = m1.m11 * s;
/*  312 */     this.m12 = m1.m12 * s;
/*  313 */     this.m13 = t1.y;
/*      */     
/*  315 */     this.m20 = m1.m20 * s;
/*  316 */     this.m21 = m1.m21 * s;
/*  317 */     this.m22 = m1.m22 * s;
/*  318 */     this.m23 = t1.z;
/*      */     
/*  320 */     this.m30 = 0.0F;
/*  321 */     this.m31 = 0.0F;
/*  322 */     this.m32 = 0.0F;
/*  323 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f() {
/*  332 */     this.m00 = 0.0F;
/*  333 */     this.m01 = 0.0F;
/*  334 */     this.m02 = 0.0F;
/*  335 */     this.m03 = 0.0F;
/*      */     
/*  337 */     this.m10 = 0.0F;
/*  338 */     this.m11 = 0.0F;
/*  339 */     this.m12 = 0.0F;
/*  340 */     this.m13 = 0.0F;
/*      */     
/*  342 */     this.m20 = 0.0F;
/*  343 */     this.m21 = 0.0F;
/*  344 */     this.m22 = 0.0F;
/*  345 */     this.m23 = 0.0F;
/*      */     
/*  347 */     this.m30 = 0.0F;
/*  348 */     this.m31 = 0.0F;
/*  349 */     this.m32 = 0.0F;
/*  350 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  361 */     return 
/*  362 */       String.valueOf(this.m00) + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + 
/*  363 */       this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + 
/*  364 */       this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + 
/*  365 */       this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  372 */     this.m00 = 1.0F;
/*  373 */     this.m01 = 0.0F;
/*  374 */     this.m02 = 0.0F;
/*  375 */     this.m03 = 0.0F;
/*      */     
/*  377 */     this.m10 = 0.0F;
/*  378 */     this.m11 = 1.0F;
/*  379 */     this.m12 = 0.0F;
/*  380 */     this.m13 = 0.0F;
/*      */     
/*  382 */     this.m20 = 0.0F;
/*  383 */     this.m21 = 0.0F;
/*  384 */     this.m22 = 1.0F;
/*  385 */     this.m23 = 0.0F;
/*      */     
/*  387 */     this.m30 = 0.0F;
/*  388 */     this.m31 = 0.0F;
/*  389 */     this.m32 = 0.0F;
/*  390 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int row, int column, float value) {
/*  401 */     switch (row) {
/*      */       case 0:
/*  403 */         switch (column) {
/*      */           case 0:
/*  405 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  408 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  411 */             this.m02 = value;
/*      */             return;
/*      */           case 3:
/*  414 */             this.m03 = value;
/*      */             return;
/*      */         } 
/*  417 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  422 */         switch (column) {
/*      */           case 0:
/*  424 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  427 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  430 */             this.m12 = value;
/*      */             return;
/*      */           case 3:
/*  433 */             this.m13 = value;
/*      */             return;
/*      */         } 
/*  436 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  441 */         switch (column) {
/*      */           case 0:
/*  443 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  446 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  449 */             this.m22 = value;
/*      */             return;
/*      */           case 3:
/*  452 */             this.m23 = value;
/*      */             return;
/*      */         } 
/*  455 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  460 */         switch (column) {
/*      */           case 0:
/*  462 */             this.m30 = value;
/*      */             return;
/*      */           case 1:
/*  465 */             this.m31 = value;
/*      */             return;
/*      */           case 2:
/*  468 */             this.m32 = value;
/*      */             return;
/*      */           case 3:
/*  471 */             this.m33 = value;
/*      */             return;
/*      */         } 
/*  474 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  479 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getElement(int row, int column) {
/*  491 */     switch (row) {
/*      */       case 0:
/*  493 */         switch (column) {
/*      */           case 0:
/*  495 */             return this.m00;
/*      */           case 1:
/*  497 */             return this.m01;
/*      */           case 2:
/*  499 */             return this.m02;
/*      */           case 3:
/*  501 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  507 */         switch (column) {
/*      */           case 0:
/*  509 */             return this.m10;
/*      */           case 1:
/*  511 */             return this.m11;
/*      */           case 2:
/*  513 */             return this.m12;
/*      */           case 3:
/*  515 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  522 */         switch (column) {
/*      */           case 0:
/*  524 */             return this.m20;
/*      */           case 1:
/*  526 */             return this.m21;
/*      */           case 2:
/*  528 */             return this.m22;
/*      */           case 3:
/*  530 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  537 */         switch (column) {
/*      */           case 0:
/*  539 */             return this.m30;
/*      */           case 1:
/*  541 */             return this.m31;
/*      */           case 2:
/*  543 */             return this.m32;
/*      */           case 3:
/*  545 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  554 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector4f v) {
/*  564 */     if (row == 0) {
/*  565 */       v.x = this.m00;
/*  566 */       v.y = this.m01;
/*  567 */       v.z = this.m02;
/*  568 */       v.w = this.m03;
/*  569 */     } else if (row == 1) {
/*  570 */       v.x = this.m10;
/*  571 */       v.y = this.m11;
/*  572 */       v.z = this.m12;
/*  573 */       v.w = this.m13;
/*  574 */     } else if (row == 2) {
/*  575 */       v.x = this.m20;
/*  576 */       v.y = this.m21;
/*  577 */       v.z = this.m22;
/*  578 */       v.w = this.m23;
/*  579 */     } else if (row == 3) {
/*  580 */       v.x = this.m30;
/*  581 */       v.y = this.m31;
/*  582 */       v.z = this.m32;
/*  583 */       v.w = this.m33;
/*      */     } else {
/*  585 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, float[] v) {
/*  597 */     if (row == 0) {
/*  598 */       v[0] = this.m00;
/*  599 */       v[1] = this.m01;
/*  600 */       v[2] = this.m02;
/*  601 */       v[3] = this.m03;
/*  602 */     } else if (row == 1) {
/*  603 */       v[0] = this.m10;
/*  604 */       v[1] = this.m11;
/*  605 */       v[2] = this.m12;
/*  606 */       v[3] = this.m13;
/*  607 */     } else if (row == 2) {
/*  608 */       v[0] = this.m20;
/*  609 */       v[1] = this.m21;
/*  610 */       v[2] = this.m22;
/*  611 */       v[3] = this.m23;
/*  612 */     } else if (row == 3) {
/*  613 */       v[0] = this.m30;
/*  614 */       v[1] = this.m31;
/*  615 */       v[2] = this.m32;
/*  616 */       v[3] = this.m33;
/*      */     } else {
/*  618 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int column, Vector4f v) {
/*  631 */     if (column == 0) {
/*  632 */       v.x = this.m00;
/*  633 */       v.y = this.m10;
/*  634 */       v.z = this.m20;
/*  635 */       v.w = this.m30;
/*  636 */     } else if (column == 1) {
/*  637 */       v.x = this.m01;
/*  638 */       v.y = this.m11;
/*  639 */       v.z = this.m21;
/*  640 */       v.w = this.m31;
/*  641 */     } else if (column == 2) {
/*  642 */       v.x = this.m02;
/*  643 */       v.y = this.m12;
/*  644 */       v.z = this.m22;
/*  645 */       v.w = this.m32;
/*  646 */     } else if (column == 3) {
/*  647 */       v.x = this.m03;
/*  648 */       v.y = this.m13;
/*  649 */       v.z = this.m23;
/*  650 */       v.w = this.m33;
/*      */     } else {
/*  652 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int column, float[] v) {
/*  665 */     if (column == 0) {
/*  666 */       v[0] = this.m00;
/*  667 */       v[1] = this.m10;
/*  668 */       v[2] = this.m20;
/*  669 */       v[3] = this.m30;
/*  670 */     } else if (column == 1) {
/*  671 */       v[0] = this.m01;
/*  672 */       v[1] = this.m11;
/*  673 */       v[2] = this.m21;
/*  674 */       v[3] = this.m31;
/*  675 */     } else if (column == 2) {
/*  676 */       v[0] = this.m02;
/*  677 */       v[1] = this.m12;
/*  678 */       v[2] = this.m22;
/*  679 */       v[3] = this.m32;
/*  680 */     } else if (column == 3) {
/*  681 */       v[0] = this.m03;
/*  682 */       v[1] = this.m13;
/*  683 */       v[2] = this.m23;
/*  684 */       v[3] = this.m33;
/*      */     } else {
/*  686 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(float scale) {
/*  701 */     double[] tmp_rot = new double[9];
/*  702 */     double[] tmp_scale = new double[3];
/*  703 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  705 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  706 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  707 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  709 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  710 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  711 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  713 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  714 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  715 */     this.m22 = (float)(tmp_rot[8] * scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3d m1) {
/*  728 */     double[] tmp_rot = new double[9];
/*  729 */     double[] tmp_scale = new double[3];
/*      */     
/*  731 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  733 */     m1.m00 = tmp_rot[0];
/*  734 */     m1.m01 = tmp_rot[1];
/*  735 */     m1.m02 = tmp_rot[2];
/*      */     
/*  737 */     m1.m10 = tmp_rot[3];
/*  738 */     m1.m11 = tmp_rot[4];
/*  739 */     m1.m12 = tmp_rot[5];
/*      */     
/*  741 */     m1.m20 = tmp_rot[6];
/*  742 */     m1.m21 = tmp_rot[7];
/*  743 */     m1.m22 = tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3f m1) {
/*  755 */     double[] tmp_rot = new double[9];
/*  756 */     double[] tmp_scale = new double[3];
/*      */     
/*  758 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  760 */     m1.m00 = (float)tmp_rot[0];
/*  761 */     m1.m01 = (float)tmp_rot[1];
/*  762 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  764 */     m1.m10 = (float)tmp_rot[3];
/*  765 */     m1.m11 = (float)tmp_rot[4];
/*  766 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  768 */     m1.m20 = (float)tmp_rot[6];
/*  769 */     m1.m21 = (float)tmp_rot[7];
/*  770 */     m1.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float get(Matrix3f m1, Vector3f t1) {
/*  785 */     double[] tmp_rot = new double[9];
/*  786 */     double[] tmp_scale = new double[3];
/*      */     
/*  788 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  790 */     m1.m00 = (float)tmp_rot[0];
/*  791 */     m1.m01 = (float)tmp_rot[1];
/*  792 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  794 */     m1.m10 = (float)tmp_rot[3];
/*  795 */     m1.m11 = (float)tmp_rot[4];
/*  796 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  798 */     m1.m20 = (float)tmp_rot[6];
/*  799 */     m1.m21 = (float)tmp_rot[7];
/*  800 */     m1.m22 = (float)tmp_rot[8];
/*      */     
/*  802 */     t1.x = this.m03;
/*  803 */     t1.y = this.m13;
/*  804 */     t1.z = this.m23;
/*      */     
/*  806 */     return (float)Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Quat4f q1) {
/*  819 */     double[] tmp_rot = new double[9];
/*  820 */     double[] tmp_scale = new double[3];
/*  821 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  825 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  826 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  827 */       q1.w = (float)Math.sqrt(ww);
/*  828 */       ww = 0.25D / q1.w;
/*  829 */       q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
/*  830 */       q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
/*  831 */       q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  835 */     q1.w = 0.0F;
/*  836 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  837 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  838 */       q1.x = (float)Math.sqrt(ww);
/*  839 */       ww = 0.5D / q1.x;
/*  840 */       q1.y = (float)(tmp_rot[3] * ww);
/*  841 */       q1.z = (float)(tmp_rot[6] * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  845 */     q1.x = 0.0F;
/*  846 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  847 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  848 */       q1.y = (float)Math.sqrt(ww);
/*  849 */       q1.z = (float)(tmp_rot[7] / 2.0D * q1.y);
/*      */       
/*      */       return;
/*      */     } 
/*  853 */     q1.y = 0.0F;
/*  854 */     q1.z = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3f trans) {
/*  865 */     trans.x = this.m03;
/*  866 */     trans.y = this.m13;
/*  867 */     trans.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f m1) {
/*  877 */     m1.m00 = this.m00;
/*  878 */     m1.m01 = this.m01;
/*  879 */     m1.m02 = this.m02;
/*  880 */     m1.m10 = this.m10;
/*  881 */     m1.m11 = this.m11;
/*  882 */     m1.m12 = this.m12;
/*  883 */     m1.m20 = this.m20;
/*  884 */     m1.m21 = this.m21;
/*  885 */     m1.m22 = this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getScale() {
/*  897 */     double[] tmp_rot = new double[9];
/*  898 */     double[] tmp_scale = new double[3];
/*      */     
/*  900 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  902 */     return (float)Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3f m1) {
/*  914 */     this.m00 = m1.m00;
/*  915 */     this.m01 = m1.m01;
/*  916 */     this.m02 = m1.m02;
/*  917 */     this.m10 = m1.m10;
/*  918 */     this.m11 = m1.m11;
/*  919 */     this.m12 = m1.m12;
/*  920 */     this.m20 = m1.m20;
/*  921 */     this.m21 = m1.m21;
/*  922 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, float x, float y, float z, float w) {
/*  936 */     switch (row) {
/*      */       case 0:
/*  938 */         this.m00 = x;
/*  939 */         this.m01 = y;
/*  940 */         this.m02 = z;
/*  941 */         this.m03 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  945 */         this.m10 = x;
/*  946 */         this.m11 = y;
/*  947 */         this.m12 = z;
/*  948 */         this.m13 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  952 */         this.m20 = x;
/*  953 */         this.m21 = y;
/*  954 */         this.m22 = z;
/*  955 */         this.m23 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  959 */         this.m30 = x;
/*  960 */         this.m31 = y;
/*  961 */         this.m32 = z;
/*  962 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/*  966 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector4f v) {
/*  977 */     switch (row) {
/*      */       case 0:
/*  979 */         this.m00 = v.x;
/*  980 */         this.m01 = v.y;
/*  981 */         this.m02 = v.z;
/*  982 */         this.m03 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  986 */         this.m10 = v.x;
/*  987 */         this.m11 = v.y;
/*  988 */         this.m12 = v.z;
/*  989 */         this.m13 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  993 */         this.m20 = v.x;
/*  994 */         this.m21 = v.y;
/*  995 */         this.m22 = v.z;
/*  996 */         this.m23 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1000 */         this.m30 = v.x;
/* 1001 */         this.m31 = v.y;
/* 1002 */         this.m32 = v.z;
/* 1003 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1007 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, float[] v) {
/* 1019 */     switch (row) {
/*      */       case 0:
/* 1021 */         this.m00 = v[0];
/* 1022 */         this.m01 = v[1];
/* 1023 */         this.m02 = v[2];
/* 1024 */         this.m03 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1028 */         this.m10 = v[0];
/* 1029 */         this.m11 = v[1];
/* 1030 */         this.m12 = v[2];
/* 1031 */         this.m13 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1035 */         this.m20 = v[0];
/* 1036 */         this.m21 = v[1];
/* 1037 */         this.m22 = v[2];
/* 1038 */         this.m23 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1042 */         this.m30 = v[0];
/* 1043 */         this.m31 = v[1];
/* 1044 */         this.m32 = v[2];
/* 1045 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1049 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, float x, float y, float z, float w) {
/* 1063 */     switch (column) {
/*      */       case 0:
/* 1065 */         this.m00 = x;
/* 1066 */         this.m10 = y;
/* 1067 */         this.m20 = z;
/* 1068 */         this.m30 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1072 */         this.m01 = x;
/* 1073 */         this.m11 = y;
/* 1074 */         this.m21 = z;
/* 1075 */         this.m31 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1079 */         this.m02 = x;
/* 1080 */         this.m12 = y;
/* 1081 */         this.m22 = z;
/* 1082 */         this.m32 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1086 */         this.m03 = x;
/* 1087 */         this.m13 = y;
/* 1088 */         this.m23 = z;
/* 1089 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1093 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector4f v) {
/* 1104 */     switch (column) {
/*      */       case 0:
/* 1106 */         this.m00 = v.x;
/* 1107 */         this.m10 = v.y;
/* 1108 */         this.m20 = v.z;
/* 1109 */         this.m30 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1113 */         this.m01 = v.x;
/* 1114 */         this.m11 = v.y;
/* 1115 */         this.m21 = v.z;
/* 1116 */         this.m31 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1120 */         this.m02 = v.x;
/* 1121 */         this.m12 = v.y;
/* 1122 */         this.m22 = v.z;
/* 1123 */         this.m32 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1127 */         this.m03 = v.x;
/* 1128 */         this.m13 = v.y;
/* 1129 */         this.m23 = v.z;
/* 1130 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1134 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, float[] v) {
/* 1145 */     switch (column) {
/*      */       case 0:
/* 1147 */         this.m00 = v[0];
/* 1148 */         this.m10 = v[1];
/* 1149 */         this.m20 = v[2];
/* 1150 */         this.m30 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1154 */         this.m01 = v[0];
/* 1155 */         this.m11 = v[1];
/* 1156 */         this.m21 = v[2];
/* 1157 */         this.m31 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1161 */         this.m02 = v[0];
/* 1162 */         this.m12 = v[1];
/* 1163 */         this.m22 = v[2];
/* 1164 */         this.m32 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1168 */         this.m03 = v[0];
/* 1169 */         this.m13 = v[1];
/* 1170 */         this.m23 = v[2];
/* 1171 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1175 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/* 1185 */     this.m00 += scalar;
/* 1186 */     this.m01 += scalar;
/* 1187 */     this.m02 += scalar;
/* 1188 */     this.m03 += scalar;
/* 1189 */     this.m10 += scalar;
/* 1190 */     this.m11 += scalar;
/* 1191 */     this.m12 += scalar;
/* 1192 */     this.m13 += scalar;
/* 1193 */     this.m20 += scalar;
/* 1194 */     this.m21 += scalar;
/* 1195 */     this.m22 += scalar;
/* 1196 */     this.m23 += scalar;
/* 1197 */     this.m30 += scalar;
/* 1198 */     this.m31 += scalar;
/* 1199 */     this.m32 += scalar;
/* 1200 */     this.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar, Matrix4f m1) {
/* 1211 */     m1.m00 += scalar;
/* 1212 */     m1.m01 += scalar;
/* 1213 */     m1.m02 += scalar;
/* 1214 */     m1.m03 += scalar;
/* 1215 */     m1.m10 += scalar;
/* 1216 */     m1.m11 += scalar;
/* 1217 */     m1.m12 += scalar;
/* 1218 */     m1.m13 += scalar;
/* 1219 */     m1.m20 += scalar;
/* 1220 */     m1.m21 += scalar;
/* 1221 */     m1.m22 += scalar;
/* 1222 */     m1.m23 += scalar;
/* 1223 */     m1.m30 += scalar;
/* 1224 */     m1.m31 += scalar;
/* 1225 */     m1.m32 += scalar;
/* 1226 */     m1.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1, Matrix4f m2) {
/* 1236 */     m1.m00 += m2.m00;
/* 1237 */     m1.m01 += m2.m01;
/* 1238 */     m1.m02 += m2.m02;
/* 1239 */     m1.m03 += m2.m03;
/*      */     
/* 1241 */     m1.m10 += m2.m10;
/* 1242 */     m1.m11 += m2.m11;
/* 1243 */     m1.m12 += m2.m12;
/* 1244 */     m1.m13 += m2.m13;
/*      */     
/* 1246 */     m1.m20 += m2.m20;
/* 1247 */     m1.m21 += m2.m21;
/* 1248 */     m1.m22 += m2.m22;
/* 1249 */     m1.m23 += m2.m23;
/*      */     
/* 1251 */     m1.m30 += m2.m30;
/* 1252 */     m1.m31 += m2.m31;
/* 1253 */     m1.m32 += m2.m32;
/* 1254 */     m1.m33 += m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1) {
/* 1264 */     this.m00 += m1.m00;
/* 1265 */     this.m01 += m1.m01;
/* 1266 */     this.m02 += m1.m02;
/* 1267 */     this.m03 += m1.m03;
/*      */     
/* 1269 */     this.m10 += m1.m10;
/* 1270 */     this.m11 += m1.m11;
/* 1271 */     this.m12 += m1.m12;
/* 1272 */     this.m13 += m1.m13;
/*      */     
/* 1274 */     this.m20 += m1.m20;
/* 1275 */     this.m21 += m1.m21;
/* 1276 */     this.m22 += m1.m22;
/* 1277 */     this.m23 += m1.m23;
/*      */     
/* 1279 */     this.m30 += m1.m30;
/* 1280 */     this.m31 += m1.m31;
/* 1281 */     this.m32 += m1.m32;
/* 1282 */     this.m33 += m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4f m1, Matrix4f m2) {
/* 1294 */     m1.m00 -= m2.m00;
/* 1295 */     m1.m01 -= m2.m01;
/* 1296 */     m1.m02 -= m2.m02;
/* 1297 */     m1.m03 -= m2.m03;
/*      */     
/* 1299 */     m1.m10 -= m2.m10;
/* 1300 */     m1.m11 -= m2.m11;
/* 1301 */     m1.m12 -= m2.m12;
/* 1302 */     m1.m13 -= m2.m13;
/*      */     
/* 1304 */     m1.m20 -= m2.m20;
/* 1305 */     m1.m21 -= m2.m21;
/* 1306 */     m1.m22 -= m2.m22;
/* 1307 */     m1.m23 -= m2.m23;
/*      */     
/* 1309 */     m1.m30 -= m2.m30;
/* 1310 */     m1.m31 -= m2.m31;
/* 1311 */     m1.m32 -= m2.m32;
/* 1312 */     m1.m33 -= m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4f m1) {
/* 1322 */     this.m00 -= m1.m00;
/* 1323 */     this.m01 -= m1.m01;
/* 1324 */     this.m02 -= m1.m02;
/* 1325 */     this.m03 -= m1.m03;
/*      */     
/* 1327 */     this.m10 -= m1.m10;
/* 1328 */     this.m11 -= m1.m11;
/* 1329 */     this.m12 -= m1.m12;
/* 1330 */     this.m13 -= m1.m13;
/*      */     
/* 1332 */     this.m20 -= m1.m20;
/* 1333 */     this.m21 -= m1.m21;
/* 1334 */     this.m22 -= m1.m22;
/* 1335 */     this.m23 -= m1.m23;
/*      */     
/* 1337 */     this.m30 -= m1.m30;
/* 1338 */     this.m31 -= m1.m31;
/* 1339 */     this.m32 -= m1.m32;
/* 1340 */     this.m33 -= m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1349 */     float temp = this.m10;
/* 1350 */     this.m10 = this.m01;
/* 1351 */     this.m01 = temp;
/*      */     
/* 1353 */     temp = this.m20;
/* 1354 */     this.m20 = this.m02;
/* 1355 */     this.m02 = temp;
/*      */     
/* 1357 */     temp = this.m30;
/* 1358 */     this.m30 = this.m03;
/* 1359 */     this.m03 = temp;
/*      */     
/* 1361 */     temp = this.m21;
/* 1362 */     this.m21 = this.m12;
/* 1363 */     this.m12 = temp;
/*      */     
/* 1365 */     temp = this.m31;
/* 1366 */     this.m31 = this.m13;
/* 1367 */     this.m13 = temp;
/*      */     
/* 1369 */     temp = this.m32;
/* 1370 */     this.m32 = this.m23;
/* 1371 */     this.m23 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4f m1) {
/* 1380 */     if (this != m1) {
/* 1381 */       this.m00 = m1.m00;
/* 1382 */       this.m01 = m1.m10;
/* 1383 */       this.m02 = m1.m20;
/* 1384 */       this.m03 = m1.m30;
/*      */       
/* 1386 */       this.m10 = m1.m01;
/* 1387 */       this.m11 = m1.m11;
/* 1388 */       this.m12 = m1.m21;
/* 1389 */       this.m13 = m1.m31;
/*      */       
/* 1391 */       this.m20 = m1.m02;
/* 1392 */       this.m21 = m1.m12;
/* 1393 */       this.m22 = m1.m22;
/* 1394 */       this.m23 = m1.m32;
/*      */       
/* 1396 */       this.m30 = m1.m03;
/* 1397 */       this.m31 = m1.m13;
/* 1398 */       this.m32 = m1.m23;
/* 1399 */       this.m33 = m1.m33;
/*      */     } else {
/* 1401 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/* 1411 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/* 1412 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1413 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1415 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1416 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/* 1417 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1419 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1420 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1421 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */     
/* 1423 */     this.m03 = 0.0F;
/* 1424 */     this.m13 = 0.0F;
/* 1425 */     this.m23 = 0.0F;
/*      */     
/* 1427 */     this.m30 = 0.0F;
/* 1428 */     this.m31 = 0.0F;
/* 1429 */     this.m32 = 0.0F;
/* 1430 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/* 1440 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 1441 */     if (mag < 1.0E-8D) {
/* 1442 */       this.m00 = 1.0F;
/* 1443 */       this.m01 = 0.0F;
/* 1444 */       this.m02 = 0.0F;
/*      */       
/* 1446 */       this.m10 = 0.0F;
/* 1447 */       this.m11 = 1.0F;
/* 1448 */       this.m12 = 0.0F;
/*      */       
/* 1450 */       this.m20 = 0.0F;
/* 1451 */       this.m21 = 0.0F;
/* 1452 */       this.m22 = 1.0F;
/*      */     } else {
/* 1454 */       mag = 1.0F / mag;
/* 1455 */       float ax = a1.x * mag;
/* 1456 */       float ay = a1.y * mag;
/* 1457 */       float az = a1.z * mag;
/*      */       
/* 1459 */       float sinTheta = (float)Math.sin(a1.angle);
/* 1460 */       float cosTheta = (float)Math.cos(a1.angle);
/* 1461 */       float t = 1.0F - cosTheta;
/*      */       
/* 1463 */       float xz = ax * az;
/* 1464 */       float xy = ax * ay;
/* 1465 */       float yz = ay * az;
/*      */       
/* 1467 */       this.m00 = t * ax * ax + cosTheta;
/* 1468 */       this.m01 = t * xy - sinTheta * az;
/* 1469 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1471 */       this.m10 = t * xy + sinTheta * az;
/* 1472 */       this.m11 = t * ay * ay + cosTheta;
/* 1473 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1475 */       this.m20 = t * xz - sinTheta * ay;
/* 1476 */       this.m21 = t * yz + sinTheta * ax;
/* 1477 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/* 1479 */     this.m03 = 0.0F;
/* 1480 */     this.m13 = 0.0F;
/* 1481 */     this.m23 = 0.0F;
/*      */     
/* 1483 */     this.m30 = 0.0F;
/* 1484 */     this.m31 = 0.0F;
/* 1485 */     this.m32 = 0.0F;
/* 1486 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/* 1496 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1497 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1498 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1500 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1501 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1502 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1504 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1505 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1506 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1508 */     this.m03 = 0.0F;
/* 1509 */     this.m13 = 0.0F;
/* 1510 */     this.m23 = 0.0F;
/*      */     
/* 1512 */     this.m30 = 0.0F;
/* 1513 */     this.m31 = 0.0F;
/* 1514 */     this.m32 = 0.0F;
/* 1515 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/* 1525 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/* 1527 */     if (mag < 1.0E-8D) {
/* 1528 */       this.m00 = 1.0F;
/* 1529 */       this.m01 = 0.0F;
/* 1530 */       this.m02 = 0.0F;
/*      */       
/* 1532 */       this.m10 = 0.0F;
/* 1533 */       this.m11 = 1.0F;
/* 1534 */       this.m12 = 0.0F;
/*      */       
/* 1536 */       this.m20 = 0.0F;
/* 1537 */       this.m21 = 0.0F;
/* 1538 */       this.m22 = 1.0F;
/*      */     } else {
/* 1540 */       mag = 1.0D / mag;
/* 1541 */       double ax = a1.x * mag;
/* 1542 */       double ay = a1.y * mag;
/* 1543 */       double az = a1.z * mag;
/*      */       
/* 1545 */       float sinTheta = (float)Math.sin(a1.angle);
/* 1546 */       float cosTheta = (float)Math.cos(a1.angle);
/* 1547 */       float t = 1.0F - cosTheta;
/*      */       
/* 1549 */       float xz = (float)(ax * az);
/* 1550 */       float xy = (float)(ax * ay);
/* 1551 */       float yz = (float)(ay * az);
/*      */       
/* 1553 */       this.m00 = t * (float)(ax * ax) + cosTheta;
/* 1554 */       this.m01 = t * xy - sinTheta * (float)az;
/* 1555 */       this.m02 = t * xz + sinTheta * (float)ay;
/*      */       
/* 1557 */       this.m10 = t * xy + sinTheta * (float)az;
/* 1558 */       this.m11 = t * (float)(ay * ay) + cosTheta;
/* 1559 */       this.m12 = t * yz - sinTheta * (float)ax;
/*      */       
/* 1561 */       this.m20 = t * xz - sinTheta * (float)ay;
/* 1562 */       this.m21 = t * yz + sinTheta * (float)ax;
/* 1563 */       this.m22 = t * (float)(az * az) + cosTheta;
/*      */     } 
/* 1565 */     this.m03 = 0.0F;
/* 1566 */     this.m13 = 0.0F;
/* 1567 */     this.m23 = 0.0F;
/*      */     
/* 1569 */     this.m30 = 0.0F;
/* 1570 */     this.m31 = 0.0F;
/* 1571 */     this.m32 = 0.0F;
/* 1572 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1, Vector3d t1, double s) {
/* 1584 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/* 1585 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1586 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1588 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1589 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/* 1590 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1592 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1593 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1594 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/* 1596 */     this.m03 = (float)t1.x;
/* 1597 */     this.m13 = (float)t1.y;
/* 1598 */     this.m23 = (float)t1.z;
/*      */     
/* 1600 */     this.m30 = 0.0F;
/* 1601 */     this.m31 = 0.0F;
/* 1602 */     this.m32 = 0.0F;
/* 1603 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1, Vector3f t1, float s) {
/* 1615 */     this.m00 = s * (1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z);
/* 1616 */     this.m10 = s * 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1617 */     this.m20 = s * 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1619 */     this.m01 = s * 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1620 */     this.m11 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z);
/* 1621 */     this.m21 = s * 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1623 */     this.m02 = s * 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1624 */     this.m12 = s * 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1625 */     this.m22 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y);
/*      */     
/* 1627 */     this.m03 = t1.x;
/* 1628 */     this.m13 = t1.y;
/* 1629 */     this.m23 = t1.z;
/*      */     
/* 1631 */     this.m30 = 0.0F;
/* 1632 */     this.m31 = 0.0F;
/* 1633 */     this.m32 = 0.0F;
/* 1634 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/* 1644 */     this.m00 = (float)m1.m00;
/* 1645 */     this.m01 = (float)m1.m01;
/* 1646 */     this.m02 = (float)m1.m02;
/* 1647 */     this.m03 = (float)m1.m03;
/*      */     
/* 1649 */     this.m10 = (float)m1.m10;
/* 1650 */     this.m11 = (float)m1.m11;
/* 1651 */     this.m12 = (float)m1.m12;
/* 1652 */     this.m13 = (float)m1.m13;
/*      */     
/* 1654 */     this.m20 = (float)m1.m20;
/* 1655 */     this.m21 = (float)m1.m21;
/* 1656 */     this.m22 = (float)m1.m22;
/* 1657 */     this.m23 = (float)m1.m23;
/*      */     
/* 1659 */     this.m30 = (float)m1.m30;
/* 1660 */     this.m31 = (float)m1.m31;
/* 1661 */     this.m32 = (float)m1.m32;
/* 1662 */     this.m33 = (float)m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/* 1672 */     this.m00 = m1.m00;
/* 1673 */     this.m01 = m1.m01;
/* 1674 */     this.m02 = m1.m02;
/* 1675 */     this.m03 = m1.m03;
/*      */     
/* 1677 */     this.m10 = m1.m10;
/* 1678 */     this.m11 = m1.m11;
/* 1679 */     this.m12 = m1.m12;
/* 1680 */     this.m13 = m1.m13;
/*      */     
/* 1682 */     this.m20 = m1.m20;
/* 1683 */     this.m21 = m1.m21;
/* 1684 */     this.m22 = m1.m22;
/* 1685 */     this.m23 = m1.m23;
/*      */     
/* 1687 */     this.m30 = m1.m30;
/* 1688 */     this.m31 = m1.m31;
/* 1689 */     this.m32 = m1.m32;
/* 1690 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4f m1) {
/* 1701 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1708 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void invertGeneral(Matrix4f m1) {
/* 1720 */     double[] temp = new double[16];
/* 1721 */     double[] result = new double[16];
/* 1722 */     int[] row_perm = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1729 */     temp[0] = m1.m00;
/* 1730 */     temp[1] = m1.m01;
/* 1731 */     temp[2] = m1.m02;
/* 1732 */     temp[3] = m1.m03;
/*      */     
/* 1734 */     temp[4] = m1.m10;
/* 1735 */     temp[5] = m1.m11;
/* 1736 */     temp[6] = m1.m12;
/* 1737 */     temp[7] = m1.m13;
/*      */     
/* 1739 */     temp[8] = m1.m20;
/* 1740 */     temp[9] = m1.m21;
/* 1741 */     temp[10] = m1.m22;
/* 1742 */     temp[11] = m1.m23;
/*      */     
/* 1744 */     temp[12] = m1.m30;
/* 1745 */     temp[13] = m1.m31;
/* 1746 */     temp[14] = m1.m32;
/* 1747 */     temp[15] = m1.m33;
/*      */ 
/*      */     
/* 1750 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1752 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4f12"));
/*      */     }
/*      */ 
/*      */     
/* 1756 */     for (int i = 0; i < 16; ) { result[i] = 0.0D; i++; }
/* 1757 */      result[0] = 1.0D;
/* 1758 */     result[5] = 1.0D;
/* 1759 */     result[10] = 1.0D;
/* 1760 */     result[15] = 1.0D;
/* 1761 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1763 */     this.m00 = (float)result[0];
/* 1764 */     this.m01 = (float)result[1];
/* 1765 */     this.m02 = (float)result[2];
/* 1766 */     this.m03 = (float)result[3];
/*      */     
/* 1768 */     this.m10 = (float)result[4];
/* 1769 */     this.m11 = (float)result[5];
/* 1770 */     this.m12 = (float)result[6];
/* 1771 */     this.m13 = (float)result[7];
/*      */     
/* 1773 */     this.m20 = (float)result[8];
/* 1774 */     this.m21 = (float)result[9];
/* 1775 */     this.m22 = (float)result[10];
/* 1776 */     this.m23 = (float)result[11];
/*      */     
/* 1778 */     this.m30 = (float)result[12];
/* 1779 */     this.m31 = (float)result[13];
/* 1780 */     this.m32 = (float)result[14];
/* 1781 */     this.m33 = (float)result[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(double[] matrix0, int[] row_perm) {
/* 1808 */     double[] row_scale = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1816 */     int ptr = 0;
/* 1817 */     int rs = 0;
/*      */ 
/*      */     
/* 1820 */     int i = 4;
/* 1821 */     while (i-- != 0) {
/* 1822 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1825 */       int k = 4;
/* 1826 */       while (k-- != 0) {
/* 1827 */         double temp = matrix0[ptr++];
/* 1828 */         temp = Math.abs(temp);
/* 1829 */         if (temp > big) {
/* 1830 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1835 */       if (big == 0.0D) {
/* 1836 */         return false;
/*      */       }
/* 1838 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1846 */     int mtx = 0;
/*      */ 
/*      */     
/* 1849 */     for (int j = 0; j < 4; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1855 */       for (k = 0; k < j; k++) {
/* 1856 */         int target = mtx + 4 * k + j;
/* 1857 */         double sum = matrix0[target];
/* 1858 */         int m = k;
/* 1859 */         int p1 = mtx + 4 * k;
/* 1860 */         int p2 = mtx + j;
/* 1861 */         while (m-- != 0) {
/* 1862 */           sum -= matrix0[p1] * matrix0[p2];
/* 1863 */           p1++;
/* 1864 */           p2 += 4;
/*      */         } 
/* 1866 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1871 */       double big = 0.0D;
/* 1872 */       int imax = -1;
/* 1873 */       for (k = j; k < 4; k++) {
/* 1874 */         int target = mtx + 4 * k + j;
/* 1875 */         double sum = matrix0[target];
/* 1876 */         int m = j;
/* 1877 */         int p1 = mtx + 4 * k;
/* 1878 */         int p2 = mtx + j;
/* 1879 */         while (m-- != 0) {
/* 1880 */           sum -= matrix0[p1] * matrix0[p2];
/* 1881 */           p1++;
/* 1882 */           p2 += 4;
/*      */         } 
/* 1884 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1887 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1888 */           big = temp;
/* 1889 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1893 */       if (imax < 0) {
/* 1894 */         throw new RuntimeException(VecMathI18N.getString("Matrix4f13"));
/*      */       }
/*      */ 
/*      */       
/* 1898 */       if (j != imax) {
/*      */         
/* 1900 */         int m = 4;
/* 1901 */         int p1 = mtx + 4 * imax;
/* 1902 */         int p2 = mtx + 4 * j;
/* 1903 */         while (m-- != 0) {
/* 1904 */           double temp = matrix0[p1];
/* 1905 */           matrix0[p1++] = matrix0[p2];
/* 1906 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1910 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1914 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1917 */       if (matrix0[mtx + 4 * j + j] == 0.0D) {
/* 1918 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1922 */       if (j != 3) {
/* 1923 */         double temp = 1.0D / matrix0[mtx + 4 * j + j];
/* 1924 */         int target = mtx + 4 * (j + 1) + j;
/* 1925 */         k = 3 - j;
/* 1926 */         while (k-- != 0) {
/* 1927 */           matrix0[target] = matrix0[target] * temp;
/* 1928 */           target += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1934 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void luBacksubstitution(double[] matrix1, int[] row_perm, double[] matrix2) {
/* 1964 */     int rp = 0;
/*      */ 
/*      */     
/* 1967 */     for (int k = 0; k < 4; k++) {
/*      */       
/* 1969 */       int cv = k;
/* 1970 */       int ii = -1;
/*      */ 
/*      */       
/* 1973 */       for (int i = 0; i < 4; i++) {
/*      */ 
/*      */         
/* 1976 */         int ip = row_perm[rp + i];
/* 1977 */         double sum = matrix2[cv + 4 * ip];
/* 1978 */         matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
/* 1979 */         if (ii >= 0) {
/*      */           
/* 1981 */           int m = i * 4;
/* 1982 */           for (int j = ii; j <= i - 1; j++) {
/* 1983 */             sum -= matrix1[m + j] * matrix2[cv + 4 * j];
/*      */           }
/* 1985 */         } else if (sum != 0.0D) {
/* 1986 */           ii = i;
/*      */         } 
/* 1988 */         matrix2[cv + 4 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1993 */       int rv = 12;
/* 1994 */       matrix2[cv + 12] = matrix2[cv + 12] / matrix1[rv + 3];
/*      */       
/* 1996 */       rv -= 4;
/* 1997 */       matrix2[cv + 8] = (matrix2[cv + 8] - 
/* 1998 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
/*      */       
/* 2000 */       rv -= 4;
/* 2001 */       matrix2[cv + 4] = (matrix2[cv + 4] - 
/* 2002 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 2003 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
/*      */       
/* 2005 */       rv -= 4;
/* 2006 */       matrix2[cv + 0] = (matrix2[cv + 0] - 
/* 2007 */         matrix1[rv + 1] * matrix2[cv + 4] - 
/* 2008 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 2009 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float determinant() {
/* 2023 */     float det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - 
/* 2024 */       this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/* 2025 */     det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - 
/* 2026 */       this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/* 2027 */     det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - 
/* 2028 */       this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/* 2029 */     det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - 
/* 2030 */       this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */     
/* 2032 */     return det;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f m1) {
/* 2044 */     this.m00 = m1.m00;
/* 2045 */     this.m01 = m1.m01;
/* 2046 */     this.m02 = m1.m02;
/* 2047 */     this.m03 = 0.0F;
/* 2048 */     this.m10 = m1.m10;
/* 2049 */     this.m11 = m1.m11;
/* 2050 */     this.m12 = m1.m12;
/* 2051 */     this.m13 = 0.0F;
/* 2052 */     this.m20 = m1.m20;
/* 2053 */     this.m21 = m1.m21;
/* 2054 */     this.m22 = m1.m22;
/* 2055 */     this.m23 = 0.0F;
/* 2056 */     this.m30 = 0.0F;
/* 2057 */     this.m31 = 0.0F;
/* 2058 */     this.m32 = 0.0F;
/* 2059 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/* 2071 */     this.m00 = (float)m1.m00;
/* 2072 */     this.m01 = (float)m1.m01;
/* 2073 */     this.m02 = (float)m1.m02;
/* 2074 */     this.m03 = 0.0F;
/* 2075 */     this.m10 = (float)m1.m10;
/* 2076 */     this.m11 = (float)m1.m11;
/* 2077 */     this.m12 = (float)m1.m12;
/* 2078 */     this.m13 = 0.0F;
/* 2079 */     this.m20 = (float)m1.m20;
/* 2080 */     this.m21 = (float)m1.m21;
/* 2081 */     this.m22 = (float)m1.m22;
/* 2082 */     this.m23 = 0.0F;
/* 2083 */     this.m30 = 0.0F;
/* 2084 */     this.m31 = 0.0F;
/* 2085 */     this.m32 = 0.0F;
/* 2086 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 2096 */     this.m00 = scale;
/* 2097 */     this.m01 = 0.0F;
/* 2098 */     this.m02 = 0.0F;
/* 2099 */     this.m03 = 0.0F;
/*      */     
/* 2101 */     this.m10 = 0.0F;
/* 2102 */     this.m11 = scale;
/* 2103 */     this.m12 = 0.0F;
/* 2104 */     this.m13 = 0.0F;
/*      */     
/* 2106 */     this.m20 = 0.0F;
/* 2107 */     this.m21 = 0.0F;
/* 2108 */     this.m22 = scale;
/* 2109 */     this.m23 = 0.0F;
/*      */     
/* 2111 */     this.m30 = 0.0F;
/* 2112 */     this.m31 = 0.0F;
/* 2113 */     this.m32 = 0.0F;
/* 2114 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float[] m) {
/* 2125 */     this.m00 = m[0];
/* 2126 */     this.m01 = m[1];
/* 2127 */     this.m02 = m[2];
/* 2128 */     this.m03 = m[3];
/* 2129 */     this.m10 = m[4];
/* 2130 */     this.m11 = m[5];
/* 2131 */     this.m12 = m[6];
/* 2132 */     this.m13 = m[7];
/* 2133 */     this.m20 = m[8];
/* 2134 */     this.m21 = m[9];
/* 2135 */     this.m22 = m[10];
/* 2136 */     this.m23 = m[11];
/* 2137 */     this.m30 = m[12];
/* 2138 */     this.m31 = m[13];
/* 2139 */     this.m32 = m[14];
/* 2140 */     this.m33 = m[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3f v1) {
/* 2150 */     this.m00 = 1.0F;
/* 2151 */     this.m01 = 0.0F;
/* 2152 */     this.m02 = 0.0F;
/* 2153 */     this.m03 = v1.x;
/*      */     
/* 2155 */     this.m10 = 0.0F;
/* 2156 */     this.m11 = 1.0F;
/* 2157 */     this.m12 = 0.0F;
/* 2158 */     this.m13 = v1.y;
/*      */     
/* 2160 */     this.m20 = 0.0F;
/* 2161 */     this.m21 = 0.0F;
/* 2162 */     this.m22 = 1.0F;
/* 2163 */     this.m23 = v1.z;
/*      */     
/* 2165 */     this.m30 = 0.0F;
/* 2166 */     this.m31 = 0.0F;
/* 2167 */     this.m32 = 0.0F;
/* 2168 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale, Vector3f t1) {
/* 2180 */     this.m00 = scale;
/* 2181 */     this.m01 = 0.0F;
/* 2182 */     this.m02 = 0.0F;
/* 2183 */     this.m03 = t1.x;
/*      */     
/* 2185 */     this.m10 = 0.0F;
/* 2186 */     this.m11 = scale;
/* 2187 */     this.m12 = 0.0F;
/* 2188 */     this.m13 = t1.y;
/*      */     
/* 2190 */     this.m20 = 0.0F;
/* 2191 */     this.m21 = 0.0F;
/* 2192 */     this.m22 = scale;
/* 2193 */     this.m23 = t1.z;
/*      */     
/* 2195 */     this.m30 = 0.0F;
/* 2196 */     this.m31 = 0.0F;
/* 2197 */     this.m32 = 0.0F;
/* 2198 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3f t1, float scale) {
/* 2210 */     this.m00 = scale;
/* 2211 */     this.m01 = 0.0F;
/* 2212 */     this.m02 = 0.0F;
/* 2213 */     this.m03 = scale * t1.x;
/*      */     
/* 2215 */     this.m10 = 0.0F;
/* 2216 */     this.m11 = scale;
/* 2217 */     this.m12 = 0.0F;
/* 2218 */     this.m13 = scale * t1.y;
/*      */     
/* 2220 */     this.m20 = 0.0F;
/* 2221 */     this.m21 = 0.0F;
/* 2222 */     this.m22 = scale;
/* 2223 */     this.m23 = scale * t1.z;
/*      */     
/* 2225 */     this.m30 = 0.0F;
/* 2226 */     this.m31 = 0.0F;
/* 2227 */     this.m32 = 0.0F;
/* 2228 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f m1, Vector3f t1, float scale) {
/* 2241 */     this.m00 = m1.m00 * scale;
/* 2242 */     this.m01 = m1.m01 * scale;
/* 2243 */     this.m02 = m1.m02 * scale;
/* 2244 */     this.m03 = t1.x;
/*      */     
/* 2246 */     this.m10 = m1.m10 * scale;
/* 2247 */     this.m11 = m1.m11 * scale;
/* 2248 */     this.m12 = m1.m12 * scale;
/* 2249 */     this.m13 = t1.y;
/*      */     
/* 2251 */     this.m20 = m1.m20 * scale;
/* 2252 */     this.m21 = m1.m21 * scale;
/* 2253 */     this.m22 = m1.m22 * scale;
/* 2254 */     this.m23 = t1.z;
/*      */     
/* 2256 */     this.m30 = 0.0F;
/* 2257 */     this.m31 = 0.0F;
/* 2258 */     this.m32 = 0.0F;
/* 2259 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1, Vector3d t1, double scale) {
/* 2272 */     this.m00 = (float)(m1.m00 * scale);
/* 2273 */     this.m01 = (float)(m1.m01 * scale);
/* 2274 */     this.m02 = (float)(m1.m02 * scale);
/* 2275 */     this.m03 = (float)t1.x;
/*      */     
/* 2277 */     this.m10 = (float)(m1.m10 * scale);
/* 2278 */     this.m11 = (float)(m1.m11 * scale);
/* 2279 */     this.m12 = (float)(m1.m12 * scale);
/* 2280 */     this.m13 = (float)t1.y;
/*      */     
/* 2282 */     this.m20 = (float)(m1.m20 * scale);
/* 2283 */     this.m21 = (float)(m1.m21 * scale);
/* 2284 */     this.m22 = (float)(m1.m22 * scale);
/* 2285 */     this.m23 = (float)t1.z;
/*      */     
/* 2287 */     this.m30 = 0.0F;
/* 2288 */     this.m31 = 0.0F;
/* 2289 */     this.m32 = 0.0F;
/* 2290 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3f trans) {
/* 2301 */     this.m03 = trans.x;
/* 2302 */     this.m13 = trans.y;
/* 2303 */     this.m23 = trans.z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotX(float angle) {
/* 2316 */     float sinAngle = (float)Math.sin(angle);
/* 2317 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2319 */     this.m00 = 1.0F;
/* 2320 */     this.m01 = 0.0F;
/* 2321 */     this.m02 = 0.0F;
/* 2322 */     this.m03 = 0.0F;
/*      */     
/* 2324 */     this.m10 = 0.0F;
/* 2325 */     this.m11 = cosAngle;
/* 2326 */     this.m12 = -sinAngle;
/* 2327 */     this.m13 = 0.0F;
/*      */     
/* 2329 */     this.m20 = 0.0F;
/* 2330 */     this.m21 = sinAngle;
/* 2331 */     this.m22 = cosAngle;
/* 2332 */     this.m23 = 0.0F;
/*      */     
/* 2334 */     this.m30 = 0.0F;
/* 2335 */     this.m31 = 0.0F;
/* 2336 */     this.m32 = 0.0F;
/* 2337 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotY(float angle) {
/* 2349 */     float sinAngle = (float)Math.sin(angle);
/* 2350 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2352 */     this.m00 = cosAngle;
/* 2353 */     this.m01 = 0.0F;
/* 2354 */     this.m02 = sinAngle;
/* 2355 */     this.m03 = 0.0F;
/*      */     
/* 2357 */     this.m10 = 0.0F;
/* 2358 */     this.m11 = 1.0F;
/* 2359 */     this.m12 = 0.0F;
/* 2360 */     this.m13 = 0.0F;
/*      */     
/* 2362 */     this.m20 = -sinAngle;
/* 2363 */     this.m21 = 0.0F;
/* 2364 */     this.m22 = cosAngle;
/* 2365 */     this.m23 = 0.0F;
/*      */     
/* 2367 */     this.m30 = 0.0F;
/* 2368 */     this.m31 = 0.0F;
/* 2369 */     this.m32 = 0.0F;
/* 2370 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotZ(float angle) {
/* 2382 */     float sinAngle = (float)Math.sin(angle);
/* 2383 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2385 */     this.m00 = cosAngle;
/* 2386 */     this.m01 = -sinAngle;
/* 2387 */     this.m02 = 0.0F;
/* 2388 */     this.m03 = 0.0F;
/*      */     
/* 2390 */     this.m10 = sinAngle;
/* 2391 */     this.m11 = cosAngle;
/* 2392 */     this.m12 = 0.0F;
/* 2393 */     this.m13 = 0.0F;
/*      */     
/* 2395 */     this.m20 = 0.0F;
/* 2396 */     this.m21 = 0.0F;
/* 2397 */     this.m22 = 1.0F;
/* 2398 */     this.m23 = 0.0F;
/*      */     
/* 2400 */     this.m30 = 0.0F;
/* 2401 */     this.m31 = 0.0F;
/* 2402 */     this.m32 = 0.0F;
/* 2403 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 2412 */     this.m00 *= scalar;
/* 2413 */     this.m01 *= scalar;
/* 2414 */     this.m02 *= scalar;
/* 2415 */     this.m03 *= scalar;
/* 2416 */     this.m10 *= scalar;
/* 2417 */     this.m11 *= scalar;
/* 2418 */     this.m12 *= scalar;
/* 2419 */     this.m13 *= scalar;
/* 2420 */     this.m20 *= scalar;
/* 2421 */     this.m21 *= scalar;
/* 2422 */     this.m22 *= scalar;
/* 2423 */     this.m23 *= scalar;
/* 2424 */     this.m30 *= scalar;
/* 2425 */     this.m31 *= scalar;
/* 2426 */     this.m32 *= scalar;
/* 2427 */     this.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar, Matrix4f m1) {
/* 2438 */     m1.m00 *= scalar;
/* 2439 */     m1.m01 *= scalar;
/* 2440 */     m1.m02 *= scalar;
/* 2441 */     m1.m03 *= scalar;
/* 2442 */     m1.m10 *= scalar;
/* 2443 */     m1.m11 *= scalar;
/* 2444 */     m1.m12 *= scalar;
/* 2445 */     m1.m13 *= scalar;
/* 2446 */     m1.m20 *= scalar;
/* 2447 */     m1.m21 *= scalar;
/* 2448 */     m1.m22 *= scalar;
/* 2449 */     m1.m23 *= scalar;
/* 2450 */     m1.m30 *= scalar;
/* 2451 */     m1.m31 *= scalar;
/* 2452 */     m1.m32 *= scalar;
/* 2453 */     m1.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4f m1) {
/* 2468 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + 
/* 2469 */       this.m02 * m1.m20 + this.m03 * m1.m30;
/* 2470 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + 
/* 2471 */       this.m02 * m1.m21 + this.m03 * m1.m31;
/* 2472 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + 
/* 2473 */       this.m02 * m1.m22 + this.m03 * m1.m32;
/* 2474 */     float m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + 
/* 2475 */       this.m02 * m1.m23 + this.m03 * m1.m33;
/*      */     
/* 2477 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + 
/* 2478 */       this.m12 * m1.m20 + this.m13 * m1.m30;
/* 2479 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + 
/* 2480 */       this.m12 * m1.m21 + this.m13 * m1.m31;
/* 2481 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + 
/* 2482 */       this.m12 * m1.m22 + this.m13 * m1.m32;
/* 2483 */     float m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + 
/* 2484 */       this.m12 * m1.m23 + this.m13 * m1.m33;
/*      */     
/* 2486 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + 
/* 2487 */       this.m22 * m1.m20 + this.m23 * m1.m30;
/* 2488 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + 
/* 2489 */       this.m22 * m1.m21 + this.m23 * m1.m31;
/* 2490 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + 
/* 2491 */       this.m22 * m1.m22 + this.m23 * m1.m32;
/* 2492 */     float m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + 
/* 2493 */       this.m22 * m1.m23 + this.m23 * m1.m33;
/*      */     
/* 2495 */     float m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + 
/* 2496 */       this.m32 * m1.m20 + this.m33 * m1.m30;
/* 2497 */     float m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + 
/* 2498 */       this.m32 * m1.m21 + this.m33 * m1.m31;
/* 2499 */     float m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + 
/* 2500 */       this.m32 * m1.m22 + this.m33 * m1.m32;
/* 2501 */     float m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + 
/* 2502 */       this.m32 * m1.m23 + this.m33 * m1.m33;
/*      */     
/* 2504 */     this.m00 = m00;
/* 2505 */     this.m01 = m01;
/* 2506 */     this.m02 = m02;
/* 2507 */     this.m03 = m03;
/* 2508 */     this.m10 = m10;
/* 2509 */     this.m11 = m11;
/* 2510 */     this.m12 = m12;
/* 2511 */     this.m13 = m13;
/* 2512 */     this.m20 = m20;
/* 2513 */     this.m21 = m21;
/* 2514 */     this.m22 = m22;
/* 2515 */     this.m23 = m23;
/* 2516 */     this.m30 = m30;
/* 2517 */     this.m31 = m31;
/* 2518 */     this.m32 = m32;
/* 2519 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4f m1, Matrix4f m2) {
/* 2530 */     if (this != m1 && this != m2) {
/*      */       
/* 2532 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + 
/* 2533 */         m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2534 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + 
/* 2535 */         m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2536 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + 
/* 2537 */         m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2538 */       this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + 
/* 2539 */         m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2541 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + 
/* 2542 */         m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2543 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + 
/* 2544 */         m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2545 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + 
/* 2546 */         m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2547 */       this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + 
/* 2548 */         m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2550 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + 
/* 2551 */         m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2552 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + 
/* 2553 */         m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2554 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + 
/* 2555 */         m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2556 */       this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + 
/* 2557 */         m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2559 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + 
/* 2560 */         m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2561 */       this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + 
/* 2562 */         m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2563 */       this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + 
/* 2564 */         m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2565 */       this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + 
/* 2566 */         m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2572 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2573 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2574 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2575 */       float m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2577 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2578 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2579 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2580 */       float m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2582 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2583 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2584 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2585 */       float m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2587 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2588 */       float m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2589 */       float m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2590 */       float m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2592 */       this.m00 = m00;
/* 2593 */       this.m01 = m01;
/* 2594 */       this.m02 = m02;
/* 2595 */       this.m03 = m03;
/* 2596 */       this.m10 = m10;
/* 2597 */       this.m11 = m11;
/* 2598 */       this.m12 = m12;
/* 2599 */       this.m13 = m13;
/* 2600 */       this.m20 = m20;
/* 2601 */       this.m21 = m21;
/* 2602 */       this.m22 = m22;
/* 2603 */       this.m23 = m23;
/* 2604 */       this.m30 = m30;
/* 2605 */       this.m31 = m31;
/* 2606 */       this.m32 = m32;
/* 2607 */       this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeBoth(Matrix4f m1, Matrix4f m2) {
/* 2619 */     if (this != m1 && this != m2) {
/* 2620 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2621 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2622 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2623 */       this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2625 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2626 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2627 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2628 */       this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2630 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2631 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2632 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2633 */       this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2635 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2636 */       this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2637 */       this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2638 */       this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2645 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2646 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2647 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2648 */       float m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2650 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2651 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2652 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2653 */       float m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2655 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2656 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2657 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2658 */       float m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2660 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2661 */       float m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2662 */       float m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2663 */       float m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2665 */       this.m00 = m00;
/* 2666 */       this.m01 = m01;
/* 2667 */       this.m02 = m02;
/* 2668 */       this.m03 = m03;
/* 2669 */       this.m10 = m10;
/* 2670 */       this.m11 = m11;
/* 2671 */       this.m12 = m12;
/* 2672 */       this.m13 = m13;
/* 2673 */       this.m20 = m20;
/* 2674 */       this.m21 = m21;
/* 2675 */       this.m22 = m22;
/* 2676 */       this.m23 = m23;
/* 2677 */       this.m30 = m30;
/* 2678 */       this.m31 = m31;
/* 2679 */       this.m32 = m32;
/* 2680 */       this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeRight(Matrix4f m1, Matrix4f m2) {
/* 2693 */     if (this != m1 && this != m2) {
/* 2694 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2695 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2696 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2697 */       this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2699 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2700 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2701 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2702 */       this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2704 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2705 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2706 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2707 */       this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2709 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2710 */       this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2711 */       this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2712 */       this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2719 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2720 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2721 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2722 */       float m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2724 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2725 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2726 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2727 */       float m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2729 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2730 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2731 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2732 */       float m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2734 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2735 */       float m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2736 */       float m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2737 */       float m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2739 */       this.m00 = m00;
/* 2740 */       this.m01 = m01;
/* 2741 */       this.m02 = m02;
/* 2742 */       this.m03 = m03;
/* 2743 */       this.m10 = m10;
/* 2744 */       this.m11 = m11;
/* 2745 */       this.m12 = m12;
/* 2746 */       this.m13 = m13;
/* 2747 */       this.m20 = m20;
/* 2748 */       this.m21 = m21;
/* 2749 */       this.m22 = m22;
/* 2750 */       this.m23 = m23;
/* 2751 */       this.m30 = m30;
/* 2752 */       this.m31 = m31;
/* 2753 */       this.m32 = m32;
/* 2754 */       this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeLeft(Matrix4f m1, Matrix4f m2) {
/* 2768 */     if (this != m1 && this != m2) {
/* 2769 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2770 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2771 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2772 */       this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2774 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2775 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2776 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2777 */       this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2779 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2780 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2781 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2782 */       this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2784 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2785 */       this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2786 */       this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2787 */       this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2795 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2796 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2797 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2798 */       float m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2800 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2801 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2802 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2803 */       float m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2805 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2806 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2807 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2808 */       float m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2810 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2811 */       float m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2812 */       float m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2813 */       float m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2815 */       this.m00 = m00;
/* 2816 */       this.m01 = m01;
/* 2817 */       this.m02 = m02;
/* 2818 */       this.m03 = m03;
/* 2819 */       this.m10 = m10;
/* 2820 */       this.m11 = m11;
/* 2821 */       this.m12 = m12;
/* 2822 */       this.m13 = m13;
/* 2823 */       this.m20 = m20;
/* 2824 */       this.m21 = m21;
/* 2825 */       this.m22 = m22;
/* 2826 */       this.m23 = m23;
/* 2827 */       this.m30 = m30;
/* 2828 */       this.m31 = m31;
/* 2829 */       this.m32 = m32;
/* 2830 */       this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Matrix4f m1) {
/*      */     try {
/* 2845 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && 
/* 2846 */         this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && 
/* 2847 */         this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && 
/* 2848 */         this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && 
/* 2849 */         this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && 
/* 2850 */         this.m33 == m1.m33);
/* 2851 */     } catch (NullPointerException e2) {
/* 2852 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object t1) {
/*      */     try {
/* 2868 */       Matrix4f m2 = (Matrix4f)t1;
/* 2869 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && 
/* 2870 */         this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && 
/* 2871 */         this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && 
/* 2872 */         this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && 
/* 2873 */         this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && 
/* 2874 */         this.m33 == m2.m33);
/* 2875 */     } catch (ClassCastException e1) {
/* 2876 */       return false;
/* 2877 */     } catch (NullPointerException e2) {
/* 2878 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix4f m1, float epsilon) {
/* 2894 */     boolean status = true;
/*      */     
/* 2896 */     if (Math.abs(this.m00 - m1.m00) > epsilon) status = false; 
/* 2897 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 2898 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false; 
/* 2899 */     if (Math.abs(this.m03 - m1.m03) > epsilon) status = false;
/*      */     
/* 2901 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 2902 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 2903 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false; 
/* 2904 */     if (Math.abs(this.m13 - m1.m13) > epsilon) status = false;
/*      */     
/* 2906 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 2907 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 2908 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false; 
/* 2909 */     if (Math.abs(this.m23 - m1.m23) > epsilon) status = false;
/*      */     
/* 2911 */     if (Math.abs(this.m30 - m1.m30) > epsilon) status = false; 
/* 2912 */     if (Math.abs(this.m31 - m1.m31) > epsilon) status = false; 
/* 2913 */     if (Math.abs(this.m32 - m1.m32) > epsilon) status = false; 
/* 2914 */     if (Math.abs(this.m33 - m1.m33) > epsilon) status = false;
/*      */     
/* 2916 */     return status;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2932 */     long bits = 1L;
/* 2933 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 2934 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 2935 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 2936 */     bits = VecMathUtil.hashFloatBits(bits, this.m03);
/* 2937 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 2938 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 2939 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 2940 */     bits = VecMathUtil.hashFloatBits(bits, this.m13);
/* 2941 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 2942 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 2943 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 2944 */     bits = VecMathUtil.hashFloatBits(bits, this.m23);
/* 2945 */     bits = VecMathUtil.hashFloatBits(bits, this.m30);
/* 2946 */     bits = VecMathUtil.hashFloatBits(bits, this.m31);
/* 2947 */     bits = VecMathUtil.hashFloatBits(bits, this.m32);
/* 2948 */     bits = VecMathUtil.hashFloatBits(bits, this.m33);
/* 2949 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple4f vec, Tuple4f vecOut) {
/* 2962 */     float x = this.m00 * vec.x + this.m01 * vec.y + 
/* 2963 */       this.m02 * vec.z + this.m03 * vec.w;
/* 2964 */     float y = this.m10 * vec.x + this.m11 * vec.y + 
/* 2965 */       this.m12 * vec.z + this.m13 * vec.w;
/* 2966 */     float z = this.m20 * vec.x + this.m21 * vec.y + 
/* 2967 */       this.m22 * vec.z + this.m23 * vec.w;
/* 2968 */     vecOut.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 2969 */       this.m32 * vec.z + this.m33 * vec.w;
/* 2970 */     vecOut.x = x;
/* 2971 */     vecOut.y = y;
/* 2972 */     vecOut.z = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple4f vec) {
/* 2985 */     float x = this.m00 * vec.x + this.m01 * vec.y + 
/* 2986 */       this.m02 * vec.z + this.m03 * vec.w;
/* 2987 */     float y = this.m10 * vec.x + this.m11 * vec.y + 
/* 2988 */       this.m12 * vec.z + this.m13 * vec.w;
/* 2989 */     float z = this.m20 * vec.x + this.m21 * vec.y + 
/* 2990 */       this.m22 * vec.z + this.m23 * vec.w;
/* 2991 */     vec.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 2992 */       this.m32 * vec.z + this.m33 * vec.w;
/* 2993 */     vec.x = x;
/* 2994 */     vec.y = y;
/* 2995 */     vec.z = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Point3f point, Point3f pointOut) {
/* 3008 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3009 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3010 */     pointOut.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3011 */     pointOut.x = x;
/* 3012 */     pointOut.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Point3f point) {
/* 3025 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3026 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3027 */     point.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3028 */     point.x = x;
/* 3029 */     point.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Vector3f normal, Vector3f normalOut) {
/* 3042 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3043 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3044 */     normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3045 */     normalOut.x = x;
/* 3046 */     normalOut.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Vector3f normal) {
/* 3059 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3060 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3061 */     normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3062 */     normal.x = x;
/* 3063 */     normal.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Matrix3d m1) {
/* 3079 */     double[] tmp_rot = new double[9];
/* 3080 */     double[] tmp_scale = new double[3];
/*      */     
/* 3082 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3084 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 3085 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 3086 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 3088 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 3089 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 3090 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 3092 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 3093 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 3094 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Matrix3f m1) {
/* 3110 */     double[] tmp_rot = new double[9];
/* 3111 */     double[] tmp_scale = new double[3];
/*      */     
/* 3113 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3115 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 3116 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 3117 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 3119 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 3120 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 3121 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 3123 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 3124 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 3125 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Quat4f q1) {
/* 3140 */     double[] tmp_rot = new double[9];
/* 3141 */     double[] tmp_scale = new double[3];
/* 3142 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3144 */     this.m00 = (float)((1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z) * tmp_scale[0]);
/* 3145 */     this.m10 = (float)((2.0F * (q1.x * q1.y + q1.w * q1.z)) * tmp_scale[0]);
/* 3146 */     this.m20 = (float)((2.0F * (q1.x * q1.z - q1.w * q1.y)) * tmp_scale[0]);
/*      */     
/* 3148 */     this.m01 = (float)((2.0F * (q1.x * q1.y - q1.w * q1.z)) * tmp_scale[1]);
/* 3149 */     this.m11 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z) * tmp_scale[1]);
/* 3150 */     this.m21 = (float)((2.0F * (q1.y * q1.z + q1.w * q1.x)) * tmp_scale[1]);
/*      */     
/* 3152 */     this.m02 = (float)((2.0F * (q1.x * q1.z + q1.w * q1.y)) * tmp_scale[2]);
/* 3153 */     this.m12 = (float)((2.0F * (q1.y * q1.z - q1.w * q1.x)) * tmp_scale[2]);
/* 3154 */     this.m22 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y) * tmp_scale[2]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Quat4d q1) {
/* 3171 */     double[] tmp_rot = new double[9];
/* 3172 */     double[] tmp_scale = new double[3];
/*      */     
/* 3174 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3176 */     this.m00 = (float)((1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z) * tmp_scale[0]);
/* 3177 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0]);
/* 3178 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0]);
/*      */     
/* 3180 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1]);
/* 3181 */     this.m11 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z) * tmp_scale[1]);
/* 3182 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1]);
/*      */     
/* 3184 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2]);
/* 3185 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2]);
/* 3186 */     this.m22 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y) * tmp_scale[2]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(AxisAngle4f a1) {
/* 3201 */     double[] tmp_rot = new double[9];
/* 3202 */     double[] tmp_scale = new double[3];
/*      */     
/* 3204 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3206 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 3207 */     if (mag < 1.0E-8D) {
/* 3208 */       this.m00 = 1.0F;
/* 3209 */       this.m01 = 0.0F;
/* 3210 */       this.m02 = 0.0F;
/*      */       
/* 3212 */       this.m10 = 0.0F;
/* 3213 */       this.m11 = 1.0F;
/* 3214 */       this.m12 = 0.0F;
/*      */       
/* 3216 */       this.m20 = 0.0F;
/* 3217 */       this.m21 = 0.0F;
/* 3218 */       this.m22 = 1.0F;
/*      */     } else {
/* 3220 */       mag = 1.0D / mag;
/* 3221 */       double ax = a1.x * mag;
/* 3222 */       double ay = a1.y * mag;
/* 3223 */       double az = a1.z * mag;
/*      */       
/* 3225 */       double sinTheta = Math.sin(a1.angle);
/* 3226 */       double cosTheta = Math.cos(a1.angle);
/* 3227 */       double t = 1.0D - cosTheta;
/*      */       
/* 3229 */       double xz = (a1.x * a1.z);
/* 3230 */       double xy = (a1.x * a1.y);
/* 3231 */       double yz = (a1.y * a1.z);
/*      */       
/* 3233 */       this.m00 = (float)((t * ax * ax + cosTheta) * tmp_scale[0]);
/* 3234 */       this.m01 = (float)((t * xy - sinTheta * az) * tmp_scale[1]);
/* 3235 */       this.m02 = (float)((t * xz + sinTheta * ay) * tmp_scale[2]);
/*      */       
/* 3237 */       this.m10 = (float)((t * xy + sinTheta * az) * tmp_scale[0]);
/* 3238 */       this.m11 = (float)((t * ay * ay + cosTheta) * tmp_scale[1]);
/* 3239 */       this.m12 = (float)((t * yz - sinTheta * ax) * tmp_scale[2]);
/*      */       
/* 3241 */       this.m20 = (float)((t * xz - sinTheta * ay) * tmp_scale[0]);
/* 3242 */       this.m21 = (float)((t * yz + sinTheta * ax) * tmp_scale[1]);
/* 3243 */       this.m22 = (float)((t * az * az + cosTheta) * tmp_scale[2]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3253 */     this.m00 = 0.0F;
/* 3254 */     this.m01 = 0.0F;
/* 3255 */     this.m02 = 0.0F;
/* 3256 */     this.m03 = 0.0F;
/* 3257 */     this.m10 = 0.0F;
/* 3258 */     this.m11 = 0.0F;
/* 3259 */     this.m12 = 0.0F;
/* 3260 */     this.m13 = 0.0F;
/* 3261 */     this.m20 = 0.0F;
/* 3262 */     this.m21 = 0.0F;
/* 3263 */     this.m22 = 0.0F;
/* 3264 */     this.m23 = 0.0F;
/* 3265 */     this.m30 = 0.0F;
/* 3266 */     this.m31 = 0.0F;
/* 3267 */     this.m32 = 0.0F;
/* 3268 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3275 */     this.m00 = -this.m00;
/* 3276 */     this.m01 = -this.m01;
/* 3277 */     this.m02 = -this.m02;
/* 3278 */     this.m03 = -this.m03;
/* 3279 */     this.m10 = -this.m10;
/* 3280 */     this.m11 = -this.m11;
/* 3281 */     this.m12 = -this.m12;
/* 3282 */     this.m13 = -this.m13;
/* 3283 */     this.m20 = -this.m20;
/* 3284 */     this.m21 = -this.m21;
/* 3285 */     this.m22 = -this.m22;
/* 3286 */     this.m23 = -this.m23;
/* 3287 */     this.m30 = -this.m30;
/* 3288 */     this.m31 = -this.m31;
/* 3289 */     this.m32 = -this.m32;
/* 3290 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4f m1) {
/* 3300 */     this.m00 = -m1.m00;
/* 3301 */     this.m01 = -m1.m01;
/* 3302 */     this.m02 = -m1.m02;
/* 3303 */     this.m03 = -m1.m03;
/* 3304 */     this.m10 = -m1.m10;
/* 3305 */     this.m11 = -m1.m11;
/* 3306 */     this.m12 = -m1.m12;
/* 3307 */     this.m13 = -m1.m13;
/* 3308 */     this.m20 = -m1.m20;
/* 3309 */     this.m21 = -m1.m21;
/* 3310 */     this.m22 = -m1.m22;
/* 3311 */     this.m23 = -m1.m23;
/* 3312 */     this.m30 = -m1.m30;
/* 3313 */     this.m31 = -m1.m31;
/* 3314 */     this.m32 = -m1.m32;
/* 3315 */     this.m33 = -m1.m33;
/*      */   }
/*      */ 
/*      */   
/*      */   private final void getScaleRotate(double[] scales, double[] rots) {
/* 3320 */     double[] tmp = new double[9];
/* 3321 */     tmp[0] = this.m00;
/* 3322 */     tmp[1] = this.m01;
/* 3323 */     tmp[2] = this.m02;
/*      */     
/* 3325 */     tmp[3] = this.m10;
/* 3326 */     tmp[4] = this.m11;
/* 3327 */     tmp[5] = this.m12;
/*      */     
/* 3329 */     tmp[6] = this.m20;
/* 3330 */     tmp[7] = this.m21;
/* 3331 */     tmp[8] = this.m22;
/*      */     
/* 3333 */     Matrix3d.compute_svd(tmp, scales, rots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 3348 */     Matrix4f m1 = null;
/*      */     try {
/* 3350 */       m1 = (Matrix4f)super.clone();
/* 3351 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3353 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3356 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM00() {
/* 3366 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(float m00) {
/* 3376 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM01() {
/* 3386 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(float m01) {
/* 3396 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM02() {
/* 3406 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(float m02) {
/* 3416 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM10() {
/* 3426 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(float m10) {
/* 3436 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM11() {
/* 3446 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(float m11) {
/* 3456 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM12() {
/* 3466 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float m12) {
/* 3476 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM20() {
/* 3486 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(float m20) {
/* 3496 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM21() {
/* 3506 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(float m21) {
/* 3516 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM22() {
/* 3526 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(float m22) {
/* 3536 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM03() {
/* 3546 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(float m03) {
/* 3556 */     this.m03 = m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM13() {
/* 3566 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(float m13) {
/* 3576 */     this.m13 = m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM23() {
/* 3586 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(float m23) {
/* 3596 */     this.m23 = m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM30() {
/* 3606 */     return this.m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM30(float m30) {
/* 3616 */     this.m30 = m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM31() {
/* 3626 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(float m31) {
/* 3636 */     this.m31 = m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM32() {
/* 3646 */     return this.m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM32(float m32) {
/* 3656 */     this.m32 = m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM33() {
/* 3666 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(float m33) {
/* 3676 */     this.m33 = m33;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Matrix4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */