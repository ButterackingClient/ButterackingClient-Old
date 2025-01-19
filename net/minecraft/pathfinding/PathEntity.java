/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathEntity
/*     */ {
/*     */   private final PathPoint[] points;
/*     */   private int currentPathIndex;
/*     */   private int pathLength;
/*     */   
/*     */   public PathEntity(PathPoint[] pathpoints) {
/*  23 */     this.points = pathpoints;
/*  24 */     this.pathLength = pathpoints.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementPathIndex() {
/*  31 */     this.currentPathIndex++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/*  38 */     return (this.currentPathIndex >= this.pathLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getFinalPathPoint() {
/*  45 */     return (this.pathLength > 0) ? this.points[this.pathLength - 1] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointFromIndex(int index) {
/*  52 */     return this.points[index];
/*     */   }
/*     */   
/*     */   public int getCurrentPathLength() {
/*  56 */     return this.pathLength;
/*     */   }
/*     */   
/*     */   public void setCurrentPathLength(int length) {
/*  60 */     this.pathLength = length;
/*     */   }
/*     */   
/*     */   public int getCurrentPathIndex() {
/*  64 */     return this.currentPathIndex;
/*     */   }
/*     */   
/*     */   public void setCurrentPathIndex(int currentPathIndexIn) {
/*  68 */     this.currentPathIndex = currentPathIndexIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getVectorFromIndex(Entity entityIn, int index) {
/*  75 */     double d0 = (this.points[index]).xCoord + (int)(entityIn.width + 1.0F) * 0.5D;
/*  76 */     double d1 = (this.points[index]).yCoord;
/*  77 */     double d2 = (this.points[index]).zCoord + (int)(entityIn.width + 1.0F) * 0.5D;
/*  78 */     return new Vec3(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getPosition(Entity entityIn) {
/*  85 */     return getVectorFromIndex(entityIn, this.currentPathIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSamePath(PathEntity pathentityIn) {
/*  92 */     if (pathentityIn == null)
/*  93 */       return false; 
/*  94 */     if (pathentityIn.points.length != this.points.length) {
/*  95 */       return false;
/*     */     }
/*  97 */     for (int i = 0; i < this.points.length; i++) {
/*  98 */       if ((this.points[i]).xCoord != (pathentityIn.points[i]).xCoord || (this.points[i]).yCoord != (pathentityIn.points[i]).yCoord || (this.points[i]).zCoord != (pathentityIn.points[i]).zCoord) {
/*  99 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDestinationSame(Vec3 vec) {
/* 111 */     PathPoint pathpoint = getFinalPathPoint();
/* 112 */     return (pathpoint == null) ? false : ((pathpoint.xCoord == (int)vec.xCoord && pathpoint.zCoord == (int)vec.zCoord));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\pathfinding\PathEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */