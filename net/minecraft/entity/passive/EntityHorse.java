/*      */ package net.minecraft.entity.passive;
/*      */ import com.google.common.base.Predicate;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityAgeable;
/*      */ import net.minecraft.entity.EntityCreature;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IEntityLivingData;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.EntityAIBase;
/*      */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*      */ import net.minecraft.entity.ai.EntityAILookIdle;
/*      */ import net.minecraft.entity.ai.EntityAIMate;
/*      */ import net.minecraft.entity.ai.EntityAIPanic;
/*      */ import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
/*      */ import net.minecraft.entity.ai.EntityAIWander;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.AnimalChest;
/*      */ import net.minecraft.inventory.IInvBasic;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class EntityHorse extends EntityAnimal implements IInvBasic {
/*   44 */   private static final Predicate<Entity> horseBreedingSelector = new Predicate<Entity>() {
/*      */       public boolean apply(Entity p_apply_1_) {
/*   46 */         return (p_apply_1_ instanceof EntityHorse && ((EntityHorse)p_apply_1_).isBreeding());
/*      */       }
/*      */     };
/*   49 */   private static final IAttribute horseJumpStrength = (IAttribute)(new RangedAttribute(null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);
/*   50 */   private static final String[] horseArmorTextures = new String[] { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
/*   51 */   private static final String[] HORSE_ARMOR_TEXTURES_ABBR = new String[] { "", "meo", "goo", "dio" };
/*   52 */   private static final int[] armorValues = new int[] { 0, 5, 7, 11 };
/*   53 */   private static final String[] horseTextures = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
/*   54 */   private static final String[] HORSE_TEXTURES_ABBR = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
/*   55 */   private static final String[] horseMarkingTextures = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
/*   56 */   private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
/*      */   
/*      */   private int eatingHaystackCounter;
/*      */   
/*      */   private int openMouthCounter;
/*      */   
/*      */   private int jumpRearingCounter;
/*      */   
/*      */   public int field_110278_bp;
/*      */   
/*      */   public int field_110279_bq;
/*      */   
/*      */   protected boolean horseJumping;
/*      */   
/*      */   private AnimalChest horseChest;
/*      */   
/*      */   private boolean hasReproduced;
/*      */   protected int temper;
/*      */   protected float jumpPower;
/*      */   private boolean field_110294_bI;
/*      */   private float headLean;
/*      */   private float prevHeadLean;
/*      */   private float rearingAmount;
/*      */   private float prevRearingAmount;
/*      */   private float mouthOpenness;
/*      */   private float prevMouthOpenness;
/*      */   private int gallopTime;
/*      */   private String texturePrefix;
/*   84 */   private String[] horseTexturesArray = new String[3];
/*      */   private boolean field_175508_bO = false;
/*      */   
/*      */   public EntityHorse(World worldIn) {
/*   88 */     super(worldIn);
/*   89 */     setSize(1.4F, 1.6F);
/*   90 */     this.isImmuneToFire = false;
/*   91 */     setChested(false);
/*   92 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*   93 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*   94 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.2D));
/*   95 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIRunAroundLikeCrazy(this, 1.2D));
/*   96 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*   97 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.0D));
/*   98 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.7D));
/*   99 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  100 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  101 */     initHorseChest();
/*      */   }
/*      */   
/*      */   protected void entityInit() {
/*  105 */     super.entityInit();
/*  106 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*  107 */     this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
/*  108 */     this.dataWatcher.addObject(20, Integer.valueOf(0));
/*  109 */     this.dataWatcher.addObject(21, String.valueOf(""));
/*  110 */     this.dataWatcher.addObject(22, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   public void setHorseType(int type) {
/*  114 */     this.dataWatcher.updateObject(19, Byte.valueOf((byte)type));
/*  115 */     resetTexturePrefix();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHorseType() {
/*  122 */     return this.dataWatcher.getWatchableObjectByte(19);
/*      */   }
/*      */   
/*      */   public void setHorseVariant(int variant) {
/*  126 */     this.dataWatcher.updateObject(20, Integer.valueOf(variant));
/*  127 */     resetTexturePrefix();
/*      */   }
/*      */   
/*      */   public int getHorseVariant() {
/*  131 */     return this.dataWatcher.getWatchableObjectInt(20);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  138 */     if (hasCustomName()) {
/*  139 */       return getCustomNameTag();
/*      */     }
/*  141 */     int i = getHorseType();
/*      */     
/*  143 */     switch (i) {
/*      */       
/*      */       default:
/*  146 */         return StatCollector.translateToLocal("entity.horse.name");
/*      */       
/*      */       case 1:
/*  149 */         return StatCollector.translateToLocal("entity.donkey.name");
/*      */       
/*      */       case 2:
/*  152 */         return StatCollector.translateToLocal("entity.mule.name");
/*      */       
/*      */       case 3:
/*  155 */         return StatCollector.translateToLocal("entity.zombiehorse.name");
/*      */       case 4:
/*      */         break;
/*  158 */     }  return StatCollector.translateToLocal("entity.skeletonhorse.name");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getHorseWatchableBoolean(int p_110233_1_) {
/*  164 */     return ((this.dataWatcher.getWatchableObjectInt(16) & p_110233_1_) != 0);
/*      */   }
/*      */   
/*      */   private void setHorseWatchableBoolean(int p_110208_1_, boolean p_110208_2_) {
/*  168 */     int i = this.dataWatcher.getWatchableObjectInt(16);
/*      */     
/*  170 */     if (p_110208_2_) {
/*  171 */       this.dataWatcher.updateObject(16, Integer.valueOf(i | p_110208_1_));
/*      */     } else {
/*  173 */       this.dataWatcher.updateObject(16, Integer.valueOf(i & (p_110208_1_ ^ 0xFFFFFFFF)));
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isAdultHorse() {
/*  178 */     return !isChild();
/*      */   }
/*      */   
/*      */   public boolean isTame() {
/*  182 */     return getHorseWatchableBoolean(2);
/*      */   }
/*      */   
/*      */   public boolean func_110253_bW() {
/*  186 */     return isAdultHorse();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOwnerId() {
/*  193 */     return this.dataWatcher.getWatchableObjectString(21);
/*      */   }
/*      */   
/*      */   public void setOwnerId(String id) {
/*  197 */     this.dataWatcher.updateObject(21, id);
/*      */   }
/*      */   
/*      */   public float getHorseSize() {
/*  201 */     return 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScaleForAge(boolean p_98054_1_) {
/*  208 */     if (p_98054_1_) {
/*  209 */       setScale(getHorseSize());
/*      */     } else {
/*  211 */       setScale(1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isHorseJumping() {
/*  216 */     return this.horseJumping;
/*      */   }
/*      */   
/*      */   public void setHorseTamed(boolean tamed) {
/*  220 */     setHorseWatchableBoolean(2, tamed);
/*      */   }
/*      */   
/*      */   public void setHorseJumping(boolean jumping) {
/*  224 */     this.horseJumping = jumping;
/*      */   }
/*      */   
/*      */   public boolean allowLeashing() {
/*  228 */     return (!isUndead() && super.allowLeashing());
/*      */   }
/*      */   
/*      */   protected void func_142017_o(float p_142017_1_) {
/*  232 */     if (p_142017_1_ > 6.0F && isEatingHaystack()) {
/*  233 */       setEatingHaystack(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isChested() {
/*  238 */     return getHorseWatchableBoolean(8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHorseArmorIndexSynced() {
/*  245 */     return this.dataWatcher.getWatchableObjectInt(22);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getHorseArmorIndex(ItemStack itemStackIn) {
/*  252 */     if (itemStackIn == null) {
/*  253 */       return 0;
/*      */     }
/*  255 */     Item item = itemStackIn.getItem();
/*  256 */     return (item == Items.iron_horse_armor) ? 1 : ((item == Items.golden_horse_armor) ? 2 : ((item == Items.diamond_horse_armor) ? 3 : 0));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEatingHaystack() {
/*  261 */     return getHorseWatchableBoolean(32);
/*      */   }
/*      */   
/*      */   public boolean isRearing() {
/*  265 */     return getHorseWatchableBoolean(64);
/*      */   }
/*      */   
/*      */   public boolean isBreeding() {
/*  269 */     return getHorseWatchableBoolean(16);
/*      */   }
/*      */   
/*      */   public boolean getHasReproduced() {
/*  273 */     return this.hasReproduced;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHorseArmorStack(ItemStack itemStackIn) {
/*  280 */     this.dataWatcher.updateObject(22, Integer.valueOf(getHorseArmorIndex(itemStackIn)));
/*  281 */     resetTexturePrefix();
/*      */   }
/*      */   
/*      */   public void setBreeding(boolean breeding) {
/*  285 */     setHorseWatchableBoolean(16, breeding);
/*      */   }
/*      */   
/*      */   public void setChested(boolean chested) {
/*  289 */     setHorseWatchableBoolean(8, chested);
/*      */   }
/*      */   
/*      */   public void setHasReproduced(boolean hasReproducedIn) {
/*  293 */     this.hasReproduced = hasReproducedIn;
/*      */   }
/*      */   
/*      */   public void setHorseSaddled(boolean saddled) {
/*  297 */     setHorseWatchableBoolean(4, saddled);
/*      */   }
/*      */   
/*      */   public int getTemper() {
/*  301 */     return this.temper;
/*      */   }
/*      */   
/*      */   public void setTemper(int temperIn) {
/*  305 */     this.temper = temperIn;
/*      */   }
/*      */   
/*      */   public int increaseTemper(int p_110198_1_) {
/*  309 */     int i = MathHelper.clamp_int(getTemper() + p_110198_1_, 0, getMaxTemper());
/*  310 */     setTemper(i);
/*  311 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  318 */     Entity entity = source.getEntity();
/*  319 */     return (this.riddenByEntity != null && this.riddenByEntity.equals(entity)) ? false : super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/*  326 */     return armorValues[getHorseArmorIndexSynced()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/*  333 */     return (this.riddenByEntity == null);
/*      */   }
/*      */   
/*      */   public boolean prepareChunkForSpawn() {
/*  337 */     int i = MathHelper.floor_double(this.posX);
/*  338 */     int j = MathHelper.floor_double(this.posZ);
/*  339 */     this.worldObj.getBiomeGenForCoords(new BlockPos(i, 0, j));
/*  340 */     return true;
/*      */   }
/*      */   
/*      */   public void dropChests() {
/*  344 */     if (!this.worldObj.isRemote && isChested()) {
/*  345 */       dropItem(Item.getItemFromBlock((Block)Blocks.chest), 1);
/*  346 */       setChested(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void func_110266_cB() {
/*  351 */     openHorseMouth();
/*      */     
/*  353 */     if (!isSilent()) {
/*  354 */       this.worldObj.playSoundAtEntity((Entity)this, "eating", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*      */     }
/*      */   }
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/*  359 */     if (distance > 1.0F) {
/*  360 */       playSound("mob.horse.land", 0.4F, 1.0F);
/*      */     }
/*      */     
/*  363 */     int i = MathHelper.ceiling_float_int((distance * 0.5F - 3.0F) * damageMultiplier);
/*      */     
/*  365 */     if (i > 0) {
/*  366 */       attackEntityFrom(DamageSource.fall, i);
/*      */       
/*  368 */       if (this.riddenByEntity != null) {
/*  369 */         this.riddenByEntity.attackEntityFrom(DamageSource.fall, i);
/*      */       }
/*      */       
/*  372 */       Block block = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - this.prevRotationYaw, this.posZ)).getBlock();
/*      */       
/*  374 */       if (block.getMaterial() != Material.air && !isSilent()) {
/*  375 */         Block.SoundType block$soundtype = block.stepSound;
/*  376 */         this.worldObj.playSoundAtEntity((Entity)this, block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.5F, block$soundtype.getFrequency() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getChestSize() {
/*  385 */     int i = getHorseType();
/*  386 */     return (!isChested() || (i != 1 && i != 2)) ? 2 : 17;
/*      */   }
/*      */   
/*      */   private void initHorseChest() {
/*  390 */     AnimalChest animalchest = this.horseChest;
/*  391 */     this.horseChest = new AnimalChest("HorseChest", getChestSize());
/*  392 */     this.horseChest.setCustomName(getName());
/*      */     
/*  394 */     if (animalchest != null) {
/*  395 */       animalchest.removeInventoryChangeListener(this);
/*  396 */       int i = Math.min(animalchest.getSizeInventory(), this.horseChest.getSizeInventory());
/*      */       
/*  398 */       for (int j = 0; j < i; j++) {
/*  399 */         ItemStack itemstack = animalchest.getStackInSlot(j);
/*      */         
/*  401 */         if (itemstack != null) {
/*  402 */           this.horseChest.setInventorySlotContents(j, itemstack.copy());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  407 */     this.horseChest.addInventoryChangeListener(this);
/*  408 */     updateHorseSlots();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateHorseSlots() {
/*  415 */     if (!this.worldObj.isRemote) {
/*  416 */       setHorseSaddled((this.horseChest.getStackInSlot(0) != null));
/*      */       
/*  418 */       if (canWearArmor()) {
/*  419 */         setHorseArmorStack(this.horseChest.getStackInSlot(1));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInventoryChanged(InventoryBasic p_76316_1_) {
/*  428 */     int i = getHorseArmorIndexSynced();
/*  429 */     boolean flag = isHorseSaddled();
/*  430 */     updateHorseSlots();
/*      */     
/*  432 */     if (this.ticksExisted > 20) {
/*  433 */       if (i == 0 && i != getHorseArmorIndexSynced()) {
/*  434 */         playSound("mob.horse.armor", 0.5F, 1.0F);
/*  435 */       } else if (i != getHorseArmorIndexSynced()) {
/*  436 */         playSound("mob.horse.armor", 0.5F, 1.0F);
/*      */       } 
/*      */       
/*  439 */       if (!flag && isHorseSaddled()) {
/*  440 */         playSound("mob.horse.leather", 0.5F, 1.0F);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnHere() {
/*  449 */     prepareChunkForSpawn();
/*  450 */     return super.getCanSpawnHere();
/*      */   }
/*      */   
/*      */   protected EntityHorse getClosestHorse(Entity entityIn, double distance) {
/*  454 */     double d0 = Double.MAX_VALUE;
/*  455 */     Entity entity = null;
/*      */     
/*  457 */     for (Entity entity1 : this.worldObj.getEntitiesInAABBexcluding(entityIn, entityIn.getEntityBoundingBox().addCoord(distance, distance, distance), horseBreedingSelector)) {
/*  458 */       double d1 = entity1.getDistanceSq(entityIn.posX, entityIn.posY, entityIn.posZ);
/*      */       
/*  460 */       if (d1 < d0) {
/*  461 */         entity = entity1;
/*  462 */         d0 = d1;
/*      */       } 
/*      */     } 
/*      */     
/*  466 */     return (EntityHorse)entity;
/*      */   }
/*      */   
/*      */   public double getHorseJumpStrength() {
/*  470 */     return getEntityAttribute(horseJumpStrength).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  477 */     openHorseMouth();
/*  478 */     int i = getHorseType();
/*  479 */     return (i == 3) ? "mob.horse.zombie.death" : ((i == 4) ? "mob.horse.skeleton.death" : ((i != 1 && i != 2) ? "mob.horse.death" : "mob.horse.donkey.death"));
/*      */   }
/*      */   
/*      */   protected Item getDropItem() {
/*  483 */     boolean flag = (this.rand.nextInt(4) == 0);
/*  484 */     int i = getHorseType();
/*  485 */     return (i == 4) ? Items.bone : ((i == 3) ? (flag ? null : Items.rotten_flesh) : Items.leather);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  492 */     openHorseMouth();
/*      */     
/*  494 */     if (this.rand.nextInt(3) == 0) {
/*  495 */       makeHorseRear();
/*      */     }
/*      */     
/*  498 */     int i = getHorseType();
/*  499 */     return (i == 3) ? "mob.horse.zombie.hit" : ((i == 4) ? "mob.horse.skeleton.hit" : ((i != 1 && i != 2) ? "mob.horse.hit" : "mob.horse.donkey.hit"));
/*      */   }
/*      */   
/*      */   public boolean isHorseSaddled() {
/*  503 */     return getHorseWatchableBoolean(4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLivingSound() {
/*  510 */     openHorseMouth();
/*      */     
/*  512 */     if (this.rand.nextInt(10) == 0 && !isMovementBlocked()) {
/*  513 */       makeHorseRear();
/*      */     }
/*      */     
/*  516 */     int i = getHorseType();
/*  517 */     return (i == 3) ? "mob.horse.zombie.idle" : ((i == 4) ? "mob.horse.skeleton.idle" : ((i != 1 && i != 2) ? "mob.horse.idle" : "mob.horse.donkey.idle"));
/*      */   }
/*      */   
/*      */   protected String getAngrySoundName() {
/*  521 */     openHorseMouth();
/*  522 */     makeHorseRear();
/*  523 */     int i = getHorseType();
/*  524 */     return (i != 3 && i != 4) ? ((i != 1 && i != 2) ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
/*      */   }
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  528 */     Block.SoundType block$soundtype = blockIn.stepSound;
/*      */     
/*  530 */     if (this.worldObj.getBlockState(pos.up()).getBlock() == Blocks.snow_layer) {
/*  531 */       block$soundtype = Blocks.snow_layer.stepSound;
/*      */     }
/*      */     
/*  534 */     if (!blockIn.getMaterial().isLiquid()) {
/*  535 */       int i = getHorseType();
/*      */       
/*  537 */       if (this.riddenByEntity != null && i != 1 && i != 2) {
/*  538 */         this.gallopTime++;
/*      */         
/*  540 */         if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
/*  541 */           playSound("mob.horse.gallop", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */           
/*  543 */           if (i == 0 && this.rand.nextInt(10) == 0) {
/*  544 */             playSound("mob.horse.breathe", block$soundtype.getVolume() * 0.6F, block$soundtype.getFrequency());
/*      */           }
/*  546 */         } else if (this.gallopTime <= 5) {
/*  547 */           playSound("mob.horse.wood", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */         } 
/*  549 */       } else if (block$soundtype == Block.soundTypeWood) {
/*  550 */         playSound("mob.horse.wood", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */       } else {
/*  552 */         playSound("mob.horse.soft", block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  558 */     super.applyEntityAttributes();
/*  559 */     getAttributeMap().registerAttribute(horseJumpStrength);
/*  560 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0D);
/*  561 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSpawnedInChunk() {
/*  568 */     return 6;
/*      */   }
/*      */   
/*      */   public int getMaxTemper() {
/*  572 */     return 100;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/*  579 */     return 0.8F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTalkInterval() {
/*  586 */     return 400;
/*      */   }
/*      */   
/*      */   public boolean func_110239_cn() {
/*  590 */     return !(getHorseType() != 0 && getHorseArmorIndexSynced() <= 0);
/*      */   }
/*      */   
/*      */   private void resetTexturePrefix() {
/*  594 */     this.texturePrefix = null;
/*      */   }
/*      */   
/*      */   public boolean func_175507_cI() {
/*  598 */     return this.field_175508_bO;
/*      */   }
/*      */   
/*      */   private void setHorseTexturePaths() {
/*  602 */     this.texturePrefix = "horse/";
/*  603 */     this.horseTexturesArray[0] = null;
/*  604 */     this.horseTexturesArray[1] = null;
/*  605 */     this.horseTexturesArray[2] = null;
/*  606 */     int i = getHorseType();
/*  607 */     int j = getHorseVariant();
/*      */     
/*  609 */     if (i == 0) {
/*  610 */       int k = j & 0xFF;
/*  611 */       int l = (j & 0xFF00) >> 8;
/*      */       
/*  613 */       if (k >= horseTextures.length) {
/*  614 */         this.field_175508_bO = false;
/*      */         
/*      */         return;
/*      */       } 
/*  618 */       this.horseTexturesArray[0] = horseTextures[k];
/*  619 */       this.texturePrefix = String.valueOf(this.texturePrefix) + HORSE_TEXTURES_ABBR[k];
/*      */       
/*  621 */       if (l >= horseMarkingTextures.length) {
/*  622 */         this.field_175508_bO = false;
/*      */         
/*      */         return;
/*      */       } 
/*  626 */       this.horseTexturesArray[1] = horseMarkingTextures[l];
/*  627 */       this.texturePrefix = String.valueOf(this.texturePrefix) + HORSE_MARKING_TEXTURES_ABBR[l];
/*      */     } else {
/*  629 */       this.horseTexturesArray[0] = "";
/*  630 */       this.texturePrefix = String.valueOf(this.texturePrefix) + "_" + i + "_";
/*      */     } 
/*      */     
/*  633 */     int i1 = getHorseArmorIndexSynced();
/*      */     
/*  635 */     if (i1 >= horseArmorTextures.length) {
/*  636 */       this.field_175508_bO = false;
/*      */     } else {
/*  638 */       this.horseTexturesArray[2] = horseArmorTextures[i1];
/*  639 */       this.texturePrefix = String.valueOf(this.texturePrefix) + HORSE_ARMOR_TEXTURES_ABBR[i1];
/*  640 */       this.field_175508_bO = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public String getHorseTexture() {
/*  645 */     if (this.texturePrefix == null) {
/*  646 */       setHorseTexturePaths();
/*      */     }
/*      */     
/*  649 */     return this.texturePrefix;
/*      */   }
/*      */   
/*      */   public String[] getVariantTexturePaths() {
/*  653 */     if (this.texturePrefix == null) {
/*  654 */       setHorseTexturePaths();
/*      */     }
/*      */     
/*  657 */     return this.horseTexturesArray;
/*      */   }
/*      */   
/*      */   public void openGUI(EntityPlayer playerEntity) {
/*  661 */     if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == playerEntity) && isTame()) {
/*  662 */       this.horseChest.setCustomName(getName());
/*  663 */       playerEntity.displayGUIHorse(this, (IInventory)this.horseChest);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interact(EntityPlayer player) {
/*  671 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*      */     
/*  673 */     if (itemstack != null && itemstack.getItem() == Items.spawn_egg)
/*  674 */       return super.interact(player); 
/*  675 */     if (!isTame() && isUndead())
/*  676 */       return false; 
/*  677 */     if (isTame() && isAdultHorse() && player.isSneaking()) {
/*  678 */       openGUI(player);
/*  679 */       return true;
/*  680 */     }  if (func_110253_bW() && this.riddenByEntity != null) {
/*  681 */       return super.interact(player);
/*      */     }
/*  683 */     if (itemstack != null) {
/*  684 */       boolean flag = false;
/*      */       
/*  686 */       if (canWearArmor()) {
/*  687 */         int i = -1;
/*      */         
/*  689 */         if (itemstack.getItem() == Items.iron_horse_armor) {
/*  690 */           i = 1;
/*  691 */         } else if (itemstack.getItem() == Items.golden_horse_armor) {
/*  692 */           i = 2;
/*  693 */         } else if (itemstack.getItem() == Items.diamond_horse_armor) {
/*  694 */           i = 3;
/*      */         } 
/*      */         
/*  697 */         if (i >= 0) {
/*  698 */           if (!isTame()) {
/*  699 */             makeHorseRearWithSound();
/*  700 */             return true;
/*      */           } 
/*      */           
/*  703 */           openGUI(player);
/*  704 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/*  708 */       if (!flag && !isUndead()) {
/*  709 */         float f = 0.0F;
/*  710 */         int j = 0;
/*  711 */         int k = 0;
/*      */         
/*  713 */         if (itemstack.getItem() == Items.wheat) {
/*  714 */           f = 2.0F;
/*  715 */           j = 20;
/*  716 */           k = 3;
/*  717 */         } else if (itemstack.getItem() == Items.sugar) {
/*  718 */           f = 1.0F;
/*  719 */           j = 30;
/*  720 */           k = 3;
/*  721 */         } else if (Block.getBlockFromItem(itemstack.getItem()) == Blocks.hay_block) {
/*  722 */           f = 20.0F;
/*  723 */           j = 180;
/*  724 */         } else if (itemstack.getItem() == Items.apple) {
/*  725 */           f = 3.0F;
/*  726 */           j = 60;
/*  727 */           k = 3;
/*  728 */         } else if (itemstack.getItem() == Items.golden_carrot) {
/*  729 */           f = 4.0F;
/*  730 */           j = 60;
/*  731 */           k = 5;
/*      */           
/*  733 */           if (isTame() && getGrowingAge() == 0) {
/*  734 */             flag = true;
/*  735 */             setInLove(player);
/*      */           } 
/*  737 */         } else if (itemstack.getItem() == Items.golden_apple) {
/*  738 */           f = 10.0F;
/*  739 */           j = 240;
/*  740 */           k = 10;
/*      */           
/*  742 */           if (isTame() && getGrowingAge() == 0) {
/*  743 */             flag = true;
/*  744 */             setInLove(player);
/*      */           } 
/*      */         } 
/*      */         
/*  748 */         if (getHealth() < getMaxHealth() && f > 0.0F) {
/*  749 */           heal(f);
/*  750 */           flag = true;
/*      */         } 
/*      */         
/*  753 */         if (!isAdultHorse() && j > 0) {
/*  754 */           addGrowth(j);
/*  755 */           flag = true;
/*      */         } 
/*      */         
/*  758 */         if (k > 0 && (flag || !isTame()) && k < getMaxTemper()) {
/*  759 */           flag = true;
/*  760 */           increaseTemper(k);
/*      */         } 
/*      */         
/*  763 */         if (flag) {
/*  764 */           func_110266_cB();
/*      */         }
/*      */       } 
/*      */       
/*  768 */       if (!isTame() && !flag) {
/*  769 */         if (itemstack != null && itemstack.interactWithEntity(player, (EntityLivingBase)this)) {
/*  770 */           return true;
/*      */         }
/*      */         
/*  773 */         makeHorseRearWithSound();
/*  774 */         return true;
/*      */       } 
/*      */       
/*  777 */       if (!flag && canCarryChest() && !isChested() && itemstack.getItem() == Item.getItemFromBlock((Block)Blocks.chest)) {
/*  778 */         setChested(true);
/*  779 */         playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  780 */         flag = true;
/*  781 */         initHorseChest();
/*      */       } 
/*      */       
/*  784 */       if (!flag && func_110253_bW() && !isHorseSaddled() && itemstack.getItem() == Items.saddle) {
/*  785 */         openGUI(player);
/*  786 */         return true;
/*      */       } 
/*      */       
/*  789 */       if (flag) {
/*  790 */         if (!player.capabilities.isCreativeMode && --itemstack.stackSize == 0) {
/*  791 */           player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*      */         }
/*      */         
/*  794 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  798 */     if (func_110253_bW() && this.riddenByEntity == null) {
/*  799 */       if (itemstack != null && itemstack.interactWithEntity(player, (EntityLivingBase)this)) {
/*  800 */         return true;
/*      */       }
/*  802 */       mountTo(player);
/*  803 */       return true;
/*      */     } 
/*      */     
/*  806 */     return super.interact(player);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void mountTo(EntityPlayer player) {
/*  812 */     player.rotationYaw = this.rotationYaw;
/*  813 */     player.rotationPitch = this.rotationPitch;
/*  814 */     setEatingHaystack(false);
/*  815 */     setRearing(false);
/*      */     
/*  817 */     if (!this.worldObj.isRemote) {
/*  818 */       player.mountEntity((Entity)this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canWearArmor() {
/*  826 */     return (getHorseType() == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCarryChest() {
/*  833 */     int i = getHorseType();
/*  834 */     return !(i != 2 && i != 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/*  841 */     return (this.riddenByEntity != null && isHorseSaddled()) ? true : (!(!isEatingHaystack() && !isRearing()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUndead() {
/*  848 */     int i = getHorseType();
/*  849 */     return !(i != 3 && i != 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSterile() {
/*  856 */     return !(!isUndead() && getHorseType() != 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBreedingItem(ItemStack stack) {
/*  864 */     return false;
/*      */   }
/*      */   
/*      */   private void func_110210_cH() {
/*  868 */     this.field_110278_bp = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  875 */     super.onDeath(cause);
/*      */     
/*  877 */     if (!this.worldObj.isRemote) {
/*  878 */       dropChestItems();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  887 */     if (this.rand.nextInt(200) == 0) {
/*  888 */       func_110210_cH();
/*      */     }
/*      */     
/*  891 */     super.onLivingUpdate();
/*      */     
/*  893 */     if (!this.worldObj.isRemote) {
/*  894 */       if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
/*  895 */         heal(1.0F);
/*      */       }
/*      */       
/*  898 */       if (!isEatingHaystack() && this.riddenByEntity == null && this.rand.nextInt(300) == 0 && this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ))).getBlock() == Blocks.grass) {
/*  899 */         setEatingHaystack(true);
/*      */       }
/*      */       
/*  902 */       if (isEatingHaystack() && ++this.eatingHaystackCounter > 50) {
/*  903 */         this.eatingHaystackCounter = 0;
/*  904 */         setEatingHaystack(false);
/*      */       } 
/*      */       
/*  907 */       if (isBreeding() && !isAdultHorse() && !isEatingHaystack()) {
/*  908 */         EntityHorse entityhorse = getClosestHorse((Entity)this, 16.0D);
/*      */         
/*  910 */         if (entityhorse != null && getDistanceSqToEntity((Entity)entityhorse) > 4.0D) {
/*  911 */           this.navigator.getPathToEntityLiving((Entity)entityhorse);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  921 */     super.onUpdate();
/*      */     
/*  923 */     if (this.worldObj.isRemote && this.dataWatcher.hasObjectChanged()) {
/*  924 */       this.dataWatcher.func_111144_e();
/*  925 */       resetTexturePrefix();
/*      */     } 
/*      */     
/*  928 */     if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
/*  929 */       this.openMouthCounter = 0;
/*  930 */       setHorseWatchableBoolean(128, false);
/*      */     } 
/*      */     
/*  933 */     if (!this.worldObj.isRemote && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
/*  934 */       this.jumpRearingCounter = 0;
/*  935 */       setRearing(false);
/*      */     } 
/*      */     
/*  938 */     if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
/*  939 */       this.field_110278_bp = 0;
/*      */     }
/*      */     
/*  942 */     if (this.field_110279_bq > 0) {
/*  943 */       this.field_110279_bq++;
/*      */       
/*  945 */       if (this.field_110279_bq > 300) {
/*  946 */         this.field_110279_bq = 0;
/*      */       }
/*      */     } 
/*      */     
/*  950 */     this.prevHeadLean = this.headLean;
/*      */     
/*  952 */     if (isEatingHaystack()) {
/*  953 */       this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;
/*      */       
/*  955 */       if (this.headLean > 1.0F) {
/*  956 */         this.headLean = 1.0F;
/*      */       }
/*      */     } else {
/*  959 */       this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
/*      */       
/*  961 */       if (this.headLean < 0.0F) {
/*  962 */         this.headLean = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/*  966 */     this.prevRearingAmount = this.rearingAmount;
/*      */     
/*  968 */     if (isRearing()) {
/*  969 */       this.prevHeadLean = this.headLean = 0.0F;
/*  970 */       this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
/*      */       
/*  972 */       if (this.rearingAmount > 1.0F) {
/*  973 */         this.rearingAmount = 1.0F;
/*      */       }
/*      */     } else {
/*  976 */       this.field_110294_bI = false;
/*  977 */       this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;
/*      */       
/*  979 */       if (this.rearingAmount < 0.0F) {
/*  980 */         this.rearingAmount = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/*  984 */     this.prevMouthOpenness = this.mouthOpenness;
/*      */     
/*  986 */     if (getHorseWatchableBoolean(128)) {
/*  987 */       this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;
/*      */       
/*  989 */       if (this.mouthOpenness > 1.0F) {
/*  990 */         this.mouthOpenness = 1.0F;
/*      */       }
/*      */     } else {
/*  993 */       this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
/*      */       
/*  995 */       if (this.mouthOpenness < 0.0F) {
/*  996 */         this.mouthOpenness = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void openHorseMouth() {
/* 1002 */     if (!this.worldObj.isRemote) {
/* 1003 */       this.openMouthCounter = 1;
/* 1004 */       setHorseWatchableBoolean(128, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canMate() {
/* 1012 */     return (this.riddenByEntity == null && this.ridingEntity == null && isTame() && isAdultHorse() && !isSterile() && getHealth() >= getMaxHealth() && isInLove());
/*      */   }
/*      */   
/*      */   public void setEating(boolean eating) {
/* 1016 */     setHorseWatchableBoolean(32, eating);
/*      */   }
/*      */   
/*      */   public void setEatingHaystack(boolean p_110227_1_) {
/* 1020 */     setEating(p_110227_1_);
/*      */   }
/*      */   
/*      */   public void setRearing(boolean rearing) {
/* 1024 */     if (rearing) {
/* 1025 */       setEatingHaystack(false);
/*      */     }
/*      */     
/* 1028 */     setHorseWatchableBoolean(64, rearing);
/*      */   }
/*      */   
/*      */   private void makeHorseRear() {
/* 1032 */     if (!this.worldObj.isRemote) {
/* 1033 */       this.jumpRearingCounter = 1;
/* 1034 */       setRearing(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void makeHorseRearWithSound() {
/* 1039 */     makeHorseRear();
/* 1040 */     String s = getAngrySoundName();
/*      */     
/* 1042 */     if (s != null) {
/* 1043 */       playSound(s, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */   
/*      */   public void dropChestItems() {
/* 1048 */     dropItemsInChest((Entity)this, this.horseChest);
/* 1049 */     dropChests();
/*      */   }
/*      */   
/*      */   private void dropItemsInChest(Entity entityIn, AnimalChest animalChestIn) {
/* 1053 */     if (animalChestIn != null && !this.worldObj.isRemote) {
/* 1054 */       for (int i = 0; i < animalChestIn.getSizeInventory(); i++) {
/* 1055 */         ItemStack itemstack = animalChestIn.getStackInSlot(i);
/*      */         
/* 1057 */         if (itemstack != null) {
/* 1058 */           entityDropItem(itemstack, 0.0F);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean setTamedBy(EntityPlayer player) {
/* 1065 */     setOwnerId(player.getUniqueID().toString());
/* 1066 */     setHorseTamed(true);
/* 1067 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1074 */     if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && isHorseSaddled()) {
/* 1075 */       this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
/* 1076 */       this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
/* 1077 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 1078 */       this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
/* 1079 */       strafe = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
/* 1080 */       forward = ((EntityLivingBase)this.riddenByEntity).moveForward;
/*      */       
/* 1082 */       if (forward <= 0.0F) {
/* 1083 */         forward *= 0.25F;
/* 1084 */         this.gallopTime = 0;
/*      */       } 
/*      */       
/* 1087 */       if (this.onGround && this.jumpPower == 0.0F && isRearing() && !this.field_110294_bI) {
/* 1088 */         strafe = 0.0F;
/* 1089 */         forward = 0.0F;
/*      */       } 
/*      */       
/* 1092 */       if (this.jumpPower > 0.0F && !isHorseJumping() && this.onGround) {
/* 1093 */         this.motionY = getHorseJumpStrength() * this.jumpPower;
/*      */         
/* 1095 */         if (isPotionActive(Potion.jump)) {
/* 1096 */           this.motionY += ((getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
/*      */         }
/*      */         
/* 1099 */         setHorseJumping(true);
/* 1100 */         this.isAirBorne = true;
/*      */         
/* 1102 */         if (forward > 0.0F) {
/* 1103 */           float f = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F);
/* 1104 */           float f1 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
/* 1105 */           this.motionX += (-0.4F * f * this.jumpPower);
/* 1106 */           this.motionZ += (0.4F * f1 * this.jumpPower);
/* 1107 */           playSound("mob.horse.jump", 0.4F, 1.0F);
/*      */         } 
/*      */         
/* 1110 */         this.jumpPower = 0.0F;
/*      */       } 
/*      */       
/* 1113 */       this.stepHeight = 1.0F;
/* 1114 */       this.jumpMovementFactor = getAIMoveSpeed() * 0.1F;
/*      */       
/* 1116 */       if (!this.worldObj.isRemote) {
/* 1117 */         setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
/* 1118 */         super.moveEntityWithHeading(strafe, forward);
/*      */       } 
/*      */       
/* 1121 */       if (this.onGround) {
/* 1122 */         this.jumpPower = 0.0F;
/* 1123 */         setHorseJumping(false);
/*      */       } 
/*      */       
/* 1126 */       this.prevLimbSwingAmount = this.limbSwingAmount;
/* 1127 */       double d1 = this.posX - this.prevPosX;
/* 1128 */       double d0 = this.posZ - this.prevPosZ;
/* 1129 */       float f2 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
/*      */       
/* 1131 */       if (f2 > 1.0F) {
/* 1132 */         f2 = 1.0F;
/*      */       }
/*      */       
/* 1135 */       this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
/* 1136 */       this.limbSwing += this.limbSwingAmount;
/*      */     } else {
/* 1138 */       this.stepHeight = 0.5F;
/* 1139 */       this.jumpMovementFactor = 0.02F;
/* 1140 */       super.moveEntityWithHeading(strafe, forward);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 1148 */     super.writeEntityToNBT(tagCompound);
/* 1149 */     tagCompound.setBoolean("EatingHaystack", isEatingHaystack());
/* 1150 */     tagCompound.setBoolean("ChestedHorse", isChested());
/* 1151 */     tagCompound.setBoolean("HasReproduced", getHasReproduced());
/* 1152 */     tagCompound.setBoolean("Bred", isBreeding());
/* 1153 */     tagCompound.setInteger("Type", getHorseType());
/* 1154 */     tagCompound.setInteger("Variant", getHorseVariant());
/* 1155 */     tagCompound.setInteger("Temper", getTemper());
/* 1156 */     tagCompound.setBoolean("Tame", isTame());
/* 1157 */     tagCompound.setString("OwnerUUID", getOwnerId());
/*      */     
/* 1159 */     if (isChested()) {
/* 1160 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/* 1162 */       for (int i = 2; i < this.horseChest.getSizeInventory(); i++) {
/* 1163 */         ItemStack itemstack = this.horseChest.getStackInSlot(i);
/*      */         
/* 1165 */         if (itemstack != null) {
/* 1166 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 1167 */           nbttagcompound.setByte("Slot", (byte)i);
/* 1168 */           itemstack.writeToNBT(nbttagcompound);
/* 1169 */           nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */         } 
/*      */       } 
/*      */       
/* 1173 */       tagCompound.setTag("Items", (NBTBase)nbttaglist);
/*      */     } 
/*      */     
/* 1176 */     if (this.horseChest.getStackInSlot(1) != null) {
/* 1177 */       tagCompound.setTag("ArmorItem", (NBTBase)this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */     
/* 1180 */     if (this.horseChest.getStackInSlot(0) != null) {
/* 1181 */       tagCompound.setTag("SaddleItem", (NBTBase)this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 1189 */     super.readEntityFromNBT(tagCompund);
/* 1190 */     setEatingHaystack(tagCompund.getBoolean("EatingHaystack"));
/* 1191 */     setBreeding(tagCompund.getBoolean("Bred"));
/* 1192 */     setChested(tagCompund.getBoolean("ChestedHorse"));
/* 1193 */     setHasReproduced(tagCompund.getBoolean("HasReproduced"));
/* 1194 */     setHorseType(tagCompund.getInteger("Type"));
/* 1195 */     setHorseVariant(tagCompund.getInteger("Variant"));
/* 1196 */     setTemper(tagCompund.getInteger("Temper"));
/* 1197 */     setHorseTamed(tagCompund.getBoolean("Tame"));
/* 1198 */     String s = "";
/*      */     
/* 1200 */     if (tagCompund.hasKey("OwnerUUID", 8)) {
/* 1201 */       s = tagCompund.getString("OwnerUUID");
/*      */     } else {
/* 1203 */       String s1 = tagCompund.getString("Owner");
/* 1204 */       s = PreYggdrasilConverter.getStringUUIDFromName(s1);
/*      */     } 
/*      */     
/* 1207 */     if (s.length() > 0) {
/* 1208 */       setOwnerId(s);
/*      */     }
/*      */     
/* 1211 */     IAttributeInstance iattributeinstance = getAttributeMap().getAttributeInstanceByName("Speed");
/*      */     
/* 1213 */     if (iattributeinstance != null) {
/* 1214 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(iattributeinstance.getBaseValue() * 0.25D);
/*      */     }
/*      */     
/* 1217 */     if (isChested()) {
/* 1218 */       NBTTagList nbttaglist = tagCompund.getTagList("Items", 10);
/* 1219 */       initHorseChest();
/*      */       
/* 1221 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 1222 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 1223 */         int j = nbttagcompound.getByte("Slot") & 0xFF;
/*      */         
/* 1225 */         if (j >= 2 && j < this.horseChest.getSizeInventory()) {
/* 1226 */           this.horseChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1231 */     if (tagCompund.hasKey("ArmorItem", 10)) {
/* 1232 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("ArmorItem"));
/*      */       
/* 1234 */       if (itemstack != null && isArmorItem(itemstack.getItem())) {
/* 1235 */         this.horseChest.setInventorySlotContents(1, itemstack);
/*      */       }
/*      */     } 
/*      */     
/* 1239 */     if (tagCompund.hasKey("SaddleItem", 10)) {
/* 1240 */       ItemStack itemstack1 = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("SaddleItem"));
/*      */       
/* 1242 */       if (itemstack1 != null && itemstack1.getItem() == Items.saddle) {
/* 1243 */         this.horseChest.setInventorySlotContents(0, itemstack1);
/*      */       }
/* 1245 */     } else if (tagCompund.getBoolean("Saddle")) {
/* 1246 */       this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
/*      */     } 
/*      */     
/* 1249 */     updateHorseSlots();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 1256 */     if (otherAnimal == this)
/* 1257 */       return false; 
/* 1258 */     if (otherAnimal.getClass() != getClass()) {
/* 1259 */       return false;
/*      */     }
/* 1261 */     EntityHorse entityhorse = (EntityHorse)otherAnimal;
/*      */     
/* 1263 */     if (canMate() && entityhorse.canMate()) {
/* 1264 */       int i = getHorseType();
/* 1265 */       int j = entityhorse.getHorseType();
/* 1266 */       return !(i != j && (i != 0 || j != 1) && (i != 1 || j != 0));
/*      */     } 
/* 1268 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityAgeable createChild(EntityAgeable ageable) {
/* 1274 */     EntityHorse entityhorse = (EntityHorse)ageable;
/* 1275 */     EntityHorse entityhorse1 = new EntityHorse(this.worldObj);
/* 1276 */     int i = getHorseType();
/* 1277 */     int j = entityhorse.getHorseType();
/* 1278 */     int k = 0;
/*      */     
/* 1280 */     if (i == j) {
/* 1281 */       k = i;
/* 1282 */     } else if ((i == 0 && j == 1) || (i == 1 && j == 0)) {
/* 1283 */       k = 2;
/*      */     } 
/*      */     
/* 1286 */     if (k == 0) {
/* 1287 */       int l, i1 = this.rand.nextInt(9);
/*      */ 
/*      */       
/* 1290 */       if (i1 < 4) {
/* 1291 */         l = getHorseVariant() & 0xFF;
/* 1292 */       } else if (i1 < 8) {
/* 1293 */         l = entityhorse.getHorseVariant() & 0xFF;
/*      */       } else {
/* 1295 */         l = this.rand.nextInt(7);
/*      */       } 
/*      */       
/* 1298 */       int j1 = this.rand.nextInt(5);
/*      */       
/* 1300 */       if (j1 < 2) {
/* 1301 */         l |= getHorseVariant() & 0xFF00;
/* 1302 */       } else if (j1 < 4) {
/* 1303 */         l |= entityhorse.getHorseVariant() & 0xFF00;
/*      */       } else {
/* 1305 */         l |= this.rand.nextInt(5) << 8 & 0xFF00;
/*      */       } 
/*      */       
/* 1308 */       entityhorse1.setHorseVariant(l);
/*      */     } 
/*      */     
/* 1311 */     entityhorse1.setHorseType(k);
/* 1312 */     double d1 = getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + ageable.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + getModifiedMaxHealth();
/* 1313 */     entityhorse1.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(d1 / 3.0D);
/* 1314 */     double d2 = getEntityAttribute(horseJumpStrength).getBaseValue() + ageable.getEntityAttribute(horseJumpStrength).getBaseValue() + getModifiedJumpStrength();
/* 1315 */     entityhorse1.getEntityAttribute(horseJumpStrength).setBaseValue(d2 / 3.0D);
/* 1316 */     double d0 = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + ageable.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() + getModifiedMovementSpeed();
/* 1317 */     entityhorse1.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(d0 / 3.0D);
/* 1318 */     return entityhorse1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 1326 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 1327 */     int i = 0;
/* 1328 */     int j = 0;
/*      */     
/* 1330 */     if (livingdata instanceof GroupData) {
/* 1331 */       i = ((GroupData)livingdata).horseType;
/* 1332 */       j = ((GroupData)livingdata).horseVariant & 0xFF | this.rand.nextInt(5) << 8;
/*      */     } else {
/* 1334 */       if (this.rand.nextInt(10) == 0) {
/* 1335 */         i = 1;
/*      */       } else {
/* 1337 */         int k = this.rand.nextInt(7);
/* 1338 */         int l = this.rand.nextInt(5);
/* 1339 */         i = 0;
/* 1340 */         j = k | l << 8;
/*      */       } 
/*      */       
/* 1343 */       livingdata = new GroupData(i, j);
/*      */     } 
/*      */     
/* 1346 */     setHorseType(i);
/* 1347 */     setHorseVariant(j);
/*      */     
/* 1349 */     if (this.rand.nextInt(5) == 0) {
/* 1350 */       setGrowingAge(-24000);
/*      */     }
/*      */     
/* 1353 */     if (i != 4 && i != 3) {
/* 1354 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getModifiedMaxHealth());
/*      */       
/* 1356 */       if (i == 0) {
/* 1357 */         getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(getModifiedMovementSpeed());
/*      */       } else {
/* 1359 */         getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776D);
/*      */       } 
/*      */     } else {
/* 1362 */       getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
/* 1363 */       getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
/*      */     } 
/*      */     
/* 1366 */     if (i != 2 && i != 1) {
/* 1367 */       getEntityAttribute(horseJumpStrength).setBaseValue(getModifiedJumpStrength());
/*      */     } else {
/* 1369 */       getEntityAttribute(horseJumpStrength).setBaseValue(0.5D);
/*      */     } 
/*      */     
/* 1372 */     setHealth(getMaxHealth());
/* 1373 */     return livingdata;
/*      */   }
/*      */   
/*      */   public float getGrassEatingAmount(float p_110258_1_) {
/* 1377 */     return this.prevHeadLean + (this.headLean - this.prevHeadLean) * p_110258_1_;
/*      */   }
/*      */   
/*      */   public float getRearingAmount(float p_110223_1_) {
/* 1381 */     return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * p_110223_1_;
/*      */   }
/*      */   
/*      */   public float getMouthOpennessAngle(float p_110201_1_) {
/* 1385 */     return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * p_110201_1_;
/*      */   }
/*      */   
/*      */   public void setJumpPower(int jumpPowerIn) {
/* 1389 */     if (isHorseSaddled()) {
/* 1390 */       if (jumpPowerIn < 0) {
/* 1391 */         jumpPowerIn = 0;
/*      */       } else {
/* 1393 */         this.field_110294_bI = true;
/* 1394 */         makeHorseRear();
/*      */       } 
/*      */       
/* 1397 */       if (jumpPowerIn >= 90) {
/* 1398 */         this.jumpPower = 1.0F;
/*      */       } else {
/* 1400 */         this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void spawnHorseParticles(boolean p_110216_1_) {
/* 1409 */     EnumParticleTypes enumparticletypes = p_110216_1_ ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;
/*      */     
/* 1411 */     for (int i = 0; i < 7; i++) {
/* 1412 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 1413 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 1414 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 1415 */       this.worldObj.spawnParticle(enumparticletypes, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/* 1420 */     if (id == 7) {
/* 1421 */       spawnHorseParticles(true);
/* 1422 */     } else if (id == 6) {
/* 1423 */       spawnHorseParticles(false);
/*      */     } else {
/* 1425 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateRiderPosition() {
/* 1430 */     super.updateRiderPosition();
/*      */     
/* 1432 */     if (this.prevRearingAmount > 0.0F) {
/* 1433 */       float f = MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F);
/* 1434 */       float f1 = MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F);
/* 1435 */       float f2 = 0.7F * this.prevRearingAmount;
/* 1436 */       float f3 = 0.15F * this.prevRearingAmount;
/* 1437 */       this.riddenByEntity.setPosition(this.posX + (f2 * f), this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset() + f3, this.posZ - (f2 * f1));
/*      */       
/* 1439 */       if (this.riddenByEntity instanceof EntityLivingBase) {
/* 1440 */         ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getModifiedMaxHealth() {
/* 1449 */     return 15.0F + this.rand.nextInt(8) + this.rand.nextInt(9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double getModifiedJumpStrength() {
/* 1456 */     return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double getModifiedMovementSpeed() {
/* 1463 */     return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isArmorItem(Item p_146085_0_) {
/* 1470 */     return !(p_146085_0_ != Items.iron_horse_armor && p_146085_0_ != Items.golden_horse_armor && p_146085_0_ != Items.diamond_horse_armor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnLadder() {
/* 1477 */     return false;
/*      */   }
/*      */   
/*      */   public float getEyeHeight() {
/* 1481 */     return this.height;
/*      */   }
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 1485 */     if (inventorySlot == 499 && canCarryChest()) {
/* 1486 */       if (itemStackIn == null && isChested()) {
/* 1487 */         setChested(false);
/* 1488 */         initHorseChest();
/* 1489 */         return true;
/*      */       } 
/*      */       
/* 1492 */       if (itemStackIn != null && itemStackIn.getItem() == Item.getItemFromBlock((Block)Blocks.chest) && !isChested()) {
/* 1493 */         setChested(true);
/* 1494 */         initHorseChest();
/* 1495 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/* 1499 */     int i = inventorySlot - 400;
/*      */     
/* 1501 */     if (i >= 0 && i < 2 && i < this.horseChest.getSizeInventory()) {
/* 1502 */       if (i == 0 && itemStackIn != null && itemStackIn.getItem() != Items.saddle)
/* 1503 */         return false; 
/* 1504 */       if (i != 1 || ((itemStackIn == null || isArmorItem(itemStackIn.getItem())) && canWearArmor())) {
/* 1505 */         this.horseChest.setInventorySlotContents(i, itemStackIn);
/* 1506 */         updateHorseSlots();
/* 1507 */         return true;
/*      */       } 
/* 1509 */       return false;
/*      */     } 
/*      */     
/* 1512 */     int j = inventorySlot - 500 + 2;
/*      */     
/* 1514 */     if (j >= 2 && j < this.horseChest.getSizeInventory()) {
/* 1515 */       this.horseChest.setInventorySlotContents(j, itemStackIn);
/* 1516 */       return true;
/*      */     } 
/* 1518 */     return false;
/*      */   }
/*      */   
/*      */   public static class GroupData
/*      */     implements IEntityLivingData
/*      */   {
/*      */     public int horseType;
/*      */     public int horseVariant;
/*      */     
/*      */     public GroupData(int type, int variant) {
/* 1528 */       this.horseType = type;
/* 1529 */       this.horseVariant = variant;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */