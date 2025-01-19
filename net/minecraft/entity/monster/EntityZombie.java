/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIBreakDoor;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityZombie
/*     */   extends EntityMob
/*     */ {
/*  51 */   protected static final IAttribute reinforcementChance = (IAttribute)(new RangedAttribute(null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
/*  52 */   private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
/*  53 */   private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
/*  54 */   private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor((EntityLiving)this);
/*     */ 
/*     */ 
/*     */   
/*     */   private int conversionTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBreakDoorsTaskSet = false;
/*     */ 
/*     */   
/*  65 */   private float zombieWidth = -1.0F;
/*     */ 
/*     */   
/*     */   private float zombieHeight;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityZombie(World worldIn) {
/*  73 */     super(worldIn);
/*  74 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*  75 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  76 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  77 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  78 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  79 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  80 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  81 */     applyEntityAI();
/*  82 */     setSize(0.6F, 1.95F);
/*     */   }
/*     */   
/*     */   protected void applyEntityAI() {
/*  86 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
/*  87 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0D, true));
/*  88 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIMoveThroughVillage(this, 1.0D, false));
/*  89 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
/*  90 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  91 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
/*  92 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  96 */     super.applyEntityAttributes();
/*  97 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
/*  98 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*  99 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
/* 100 */     getAttributeMap().registerAttribute(reinforcementChance).setBaseValue(this.rand.nextDouble() * 0.10000000149011612D);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 104 */     super.entityInit();
/* 105 */     getDataWatcher().addObject(12, Byte.valueOf((byte)0));
/* 106 */     getDataWatcher().addObject(13, Byte.valueOf((byte)0));
/* 107 */     getDataWatcher().addObject(14, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 114 */     int i = super.getTotalArmorValue() + 2;
/*     */     
/* 116 */     if (i > 20) {
/* 117 */       i = 20;
/*     */     }
/*     */     
/* 120 */     return i;
/*     */   }
/*     */   
/*     */   public boolean isBreakDoorsTaskSet() {
/* 124 */     return this.isBreakDoorsTaskSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBreakDoorsAItask(boolean par1) {
/* 131 */     if (this.isBreakDoorsTaskSet != par1) {
/* 132 */       this.isBreakDoorsTaskSet = par1;
/*     */       
/* 134 */       if (par1) {
/* 135 */         this.tasks.addTask(1, (EntityAIBase)this.breakDoor);
/*     */       } else {
/* 137 */         this.tasks.removeTask((EntityAIBase)this.breakDoor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 146 */     return (getDataWatcher().getWatchableObjectByte(12) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 153 */     if (isChild()) {
/* 154 */       this.experienceValue = (int)(this.experienceValue * 2.5F);
/*     */     }
/*     */     
/* 157 */     return super.getExperiencePoints(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChild(boolean childZombie) {
/* 164 */     getDataWatcher().updateObject(12, Byte.valueOf((byte)(childZombie ? 1 : 0)));
/*     */     
/* 166 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/* 167 */       IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 168 */       iattributeinstance.removeModifier(babySpeedBoostModifier);
/*     */       
/* 170 */       if (childZombie) {
/* 171 */         iattributeinstance.applyModifier(babySpeedBoostModifier);
/*     */       }
/*     */     } 
/*     */     
/* 175 */     setChildSize(childZombie);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVillager() {
/* 182 */     return (getDataWatcher().getWatchableObjectByte(13) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVillager(boolean villager) {
/* 189 */     getDataWatcher().updateObject(13, Byte.valueOf((byte)(villager ? 1 : 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 197 */     if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !isChild()) {
/* 198 */       float f = getBrightness(1.0F);
/* 199 */       BlockPos blockpos = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
/*     */       
/* 201 */       if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canSeeSky(blockpos)) {
/* 202 */         boolean flag = true;
/* 203 */         ItemStack itemstack = getEquipmentInSlot(4);
/*     */         
/* 205 */         if (itemstack != null) {
/* 206 */           if (itemstack.isItemStackDamageable()) {
/* 207 */             itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
/*     */             
/* 209 */             if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
/* 210 */               renderBrokenItemStack(itemstack);
/* 211 */               setCurrentItemOrArmor(4, null);
/*     */             } 
/*     */           } 
/*     */           
/* 215 */           flag = false;
/*     */         } 
/*     */         
/* 218 */         if (flag) {
/* 219 */           setFire(8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     if (isRiding() && getAttackTarget() != null && this.ridingEntity instanceof EntityChicken) {
/* 225 */       ((EntityLiving)this.ridingEntity).getNavigator().setPath(getNavigator().getPath(), 1.5D);
/*     */     }
/*     */     
/* 228 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 235 */     if (super.attackEntityFrom(source, amount)) {
/* 236 */       EntityLivingBase entitylivingbase = getAttackTarget();
/*     */       
/* 238 */       if (entitylivingbase == null && source.getEntity() instanceof EntityLivingBase) {
/* 239 */         entitylivingbase = (EntityLivingBase)source.getEntity();
/*     */       }
/*     */       
/* 242 */       if (entitylivingbase != null && this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.rand.nextFloat() < getEntityAttribute(reinforcementChance).getAttributeValue()) {
/* 243 */         int i = MathHelper.floor_double(this.posX);
/* 244 */         int j = MathHelper.floor_double(this.posY);
/* 245 */         int k = MathHelper.floor_double(this.posZ);
/* 246 */         EntityZombie entityzombie = new EntityZombie(this.worldObj);
/*     */         
/* 248 */         for (int l = 0; l < 50; l++) {
/* 249 */           int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/* 250 */           int j1 = j + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/* 251 */           int k1 = k + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
/*     */           
/* 253 */           if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(i1, j1 - 1, k1)) && this.worldObj.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10) {
/* 254 */             entityzombie.setPosition(i1, j1, k1);
/*     */             
/* 256 */             if (!this.worldObj.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) && this.worldObj.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), (Entity)entityzombie) && this.worldObj.getCollidingBoundingBoxes((Entity)entityzombie, entityzombie.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(entityzombie.getEntityBoundingBox())) {
/* 257 */               this.worldObj.spawnEntityInWorld((Entity)entityzombie);
/* 258 */               entityzombie.setAttackTarget(entitylivingbase);
/* 259 */               entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
/* 260 */               getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
/* 261 */               entityzombie.getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 268 */       return true;
/*     */     } 
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 278 */     if (!this.worldObj.isRemote && isConverting()) {
/* 279 */       int i = getConversionTimeBoost();
/* 280 */       this.conversionTime -= i;
/*     */       
/* 282 */       if (this.conversionTime <= 0) {
/* 283 */         convertToVillager();
/*     */       }
/*     */     } 
/*     */     
/* 287 */     super.onUpdate();
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 291 */     boolean flag = super.attackEntityAsMob(entityIn);
/*     */     
/* 293 */     if (flag) {
/* 294 */       int i = this.worldObj.getDifficulty().getDifficultyId();
/*     */       
/* 296 */       if (getHeldItem() == null && isBurning() && this.rand.nextFloat() < i * 0.3F) {
/* 297 */         entityIn.setFire(2 * i);
/*     */       }
/*     */     } 
/*     */     
/* 301 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 308 */     return "mob.zombie.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 315 */     return "mob.zombie.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 322 */     return "mob.zombie.death";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 326 */     playSound("mob.zombie.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 330 */     return Items.rotten_flesh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 337 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 344 */     switch (this.rand.nextInt(3)) {
/*     */       case 0:
/* 346 */         dropItem(Items.iron_ingot, 1);
/*     */         break;
/*     */       
/*     */       case 1:
/* 350 */         dropItem(Items.carrot, 1);
/*     */         break;
/*     */       
/*     */       case 2:
/* 354 */         dropItem(Items.potato, 1);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 362 */     super.setEquipmentBasedOnDifficulty(difficulty);
/*     */     
/* 364 */     if (this.rand.nextFloat() < ((this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.05F : 0.01F)) {
/* 365 */       int i = this.rand.nextInt(3);
/*     */       
/* 367 */       if (i == 0) {
/* 368 */         setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
/*     */       } else {
/* 370 */         setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 379 */     super.writeEntityToNBT(tagCompound);
/*     */     
/* 381 */     if (isChild()) {
/* 382 */       tagCompound.setBoolean("IsBaby", true);
/*     */     }
/*     */     
/* 385 */     if (isVillager()) {
/* 386 */       tagCompound.setBoolean("IsVillager", true);
/*     */     }
/*     */     
/* 389 */     tagCompound.setInteger("ConversionTime", isConverting() ? this.conversionTime : -1);
/* 390 */     tagCompound.setBoolean("CanBreakDoors", isBreakDoorsTaskSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 397 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 399 */     if (tagCompund.getBoolean("IsBaby")) {
/* 400 */       setChild(true);
/*     */     }
/*     */     
/* 403 */     if (tagCompund.getBoolean("IsVillager")) {
/* 404 */       setVillager(true);
/*     */     }
/*     */     
/* 407 */     if (tagCompund.hasKey("ConversionTime", 99) && tagCompund.getInteger("ConversionTime") > -1) {
/* 408 */       startConversion(tagCompund.getInteger("ConversionTime"));
/*     */     }
/*     */     
/* 411 */     setBreakDoorsAItask(tagCompund.getBoolean("CanBreakDoors"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillEntity(EntityLivingBase entityLivingIn) {
/* 418 */     super.onKillEntity(entityLivingIn);
/*     */     
/* 420 */     if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager) {
/* 421 */       if (this.worldObj.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
/*     */         return;
/*     */       }
/*     */       
/* 425 */       EntityLiving entityliving = (EntityLiving)entityLivingIn;
/* 426 */       EntityZombie entityzombie = new EntityZombie(this.worldObj);
/* 427 */       entityzombie.copyLocationAndAnglesFrom((Entity)entityLivingIn);
/* 428 */       this.worldObj.removeEntity((Entity)entityLivingIn);
/* 429 */       entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
/* 430 */       entityzombie.setVillager(true);
/*     */       
/* 432 */       if (entityLivingIn.isChild()) {
/* 433 */         entityzombie.setChild(true);
/*     */       }
/*     */       
/* 436 */       entityzombie.setNoAI(entityliving.isAIDisabled());
/*     */       
/* 438 */       if (entityliving.hasCustomName()) {
/* 439 */         entityzombie.setCustomNameTag(entityliving.getCustomNameTag());
/* 440 */         entityzombie.setAlwaysRenderNameTag(entityliving.getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 443 */       this.worldObj.spawnEntityInWorld((Entity)entityzombie);
/* 444 */       this.worldObj.playAuxSFXAtEntity(null, 1016, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 449 */     float f = 1.74F;
/*     */     
/* 451 */     if (isChild()) {
/* 452 */       f = (float)(f - 0.81D);
/*     */     }
/*     */     
/* 455 */     return f;
/*     */   }
/*     */   
/*     */   protected boolean func_175448_a(ItemStack stack) {
/* 459 */     return (stack.getItem() == Items.egg && isChild() && isRiding()) ? false : super.func_175448_a(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 467 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 468 */     float f = difficulty.getClampedAdditionalDifficulty();
/* 469 */     setCanPickUpLoot((this.rand.nextFloat() < 0.55F * f));
/*     */     
/* 471 */     if (livingdata == null) {
/* 472 */       livingdata = new GroupData((this.worldObj.rand.nextFloat() < 0.05F), (this.worldObj.rand.nextFloat() < 0.05F), null);
/*     */     }
/*     */     
/* 475 */     if (livingdata instanceof GroupData) {
/* 476 */       GroupData entityzombie$groupdata = (GroupData)livingdata;
/*     */       
/* 478 */       if (entityzombie$groupdata.isVillager) {
/* 479 */         setVillager(true);
/*     */       }
/*     */       
/* 482 */       if (entityzombie$groupdata.isChild) {
/* 483 */         setChild(true);
/*     */         
/* 485 */         if (this.worldObj.rand.nextFloat() < 0.05D) {
/* 486 */           List<EntityChicken> list = this.worldObj.getEntitiesWithinAABB(EntityChicken.class, getEntityBoundingBox().expand(5.0D, 3.0D, 5.0D), EntitySelectors.IS_STANDALONE);
/*     */           
/* 488 */           if (!list.isEmpty()) {
/* 489 */             EntityChicken entitychicken = list.get(0);
/* 490 */             entitychicken.setChickenJockey(true);
/* 491 */             mountEntity((Entity)entitychicken);
/*     */           } 
/* 493 */         } else if (this.worldObj.rand.nextFloat() < 0.05D) {
/* 494 */           EntityChicken entitychicken1 = new EntityChicken(this.worldObj);
/* 495 */           entitychicken1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 496 */           entitychicken1.onInitialSpawn(difficulty, null);
/* 497 */           entitychicken1.setChickenJockey(true);
/* 498 */           this.worldObj.spawnEntityInWorld((Entity)entitychicken1);
/* 499 */           mountEntity((Entity)entitychicken1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 504 */     setBreakDoorsAItask((this.rand.nextFloat() < f * 0.1F));
/* 505 */     setEquipmentBasedOnDifficulty(difficulty);
/* 506 */     setEnchantmentBasedOnDifficulty(difficulty);
/*     */     
/* 508 */     if (getEquipmentInSlot(4) == null) {
/* 509 */       Calendar calendar = this.worldObj.getCurrentDate();
/*     */       
/* 511 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
/* 512 */         setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.lit_pumpkin : Blocks.pumpkin));
/* 513 */         this.equipmentDropChances[4] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 517 */     getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
/* 518 */     double d0 = this.rand.nextDouble() * 1.5D * f;
/*     */     
/* 520 */     if (d0 > 1.0D) {
/* 521 */       getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
/*     */     }
/*     */     
/* 524 */     if (this.rand.nextFloat() < f * 0.05F) {
/* 525 */       getEntityAttribute(reinforcementChance).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
/* 526 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
/* 527 */       setBreakDoorsAItask(true);
/*     */     } 
/*     */     
/* 530 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 537 */     ItemStack itemstack = player.getCurrentEquippedItem();
/*     */     
/* 539 */     if (itemstack != null && itemstack.getItem() == Items.golden_apple && itemstack.getMetadata() == 0 && isVillager() && isPotionActive(Potion.weakness)) {
/* 540 */       if (!player.capabilities.isCreativeMode) {
/* 541 */         itemstack.stackSize--;
/*     */       }
/*     */       
/* 544 */       if (itemstack.stackSize <= 0) {
/* 545 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*     */       }
/*     */       
/* 548 */       if (!this.worldObj.isRemote) {
/* 549 */         startConversion(this.rand.nextInt(2401) + 3600);
/*     */       }
/*     */       
/* 552 */       return true;
/*     */     } 
/* 554 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void startConversion(int ticks) {
/* 563 */     this.conversionTime = ticks;
/* 564 */     getDataWatcher().updateObject(14, Byte.valueOf((byte)1));
/* 565 */     removePotionEffect(Potion.weakness.id);
/* 566 */     addPotionEffect(new PotionEffect(Potion.damageBoost.id, ticks, Math.min(this.worldObj.getDifficulty().getDifficultyId() - 1, 0)));
/* 567 */     this.worldObj.setEntityState((Entity)this, (byte)16);
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 571 */     if (id == 16) {
/* 572 */       if (!isSilent()) {
/* 573 */         this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*     */     } else {
/* 576 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 584 */     return !isConverting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConverting() {
/* 591 */     return (getDataWatcher().getWatchableObjectByte(14) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void convertToVillager() {
/* 598 */     EntityVillager entityvillager = new EntityVillager(this.worldObj);
/* 599 */     entityvillager.copyLocationAndAnglesFrom((Entity)this);
/* 600 */     entityvillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), null);
/* 601 */     entityvillager.setLookingForHome();
/*     */     
/* 603 */     if (isChild()) {
/* 604 */       entityvillager.setGrowingAge(-24000);
/*     */     }
/*     */     
/* 607 */     this.worldObj.removeEntity((Entity)this);
/* 608 */     entityvillager.setNoAI(isAIDisabled());
/*     */     
/* 610 */     if (hasCustomName()) {
/* 611 */       entityvillager.setCustomNameTag(getCustomNameTag());
/* 612 */       entityvillager.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */     } 
/*     */     
/* 615 */     this.worldObj.spawnEntityInWorld((Entity)entityvillager);
/* 616 */     entityvillager.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
/* 617 */     this.worldObj.playAuxSFXAtEntity(null, 1017, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getConversionTimeBoost() {
/* 624 */     int i = 1;
/*     */     
/* 626 */     if (this.rand.nextFloat() < 0.01F) {
/* 627 */       int j = 0;
/* 628 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */       
/* 630 */       for (int k = (int)this.posX - 4; k < (int)this.posX + 4 && j < 14; k++) {
/* 631 */         for (int l = (int)this.posY - 4; l < (int)this.posY + 4 && j < 14; l++) {
/* 632 */           for (int i1 = (int)this.posZ - 4; i1 < (int)this.posZ + 4 && j < 14; i1++) {
/* 633 */             Block block = this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos.set(k, l, i1)).getBlock();
/*     */             
/* 635 */             if (block == Blocks.iron_bars || block == Blocks.bed) {
/* 636 */               if (this.rand.nextFloat() < 0.3F) {
/* 637 */                 i++;
/*     */               }
/*     */               
/* 640 */               j++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 647 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChildSize(boolean isChild) {
/* 654 */     multiplySize(isChild ? 0.5F : 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setSize(float width, float height) {
/* 661 */     boolean flag = (this.zombieWidth > 0.0F && this.zombieHeight > 0.0F);
/* 662 */     this.zombieWidth = width;
/* 663 */     this.zombieHeight = height;
/*     */     
/* 665 */     if (!flag) {
/* 666 */       multiplySize(1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void multiplySize(float size) {
/* 674 */     super.setSize(this.zombieWidth * size, this.zombieHeight * size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 681 */     return isChild() ? 0.0D : -0.35D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 688 */     super.onDeath(cause);
/*     */     
/* 690 */     if (cause.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
/* 691 */       ((EntityCreeper)cause.getEntity()).func_175493_co();
/* 692 */       entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   class GroupData implements IEntityLivingData {
/*     */     public boolean isChild;
/*     */     public boolean isVillager;
/*     */     
/*     */     private GroupData(boolean isBaby, boolean isVillagerZombie) {
/* 701 */       this.isChild = false;
/* 702 */       this.isVillager = false;
/* 703 */       this.isChild = isBaby;
/* 704 */       this.isVillager = isVillagerZombie;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */