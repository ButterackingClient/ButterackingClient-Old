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
/*     */ public class Vector2d
/*     */   extends Tuple2d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 8572646365302599857L;
/*     */   
/*     */   public Vector2d(double x, double y) {
/*  46 */     super(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(double[] v) {
/*  56 */     super(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Vector2d v1) {
/*  66 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Vector2f v1) {
/*  76 */     super(v1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Tuple2d t1) {
/*  86 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d(Tuple2f t1) {
/*  96 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(Vector2d v1) {
/* 114 */     return this.x * v1.x + this.y * v1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double length() {
/* 124 */     return Math.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double lengthSquared() {
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
/*     */   public final void normalize(Vector2d v1) {
/* 144 */     double norm = 1.0D / Math.sqrt(v1.x * v1.x + v1.y * v1.y);
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
/* 155 */     double norm = 
/* 156 */       1.0D / Math.sqrt(this.x * this.x + this.y * this.y);
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
/*     */   public final double angle(Vector2d v1) {
/* 170 */     double vDot = dot(v1) / length() * v1.length();
/* 171 */     if (vDot < -1.0D) vDot = -1.0D; 
/* 172 */     if (vDot > 1.0D) vDot = 1.0D; 
/* 173 */     return Math.acos(vDot);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Vector2d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */