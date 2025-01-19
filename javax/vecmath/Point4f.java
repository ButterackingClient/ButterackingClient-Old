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
/*     */ public class Point4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 4643134103185764459L;
/*     */   
/*     */   public Point4f(float x, float y, float z, float w) {
/*  49 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(float[] p) {
/*  59 */     super(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Point4f p1) {
/*  69 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Point4d p1) {
/*  79 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Tuple4f t1) {
/*  89 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4f(Tuple4d t1) {
/*  99 */     super(t1);
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
/*     */   public Point4f(Tuple3f t1) {
/* 113 */     super(t1.x, t1.y, t1.z, 1.0F);
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
/*     */   public Point4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 134 */     this.x = t1.x;
/* 135 */     this.y = t1.y;
/* 136 */     this.z = t1.z;
/* 137 */     this.w = 1.0F;
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
/*     */   public final float distanceSquared(Point4f p1) {
/* 150 */     float dx = this.x - p1.x;
/* 151 */     float dy = this.y - p1.y;
/* 152 */     float dz = this.z - p1.z;
/* 153 */     float dw = this.w - p1.w;
/* 154 */     return dx * dx + dy * dy + dz * dz + dw * dw;
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
/*     */   public final float distance(Point4f p1) {
/* 167 */     float dx = this.x - p1.x;
/* 168 */     float dy = this.y - p1.y;
/* 169 */     float dz = this.z - p1.z;
/* 170 */     float dw = this.w - p1.w;
/* 171 */     return (float)Math.sqrt((dx * dx + dy * dy + dz * dz + dw * dw));
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
/*     */   public final float distanceL1(Point4f p1) {
/* 184 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + Math.abs(this.z - p1.z) + Math.abs(this.w - p1.w);
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
/*     */   public final float distanceLinf(Point4f p1) {
/* 198 */     float t1 = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/* 199 */     float t2 = Math.max(Math.abs(this.z - p1.z), Math.abs(this.w - p1.w));
/*     */     
/* 201 */     return Math.max(t1, t2);
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
/*     */   public final void project(Point4f p1) {
/* 215 */     float oneOw = 1.0F / p1.w;
/* 216 */     p1.x *= oneOw;
/* 217 */     p1.y *= oneOw;
/* 218 */     p1.z *= oneOw;
/* 219 */     this.w = 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Point4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */