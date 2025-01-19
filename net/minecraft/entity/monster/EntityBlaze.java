/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityBlaze
/*     */   extends EntityMob {
/*  26 */   private float heightOffset = 0.5F;
/*     */ 
/*     */   
/*     */   private int heightOffsetUpdateTime;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityBlaze(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     this.isImmuneToFire = true;
/*  36 */     this.experienceValue = 10;
/*  37 */     this.tasks.addTask(4, new AIFireballAttack(this));
/*  38 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  39 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  40 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  41 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  42 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  43 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  47 */     super.applyEntityAttributes();
/*  48 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
/*  49 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*  50 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  54 */     super.entityInit();
/*  55 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  62 */     return "mob.blaze.breathe";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  69 */     return "mob.blaze.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  76 */     return "mob.blaze.death";
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  80 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/*  87 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  95 */     if (!this.onGround && this.motionY < 0.0D) {
/*  96 */       this.motionY *= 0.6D;
/*     */     }
/*     */     
/*  99 */     if (this.worldObj.isRemote) {
/* 100 */       if (this.rand.nextInt(24) == 0 && !isSilent()) {
/* 101 */         this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.fire", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*     */       
/* 104 */       for (int i = 0; i < 2; i++) {
/* 105 */         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */     
/* 109 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/* 113 */     if (isWet()) {
/* 114 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*     */     }
/*     */     
/* 117 */     this.heightOffsetUpdateTime--;
/*     */     
/* 119 */     if (this.heightOffsetUpdateTime <= 0) {
/* 120 */       this.heightOffsetUpdateTime = 100;
/* 121 */       this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
/*     */     } 
/*     */     
/* 124 */     EntityLivingBase entitylivingbase = getAttackTarget();
/*     */     
/* 126 */     if (entitylivingbase != null && entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + getEyeHeight() + this.heightOffset) {
/* 127 */       this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
/* 128 */       this.isAirBorne = true;
/*     */     } 
/*     */     
/* 131 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */   
/*     */   protected Item getDropItem() {
/* 138 */     return Items.blaze_rod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 145 */     return func_70845_n();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 156 */     if (wasRecentlyHit) {
/* 157 */       int i = this.rand.nextInt(2 + lootingModifier);
/*     */       
/* 159 */       for (int j = 0; j < i; j++) {
/* 160 */         dropItem(Items.blaze_rod, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean func_70845_n() {
/* 166 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setOnFire(boolean onFire) {
/* 170 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 172 */     if (onFire) {
/* 173 */       b0 = (byte)(b0 | 0x1);
/*     */     } else {
/* 175 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 178 */     this.dataWatcher.updateObject(16, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   static class AIFireballAttack extends EntityAIBase {
/*     */     private EntityBlaze blaze;
/*     */     private int field_179467_b;
/*     */     private int field_179468_c;
/*     */     
/*     */     public AIFireballAttack(EntityBlaze p_i45846_1_) {
/* 194 */       this.blaze = p_i45846_1_;
/* 195 */       setMutexBits(3);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 199 */       EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
/* 200 */       return (entitylivingbase != null && entitylivingbase.isEntityAlive());
/*     */     }
/*     */     
/*     */     public void startExecuting() {
/* 204 */       this.field_179467_b = 0;
/*     */     }
/*     */     
/*     */     public void resetTask() {
/* 208 */       this.blaze.setOnFire(false);
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 212 */       this.field_179468_c--;
/* 213 */       EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
/* 214 */       double d0 = this.blaze.getDistanceSqToEntity((Entity)entitylivingbase);
/*     */       
/* 216 */       if (d0 < 4.0D) {
/* 217 */         if (this.field_179468_c <= 0) {
/* 218 */           this.field_179468_c = 20;
/* 219 */           this.blaze.attackEntityAsMob((Entity)entitylivingbase);
/*     */         } 
/*     */         
/* 222 */         this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
/* 223 */       } else if (d0 < 256.0D) {
/* 224 */         double d1 = entitylivingbase.posX - this.blaze.posX;
/* 225 */         double d2 = (entitylivingbase.getEntityBoundingBox()).minY + (entitylivingbase.height / 2.0F) - this.blaze.posY + (this.blaze.height / 2.0F);
/* 226 */         double d3 = entitylivingbase.posZ - this.blaze.posZ;
/*     */         
/* 228 */         if (this.field_179468_c <= 0) {
/* 229 */           this.field_179467_b++;
/*     */           
/* 231 */           if (this.field_179467_b == 1) {
/* 232 */             this.field_179468_c = 60;
/* 233 */             this.blaze.setOnFire(true);
/* 234 */           } else if (this.field_179467_b <= 4) {
/* 235 */             this.field_179468_c = 6;
/*     */           } else {
/* 237 */             this.field_179468_c = 100;
/* 238 */             this.field_179467_b = 0;
/* 239 */             this.blaze.setOnFire(false);
/*     */           } 
/*     */           
/* 242 */           if (this.field_179467_b > 1) {
/* 243 */             float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.5F;
/* 244 */             this.blaze.worldObj.playAuxSFXAtEntity(null, 1009, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);
/*     */             
/* 246 */             for (int i = 0; i < 1; i++) {
/* 247 */               EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.blaze.worldObj, (EntityLivingBase)this.blaze, d1 + this.blaze.getRNG().nextGaussian() * f, d2, d3 + this.blaze.getRNG().nextGaussian() * f);
/* 248 */               entitysmallfireball.posY = this.blaze.posY + (this.blaze.height / 2.0F) + 0.5D;
/* 249 */               this.blaze.worldObj.spawnEntityInWorld((Entity)entitysmallfireball);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 254 */         this.blaze.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 10.0F, 10.0F);
/*     */       } else {
/* 256 */         this.blaze.getNavigator().clearPathEntity();
/* 257 */         this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
/*     */       } 
/*     */       
/* 260 */       super.updateTask();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityBlaze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */