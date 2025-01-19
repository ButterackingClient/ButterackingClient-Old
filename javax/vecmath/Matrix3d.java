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
/*      */ public class Matrix3d
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 6837536777072402710L;
/*      */   public double m00;
/*      */   public double m01;
/*      */   public double m02;
/*      */   public double m10;
/*      */   public double m11;
/*      */   public double m12;
/*      */   public double m20;
/*      */   public double m21;
/*      */   public double m22;
/*      */   private static final double EPS = 1.110223024E-16D;
/*      */   
/*      */   public Matrix3d(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
/*  105 */     this.m00 = m00;
/*  106 */     this.m01 = m01;
/*  107 */     this.m02 = m02;
/*      */     
/*  109 */     this.m10 = m10;
/*  110 */     this.m11 = m11;
/*  111 */     this.m12 = m12;
/*      */     
/*  113 */     this.m20 = m20;
/*  114 */     this.m21 = m21;
/*  115 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(double[] v) {
/*  126 */     this.m00 = v[0];
/*  127 */     this.m01 = v[1];
/*  128 */     this.m02 = v[2];
/*      */     
/*  130 */     this.m10 = v[3];
/*  131 */     this.m11 = v[4];
/*  132 */     this.m12 = v[5];
/*      */     
/*  134 */     this.m20 = v[6];
/*  135 */     this.m21 = v[7];
/*  136 */     this.m22 = v[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(Matrix3d m1) {
/*  147 */     this.m00 = m1.m00;
/*  148 */     this.m01 = m1.m01;
/*  149 */     this.m02 = m1.m02;
/*      */     
/*  151 */     this.m10 = m1.m10;
/*  152 */     this.m11 = m1.m11;
/*  153 */     this.m12 = m1.m12;
/*      */     
/*  155 */     this.m20 = m1.m20;
/*  156 */     this.m21 = m1.m21;
/*  157 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d(Matrix3f m1) {
/*  168 */     this.m00 = m1.m00;
/*  169 */     this.m01 = m1.m01;
/*  170 */     this.m02 = m1.m02;
/*      */     
/*  172 */     this.m10 = m1.m10;
/*  173 */     this.m11 = m1.m11;
/*  174 */     this.m12 = m1.m12;
/*      */     
/*  176 */     this.m20 = m1.m20;
/*  177 */     this.m21 = m1.m21;
/*  178 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3d() {
/*  186 */     this.m00 = 0.0D;
/*  187 */     this.m01 = 0.0D;
/*  188 */     this.m02 = 0.0D;
/*      */     
/*  190 */     this.m10 = 0.0D;
/*  191 */     this.m11 = 0.0D;
/*  192 */     this.m12 = 0.0D;
/*      */     
/*  194 */     this.m20 = 0.0D;
/*  195 */     this.m21 = 0.0D;
/*  196 */     this.m22 = 0.0D;
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
/*  207 */     return 
/*  208 */       String.valueOf(this.m00) + ", " + this.m01 + ", " + this.m02 + "\n" + 
/*  209 */       this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + 
/*  210 */       this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  217 */     this.m00 = 1.0D;
/*  218 */     this.m01 = 0.0D;
/*  219 */     this.m02 = 0.0D;
/*      */     
/*  221 */     this.m10 = 0.0D;
/*  222 */     this.m11 = 1.0D;
/*  223 */     this.m12 = 0.0D;
/*      */     
/*  225 */     this.m20 = 0.0D;
/*  226 */     this.m21 = 0.0D;
/*  227 */     this.m22 = 1.0D;
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
/*      */   public final void setScale(double scale) {
/*  239 */     double[] tmp_rot = new double[9];
/*  240 */     double[] tmp_scale = new double[3];
/*      */     
/*  242 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  244 */     this.m00 = tmp_rot[0] * scale;
/*  245 */     this.m01 = tmp_rot[1] * scale;
/*  246 */     this.m02 = tmp_rot[2] * scale;
/*      */     
/*  248 */     this.m10 = tmp_rot[3] * scale;
/*  249 */     this.m11 = tmp_rot[4] * scale;
/*  250 */     this.m12 = tmp_rot[5] * scale;
/*      */     
/*  252 */     this.m20 = tmp_rot[6] * scale;
/*  253 */     this.m21 = tmp_rot[7] * scale;
/*  254 */     this.m22 = tmp_rot[8] * scale;
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
/*  265 */     switch (row) {
/*      */       case 0:
/*  267 */         switch (column) {
/*      */           case 0:
/*  269 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  272 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  275 */             this.m02 = value;
/*      */             return;
/*      */         } 
/*  278 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  283 */         switch (column) {
/*      */           case 0:
/*  285 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  288 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  291 */             this.m12 = value;
/*      */             return;
/*      */         } 
/*  294 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  300 */         switch (column) {
/*      */           case 0:
/*  302 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  305 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  308 */             this.m22 = value;
/*      */             return;
/*      */         } 
/*  311 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  316 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
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
/*      */   public final double getElement(int row, int column) {
/*  329 */     switch (row) {
/*      */       case 0:
/*  331 */         switch (column) {
/*      */           case 0:
/*  333 */             return this.m00;
/*      */           case 1:
/*  335 */             return this.m01;
/*      */           case 2:
/*  337 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  343 */         switch (column) {
/*      */           case 0:
/*  345 */             return this.m10;
/*      */           case 1:
/*  347 */             return this.m11;
/*      */           case 2:
/*  349 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  356 */         switch (column) {
/*      */           case 0:
/*  358 */             return this.m20;
/*      */           case 1:
/*  360 */             return this.m21;
/*      */           case 2:
/*  362 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  372 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector3d v) {
/*  382 */     if (row == 0) {
/*  383 */       v.x = this.m00;
/*  384 */       v.y = this.m01;
/*  385 */       v.z = this.m02;
/*  386 */     } else if (row == 1) {
/*  387 */       v.x = this.m10;
/*  388 */       v.y = this.m11;
/*  389 */       v.z = this.m12;
/*  390 */     } else if (row == 2) {
/*  391 */       v.x = this.m20;
/*  392 */       v.y = this.m21;
/*  393 */       v.z = this.m22;
/*      */     } else {
/*  395 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
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
/*  407 */     if (row == 0) {
/*  408 */       v[0] = this.m00;
/*  409 */       v[1] = this.m01;
/*  410 */       v[2] = this.m02;
/*  411 */     } else if (row == 1) {
/*  412 */       v[0] = this.m10;
/*  413 */       v[1] = this.m11;
/*  414 */       v[2] = this.m12;
/*  415 */     } else if (row == 2) {
/*  416 */       v[0] = this.m20;
/*  417 */       v[1] = this.m21;
/*  418 */       v[2] = this.m22;
/*      */     } else {
/*  420 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
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
/*      */   public final void getColumn(int column, Vector3d v) {
/*  433 */     if (column == 0) {
/*  434 */       v.x = this.m00;
/*  435 */       v.y = this.m10;
/*  436 */       v.z = this.m20;
/*  437 */     } else if (column == 1) {
/*  438 */       v.x = this.m01;
/*  439 */       v.y = this.m11;
/*  440 */       v.z = this.m21;
/*  441 */     } else if (column == 2) {
/*  442 */       v.x = this.m02;
/*  443 */       v.y = this.m12;
/*  444 */       v.z = this.m22;
/*      */     } else {
/*  446 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
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
/*      */   public final void getColumn(int column, double[] v) {
/*  459 */     if (column == 0) {
/*  460 */       v[0] = this.m00;
/*  461 */       v[1] = this.m10;
/*  462 */       v[2] = this.m20;
/*  463 */     } else if (column == 1) {
/*  464 */       v[0] = this.m01;
/*  465 */       v[1] = this.m11;
/*  466 */       v[2] = this.m21;
/*  467 */     } else if (column == 2) {
/*  468 */       v[0] = this.m02;
/*  469 */       v[1] = this.m12;
/*  470 */       v[2] = this.m22;
/*      */     } else {
/*  472 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
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
/*      */   public final void setRow(int row, double x, double y, double z) {
/*  487 */     switch (row) {
/*      */       case 0:
/*  489 */         this.m00 = x;
/*  490 */         this.m01 = y;
/*  491 */         this.m02 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  495 */         this.m10 = x;
/*  496 */         this.m11 = y;
/*  497 */         this.m12 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  501 */         this.m20 = x;
/*  502 */         this.m21 = y;
/*  503 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  507 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector3d v) {
/*  518 */     switch (row) {
/*      */       case 0:
/*  520 */         this.m00 = v.x;
/*  521 */         this.m01 = v.y;
/*  522 */         this.m02 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  526 */         this.m10 = v.x;
/*  527 */         this.m11 = v.y;
/*  528 */         this.m12 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  532 */         this.m20 = v.x;
/*  533 */         this.m21 = v.y;
/*  534 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  538 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
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
/*  549 */     switch (row) {
/*      */       case 0:
/*  551 */         this.m00 = v[0];
/*  552 */         this.m01 = v[1];
/*  553 */         this.m02 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  557 */         this.m10 = v[0];
/*  558 */         this.m11 = v[1];
/*  559 */         this.m12 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  563 */         this.m20 = v[0];
/*  564 */         this.m21 = v[1];
/*  565 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  569 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
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
/*      */   public final void setColumn(int column, double x, double y, double z) {
/*  582 */     switch (column) {
/*      */       case 0:
/*  584 */         this.m00 = x;
/*  585 */         this.m10 = y;
/*  586 */         this.m20 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  590 */         this.m01 = x;
/*  591 */         this.m11 = y;
/*  592 */         this.m21 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  596 */         this.m02 = x;
/*  597 */         this.m12 = y;
/*  598 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  602 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector3d v) {
/*  613 */     switch (column) {
/*      */       case 0:
/*  615 */         this.m00 = v.x;
/*  616 */         this.m10 = v.y;
/*  617 */         this.m20 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  621 */         this.m01 = v.x;
/*  622 */         this.m11 = v.y;
/*  623 */         this.m21 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  627 */         this.m02 = v.x;
/*  628 */         this.m12 = v.y;
/*  629 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  633 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
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
/*  644 */     switch (column) {
/*      */       case 0:
/*  646 */         this.m00 = v[0];
/*  647 */         this.m10 = v[1];
/*  648 */         this.m20 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  652 */         this.m01 = v[0];
/*  653 */         this.m11 = v[1];
/*  654 */         this.m21 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  658 */         this.m02 = v[0];
/*  659 */         this.m12 = v[1];
/*  660 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  664 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
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
/*      */   public final double getScale() {
/*  678 */     double[] tmp_scale = new double[3];
/*  679 */     double[] tmp_rot = new double[9];
/*  680 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  682 */     return max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar) {
/*  692 */     this.m00 += scalar;
/*  693 */     this.m01 += scalar;
/*  694 */     this.m02 += scalar;
/*      */     
/*  696 */     this.m10 += scalar;
/*  697 */     this.m11 += scalar;
/*  698 */     this.m12 += scalar;
/*      */     
/*  700 */     this.m20 += scalar;
/*  701 */     this.m21 += scalar;
/*  702 */     this.m22 += scalar;
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
/*      */   public final void add(double scalar, Matrix3d m1) {
/*  714 */     m1.m00 += scalar;
/*  715 */     m1.m01 += scalar;
/*  716 */     m1.m02 += scalar;
/*      */     
/*  718 */     m1.m10 += scalar;
/*  719 */     m1.m11 += scalar;
/*  720 */     m1.m12 += scalar;
/*      */     
/*  722 */     m1.m20 += scalar;
/*  723 */     m1.m21 += scalar;
/*  724 */     m1.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3d m1, Matrix3d m2) {
/*  734 */     m1.m00 += m2.m00;
/*  735 */     m1.m01 += m2.m01;
/*  736 */     m1.m02 += m2.m02;
/*      */     
/*  738 */     m1.m10 += m2.m10;
/*  739 */     m1.m11 += m2.m11;
/*  740 */     m1.m12 += m2.m12;
/*      */     
/*  742 */     m1.m20 += m2.m20;
/*  743 */     m1.m21 += m2.m21;
/*  744 */     m1.m22 += m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3d m1) {
/*  753 */     this.m00 += m1.m00;
/*  754 */     this.m01 += m1.m01;
/*  755 */     this.m02 += m1.m02;
/*      */     
/*  757 */     this.m10 += m1.m10;
/*  758 */     this.m11 += m1.m11;
/*  759 */     this.m12 += m1.m12;
/*      */     
/*  761 */     this.m20 += m1.m20;
/*  762 */     this.m21 += m1.m21;
/*  763 */     this.m22 += m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3d m1, Matrix3d m2) {
/*  774 */     m1.m00 -= m2.m00;
/*  775 */     m1.m01 -= m2.m01;
/*  776 */     m1.m02 -= m2.m02;
/*      */     
/*  778 */     m1.m10 -= m2.m10;
/*  779 */     m1.m11 -= m2.m11;
/*  780 */     m1.m12 -= m2.m12;
/*      */     
/*  782 */     m1.m20 -= m2.m20;
/*  783 */     m1.m21 -= m2.m21;
/*  784 */     m1.m22 -= m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3d m1) {
/*  794 */     this.m00 -= m1.m00;
/*  795 */     this.m01 -= m1.m01;
/*  796 */     this.m02 -= m1.m02;
/*      */     
/*  798 */     this.m10 -= m1.m10;
/*  799 */     this.m11 -= m1.m11;
/*  800 */     this.m12 -= m1.m12;
/*      */     
/*  802 */     this.m20 -= m1.m20;
/*  803 */     this.m21 -= m1.m21;
/*  804 */     this.m22 -= m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/*  813 */     double temp = this.m10;
/*  814 */     this.m10 = this.m01;
/*  815 */     this.m01 = temp;
/*      */     
/*  817 */     temp = this.m20;
/*  818 */     this.m20 = this.m02;
/*  819 */     this.m02 = temp;
/*      */     
/*  821 */     temp = this.m21;
/*  822 */     this.m21 = this.m12;
/*  823 */     this.m12 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix3d m1) {
/*  832 */     if (this != m1) {
/*  833 */       this.m00 = m1.m00;
/*  834 */       this.m01 = m1.m10;
/*  835 */       this.m02 = m1.m20;
/*      */       
/*  837 */       this.m10 = m1.m01;
/*  838 */       this.m11 = m1.m11;
/*  839 */       this.m12 = m1.m21;
/*      */       
/*  841 */       this.m20 = m1.m02;
/*  842 */       this.m21 = m1.m12;
/*  843 */       this.m22 = m1.m22;
/*      */     } else {
/*  845 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/*  855 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/*  856 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  857 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  859 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  860 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/*  861 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  863 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  864 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  865 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/*  875 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/*  877 */     if (mag < 1.110223024E-16D) {
/*  878 */       this.m00 = 1.0D;
/*  879 */       this.m01 = 0.0D;
/*  880 */       this.m02 = 0.0D;
/*      */       
/*  882 */       this.m10 = 0.0D;
/*  883 */       this.m11 = 1.0D;
/*  884 */       this.m12 = 0.0D;
/*      */       
/*  886 */       this.m20 = 0.0D;
/*  887 */       this.m21 = 0.0D;
/*  888 */       this.m22 = 1.0D;
/*      */     } else {
/*  890 */       mag = 1.0D / mag;
/*  891 */       double ax = a1.x * mag;
/*  892 */       double ay = a1.y * mag;
/*  893 */       double az = a1.z * mag;
/*      */       
/*  895 */       double sinTheta = Math.sin(a1.angle);
/*  896 */       double cosTheta = Math.cos(a1.angle);
/*  897 */       double t = 1.0D - cosTheta;
/*      */       
/*  899 */       double xz = ax * az;
/*  900 */       double xy = ax * ay;
/*  901 */       double yz = ay * az;
/*      */       
/*  903 */       this.m00 = t * ax * ax + cosTheta;
/*  904 */       this.m01 = t * xy - sinTheta * az;
/*  905 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  907 */       this.m10 = t * xy + sinTheta * az;
/*  908 */       this.m11 = t * ay * ay + cosTheta;
/*  909 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  911 */       this.m20 = t * xz - sinTheta * ay;
/*  912 */       this.m21 = t * yz + sinTheta * ax;
/*  913 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/*  924 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/*  925 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  926 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  928 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  929 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/*  930 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  932 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  933 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  934 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/*  944 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*  945 */     if (mag < 1.110223024E-16D) {
/*  946 */       this.m00 = 1.0D;
/*  947 */       this.m01 = 0.0D;
/*  948 */       this.m02 = 0.0D;
/*      */       
/*  950 */       this.m10 = 0.0D;
/*  951 */       this.m11 = 1.0D;
/*  952 */       this.m12 = 0.0D;
/*      */       
/*  954 */       this.m20 = 0.0D;
/*  955 */       this.m21 = 0.0D;
/*  956 */       this.m22 = 1.0D;
/*      */     } else {
/*  958 */       mag = 1.0D / mag;
/*  959 */       double ax = a1.x * mag;
/*  960 */       double ay = a1.y * mag;
/*  961 */       double az = a1.z * mag;
/*  962 */       double sinTheta = Math.sin(a1.angle);
/*  963 */       double cosTheta = Math.cos(a1.angle);
/*  964 */       double t = 1.0D - cosTheta;
/*      */       
/*  966 */       double xz = ax * az;
/*  967 */       double xy = ax * ay;
/*  968 */       double yz = ay * az;
/*      */       
/*  970 */       this.m00 = t * ax * ax + cosTheta;
/*  971 */       this.m01 = t * xy - sinTheta * az;
/*  972 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  974 */       this.m10 = t * xy + sinTheta * az;
/*  975 */       this.m11 = t * ay * ay + cosTheta;
/*  976 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  978 */       this.m20 = t * xz - sinTheta * ay;
/*  979 */       this.m21 = t * yz + sinTheta * ax;
/*  980 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f m1) {
/*  991 */     this.m00 = m1.m00;
/*  992 */     this.m01 = m1.m01;
/*  993 */     this.m02 = m1.m02;
/*      */     
/*  995 */     this.m10 = m1.m10;
/*  996 */     this.m11 = m1.m11;
/*  997 */     this.m12 = m1.m12;
/*      */     
/*  999 */     this.m20 = m1.m20;
/* 1000 */     this.m21 = m1.m21;
/* 1001 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/* 1011 */     this.m00 = m1.m00;
/* 1012 */     this.m01 = m1.m01;
/* 1013 */     this.m02 = m1.m02;
/*      */     
/* 1015 */     this.m10 = m1.m10;
/* 1016 */     this.m11 = m1.m11;
/* 1017 */     this.m12 = m1.m12;
/*      */     
/* 1019 */     this.m20 = m1.m20;
/* 1020 */     this.m21 = m1.m21;
/* 1021 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double[] m) {
/* 1032 */     this.m00 = m[0];
/* 1033 */     this.m01 = m[1];
/* 1034 */     this.m02 = m[2];
/*      */     
/* 1036 */     this.m10 = m[3];
/* 1037 */     this.m11 = m[4];
/* 1038 */     this.m12 = m[5];
/*      */     
/* 1040 */     this.m20 = m[6];
/* 1041 */     this.m21 = m[7];
/* 1042 */     this.m22 = m[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix3d m1) {
/* 1053 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1060 */     invertGeneral(this);
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
/*      */   private final void invertGeneral(Matrix3d m1) {
/* 1072 */     double[] result = new double[9];
/* 1073 */     int[] row_perm = new int[3];
/*      */     
/* 1075 */     double[] tmp = new double[9];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1081 */     tmp[0] = m1.m00;
/* 1082 */     tmp[1] = m1.m01;
/* 1083 */     tmp[2] = m1.m02;
/*      */     
/* 1085 */     tmp[3] = m1.m10;
/* 1086 */     tmp[4] = m1.m11;
/* 1087 */     tmp[5] = m1.m12;
/*      */     
/* 1089 */     tmp[6] = m1.m20;
/* 1090 */     tmp[7] = m1.m21;
/* 1091 */     tmp[8] = m1.m22;
/*      */ 
/*      */ 
/*      */     
/* 1095 */     if (!luDecomposition(tmp, row_perm))
/*      */     {
/* 1097 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3d12"));
/*      */     }
/*      */ 
/*      */     
/* 1101 */     for (int i = 0; i < 9; ) { result[i] = 0.0D; i++; }
/* 1102 */      result[0] = 1.0D;
/* 1103 */     result[4] = 1.0D;
/* 1104 */     result[8] = 1.0D;
/* 1105 */     luBacksubstitution(tmp, row_perm, result);
/*      */     
/* 1107 */     this.m00 = result[0];
/* 1108 */     this.m01 = result[1];
/* 1109 */     this.m02 = result[2];
/*      */     
/* 1111 */     this.m10 = result[3];
/* 1112 */     this.m11 = result[4];
/* 1113 */     this.m12 = result[5];
/*      */     
/* 1115 */     this.m20 = result[6];
/* 1116 */     this.m21 = result[7];
/* 1117 */     this.m22 = result[8];
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
/* 1144 */     double[] row_scale = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     int ptr = 0;
/* 1153 */     int rs = 0;
/*      */ 
/*      */     
/* 1156 */     int i = 3;
/* 1157 */     while (i-- != 0) {
/* 1158 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1161 */       int k = 3;
/* 1162 */       while (k-- != 0) {
/* 1163 */         double temp = matrix0[ptr++];
/* 1164 */         temp = Math.abs(temp);
/* 1165 */         if (temp > big) {
/* 1166 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1171 */       if (big == 0.0D) {
/* 1172 */         return false;
/*      */       }
/* 1174 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1182 */     int mtx = 0;
/*      */ 
/*      */     
/* 1185 */     for (int j = 0; j < 3; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1191 */       for (k = 0; k < j; k++) {
/* 1192 */         int target = mtx + 3 * k + j;
/* 1193 */         double sum = matrix0[target];
/* 1194 */         int m = k;
/* 1195 */         int p1 = mtx + 3 * k;
/* 1196 */         int p2 = mtx + j;
/* 1197 */         while (m-- != 0) {
/* 1198 */           sum -= matrix0[p1] * matrix0[p2];
/* 1199 */           p1++;
/* 1200 */           p2 += 3;
/*      */         } 
/* 1202 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1207 */       double big = 0.0D;
/* 1208 */       int imax = -1;
/* 1209 */       for (k = j; k < 3; k++) {
/* 1210 */         int target = mtx + 3 * k + j;
/* 1211 */         double sum = matrix0[target];
/* 1212 */         int m = j;
/* 1213 */         int p1 = mtx + 3 * k;
/* 1214 */         int p2 = mtx + j;
/* 1215 */         while (m-- != 0) {
/* 1216 */           sum -= matrix0[p1] * matrix0[p2];
/* 1217 */           p1++;
/* 1218 */           p2 += 3;
/*      */         } 
/* 1220 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1223 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1224 */           big = temp;
/* 1225 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1229 */       if (imax < 0) {
/* 1230 */         throw new RuntimeException(VecMathI18N.getString("Matrix3d13"));
/*      */       }
/*      */ 
/*      */       
/* 1234 */       if (j != imax) {
/*      */         
/* 1236 */         int m = 3;
/* 1237 */         int p1 = mtx + 3 * imax;
/* 1238 */         int p2 = mtx + 3 * j;
/* 1239 */         while (m-- != 0) {
/* 1240 */           double temp = matrix0[p1];
/* 1241 */           matrix0[p1++] = matrix0[p2];
/* 1242 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1246 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1250 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1253 */       if (matrix0[mtx + 3 * j + j] == 0.0D) {
/* 1254 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1258 */       if (j != 2) {
/* 1259 */         double temp = 1.0D / matrix0[mtx + 3 * j + j];
/* 1260 */         int target = mtx + 3 * (j + 1) + j;
/* 1261 */         k = 2 - j;
/* 1262 */         while (k-- != 0) {
/* 1263 */           matrix0[target] = matrix0[target] * temp;
/* 1264 */           target += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1270 */     return true;
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
/* 1300 */     int rp = 0;
/*      */ 
/*      */     
/* 1303 */     for (int k = 0; k < 3; k++) {
/*      */       
/* 1305 */       int cv = k;
/* 1306 */       int ii = -1;
/*      */ 
/*      */       
/* 1309 */       for (int i = 0; i < 3; i++) {
/*      */ 
/*      */         
/* 1312 */         int ip = row_perm[rp + i];
/* 1313 */         double sum = matrix2[cv + 3 * ip];
/* 1314 */         matrix2[cv + 3 * ip] = matrix2[cv + 3 * i];
/* 1315 */         if (ii >= 0) {
/*      */           
/* 1317 */           int m = i * 3;
/* 1318 */           for (int j = ii; j <= i - 1; j++) {
/* 1319 */             sum -= matrix1[m + j] * matrix2[cv + 3 * j];
/*      */           }
/* 1321 */         } else if (sum != 0.0D) {
/* 1322 */           ii = i;
/*      */         } 
/* 1324 */         matrix2[cv + 3 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1329 */       int rv = 6;
/* 1330 */       matrix2[cv + 6] = matrix2[cv + 6] / matrix1[rv + 2];
/*      */       
/* 1332 */       rv -= 3;
/* 1333 */       matrix2[cv + 3] = (matrix2[cv + 3] - 
/* 1334 */         matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 1];
/*      */       
/* 1336 */       rv -= 3;
/* 1337 */       matrix2[cv + 0] = (matrix2[cv + 0] - 
/* 1338 */         matrix1[rv + 1] * matrix2[cv + 3] - 
/* 1339 */         matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 0];
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
/*      */   public final double determinant() {
/* 1352 */     double total = this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + 
/* 1353 */       this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + 
/* 1354 */       this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
/* 1355 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double scale) {
/* 1365 */     this.m00 = scale;
/* 1366 */     this.m01 = 0.0D;
/* 1367 */     this.m02 = 0.0D;
/*      */     
/* 1369 */     this.m10 = 0.0D;
/* 1370 */     this.m11 = scale;
/* 1371 */     this.m12 = 0.0D;
/*      */     
/* 1373 */     this.m20 = 0.0D;
/* 1374 */     this.m21 = 0.0D;
/* 1375 */     this.m22 = scale;
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
/* 1387 */     double sinAngle = Math.sin(angle);
/* 1388 */     double cosAngle = Math.cos(angle);
/*      */     
/* 1390 */     this.m00 = 1.0D;
/* 1391 */     this.m01 = 0.0D;
/* 1392 */     this.m02 = 0.0D;
/*      */     
/* 1394 */     this.m10 = 0.0D;
/* 1395 */     this.m11 = cosAngle;
/* 1396 */     this.m12 = -sinAngle;
/*      */     
/* 1398 */     this.m20 = 0.0D;
/* 1399 */     this.m21 = sinAngle;
/* 1400 */     this.m22 = cosAngle;
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
/* 1412 */     double sinAngle = Math.sin(angle);
/* 1413 */     double cosAngle = Math.cos(angle);
/*      */     
/* 1415 */     this.m00 = cosAngle;
/* 1416 */     this.m01 = 0.0D;
/* 1417 */     this.m02 = sinAngle;
/*      */     
/* 1419 */     this.m10 = 0.0D;
/* 1420 */     this.m11 = 1.0D;
/* 1421 */     this.m12 = 0.0D;
/*      */     
/* 1423 */     this.m20 = -sinAngle;
/* 1424 */     this.m21 = 0.0D;
/* 1425 */     this.m22 = cosAngle;
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
/* 1437 */     double sinAngle = Math.sin(angle);
/* 1438 */     double cosAngle = Math.cos(angle);
/*      */     
/* 1440 */     this.m00 = cosAngle;
/* 1441 */     this.m01 = -sinAngle;
/* 1442 */     this.m02 = 0.0D;
/*      */     
/* 1444 */     this.m10 = sinAngle;
/* 1445 */     this.m11 = cosAngle;
/* 1446 */     this.m12 = 0.0D;
/*      */     
/* 1448 */     this.m20 = 0.0D;
/* 1449 */     this.m21 = 0.0D;
/* 1450 */     this.m22 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar) {
/* 1459 */     this.m00 *= scalar;
/* 1460 */     this.m01 *= scalar;
/* 1461 */     this.m02 *= scalar;
/*      */     
/* 1463 */     this.m10 *= scalar;
/* 1464 */     this.m11 *= scalar;
/* 1465 */     this.m12 *= scalar;
/*      */     
/* 1467 */     this.m20 *= scalar;
/* 1468 */     this.m21 *= scalar;
/* 1469 */     this.m22 *= scalar;
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
/*      */   public final void mul(double scalar, Matrix3d m1) {
/* 1481 */     this.m00 = scalar * m1.m00;
/* 1482 */     this.m01 = scalar * m1.m01;
/* 1483 */     this.m02 = scalar * m1.m02;
/*      */     
/* 1485 */     this.m10 = scalar * m1.m10;
/* 1486 */     this.m11 = scalar * m1.m11;
/* 1487 */     this.m12 = scalar * m1.m12;
/*      */     
/* 1489 */     this.m20 = scalar * m1.m20;
/* 1490 */     this.m21 = scalar * m1.m21;
/* 1491 */     this.m22 = scalar * m1.m22;
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
/*      */   public final void mul(Matrix3d m1) {
/* 1506 */     double m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1507 */     double m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1508 */     double m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1510 */     double m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1511 */     double m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1512 */     double m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1514 */     double m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1515 */     double m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1516 */     double m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
/*      */     
/* 1518 */     this.m00 = m00;
/* 1519 */     this.m01 = m01;
/* 1520 */     this.m02 = m02;
/* 1521 */     this.m10 = m10;
/* 1522 */     this.m11 = m11;
/* 1523 */     this.m12 = m12;
/* 1524 */     this.m20 = m20;
/* 1525 */     this.m21 = m21;
/* 1526 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3d m1, Matrix3d m2) {
/* 1537 */     if (this != m1 && this != m2) {
/* 1538 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1539 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1540 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1542 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1543 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1544 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1546 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1547 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1548 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1554 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1555 */       double m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1556 */       double m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1558 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1559 */       double m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1560 */       double m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1562 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1563 */       double m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1564 */       double m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1566 */       this.m00 = m00;
/* 1567 */       this.m01 = m01;
/* 1568 */       this.m02 = m02;
/* 1569 */       this.m10 = m10;
/* 1570 */       this.m11 = m11;
/* 1571 */       this.m12 = m12;
/* 1572 */       this.m20 = m20;
/* 1573 */       this.m21 = m21;
/* 1574 */       this.m22 = m22;
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
/*      */   public final void mulNormalize(Matrix3d m1) {
/* 1587 */     double[] tmp = new double[9];
/* 1588 */     double[] tmp_rot = new double[9];
/* 1589 */     double[] tmp_scale = new double[3];
/*      */     
/* 1591 */     tmp[0] = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1592 */     tmp[1] = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1593 */     tmp[2] = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1595 */     tmp[3] = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1596 */     tmp[4] = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1597 */     tmp[5] = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1599 */     tmp[6] = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1600 */     tmp[7] = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1601 */     tmp[8] = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
/*      */     
/* 1603 */     compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1605 */     this.m00 = tmp_rot[0];
/* 1606 */     this.m01 = tmp_rot[1];
/* 1607 */     this.m02 = tmp_rot[2];
/*      */     
/* 1609 */     this.m10 = tmp_rot[3];
/* 1610 */     this.m11 = tmp_rot[4];
/* 1611 */     this.m12 = tmp_rot[5];
/*      */     
/* 1613 */     this.m20 = tmp_rot[6];
/* 1614 */     this.m21 = tmp_rot[7];
/* 1615 */     this.m22 = tmp_rot[8];
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
/*      */   public final void mulNormalize(Matrix3d m1, Matrix3d m2) {
/* 1630 */     double[] tmp = new double[9];
/* 1631 */     double[] tmp_rot = new double[9];
/* 1632 */     double[] tmp_scale = new double[3];
/*      */     
/* 1634 */     tmp[0] = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1635 */     tmp[1] = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1636 */     tmp[2] = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */     
/* 1638 */     tmp[3] = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1639 */     tmp[4] = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1640 */     tmp[5] = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */     
/* 1642 */     tmp[6] = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1643 */     tmp[7] = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1644 */     tmp[8] = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/* 1646 */     compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1648 */     this.m00 = tmp_rot[0];
/* 1649 */     this.m01 = tmp_rot[1];
/* 1650 */     this.m02 = tmp_rot[2];
/*      */     
/* 1652 */     this.m10 = tmp_rot[3];
/* 1653 */     this.m11 = tmp_rot[4];
/* 1654 */     this.m12 = tmp_rot[5];
/*      */     
/* 1656 */     this.m20 = tmp_rot[6];
/* 1657 */     this.m21 = tmp_rot[7];
/* 1658 */     this.m22 = tmp_rot[8];
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
/*      */   public final void mulTransposeBoth(Matrix3d m1, Matrix3d m2) {
/* 1670 */     if (this != m1 && this != m2) {
/* 1671 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1672 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1673 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1675 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1676 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1677 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1679 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1680 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1681 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1687 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1688 */       double m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1689 */       double m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1691 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1692 */       double m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1693 */       double m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1695 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1696 */       double m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1697 */       double m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1699 */       this.m00 = m00;
/* 1700 */       this.m01 = m01;
/* 1701 */       this.m02 = m02;
/* 1702 */       this.m10 = m10;
/* 1703 */       this.m11 = m11;
/* 1704 */       this.m12 = m12;
/* 1705 */       this.m20 = m20;
/* 1706 */       this.m21 = m21;
/* 1707 */       this.m22 = m22;
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
/*      */   public final void mulTransposeRight(Matrix3d m1, Matrix3d m2) {
/* 1720 */     if (this != m1 && this != m2) {
/* 1721 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1722 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1723 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1725 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1726 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1727 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1729 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1730 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1731 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1737 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1738 */       double m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1739 */       double m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1741 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1742 */       double m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1743 */       double m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1745 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1746 */       double m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1747 */       double m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1749 */       this.m00 = m00;
/* 1750 */       this.m01 = m01;
/* 1751 */       this.m02 = m02;
/* 1752 */       this.m10 = m10;
/* 1753 */       this.m11 = m11;
/* 1754 */       this.m12 = m12;
/* 1755 */       this.m20 = m20;
/* 1756 */       this.m21 = m21;
/* 1757 */       this.m22 = m22;
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
/*      */   public final void mulTransposeLeft(Matrix3d m1, Matrix3d m2) {
/* 1770 */     if (this != m1 && this != m2) {
/* 1771 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1772 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1773 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1775 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1776 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1777 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1779 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1780 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1781 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1787 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1788 */       double m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1789 */       double m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1791 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1792 */       double m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1793 */       double m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1795 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1796 */       double m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1797 */       double m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1799 */       this.m00 = m00;
/* 1800 */       this.m01 = m01;
/* 1801 */       this.m02 = m02;
/* 1802 */       this.m10 = m10;
/* 1803 */       this.m11 = m11;
/* 1804 */       this.m12 = m12;
/* 1805 */       this.m20 = m20;
/* 1806 */       this.m21 = m21;
/* 1807 */       this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1816 */     double[] tmp_rot = new double[9];
/* 1817 */     double[] tmp_scale = new double[3];
/*      */     
/* 1819 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1821 */     this.m00 = tmp_rot[0];
/* 1822 */     this.m01 = tmp_rot[1];
/* 1823 */     this.m02 = tmp_rot[2];
/*      */     
/* 1825 */     this.m10 = tmp_rot[3];
/* 1826 */     this.m11 = tmp_rot[4];
/* 1827 */     this.m12 = tmp_rot[5];
/*      */     
/* 1829 */     this.m20 = tmp_rot[6];
/* 1830 */     this.m21 = tmp_rot[7];
/* 1831 */     this.m22 = tmp_rot[8];
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
/*      */   public final void normalize(Matrix3d m1) {
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
/* 1860 */     compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1862 */     this.m00 = tmp_rot[0];
/* 1863 */     this.m01 = tmp_rot[1];
/* 1864 */     this.m02 = tmp_rot[2];
/*      */     
/* 1866 */     this.m10 = tmp_rot[3];
/* 1867 */     this.m11 = tmp_rot[4];
/* 1868 */     this.m12 = tmp_rot[5];
/*      */     
/* 1870 */     this.m20 = tmp_rot[6];
/* 1871 */     this.m21 = tmp_rot[7];
/* 1872 */     this.m22 = tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP() {
/* 1881 */     double mag = 1.0D / Math.sqrt(this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
/* 1882 */     this.m00 *= mag;
/* 1883 */     this.m10 *= mag;
/* 1884 */     this.m20 *= mag;
/*      */     
/* 1886 */     mag = 1.0D / Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
/* 1887 */     this.m01 *= mag;
/* 1888 */     this.m11 *= mag;
/* 1889 */     this.m21 *= mag;
/*      */     
/* 1891 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1892 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1893 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP(Matrix3d m1) {
/* 1904 */     double mag = 1.0D / Math.sqrt(m1.m00 * m1.m00 + m1.m10 * m1.m10 + m1.m20 * m1.m20);
/* 1905 */     m1.m00 *= mag;
/* 1906 */     m1.m10 *= mag;
/* 1907 */     m1.m20 *= mag;
/*      */     
/* 1909 */     mag = 1.0D / Math.sqrt(m1.m01 * m1.m01 + m1.m11 * m1.m11 + m1.m21 * m1.m21);
/* 1910 */     m1.m01 *= mag;
/* 1911 */     m1.m11 *= mag;
/* 1912 */     m1.m21 *= mag;
/*      */     
/* 1914 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1915 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1916 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Matrix3d m1) {
/*      */     try {
/* 1928 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && 
/* 1929 */         this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && 
/* 1930 */         this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22);
/* 1931 */     } catch (NullPointerException e2) {
/* 1932 */       return false;
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
/* 1948 */       Matrix3d m2 = (Matrix3d)t1;
/* 1949 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && 
/* 1950 */         this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && 
/* 1951 */         this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22);
/* 1952 */     } catch (ClassCastException e1) {
/* 1953 */       return false;
/* 1954 */     } catch (NullPointerException e2) {
/* 1955 */       return false;
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
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix3d m1, double epsilon) {
/* 1973 */     double diff = this.m00 - m1.m00;
/* 1974 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1976 */     diff = this.m01 - m1.m01;
/* 1977 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1979 */     diff = this.m02 - m1.m02;
/* 1980 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1982 */     diff = this.m10 - m1.m10;
/* 1983 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1985 */     diff = this.m11 - m1.m11;
/* 1986 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1988 */     diff = this.m12 - m1.m12;
/* 1989 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1991 */     diff = this.m20 - m1.m20;
/* 1992 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1994 */     diff = this.m21 - m1.m21;
/* 1995 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 1997 */     diff = this.m22 - m1.m22;
/* 1998 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 2000 */     return true;
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
/*      */   public int hashCode() {
/* 2015 */     long bits = 1L;
/* 2016 */     bits = VecMathUtil.hashDoubleBits(bits, this.m00);
/* 2017 */     bits = VecMathUtil.hashDoubleBits(bits, this.m01);
/* 2018 */     bits = VecMathUtil.hashDoubleBits(bits, this.m02);
/* 2019 */     bits = VecMathUtil.hashDoubleBits(bits, this.m10);
/* 2020 */     bits = VecMathUtil.hashDoubleBits(bits, this.m11);
/* 2021 */     bits = VecMathUtil.hashDoubleBits(bits, this.m12);
/* 2022 */     bits = VecMathUtil.hashDoubleBits(bits, this.m20);
/* 2023 */     bits = VecMathUtil.hashDoubleBits(bits, this.m21);
/* 2024 */     bits = VecMathUtil.hashDoubleBits(bits, this.m22);
/* 2025 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 2033 */     this.m00 = 0.0D;
/* 2034 */     this.m01 = 0.0D;
/* 2035 */     this.m02 = 0.0D;
/*      */     
/* 2037 */     this.m10 = 0.0D;
/* 2038 */     this.m11 = 0.0D;
/* 2039 */     this.m12 = 0.0D;
/*      */     
/* 2041 */     this.m20 = 0.0D;
/* 2042 */     this.m21 = 0.0D;
/* 2043 */     this.m22 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 2051 */     this.m00 = -this.m00;
/* 2052 */     this.m01 = -this.m01;
/* 2053 */     this.m02 = -this.m02;
/*      */     
/* 2055 */     this.m10 = -this.m10;
/* 2056 */     this.m11 = -this.m11;
/* 2057 */     this.m12 = -this.m12;
/*      */     
/* 2059 */     this.m20 = -this.m20;
/* 2060 */     this.m21 = -this.m21;
/* 2061 */     this.m22 = -this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix3d m1) {
/* 2072 */     this.m00 = -m1.m00;
/* 2073 */     this.m01 = -m1.m01;
/* 2074 */     this.m02 = -m1.m02;
/*      */     
/* 2076 */     this.m10 = -m1.m10;
/* 2077 */     this.m11 = -m1.m11;
/* 2078 */     this.m12 = -m1.m12;
/*      */     
/* 2080 */     this.m20 = -m1.m20;
/* 2081 */     this.m21 = -m1.m21;
/* 2082 */     this.m22 = -m1.m22;
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
/*      */   public final void transform(Tuple3d t) {
/* 2094 */     double x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2095 */     double y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2096 */     double z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2097 */     t.set(x, y, z);
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
/*      */   public final void transform(Tuple3d t, Tuple3d result) {
/* 2109 */     double x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2110 */     double y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2111 */     result.z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2112 */     result.x = x;
/* 2113 */     result.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void getScaleRotate(double[] scales, double[] rots) {
/* 2121 */     double[] tmp = new double[9];
/*      */     
/* 2123 */     tmp[0] = this.m00;
/* 2124 */     tmp[1] = this.m01;
/* 2125 */     tmp[2] = this.m02;
/*      */     
/* 2127 */     tmp[3] = this.m10;
/* 2128 */     tmp[4] = this.m11;
/* 2129 */     tmp[5] = this.m12;
/*      */     
/* 2131 */     tmp[6] = this.m20;
/* 2132 */     tmp[7] = this.m21;
/* 2133 */     tmp[8] = this.m22;
/* 2134 */     compute_svd(tmp, scales, rots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void compute_svd(double[] m, double[] outScale, double[] outRot) {
/* 2142 */     double[] u1 = new double[9];
/* 2143 */     double[] v1 = new double[9];
/* 2144 */     double[] t1 = new double[9];
/* 2145 */     double[] t2 = new double[9];
/*      */     
/* 2147 */     double[] tmp = t1;
/* 2148 */     double[] single_values = t2;
/*      */     
/* 2150 */     double[] rot = new double[9];
/* 2151 */     double[] e = new double[3];
/* 2152 */     double[] scales = new double[3];
/*      */     
/* 2154 */     int negCnt = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */     
/* 2161 */     for (i = 0; i < 9; i++) {
/* 2162 */       rot[i] = m[i];
/*      */     }
/*      */ 
/*      */     
/* 2166 */     if (m[3] * m[3] < 1.110223024E-16D) {
/* 2167 */       u1[0] = 1.0D;
/* 2168 */       u1[1] = 0.0D;
/* 2169 */       u1[2] = 0.0D;
/* 2170 */       u1[3] = 0.0D;
/* 2171 */       u1[4] = 1.0D;
/* 2172 */       u1[5] = 0.0D;
/* 2173 */       u1[6] = 0.0D;
/* 2174 */       u1[7] = 0.0D;
/* 2175 */       u1[8] = 1.0D;
/* 2176 */     } else if (m[0] * m[0] < 1.110223024E-16D) {
/* 2177 */       tmp[0] = m[0];
/* 2178 */       tmp[1] = m[1];
/* 2179 */       tmp[2] = m[2];
/* 2180 */       m[0] = m[3];
/* 2181 */       m[1] = m[4];
/* 2182 */       m[2] = m[5];
/*      */       
/* 2184 */       m[3] = -tmp[0];
/* 2185 */       m[4] = -tmp[1];
/* 2186 */       m[5] = -tmp[2];
/*      */       
/* 2188 */       u1[0] = 0.0D;
/* 2189 */       u1[1] = 1.0D;
/* 2190 */       u1[2] = 0.0D;
/* 2191 */       u1[3] = -1.0D;
/* 2192 */       u1[4] = 0.0D;
/* 2193 */       u1[5] = 0.0D;
/* 2194 */       u1[6] = 0.0D;
/* 2195 */       u1[7] = 0.0D;
/* 2196 */       u1[8] = 1.0D;
/*      */     } else {
/* 2198 */       double g = 1.0D / Math.sqrt(m[0] * m[0] + m[3] * m[3]);
/* 2199 */       double c1 = m[0] * g;
/* 2200 */       double s1 = m[3] * g;
/* 2201 */       tmp[0] = c1 * m[0] + s1 * m[3];
/* 2202 */       tmp[1] = c1 * m[1] + s1 * m[4];
/* 2203 */       tmp[2] = c1 * m[2] + s1 * m[5];
/*      */       
/* 2205 */       m[3] = -s1 * m[0] + c1 * m[3];
/* 2206 */       m[4] = -s1 * m[1] + c1 * m[4];
/* 2207 */       m[5] = -s1 * m[2] + c1 * m[5];
/*      */       
/* 2209 */       m[0] = tmp[0];
/* 2210 */       m[1] = tmp[1];
/* 2211 */       m[2] = tmp[2];
/* 2212 */       u1[0] = c1;
/* 2213 */       u1[1] = s1;
/* 2214 */       u1[2] = 0.0D;
/* 2215 */       u1[3] = -s1;
/* 2216 */       u1[4] = c1;
/* 2217 */       u1[5] = 0.0D;
/* 2218 */       u1[6] = 0.0D;
/* 2219 */       u1[7] = 0.0D;
/* 2220 */       u1[8] = 1.0D;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2225 */     if (m[6] * m[6] >= 1.110223024E-16D) {
/* 2226 */       if (m[0] * m[0] < 1.110223024E-16D) {
/* 2227 */         tmp[0] = m[0];
/* 2228 */         tmp[1] = m[1];
/* 2229 */         tmp[2] = m[2];
/* 2230 */         m[0] = m[6];
/* 2231 */         m[1] = m[7];
/* 2232 */         m[2] = m[8];
/*      */         
/* 2234 */         m[6] = -tmp[0];
/* 2235 */         m[7] = -tmp[1];
/* 2236 */         m[8] = -tmp[2];
/*      */         
/* 2238 */         tmp[0] = u1[0];
/* 2239 */         tmp[1] = u1[1];
/* 2240 */         tmp[2] = u1[2];
/* 2241 */         u1[0] = u1[6];
/* 2242 */         u1[1] = u1[7];
/* 2243 */         u1[2] = u1[8];
/*      */         
/* 2245 */         u1[6] = -tmp[0];
/* 2246 */         u1[7] = -tmp[1];
/* 2247 */         u1[8] = -tmp[2];
/*      */       } else {
/* 2249 */         double g = 1.0D / Math.sqrt(m[0] * m[0] + m[6] * m[6]);
/* 2250 */         double c2 = m[0] * g;
/* 2251 */         double s2 = m[6] * g;
/* 2252 */         tmp[0] = c2 * m[0] + s2 * m[6];
/* 2253 */         tmp[1] = c2 * m[1] + s2 * m[7];
/* 2254 */         tmp[2] = c2 * m[2] + s2 * m[8];
/*      */         
/* 2256 */         m[6] = -s2 * m[0] + c2 * m[6];
/* 2257 */         m[7] = -s2 * m[1] + c2 * m[7];
/* 2258 */         m[8] = -s2 * m[2] + c2 * m[8];
/* 2259 */         m[0] = tmp[0];
/* 2260 */         m[1] = tmp[1];
/* 2261 */         m[2] = tmp[2];
/*      */         
/* 2263 */         tmp[0] = c2 * u1[0];
/* 2264 */         tmp[1] = c2 * u1[1];
/* 2265 */         u1[2] = s2;
/*      */         
/* 2267 */         tmp[6] = -u1[0] * s2;
/* 2268 */         tmp[7] = -u1[1] * s2;
/* 2269 */         u1[8] = c2;
/* 2270 */         u1[0] = tmp[0];
/* 2271 */         u1[1] = tmp[1];
/* 2272 */         u1[6] = tmp[6];
/* 2273 */         u1[7] = tmp[7];
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2278 */     if (m[2] * m[2] < 1.110223024E-16D) {
/* 2279 */       v1[0] = 1.0D;
/* 2280 */       v1[1] = 0.0D;
/* 2281 */       v1[2] = 0.0D;
/* 2282 */       v1[3] = 0.0D;
/* 2283 */       v1[4] = 1.0D;
/* 2284 */       v1[5] = 0.0D;
/* 2285 */       v1[6] = 0.0D;
/* 2286 */       v1[7] = 0.0D;
/* 2287 */       v1[8] = 1.0D;
/* 2288 */     } else if (m[1] * m[1] < 1.110223024E-16D) {
/* 2289 */       tmp[2] = m[2];
/* 2290 */       tmp[5] = m[5];
/* 2291 */       tmp[8] = m[8];
/* 2292 */       m[2] = -m[1];
/* 2293 */       m[5] = -m[4];
/* 2294 */       m[8] = -m[7];
/*      */       
/* 2296 */       m[1] = tmp[2];
/* 2297 */       m[4] = tmp[5];
/* 2298 */       m[7] = tmp[8];
/*      */       
/* 2300 */       v1[0] = 1.0D;
/* 2301 */       v1[1] = 0.0D;
/* 2302 */       v1[2] = 0.0D;
/* 2303 */       v1[3] = 0.0D;
/* 2304 */       v1[4] = 0.0D;
/* 2305 */       v1[5] = -1.0D;
/* 2306 */       v1[6] = 0.0D;
/* 2307 */       v1[7] = 1.0D;
/* 2308 */       v1[8] = 0.0D;
/*      */     } else {
/* 2310 */       double g = 1.0D / Math.sqrt(m[1] * m[1] + m[2] * m[2]);
/* 2311 */       double c3 = m[1] * g;
/* 2312 */       double s3 = m[2] * g;
/* 2313 */       tmp[1] = c3 * m[1] + s3 * m[2];
/* 2314 */       m[2] = -s3 * m[1] + c3 * m[2];
/* 2315 */       m[1] = tmp[1];
/*      */       
/* 2317 */       tmp[4] = c3 * m[4] + s3 * m[5];
/* 2318 */       m[5] = -s3 * m[4] + c3 * m[5];
/* 2319 */       m[4] = tmp[4];
/*      */       
/* 2321 */       tmp[7] = c3 * m[7] + s3 * m[8];
/* 2322 */       m[8] = -s3 * m[7] + c3 * m[8];
/* 2323 */       m[7] = tmp[7];
/*      */       
/* 2325 */       v1[0] = 1.0D;
/* 2326 */       v1[1] = 0.0D;
/* 2327 */       v1[2] = 0.0D;
/* 2328 */       v1[3] = 0.0D;
/* 2329 */       v1[4] = c3;
/* 2330 */       v1[5] = -s3;
/* 2331 */       v1[6] = 0.0D;
/* 2332 */       v1[7] = s3;
/* 2333 */       v1[8] = c3;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2338 */     if (m[7] * m[7] >= 1.110223024E-16D) {
/* 2339 */       if (m[4] * m[4] < 1.110223024E-16D) {
/* 2340 */         tmp[3] = m[3];
/* 2341 */         tmp[4] = m[4];
/* 2342 */         tmp[5] = m[5];
/* 2343 */         m[3] = m[6];
/* 2344 */         m[4] = m[7];
/* 2345 */         m[5] = m[8];
/*      */         
/* 2347 */         m[6] = -tmp[3];
/* 2348 */         m[7] = -tmp[4];
/* 2349 */         m[8] = -tmp[5];
/*      */         
/* 2351 */         tmp[3] = u1[3];
/* 2352 */         tmp[4] = u1[4];
/* 2353 */         tmp[5] = u1[5];
/* 2354 */         u1[3] = u1[6];
/* 2355 */         u1[4] = u1[7];
/* 2356 */         u1[5] = u1[8];
/*      */         
/* 2358 */         u1[6] = -tmp[3];
/* 2359 */         u1[7] = -tmp[4];
/* 2360 */         u1[8] = -tmp[5];
/*      */       } else {
/*      */         
/* 2363 */         double g = 1.0D / Math.sqrt(m[4] * m[4] + m[7] * m[7]);
/* 2364 */         double c4 = m[4] * g;
/* 2365 */         double s4 = m[7] * g;
/* 2366 */         tmp[3] = c4 * m[3] + s4 * m[6];
/* 2367 */         m[6] = -s4 * m[3] + c4 * m[6];
/* 2368 */         m[3] = tmp[3];
/*      */         
/* 2370 */         tmp[4] = c4 * m[4] + s4 * m[7];
/* 2371 */         m[7] = -s4 * m[4] + c4 * m[7];
/* 2372 */         m[4] = tmp[4];
/*      */         
/* 2374 */         tmp[5] = c4 * m[5] + s4 * m[8];
/* 2375 */         m[8] = -s4 * m[5] + c4 * m[8];
/* 2376 */         m[5] = tmp[5];
/*      */         
/* 2378 */         tmp[3] = c4 * u1[3] + s4 * u1[6];
/* 2379 */         u1[6] = -s4 * u1[3] + c4 * u1[6];
/* 2380 */         u1[3] = tmp[3];
/*      */         
/* 2382 */         tmp[4] = c4 * u1[4] + s4 * u1[7];
/* 2383 */         u1[7] = -s4 * u1[4] + c4 * u1[7];
/* 2384 */         u1[4] = tmp[4];
/*      */         
/* 2386 */         tmp[5] = c4 * u1[5] + s4 * u1[8];
/* 2387 */         u1[8] = -s4 * u1[5] + c4 * u1[8];
/* 2388 */         u1[5] = tmp[5];
/*      */       } 
/*      */     }
/* 2391 */     single_values[0] = m[0];
/* 2392 */     single_values[1] = m[4];
/* 2393 */     single_values[2] = m[8];
/* 2394 */     e[0] = m[1];
/* 2395 */     e[1] = m[5];
/*      */     
/* 2397 */     if (e[0] * e[0] >= 1.110223024E-16D || e[1] * e[1] >= 1.110223024E-16D)
/*      */     {
/*      */       
/* 2400 */       compute_qr(single_values, e, u1, v1);
/*      */     }
/*      */     
/* 2403 */     scales[0] = single_values[0];
/* 2404 */     scales[1] = single_values[1];
/* 2405 */     scales[2] = single_values[2];
/*      */ 
/*      */ 
/*      */     
/* 2409 */     if (almostEqual(Math.abs(scales[0]), 1.0D) && 
/* 2410 */       almostEqual(Math.abs(scales[1]), 1.0D) && 
/* 2411 */       almostEqual(Math.abs(scales[2]), 1.0D)) {
/*      */ 
/*      */       
/* 2414 */       for (i = 0; i < 3; i++) {
/* 2415 */         if (scales[i] < 0.0D)
/* 2416 */           negCnt++; 
/*      */       } 
/* 2418 */       if (negCnt == 0 || negCnt == 2) {
/*      */         
/* 2420 */         outScale[2] = 1.0D; outScale[1] = 1.0D; outScale[0] = 1.0D;
/* 2421 */         i = 0;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2429 */     transpose_mat(u1, t1);
/* 2430 */     transpose_mat(v1, t2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2444 */     svdReorder(m, t1, t2, scales, outRot, outScale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void svdReorder(double[] m, double[] t1, double[] t2, double[] scales, double[] outRot, double[] outScale) {
/* 2451 */     int[] out = new int[3];
/* 2452 */     int[] in = new int[3];
/*      */     
/* 2454 */     double[] mag = new double[3];
/* 2455 */     double[] rot = new double[9];
/*      */ 
/*      */ 
/*      */     
/* 2459 */     if (scales[0] < 0.0D) {
/* 2460 */       scales[0] = -scales[0];
/* 2461 */       t2[0] = -t2[0];
/* 2462 */       t2[1] = -t2[1];
/* 2463 */       t2[2] = -t2[2];
/*      */     } 
/* 2465 */     if (scales[1] < 0.0D) {
/* 2466 */       scales[1] = -scales[1];
/* 2467 */       t2[3] = -t2[3];
/* 2468 */       t2[4] = -t2[4];
/* 2469 */       t2[5] = -t2[5];
/*      */     } 
/* 2471 */     if (scales[2] < 0.0D) {
/* 2472 */       scales[2] = -scales[2];
/* 2473 */       t2[6] = -t2[6];
/* 2474 */       t2[7] = -t2[7];
/* 2475 */       t2[8] = -t2[8];
/*      */     } 
/*      */     
/* 2478 */     mat_mul(t1, t2, rot);
/*      */ 
/*      */     
/* 2481 */     if (almostEqual(Math.abs(scales[0]), Math.abs(scales[1])) && 
/* 2482 */       almostEqual(Math.abs(scales[1]), Math.abs(scales[2]))) {
/* 2483 */       int i; for (i = 0; i < 9; i++) {
/* 2484 */         outRot[i] = rot[i];
/*      */       }
/* 2486 */       for (i = 0; i < 3; i++) {
/* 2487 */         outScale[i] = scales[i];
/*      */       }
/*      */     } else {
/*      */       int in0, in1, in2;
/*      */ 
/*      */       
/* 2493 */       if (scales[0] > scales[1]) {
/* 2494 */         if (scales[0] > scales[2]) {
/* 2495 */           if (scales[2] > scales[1]) {
/* 2496 */             out[0] = 0;
/* 2497 */             out[1] = 2;
/* 2498 */             out[2] = 1;
/*      */           } else {
/* 2500 */             out[0] = 0;
/* 2501 */             out[1] = 1;
/* 2502 */             out[2] = 2;
/*      */           } 
/*      */         } else {
/* 2505 */           out[0] = 2;
/* 2506 */           out[1] = 0;
/* 2507 */           out[2] = 1;
/*      */         }
/*      */       
/* 2510 */       } else if (scales[1] > scales[2]) {
/* 2511 */         if (scales[2] > scales[0]) {
/* 2512 */           out[0] = 1;
/* 2513 */           out[1] = 2;
/* 2514 */           out[2] = 0;
/*      */         } else {
/* 2516 */           out[0] = 1;
/* 2517 */           out[1] = 0;
/* 2518 */           out[2] = 2;
/*      */         } 
/*      */       } else {
/* 2521 */         out[0] = 2;
/* 2522 */         out[1] = 1;
/* 2523 */         out[2] = 0;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2535 */       mag[0] = m[0] * m[0] + m[1] * m[1] + m[2] * m[2];
/* 2536 */       mag[1] = m[3] * m[3] + m[4] * m[4] + m[5] * m[5];
/* 2537 */       mag[2] = m[6] * m[6] + m[7] * m[7] + m[8] * m[8];
/*      */       
/* 2539 */       if (mag[0] > mag[1]) {
/* 2540 */         if (mag[0] > mag[2]) {
/* 2541 */           if (mag[2] > mag[1]) {
/*      */             
/* 2543 */             in0 = 0;
/* 2544 */             in2 = 1;
/* 2545 */             in1 = 2;
/*      */           } else {
/*      */             
/* 2548 */             in0 = 0;
/* 2549 */             in1 = 1;
/* 2550 */             in2 = 2;
/*      */           } 
/*      */         } else {
/*      */           
/* 2554 */           in2 = 0;
/* 2555 */           in0 = 1;
/* 2556 */           in1 = 2;
/*      */         }
/*      */       
/* 2559 */       } else if (mag[1] > mag[2]) {
/* 2560 */         if (mag[2] > mag[0]) {
/*      */           
/* 2562 */           in1 = 0;
/* 2563 */           in2 = 1;
/* 2564 */           in0 = 2;
/*      */         } else {
/*      */           
/* 2567 */           in1 = 0;
/* 2568 */           in0 = 1;
/* 2569 */           in2 = 2;
/*      */         } 
/*      */       } else {
/*      */         
/* 2573 */         in2 = 0;
/* 2574 */         in1 = 1;
/* 2575 */         in0 = 2;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2580 */       int index = out[in0];
/* 2581 */       outScale[0] = scales[index];
/*      */       
/* 2583 */       index = out[in1];
/* 2584 */       outScale[1] = scales[index];
/*      */       
/* 2586 */       index = out[in2];
/* 2587 */       outScale[2] = scales[index];
/*      */ 
/*      */       
/* 2590 */       index = out[in0];
/* 2591 */       outRot[0] = rot[index];
/*      */       
/* 2593 */       index = out[in0] + 3;
/* 2594 */       outRot[3] = rot[index];
/*      */       
/* 2596 */       index = out[in0] + 6;
/* 2597 */       outRot[6] = rot[index];
/*      */       
/* 2599 */       index = out[in1];
/* 2600 */       outRot[1] = rot[index];
/*      */       
/* 2602 */       index = out[in1] + 3;
/* 2603 */       outRot[4] = rot[index];
/*      */       
/* 2605 */       index = out[in1] + 6;
/* 2606 */       outRot[7] = rot[index];
/*      */       
/* 2608 */       index = out[in2];
/* 2609 */       outRot[2] = rot[index];
/*      */       
/* 2611 */       index = out[in2] + 3;
/* 2612 */       outRot[5] = rot[index];
/*      */       
/* 2614 */       index = out[in2] + 6;
/* 2615 */       outRot[8] = rot[index];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int compute_qr(double[] s, double[] e, double[] u, double[] v) {
/* 2624 */     double[] cosl = new double[2];
/* 2625 */     double[] cosr = new double[2];
/* 2626 */     double[] sinl = new double[2];
/* 2627 */     double[] sinr = new double[2];
/* 2628 */     double[] m = new double[9];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2633 */     int MAX_INTERATIONS = 10;
/* 2634 */     double CONVERGE_TOL = 4.89E-15D;
/*      */     
/* 2636 */     double c_b48 = 1.0D;
/* 2637 */     double c_b71 = -1.0D;
/*      */     
/* 2639 */     boolean converged = false;
/*      */ 
/*      */     
/* 2642 */     int first = 1;
/*      */     
/* 2644 */     if (Math.abs(e[1]) < 4.89E-15D || Math.abs(e[0]) < 4.89E-15D) converged = true;
/*      */     
/* 2646 */     for (int k = 0; k < 10 && !converged; k++) {
/* 2647 */       double shift = compute_shift(s[1], e[1], s[2]);
/* 2648 */       double f = (Math.abs(s[0]) - shift) * (d_sign(c_b48, s[0]) + shift / s[0]);
/* 2649 */       double g = e[0];
/* 2650 */       double r = compute_rot(f, g, sinr, cosr, 0, first);
/* 2651 */       f = cosr[0] * s[0] + sinr[0] * e[0];
/* 2652 */       e[0] = cosr[0] * e[0] - sinr[0] * s[0];
/* 2653 */       g = sinr[0] * s[1];
/* 2654 */       s[1] = cosr[0] * s[1];
/*      */       
/* 2656 */       r = compute_rot(f, g, sinl, cosl, 0, first);
/* 2657 */       first = 0;
/* 2658 */       s[0] = r;
/* 2659 */       f = cosl[0] * e[0] + sinl[0] * s[1];
/* 2660 */       s[1] = cosl[0] * s[1] - sinl[0] * e[0];
/* 2661 */       g = sinl[0] * e[1];
/* 2662 */       e[1] = cosl[0] * e[1];
/*      */       
/* 2664 */       r = compute_rot(f, g, sinr, cosr, 1, first);
/* 2665 */       e[0] = r;
/* 2666 */       f = cosr[1] * s[1] + sinr[1] * e[1];
/* 2667 */       e[1] = cosr[1] * e[1] - sinr[1] * s[1];
/* 2668 */       g = sinr[1] * s[2];
/* 2669 */       s[2] = cosr[1] * s[2];
/*      */       
/* 2671 */       r = compute_rot(f, g, sinl, cosl, 1, first);
/* 2672 */       s[1] = r;
/* 2673 */       f = cosl[1] * e[1] + sinl[1] * s[2];
/* 2674 */       s[2] = cosl[1] * s[2] - sinl[1] * e[1];
/* 2675 */       e[1] = f;
/*      */ 
/*      */       
/* 2678 */       double utemp = u[0];
/* 2679 */       u[0] = cosl[0] * utemp + sinl[0] * u[3];
/* 2680 */       u[3] = -sinl[0] * utemp + cosl[0] * u[3];
/* 2681 */       utemp = u[1];
/* 2682 */       u[1] = cosl[0] * utemp + sinl[0] * u[4];
/* 2683 */       u[4] = -sinl[0] * utemp + cosl[0] * u[4];
/* 2684 */       utemp = u[2];
/* 2685 */       u[2] = cosl[0] * utemp + sinl[0] * u[5];
/* 2686 */       u[5] = -sinl[0] * utemp + cosl[0] * u[5];
/*      */       
/* 2688 */       utemp = u[3];
/* 2689 */       u[3] = cosl[1] * utemp + sinl[1] * u[6];
/* 2690 */       u[6] = -sinl[1] * utemp + cosl[1] * u[6];
/* 2691 */       utemp = u[4];
/* 2692 */       u[4] = cosl[1] * utemp + sinl[1] * u[7];
/* 2693 */       u[7] = -sinl[1] * utemp + cosl[1] * u[7];
/* 2694 */       utemp = u[5];
/* 2695 */       u[5] = cosl[1] * utemp + sinl[1] * u[8];
/* 2696 */       u[8] = -sinl[1] * utemp + cosl[1] * u[8];
/*      */ 
/*      */ 
/*      */       
/* 2700 */       double vtemp = v[0];
/* 2701 */       v[0] = cosr[0] * vtemp + sinr[0] * v[1];
/* 2702 */       v[1] = -sinr[0] * vtemp + cosr[0] * v[1];
/* 2703 */       vtemp = v[3];
/* 2704 */       v[3] = cosr[0] * vtemp + sinr[0] * v[4];
/* 2705 */       v[4] = -sinr[0] * vtemp + cosr[0] * v[4];
/* 2706 */       vtemp = v[6];
/* 2707 */       v[6] = cosr[0] * vtemp + sinr[0] * v[7];
/* 2708 */       v[7] = -sinr[0] * vtemp + cosr[0] * v[7];
/*      */       
/* 2710 */       vtemp = v[1];
/* 2711 */       v[1] = cosr[1] * vtemp + sinr[1] * v[2];
/* 2712 */       v[2] = -sinr[1] * vtemp + cosr[1] * v[2];
/* 2713 */       vtemp = v[4];
/* 2714 */       v[4] = cosr[1] * vtemp + sinr[1] * v[5];
/* 2715 */       v[5] = -sinr[1] * vtemp + cosr[1] * v[5];
/* 2716 */       vtemp = v[7];
/* 2717 */       v[7] = cosr[1] * vtemp + sinr[1] * v[8];
/* 2718 */       v[8] = -sinr[1] * vtemp + cosr[1] * v[8];
/*      */ 
/*      */       
/* 2721 */       m[0] = s[0];
/* 2722 */       m[1] = e[0];
/* 2723 */       m[2] = 0.0D;
/* 2724 */       m[3] = 0.0D;
/* 2725 */       m[4] = s[1];
/* 2726 */       m[5] = e[1];
/* 2727 */       m[6] = 0.0D;
/* 2728 */       m[7] = 0.0D;
/* 2729 */       m[8] = s[2];
/*      */       
/* 2731 */       if (Math.abs(e[1]) < 4.89E-15D || Math.abs(e[0]) < 4.89E-15D) converged = true;
/*      */     
/*      */     } 
/* 2734 */     if (Math.abs(e[1]) < 4.89E-15D) {
/* 2735 */       compute_2X2(s[0], e[0], s[1], s, sinl, cosl, sinr, cosr, 0);
/*      */       
/* 2737 */       double utemp = u[0];
/* 2738 */       u[0] = cosl[0] * utemp + sinl[0] * u[3];
/* 2739 */       u[3] = -sinl[0] * utemp + cosl[0] * u[3];
/* 2740 */       utemp = u[1];
/* 2741 */       u[1] = cosl[0] * utemp + sinl[0] * u[4];
/* 2742 */       u[4] = -sinl[0] * utemp + cosl[0] * u[4];
/* 2743 */       utemp = u[2];
/* 2744 */       u[2] = cosl[0] * utemp + sinl[0] * u[5];
/* 2745 */       u[5] = -sinl[0] * utemp + cosl[0] * u[5];
/*      */ 
/*      */ 
/*      */       
/* 2749 */       double vtemp = v[0];
/* 2750 */       v[0] = cosr[0] * vtemp + sinr[0] * v[1];
/* 2751 */       v[1] = -sinr[0] * vtemp + cosr[0] * v[1];
/* 2752 */       vtemp = v[3];
/* 2753 */       v[3] = cosr[0] * vtemp + sinr[0] * v[4];
/* 2754 */       v[4] = -sinr[0] * vtemp + cosr[0] * v[4];
/* 2755 */       vtemp = v[6];
/* 2756 */       v[6] = cosr[0] * vtemp + sinr[0] * v[7];
/* 2757 */       v[7] = -sinr[0] * vtemp + cosr[0] * v[7];
/*      */     } else {
/* 2759 */       compute_2X2(s[1], e[1], s[2], s, sinl, cosl, sinr, cosr, 1);
/*      */       
/* 2761 */       double utemp = u[3];
/* 2762 */       u[3] = cosl[0] * utemp + sinl[0] * u[6];
/* 2763 */       u[6] = -sinl[0] * utemp + cosl[0] * u[6];
/* 2764 */       utemp = u[4];
/* 2765 */       u[4] = cosl[0] * utemp + sinl[0] * u[7];
/* 2766 */       u[7] = -sinl[0] * utemp + cosl[0] * u[7];
/* 2767 */       utemp = u[5];
/* 2768 */       u[5] = cosl[0] * utemp + sinl[0] * u[8];
/* 2769 */       u[8] = -sinl[0] * utemp + cosl[0] * u[8];
/*      */ 
/*      */ 
/*      */       
/* 2773 */       double vtemp = v[1];
/* 2774 */       v[1] = cosr[0] * vtemp + sinr[0] * v[2];
/* 2775 */       v[2] = -sinr[0] * vtemp + cosr[0] * v[2];
/* 2776 */       vtemp = v[4];
/* 2777 */       v[4] = cosr[0] * vtemp + sinr[0] * v[5];
/* 2778 */       v[5] = -sinr[0] * vtemp + cosr[0] * v[5];
/* 2779 */       vtemp = v[7];
/* 2780 */       v[7] = cosr[0] * vtemp + sinr[0] * v[8];
/* 2781 */       v[8] = -sinr[0] * vtemp + cosr[0] * v[8];
/*      */     } 
/*      */     
/* 2784 */     return 0;
/*      */   }
/*      */   
/*      */   static double max(double a, double b) {
/* 2788 */     if (a > b) {
/* 2789 */       return a;
/*      */     }
/* 2791 */     return b;
/*      */   }
/*      */   
/*      */   static double min(double a, double b) {
/* 2795 */     if (a < b) {
/* 2796 */       return a;
/*      */     }
/* 2798 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   static double d_sign(double a, double b) {
/* 2803 */     double x = (a >= 0.0D) ? a : -a;
/* 2804 */     return (b >= 0.0D) ? x : -x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_shift(double f, double g, double h) {
/* 2812 */     double ssmin, fa = Math.abs(f);
/* 2813 */     double ga = Math.abs(g);
/* 2814 */     double ha = Math.abs(h);
/* 2815 */     double fhmn = min(fa, ha);
/* 2816 */     double fhmx = max(fa, ha);
/* 2817 */     if (fhmn == 0.0D) {
/* 2818 */       ssmin = 0.0D;
/* 2819 */       if (fhmx != 0.0D)
/*      */       {
/* 2821 */         double d = min(fhmx, ga) / max(fhmx, ga);
/*      */       }
/*      */     }
/* 2824 */     else if (ga < fhmx) {
/* 2825 */       double as = fhmn / fhmx + 1.0D;
/* 2826 */       double at = (fhmx - fhmn) / fhmx;
/* 2827 */       double d__1 = ga / fhmx;
/* 2828 */       double au = d__1 * d__1;
/* 2829 */       double c = 2.0D / (Math.sqrt(as * as + au) + Math.sqrt(at * at + au));
/* 2830 */       ssmin = fhmn * c;
/*      */     } else {
/* 2832 */       double au = fhmx / ga;
/* 2833 */       if (au == 0.0D) {
/* 2834 */         ssmin = fhmn * fhmx / ga;
/*      */       } else {
/* 2836 */         double as = fhmn / fhmx + 1.0D;
/* 2837 */         double at = (fhmx - fhmn) / fhmx;
/* 2838 */         double d__1 = as * au;
/* 2839 */         double d__2 = at * au;
/* 2840 */         double c = 1.0D / (Math.sqrt(d__1 * d__1 + 1.0D) + Math.sqrt(d__2 * d__2 + 1.0D));
/* 2841 */         ssmin = fhmn * c * au;
/* 2842 */         ssmin += ssmin;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2847 */     return ssmin;
/*      */   }
/*      */ 
/*      */   
/*      */   static int compute_2X2(double f, double g, double h, double[] single_values, double[] snl, double[] csl, double[] snr, double[] csr, int index) {
/*      */     boolean swap;
/* 2853 */     double c_b3 = 2.0D;
/* 2854 */     double c_b4 = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2866 */     double ssmax = single_values[0];
/* 2867 */     double ssmin = single_values[1];
/* 2868 */     double clt = 0.0D;
/* 2869 */     double crt = 0.0D;
/* 2870 */     double slt = 0.0D;
/* 2871 */     double srt = 0.0D;
/* 2872 */     double tsign = 0.0D;
/*      */     
/* 2874 */     double ft = f;
/* 2875 */     double fa = Math.abs(ft);
/* 2876 */     double ht = h;
/* 2877 */     double ha = Math.abs(h);
/*      */     
/* 2879 */     int pmax = 1;
/* 2880 */     if (ha > fa) {
/* 2881 */       swap = true;
/*      */     } else {
/* 2883 */       swap = false;
/*      */     } 
/* 2885 */     if (swap) {
/* 2886 */       pmax = 3;
/* 2887 */       double temp = ft;
/* 2888 */       ft = ht;
/* 2889 */       ht = temp;
/* 2890 */       temp = fa;
/* 2891 */       fa = ha;
/* 2892 */       ha = temp;
/*      */     } 
/*      */     
/* 2895 */     double gt = g;
/* 2896 */     double ga = Math.abs(gt);
/* 2897 */     if (ga == 0.0D) {
/*      */       
/* 2899 */       single_values[1] = ha;
/* 2900 */       single_values[0] = fa;
/* 2901 */       clt = 1.0D;
/* 2902 */       crt = 1.0D;
/* 2903 */       slt = 0.0D;
/* 2904 */       srt = 0.0D;
/*      */     } else {
/* 2906 */       boolean gasmal = true;
/*      */       
/* 2908 */       if (ga > fa) {
/* 2909 */         pmax = 2;
/* 2910 */         if (fa / ga < 1.110223024E-16D) {
/*      */           
/* 2912 */           gasmal = false;
/* 2913 */           ssmax = ga;
/* 2914 */           if (ha > 1.0D) {
/* 2915 */             ssmin = fa / ga / ha;
/*      */           } else {
/* 2917 */             ssmin = fa / ga * ha;
/*      */           } 
/* 2919 */           clt = 1.0D;
/* 2920 */           slt = ht / gt;
/* 2921 */           srt = 1.0D;
/* 2922 */           crt = ft / gt;
/*      */         } 
/*      */       } 
/* 2925 */       if (gasmal) {
/*      */         
/* 2927 */         double l, r, d = fa - ha;
/* 2928 */         if (d == fa) {
/*      */           
/* 2930 */           l = 1.0D;
/*      */         } else {
/* 2932 */           l = d / fa;
/*      */         } 
/*      */         
/* 2935 */         double m = gt / ft;
/*      */         
/* 2937 */         double t = 2.0D - l;
/*      */         
/* 2939 */         double mm = m * m;
/* 2940 */         double tt = t * t;
/* 2941 */         double s = Math.sqrt(tt + mm);
/*      */         
/* 2943 */         if (l == 0.0D) {
/* 2944 */           r = Math.abs(m);
/*      */         } else {
/* 2946 */           r = Math.sqrt(l * l + mm);
/*      */         } 
/*      */         
/* 2949 */         double a = (s + r) * 0.5D;
/*      */         
/* 2951 */         if (ga > fa) {
/* 2952 */           pmax = 2;
/* 2953 */           if (fa / ga < 1.110223024E-16D) {
/*      */             
/* 2955 */             gasmal = false;
/* 2956 */             ssmax = ga;
/* 2957 */             if (ha > 1.0D) {
/* 2958 */               ssmin = fa / ga / ha;
/*      */             } else {
/* 2960 */               ssmin = fa / ga * ha;
/*      */             } 
/* 2962 */             clt = 1.0D;
/* 2963 */             slt = ht / gt;
/* 2964 */             srt = 1.0D;
/* 2965 */             crt = ft / gt;
/*      */           } 
/*      */         } 
/* 2968 */         if (gasmal) {
/*      */           
/* 2970 */           d = fa - ha;
/* 2971 */           if (d == fa) {
/*      */             
/* 2973 */             l = 1.0D;
/*      */           } else {
/* 2975 */             l = d / fa;
/*      */           } 
/*      */           
/* 2978 */           m = gt / ft;
/*      */           
/* 2980 */           t = 2.0D - l;
/*      */           
/* 2982 */           mm = m * m;
/* 2983 */           tt = t * t;
/* 2984 */           s = Math.sqrt(tt + mm);
/*      */           
/* 2986 */           if (l == 0.0D) {
/* 2987 */             r = Math.abs(m);
/*      */           } else {
/* 2989 */             r = Math.sqrt(l * l + mm);
/*      */           } 
/*      */           
/* 2992 */           a = (s + r) * 0.5D;
/*      */ 
/*      */           
/* 2995 */           ssmin = ha / a;
/* 2996 */           ssmax = fa * a;
/* 2997 */           if (mm == 0.0D) {
/*      */             
/* 2999 */             if (l == 0.0D) {
/* 3000 */               t = d_sign(c_b3, ft) * d_sign(c_b4, gt);
/*      */             } else {
/* 3002 */               t = gt / d_sign(d, ft) + m / t;
/*      */             } 
/*      */           } else {
/* 3005 */             t = (m / (s + t) + m / (r + l)) * (a + 1.0D);
/*      */           } 
/* 3007 */           l = Math.sqrt(t * t + 4.0D);
/* 3008 */           crt = 2.0D / l;
/* 3009 */           srt = t / l;
/* 3010 */           clt = (crt + srt * m) / a;
/* 3011 */           slt = ht / ft * srt / a;
/*      */         } 
/*      */       } 
/* 3014 */       if (swap) {
/* 3015 */         csl[0] = srt;
/* 3016 */         snl[0] = crt;
/* 3017 */         csr[0] = slt;
/* 3018 */         snr[0] = clt;
/*      */       } else {
/* 3020 */         csl[0] = clt;
/* 3021 */         snl[0] = slt;
/* 3022 */         csr[0] = crt;
/* 3023 */         snr[0] = srt;
/*      */       } 
/*      */       
/* 3026 */       if (pmax == 1) {
/* 3027 */         tsign = d_sign(c_b4, csr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, f);
/*      */       }
/* 3029 */       if (pmax == 2) {
/* 3030 */         tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, csl[0]) * d_sign(c_b4, g);
/*      */       }
/* 3032 */       if (pmax == 3) {
/* 3033 */         tsign = d_sign(c_b4, snr[0]) * d_sign(c_b4, snl[0]) * d_sign(c_b4, h);
/*      */       }
/* 3035 */       single_values[index] = d_sign(ssmax, tsign);
/* 3036 */       double d__1 = tsign * d_sign(c_b4, f) * d_sign(c_b4, h);
/* 3037 */       single_values[index + 1] = d_sign(ssmin, d__1);
/*      */     } 
/*      */ 
/*      */     
/* 3041 */     return 0;
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
/*      */   static double compute_rot(double f, double g, double[] sin, double[] cos, int index, int first) {
/* 3053 */     double cs, sn, r, safmn2 = 2.002083095183101E-146D;
/* 3054 */     double safmx2 = 4.9947976805055876E145D;
/*      */     
/* 3056 */     if (g == 0.0D) {
/* 3057 */       cs = 1.0D;
/* 3058 */       sn = 0.0D;
/* 3059 */       r = f;
/* 3060 */     } else if (f == 0.0D) {
/* 3061 */       cs = 0.0D;
/* 3062 */       sn = 1.0D;
/* 3063 */       r = g;
/*      */     } else {
/* 3065 */       double f1 = f;
/* 3066 */       double g1 = g;
/* 3067 */       double scale = max(Math.abs(f1), Math.abs(g1));
/* 3068 */       if (scale >= 4.9947976805055876E145D) {
/* 3069 */         int count = 0;
/* 3070 */         while (scale >= 4.9947976805055876E145D) {
/* 3071 */           count++;
/* 3072 */           f1 *= 2.002083095183101E-146D;
/* 3073 */           g1 *= 2.002083095183101E-146D;
/* 3074 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 3076 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 3077 */         cs = f1 / r;
/* 3078 */         sn = g1 / r;
/* 3079 */         int i__1 = count;
/* 3080 */         for (int i = 1; i <= count; i++) {
/* 3081 */           r *= 4.9947976805055876E145D;
/*      */         }
/* 3083 */       } else if (scale <= 2.002083095183101E-146D) {
/* 3084 */         int count = 0;
/* 3085 */         while (scale <= 2.002083095183101E-146D) {
/* 3086 */           count++;
/* 3087 */           f1 *= 4.9947976805055876E145D;
/* 3088 */           g1 *= 4.9947976805055876E145D;
/* 3089 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 3091 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 3092 */         cs = f1 / r;
/* 3093 */         sn = g1 / r;
/* 3094 */         int i__1 = count;
/* 3095 */         for (int i = 1; i <= count; i++) {
/* 3096 */           r *= 2.002083095183101E-146D;
/*      */         }
/*      */       } else {
/* 3099 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 3100 */         cs = f1 / r;
/* 3101 */         sn = g1 / r;
/*      */       } 
/* 3103 */       if (Math.abs(f) > Math.abs(g) && cs < 0.0D) {
/* 3104 */         cs = -cs;
/* 3105 */         sn = -sn;
/* 3106 */         r = -r;
/*      */       } 
/*      */     } 
/* 3109 */     sin[index] = sn;
/* 3110 */     cos[index] = cs;
/* 3111 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void print_mat(double[] mat) {
/* 3117 */     for (int i = 0; i < 3; i++) {
/* 3118 */       System.out.println(String.valueOf(mat[i * 3 + 0]) + " " + mat[i * 3 + 1] + " " + mat[i * 3 + 2] + "\n");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void print_det(double[] mat) {
/* 3126 */     double det = mat[0] * mat[4] * mat[8] + 
/* 3127 */       mat[1] * mat[5] * mat[6] + 
/* 3128 */       mat[2] * mat[3] * mat[7] - 
/* 3129 */       mat[2] * mat[4] * mat[6] - 
/* 3130 */       mat[0] * mat[5] * mat[7] - 
/* 3131 */       mat[1] * mat[3] * mat[8];
/* 3132 */     System.out.println("det= " + det);
/*      */   }
/*      */ 
/*      */   
/*      */   static void mat_mul(double[] m1, double[] m2, double[] m3) {
/* 3137 */     double[] tmp = new double[9];
/*      */     
/* 3139 */     tmp[0] = m1[0] * m2[0] + m1[1] * m2[3] + m1[2] * m2[6];
/* 3140 */     tmp[1] = m1[0] * m2[1] + m1[1] * m2[4] + m1[2] * m2[7];
/* 3141 */     tmp[2] = m1[0] * m2[2] + m1[1] * m2[5] + m1[2] * m2[8];
/*      */     
/* 3143 */     tmp[3] = m1[3] * m2[0] + m1[4] * m2[3] + m1[5] * m2[6];
/* 3144 */     tmp[4] = m1[3] * m2[1] + m1[4] * m2[4] + m1[5] * m2[7];
/* 3145 */     tmp[5] = m1[3] * m2[2] + m1[4] * m2[5] + m1[5] * m2[8];
/*      */     
/* 3147 */     tmp[6] = m1[6] * m2[0] + m1[7] * m2[3] + m1[8] * m2[6];
/* 3148 */     tmp[7] = m1[6] * m2[1] + m1[7] * m2[4] + m1[8] * m2[7];
/* 3149 */     tmp[8] = m1[6] * m2[2] + m1[7] * m2[5] + m1[8] * m2[8];
/*      */     
/* 3151 */     for (int i = 0; i < 9; i++) {
/* 3152 */       m3[i] = tmp[i];
/*      */     }
/*      */   }
/*      */   
/*      */   static void transpose_mat(double[] in, double[] out) {
/* 3157 */     out[0] = in[0];
/* 3158 */     out[1] = in[3];
/* 3159 */     out[2] = in[6];
/*      */     
/* 3161 */     out[3] = in[1];
/* 3162 */     out[4] = in[4];
/* 3163 */     out[5] = in[7];
/*      */     
/* 3165 */     out[6] = in[2];
/* 3166 */     out[7] = in[5];
/* 3167 */     out[8] = in[8];
/*      */   }
/*      */   
/*      */   static double max3(double[] values) {
/* 3171 */     if (values[0] > values[1]) {
/* 3172 */       if (values[0] > values[2]) {
/* 3173 */         return values[0];
/*      */       }
/* 3175 */       return values[2];
/*      */     } 
/* 3177 */     if (values[1] > values[2]) {
/* 3178 */       return values[1];
/*      */     }
/* 3180 */     return values[2];
/*      */   }
/*      */ 
/*      */   
/*      */   private static final boolean almostEqual(double a, double b) {
/* 3185 */     if (a == b) {
/* 3186 */       return true;
/*      */     }
/* 3188 */     double EPSILON_ABSOLUTE = 1.0E-6D;
/* 3189 */     double EPSILON_RELATIVE = 1.0E-4D;
/* 3190 */     double diff = Math.abs(a - b);
/* 3191 */     double absA = Math.abs(a);
/* 3192 */     double absB = Math.abs(b);
/* 3193 */     double max = (absA >= absB) ? absA : absB;
/*      */     
/* 3195 */     if (diff < 1.0E-6D) {
/* 3196 */       return true;
/*      */     }
/* 3198 */     if (diff / max < 1.0E-4D) {
/* 3199 */       return true;
/*      */     }
/* 3201 */     return false;
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
/*      */   public Object clone() {
/* 3214 */     Matrix3d m1 = null;
/*      */     try {
/* 3216 */       m1 = (Matrix3d)super.clone();
/* 3217 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3219 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 3223 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM00() {
/* 3233 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(double m00) {
/* 3243 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM01() {
/* 3253 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(double m01) {
/* 3263 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM02() {
/* 3273 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(double m02) {
/* 3283 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM10() {
/* 3293 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(double m10) {
/* 3303 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM11() {
/* 3313 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(double m11) {
/* 3323 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM12() {
/* 3333 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(double m12) {
/* 3343 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM20() {
/* 3353 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(double m20) {
/* 3363 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM21() {
/* 3373 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(double m21) {
/* 3383 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM22() {
/* 3393 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(double m22) {
/* 3403 */     this.m22 = m22;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Matrix3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */