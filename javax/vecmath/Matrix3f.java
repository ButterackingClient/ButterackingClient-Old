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
/*      */ public class Matrix3f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 329697160112089834L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix3f(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
/*  107 */     this.m00 = m00;
/*  108 */     this.m01 = m01;
/*  109 */     this.m02 = m02;
/*      */     
/*  111 */     this.m10 = m10;
/*  112 */     this.m11 = m11;
/*  113 */     this.m12 = m12;
/*      */     
/*  115 */     this.m20 = m20;
/*  116 */     this.m21 = m21;
/*  117 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(float[] v) {
/*  128 */     this.m00 = v[0];
/*  129 */     this.m01 = v[1];
/*  130 */     this.m02 = v[2];
/*      */     
/*  132 */     this.m10 = v[3];
/*  133 */     this.m11 = v[4];
/*  134 */     this.m12 = v[5];
/*      */     
/*  136 */     this.m20 = v[6];
/*  137 */     this.m21 = v[7];
/*  138 */     this.m22 = v[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(Matrix3d m1) {
/*  149 */     this.m00 = (float)m1.m00;
/*  150 */     this.m01 = (float)m1.m01;
/*  151 */     this.m02 = (float)m1.m02;
/*      */     
/*  153 */     this.m10 = (float)m1.m10;
/*  154 */     this.m11 = (float)m1.m11;
/*  155 */     this.m12 = (float)m1.m12;
/*      */     
/*  157 */     this.m20 = (float)m1.m20;
/*  158 */     this.m21 = (float)m1.m21;
/*  159 */     this.m22 = (float)m1.m22;
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
/*      */   public Matrix3f(Matrix3f m1) {
/*  171 */     this.m00 = m1.m00;
/*  172 */     this.m01 = m1.m01;
/*  173 */     this.m02 = m1.m02;
/*      */     
/*  175 */     this.m10 = m1.m10;
/*  176 */     this.m11 = m1.m11;
/*  177 */     this.m12 = m1.m12;
/*      */     
/*  179 */     this.m20 = m1.m20;
/*  180 */     this.m21 = m1.m21;
/*  181 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f() {
/*  190 */     this.m00 = 0.0F;
/*  191 */     this.m01 = 0.0F;
/*  192 */     this.m02 = 0.0F;
/*      */     
/*  194 */     this.m10 = 0.0F;
/*  195 */     this.m11 = 0.0F;
/*  196 */     this.m12 = 0.0F;
/*      */     
/*  198 */     this.m20 = 0.0F;
/*  199 */     this.m21 = 0.0F;
/*  200 */     this.m22 = 0.0F;
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
/*  211 */     return 
/*  212 */       String.valueOf(this.m00) + ", " + this.m01 + ", " + this.m02 + "\n" + 
/*  213 */       this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + 
/*  214 */       this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  221 */     this.m00 = 1.0F;
/*  222 */     this.m01 = 0.0F;
/*  223 */     this.m02 = 0.0F;
/*      */     
/*  225 */     this.m10 = 0.0F;
/*  226 */     this.m11 = 1.0F;
/*  227 */     this.m12 = 0.0F;
/*      */     
/*  229 */     this.m20 = 0.0F;
/*  230 */     this.m21 = 0.0F;
/*  231 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(float scale) {
/*  242 */     double[] tmp_rot = new double[9];
/*  243 */     double[] tmp_scale = new double[3];
/*      */     
/*  245 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  247 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  248 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  249 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  251 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  252 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  253 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  255 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  256 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  257 */     this.m22 = (float)(tmp_rot[8] * scale);
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
/*      */   public final void setElement(int row, int column, float value) {
/*  269 */     switch (row) {
/*      */       case 0:
/*  271 */         switch (column) {
/*      */           case 0:
/*  273 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  276 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  279 */             this.m02 = value;
/*      */             return;
/*      */         } 
/*  282 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  287 */         switch (column) {
/*      */           case 0:
/*  289 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  292 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  295 */             this.m12 = value;
/*      */             return;
/*      */         } 
/*  298 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  303 */         switch (column) {
/*      */           case 0:
/*  305 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  308 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  311 */             this.m22 = value;
/*      */             return;
/*      */         } 
/*      */         
/*  315 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  320 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector3f v) {
/*  331 */     if (row == 0) {
/*  332 */       v.x = this.m00;
/*  333 */       v.y = this.m01;
/*  334 */       v.z = this.m02;
/*  335 */     } else if (row == 1) {
/*  336 */       v.x = this.m10;
/*  337 */       v.y = this.m11;
/*  338 */       v.z = this.m12;
/*  339 */     } else if (row == 2) {
/*  340 */       v.x = this.m20;
/*  341 */       v.y = this.m21;
/*  342 */       v.z = this.m22;
/*      */     } else {
/*  344 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
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
/*  356 */     if (row == 0) {
/*  357 */       v[0] = this.m00;
/*  358 */       v[1] = this.m01;
/*  359 */       v[2] = this.m02;
/*  360 */     } else if (row == 1) {
/*  361 */       v[0] = this.m10;
/*  362 */       v[1] = this.m11;
/*  363 */       v[2] = this.m12;
/*  364 */     } else if (row == 2) {
/*  365 */       v[0] = this.m20;
/*  366 */       v[1] = this.m21;
/*  367 */       v[2] = this.m22;
/*      */     } else {
/*  369 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
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
/*      */   public final void getColumn(int column, Vector3f v) {
/*  382 */     if (column == 0) {
/*  383 */       v.x = this.m00;
/*  384 */       v.y = this.m10;
/*  385 */       v.z = this.m20;
/*  386 */     } else if (column == 1) {
/*  387 */       v.x = this.m01;
/*  388 */       v.y = this.m11;
/*  389 */       v.z = this.m21;
/*  390 */     } else if (column == 2) {
/*  391 */       v.x = this.m02;
/*  392 */       v.y = this.m12;
/*  393 */       v.z = this.m22;
/*      */     } else {
/*  395 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
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
/*  408 */     if (column == 0) {
/*  409 */       v[0] = this.m00;
/*  410 */       v[1] = this.m10;
/*  411 */       v[2] = this.m20;
/*  412 */     } else if (column == 1) {
/*  413 */       v[0] = this.m01;
/*  414 */       v[1] = this.m11;
/*  415 */       v[2] = this.m21;
/*  416 */     } else if (column == 2) {
/*  417 */       v[0] = this.m02;
/*  418 */       v[1] = this.m12;
/*  419 */       v[2] = this.m22;
/*      */     } else {
/*  421 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
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
/*      */   public final float getElement(int row, int column) {
/*  434 */     switch (row) {
/*      */       case 0:
/*  436 */         switch (column) {
/*      */           case 0:
/*  438 */             return this.m00;
/*      */           case 1:
/*  440 */             return this.m01;
/*      */           case 2:
/*  442 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  448 */         switch (column) {
/*      */           case 0:
/*  450 */             return this.m10;
/*      */           case 1:
/*  452 */             return this.m11;
/*      */           case 2:
/*  454 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  461 */         switch (column) {
/*      */           case 0:
/*  463 */             return this.m20;
/*      */           case 1:
/*  465 */             return this.m21;
/*      */           case 2:
/*  467 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  476 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));
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
/*      */   public final void setRow(int row, float x, float y, float z) {
/*  488 */     switch (row) {
/*      */       case 0:
/*  490 */         this.m00 = x;
/*  491 */         this.m01 = y;
/*  492 */         this.m02 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  496 */         this.m10 = x;
/*  497 */         this.m11 = y;
/*  498 */         this.m12 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  502 */         this.m20 = x;
/*  503 */         this.m21 = y;
/*  504 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  508 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector3f v) {
/*  519 */     switch (row) {
/*      */       case 0:
/*  521 */         this.m00 = v.x;
/*  522 */         this.m01 = v.y;
/*  523 */         this.m02 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  527 */         this.m10 = v.x;
/*  528 */         this.m11 = v.y;
/*  529 */         this.m12 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  533 */         this.m20 = v.x;
/*  534 */         this.m21 = v.y;
/*  535 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  539 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, float[] v) {
/*  550 */     switch (row) {
/*      */       case 0:
/*  552 */         this.m00 = v[0];
/*  553 */         this.m01 = v[1];
/*  554 */         this.m02 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  558 */         this.m10 = v[0];
/*  559 */         this.m11 = v[1];
/*  560 */         this.m12 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  564 */         this.m20 = v[0];
/*  565 */         this.m21 = v[1];
/*  566 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  570 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
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
/*      */   public final void setColumn(int column, float x, float y, float z) {
/*  583 */     switch (column) {
/*      */       case 0:
/*  585 */         this.m00 = x;
/*  586 */         this.m10 = y;
/*  587 */         this.m20 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  591 */         this.m01 = x;
/*  592 */         this.m11 = y;
/*  593 */         this.m21 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  597 */         this.m02 = x;
/*  598 */         this.m12 = y;
/*  599 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  603 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector3f v) {
/*  614 */     switch (column) {
/*      */       case 0:
/*  616 */         this.m00 = v.x;
/*  617 */         this.m10 = v.y;
/*  618 */         this.m20 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  622 */         this.m01 = v.x;
/*  623 */         this.m11 = v.y;
/*  624 */         this.m21 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  628 */         this.m02 = v.x;
/*  629 */         this.m12 = v.y;
/*  630 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  634 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
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
/*  645 */     switch (column) {
/*      */       case 0:
/*  647 */         this.m00 = v[0];
/*  648 */         this.m10 = v[1];
/*  649 */         this.m20 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  653 */         this.m01 = v[0];
/*  654 */         this.m11 = v[1];
/*  655 */         this.m21 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  659 */         this.m02 = v[0];
/*  660 */         this.m12 = v[1];
/*  661 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  665 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
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
/*      */   public final float getScale() {
/*  679 */     double[] tmp_rot = new double[9];
/*  680 */     double[] tmp_scale = new double[3];
/*  681 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  683 */     return (float)Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/*  693 */     this.m00 += scalar;
/*  694 */     this.m01 += scalar;
/*  695 */     this.m02 += scalar;
/*  696 */     this.m10 += scalar;
/*  697 */     this.m11 += scalar;
/*  698 */     this.m12 += scalar;
/*  699 */     this.m20 += scalar;
/*  700 */     this.m21 += scalar;
/*  701 */     this.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar, Matrix3f m1) {
/*  712 */     m1.m00 += scalar;
/*  713 */     m1.m01 += scalar;
/*  714 */     m1.m02 += scalar;
/*  715 */     m1.m10 += scalar;
/*  716 */     m1.m11 += scalar;
/*  717 */     m1.m12 += scalar;
/*  718 */     m1.m20 += scalar;
/*  719 */     m1.m21 += scalar;
/*  720 */     m1.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1, Matrix3f m2) {
/*  730 */     m1.m00 += m2.m00;
/*  731 */     m1.m01 += m2.m01;
/*  732 */     m1.m02 += m2.m02;
/*      */     
/*  734 */     m1.m10 += m2.m10;
/*  735 */     m1.m11 += m2.m11;
/*  736 */     m1.m12 += m2.m12;
/*      */     
/*  738 */     m1.m20 += m2.m20;
/*  739 */     m1.m21 += m2.m21;
/*  740 */     m1.m22 += m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1) {
/*  750 */     this.m00 += m1.m00;
/*  751 */     this.m01 += m1.m01;
/*  752 */     this.m02 += m1.m02;
/*      */     
/*  754 */     this.m10 += m1.m10;
/*  755 */     this.m11 += m1.m11;
/*  756 */     this.m12 += m1.m12;
/*      */     
/*  758 */     this.m20 += m1.m20;
/*  759 */     this.m21 += m1.m21;
/*  760 */     this.m22 += m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f m1, Matrix3f m2) {
/*  771 */     m1.m00 -= m2.m00;
/*  772 */     m1.m01 -= m2.m01;
/*  773 */     m1.m02 -= m2.m02;
/*      */     
/*  775 */     m1.m10 -= m2.m10;
/*  776 */     m1.m11 -= m2.m11;
/*  777 */     m1.m12 -= m2.m12;
/*      */     
/*  779 */     m1.m20 -= m2.m20;
/*  780 */     m1.m21 -= m2.m21;
/*  781 */     m1.m22 -= m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f m1) {
/*  791 */     this.m00 -= m1.m00;
/*  792 */     this.m01 -= m1.m01;
/*  793 */     this.m02 -= m1.m02;
/*      */     
/*  795 */     this.m10 -= m1.m10;
/*  796 */     this.m11 -= m1.m11;
/*  797 */     this.m12 -= m1.m12;
/*      */     
/*  799 */     this.m20 -= m1.m20;
/*  800 */     this.m21 -= m1.m21;
/*  801 */     this.m22 -= m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/*  810 */     float temp = this.m10;
/*  811 */     this.m10 = this.m01;
/*  812 */     this.m01 = temp;
/*      */     
/*  814 */     temp = this.m20;
/*  815 */     this.m20 = this.m02;
/*  816 */     this.m02 = temp;
/*      */     
/*  818 */     temp = this.m21;
/*  819 */     this.m21 = this.m12;
/*  820 */     this.m12 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix3f m1) {
/*  829 */     if (this != m1) {
/*  830 */       this.m00 = m1.m00;
/*  831 */       this.m01 = m1.m10;
/*  832 */       this.m02 = m1.m20;
/*      */       
/*  834 */       this.m10 = m1.m01;
/*  835 */       this.m11 = m1.m11;
/*  836 */       this.m12 = m1.m21;
/*      */       
/*  838 */       this.m20 = m1.m02;
/*  839 */       this.m21 = m1.m12;
/*  840 */       this.m22 = m1.m22;
/*      */     } else {
/*  842 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/*  852 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/*  853 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/*  854 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  856 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/*  857 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/*  858 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  860 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/*  861 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/*  862 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/*  872 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*  873 */     if (mag < 1.0E-8D) {
/*  874 */       this.m00 = 1.0F;
/*  875 */       this.m01 = 0.0F;
/*  876 */       this.m02 = 0.0F;
/*      */       
/*  878 */       this.m10 = 0.0F;
/*  879 */       this.m11 = 1.0F;
/*  880 */       this.m12 = 0.0F;
/*      */       
/*  882 */       this.m20 = 0.0F;
/*  883 */       this.m21 = 0.0F;
/*  884 */       this.m22 = 1.0F;
/*      */     } else {
/*  886 */       mag = 1.0F / mag;
/*  887 */       float ax = a1.x * mag;
/*  888 */       float ay = a1.y * mag;
/*  889 */       float az = a1.z * mag;
/*      */       
/*  891 */       float sinTheta = (float)Math.sin(a1.angle);
/*  892 */       float cosTheta = (float)Math.cos(a1.angle);
/*  893 */       float t = 1.0F - cosTheta;
/*      */       
/*  895 */       float xz = ax * az;
/*  896 */       float xy = ax * ay;
/*  897 */       float yz = ay * az;
/*      */       
/*  899 */       this.m00 = t * ax * ax + cosTheta;
/*  900 */       this.m01 = t * xy - sinTheta * az;
/*  901 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  903 */       this.m10 = t * xy + sinTheta * az;
/*  904 */       this.m11 = t * ay * ay + cosTheta;
/*  905 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  907 */       this.m20 = t * xz - sinTheta * ay;
/*  908 */       this.m21 = t * yz + sinTheta * ax;
/*  909 */       this.m22 = t * az * az + cosTheta;
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
/*      */   public final void set(AxisAngle4d a1) {
/*  921 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*  922 */     if (mag < 1.0E-8D) {
/*  923 */       this.m00 = 1.0F;
/*  924 */       this.m01 = 0.0F;
/*  925 */       this.m02 = 0.0F;
/*      */       
/*  927 */       this.m10 = 0.0F;
/*  928 */       this.m11 = 1.0F;
/*  929 */       this.m12 = 0.0F;
/*      */       
/*  931 */       this.m20 = 0.0F;
/*  932 */       this.m21 = 0.0F;
/*  933 */       this.m22 = 1.0F;
/*      */     } else {
/*  935 */       mag = 1.0D / mag;
/*  936 */       double ax = a1.x * mag;
/*  937 */       double ay = a1.y * mag;
/*  938 */       double az = a1.z * mag;
/*      */       
/*  940 */       double sinTheta = Math.sin(a1.angle);
/*  941 */       double cosTheta = Math.cos(a1.angle);
/*  942 */       double t = 1.0D - cosTheta;
/*      */       
/*  944 */       double xz = ax * az;
/*  945 */       double xy = ax * ay;
/*  946 */       double yz = ay * az;
/*      */       
/*  948 */       this.m00 = (float)(t * ax * ax + cosTheta);
/*  949 */       this.m01 = (float)(t * xy - sinTheta * az);
/*  950 */       this.m02 = (float)(t * xz + sinTheta * ay);
/*      */       
/*  952 */       this.m10 = (float)(t * xy + sinTheta * az);
/*  953 */       this.m11 = (float)(t * ay * ay + cosTheta);
/*  954 */       this.m12 = (float)(t * yz - sinTheta * ax);
/*      */       
/*  956 */       this.m20 = (float)(t * xz - sinTheta * ay);
/*  957 */       this.m21 = (float)(t * yz + sinTheta * ax);
/*  958 */       this.m22 = (float)(t * az * az + cosTheta);
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
/*      */   public final void set(Quat4d q1) {
/*  970 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  971 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  972 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  974 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  975 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  976 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  978 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  979 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  980 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
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
/*  991 */     this.m00 = m[0];
/*  992 */     this.m01 = m[1];
/*  993 */     this.m02 = m[2];
/*      */     
/*  995 */     this.m10 = m[3];
/*  996 */     this.m11 = m[4];
/*  997 */     this.m12 = m[5];
/*      */     
/*  999 */     this.m20 = m[6];
/* 1000 */     this.m21 = m[7];
/* 1001 */     this.m22 = m[8];
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
/*      */   public final void set(Matrix3f m1) {
/* 1014 */     this.m00 = m1.m00;
/* 1015 */     this.m01 = m1.m01;
/* 1016 */     this.m02 = m1.m02;
/*      */     
/* 1018 */     this.m10 = m1.m10;
/* 1019 */     this.m11 = m1.m11;
/* 1020 */     this.m12 = m1.m12;
/*      */     
/* 1022 */     this.m20 = m1.m20;
/* 1023 */     this.m21 = m1.m21;
/* 1024 */     this.m22 = m1.m22;
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
/*      */   public final void set(Matrix3d m1) {
/* 1037 */     this.m00 = (float)m1.m00;
/* 1038 */     this.m01 = (float)m1.m01;
/* 1039 */     this.m02 = (float)m1.m02;
/*      */     
/* 1041 */     this.m10 = (float)m1.m10;
/* 1042 */     this.m11 = (float)m1.m11;
/* 1043 */     this.m12 = (float)m1.m12;
/*      */     
/* 1045 */     this.m20 = (float)m1.m20;
/* 1046 */     this.m21 = (float)m1.m21;
/* 1047 */     this.m22 = (float)m1.m22;
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
/*      */   public final void invert(Matrix3f m1) {
/* 1059 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1066 */     invertGeneral(this);
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
/*      */   private final void invertGeneral(Matrix3f m1) {
/* 1078 */     double[] temp = new double[9];
/* 1079 */     double[] result = new double[9];
/* 1080 */     int[] row_perm = new int[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1087 */     temp[0] = m1.m00;
/* 1088 */     temp[1] = m1.m01;
/* 1089 */     temp[2] = m1.m02;
/*      */     
/* 1091 */     temp[3] = m1.m10;
/* 1092 */     temp[4] = m1.m11;
/* 1093 */     temp[5] = m1.m12;
/*      */     
/* 1095 */     temp[6] = m1.m20;
/* 1096 */     temp[7] = m1.m21;
/* 1097 */     temp[8] = m1.m22;
/*      */ 
/*      */ 
/*      */     
/* 1101 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1103 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));
/*      */     }
/*      */ 
/*      */     
/* 1107 */     for (int i = 0; i < 9; ) { result[i] = 0.0D; i++; }
/* 1108 */      result[0] = 1.0D;
/* 1109 */     result[4] = 1.0D;
/* 1110 */     result[8] = 1.0D;
/* 1111 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1113 */     this.m00 = (float)result[0];
/* 1114 */     this.m01 = (float)result[1];
/* 1115 */     this.m02 = (float)result[2];
/*      */     
/* 1117 */     this.m10 = (float)result[3];
/* 1118 */     this.m11 = (float)result[4];
/* 1119 */     this.m12 = (float)result[5];
/*      */     
/* 1121 */     this.m20 = (float)result[6];
/* 1122 */     this.m21 = (float)result[7];
/* 1123 */     this.m22 = (float)result[8];
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
/* 1150 */     double[] row_scale = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1158 */     int ptr = 0;
/* 1159 */     int rs = 0;
/*      */ 
/*      */     
/* 1162 */     int i = 3;
/* 1163 */     while (i-- != 0) {
/* 1164 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1167 */       int k = 3;
/* 1168 */       while (k-- != 0) {
/* 1169 */         double temp = matrix0[ptr++];
/* 1170 */         temp = Math.abs(temp);
/* 1171 */         if (temp > big) {
/* 1172 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1177 */       if (big == 0.0D) {
/* 1178 */         return false;
/*      */       }
/* 1180 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1188 */     int mtx = 0;
/*      */ 
/*      */     
/* 1191 */     for (int j = 0; j < 3; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1197 */       for (k = 0; k < j; k++) {
/* 1198 */         int target = mtx + 3 * k + j;
/* 1199 */         double sum = matrix0[target];
/* 1200 */         int m = k;
/* 1201 */         int p1 = mtx + 3 * k;
/* 1202 */         int p2 = mtx + j;
/* 1203 */         while (m-- != 0) {
/* 1204 */           sum -= matrix0[p1] * matrix0[p2];
/* 1205 */           p1++;
/* 1206 */           p2 += 3;
/*      */         } 
/* 1208 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1213 */       double big = 0.0D;
/* 1214 */       int imax = -1;
/* 1215 */       for (k = j; k < 3; k++) {
/* 1216 */         int target = mtx + 3 * k + j;
/* 1217 */         double sum = matrix0[target];
/* 1218 */         int m = j;
/* 1219 */         int p1 = mtx + 3 * k;
/* 1220 */         int p2 = mtx + j;
/* 1221 */         while (m-- != 0) {
/* 1222 */           sum -= matrix0[p1] * matrix0[p2];
/* 1223 */           p1++;
/* 1224 */           p2 += 3;
/*      */         } 
/* 1226 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1229 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1230 */           big = temp;
/* 1231 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1235 */       if (imax < 0) {
/* 1236 */         throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));
/*      */       }
/*      */ 
/*      */       
/* 1240 */       if (j != imax) {
/*      */         
/* 1242 */         int m = 3;
/* 1243 */         int p1 = mtx + 3 * imax;
/* 1244 */         int p2 = mtx + 3 * j;
/* 1245 */         while (m-- != 0) {
/* 1246 */           double temp = matrix0[p1];
/* 1247 */           matrix0[p1++] = matrix0[p2];
/* 1248 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1252 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1256 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1259 */       if (matrix0[mtx + 3 * j + j] == 0.0D) {
/* 1260 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1264 */       if (j != 2) {
/* 1265 */         double temp = 1.0D / matrix0[mtx + 3 * j + j];
/* 1266 */         int target = mtx + 3 * (j + 1) + j;
/* 1267 */         k = 2 - j;
/* 1268 */         while (k-- != 0) {
/* 1269 */           matrix0[target] = matrix0[target] * temp;
/* 1270 */           target += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1276 */     return true;
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
/* 1306 */     int rp = 0;
/*      */ 
/*      */     
/* 1309 */     for (int k = 0; k < 3; k++) {
/*      */       
/* 1311 */       int cv = k;
/* 1312 */       int ii = -1;
/*      */ 
/*      */       
/* 1315 */       for (int i = 0; i < 3; i++) {
/*      */ 
/*      */         
/* 1318 */         int ip = row_perm[rp + i];
/* 1319 */         double sum = matrix2[cv + 3 * ip];
/* 1320 */         matrix2[cv + 3 * ip] = matrix2[cv + 3 * i];
/* 1321 */         if (ii >= 0) {
/*      */           
/* 1323 */           int m = i * 3;
/* 1324 */           for (int j = ii; j <= i - 1; j++) {
/* 1325 */             sum -= matrix1[m + j] * matrix2[cv + 3 * j];
/*      */           }
/* 1327 */         } else if (sum != 0.0D) {
/* 1328 */           ii = i;
/*      */         } 
/* 1330 */         matrix2[cv + 3 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1335 */       int rv = 6;
/* 1336 */       matrix2[cv + 6] = matrix2[cv + 6] / matrix1[rv + 2];
/*      */       
/* 1338 */       rv -= 3;
/* 1339 */       matrix2[cv + 3] = (matrix2[cv + 3] - 
/* 1340 */         matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 1];
/*      */       
/* 1342 */       rv -= 3;
/* 1343 */       matrix2[cv + 0] = (matrix2[cv + 0] - 
/* 1344 */         matrix1[rv + 1] * matrix2[cv + 3] - 
/* 1345 */         matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 0];
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
/*      */   public final float determinant() {
/* 1357 */     float total = this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + 
/* 1358 */       this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + 
/* 1359 */       this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
/* 1360 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 1370 */     this.m00 = scale;
/* 1371 */     this.m01 = 0.0F;
/* 1372 */     this.m02 = 0.0F;
/*      */     
/* 1374 */     this.m10 = 0.0F;
/* 1375 */     this.m11 = scale;
/* 1376 */     this.m12 = 0.0F;
/*      */     
/* 1378 */     this.m20 = 0.0F;
/* 1379 */     this.m21 = 0.0F;
/* 1380 */     this.m22 = scale;
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
/*      */   public final void rotX(float angle) {
/* 1392 */     float sinAngle = (float)Math.sin(angle);
/* 1393 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1395 */     this.m00 = 1.0F;
/* 1396 */     this.m01 = 0.0F;
/* 1397 */     this.m02 = 0.0F;
/*      */     
/* 1399 */     this.m10 = 0.0F;
/* 1400 */     this.m11 = cosAngle;
/* 1401 */     this.m12 = -sinAngle;
/*      */     
/* 1403 */     this.m20 = 0.0F;
/* 1404 */     this.m21 = sinAngle;
/* 1405 */     this.m22 = cosAngle;
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
/* 1417 */     float sinAngle = (float)Math.sin(angle);
/* 1418 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1420 */     this.m00 = cosAngle;
/* 1421 */     this.m01 = 0.0F;
/* 1422 */     this.m02 = sinAngle;
/*      */     
/* 1424 */     this.m10 = 0.0F;
/* 1425 */     this.m11 = 1.0F;
/* 1426 */     this.m12 = 0.0F;
/*      */     
/* 1428 */     this.m20 = -sinAngle;
/* 1429 */     this.m21 = 0.0F;
/* 1430 */     this.m22 = cosAngle;
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
/* 1442 */     float sinAngle = (float)Math.sin(angle);
/* 1443 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1445 */     this.m00 = cosAngle;
/* 1446 */     this.m01 = -sinAngle;
/* 1447 */     this.m02 = 0.0F;
/*      */     
/* 1449 */     this.m10 = sinAngle;
/* 1450 */     this.m11 = cosAngle;
/* 1451 */     this.m12 = 0.0F;
/*      */     
/* 1453 */     this.m20 = 0.0F;
/* 1454 */     this.m21 = 0.0F;
/* 1455 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 1464 */     this.m00 *= scalar;
/* 1465 */     this.m01 *= scalar;
/* 1466 */     this.m02 *= scalar;
/*      */     
/* 1468 */     this.m10 *= scalar;
/* 1469 */     this.m11 *= scalar;
/* 1470 */     this.m12 *= scalar;
/*      */     
/* 1472 */     this.m20 *= scalar;
/* 1473 */     this.m21 *= scalar;
/* 1474 */     this.m22 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar, Matrix3f m1) {
/* 1485 */     this.m00 = scalar * m1.m00;
/* 1486 */     this.m01 = scalar * m1.m01;
/* 1487 */     this.m02 = scalar * m1.m02;
/*      */     
/* 1489 */     this.m10 = scalar * m1.m10;
/* 1490 */     this.m11 = scalar * m1.m11;
/* 1491 */     this.m12 = scalar * m1.m12;
/*      */     
/* 1493 */     this.m20 = scalar * m1.m20;
/* 1494 */     this.m21 = scalar * m1.m21;
/* 1495 */     this.m22 = scalar * m1.m22;
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
/*      */   public final void mul(Matrix3f m1) {
/* 1510 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1511 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1512 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1514 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1515 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1516 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1518 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1519 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1520 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
/*      */     
/* 1522 */     this.m00 = m00;
/* 1523 */     this.m01 = m01;
/* 1524 */     this.m02 = m02;
/* 1525 */     this.m10 = m10;
/* 1526 */     this.m11 = m11;
/* 1527 */     this.m12 = m12;
/* 1528 */     this.m20 = m20;
/* 1529 */     this.m21 = m21;
/* 1530 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3f m1, Matrix3f m2) {
/* 1541 */     if (this != m1 && this != m2) {
/* 1542 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1543 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1544 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1546 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1547 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1548 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1550 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1551 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1552 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1558 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1559 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1560 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1562 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1563 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1564 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1566 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1567 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1568 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1570 */       this.m00 = m00;
/* 1571 */       this.m01 = m01;
/* 1572 */       this.m02 = m02;
/* 1573 */       this.m10 = m10;
/* 1574 */       this.m11 = m11;
/* 1575 */       this.m12 = m12;
/* 1576 */       this.m20 = m20;
/* 1577 */       this.m21 = m21;
/* 1578 */       this.m22 = m22;
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
/*      */   public final void mulNormalize(Matrix3f m1) {
/* 1591 */     double[] tmp = new double[9];
/* 1592 */     double[] tmp_rot = new double[9];
/* 1593 */     double[] tmp_scale = new double[3];
/*      */     
/* 1595 */     tmp[0] = (this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20);
/* 1596 */     tmp[1] = (this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21);
/* 1597 */     tmp[2] = (this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22);
/*      */     
/* 1599 */     tmp[3] = (this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20);
/* 1600 */     tmp[4] = (this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21);
/* 1601 */     tmp[5] = (this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22);
/*      */     
/* 1603 */     tmp[6] = (this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20);
/* 1604 */     tmp[7] = (this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21);
/* 1605 */     tmp[8] = (this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22);
/*      */     
/* 1607 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1609 */     this.m00 = (float)tmp_rot[0];
/* 1610 */     this.m01 = (float)tmp_rot[1];
/* 1611 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1613 */     this.m10 = (float)tmp_rot[3];
/* 1614 */     this.m11 = (float)tmp_rot[4];
/* 1615 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1617 */     this.m20 = (float)tmp_rot[6];
/* 1618 */     this.m21 = (float)tmp_rot[7];
/* 1619 */     this.m22 = (float)tmp_rot[8];
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
/*      */   public final void mulNormalize(Matrix3f m1, Matrix3f m2) {
/* 1633 */     double[] tmp = new double[9];
/* 1634 */     double[] tmp_rot = new double[9];
/* 1635 */     double[] tmp_scale = new double[3];
/*      */ 
/*      */     
/* 1638 */     tmp[0] = (m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20);
/* 1639 */     tmp[1] = (m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21);
/* 1640 */     tmp[2] = (m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22);
/*      */     
/* 1642 */     tmp[3] = (m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20);
/* 1643 */     tmp[4] = (m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21);
/* 1644 */     tmp[5] = (m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22);
/*      */     
/* 1646 */     tmp[6] = (m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20);
/* 1647 */     tmp[7] = (m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21);
/* 1648 */     tmp[8] = (m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22);
/*      */     
/* 1650 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1652 */     this.m00 = (float)tmp_rot[0];
/* 1653 */     this.m01 = (float)tmp_rot[1];
/* 1654 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1656 */     this.m10 = (float)tmp_rot[3];
/* 1657 */     this.m11 = (float)tmp_rot[4];
/* 1658 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1660 */     this.m20 = (float)tmp_rot[6];
/* 1661 */     this.m21 = (float)tmp_rot[7];
/* 1662 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeBoth(Matrix3f m1, Matrix3f m2) {
/* 1673 */     if (this != m1 && this != m2) {
/* 1674 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1675 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1676 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1678 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1679 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1680 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1682 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1683 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1684 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1690 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1691 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1692 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1694 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1695 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1696 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1698 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1699 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1700 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1702 */       this.m00 = m00;
/* 1703 */       this.m01 = m01;
/* 1704 */       this.m02 = m02;
/* 1705 */       this.m10 = m10;
/* 1706 */       this.m11 = m11;
/* 1707 */       this.m12 = m12;
/* 1708 */       this.m20 = m20;
/* 1709 */       this.m21 = m21;
/* 1710 */       this.m22 = m22;
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
/*      */   public final void mulTransposeRight(Matrix3f m1, Matrix3f m2) {
/* 1724 */     if (this != m1 && this != m2) {
/* 1725 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1726 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1727 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1729 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1730 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1731 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1733 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1734 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1735 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1741 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1742 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1743 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1745 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1746 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1747 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1749 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1750 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1751 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1753 */       this.m00 = m00;
/* 1754 */       this.m01 = m01;
/* 1755 */       this.m02 = m02;
/* 1756 */       this.m10 = m10;
/* 1757 */       this.m11 = m11;
/* 1758 */       this.m12 = m12;
/* 1759 */       this.m20 = m20;
/* 1760 */       this.m21 = m21;
/* 1761 */       this.m22 = m22;
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
/*      */   public final void mulTransposeLeft(Matrix3f m1, Matrix3f m2) {
/* 1773 */     if (this != m1 && this != m2) {
/* 1774 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1775 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1776 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1778 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1779 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1780 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1782 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1783 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1784 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1790 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1791 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1792 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1794 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1795 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1796 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1798 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1799 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1800 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1802 */       this.m00 = m00;
/* 1803 */       this.m01 = m01;
/* 1804 */       this.m02 = m02;
/* 1805 */       this.m10 = m10;
/* 1806 */       this.m11 = m11;
/* 1807 */       this.m12 = m12;
/* 1808 */       this.m20 = m20;
/* 1809 */       this.m21 = m21;
/* 1810 */       this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1819 */     double[] tmp_rot = new double[9];
/* 1820 */     double[] tmp_scale = new double[3];
/* 1821 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1823 */     this.m00 = (float)tmp_rot[0];
/* 1824 */     this.m01 = (float)tmp_rot[1];
/* 1825 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1827 */     this.m10 = (float)tmp_rot[3];
/* 1828 */     this.m11 = (float)tmp_rot[4];
/* 1829 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1831 */     this.m20 = (float)tmp_rot[6];
/* 1832 */     this.m21 = (float)tmp_rot[7];
/* 1833 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize(Matrix3f m1) {
/* 1844 */     double[] tmp = new double[9];
/* 1845 */     double[] tmp_rot = new double[9];
/* 1846 */     double[] tmp_scale = new double[3];
/*      */     
/* 1848 */     tmp[0] = m1.m00;
/* 1849 */     tmp[1] = m1.m01;
/* 1850 */     tmp[2] = m1.m02;
/*      */     
/* 1852 */     tmp[3] = m1.m10;
/* 1853 */     tmp[4] = m1.m11;
/* 1854 */     tmp[5] = m1.m12;
/*      */     
/* 1856 */     tmp[6] = m1.m20;
/* 1857 */     tmp[7] = m1.m21;
/* 1858 */     tmp[8] = m1.m22;
/*      */     
/* 1860 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1862 */     this.m00 = (float)tmp_rot[0];
/* 1863 */     this.m01 = (float)tmp_rot[1];
/* 1864 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1866 */     this.m10 = (float)tmp_rot[3];
/* 1867 */     this.m11 = (float)tmp_rot[4];
/* 1868 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1870 */     this.m20 = (float)tmp_rot[6];
/* 1871 */     this.m21 = (float)tmp_rot[7];
/* 1872 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP() {
/* 1880 */     float mag = 1.0F / (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20));
/* 1881 */     this.m00 *= mag;
/* 1882 */     this.m10 *= mag;
/* 1883 */     this.m20 *= mag;
/*      */     
/* 1885 */     mag = 1.0F / (float)Math.sqrt((this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21));
/* 1886 */     this.m01 *= mag;
/* 1887 */     this.m11 *= mag;
/* 1888 */     this.m21 *= mag;
/*      */     
/* 1890 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1891 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1892 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP(Matrix3f m1) {
/* 1903 */     float mag = 1.0F / (float)Math.sqrt((m1.m00 * m1.m00 + m1.m10 * m1.m10 + m1.m20 * m1.m20));
/* 1904 */     m1.m00 *= mag;
/* 1905 */     m1.m10 *= mag;
/* 1906 */     m1.m20 *= mag;
/*      */     
/* 1908 */     mag = 1.0F / (float)Math.sqrt((m1.m01 * m1.m01 + m1.m11 * m1.m11 + m1.m21 * m1.m21));
/* 1909 */     m1.m01 *= mag;
/* 1910 */     m1.m11 *= mag;
/* 1911 */     m1.m21 *= mag;
/*      */     
/* 1913 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1914 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1915 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
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
/*      */   public boolean equals(Matrix3f m1) {
/*      */     try {
/* 1929 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && 
/* 1930 */         this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && 
/* 1931 */         this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22);
/* 1932 */     } catch (NullPointerException e2) {
/* 1933 */       return false;
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
/*      */   public boolean equals(Object o1) {
/*      */     try {
/* 1950 */       Matrix3f m2 = (Matrix3f)o1;
/* 1951 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && 
/* 1952 */         this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && 
/* 1953 */         this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22);
/* 1954 */     } catch (ClassCastException e1) {
/* 1955 */       return false;
/* 1956 */     } catch (NullPointerException e2) {
/* 1957 */       return false;
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
/*      */   public boolean epsilonEquals(Matrix3f m1, float epsilon) {
/* 1972 */     boolean status = true;
/*      */     
/* 1974 */     if (Math.abs(this.m00 - m1.m00) > epsilon) status = false; 
/* 1975 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 1976 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false;
/*      */     
/* 1978 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 1979 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 1980 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false;
/*      */     
/* 1982 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 1983 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 1984 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false;
/*      */     
/* 1986 */     return status;
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
/* 2002 */     long bits = 1L;
/* 2003 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 2004 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 2005 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 2006 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 2007 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 2008 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 2009 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 2010 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 2011 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 2012 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 2020 */     this.m00 = 0.0F;
/* 2021 */     this.m01 = 0.0F;
/* 2022 */     this.m02 = 0.0F;
/*      */     
/* 2024 */     this.m10 = 0.0F;
/* 2025 */     this.m11 = 0.0F;
/* 2026 */     this.m12 = 0.0F;
/*      */     
/* 2028 */     this.m20 = 0.0F;
/* 2029 */     this.m21 = 0.0F;
/* 2030 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 2038 */     this.m00 = -this.m00;
/* 2039 */     this.m01 = -this.m01;
/* 2040 */     this.m02 = -this.m02;
/*      */     
/* 2042 */     this.m10 = -this.m10;
/* 2043 */     this.m11 = -this.m11;
/* 2044 */     this.m12 = -this.m12;
/*      */     
/* 2046 */     this.m20 = -this.m20;
/* 2047 */     this.m21 = -this.m21;
/* 2048 */     this.m22 = -this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix3f m1) {
/* 2059 */     this.m00 = -m1.m00;
/* 2060 */     this.m01 = -m1.m01;
/* 2061 */     this.m02 = -m1.m02;
/*      */     
/* 2063 */     this.m10 = -m1.m10;
/* 2064 */     this.m11 = -m1.m11;
/* 2065 */     this.m12 = -m1.m12;
/*      */     
/* 2067 */     this.m20 = -m1.m20;
/* 2068 */     this.m21 = -m1.m21;
/* 2069 */     this.m22 = -m1.m22;
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
/*      */   public final void transform(Tuple3f t) {
/* 2081 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2082 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2083 */     float z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2084 */     t.set(x, y, z);
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
/*      */   public final void transform(Tuple3f t, Tuple3f result) {
/* 2096 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2097 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2098 */     result.z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2099 */     result.x = x;
/* 2100 */     result.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getScaleRotate(double[] scales, double[] rot) {
/* 2108 */     double[] tmp = new double[9];
/* 2109 */     tmp[0] = this.m00;
/* 2110 */     tmp[1] = this.m01;
/* 2111 */     tmp[2] = this.m02;
/* 2112 */     tmp[3] = this.m10;
/* 2113 */     tmp[4] = this.m11;
/* 2114 */     tmp[5] = this.m12;
/* 2115 */     tmp[6] = this.m20;
/* 2116 */     tmp[7] = this.m21;
/* 2117 */     tmp[8] = this.m22;
/* 2118 */     Matrix3d.compute_svd(tmp, scales, rot);
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
/*      */   public Object clone() {
/* 2134 */     Matrix3f m1 = null;
/*      */     try {
/* 2136 */       m1 = (Matrix3f)super.clone();
/* 2137 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 2139 */       throw new InternalError();
/*      */     } 
/* 2141 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM00() {
/* 2152 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(float m00) {
/* 2162 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM01() {
/* 2172 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(float m01) {
/* 2182 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM02() {
/* 2192 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(float m02) {
/* 2202 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM10() {
/* 2212 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(float m10) {
/* 2222 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM11() {
/* 2232 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(float m11) {
/* 2242 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM12() {
/* 2252 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float m12) {
/* 2262 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM20() {
/* 2272 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(float m20) {
/* 2282 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM21() {
/* 2292 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(float m21) {
/* 2302 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM22() {
/* 2312 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(float m22) {
/* 2322 */     this.m22 = m22;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Matrix3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */