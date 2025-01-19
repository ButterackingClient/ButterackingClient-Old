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
/*     */ public class Vector3f
/*     */   extends Tuple3f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -7031930069184524614L;
/*     */   
/*     */   public Vector3f(float x, float y, float z) {
/*  48 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(float[] v) {
/*  58 */     super(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Vector3f v1) {
/*  68 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Vector3d v1) {
/*  78 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Tuple3f t1) {
/*  88 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f(Tuple3d t1) {
/*  98 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float lengthSquared() {
/* 116 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float length() {
/* 125 */     return 
/* 126 */       (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
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
/*     */   public final void cross(Vector3f v1, Vector3f v2) {
/* 139 */     float x = v1.y * v2.z - v1.z * v2.y;
/* 140 */     float y = v2.x * v1.z - v2.z * v1.x;
/* 141 */     this.z = v1.x * v2.y - v1.y * v2.x;
/* 142 */     this.x = x;
/* 143 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float dot(Vector3f v1) {
/* 153 */     return this.x * v1.x + this.y * v1.y + this.z * v1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(Vector3f v1) {
/* 164 */     float norm = (float)(1.0D / Math.sqrt((v1.x * v1.x + v1.y * v1.y + v1.z * v1.z)));
/* 165 */     v1.x *= norm;
/* 166 */     v1.y *= norm;
/* 167 */     v1.z *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 176 */     float norm = 
/* 177 */       (float)(1.0D / Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z)));
/* 178 */     this.x *= norm;
/* 179 */     this.y *= norm;
/* 180 */     this.z *= norm;
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
/*     */   public final float angle(Vector3f v1) {
/* 192 */     double vDot = (dot(v1) / length() * v1.length());
/* 193 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 194 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 195 */     return (float)Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Vector3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */