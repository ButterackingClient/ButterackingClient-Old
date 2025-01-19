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
/*     */ public abstract class Tuple2f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 9011180388985266884L;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public Tuple2f(float x, float y) {
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
/*     */   public Tuple2f(float[] t) {
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
/*     */   public Tuple2f(Tuple2f t1) {
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
/*     */   public Tuple2f(Tuple2d t1) {
/*  89 */     this.x = (float)t1.x;
/*  90 */     this.y = (float)t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f() {
/*  98 */     this.x = 0.0F;
/*  99 */     this.y = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float x, float y) {
/* 110 */     this.x = x;
/* 111 */     this.y = y;
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
/* 122 */     this.x = t[0];
/* 123 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f t1) {
/* 133 */     this.x = t1.x;
/* 134 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2d t1) {
/* 144 */     this.x = (float)t1.x;
/* 145 */     this.y = (float)t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 155 */     t[0] = this.x;
/* 156 */     t[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f t1, Tuple2f t2) {
/* 167 */     t1.x += t2.x;
/* 168 */     t1.y += t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f t1) {
/* 178 */     this.x += t1.x;
/* 179 */     this.y += t1.y;
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
/*     */   public final void sub(Tuple2f t1, Tuple2f t2) {
/* 191 */     t1.x -= t2.x;
/* 192 */     t1.y -= t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2f t1) {
/* 203 */     this.x -= t1.x;
/* 204 */     this.y -= t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2f t1) {
/* 214 */     this.x = -t1.x;
/* 215 */     this.y = -t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 223 */     this.x = -this.x;
/* 224 */     this.y = -this.y;
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
/*     */   public final void scale(float s, Tuple2f t1) {
/* 236 */     this.x = s * t1.x;
/* 237 */     this.y = s * t1.y;
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
/* 248 */     this.x *= s;
/* 249 */     this.y *= s;
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
/*     */   public final void scaleAdd(float s, Tuple2f t1, Tuple2f t2) {
/* 262 */     this.x = s * t1.x + t2.x;
/* 263 */     this.y = s * t1.y + t2.y;
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
/*     */   public final void scaleAdd(float s, Tuple2f t1) {
/* 275 */     this.x = s * this.x + t1.x;
/* 276 */     this.y = s * this.y + t1.y;
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
/* 291 */     long bits = 1L;
/* 292 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 293 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 294 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple2f t1) {
/*     */     try {
/* 307 */       return (this.x == t1.x && this.y == t1.y);
/* 308 */     } catch (NullPointerException e2) {
/* 309 */       return false;
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
/* 325 */       Tuple2f t2 = (Tuple2f)t1;
/* 326 */       return (this.x == t2.x && this.y == t2.y);
/* 327 */     } catch (NullPointerException e2) {
/* 328 */       return false;
/* 329 */     } catch (ClassCastException e1) {
/* 330 */       return false;
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
/*     */   public boolean epsilonEquals(Tuple2f t1, float epsilon) {
/* 348 */     float diff = this.x - t1.x;
/* 349 */     if (Float.isNaN(diff)) return false; 
/* 350 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 352 */     diff = this.y - t1.y;
/* 353 */     if (Float.isNaN(diff)) return false; 
/* 354 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 356 */     return true;
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
/* 367 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public final void clamp(float min, float max, Tuple2f t) {
/* 380 */     if (t.x > max) {
/* 381 */       this.x = max;
/* 382 */     } else if (t.x < min) {
/* 383 */       this.x = min;
/*     */     } else {
/* 385 */       this.x = t.x;
/*     */     } 
/*     */     
/* 388 */     if (t.y > max) {
/* 389 */       this.y = max;
/* 390 */     } else if (t.y < min) {
/* 391 */       this.y = min;
/*     */     } else {
/* 393 */       this.y = t.y;
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
/*     */   public final void clampMin(float min, Tuple2f t) {
/* 407 */     if (t.x < min) {
/* 408 */       this.x = min;
/*     */     } else {
/* 410 */       this.x = t.x;
/*     */     } 
/*     */     
/* 413 */     if (t.y < min) {
/* 414 */       this.y = min;
/*     */     } else {
/* 416 */       this.y = t.y;
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
/*     */   public final void clampMax(float max, Tuple2f t) {
/* 430 */     if (t.x > max) {
/* 431 */       this.x = max;
/*     */     } else {
/* 433 */       this.x = t.x;
/*     */     } 
/*     */     
/* 436 */     if (t.y > max) {
/* 437 */       this.y = max;
/*     */     } else {
/* 439 */       this.y = t.y;
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
/*     */   public final void absolute(Tuple2f t) {
/* 452 */     this.x = Math.abs(t.x);
/* 453 */     this.y = Math.abs(t.y);
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
/* 464 */     if (this.x > max) {
/* 465 */       this.x = max;
/* 466 */     } else if (this.x < min) {
/* 467 */       this.x = min;
/*     */     } 
/*     */     
/* 470 */     if (this.y > max) {
/* 471 */       this.y = max;
/* 472 */     } else if (this.y < min) {
/* 473 */       this.y = min;
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
/* 485 */     if (this.x < min) this.x = min; 
/* 486 */     if (this.y < min) this.y = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 496 */     if (this.x > max) this.x = max; 
/* 497 */     if (this.y > max) this.y = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 505 */     this.x = Math.abs(this.x);
/* 506 */     this.y = Math.abs(this.y);
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
/*     */   public final void interpolate(Tuple2f t1, Tuple2f t2, float alpha) {
/* 519 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 520 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
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
/*     */   public final void interpolate(Tuple2f t1, float alpha) {
/* 534 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 535 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
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
/* 551 */       return super.clone();
/* 552 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 554 */       throw new InternalError();
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
/* 566 */     return this.x;
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
/* 577 */     this.x = x;
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
/* 588 */     return this.y;
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
/* 599 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */