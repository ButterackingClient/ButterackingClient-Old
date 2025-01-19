/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCarrot;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIMoveToBlock;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityJumpHelper;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityRabbit extends EntityAnimal {
/*  43 */   private int field_175540_bm = 0; private AIAvoidEntity<EntityWolf> aiAvoidWolves;
/*  44 */   private int field_175535_bn = 0;
/*     */   private boolean field_175536_bo = false;
/*     */   private boolean field_175537_bp = false;
/*  47 */   private int currentMoveTypeDuration = 0;
/*  48 */   private EnumMoveType moveType = EnumMoveType.HOP;
/*  49 */   private int carrotTicks = 0;
/*  50 */   private EntityPlayer field_175543_bt = null;
/*     */   
/*     */   public EntityRabbit(World worldIn) {
/*  53 */     super(worldIn);
/*  54 */     setSize(0.6F, 0.7F);
/*  55 */     this.jumpHelper = new RabbitJumpHelper(this);
/*  56 */     this.moveHelper = new RabbitMoveHelper(this);
/*  57 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  58 */     this.navigator.setHeightRequirement(2.5F);
/*  59 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  60 */     this.tasks.addTask(1, (EntityAIBase)new AIPanic(this, 1.33D));
/*  61 */     this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.carrot, false));
/*  62 */     this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.golden_carrot, false));
/*  63 */     this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Item.getItemFromBlock((Block)Blocks.yellow_flower), false));
/*  64 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMate(this, 0.8D));
/*  65 */     this.tasks.addTask(5, (EntityAIBase)new AIRaidFarm(this));
/*  66 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D));
/*  67 */     this.tasks.addTask(11, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 10.0F));
/*  68 */     this.aiAvoidWolves = new AIAvoidEntity<>(this, EntityWolf.class, 16.0F, 1.33D, 1.33D);
/*  69 */     this.tasks.addTask(4, (EntityAIBase)this.aiAvoidWolves);
/*  70 */     setMovementSpeed(0.0D);
/*     */   }
/*     */   
/*     */   protected float getJumpUpwardsMotion() {
/*  74 */     return (this.moveHelper.isUpdating() && this.moveHelper.getY() > this.posY + 0.5D) ? 0.5F : this.moveType.func_180074_b();
/*     */   }
/*     */   
/*     */   public void setMoveType(EnumMoveType type) {
/*  78 */     this.moveType = type;
/*     */   }
/*     */   
/*     */   public float func_175521_o(float p_175521_1_) {
/*  82 */     return (this.field_175535_bn == 0) ? 0.0F : ((this.field_175540_bm + p_175521_1_) / this.field_175535_bn);
/*     */   }
/*     */   
/*     */   public void setMovementSpeed(double newSpeed) {
/*  86 */     getNavigator().setSpeed(newSpeed);
/*  87 */     this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
/*     */   }
/*     */   
/*     */   public void setJumping(boolean jump, EnumMoveType moveTypeIn) {
/*  91 */     setJumping(jump);
/*     */     
/*  93 */     if (!jump) {
/*  94 */       if (this.moveType == EnumMoveType.ATTACK) {
/*  95 */         this.moveType = EnumMoveType.HOP;
/*     */       }
/*     */     } else {
/*  98 */       setMovementSpeed(1.5D * moveTypeIn.getSpeed());
/*  99 */       playSound(getJumpingSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */     } 
/*     */     
/* 102 */     this.field_175536_bo = jump;
/*     */   }
/*     */   
/*     */   public void doMovementAction(EnumMoveType movetype) {
/* 106 */     setJumping(true, movetype);
/* 107 */     this.field_175535_bn = movetype.func_180073_d();
/* 108 */     this.field_175540_bm = 0;
/*     */   }
/*     */   
/*     */   public boolean func_175523_cj() {
/* 112 */     return this.field_175536_bo;
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 116 */     super.entityInit();
/* 117 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void updateAITasks() {
/* 121 */     if (this.moveHelper.getSpeed() > 0.8D) {
/* 122 */       setMoveType(EnumMoveType.SPRINT);
/* 123 */     } else if (this.moveType != EnumMoveType.ATTACK) {
/* 124 */       setMoveType(EnumMoveType.HOP);
/*     */     } 
/*     */     
/* 127 */     if (this.currentMoveTypeDuration > 0) {
/* 128 */       this.currentMoveTypeDuration--;
/*     */     }
/*     */     
/* 131 */     if (this.carrotTicks > 0) {
/* 132 */       this.carrotTicks -= this.rand.nextInt(3);
/*     */       
/* 134 */       if (this.carrotTicks < 0) {
/* 135 */         this.carrotTicks = 0;
/*     */       }
/*     */     } 
/*     */     
/* 139 */     if (this.onGround) {
/* 140 */       if (!this.field_175537_bp) {
/* 141 */         setJumping(false, EnumMoveType.NONE);
/* 142 */         func_175517_cu();
/*     */       } 
/*     */       
/* 145 */       if (getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
/* 146 */         EntityLivingBase entitylivingbase = getAttackTarget();
/*     */         
/* 148 */         if (entitylivingbase != null && getDistanceSqToEntity((Entity)entitylivingbase) < 16.0D) {
/* 149 */           calculateRotationYaw(entitylivingbase.posX, entitylivingbase.posZ);
/* 150 */           this.moveHelper.setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, this.moveHelper.getSpeed());
/* 151 */           doMovementAction(EnumMoveType.ATTACK);
/* 152 */           this.field_175537_bp = true;
/*     */         } 
/*     */       } 
/*     */       
/* 156 */       RabbitJumpHelper entityrabbit$rabbitjumphelper = (RabbitJumpHelper)this.jumpHelper;
/*     */       
/* 158 */       if (!entityrabbit$rabbitjumphelper.getIsJumping()) {
/* 159 */         if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
/* 160 */           PathEntity pathentity = this.navigator.getPath();
/* 161 */           Vec3 vec3 = new Vec3(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
/*     */           
/* 163 */           if (pathentity != null && pathentity.getCurrentPathIndex() < pathentity.getCurrentPathLength()) {
/* 164 */             vec3 = pathentity.getPosition((Entity)this);
/*     */           }
/*     */           
/* 167 */           calculateRotationYaw(vec3.xCoord, vec3.zCoord);
/* 168 */           doMovementAction(this.moveType);
/*     */         } 
/* 170 */       } else if (!entityrabbit$rabbitjumphelper.func_180065_d()) {
/* 171 */         func_175518_cr();
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     this.field_175537_bp = this.onGround;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnRunningParticles() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateRotationYaw(double x, double z) {
/* 185 */     this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * 180.0D / Math.PI) - 90.0F;
/*     */   }
/*     */   
/*     */   private void func_175518_cr() {
/* 189 */     ((RabbitJumpHelper)this.jumpHelper).func_180066_a(true);
/*     */   }
/*     */   
/*     */   private void func_175520_cs() {
/* 193 */     ((RabbitJumpHelper)this.jumpHelper).func_180066_a(false);
/*     */   }
/*     */   
/*     */   private void updateMoveTypeDuration() {
/* 197 */     this.currentMoveTypeDuration = getMoveTypeDuration();
/*     */   }
/*     */   
/*     */   private void func_175517_cu() {
/* 201 */     updateMoveTypeDuration();
/* 202 */     func_175520_cs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 210 */     super.onLivingUpdate();
/*     */     
/* 212 */     if (this.field_175540_bm != this.field_175535_bn) {
/* 213 */       if (this.field_175540_bm == 0 && !this.worldObj.isRemote) {
/* 214 */         this.worldObj.setEntityState((Entity)this, (byte)1);
/*     */       }
/*     */       
/* 217 */       this.field_175540_bm++;
/* 218 */     } else if (this.field_175535_bn != 0) {
/* 219 */       this.field_175540_bm = 0;
/* 220 */       this.field_175535_bn = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 225 */     super.applyEntityAttributes();
/* 226 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/* 227 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 234 */     super.writeEntityToNBT(tagCompound);
/* 235 */     tagCompound.setInteger("RabbitType", getRabbitType());
/* 236 */     tagCompound.setInteger("MoreCarrotTicks", this.carrotTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 243 */     super.readEntityFromNBT(tagCompund);
/* 244 */     setRabbitType(tagCompund.getInteger("RabbitType"));
/* 245 */     this.carrotTicks = tagCompund.getInteger("MoreCarrotTicks");
/*     */   }
/*     */   
/*     */   protected String getJumpingSound() {
/* 249 */     return "mob.rabbit.hop";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 256 */     return "mob.rabbit.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 263 */     return "mob.rabbit.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 270 */     return "mob.rabbit.death";
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 274 */     if (getRabbitType() == 99) {
/* 275 */       playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 276 */       return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 8.0F);
/*     */     } 
/* 278 */     return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 286 */     return (getRabbitType() == 99) ? 8 : super.getTotalArmorValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 293 */     return isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 300 */     entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0F);
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
/* 311 */     int i = this.rand.nextInt(2) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 313 */     for (int j = 0; j < i; j++) {
/* 314 */       dropItem(Items.rabbit_hide, 1);
/*     */     }
/*     */     
/* 317 */     i = this.rand.nextInt(2);
/*     */     
/* 319 */     for (int k = 0; k < i; k++) {
/* 320 */       if (isBurning()) {
/* 321 */         dropItem(Items.cooked_rabbit, 1);
/*     */       } else {
/* 323 */         dropItem(Items.rabbit, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isRabbitBreedingItem(Item itemIn) {
/* 329 */     return !(itemIn != Items.carrot && itemIn != Items.golden_carrot && itemIn != Item.getItemFromBlock((Block)Blocks.yellow_flower));
/*     */   }
/*     */   
/*     */   public EntityRabbit createChild(EntityAgeable ageable) {
/* 333 */     EntityRabbit entityrabbit = new EntityRabbit(this.worldObj);
/*     */     
/* 335 */     if (ageable instanceof EntityRabbit) {
/* 336 */       entityrabbit.setRabbitType(this.rand.nextBoolean() ? getRabbitType() : ((EntityRabbit)ageable).getRabbitType());
/*     */     }
/*     */     
/* 339 */     return entityrabbit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 347 */     return (stack != null && isRabbitBreedingItem(stack.getItem()));
/*     */   }
/*     */   
/*     */   public int getRabbitType() {
/* 351 */     return this.dataWatcher.getWatchableObjectByte(18);
/*     */   }
/*     */   
/*     */   public void setRabbitType(int rabbitTypeId) {
/* 355 */     if (rabbitTypeId == 99) {
/* 356 */       this.tasks.removeTask((EntityAIBase)this.aiAvoidWolves);
/* 357 */       this.tasks.addTask(4, (EntityAIBase)new AIEvilAttack(this));
/* 358 */       this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
/* 359 */       this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
/* 360 */       this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityWolf.class, true));
/*     */       
/* 362 */       if (!hasCustomName()) {
/* 363 */         setCustomNameTag(StatCollector.translateToLocal("entity.KillerBunny.name"));
/*     */       }
/*     */     } 
/*     */     
/* 367 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)rabbitTypeId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 375 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 376 */     int i = this.rand.nextInt(6);
/* 377 */     boolean flag = false;
/*     */     
/* 379 */     if (livingdata instanceof RabbitTypeData) {
/* 380 */       i = ((RabbitTypeData)livingdata).typeData;
/* 381 */       flag = true;
/*     */     } else {
/* 383 */       livingdata = new RabbitTypeData(i);
/*     */     } 
/*     */     
/* 386 */     setRabbitType(i);
/*     */     
/* 388 */     if (flag) {
/* 389 */       setGrowingAge(-24000);
/*     */     }
/*     */     
/* 392 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCarrotEaten() {
/* 399 */     return (this.carrotTicks == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMoveTypeDuration() {
/* 406 */     return this.moveType.getDuration();
/*     */   }
/*     */   
/*     */   protected void createEatingParticles() {
/* 410 */     this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, new int[] { Block.getStateId(Blocks.carrots.getStateFromMeta(7)) });
/* 411 */     this.carrotTicks = 100;
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 415 */     if (id == 1) {
/* 416 */       createRunningParticles();
/* 417 */       this.field_175535_bn = 10;
/* 418 */       this.field_175540_bm = 0;
/*     */     } else {
/* 420 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T> {
/*     */     private EntityRabbit entityInstance;
/*     */     
/*     */     public AIAvoidEntity(EntityRabbit rabbit, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_) {
/* 428 */       super((EntityCreature)rabbit, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
/* 429 */       this.entityInstance = rabbit;
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 433 */       super.updateTask();
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIEvilAttack extends EntityAIAttackOnCollide {
/*     */     public AIEvilAttack(EntityRabbit rabbit) {
/* 439 */       super((EntityCreature)rabbit, EntityLivingBase.class, 1.4D, true);
/*     */     }
/*     */     
/*     */     protected double func_179512_a(EntityLivingBase attackTarget) {
/* 443 */       return (4.0F + attackTarget.width);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIPanic extends EntityAIPanic {
/*     */     private EntityRabbit theEntity;
/*     */     
/*     */     public AIPanic(EntityRabbit rabbit, double speedIn) {
/* 451 */       super((EntityCreature)rabbit, speedIn);
/* 452 */       this.theEntity = rabbit;
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 456 */       super.updateTask();
/* 457 */       this.theEntity.setMovementSpeed(this.speed);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIRaidFarm extends EntityAIMoveToBlock {
/*     */     private final EntityRabbit rabbit;
/*     */     private boolean field_179498_d;
/*     */     private boolean field_179499_e = false;
/*     */     
/*     */     public AIRaidFarm(EntityRabbit rabbitIn) {
/* 467 */       super((EntityCreature)rabbitIn, 0.699999988079071D, 16);
/* 468 */       this.rabbit = rabbitIn;
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 472 */       if (this.runDelay <= 0) {
/* 473 */         if (!this.rabbit.worldObj.getGameRules().getBoolean("mobGriefing")) {
/* 474 */           return false;
/*     */         }
/*     */         
/* 477 */         this.field_179499_e = false;
/* 478 */         this.field_179498_d = this.rabbit.isCarrotEaten();
/*     */       } 
/*     */       
/* 481 */       return super.shouldExecute();
/*     */     }
/*     */     
/*     */     public boolean continueExecuting() {
/* 485 */       return (this.field_179499_e && super.continueExecuting());
/*     */     }
/*     */     
/*     */     public void startExecuting() {
/* 489 */       super.startExecuting();
/*     */     }
/*     */     
/*     */     public void resetTask() {
/* 493 */       super.resetTask();
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 497 */       super.updateTask();
/* 498 */       this.rabbit.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, 10.0F, this.rabbit.getVerticalFaceSpeed());
/*     */       
/* 500 */       if (getIsAboveDestination()) {
/* 501 */         World world = this.rabbit.worldObj;
/* 502 */         BlockPos blockpos = this.destinationBlock.up();
/* 503 */         IBlockState iblockstate = world.getBlockState(blockpos);
/* 504 */         Block block = iblockstate.getBlock();
/*     */         
/* 506 */         if (this.field_179499_e && block instanceof BlockCarrot && ((Integer)iblockstate.getValue((IProperty)BlockCarrot.AGE)).intValue() == 7) {
/* 507 */           world.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/* 508 */           world.destroyBlock(blockpos, true);
/* 509 */           this.rabbit.createEatingParticles();
/*     */         } 
/*     */         
/* 512 */         this.field_179499_e = false;
/* 513 */         this.runDelay = 10;
/*     */       } 
/*     */     }
/*     */     
/*     */     protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/* 518 */       Block block = worldIn.getBlockState(pos).getBlock();
/*     */       
/* 520 */       if (block == Blocks.farmland) {
/* 521 */         pos = pos.up();
/* 522 */         IBlockState iblockstate = worldIn.getBlockState(pos);
/* 523 */         block = iblockstate.getBlock();
/*     */         
/* 525 */         if (block instanceof BlockCarrot && ((Integer)iblockstate.getValue((IProperty)BlockCarrot.AGE)).intValue() == 7 && this.field_179498_d && !this.field_179499_e) {
/* 526 */           this.field_179499_e = true;
/* 527 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 531 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   enum EnumMoveType {
/* 536 */     NONE(0.0F, 0.0F, 30, 1),
/* 537 */     HOP(0.8F, 0.2F, 20, 10),
/* 538 */     STEP(1.0F, 0.45F, 14, 14),
/* 539 */     SPRINT(1.75F, 0.4F, 1, 8),
/* 540 */     ATTACK(2.0F, 0.7F, 7, 8);
/*     */     
/*     */     private final float speed;
/*     */     private final float field_180077_g;
/*     */     private final int duration;
/*     */     private final int field_180085_i;
/*     */     
/*     */     EnumMoveType(float typeSpeed, float p_i45866_4_, int typeDuration, int p_i45866_6_) {
/* 548 */       this.speed = typeSpeed;
/* 549 */       this.field_180077_g = p_i45866_4_;
/* 550 */       this.duration = typeDuration;
/* 551 */       this.field_180085_i = p_i45866_6_;
/*     */     }
/*     */     
/*     */     public float getSpeed() {
/* 555 */       return this.speed;
/*     */     }
/*     */     
/*     */     public float func_180074_b() {
/* 559 */       return this.field_180077_g;
/*     */     }
/*     */     
/*     */     public int getDuration() {
/* 563 */       return this.duration;
/*     */     }
/*     */     
/*     */     public int func_180073_d() {
/* 567 */       return this.field_180085_i;
/*     */     }
/*     */   }
/*     */   
/*     */   public class RabbitJumpHelper extends EntityJumpHelper {
/*     */     private EntityRabbit theEntity;
/*     */     private boolean field_180068_d = false;
/*     */     
/*     */     public RabbitJumpHelper(EntityRabbit rabbit) {
/* 576 */       super((EntityLiving)rabbit);
/* 577 */       this.theEntity = rabbit;
/*     */     }
/*     */     
/*     */     public boolean getIsJumping() {
/* 581 */       return this.isJumping;
/*     */     }
/*     */     
/*     */     public boolean func_180065_d() {
/* 585 */       return this.field_180068_d;
/*     */     }
/*     */     
/*     */     public void func_180066_a(boolean p_180066_1_) {
/* 589 */       this.field_180068_d = p_180066_1_;
/*     */     }
/*     */     
/*     */     public void doJump() {
/* 593 */       if (this.isJumping) {
/* 594 */         this.theEntity.doMovementAction(EntityRabbit.EnumMoveType.STEP);
/* 595 */         this.isJumping = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class RabbitMoveHelper extends EntityMoveHelper {
/*     */     private EntityRabbit theEntity;
/*     */     
/*     */     public RabbitMoveHelper(EntityRabbit rabbit) {
/* 604 */       super((EntityLiving)rabbit);
/* 605 */       this.theEntity = rabbit;
/*     */     }
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 609 */       if (this.theEntity.onGround && !this.theEntity.func_175523_cj()) {
/* 610 */         this.theEntity.setMovementSpeed(0.0D);
/*     */       }
/*     */       
/* 613 */       super.onUpdateMoveHelper();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RabbitTypeData implements IEntityLivingData {
/*     */     public int typeData;
/*     */     
/*     */     public RabbitTypeData(int type) {
/* 621 */       this.typeData = type;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */