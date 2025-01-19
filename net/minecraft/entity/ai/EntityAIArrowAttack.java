/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ public class EntityAIArrowAttack
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityLiving entityHost;
/*     */   private final IRangedAttackMob rangedAttackEntityHost;
/*     */   private EntityLivingBase attackTarget;
/*     */   
/*     */   public EntityAIArrowAttack(IRangedAttackMob attacker, double movespeed, int p_i1649_4_, float p_i1649_5_) {
/*  37 */     this(attacker, movespeed, p_i1649_4_, p_i1649_4_, p_i1649_5_);
/*     */   }
/*     */ 
/*     */   
/*  41 */   private int rangedAttackTime = -1; private double entityMoveSpeed;
/*     */   public EntityAIArrowAttack(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
/*  43 */     if (!(attacker instanceof EntityLivingBase)) {
/*  44 */       throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
/*     */     }
/*  46 */     this.rangedAttackEntityHost = attacker;
/*  47 */     this.entityHost = (EntityLiving)attacker;
/*  48 */     this.entityMoveSpeed = movespeed;
/*  49 */     this.field_96561_g = p_i1650_4_;
/*  50 */     this.maxRangedAttackTime = maxAttackTime;
/*  51 */     this.field_96562_i = maxAttackDistanceIn;
/*  52 */     this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
/*  53 */     setMutexBits(3);
/*     */   }
/*     */   private int field_75318_f; private int field_96561_g;
/*     */   private int maxRangedAttackTime;
/*     */   private float field_96562_i;
/*     */   private float maxAttackDistance;
/*     */   
/*     */   public boolean shouldExecute() {
/*  61 */     EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();
/*     */     
/*  63 */     if (entitylivingbase == null) {
/*  64 */       return false;
/*     */     }
/*  66 */     this.attackTarget = entitylivingbase;
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  75 */     return !(!shouldExecute() && this.entityHost.getNavigator().noPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  82 */     this.attackTarget = null;
/*  83 */     this.field_75318_f = 0;
/*  84 */     this.rangedAttackTime = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  91 */     double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, (this.attackTarget.getEntityBoundingBox()).minY, this.attackTarget.posZ);
/*  92 */     boolean flag = this.entityHost.getEntitySenses().canSee((Entity)this.attackTarget);
/*     */     
/*  94 */     if (flag) {
/*  95 */       this.field_75318_f++;
/*     */     } else {
/*  97 */       this.field_75318_f = 0;
/*     */     } 
/*     */     
/* 100 */     if (d0 <= this.maxAttackDistance && this.field_75318_f >= 20) {
/* 101 */       this.entityHost.getNavigator().clearPathEntity();
/*     */     } else {
/* 103 */       this.entityHost.getNavigator().tryMoveToEntityLiving((Entity)this.attackTarget, this.entityMoveSpeed);
/*     */     } 
/*     */     
/* 106 */     this.entityHost.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0F, 30.0F);
/*     */     
/* 108 */     if (--this.rangedAttackTime == 0) {
/* 109 */       if (d0 > this.maxAttackDistance || !flag) {
/*     */         return;
/*     */       }
/*     */       
/* 113 */       float f = MathHelper.sqrt_double(d0) / this.field_96562_i;
/* 114 */       float lvt_5_1_ = MathHelper.clamp_float(f, 0.1F, 1.0F);
/* 115 */       this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
/* 116 */       this.rangedAttackTime = MathHelper.floor_float(f * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
/* 117 */     } else if (this.rangedAttackTime < 0) {
/* 118 */       float f2 = MathHelper.sqrt_double(d0) / this.field_96562_i;
/* 119 */       this.rangedAttackTime = MathHelper.floor_float(f2 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIArrowAttack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */