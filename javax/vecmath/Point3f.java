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
/*     */ public class Point3f
/*     */   extends Tuple3f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -8689337816398030143L;
/*     */   
/*     */   public Point3f(float x, float y, float z) {
/*  48 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(float[] p) {
/*  58 */     super(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Point3f p1) {
/*  68 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Point3d p1) {
/*  78 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Tuple3f t1) {
/*  88 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3f(Tuple3d t1) {
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
/*     */   public Point3f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceSquared(Point3f p1) {
/* 120 */     float dx = this.x - p1.x;
/* 121 */     float dy = this.y - p1.y;
/* 122 */     float dz = this.z - p1.z;
/* 123 */     return dx * dx + dy * dy + dz * dz;
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
/*     */   public final float distance(Point3f p1) {
/* 136 */     float dx = this.x - p1.x;
/* 137 */     float dy = this.y - p1.y;
/* 138 */     float dz = this.z - p1.z;
/* 139 */     return (float)Math.sqrt((dx * dx + dy * dy + dz * dz));
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
/*     */   public final float distanceL1(Point3f p1) {
/* 152 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + Math.abs(this.z - p1.z);
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
/*     */   public final float distanceLinf(Point3f p1) {
/* 166 */     float tmp = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/* 167 */     return Math.max(tmp, Math.abs(this.z - p1.z));
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
/* 181 */     float oneOw = 1.0F / p1.w;
/* 182 */     this.x = p1.x * oneOw;
/* 183 */     this.y = p1.y * oneOw;
/* 184 */     this.z = p1.z * oneOw;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Point3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */