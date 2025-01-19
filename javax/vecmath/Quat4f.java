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
/*     */ public class Quat4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 2675933778405442383L;
/*     */   static final double EPS = 1.0E-6D;
/*     */   static final double EPS2 = 1.0E-30D;
/*     */   static final double PIO2 = 1.57079632679D;
/*     */   
/*     */   public Quat4f(float x, float y, float z, float w) {
/*  53 */     float mag = (float)(1.0D / Math.sqrt((x * x + y * y + z * z + w * w)));
/*  54 */     this.x = x * mag;
/*  55 */     this.y = y * mag;
/*  56 */     this.z = z * mag;
/*  57 */     this.w = w * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(float[] q) {
/*  68 */     float mag = (float)(1.0D / Math.sqrt((q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3])));
/*  69 */     this.x = q[0] * mag;
/*  70 */     this.y = q[1] * mag;
/*  71 */     this.z = q[2] * mag;
/*  72 */     this.w = q[3] * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Quat4f q1) {
/*  83 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Quat4d q1) {
/*  92 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Tuple4f t1) {
/* 103 */     float mag = (float)(1.0D / Math.sqrt((t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w)));
/* 104 */     this.x = t1.x * mag;
/* 105 */     this.y = t1.y * mag;
/* 106 */     this.z = t1.z * mag;
/* 107 */     this.w = t1.w * mag;
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
/*     */   public Quat4f(Tuple4d t1) {
/* 119 */     double mag = 1.0D / Math.sqrt(t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w);
/* 120 */     this.x = (float)(t1.x * mag);
/* 121 */     this.y = (float)(t1.y * mag);
/* 122 */     this.z = (float)(t1.z * mag);
/* 123 */     this.w = (float)(t1.w * mag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate(Quat4f q1) {
/* 141 */     this.x = -q1.x;
/* 142 */     this.y = -q1.y;
/* 143 */     this.z = -q1.z;
/* 144 */     this.w = q1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate() {
/* 151 */     this.x = -this.x;
/* 152 */     this.y = -this.y;
/* 153 */     this.z = -this.z;
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
/*     */   public final void mul(Quat4f q1, Quat4f q2) {
/* 166 */     if (this != q1 && this != q2) {
/* 167 */       this.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 168 */       this.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 169 */       this.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 170 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/*     */     }
/*     */     else {
/*     */       
/* 174 */       float w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 175 */       float x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 176 */       float y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 177 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/* 178 */       this.w = w;
/* 179 */       this.x = x;
/* 180 */       this.y = y;
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
/*     */   public final void mul(Quat4f q1) {
/* 194 */     float w = this.w * q1.w - this.x * q1.x - this.y * q1.y - this.z * q1.z;
/* 195 */     float x = this.w * q1.x + q1.w * this.x + this.y * q1.z - this.z * q1.y;
/* 196 */     float y = this.w * q1.y + q1.w * this.y - this.x * q1.z + this.z * q1.x;
/* 197 */     this.z = this.w * q1.z + q1.w * this.z + this.x * q1.y - this.y * q1.x;
/* 198 */     this.w = w;
/* 199 */     this.x = x;
/* 200 */     this.y = y;
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
/*     */   public final void mulInverse(Quat4f q1, Quat4f q2) {
/* 213 */     Quat4f tempQuat = new Quat4f(q2);
/*     */     
/* 215 */     tempQuat.inverse();
/* 216 */     mul(q1, tempQuat);
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
/*     */   public final void mulInverse(Quat4f q1) {
/* 228 */     Quat4f tempQuat = new Quat4f(q1);
/*     */     
/* 230 */     tempQuat.inverse();
/* 231 */     mul(tempQuat);
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
/*     */   public final void inverse(Quat4f q1) {
/* 243 */     float norm = 1.0F / (q1.w * q1.w + q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/* 244 */     this.w = norm * q1.w;
/* 245 */     this.x = -norm * q1.x;
/* 246 */     this.y = -norm * q1.y;
/* 247 */     this.z = -norm * q1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inverse() {
/* 257 */     float norm = 1.0F / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
/* 258 */     this.w *= norm;
/* 259 */     this.x *= -norm;
/* 260 */     this.y *= -norm;
/* 261 */     this.z *= -norm;
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
/*     */   public final void normalize(Quat4f q1) {
/* 274 */     float norm = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z + q1.w * q1.w;
/*     */     
/* 276 */     if (norm > 0.0F) {
/* 277 */       norm = 1.0F / (float)Math.sqrt(norm);
/* 278 */       this.x = norm * q1.x;
/* 279 */       this.y = norm * q1.y;
/* 280 */       this.z = norm * q1.z;
/* 281 */       this.w = norm * q1.w;
/*     */     } else {
/* 283 */       this.x = 0.0F;
/* 284 */       this.y = 0.0F;
/* 285 */       this.z = 0.0F;
/* 286 */       this.w = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 297 */     float norm = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 299 */     if (norm > 0.0F) {
/* 300 */       norm = 1.0F / (float)Math.sqrt(norm);
/* 301 */       this.x *= norm;
/* 302 */       this.y *= norm;
/* 303 */       this.z *= norm;
/* 304 */       this.w *= norm;
/*     */     } else {
/* 306 */       this.x = 0.0F;
/* 307 */       this.y = 0.0F;
/* 308 */       this.z = 0.0F;
/* 309 */       this.w = 0.0F;
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
/*     */   public final void set(Matrix4f m1) {
/* 321 */     float ww = 0.25F * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 323 */     if (ww >= 0.0F) {
/* 324 */       if (ww >= 1.0E-30D) {
/* 325 */         this.w = (float)Math.sqrt(ww);
/* 326 */         ww = 0.25F / this.w;
/* 327 */         this.x = (m1.m21 - m1.m12) * ww;
/* 328 */         this.y = (m1.m02 - m1.m20) * ww;
/* 329 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 333 */       this.w = 0.0F;
/* 334 */       this.x = 0.0F;
/* 335 */       this.y = 0.0F;
/* 336 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 340 */     this.w = 0.0F;
/* 341 */     ww = -0.5F * (m1.m11 + m1.m22);
/*     */     
/* 343 */     if (ww >= 0.0F) {
/* 344 */       if (ww >= 1.0E-30D) {
/* 345 */         this.x = (float)Math.sqrt(ww);
/* 346 */         ww = 1.0F / 2.0F * this.x;
/* 347 */         this.y = m1.m10 * ww;
/* 348 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 352 */       this.x = 0.0F;
/* 353 */       this.y = 0.0F;
/* 354 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 358 */     this.x = 0.0F;
/* 359 */     ww = 0.5F * (1.0F - m1.m22);
/*     */     
/* 361 */     if (ww >= 1.0E-30D) {
/* 362 */       this.y = (float)Math.sqrt(ww);
/* 363 */       this.z = m1.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 367 */     this.y = 0.0F;
/* 368 */     this.z = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix4d m1) {
/* 379 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 381 */     if (ww >= 0.0D) {
/* 382 */       if (ww >= 1.0E-30D) {
/* 383 */         this.w = (float)Math.sqrt(ww);
/* 384 */         ww = 0.25D / this.w;
/* 385 */         this.x = (float)((m1.m21 - m1.m12) * ww);
/* 386 */         this.y = (float)((m1.m02 - m1.m20) * ww);
/* 387 */         this.z = (float)((m1.m10 - m1.m01) * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 391 */       this.w = 0.0F;
/* 392 */       this.x = 0.0F;
/* 393 */       this.y = 0.0F;
/* 394 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 398 */     this.w = 0.0F;
/* 399 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 400 */     if (ww >= 0.0D) {
/* 401 */       if (ww >= 1.0E-30D) {
/* 402 */         this.x = (float)Math.sqrt(ww);
/* 403 */         ww = 0.5D / this.x;
/* 404 */         this.y = (float)(m1.m10 * ww);
/* 405 */         this.z = (float)(m1.m20 * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 409 */       this.x = 0.0F;
/* 410 */       this.y = 0.0F;
/* 411 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 415 */     this.x = 0.0F;
/* 416 */     ww = 0.5D * (1.0D - m1.m22);
/* 417 */     if (ww >= 1.0E-30D) {
/* 418 */       this.y = (float)Math.sqrt(ww);
/* 419 */       this.z = (float)(m1.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 423 */     this.y = 0.0F;
/* 424 */     this.z = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3f m1) {
/* 435 */     float ww = 0.25F * (m1.m00 + m1.m11 + m1.m22 + 1.0F);
/*     */     
/* 437 */     if (ww >= 0.0F) {
/* 438 */       if (ww >= 1.0E-30D) {
/* 439 */         this.w = (float)Math.sqrt(ww);
/* 440 */         ww = 0.25F / this.w;
/* 441 */         this.x = (m1.m21 - m1.m12) * ww;
/* 442 */         this.y = (m1.m02 - m1.m20) * ww;
/* 443 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 447 */       this.w = 0.0F;
/* 448 */       this.x = 0.0F;
/* 449 */       this.y = 0.0F;
/* 450 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 454 */     this.w = 0.0F;
/* 455 */     ww = -0.5F * (m1.m11 + m1.m22);
/* 456 */     if (ww >= 0.0F) {
/* 457 */       if (ww >= 1.0E-30D) {
/* 458 */         this.x = (float)Math.sqrt(ww);
/* 459 */         ww = 0.5F / this.x;
/* 460 */         this.y = m1.m10 * ww;
/* 461 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 465 */       this.x = 0.0F;
/* 466 */       this.y = 0.0F;
/* 467 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 471 */     this.x = 0.0F;
/* 472 */     ww = 0.5F * (1.0F - m1.m22);
/* 473 */     if (ww >= 1.0E-30D) {
/* 474 */       this.y = (float)Math.sqrt(ww);
/* 475 */       this.z = m1.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 479 */     this.y = 0.0F;
/* 480 */     this.z = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3d m1) {
/* 491 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + 1.0D);
/*     */     
/* 493 */     if (ww >= 0.0D) {
/* 494 */       if (ww >= 1.0E-30D) {
/* 495 */         this.w = (float)Math.sqrt(ww);
/* 496 */         ww = 0.25D / this.w;
/* 497 */         this.x = (float)((m1.m21 - m1.m12) * ww);
/* 498 */         this.y = (float)((m1.m02 - m1.m20) * ww);
/* 499 */         this.z = (float)((m1.m10 - m1.m01) * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 503 */       this.w = 0.0F;
/* 504 */       this.x = 0.0F;
/* 505 */       this.y = 0.0F;
/* 506 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 510 */     this.w = 0.0F;
/* 511 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 512 */     if (ww >= 0.0D) {
/* 513 */       if (ww >= 1.0E-30D) {
/* 514 */         this.x = (float)Math.sqrt(ww);
/* 515 */         ww = 0.5D / this.x;
/* 516 */         this.y = (float)(m1.m10 * ww);
/* 517 */         this.z = (float)(m1.m20 * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 521 */       this.x = 0.0F;
/* 522 */       this.y = 0.0F;
/* 523 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 527 */     this.x = 0.0F;
/* 528 */     ww = 0.5D * (1.0D - m1.m22);
/* 529 */     if (ww >= 1.0E-30D) {
/* 530 */       this.y = (float)Math.sqrt(ww);
/* 531 */       this.z = (float)(m1.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 535 */     this.y = 0.0F;
/* 536 */     this.z = 1.0F;
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
/*     */   public final void set(AxisAngle4f a) {
/* 549 */     float amag = (float)Math.sqrt((a.x * a.x + a.y * a.y + a.z * a.z));
/* 550 */     if (amag < 1.0E-6D) {
/* 551 */       this.w = 0.0F;
/* 552 */       this.x = 0.0F;
/* 553 */       this.y = 0.0F;
/* 554 */       this.z = 0.0F;
/*     */     } else {
/* 556 */       amag = 1.0F / amag;
/* 557 */       float mag = (float)Math.sin(a.angle / 2.0D);
/* 558 */       this.w = (float)Math.cos(a.angle / 2.0D);
/* 559 */       this.x = a.x * amag * mag;
/* 560 */       this.y = a.y * amag * mag;
/* 561 */       this.z = a.z * amag * mag;
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
/*     */   public final void set(AxisAngle4d a) {
/* 576 */     float amag = (float)(1.0D / Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z));
/*     */     
/* 578 */     if (amag < 1.0E-6D) {
/* 579 */       this.w = 0.0F;
/* 580 */       this.x = 0.0F;
/* 581 */       this.y = 0.0F;
/* 582 */       this.z = 0.0F;
/*     */     } else {
/* 584 */       amag = 1.0F / amag;
/* 585 */       float mag = (float)Math.sin(a.angle / 2.0D);
/* 586 */       this.w = (float)Math.cos(a.angle / 2.0D);
/* 587 */       this.x = (float)a.x * amag * mag;
/* 588 */       this.y = (float)a.y * amag * mag;
/* 589 */       this.z = (float)a.z * amag * mag;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4f q1, float alpha) {
/* 613 */     double s1, s2, dot = (this.x * q1.x + this.y * q1.y + this.z * q1.z + this.w * q1.w);
/*     */     
/* 615 */     if (dot < 0.0D) {
/*     */       
/* 617 */       q1.x = -q1.x;
/* 618 */       q1.y = -q1.y;
/* 619 */       q1.z = -q1.z;
/* 620 */       q1.w = -q1.w;
/* 621 */       dot = -dot;
/*     */     } 
/*     */     
/* 624 */     if (1.0D - dot > 1.0E-6D) {
/* 625 */       double om = Math.acos(dot);
/* 626 */       double sinom = Math.sin(om);
/* 627 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 628 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 630 */       s1 = 1.0D - alpha;
/* 631 */       s2 = alpha;
/*     */     } 
/*     */     
/* 634 */     this.w = (float)(s1 * this.w + s2 * q1.w);
/* 635 */     this.x = (float)(s1 * this.x + s2 * q1.x);
/* 636 */     this.y = (float)(s1 * this.y + s2 * q1.y);
/* 637 */     this.z = (float)(s1 * this.z + s2 * q1.z);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4f q1, Quat4f q2, float alpha) {
/* 659 */     double s1, s2, dot = (q2.x * q1.x + q2.y * q1.y + q2.z * q1.z + q2.w * q1.w);
/*     */     
/* 661 */     if (dot < 0.0D) {
/*     */       
/* 663 */       q1.x = -q1.x;
/* 664 */       q1.y = -q1.y;
/* 665 */       q1.z = -q1.z;
/* 666 */       q1.w = -q1.w;
/* 667 */       dot = -dot;
/*     */     } 
/*     */     
/* 670 */     if (1.0D - dot > 1.0E-6D) {
/* 671 */       double om = Math.acos(dot);
/* 672 */       double sinom = Math.sin(om);
/* 673 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 674 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 676 */       s1 = 1.0D - alpha;
/* 677 */       s2 = alpha;
/*     */     } 
/* 679 */     this.w = (float)(s1 * q1.w + s2 * q2.w);
/* 680 */     this.x = (float)(s1 * q1.x + s2 * q2.x);
/* 681 */     this.y = (float)(s1 * q1.y + s2 * q2.y);
/* 682 */     this.z = (float)(s1 * q1.z + s2 * q2.z);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Quat4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */