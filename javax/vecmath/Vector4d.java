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
/*     */ public class Vector4d
/*     */   extends Tuple4d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3938123424117448700L;
/*     */   
/*     */   public Vector4d(double x, double y, double z, double w) {
/*  48 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(double[] v) {
/*  58 */     super(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Vector4d v1) {
/*  67 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Vector4f v1) {
/*  76 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Tuple4f t1) {
/*  85 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4d(Tuple4d t1) {
/*  94 */     super(t1);
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
/*     */   public Vector4d(Tuple3d t1) {
/* 108 */     super(t1.x, t1.y, t1.z, 0.0D);
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
/*     */   public Vector4d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 129 */     this.x = t1.x;
/* 130 */     this.y = t1.y;
/* 131 */     this.z = t1.z;
/* 132 */     this.w = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double length() {
/* 142 */     return Math.sqrt(this.x * this.x + this.y * this.y + 
/* 143 */         this.z * this.z + this.w * this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double lengthSquared() {
/* 153 */     return this.x * this.x + this.y * this.y + 
/* 154 */       this.z * this.z + this.w * this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(Vector4d v1) {
/* 165 */     return this.x * v1.x + this.y * v1.y + this.z * v1.z + this.w * v1.w;
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
/*     */   public final void normalize(Vector4d v1) {
/* 177 */     double norm = 1.0D / Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z + v1.w * v1.w);
/* 178 */     v1.x *= norm;
/* 179 */     v1.y *= norm;
/* 180 */     v1.z *= norm;
/* 181 */     v1.w *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 191 */     double norm = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y + 
/* 192 */         this.z * this.z + this.w * this.w);
/* 193 */     this.x *= norm;
/* 194 */     this.y *= norm;
/* 195 */     this.z *= norm;
/* 196 */     this.w *= norm;
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
/*     */   public final double angle(Vector4d v1) {
/* 209 */     double vDot = dot(v1) / length() * v1.length();
/* 210 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 211 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 212 */     return Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Vector4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */