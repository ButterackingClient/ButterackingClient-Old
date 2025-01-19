/*     */ package net.minecraft.world.pathfinder;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WalkNodeProcessor
/*     */   extends NodeProcessor
/*     */ {
/*     */   private boolean canEnterDoors;
/*     */   private boolean canBreakDoors;
/*     */   private boolean avoidsWater;
/*     */   private boolean canSwim;
/*     */   private boolean shouldAvoidWater;
/*     */   
/*     */   public void initProcessor(IBlockAccess iblockaccessIn, Entity entityIn) {
/*  25 */     super.initProcessor(iblockaccessIn, entityIn);
/*  26 */     this.shouldAvoidWater = this.avoidsWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess() {
/*  35 */     super.postProcess();
/*  36 */     this.avoidsWater = this.shouldAvoidWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointTo(Entity entityIn) {
/*     */     int i;
/*  45 */     if (this.canSwim && entityIn.isInWater()) {
/*  46 */       i = (int)(entityIn.getEntityBoundingBox()).minY;
/*  47 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor_double(entityIn.posX), i, MathHelper.floor_double(entityIn.posZ));
/*     */       
/*  49 */       for (Block block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock(); block == Blocks.flowing_water || block == Blocks.water; block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock()) {
/*  50 */         i++;
/*  51 */         blockpos$mutableblockpos.set(MathHelper.floor_double(entityIn.posX), i, MathHelper.floor_double(entityIn.posZ));
/*     */       } 
/*     */       
/*  54 */       this.avoidsWater = false;
/*     */     } else {
/*  56 */       i = MathHelper.floor_double((entityIn.getEntityBoundingBox()).minY + 0.5D);
/*     */     } 
/*     */     
/*  59 */     return openPoint(MathHelper.floor_double((entityIn.getEntityBoundingBox()).minX), i, MathHelper.floor_double((entityIn.getEntityBoundingBox()).minZ));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointToCoords(Entity entityIn, double x, double y, double target) {
/*  66 */     return openPoint(MathHelper.floor_double(x - (entityIn.width / 2.0F)), MathHelper.floor_double(y), MathHelper.floor_double(target - (entityIn.width / 2.0F)));
/*     */   }
/*     */   
/*     */   public int findPathOptions(PathPoint[] pathOptions, Entity entityIn, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
/*  70 */     int i = 0;
/*  71 */     int j = 0;
/*     */     
/*  73 */     if (getVerticalOffset(entityIn, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord) == 1) {
/*  74 */       j = 1;
/*     */     }
/*     */     
/*  77 */     PathPoint pathpoint = getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1, j);
/*  78 */     PathPoint pathpoint1 = getSafePoint(entityIn, currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord, j);
/*  79 */     PathPoint pathpoint2 = getSafePoint(entityIn, currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord, j);
/*  80 */     PathPoint pathpoint3 = getSafePoint(entityIn, currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1, j);
/*     */     
/*  82 */     if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance) {
/*  83 */       pathOptions[i++] = pathpoint;
/*     */     }
/*     */     
/*  86 */     if (pathpoint1 != null && !pathpoint1.visited && pathpoint1.distanceTo(targetPoint) < maxDistance) {
/*  87 */       pathOptions[i++] = pathpoint1;
/*     */     }
/*     */     
/*  90 */     if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance) {
/*  91 */       pathOptions[i++] = pathpoint2;
/*     */     }
/*     */     
/*  94 */     if (pathpoint3 != null && !pathpoint3.visited && pathpoint3.distanceTo(targetPoint) < maxDistance) {
/*  95 */       pathOptions[i++] = pathpoint3;
/*     */     }
/*     */     
/*  98 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathPoint getSafePoint(Entity entityIn, int x, int y, int z, int p_176171_5_) {
/* 105 */     PathPoint pathpoint = null;
/* 106 */     int i = getVerticalOffset(entityIn, x, y, z);
/*     */     
/* 108 */     if (i == 2) {
/* 109 */       return openPoint(x, y, z);
/*     */     }
/* 111 */     if (i == 1) {
/* 112 */       pathpoint = openPoint(x, y, z);
/*     */     }
/*     */     
/* 115 */     if (pathpoint == null && p_176171_5_ > 0 && i != -3 && i != -4 && getVerticalOffset(entityIn, x, y + p_176171_5_, z) == 1) {
/* 116 */       pathpoint = openPoint(x, y + p_176171_5_, z);
/* 117 */       y += p_176171_5_;
/*     */     } 
/*     */     
/* 120 */     if (pathpoint != null) {
/* 121 */       int j = 0;
/*     */       
/*     */       int k;
/* 124 */       for (k = 0; y > 0; pathpoint = openPoint(x, y, z)) {
/* 125 */         k = getVerticalOffset(entityIn, x, y - 1, z);
/*     */         
/* 127 */         if (this.avoidsWater && k == -1) {
/* 128 */           return null;
/*     */         }
/*     */         
/* 131 */         if (k != 1) {
/*     */           break;
/*     */         }
/*     */         
/* 135 */         if (j++ >= entityIn.getMaxFallHeight()) {
/* 136 */           return null;
/*     */         }
/*     */         
/* 139 */         y--;
/*     */         
/* 141 */         if (y <= 0) {
/* 142 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 146 */       if (k == -2) {
/* 147 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return pathpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getVerticalOffset(Entity entityIn, int x, int y, int z) {
/* 162 */     return func_176170_a(this.blockaccess, entityIn, x, y, z, this.entitySizeX, this.entitySizeY, this.entitySizeZ, this.avoidsWater, this.canBreakDoors, this.canEnterDoors);
/*     */   }
/*     */   
/*     */   public static int func_176170_a(IBlockAccess blockaccessIn, Entity entityIn, int x, int y, int z, int sizeX, int sizeY, int sizeZ, boolean avoidWater, boolean breakDoors, boolean enterDoors) {
/* 166 */     boolean flag = false;
/* 167 */     BlockPos blockpos = new BlockPos(entityIn);
/* 168 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 170 */     for (int i = x; i < x + sizeX; i++) {
/* 171 */       for (int j = y; j < y + sizeY; j++) {
/* 172 */         for (int k = z; k < z + sizeZ; k++) {
/* 173 */           blockpos$mutableblockpos.set(i, j, k);
/* 174 */           Block block = blockaccessIn.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock();
/*     */           
/* 176 */           if (block.getMaterial() != Material.air) {
/* 177 */             if (block != Blocks.trapdoor && block != Blocks.iron_trapdoor) {
/* 178 */               if (block != Blocks.flowing_water && block != Blocks.water) {
/* 179 */                 if (!enterDoors && block instanceof net.minecraft.block.BlockDoor && block.getMaterial() == Material.wood) {
/* 180 */                   return 0;
/*     */                 }
/*     */               } else {
/* 183 */                 if (avoidWater) {
/* 184 */                   return -1;
/*     */                 }
/*     */                 
/* 187 */                 flag = true;
/*     */               } 
/*     */             } else {
/* 190 */               flag = true;
/*     */             } 
/*     */             
/* 193 */             if (entityIn.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock() instanceof net.minecraft.block.BlockRailBase) {
/* 194 */               if (!(entityIn.worldObj.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockRailBase) && !(entityIn.worldObj.getBlockState(blockpos.down()).getBlock() instanceof net.minecraft.block.BlockRailBase)) {
/* 195 */                 return -3;
/*     */               }
/* 197 */             } else if (!block.isPassable(blockaccessIn, (BlockPos)blockpos$mutableblockpos) && (!breakDoors || !(block instanceof net.minecraft.block.BlockDoor) || block.getMaterial() != Material.wood)) {
/* 198 */               if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockFenceGate || block instanceof net.minecraft.block.BlockWall) {
/* 199 */                 return -3;
/*     */               }
/*     */               
/* 202 */               if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor) {
/* 203 */                 return -4;
/*     */               }
/*     */               
/* 206 */               Material material = block.getMaterial();
/*     */               
/* 208 */               if (material != Material.lava) {
/* 209 */                 return 0;
/*     */               }
/*     */               
/* 212 */               if (!entityIn.isInLava()) {
/* 213 */                 return -2;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     return flag ? 2 : 1;
/*     */   }
/*     */   
/*     */   public void setEnterDoors(boolean canEnterDoorsIn) {
/* 225 */     this.canEnterDoors = canEnterDoorsIn;
/*     */   }
/*     */   
/*     */   public void setBreakDoors(boolean canBreakDoorsIn) {
/* 229 */     this.canBreakDoors = canBreakDoorsIn;
/*     */   }
/*     */   
/*     */   public void setAvoidsWater(boolean avoidsWaterIn) {
/* 233 */     this.avoidsWater = avoidsWaterIn;
/*     */   }
/*     */   
/*     */   public void setCanSwim(boolean canSwimIn) {
/* 237 */     this.canSwim = canSwimIn;
/*     */   }
/*     */   
/*     */   public boolean getEnterDoors() {
/* 241 */     return this.canEnterDoors;
/*     */   }
/*     */   
/*     */   public boolean getCanSwim() {
/* 245 */     return this.canSwim;
/*     */   }
/*     */   
/*     */   public boolean getAvoidsWater() {
/* 249 */     return this.avoidsWater;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\pathfinder\WalkNodeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */