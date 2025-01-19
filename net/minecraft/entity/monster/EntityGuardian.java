/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateSwimmer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.WeightedRandomFishable;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityGuardian
/*     */   extends EntityMob
/*     */ {
/*     */   private float field_175482_b;
/*     */   private float field_175484_c;
/*     */   private float field_175483_bk;
/*     */   private float field_175485_bl;
/*     */   
/*     */   public EntityGuardian(World worldIn) {
/*  52 */     super(worldIn);
/*  53 */     this.experienceValue = 10;
/*  54 */     setSize(0.85F, 0.85F);
/*  55 */     this.tasks.addTask(4, new AIGuardianAttack(this));
/*     */     EntityAIMoveTowardsRestriction entityaimovetowardsrestriction;
/*  57 */     this.tasks.addTask(5, (EntityAIBase)(entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D)));
/*  58 */     this.tasks.addTask(7, (EntityAIBase)(this.wander = new EntityAIWander(this, 1.0D, 80)));
/*  59 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  60 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityGuardian.class, 12.0F, 0.01F));
/*  61 */     this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  62 */     this.wander.setMutexBits(3);
/*  63 */     entityaimovetowardsrestriction.setMutexBits(3);
/*  64 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new GuardianTargetSelector(this)));
/*  65 */     this.moveHelper = new GuardianMoveHelper(this);
/*  66 */     this.field_175484_c = this.field_175482_b = this.rand.nextFloat();
/*     */   }
/*     */   private float field_175486_bm; private EntityLivingBase targetedEntity; private int field_175479_bo; private boolean field_175480_bp; private EntityAIWander wander;
/*     */   protected void applyEntityAttributes() {
/*  70 */     super.applyEntityAttributes();
/*  71 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
/*  72 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*  73 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
/*  74 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  81 */     super.readEntityFromNBT(tagCompund);
/*  82 */     setElder(tagCompund.getBoolean("Elder"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  89 */     super.writeEntityToNBT(tagCompound);
/*  90 */     tagCompound.setBoolean("Elder", isElder());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/*  97 */     return (PathNavigate)new PathNavigateSwimmer((EntityLiving)this, worldIn);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 101 */     super.entityInit();
/* 102 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/* 103 */     this.dataWatcher.addObject(17, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSyncedFlagSet(int flagId) {
/* 110 */     return ((this.dataWatcher.getWatchableObjectInt(16) & flagId) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSyncedFlag(int flagId, boolean state) {
/* 117 */     int i = this.dataWatcher.getWatchableObjectInt(16);
/*     */     
/* 119 */     if (state) {
/* 120 */       this.dataWatcher.updateObject(16, Integer.valueOf(i | flagId));
/*     */     } else {
/* 122 */       this.dataWatcher.updateObject(16, Integer.valueOf(i & (flagId ^ 0xFFFFFFFF)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean func_175472_n() {
/* 127 */     return isSyncedFlagSet(2);
/*     */   }
/*     */   
/*     */   private void func_175476_l(boolean p_175476_1_) {
/* 131 */     setSyncedFlag(2, p_175476_1_);
/*     */   }
/*     */   
/*     */   public int func_175464_ck() {
/* 135 */     return isElder() ? 60 : 80;
/*     */   }
/*     */   
/*     */   public boolean isElder() {
/* 139 */     return isSyncedFlagSet(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElder(boolean elder) {
/* 146 */     setSyncedFlag(4, elder);
/*     */     
/* 148 */     if (elder) {
/* 149 */       setSize(1.9975F, 1.9975F);
/* 150 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/* 151 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
/* 152 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
/* 153 */       enablePersistence();
/* 154 */       this.wander.setExecutionChance(400);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setElder() {
/* 159 */     setElder(true);
/* 160 */     this.field_175486_bm = this.field_175485_bl = 1.0F;
/*     */   }
/*     */   
/*     */   private void setTargetedEntity(int entityId) {
/* 164 */     this.dataWatcher.updateObject(17, Integer.valueOf(entityId));
/*     */   }
/*     */   
/*     */   public boolean hasTargetedEntity() {
/* 168 */     return (this.dataWatcher.getWatchableObjectInt(17) != 0);
/*     */   }
/*     */   
/*     */   public EntityLivingBase getTargetedEntity() {
/* 172 */     if (!hasTargetedEntity())
/* 173 */       return null; 
/* 174 */     if (this.worldObj.isRemote) {
/* 175 */       if (this.targetedEntity != null) {
/* 176 */         return this.targetedEntity;
/*     */       }
/* 178 */       Entity entity = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
/*     */       
/* 180 */       if (entity instanceof EntityLivingBase) {
/* 181 */         this.targetedEntity = (EntityLivingBase)entity;
/* 182 */         return this.targetedEntity;
/*     */       } 
/* 184 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 188 */     return getAttackTarget();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDataWatcherUpdate(int dataID) {
/* 193 */     super.onDataWatcherUpdate(dataID);
/*     */     
/* 195 */     if (dataID == 16) {
/* 196 */       if (isElder() && this.width < 1.0F) {
/* 197 */         setSize(1.9975F, 1.9975F);
/*     */       }
/* 199 */     } else if (dataID == 17) {
/* 200 */       this.field_175479_bo = 0;
/* 201 */       this.targetedEntity = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTalkInterval() {
/* 209 */     return 160;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 216 */     return !isInWater() ? "mob.guardian.land.idle" : (isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 223 */     return !isInWater() ? "mob.guardian.land.hit" : (isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 230 */     return !isInWater() ? "mob.guardian.land.death" : (isElder() ? "mob.guardian.elder.death" : "mob.guardian.death");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 238 */     return false;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 242 */     return this.height * 0.5F;
/*     */   }
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 246 */     return (this.worldObj.getBlockState(pos).getBlock().getMaterial() == Material.water) ? (10.0F + this.worldObj.getLightBrightness(pos) - 0.5F) : super.getBlockPathWeight(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 254 */     if (this.worldObj.isRemote) {
/* 255 */       this.field_175484_c = this.field_175482_b;
/*     */       
/* 257 */       if (!isInWater()) {
/* 258 */         this.field_175483_bk = 2.0F;
/*     */         
/* 260 */         if (this.motionY > 0.0D && this.field_175480_bp && !isSilent()) {
/* 261 */           this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.guardian.flop", 1.0F, 1.0F, false);
/*     */         }
/*     */         
/* 264 */         this.field_175480_bp = (this.motionY < 0.0D && this.worldObj.isBlockNormalCube((new BlockPos((Entity)this)).down(), false));
/* 265 */       } else if (func_175472_n()) {
/* 266 */         if (this.field_175483_bk < 0.5F) {
/* 267 */           this.field_175483_bk = 4.0F;
/*     */         } else {
/* 269 */           this.field_175483_bk += (0.5F - this.field_175483_bk) * 0.1F;
/*     */         } 
/*     */       } else {
/* 272 */         this.field_175483_bk += (0.125F - this.field_175483_bk) * 0.2F;
/*     */       } 
/*     */       
/* 275 */       this.field_175482_b += this.field_175483_bk;
/* 276 */       this.field_175486_bm = this.field_175485_bl;
/*     */       
/* 278 */       if (!isInWater()) {
/* 279 */         this.field_175485_bl = this.rand.nextFloat();
/* 280 */       } else if (func_175472_n()) {
/* 281 */         this.field_175485_bl += (0.0F - this.field_175485_bl) * 0.25F;
/*     */       } else {
/* 283 */         this.field_175485_bl += (1.0F - this.field_175485_bl) * 0.06F;
/*     */       } 
/*     */       
/* 286 */       if (func_175472_n() && isInWater()) {
/* 287 */         Vec3 vec3 = getLook(0.0F);
/*     */         
/* 289 */         for (int i = 0; i < 2; i++) {
/* 290 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width - vec3.xCoord * 1.5D, this.posY + this.rand.nextDouble() * this.height - vec3.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width - vec3.zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */       } 
/*     */       
/* 294 */       if (hasTargetedEntity()) {
/* 295 */         if (this.field_175479_bo < func_175464_ck()) {
/* 296 */           this.field_175479_bo++;
/*     */         }
/*     */         
/* 299 */         EntityLivingBase entitylivingbase = getTargetedEntity();
/*     */         
/* 301 */         if (entitylivingbase != null) {
/* 302 */           getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/* 303 */           getLookHelper().onUpdateLook();
/* 304 */           double d5 = func_175477_p(0.0F);
/* 305 */           double d0 = entitylivingbase.posX - this.posX;
/* 306 */           double d1 = entitylivingbase.posY + (entitylivingbase.height * 0.5F) - this.posY + getEyeHeight();
/* 307 */           double d2 = entitylivingbase.posZ - this.posZ;
/* 308 */           double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 309 */           d0 /= d3;
/* 310 */           d1 /= d3;
/* 311 */           d2 /= d3;
/* 312 */           double d4 = this.rand.nextDouble();
/*     */           
/* 314 */           while (d4 < d3) {
/* 315 */             d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
/* 316 */             this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4, this.posY + d1 * d4 + getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 322 */     if (this.inWater) {
/* 323 */       setAir(300);
/* 324 */     } else if (this.onGround) {
/* 325 */       this.motionY += 0.5D;
/* 326 */       this.motionX += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 327 */       this.motionZ += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 328 */       this.rotationYaw = this.rand.nextFloat() * 360.0F;
/* 329 */       this.onGround = false;
/* 330 */       this.isAirBorne = true;
/*     */     } 
/*     */     
/* 333 */     if (hasTargetedEntity()) {
/* 334 */       this.rotationYaw = this.rotationYawHead;
/*     */     }
/*     */     
/* 337 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */   public float func_175471_a(float p_175471_1_) {
/* 341 */     return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * p_175471_1_;
/*     */   }
/*     */   
/*     */   public float func_175469_o(float p_175469_1_) {
/* 345 */     return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * p_175469_1_;
/*     */   }
/*     */   
/*     */   public float func_175477_p(float p_175477_1_) {
/* 349 */     return (this.field_175479_bo + p_175477_1_) / func_175464_ck();
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/* 353 */     super.updateAITasks();
/*     */     
/* 355 */     if (isElder()) {
/* 356 */       int i = 1200;
/* 357 */       int j = 1200;
/* 358 */       int k = 6000;
/* 359 */       int l = 2;
/*     */       
/* 361 */       if ((this.ticksExisted + getEntityId()) % 1200 == 0) {
/* 362 */         Potion potion = Potion.digSlowdown;
/*     */         
/* 364 */         for (EntityPlayerMP entityplayermp : this.worldObj.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
/*     */               public boolean apply(EntityPlayerMP p_apply_1_) {
/* 366 */                 return (EntityGuardian.this.getDistanceSqToEntity((Entity)p_apply_1_) < 2500.0D && p_apply_1_.theItemInWorldManager.survivalOrAdventure());
/*     */               }
/*     */             })) {
/* 369 */           if (!entityplayermp.isPotionActive(potion) || entityplayermp.getActivePotionEffect(potion).getAmplifier() < 2 || entityplayermp.getActivePotionEffect(potion).getDuration() < 1200) {
/* 370 */             entityplayermp.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(10, 0.0F));
/* 371 */             entityplayermp.addPotionEffect(new PotionEffect(potion.id, 6000, 2));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 376 */       if (!hasHome()) {
/* 377 */         setHomePosAndDistance(new BlockPos((Entity)this), 16);
/*     */       }
/*     */     } 
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
/* 390 */     int i = this.rand.nextInt(3) + this.rand.nextInt(lootingModifier + 1);
/*     */     
/* 392 */     if (i > 0) {
/* 393 */       entityDropItem(new ItemStack(Items.prismarine_shard, i, 0), 1.0F);
/*     */     }
/*     */     
/* 396 */     if (this.rand.nextInt(3 + lootingModifier) > 1) {
/* 397 */       entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 1.0F);
/* 398 */     } else if (this.rand.nextInt(3 + lootingModifier) > 1) {
/* 399 */       entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
/*     */     } 
/*     */     
/* 402 */     if (wasRecentlyHit && isElder()) {
/* 403 */       entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 411 */     ItemStack itemstack = ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j())).getItemStack(this.rand);
/* 412 */     entityDropItem(itemstack, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 419 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 426 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 433 */     return ((this.rand.nextInt(20) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos((Entity)this))) && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 440 */     if (!func_175472_n() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
/* 441 */       EntityLivingBase entitylivingbase = (EntityLivingBase)source.getSourceOfDamage();
/*     */       
/* 443 */       if (!source.isExplosion()) {
/* 444 */         entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage((Entity)this), 2.0F);
/* 445 */         entitylivingbase.playSound("damage.thorns", 0.5F, 1.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 449 */     this.wander.makeUpdate();
/* 450 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 458 */     return 180;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 465 */     if (isServerWorld()) {
/* 466 */       if (isInWater()) {
/* 467 */         moveFlying(strafe, forward, 0.1F);
/* 468 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 469 */         this.motionX *= 0.8999999761581421D;
/* 470 */         this.motionY *= 0.8999999761581421D;
/* 471 */         this.motionZ *= 0.8999999761581421D;
/*     */         
/* 473 */         if (!func_175472_n() && getAttackTarget() == null) {
/* 474 */           this.motionY -= 0.005D;
/*     */         }
/*     */       } else {
/* 477 */         super.moveEntityWithHeading(strafe, forward);
/*     */       } 
/*     */     } else {
/* 480 */       super.moveEntityWithHeading(strafe, forward);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AIGuardianAttack extends EntityAIBase {
/*     */     private EntityGuardian theEntity;
/*     */     private int tickCounter;
/*     */     
/*     */     public AIGuardianAttack(EntityGuardian guardian) {
/* 489 */       this.theEntity = guardian;
/* 490 */       setMutexBits(3);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 494 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 495 */       return (entitylivingbase != null && entitylivingbase.isEntityAlive());
/*     */     }
/*     */     
/*     */     public boolean continueExecuting() {
/* 499 */       return (super.continueExecuting() && (this.theEntity.isElder() || this.theEntity.getDistanceSqToEntity((Entity)this.theEntity.getAttackTarget()) > 9.0D));
/*     */     }
/*     */     
/*     */     public void startExecuting() {
/* 503 */       this.tickCounter = -10;
/* 504 */       this.theEntity.getNavigator().clearPathEntity();
/* 505 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)this.theEntity.getAttackTarget(), 90.0F, 90.0F);
/* 506 */       this.theEntity.isAirBorne = true;
/*     */     }
/*     */     
/*     */     public void resetTask() {
/* 510 */       this.theEntity.setTargetedEntity(0);
/* 511 */       this.theEntity.setAttackTarget(null);
/* 512 */       this.theEntity.wander.makeUpdate();
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 516 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 517 */       this.theEntity.getNavigator().clearPathEntity();
/* 518 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/*     */       
/* 520 */       if (!this.theEntity.canEntityBeSeen((Entity)entitylivingbase)) {
/* 521 */         this.theEntity.setAttackTarget(null);
/*     */       } else {
/* 523 */         this.tickCounter++;
/*     */         
/* 525 */         if (this.tickCounter == 0) {
/* 526 */           this.theEntity.setTargetedEntity(this.theEntity.getAttackTarget().getEntityId());
/* 527 */           this.theEntity.worldObj.setEntityState((Entity)this.theEntity, (byte)21);
/* 528 */         } else if (this.tickCounter >= this.theEntity.func_175464_ck()) {
/* 529 */           float f = 1.0F;
/*     */           
/* 531 */           if (this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/* 532 */             f += 2.0F;
/*     */           }
/*     */           
/* 535 */           if (this.theEntity.isElder()) {
/* 536 */             f += 2.0F;
/*     */           }
/*     */           
/* 539 */           entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage((Entity)this.theEntity, (Entity)this.theEntity), f);
/* 540 */           entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.theEntity), (float)this.theEntity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
/* 541 */           this.theEntity.setAttackTarget(null);
/* 542 */         } else if (this.tickCounter < 60 || this.tickCounter % 20 == 0) {
/*     */         
/*     */         } 
/*     */         
/* 546 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianMoveHelper extends EntityMoveHelper {
/*     */     private EntityGuardian entityGuardian;
/*     */     
/*     */     public GuardianMoveHelper(EntityGuardian guardian) {
/* 555 */       super((EntityLiving)guardian);
/* 556 */       this.entityGuardian = guardian;
/*     */     }
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 560 */       if (this.update && !this.entityGuardian.getNavigator().noPath()) {
/* 561 */         double d0 = this.posX - this.entityGuardian.posX;
/* 562 */         double d1 = this.posY - this.entityGuardian.posY;
/* 563 */         double d2 = this.posZ - this.entityGuardian.posZ;
/* 564 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 565 */         d3 = MathHelper.sqrt_double(d3);
/* 566 */         d1 /= d3;
/* 567 */         float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
/* 568 */         this.entityGuardian.rotationYaw = limitAngle(this.entityGuardian.rotationYaw, f, 30.0F);
/* 569 */         this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
/* 570 */         float f1 = (float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 571 */         this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + (f1 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
/* 572 */         double d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5D) * 0.05D;
/* 573 */         double d5 = Math.cos((this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
/* 574 */         double d6 = Math.sin((this.entityGuardian.rotationYaw * 3.1415927F / 180.0F));
/* 575 */         this.entityGuardian.motionX += d4 * d5;
/* 576 */         this.entityGuardian.motionZ += d4 * d6;
/* 577 */         d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75D) * 0.05D;
/* 578 */         this.entityGuardian.motionY += d4 * (d6 + d5) * 0.25D;
/* 579 */         this.entityGuardian.motionY += this.entityGuardian.getAIMoveSpeed() * d1 * 0.1D;
/* 580 */         EntityLookHelper entitylookhelper = this.entityGuardian.getLookHelper();
/* 581 */         double d7 = this.entityGuardian.posX + d0 / d3 * 2.0D;
/* 582 */         double d8 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + d1 / d3 * 1.0D;
/* 583 */         double d9 = this.entityGuardian.posZ + d2 / d3 * 2.0D;
/* 584 */         double d10 = entitylookhelper.getLookPosX();
/* 585 */         double d11 = entitylookhelper.getLookPosY();
/* 586 */         double d12 = entitylookhelper.getLookPosZ();
/*     */         
/* 588 */         if (!entitylookhelper.getIsLooking()) {
/* 589 */           d10 = d7;
/* 590 */           d11 = d8;
/* 591 */           d12 = d9;
/*     */         } 
/*     */         
/* 594 */         this.entityGuardian.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
/* 595 */         this.entityGuardian.func_175476_l(true);
/*     */       } else {
/* 597 */         this.entityGuardian.setAIMoveSpeed(0.0F);
/* 598 */         this.entityGuardian.func_175476_l(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianTargetSelector implements Predicate<EntityLivingBase> {
/*     */     private EntityGuardian parentEntity;
/*     */     
/*     */     public GuardianTargetSelector(EntityGuardian guardian) {
/* 607 */       this.parentEntity = guardian;
/*     */     }
/*     */     
/*     */     public boolean apply(EntityLivingBase p_apply_1_) {
/* 611 */       return ((p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof net.minecraft.entity.passive.EntitySquid) && p_apply_1_.getDistanceSqToEntity((Entity)this.parentEntity) > 9.0D);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */