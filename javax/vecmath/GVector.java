/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GVector
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private int length;
/*     */   double[] values;
/*     */   static final long serialVersionUID = 1398850036893875112L;
/*     */   
/*     */   public GVector(int length) {
/*  52 */     this.length = length;
/*  53 */     this.values = new double[length];
/*  54 */     for (int i = 0; i < length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(double[] vector) {
/*  68 */     this.length = vector.length;
/*  69 */     this.values = new double[vector.length];
/*  70 */     for (int i = 0; i < this.length; ) { this.values[i] = vector[i]; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(GVector vector) {
/*  82 */     this.values = new double[vector.length];
/*  83 */     this.length = vector.length;
/*  84 */     for (int i = 0; i < this.length; ) { this.values[i] = vector.values[i]; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple2f tuple) {
/*  94 */     this.values = new double[2];
/*  95 */     this.values[0] = tuple.x;
/*  96 */     this.values[1] = tuple.y;
/*  97 */     this.length = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3f tuple) {
/* 107 */     this.values = new double[3];
/* 108 */     this.values[0] = tuple.x;
/* 109 */     this.values[1] = tuple.y;
/* 110 */     this.values[2] = tuple.z;
/* 111 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3d tuple) {
/* 121 */     this.values = new double[3];
/* 122 */     this.values[0] = tuple.x;
/* 123 */     this.values[1] = tuple.y;
/* 124 */     this.values[2] = tuple.z;
/* 125 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4f tuple) {
/* 135 */     this.values = new double[4];
/* 136 */     this.values[0] = tuple.x;
/* 137 */     this.values[1] = tuple.y;
/* 138 */     this.values[2] = tuple.z;
/* 139 */     this.values[3] = tuple.w;
/* 140 */     this.length = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4d tuple) {
/* 150 */     this.values = new double[4];
/* 151 */     this.values[0] = tuple.x;
/* 152 */     this.values[1] = tuple.y;
/* 153 */     this.values[2] = tuple.z;
/* 154 */     this.values[3] = tuple.w;
/* 155 */     this.length = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(double[] vector, int length) {
/* 172 */     this.length = length;
/* 173 */     this.values = new double[length];
/* 174 */     for (int i = 0; i < length; i++) {
/* 175 */       this.values[i] = vector[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double norm() {
/* 187 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 190 */     for (int i = 0; i < this.length; i++) {
/* 191 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */     
/* 194 */     return Math.sqrt(sq);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double normSquared() {
/* 205 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 208 */     for (int i = 0; i < this.length; i++) {
/* 209 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */     
/* 212 */     return sq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(GVector v1) {
/* 221 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 224 */     if (this.length != v1.length)
/* 225 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector0")); 
/*     */     int i;
/* 227 */     for (i = 0; i < this.length; i++) {
/* 228 */       sq += v1.values[i] * v1.values[i];
/*     */     }
/*     */ 
/*     */     
/* 232 */     double invMag = 1.0D / Math.sqrt(sq);
/*     */     
/* 234 */     for (i = 0; i < this.length; i++) {
/* 235 */       this.values[i] = v1.values[i] * invMag;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 244 */     double sq = 0.0D;
/*     */     
/*     */     int i;
/* 247 */     for (i = 0; i < this.length; i++) {
/* 248 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */ 
/*     */     
/* 252 */     double invMag = 1.0D / Math.sqrt(sq);
/*     */     
/* 254 */     for (i = 0; i < this.length; i++) {
/* 255 */       this.values[i] = this.values[i] * invMag;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(double s, GVector v1) {
/* 269 */     if (this.length != v1.length) {
/* 270 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector1"));
/*     */     }
/* 272 */     for (int i = 0; i < this.length; i++) {
/* 273 */       this.values[i] = v1.values[i] * s;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(double s) {
/* 285 */     for (int i = 0; i < this.length; i++) {
/* 286 */       this.values[i] = this.values[i] * s;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, GVector v1, GVector v2) {
/* 302 */     if (v2.length != v1.length) {
/* 303 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector2"));
/*     */     }
/* 305 */     if (this.length != v1.length) {
/* 306 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector3"));
/*     */     }
/* 308 */     for (int i = 0; i < this.length; i++) {
/* 309 */       this.values[i] = v1.values[i] * s + v2.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(GVector vector) {
/* 322 */     if (this.length != vector.length) {
/* 323 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector4"));
/*     */     }
/* 325 */     for (int i = 0; i < this.length; i++) {
/* 326 */       this.values[i] = this.values[i] + vector.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(GVector vector1, GVector vector2) {
/* 340 */     if (vector1.length != vector2.length) {
/* 341 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector5"));
/*     */     }
/* 343 */     if (this.length != vector1.length) {
/* 344 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector6"));
/*     */     }
/* 346 */     for (int i = 0; i < this.length; i++) {
/* 347 */       this.values[i] = vector1.values[i] + vector2.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(GVector vector) {
/* 359 */     if (this.length != vector.length) {
/* 360 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector7"));
/*     */     }
/* 362 */     for (int i = 0; i < this.length; i++) {
/* 363 */       this.values[i] = this.values[i] - vector.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(GVector vector1, GVector vector2) {
/* 378 */     if (vector1.length != vector2.length) {
/* 379 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector8"));
/*     */     }
/* 381 */     if (this.length != vector1.length) {
/* 382 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector9"));
/*     */     }
/* 384 */     for (int i = 0; i < this.length; i++) {
/* 385 */       this.values[i] = vector1.values[i] - vector2.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mul(GMatrix m1, GVector v1) {
/*     */     double[] v;
/* 396 */     if (m1.getNumCol() != v1.length) {
/* 397 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector10"));
/*     */     }
/* 399 */     if (this.length != m1.getNumRow()) {
/* 400 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector11"));
/*     */     }
/*     */     
/* 403 */     if (v1 != this) {
/* 404 */       v = v1.values;
/*     */     } else {
/* 406 */       v = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 409 */     for (int j = this.length - 1; j >= 0; j--) {
/* 410 */       this.values[j] = 0.0D;
/* 411 */       for (int i = v1.length - 1; i >= 0; i--) {
/* 412 */         this.values[j] = this.values[j] + m1.values[j][i] * v[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mul(GVector v1, GMatrix m1) {
/*     */     double[] v;
/* 429 */     if (m1.getNumRow() != v1.length) {
/* 430 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector12"));
/*     */     }
/* 432 */     if (this.length != m1.getNumCol()) {
/* 433 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector13"));
/*     */     }
/*     */     
/* 436 */     if (v1 != this) {
/* 437 */       v = v1.values;
/*     */     } else {
/* 439 */       v = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 442 */     for (int j = this.length - 1; j >= 0; j--) {
/* 443 */       this.values[j] = 0.0D;
/* 444 */       for (int i = v1.length - 1; i >= 0; i--) {
/* 445 */         this.values[j] = this.values[j] + m1.values[i][j] * v[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 454 */     for (int i = this.length - 1; i >= 0; i--) {
/* 455 */       this.values[i] = this.values[i] * -1.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void zero() {
/* 463 */     for (int i = 0; i < this.length; i++) {
/* 464 */       this.values[i] = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSize(int length) {
/*     */     int max;
/* 476 */     double[] tmp = new double[length];
/*     */ 
/*     */     
/* 479 */     if (this.length < length) {
/* 480 */       max = this.length;
/*     */     } else {
/* 482 */       max = length;
/*     */     } 
/* 484 */     for (int i = 0; i < max; i++) {
/* 485 */       tmp[i] = this.values[i];
/*     */     }
/* 487 */     this.length = length;
/*     */     
/* 489 */     this.values = tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] vector) {
/* 501 */     for (int i = this.length - 1; i >= 0; i--) {
/* 502 */       this.values[i] = vector[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(GVector vector) {
/* 513 */     if (this.length < vector.length) {
/* 514 */       this.length = vector.length;
/* 515 */       this.values = new double[this.length];
/* 516 */       for (int i = 0; i < this.length; i++)
/* 517 */         this.values[i] = vector.values[i]; 
/*     */     } else {
/* 519 */       int i; for (i = 0; i < vector.length; i++)
/* 520 */         this.values[i] = vector.values[i]; 
/* 521 */       for (i = vector.length; i < this.length; i++) {
/* 522 */         this.values[i] = 0.0D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f tuple) {
/* 532 */     if (this.length < 2) {
/* 533 */       this.length = 2;
/* 534 */       this.values = new double[2];
/*     */     } 
/* 536 */     this.values[0] = tuple.x;
/* 537 */     this.values[1] = tuple.y;
/* 538 */     for (int i = 2; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f tuple) {
/* 548 */     if (this.length < 3) {
/* 549 */       this.length = 3;
/* 550 */       this.values = new double[3];
/*     */     } 
/* 552 */     this.values[0] = tuple.x;
/* 553 */     this.values[1] = tuple.y;
/* 554 */     this.values[2] = tuple.z;
/* 555 */     for (int i = 3; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d tuple) {
/* 564 */     if (this.length < 3) {
/* 565 */       this.length = 3;
/* 566 */       this.values = new double[3];
/*     */     } 
/* 568 */     this.values[0] = tuple.x;
/* 569 */     this.values[1] = tuple.y;
/* 570 */     this.values[2] = tuple.z;
/* 571 */     for (int i = 3; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f tuple) {
/* 580 */     if (this.length < 4) {
/* 581 */       this.length = 4;
/* 582 */       this.values = new double[4];
/*     */     } 
/* 584 */     this.values[0] = tuple.x;
/* 585 */     this.values[1] = tuple.y;
/* 586 */     this.values[2] = tuple.z;
/* 587 */     this.values[3] = tuple.w;
/* 588 */     for (int i = 4; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d tuple) {
/* 597 */     if (this.length < 4) {
/* 598 */       this.length = 4;
/* 599 */       this.values = new double[4];
/*     */     } 
/* 601 */     this.values[0] = tuple.x;
/* 602 */     this.values[1] = tuple.y;
/* 603 */     this.values[2] = tuple.z;
/* 604 */     this.values[3] = tuple.w;
/* 605 */     for (int i = 4; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSize() {
/* 614 */     return this.values.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getElement(int index) {
/* 624 */     return this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setElement(int index, double value) {
/* 635 */     this.values[index] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 645 */     StringBuffer buffer = new StringBuffer(this.length * 8);
/*     */ 
/*     */ 
/*     */     
/* 649 */     for (int i = 0; i < this.length; i++) {
/* 650 */       buffer.append(this.values[i]).append(" ");
/*     */     }
/*     */     
/* 653 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 670 */     long bits = 1L;
/*     */     
/* 672 */     for (int i = 0; i < this.length; i++) {
/* 673 */       bits = VecMathUtil.hashDoubleBits(bits, this.values[i]);
/*     */     }
/*     */     
/* 676 */     return VecMathUtil.hashFinish(bits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(GVector vector1) {
/*     */     try {
/* 689 */       if (this.length != vector1.length) return false;
/*     */       
/* 691 */       for (int i = 0; i < this.length; i++) {
/* 692 */         if (this.values[i] != vector1.values[i]) return false;
/*     */       
/*     */       } 
/* 695 */       return true;
/* 696 */     } catch (NullPointerException e2) {
/* 697 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o1) {
/*     */     try {
/* 713 */       GVector v2 = (GVector)o1;
/*     */       
/* 715 */       if (this.length != v2.length) return false;
/*     */       
/* 717 */       for (int i = 0; i < this.length; i++) {
/* 718 */         if (this.values[i] != v2.values[i]) return false; 
/*     */       } 
/* 720 */       return true;
/* 721 */     } catch (ClassCastException e1) {
/* 722 */       return false;
/* 723 */     } catch (NullPointerException e2) {
/* 724 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean epsilonEquals(GVector v1, double epsilon) {
/* 742 */     if (this.length != v1.length) return false;
/*     */     
/* 744 */     for (int i = 0; i < this.length; i++) {
/* 745 */       double diff = this.values[i] - v1.values[i];
/* 746 */       if (((diff < 0.0D) ? -diff : diff) > epsilon) return false; 
/*     */     } 
/* 748 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(GVector v1) {
/* 758 */     if (this.length != v1.length) {
/* 759 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector14"));
/*     */     }
/* 761 */     double result = 0.0D;
/* 762 */     for (int i = 0; i < this.length; i++) {
/* 763 */       result += this.values[i] * v1.values[i];
/*     */     }
/* 765 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void SVDBackSolve(GMatrix U, GMatrix W, GMatrix V, GVector b) {
/* 782 */     if (U.nRow != b.getSize() || 
/* 783 */       U.nRow != U.nCol || 
/* 784 */       U.nRow != W.nRow) {
/* 785 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector15"));
/*     */     }
/*     */     
/* 788 */     if (W.nCol != this.values.length || 
/* 789 */       W.nCol != V.nCol || 
/* 790 */       W.nCol != V.nRow) {
/* 791 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector23"));
/*     */     }
/*     */     
/* 794 */     GMatrix tmp = new GMatrix(U.nRow, W.nCol);
/* 795 */     tmp.mul(U, V);
/* 796 */     tmp.mulTransposeRight(U, W);
/* 797 */     tmp.invert();
/* 798 */     mul(tmp, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void LUDBackSolve(GMatrix LU, GVector b, GVector permutation) {
/* 816 */     int size = LU.nRow * LU.nCol;
/*     */     
/* 818 */     double[] temp = new double[size];
/* 819 */     double[] result = new double[size];
/* 820 */     int[] row_perm = new int[b.getSize()];
/*     */ 
/*     */     
/* 823 */     if (LU.nRow != b.getSize()) {
/* 824 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector16"));
/*     */     }
/*     */     
/* 827 */     if (LU.nRow != permutation.getSize()) {
/* 828 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector24"));
/*     */     }
/*     */     
/* 831 */     if (LU.nRow != LU.nCol) {
/* 832 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector25"));
/*     */     }
/*     */     int i;
/* 835 */     for (i = 0; i < LU.nRow; i++) {
/* 836 */       for (int j = 0; j < LU.nCol; j++) {
/* 837 */         temp[i * LU.nCol + j] = LU.values[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 841 */     for (i = 0; i < size; ) { result[i] = 0.0D; i++; }
/* 842 */      for (i = 0; i < LU.nRow; ) { result[i * LU.nCol] = b.values[i]; i++; }
/* 843 */      for (i = 0; i < LU.nCol; ) { row_perm[i] = (int)permutation.values[i]; i++; }
/*     */     
/* 845 */     GMatrix.luBacksubstitution(LU.nRow, temp, row_perm, result);
/*     */     
/* 847 */     for (i = 0; i < LU.nRow; ) { this.values[i] = result[i * LU.nCol]; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double angle(GVector v1) {
/* 859 */     return Math.acos(dot(v1) / norm() * v1.norm());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, GVector v2, float alpha) {
/* 867 */     interpolate(v1, v2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, float alpha) {
/* 875 */     interpolate(v1, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, GVector v2, double alpha) {
/* 888 */     if (v2.length != v1.length) {
/* 889 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector20"));
/*     */     }
/* 891 */     if (this.length != v1.length) {
/* 892 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector21"));
/*     */     }
/* 894 */     for (int i = 0; i < this.length; i++) {
/* 895 */       this.values[i] = (1.0D - alpha) * v1.values[i] + alpha * v2.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, double alpha) {
/* 907 */     if (v1.length != this.length) {
/* 908 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector22"));
/*     */     }
/* 910 */     for (int i = 0; i < this.length; i++) {
/* 911 */       this.values[i] = (1.0D - alpha) * this.values[i] + alpha * v1.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 925 */     GVector v1 = null;
/*     */     try {
/* 927 */       v1 = (GVector)super.clone();
/* 928 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 930 */       throw new InternalError();
/*     */     } 
/*     */ 
/*     */     
/* 934 */     v1.values = new double[this.length];
/* 935 */     for (int i = 0; i < this.length; i++) {
/* 936 */       v1.values[i] = this.values[i];
/*     */     }
/*     */     
/* 939 */     return v1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\GVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */