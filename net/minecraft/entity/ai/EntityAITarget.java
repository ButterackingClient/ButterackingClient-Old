/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.StringUtils;
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
/*     */ 
/*     */ 
/*     */ public abstract class EntityAITarget
/*     */   extends EntityAIBase
/*     */ {
/*     */   protected final EntityCreature taskOwner;
/*     */   protected boolean shouldCheckSight;
/*     */   private boolean nearbyOnly;
/*     */   private int targetSearchStatus;
/*     */   private int targetSearchDelay;
/*     */   private int targetUnseenTicks;
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight) {
/*  50 */     this(creature, checkSight, false);
/*     */   }
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight, boolean onlyNearby) {
/*  54 */     this.taskOwner = creature;
/*  55 */     this.shouldCheckSight = checkSight;
/*  56 */     this.nearbyOnly = onlyNearby;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  63 */     EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
/*     */     
/*  65 */     if (entitylivingbase == null)
/*  66 */       return false; 
/*  67 */     if (!entitylivingbase.isEntityAlive()) {
/*  68 */       return false;
/*     */     }
/*  70 */     Team team = this.taskOwner.getTeam();
/*  71 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/*  73 */     if (team != null && team1 == team) {
/*  74 */       return false;
/*     */     }
/*  76 */     double d0 = getTargetDistance();
/*     */     
/*  78 */     if (this.taskOwner.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0) {
/*  79 */       return false;
/*     */     }
/*  81 */     if (this.shouldCheckSight) {
/*  82 */       if (this.taskOwner.getEntitySenses().canSee((Entity)entitylivingbase)) {
/*  83 */         this.targetUnseenTicks = 0;
/*  84 */       } else if (++this.targetUnseenTicks > 60) {
/*  85 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*  89 */     return !(entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getTargetDistance() {
/*  96 */     IAttributeInstance iattributeinstance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
/*  97 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 104 */     this.targetSearchStatus = 0;
/* 105 */     this.targetSearchDelay = 0;
/* 106 */     this.targetUnseenTicks = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 113 */     this.taskOwner.setAttackTarget(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSuitableTarget(EntityLiving attacker, EntityLivingBase target, boolean includeInvincibles, boolean checkSight) {
/* 120 */     if (target == null)
/* 121 */       return false; 
/* 122 */     if (target == attacker)
/* 123 */       return false; 
/* 124 */     if (!target.isEntityAlive())
/* 125 */       return false; 
/* 126 */     if (!attacker.canAttackClass(target.getClass())) {
/* 127 */       return false;
/*     */     }
/* 129 */     Team team = attacker.getTeam();
/* 130 */     Team team1 = target.getTeam();
/*     */     
/* 132 */     if (team != null && team1 == team) {
/* 133 */       return false;
/*     */     }
/* 135 */     if (attacker instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable)attacker).getOwnerId())) {
/* 136 */       if (target instanceof IEntityOwnable && ((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)target).getOwnerId())) {
/* 137 */         return false;
/*     */       }
/*     */       
/* 140 */       if (target == ((IEntityOwnable)attacker).getOwner()) {
/* 141 */         return false;
/*     */       }
/* 143 */     } else if (target instanceof EntityPlayer && !includeInvincibles && ((EntityPlayer)target).capabilities.disableDamage) {
/* 144 */       return false;
/*     */     } 
/*     */     
/* 147 */     return !(checkSight && !attacker.getEntitySenses().canSee((Entity)target));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
/* 157 */     if (!isSuitableTarget((EntityLiving)this.taskOwner, target, includeInvincibles, this.shouldCheckSight))
/* 158 */       return false; 
/* 159 */     if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos((Entity)target))) {
/* 160 */       return false;
/*     */     }
/* 162 */     if (this.nearbyOnly) {
/* 163 */       if (--this.targetSearchDelay <= 0) {
/* 164 */         this.targetSearchStatus = 0;
/*     */       }
/*     */       
/* 167 */       if (this.targetSearchStatus == 0) {
/* 168 */         this.targetSearchStatus = canEasilyReach(target) ? 1 : 2;
/*     */       }
/*     */       
/* 171 */       if (this.targetSearchStatus == 2) {
/* 172 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canEasilyReach(EntityLivingBase target) {
/* 186 */     this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
/* 187 */     PathEntity pathentity = this.taskOwner.getNavigator().getPathToEntityLiving((Entity)target);
/*     */     
/* 189 */     if (pathentity == null) {
/* 190 */       return false;
/*     */     }
/* 192 */     PathPoint pathpoint = pathentity.getFinalPathPoint();
/*     */     
/* 194 */     if (pathpoint == null) {
/* 195 */       return false;
/*     */     }
/* 197 */     int i = pathpoint.xCoord - MathHelper.floor_double(target.posX);
/* 198 */     int j = pathpoint.zCoord - MathHelper.floor_double(target.posZ);
/* 199 */     return ((i * i + j * j) <= 2.25D);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAITarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */