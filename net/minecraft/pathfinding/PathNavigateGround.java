/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.pathfinder.NodeProcessor;
/*     */ import net.minecraft.world.pathfinder.WalkNodeProcessor;
/*     */ 
/*     */ public class PathNavigateGround extends PathNavigate {
/*     */   protected WalkNodeProcessor nodeProcessor;
/*     */   private boolean shouldAvoidSun;
/*     */   
/*     */   public PathNavigateGround(EntityLiving entitylivingIn, World worldIn) {
/*  20 */     super(entitylivingIn, worldIn);
/*     */   }
/*     */   
/*     */   protected PathFinder getPathFinder() {
/*  24 */     this.nodeProcessor = new WalkNodeProcessor();
/*  25 */     this.nodeProcessor.setEnterDoors(true);
/*  26 */     return new PathFinder((NodeProcessor)this.nodeProcessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canNavigate() {
/*  33 */     return !(!this.theEntity.onGround && (!getCanSwim() || !isInLiquid()) && (!this.theEntity.isRiding() || !(this.theEntity instanceof net.minecraft.entity.monster.EntityZombie) || !(this.theEntity.ridingEntity instanceof net.minecraft.entity.passive.EntityChicken)));
/*     */   }
/*     */   
/*     */   protected Vec3 getEntityPosition() {
/*  37 */     return new Vec3(this.theEntity.posX, getPathablePosY(), this.theEntity.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPathablePosY() {
/*  44 */     if (this.theEntity.isInWater() && getCanSwim()) {
/*  45 */       int i = (int)(this.theEntity.getEntityBoundingBox()).minY;
/*  46 */       Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), i, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
/*  47 */       int j = 0;
/*     */       
/*  49 */       while (block == Blocks.flowing_water || block == Blocks.water) {
/*  50 */         i++;
/*  51 */         block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.theEntity.posX), i, MathHelper.floor_double(this.theEntity.posZ))).getBlock();
/*  52 */         j++;
/*     */         
/*  54 */         if (j > 16) {
/*  55 */           return (int)(this.theEntity.getEntityBoundingBox()).minY;
/*     */         }
/*     */       } 
/*     */       
/*  59 */       return i;
/*     */     } 
/*  61 */     return (int)((this.theEntity.getEntityBoundingBox()).minY + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeSunnyPath() {
/*  69 */     super.removeSunnyPath();
/*     */     
/*  71 */     if (this.shouldAvoidSun) {
/*  72 */       if (this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.theEntity.posX), (int)((this.theEntity.getEntityBoundingBox()).minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ)))) {
/*     */         return;
/*     */       }
/*     */       
/*  76 */       for (int i = 0; i < this.currentPath.getCurrentPathLength(); i++) {
/*  77 */         PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
/*     */         
/*  79 */         if (this.worldObj.canSeeSky(new BlockPos(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord))) {
/*  80 */           this.currentPath.setCurrentPathLength(i - 1);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDirectPathBetweenPoints(Vec3 posVec31, Vec3 posVec32, int sizeX, int sizeY, int sizeZ) {
/*  92 */     int i = MathHelper.floor_double(posVec31.xCoord);
/*  93 */     int j = MathHelper.floor_double(posVec31.zCoord);
/*  94 */     double d0 = posVec32.xCoord - posVec31.xCoord;
/*  95 */     double d1 = posVec32.zCoord - posVec31.zCoord;
/*  96 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/*  98 */     if (d2 < 1.0E-8D) {
/*  99 */       return false;
/*     */     }
/* 101 */     double d3 = 1.0D / Math.sqrt(d2);
/* 102 */     d0 *= d3;
/* 103 */     d1 *= d3;
/* 104 */     sizeX += 2;
/* 105 */     sizeZ += 2;
/*     */     
/* 107 */     if (!isSafeToStandAt(i, (int)posVec31.yCoord, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
/* 108 */       return false;
/*     */     }
/* 110 */     sizeX -= 2;
/* 111 */     sizeZ -= 2;
/* 112 */     double d4 = 1.0D / Math.abs(d0);
/* 113 */     double d5 = 1.0D / Math.abs(d1);
/* 114 */     double d6 = (i * 1) - posVec31.xCoord;
/* 115 */     double d7 = (j * 1) - posVec31.zCoord;
/*     */     
/* 117 */     if (d0 >= 0.0D) {
/* 118 */       d6++;
/*     */     }
/*     */     
/* 121 */     if (d1 >= 0.0D) {
/* 122 */       d7++;
/*     */     }
/*     */     
/* 125 */     d6 /= d0;
/* 126 */     d7 /= d1;
/* 127 */     int k = (d0 < 0.0D) ? -1 : 1;
/* 128 */     int l = (d1 < 0.0D) ? -1 : 1;
/* 129 */     int i1 = MathHelper.floor_double(posVec32.xCoord);
/* 130 */     int j1 = MathHelper.floor_double(posVec32.zCoord);
/* 131 */     int k1 = i1 - i;
/* 132 */     int l1 = j1 - j;
/*     */     
/* 134 */     while (k1 * k > 0 || l1 * l > 0) {
/* 135 */       if (d6 < d7) {
/* 136 */         d6 += d4;
/* 137 */         i += k;
/* 138 */         k1 = i1 - i;
/*     */       } else {
/* 140 */         d7 += d5;
/* 141 */         j += l;
/* 142 */         l1 = j1 - j;
/*     */       } 
/*     */       
/* 145 */       if (!isSafeToStandAt(i, (int)posVec31.yCoord, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
/* 146 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 vec31, double p_179683_8_, double p_179683_10_) {
/* 159 */     int i = x - sizeX / 2;
/* 160 */     int j = z - sizeZ / 2;
/*     */     
/* 162 */     if (!isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
/* 163 */       return false;
/*     */     }
/* 165 */     for (int k = i; k < i + sizeX; k++) {
/* 166 */       for (int l = j; l < j + sizeZ; l++) {
/* 167 */         double d0 = k + 0.5D - vec31.xCoord;
/* 168 */         double d1 = l + 0.5D - vec31.zCoord;
/*     */         
/* 170 */         if (d0 * p_179683_8_ + d1 * p_179683_10_ >= 0.0D) {
/* 171 */           Block block = this.worldObj.getBlockState(new BlockPos(k, y - 1, l)).getBlock();
/* 172 */           Material material = block.getMaterial();
/*     */           
/* 174 */           if (material == Material.air) {
/* 175 */             return false;
/*     */           }
/*     */           
/* 178 */           if (material == Material.water && !this.theEntity.isInWater()) {
/* 179 */             return false;
/*     */           }
/*     */           
/* 182 */           if (material == Material.lava) {
/* 183 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPositionClear(int p_179692_1_, int p_179692_2_, int p_179692_3_, int p_179692_4_, int p_179692_5_, int p_179692_6_, Vec3 p_179692_7_, double p_179692_8_, double p_179692_10_) {
/* 197 */     for (BlockPos blockpos : BlockPos.getAllInBox(new BlockPos(p_179692_1_, p_179692_2_, p_179692_3_), new BlockPos(p_179692_1_ + p_179692_4_ - 1, p_179692_2_ + p_179692_5_ - 1, p_179692_3_ + p_179692_6_ - 1))) {
/* 198 */       double d0 = blockpos.getX() + 0.5D - p_179692_7_.xCoord;
/* 199 */       double d1 = blockpos.getZ() + 0.5D - p_179692_7_.zCoord;
/*     */       
/* 201 */       if (d0 * p_179692_8_ + d1 * p_179692_10_ >= 0.0D) {
/* 202 */         Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */         
/* 204 */         if (!block.isPassable((IBlockAccess)this.worldObj, blockpos)) {
/* 205 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     return true;
/*     */   }
/*     */   
/*     */   public void setAvoidsWater(boolean avoidsWater) {
/* 214 */     this.nodeProcessor.setAvoidsWater(avoidsWater);
/*     */   }
/*     */   
/*     */   public boolean getAvoidsWater() {
/* 218 */     return this.nodeProcessor.getAvoidsWater();
/*     */   }
/*     */   
/*     */   public void setBreakDoors(boolean canBreakDoors) {
/* 222 */     this.nodeProcessor.setBreakDoors(canBreakDoors);
/*     */   }
/*     */   
/*     */   public void setEnterDoors(boolean par1) {
/* 226 */     this.nodeProcessor.setEnterDoors(par1);
/*     */   }
/*     */   
/*     */   public boolean getEnterDoors() {
/* 230 */     return this.nodeProcessor.getEnterDoors();
/*     */   }
/*     */   
/*     */   public void setCanSwim(boolean canSwim) {
/* 234 */     this.nodeProcessor.setCanSwim(canSwim);
/*     */   }
/*     */   
/*     */   public boolean getCanSwim() {
/* 238 */     return this.nodeProcessor.getCanSwim();
/*     */   }
/*     */   
/*     */   public void setAvoidSun(boolean par1) {
/* 242 */     this.shouldAvoidSun = par1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\pathfinding\PathNavigateGround.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */