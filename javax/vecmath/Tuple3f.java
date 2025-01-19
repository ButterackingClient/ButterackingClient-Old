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
/*     */ public abstract class Tuple3f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5019834619484343712L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public Tuple3f(float x, float y, float z) {
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
/*     */   
/*     */   public Tuple3f(float[] t) {
/*  74 */     this.x = t[0];
/*  75 */     this.y = t[1];
/*  76 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3f t1) {
/*  86 */     this.x = t1.x;
/*  87 */     this.y = t1.y;
/*  88 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3d t1) {
/*  98 */     this.x = (float)t1.x;
/*  99 */     this.y = (float)t1.y;
/* 100 */     this.z = (float)t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f() {
/* 108 */     this.x = 0.0F;
/* 109 */     this.y = 0.0F;
/* 110 */     this.z = 0.0F;
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
/* 122 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */   public final void set(float x, float y, float z) {
/* 134 */     this.x = x;
/* 135 */     this.y = y;
/* 136 */     this.z = z;
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
/* 147 */     this.x = t[0];
/* 148 */     this.y = t[1];
/* 149 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 159 */     this.x = t1.x;
/* 160 */     this.y = t1.y;
/* 161 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 171 */     this.x = (float)t1.x;
/* 172 */     this.y = (float)t1.y;
/* 173 */     this.z = (float)t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 183 */     t[0] = this.x;
/* 184 */     t[1] = this.y;
/* 185 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3f t) {
/* 195 */     t.x = this.x;
/* 196 */     t.y = this.y;
/* 197 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f t1, Tuple3f t2) {
/* 208 */     t1.x += t2.x;
/* 209 */     t1.y += t2.y;
/* 210 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f t1) {
/* 220 */     this.x += t1.x;
/* 221 */     this.y += t1.y;
/* 222 */     this.z += t1.z;
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
/*     */   public final void sub(Tuple3f t1, Tuple3f t2) {
/* 234 */     t1.x -= t2.x;
/* 235 */     t1.y -= t2.y;
/* 236 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3f t1) {
/* 247 */     this.x -= t1.x;
/* 248 */     this.y -= t1.y;
/* 249 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3f t1) {
/* 259 */     this.x = -t1.x;
/* 260 */     this.y = -t1.y;
/* 261 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 269 */     this.x = -this.x;
/* 270 */     this.y = -this.y;
/* 271 */     this.z = -this.z;
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
/*     */   public final void scale(float s, Tuple3f t1) {
/* 283 */     this.x = s * t1.x;
/* 284 */     this.y = s * t1.y;
/* 285 */     this.z = s * t1.z;
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
/* 296 */     this.x *= s;
/* 297 */     this.y *= s;
/* 298 */     this.z *= s;
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
/*     */   public final void scaleAdd(float s, Tuple3f t1, Tuple3f t2) {
/* 311 */     this.x = s * t1.x + t2.x;
/* 312 */     this.y = s * t1.y + t2.y;
/* 313 */     this.z = s * t1.z + t2.z;
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
/*     */   public final void scaleAdd(float s, Tuple3f t1) {
/* 325 */     this.x = s * this.x + t1.x;
/* 326 */     this.y = s * this.y + t1.y;
/* 327 */     this.z = s * this.z + t1.z;
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
/*     */   public boolean equals(Tuple3f t1) {
/*     */     try {
/* 341 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
/* 342 */     } catch (NullPointerException e2) {
/* 343 */       return false;
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
/* 358 */       Tuple3f t2 = (Tuple3f)t1;
/* 359 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
/* 360 */     } catch (NullPointerException e2) {
/* 361 */       return false;
/* 362 */     } catch (ClassCastException e1) {
/* 363 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple3f t1, float epsilon) {
/* 381 */     float diff = this.x - t1.x;
/* 382 */     if (Float.isNaN(diff)) return false; 
/* 383 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 385 */     diff = this.y - t1.y;
/* 386 */     if (Float.isNaN(diff)) return false; 
/* 387 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 389 */     diff = this.z - t1.z;
/* 390 */     if (Float.isNaN(diff)) return false; 
/* 391 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 393 */     return true;
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
/* 409 */     long bits = 1L;
/* 410 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 411 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 412 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 413 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public final void clamp(float min, float max, Tuple3f t) {
/* 426 */     if (t.x > max) {
/* 427 */       this.x = max;
/* 428 */     } else if (t.x < min) {
/* 429 */       this.x = min;
/*     */     } else {
/* 431 */       this.x = t.x;
/*     */     } 
/*     */     
/* 434 */     if (t.y > max) {
/* 435 */       this.y = max;
/* 436 */     } else if (t.y < min) {
/* 437 */       this.y = min;
/*     */     } else {
/* 439 */       this.y = t.y;
/*     */     } 
/*     */     
/* 442 */     if (t.z > max) {
/* 443 */       this.z = max;
/* 444 */     } else if (t.z < min) {
/* 445 */       this.z = min;
/*     */     } else {
/* 447 */       this.z = t.z;
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
/*     */   public final void clampMin(float min, Tuple3f t) {
/* 461 */     if (t.x < min) {
/* 462 */       this.x = min;
/*     */     } else {
/* 464 */       this.x = t.x;
/*     */     } 
/*     */     
/* 467 */     if (t.y < min) {
/* 468 */       this.y = min;
/*     */     } else {
/* 470 */       this.y = t.y;
/*     */     } 
/*     */     
/* 473 */     if (t.z < min) {
/* 474 */       this.z = min;
/*     */     } else {
/* 476 */       this.z = t.z;
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
/*     */   public final void clampMax(float max, Tuple3f t) {
/* 490 */     if (t.x > max) {
/* 491 */       this.x = max;
/*     */     } else {
/* 493 */       this.x = t.x;
/*     */     } 
/*     */     
/* 496 */     if (t.y > max) {
/* 497 */       this.y = max;
/*     */     } else {
/* 499 */       this.y = t.y;
/*     */     } 
/*     */     
/* 502 */     if (t.z > max) {
/* 503 */       this.z = max;
/*     */     } else {
/* 505 */       this.z = t.z;
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
/*     */   public final void absolute(Tuple3f t) {
/* 518 */     this.x = Math.abs(t.x);
/* 519 */     this.y = Math.abs(t.y);
/* 520 */     this.z = Math.abs(t.z);
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
/* 531 */     if (this.x > max) {
/* 532 */       this.x = max;
/* 533 */     } else if (this.x < min) {
/* 534 */       this.x = min;
/*     */     } 
/*     */     
/* 537 */     if (this.y > max) {
/* 538 */       this.y = max;
/* 539 */     } else if (this.y < min) {
/* 540 */       this.y = min;
/*     */     } 
/*     */     
/* 543 */     if (this.z > max) {
/* 544 */       this.z = max;
/* 545 */     } else if (this.z < min) {
/* 546 */       this.z = min;
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
/* 558 */     if (this.x < min) this.x = min; 
/* 559 */     if (this.y < min) this.y = min; 
/* 560 */     if (this.z < min) this.z = min;
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
/* 571 */     if (this.x > max) this.x = max; 
/* 572 */     if (this.y > max) this.y = max; 
/* 573 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 582 */     this.x = Math.abs(this.x);
/* 583 */     this.y = Math.abs(this.y);
/* 584 */     this.z = Math.abs(this.z);
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
/*     */   public final void interpolate(Tuple3f t1, Tuple3f t2, float alpha) {
/* 598 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 599 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 600 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
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
/*     */   public final void interpolate(Tuple3f t1, float alpha) {
/* 614 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 615 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 616 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
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
/*     */   public Object clone() {
/*     */     try {
/* 633 */       return super.clone();
/* 634 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 636 */       throw new InternalError();
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
/*     */   public final float getX() {
/* 648 */     return this.x;
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
/* 659 */     this.x = x;
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
/* 670 */     return this.y;
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
/* 681 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getZ() {
/* 691 */     return this.z;
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
/* 702 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */