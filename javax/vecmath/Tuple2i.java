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
/*     */ public abstract class Tuple2i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -3555701650170169638L;
/*     */   public int x;
/*     */   public int y;
/*     */   
/*     */   public Tuple2i(int x, int y) {
/*  59 */     this.x = x;
/*  60 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i(int[] t) {
/*  70 */     this.x = t[0];
/*  71 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i(Tuple2i t1) {
/*  82 */     this.x = t1.x;
/*  83 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i() {
/*  91 */     this.x = 0;
/*  92 */     this.y = 0;
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
/*     */   public final void set(int x, int y) {
/* 104 */     this.x = x;
/* 105 */     this.y = y;
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
/* 116 */     this.x = t[0];
/* 117 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2i t1) {
/* 127 */     this.x = t1.x;
/* 128 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] t) {
/* 138 */     t[0] = this.x;
/* 139 */     t[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple2i t) {
/* 149 */     t.x = this.x;
/* 150 */     t.y = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2i t1, Tuple2i t2) {
/* 161 */     t1.x += t2.x;
/* 162 */     t1.y += t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2i t1) {
/* 172 */     this.x += t1.x;
/* 173 */     this.y += t1.y;
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
/*     */   public final void sub(Tuple2i t1, Tuple2i t2) {
/* 185 */     t1.x -= t2.x;
/* 186 */     t1.y -= t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2i t1) {
/* 197 */     this.x -= t1.x;
/* 198 */     this.y -= t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2i t1) {
/* 208 */     this.x = -t1.x;
/* 209 */     this.y = -t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 217 */     this.x = -this.x;
/* 218 */     this.y = -this.y;
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
/*     */   public final void scale(int s, Tuple2i t1) {
/* 230 */     this.x = s * t1.x;
/* 231 */     this.y = s * t1.y;
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
/* 242 */     this.x *= s;
/* 243 */     this.y *= s;
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
/*     */   public final void scaleAdd(int s, Tuple2i t1, Tuple2i t2) {
/* 256 */     this.x = s * t1.x + t2.x;
/* 257 */     this.y = s * t1.y + t2.y;
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
/*     */   public final void scaleAdd(int s, Tuple2i t1) {
/* 269 */     this.x = s * this.x + t1.x;
/* 270 */     this.y = s * this.y + t1.y;
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
/* 282 */     return "(" + this.x + ", " + this.y + ")";
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
/* 296 */       Tuple2i t2 = (Tuple2i)t1;
/* 297 */       return (this.x == t2.x && this.y == t2.y);
/* 298 */     } catch (NullPointerException e2) {
/* 299 */       return false;
/* 300 */     } catch (ClassCastException e1) {
/* 301 */       return false;
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
/* 317 */     long bits = 1L;
/* 318 */     bits = 31L * bits + this.x;
/* 319 */     bits = 31L * bits + this.y;
/* 320 */     return (int)(bits ^ bits >> 32L);
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
/*     */   public final void clamp(int min, int max, Tuple2i t) {
/* 333 */     if (t.x > max) {
/* 334 */       this.x = max;
/* 335 */     } else if (t.x < min) {
/* 336 */       this.x = min;
/*     */     } else {
/* 338 */       this.x = t.x;
/*     */     } 
/*     */     
/* 341 */     if (t.y > max) {
/* 342 */       this.y = max;
/* 343 */     } else if (t.y < min) {
/* 344 */       this.y = min;
/*     */     } else {
/* 346 */       this.y = t.y;
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
/*     */   public final void clampMin(int min, Tuple2i t) {
/* 359 */     if (t.x < min) {
/* 360 */       this.x = min;
/*     */     } else {
/* 362 */       this.x = t.x;
/*     */     } 
/*     */     
/* 365 */     if (t.y < min) {
/* 366 */       this.y = min;
/*     */     } else {
/* 368 */       this.y = t.y;
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
/*     */   public final void clampMax(int max, Tuple2i t) {
/* 381 */     if (t.x > max) {
/* 382 */       this.x = max;
/*     */     } else {
/* 384 */       this.x = t.x;
/*     */     } 
/*     */     
/* 387 */     if (t.y > max) {
/* 388 */       this.y = max;
/*     */     } else {
/* 390 */       this.y = t.y;
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
/*     */   public final void absolute(Tuple2i t) {
/* 402 */     this.x = Math.abs(t.x);
/* 403 */     this.y = Math.abs(t.y);
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
/* 414 */     if (this.x > max) {
/* 415 */       this.x = max;
/* 416 */     } else if (this.x < min) {
/* 417 */       this.x = min;
/*     */     } 
/*     */     
/* 420 */     if (this.y > max) {
/* 421 */       this.y = max;
/* 422 */     } else if (this.y < min) {
/* 423 */       this.y = min;
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
/* 434 */     if (this.x < min) {
/* 435 */       this.x = min;
/*     */     }
/* 437 */     if (this.y < min) {
/* 438 */       this.y = min;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int max) {
/* 448 */     if (this.x > max) {
/* 449 */       this.x = max;
/*     */     }
/* 451 */     if (this.y > max) {
/* 452 */       this.y = max;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 460 */     this.x = Math.abs(this.x);
/* 461 */     this.y = Math.abs(this.y);
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
/*     */     try {
/* 475 */       return super.clone();
/* 476 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 478 */       throw new InternalError();
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
/* 490 */     return this.x;
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
/* 501 */     this.x = x;
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
/* 512 */     return this.y;
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
/* 523 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple2i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */