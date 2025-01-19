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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Tuple4i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 8064614250942616720L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public int w;
/*     */   
/*     */   public Tuple4i(int x, int y, int z, int w) {
/*  71 */     this.x = x;
/*  72 */     this.y = y;
/*  73 */     this.z = z;
/*  74 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4i(int[] t) {
/*  84 */     this.x = t[0];
/*  85 */     this.y = t[1];
/*  86 */     this.z = t[2];
/*  87 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4i(Tuple4i t1) {
/*  98 */     this.x = t1.x;
/*  99 */     this.y = t1.y;
/* 100 */     this.z = t1.z;
/* 101 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4i() {
/* 109 */     this.x = 0;
/* 110 */     this.y = 0;
/* 111 */     this.z = 0;
/* 112 */     this.w = 0;
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
/*     */   public final void set(int x, int y, int z, int w) {
/* 126 */     this.x = x;
/* 127 */     this.y = y;
/* 128 */     this.z = z;
/* 129 */     this.w = w;
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
/* 140 */     this.x = t[0];
/* 141 */     this.y = t[1];
/* 142 */     this.z = t[2];
/* 143 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4i t1) {
/* 153 */     this.x = t1.x;
/* 154 */     this.y = t1.y;
/* 155 */     this.z = t1.z;
/* 156 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] t) {
/* 166 */     t[0] = this.x;
/* 167 */     t[1] = this.y;
/* 168 */     t[2] = this.z;
/* 169 */     t[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4i t) {
/* 179 */     t.x = this.x;
/* 180 */     t.y = this.y;
/* 181 */     t.z = this.z;
/* 182 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4i t1, Tuple4i t2) {
/* 193 */     t1.x += t2.x;
/* 194 */     t1.y += t2.y;
/* 195 */     t1.z += t2.z;
/* 196 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4i t1) {
/* 206 */     this.x += t1.x;
/* 207 */     this.y += t1.y;
/* 208 */     this.z += t1.z;
/* 209 */     this.w += t1.w;
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
/*     */   public final void sub(Tuple4i t1, Tuple4i t2) {
/* 221 */     t1.x -= t2.x;
/* 222 */     t1.y -= t2.y;
/* 223 */     t1.z -= t2.z;
/* 224 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4i t1) {
/* 235 */     this.x -= t1.x;
/* 236 */     this.y -= t1.y;
/* 237 */     this.z -= t1.z;
/* 238 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4i t1) {
/* 248 */     this.x = -t1.x;
/* 249 */     this.y = -t1.y;
/* 250 */     this.z = -t1.z;
/* 251 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 259 */     this.x = -this.x;
/* 260 */     this.y = -this.y;
/* 261 */     this.z = -this.z;
/* 262 */     this.w = -this.w;
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
/*     */   public final void scale(int s, Tuple4i t1) {
/* 274 */     this.x = s * t1.x;
/* 275 */     this.y = s * t1.y;
/* 276 */     this.z = s * t1.z;
/* 277 */     this.w = s * t1.w;
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
/* 288 */     this.x *= s;
/* 289 */     this.y *= s;
/* 290 */     this.z *= s;
/* 291 */     this.w *= s;
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
/*     */   public final void scaleAdd(int s, Tuple4i t1, Tuple4i t2) {
/* 304 */     this.x = s * t1.x + t2.x;
/* 305 */     this.y = s * t1.y + t2.y;
/* 306 */     this.z = s * t1.z + t2.z;
/* 307 */     this.w = s * t1.w + t2.w;
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
/*     */   public final void scaleAdd(int s, Tuple4i t1) {
/* 319 */     this.x = s * this.x + t1.x;
/* 320 */     this.y = s * this.y + t1.y;
/* 321 */     this.z = s * this.z + t1.z;
/* 322 */     this.w = s * this.w + t1.w;
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
/* 334 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
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
/* 349 */       Tuple4i t2 = (Tuple4i)t1;
/* 350 */       return (this.x == t2.x && this.y == t2.y && 
/* 351 */         this.z == t2.z && this.w == t2.w);
/* 352 */     } catch (NullPointerException e2) {
/* 353 */       return false;
/* 354 */     } catch (ClassCastException e1) {
/* 355 */       return false;
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
/* 371 */     long bits = 1L;
/* 372 */     bits = 31L * bits + this.x;
/* 373 */     bits = 31L * bits + this.y;
/* 374 */     bits = 31L * bits + this.z;
/* 375 */     bits = 31L * bits + this.w;
/* 376 */     return (int)(bits ^ bits >> 32L);
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
/*     */   public final void clamp(int min, int max, Tuple4i t) {
/* 389 */     if (t.x > max) {
/* 390 */       this.x = max;
/* 391 */     } else if (t.x < min) {
/* 392 */       this.x = min;
/*     */     } else {
/* 394 */       this.x = t.x;
/*     */     } 
/*     */     
/* 397 */     if (t.y > max) {
/* 398 */       this.y = max;
/* 399 */     } else if (t.y < min) {
/* 400 */       this.y = min;
/*     */     } else {
/* 402 */       this.y = t.y;
/*     */     } 
/*     */     
/* 405 */     if (t.z > max) {
/* 406 */       this.z = max;
/* 407 */     } else if (t.z < min) {
/* 408 */       this.z = min;
/*     */     } else {
/* 410 */       this.z = t.z;
/*     */     } 
/*     */     
/* 413 */     if (t.w > max) {
/* 414 */       this.w = max;
/* 415 */     } else if (t.w < min) {
/* 416 */       this.w = min;
/*     */     } else {
/* 418 */       this.w = t.w;
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
/*     */   public final void clampMin(int min, Tuple4i t) {
/* 431 */     if (t.x < min) {
/* 432 */       this.x = min;
/*     */     } else {
/* 434 */       this.x = t.x;
/*     */     } 
/*     */     
/* 437 */     if (t.y < min) {
/* 438 */       this.y = min;
/*     */     } else {
/* 440 */       this.y = t.y;
/*     */     } 
/*     */     
/* 443 */     if (t.z < min) {
/* 444 */       this.z = min;
/*     */     } else {
/* 446 */       this.z = t.z;
/*     */     } 
/*     */     
/* 449 */     if (t.w < min) {
/* 450 */       this.w = min;
/*     */     } else {
/* 452 */       this.w = t.w;
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
/*     */   public final void clampMax(int max, Tuple4i t) {
/* 467 */     if (t.x > max) {
/* 468 */       this.x = max;
/*     */     } else {
/* 470 */       this.x = t.x;
/*     */     } 
/*     */     
/* 473 */     if (t.y > max) {
/* 474 */       this.y = max;
/*     */     } else {
/* 476 */       this.y = t.y;
/*     */     } 
/*     */     
/* 479 */     if (t.z > max) {
/* 480 */       this.z = max;
/*     */     } else {
/* 482 */       this.z = t.z;
/*     */     } 
/*     */     
/* 485 */     if (t.w > max) {
/* 486 */       this.w = max;
/*     */     } else {
/* 488 */       this.w = t.z;
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
/*     */   public final void absolute(Tuple4i t) {
/* 500 */     this.x = Math.abs(t.x);
/* 501 */     this.y = Math.abs(t.y);
/* 502 */     this.z = Math.abs(t.z);
/* 503 */     this.w = Math.abs(t.w);
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
/* 514 */     if (this.x > max) {
/* 515 */       this.x = max;
/* 516 */     } else if (this.x < min) {
/* 517 */       this.x = min;
/*     */     } 
/*     */     
/* 520 */     if (this.y > max) {
/* 521 */       this.y = max;
/* 522 */     } else if (this.y < min) {
/* 523 */       this.y = min;
/*     */     } 
/*     */     
/* 526 */     if (this.z > max) {
/* 527 */       this.z = max;
/* 528 */     } else if (this.z < min) {
/* 529 */       this.z = min;
/*     */     } 
/*     */     
/* 532 */     if (this.w > max) {
/* 533 */       this.w = max;
/* 534 */     } else if (this.w < min) {
/* 535 */       this.w = min;
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
/* 546 */     if (this.x < min) {
/* 547 */       this.x = min;
/*     */     }
/* 549 */     if (this.y < min) {
/* 550 */       this.y = min;
/*     */     }
/* 552 */     if (this.z < min) {
/* 553 */       this.z = min;
/*     */     }
/* 555 */     if (this.w < min) {
/* 556 */       this.w = min;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int max) {
/* 566 */     if (this.x > max) {
/* 567 */       this.x = max;
/*     */     }
/* 569 */     if (this.y > max) {
/* 570 */       this.y = max;
/*     */     }
/* 572 */     if (this.z > max) {
/* 573 */       this.z = max;
/*     */     }
/* 575 */     if (this.w > max) {
/* 576 */       this.w = max;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 584 */     this.x = Math.abs(this.x);
/* 585 */     this.y = Math.abs(this.y);
/* 586 */     this.z = Math.abs(this.z);
/* 587 */     this.w = Math.abs(this.w);
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
/* 602 */       return super.clone();
/* 603 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 605 */       throw new InternalError();
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
/* 617 */     return this.x;
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
/* 628 */     this.x = x;
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
/* 639 */     return this.y;
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
/* 650 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getZ() {
/* 660 */     return this.z;
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
/* 671 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getW() {
/* 682 */     return this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setW(int w) {
/* 693 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple4i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */