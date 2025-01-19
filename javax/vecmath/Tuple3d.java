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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Tuple3d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5542096614926168415L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   
/*     */   public Tuple3d(double x, double y, double z) {
/*  62 */     this.x = x;
/*  63 */     this.y = y;
/*  64 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(double[] t) {
/*  73 */     this.x = t[0];
/*  74 */     this.y = t[1];
/*  75 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3d t1) {
/*  84 */     this.x = t1.x;
/*  85 */     this.y = t1.y;
/*  86 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3f t1) {
/*  95 */     this.x = t1.x;
/*  96 */     this.y = t1.y;
/*  97 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d() {
/* 104 */     this.x = 0.0D;
/* 105 */     this.y = 0.0D;
/* 106 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double x, double y, double z) {
/* 117 */     this.x = x;
/* 118 */     this.y = y;
/* 119 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 129 */     this.x = t[0];
/* 130 */     this.y = t[1];
/* 131 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 140 */     this.x = t1.x;
/* 141 */     this.y = t1.y;
/* 142 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 151 */     this.x = t1.x;
/* 152 */     this.y = t1.y;
/* 153 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 163 */     t[0] = this.x;
/* 164 */     t[1] = this.y;
/* 165 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3d t) {
/* 175 */     t.x = this.x;
/* 176 */     t.y = this.y;
/* 177 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d t1, Tuple3d t2) {
/* 188 */     t1.x += t2.x;
/* 189 */     t1.y += t2.y;
/* 190 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d t1) {
/* 200 */     this.x += t1.x;
/* 201 */     this.y += t1.y;
/* 202 */     this.z += t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d t1, Tuple3d t2) {
/* 213 */     t1.x -= t2.x;
/* 214 */     t1.y -= t2.y;
/* 215 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d t1) {
/* 225 */     this.x -= t1.x;
/* 226 */     this.y -= t1.y;
/* 227 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3d t1) {
/* 237 */     this.x = -t1.x;
/* 238 */     this.y = -t1.y;
/* 239 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 247 */     this.x = -this.x;
/* 248 */     this.y = -this.y;
/* 249 */     this.z = -this.z;
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
/*     */   public final void scale(double s, Tuple3d t1) {
/* 261 */     this.x = s * t1.x;
/* 262 */     this.y = s * t1.y;
/* 263 */     this.z = s * t1.z;
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
/* 274 */     this.x *= s;
/* 275 */     this.y *= s;
/* 276 */     this.z *= s;
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
/*     */   public final void scaleAdd(double s, Tuple3d t1, Tuple3d t2) {
/* 289 */     this.x = s * t1.x + t2.x;
/* 290 */     this.y = s * t1.y + t2.y;
/* 291 */     this.z = s * t1.z + t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, Tuple3f t1) {
/* 299 */     scaleAdd(s, new Point3d(t1));
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
/*     */   public final void scaleAdd(double s, Tuple3d t1) {
/* 311 */     this.x = s * this.x + t1.x;
/* 312 */     this.y = s * this.y + t1.y;
/* 313 */     this.z = s * this.z + t1.z;
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
/*     */   public String toString() {
/* 325 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */   public int hashCode() {
/* 340 */     long bits = 1L;
/* 341 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 342 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 343 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 344 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple3d t1) {
/*     */     try {
/* 357 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
/* 358 */     } catch (NullPointerException e2) {
/* 359 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     try {
/* 374 */       Tuple3d t2 = (Tuple3d)t1;
/* 375 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
/* 376 */     } catch (ClassCastException e1) {
/* 377 */       return false;
/* 378 */     } catch (NullPointerException e2) {
/* 379 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple3d t1, double epsilon) {
/* 397 */     double diff = this.x - t1.x;
/* 398 */     if (Double.isNaN(diff)) return false; 
/* 399 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 401 */     diff = this.y - t1.y;
/* 402 */     if (Double.isNaN(diff)) return false; 
/* 403 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 405 */     diff = this.z - t1.z;
/* 406 */     if (Double.isNaN(diff)) return false; 
/* 407 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 409 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max, Tuple3d t) {
/* 418 */     clamp(min, max, t);
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
/*     */   public final void clamp(double min, double max, Tuple3d t) {
/* 431 */     if (t.x > max) {
/* 432 */       this.x = max;
/* 433 */     } else if (t.x < min) {
/* 434 */       this.x = min;
/*     */     } else {
/* 436 */       this.x = t.x;
/*     */     } 
/*     */     
/* 439 */     if (t.y > max) {
/* 440 */       this.y = max;
/* 441 */     } else if (t.y < min) {
/* 442 */       this.y = min;
/*     */     } else {
/* 444 */       this.y = t.y;
/*     */     } 
/*     */     
/* 447 */     if (t.z > max) {
/* 448 */       this.z = max;
/* 449 */     } else if (t.z < min) {
/* 450 */       this.z = min;
/*     */     } else {
/* 452 */       this.z = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min, Tuple3d t) {
/* 462 */     clampMin(min, t);
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
/*     */   public final void clampMin(double min, Tuple3d t) {
/* 474 */     if (t.x < min) {
/* 475 */       this.x = min;
/*     */     } else {
/* 477 */       this.x = t.x;
/*     */     } 
/*     */     
/* 480 */     if (t.y < min) {
/* 481 */       this.y = min;
/*     */     } else {
/* 483 */       this.y = t.y;
/*     */     } 
/*     */     
/* 486 */     if (t.z < min) {
/* 487 */       this.z = min;
/*     */     } else {
/* 489 */       this.z = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max, Tuple3d t) {
/* 499 */     clampMax(max, t);
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
/*     */   public final void clampMax(double max, Tuple3d t) {
/* 511 */     if (t.x > max) {
/* 512 */       this.x = max;
/*     */     } else {
/* 514 */       this.x = t.x;
/*     */     } 
/*     */     
/* 517 */     if (t.y > max) {
/* 518 */       this.y = max;
/*     */     } else {
/* 520 */       this.y = t.y;
/*     */     } 
/*     */     
/* 523 */     if (t.z > max) {
/* 524 */       this.z = max;
/*     */     } else {
/* 526 */       this.z = t.z;
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
/*     */   public final void absolute(Tuple3d t) {
/* 539 */     this.x = Math.abs(t.x);
/* 540 */     this.y = Math.abs(t.y);
/* 541 */     this.z = Math.abs(t.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 550 */     clamp(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double min, double max) {
/* 561 */     if (this.x > max) {
/* 562 */       this.x = max;
/* 563 */     } else if (this.x < min) {
/* 564 */       this.x = min;
/*     */     } 
/*     */     
/* 567 */     if (this.y > max) {
/* 568 */       this.y = max;
/* 569 */     } else if (this.y < min) {
/* 570 */       this.y = min;
/*     */     } 
/*     */     
/* 573 */     if (this.z > max) {
/* 574 */       this.z = max;
/* 575 */     } else if (this.z < min) {
/* 576 */       this.z = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min) {
/* 586 */     clampMin(min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min) {
/* 596 */     if (this.x < min) this.x = min; 
/* 597 */     if (this.y < min) this.y = min; 
/* 598 */     if (this.z < min) this.z = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 607 */     clampMax(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 617 */     if (this.x > max) this.x = max; 
/* 618 */     if (this.y > max) this.y = max; 
/* 619 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 627 */     this.x = Math.abs(this.x);
/* 628 */     this.y = Math.abs(this.y);
/* 629 */     this.z = Math.abs(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, Tuple3d t2, float alpha) {
/* 637 */     interpolate(t1, t2, alpha);
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
/*     */   public final void interpolate(Tuple3d t1, Tuple3d t2, double alpha) {
/* 650 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 651 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
/* 652 */     this.z = (1.0D - alpha) * t1.z + alpha * t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, float alpha) {
/* 660 */     interpolate(t1, alpha);
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
/*     */   public final void interpolate(Tuple3d t1, double alpha) {
/* 672 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 673 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
/* 674 */     this.z = (1.0D - alpha) * this.z + alpha * t1.z;
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
/*     */   public Object clone() {
/*     */     try {
/* 689 */       return super.clone();
/* 690 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 692 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getX() {
/* 703 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(double x) {
/* 714 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getY() {
/* 725 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(double y) {
/* 736 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getZ() {
/* 746 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setZ(double z) {
/* 757 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */