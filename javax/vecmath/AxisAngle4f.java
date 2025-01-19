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
/*     */ public class AxisAngle4f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -163246355858070601L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float angle;
/*     */   static final double EPS = 1.0E-6D;
/*     */   
/*     */   public AxisAngle4f(float x, float y, float z, float angle) {
/*  71 */     this.x = x;
/*  72 */     this.y = y;
/*  73 */     this.z = z;
/*  74 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(float[] a) {
/*  84 */     this.x = a[0];
/*  85 */     this.y = a[1];
/*  86 */     this.z = a[2];
/*  87 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(AxisAngle4f a1) {
/*  98 */     this.x = a1.x;
/*  99 */     this.y = a1.y;
/* 100 */     this.z = a1.z;
/* 101 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(AxisAngle4d a1) {
/* 111 */     this.x = (float)a1.x;
/* 112 */     this.y = (float)a1.y;
/* 113 */     this.z = (float)a1.z;
/* 114 */     this.angle = (float)a1.angle;
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
/*     */   public AxisAngle4f(Vector3f axis, float angle) {
/* 127 */     this.x = axis.x;
/* 128 */     this.y = axis.y;
/* 129 */     this.z = axis.z;
/* 130 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f() {
/* 138 */     this.x = 0.0F;
/* 139 */     this.y = 0.0F;
/* 140 */     this.z = 1.0F;
/* 141 */     this.angle = 0.0F;
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
/*     */   public final void set(float x, float y, float z, float angle) {
/* 154 */     this.x = x;
/* 155 */     this.y = y;
/* 156 */     this.z = z;
/* 157 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float[] a) {
/* 168 */     this.x = a[0];
/* 169 */     this.y = a[1];
/* 170 */     this.z = a[2];
/* 171 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4f a1) {
/* 181 */     this.x = a1.x;
/* 182 */     this.y = a1.y;
/* 183 */     this.z = a1.z;
/* 184 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4d a1) {
/* 194 */     this.x = (float)a1.x;
/* 195 */     this.y = (float)a1.y;
/* 196 */     this.z = (float)a1.z;
/* 197 */     this.angle = (float)a1.angle;
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
/*     */   public final void set(Vector3f axis, float angle) {
/* 210 */     this.x = axis.x;
/* 211 */     this.y = axis.y;
/* 212 */     this.z = axis.z;
/* 213 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] a) {
/* 223 */     a[0] = this.x;
/* 224 */     a[1] = this.y;
/* 225 */     a[2] = this.z;
/* 226 */     a[3] = this.angle;
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
/*     */   public final void set(Quat4f q1) {
/* 239 */     double mag = (q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/*     */     
/* 241 */     if (mag > 1.0E-6D) {
/* 242 */       mag = Math.sqrt(mag);
/* 243 */       double invMag = 1.0D / mag;
/*     */       
/* 245 */       this.x = (float)(q1.x * invMag);
/* 246 */       this.y = (float)(q1.y * invMag);
/* 247 */       this.z = (float)(q1.z * invMag);
/* 248 */       this.angle = (float)(2.0D * Math.atan2(mag, q1.w));
/*     */     } else {
/* 250 */       this.x = 0.0F;
/* 251 */       this.y = 1.0F;
/* 252 */       this.z = 0.0F;
/* 253 */       this.angle = 0.0F;
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
/*     */   public final void set(Quat4d q1) {
/* 267 */     double mag = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z;
/*     */     
/* 269 */     if (mag > 1.0E-6D) {
/* 270 */       mag = Math.sqrt(mag);
/* 271 */       double invMag = 1.0D / mag;
/*     */       
/* 273 */       this.x = (float)(q1.x * invMag);
/* 274 */       this.y = (float)(q1.y * invMag);
/* 275 */       this.z = (float)(q1.z * invMag);
/* 276 */       this.angle = (float)(2.0D * Math.atan2(mag, q1.w));
/*     */     } else {
/* 278 */       this.x = 0.0F;
/* 279 */       this.y = 1.0F;
/* 280 */       this.z = 0.0F;
/* 281 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix4f m1) {
/* 295 */     Matrix3f m3f = new Matrix3f();
/*     */     
/* 297 */     m1.get(m3f);
/*     */     
/* 299 */     this.x = m3f.m21 - m3f.m12;
/* 300 */     this.y = m3f.m02 - m3f.m20;
/* 301 */     this.z = m3f.m10 - m3f.m01;
/* 302 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 304 */     if (mag > 1.0E-6D) {
/* 305 */       mag = Math.sqrt(mag);
/* 306 */       double sin = 0.5D * mag;
/* 307 */       double cos = 0.5D * ((m3f.m00 + m3f.m11 + m3f.m22) - 1.0D);
/*     */       
/* 309 */       this.angle = (float)Math.atan2(sin, cos);
/* 310 */       double invMag = 1.0D / mag;
/* 311 */       this.x = (float)(this.x * invMag);
/* 312 */       this.y = (float)(this.y * invMag);
/* 313 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 315 */       this.x = 0.0F;
/* 316 */       this.y = 1.0F;
/* 317 */       this.z = 0.0F;
/* 318 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix4d m1) {
/* 334 */     Matrix3d m3d = new Matrix3d();
/*     */     
/* 336 */     m1.get(m3d);
/*     */ 
/*     */     
/* 339 */     this.x = (float)(m3d.m21 - m3d.m12);
/* 340 */     this.y = (float)(m3d.m02 - m3d.m20);
/* 341 */     this.z = (float)(m3d.m10 - m3d.m01);
/* 342 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 344 */     if (mag > 1.0E-6D) {
/* 345 */       mag = Math.sqrt(mag);
/* 346 */       double sin = 0.5D * mag;
/* 347 */       double cos = 0.5D * (m3d.m00 + m3d.m11 + m3d.m22 - 1.0D);
/* 348 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 350 */       double invMag = 1.0D / mag;
/* 351 */       this.x = (float)(this.x * invMag);
/* 352 */       this.y = (float)(this.y * invMag);
/* 353 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 355 */       this.x = 0.0F;
/* 356 */       this.y = 1.0F;
/* 357 */       this.z = 0.0F;
/* 358 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix3f m1) {
/* 373 */     this.x = m1.m21 - m1.m12;
/* 374 */     this.y = m1.m02 - m1.m20;
/* 375 */     this.z = m1.m10 - m1.m01;
/* 376 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/* 377 */     if (mag > 1.0E-6D) {
/* 378 */       mag = Math.sqrt(mag);
/* 379 */       double sin = 0.5D * mag;
/* 380 */       double cos = 0.5D * ((m1.m00 + m1.m11 + m1.m22) - 1.0D);
/*     */       
/* 382 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 384 */       double invMag = 1.0D / mag;
/* 385 */       this.x = (float)(this.x * invMag);
/* 386 */       this.y = (float)(this.y * invMag);
/* 387 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 389 */       this.x = 0.0F;
/* 390 */       this.y = 1.0F;
/* 391 */       this.z = 0.0F;
/* 392 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix3d m1) {
/* 408 */     this.x = (float)(m1.m21 - m1.m12);
/* 409 */     this.y = (float)(m1.m02 - m1.m20);
/* 410 */     this.z = (float)(m1.m10 - m1.m01);
/* 411 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 413 */     if (mag > 1.0E-6D) {
/* 414 */       mag = Math.sqrt(mag);
/* 415 */       double sin = 0.5D * mag;
/* 416 */       double cos = 0.5D * (m1.m00 + m1.m11 + m1.m22 - 1.0D);
/*     */       
/* 418 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 420 */       double invMag = 1.0D / mag;
/* 421 */       this.x = (float)(this.x * invMag);
/* 422 */       this.y = (float)(this.y * invMag);
/* 423 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 425 */       this.x = 0.0F;
/* 426 */       this.y = 1.0F;
/* 427 */       this.z = 0.0F;
/* 428 */       this.angle = 0.0F;
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
/*     */   public String toString() {
/* 441 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
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
/*     */   public boolean equals(AxisAngle4f a1) {
/*     */     try {
/* 454 */       return (this.x == a1.x && this.y == a1.y && this.z == a1.z && 
/* 455 */         this.angle == a1.angle);
/* 456 */     } catch (NullPointerException e2) {
/* 457 */       return false;
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
/*     */   public boolean equals(Object o1) {
/*     */     try {
/* 473 */       AxisAngle4f a2 = (AxisAngle4f)o1;
/* 474 */       return (this.x == a2.x && this.y == a2.y && this.z == a2.z && 
/* 475 */         this.angle == a2.angle);
/* 476 */     } catch (NullPointerException e2) {
/* 477 */       return false;
/* 478 */     } catch (ClassCastException e1) {
/* 479 */       return false;
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
/*     */   public boolean epsilonEquals(AxisAngle4f a1, float epsilon) {
/* 497 */     float diff = this.x - a1.x;
/* 498 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 500 */     diff = this.y - a1.y;
/* 501 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 503 */     diff = this.z - a1.z;
/* 504 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 506 */     diff = this.angle - a1.angle;
/* 507 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 509 */     return true;
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
/* 525 */     long bits = 1L;
/* 526 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 527 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 528 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 529 */     bits = VecMathUtil.hashFloatBits(bits, this.angle);
/* 530 */     return VecMathUtil.hashFinish(bits);
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
/* 545 */       return super.clone();
/* 546 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 548 */       throw new InternalError();
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
/*     */   public final float getAngle() {
/* 561 */     return this.angle;
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
/*     */   public final void setAngle(float angle) {
/* 573 */     this.angle = angle;
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
/* 584 */     return this.x;
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
/* 595 */     this.x = x;
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
/* 606 */     return this.y;
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
/* 617 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getZ() {
/* 628 */     return this.z;
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
/* 639 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\AxisAngle4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */