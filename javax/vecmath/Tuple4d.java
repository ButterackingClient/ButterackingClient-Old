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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Tuple4d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -4748953690425311052L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double w;
/*     */   
/*     */   public Tuple4d(double x, double y, double z, double w) {
/*  68 */     this.x = x;
/*  69 */     this.y = y;
/*  70 */     this.z = z;
/*  71 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(double[] t) {
/*  82 */     this.x = t[0];
/*  83 */     this.y = t[1];
/*  84 */     this.z = t[2];
/*  85 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4d t1) {
/*  95 */     this.x = t1.x;
/*  96 */     this.y = t1.y;
/*  97 */     this.z = t1.z;
/*  98 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4f t1) {
/* 108 */     this.x = t1.x;
/* 109 */     this.y = t1.y;
/* 110 */     this.z = t1.z;
/* 111 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d() {
/* 119 */     this.x = 0.0D;
/* 120 */     this.y = 0.0D;
/* 121 */     this.z = 0.0D;
/* 122 */     this.w = 0.0D;
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
/*     */   public final void set(double x, double y, double z, double w) {
/* 135 */     this.x = x;
/* 136 */     this.y = y;
/* 137 */     this.z = z;
/* 138 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 148 */     this.x = t[0];
/* 149 */     this.y = t[1];
/* 150 */     this.z = t[2];
/* 151 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d t1) {
/* 161 */     this.x = t1.x;
/* 162 */     this.y = t1.y;
/* 163 */     this.z = t1.z;
/* 164 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f t1) {
/* 174 */     this.x = t1.x;
/* 175 */     this.y = t1.y;
/* 176 */     this.z = t1.z;
/* 177 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 188 */     t[0] = this.x;
/* 189 */     t[1] = this.y;
/* 190 */     t[2] = this.z;
/* 191 */     t[3] = this.w;
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
/*     */   public final void get(Tuple4d t) {
/* 203 */     t.x = this.x;
/* 204 */     t.y = this.y;
/* 205 */     t.z = this.z;
/* 206 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d t1, Tuple4d t2) {
/* 217 */     t1.x += t2.x;
/* 218 */     t1.y += t2.y;
/* 219 */     t1.z += t2.z;
/* 220 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d t1) {
/* 230 */     this.x += t1.x;
/* 231 */     this.y += t1.y;
/* 232 */     this.z += t1.z;
/* 233 */     this.w += t1.w;
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
/*     */   public final void sub(Tuple4d t1, Tuple4d t2) {
/* 245 */     t1.x -= t2.x;
/* 246 */     t1.y -= t2.y;
/* 247 */     t1.z -= t2.z;
/* 248 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4d t1) {
/* 259 */     this.x -= t1.x;
/* 260 */     this.y -= t1.y;
/* 261 */     this.z -= t1.z;
/* 262 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4d t1) {
/* 272 */     this.x = -t1.x;
/* 273 */     this.y = -t1.y;
/* 274 */     this.z = -t1.z;
/* 275 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 283 */     this.x = -this.x;
/* 284 */     this.y = -this.y;
/* 285 */     this.z = -this.z;
/* 286 */     this.w = -this.w;
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
/*     */   public final void scale(double s, Tuple4d t1) {
/* 298 */     this.x = s * t1.x;
/* 299 */     this.y = s * t1.y;
/* 300 */     this.z = s * t1.z;
/* 301 */     this.w = s * t1.w;
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
/* 312 */     this.x *= s;
/* 313 */     this.y *= s;
/* 314 */     this.z *= s;
/* 315 */     this.w *= s;
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
/*     */   public final void scaleAdd(double s, Tuple4d t1, Tuple4d t2) {
/* 328 */     this.x = s * t1.x + t2.x;
/* 329 */     this.y = s * t1.y + t2.y;
/* 330 */     this.z = s * t1.z + t2.z;
/* 331 */     this.w = s * t1.w + t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(float s, Tuple4d t1) {
/* 339 */     scaleAdd(s, t1);
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
/*     */   public final void scaleAdd(double s, Tuple4d t1) {
/* 351 */     this.x = s * this.x + t1.x;
/* 352 */     this.y = s * this.y + t1.y;
/* 353 */     this.z = s * this.z + t1.z;
/* 354 */     this.w = s * this.w + t1.w;
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
/* 366 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
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
/*     */   public boolean equals(Tuple4d t1) {
/*     */     try {
/* 379 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z && 
/* 380 */         this.w == t1.w);
/* 381 */     } catch (NullPointerException e2) {
/* 382 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     try {
/* 398 */       Tuple4d t2 = (Tuple4d)t1;
/* 399 */       return (this.x == t2.x && this.y == t2.y && 
/* 400 */         this.z == t2.z && this.w == t2.w);
/* 401 */     } catch (NullPointerException e2) {
/* 402 */       return false;
/* 403 */     } catch (ClassCastException e1) {
/* 404 */       return false;
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
/*     */   
/*     */   public boolean epsilonEquals(Tuple4d t1, double epsilon) {
/* 423 */     double diff = this.x - t1.x;
/* 424 */     if (Double.isNaN(diff)) return false; 
/* 425 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 427 */     diff = this.y - t1.y;
/* 428 */     if (Double.isNaN(diff)) return false; 
/* 429 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 431 */     diff = this.z - t1.z;
/* 432 */     if (Double.isNaN(diff)) return false; 
/* 433 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 435 */     diff = this.w - t1.w;
/* 436 */     if (Double.isNaN(diff)) return false; 
/* 437 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 439 */     return true;
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
/*     */   public int hashCode() {
/* 455 */     long bits = 1L;
/* 456 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 457 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 458 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 459 */     bits = VecMathUtil.hashDoubleBits(bits, this.w);
/* 460 */     return VecMathUtil.hashFinish(bits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max, Tuple4d t) {
/* 468 */     clamp(min, max, t);
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
/*     */   public final void clamp(double min, double max, Tuple4d t) {
/* 481 */     if (t.x > max) {
/* 482 */       this.x = max;
/* 483 */     } else if (t.x < min) {
/* 484 */       this.x = min;
/*     */     } else {
/* 486 */       this.x = t.x;
/*     */     } 
/*     */     
/* 489 */     if (t.y > max) {
/* 490 */       this.y = max;
/* 491 */     } else if (t.y < min) {
/* 492 */       this.y = min;
/*     */     } else {
/* 494 */       this.y = t.y;
/*     */     } 
/*     */     
/* 497 */     if (t.z > max) {
/* 498 */       this.z = max;
/* 499 */     } else if (t.z < min) {
/* 500 */       this.z = min;
/*     */     } else {
/* 502 */       this.z = t.z;
/*     */     } 
/*     */     
/* 505 */     if (t.w > max) {
/* 506 */       this.w = max;
/* 507 */     } else if (t.w < min) {
/* 508 */       this.w = min;
/*     */     } else {
/* 510 */       this.w = t.w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min, Tuple4d t) {
/* 520 */     clampMin(min, t);
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
/*     */   public final void clampMin(double min, Tuple4d t) {
/* 532 */     if (t.x < min) {
/* 533 */       this.x = min;
/*     */     } else {
/* 535 */       this.x = t.x;
/*     */     } 
/*     */     
/* 538 */     if (t.y < min) {
/* 539 */       this.y = min;
/*     */     } else {
/* 541 */       this.y = t.y;
/*     */     } 
/*     */     
/* 544 */     if (t.z < min) {
/* 545 */       this.z = min;
/*     */     } else {
/* 547 */       this.z = t.z;
/*     */     } 
/*     */     
/* 550 */     if (t.w < min) {
/* 551 */       this.w = min;
/*     */     } else {
/* 553 */       this.w = t.w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max, Tuple4d t) {
/* 563 */     clampMax(max, t);
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
/*     */   public final void clampMax(double max, Tuple4d t) {
/* 575 */     if (t.x > max) {
/* 576 */       this.x = max;
/*     */     } else {
/* 578 */       this.x = t.x;
/*     */     } 
/*     */     
/* 581 */     if (t.y > max) {
/* 582 */       this.y = max;
/*     */     } else {
/* 584 */       this.y = t.y;
/*     */     } 
/*     */     
/* 587 */     if (t.z > max) {
/* 588 */       this.z = max;
/*     */     } else {
/* 590 */       this.z = t.z;
/*     */     } 
/*     */     
/* 593 */     if (t.w > max) {
/* 594 */       this.w = max;
/*     */     } else {
/* 596 */       this.w = t.z;
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
/*     */   public final void absolute(Tuple4d t) {
/* 609 */     this.x = Math.abs(t.x);
/* 610 */     this.y = Math.abs(t.y);
/* 611 */     this.z = Math.abs(t.z);
/* 612 */     this.w = Math.abs(t.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 621 */     clamp(min, max);
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
/* 632 */     if (this.x > max) {
/* 633 */       this.x = max;
/* 634 */     } else if (this.x < min) {
/* 635 */       this.x = min;
/*     */     } 
/*     */     
/* 638 */     if (this.y > max) {
/* 639 */       this.y = max;
/* 640 */     } else if (this.y < min) {
/* 641 */       this.y = min;
/*     */     } 
/*     */     
/* 644 */     if (this.z > max) {
/* 645 */       this.z = max;
/* 646 */     } else if (this.z < min) {
/* 647 */       this.z = min;
/*     */     } 
/*     */     
/* 650 */     if (this.w > max) {
/* 651 */       this.w = max;
/* 652 */     } else if (this.w < min) {
/* 653 */       this.w = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min) {
/* 663 */     clampMin(min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min) {
/* 673 */     if (this.x < min) this.x = min; 
/* 674 */     if (this.y < min) this.y = min; 
/* 675 */     if (this.z < min) this.z = min; 
/* 676 */     if (this.w < min) this.w = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 684 */     clampMax(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 694 */     if (this.x > max) this.x = max; 
/* 695 */     if (this.y > max) this.y = max; 
/* 696 */     if (this.z > max) this.z = max; 
/* 697 */     if (this.w > max) this.w = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 706 */     this.x = Math.abs(this.x);
/* 707 */     this.y = Math.abs(this.y);
/* 708 */     this.z = Math.abs(this.z);
/* 709 */     this.w = Math.abs(this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, Tuple4d t2, float alpha) {
/* 718 */     interpolate(t1, t2, alpha);
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
/*     */   public void interpolate(Tuple4d t1, Tuple4d t2, double alpha) {
/* 731 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 732 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
/* 733 */     this.z = (1.0D - alpha) * t1.z + alpha * t2.z;
/* 734 */     this.w = (1.0D - alpha) * t1.w + alpha * t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, float alpha) {
/* 742 */     interpolate(t1, alpha);
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
/*     */   public void interpolate(Tuple4d t1, double alpha) {
/* 754 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 755 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
/* 756 */     this.z = (1.0D - alpha) * this.z + alpha * t1.z;
/* 757 */     this.w = (1.0D - alpha) * this.w + alpha * t1.w;
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
/* 772 */       return super.clone();
/* 773 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 775 */       throw new InternalError();
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
/* 786 */     return this.x;
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
/* 797 */     this.x = x;
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
/* 808 */     return this.y;
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
/* 819 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getZ() {
/* 829 */     return this.z;
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
/* 840 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getW() {
/* 851 */     return this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setW(double w) {
/* 862 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */