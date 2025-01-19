/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.pathfinder.NodeProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathFinder
/*     */ {
/*  12 */   private Path path = new Path();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  17 */   private PathPoint[] pathOptions = new PathPoint[32];
/*     */   private NodeProcessor nodeProcessor;
/*     */   
/*     */   public PathFinder(NodeProcessor nodeProcessorIn) {
/*  21 */     this.nodeProcessor = nodeProcessorIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity createEntityPathTo(IBlockAccess blockaccess, Entity entityFrom, Entity entityTo, float dist) {
/*  28 */     return createEntityPathTo(blockaccess, entityFrom, entityTo.posX, (entityTo.getEntityBoundingBox()).minY, entityTo.posZ, dist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity createEntityPathTo(IBlockAccess blockaccess, Entity entityIn, BlockPos targetPos, float dist) {
/*  35 */     return createEntityPathTo(blockaccess, entityIn, (targetPos.getX() + 0.5F), (targetPos.getY() + 0.5F), (targetPos.getZ() + 0.5F), dist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathEntity createEntityPathTo(IBlockAccess blockaccess, Entity entityIn, double x, double y, double z, float distance) {
/*  42 */     this.path.clearPath();
/*  43 */     this.nodeProcessor.initProcessor(blockaccess, entityIn);
/*  44 */     PathPoint pathpoint = this.nodeProcessor.getPathPointTo(entityIn);
/*  45 */     PathPoint pathpoint1 = this.nodeProcessor.getPathPointToCoords(entityIn, x, y, z);
/*  46 */     PathEntity pathentity = addToPath(entityIn, pathpoint, pathpoint1, distance);
/*  47 */     this.nodeProcessor.postProcess();
/*  48 */     return pathentity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathEntity addToPath(Entity entityIn, PathPoint pathpointStart, PathPoint pathpointEnd, float maxDistance) {
/*  55 */     pathpointStart.totalPathDistance = 0.0F;
/*  56 */     pathpointStart.distanceToNext = pathpointStart.distanceToSquared(pathpointEnd);
/*  57 */     pathpointStart.distanceToTarget = pathpointStart.distanceToNext;
/*  58 */     this.path.clearPath();
/*  59 */     this.path.addPoint(pathpointStart);
/*  60 */     PathPoint pathpoint = pathpointStart;
/*     */     
/*  62 */     while (!this.path.isPathEmpty()) {
/*  63 */       PathPoint pathpoint1 = this.path.dequeue();
/*     */       
/*  65 */       if (pathpoint1.equals(pathpointEnd)) {
/*  66 */         return createEntityPath(pathpointStart, pathpointEnd);
/*     */       }
/*     */       
/*  69 */       if (pathpoint1.distanceToSquared(pathpointEnd) < pathpoint.distanceToSquared(pathpointEnd)) {
/*  70 */         pathpoint = pathpoint1;
/*     */       }
/*     */       
/*  73 */       pathpoint1.visited = true;
/*  74 */       int i = this.nodeProcessor.findPathOptions(this.pathOptions, entityIn, pathpoint1, pathpointEnd, maxDistance);
/*     */       
/*  76 */       for (int j = 0; j < i; j++) {
/*  77 */         PathPoint pathpoint2 = this.pathOptions[j];
/*  78 */         float f = pathpoint1.totalPathDistance + pathpoint1.distanceToSquared(pathpoint2);
/*     */         
/*  80 */         if (f < maxDistance * 2.0F && (!pathpoint2.isAssigned() || f < pathpoint2.totalPathDistance)) {
/*  81 */           pathpoint2.previous = pathpoint1;
/*  82 */           pathpoint2.totalPathDistance = f;
/*  83 */           pathpoint2.distanceToNext = pathpoint2.distanceToSquared(pathpointEnd);
/*     */           
/*  85 */           if (pathpoint2.isAssigned()) {
/*  86 */             this.path.changeDistance(pathpoint2, pathpoint2.totalPathDistance + pathpoint2.distanceToNext);
/*     */           } else {
/*  88 */             pathpoint2.distanceToTarget = pathpoint2.totalPathDistance + pathpoint2.distanceToNext;
/*  89 */             this.path.addPoint(pathpoint2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     if (pathpoint == pathpointStart) {
/*  96 */       return null;
/*     */     }
/*  98 */     return createEntityPath(pathpointStart, pathpoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathEntity createEntityPath(PathPoint start, PathPoint end) {
/* 106 */     int i = 1;
/*     */     
/* 108 */     for (PathPoint pathpoint = end; pathpoint.previous != null; pathpoint = pathpoint.previous) {
/* 109 */       i++;
/*     */     }
/*     */     
/* 112 */     PathPoint[] apathpoint = new PathPoint[i];
/* 113 */     PathPoint pathpoint1 = end;
/* 114 */     i--;
/*     */     
/* 116 */     for (apathpoint[i] = end; pathpoint1.previous != null; apathpoint[i] = pathpoint1) {
/* 117 */       pathpoint1 = pathpoint1.previous;
/* 118 */       i--;
/*     */     } 
/*     */     
/* 121 */     return new PathEntity(apathpoint);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\pathfinding\PathFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */