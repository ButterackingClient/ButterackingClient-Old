/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ public class PathPoint
/*     */ {
/*     */   public final int xCoord;
/*     */   public final int yCoord;
/*     */   public final int zCoord;
/*     */   private final int hash;
/*  29 */   int index = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float totalPathDistance;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float distanceToNext;
/*     */ 
/*     */ 
/*     */   
/*     */   float distanceToTarget;
/*     */ 
/*     */ 
/*     */   
/*     */   PathPoint previous;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean visited;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint(int x, int y, int z) {
/*  57 */     this.xCoord = x;
/*  58 */     this.yCoord = y;
/*  59 */     this.zCoord = z;
/*  60 */     this.hash = makeHash(x, y, z);
/*     */   }
/*     */   
/*     */   public static int makeHash(int x, int y, int z) {
/*  64 */     return y & 0xFF | (x & 0x7FFF) << 8 | (z & 0x7FFF) << 24 | ((x < 0) ? Integer.MIN_VALUE : 0) | ((z < 0) ? 32768 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float distanceTo(PathPoint pathpointIn) {
/*  71 */     float f = (pathpointIn.xCoord - this.xCoord);
/*  72 */     float f1 = (pathpointIn.yCoord - this.yCoord);
/*  73 */     float f2 = (pathpointIn.zCoord - this.zCoord);
/*  74 */     return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float distanceToSquared(PathPoint pathpointIn) {
/*  81 */     float f = (pathpointIn.xCoord - this.xCoord);
/*  82 */     float f1 = (pathpointIn.yCoord - this.yCoord);
/*  83 */     float f2 = (pathpointIn.zCoord - this.zCoord);
/*  84 */     return f * f + f1 * f1 + f2 * f2;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  88 */     if (!(p_equals_1_ instanceof PathPoint)) {
/*  89 */       return false;
/*     */     }
/*  91 */     PathPoint pathpoint = (PathPoint)p_equals_1_;
/*  92 */     return (this.hash == pathpoint.hash && this.xCoord == pathpoint.xCoord && this.yCoord == pathpoint.yCoord && this.zCoord == pathpoint.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     return this.hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAssigned() {
/* 104 */     return (this.index >= 0);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 108 */     return String.valueOf(this.xCoord) + ", " + this.yCoord + ", " + this.zCoord;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\pathfinding\PathPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */