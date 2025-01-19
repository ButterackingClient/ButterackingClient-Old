/*     */ package net.minecraft.util;
/*     */ 
/*     */ 
/*     */ public class AxisAlignedBB
/*     */ {
/*     */   public final double minX;
/*     */   public final double minY;
/*     */   public final double minZ;
/*     */   public final double maxX;
/*     */   public final double maxY;
/*     */   public final double maxZ;
/*     */   
/*     */   public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  14 */     this.minX = Math.min(x1, x2);
/*  15 */     this.minY = Math.min(y1, y2);
/*  16 */     this.minZ = Math.min(z1, z2);
/*  17 */     this.maxX = Math.max(x1, x2);
/*  18 */     this.maxY = Math.max(y1, y2);
/*  19 */     this.maxZ = Math.max(z1, z2);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB(BlockPos pos1, BlockPos pos2) {
/*  23 */     this.minX = pos1.getX();
/*  24 */     this.minY = pos1.getY();
/*  25 */     this.minZ = pos1.getZ();
/*  26 */     this.maxX = pos2.getX();
/*  27 */     this.maxY = pos2.getY();
/*  28 */     this.maxZ = pos2.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB addCoord(double x, double y, double z) {
/*  35 */     double d0 = this.minX;
/*  36 */     double d1 = this.minY;
/*  37 */     double d2 = this.minZ;
/*  38 */     double d3 = this.maxX;
/*  39 */     double d4 = this.maxY;
/*  40 */     double d5 = this.maxZ;
/*     */     
/*  42 */     if (x < 0.0D) {
/*  43 */       d0 += x;
/*  44 */     } else if (x > 0.0D) {
/*  45 */       d3 += x;
/*     */     } 
/*     */     
/*  48 */     if (y < 0.0D) {
/*  49 */       d1 += y;
/*  50 */     } else if (y > 0.0D) {
/*  51 */       d4 += y;
/*     */     } 
/*     */     
/*  54 */     if (z < 0.0D) {
/*  55 */       d2 += z;
/*  56 */     } else if (z > 0.0D) {
/*  57 */       d5 += z;
/*     */     } 
/*     */     
/*  60 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB expand(double x, double y, double z) {
/*  68 */     double d0 = this.minX - x;
/*  69 */     double d1 = this.minY - y;
/*  70 */     double d2 = this.minZ - z;
/*  71 */     double d3 = this.maxX + x;
/*  72 */     double d4 = this.maxY + y;
/*  73 */     double d5 = this.maxZ + z;
/*  74 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB union(AxisAlignedBB other) {
/*  78 */     double d0 = Math.min(this.minX, other.minX);
/*  79 */     double d1 = Math.min(this.minY, other.minY);
/*  80 */     double d2 = Math.min(this.minZ, other.minZ);
/*  81 */     double d3 = Math.max(this.maxX, other.maxX);
/*  82 */     double d4 = Math.max(this.maxY, other.maxY);
/*  83 */     double d5 = Math.max(this.maxZ, other.maxZ);
/*  84 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AxisAlignedBB fromBounds(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  91 */     double d0 = Math.min(x1, x2);
/*  92 */     double d1 = Math.min(y1, y2);
/*  93 */     double d2 = Math.min(z1, z2);
/*  94 */     double d3 = Math.max(x1, x2);
/*  95 */     double d4 = Math.max(y1, y2);
/*  96 */     double d5 = Math.max(z1, z2);
/*  97 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB offset(double x, double y, double z) {
/* 104 */     return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateXOffset(AxisAlignedBB other, double offsetX) {
/* 113 */     if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/* 114 */       if (offsetX > 0.0D && other.maxX <= this.minX) {
/* 115 */         double d1 = this.minX - other.maxX;
/*     */         
/* 117 */         if (d1 < offsetX) {
/* 118 */           offsetX = d1;
/*     */         }
/* 120 */       } else if (offsetX < 0.0D && other.minX >= this.maxX) {
/* 121 */         double d0 = this.maxX - other.minX;
/*     */         
/* 123 */         if (d0 > offsetX) {
/* 124 */           offsetX = d0;
/*     */         }
/*     */       } 
/*     */       
/* 128 */       return offsetX;
/*     */     } 
/* 130 */     return offsetX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateYOffset(AxisAlignedBB other, double offsetY) {
/* 140 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/* 141 */       if (offsetY > 0.0D && other.maxY <= this.minY) {
/* 142 */         double d1 = this.minY - other.maxY;
/*     */         
/* 144 */         if (d1 < offsetY) {
/* 145 */           offsetY = d1;
/*     */         }
/* 147 */       } else if (offsetY < 0.0D && other.minY >= this.maxY) {
/* 148 */         double d0 = this.maxY - other.minY;
/*     */         
/* 150 */         if (d0 > offsetY) {
/* 151 */           offsetY = d0;
/*     */         }
/*     */       } 
/*     */       
/* 155 */       return offsetY;
/*     */     } 
/* 157 */     return offsetY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateZOffset(AxisAlignedBB other, double offsetZ) {
/* 167 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
/* 168 */       if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
/* 169 */         double d1 = this.minZ - other.maxZ;
/*     */         
/* 171 */         if (d1 < offsetZ) {
/* 172 */           offsetZ = d1;
/*     */         }
/* 174 */       } else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
/* 175 */         double d0 = this.maxZ - other.minZ;
/*     */         
/* 177 */         if (d0 > offsetZ) {
/* 178 */           offsetZ = d0;
/*     */         }
/*     */       } 
/*     */       
/* 182 */       return offsetZ;
/*     */     } 
/* 184 */     return offsetZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersectsWith(AxisAlignedBB other) {
/* 192 */     return (other.maxX > this.minX && other.minX < this.maxX) ? ((other.maxY > this.minY && other.minY < this.maxY) ? ((other.maxZ > this.minZ && other.minZ < this.maxZ)) : false) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVecInside(Vec3 vec) {
/* 199 */     return (vec.xCoord > this.minX && vec.xCoord < this.maxX) ? ((vec.yCoord > this.minY && vec.yCoord < this.maxY) ? ((vec.zCoord > this.minZ && vec.zCoord < this.maxZ)) : false) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAverageEdgeLength() {
/* 206 */     double d0 = this.maxX - this.minX;
/* 207 */     double d1 = this.maxY - this.minY;
/* 208 */     double d2 = this.maxZ - this.minZ;
/* 209 */     return (d0 + d1 + d2) / 3.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB contract(double x, double y, double z) {
/* 216 */     double d0 = this.minX + x;
/* 217 */     double d1 = this.minY + y;
/* 218 */     double d2 = this.minZ + z;
/* 219 */     double d3 = this.maxX - x;
/* 220 */     double d4 = this.maxY - y;
/* 221 */     double d5 = this.maxZ - z;
/* 222 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */   
/*     */   public MovingObjectPosition calculateIntercept(Vec3 vecA, Vec3 vecB) {
/* 226 */     Vec3 vec3 = vecA.getIntermediateWithXValue(vecB, this.minX);
/* 227 */     Vec3 vec31 = vecA.getIntermediateWithXValue(vecB, this.maxX);
/* 228 */     Vec3 vec32 = vecA.getIntermediateWithYValue(vecB, this.minY);
/* 229 */     Vec3 vec33 = vecA.getIntermediateWithYValue(vecB, this.maxY);
/* 230 */     Vec3 vec34 = vecA.getIntermediateWithZValue(vecB, this.minZ);
/* 231 */     Vec3 vec35 = vecA.getIntermediateWithZValue(vecB, this.maxZ);
/*     */     
/* 233 */     if (!isVecInYZ(vec3)) {
/* 234 */       vec3 = null;
/*     */     }
/*     */     
/* 237 */     if (!isVecInYZ(vec31)) {
/* 238 */       vec31 = null;
/*     */     }
/*     */     
/* 241 */     if (!isVecInXZ(vec32)) {
/* 242 */       vec32 = null;
/*     */     }
/*     */     
/* 245 */     if (!isVecInXZ(vec33)) {
/* 246 */       vec33 = null;
/*     */     }
/*     */     
/* 249 */     if (!isVecInXY(vec34)) {
/* 250 */       vec34 = null;
/*     */     }
/*     */     
/* 253 */     if (!isVecInXY(vec35)) {
/* 254 */       vec35 = null;
/*     */     }
/*     */     
/* 257 */     Vec3 vec36 = null;
/*     */     
/* 259 */     if (vec3 != null) {
/* 260 */       vec36 = vec3;
/*     */     }
/*     */     
/* 263 */     if (vec31 != null && (vec36 == null || vecA.squareDistanceTo(vec31) < vecA.squareDistanceTo(vec36))) {
/* 264 */       vec36 = vec31;
/*     */     }
/*     */     
/* 267 */     if (vec32 != null && (vec36 == null || vecA.squareDistanceTo(vec32) < vecA.squareDistanceTo(vec36))) {
/* 268 */       vec36 = vec32;
/*     */     }
/*     */     
/* 271 */     if (vec33 != null && (vec36 == null || vecA.squareDistanceTo(vec33) < vecA.squareDistanceTo(vec36))) {
/* 272 */       vec36 = vec33;
/*     */     }
/*     */     
/* 275 */     if (vec34 != null && (vec36 == null || vecA.squareDistanceTo(vec34) < vecA.squareDistanceTo(vec36))) {
/* 276 */       vec36 = vec34;
/*     */     }
/*     */     
/* 279 */     if (vec35 != null && (vec36 == null || vecA.squareDistanceTo(vec35) < vecA.squareDistanceTo(vec36))) {
/* 280 */       vec36 = vec35;
/*     */     }
/*     */     
/* 283 */     if (vec36 == null) {
/* 284 */       return null;
/*     */     }
/* 286 */     EnumFacing enumfacing = null;
/*     */     
/* 288 */     if (vec36 == vec3) {
/* 289 */       enumfacing = EnumFacing.WEST;
/* 290 */     } else if (vec36 == vec31) {
/* 291 */       enumfacing = EnumFacing.EAST;
/* 292 */     } else if (vec36 == vec32) {
/* 293 */       enumfacing = EnumFacing.DOWN;
/* 294 */     } else if (vec36 == vec33) {
/* 295 */       enumfacing = EnumFacing.UP;
/* 296 */     } else if (vec36 == vec34) {
/* 297 */       enumfacing = EnumFacing.NORTH;
/*     */     } else {
/* 299 */       enumfacing = EnumFacing.SOUTH;
/*     */     } 
/*     */     
/* 302 */     return new MovingObjectPosition(vec36, enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInYZ(Vec3 vec) {
/* 310 */     return (vec == null) ? false : ((vec.yCoord >= this.minY && vec.yCoord <= this.maxY && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInXZ(Vec3 vec) {
/* 317 */     return (vec == null) ? false : ((vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isVecInXY(Vec3 vec) {
/* 324 */     return (vec == null) ? false : ((vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.yCoord >= this.minY && vec.yCoord <= this.maxY));
/*     */   }
/*     */   
/*     */   public String toString() {
/* 328 */     return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
/*     */   }
/*     */   
/*     */   public boolean hasNaN() {
/* 332 */     return !(!Double.isNaN(this.minX) && !Double.isNaN(this.minY) && !Double.isNaN(this.minZ) && !Double.isNaN(this.maxX) && !Double.isNaN(this.maxY) && !Double.isNaN(this.maxZ));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\AxisAlignedBB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */