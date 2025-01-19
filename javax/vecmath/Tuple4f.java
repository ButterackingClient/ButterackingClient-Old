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
/*     */ public abstract class Tuple4f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 7068460319248845763L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float w;
/*     */   
/*     */   public Tuple4f(float x, float y, float z, float w) {
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
/*     */   public Tuple4f(float[] t) {
/*  81 */     this.x = t[0];
/*  82 */     this.y = t[1];
/*  83 */     this.z = t[2];
/*  84 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4f t1) {
/*  94 */     this.x = t1.x;
/*  95 */     this.y = t1.y;
/*  96 */     this.z = t1.z;
/*  97 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4d t1) {
/* 107 */     this.x = (float)t1.x;
/* 108 */     this.y = (float)t1.y;
/* 109 */     this.z = (float)t1.z;
/* 110 */     this.w = (float)t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f() {
/* 118 */     this.x = 0.0F;
/* 119 */     this.y = 0.0F;
/* 120 */     this.z = 0.0F;
/* 121 */     this.w = 0.0F;
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
/*     */   public final void set(float x, float y, float z, float w) {
/* 134 */     this.x = x;
/* 135 */     this.y = y;
/* 136 */     this.z = z;
/* 137 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float[] t) {
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
/*     */   public final void set(Tuple4f t1) {
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
/*     */   public final void set(Tuple4d t1) {
/* 174 */     this.x = (float)t1.x;
/* 175 */     this.y = (float)t1.y;
/* 176 */     this.z = (float)t1.z;
/* 177 */     this.w = (float)t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 187 */     t[0] = this.x;
/* 188 */     t[1] = this.y;
/* 189 */     t[2] = this.z;
/* 190 */     t[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4f t) {
/* 200 */     t.x = this.x;
/* 201 */     t.y = this.y;
/* 202 */     t.z = this.z;
/* 203 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f t1, Tuple4f t2) {
/* 214 */     t1.x += t2.x;
/* 215 */     t1.y += t2.y;
/* 216 */     t1.z += t2.z;
/* 217 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f t1) {
/* 227 */     this.x += t1.x;
/* 228 */     this.y += t1.y;
/* 229 */     this.z += t1.z;
/* 230 */     this.w += t1.w;
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
/*     */   public final void sub(Tuple4f t1, Tuple4f t2) {
/* 242 */     t1.x -= t2.x;
/* 243 */     t1.y -= t2.y;
/* 244 */     t1.z -= t2.z;
/* 245 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4f t1) {
/* 256 */     this.x -= t1.x;
/* 257 */     this.y -= t1.y;
/* 258 */     this.z -= t1.z;
/* 259 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4f t1) {
/* 269 */     this.x = -t1.x;
/* 270 */     this.y = -t1.y;
/* 271 */     this.z = -t1.z;
/* 272 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 280 */     this.x = -this.x;
/* 281 */     this.y = -this.y;
/* 282 */     this.z = -this.z;
/* 283 */     this.w = -this.w;
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
/*     */   public final void scale(float s, Tuple4f t1) {
/* 295 */     this.x = s * t1.x;
/* 296 */     this.y = s * t1.y;
/* 297 */     this.z = s * t1.z;
/* 298 */     this.w = s * t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(float s) {
/* 309 */     this.x *= s;
/* 310 */     this.y *= s;
/* 311 */     this.z *= s;
/* 312 */     this.w *= s;
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
/*     */   public final void scaleAdd(float s, Tuple4f t1, Tuple4f t2) {
/* 325 */     this.x = s * t1.x + t2.x;
/* 326 */     this.y = s * t1.y + t2.y;
/* 327 */     this.z = s * t1.z + t2.z;
/* 328 */     this.w = s * t1.w + t2.w;
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
/*     */   public final void scaleAdd(float s, Tuple4f t1) {
/* 340 */     this.x = s * this.x + t1.x;
/* 341 */     this.y = s * this.y + t1.y;
/* 342 */     this.z = s * this.z + t1.z;
/* 343 */     this.w = s * this.w + t1.w;
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
/* 355 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Tuple4f t1) {
/*     */     try {
/* 367 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z && 
/* 368 */         this.w == t1.w);
/* 369 */     } catch (NullPointerException e2) {
/* 370 */       return false;
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
/* 385 */       Tuple4f t2 = (Tuple4f)t1;
/* 386 */       return (this.x == t2.x && this.y == t2.y && 
/* 387 */         this.z == t2.z && this.w == t2.w);
/* 388 */     } catch (NullPointerException e2) {
/* 389 */       return false;
/* 390 */     } catch (ClassCastException e1) {
/* 391 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple4f t1, float epsilon) {
/* 410 */     float diff = this.x - t1.x;
/* 411 */     if (Float.isNaN(diff)) return false; 
/* 412 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 414 */     diff = this.y - t1.y;
/* 415 */     if (Float.isNaN(diff)) return false; 
/* 416 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 418 */     diff = this.z - t1.z;
/* 419 */     if (Float.isNaN(diff)) return false; 
/* 420 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 422 */     diff = this.w - t1.w;
/* 423 */     if (Float.isNaN(diff)) return false; 
/* 424 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 426 */     return true;
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
/* 441 */     long bits = 1L;
/* 442 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 443 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 444 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 445 */     bits = VecMathUtil.hashFloatBits(bits, this.w);
/* 446 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public final void clamp(float min, float max, Tuple4f t) {
/* 459 */     if (t.x > max) {
/* 460 */       this.x = max;
/* 461 */     } else if (t.x < min) {
/* 462 */       this.x = min;
/*     */     } else {
/* 464 */       this.x = t.x;
/*     */     } 
/*     */     
/* 467 */     if (t.y > max) {
/* 468 */       this.y = max;
/* 469 */     } else if (t.y < min) {
/* 470 */       this.y = min;
/*     */     } else {
/* 472 */       this.y = t.y;
/*     */     } 
/*     */     
/* 475 */     if (t.z > max) {
/* 476 */       this.z = max;
/* 477 */     } else if (t.z < min) {
/* 478 */       this.z = min;
/*     */     } else {
/* 480 */       this.z = t.z;
/*     */     } 
/*     */     
/* 483 */     if (t.w > max) {
/* 484 */       this.w = max;
/* 485 */     } else if (t.w < min) {
/* 486 */       this.w = min;
/*     */     } else {
/* 488 */       this.w = t.w;
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
/*     */   public final void clampMin(float min, Tuple4f t) {
/* 502 */     if (t.x < min) {
/* 503 */       this.x = min;
/*     */     } else {
/* 505 */       this.x = t.x;
/*     */     } 
/*     */     
/* 508 */     if (t.y < min) {
/* 509 */       this.y = min;
/*     */     } else {
/* 511 */       this.y = t.y;
/*     */     } 
/*     */     
/* 514 */     if (t.z < min) {
/* 515 */       this.z = min;
/*     */     } else {
/* 517 */       this.z = t.z;
/*     */     } 
/*     */     
/* 520 */     if (t.w < min) {
/* 521 */       this.w = min;
/*     */     } else {
/* 523 */       this.w = t.w;
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
/*     */   public final void clampMax(float max, Tuple4f t) {
/* 538 */     if (t.x > max) {
/* 539 */       this.x = max;
/*     */     } else {
/* 541 */       this.x = t.x;
/*     */     } 
/*     */     
/* 544 */     if (t.y > max) {
/* 545 */       this.y = max;
/*     */     } else {
/* 547 */       this.y = t.y;
/*     */     } 
/*     */     
/* 550 */     if (t.z > max) {
/* 551 */       this.z = max;
/*     */     } else {
/* 553 */       this.z = t.z;
/*     */     } 
/*     */     
/* 556 */     if (t.w > max) {
/* 557 */       this.w = max;
/*     */     } else {
/* 559 */       this.w = t.z;
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
/*     */   public final void absolute(Tuple4f t) {
/* 572 */     this.x = Math.abs(t.x);
/* 573 */     this.y = Math.abs(t.y);
/* 574 */     this.z = Math.abs(t.z);
/* 575 */     this.w = Math.abs(t.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 586 */     if (this.x > max) {
/* 587 */       this.x = max;
/* 588 */     } else if (this.x < min) {
/* 589 */       this.x = min;
/*     */     } 
/*     */     
/* 592 */     if (this.y > max) {
/* 593 */       this.y = max;
/* 594 */     } else if (this.y < min) {
/* 595 */       this.y = min;
/*     */     } 
/*     */     
/* 598 */     if (this.z > max) {
/* 599 */       this.z = max;
/* 600 */     } else if (this.z < min) {
/* 601 */       this.z = min;
/*     */     } 
/*     */     
/* 604 */     if (this.w > max) {
/* 605 */       this.w = max;
/* 606 */     } else if (this.w < min) {
/* 607 */       this.w = min;
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
/*     */   public final void clampMin(float min) {
/* 619 */     if (this.x < min) this.x = min; 
/* 620 */     if (this.y < min) this.y = min; 
/* 621 */     if (this.z < min) this.z = min; 
/* 622 */     if (this.w < min) this.w = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 633 */     if (this.x > max) this.x = max; 
/* 634 */     if (this.y > max) this.y = max; 
/* 635 */     if (this.z > max) this.z = max; 
/* 636 */     if (this.w > max) this.w = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 645 */     this.x = Math.abs(this.x);
/* 646 */     this.y = Math.abs(this.y);
/* 647 */     this.z = Math.abs(this.z);
/* 648 */     this.w = Math.abs(this.w);
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
/*     */   public void interpolate(Tuple4f t1, Tuple4f t2, float alpha) {
/* 661 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 662 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 663 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
/* 664 */     this.w = (1.0F - alpha) * t1.w + alpha * t2.w;
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
/*     */   public void interpolate(Tuple4f t1, float alpha) {
/* 677 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 678 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 679 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
/* 680 */     this.w = (1.0F - alpha) * this.w + alpha * t1.w;
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
/* 696 */       return super.clone();
/* 697 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 699 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getX() {
/* 710 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(float x) {
/* 721 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getY() {
/* 732 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(float y) {
/* 743 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getZ() {
/* 753 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setZ(float z) {
/* 764 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getW() {
/* 775 */     return this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setW(float w) {
/* 786 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */