/*     */ package net.minecraft.entity.boss;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob {
/*  43 */   private float[] field_82220_d = new float[2];
/*  44 */   private float[] field_82221_e = new float[2];
/*  45 */   private float[] field_82217_f = new float[2];
/*  46 */   private float[] field_82218_g = new float[2];
/*  47 */   private int[] field_82223_h = new int[2];
/*  48 */   private int[] field_82224_i = new int[2];
/*     */ 
/*     */   
/*     */   private int blockBreakCounter;
/*     */ 
/*     */   
/*  54 */   private static final Predicate<Entity> attackEntitySelector = new Predicate<Entity>() {
/*     */       public boolean apply(Entity p_apply_1_) {
/*  56 */         return (p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD);
/*     */       }
/*     */     };
/*     */   
/*     */   public EntityWither(World worldIn) {
/*  61 */     super(worldIn);
/*  62 */     setHealth(getMaxHealth());
/*  63 */     setSize(0.9F, 3.5F);
/*  64 */     this.isImmuneToFire = true;
/*  65 */     ((PathNavigateGround)getNavigator()).setCanSwim(true);
/*  66 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  67 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
/*  68 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  69 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  70 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  71 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
/*  72 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 0, false, false, attackEntitySelector));
/*  73 */     this.experienceValue = 50;
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  77 */     super.entityInit();
/*  78 */     this.dataWatcher.addObject(17, new Integer(0));
/*  79 */     this.dataWatcher.addObject(18, new Integer(0));
/*  80 */     this.dataWatcher.addObject(19, new Integer(0));
/*  81 */     this.dataWatcher.addObject(20, new Integer(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  88 */     super.writeEntityToNBT(tagCompound);
/*  89 */     tagCompound.setInteger("Invul", getInvulTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  96 */     super.readEntityFromNBT(tagCompund);
/*  97 */     setInvulTime(tagCompund.getInteger("Invul"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 104 */     return "mob.wither.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 111 */     return "mob.wither.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 118 */     return "mob.wither.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 126 */     this.motionY *= 0.6000000238418579D;
/*     */     
/* 128 */     if (!this.worldObj.isRemote && getWatchedTargetId(0) > 0) {
/* 129 */       Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
/*     */       
/* 131 */       if (entity != null) {
/* 132 */         if (this.posY < entity.posY || (!isArmored() && this.posY < entity.posY + 5.0D)) {
/* 133 */           if (this.motionY < 0.0D) {
/* 134 */             this.motionY = 0.0D;
/*     */           }
/*     */           
/* 137 */           this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
/*     */         } 
/*     */         
/* 140 */         double d0 = entity.posX - this.posX;
/* 141 */         double d1 = entity.posZ - this.posZ;
/* 142 */         double d3 = d0 * d0 + d1 * d1;
/*     */         
/* 144 */         if (d3 > 9.0D) {
/* 145 */           double d5 = MathHelper.sqrt_double(d3);
/* 146 */           this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
/* 147 */           this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D) {
/* 153 */       this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
/*     */     }
/*     */     
/* 156 */     super.onLivingUpdate();
/*     */     
/* 158 */     for (int i = 0; i < 2; i++) {
/* 159 */       this.field_82218_g[i] = this.field_82221_e[i];
/* 160 */       this.field_82217_f[i] = this.field_82220_d[i];
/*     */     } 
/*     */     
/* 163 */     for (int j = 0; j < 2; j++) {
/* 164 */       int k = getWatchedTargetId(j + 1);
/* 165 */       Entity entity1 = null;
/*     */       
/* 167 */       if (k > 0) {
/* 168 */         entity1 = this.worldObj.getEntityByID(k);
/*     */       }
/*     */       
/* 171 */       if (entity1 != null) {
/* 172 */         double d11 = func_82214_u(j + 1);
/* 173 */         double d12 = func_82208_v(j + 1);
/* 174 */         double d13 = func_82213_w(j + 1);
/* 175 */         double d6 = entity1.posX - d11;
/* 176 */         double d7 = entity1.posY + entity1.getEyeHeight() - d12;
/* 177 */         double d8 = entity1.posZ - d13;
/* 178 */         double d9 = MathHelper.sqrt_double(d6 * d6 + d8 * d8);
/* 179 */         float f = (float)(MathHelper.atan2(d8, d6) * 180.0D / Math.PI) - 90.0F;
/* 180 */         float f1 = (float)-(MathHelper.atan2(d7, d9) * 180.0D / Math.PI);
/* 181 */         this.field_82220_d[j] = func_82204_b(this.field_82220_d[j], f1, 40.0F);
/* 182 */         this.field_82221_e[j] = func_82204_b(this.field_82221_e[j], f, 10.0F);
/*     */       } else {
/* 184 */         this.field_82221_e[j] = func_82204_b(this.field_82221_e[j], this.renderYawOffset, 10.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     boolean flag = isArmored();
/*     */     
/* 190 */     for (int l = 0; l < 3; l++) {
/* 191 */       double d10 = func_82214_u(l);
/* 192 */       double d2 = func_82208_v(l);
/* 193 */       double d4 = func_82213_w(l);
/* 194 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       
/* 196 */       if (flag && this.worldObj.rand.nextInt(4) == 0) {
/* 197 */         this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
/*     */       }
/*     */     } 
/*     */     
/* 201 */     if (getInvulTime() > 0) {
/* 202 */       for (int i1 = 0; i1 < 3; i1++) {
/* 203 */         this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + (this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/* 209 */     if (getInvulTime() > 0) {
/* 210 */       int j1 = getInvulTime() - 1;
/*     */       
/* 212 */       if (j1 <= 0) {
/* 213 */         this.worldObj.newExplosion((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getBoolean("mobGriefing"));
/* 214 */         this.worldObj.playBroadcastSound(1013, new BlockPos((Entity)this), 0);
/*     */       } 
/*     */       
/* 217 */       setInvulTime(j1);
/*     */       
/* 219 */       if (this.ticksExisted % 10 == 0) {
/* 220 */         heal(10.0F);
/*     */       }
/*     */     } else {
/* 223 */       super.updateAITasks();
/*     */       
/* 225 */       for (int i = 1; i < 3; i++) {
/* 226 */         if (this.ticksExisted >= this.field_82223_h[i - 1]) {
/* 227 */           this.field_82223_h[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
/*     */           
/* 229 */           if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/* 230 */             int j3 = i - 1;
/* 231 */             int k3 = this.field_82224_i[i - 1];
/* 232 */             this.field_82224_i[j3] = this.field_82224_i[i - 1] + 1;
/*     */             
/* 234 */             if (k3 > 15) {
/* 235 */               float f = 10.0F;
/* 236 */               float f1 = 5.0F;
/* 237 */               double d0 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
/* 238 */               double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
/* 239 */               double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
/* 240 */               launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
/* 241 */               this.field_82224_i[i - 1] = 0;
/*     */             } 
/*     */           } 
/*     */           
/* 245 */           int k1 = getWatchedTargetId(i);
/*     */           
/* 247 */           if (k1 > 0) {
/* 248 */             Entity entity = this.worldObj.getEntityByID(k1);
/*     */             
/* 250 */             if (entity != null && entity.isEntityAlive() && getDistanceSqToEntity(entity) <= 900.0D && canEntityBeSeen(entity)) {
/* 251 */               if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage) {
/* 252 */                 updateWatchedTargetId(i, 0);
/*     */               } else {
/* 254 */                 launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
/* 255 */                 this.field_82223_h[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
/* 256 */                 this.field_82224_i[i - 1] = 0;
/*     */               } 
/*     */             } else {
/* 259 */               updateWatchedTargetId(i, 0);
/*     */             } 
/*     */           } else {
/* 262 */             List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.and(attackEntitySelector, EntitySelectors.NOT_SPECTATING));
/*     */             
/* 264 */             for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
/* 265 */               EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
/*     */               
/* 267 */               if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase)) {
/* 268 */                 if (entitylivingbase instanceof EntityPlayer) {
/* 269 */                   if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage)
/* 270 */                     updateWatchedTargetId(i, entitylivingbase.getEntityId()); 
/*     */                   break;
/*     */                 } 
/* 273 */                 updateWatchedTargetId(i, entitylivingbase.getEntityId());
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 279 */               list.remove(entitylivingbase);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 285 */       if (getAttackTarget() != null) {
/* 286 */         updateWatchedTargetId(0, getAttackTarget().getEntityId());
/*     */       } else {
/* 288 */         updateWatchedTargetId(0, 0);
/*     */       } 
/*     */       
/* 291 */       if (this.blockBreakCounter > 0) {
/* 292 */         this.blockBreakCounter--;
/*     */         
/* 294 */         if (this.blockBreakCounter == 0 && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
/* 295 */           int i1 = MathHelper.floor_double(this.posY);
/* 296 */           int l1 = MathHelper.floor_double(this.posX);
/* 297 */           int i2 = MathHelper.floor_double(this.posZ);
/* 298 */           boolean flag = false;
/*     */           
/* 300 */           for (int k2 = -1; k2 <= 1; k2++) {
/* 301 */             for (int l2 = -1; l2 <= 1; l2++) {
/* 302 */               for (int j = 0; j <= 3; j++) {
/* 303 */                 int i3 = l1 + k2;
/* 304 */                 int k = i1 + j;
/* 305 */                 int l = i2 + l2;
/* 306 */                 BlockPos blockpos = new BlockPos(i3, k, l);
/* 307 */                 Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */                 
/* 309 */                 if (block.getMaterial() != Material.air && canDestroyBlock(block)) {
/* 310 */                   flag = !(!this.worldObj.destroyBlock(blockpos, true) && !flag);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 316 */           if (flag) {
/* 317 */             this.worldObj.playAuxSFXAtEntity(null, 1012, new BlockPos((Entity)this), 0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 322 */       if (this.ticksExisted % 20 == 0) {
/* 323 */         heal(1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean canDestroyBlock(Block p_181033_0_) {
/* 329 */     return (p_181033_0_ != Blocks.bedrock && p_181033_0_ != Blocks.end_portal && p_181033_0_ != Blocks.end_portal_frame && p_181033_0_ != Blocks.command_block && p_181033_0_ != Blocks.barrier);
/*     */   }
/*     */   
/*     */   public void func_82206_m() {
/* 333 */     setInvulTime(220);
/* 334 */     setHealth(getMaxHealth() / 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInWeb() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 347 */     return 4;
/*     */   }
/*     */   
/*     */   private double func_82214_u(int p_82214_1_) {
/* 351 */     if (p_82214_1_ <= 0) {
/* 352 */       return this.posX;
/*     */     }
/* 354 */     float f = (this.renderYawOffset + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
/* 355 */     float f1 = MathHelper.cos(f);
/* 356 */     return this.posX + f1 * 1.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   private double func_82208_v(int p_82208_1_) {
/* 361 */     return (p_82208_1_ <= 0) ? (this.posY + 3.0D) : (this.posY + 2.2D);
/*     */   }
/*     */   
/*     */   private double func_82213_w(int p_82213_1_) {
/* 365 */     if (p_82213_1_ <= 0) {
/* 366 */       return this.posZ;
/*     */     }
/* 368 */     float f = (this.renderYawOffset + (180 * (p_82213_1_ - 1))) / 180.0F * 3.1415927F;
/* 369 */     float f1 = MathHelper.sin(f);
/* 370 */     return this.posZ + f1 * 1.3D;
/*     */   }
/*     */ 
/*     */   
/*     */   private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
/* 375 */     float f = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
/*     */     
/* 377 */     if (f > p_82204_3_) {
/* 378 */       f = p_82204_3_;
/*     */     }
/*     */     
/* 381 */     if (f < -p_82204_3_) {
/* 382 */       f = -p_82204_3_;
/*     */     }
/*     */     
/* 385 */     return p_82204_1_ + f;
/*     */   }
/*     */   
/*     */   private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
/* 389 */     launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0 && this.rand.nextFloat() < 0.001F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void launchWitherSkullToCoords(int p_82209_1_, double x, double y, double z, boolean invulnerable) {
/* 396 */     this.worldObj.playAuxSFXAtEntity(null, 1014, new BlockPos((Entity)this), 0);
/* 397 */     double d0 = func_82214_u(p_82209_1_);
/* 398 */     double d1 = func_82208_v(p_82209_1_);
/* 399 */     double d2 = func_82213_w(p_82209_1_);
/* 400 */     double d3 = x - d0;
/* 401 */     double d4 = y - d1;
/* 402 */     double d5 = z - d2;
/* 403 */     EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.worldObj, (EntityLivingBase)this, d3, d4, d5);
/*     */     
/* 405 */     if (invulnerable) {
/* 406 */       entitywitherskull.setInvulnerable(true);
/*     */     }
/*     */     
/* 409 */     entitywitherskull.posY = d1;
/* 410 */     entitywitherskull.posX = d0;
/* 411 */     entitywitherskull.posZ = d2;
/* 412 */     this.worldObj.spawnEntityInWorld((Entity)entitywitherskull);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
/* 419 */     launchWitherSkullToEntity(0, target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 426 */     if (isEntityInvulnerable(source))
/* 427 */       return false; 
/* 428 */     if (source != DamageSource.drown && !(source.getEntity() instanceof EntityWither)) {
/* 429 */       if (getInvulTime() > 0 && source != DamageSource.outOfWorld) {
/* 430 */         return false;
/*     */       }
/* 432 */       if (isArmored()) {
/* 433 */         Entity entity = source.getSourceOfDamage();
/*     */         
/* 435 */         if (entity instanceof net.minecraft.entity.projectile.EntityArrow) {
/* 436 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 440 */       Entity entity1 = source.getEntity();
/*     */       
/* 442 */       if (entity1 != null && !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase && ((EntityLivingBase)entity1).getCreatureAttribute() == getCreatureAttribute()) {
/* 443 */         return false;
/*     */       }
/* 445 */       if (this.blockBreakCounter <= 0) {
/* 446 */         this.blockBreakCounter = 20;
/*     */       }
/*     */       
/* 449 */       for (int i = 0; i < this.field_82224_i.length; i++) {
/* 450 */         this.field_82224_i[i] = this.field_82224_i[i] + 3;
/*     */       }
/*     */       
/* 453 */       return super.attackEntityFrom(source, amount);
/*     */     } 
/*     */ 
/*     */     
/* 457 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 469 */     EntityItem entityitem = dropItem(Items.nether_star, 1);
/*     */     
/* 471 */     if (entityitem != null) {
/* 472 */       entityitem.setNoDespawn();
/*     */     }
/*     */     
/* 475 */     if (!this.worldObj.isRemote) {
/* 476 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D))) {
/* 477 */         entityplayer.triggerAchievement((StatBase)AchievementList.killWither);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void despawnEntity() {
/* 486 */     this.entityAge = 0;
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 490 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPotionEffect(PotionEffect potioneffectIn) {}
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 503 */     super.applyEntityAttributes();
/* 504 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
/* 505 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579D);
/* 506 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
/*     */   }
/*     */   
/*     */   public float func_82207_a(int p_82207_1_) {
/* 510 */     return this.field_82221_e[p_82207_1_];
/*     */   }
/*     */   
/*     */   public float func_82210_r(int p_82210_1_) {
/* 514 */     return this.field_82220_d[p_82210_1_];
/*     */   }
/*     */   
/*     */   public int getInvulTime() {
/* 518 */     return this.dataWatcher.getWatchableObjectInt(20);
/*     */   }
/*     */   
/*     */   public void setInvulTime(int p_82215_1_) {
/* 522 */     this.dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWatchedTargetId(int p_82203_1_) {
/* 529 */     return this.dataWatcher.getWatchableObjectInt(17 + p_82203_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWatchedTargetId(int targetOffset, int newId) {
/* 536 */     this.dataWatcher.updateObject(17 + targetOffset, Integer.valueOf(newId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmored() {
/* 544 */     return (getHealth() <= getMaxHealth() / 2.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 551 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mountEntity(Entity entityIn) {
/* 558 */     this.ridingEntity = null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\boss\EntityWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */