/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ 
/*     */ 
/*     */ public class Vec3i
/*     */   implements Comparable<Vec3i>
/*     */ {
/*   9 */   public static final Vec3i NULL_VECTOR = new Vec3i(0, 0, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   private final int x;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int y;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int z;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3i(int xIn, int yIn, int zIn) {
/*  27 */     this.x = xIn;
/*  28 */     this.y = yIn;
/*  29 */     this.z = zIn;
/*     */   }
/*     */   
/*     */   public Vec3i(double xIn, double yIn, double zIn) {
/*  33 */     this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  37 */     if (this == p_equals_1_)
/*  38 */       return true; 
/*  39 */     if (!(p_equals_1_ instanceof Vec3i)) {
/*  40 */       return false;
/*     */     }
/*  42 */     Vec3i vec3i = (Vec3i)p_equals_1_;
/*  43 */     return (getX() != vec3i.getX()) ? false : ((getY() != vec3i.getY()) ? false : ((getZ() == vec3i.getZ())));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  48 */     return (getY() + getZ() * 31) * 31 + getX();
/*     */   }
/*     */   
/*     */   public int compareTo(Vec3i p_compareTo_1_) {
/*  52 */     return (getY() == p_compareTo_1_.getY()) ? ((getZ() == p_compareTo_1_.getZ()) ? (getX() - p_compareTo_1_.getX()) : (getZ() - p_compareTo_1_.getZ())) : (getY() - p_compareTo_1_.getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  59 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  66 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  73 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3i crossProduct(Vec3i vec) {
/*  80 */     return new Vec3i(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSq(double toX, double toY, double toZ) {
/*  87 */     double d0 = getX() - toX;
/*  88 */     double d1 = getY() - toY;
/*  89 */     double d2 = getZ() - toZ;
/*  90 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSqToCenter(double xIn, double yIn, double zIn) {
/*  97 */     double d0 = getX() + 0.5D - xIn;
/*  98 */     double d1 = getY() + 0.5D - yIn;
/*  99 */     double d2 = getZ() + 0.5D - zIn;
/* 100 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSq(Vec3i to) {
/* 107 */     return distanceSq(to.getX(), to.getY(), to.getZ());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 111 */     return Objects.toStringHelper(this).add("x", getX()).add("y", getY()).add("z", getZ()).toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Vec3i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */