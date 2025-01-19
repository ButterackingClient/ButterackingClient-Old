/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.passive.EntityTameable;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityCreature extends EntityLiving {
/*  14 */   public static final UUID FLEEING_SPEED_MODIFIER_UUID = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
/*  15 */   public static final AttributeModifier FLEEING_SPEED_MODIFIER = (new AttributeModifier(FLEEING_SPEED_MODIFIER_UUID, "Fleeing speed bonus", 2.0D, 2)).setSaved(false);
/*  16 */   private BlockPos homePosition = BlockPos.ORIGIN;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  21 */   private float maximumHomeDistance = -1.0F;
/*  22 */   private EntityAIBase aiBase = (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D);
/*     */   private boolean isMovementAITaskSet;
/*     */   
/*     */   public EntityCreature(World worldIn) {
/*  26 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/*  30 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/*  37 */     return (super.getCanSpawnHere() && getBlockPathWeight(new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ)) >= 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPath() {
/*  44 */     return !this.navigator.noPath();
/*     */   }
/*     */   
/*     */   public boolean isWithinHomeDistanceCurrentPosition() {
/*  48 */     return isWithinHomeDistanceFromPosition(new BlockPos(this));
/*     */   }
/*     */   
/*     */   public boolean isWithinHomeDistanceFromPosition(BlockPos pos) {
/*  52 */     return (this.maximumHomeDistance == -1.0F) ? true : ((this.homePosition.distanceSq((Vec3i)pos) < (this.maximumHomeDistance * this.maximumHomeDistance)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHomePosAndDistance(BlockPos pos, int distance) {
/*  59 */     this.homePosition = pos;
/*  60 */     this.maximumHomeDistance = distance;
/*     */   }
/*     */   
/*     */   public BlockPos getHomePosition() {
/*  64 */     return this.homePosition;
/*     */   }
/*     */   
/*     */   public float getMaximumHomeDistance() {
/*  68 */     return this.maximumHomeDistance;
/*     */   }
/*     */   
/*     */   public void detachHome() {
/*  72 */     this.maximumHomeDistance = -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasHome() {
/*  79 */     return (this.maximumHomeDistance != -1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateLeashedState() {
/*  86 */     super.updateLeashedState();
/*     */     
/*  88 */     if (getLeashed() && getLeashedToEntity() != null && (getLeashedToEntity()).worldObj == this.worldObj) {
/*  89 */       Entity entity = getLeashedToEntity();
/*  90 */       setHomePosAndDistance(new BlockPos((int)entity.posX, (int)entity.posY, (int)entity.posZ), 5);
/*  91 */       float f = getDistanceToEntity(entity);
/*     */       
/*  93 */       if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
/*  94 */         if (f > 10.0F) {
/*  95 */           clearLeashed(true, true);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 101 */       if (!this.isMovementAITaskSet) {
/* 102 */         this.tasks.addTask(2, this.aiBase);
/*     */         
/* 104 */         if (getNavigator() instanceof PathNavigateGround) {
/* 105 */           ((PathNavigateGround)getNavigator()).setAvoidsWater(false);
/*     */         }
/*     */         
/* 108 */         this.isMovementAITaskSet = true;
/*     */       } 
/*     */       
/* 111 */       func_142017_o(f);
/*     */       
/* 113 */       if (f > 4.0F) {
/* 114 */         getNavigator().tryMoveToEntityLiving(entity, 1.0D);
/*     */       }
/*     */       
/* 117 */       if (f > 6.0F) {
/* 118 */         double d0 = (entity.posX - this.posX) / f;
/* 119 */         double d1 = (entity.posY - this.posY) / f;
/* 120 */         double d2 = (entity.posZ - this.posZ) / f;
/* 121 */         this.motionX += d0 * Math.abs(d0) * 0.4D;
/* 122 */         this.motionY += d1 * Math.abs(d1) * 0.4D;
/* 123 */         this.motionZ += d2 * Math.abs(d2) * 0.4D;
/*     */       } 
/*     */       
/* 126 */       if (f > 10.0F) {
/* 127 */         clearLeashed(true, true);
/*     */       }
/* 129 */     } else if (!getLeashed() && this.isMovementAITaskSet) {
/* 130 */       this.isMovementAITaskSet = false;
/* 131 */       this.tasks.removeTask(this.aiBase);
/*     */       
/* 133 */       if (getNavigator() instanceof PathNavigateGround) {
/* 134 */         ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*     */       }
/*     */       
/* 137 */       detachHome();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_142017_o(float p_142017_1_) {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityCreature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */