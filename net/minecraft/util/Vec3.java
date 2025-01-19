/*     */ package net.minecraft.util;
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
/*     */ public class Vec3
/*     */ {
/*     */   public final double xCoord;
/*     */   public final double yCoord;
/*     */   public final double zCoord;
/*     */   
/*     */   public Vec3(double x, double y, double z) {
/*  20 */     if (x == -0.0D) {
/*  21 */       x = 0.0D;
/*     */     }
/*     */     
/*  24 */     if (y == -0.0D) {
/*  25 */       y = 0.0D;
/*     */     }
/*     */     
/*  28 */     if (z == -0.0D) {
/*  29 */       z = 0.0D;
/*     */     }
/*     */     
/*  32 */     this.xCoord = x;
/*  33 */     this.yCoord = y;
/*  34 */     this.zCoord = z;
/*     */   }
/*     */   
/*     */   public Vec3(Vec3i p_i46377_1_) {
/*  38 */     this(p_i46377_1_.getX(), p_i46377_1_.getY(), p_i46377_1_.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 subtractReverse(Vec3 vec) {
/*  45 */     return new Vec3(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord, vec.zCoord - this.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 normalize() {
/*  52 */     double d0 = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*  53 */     return (d0 < 1.0E-4D) ? new Vec3(0.0D, 0.0D, 0.0D) : new Vec3(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
/*     */   }
/*     */   
/*     */   public double dotProduct(Vec3 vec) {
/*  57 */     return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord + this.zCoord * vec.zCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 crossProduct(Vec3 vec) {
/*  64 */     return new Vec3(this.yCoord * vec.zCoord - this.zCoord * vec.yCoord, this.zCoord * vec.xCoord - this.xCoord * vec.zCoord, this.xCoord * vec.yCoord - this.yCoord * vec.xCoord);
/*     */   }
/*     */   
/*     */   public Vec3 subtract(Vec3 vec) {
/*  68 */     return subtract(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */   
/*     */   public Vec3 subtract(double x, double y, double z) {
/*  72 */     return addVector(-x, -y, -z);
/*     */   }
/*     */   
/*     */   public Vec3 add(Vec3 vec) {
/*  76 */     return addVector(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 addVector(double x, double y, double z) {
/*  84 */     return new Vec3(this.xCoord + x, this.yCoord + y, this.zCoord + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(Vec3 vec) {
/*  91 */     double d0 = vec.xCoord - this.xCoord;
/*  92 */     double d1 = vec.yCoord - this.yCoord;
/*  93 */     double d2 = vec.zCoord - this.zCoord;
/*  94 */     return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double squareDistanceTo(Vec3 vec) {
/* 101 */     double d0 = vec.xCoord - this.xCoord;
/* 102 */     double d1 = vec.yCoord - this.yCoord;
/* 103 */     double d2 = vec.zCoord - this.zCoord;
/* 104 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double lengthVector() {
/* 111 */     return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getIntermediateWithXValue(Vec3 vec, double x) {
/* 119 */     double d0 = vec.xCoord - this.xCoord;
/* 120 */     double d1 = vec.yCoord - this.yCoord;
/* 121 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 123 */     if (d0 * d0 < 1.0000000116860974E-7D) {
/* 124 */       return null;
/*     */     }
/* 126 */     double d3 = (x - this.xCoord) / d0;
/* 127 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getIntermediateWithYValue(Vec3 vec, double y) {
/* 136 */     double d0 = vec.xCoord - this.xCoord;
/* 137 */     double d1 = vec.yCoord - this.yCoord;
/* 138 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 140 */     if (d1 * d1 < 1.0000000116860974E-7D) {
/* 141 */       return null;
/*     */     }
/* 143 */     double d3 = (y - this.yCoord) / d1;
/* 144 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getIntermediateWithZValue(Vec3 vec, double z) {
/* 153 */     double d0 = vec.xCoord - this.xCoord;
/* 154 */     double d1 = vec.yCoord - this.yCoord;
/* 155 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 157 */     if (d2 * d2 < 1.0000000116860974E-7D) {
/* 158 */       return null;
/*     */     }
/* 160 */     double d3 = (z - this.zCoord) / d2;
/* 161 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 166 */     return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
/*     */   }
/*     */   
/*     */   public Vec3 rotatePitch(float pitch) {
/* 170 */     float f = MathHelper.cos(pitch);
/* 171 */     float f1 = MathHelper.sin(pitch);
/* 172 */     double d0 = this.xCoord;
/* 173 */     double d1 = this.yCoord * f + this.zCoord * f1;
/* 174 */     double d2 = this.zCoord * f - this.yCoord * f1;
/* 175 */     return new Vec3(d0, d1, d2);
/*     */   }
/*     */   
/*     */   public Vec3 rotateYaw(float yaw) {
/* 179 */     float f = MathHelper.cos(yaw);
/* 180 */     float f1 = MathHelper.sin(yaw);
/* 181 */     double d0 = this.xCoord * f + this.zCoord * f1;
/* 182 */     double d1 = this.yCoord;
/* 183 */     double d2 = this.zCoord * f - this.xCoord * f1;
/* 184 */     return new Vec3(d0, d1, d2);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Vec3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */