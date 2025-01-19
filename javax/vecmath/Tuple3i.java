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
/*     */ public abstract class Tuple3i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -732740491767276200L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public Tuple3i(int x, int y, int z) {
/*  65 */     this.x = x;
/*  66 */     this.y = y;
/*  67 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i(int[] t) {
/*  77 */     this.x = t[0];
/*  78 */     this.y = t[1];
/*  79 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i(Tuple3i t1) {
/*  90 */     this.x = t1.x;
/*  91 */     this.y = t1.y;
/*  92 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i() {
/* 100 */     this.x = 0;
/* 101 */     this.y = 0;
/* 102 */     this.z = 0;
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
/*     */   public final void set(int x, int y, int z) {
/* 115 */     this.x = x;
/* 116 */     this.y = y;
/* 117 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int[] t) {
/* 128 */     this.x = t[0];
/* 129 */     this.y = t[1];
/* 130 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3i t1) {
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
/*     */   
/*     */   public final void get(int[] t) {
/* 152 */     t[0] = this.x;
/* 153 */     t[1] = this.y;
/* 154 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3i t) {
/* 164 */     t.x = this.x;
/* 165 */     t.y = this.y;
/* 166 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3i t1, Tuple3i t2) {
/* 177 */     t1.x += t2.x;
/* 178 */     t1.y += t2.y;
/* 179 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3i t1) {
/* 189 */     this.x += t1.x;
/* 190 */     this.y += t1.y;
/* 191 */     this.z += t1.z;
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
/*     */   public final void sub(Tuple3i t1, Tuple3i t2) {
/* 203 */     t1.x -= t2.x;
/* 204 */     t1.y -= t2.y;
/* 205 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3i t1) {
/* 216 */     this.x -= t1.x;
/* 217 */     this.y -= t1.y;
/* 218 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3i t1) {
/* 228 */     this.x = -t1.x;
/* 229 */     this.y = -t1.y;
/* 230 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 238 */     this.x = -this.x;
/* 239 */     this.y = -this.y;
/* 240 */     this.z = -this.z;
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
/*     */   public final void scale(int s, Tuple3i t1) {
/* 252 */     this.x = s * t1.x;
/* 253 */     this.y = s * t1.y;
/* 254 */     this.z = s * t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int s) {
/* 265 */     this.x *= s;
/* 266 */     this.y *= s;
/* 267 */     this.z *= s;
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
/*     */   public final void scaleAdd(int s, Tuple3i t1, Tuple3i t2) {
/* 280 */     this.x = s * t1.x + t2.x;
/* 281 */     this.y = s * t1.y + t2.y;
/* 282 */     this.z = s * t1.z + t2.z;
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
/*     */   public final void scaleAdd(int s, Tuple3i t1) {
/* 294 */     this.x = s * this.x + t1.x;
/* 295 */     this.y = s * this.y + t1.y;
/* 296 */     this.z = s * this.z + t1.z;
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
/* 308 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/* 322 */       Tuple3i t2 = (Tuple3i)t1;
/* 323 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
/* 324 */     } catch (NullPointerException e2) {
/* 325 */       return false;
/* 326 */     } catch (ClassCastException e1) {
/* 327 */       return false;
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
/*     */   public int hashCode() {
/* 343 */     long bits = 1L;
/* 344 */     bits = 31L * bits + this.x;
/* 345 */     bits = 31L * bits + this.y;
/* 346 */     bits = 31L * bits + this.z;
/* 347 */     return (int)(bits ^ bits >> 32L);
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
/*     */   public final void clamp(int min, int max, Tuple3i t) {
/* 360 */     if (t.x > max) {
/* 361 */       this.x = max;
/* 362 */     } else if (t.x < min) {
/* 363 */       this.x = min;
/*     */     } else {
/* 365 */       this.x = t.x;
/*     */     } 
/*     */     
/* 368 */     if (t.y > max) {
/* 369 */       this.y = max;
/* 370 */     } else if (t.y < min) {
/* 371 */       this.y = min;
/*     */     } else {
/* 373 */       this.y = t.y;
/*     */     } 
/*     */     
/* 376 */     if (t.z > max) {
/* 377 */       this.z = max;
/* 378 */     } else if (t.z < min) {
/* 379 */       this.z = min;
/*     */     } else {
/* 381 */       this.z = t.z;
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
/*     */   public final void clampMin(int min, Tuple3i t) {
/* 394 */     if (t.x < min) {
/* 395 */       this.x = min;
/*     */     } else {
/* 397 */       this.x = t.x;
/*     */     } 
/*     */     
/* 400 */     if (t.y < min) {
/* 401 */       this.y = min;
/*     */     } else {
/* 403 */       this.y = t.y;
/*     */     } 
/*     */     
/* 406 */     if (t.z < min) {
/* 407 */       this.z = min;
/*     */     } else {
/* 409 */       this.z = t.z;
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
/*     */   public final void clampMax(int max, Tuple3i t) {
/* 422 */     if (t.x > max) {
/* 423 */       this.x = max;
/*     */     } else {
/* 425 */       this.x = t.x;
/*     */     } 
/*     */     
/* 428 */     if (t.y > max) {
/* 429 */       this.y = max;
/*     */     } else {
/* 431 */       this.y = t.y;
/*     */     } 
/*     */     
/* 434 */     if (t.z > max) {
/* 435 */       this.z = max;
/*     */     } else {
/* 437 */       this.z = t.z;
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
/*     */   public final void absolute(Tuple3i t) {
/* 449 */     this.x = Math.abs(t.x);
/* 450 */     this.y = Math.abs(t.y);
/* 451 */     this.z = Math.abs(t.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(int min, int max) {
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
/*     */     
/* 474 */     if (this.z > max) {
/* 475 */       this.z = max;
/* 476 */     } else if (this.z < min) {
/* 477 */       this.z = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(int min) {
/* 488 */     if (this.x < min) {
/* 489 */       this.x = min;
/*     */     }
/* 491 */     if (this.y < min) {
/* 492 */       this.y = min;
/*     */     }
/* 494 */     if (this.z < min) {
/* 495 */       this.z = min;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int max) {
/* 505 */     if (this.x > max) {
/* 506 */       this.x = max;
/*     */     }
/* 508 */     if (this.y > max) {
/* 509 */       this.y = max;
/*     */     }
/* 511 */     if (this.z > max) {
/* 512 */       this.z = max;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 520 */     this.x = Math.abs(this.x);
/* 521 */     this.y = Math.abs(this.y);
/* 522 */     this.z = Math.abs(this.z);
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
/* 537 */       return super.clone();
/* 538 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 540 */       throw new InternalError();
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
/*     */   public final int getX() {
/* 552 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(int x) {
/* 563 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getY() {
/* 574 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(int y) {
/* 585 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getZ() {
/* 595 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setZ(int z) {
/* 606 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple3i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */