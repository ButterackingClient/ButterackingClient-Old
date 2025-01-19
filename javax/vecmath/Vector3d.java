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
/*     */ public class Vector3d
/*     */   extends Tuple3d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 3761969948420550442L;
/*     */   
/*     */   public Vector3d(double x, double y, double z) {
/*  48 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(double[] v) {
/*  58 */     super(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Vector3d v1) {
/*  68 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Vector3f v1) {
/*  78 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Tuple3f t1) {
/*  88 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3d(Tuple3d t1) {
/*  98 */     super(t1);
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
/*     */   public Vector3d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void cross(Vector3d v1, Vector3d v2) {
/* 119 */     double x = v1.y * v2.z - v1.z * v2.y;
/* 120 */     double y = v2.x * v1.z - v2.z * v1.x;
/* 121 */     this.z = v1.x * v2.y - v1.y * v2.x;
/* 122 */     this.x = x;
/* 123 */     this.y = y;
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
/*     */   public final void normalize(Vector3d v1) {
/* 135 */     double norm = 1.0D / Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
/* 136 */     v1.x *= norm;
/* 137 */     v1.y *= norm;
/* 138 */     v1.z *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 148 */     double norm = 1.0D / Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 149 */     this.x *= norm;
/* 150 */     this.y *= norm;
/* 151 */     this.z *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(Vector3d v1) {
/* 162 */     return this.x * v1.x + this.y * v1.y + this.z * v1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double lengthSquared() {
/* 172 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double length() {
/* 182 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
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
/*     */   public final double angle(Vector3d v1) {
/* 194 */     double vDot = dot(v1) / length() * v1.length();
/* 195 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 196 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 197 */     return Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Vector3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */