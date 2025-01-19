/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
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
/*     */ public abstract class PathNavigate
/*     */ {
/*     */   protected EntityLiving theEntity;
/*     */   protected World worldObj;
/*     */   protected PathEntity currentPath;
/*     */   protected double speed;
/*     */   private final IAttributeInstance pathSearchRange;
/*     */   private int totalTicks;
/*     */   private int ticksAtLastPos;
/*  44 */   private Vec3 lastPosCheck = new Vec3(0.0D, 0.0D, 0.0D);
/*  45 */   private float heightRequirement = 1.0F;
/*     */   private final PathFinder pathFinder;
/*     */   
/*     */   public PathNavigate(EntityLiving entitylivingIn, World worldIn) {
/*  49 */     this.theEntity = entitylivingIn;
/*  50 */     this.worldObj = worldIn;
/*  51 */     this.pathSearchRange = entitylivingIn.getEntityAttribute(SharedMonsterAttributes.followRange);
/*  52 */     this.pathFinder = getPathFinder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract PathFinder getPathFinder();
/*     */ 
/*     */   
/*     */   public void setSpeed(double speedIn) {
/*  61 */     this.speed = speedIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPathSearchRange() {
/*  68 */     return (float)this.pathSearchRange.getAttributeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final PathEntity getPathToXYZ(double x, double y, double z) {
/*  75 */     return getPathToPos(new BlockPos(MathHelper.floor_double(x), (int)y, MathHelper.floor_double(z)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity getPathToPos(BlockPos pos) {
/*  82 */     if (!canNavigate()) {
/*  83 */       return null;
/*     */     }
/*  85 */     float f = getPathSearchRange();
/*  86 */     this.worldObj.theProfiler.startSection("pathfind");
/*  87 */     BlockPos blockpos = new BlockPos((Entity)this.theEntity);
/*  88 */     int i = (int)(f + 8.0F);
/*  89 */     ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
/*  90 */     PathEntity pathentity = this.pathFinder.createEntityPathTo((IBlockAccess)chunkcache, (Entity)this.theEntity, pos, f);
/*  91 */     this.worldObj.theProfiler.endSection();
/*  92 */     return pathentity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
/* 100 */     PathEntity pathentity = getPathToXYZ(MathHelper.floor_double(x), (int)y, MathHelper.floor_double(z));
/* 101 */     return setPath(pathentity, speedIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeightRequirement(float jumpHeight) {
/* 108 */     this.heightRequirement = jumpHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity getPathToEntityLiving(Entity entityIn) {
/* 115 */     if (!canNavigate()) {
/* 116 */       return null;
/*     */     }
/* 118 */     float f = getPathSearchRange();
/* 119 */     this.worldObj.theProfiler.startSection("pathfind");
/* 120 */     BlockPos blockpos = (new BlockPos((Entity)this.theEntity)).up();
/* 121 */     int i = (int)(f + 16.0F);
/* 122 */     ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
/* 123 */     PathEntity pathentity = this.pathFinder.createEntityPathTo((IBlockAccess)chunkcache, (Entity)this.theEntity, entityIn, f);
/* 124 */     this.worldObj.theProfiler.endSection();
/* 125 */     return pathentity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
/* 133 */     PathEntity pathentity = getPathToEntityLiving(entityIn);
/* 134 */     return (pathentity != null) ? setPath(pathentity, speedIn) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPath(PathEntity pathentityIn, double speedIn) {
/* 142 */     if (pathentityIn == null) {
/* 143 */       this.currentPath = null;
/* 144 */       return false;
/*     */     } 
/* 146 */     if (!pathentityIn.isSamePath(this.currentPath)) {
/* 147 */       this.currentPath = pathentityIn;
/*     */     }
/*     */     
/* 150 */     removeSunnyPath();
/*     */     
/* 152 */     if (this.currentPath.getCurrentPathLength() == 0) {
/* 153 */       return false;
/*     */     }
/* 155 */     this.speed = speedIn;
/* 156 */     Vec3 vec3 = getEntityPosition();
/* 157 */     this.ticksAtLastPos = this.totalTicks;
/* 158 */     this.lastPosCheck = vec3;
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity getPath() {
/* 168 */     return this.currentPath;
/*     */   }
/*     */   
/*     */   public void onUpdateNavigation() {
/* 172 */     this.totalTicks++;
/*     */     
/* 174 */     if (!noPath()) {
/* 175 */       if (canNavigate()) {
/* 176 */         pathFollow();
/* 177 */       } else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
/* 178 */         Vec3 vec3 = getEntityPosition();
/* 179 */         Vec3 vec31 = this.currentPath.getVectorFromIndex((Entity)this.theEntity, this.currentPath.getCurrentPathIndex());
/*     */         
/* 181 */         if (vec3.yCoord > vec31.yCoord && !this.theEntity.onGround && MathHelper.floor_double(vec3.xCoord) == MathHelper.floor_double(vec31.xCoord) && MathHelper.floor_double(vec3.zCoord) == MathHelper.floor_double(vec31.zCoord)) {
/* 182 */           this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
/*     */         }
/*     */       } 
/*     */       
/* 186 */       if (!noPath()) {
/* 187 */         Vec3 vec32 = this.currentPath.getPosition((Entity)this.theEntity);
/*     */         
/* 189 */         if (vec32 != null) {
/* 190 */           AxisAlignedBB axisalignedbb1 = (new AxisAlignedBB(vec32.xCoord, vec32.yCoord, vec32.zCoord, vec32.xCoord, vec32.yCoord, vec32.zCoord)).expand(0.5D, 0.5D, 0.5D);
/* 191 */           List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes((Entity)this.theEntity, axisalignedbb1.addCoord(0.0D, -1.0D, 0.0D));
/* 192 */           double d0 = -1.0D;
/* 193 */           axisalignedbb1 = axisalignedbb1.offset(0.0D, 1.0D, 0.0D);
/*     */           
/* 195 */           for (AxisAlignedBB axisalignedbb : list) {
/* 196 */             d0 = axisalignedbb.calculateYOffset(axisalignedbb1, d0);
/*     */           }
/*     */           
/* 199 */           this.theEntity.getMoveHelper().setMoveTo(vec32.xCoord, vec32.yCoord + d0, vec32.zCoord, this.speed);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void pathFollow() {
/* 206 */     Vec3 vec3 = getEntityPosition();
/* 207 */     int i = this.currentPath.getCurrentPathLength();
/*     */     
/* 209 */     for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); j++) {
/* 210 */       if ((this.currentPath.getPathPointFromIndex(j)).yCoord != (int)vec3.yCoord) {
/* 211 */         i = j;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 216 */     float f = this.theEntity.width * this.theEntity.width * this.heightRequirement;
/*     */     
/* 218 */     for (int k = this.currentPath.getCurrentPathIndex(); k < i; k++) {
/* 219 */       Vec3 vec31 = this.currentPath.getVectorFromIndex((Entity)this.theEntity, k);
/*     */       
/* 221 */       if (vec3.squareDistanceTo(vec31) < f) {
/* 222 */         this.currentPath.setCurrentPathIndex(k + 1);
/*     */       }
/*     */     } 
/*     */     
/* 226 */     int j1 = MathHelper.ceiling_float_int(this.theEntity.width);
/* 227 */     int k1 = (int)this.theEntity.height + 1;
/* 228 */     int l = j1;
/*     */     
/* 230 */     for (int i1 = i - 1; i1 >= this.currentPath.getCurrentPathIndex(); i1--) {
/* 231 */       if (isDirectPathBetweenPoints(vec3, this.currentPath.getVectorFromIndex((Entity)this.theEntity, i1), j1, k1, l)) {
/* 232 */         this.currentPath.setCurrentPathIndex(i1);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 237 */     checkForStuck(vec3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkForStuck(Vec3 positionVec3) {
/* 245 */     if (this.totalTicks - this.ticksAtLastPos > 100) {
/* 246 */       if (positionVec3.squareDistanceTo(this.lastPosCheck) < 2.25D) {
/* 247 */         clearPathEntity();
/*     */       }
/*     */       
/* 250 */       this.ticksAtLastPos = this.totalTicks;
/* 251 */       this.lastPosCheck = positionVec3;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean noPath() {
/* 259 */     return !(this.currentPath != null && !this.currentPath.isFinished());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPathEntity() {
/* 266 */     this.currentPath = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Vec3 getEntityPosition();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean canNavigate();
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInLiquid() {
/* 280 */     return !(!this.theEntity.isInWater() && !this.theEntity.isInLava());
/*     */   }
/*     */   
/*     */   protected void removeSunnyPath() {}
/*     */   
/*     */   protected abstract boolean isDirectPathBetweenPoints(Vec3 paramVec31, Vec3 paramVec32, int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\pathfinding\PathNavigate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */