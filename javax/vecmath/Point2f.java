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
/*     */ public class Point2f
/*     */   extends Tuple2f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -4801347926528714435L;
/*     */   
/*     */   public Point2f(float x, float y) {
/*  46 */     super(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(float[] p) {
/*  56 */     super(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Point2f p1) {
/*  66 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Point2d p1) {
/*  75 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Tuple2d t1) {
/*  85 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f(Tuple2f t1) {
/*  95 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceSquared(Point2f p1) {
/* 114 */     float dx = this.x - p1.x;
/* 115 */     float dy = this.y - p1.y;
/* 116 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distance(Point2f p1) {
/* 127 */     float dx = this.x - p1.x;
/* 128 */     float dy = this.y - p1.y;
/* 129 */     return (float)Math.sqrt((dx * dx + dy * dy));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceL1(Point2f p1) {
/* 140 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float distanceLinf(Point2f p1) {
/* 151 */     return Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Point2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */