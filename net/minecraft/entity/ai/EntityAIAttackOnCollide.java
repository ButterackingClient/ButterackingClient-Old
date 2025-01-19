/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.util.BlockPos;
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
/*     */ public class EntityAIAttackOnCollide
/*     */   extends EntityAIBase
/*     */ {
/*     */   World worldObj;
/*     */   protected EntityCreature attacker;
/*     */   int attackTick;
/*     */   double speedTowardsTarget;
/*     */   boolean longMemory;
/*     */   PathEntity entityPathEntity;
/*     */   Class<? extends Entity> classTarget;
/*     */   private int delayCounter;
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   
/*     */   public EntityAIAttackOnCollide(EntityCreature creature, Class<? extends Entity> targetClass, double speedIn, boolean useLongMemory) {
/*  40 */     this(creature, speedIn, useLongMemory);
/*  41 */     this.classTarget = targetClass;
/*     */   }
/*     */   
/*     */   public EntityAIAttackOnCollide(EntityCreature creature, double speedIn, boolean useLongMemory) {
/*  45 */     this.attacker = creature;
/*  46 */     this.worldObj = creature.worldObj;
/*  47 */     this.speedTowardsTarget = speedIn;
/*  48 */     this.longMemory = useLongMemory;
/*  49 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  56 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*     */     
/*  58 */     if (entitylivingbase == null)
/*  59 */       return false; 
/*  60 */     if (!entitylivingbase.isEntityAlive())
/*  61 */       return false; 
/*  62 */     if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
/*  63 */       return false;
/*     */     }
/*  65 */     this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving((Entity)entitylivingbase);
/*  66 */     return (this.entityPathEntity != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  74 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*  75 */     return (entitylivingbase == null) ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? (!this.attacker.getNavigator().noPath()) : this.attacker.isWithinHomeDistanceFromPosition(new BlockPos((Entity)entitylivingbase))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  82 */     this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
/*  83 */     this.delayCounter = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  90 */     this.attacker.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  97 */     EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
/*  98 */     this.attacker.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 30.0F, 30.0F);
/*  99 */     double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, (entitylivingbase.getEntityBoundingBox()).minY, entitylivingbase.posZ);
/* 100 */     double d1 = func_179512_a(entitylivingbase);
/* 101 */     this.delayCounter--;
/*     */     
/* 103 */     if ((this.longMemory || this.attacker.getEntitySenses().canSee((Entity)entitylivingbase)) && this.delayCounter <= 0 && ((this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D) || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
/* 104 */       this.targetX = entitylivingbase.posX;
/* 105 */       this.targetY = (entitylivingbase.getEntityBoundingBox()).minY;
/* 106 */       this.targetZ = entitylivingbase.posZ;
/* 107 */       this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
/*     */       
/* 109 */       if (d0 > 1024.0D) {
/* 110 */         this.delayCounter += 10;
/* 111 */       } else if (d0 > 256.0D) {
/* 112 */         this.delayCounter += 5;
/*     */       } 
/*     */       
/* 115 */       if (!this.attacker.getNavigator().tryMoveToEntityLiving((Entity)entitylivingbase, this.speedTowardsTarget)) {
/* 116 */         this.delayCounter += 15;
/*     */       }
/*     */     } 
/*     */     
/* 120 */     this.attackTick = Math.max(this.attackTick - 1, 0);
/*     */     
/* 122 */     if (d0 <= d1 && this.attackTick <= 0) {
/* 123 */       this.attackTick = 20;
/*     */       
/* 125 */       if (this.attacker.getHeldItem() != null) {
/* 126 */         this.attacker.swingItem();
/*     */       }
/*     */       
/* 129 */       this.attacker.attackEntityAsMob((Entity)entitylivingbase);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected double func_179512_a(EntityLivingBase attackTarget) {
/* 134 */     return (this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIAttackOnCollide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */