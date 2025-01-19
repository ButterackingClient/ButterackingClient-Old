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
/*     */ public class Vector2f
/*     */   extends Tuple2f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -2168194326883512320L;
/*     */   
/*     */   public Vector2f(float x, float y) {
/*  46 */     super(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(float[] v) {
/*  56 */     super(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Vector2f v1) {
/*  66 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Vector2d v1) {
/*  76 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Tuple2f t1) {
/*  86 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f(Tuple2d t1) {
/*  96 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float dot(Vector2f v1) {
/* 114 */     return this.x * v1.x + this.y * v1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float length() {
/* 124 */     return (float)Math.sqrt((this.x * this.x + this.y * this.y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float lengthSquared() {
/* 133 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(Vector2f v1) {
/* 144 */     float norm = (float)(1.0D / Math.sqrt((v1.x * v1.x + v1.y * v1.y)));
/* 145 */     v1.x *= norm;
/* 146 */     v1.y *= norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 155 */     float norm = 
/* 156 */       (float)(1.0D / Math.sqrt((this.x * this.x + this.y * this.y)));
/* 157 */     this.x *= norm;
/* 158 */     this.y *= norm;
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
/*     */   public final float angle(Vector2f v1) {
/* 170 */     double vDot = (dot(v1) / length() * v1.length());
/* 171 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 172 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 173 */     return (float)Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Vector2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */