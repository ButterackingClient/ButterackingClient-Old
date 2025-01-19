/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Path
/*     */ {
/*   7 */   private PathPoint[] pathPoints = new PathPoint[1024];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int count;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint addPoint(PathPoint point) {
/*  18 */     if (point.index >= 0) {
/*  19 */       throw new IllegalStateException("OW KNOWS!");
/*     */     }
/*  21 */     if (this.count == this.pathPoints.length) {
/*  22 */       PathPoint[] apathpoint = new PathPoint[this.count << 1];
/*  23 */       System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
/*  24 */       this.pathPoints = apathpoint;
/*     */     } 
/*     */     
/*  27 */     this.pathPoints[this.count] = point;
/*  28 */     point.index = this.count;
/*  29 */     sortBack(this.count++);
/*  30 */     return point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPath() {
/*  38 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint dequeue() {
/*  45 */     PathPoint pathpoint = this.pathPoints[0];
/*  46 */     this.pathPoints[0] = this.pathPoints[--this.count];
/*  47 */     this.pathPoints[this.count] = null;
/*     */     
/*  49 */     if (this.count > 0) {
/*  50 */       sortForward(0);
/*     */     }
/*     */     
/*  53 */     pathpoint.index = -1;
/*  54 */     return pathpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeDistance(PathPoint p_75850_1_, float p_75850_2_) {
/*  61 */     float f = p_75850_1_.distanceToTarget;
/*  62 */     p_75850_1_.distanceToTarget = p_75850_2_;
/*     */     
/*  64 */     if (p_75850_2_ < f) {
/*  65 */       sortBack(p_75850_1_.index);
/*     */     } else {
/*  67 */       sortForward(p_75850_1_.index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortBack(int p_75847_1_) {
/*  75 */     PathPoint pathpoint = this.pathPoints[p_75847_1_];
/*     */ 
/*     */     
/*  78 */     for (float f = pathpoint.distanceToTarget; p_75847_1_ > 0; p_75847_1_ = i) {
/*  79 */       int i = p_75847_1_ - 1 >> 1;
/*  80 */       PathPoint pathpoint1 = this.pathPoints[i];
/*     */       
/*  82 */       if (f >= pathpoint1.distanceToTarget) {
/*     */         break;
/*     */       }
/*     */       
/*  86 */       this.pathPoints[p_75847_1_] = pathpoint1;
/*  87 */       pathpoint1.index = p_75847_1_;
/*     */     } 
/*     */     
/*  90 */     this.pathPoints[p_75847_1_] = pathpoint;
/*  91 */     pathpoint.index = p_75847_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortForward(int p_75846_1_) {
/*  98 */     PathPoint pathpoint = this.pathPoints[p_75846_1_];
/*  99 */     float f = pathpoint.distanceToTarget; while (true) {
/*     */       PathPoint pathpoint2;
/*     */       float f2;
/* 102 */       int i = 1 + (p_75846_1_ << 1);
/* 103 */       int j = i + 1;
/*     */       
/* 105 */       if (i >= this.count) {
/*     */         break;
/*     */       }
/*     */       
/* 109 */       PathPoint pathpoint1 = this.pathPoints[i];
/* 110 */       float f1 = pathpoint1.distanceToTarget;
/*     */ 
/*     */ 
/*     */       
/* 114 */       if (j >= this.count) {
/* 115 */         pathpoint2 = null;
/* 116 */         f2 = Float.POSITIVE_INFINITY;
/*     */       } else {
/* 118 */         pathpoint2 = this.pathPoints[j];
/* 119 */         f2 = pathpoint2.distanceToTarget;
/*     */       } 
/*     */       
/* 122 */       if (f1 < f2) {
/* 123 */         if (f1 >= f) {
/*     */           break;
/*     */         }
/*     */         
/* 127 */         this.pathPoints[p_75846_1_] = pathpoint1;
/* 128 */         pathpoint1.index = p_75846_1_;
/* 129 */         p_75846_1_ = i; continue;
/*     */       } 
/* 131 */       if (f2 >= f) {
/*     */         break;
/*     */       }
/*     */       
/* 135 */       this.pathPoints[p_75846_1_] = pathpoint2;
/* 136 */       pathpoint2.index = p_75846_1_;
/* 137 */       p_75846_1_ = j;
/*     */     } 
/*     */ 
/*     */     
/* 141 */     this.pathPoints[p_75846_1_] = pathpoint;
/* 142 */     pathpoint.index = p_75846_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPathEmpty() {
/* 149 */     return (this.count == 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\pathfinding\Path.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */