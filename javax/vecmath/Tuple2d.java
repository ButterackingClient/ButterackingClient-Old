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
/*     */ public abstract class Tuple2d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 6205762482756093838L;
/*     */   public double x;
/*     */   public double y;
/*     */   
/*     */   public Tuple2d(double x, double y) {
/*  56 */     this.x = x;
/*  57 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(double[] t) {
/*  67 */     this.x = t[0];
/*  68 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(Tuple2d t1) {
/*  78 */     this.x = t1.x;
/*  79 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d(Tuple2f t1) {
/*  89 */     this.x = t1.x;
/*  90 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2d() {
/*  97 */     this.x = 0.0D;
/*  98 */     this.y = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double x, double y) {
/* 109 */     this.x = x;
/* 110 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 121 */     this.x = t[0];
/* 122 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2d t1) {
/* 132 */     this.x = t1.x;
/* 133 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f t1) {
/* 143 */     this.x = t1.x;
/* 144 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 153 */     t[0] = this.x;
/* 154 */     t[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2d t1, Tuple2d t2) {
/* 165 */     t1.x += t2.x;
/* 166 */     t1.y += t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2d t1) {
/* 176 */     this.x += t1.x;
/* 177 */     this.y += t1.y;
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
/*     */   public final void sub(Tuple2d t1, Tuple2d t2) {
/* 189 */     t1.x -= t2.x;
/* 190 */     t1.y -= t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2d t1) {
/* 201 */     this.x -= t1.x;
/* 202 */     this.y -= t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2d t1) {
/* 212 */     this.x = -t1.x;
/* 213 */     this.y = -t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 221 */     this.x = -this.x;
/* 222 */     this.y = -this.y;
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
/*     */   public final void scale(double s, Tuple2d t1) {
/* 234 */     this.x = s * t1.x;
/* 235 */     this.y = s * t1.y;
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
/* 246 */     this.x *= s;
/* 247 */     this.y *= s;
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
/*     */   public final void scaleAdd(double s, Tuple2d t1, Tuple2d t2) {
/* 260 */     this.x = s * t1.x + t2.x;
/* 261 */     this.y = s * t1.y + t2.y;
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
/*     */   public final void scaleAdd(double s, Tuple2d t1) {
/* 273 */     this.x = s * this.x + t1.x;
/* 274 */     this.y = s * this.y + t1.y;
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
/* 289 */     long bits = 1L;
/* 290 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 291 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 292 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple2d t1) {
/*     */     try {
/* 305 */       return (this.x == t1.x && this.y == t1.y);
/* 306 */     } catch (NullPointerException e2) {
/* 307 */       return false;
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
/* 323 */       Tuple2d t2 = (Tuple2d)t1;
/* 324 */       return (this.x == t2.x && this.y == t2.y);
/* 325 */     } catch (NullPointerException e2) {
/* 326 */       return false;
/* 327 */     } catch (ClassCastException e1) {
/* 328 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple2d t1, double epsilon) {
/* 346 */     double diff = this.x - t1.x;
/* 347 */     if (Double.isNaN(diff)) return false; 
/* 348 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 350 */     diff = this.y - t1.y;
/* 351 */     if (Double.isNaN(diff)) return false; 
/* 352 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 354 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 365 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public final void clamp(double min, double max, Tuple2d t) {
/* 378 */     if (t.x > max) {
/* 379 */       this.x = max;
/* 380 */     } else if (t.x < min) {
/* 381 */       this.x = min;
/*     */     } else {
/* 383 */       this.x = t.x;
/*     */     } 
/*     */     
/* 386 */     if (t.y > max) {
/* 387 */       this.y = max;
/* 388 */     } else if (t.y < min) {
/* 389 */       this.y = min;
/*     */     } else {
/* 391 */       this.y = t.y;
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
/*     */   public final void clampMin(double min, Tuple2d t) {
/* 405 */     if (t.x < min) {
/* 406 */       this.x = min;
/*     */     } else {
/* 408 */       this.x = t.x;
/*     */     } 
/*     */     
/* 411 */     if (t.y < min) {
/* 412 */       this.y = min;
/*     */     } else {
/* 414 */       this.y = t.y;
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
/*     */   public final void clampMax(double max, Tuple2d t) {
/* 428 */     if (t.x > max) {
/* 429 */       this.x = max;
/*     */     } else {
/* 431 */       this.x = t.x;
/*     */     } 
/*     */     
/* 434 */     if (t.y > max) {
/* 435 */       this.y = max;
/*     */     } else {
/* 437 */       this.y = t.y;
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
/*     */   public final void absolute(Tuple2d t) {
/* 450 */     this.x = Math.abs(t.x);
/* 451 */     this.y = Math.abs(t.y);
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
/* 462 */     if (this.x > max) {
/* 463 */       this.x = max;
/* 464 */     } else if (this.x < min) {
/* 465 */       this.x = min;
/*     */     } 
/*     */     
/* 468 */     if (this.y > max) {
/* 469 */       this.y = max;
/* 470 */     } else if (this.y < min) {
/* 471 */       this.y = min;
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
/*     */   public final void clampMin(double min) {
/* 483 */     if (this.x < min) this.x = min; 
/* 484 */     if (this.y < min) this.y = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 494 */     if (this.x > max) this.x = max; 
/* 495 */     if (this.y > max) this.y = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 503 */     this.x = Math.abs(this.x);
/* 504 */     this.y = Math.abs(this.y);
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
/*     */   public final void interpolate(Tuple2d t1, Tuple2d t2, double alpha) {
/* 517 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 518 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
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
/*     */   public final void interpolate(Tuple2d t1, double alpha) {
/* 530 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 531 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
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
/*     */   public Object clone() {
/*     */     try {
/* 547 */       return super.clone();
/* 548 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 550 */       throw new InternalError();
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
/*     */   public final double getX() {
/* 562 */     return this.x;
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
/* 573 */     this.x = x;
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
/* 584 */     return this.y;
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
/* 595 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple2d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */