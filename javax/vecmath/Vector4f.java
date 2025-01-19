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
/*     */ public class Vector4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 8749319902347760659L;
/*     */   
/*     */   public Vector4f(float x, float y, float z, float w) {
/*  48 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(float[] v) {
/*  58 */     super(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Vector4f v1) {
/*  68 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Vector4d v1) {
/*  78 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Tuple4f t1) {
/*  88 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4f(Tuple4d t1) {
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
/*     */ 
/*     */   
/*     */   public Vector4f(Tuple3f t1) {
/* 112 */     super(t1.x, t1.y, t1.z, 0.0F);
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
/*     */   public Vector4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 133 */     this.x = t1.x;
/* 134 */     this.y = t1.y;
/* 135 */     this.z = t1.z;
/* 136 */     this.w = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float length() {
/* 146 */     return 
/* 147 */       (float)Math.sqrt((this.x * this.x + this.y * this.y + 
/* 148 */         this.z * this.z + this.w * this.w));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float lengthSquared() {
/* 157 */     return this.x * this.x + this.y * this.y + 
/* 158 */       this.z * this.z + this.w * this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float dot(Vector4f v1) {
/* 168 */     return this.x * v1.x + this.y * v1.y + this.z * v1.z + this.w * v1.w;
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
/*     */   public final void normalize(Vector4f v1) {
/* 180 */     float norm = (float)(1.0D / Math.sqrt((v1.x * v1.x + v1.y * v1.y + 
/* 181 */         v1.z * v1.z + v1.w * v1.w)));
/* 182 */     v1.x *= norm;
/* 183 */     v1.y *= norm;
/* 184 */     v1.z *= norm;
/* 185 */     v1.w *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 195 */     float norm = (float)(1.0D / Math.sqrt((this.x * this.x + this.y * this.y + 
/* 196 */         this.z * this.z + this.w * this.w)));
/* 197 */     this.x *= norm;
/* 198 */     this.y *= norm;
/* 199 */     this.z *= norm;
/* 200 */     this.w *= norm;
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
/*     */   public final float angle(Vector4f v1) {
/* 213 */     double vDot = (dot(v1) / length() * v1.length());
/* 214 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 215 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 216 */     return (float)Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Vector4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */