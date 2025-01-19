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
/*     */ 
/*     */ public class AxisAngle4d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 3644296204459140589L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double angle;
/*     */   static final double EPS = 1.0E-12D;
/*     */   
/*     */   public AxisAngle4d(double x, double y, double z, double angle) {
/*  73 */     this.x = x;
/*  74 */     this.y = y;
/*  75 */     this.z = z;
/*  76 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(double[] a) {
/*  87 */     this.x = a[0];
/*  88 */     this.y = a[1];
/*  89 */     this.z = a[2];
/*  90 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(AxisAngle4d a1) {
/*  99 */     this.x = a1.x;
/* 100 */     this.y = a1.y;
/* 101 */     this.z = a1.z;
/* 102 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(AxisAngle4f a1) {
/* 113 */     this.x = a1.x;
/* 114 */     this.y = a1.y;
/* 115 */     this.z = a1.z;
/* 116 */     this.angle = a1.angle;
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
/*     */   public AxisAngle4d(Vector3d axis, double angle) {
/* 129 */     this.x = axis.x;
/* 130 */     this.y = axis.y;
/* 131 */     this.z = axis.z;
/* 132 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d() {
/* 140 */     this.x = 0.0D;
/* 141 */     this.y = 0.0D;
/* 142 */     this.z = 1.0D;
/* 143 */     this.angle = 0.0D;
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
/*     */   public final void set(double x, double y, double z, double angle) {
/* 156 */     this.x = x;
/* 157 */     this.y = y;
/* 158 */     this.z = z;
/* 159 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] a) {
/* 169 */     this.x = a[0];
/* 170 */     this.y = a[1];
/* 171 */     this.z = a[2];
/* 172 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4d a1) {
/* 182 */     this.x = a1.x;
/* 183 */     this.y = a1.y;
/* 184 */     this.z = a1.z;
/* 185 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4f a1) {
/* 195 */     this.x = a1.x;
/* 196 */     this.y = a1.y;
/* 197 */     this.z = a1.z;
/* 198 */     this.angle = a1.angle;
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
/*     */   public final void set(Vector3d axis, double angle) {
/* 211 */     this.x = axis.x;
/* 212 */     this.y = axis.y;
/* 213 */     this.z = axis.z;
/* 214 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] a) {
/* 225 */     a[0] = this.x;
/* 226 */     a[1] = this.y;
/* 227 */     a[2] = this.z;
/* 228 */     a[3] = this.angle;
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
/* 241 */     Matrix3d m3d = new Matrix3d();
/*     */     
/* 243 */     m1.get(m3d);
/*     */     
/* 245 */     this.x = (float)(m3d.m21 - m3d.m12);
/* 246 */     this.y = (float)(m3d.m02 - m3d.m20);
/* 247 */     this.z = (float)(m3d.m10 - m3d.m01);
/* 248 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 250 */     if (mag > 1.0E-12D) {
/* 251 */       mag = Math.sqrt(mag);
/* 252 */       double sin = 0.5D * mag;
/* 253 */       double cos = 0.5D * (m3d.m00 + m3d.m11 + m3d.m22 - 1.0D);
/*     */       
/* 255 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 257 */       double invMag = 1.0D / mag;
/* 258 */       this.x *= invMag;
/* 259 */       this.y *= invMag;
/* 260 */       this.z *= invMag;
/*     */     } else {
/* 262 */       this.x = 0.0D;
/* 263 */       this.y = 1.0D;
/* 264 */       this.z = 0.0D;
/* 265 */       this.angle = 0.0D;
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
/*     */   public final void set(Matrix4d m1) {
/* 279 */     Matrix3d m3d = new Matrix3d();
/*     */     
/* 281 */     m1.get(m3d);
/*     */     
/* 283 */     this.x = (float)(m3d.m21 - m3d.m12);
/* 284 */     this.y = (float)(m3d.m02 - m3d.m20);
/* 285 */     this.z = (float)(m3d.m10 - m3d.m01);
/*     */     
/* 287 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 289 */     if (mag > 1.0E-12D) {
/* 290 */       mag = Math.sqrt(mag);
/*     */       
/* 292 */       double sin = 0.5D * mag;
/* 293 */       double cos = 0.5D * (m3d.m00 + m3d.m11 + m3d.m22 - 1.0D);
/* 294 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 296 */       double invMag = 1.0D / mag;
/* 297 */       this.x *= invMag;
/* 298 */       this.y *= invMag;
/* 299 */       this.z *= invMag;
/*     */     } else {
/* 301 */       this.x = 0.0D;
/* 302 */       this.y = 1.0D;
/* 303 */       this.z = 0.0D;
/* 304 */       this.angle = 0.0D;
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
/*     */   public final void set(Matrix3f m1) {
/* 318 */     this.x = (m1.m21 - m1.m12);
/* 319 */     this.y = (m1.m02 - m1.m20);
/* 320 */     this.z = (m1.m10 - m1.m01);
/* 321 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 323 */     if (mag > 1.0E-12D) {
/* 324 */       mag = Math.sqrt(mag);
/*     */       
/* 326 */       double sin = 0.5D * mag;
/* 327 */       double cos = 0.5D * ((m1.m00 + m1.m11 + m1.m22) - 1.0D);
/* 328 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 330 */       double invMag = 1.0D / mag;
/* 331 */       this.x *= invMag;
/* 332 */       this.y *= invMag;
/* 333 */       this.z *= invMag;
/*     */     } else {
/* 335 */       this.x = 0.0D;
/* 336 */       this.y = 1.0D;
/* 337 */       this.z = 0.0D;
/* 338 */       this.angle = 0.0D;
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
/*     */   public final void set(Matrix3d m1) {
/* 352 */     this.x = (float)(m1.m21 - m1.m12);
/* 353 */     this.y = (float)(m1.m02 - m1.m20);
/* 354 */     this.z = (float)(m1.m10 - m1.m01);
/*     */     
/* 356 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 358 */     if (mag > 1.0E-12D) {
/* 359 */       mag = Math.sqrt(mag);
/*     */       
/* 361 */       double sin = 0.5D * mag;
/* 362 */       double cos = 0.5D * (m1.m00 + m1.m11 + m1.m22 - 1.0D);
/*     */       
/* 364 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 366 */       double invMag = 1.0D / mag;
/* 367 */       this.x *= invMag;
/* 368 */       this.y *= invMag;
/* 369 */       this.z *= invMag;
/*     */     } else {
/* 371 */       this.x = 0.0D;
/* 372 */       this.y = 1.0D;
/* 373 */       this.z = 0.0D;
/* 374 */       this.angle = 0.0D;
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
/*     */   public final void set(Quat4f q1) {
/* 389 */     double mag = (q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/*     */     
/* 391 */     if (mag > 1.0E-12D) {
/* 392 */       mag = Math.sqrt(mag);
/* 393 */       double invMag = 1.0D / mag;
/*     */       
/* 395 */       this.x = q1.x * invMag;
/* 396 */       this.y = q1.y * invMag;
/* 397 */       this.z = q1.z * invMag;
/* 398 */       this.angle = 2.0D * Math.atan2(mag, q1.w);
/*     */     } else {
/* 400 */       this.x = 0.0D;
/* 401 */       this.y = 1.0D;
/* 402 */       this.z = 0.0D;
/* 403 */       this.angle = 0.0D;
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
/* 417 */     double mag = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z;
/*     */     
/* 419 */     if (mag > 1.0E-12D) {
/* 420 */       mag = Math.sqrt(mag);
/* 421 */       double invMag = 1.0D / mag;
/*     */       
/* 423 */       this.x = q1.x * invMag;
/* 424 */       this.y = q1.y * invMag;
/* 425 */       this.z = q1.z * invMag;
/* 426 */       this.angle = 2.0D * Math.atan2(mag, q1.w);
/*     */     } else {
/* 428 */       this.x = 0.0D;
/* 429 */       this.y = 1.0D;
/* 430 */       this.z = 0.0D;
/* 431 */       this.angle = 0.0D;
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
/* 444 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
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
/*     */   public boolean equals(AxisAngle4d a1) {
/*     */     try {
/* 457 */       return (this.x == a1.x && this.y == a1.y && this.z == a1.z && 
/* 458 */         this.angle == a1.angle);
/* 459 */     } catch (NullPointerException e2) {
/* 460 */       return false;
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
/* 476 */       AxisAngle4d a2 = (AxisAngle4d)o1;
/* 477 */       return (this.x == a2.x && this.y == a2.y && this.z == a2.z && 
/* 478 */         this.angle == a2.angle);
/* 479 */     } catch (NullPointerException e2) {
/* 480 */       return false;
/* 481 */     } catch (ClassCastException e1) {
/* 482 */       return false;
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
/*     */   public boolean epsilonEquals(AxisAngle4d a1, double epsilon) {
/* 501 */     double diff = this.x - a1.x;
/* 502 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 504 */     diff = this.y - a1.y;
/* 505 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 507 */     diff = this.z - a1.z;
/* 508 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 510 */     diff = this.angle - a1.angle;
/* 511 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 513 */     return true;
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
/* 528 */     long bits = 1L;
/* 529 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 530 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 531 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 532 */     bits = VecMathUtil.hashDoubleBits(bits, this.angle);
/* 533 */     return VecMathUtil.hashFinish(bits);
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
/* 548 */       return super.clone();
/* 549 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 551 */       throw new InternalError();
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
/*     */   public final double getAngle() {
/* 564 */     return this.angle;
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
/*     */   public final void setAngle(double angle) {
/* 576 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 587 */     return this.x;
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
/* 598 */     this.x = x;
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
/* 609 */     return this.y;
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
/* 620 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 631 */     return this.z;
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
/* 642 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\AxisAngle4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */