/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityTameable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class EntityAIFollowOwner
/*     */   extends EntityAIBase
/*     */ {
/*     */   private EntityTameable thePet;
/*     */   private EntityLivingBase theOwner;
/*     */   World theWorld;
/*     */   private double followSpeed;
/*     */   
/*     */   public EntityAIFollowOwner(EntityTameable thePetIn, double followSpeedIn, float minDistIn, float maxDistIn) {
/*  27 */     this.thePet = thePetIn;
/*  28 */     this.theWorld = thePetIn.worldObj;
/*  29 */     this.followSpeed = followSpeedIn;
/*  30 */     this.petPathfinder = thePetIn.getNavigator();
/*  31 */     this.minDist = minDistIn;
/*  32 */     this.maxDist = maxDistIn;
/*  33 */     setMutexBits(3);
/*     */     
/*  35 */     if (!(thePetIn.getNavigator() instanceof PathNavigateGround))
/*  36 */       throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal"); 
/*     */   }
/*     */   private PathNavigate petPathfinder; private int field_75343_h;
/*     */   float maxDist;
/*     */   float minDist;
/*     */   private boolean field_75344_i;
/*     */   
/*     */   public boolean shouldExecute() {
/*  44 */     EntityLivingBase entitylivingbase = this.thePet.getOwner();
/*     */     
/*  46 */     if (entitylivingbase == null)
/*  47 */       return false; 
/*  48 */     if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).isSpectator())
/*  49 */       return false; 
/*  50 */     if (this.thePet.isSitting())
/*  51 */       return false; 
/*  52 */     if (this.thePet.getDistanceSqToEntity((Entity)entitylivingbase) < (this.minDist * this.minDist)) {
/*  53 */       return false;
/*     */     }
/*  55 */     this.theOwner = entitylivingbase;
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  64 */     return (!this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity((Entity)this.theOwner) > (this.maxDist * this.maxDist) && !this.thePet.isSitting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  71 */     this.field_75343_h = 0;
/*  72 */     this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).getAvoidsWater();
/*  73 */     ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  80 */     this.theOwner = null;
/*  81 */     this.petPathfinder.clearPathEntity();
/*  82 */     ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(true);
/*     */   }
/*     */   
/*     */   private boolean func_181065_a(BlockPos p_181065_1_) {
/*  86 */     IBlockState iblockstate = this.theWorld.getBlockState(p_181065_1_);
/*  87 */     Block block = iblockstate.getBlock();
/*  88 */     return (block == Blocks.air) ? true : (!block.isFullCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  95 */     this.thePet.getLookHelper().setLookPositionWithEntity((Entity)this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
/*     */     
/*  97 */     if (!this.thePet.isSitting() && 
/*  98 */       --this.field_75343_h <= 0) {
/*  99 */       this.field_75343_h = 10;
/*     */       
/* 101 */       if (!this.petPathfinder.tryMoveToEntityLiving((Entity)this.theOwner, this.followSpeed) && 
/* 102 */         !this.thePet.getLeashed() && 
/* 103 */         this.thePet.getDistanceSqToEntity((Entity)this.theOwner) >= 144.0D) {
/* 104 */         int i = MathHelper.floor_double(this.theOwner.posX) - 2;
/* 105 */         int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
/* 106 */         int k = MathHelper.floor_double((this.theOwner.getEntityBoundingBox()).minY);
/*     */         
/* 108 */         for (int l = 0; l <= 4; l++) {
/* 109 */           for (int i1 = 0; i1 <= 4; i1++) {
/* 110 */             if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.doesBlockHaveSolidTopSurface((IBlockAccess)this.theWorld, new BlockPos(i + l, k - 1, j + i1)) && func_181065_a(new BlockPos(i + l, k, j + i1)) && func_181065_a(new BlockPos(i + l, k + 1, j + i1))) {
/* 111 */               this.thePet.setLocationAndAngles(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
/* 112 */               this.petPathfinder.clearPathEntity();
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIFollowOwner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */