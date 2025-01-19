/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIBeg;
/*     */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITargetNonTamed;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ public class EntityWolf
/*     */   extends EntityTameable
/*     */ {
/*     */   private float headRotationCourse;
/*     */   private float headRotationCourseOld;
/*     */   private boolean isWet;
/*     */   private boolean isShaking;
/*     */   private float timeWolfIsShaking;
/*     */   private float prevTimeWolfIsShaking;
/*     */   
/*     */   public EntityWolf(World worldIn) {
/*  65 */     super(worldIn);
/*  66 */     setSize(0.6F, 0.8F);
/*  67 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  68 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  69 */     this.tasks.addTask(2, (EntityAIBase)this.aiSit);
/*  70 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4F));
/*  71 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide((EntityCreature)this, 1.0D, true));
/*  72 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
/*  73 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  74 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  75 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIBeg(this, 8.0F));
/*  76 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  77 */     this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  78 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIOwnerHurtByTarget(this));
/*  79 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIOwnerHurtTarget(this));
/*  80 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
/*  81 */     this.targetTasks.addTask(4, (EntityAIBase)new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/*  83 */               return !(!(p_apply_1_ instanceof EntitySheep) && !(p_apply_1_ instanceof EntityRabbit));
/*     */             }
/*     */           }));
/*  86 */     this.targetTasks.addTask(5, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntitySkeleton.class, false));
/*  87 */     setTamed(false);
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  91 */     super.applyEntityAttributes();
/*  92 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */     
/*  94 */     if (isTamed()) {
/*  95 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*     */     } else {
/*  97 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*     */     } 
/*     */     
/* 100 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
/* 101 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
/* 108 */     super.setAttackTarget(entitylivingbaseIn);
/*     */     
/* 110 */     if (entitylivingbaseIn == null) {
/* 111 */       setAngry(false);
/* 112 */     } else if (!isTamed()) {
/* 113 */       setAngry(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/* 118 */     this.dataWatcher.updateObject(18, Float.valueOf(getHealth()));
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 122 */     super.entityInit();
/* 123 */     this.dataWatcher.addObject(18, new Float(getHealth()));
/* 124 */     this.dataWatcher.addObject(19, new Byte((byte)0));
/* 125 */     this.dataWatcher.addObject(20, new Byte((byte)EnumDyeColor.RED.getMetadata()));
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 129 */     playSound("mob.wolf.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 136 */     super.writeEntityToNBT(tagCompound);
/* 137 */     tagCompound.setBoolean("Angry", isAngry());
/* 138 */     tagCompound.setByte("CollarColor", (byte)getCollarColor().getDyeDamage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 145 */     super.readEntityFromNBT(tagCompund);
/* 146 */     setAngry(tagCompund.getBoolean("Angry"));
/*     */     
/* 148 */     if (tagCompund.hasKey("CollarColor", 99)) {
/* 149 */       setCollarColor(EnumDyeColor.byDyeDamage(tagCompund.getByte("CollarColor")));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 157 */     return isAngry() ? "mob.wolf.growl" : ((this.rand.nextInt(3) == 0) ? ((isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 164 */     return "mob.wolf.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 171 */     return "mob.wolf.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 178 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 182 */     return Item.getItemById(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 190 */     super.onLivingUpdate();
/*     */     
/* 192 */     if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !hasPath() && this.onGround) {
/* 193 */       this.isShaking = true;
/* 194 */       this.timeWolfIsShaking = 0.0F;
/* 195 */       this.prevTimeWolfIsShaking = 0.0F;
/* 196 */       this.worldObj.setEntityState((Entity)this, (byte)8);
/*     */     } 
/*     */     
/* 199 */     if (!this.worldObj.isRemote && getAttackTarget() == null && isAngry()) {
/* 200 */       setAngry(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 208 */     super.onUpdate();
/* 209 */     this.headRotationCourseOld = this.headRotationCourse;
/*     */     
/* 211 */     if (isBegging()) {
/* 212 */       this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
/*     */     } else {
/* 214 */       this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
/*     */     } 
/*     */     
/* 217 */     if (isWet()) {
/* 218 */       this.isWet = true;
/* 219 */       this.isShaking = false;
/* 220 */       this.timeWolfIsShaking = 0.0F;
/* 221 */       this.prevTimeWolfIsShaking = 0.0F;
/* 222 */     } else if ((this.isWet || this.isShaking) && this.isShaking) {
/* 223 */       if (this.timeWolfIsShaking == 0.0F) {
/* 224 */         playSound("mob.wolf.shake", getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */       
/* 227 */       this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
/* 228 */       this.timeWolfIsShaking += 0.05F;
/*     */       
/* 230 */       if (this.prevTimeWolfIsShaking >= 2.0F) {
/* 231 */         this.isWet = false;
/* 232 */         this.isShaking = false;
/* 233 */         this.prevTimeWolfIsShaking = 0.0F;
/* 234 */         this.timeWolfIsShaking = 0.0F;
/*     */       } 
/*     */       
/* 237 */       if (this.timeWolfIsShaking > 0.4F) {
/* 238 */         float f = (float)(getEntityBoundingBox()).minY;
/* 239 */         int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);
/*     */         
/* 241 */         for (int j = 0; j < i; j++) {
/* 242 */           float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 243 */           float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 244 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f1, (f + 0.8F), this.posZ + f2, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWolfWet() {
/* 254 */     return this.isWet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getShadingWhileWet(float p_70915_1_) {
/* 261 */     return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
/*     */   }
/*     */   
/*     */   public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
/* 265 */     float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;
/*     */     
/* 267 */     if (f < 0.0F) {
/* 268 */       f = 0.0F;
/* 269 */     } else if (f > 1.0F) {
/* 270 */       f = 1.0F;
/*     */     } 
/*     */     
/* 273 */     return MathHelper.sin(f * 3.1415927F) * MathHelper.sin(f * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
/*     */   }
/*     */   
/*     */   public float getInterestedAngle(float p_70917_1_) {
/* 277 */     return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * 3.1415927F;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 281 */     return this.height * 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 289 */     return isSitting() ? 20 : super.getVerticalFaceSpeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 296 */     if (isEntityInvulnerable(source)) {
/* 297 */       return false;
/*     */     }
/* 299 */     Entity entity = source.getEntity();
/* 300 */     this.aiSit.setSitting(false);
/*     */     
/* 302 */     if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof net.minecraft.entity.projectile.EntityArrow)) {
/* 303 */       amount = (amount + 1.0F) / 2.0F;
/*     */     }
/*     */     
/* 306 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 311 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (int)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
/*     */     
/* 313 */     if (flag) {
/* 314 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     }
/*     */     
/* 317 */     return flag;
/*     */   }
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 321 */     super.setTamed(tamed);
/*     */     
/* 323 */     if (tamed) {
/* 324 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
/*     */     } else {
/* 326 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*     */     } 
/*     */     
/* 329 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 336 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 338 */     if (isTamed()) {
/* 339 */       if (itemstack != null) {
/* 340 */         if (itemstack.getItem() instanceof ItemFood) {
/* 341 */           ItemFood itemfood = (ItemFood)itemstack.getItem();
/*     */           
/* 343 */           if (itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
/* 344 */             if (!player.capabilities.isCreativeMode) {
/* 345 */               itemstack.stackSize--;
/*     */             }
/*     */             
/* 348 */             heal(itemfood.getHealAmount(itemstack));
/*     */             
/* 350 */             if (itemstack.stackSize <= 0) {
/* 351 */               player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*     */             }
/*     */             
/* 354 */             return true;
/*     */           } 
/* 356 */         } else if (itemstack.getItem() == Items.dye) {
/* 357 */           EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());
/*     */           
/* 359 */           if (enumdyecolor != getCollarColor()) {
/* 360 */             setCollarColor(enumdyecolor);
/*     */             
/* 362 */             if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
/* 363 */               player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*     */             }
/*     */             
/* 366 */             return true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 371 */       if (isOwner((EntityLivingBase)player) && !this.worldObj.isRemote && !isBreedingItem(itemstack)) {
/* 372 */         this.aiSit.setSitting(!isSitting());
/* 373 */         this.isJumping = false;
/* 374 */         this.navigator.clearPathEntity();
/* 375 */         setAttackTarget((EntityLivingBase)null);
/*     */       } 
/* 377 */     } else if (itemstack != null && itemstack.getItem() == Items.bone && !isAngry()) {
/* 378 */       if (!player.capabilities.isCreativeMode) {
/* 379 */         itemstack.stackSize--;
/*     */       }
/*     */       
/* 382 */       if (itemstack.stackSize <= 0) {
/* 383 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*     */       }
/*     */       
/* 386 */       if (!this.worldObj.isRemote) {
/* 387 */         if (this.rand.nextInt(3) == 0) {
/* 388 */           setTamed(true);
/* 389 */           this.navigator.clearPathEntity();
/* 390 */           setAttackTarget((EntityLivingBase)null);
/* 391 */           this.aiSit.setSitting(true);
/* 392 */           setHealth(20.0F);
/* 393 */           setOwnerId(player.getUniqueID().toString());
/* 394 */           playTameEffect(true);
/* 395 */           this.worldObj.setEntityState((Entity)this, (byte)7);
/*     */         } else {
/* 397 */           playTameEffect(false);
/* 398 */           this.worldObj.setEntityState((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 402 */       return true;
/*     */     } 
/*     */     
/* 405 */     return super.interact(player);
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 409 */     if (id == 8) {
/* 410 */       this.isShaking = true;
/* 411 */       this.timeWolfIsShaking = 0.0F;
/* 412 */       this.prevTimeWolfIsShaking = 0.0F;
/*     */     } else {
/* 414 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getTailRotation() {
/* 419 */     return isAngry() ? 1.5393804F : (isTamed() ? ((0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * 3.1415927F) : 0.62831855F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 427 */     return (stack == null) ? false : (!(stack.getItem() instanceof ItemFood) ? false : ((ItemFood)stack.getItem()).isWolfsFavoriteMeat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSpawnedInChunk() {
/* 434 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAngry() {
/* 441 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAngry(boolean angry) {
/* 448 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 450 */     if (angry) {
/* 451 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x2)));
/*     */     } else {
/* 453 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public EnumDyeColor getCollarColor() {
/* 458 */     return EnumDyeColor.byDyeDamage(this.dataWatcher.getWatchableObjectByte(20) & 0xF);
/*     */   }
/*     */   
/*     */   public void setCollarColor(EnumDyeColor collarcolor) {
/* 462 */     this.dataWatcher.updateObject(20, Byte.valueOf((byte)(collarcolor.getDyeDamage() & 0xF)));
/*     */   }
/*     */   
/*     */   public EntityWolf createChild(EntityAgeable ageable) {
/* 466 */     EntityWolf entitywolf = new EntityWolf(this.worldObj);
/* 467 */     String s = getOwnerId();
/*     */     
/* 469 */     if (s != null && s.trim().length() > 0) {
/* 470 */       entitywolf.setOwnerId(s);
/* 471 */       entitywolf.setTamed(true);
/*     */     } 
/*     */     
/* 474 */     return entitywolf;
/*     */   }
/*     */   
/*     */   public void setBegging(boolean beg) {
/* 478 */     if (beg) {
/* 479 */       this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
/*     */     } else {
/* 481 */       this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 489 */     if (otherAnimal == this)
/* 490 */       return false; 
/* 491 */     if (!isTamed())
/* 492 */       return false; 
/* 493 */     if (!(otherAnimal instanceof EntityWolf)) {
/* 494 */       return false;
/*     */     }
/* 496 */     EntityWolf entitywolf = (EntityWolf)otherAnimal;
/* 497 */     return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : ((isInLove() && entitywolf.isInLove())));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBegging() {
/* 502 */     return (this.dataWatcher.getWatchableObjectByte(19) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 509 */     return (!isTamed() && this.ticksExisted > 2400);
/*     */   }
/*     */   
/*     */   public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
/* 513 */     if (!(p_142018_1_ instanceof net.minecraft.entity.monster.EntityCreeper) && !(p_142018_1_ instanceof net.minecraft.entity.monster.EntityGhast)) {
/* 514 */       if (p_142018_1_ instanceof EntityWolf) {
/* 515 */         EntityWolf entitywolf = (EntityWolf)p_142018_1_;
/*     */         
/* 517 */         if (entitywolf.isTamed() && entitywolf.getOwner() == p_142018_2_) {
/* 518 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 522 */       return (p_142018_1_ instanceof EntityPlayer && p_142018_2_ instanceof EntityPlayer && !((EntityPlayer)p_142018_2_).canAttackPlayer((EntityPlayer)p_142018_1_)) ? false : (!(p_142018_1_ instanceof EntityHorse && ((EntityHorse)p_142018_1_).isTame()));
/*     */     } 
/* 524 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean allowLeashing() {
/* 529 */     return (!isAngry() && super.allowLeashing());
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */