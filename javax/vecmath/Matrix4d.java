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
/*      */ 
/*      */ public class Matrix4d
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 8223903484171633710L;
/*      */   public double m00;
/*      */   public double m01;
/*      */   public double m02;
/*      */   public double m03;
/*      */   public double m10;
/*      */   public double m11;
/*      */   public double m12;
/*      */   public double m13;
/*      */   public double m20;
/*      */   public double m21;
/*      */   public double m22;
/*      */   public double m23;
/*      */   public double m30;
/*      */   public double m31;
/*      */   public double m32;
/*      */   public double m33;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public Matrix4d(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
/*  150 */     this.m00 = m00;
/*  151 */     this.m01 = m01;
/*  152 */     this.m02 = m02;
/*  153 */     this.m03 = m03;
/*      */     
/*  155 */     this.m10 = m10;
/*  156 */     this.m11 = m11;
/*  157 */     this.m12 = m12;
/*  158 */     this.m13 = m13;
/*      */     
/*  160 */     this.m20 = m20;
/*  161 */     this.m21 = m21;
/*  162 */     this.m22 = m22;
/*  163 */     this.m23 = m23;
/*      */     
/*  165 */     this.m30 = m30;
/*  166 */     this.m31 = m31;
/*  167 */     this.m32 = m32;
/*  168 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(double[] v) {
/*  179 */     this.m00 = v[0];
/*  180 */     this.m01 = v[1];
/*  181 */     this.m02 = v[2];
/*  182 */     this.m03 = v[3];
/*      */     
/*  184 */     this.m10 = v[4];
/*  185 */     this.m11 = v[5];
/*  186 */     this.m12 = v[6];
/*  187 */     this.m13 = v[7];
/*      */     
/*  189 */     this.m20 = v[8];
/*  190 */     this.m21 = v[9];
/*  191 */     this.m22 = v[10];
/*  192 */     this.m23 = v[11];
/*      */     
/*  194 */     this.m30 = v[12];
/*  195 */     this.m31 = v[13];
/*  196 */     this.m32 = v[14];
/*  197 */     this.m33 = v[15];
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
/*      */   public Matrix4d(Quat4d q1, Vector3d t1, double s) {
/*  212 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  213 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  214 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  216 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  217 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  218 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  220 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  221 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  222 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/*  224 */     this.m03 = t1.x;
/*  225 */     this.m13 = t1.y;
/*  226 */     this.m23 = t1.z;
/*      */     
/*  228 */     this.m30 = 0.0D;
/*  229 */     this.m31 = 0.0D;
/*  230 */     this.m32 = 0.0D;
/*  231 */     this.m33 = 1.0D;
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
/*      */   public Matrix4d(Quat4f q1, Vector3d t1, double s) {
/*  246 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  247 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  248 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  250 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  251 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  252 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  254 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  255 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  256 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/*  258 */     this.m03 = t1.x;
/*  259 */     this.m13 = t1.y;
/*  260 */     this.m23 = t1.z;
/*      */     
/*  262 */     this.m30 = 0.0D;
/*  263 */     this.m31 = 0.0D;
/*  264 */     this.m32 = 0.0D;
/*  265 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4d m1) {
/*  276 */     this.m00 = m1.m00;
/*  277 */     this.m01 = m1.m01;
/*  278 */     this.m02 = m1.m02;
/*  279 */     this.m03 = m1.m03;
/*      */     
/*  281 */     this.m10 = m1.m10;
/*  282 */     this.m11 = m1.m11;
/*  283 */     this.m12 = m1.m12;
/*  284 */     this.m13 = m1.m13;
/*      */     
/*  286 */     this.m20 = m1.m20;
/*  287 */     this.m21 = m1.m21;
/*  288 */     this.m22 = m1.m22;
/*  289 */     this.m23 = m1.m23;
/*      */     
/*  291 */     this.m30 = m1.m30;
/*  292 */     this.m31 = m1.m31;
/*  293 */     this.m32 = m1.m32;
/*  294 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4f m1) {
/*  305 */     this.m00 = m1.m00;
/*  306 */     this.m01 = m1.m01;
/*  307 */     this.m02 = m1.m02;
/*  308 */     this.m03 = m1.m03;
/*      */     
/*  310 */     this.m10 = m1.m10;
/*  311 */     this.m11 = m1.m11;
/*  312 */     this.m12 = m1.m12;
/*  313 */     this.m13 = m1.m13;
/*      */     
/*  315 */     this.m20 = m1.m20;
/*  316 */     this.m21 = m1.m21;
/*  317 */     this.m22 = m1.m22;
/*  318 */     this.m23 = m1.m23;
/*      */     
/*  320 */     this.m30 = m1.m30;
/*  321 */     this.m31 = m1.m31;
/*  322 */     this.m32 = m1.m32;
/*  323 */     this.m33 = m1.m33;
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
/*      */   public Matrix4d(Matrix3f m1, Vector3d t1, double s) {
/*  338 */     this.m00 = m1.m00 * s;
/*  339 */     this.m01 = m1.m01 * s;
/*  340 */     this.m02 = m1.m02 * s;
/*  341 */     this.m03 = t1.x;
/*      */     
/*  343 */     this.m10 = m1.m10 * s;
/*  344 */     this.m11 = m1.m11 * s;
/*  345 */     this.m12 = m1.m12 * s;
/*  346 */     this.m13 = t1.y;
/*      */     
/*  348 */     this.m20 = m1.m20 * s;
/*  349 */     this.m21 = m1.m21 * s;
/*  350 */     this.m22 = m1.m22 * s;
/*  351 */     this.m23 = t1.z;
/*      */     
/*  353 */     this.m30 = 0.0D;
/*  354 */     this.m31 = 0.0D;
/*  355 */     this.m32 = 0.0D;
/*  356 */     this.m33 = 1.0D;
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
/*      */   public Matrix4d(Matrix3d m1, Vector3d t1, double s) {
/*  371 */     this.m00 = m1.m00 * s;
/*  372 */     this.m01 = m1.m01 * s;
/*  373 */     this.m02 = m1.m02 * s;
/*  374 */     this.m03 = t1.x;
/*      */     
/*  376 */     this.m10 = m1.m10 * s;
/*  377 */     this.m11 = m1.m11 * s;
/*  378 */     this.m12 = m1.m12 * s;
/*  379 */     this.m13 = t1.y;
/*      */     
/*  381 */     this.m20 = m1.m20 * s;
/*  382 */     this.m21 = m1.m21 * s;
/*  383 */     this.m22 = m1.m22 * s;
/*  384 */     this.m23 = t1.z;
/*      */     
/*  386 */     this.m30 = 0.0D;
/*  387 */     this.m31 = 0.0D;
/*  388 */     this.m32 = 0.0D;
/*  389 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d() {
/*  397 */     this.m00 = 0.0D;
/*  398 */     this.m01 = 0.0D;
/*  399 */     this.m02 = 0.0D;
/*  400 */     this.m03 = 0.0D;
/*      */     
/*  402 */     this.m10 = 0.0D;
/*  403 */     this.m11 = 0.0D;
/*  404 */     this.m12 = 0.0D;
/*  405 */     this.m13 = 0.0D;
/*      */     
/*  407 */     this.m20 = 0.0D;
/*  408 */     this.m21 = 0.0D;
/*  409 */     this.m22 = 0.0D;
/*  410 */     this.m23 = 0.0D;
/*      */     
/*  412 */     this.m30 = 0.0D;
/*  413 */     this.m31 = 0.0D;
/*  414 */     this.m32 = 0.0D;
/*  415 */     this.m33 = 0.0D;
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
/*  426 */     return 
/*  427 */       String.valueOf(this.m00) + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + 
/*  428 */       this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + 
/*  429 */       this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + 
/*  430 */       this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  437 */     this.m00 = 1.0D;
/*  438 */     this.m01 = 0.0D;
/*  439 */     this.m02 = 0.0D;
/*  440 */     this.m03 = 0.0D;
/*      */     
/*  442 */     this.m10 = 0.0D;
/*  443 */     this.m11 = 1.0D;
/*  444 */     this.m12 = 0.0D;
/*  445 */     this.m13 = 0.0D;
/*      */     
/*  447 */     this.m20 = 0.0D;
/*  448 */     this.m21 = 0.0D;
/*  449 */     this.m22 = 1.0D;
/*  450 */     this.m23 = 0.0D;
/*      */     
/*  452 */     this.m30 = 0.0D;
/*  453 */     this.m31 = 0.0D;
/*  454 */     this.m32 = 0.0D;
/*  455 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int row, int column, double value) {
/*  466 */     switch (row) {
/*      */       case 0:
/*  468 */         switch (column) {
/*      */           case 0:
/*  470 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  473 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  476 */             this.m02 = value;
/*      */             return;
/*      */           case 3:
/*  479 */             this.m03 = value;
/*      */             return;
/*      */         } 
/*  482 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  487 */         switch (column) {
/*      */           case 0:
/*  489 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  492 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  495 */             this.m12 = value;
/*      */             return;
/*      */           case 3:
/*  498 */             this.m13 = value;
/*      */             return;
/*      */         } 
/*  501 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  506 */         switch (column) {
/*      */           case 0:
/*  508 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  511 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  514 */             this.m22 = value;
/*      */             return;
/*      */           case 3:
/*  517 */             this.m23 = value;
/*      */             return;
/*      */         } 
/*  520 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  525 */         switch (column) {
/*      */           case 0:
/*  527 */             this.m30 = value;
/*      */             return;
/*      */           case 1:
/*  530 */             this.m31 = value;
/*      */             return;
/*      */           case 2:
/*  533 */             this.m32 = value;
/*      */             return;
/*      */           case 3:
/*  536 */             this.m33 = value;
/*      */             return;
/*      */         } 
/*  539 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  544 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
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
/*      */   public final double getElement(int row, int column) {
/*  556 */     switch (row) {
/*      */       case 0:
/*  558 */         switch (column) {
/*      */           case 0:
/*  560 */             return this.m00;
/*      */           case 1:
/*  562 */             return this.m01;
/*      */           case 2:
/*  564 */             return this.m02;
/*      */           case 3:
/*  566 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  572 */         switch (column) {
/*      */           case 0:
/*  574 */             return this.m10;
/*      */           case 1:
/*  576 */             return this.m11;
/*      */           case 2:
/*  578 */             return this.m12;
/*      */           case 3:
/*  580 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  587 */         switch (column) {
/*      */           case 0:
/*  589 */             return this.m20;
/*      */           case 1:
/*  591 */             return this.m21;
/*      */           case 2:
/*  593 */             return this.m22;
/*      */           case 3:
/*  595 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  602 */         switch (column) {
/*      */           case 0:
/*  604 */             return this.m30;
/*      */           case 1:
/*  606 */             return this.m31;
/*      */           case 2:
/*  608 */             return this.m32;
/*      */           case 3:
/*  610 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  619 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector4d v) {
/*  629 */     if (row == 0) {
/*  630 */       v.x = this.m00;
/*  631 */       v.y = this.m01;
/*  632 */       v.z = this.m02;
/*  633 */       v.w = this.m03;
/*  634 */     } else if (row == 1) {
/*  635 */       v.x = this.m10;
/*  636 */       v.y = this.m11;
/*  637 */       v.z = this.m12;
/*  638 */       v.w = this.m13;
/*  639 */     } else if (row == 2) {
/*  640 */       v.x = this.m20;
/*  641 */       v.y = this.m21;
/*  642 */       v.z = this.m22;
/*  643 */       v.w = this.m23;
/*  644 */     } else if (row == 3) {
/*  645 */       v.x = this.m30;
/*  646 */       v.y = this.m31;
/*  647 */       v.z = this.m32;
/*  648 */       v.w = this.m33;
/*      */     } else {
/*  650 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
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
/*      */   public final void getRow(int row, double[] v) {
/*  662 */     if (row == 0) {
/*  663 */       v[0] = this.m00;
/*  664 */       v[1] = this.m01;
/*  665 */       v[2] = this.m02;
/*  666 */       v[3] = this.m03;
/*  667 */     } else if (row == 1) {
/*  668 */       v[0] = this.m10;
/*  669 */       v[1] = this.m11;
/*  670 */       v[2] = this.m12;
/*  671 */       v[3] = this.m13;
/*  672 */     } else if (row == 2) {
/*  673 */       v[0] = this.m20;
/*  674 */       v[1] = this.m21;
/*  675 */       v[2] = this.m22;
/*  676 */       v[3] = this.m23;
/*  677 */     } else if (row == 3) {
/*  678 */       v[0] = this.m30;
/*  679 */       v[1] = this.m31;
/*  680 */       v[2] = this.m32;
/*  681 */       v[3] = this.m33;
/*      */     } else {
/*      */       
/*  684 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
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
/*      */   public final void getColumn(int column, Vector4d v) {
/*  697 */     if (column == 0) {
/*  698 */       v.x = this.m00;
/*  699 */       v.y = this.m10;
/*  700 */       v.z = this.m20;
/*  701 */       v.w = this.m30;
/*  702 */     } else if (column == 1) {
/*  703 */       v.x = this.m01;
/*  704 */       v.y = this.m11;
/*  705 */       v.z = this.m21;
/*  706 */       v.w = this.m31;
/*  707 */     } else if (column == 2) {
/*  708 */       v.x = this.m02;
/*  709 */       v.y = this.m12;
/*  710 */       v.z = this.m22;
/*  711 */       v.w = this.m32;
/*  712 */     } else if (column == 3) {
/*  713 */       v.x = this.m03;
/*  714 */       v.y = this.m13;
/*  715 */       v.z = this.m23;
/*  716 */       v.w = this.m33;
/*      */     } else {
/*  718 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
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
/*      */   public final void getColumn(int column, double[] v) {
/*  733 */     if (column == 0) {
/*  734 */       v[0] = this.m00;
/*  735 */       v[1] = this.m10;
/*  736 */       v[2] = this.m20;
/*  737 */       v[3] = this.m30;
/*  738 */     } else if (column == 1) {
/*  739 */       v[0] = this.m01;
/*  740 */       v[1] = this.m11;
/*  741 */       v[2] = this.m21;
/*  742 */       v[3] = this.m31;
/*  743 */     } else if (column == 2) {
/*  744 */       v[0] = this.m02;
/*  745 */       v[1] = this.m12;
/*  746 */       v[2] = this.m22;
/*  747 */       v[3] = this.m32;
/*  748 */     } else if (column == 3) {
/*  749 */       v[0] = this.m03;
/*  750 */       v[1] = this.m13;
/*  751 */       v[2] = this.m23;
/*  752 */       v[3] = this.m33;
/*      */     } else {
/*  754 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
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
/*      */   public final void get(Matrix3d m1) {
/*  770 */     double[] tmp_rot = new double[9];
/*  771 */     double[] tmp_scale = new double[3];
/*  772 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  774 */     m1.m00 = tmp_rot[0];
/*  775 */     m1.m01 = tmp_rot[1];
/*  776 */     m1.m02 = tmp_rot[2];
/*      */     
/*  778 */     m1.m10 = tmp_rot[3];
/*  779 */     m1.m11 = tmp_rot[4];
/*  780 */     m1.m12 = tmp_rot[5];
/*      */     
/*  782 */     m1.m20 = tmp_rot[6];
/*  783 */     m1.m21 = tmp_rot[7];
/*  784 */     m1.m22 = tmp_rot[8];
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
/*      */   public final void get(Matrix3f m1) {
/*  797 */     double[] tmp_rot = new double[9];
/*  798 */     double[] tmp_scale = new double[3];
/*      */     
/*  800 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  802 */     m1.m00 = (float)tmp_rot[0];
/*  803 */     m1.m01 = (float)tmp_rot[1];
/*  804 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  806 */     m1.m10 = (float)tmp_rot[3];
/*  807 */     m1.m11 = (float)tmp_rot[4];
/*  808 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  810 */     m1.m20 = (float)tmp_rot[6];
/*  811 */     m1.m21 = (float)tmp_rot[7];
/*  812 */     m1.m22 = (float)tmp_rot[8];
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
/*      */   public final double get(Matrix3d m1, Vector3d t1) {
/*  826 */     double[] tmp_rot = new double[9];
/*  827 */     double[] tmp_scale = new double[3];
/*  828 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  830 */     m1.m00 = tmp_rot[0];
/*  831 */     m1.m01 = tmp_rot[1];
/*  832 */     m1.m02 = tmp_rot[2];
/*      */     
/*  834 */     m1.m10 = tmp_rot[3];
/*  835 */     m1.m11 = tmp_rot[4];
/*  836 */     m1.m12 = tmp_rot[5];
/*      */     
/*  838 */     m1.m20 = tmp_rot[6];
/*  839 */     m1.m21 = tmp_rot[7];
/*  840 */     m1.m22 = tmp_rot[8];
/*      */     
/*  842 */     t1.x = this.m03;
/*  843 */     t1.y = this.m13;
/*  844 */     t1.z = this.m23;
/*      */     
/*  846 */     return Matrix3d.max3(tmp_scale);
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
/*      */   public final double get(Matrix3f m1, Vector3d t1) {
/*  861 */     double[] tmp_rot = new double[9];
/*  862 */     double[] tmp_scale = new double[3];
/*  863 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  865 */     m1.m00 = (float)tmp_rot[0];
/*  866 */     m1.m01 = (float)tmp_rot[1];
/*  867 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  869 */     m1.m10 = (float)tmp_rot[3];
/*  870 */     m1.m11 = (float)tmp_rot[4];
/*  871 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  873 */     m1.m20 = (float)tmp_rot[6];
/*  874 */     m1.m21 = (float)tmp_rot[7];
/*  875 */     m1.m22 = (float)tmp_rot[8];
/*      */     
/*  877 */     t1.x = this.m03;
/*  878 */     t1.y = this.m13;
/*  879 */     t1.z = this.m23;
/*      */     
/*  881 */     return Matrix3d.max3(tmp_scale);
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
/*  894 */     double[] tmp_rot = new double[9];
/*  895 */     double[] tmp_scale = new double[3];
/*  896 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  900 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  901 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  902 */       q1.w = (float)Math.sqrt(ww);
/*  903 */       ww = 0.25D / q1.w;
/*  904 */       q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
/*  905 */       q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
/*  906 */       q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  910 */     q1.w = 0.0F;
/*  911 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  912 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  913 */       q1.x = (float)Math.sqrt(ww);
/*  914 */       ww = 0.5D / q1.x;
/*  915 */       q1.y = (float)(tmp_rot[3] * ww);
/*  916 */       q1.z = (float)(tmp_rot[6] * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  920 */     q1.x = 0.0F;
/*  921 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  922 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  923 */       q1.y = (float)Math.sqrt(ww);
/*  924 */       q1.z = (float)(tmp_rot[7] / 2.0D * q1.y);
/*      */       
/*      */       return;
/*      */     } 
/*  928 */     q1.y = 0.0F;
/*  929 */     q1.z = 1.0F;
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
/*      */   public final void get(Quat4d q1) {
/*  941 */     double[] tmp_rot = new double[9];
/*  942 */     double[] tmp_scale = new double[3];
/*      */     
/*  944 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  948 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  949 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  950 */       q1.w = Math.sqrt(ww);
/*  951 */       ww = 0.25D / q1.w;
/*  952 */       q1.x = (tmp_rot[7] - tmp_rot[5]) * ww;
/*  953 */       q1.y = (tmp_rot[2] - tmp_rot[6]) * ww;
/*  954 */       q1.z = (tmp_rot[3] - tmp_rot[1]) * ww;
/*      */       
/*      */       return;
/*      */     } 
/*  958 */     q1.w = 0.0D;
/*  959 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  960 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  961 */       q1.x = Math.sqrt(ww);
/*  962 */       ww = 0.5D / q1.x;
/*  963 */       q1.y = tmp_rot[3] * ww;
/*  964 */       q1.z = tmp_rot[6] * ww;
/*      */       
/*      */       return;
/*      */     } 
/*  968 */     q1.x = 0.0D;
/*  969 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  970 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  971 */       q1.y = Math.sqrt(ww);
/*  972 */       q1.z = tmp_rot[7] / 2.0D * q1.y;
/*      */       
/*      */       return;
/*      */     } 
/*  976 */     q1.y = 0.0D;
/*  977 */     q1.z = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3d trans) {
/*  986 */     trans.x = this.m03;
/*  987 */     trans.y = this.m13;
/*  988 */     trans.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f m1) {
/*  998 */     m1.m00 = (float)this.m00;
/*  999 */     m1.m01 = (float)this.m01;
/* 1000 */     m1.m02 = (float)this.m02;
/* 1001 */     m1.m10 = (float)this.m10;
/* 1002 */     m1.m11 = (float)this.m11;
/* 1003 */     m1.m12 = (float)this.m12;
/* 1004 */     m1.m20 = (float)this.m20;
/* 1005 */     m1.m21 = (float)this.m21;
/* 1006 */     m1.m22 = (float)this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3d m1) {
/* 1016 */     m1.m00 = this.m00;
/* 1017 */     m1.m01 = this.m01;
/* 1018 */     m1.m02 = this.m02;
/* 1019 */     m1.m10 = this.m10;
/* 1020 */     m1.m11 = this.m11;
/* 1021 */     m1.m12 = this.m12;
/* 1022 */     m1.m20 = this.m20;
/* 1023 */     m1.m21 = this.m21;
/* 1024 */     m1.m22 = this.m22;
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
/*      */   public final double getScale() {
/* 1037 */     double[] tmp_rot = new double[9];
/* 1038 */     double[] tmp_scale = new double[3];
/* 1039 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1041 */     return Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3d m1) {
/* 1052 */     this.m00 = m1.m00;
/* 1053 */     this.m01 = m1.m01;
/* 1054 */     this.m02 = m1.m02;
/* 1055 */     this.m10 = m1.m10;
/* 1056 */     this.m11 = m1.m11;
/* 1057 */     this.m12 = m1.m12;
/* 1058 */     this.m20 = m1.m20;
/* 1059 */     this.m21 = m1.m21;
/* 1060 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3f m1) {
/* 1070 */     this.m00 = m1.m00;
/* 1071 */     this.m01 = m1.m01;
/* 1072 */     this.m02 = m1.m02;
/* 1073 */     this.m10 = m1.m10;
/* 1074 */     this.m11 = m1.m11;
/* 1075 */     this.m12 = m1.m12;
/* 1076 */     this.m20 = m1.m20;
/* 1077 */     this.m21 = m1.m21;
/* 1078 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(double scale) {
/* 1089 */     double[] tmp_rot = new double[9];
/* 1090 */     double[] tmp_scale = new double[3];
/*      */     
/* 1092 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1094 */     this.m00 = tmp_rot[0] * scale;
/* 1095 */     this.m01 = tmp_rot[1] * scale;
/* 1096 */     this.m02 = tmp_rot[2] * scale;
/*      */     
/* 1098 */     this.m10 = tmp_rot[3] * scale;
/* 1099 */     this.m11 = tmp_rot[4] * scale;
/* 1100 */     this.m12 = tmp_rot[5] * scale;
/*      */     
/* 1102 */     this.m20 = tmp_rot[6] * scale;
/* 1103 */     this.m21 = tmp_rot[7] * scale;
/* 1104 */     this.m22 = tmp_rot[8] * scale;
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
/*      */   public final void setRow(int row, double x, double y, double z, double w) {
/* 1118 */     switch (row) {
/*      */       case 0:
/* 1120 */         this.m00 = x;
/* 1121 */         this.m01 = y;
/* 1122 */         this.m02 = z;
/* 1123 */         this.m03 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1127 */         this.m10 = x;
/* 1128 */         this.m11 = y;
/* 1129 */         this.m12 = z;
/* 1130 */         this.m13 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1134 */         this.m20 = x;
/* 1135 */         this.m21 = y;
/* 1136 */         this.m22 = z;
/* 1137 */         this.m23 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1141 */         this.m30 = x;
/* 1142 */         this.m31 = y;
/* 1143 */         this.m32 = z;
/* 1144 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1148 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/*      */   public final void setRow(int row, Vector4d v) {
/* 1160 */     switch (row) {
/*      */       case 0:
/* 1162 */         this.m00 = v.x;
/* 1163 */         this.m01 = v.y;
/* 1164 */         this.m02 = v.z;
/* 1165 */         this.m03 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1169 */         this.m10 = v.x;
/* 1170 */         this.m11 = v.y;
/* 1171 */         this.m12 = v.z;
/* 1172 */         this.m13 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1176 */         this.m20 = v.x;
/* 1177 */         this.m21 = v.y;
/* 1178 */         this.m22 = v.z;
/* 1179 */         this.m23 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1183 */         this.m30 = v.x;
/* 1184 */         this.m31 = v.y;
/* 1185 */         this.m32 = v.z;
/* 1186 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1190 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, double[] v) {
/* 1201 */     switch (row) {
/*      */       case 0:
/* 1203 */         this.m00 = v[0];
/* 1204 */         this.m01 = v[1];
/* 1205 */         this.m02 = v[2];
/* 1206 */         this.m03 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1210 */         this.m10 = v[0];
/* 1211 */         this.m11 = v[1];
/* 1212 */         this.m12 = v[2];
/* 1213 */         this.m13 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1217 */         this.m20 = v[0];
/* 1218 */         this.m21 = v[1];
/* 1219 */         this.m22 = v[2];
/* 1220 */         this.m23 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1224 */         this.m30 = v[0];
/* 1225 */         this.m31 = v[1];
/* 1226 */         this.m32 = v[2];
/* 1227 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1231 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
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
/*      */   public final void setColumn(int column, double x, double y, double z, double w) {
/* 1245 */     switch (column) {
/*      */       case 0:
/* 1247 */         this.m00 = x;
/* 1248 */         this.m10 = y;
/* 1249 */         this.m20 = z;
/* 1250 */         this.m30 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1254 */         this.m01 = x;
/* 1255 */         this.m11 = y;
/* 1256 */         this.m21 = z;
/* 1257 */         this.m31 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1261 */         this.m02 = x;
/* 1262 */         this.m12 = y;
/* 1263 */         this.m22 = z;
/* 1264 */         this.m32 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1268 */         this.m03 = x;
/* 1269 */         this.m13 = y;
/* 1270 */         this.m23 = z;
/* 1271 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1275 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector4d v) {
/* 1286 */     switch (column) {
/*      */       case 0:
/* 1288 */         this.m00 = v.x;
/* 1289 */         this.m10 = v.y;
/* 1290 */         this.m20 = v.z;
/* 1291 */         this.m30 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1295 */         this.m01 = v.x;
/* 1296 */         this.m11 = v.y;
/* 1297 */         this.m21 = v.z;
/* 1298 */         this.m31 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1302 */         this.m02 = v.x;
/* 1303 */         this.m12 = v.y;
/* 1304 */         this.m22 = v.z;
/* 1305 */         this.m32 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1309 */         this.m03 = v.x;
/* 1310 */         this.m13 = v.y;
/* 1311 */         this.m23 = v.z;
/* 1312 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1316 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, double[] v) {
/* 1327 */     switch (column) {
/*      */       case 0:
/* 1329 */         this.m00 = v[0];
/* 1330 */         this.m10 = v[1];
/* 1331 */         this.m20 = v[2];
/* 1332 */         this.m30 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1336 */         this.m01 = v[0];
/* 1337 */         this.m11 = v[1];
/* 1338 */         this.m21 = v[2];
/* 1339 */         this.m31 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1343 */         this.m02 = v[0];
/* 1344 */         this.m12 = v[1];
/* 1345 */         this.m22 = v[2];
/* 1346 */         this.m32 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1350 */         this.m03 = v[0];
/* 1351 */         this.m13 = v[1];
/* 1352 */         this.m23 = v[2];
/* 1353 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1357 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar) {
/* 1367 */     this.m00 += scalar;
/* 1368 */     this.m01 += scalar;
/* 1369 */     this.m02 += scalar;
/* 1370 */     this.m03 += scalar;
/* 1371 */     this.m10 += scalar;
/* 1372 */     this.m11 += scalar;
/* 1373 */     this.m12 += scalar;
/* 1374 */     this.m13 += scalar;
/* 1375 */     this.m20 += scalar;
/* 1376 */     this.m21 += scalar;
/* 1377 */     this.m22 += scalar;
/* 1378 */     this.m23 += scalar;
/* 1379 */     this.m30 += scalar;
/* 1380 */     this.m31 += scalar;
/* 1381 */     this.m32 += scalar;
/* 1382 */     this.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar, Matrix4d m1) {
/* 1393 */     m1.m00 += scalar;
/* 1394 */     m1.m01 += scalar;
/* 1395 */     m1.m02 += scalar;
/* 1396 */     m1.m03 += scalar;
/* 1397 */     m1.m10 += scalar;
/* 1398 */     m1.m11 += scalar;
/* 1399 */     m1.m12 += scalar;
/* 1400 */     m1.m13 += scalar;
/* 1401 */     m1.m20 += scalar;
/* 1402 */     m1.m21 += scalar;
/* 1403 */     m1.m22 += scalar;
/* 1404 */     m1.m23 += scalar;
/* 1405 */     m1.m30 += scalar;
/* 1406 */     m1.m31 += scalar;
/* 1407 */     m1.m32 += scalar;
/* 1408 */     m1.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d m1, Matrix4d m2) {
/* 1418 */     m1.m00 += m2.m00;
/* 1419 */     m1.m01 += m2.m01;
/* 1420 */     m1.m02 += m2.m02;
/* 1421 */     m1.m03 += m2.m03;
/*      */     
/* 1423 */     m1.m10 += m2.m10;
/* 1424 */     m1.m11 += m2.m11;
/* 1425 */     m1.m12 += m2.m12;
/* 1426 */     m1.m13 += m2.m13;
/*      */     
/* 1428 */     m1.m20 += m2.m20;
/* 1429 */     m1.m21 += m2.m21;
/* 1430 */     m1.m22 += m2.m22;
/* 1431 */     m1.m23 += m2.m23;
/*      */     
/* 1433 */     m1.m30 += m2.m30;
/* 1434 */     m1.m31 += m2.m31;
/* 1435 */     m1.m32 += m2.m32;
/* 1436 */     m1.m33 += m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d m1) {
/* 1445 */     this.m00 += m1.m00;
/* 1446 */     this.m01 += m1.m01;
/* 1447 */     this.m02 += m1.m02;
/* 1448 */     this.m03 += m1.m03;
/*      */     
/* 1450 */     this.m10 += m1.m10;
/* 1451 */     this.m11 += m1.m11;
/* 1452 */     this.m12 += m1.m12;
/* 1453 */     this.m13 += m1.m13;
/*      */     
/* 1455 */     this.m20 += m1.m20;
/* 1456 */     this.m21 += m1.m21;
/* 1457 */     this.m22 += m1.m22;
/* 1458 */     this.m23 += m1.m23;
/*      */     
/* 1460 */     this.m30 += m1.m30;
/* 1461 */     this.m31 += m1.m31;
/* 1462 */     this.m32 += m1.m32;
/* 1463 */     this.m33 += m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d m1, Matrix4d m2) {
/* 1474 */     m1.m00 -= m2.m00;
/* 1475 */     m1.m01 -= m2.m01;
/* 1476 */     m1.m02 -= m2.m02;
/* 1477 */     m1.m03 -= m2.m03;
/*      */     
/* 1479 */     m1.m10 -= m2.m10;
/* 1480 */     m1.m11 -= m2.m11;
/* 1481 */     m1.m12 -= m2.m12;
/* 1482 */     m1.m13 -= m2.m13;
/*      */     
/* 1484 */     m1.m20 -= m2.m20;
/* 1485 */     m1.m21 -= m2.m21;
/* 1486 */     m1.m22 -= m2.m22;
/* 1487 */     m1.m23 -= m2.m23;
/*      */     
/* 1489 */     m1.m30 -= m2.m30;
/* 1490 */     m1.m31 -= m2.m31;
/* 1491 */     m1.m32 -= m2.m32;
/* 1492 */     m1.m33 -= m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d m1) {
/* 1503 */     this.m00 -= m1.m00;
/* 1504 */     this.m01 -= m1.m01;
/* 1505 */     this.m02 -= m1.m02;
/* 1506 */     this.m03 -= m1.m03;
/*      */     
/* 1508 */     this.m10 -= m1.m10;
/* 1509 */     this.m11 -= m1.m11;
/* 1510 */     this.m12 -= m1.m12;
/* 1511 */     this.m13 -= m1.m13;
/*      */     
/* 1513 */     this.m20 -= m1.m20;
/* 1514 */     this.m21 -= m1.m21;
/* 1515 */     this.m22 -= m1.m22;
/* 1516 */     this.m23 -= m1.m23;
/*      */     
/* 1518 */     this.m30 -= m1.m30;
/* 1519 */     this.m31 -= m1.m31;
/* 1520 */     this.m32 -= m1.m32;
/* 1521 */     this.m33 -= m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1530 */     double temp = this.m10;
/* 1531 */     this.m10 = this.m01;
/* 1532 */     this.m01 = temp;
/*      */     
/* 1534 */     temp = this.m20;
/* 1535 */     this.m20 = this.m02;
/* 1536 */     this.m02 = temp;
/*      */     
/* 1538 */     temp = this.m30;
/* 1539 */     this.m30 = this.m03;
/* 1540 */     this.m03 = temp;
/*      */     
/* 1542 */     temp = this.m21;
/* 1543 */     this.m21 = this.m12;
/* 1544 */     this.m12 = temp;
/*      */     
/* 1546 */     temp = this.m31;
/* 1547 */     this.m31 = this.m13;
/* 1548 */     this.m13 = temp;
/*      */     
/* 1550 */     temp = this.m32;
/* 1551 */     this.m32 = this.m23;
/* 1552 */     this.m23 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4d m1) {
/* 1561 */     if (this != m1) {
/* 1562 */       this.m00 = m1.m00;
/* 1563 */       this.m01 = m1.m10;
/* 1564 */       this.m02 = m1.m20;
/* 1565 */       this.m03 = m1.m30;
/*      */       
/* 1567 */       this.m10 = m1.m01;
/* 1568 */       this.m11 = m1.m11;
/* 1569 */       this.m12 = m1.m21;
/* 1570 */       this.m13 = m1.m31;
/*      */       
/* 1572 */       this.m20 = m1.m02;
/* 1573 */       this.m21 = m1.m12;
/* 1574 */       this.m22 = m1.m22;
/* 1575 */       this.m23 = m1.m32;
/*      */       
/* 1577 */       this.m30 = m1.m03;
/* 1578 */       this.m31 = m1.m13;
/* 1579 */       this.m32 = m1.m23;
/* 1580 */       this.m33 = m1.m33;
/*      */     } else {
/* 1582 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double[] m) {
/* 1593 */     this.m00 = m[0];
/* 1594 */     this.m01 = m[1];
/* 1595 */     this.m02 = m[2];
/* 1596 */     this.m03 = m[3];
/* 1597 */     this.m10 = m[4];
/* 1598 */     this.m11 = m[5];
/* 1599 */     this.m12 = m[6];
/* 1600 */     this.m13 = m[7];
/* 1601 */     this.m20 = m[8];
/* 1602 */     this.m21 = m[9];
/* 1603 */     this.m22 = m[10];
/* 1604 */     this.m23 = m[11];
/* 1605 */     this.m30 = m[12];
/* 1606 */     this.m31 = m[13];
/* 1607 */     this.m32 = m[14];
/* 1608 */     this.m33 = m[15];
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
/* 1620 */     this.m00 = m1.m00;
/* 1621 */     this.m01 = m1.m01;
/* 1622 */     this.m02 = m1.m02;
/* 1623 */     this.m03 = 0.0D;
/* 1624 */     this.m10 = m1.m10;
/* 1625 */     this.m11 = m1.m11;
/* 1626 */     this.m12 = m1.m12;
/* 1627 */     this.m13 = 0.0D;
/* 1628 */     this.m20 = m1.m20;
/* 1629 */     this.m21 = m1.m21;
/* 1630 */     this.m22 = m1.m22;
/* 1631 */     this.m23 = 0.0D;
/* 1632 */     this.m30 = 0.0D;
/* 1633 */     this.m31 = 0.0D;
/* 1634 */     this.m32 = 0.0D;
/* 1635 */     this.m33 = 1.0D;
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
/* 1647 */     this.m00 = m1.m00;
/* 1648 */     this.m01 = m1.m01;
/* 1649 */     this.m02 = m1.m02;
/* 1650 */     this.m03 = 0.0D;
/* 1651 */     this.m10 = m1.m10;
/* 1652 */     this.m11 = m1.m11;
/* 1653 */     this.m12 = m1.m12;
/* 1654 */     this.m13 = 0.0D;
/* 1655 */     this.m20 = m1.m20;
/* 1656 */     this.m21 = m1.m21;
/* 1657 */     this.m22 = m1.m22;
/* 1658 */     this.m23 = 0.0D;
/* 1659 */     this.m30 = 0.0D;
/* 1660 */     this.m31 = 0.0D;
/* 1661 */     this.m32 = 0.0D;
/* 1662 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/* 1672 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/* 1673 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1674 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1676 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1677 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/* 1678 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1680 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1681 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1682 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */     
/* 1684 */     this.m03 = 0.0D;
/* 1685 */     this.m13 = 0.0D;
/* 1686 */     this.m23 = 0.0D;
/*      */     
/* 1688 */     this.m30 = 0.0D;
/* 1689 */     this.m31 = 0.0D;
/* 1690 */     this.m32 = 0.0D;
/* 1691 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/* 1701 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/* 1703 */     if (mag < 1.0E-10D) {
/* 1704 */       this.m00 = 1.0D;
/* 1705 */       this.m01 = 0.0D;
/* 1706 */       this.m02 = 0.0D;
/*      */       
/* 1708 */       this.m10 = 0.0D;
/* 1709 */       this.m11 = 1.0D;
/* 1710 */       this.m12 = 0.0D;
/*      */       
/* 1712 */       this.m20 = 0.0D;
/* 1713 */       this.m21 = 0.0D;
/* 1714 */       this.m22 = 1.0D;
/*      */     } else {
/* 1716 */       mag = 1.0D / mag;
/* 1717 */       double ax = a1.x * mag;
/* 1718 */       double ay = a1.y * mag;
/* 1719 */       double az = a1.z * mag;
/*      */       
/* 1721 */       double sinTheta = Math.sin(a1.angle);
/* 1722 */       double cosTheta = Math.cos(a1.angle);
/* 1723 */       double t = 1.0D - cosTheta;
/*      */       
/* 1725 */       double xz = ax * az;
/* 1726 */       double xy = ax * ay;
/* 1727 */       double yz = ay * az;
/*      */       
/* 1729 */       this.m00 = t * ax * ax + cosTheta;
/* 1730 */       this.m01 = t * xy - sinTheta * az;
/* 1731 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1733 */       this.m10 = t * xy + sinTheta * az;
/* 1734 */       this.m11 = t * ay * ay + cosTheta;
/* 1735 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1737 */       this.m20 = t * xz - sinTheta * ay;
/* 1738 */       this.m21 = t * yz + sinTheta * ax;
/* 1739 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */     
/* 1742 */     this.m03 = 0.0D;
/* 1743 */     this.m13 = 0.0D;
/* 1744 */     this.m23 = 0.0D;
/*      */     
/* 1746 */     this.m30 = 0.0D;
/* 1747 */     this.m31 = 0.0D;
/* 1748 */     this.m32 = 0.0D;
/* 1749 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/* 1759 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/* 1760 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1761 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1763 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1764 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/* 1765 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1767 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1768 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1769 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */     
/* 1771 */     this.m03 = 0.0D;
/* 1772 */     this.m13 = 0.0D;
/* 1773 */     this.m23 = 0.0D;
/*      */     
/* 1775 */     this.m30 = 0.0D;
/* 1776 */     this.m31 = 0.0D;
/* 1777 */     this.m32 = 0.0D;
/* 1778 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/* 1788 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*      */     
/* 1790 */     if (mag < 1.0E-10D) {
/* 1791 */       this.m00 = 1.0D;
/* 1792 */       this.m01 = 0.0D;
/* 1793 */       this.m02 = 0.0D;
/*      */       
/* 1795 */       this.m10 = 0.0D;
/* 1796 */       this.m11 = 1.0D;
/* 1797 */       this.m12 = 0.0D;
/*      */       
/* 1799 */       this.m20 = 0.0D;
/* 1800 */       this.m21 = 0.0D;
/* 1801 */       this.m22 = 1.0D;
/*      */     } else {
/* 1803 */       mag = 1.0D / mag;
/* 1804 */       double ax = a1.x * mag;
/* 1805 */       double ay = a1.y * mag;
/* 1806 */       double az = a1.z * mag;
/*      */       
/* 1808 */       double sinTheta = Math.sin(a1.angle);
/* 1809 */       double cosTheta = Math.cos(a1.angle);
/* 1810 */       double t = 1.0D - cosTheta;
/*      */       
/* 1812 */       double xz = ax * az;
/* 1813 */       double xy = ax * ay;
/* 1814 */       double yz = ay * az;
/*      */       
/* 1816 */       this.m00 = t * ax * ax + cosTheta;
/* 1817 */       this.m01 = t * xy - sinTheta * az;
/* 1818 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1820 */       this.m10 = t * xy + sinTheta * az;
/* 1821 */       this.m11 = t * ay * ay + cosTheta;
/* 1822 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1824 */       this.m20 = t * xz - sinTheta * ay;
/* 1825 */       this.m21 = t * yz + sinTheta * ax;
/* 1826 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/* 1828 */     this.m03 = 0.0D;
/* 1829 */     this.m13 = 0.0D;
/* 1830 */     this.m23 = 0.0D;
/*      */     
/* 1832 */     this.m30 = 0.0D;
/* 1833 */     this.m31 = 0.0D;
/* 1834 */     this.m32 = 0.0D;
/* 1835 */     this.m33 = 1.0D;
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
/* 1847 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1848 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1849 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1851 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1852 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1853 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1855 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1856 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1857 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1859 */     this.m03 = t1.x;
/* 1860 */     this.m13 = t1.y;
/* 1861 */     this.m23 = t1.z;
/*      */     
/* 1863 */     this.m30 = 0.0D;
/* 1864 */     this.m31 = 0.0D;
/* 1865 */     this.m32 = 0.0D;
/* 1866 */     this.m33 = 1.0D;
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
/*      */   public final void set(Quat4f q1, Vector3d t1, double s) {
/* 1878 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1879 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1880 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1882 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1883 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1884 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1886 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1887 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1888 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1890 */     this.m03 = t1.x;
/* 1891 */     this.m13 = t1.y;
/* 1892 */     this.m23 = t1.z;
/*      */     
/* 1894 */     this.m30 = 0.0D;
/* 1895 */     this.m31 = 0.0D;
/* 1896 */     this.m32 = 0.0D;
/* 1897 */     this.m33 = 1.0D;
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
/* 1909 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1910 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1911 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1913 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1914 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1915 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1917 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1918 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1919 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1921 */     this.m03 = t1.x;
/* 1922 */     this.m13 = t1.y;
/* 1923 */     this.m23 = t1.z;
/*      */     
/* 1925 */     this.m30 = 0.0D;
/* 1926 */     this.m31 = 0.0D;
/* 1927 */     this.m32 = 0.0D;
/* 1928 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/* 1938 */     this.m00 = m1.m00;
/* 1939 */     this.m01 = m1.m01;
/* 1940 */     this.m02 = m1.m02;
/* 1941 */     this.m03 = m1.m03;
/*      */     
/* 1943 */     this.m10 = m1.m10;
/* 1944 */     this.m11 = m1.m11;
/* 1945 */     this.m12 = m1.m12;
/* 1946 */     this.m13 = m1.m13;
/*      */     
/* 1948 */     this.m20 = m1.m20;
/* 1949 */     this.m21 = m1.m21;
/* 1950 */     this.m22 = m1.m22;
/* 1951 */     this.m23 = m1.m23;
/*      */     
/* 1953 */     this.m30 = m1.m30;
/* 1954 */     this.m31 = m1.m31;
/* 1955 */     this.m32 = m1.m32;
/* 1956 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/* 1966 */     this.m00 = m1.m00;
/* 1967 */     this.m01 = m1.m01;
/* 1968 */     this.m02 = m1.m02;
/* 1969 */     this.m03 = m1.m03;
/*      */     
/* 1971 */     this.m10 = m1.m10;
/* 1972 */     this.m11 = m1.m11;
/* 1973 */     this.m12 = m1.m12;
/* 1974 */     this.m13 = m1.m13;
/*      */     
/* 1976 */     this.m20 = m1.m20;
/* 1977 */     this.m21 = m1.m21;
/* 1978 */     this.m22 = m1.m22;
/* 1979 */     this.m23 = m1.m23;
/*      */     
/* 1981 */     this.m30 = m1.m30;
/* 1982 */     this.m31 = m1.m31;
/* 1983 */     this.m32 = m1.m32;
/* 1984 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4d m1) {
/* 1995 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 2002 */     invertGeneral(this);
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
/*      */   final void invertGeneral(Matrix4d m1) {
/* 2014 */     double[] result = new double[16];
/* 2015 */     int[] row_perm = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2020 */     double[] tmp = new double[16];
/*      */     
/* 2022 */     tmp[0] = m1.m00;
/* 2023 */     tmp[1] = m1.m01;
/* 2024 */     tmp[2] = m1.m02;
/* 2025 */     tmp[3] = m1.m03;
/*      */     
/* 2027 */     tmp[4] = m1.m10;
/* 2028 */     tmp[5] = m1.m11;
/* 2029 */     tmp[6] = m1.m12;
/* 2030 */     tmp[7] = m1.m13;
/*      */     
/* 2032 */     tmp[8] = m1.m20;
/* 2033 */     tmp[9] = m1.m21;
/* 2034 */     tmp[10] = m1.m22;
/* 2035 */     tmp[11] = m1.m23;
/*      */     
/* 2037 */     tmp[12] = m1.m30;
/* 2038 */     tmp[13] = m1.m31;
/* 2039 */     tmp[14] = m1.m32;
/* 2040 */     tmp[15] = m1.m33;
/*      */ 
/*      */     
/* 2043 */     if (!luDecomposition(tmp, row_perm))
/*      */     {
/* 2045 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4d10"));
/*      */     }
/*      */ 
/*      */     
/* 2049 */     for (int i = 0; i < 16; ) { result[i] = 0.0D; i++; }
/* 2050 */      result[0] = 1.0D;
/* 2051 */     result[5] = 1.0D;
/* 2052 */     result[10] = 1.0D;
/* 2053 */     result[15] = 1.0D;
/* 2054 */     luBacksubstitution(tmp, row_perm, result);
/*      */     
/* 2056 */     this.m00 = result[0];
/* 2057 */     this.m01 = result[1];
/* 2058 */     this.m02 = result[2];
/* 2059 */     this.m03 = result[3];
/*      */     
/* 2061 */     this.m10 = result[4];
/* 2062 */     this.m11 = result[5];
/* 2063 */     this.m12 = result[6];
/* 2064 */     this.m13 = result[7];
/*      */     
/* 2066 */     this.m20 = result[8];
/* 2067 */     this.m21 = result[9];
/* 2068 */     this.m22 = result[10];
/* 2069 */     this.m23 = result[11];
/*      */     
/* 2071 */     this.m30 = result[12];
/* 2072 */     this.m31 = result[13];
/* 2073 */     this.m32 = result[14];
/* 2074 */     this.m33 = result[15];
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
/* 2101 */     double[] row_scale = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2109 */     int ptr = 0;
/* 2110 */     int rs = 0;
/*      */ 
/*      */     
/* 2113 */     int i = 4;
/* 2114 */     while (i-- != 0) {
/* 2115 */       double big = 0.0D;
/*      */ 
/*      */       
/* 2118 */       int k = 4;
/* 2119 */       while (k-- != 0) {
/* 2120 */         double temp = matrix0[ptr++];
/* 2121 */         temp = Math.abs(temp);
/* 2122 */         if (temp > big) {
/* 2123 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2128 */       if (big == 0.0D) {
/* 2129 */         return false;
/*      */       }
/* 2131 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2139 */     int mtx = 0;
/*      */ 
/*      */     
/* 2142 */     for (int j = 0; j < 4; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2148 */       for (k = 0; k < j; k++) {
/* 2149 */         int target = mtx + 4 * k + j;
/* 2150 */         double sum = matrix0[target];
/* 2151 */         int m = k;
/* 2152 */         int p1 = mtx + 4 * k;
/* 2153 */         int p2 = mtx + j;
/* 2154 */         while (m-- != 0) {
/* 2155 */           sum -= matrix0[p1] * matrix0[p2];
/* 2156 */           p1++;
/* 2157 */           p2 += 4;
/*      */         } 
/* 2159 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2164 */       double big = 0.0D;
/* 2165 */       int imax = -1;
/* 2166 */       for (k = j; k < 4; k++) {
/* 2167 */         int target = mtx + 4 * k + j;
/* 2168 */         double sum = matrix0[target];
/* 2169 */         int m = j;
/* 2170 */         int p1 = mtx + 4 * k;
/* 2171 */         int p2 = mtx + j;
/* 2172 */         while (m-- != 0) {
/* 2173 */           sum -= matrix0[p1] * matrix0[p2];
/* 2174 */           p1++;
/* 2175 */           p2 += 4;
/*      */         } 
/* 2177 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 2180 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 2181 */           big = temp;
/* 2182 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 2186 */       if (imax < 0) {
/* 2187 */         throw new RuntimeException(VecMathI18N.getString("Matrix4d11"));
/*      */       }
/*      */ 
/*      */       
/* 2191 */       if (j != imax) {
/*      */         
/* 2193 */         int m = 4;
/* 2194 */         int p1 = mtx + 4 * imax;
/* 2195 */         int p2 = mtx + 4 * j;
/* 2196 */         while (m-- != 0) {
/* 2197 */           double temp = matrix0[p1];
/* 2198 */           matrix0[p1++] = matrix0[p2];
/* 2199 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 2203 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 2207 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 2210 */       if (matrix0[mtx + 4 * j + j] == 0.0D) {
/* 2211 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 2215 */       if (j != 3) {
/* 2216 */         double temp = 1.0D / matrix0[mtx + 4 * j + j];
/* 2217 */         int target = mtx + 4 * (j + 1) + j;
/* 2218 */         k = 3 - j;
/* 2219 */         while (k-- != 0) {
/* 2220 */           matrix0[target] = matrix0[target] * temp;
/* 2221 */           target += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2227 */     return true;
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
/* 2257 */     int rp = 0;
/*      */ 
/*      */     
/* 2260 */     for (int k = 0; k < 4; k++) {
/*      */       
/* 2262 */       int cv = k;
/* 2263 */       int ii = -1;
/*      */ 
/*      */       
/* 2266 */       for (int i = 0; i < 4; i++) {
/*      */ 
/*      */         
/* 2269 */         int ip = row_perm[rp + i];
/* 2270 */         double sum = matrix2[cv + 4 * ip];
/* 2271 */         matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
/* 2272 */         if (ii >= 0) {
/*      */           
/* 2274 */           int m = i * 4;
/* 2275 */           for (int j = ii; j <= i - 1; j++) {
/* 2276 */             sum -= matrix1[m + j] * matrix2[cv + 4 * j];
/*      */           }
/* 2278 */         } else if (sum != 0.0D) {
/* 2279 */           ii = i;
/*      */         } 
/* 2281 */         matrix2[cv + 4 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2286 */       int rv = 12;
/* 2287 */       matrix2[cv + 12] = matrix2[cv + 12] / matrix1[rv + 3];
/*      */       
/* 2289 */       rv -= 4;
/* 2290 */       matrix2[cv + 8] = (matrix2[cv + 8] - 
/* 2291 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
/*      */       
/* 2293 */       rv -= 4;
/* 2294 */       matrix2[cv + 4] = (matrix2[cv + 4] - 
/* 2295 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 2296 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
/*      */       
/* 2298 */       rv -= 4;
/* 2299 */       matrix2[cv + 0] = (matrix2[cv + 0] - 
/* 2300 */         matrix1[rv + 1] * matrix2[cv + 4] - 
/* 2301 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 2302 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 0];
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
/*      */   public final double determinant() {
/* 2316 */     double det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - 
/* 2317 */       this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/* 2318 */     det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - 
/* 2319 */       this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/* 2320 */     det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - 
/* 2321 */       this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/* 2322 */     det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - 
/* 2323 */       this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */     
/* 2325 */     return det;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double scale) {
/* 2335 */     this.m00 = scale;
/* 2336 */     this.m01 = 0.0D;
/* 2337 */     this.m02 = 0.0D;
/* 2338 */     this.m03 = 0.0D;
/*      */     
/* 2340 */     this.m10 = 0.0D;
/* 2341 */     this.m11 = scale;
/* 2342 */     this.m12 = 0.0D;
/* 2343 */     this.m13 = 0.0D;
/*      */     
/* 2345 */     this.m20 = 0.0D;
/* 2346 */     this.m21 = 0.0D;
/* 2347 */     this.m22 = scale;
/* 2348 */     this.m23 = 0.0D;
/*      */     
/* 2350 */     this.m30 = 0.0D;
/* 2351 */     this.m31 = 0.0D;
/* 2352 */     this.m32 = 0.0D;
/* 2353 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3d v1) {
/* 2363 */     this.m00 = 1.0D;
/* 2364 */     this.m01 = 0.0D;
/* 2365 */     this.m02 = 0.0D;
/* 2366 */     this.m03 = v1.x;
/*      */     
/* 2368 */     this.m10 = 0.0D;
/* 2369 */     this.m11 = 1.0D;
/* 2370 */     this.m12 = 0.0D;
/* 2371 */     this.m13 = v1.y;
/*      */     
/* 2373 */     this.m20 = 0.0D;
/* 2374 */     this.m21 = 0.0D;
/* 2375 */     this.m22 = 1.0D;
/* 2376 */     this.m23 = v1.z;
/*      */     
/* 2378 */     this.m30 = 0.0D;
/* 2379 */     this.m31 = 0.0D;
/* 2380 */     this.m32 = 0.0D;
/* 2381 */     this.m33 = 1.0D;
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
/*      */   public final void set(double scale, Vector3d v1) {
/* 2393 */     this.m00 = scale;
/* 2394 */     this.m01 = 0.0D;
/* 2395 */     this.m02 = 0.0D;
/* 2396 */     this.m03 = v1.x;
/*      */     
/* 2398 */     this.m10 = 0.0D;
/* 2399 */     this.m11 = scale;
/* 2400 */     this.m12 = 0.0D;
/* 2401 */     this.m13 = v1.y;
/*      */     
/* 2403 */     this.m20 = 0.0D;
/* 2404 */     this.m21 = 0.0D;
/* 2405 */     this.m22 = scale;
/* 2406 */     this.m23 = v1.z;
/*      */     
/* 2408 */     this.m30 = 0.0D;
/* 2409 */     this.m31 = 0.0D;
/* 2410 */     this.m32 = 0.0D;
/* 2411 */     this.m33 = 1.0D;
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
/*      */   public final void set(Vector3d v1, double scale) {
/* 2423 */     this.m00 = scale;
/* 2424 */     this.m01 = 0.0D;
/* 2425 */     this.m02 = 0.0D;
/* 2426 */     this.m03 = scale * v1.x;
/*      */     
/* 2428 */     this.m10 = 0.0D;
/* 2429 */     this.m11 = scale;
/* 2430 */     this.m12 = 0.0D;
/* 2431 */     this.m13 = scale * v1.y;
/*      */     
/* 2433 */     this.m20 = 0.0D;
/* 2434 */     this.m21 = 0.0D;
/* 2435 */     this.m22 = scale;
/* 2436 */     this.m23 = scale * v1.z;
/*      */     
/* 2438 */     this.m30 = 0.0D;
/* 2439 */     this.m31 = 0.0D;
/* 2440 */     this.m32 = 0.0D;
/* 2441 */     this.m33 = 1.0D;
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
/* 2454 */     this.m00 = (m1.m00 * scale);
/* 2455 */     this.m01 = (m1.m01 * scale);
/* 2456 */     this.m02 = (m1.m02 * scale);
/* 2457 */     this.m03 = t1.x;
/*      */     
/* 2459 */     this.m10 = (m1.m10 * scale);
/* 2460 */     this.m11 = (m1.m11 * scale);
/* 2461 */     this.m12 = (m1.m12 * scale);
/* 2462 */     this.m13 = t1.y;
/*      */     
/* 2464 */     this.m20 = (m1.m20 * scale);
/* 2465 */     this.m21 = (m1.m21 * scale);
/* 2466 */     this.m22 = (m1.m22 * scale);
/* 2467 */     this.m23 = t1.z;
/*      */     
/* 2469 */     this.m30 = 0.0D;
/* 2470 */     this.m31 = 0.0D;
/* 2471 */     this.m32 = 0.0D;
/* 2472 */     this.m33 = 1.0D;
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
/*      */   public final void set(Matrix3d m1, Vector3d t1, double scale) {
/* 2486 */     this.m00 = m1.m00 * scale;
/* 2487 */     this.m01 = m1.m01 * scale;
/* 2488 */     this.m02 = m1.m02 * scale;
/* 2489 */     this.m03 = t1.x;
/*      */     
/* 2491 */     this.m10 = m1.m10 * scale;
/* 2492 */     this.m11 = m1.m11 * scale;
/* 2493 */     this.m12 = m1.m12 * scale;
/* 2494 */     this.m13 = t1.y;
/*      */     
/* 2496 */     this.m20 = m1.m20 * scale;
/* 2497 */     this.m21 = m1.m21 * scale;
/* 2498 */     this.m22 = m1.m22 * scale;
/* 2499 */     this.m23 = t1.z;
/*      */     
/* 2501 */     this.m30 = 0.0D;
/* 2502 */     this.m31 = 0.0D;
/* 2503 */     this.m32 = 0.0D;
/* 2504 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3d trans) {
/* 2515 */     this.m03 = trans.x;
/* 2516 */     this.m13 = trans.y;
/* 2517 */     this.m23 = trans.z;
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
/*      */   public final void rotX(double angle) {
/* 2529 */     double sinAngle = Math.sin(angle);
/* 2530 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2532 */     this.m00 = 1.0D;
/* 2533 */     this.m01 = 0.0D;
/* 2534 */     this.m02 = 0.0D;
/* 2535 */     this.m03 = 0.0D;
/*      */     
/* 2537 */     this.m10 = 0.0D;
/* 2538 */     this.m11 = cosAngle;
/* 2539 */     this.m12 = -sinAngle;
/* 2540 */     this.m13 = 0.0D;
/*      */     
/* 2542 */     this.m20 = 0.0D;
/* 2543 */     this.m21 = sinAngle;
/* 2544 */     this.m22 = cosAngle;
/* 2545 */     this.m23 = 0.0D;
/*      */     
/* 2547 */     this.m30 = 0.0D;
/* 2548 */     this.m31 = 0.0D;
/* 2549 */     this.m32 = 0.0D;
/* 2550 */     this.m33 = 1.0D;
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
/*      */   public final void rotY(double angle) {
/* 2562 */     double sinAngle = Math.sin(angle);
/* 2563 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2565 */     this.m00 = cosAngle;
/* 2566 */     this.m01 = 0.0D;
/* 2567 */     this.m02 = sinAngle;
/* 2568 */     this.m03 = 0.0D;
/*      */     
/* 2570 */     this.m10 = 0.0D;
/* 2571 */     this.m11 = 1.0D;
/* 2572 */     this.m12 = 0.0D;
/* 2573 */     this.m13 = 0.0D;
/*      */     
/* 2575 */     this.m20 = -sinAngle;
/* 2576 */     this.m21 = 0.0D;
/* 2577 */     this.m22 = cosAngle;
/* 2578 */     this.m23 = 0.0D;
/*      */     
/* 2580 */     this.m30 = 0.0D;
/* 2581 */     this.m31 = 0.0D;
/* 2582 */     this.m32 = 0.0D;
/* 2583 */     this.m33 = 1.0D;
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
/*      */   public final void rotZ(double angle) {
/* 2595 */     double sinAngle = Math.sin(angle);
/* 2596 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2598 */     this.m00 = cosAngle;
/* 2599 */     this.m01 = -sinAngle;
/* 2600 */     this.m02 = 0.0D;
/* 2601 */     this.m03 = 0.0D;
/*      */     
/* 2603 */     this.m10 = sinAngle;
/* 2604 */     this.m11 = cosAngle;
/* 2605 */     this.m12 = 0.0D;
/* 2606 */     this.m13 = 0.0D;
/*      */     
/* 2608 */     this.m20 = 0.0D;
/* 2609 */     this.m21 = 0.0D;
/* 2610 */     this.m22 = 1.0D;
/* 2611 */     this.m23 = 0.0D;
/*      */     
/* 2613 */     this.m30 = 0.0D;
/* 2614 */     this.m31 = 0.0D;
/* 2615 */     this.m32 = 0.0D;
/* 2616 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar) {
/* 2625 */     this.m00 *= scalar;
/* 2626 */     this.m01 *= scalar;
/* 2627 */     this.m02 *= scalar;
/* 2628 */     this.m03 *= scalar;
/* 2629 */     this.m10 *= scalar;
/* 2630 */     this.m11 *= scalar;
/* 2631 */     this.m12 *= scalar;
/* 2632 */     this.m13 *= scalar;
/* 2633 */     this.m20 *= scalar;
/* 2634 */     this.m21 *= scalar;
/* 2635 */     this.m22 *= scalar;
/* 2636 */     this.m23 *= scalar;
/* 2637 */     this.m30 *= scalar;
/* 2638 */     this.m31 *= scalar;
/* 2639 */     this.m32 *= scalar;
/* 2640 */     this.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar, Matrix4d m1) {
/* 2651 */     m1.m00 *= scalar;
/* 2652 */     m1.m01 *= scalar;
/* 2653 */     m1.m02 *= scalar;
/* 2654 */     m1.m03 *= scalar;
/* 2655 */     m1.m10 *= scalar;
/* 2656 */     m1.m11 *= scalar;
/* 2657 */     m1.m12 *= scalar;
/* 2658 */     m1.m13 *= scalar;
/* 2659 */     m1.m20 *= scalar;
/* 2660 */     m1.m21 *= scalar;
/* 2661 */     m1.m22 *= scalar;
/* 2662 */     m1.m23 *= scalar;
/* 2663 */     m1.m30 *= scalar;
/* 2664 */     m1.m31 *= scalar;
/* 2665 */     m1.m32 *= scalar;
/* 2666 */     m1.m33 *= scalar;
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
/*      */   public final void mul(Matrix4d m1) {
/* 2681 */     double m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + 
/* 2682 */       this.m02 * m1.m20 + this.m03 * m1.m30;
/* 2683 */     double m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + 
/* 2684 */       this.m02 * m1.m21 + this.m03 * m1.m31;
/* 2685 */     double m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + 
/* 2686 */       this.m02 * m1.m22 + this.m03 * m1.m32;
/* 2687 */     double m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + 
/* 2688 */       this.m02 * m1.m23 + this.m03 * m1.m33;
/*      */     
/* 2690 */     double m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + 
/* 2691 */       this.m12 * m1.m20 + this.m13 * m1.m30;
/* 2692 */     double m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + 
/* 2693 */       this.m12 * m1.m21 + this.m13 * m1.m31;
/* 2694 */     double m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + 
/* 2695 */       this.m12 * m1.m22 + this.m13 * m1.m32;
/* 2696 */     double m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + 
/* 2697 */       this.m12 * m1.m23 + this.m13 * m1.m33;
/*      */     
/* 2699 */     double m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + 
/* 2700 */       this.m22 * m1.m20 + this.m23 * m1.m30;
/* 2701 */     double m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + 
/* 2702 */       this.m22 * m1.m21 + this.m23 * m1.m31;
/* 2703 */     double m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + 
/* 2704 */       this.m22 * m1.m22 + this.m23 * m1.m32;
/* 2705 */     double m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + 
/* 2706 */       this.m22 * m1.m23 + this.m23 * m1.m33;
/*      */     
/* 2708 */     double m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + 
/* 2709 */       this.m32 * m1.m20 + this.m33 * m1.m30;
/* 2710 */     double m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + 
/* 2711 */       this.m32 * m1.m21 + this.m33 * m1.m31;
/* 2712 */     double m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + 
/* 2713 */       this.m32 * m1.m22 + this.m33 * m1.m32;
/* 2714 */     double m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + 
/* 2715 */       this.m32 * m1.m23 + this.m33 * m1.m33;
/*      */     
/* 2717 */     this.m00 = m00;
/* 2718 */     this.m01 = m01;
/* 2719 */     this.m02 = m02;
/* 2720 */     this.m03 = m03;
/* 2721 */     this.m10 = m10;
/* 2722 */     this.m11 = m11;
/* 2723 */     this.m12 = m12;
/* 2724 */     this.m13 = m13;
/* 2725 */     this.m20 = m20;
/* 2726 */     this.m21 = m21;
/* 2727 */     this.m22 = m22;
/* 2728 */     this.m23 = m23;
/* 2729 */     this.m30 = m30;
/* 2730 */     this.m31 = m31;
/* 2731 */     this.m32 = m32;
/* 2732 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4d m1, Matrix4d m2) {
/* 2743 */     if (this != m1 && this != m2) {
/*      */       
/* 2745 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + 
/* 2746 */         m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2747 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + 
/* 2748 */         m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2749 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + 
/* 2750 */         m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2751 */       this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + 
/* 2752 */         m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2754 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + 
/* 2755 */         m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2756 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + 
/* 2757 */         m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2758 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + 
/* 2759 */         m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2760 */       this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + 
/* 2761 */         m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2763 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + 
/* 2764 */         m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2765 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + 
/* 2766 */         m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2767 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + 
/* 2768 */         m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2769 */       this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + 
/* 2770 */         m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2772 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + 
/* 2773 */         m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2774 */       this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + 
/* 2775 */         m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2776 */       this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + 
/* 2777 */         m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2778 */       this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + 
/* 2779 */         m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2787 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2788 */       double m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2789 */       double m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2790 */       double m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2792 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2793 */       double m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2794 */       double m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2795 */       double m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2797 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2798 */       double m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2799 */       double m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2800 */       double m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2802 */       double m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2803 */       double m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2804 */       double m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2805 */       double m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2807 */       this.m00 = m00;
/* 2808 */       this.m01 = m01;
/* 2809 */       this.m02 = m02;
/* 2810 */       this.m03 = m03;
/* 2811 */       this.m10 = m10;
/* 2812 */       this.m11 = m11;
/* 2813 */       this.m12 = m12;
/* 2814 */       this.m13 = m13;
/* 2815 */       this.m20 = m20;
/* 2816 */       this.m21 = m21;
/* 2817 */       this.m22 = m22;
/* 2818 */       this.m23 = m23;
/* 2819 */       this.m30 = m30;
/* 2820 */       this.m31 = m31;
/* 2821 */       this.m32 = m32;
/* 2822 */       this.m33 = m33;
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
/*      */   public final void mulTransposeBoth(Matrix4d m1, Matrix4d m2) {
/* 2835 */     if (this != m1 && this != m2) {
/* 2836 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2837 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2838 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2839 */       this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2841 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2842 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2843 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2844 */       this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2846 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2847 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2848 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2849 */       this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2851 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2852 */       this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2853 */       this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2854 */       this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2861 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2862 */       double m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2863 */       double m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2864 */       double m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2866 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2867 */       double m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2868 */       double m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2869 */       double m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2871 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2872 */       double m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2873 */       double m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2874 */       double m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2876 */       double m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2877 */       double m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2878 */       double m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2879 */       double m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2881 */       this.m00 = m00;
/* 2882 */       this.m01 = m01;
/* 2883 */       this.m02 = m02;
/* 2884 */       this.m03 = m03;
/* 2885 */       this.m10 = m10;
/* 2886 */       this.m11 = m11;
/* 2887 */       this.m12 = m12;
/* 2888 */       this.m13 = m13;
/* 2889 */       this.m20 = m20;
/* 2890 */       this.m21 = m21;
/* 2891 */       this.m22 = m22;
/* 2892 */       this.m23 = m23;
/* 2893 */       this.m30 = m30;
/* 2894 */       this.m31 = m31;
/* 2895 */       this.m32 = m32;
/* 2896 */       this.m33 = m33;
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
/*      */   public final void mulTransposeRight(Matrix4d m1, Matrix4d m2) {
/* 2910 */     if (this != m1 && this != m2) {
/* 2911 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2912 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2913 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2914 */       this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2916 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2917 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2918 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2919 */       this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2921 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2922 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2923 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2924 */       this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2926 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2927 */       this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2928 */       this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2929 */       this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2936 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2937 */       double m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2938 */       double m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2939 */       double m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2941 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2942 */       double m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2943 */       double m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2944 */       double m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2946 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2947 */       double m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2948 */       double m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2949 */       double m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2951 */       double m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2952 */       double m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2953 */       double m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2954 */       double m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2956 */       this.m00 = m00;
/* 2957 */       this.m01 = m01;
/* 2958 */       this.m02 = m02;
/* 2959 */       this.m03 = m03;
/* 2960 */       this.m10 = m10;
/* 2961 */       this.m11 = m11;
/* 2962 */       this.m12 = m12;
/* 2963 */       this.m13 = m13;
/* 2964 */       this.m20 = m20;
/* 2965 */       this.m21 = m21;
/* 2966 */       this.m22 = m22;
/* 2967 */       this.m23 = m23;
/* 2968 */       this.m30 = m30;
/* 2969 */       this.m31 = m31;
/* 2970 */       this.m32 = m32;
/* 2971 */       this.m33 = m33;
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
/*      */   public final void mulTransposeLeft(Matrix4d m1, Matrix4d m2) {
/* 2984 */     if (this != m1 && this != m2) {
/* 2985 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2986 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2987 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2988 */       this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2990 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2991 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2992 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2993 */       this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2995 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2996 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2997 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2998 */       this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 3000 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 3001 */       this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 3002 */       this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 3003 */       this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 3011 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 3012 */       double m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 3013 */       double m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 3014 */       double m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 3016 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 3017 */       double m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 3018 */       double m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 3019 */       double m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 3021 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 3022 */       double m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 3023 */       double m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 3024 */       double m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 3026 */       double m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 3027 */       double m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 3028 */       double m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 3029 */       double m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 3031 */       this.m00 = m00;
/* 3032 */       this.m01 = m01;
/* 3033 */       this.m02 = m02;
/* 3034 */       this.m03 = m03;
/* 3035 */       this.m10 = m10;
/* 3036 */       this.m11 = m11;
/* 3037 */       this.m12 = m12;
/* 3038 */       this.m13 = m13;
/* 3039 */       this.m20 = m20;
/* 3040 */       this.m21 = m21;
/* 3041 */       this.m22 = m22;
/* 3042 */       this.m23 = m23;
/* 3043 */       this.m30 = m30;
/* 3044 */       this.m31 = m31;
/* 3045 */       this.m32 = m32;
/* 3046 */       this.m33 = m33;
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
/*      */   public boolean equals(Matrix4d m1) {
/*      */     try {
/* 3061 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && 
/* 3062 */         this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && 
/* 3063 */         this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && 
/* 3064 */         this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && 
/* 3065 */         this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && 
/* 3066 */         this.m33 == m1.m33);
/* 3067 */     } catch (NullPointerException e2) {
/* 3068 */       return false;
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
/* 3084 */       Matrix4d m2 = (Matrix4d)t1;
/* 3085 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && 
/* 3086 */         this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && 
/* 3087 */         this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && 
/* 3088 */         this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && 
/* 3089 */         this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && 
/* 3090 */         this.m33 == m2.m33);
/* 3091 */     } catch (ClassCastException e1) {
/* 3092 */       return false;
/* 3093 */     } catch (NullPointerException e2) {
/* 3094 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix4d m1, float epsilon) {
/* 3102 */     return epsilonEquals(m1, epsilon);
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
/*      */   public boolean epsilonEquals(Matrix4d m1, double epsilon) {
/* 3118 */     double diff = this.m00 - m1.m00;
/* 3119 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3121 */     diff = this.m01 - m1.m01;
/* 3122 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3124 */     diff = this.m02 - m1.m02;
/* 3125 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3127 */     diff = this.m03 - m1.m03;
/* 3128 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3130 */     diff = this.m10 - m1.m10;
/* 3131 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3133 */     diff = this.m11 - m1.m11;
/* 3134 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3136 */     diff = this.m12 - m1.m12;
/* 3137 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3139 */     diff = this.m13 - m1.m13;
/* 3140 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3142 */     diff = this.m20 - m1.m20;
/* 3143 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3145 */     diff = this.m21 - m1.m21;
/* 3146 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3148 */     diff = this.m22 - m1.m22;
/* 3149 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3151 */     diff = this.m23 - m1.m23;
/* 3152 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3154 */     diff = this.m30 - m1.m30;
/* 3155 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3157 */     diff = this.m31 - m1.m31;
/* 3158 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3160 */     diff = this.m32 - m1.m32;
/* 3161 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3163 */     diff = this.m33 - m1.m33;
/* 3164 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3166 */     return true;
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
/*      */   public int hashCode() {
/* 3180 */     long bits = 1L;
/* 3181 */     bits = VecMathUtil.hashDoubleBits(bits, this.m00);
/* 3182 */     bits = VecMathUtil.hashDoubleBits(bits, this.m01);
/* 3183 */     bits = VecMathUtil.hashDoubleBits(bits, this.m02);
/* 3184 */     bits = VecMathUtil.hashDoubleBits(bits, this.m03);
/* 3185 */     bits = VecMathUtil.hashDoubleBits(bits, this.m10);
/* 3186 */     bits = VecMathUtil.hashDoubleBits(bits, this.m11);
/* 3187 */     bits = VecMathUtil.hashDoubleBits(bits, this.m12);
/* 3188 */     bits = VecMathUtil.hashDoubleBits(bits, this.m13);
/* 3189 */     bits = VecMathUtil.hashDoubleBits(bits, this.m20);
/* 3190 */     bits = VecMathUtil.hashDoubleBits(bits, this.m21);
/* 3191 */     bits = VecMathUtil.hashDoubleBits(bits, this.m22);
/* 3192 */     bits = VecMathUtil.hashDoubleBits(bits, this.m23);
/* 3193 */     bits = VecMathUtil.hashDoubleBits(bits, this.m30);
/* 3194 */     bits = VecMathUtil.hashDoubleBits(bits, this.m31);
/* 3195 */     bits = VecMathUtil.hashDoubleBits(bits, this.m32);
/* 3196 */     bits = VecMathUtil.hashDoubleBits(bits, this.m33);
/* 3197 */     return VecMathUtil.hashFinish(bits);
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
/*      */   public final void transform(Tuple4d vec, Tuple4d vecOut) {
/* 3210 */     double x = this.m00 * vec.x + this.m01 * vec.y + 
/* 3211 */       this.m02 * vec.z + this.m03 * vec.w;
/* 3212 */     double y = this.m10 * vec.x + this.m11 * vec.y + 
/* 3213 */       this.m12 * vec.z + this.m13 * vec.w;
/* 3214 */     double z = this.m20 * vec.x + this.m21 * vec.y + 
/* 3215 */       this.m22 * vec.z + this.m23 * vec.w;
/* 3216 */     vecOut.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 3217 */       this.m32 * vec.z + this.m33 * vec.w;
/* 3218 */     vecOut.x = x;
/* 3219 */     vecOut.y = y;
/* 3220 */     vecOut.z = z;
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
/*      */   public final void transform(Tuple4d vec) {
/* 3232 */     double x = this.m00 * vec.x + this.m01 * vec.y + 
/* 3233 */       this.m02 * vec.z + this.m03 * vec.w;
/* 3234 */     double y = this.m10 * vec.x + this.m11 * vec.y + 
/* 3235 */       this.m12 * vec.z + this.m13 * vec.w;
/* 3236 */     double z = this.m20 * vec.x + this.m21 * vec.y + 
/* 3237 */       this.m22 * vec.z + this.m23 * vec.w;
/* 3238 */     vec.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 3239 */       this.m32 * vec.z + this.m33 * vec.w;
/* 3240 */     vec.x = x;
/* 3241 */     vec.y = y;
/* 3242 */     vec.z = z;
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
/*      */   public final void transform(Tuple4f vec, Tuple4f vecOut) {
/* 3254 */     float x = (float)(this.m00 * vec.x + this.m01 * vec.y + 
/* 3255 */       this.m02 * vec.z + this.m03 * vec.w);
/* 3256 */     float y = (float)(this.m10 * vec.x + this.m11 * vec.y + 
/* 3257 */       this.m12 * vec.z + this.m13 * vec.w);
/* 3258 */     float z = (float)(this.m20 * vec.x + this.m21 * vec.y + 
/* 3259 */       this.m22 * vec.z + this.m23 * vec.w);
/* 3260 */     vecOut.w = 
/* 3261 */       (float)(this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w);
/* 3262 */     vecOut.x = x;
/* 3263 */     vecOut.y = y;
/* 3264 */     vecOut.z = z;
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
/*      */   public final void transform(Tuple4f vec) {
/* 3276 */     float x = (float)(this.m00 * vec.x + this.m01 * vec.y + 
/* 3277 */       this.m02 * vec.z + this.m03 * vec.w);
/* 3278 */     float y = (float)(this.m10 * vec.x + this.m11 * vec.y + 
/* 3279 */       this.m12 * vec.z + this.m13 * vec.w);
/* 3280 */     float z = (float)(this.m20 * vec.x + this.m21 * vec.y + 
/* 3281 */       this.m22 * vec.z + this.m23 * vec.w);
/* 3282 */     vec.w = 
/* 3283 */       (float)(this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w);
/* 3284 */     vec.x = x;
/* 3285 */     vec.y = y;
/* 3286 */     vec.z = z;
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
/*      */   public final void transform(Point3d point, Point3d pointOut) {
/* 3300 */     double x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3301 */     double y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3302 */     pointOut.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3303 */     pointOut.x = x;
/* 3304 */     pointOut.y = y;
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
/*      */   public final void transform(Point3d point) {
/* 3318 */     double x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3319 */     double y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3320 */     point.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3321 */     point.x = x;
/* 3322 */     point.y = y;
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
/*      */   public final void transform(Point3f point, Point3f pointOut) {
/* 3337 */     float x = (float)(this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03);
/* 3338 */     float y = (float)(this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13);
/* 3339 */     pointOut.z = (float)(this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23);
/* 3340 */     pointOut.x = x;
/* 3341 */     pointOut.y = y;
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
/* 3354 */     float x = (float)(this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03);
/* 3355 */     float y = (float)(this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13);
/* 3356 */     point.z = (float)(this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23);
/* 3357 */     point.x = x;
/* 3358 */     point.y = y;
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
/*      */   public final void transform(Vector3d normal, Vector3d normalOut) {
/* 3371 */     double x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3372 */     double y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3373 */     normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3374 */     normalOut.x = x;
/* 3375 */     normalOut.y = y;
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
/*      */   public final void transform(Vector3d normal) {
/* 3388 */     double x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3389 */     double y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3390 */     normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3391 */     normal.x = x;
/* 3392 */     normal.y = y;
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
/* 3405 */     float x = (float)(this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z);
/* 3406 */     float y = (float)(this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z);
/* 3407 */     normalOut.z = (float)(this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z);
/* 3408 */     normalOut.x = x;
/* 3409 */     normalOut.y = y;
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
/* 3422 */     float x = (float)(this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z);
/* 3423 */     float y = (float)(this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z);
/* 3424 */     normal.z = (float)(this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z);
/* 3425 */     normal.x = x;
/* 3426 */     normal.y = y;
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
/*      */   public final void setRotation(Matrix3d m1) {
/* 3441 */     double[] tmp_rot = new double[9];
/* 3442 */     double[] tmp_scale = new double[3];
/*      */     
/* 3444 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3446 */     this.m00 = m1.m00 * tmp_scale[0];
/* 3447 */     this.m01 = m1.m01 * tmp_scale[1];
/* 3448 */     this.m02 = m1.m02 * tmp_scale[2];
/*      */     
/* 3450 */     this.m10 = m1.m10 * tmp_scale[0];
/* 3451 */     this.m11 = m1.m11 * tmp_scale[1];
/* 3452 */     this.m12 = m1.m12 * tmp_scale[2];
/*      */     
/* 3454 */     this.m20 = m1.m20 * tmp_scale[0];
/* 3455 */     this.m21 = m1.m21 * tmp_scale[1];
/* 3456 */     this.m22 = m1.m22 * tmp_scale[2];
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
/*      */   public final void setRotation(Matrix3f m1) {
/* 3474 */     double[] tmp_rot = new double[9];
/* 3475 */     double[] tmp_scale = new double[3];
/* 3476 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3478 */     this.m00 = m1.m00 * tmp_scale[0];
/* 3479 */     this.m01 = m1.m01 * tmp_scale[1];
/* 3480 */     this.m02 = m1.m02 * tmp_scale[2];
/*      */     
/* 3482 */     this.m10 = m1.m10 * tmp_scale[0];
/* 3483 */     this.m11 = m1.m11 * tmp_scale[1];
/* 3484 */     this.m12 = m1.m12 * tmp_scale[2];
/*      */     
/* 3486 */     this.m20 = m1.m20 * tmp_scale[0];
/* 3487 */     this.m21 = m1.m21 * tmp_scale[1];
/* 3488 */     this.m22 = m1.m22 * tmp_scale[2];
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
/* 3503 */     double[] tmp_rot = new double[9];
/* 3504 */     double[] tmp_scale = new double[3];
/* 3505 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3507 */     this.m00 = (1.0D - (2.0F * q1.y * q1.y) - (2.0F * q1.z * q1.z)) * tmp_scale[0];
/* 3508 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0];
/* 3509 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0];
/*      */     
/* 3511 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1];
/* 3512 */     this.m11 = (1.0D - (2.0F * q1.x * q1.x) - (2.0F * q1.z * q1.z)) * tmp_scale[1];
/* 3513 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1];
/*      */     
/* 3515 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2];
/* 3516 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2];
/* 3517 */     this.m22 = (1.0D - (2.0F * q1.x * q1.x) - (2.0F * q1.y * q1.y)) * tmp_scale[2];
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
/*      */   public final void setRotation(Quat4d q1) {
/* 3535 */     double[] tmp_rot = new double[9];
/* 3536 */     double[] tmp_scale = new double[3];
/* 3537 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3539 */     this.m00 = (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z) * tmp_scale[0];
/* 3540 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0];
/* 3541 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0];
/*      */     
/* 3543 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1];
/* 3544 */     this.m11 = (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z) * tmp_scale[1];
/* 3545 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1];
/*      */     
/* 3547 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2];
/* 3548 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2];
/* 3549 */     this.m22 = (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y) * tmp_scale[2];
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
/*      */   public final void setRotation(AxisAngle4d a1) {
/* 3565 */     double[] tmp_rot = new double[9];
/* 3566 */     double[] tmp_scale = new double[3];
/*      */     
/* 3568 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3570 */     double mag = 1.0D / Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/* 3571 */     double ax = a1.x * mag;
/* 3572 */     double ay = a1.y * mag;
/* 3573 */     double az = a1.z * mag;
/*      */     
/* 3575 */     double sinTheta = Math.sin(a1.angle);
/* 3576 */     double cosTheta = Math.cos(a1.angle);
/* 3577 */     double t = 1.0D - cosTheta;
/*      */     
/* 3579 */     double xz = a1.x * a1.z;
/* 3580 */     double xy = a1.x * a1.y;
/* 3581 */     double yz = a1.y * a1.z;
/*      */     
/* 3583 */     this.m00 = (t * ax * ax + cosTheta) * tmp_scale[0];
/* 3584 */     this.m01 = (t * xy - sinTheta * az) * tmp_scale[1];
/* 3585 */     this.m02 = (t * xz + sinTheta * ay) * tmp_scale[2];
/*      */     
/* 3587 */     this.m10 = (t * xy + sinTheta * az) * tmp_scale[0];
/* 3588 */     this.m11 = (t * ay * ay + cosTheta) * tmp_scale[1];
/* 3589 */     this.m12 = (t * yz - sinTheta * ax) * tmp_scale[2];
/*      */     
/* 3591 */     this.m20 = (t * xz - sinTheta * ay) * tmp_scale[0];
/* 3592 */     this.m21 = (t * yz + sinTheta * ax) * tmp_scale[1];
/* 3593 */     this.m22 = (t * az * az + cosTheta) * tmp_scale[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3601 */     this.m00 = 0.0D;
/* 3602 */     this.m01 = 0.0D;
/* 3603 */     this.m02 = 0.0D;
/* 3604 */     this.m03 = 0.0D;
/* 3605 */     this.m10 = 0.0D;
/* 3606 */     this.m11 = 0.0D;
/* 3607 */     this.m12 = 0.0D;
/* 3608 */     this.m13 = 0.0D;
/* 3609 */     this.m20 = 0.0D;
/* 3610 */     this.m21 = 0.0D;
/* 3611 */     this.m22 = 0.0D;
/* 3612 */     this.m23 = 0.0D;
/* 3613 */     this.m30 = 0.0D;
/* 3614 */     this.m31 = 0.0D;
/* 3615 */     this.m32 = 0.0D;
/* 3616 */     this.m33 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3623 */     this.m00 = -this.m00;
/* 3624 */     this.m01 = -this.m01;
/* 3625 */     this.m02 = -this.m02;
/* 3626 */     this.m03 = -this.m03;
/* 3627 */     this.m10 = -this.m10;
/* 3628 */     this.m11 = -this.m11;
/* 3629 */     this.m12 = -this.m12;
/* 3630 */     this.m13 = -this.m13;
/* 3631 */     this.m20 = -this.m20;
/* 3632 */     this.m21 = -this.m21;
/* 3633 */     this.m22 = -this.m22;
/* 3634 */     this.m23 = -this.m23;
/* 3635 */     this.m30 = -this.m30;
/* 3636 */     this.m31 = -this.m31;
/* 3637 */     this.m32 = -this.m32;
/* 3638 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4d m1) {
/* 3648 */     this.m00 = -m1.m00;
/* 3649 */     this.m01 = -m1.m01;
/* 3650 */     this.m02 = -m1.m02;
/* 3651 */     this.m03 = -m1.m03;
/* 3652 */     this.m10 = -m1.m10;
/* 3653 */     this.m11 = -m1.m11;
/* 3654 */     this.m12 = -m1.m12;
/* 3655 */     this.m13 = -m1.m13;
/* 3656 */     this.m20 = -m1.m20;
/* 3657 */     this.m21 = -m1.m21;
/* 3658 */     this.m22 = -m1.m22;
/* 3659 */     this.m23 = -m1.m23;
/* 3660 */     this.m30 = -m1.m30;
/* 3661 */     this.m31 = -m1.m31;
/* 3662 */     this.m32 = -m1.m32;
/* 3663 */     this.m33 = -m1.m33;
/*      */   }
/*      */   
/*      */   private final void getScaleRotate(double[] scales, double[] rots) {
/* 3667 */     double[] tmp = new double[9];
/* 3668 */     tmp[0] = this.m00;
/* 3669 */     tmp[1] = this.m01;
/* 3670 */     tmp[2] = this.m02;
/*      */     
/* 3672 */     tmp[3] = this.m10;
/* 3673 */     tmp[4] = this.m11;
/* 3674 */     tmp[5] = this.m12;
/*      */     
/* 3676 */     tmp[6] = this.m20;
/* 3677 */     tmp[7] = this.m21;
/* 3678 */     tmp[8] = this.m22;
/*      */     
/* 3680 */     Matrix3d.compute_svd(tmp, scales, rots);
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
/* 3695 */     Matrix4d m1 = null;
/*      */     try {
/* 3697 */       m1 = (Matrix4d)super.clone();
/* 3698 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3700 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3703 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM00() {
/* 3713 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(double m00) {
/* 3723 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM01() {
/* 3733 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(double m01) {
/* 3743 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM02() {
/* 3753 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(double m02) {
/* 3763 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM10() {
/* 3773 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(double m10) {
/* 3783 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM11() {
/* 3793 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(double m11) {
/* 3803 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM12() {
/* 3813 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(double m12) {
/* 3823 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM20() {
/* 3833 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(double m20) {
/* 3843 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM21() {
/* 3853 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(double m21) {
/* 3863 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM22() {
/* 3873 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(double m22) {
/* 3883 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM03() {
/* 3893 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(double m03) {
/* 3903 */     this.m03 = m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM13() {
/* 3913 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(double m13) {
/* 3923 */     this.m13 = m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM23() {
/* 3933 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(double m23) {
/* 3943 */     this.m23 = m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM30() {
/* 3953 */     return this.m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM30(double m30) {
/* 3963 */     this.m30 = m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM31() {
/* 3973 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(double m31) {
/* 3983 */     this.m31 = m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM32() {
/* 3993 */     return this.m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM32(double m32) {
/* 4003 */     this.m32 = m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM33() {
/* 4013 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(double m33) {
/* 4023 */     this.m33 = m33;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Matrix4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */