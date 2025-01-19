/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearest;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class EntitySlime extends EntityLiving implements IMob {
/*     */   public float squishAmount;
/*     */   public float squishFactor;
/*     */   public float prevSquishFactor;
/*     */   private boolean wasOnGround;
/*     */   
/*     */   public EntitySlime(World worldIn) {
/*  35 */     super(worldIn);
/*  36 */     this.moveHelper = new SlimeMoveHelper(this);
/*  37 */     this.tasks.addTask(1, new AISlimeFloat(this));
/*  38 */     this.tasks.addTask(2, new AISlimeAttack(this));
/*  39 */     this.tasks.addTask(3, new AISlimeFaceRandom(this));
/*  40 */     this.tasks.addTask(5, new AISlimeHop(this));
/*  41 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIFindEntityNearestPlayer(this));
/*  42 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAIFindEntityNearest(this, EntityIronGolem.class));
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  46 */     super.entityInit();
/*  47 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)1));
/*     */   }
/*     */   
/*     */   protected void setSlimeSize(int size) {
/*  51 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)size));
/*  52 */     setSize(0.51000005F * size, 0.51000005F * size);
/*  53 */     setPosition(this.posX, this.posY, this.posZ);
/*  54 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((size * size));
/*  55 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((0.2F + 0.1F * size));
/*  56 */     setHealth(getMaxHealth());
/*  57 */     this.experienceValue = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlimeSize() {
/*  64 */     return this.dataWatcher.getWatchableObjectByte(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  71 */     super.writeEntityToNBT(tagCompound);
/*  72 */     tagCompound.setInteger("Size", getSlimeSize() - 1);
/*  73 */     tagCompound.setBoolean("wasOnGround", this.wasOnGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  80 */     super.readEntityFromNBT(tagCompund);
/*  81 */     int i = tagCompund.getInteger("Size");
/*     */     
/*  83 */     if (i < 0) {
/*  84 */       i = 0;
/*     */     }
/*     */     
/*  87 */     setSlimeSize(i + 1);
/*  88 */     this.wasOnGround = tagCompund.getBoolean("wasOnGround");
/*     */   }
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/*  92 */     return EnumParticleTypes.SLIME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJumpSound() {
/*  99 */     return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 106 */     if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && getSlimeSize() > 0) {
/* 107 */       this.isDead = true;
/*     */     }
/*     */     
/* 110 */     this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
/* 111 */     this.prevSquishFactor = this.squishFactor;
/* 112 */     super.onUpdate();
/*     */     
/* 114 */     if (this.onGround && !this.wasOnGround) {
/* 115 */       int i = getSlimeSize();
/*     */       
/* 117 */       for (int j = 0; j < i * 8; j++) {
/* 118 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 119 */         float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
/* 120 */         float f2 = MathHelper.sin(f) * i * 0.5F * f1;
/* 121 */         float f3 = MathHelper.cos(f) * i * 0.5F * f1;
/* 122 */         World world = this.worldObj;
/* 123 */         EnumParticleTypes enumparticletypes = getParticleType();
/* 124 */         double d0 = this.posX + f2;
/* 125 */         double d1 = this.posZ + f3;
/* 126 */         world.spawnParticle(enumparticletypes, d0, (getEntityBoundingBox()).minY, d1, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */       
/* 129 */       if (makesSoundOnLand()) {
/* 130 */         playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/*     */       }
/*     */       
/* 133 */       this.squishAmount = -0.5F;
/* 134 */     } else if (!this.onGround && this.wasOnGround) {
/* 135 */       this.squishAmount = 1.0F;
/*     */     } 
/*     */     
/* 138 */     this.wasOnGround = this.onGround;
/* 139 */     alterSquishAmount();
/*     */   }
/*     */   
/*     */   protected void alterSquishAmount() {
/* 143 */     this.squishAmount *= 0.6F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getJumpDelay() {
/* 150 */     return this.rand.nextInt(20) + 10;
/*     */   }
/*     */   
/*     */   protected EntitySlime createInstance() {
/* 154 */     return new EntitySlime(this.worldObj);
/*     */   }
/*     */   
/*     */   public void onDataWatcherUpdate(int dataID) {
/* 158 */     if (dataID == 16) {
/* 159 */       int i = getSlimeSize();
/* 160 */       setSize(0.51000005F * i, 0.51000005F * i);
/* 161 */       this.rotationYaw = this.rotationYawHead;
/* 162 */       this.renderYawOffset = this.rotationYawHead;
/*     */       
/* 164 */       if (isInWater() && this.rand.nextInt(20) == 0) {
/* 165 */         resetHeight();
/*     */       }
/*     */     } 
/*     */     
/* 169 */     super.onDataWatcherUpdate(dataID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 176 */     int i = getSlimeSize();
/*     */     
/* 178 */     if (!this.worldObj.isRemote && i > 1 && getHealth() <= 0.0F) {
/* 179 */       int j = 2 + this.rand.nextInt(3);
/*     */       
/* 181 */       for (int k = 0; k < j; k++) {
/* 182 */         float f = ((k % 2) - 0.5F) * i / 4.0F;
/* 183 */         float f1 = ((k / 2) - 0.5F) * i / 4.0F;
/* 184 */         EntitySlime entityslime = createInstance();
/*     */         
/* 186 */         if (hasCustomName()) {
/* 187 */           entityslime.setCustomNameTag(getCustomNameTag());
/*     */         }
/*     */         
/* 190 */         if (isNoDespawnRequired()) {
/* 191 */           entityslime.enablePersistence();
/*     */         }
/*     */         
/* 194 */         entityslime.setSlimeSize(i / 2);
/* 195 */         entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
/* 196 */         this.worldObj.spawnEntityInWorld((Entity)entityslime);
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEntityCollision(Entity entityIn) {
/* 207 */     super.applyEntityCollision(entityIn);
/*     */     
/* 209 */     if (entityIn instanceof EntityIronGolem && canDamagePlayer()) {
/* 210 */       func_175451_e((EntityLivingBase)entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 218 */     if (canDamagePlayer()) {
/* 219 */       func_175451_e((EntityLivingBase)entityIn);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void func_175451_e(EntityLivingBase p_175451_1_) {
/* 224 */     int i = getSlimeSize();
/*     */     
/* 226 */     if (canEntityBeSeen((Entity)p_175451_1_) && getDistanceSqToEntity((Entity)p_175451_1_) < 0.6D * i * 0.6D * i && p_175451_1_.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), getAttackStrength())) {
/* 227 */       playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 228 */       applyEnchantments((EntityLivingBase)this, (Entity)p_175451_1_);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 233 */     return 0.625F * this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDamagePlayer() {
/* 240 */     return (getSlimeSize() > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getAttackStrength() {
/* 247 */     return getSlimeSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 254 */     return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 261 */     return "mob.slime." + ((getSlimeSize() > 1) ? "big" : "small");
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 265 */     return (getSlimeSize() == 1) ? Items.slime_ball : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 272 */     BlockPos blockpos = new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ));
/* 273 */     Chunk chunk = this.worldObj.getChunkFromBlockCoords(blockpos);
/*     */     
/* 275 */     if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1) {
/* 276 */       return false;
/*     */     }
/* 278 */     if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
/* 279 */       BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(blockpos);
/*     */       
/* 281 */       if (biomegenbase == BiomeGenBase.swampland && this.posY > 50.0D && this.posY < 70.0D && this.rand.nextFloat() < 0.5F && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getLightFromNeighbors(new BlockPos((Entity)this)) <= this.rand.nextInt(8)) {
/* 282 */         return super.getCanSpawnHere();
/*     */       }
/*     */       
/* 285 */       if (this.rand.nextInt(10) == 0 && chunk.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0D) {
/* 286 */         return super.getCanSpawnHere();
/*     */       }
/*     */     } 
/*     */     
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 298 */     return 0.4F * getSlimeSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 306 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean makesSoundOnJump() {
/* 313 */     return (getSlimeSize() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean makesSoundOnLand() {
/* 320 */     return (getSlimeSize() > 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jump() {
/* 327 */     this.motionY = 0.41999998688697815D;
/* 328 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 336 */     int i = this.rand.nextInt(3);
/*     */     
/* 338 */     if (i < 2 && this.rand.nextFloat() < 0.5F * difficulty.getClampedAdditionalDifficulty()) {
/* 339 */       i++;
/*     */     }
/*     */     
/* 342 */     int j = 1 << i;
/* 343 */     setSlimeSize(j);
/* 344 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */   
/*     */   static class AISlimeAttack extends EntityAIBase {
/*     */     private EntitySlime slime;
/*     */     private int field_179465_b;
/*     */     
/*     */     public AISlimeAttack(EntitySlime slimeIn) {
/* 352 */       this.slime = slimeIn;
/* 353 */       setMutexBits(2);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 357 */       EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
/* 358 */       return (entitylivingbase == null) ? false : (!entitylivingbase.isEntityAlive() ? false : (!(entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)));
/*     */     }
/*     */     
/*     */     public void startExecuting() {
/* 362 */       this.field_179465_b = 300;
/* 363 */       super.startExecuting();
/*     */     }
/*     */     
/*     */     public boolean continueExecuting() {
/* 367 */       EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
/* 368 */       return (entitylivingbase == null) ? false : (!entitylivingbase.isEntityAlive() ? false : ((entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) ? false : ((--this.field_179465_b > 0))));
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 372 */       this.slime.faceEntity((Entity)this.slime.getAttackTarget(), 10.0F, 10.0F);
/* 373 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.slime.rotationYaw, this.slime.canDamagePlayer());
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeFaceRandom extends EntityAIBase {
/*     */     private EntitySlime slime;
/*     */     private float field_179459_b;
/*     */     private int field_179460_c;
/*     */     
/*     */     public AISlimeFaceRandom(EntitySlime slimeIn) {
/* 383 */       this.slime = slimeIn;
/* 384 */       setMutexBits(2);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 388 */       return (this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava()));
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 392 */       if (--this.field_179460_c <= 0) {
/* 393 */         this.field_179460_c = 40 + this.slime.getRNG().nextInt(60);
/* 394 */         this.field_179459_b = this.slime.getRNG().nextInt(360);
/*     */       } 
/*     */       
/* 397 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.field_179459_b, false);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeFloat extends EntityAIBase {
/*     */     private EntitySlime slime;
/*     */     
/*     */     public AISlimeFloat(EntitySlime slimeIn) {
/* 405 */       this.slime = slimeIn;
/* 406 */       setMutexBits(5);
/* 407 */       ((PathNavigateGround)slimeIn.getNavigator()).setCanSwim(true);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 411 */       return !(!this.slime.isInWater() && !this.slime.isInLava());
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 415 */       if (this.slime.getRNG().nextFloat() < 0.8F) {
/* 416 */         this.slime.getJumpHelper().setJumping();
/*     */       }
/*     */       
/* 419 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeHop extends EntityAIBase {
/*     */     private EntitySlime slime;
/*     */     
/*     */     public AISlimeHop(EntitySlime slimeIn) {
/* 427 */       this.slime = slimeIn;
/* 428 */       setMutexBits(5);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 432 */       return true;
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 436 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class SlimeMoveHelper extends EntityMoveHelper {
/*     */     private float field_179922_g;
/*     */     private int field_179924_h;
/*     */     private EntitySlime slime;
/*     */     private boolean field_179923_j;
/*     */     
/*     */     public SlimeMoveHelper(EntitySlime slimeIn) {
/* 447 */       super(slimeIn);
/* 448 */       this.slime = slimeIn;
/*     */     }
/*     */     
/*     */     public void func_179920_a(float p_179920_1_, boolean p_179920_2_) {
/* 452 */       this.field_179922_g = p_179920_1_;
/* 453 */       this.field_179923_j = p_179920_2_;
/*     */     }
/*     */     
/*     */     public void setSpeed(double speedIn) {
/* 457 */       this.speed = speedIn;
/* 458 */       this.update = true;
/*     */     }
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 462 */       this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0F);
/* 463 */       this.entity.rotationYawHead = this.entity.rotationYaw;
/* 464 */       this.entity.renderYawOffset = this.entity.rotationYaw;
/*     */       
/* 466 */       if (!this.update) {
/* 467 */         this.entity.setMoveForward(0.0F);
/*     */       } else {
/* 469 */         this.update = false;
/*     */         
/* 471 */         if (this.entity.onGround) {
/* 472 */           this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
/*     */           
/* 474 */           if (this.field_179924_h-- <= 0) {
/* 475 */             this.field_179924_h = this.slime.getJumpDelay();
/*     */             
/* 477 */             if (this.field_179923_j) {
/* 478 */               this.field_179924_h /= 3;
/*     */             }
/*     */             
/* 481 */             this.slime.getJumpHelper().setJumping();
/*     */             
/* 483 */             if (this.slime.makesSoundOnJump()) {
/* 484 */               this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */             }
/*     */           } else {
/* 487 */             this.slime.moveStrafing = this.slime.moveForward = 0.0F;
/* 488 */             this.entity.setAIMoveSpeed(0.0F);
/*     */           } 
/*     */         } else {
/* 491 */           this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntitySlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */