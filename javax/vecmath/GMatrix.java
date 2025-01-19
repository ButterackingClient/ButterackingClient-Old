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
/*      */ public class GMatrix
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 2777097312029690941L;
/*      */   private static final boolean debug = false;
/*      */   int nRow;
/*      */   int nCol;
/*      */   double[][] values;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public GMatrix(int nRow, int nCol) {
/*      */     int l;
/*   60 */     this.values = new double[nRow][nCol];
/*   61 */     this.nRow = nRow;
/*   62 */     this.nCol = nCol;
/*      */     
/*      */     int i;
/*   65 */     for (i = 0; i < nRow; i++) {
/*   66 */       for (int j = 0; j < nCol; j++) {
/*   67 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*   72 */     if (nRow < nCol) {
/*   73 */       l = nRow;
/*      */     } else {
/*   75 */       l = nCol;
/*      */     } 
/*   77 */     for (i = 0; i < l; i++) {
/*   78 */       this.values[i][i] = 1.0D;
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
/*      */   public GMatrix(int nRow, int nCol, double[] matrix) {
/*   96 */     this.values = new double[nRow][nCol];
/*   97 */     this.nRow = nRow;
/*   98 */     this.nCol = nCol;
/*      */ 
/*      */     
/*  101 */     for (int i = 0; i < nRow; i++) {
/*  102 */       for (int j = 0; j < nCol; j++) {
/*  103 */         this.values[i][j] = matrix[i * nCol + j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GMatrix(GMatrix matrix) {
/*  115 */     this.nRow = matrix.nRow;
/*  116 */     this.nCol = matrix.nCol;
/*  117 */     this.values = new double[this.nRow][this.nCol];
/*      */ 
/*      */     
/*  120 */     for (int i = 0; i < this.nRow; i++) {
/*  121 */       for (int j = 0; j < this.nCol; j++) {
/*  122 */         this.values[i][j] = matrix.values[i][j];
/*      */       }
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
/*      */   public final void mul(GMatrix m1) {
/*  136 */     if (this.nCol != m1.nRow || this.nCol != m1.nCol) {
/*  137 */       throw new MismatchedSizeException(
/*  138 */           VecMathI18N.getString("GMatrix0"));
/*      */     }
/*  140 */     double[][] tmp = new double[this.nRow][this.nCol];
/*      */     
/*  142 */     for (int i = 0; i < this.nRow; i++) {
/*  143 */       for (int j = 0; j < this.nCol; j++) {
/*  144 */         tmp[i][j] = 0.0D;
/*  145 */         for (int k = 0; k < this.nCol; k++) {
/*  146 */           tmp[i][j] = tmp[i][j] + this.values[i][k] * m1.values[k][j];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  151 */     this.values = tmp;
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
/*      */   public final void mul(GMatrix m1, GMatrix m2) {
/*  164 */     if (m1.nCol != m2.nRow || this.nRow != m1.nRow || this.nCol != m2.nCol) {
/*  165 */       throw new MismatchedSizeException(
/*  166 */           VecMathI18N.getString("GMatrix1"));
/*      */     }
/*  168 */     double[][] tmp = new double[this.nRow][this.nCol];
/*      */     
/*  170 */     for (int i = 0; i < m1.nRow; i++) {
/*  171 */       for (int j = 0; j < m2.nCol; j++) {
/*  172 */         tmp[i][j] = 0.0D;
/*  173 */         for (int k = 0; k < m1.nCol; k++) {
/*  174 */           tmp[i][j] = tmp[i][j] + m1.values[i][k] * m2.values[k][j];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  179 */     this.values = tmp;
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
/*      */   public final void mul(GVector v1, GVector v2) {
/*  194 */     if (this.nRow < v1.getSize()) {
/*  195 */       throw new MismatchedSizeException(
/*  196 */           VecMathI18N.getString("GMatrix2"));
/*      */     }
/*  198 */     if (this.nCol < v2.getSize()) {
/*  199 */       throw new MismatchedSizeException(
/*  200 */           VecMathI18N.getString("GMatrix3"));
/*      */     }
/*  202 */     for (int i = 0; i < v1.getSize(); i++) {
/*  203 */       for (int j = 0; j < v2.getSize(); j++) {
/*  204 */         this.values[i][j] = v1.values[i] * v2.values[j];
/*      */       }
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
/*      */   public final void add(GMatrix m1) {
/*  217 */     if (this.nRow != m1.nRow) {
/*  218 */       throw new MismatchedSizeException(
/*  219 */           VecMathI18N.getString("GMatrix4"));
/*      */     }
/*  221 */     if (this.nCol != m1.nCol) {
/*  222 */       throw new MismatchedSizeException(
/*  223 */           VecMathI18N.getString("GMatrix5"));
/*      */     }
/*  225 */     for (int i = 0; i < this.nRow; i++) {
/*  226 */       for (int j = 0; j < this.nCol; j++) {
/*  227 */         this.values[i][j] = this.values[i][j] + m1.values[i][j];
/*      */       }
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
/*      */   public final void add(GMatrix m1, GMatrix m2) {
/*  241 */     if (m2.nRow != m1.nRow) {
/*  242 */       throw new MismatchedSizeException(
/*  243 */           VecMathI18N.getString("GMatrix6"));
/*      */     }
/*  245 */     if (m2.nCol != m1.nCol) {
/*  246 */       throw new MismatchedSizeException(
/*  247 */           VecMathI18N.getString("GMatrix7"));
/*      */     }
/*  249 */     if (this.nCol != m1.nCol || this.nRow != m1.nRow) {
/*  250 */       throw new MismatchedSizeException(
/*  251 */           VecMathI18N.getString("GMatrix8"));
/*      */     }
/*  253 */     for (int i = 0; i < this.nRow; i++) {
/*  254 */       for (int j = 0; j < this.nCol; j++) {
/*  255 */         this.values[i][j] = m1.values[i][j] + m2.values[i][j];
/*      */       }
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
/*      */   public final void sub(GMatrix m1) {
/*  268 */     if (this.nRow != m1.nRow) {
/*  269 */       throw new MismatchedSizeException(
/*  270 */           VecMathI18N.getString("GMatrix9"));
/*      */     }
/*  272 */     if (this.nCol != m1.nCol) {
/*  273 */       throw new MismatchedSizeException(
/*  274 */           VecMathI18N.getString("GMatrix28"));
/*      */     }
/*  276 */     for (int i = 0; i < this.nRow; i++) {
/*  277 */       for (int j = 0; j < this.nCol; j++) {
/*  278 */         this.values[i][j] = this.values[i][j] - m1.values[i][j];
/*      */       }
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
/*      */   public final void sub(GMatrix m1, GMatrix m2) {
/*  292 */     if (m2.nRow != m1.nRow) {
/*  293 */       throw new MismatchedSizeException(
/*  294 */           VecMathI18N.getString("GMatrix10"));
/*      */     }
/*  296 */     if (m2.nCol != m1.nCol) {
/*  297 */       throw new MismatchedSizeException(
/*  298 */           VecMathI18N.getString("GMatrix11"));
/*      */     }
/*  300 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/*  301 */       throw new MismatchedSizeException(
/*  302 */           VecMathI18N.getString("GMatrix12"));
/*      */     }
/*  304 */     for (int i = 0; i < this.nRow; i++) {
/*  305 */       for (int j = 0; j < this.nCol; j++) {
/*  306 */         this.values[i][j] = m1.values[i][j] - m2.values[i][j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/*  316 */     for (int i = 0; i < this.nRow; i++) {
/*  317 */       for (int j = 0; j < this.nCol; j++) {
/*  318 */         this.values[i][j] = -this.values[i][j];
/*      */       }
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
/*      */   public final void negate(GMatrix m1) {
/*  331 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/*  332 */       throw new MismatchedSizeException(
/*  333 */           VecMathI18N.getString("GMatrix13"));
/*      */     }
/*  335 */     for (int i = 0; i < this.nRow; i++) {
/*  336 */       for (int j = 0; j < this.nCol; j++) {
/*  337 */         this.values[i][j] = -m1.values[i][j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*      */     int l;
/*      */     int i;
/*  347 */     for (i = 0; i < this.nRow; i++) {
/*  348 */       for (int j = 0; j < this.nCol; j++) {
/*  349 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  354 */     if (this.nRow < this.nCol) {
/*  355 */       l = this.nRow;
/*      */     } else {
/*  357 */       l = this.nCol;
/*      */     } 
/*  359 */     for (i = 0; i < l; i++) {
/*  360 */       this.values[i][i] = 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/*  369 */     for (int i = 0; i < this.nRow; i++) {
/*  370 */       for (int j = 0; j < this.nCol; j++) {
/*  371 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void identityMinus() {
/*      */     int l;
/*      */     int i;
/*  383 */     for (i = 0; i < this.nRow; i++) {
/*  384 */       for (int j = 0; j < this.nCol; j++) {
/*  385 */         this.values[i][j] = -this.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  390 */     if (this.nRow < this.nCol) {
/*  391 */       l = this.nRow;
/*      */     } else {
/*  393 */       l = this.nCol;
/*      */     } 
/*  395 */     for (i = 0; i < l; i++) {
/*  396 */       this.values[i][i] = this.values[i][i] + 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/*  405 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(GMatrix m1) {
/*  415 */     invertGeneral(m1);
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
/*      */   public final void copySubMatrix(int rowSource, int colSource, int numRow, int numCol, int rowDest, int colDest, GMatrix target) {
/*  440 */     if (this != target) {
/*  441 */       for (int i = 0; i < numRow; i++) {
/*  442 */         for (int j = 0; j < numCol; j++) {
/*  443 */           target.values[rowDest + i][colDest + j] = 
/*  444 */             this.values[rowSource + i][colSource + j];
/*      */         }
/*      */       } 
/*      */     } else {
/*  448 */       double[][] tmp = new double[numRow][numCol]; int i;
/*  449 */       for (i = 0; i < numRow; i++) {
/*  450 */         for (int j = 0; j < numCol; j++) {
/*  451 */           tmp[i][j] = this.values[rowSource + i][colSource + j];
/*      */         }
/*      */       } 
/*  454 */       for (i = 0; i < numRow; i++) {
/*  455 */         for (int j = 0; j < numCol; j++) {
/*  456 */           target.values[rowDest + i][colDest + j] = tmp[i][j];
/*      */         }
/*      */       } 
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
/*      */   public final void setSize(int nRow, int nCol) {
/*      */     int maxRow, maxCol;
/*  471 */     double[][] tmp = new double[nRow][nCol];
/*      */ 
/*      */     
/*  474 */     if (this.nRow < nRow) {
/*  475 */       maxRow = this.nRow;
/*      */     } else {
/*  477 */       maxRow = nRow;
/*      */     } 
/*  479 */     if (this.nCol < nCol) {
/*  480 */       maxCol = this.nCol;
/*      */     } else {
/*  482 */       maxCol = nCol;
/*      */     } 
/*  484 */     for (int i = 0; i < maxRow; i++) {
/*  485 */       for (int j = 0; j < maxCol; j++) {
/*  486 */         tmp[i][j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/*  490 */     this.nRow = nRow;
/*  491 */     this.nCol = nCol;
/*      */     
/*  493 */     this.values = tmp;
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
/*      */   public final void set(double[] matrix) {
/*  508 */     for (int i = 0; i < this.nRow; i++) {
/*  509 */       for (int j = 0; j < this.nCol; j++) {
/*  510 */         this.values[i][j] = matrix[this.nCol * i + j];
/*      */       }
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
/*      */   public final void set(Matrix3f m1) {
/*  523 */     if (this.nCol < 3 || this.nRow < 3) {
/*  524 */       this.nCol = 3;
/*  525 */       this.nRow = 3;
/*  526 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     
/*  529 */     this.values[0][0] = m1.m00;
/*  530 */     this.values[0][1] = m1.m01;
/*  531 */     this.values[0][2] = m1.m02;
/*      */     
/*  533 */     this.values[1][0] = m1.m10;
/*  534 */     this.values[1][1] = m1.m11;
/*  535 */     this.values[1][2] = m1.m12;
/*      */     
/*  537 */     this.values[2][0] = m1.m20;
/*  538 */     this.values[2][1] = m1.m21;
/*  539 */     this.values[2][2] = m1.m22;
/*      */     
/*  541 */     for (int i = 3; i < this.nRow; i++) {
/*  542 */       for (int j = 3; j < this.nCol; j++) {
/*  543 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/*  554 */     if (this.nRow < 3 || this.nCol < 3) {
/*  555 */       this.values = new double[3][3];
/*  556 */       this.nRow = 3;
/*  557 */       this.nCol = 3;
/*      */     } 
/*      */     
/*  560 */     this.values[0][0] = m1.m00;
/*  561 */     this.values[0][1] = m1.m01;
/*  562 */     this.values[0][2] = m1.m02;
/*      */     
/*  564 */     this.values[1][0] = m1.m10;
/*  565 */     this.values[1][1] = m1.m11;
/*  566 */     this.values[1][2] = m1.m12;
/*      */     
/*  568 */     this.values[2][0] = m1.m20;
/*  569 */     this.values[2][1] = m1.m21;
/*  570 */     this.values[2][2] = m1.m22;
/*      */     
/*  572 */     for (int i = 3; i < this.nRow; i++) {
/*  573 */       for (int j = 3; j < this.nCol; j++) {
/*  574 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/*  586 */     if (this.nRow < 4 || this.nCol < 4) {
/*  587 */       this.values = new double[4][4];
/*  588 */       this.nRow = 4;
/*  589 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  592 */     this.values[0][0] = m1.m00;
/*  593 */     this.values[0][1] = m1.m01;
/*  594 */     this.values[0][2] = m1.m02;
/*  595 */     this.values[0][3] = m1.m03;
/*      */     
/*  597 */     this.values[1][0] = m1.m10;
/*  598 */     this.values[1][1] = m1.m11;
/*  599 */     this.values[1][2] = m1.m12;
/*  600 */     this.values[1][3] = m1.m13;
/*      */     
/*  602 */     this.values[2][0] = m1.m20;
/*  603 */     this.values[2][1] = m1.m21;
/*  604 */     this.values[2][2] = m1.m22;
/*  605 */     this.values[2][3] = m1.m23;
/*      */     
/*  607 */     this.values[3][0] = m1.m30;
/*  608 */     this.values[3][1] = m1.m31;
/*  609 */     this.values[3][2] = m1.m32;
/*  610 */     this.values[3][3] = m1.m33;
/*      */     
/*  612 */     for (int i = 4; i < this.nRow; i++) {
/*  613 */       for (int j = 4; j < this.nCol; j++) {
/*  614 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/*  625 */     if (this.nRow < 4 || this.nCol < 4) {
/*  626 */       this.values = new double[4][4];
/*  627 */       this.nRow = 4;
/*  628 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  631 */     this.values[0][0] = m1.m00;
/*  632 */     this.values[0][1] = m1.m01;
/*  633 */     this.values[0][2] = m1.m02;
/*  634 */     this.values[0][3] = m1.m03;
/*      */     
/*  636 */     this.values[1][0] = m1.m10;
/*  637 */     this.values[1][1] = m1.m11;
/*  638 */     this.values[1][2] = m1.m12;
/*  639 */     this.values[1][3] = m1.m13;
/*      */     
/*  641 */     this.values[2][0] = m1.m20;
/*  642 */     this.values[2][1] = m1.m21;
/*  643 */     this.values[2][2] = m1.m22;
/*  644 */     this.values[2][3] = m1.m23;
/*      */     
/*  646 */     this.values[3][0] = m1.m30;
/*  647 */     this.values[3][1] = m1.m31;
/*  648 */     this.values[3][2] = m1.m32;
/*  649 */     this.values[3][3] = m1.m33;
/*      */     
/*  651 */     for (int i = 4; i < this.nRow; i++) {
/*  652 */       for (int j = 4; j < this.nCol; j++) {
/*  653 */         this.values[i][j] = 0.0D;
/*      */       }
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
/*      */   public final void set(GMatrix m1) {
/*  666 */     if (this.nRow < m1.nRow || this.nCol < m1.nCol) {
/*  667 */       this.nRow = m1.nRow;
/*  668 */       this.nCol = m1.nCol;
/*  669 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     int i;
/*  672 */     for (i = 0; i < Math.min(this.nRow, m1.nRow); i++) {
/*  673 */       for (int j = 0; j < Math.min(this.nCol, m1.nCol); j++) {
/*  674 */         this.values[i][j] = m1.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/*  678 */     for (i = m1.nRow; i < this.nRow; i++) {
/*  679 */       for (int j = m1.nCol; j < this.nCol; j++) {
/*  680 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumRow() {
/*  691 */     return this.nRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumCol() {
/*  700 */     return this.nCol;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getElement(int row, int column) {
/*  711 */     return this.values[row][column];
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
/*      */   public final void setElement(int row, int column, double value) {
/*  723 */     this.values[row][column] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, double[] array) {
/*  733 */     for (int i = 0; i < this.nCol; i++) {
/*  734 */       array[i] = this.values[row][i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, GVector vector) {
/*  745 */     if (vector.getSize() < this.nCol) {
/*  746 */       vector.setSize(this.nCol);
/*      */     }
/*  748 */     for (int i = 0; i < this.nCol; i++) {
/*  749 */       vector.values[i] = this.values[row][i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int col, double[] array) {
/*  760 */     for (int i = 0; i < this.nRow; i++) {
/*  761 */       array[i] = this.values[i][col];
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
/*      */   public final void getColumn(int col, GVector vector) {
/*  773 */     if (vector.getSize() < this.nRow) {
/*  774 */       vector.setSize(this.nRow);
/*      */     }
/*  776 */     for (int i = 0; i < this.nRow; i++) {
/*  777 */       vector.values[i] = this.values[i][col];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3d m1) {
/*  788 */     if (this.nRow < 3 || this.nCol < 3) {
/*  789 */       m1.setZero();
/*  790 */       if (this.nCol > 0) {
/*  791 */         if (this.nRow > 0) {
/*  792 */           m1.m00 = this.values[0][0];
/*  793 */           if (this.nRow > 1) {
/*  794 */             m1.m10 = this.values[1][0];
/*  795 */             if (this.nRow > 2) {
/*  796 */               m1.m20 = this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  800 */         if (this.nCol > 1) {
/*  801 */           if (this.nRow > 0) {
/*  802 */             m1.m01 = this.values[0][1];
/*  803 */             if (this.nRow > 1) {
/*  804 */               m1.m11 = this.values[1][1];
/*  805 */               if (this.nRow > 2) {
/*  806 */                 m1.m21 = this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  810 */           if (this.nCol > 2 && 
/*  811 */             this.nRow > 0) {
/*  812 */             m1.m02 = this.values[0][2];
/*  813 */             if (this.nRow > 1) {
/*  814 */               m1.m12 = this.values[1][2];
/*  815 */               if (this.nRow > 2) {
/*  816 */                 m1.m22 = this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  824 */       m1.m00 = this.values[0][0];
/*  825 */       m1.m01 = this.values[0][1];
/*  826 */       m1.m02 = this.values[0][2];
/*      */       
/*  828 */       m1.m10 = this.values[1][0];
/*  829 */       m1.m11 = this.values[1][1];
/*  830 */       m1.m12 = this.values[1][2];
/*      */       
/*  832 */       m1.m20 = this.values[2][0];
/*  833 */       m1.m21 = this.values[2][1];
/*  834 */       m1.m22 = this.values[2][2];
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
/*      */   public final void get(Matrix3f m1) {
/*  846 */     if (this.nRow < 3 || this.nCol < 3) {
/*  847 */       m1.setZero();
/*  848 */       if (this.nCol > 0) {
/*  849 */         if (this.nRow > 0) {
/*  850 */           m1.m00 = (float)this.values[0][0];
/*  851 */           if (this.nRow > 1) {
/*  852 */             m1.m10 = (float)this.values[1][0];
/*  853 */             if (this.nRow > 2) {
/*  854 */               m1.m20 = (float)this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  858 */         if (this.nCol > 1) {
/*  859 */           if (this.nRow > 0) {
/*  860 */             m1.m01 = (float)this.values[0][1];
/*  861 */             if (this.nRow > 1) {
/*  862 */               m1.m11 = (float)this.values[1][1];
/*  863 */               if (this.nRow > 2) {
/*  864 */                 m1.m21 = (float)this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  868 */           if (this.nCol > 2 && 
/*  869 */             this.nRow > 0) {
/*  870 */             m1.m02 = (float)this.values[0][2];
/*  871 */             if (this.nRow > 1) {
/*  872 */               m1.m12 = (float)this.values[1][2];
/*  873 */               if (this.nRow > 2) {
/*  874 */                 m1.m22 = (float)this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  882 */       m1.m00 = (float)this.values[0][0];
/*  883 */       m1.m01 = (float)this.values[0][1];
/*  884 */       m1.m02 = (float)this.values[0][2];
/*      */       
/*  886 */       m1.m10 = (float)this.values[1][0];
/*  887 */       m1.m11 = (float)this.values[1][1];
/*  888 */       m1.m12 = (float)this.values[1][2];
/*      */       
/*  890 */       m1.m20 = (float)this.values[2][0];
/*  891 */       m1.m21 = (float)this.values[2][1];
/*  892 */       m1.m22 = (float)this.values[2][2];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix4d m1) {
/*  903 */     if (this.nRow < 4 || this.nCol < 4) {
/*  904 */       m1.setZero();
/*  905 */       if (this.nCol > 0) {
/*  906 */         if (this.nRow > 0) {
/*  907 */           m1.m00 = this.values[0][0];
/*  908 */           if (this.nRow > 1) {
/*  909 */             m1.m10 = this.values[1][0];
/*  910 */             if (this.nRow > 2) {
/*  911 */               m1.m20 = this.values[2][0];
/*  912 */               if (this.nRow > 3) {
/*  913 */                 m1.m30 = this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  918 */         if (this.nCol > 1) {
/*  919 */           if (this.nRow > 0) {
/*  920 */             m1.m01 = this.values[0][1];
/*  921 */             if (this.nRow > 1) {
/*  922 */               m1.m11 = this.values[1][1];
/*  923 */               if (this.nRow > 2) {
/*  924 */                 m1.m21 = this.values[2][1];
/*  925 */                 if (this.nRow > 3) {
/*  926 */                   m1.m31 = this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*  931 */           if (this.nCol > 2) {
/*  932 */             if (this.nRow > 0) {
/*  933 */               m1.m02 = this.values[0][2];
/*  934 */               if (this.nRow > 1) {
/*  935 */                 m1.m12 = this.values[1][2];
/*  936 */                 if (this.nRow > 2) {
/*  937 */                   m1.m22 = this.values[2][2];
/*  938 */                   if (this.nRow > 3) {
/*  939 */                     m1.m32 = this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*  944 */             if (this.nCol > 3 && 
/*  945 */               this.nRow > 0) {
/*  946 */               m1.m03 = this.values[0][3];
/*  947 */               if (this.nRow > 1) {
/*  948 */                 m1.m13 = this.values[1][3];
/*  949 */                 if (this.nRow > 2) {
/*  950 */                   m1.m23 = this.values[2][3];
/*  951 */                   if (this.nRow > 3) {
/*  952 */                     m1.m33 = this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  962 */       m1.m00 = this.values[0][0];
/*  963 */       m1.m01 = this.values[0][1];
/*  964 */       m1.m02 = this.values[0][2];
/*  965 */       m1.m03 = this.values[0][3];
/*      */       
/*  967 */       m1.m10 = this.values[1][0];
/*  968 */       m1.m11 = this.values[1][1];
/*  969 */       m1.m12 = this.values[1][2];
/*  970 */       m1.m13 = this.values[1][3];
/*      */       
/*  972 */       m1.m20 = this.values[2][0];
/*  973 */       m1.m21 = this.values[2][1];
/*  974 */       m1.m22 = this.values[2][2];
/*  975 */       m1.m23 = this.values[2][3];
/*      */       
/*  977 */       m1.m30 = this.values[3][0];
/*  978 */       m1.m31 = this.values[3][1];
/*  979 */       m1.m32 = this.values[3][2];
/*  980 */       m1.m33 = this.values[3][3];
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
/*      */   public final void get(Matrix4f m1) {
/*  993 */     if (this.nRow < 4 || this.nCol < 4) {
/*  994 */       m1.setZero();
/*  995 */       if (this.nCol > 0) {
/*  996 */         if (this.nRow > 0) {
/*  997 */           m1.m00 = (float)this.values[0][0];
/*  998 */           if (this.nRow > 1) {
/*  999 */             m1.m10 = (float)this.values[1][0];
/* 1000 */             if (this.nRow > 2) {
/* 1001 */               m1.m20 = (float)this.values[2][0];
/* 1002 */               if (this.nRow > 3) {
/* 1003 */                 m1.m30 = (float)this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 1008 */         if (this.nCol > 1) {
/* 1009 */           if (this.nRow > 0) {
/* 1010 */             m1.m01 = (float)this.values[0][1];
/* 1011 */             if (this.nRow > 1) {
/* 1012 */               m1.m11 = (float)this.values[1][1];
/* 1013 */               if (this.nRow > 2) {
/* 1014 */                 m1.m21 = (float)this.values[2][1];
/* 1015 */                 if (this.nRow > 3) {
/* 1016 */                   m1.m31 = (float)this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/* 1021 */           if (this.nCol > 2) {
/* 1022 */             if (this.nRow > 0) {
/* 1023 */               m1.m02 = (float)this.values[0][2];
/* 1024 */               if (this.nRow > 1) {
/* 1025 */                 m1.m12 = (float)this.values[1][2];
/* 1026 */                 if (this.nRow > 2) {
/* 1027 */                   m1.m22 = (float)this.values[2][2];
/* 1028 */                   if (this.nRow > 3) {
/* 1029 */                     m1.m32 = (float)this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1034 */             if (this.nCol > 3 && 
/* 1035 */               this.nRow > 0) {
/* 1036 */               m1.m03 = (float)this.values[0][3];
/* 1037 */               if (this.nRow > 1) {
/* 1038 */                 m1.m13 = (float)this.values[1][3];
/* 1039 */                 if (this.nRow > 2) {
/* 1040 */                   m1.m23 = (float)this.values[2][3];
/* 1041 */                   if (this.nRow > 3) {
/* 1042 */                     m1.m33 = (float)this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1052 */       m1.m00 = (float)this.values[0][0];
/* 1053 */       m1.m01 = (float)this.values[0][1];
/* 1054 */       m1.m02 = (float)this.values[0][2];
/* 1055 */       m1.m03 = (float)this.values[0][3];
/*      */       
/* 1057 */       m1.m10 = (float)this.values[1][0];
/* 1058 */       m1.m11 = (float)this.values[1][1];
/* 1059 */       m1.m12 = (float)this.values[1][2];
/* 1060 */       m1.m13 = (float)this.values[1][3];
/*      */       
/* 1062 */       m1.m20 = (float)this.values[2][0];
/* 1063 */       m1.m21 = (float)this.values[2][1];
/* 1064 */       m1.m22 = (float)this.values[2][2];
/* 1065 */       m1.m23 = (float)this.values[2][3];
/*      */       
/* 1067 */       m1.m30 = (float)this.values[3][0];
/* 1068 */       m1.m31 = (float)this.values[3][1];
/* 1069 */       m1.m32 = (float)this.values[3][2];
/* 1070 */       m1.m33 = (float)this.values[3][3];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(GMatrix m1) {
/*      */     int nc;
/*      */     int nr;
/* 1083 */     if (this.nCol < m1.nCol) {
/* 1084 */       nc = this.nCol;
/*      */     } else {
/* 1086 */       nc = m1.nCol;
/*      */     } 
/* 1088 */     if (this.nRow < m1.nRow) {
/* 1089 */       nr = this.nRow;
/*      */     } else {
/* 1091 */       nr = m1.nRow;
/*      */     }  int i;
/* 1093 */     for (i = 0; i < nr; i++) {
/* 1094 */       for (int k = 0; k < nc; k++) {
/* 1095 */         m1.values[i][k] = this.values[i][k];
/*      */       }
/*      */     } 
/* 1098 */     for (i = nr; i < m1.nRow; i++) {
/* 1099 */       for (int k = 0; k < m1.nCol; k++) {
/* 1100 */         m1.values[i][k] = 0.0D;
/*      */       }
/*      */     } 
/* 1103 */     for (int j = nc; j < m1.nCol; j++) {
/* 1104 */       for (i = 0; i < nr; i++) {
/* 1105 */         m1.values[i][j] = 0.0D;
/*      */       }
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
/*      */   public final void setRow(int row, double[] array) {
/* 1119 */     for (int i = 0; i < this.nCol; i++) {
/* 1120 */       this.values[row][i] = array[i];
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
/*      */   public final void setRow(int row, GVector vector) {
/* 1133 */     for (int i = 0; i < this.nCol; i++) {
/* 1134 */       this.values[row][i] = vector.values[i];
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
/*      */   public final void setColumn(int col, double[] array) {
/* 1147 */     for (int i = 0; i < this.nRow; i++) {
/* 1148 */       this.values[i][col] = array[i];
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
/*      */   public final void setColumn(int col, GVector vector) {
/* 1161 */     for (int i = 0; i < this.nRow; i++) {
/* 1162 */       this.values[i][col] = vector.values[i];
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
/*      */   public final void mulTransposeBoth(GMatrix m1, GMatrix m2) {
/* 1177 */     if (m1.nRow != m2.nCol || this.nRow != m1.nCol || this.nCol != m2.nRow) {
/* 1178 */       throw new MismatchedSizeException(
/* 1179 */           VecMathI18N.getString("GMatrix14"));
/*      */     }
/* 1181 */     if (m1 == this || m2 == this) {
/* 1182 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1183 */       for (int i = 0; i < this.nRow; i++) {
/* 1184 */         for (int j = 0; j < this.nCol; j++) {
/* 1185 */           tmp[i][j] = 0.0D;
/* 1186 */           for (int k = 0; k < m1.nRow; k++) {
/* 1187 */             tmp[i][j] = tmp[i][j] + m1.values[k][i] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
/* 1191 */       this.values = tmp;
/*      */     } else {
/* 1193 */       for (int i = 0; i < this.nRow; i++) {
/* 1194 */         for (int j = 0; j < this.nCol; j++) {
/* 1195 */           this.values[i][j] = 0.0D;
/* 1196 */           for (int k = 0; k < m1.nRow; k++) {
/* 1197 */             this.values[i][j] = this.values[i][j] + m1.values[k][i] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void mulTransposeRight(GMatrix m1, GMatrix m2) {
/* 1214 */     if (m1.nCol != m2.nCol || this.nCol != m2.nRow || this.nRow != m1.nRow) {
/* 1215 */       throw new MismatchedSizeException(
/* 1216 */           VecMathI18N.getString("GMatrix15"));
/*      */     }
/* 1218 */     if (m1 == this || m2 == this) {
/* 1219 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1220 */       for (int i = 0; i < this.nRow; i++) {
/* 1221 */         for (int j = 0; j < this.nCol; j++) {
/* 1222 */           tmp[i][j] = 0.0D;
/* 1223 */           for (int k = 0; k < m1.nCol; k++) {
/* 1224 */             tmp[i][j] = tmp[i][j] + m1.values[i][k] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
/* 1228 */       this.values = tmp;
/*      */     } else {
/* 1230 */       for (int i = 0; i < this.nRow; i++) {
/* 1231 */         for (int j = 0; j < this.nCol; j++) {
/* 1232 */           this.values[i][j] = 0.0D;
/* 1233 */           for (int k = 0; k < m1.nCol; k++) {
/* 1234 */             this.values[i][j] = this.values[i][j] + m1.values[i][k] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void mulTransposeLeft(GMatrix m1, GMatrix m2) {
/* 1253 */     if (m1.nRow != m2.nRow || this.nCol != m2.nCol || this.nRow != m1.nCol) {
/* 1254 */       throw new MismatchedSizeException(
/* 1255 */           VecMathI18N.getString("GMatrix16"));
/*      */     }
/* 1257 */     if (m1 == this || m2 == this) {
/* 1258 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1259 */       for (int i = 0; i < this.nRow; i++) {
/* 1260 */         for (int j = 0; j < this.nCol; j++) {
/* 1261 */           tmp[i][j] = 0.0D;
/* 1262 */           for (int k = 0; k < m1.nRow; k++) {
/* 1263 */             tmp[i][j] = tmp[i][j] + m1.values[k][i] * m2.values[k][j];
/*      */           }
/*      */         } 
/*      */       } 
/* 1267 */       this.values = tmp;
/*      */     } else {
/* 1269 */       for (int i = 0; i < this.nRow; i++) {
/* 1270 */         for (int j = 0; j < this.nCol; j++) {
/* 1271 */           this.values[i][j] = 0.0D;
/* 1272 */           for (int k = 0; k < m1.nRow; k++) {
/* 1273 */             this.values[i][j] = this.values[i][j] + m1.values[k][i] * m2.values[k][j];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1287 */     if (this.nRow != this.nCol) {
/*      */       
/* 1289 */       int i = this.nRow;
/* 1290 */       this.nRow = this.nCol;
/* 1291 */       this.nCol = i;
/* 1292 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1293 */       for (i = 0; i < this.nRow; i++) {
/* 1294 */         for (int j = 0; j < this.nCol; j++) {
/* 1295 */           tmp[i][j] = this.values[j][i];
/*      */         }
/*      */       } 
/* 1298 */       this.values = tmp;
/*      */     } else {
/*      */       
/* 1301 */       for (int i = 0; i < this.nRow; i++) {
/* 1302 */         for (int j = 0; j < i; j++) {
/* 1303 */           double swap = this.values[i][j];
/* 1304 */           this.values[i][j] = this.values[j][i];
/* 1305 */           this.values[j][i] = swap;
/*      */         } 
/*      */       } 
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
/*      */   public final void transpose(GMatrix m1) {
/* 1319 */     if (this.nRow != m1.nCol || this.nCol != m1.nRow) {
/* 1320 */       throw new MismatchedSizeException(
/* 1321 */           VecMathI18N.getString("GMatrix17"));
/*      */     }
/* 1323 */     if (m1 != this) {
/* 1324 */       for (int i = 0; i < this.nRow; i++) {
/* 1325 */         for (int j = 0; j < this.nCol; j++) {
/* 1326 */           this.values[i][j] = m1.values[j][i];
/*      */         }
/*      */       } 
/*      */     } else {
/* 1330 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1341 */     StringBuffer buffer = new StringBuffer(this.nRow * this.nCol * 8);
/*      */ 
/*      */ 
/*      */     
/* 1345 */     for (int i = 0; i < this.nRow; i++) {
/* 1346 */       for (int j = 0; j < this.nCol; j++) {
/* 1347 */         buffer.append(this.values[i][j]).append(" ");
/*      */       }
/* 1349 */       buffer.append("\n");
/*      */     } 
/*      */     
/* 1352 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkMatrix(GMatrix m) {
/* 1358 */     for (int i = 0; i < m.nRow; i++) {
/* 1359 */       for (int j = 0; j < m.nCol; j++) {
/* 1360 */         if (Math.abs(m.values[i][j]) < 1.0E-10D) {
/* 1361 */           System.out.print(" 0.0     ");
/*      */         } else {
/* 1363 */           System.out.print(" " + m.values[i][j]);
/*      */         } 
/*      */       } 
/* 1366 */       System.out.print("\n");
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
/*      */   public int hashCode() {
/* 1383 */     long bits = 1L;
/*      */     
/* 1385 */     bits = VecMathUtil.hashLongBits(bits, this.nRow);
/* 1386 */     bits = VecMathUtil.hashLongBits(bits, this.nCol);
/*      */     
/* 1388 */     for (int i = 0; i < this.nRow; i++) {
/* 1389 */       for (int j = 0; j < this.nCol; j++) {
/* 1390 */         bits = VecMathUtil.hashDoubleBits(bits, this.values[i][j]);
/*      */       }
/*      */     } 
/*      */     
/* 1394 */     return VecMathUtil.hashFinish(bits);
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
/*      */   public boolean equals(GMatrix m1) {
/*      */     try {
/* 1409 */       if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/* 1410 */         return false;
/*      */       }
/* 1412 */       for (int i = 0; i < this.nRow; i++) {
/* 1413 */         for (int j = 0; j < this.nCol; j++) {
/* 1414 */           if (this.values[i][j] != m1.values[i][j])
/* 1415 */             return false; 
/*      */         } 
/*      */       } 
/* 1418 */       return true;
/* 1419 */     } catch (NullPointerException e2) {
/* 1420 */       return false;
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
/*      */   public boolean equals(Object o1) {
/*      */     try {
/* 1435 */       GMatrix m2 = (GMatrix)o1;
/*      */       
/* 1437 */       if (this.nRow != m2.nRow || this.nCol != m2.nCol) {
/* 1438 */         return false;
/*      */       }
/* 1440 */       for (int i = 0; i < this.nRow; i++) {
/* 1441 */         for (int j = 0; j < this.nCol; j++) {
/* 1442 */           if (this.values[i][j] != m2.values[i][j])
/* 1443 */             return false; 
/*      */         } 
/*      */       } 
/* 1446 */       return true;
/* 1447 */     } catch (ClassCastException e1) {
/* 1448 */       return false;
/* 1449 */     } catch (NullPointerException e2) {
/* 1450 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(GMatrix m1, float epsilon) {
/* 1458 */     return epsilonEquals(m1, epsilon);
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
/*      */   public boolean epsilonEquals(GMatrix m1, double epsilon) {
/* 1474 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/* 1475 */       return false;
/*      */     }
/* 1477 */     for (int i = 0; i < this.nRow; i++) {
/* 1478 */       for (int j = 0; j < this.nCol; j++) {
/* 1479 */         double diff = this.values[i][j] - m1.values[i][j];
/* 1480 */         if (((diff < 0.0D) ? -diff : diff) > epsilon)
/* 1481 */           return false; 
/*      */       } 
/*      */     } 
/* 1484 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double trace() {
/*      */     int l;
/* 1496 */     if (this.nRow < this.nCol) {
/* 1497 */       l = this.nRow;
/*      */     } else {
/* 1499 */       l = this.nCol;
/*      */     } 
/* 1501 */     double t = 0.0D;
/* 1502 */     for (int i = 0; i < l; i++) {
/* 1503 */       t += this.values[i][i];
/*      */     }
/* 1505 */     return t;
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
/*      */   public final int SVD(GMatrix U, GMatrix W, GMatrix V) {
/* 1527 */     if (this.nCol != V.nCol || this.nCol != V.nRow) {
/* 1528 */       throw new MismatchedSizeException(
/* 1529 */           VecMathI18N.getString("GMatrix18"));
/*      */     }
/*      */     
/* 1532 */     if (this.nRow != U.nRow || this.nRow != U.nCol) {
/* 1533 */       throw new MismatchedSizeException(
/* 1534 */           VecMathI18N.getString("GMatrix25"));
/*      */     }
/*      */     
/* 1537 */     if (this.nRow != W.nRow || this.nCol != W.nCol) {
/* 1538 */       throw new MismatchedSizeException(
/* 1539 */           VecMathI18N.getString("GMatrix26"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1552 */     if (this.nRow == 2 && this.nCol == 2 && 
/* 1553 */       this.values[1][0] == 0.0D) {
/* 1554 */       U.setIdentity();
/* 1555 */       V.setIdentity();
/*      */       
/* 1557 */       if (this.values[0][1] == 0.0D) {
/* 1558 */         return 2;
/*      */       }
/*      */       
/* 1561 */       double[] sinl = new double[1];
/* 1562 */       double[] sinr = new double[1];
/* 1563 */       double[] cosl = new double[1];
/* 1564 */       double[] cosr = new double[1];
/* 1565 */       double[] single_values = new double[2];
/*      */       
/* 1567 */       single_values[0] = this.values[0][0];
/* 1568 */       single_values[1] = this.values[1][1];
/*      */       
/* 1570 */       compute_2X2(this.values[0][0], this.values[0][1], this.values[1][1], 
/* 1571 */           single_values, sinl, cosl, sinr, cosr, 0);
/*      */       
/* 1573 */       update_u(0, U, cosl, sinl);
/* 1574 */       update_v(0, V, cosr, sinr);
/*      */       
/* 1576 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1581 */     return computeSVD(this, U, W, V);
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
/*      */   public final int LUD(GMatrix LU, GVector permutation) {
/* 1605 */     int size = LU.nRow * LU.nCol;
/* 1606 */     double[] temp = new double[size];
/* 1607 */     int[] even_row_exchange = new int[1];
/* 1608 */     int[] row_perm = new int[LU.nRow];
/*      */ 
/*      */     
/* 1611 */     if (this.nRow != this.nCol) {
/* 1612 */       throw new MismatchedSizeException(
/* 1613 */           VecMathI18N.getString("GMatrix19"));
/*      */     }
/*      */     
/* 1616 */     if (this.nRow != LU.nRow) {
/* 1617 */       throw new MismatchedSizeException(
/* 1618 */           VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */     
/* 1621 */     if (this.nCol != LU.nCol) {
/* 1622 */       throw new MismatchedSizeException(
/* 1623 */           VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */     
/* 1626 */     if (LU.nRow != permutation.getSize()) {
/* 1627 */       throw new MismatchedSizeException(
/* 1628 */           VecMathI18N.getString("GMatrix20"));
/*      */     }
/*      */     int i;
/* 1631 */     for (i = 0; i < this.nRow; i++) {
/* 1632 */       for (int j = 0; j < this.nCol; j++) {
/* 1633 */         temp[i * this.nCol + j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1638 */     if (!luDecomposition(LU.nRow, temp, row_perm, even_row_exchange))
/*      */     {
/* 1640 */       throw new SingularMatrixException(
/* 1641 */           VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */     
/* 1644 */     for (i = 0; i < this.nRow; i++) {
/* 1645 */       for (int j = 0; j < this.nCol; j++) {
/* 1646 */         LU.values[i][j] = temp[i * this.nCol + j];
/*      */       }
/*      */     } 
/*      */     
/* 1650 */     for (i = 0; i < LU.nRow; i++) {
/* 1651 */       permutation.values[i] = row_perm[i];
/*      */     }
/*      */     
/* 1654 */     return even_row_exchange[0];
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
/*      */     int l;
/* 1666 */     if (this.nRow < this.nCol) {
/* 1667 */       l = this.nRow;
/*      */     } else {
/* 1669 */       l = this.nCol;
/*      */     }  int i;
/* 1671 */     for (i = 0; i < this.nRow; i++) {
/* 1672 */       for (int j = 0; j < this.nCol; j++) {
/* 1673 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */     
/* 1677 */     for (i = 0; i < l; i++) {
/* 1678 */       this.values[i][i] = scale;
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
/*      */   final void invertGeneral(GMatrix m1) {
/* 1691 */     int size = m1.nRow * m1.nCol;
/* 1692 */     double[] temp = new double[size];
/* 1693 */     double[] result = new double[size];
/* 1694 */     int[] row_perm = new int[m1.nRow];
/* 1695 */     int[] even_row_exchange = new int[1];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1700 */     if (m1.nRow != m1.nCol)
/*      */     {
/* 1702 */       throw new MismatchedSizeException(
/* 1703 */           VecMathI18N.getString("GMatrix22"));
/*      */     }
/*      */     
/*      */     int i;
/* 1707 */     for (i = 0; i < this.nRow; i++) {
/* 1708 */       for (int j = 0; j < this.nCol; j++) {
/* 1709 */         temp[i * this.nCol + j] = m1.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1714 */     if (!luDecomposition(m1.nRow, temp, row_perm, even_row_exchange))
/*      */     {
/* 1716 */       throw new SingularMatrixException(
/* 1717 */           VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */ 
/*      */     
/* 1721 */     for (i = 0; i < size; i++) {
/* 1722 */       result[i] = 0.0D;
/*      */     }
/* 1724 */     for (i = 0; i < this.nCol; i++) {
/* 1725 */       result[i + i * this.nCol] = 1.0D;
/*      */     }
/* 1727 */     luBacksubstitution(m1.nRow, temp, row_perm, result);
/*      */     
/* 1729 */     for (i = 0; i < this.nRow; i++) {
/* 1730 */       for (int j = 0; j < this.nCol; j++) {
/* 1731 */         this.values[i][j] = result[i * this.nCol + j];
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(int dim, double[] matrix0, int[] row_perm, int[] even_row_xchg) {
/* 1756 */     double[] row_scale = new double[dim];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1763 */     int ptr = 0;
/* 1764 */     int rs = 0;
/* 1765 */     even_row_xchg[0] = 1;
/*      */ 
/*      */     
/* 1768 */     int i = dim;
/* 1769 */     while (i-- != 0) {
/* 1770 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1773 */       int k = dim;
/* 1774 */       while (k-- != 0) {
/* 1775 */         double temp = matrix0[ptr++];
/* 1776 */         temp = Math.abs(temp);
/* 1777 */         if (temp > big) {
/* 1778 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1783 */       if (big == 0.0D) {
/* 1784 */         return false;
/*      */       }
/* 1786 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */     
/* 1790 */     int mtx = 0;
/* 1791 */     for (int j = 0; j < dim; j++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1797 */       for (i = 0; i < j; i++) {
/* 1798 */         int target = mtx + dim * i + j;
/* 1799 */         double sum = matrix0[target];
/* 1800 */         int k = i;
/* 1801 */         int p1 = mtx + dim * i;
/* 1802 */         int p2 = mtx + j;
/* 1803 */         while (k-- != 0) {
/* 1804 */           sum -= matrix0[p1] * matrix0[p2];
/* 1805 */           p1++;
/* 1806 */           p2 += dim;
/*      */         } 
/* 1808 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1813 */       double big = 0.0D;
/* 1814 */       int imax = -1;
/* 1815 */       for (i = j; i < dim; i++) {
/* 1816 */         int target = mtx + dim * i + j;
/* 1817 */         double sum = matrix0[target];
/* 1818 */         int k = j;
/* 1819 */         int p1 = mtx + dim * i;
/* 1820 */         int p2 = mtx + j;
/* 1821 */         while (k-- != 0) {
/* 1822 */           sum -= matrix0[p1] * matrix0[p2];
/* 1823 */           p1++;
/* 1824 */           p2 += dim;
/*      */         } 
/* 1826 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1829 */         if ((temp = row_scale[i] * Math.abs(sum)) >= big) {
/* 1830 */           big = temp;
/* 1831 */           imax = i;
/*      */         } 
/*      */       } 
/*      */       
/* 1835 */       if (imax < 0) {
/* 1836 */         throw new RuntimeException(VecMathI18N.getString("GMatrix24"));
/*      */       }
/*      */ 
/*      */       
/* 1840 */       if (j != imax) {
/*      */         
/* 1842 */         int k = dim;
/* 1843 */         int p1 = mtx + dim * imax;
/* 1844 */         int p2 = mtx + dim * j;
/* 1845 */         while (k-- != 0) {
/* 1846 */           double temp = matrix0[p1];
/* 1847 */           matrix0[p1++] = matrix0[p2];
/* 1848 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1852 */         row_scale[imax] = row_scale[j];
/* 1853 */         even_row_xchg[0] = -even_row_xchg[0];
/*      */       } 
/*      */ 
/*      */       
/* 1857 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1860 */       if (matrix0[mtx + dim * j + j] == 0.0D) {
/* 1861 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1865 */       if (j != dim - 1) {
/* 1866 */         double temp = 1.0D / matrix0[mtx + dim * j + j];
/* 1867 */         int target = mtx + dim * (j + 1) + j;
/* 1868 */         i = dim - 1 - j;
/* 1869 */         while (i-- != 0) {
/* 1870 */           matrix0[target] = matrix0[target] * temp;
/* 1871 */           target += dim;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1877 */     return true;
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
/*      */   
/*      */   static void luBacksubstitution(int dim, double[] matrix1, int[] row_perm, double[] matrix2) {
/* 1908 */     int rp = 0;
/*      */ 
/*      */     
/* 1911 */     for (int k = 0; k < dim; k++) {
/*      */       
/* 1913 */       int cv = k;
/* 1914 */       int ii = -1;
/*      */       
/*      */       int i;
/* 1917 */       for (i = 0; i < dim; i++) {
/*      */ 
/*      */         
/* 1920 */         int ip = row_perm[rp + i];
/* 1921 */         double sum = matrix2[cv + dim * ip];
/* 1922 */         matrix2[cv + dim * ip] = matrix2[cv + dim * i];
/* 1923 */         if (ii >= 0) {
/*      */           
/* 1925 */           int rv = i * dim;
/* 1926 */           for (int j = ii; j <= i - 1; j++) {
/* 1927 */             sum -= matrix1[rv + j] * matrix2[cv + dim * j];
/*      */           }
/* 1929 */         } else if (sum != 0.0D) {
/* 1930 */           ii = i;
/*      */         } 
/* 1932 */         matrix2[cv + dim * i] = sum;
/*      */       } 
/*      */ 
/*      */       
/* 1936 */       for (i = 0; i < dim; i++) {
/* 1937 */         int ri = dim - 1 - i;
/* 1938 */         int rv = dim * ri;
/* 1939 */         double tt = 0.0D;
/* 1940 */         for (int j = 1; j <= i; j++) {
/* 1941 */           tt += matrix1[rv + dim - j] * matrix2[cv + dim * (dim - j)];
/*      */         }
/* 1943 */         matrix2[cv + dim * ri] = (matrix2[cv + dim * ri] - tt) / matrix1[rv + ri];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int computeSVD(GMatrix mat, GMatrix U, GMatrix W, GMatrix V) {
/*      */     int eLength, sLength, vecLength;
/* 1956 */     GMatrix tmp = new GMatrix(mat.nRow, mat.nCol);
/* 1957 */     GMatrix u = new GMatrix(mat.nRow, mat.nCol);
/* 1958 */     GMatrix v = new GMatrix(mat.nRow, mat.nCol);
/* 1959 */     GMatrix m = new GMatrix(mat);
/*      */ 
/*      */     
/* 1962 */     if (m.nRow >= m.nCol) {
/* 1963 */       sLength = m.nCol;
/* 1964 */       eLength = m.nCol - 1;
/*      */     } else {
/* 1966 */       sLength = m.nRow;
/* 1967 */       eLength = m.nRow;
/*      */     } 
/*      */     
/* 1970 */     if (m.nRow > m.nCol) {
/* 1971 */       vecLength = m.nRow;
/*      */     } else {
/* 1973 */       vecLength = m.nCol;
/*      */     } 
/* 1975 */     double[] vec = new double[vecLength];
/* 1976 */     double[] single_values = new double[sLength];
/* 1977 */     double[] e = new double[eLength];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1983 */     int rank = 0;
/*      */     
/* 1985 */     U.setIdentity();
/* 1986 */     V.setIdentity();
/*      */     
/* 1988 */     int nr = m.nRow;
/* 1989 */     int nc = m.nCol;
/*      */ 
/*      */     
/* 1992 */     for (int si = 0; si < sLength; si++) {
/*      */ 
/*      */       
/* 1995 */       if (nr > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2002 */         double mag = 0.0D; int k;
/* 2003 */         for (k = 0; k < nr; k++) {
/* 2004 */           mag += m.values[k + si][si] * m.values[k + si][si];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2011 */         mag = Math.sqrt(mag);
/* 2012 */         if (m.values[si][si] == 0.0D) {
/* 2013 */           vec[0] = mag;
/*      */         } else {
/* 2015 */           vec[0] = m.values[si][si] + d_sign(mag, m.values[si][si]);
/*      */         } 
/*      */         
/* 2018 */         for (k = 1; k < nr; k++) {
/* 2019 */           vec[k] = m.values[si + k][si];
/*      */         }
/*      */         
/* 2022 */         double scale = 0.0D;
/* 2023 */         for (k = 0; k < nr; k++)
/*      */         {
/*      */ 
/*      */           
/* 2027 */           scale += vec[k] * vec[k];
/*      */         }
/*      */         
/* 2030 */         scale = 2.0D / scale;
/*      */         
/*      */         int j;
/*      */         
/* 2034 */         for (j = si; j < m.nRow; j++) {
/* 2035 */           for (int n = si; n < m.nRow; n++) {
/* 2036 */             u.values[j][n] = -scale * vec[j - si] * vec[n - si];
/*      */           }
/*      */         } 
/*      */         
/* 2040 */         for (k = si; k < m.nRow; k++) {
/* 2041 */           u.values[k][k] = u.values[k][k] + 1.0D;
/*      */         }
/*      */ 
/*      */         
/* 2045 */         double t = 0.0D;
/* 2046 */         for (k = si; k < m.nRow; k++) {
/* 2047 */           t += u.values[si][k] * m.values[k][si];
/*      */         }
/* 2049 */         m.values[si][si] = t;
/*      */ 
/*      */         
/* 2052 */         for (j = si; j < m.nRow; j++) {
/* 2053 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2054 */             tmp.values[j][n] = 0.0D;
/* 2055 */             for (k = si; k < m.nCol; k++) {
/* 2056 */               tmp.values[j][n] = tmp.values[j][n] + u.values[j][k] * m.values[k][n];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2061 */         for (j = si; j < m.nRow; j++) {
/* 2062 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2063 */             m.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2073 */         for (j = si; j < m.nRow; j++) {
/* 2074 */           for (int n = 0; n < m.nCol; n++) {
/* 2075 */             tmp.values[j][n] = 0.0D;
/* 2076 */             for (k = si; k < m.nCol; k++) {
/* 2077 */               tmp.values[j][n] = tmp.values[j][n] + u.values[j][k] * U.values[k][n];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2082 */         for (j = si; j < m.nRow; j++) {
/* 2083 */           for (int n = 0; n < m.nCol; n++) {
/* 2084 */             U.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2095 */         nr--;
/*      */       } 
/*      */       
/* 2098 */       if (nc > 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2104 */         double mag = 0.0D; int k;
/* 2105 */         for (k = 1; k < nc; k++) {
/* 2106 */           mag += m.values[si][si + k] * m.values[si][si + k];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2114 */         mag = Math.sqrt(mag);
/* 2115 */         if (m.values[si][si + 1] == 0.0D) {
/* 2116 */           vec[0] = mag;
/*      */         } else {
/* 2118 */           vec[0] = m.values[si][si + 1] + 
/* 2119 */             d_sign(mag, m.values[si][si + 1]);
/*      */         } 
/*      */         
/* 2122 */         for (k = 1; k < nc - 1; k++) {
/* 2123 */           vec[k] = m.values[si][si + k + 1];
/*      */         }
/*      */ 
/*      */         
/* 2127 */         double scale = 0.0D;
/* 2128 */         for (k = 0; k < nc - 1; k++)
/*      */         {
/* 2130 */           scale += vec[k] * vec[k];
/*      */         }
/*      */         
/* 2133 */         scale = 2.0D / scale;
/*      */         
/*      */         int j;
/*      */         
/* 2137 */         for (j = si + 1; j < nc; j++) {
/* 2138 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2139 */             v.values[j][n] = -scale * vec[j - si - 1] * vec[n - si - 1];
/*      */           }
/*      */         } 
/*      */         
/* 2143 */         for (k = si + 1; k < m.nCol; k++) {
/* 2144 */           v.values[k][k] = v.values[k][k] + 1.0D;
/*      */         }
/*      */         
/* 2147 */         double t = 0.0D;
/* 2148 */         for (k = si; k < m.nCol; k++) {
/* 2149 */           t += v.values[k][si + 1] * m.values[si][k];
/*      */         }
/* 2151 */         m.values[si][si + 1] = t;
/*      */ 
/*      */         
/* 2154 */         for (j = si + 1; j < m.nRow; j++) {
/* 2155 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2156 */             tmp.values[j][n] = 0.0D;
/* 2157 */             for (k = si + 1; k < m.nCol; k++) {
/* 2158 */               tmp.values[j][n] = tmp.values[j][n] + v.values[k][n] * m.values[j][k];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2163 */         for (j = si + 1; j < m.nRow; j++) {
/* 2164 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2165 */             m.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2176 */         for (j = 0; j < m.nRow; j++) {
/* 2177 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2178 */             tmp.values[j][n] = 0.0D;
/* 2179 */             for (k = si + 1; k < m.nCol; k++) {
/* 2180 */               tmp.values[j][n] = tmp.values[j][n] + v.values[k][n] * V.values[j][k];
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2188 */         for (j = 0; j < m.nRow; j++) {
/* 2189 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2190 */             V.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2199 */         nc--;
/*      */       } 
/*      */     } 
/*      */     int i;
/* 2203 */     for (i = 0; i < sLength; i++) {
/* 2204 */       single_values[i] = m.values[i][i];
/*      */     }
/*      */     
/* 2207 */     for (i = 0; i < eLength; i++) {
/* 2208 */       e[i] = m.values[i][i + 1];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2221 */     if (m.nRow == 2 && m.nCol == 2) {
/* 2222 */       double[] cosl = new double[1];
/* 2223 */       double[] cosr = new double[1];
/* 2224 */       double[] sinl = new double[1];
/* 2225 */       double[] sinr = new double[1];
/*      */       
/* 2227 */       compute_2X2(single_values[0], e[0], single_values[1], 
/* 2228 */           single_values, sinl, cosl, sinr, cosr, 0);
/*      */       
/* 2230 */       update_u(0, U, cosl, sinl);
/* 2231 */       update_v(0, V, cosr, sinr);
/*      */       
/* 2233 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/* 2237 */     compute_qr(0, e.length - 1, single_values, e, U, V);
/*      */ 
/*      */     
/* 2240 */     rank = single_values.length;
/*      */ 
/*      */ 
/*      */     
/* 2244 */     return rank;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void compute_qr(int start, int end, double[] s, double[] e, GMatrix u, GMatrix v) {
/* 2253 */     double[] cosl = new double[1];
/* 2254 */     double[] cosr = new double[1];
/* 2255 */     double[] sinl = new double[1];
/* 2256 */     double[] sinr = new double[1];
/* 2257 */     GMatrix m = new GMatrix(u.nCol, v.nRow);
/*      */     
/* 2259 */     int MAX_INTERATIONS = 2;
/* 2260 */     double CONVERGE_TOL = 4.89E-15D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2284 */     double c_b48 = 1.0D;
/* 2285 */     double c_b71 = -1.0D;
/* 2286 */     boolean converged = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2291 */     double f = 0.0D;
/* 2292 */     double g = 0.0D;
/*      */     
/* 2294 */     for (int k = 0; k < 2 && !converged; k++) {
/* 2295 */       int j; for (j = start; j <= end; j++) {
/*      */ 
/*      */         
/* 2298 */         if (j == start) {
/* 2299 */           int sl; if (e.length == s.length) {
/* 2300 */             sl = end;
/*      */           } else {
/* 2302 */             sl = end + 1;
/*      */           } 
/* 2304 */           double shift = compute_shift(s[sl - 1], e[end], s[sl]);
/*      */           
/* 2306 */           f = (Math.abs(s[j]) - shift) * (
/* 2307 */             d_sign(c_b48, s[j]) + shift / s[j]);
/* 2308 */           g = e[j];
/*      */         } 
/*      */         
/* 2311 */         double r = compute_rot(f, g, sinr, cosr);
/* 2312 */         if (j != start) {
/* 2313 */           e[j - 1] = r;
/*      */         }
/* 2315 */         f = cosr[0] * s[j] + sinr[0] * e[j];
/* 2316 */         e[j] = cosr[0] * e[j] - sinr[0] * s[j];
/* 2317 */         g = sinr[0] * s[j + 1];
/* 2318 */         s[j + 1] = cosr[0] * s[j + 1];
/*      */ 
/*      */         
/* 2321 */         update_v(j, v, cosr, sinr);
/*      */ 
/*      */ 
/*      */         
/* 2325 */         r = compute_rot(f, g, sinl, cosl);
/* 2326 */         s[j] = r;
/* 2327 */         f = cosl[0] * e[j] + sinl[0] * s[j + 1];
/* 2328 */         s[j + 1] = cosl[0] * s[j + 1] - sinl[0] * e[j];
/*      */         
/* 2330 */         if (j < end) {
/*      */           
/* 2332 */           g = sinl[0] * e[j + 1];
/* 2333 */           e[j + 1] = cosl[0] * e[j + 1];
/*      */         } 
/*      */ 
/*      */         
/* 2337 */         update_u(j, u, cosl, sinl);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2343 */       if (s.length == e.length) {
/* 2344 */         double r = compute_rot(f, g, sinr, cosr);
/* 2345 */         f = cosr[0] * s[j] + sinr[0] * e[j];
/* 2346 */         e[j] = cosr[0] * e[j] - sinr[0] * s[j];
/* 2347 */         s[j + 1] = cosr[0] * s[j + 1];
/*      */         
/* 2349 */         update_v(j, v, cosr, sinr);
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
/*      */       
/* 2362 */       while (end - start > 1 && Math.abs(e[end]) < 4.89E-15D) {
/* 2363 */         end--;
/*      */       }
/*      */ 
/*      */       
/* 2367 */       for (int n = end - 2; n > start; n--) {
/* 2368 */         if (Math.abs(e[n]) < 4.89E-15D) {
/* 2369 */           compute_qr(n + 1, end, s, e, u, v);
/* 2370 */           end = n - 1;
/*      */ 
/*      */           
/* 2373 */           while (end - start > 1 && 
/* 2374 */             Math.abs(e[end]) < 4.89E-15D) {
/* 2375 */             end--;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2383 */       if (end - start <= 1 && Math.abs(e[start + 1]) < 4.89E-15D) {
/* 2384 */         converged = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2394 */     if (Math.abs(e[1]) < 4.89E-15D) {
/* 2395 */       compute_2X2(s[start], e[start], s[start + 1], s, 
/* 2396 */           sinl, cosl, sinr, cosr, 0);
/* 2397 */       e[start] = 0.0D;
/* 2398 */       e[start + 1] = 0.0D;
/*      */     } 
/*      */ 
/*      */     
/* 2402 */     int i = start;
/* 2403 */     update_u(i, u, cosl, sinl);
/* 2404 */     update_v(i, v, cosr, sinr);
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
/*      */   private static void print_se(double[] s, double[] e) {
/* 2416 */     System.out.println("\ns =" + s[0] + " " + s[1] + " " + s[2]);
/* 2417 */     System.out.println("e =" + e[0] + " " + e[1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v(int index, GMatrix v, double[] cosr, double[] sinr) {
/* 2425 */     for (int j = 0; j < v.nRow; j++) {
/* 2426 */       double vtemp = v.values[j][index];
/* 2427 */       v.values[j][index] = 
/* 2428 */         cosr[0] * vtemp + sinr[0] * v.values[j][index + 1];
/* 2429 */       v.values[j][index + 1] = 
/* 2430 */         -sinr[0] * vtemp + cosr[0] * v.values[j][index + 1];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void chase_up(double[] s, double[] e, int k, GMatrix v) {
/* 2436 */     double[] cosr = new double[1];
/* 2437 */     double[] sinr = new double[1];
/*      */     
/* 2439 */     GMatrix t = new GMatrix(v.nRow, v.nCol);
/* 2440 */     GMatrix m = new GMatrix(v.nRow, v.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2452 */     double f = e[k];
/* 2453 */     double g = s[k];
/*      */     int i;
/* 2455 */     for (i = k; i > 0; i--) {
/* 2456 */       double r = compute_rot(f, g, sinr, cosr);
/* 2457 */       f = -e[i - 1] * sinr[0];
/* 2458 */       g = s[i - 1];
/* 2459 */       s[i] = r;
/* 2460 */       e[i - 1] = e[i - 1] * cosr[0];
/* 2461 */       update_v_split(i, k + 1, v, cosr, sinr, t, m);
/*      */     } 
/*      */     
/* 2464 */     s[i + 1] = compute_rot(f, g, sinr, cosr);
/* 2465 */     update_v_split(i, k + 1, v, cosr, sinr, t, m);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void chase_across(double[] s, double[] e, int k, GMatrix u) {
/* 2470 */     double[] cosl = new double[1];
/* 2471 */     double[] sinl = new double[1];
/*      */     
/* 2473 */     GMatrix t = new GMatrix(u.nRow, u.nCol);
/* 2474 */     GMatrix m = new GMatrix(u.nRow, u.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2486 */     double g = e[k];
/* 2487 */     double f = s[k + 1];
/*      */     int i;
/* 2489 */     for (i = k; i < u.nCol - 2; i++) {
/* 2490 */       double r = compute_rot(f, g, sinl, cosl);
/* 2491 */       g = -e[i + 1] * sinl[0];
/* 2492 */       f = s[i + 2];
/* 2493 */       s[i + 1] = r;
/* 2494 */       e[i + 1] = e[i + 1] * cosl[0];
/* 2495 */       update_u_split(k, i + 1, u, cosl, sinl, t, m);
/*      */     } 
/*      */     
/* 2498 */     s[i + 1] = compute_rot(f, g, sinl, cosl);
/* 2499 */     update_u_split(k, i + 1, u, cosl, sinl, t, m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v_split(int topr, int bottomr, GMatrix v, double[] cosr, double[] sinr, GMatrix t, GMatrix m) {
/* 2508 */     for (int j = 0; j < v.nRow; j++) {
/* 2509 */       double vtemp = v.values[j][topr];
/* 2510 */       v.values[j][topr] = cosr[0] * vtemp - sinr[0] * v.values[j][bottomr];
/* 2511 */       v.values[j][bottomr] = sinr[0] * vtemp + cosr[0] * v.values[j][bottomr];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2525 */     System.out.println("topr    =" + topr);
/* 2526 */     System.out.println("bottomr =" + bottomr);
/* 2527 */     System.out.println("cosr =" + cosr[0]);
/* 2528 */     System.out.println("sinr =" + sinr[0]);
/* 2529 */     System.out.println("\nm =");
/* 2530 */     checkMatrix(m);
/* 2531 */     System.out.println("\nv =");
/* 2532 */     checkMatrix(t);
/* 2533 */     m.mul(m, t);
/* 2534 */     System.out.println("\nt*m =");
/* 2535 */     checkMatrix(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u_split(int topr, int bottomr, GMatrix u, double[] cosl, double[] sinl, GMatrix t, GMatrix m) {
/* 2544 */     for (int j = 0; j < u.nCol; j++) {
/* 2545 */       double utemp = u.values[topr][j];
/* 2546 */       u.values[topr][j] = cosl[0] * utemp - sinl[0] * u.values[bottomr][j];
/* 2547 */       u.values[bottomr][j] = sinl[0] * utemp + cosl[0] * u.values[bottomr][j];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2560 */     System.out.println("\nm=");
/* 2561 */     checkMatrix(m);
/* 2562 */     System.out.println("\nu=");
/* 2563 */     checkMatrix(t);
/* 2564 */     m.mul(t, m);
/* 2565 */     System.out.println("\nt*m=");
/* 2566 */     checkMatrix(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u(int index, GMatrix u, double[] cosl, double[] sinl) {
/* 2574 */     for (int j = 0; j < u.nCol; j++) {
/* 2575 */       double utemp = u.values[index][j];
/* 2576 */       u.values[index][j] = 
/* 2577 */         cosl[0] * utemp + sinl[0] * u.values[index + 1][j];
/* 2578 */       u.values[index + 1][j] = 
/* 2579 */         -sinl[0] * utemp + cosl[0] * u.values[index + 1][j];
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void print_m(GMatrix m, GMatrix u, GMatrix v) {
/* 2584 */     GMatrix mtmp = new GMatrix(m.nCol, m.nRow);
/*      */     
/* 2586 */     mtmp.mul(u, mtmp);
/* 2587 */     mtmp.mul(mtmp, v);
/* 2588 */     System.out.println("\n m = \n" + toString(mtmp));
/*      */   }
/*      */ 
/*      */   
/*      */   private static String toString(GMatrix m) {
/* 2593 */     StringBuffer buffer = new StringBuffer(m.nRow * m.nCol * 8);
/*      */ 
/*      */     
/* 2596 */     for (int i = 0; i < m.nRow; i++) {
/* 2597 */       for (int j = 0; j < m.nCol; j++) {
/* 2598 */         if (Math.abs(m.values[i][j]) < 1.0E-9D) {
/* 2599 */           buffer.append("0.0000 ");
/*      */         } else {
/* 2601 */           buffer.append(m.values[i][j]).append(" ");
/*      */         } 
/*      */       } 
/* 2604 */       buffer.append("\n");
/*      */     } 
/* 2606 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void print_svd(double[] s, double[] e, GMatrix u, GMatrix v) {
/* 2612 */     GMatrix mtmp = new GMatrix(u.nCol, v.nRow);
/*      */     
/* 2614 */     System.out.println(" \ns = "); int i;
/* 2615 */     for (i = 0; i < s.length; i++) {
/* 2616 */       System.out.println(" " + s[i]);
/*      */     }
/*      */     
/* 2619 */     System.out.println(" \ne = ");
/* 2620 */     for (i = 0; i < e.length; i++) {
/* 2621 */       System.out.println(" " + e[i]);
/*      */     }
/*      */     
/* 2624 */     System.out.println(" \nu  = \n" + u.toString());
/* 2625 */     System.out.println(" \nv  = \n" + v.toString());
/*      */     
/* 2627 */     mtmp.setIdentity();
/* 2628 */     for (i = 0; i < s.length; i++) {
/* 2629 */       mtmp.values[i][i] = s[i];
/*      */     }
/* 2631 */     for (i = 0; i < e.length; i++) {
/* 2632 */       mtmp.values[i][i + 1] = e[i];
/*      */     }
/* 2634 */     System.out.println(" \nm  = \n" + mtmp.toString());
/*      */     
/* 2636 */     mtmp.mulTransposeLeft(u, mtmp);
/* 2637 */     mtmp.mulTransposeRight(mtmp, v);
/*      */     
/* 2639 */     System.out.println(" \n u.transpose*m*v.transpose  = \n" + 
/* 2640 */         mtmp.toString());
/*      */   }
/*      */   
/*      */   static double max(double a, double b) {
/* 2644 */     if (a > b) {
/* 2645 */       return a;
/*      */     }
/* 2647 */     return b;
/*      */   }
/*      */   
/*      */   static double min(double a, double b) {
/* 2651 */     if (a < b) {
/* 2652 */       return a;
/*      */     }
/* 2654 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_shift(double f, double g, double h) {
/* 2662 */     double ssmin, fa = Math.abs(f);
/* 2663 */     double ga = Math.abs(g);
/* 2664 */     double ha = Math.abs(h);
/* 2665 */     double fhmn = min(fa, ha);
/* 2666 */     double fhmx = max(fa, ha);
/*      */     
/* 2668 */     if (fhmn == 0.0D) {
/* 2669 */       ssmin = 0.0D;
/* 2670 */       if (fhmx != 0.0D)
/*      */       {
/* 2672 */         double d = min(fhmx, ga) / max(fhmx, ga);
/*      */       }
/*      */     }
/* 2675 */     else if (ga < fhmx) {
/* 2676 */       double as = fhmn / fhmx + 1.0D;
/* 2677 */       double at = (fhmx - fhmn) / fhmx;
/* 2678 */       double d__1 = ga / fhmx;
/* 2679 */       double au = d__1 * d__1;
/* 2680 */       double c = 2.0D / (Math.sqrt(as * as + au) + Math.sqrt(at * at + au));
/* 2681 */       ssmin = fhmn * c;
/*      */     } else {
/* 2683 */       double au = fhmx / ga;
/* 2684 */       if (au == 0.0D) {
/* 2685 */         ssmin = fhmn * fhmx / ga;
/*      */       } else {
/* 2687 */         double as = fhmn / fhmx + 1.0D;
/* 2688 */         double at = (fhmx - fhmn) / fhmx;
/* 2689 */         double d__1 = as * au;
/* 2690 */         double d__2 = at * au;
/* 2691 */         double c = 1.0D / (Math.sqrt(d__1 * d__1 + 1.0D) + 
/* 2692 */           Math.sqrt(d__2 * d__2 + 1.0D));
/* 2693 */         ssmin = fhmn * c * au;
/* 2694 */         ssmin += ssmin;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2699 */     return ssmin;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int compute_2X2(double f, double g, double h, double[] single_values, double[] snl, double[] csl, double[] snr, double[] csr, int index) {
/*      */     boolean swap;
/* 2706 */     double c_b3 = 2.0D;
/* 2707 */     double c_b4 = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2719 */     double ssmax = single_values[0];
/* 2720 */     double ssmin = single_values[1];
/* 2721 */     double clt = 0.0D;
/* 2722 */     double crt = 0.0D;
/* 2723 */     double slt = 0.0D;
/* 2724 */     double srt = 0.0D;
/* 2725 */     double tsign = 0.0D;
/*      */     
/* 2727 */     double ft = f;
/* 2728 */     double fa = Math.abs(ft);
/* 2729 */     double ht = h;
/* 2730 */     double ha = Math.abs(h);
/*      */     
/* 2732 */     int pmax = 1;
/* 2733 */     if (ha > fa) {
/* 2734 */       swap = true;
/*      */     } else {
/* 2736 */       swap = false;
/*      */     } 
/* 2738 */     if (swap) {
/* 2739 */       pmax = 3;
/* 2740 */       double temp = ft;
/* 2741 */       ft = ht;
/* 2742 */       ht = temp;
/* 2743 */       temp = fa;
/* 2744 */       fa = ha;
/* 2745 */       ha = temp;
/*      */     } 
/*      */ 
/*      */     
/* 2749 */     double gt = g;
/* 2750 */     double ga = Math.abs(gt);
/* 2751 */     if (ga == 0.0D) {
/* 2752 */       single_values[1] = ha;
/* 2753 */       single_values[0] = fa;
/* 2754 */       clt = 1.0D;
/* 2755 */       crt = 1.0D;
/* 2756 */       slt = 0.0D;
/* 2757 */       srt = 0.0D;
/*      */     } else {
/* 2759 */       boolean gasmal = true;
/* 2760 */       if (ga > fa) {
/* 2761 */         pmax = 2;
/* 2762 */         if (fa / ga < 1.0E-10D) {
/* 2763 */           gasmal = false;
/* 2764 */           ssmax = ga;
/*      */           
/* 2766 */           if (ha > 1.0D) {
/* 2767 */             ssmin = fa / ga / ha;
/*      */           } else {
/* 2769 */             ssmin = fa / ga * ha;
/*      */           } 
/* 2771 */           clt = 1.0D;
/* 2772 */           slt = ht / gt;
/* 2773 */           srt = 1.0D;
/* 2774 */           crt = ft / gt;
/*      */         } 
/*      */       } 
/* 2777 */       if (gasmal) {
/* 2778 */         double l, r, d = fa - ha;
/* 2779 */         if (d == fa) {
/*      */           
/* 2781 */           l = 1.0D;
/*      */         } else {
/* 2783 */           l = d / fa;
/*      */         } 
/*      */         
/* 2786 */         double m = gt / ft;
/* 2787 */         double t = 2.0D - l;
/* 2788 */         double mm = m * m;
/* 2789 */         double tt = t * t;
/* 2790 */         double s = Math.sqrt(tt + mm);
/*      */         
/* 2792 */         if (l == 0.0D) {
/* 2793 */           r = Math.abs(m);
/*      */         } else {
/* 2795 */           r = Math.sqrt(l * l + mm);
/*      */         } 
/*      */         
/* 2798 */         double a = (s + r) * 0.5D;
/* 2799 */         if (ga > fa) {
/* 2800 */           pmax = 2;
/* 2801 */           if (fa / ga < 1.0E-10D) {
/* 2802 */             gasmal = false;
/* 2803 */             ssmax = ga;
/* 2804 */             if (ha > 1.0D) {
/* 2805 */               ssmin = fa / ga / ha;
/*      */             } else {
/* 2807 */               ssmin = fa / ga * ha;
/*      */             } 
/* 2809 */             clt = 1.0D;
/* 2810 */             slt = ht / gt;
/* 2811 */             srt = 1.0D;
/* 2812 */             crt = ft / gt;
/*      */           } 
/*      */         } 
/* 2815 */         if (gasmal) {
/* 2816 */           d = fa - ha;
/* 2817 */           if (d == fa) {
/* 2818 */             l = 1.0D;
/*      */           } else {
/* 2820 */             l = d / fa;
/*      */           } 
/*      */           
/* 2823 */           m = gt / ft;
/* 2824 */           t = 2.0D - l;
/*      */           
/* 2826 */           mm = m * m;
/* 2827 */           tt = t * t;
/* 2828 */           s = Math.sqrt(tt + mm);
/*      */           
/* 2830 */           if (l == 0.0D) {
/* 2831 */             r = Math.abs(m);
/*      */           } else {
/* 2833 */             r = Math.sqrt(l * l + mm);
/*      */           } 
/*      */           
/* 2836 */           a = (s + r) * 0.5D;
/* 2837 */           ssmin = ha / a;
/* 2838 */           ssmax = fa * a;
/*      */           
/* 2840 */           if (mm == 0.0D) {
/* 2841 */             if (l == 0.0D) {
/* 2842 */               t = d_sign(c_b3, ft) * d_sign(c_b4, gt);
/*      */             } else {
/* 2844 */               t = gt / d_sign(d, ft) + m / t;
/*      */             } 
/*      */           } else {
/* 2847 */             t = (m / (s + t) + m / (r + l)) * (a + 1.0D);
/*      */           } 
/*      */           
/* 2850 */           l = Math.sqrt(t * t + 4.0D);
/* 2851 */           crt = 2.0D / l;
/* 2852 */           srt = t / l;
/* 2853 */           clt = (crt + srt * m) / a;
/* 2854 */           slt = ht / ft * srt / a;
/*      */         } 
/*      */       } 
/* 2857 */       if (swap) {
/* 2858 */         csl[0] = srt;
/* 2859 */         snl[0] = crt;
/* 2860 */         csr[0] = slt;
/* 2861 */         snr[0] = clt;
/*      */       } else {
/* 2863 */         csl[0] = clt;
/* 2864 */         snl[0] = slt;
/* 2865 */         csr[0] = crt;
/* 2866 */         snr[0] = srt;
/*      */       } 
/*      */       
/* 2869 */       if (pmax == 1) {
/* 2870 */         tsign = d_sign(c_b4, csr[0]) * 
/* 2871 */           d_sign(c_b4, csl[0]) * d_sign(c_b4, f);
/*      */       }
/* 2873 */       if (pmax == 2) {
/* 2874 */         tsign = d_sign(c_b4, snr[0]) * 
/* 2875 */           d_sign(c_b4, csl[0]) * d_sign(c_b4, g);
/*      */       }
/* 2877 */       if (pmax == 3) {
/* 2878 */         tsign = d_sign(c_b4, snr[0]) * 
/* 2879 */           d_sign(c_b4, snl[0]) * d_sign(c_b4, h);
/*      */       }
/*      */       
/* 2882 */       single_values[index] = d_sign(ssmax, tsign);
/* 2883 */       double d__1 = tsign * d_sign(c_b4, f) * d_sign(c_b4, h);
/* 2884 */       single_values[index + 1] = d_sign(ssmin, d__1);
/*      */     } 
/*      */     
/* 2887 */     return 0;
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
/*      */   static double compute_rot(double f, double g, double[] sin, double[] cos) {
/* 2899 */     double cs, sn, r, safmn2 = 2.002083095183101E-146D;
/* 2900 */     double safmx2 = 4.9947976805055876E145D;
/*      */     
/* 2902 */     if (g == 0.0D) {
/* 2903 */       cs = 1.0D;
/* 2904 */       sn = 0.0D;
/* 2905 */       r = f;
/* 2906 */     } else if (f == 0.0D) {
/* 2907 */       cs = 0.0D;
/* 2908 */       sn = 1.0D;
/* 2909 */       r = g;
/*      */     } else {
/* 2911 */       double f1 = f;
/* 2912 */       double g1 = g;
/* 2913 */       double scale = max(Math.abs(f1), Math.abs(g1));
/* 2914 */       if (scale >= 4.9947976805055876E145D) {
/* 2915 */         int count = 0;
/* 2916 */         while (scale >= 4.9947976805055876E145D) {
/* 2917 */           count++;
/* 2918 */           f1 *= 2.002083095183101E-146D;
/* 2919 */           g1 *= 2.002083095183101E-146D;
/* 2920 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2922 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2923 */         cs = f1 / r;
/* 2924 */         sn = g1 / r;
/* 2925 */         int i__1 = count;
/* 2926 */         for (int i = 1; i <= count; i++) {
/* 2927 */           r *= 4.9947976805055876E145D;
/*      */         }
/* 2929 */       } else if (scale <= 2.002083095183101E-146D) {
/* 2930 */         int count = 0;
/* 2931 */         while (scale <= 2.002083095183101E-146D) {
/* 2932 */           count++;
/* 2933 */           f1 *= 4.9947976805055876E145D;
/* 2934 */           g1 *= 4.9947976805055876E145D;
/* 2935 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2937 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2938 */         cs = f1 / r;
/* 2939 */         sn = g1 / r;
/* 2940 */         int i__1 = count;
/* 2941 */         for (int i = 1; i <= count; i++) {
/* 2942 */           r *= 2.002083095183101E-146D;
/*      */         }
/*      */       } else {
/* 2945 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2946 */         cs = f1 / r;
/* 2947 */         sn = g1 / r;
/*      */       } 
/* 2949 */       if (Math.abs(f) > Math.abs(g) && cs < 0.0D) {
/* 2950 */         cs = -cs;
/* 2951 */         sn = -sn;
/* 2952 */         r = -r;
/*      */       } 
/*      */     } 
/* 2955 */     sin[0] = sn;
/* 2956 */     cos[0] = cs;
/* 2957 */     return r;
/*      */   }
/*      */ 
/*      */   
/*      */   static double d_sign(double a, double b) {
/* 2962 */     double x = (a >= 0.0D) ? a : -a;
/* 2963 */     return (b >= 0.0D) ? x : -x;
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
/* 2976 */     GMatrix m1 = null;
/*      */     try {
/* 2978 */       m1 = (GMatrix)super.clone();
/* 2979 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 2981 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 2985 */     m1.values = new double[this.nRow][this.nCol];
/* 2986 */     for (int i = 0; i < this.nRow; i++) {
/* 2987 */       for (int j = 0; j < this.nCol; j++) {
/* 2988 */         m1.values[i][j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 2992 */     return m1;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\GMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */