/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Calendar;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFleeSun;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIRestrictSun;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySkeleton extends EntityMob implements IRangedAttackMob {
/*  44 */   private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
/*  45 */   private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
/*     */   
/*     */   public EntitySkeleton(World worldIn) {
/*  48 */     super(worldIn);
/*  49 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  50 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIRestrictSun(this));
/*  51 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIFleeSun(this, 1.0D));
/*  52 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
/*  53 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  54 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  55 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  56 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  57 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  58 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
/*     */     
/*  60 */     if (worldIn != null && !worldIn.isRemote) {
/*  61 */       setCombatTask();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  66 */     super.applyEntityAttributes();
/*  67 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  71 */     super.entityInit();
/*  72 */     this.dataWatcher.addObject(13, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  79 */     return "mob.skeleton.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  86 */     return "mob.skeleton.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  93 */     return "mob.skeleton.death";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  97 */     playSound("mob.skeleton.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 101 */     if (super.attackEntityAsMob(entityIn)) {
/* 102 */       if (getSkeletonType() == 1 && entityIn instanceof EntityLivingBase) {
/* 103 */         ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
/*     */       }
/*     */       
/* 106 */       return true;
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 116 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 124 */     if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
/* 125 */       float f = getBrightness(1.0F);
/* 126 */       BlockPos blockpos = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
/*     */       
/* 128 */       if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canSeeSky(blockpos)) {
/* 129 */         boolean flag = true;
/* 130 */         ItemStack itemstack = getEquipmentInSlot(4);
/*     */         
/* 132 */         if (itemstack != null) {
/* 133 */           if (itemstack.isItemStackDamageable()) {
/* 134 */             itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
/*     */             
/* 136 */             if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
/* 137 */               renderBrokenItemStack(itemstack);
/* 138 */               setCurrentItemOrArmor(4, (ItemStack)null);
/*     */             } 
/*     */           } 
/*     */           
/* 142 */           flag = false;
/*     */         } 
/*     */         
/* 145 */         if (flag) {
/* 146 */           setFire(8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     if (this.worldObj.isRemote && getSkeletonType() == 1) {
/* 152 */       setSize(0.72F, 2.535F);
/*     */     }
/*     */     
/* 155 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRidden() {
/* 162 */     super.updateRidden();
/*     */     
/* 164 */     if (this.ridingEntity instanceof EntityCreature) {
/* 165 */       EntityCreature entitycreature = (EntityCreature)this.ridingEntity;
/* 166 */       this.renderYawOffset = entitycreature.renderYawOffset;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 174 */     super.onDeath(cause);
/*     */     
/* 176 */     if (cause.getSourceOfDamage() instanceof EntityArrow && cause.getEntity() instanceof EntityPlayer) {
/* 177 */       EntityPlayer entityplayer = (EntityPlayer)cause.getEntity();
/* 178 */       double d0 = entityplayer.posX - this.posX;
/* 179 */       double d1 = entityplayer.posZ - this.posZ;
/*     */       
/* 181 */       if (d0 * d0 + d1 * d1 >= 2500.0D) {
/* 182 */         entityplayer.triggerAchievement((StatBase)AchievementList.snipeSkeleton);
/*     */       }
/* 184 */     } else if (cause.getEntity() instanceof EntityCreeper && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
/* 185 */       ((EntityCreeper)cause.getEntity()).func_175493_co();
/* 186 */       entityDropItem(new ItemStack(Items.skull, 1, (getSkeletonType() == 1) ? 1 : 0), 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 191 */     return Items.arrow;
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
/* 202 */     if (getSkeletonType() == 1) {
/* 203 */       int i = this.rand.nextInt(3 + lootingModifier) - 1;
/*     */       
/* 205 */       for (int j = 0; j < i; j++) {
/* 206 */         dropItem(Items.coal, 1);
/*     */       }
/*     */     } else {
/* 209 */       int k = this.rand.nextInt(3 + lootingModifier);
/*     */       
/* 211 */       for (int i1 = 0; i1 < k; i1++) {
/* 212 */         dropItem(Items.arrow, 1);
/*     */       }
/*     */     } 
/*     */     
/* 216 */     int l = this.rand.nextInt(3 + lootingModifier);
/*     */     
/* 218 */     for (int j1 = 0; j1 < l; j1++) {
/* 219 */       dropItem(Items.bone, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 227 */     if (getSkeletonType() == 1) {
/* 228 */       entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 236 */     super.setEquipmentBasedOnDifficulty(difficulty);
/* 237 */     setCurrentItemOrArmor(0, new ItemStack((Item)Items.bow));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 245 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 247 */     if (this.worldObj.provider instanceof net.minecraft.world.WorldProviderHell && getRNG().nextInt(5) > 0) {
/* 248 */       this.tasks.addTask(4, (EntityAIBase)this.aiAttackOnCollide);
/* 249 */       setSkeletonType(1);
/* 250 */       setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
/* 251 */       getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
/*     */     } else {
/* 253 */       this.tasks.addTask(4, (EntityAIBase)this.aiArrowAttack);
/* 254 */       setEquipmentBasedOnDifficulty(difficulty);
/* 255 */       setEnchantmentBasedOnDifficulty(difficulty);
/*     */     } 
/*     */     
/* 258 */     setCanPickUpLoot((this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty()));
/*     */     
/* 260 */     if (getEquipmentInSlot(4) == null) {
/* 261 */       Calendar calendar = this.worldObj.getCurrentDate();
/*     */       
/* 263 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
/* 264 */         setCurrentItemOrArmor(4, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.lit_pumpkin : Blocks.pumpkin));
/* 265 */         this.equipmentDropChances[4] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCombatTask() {
/* 276 */     this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
/* 277 */     this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
/* 278 */     ItemStack itemstack = getHeldItem();
/*     */     
/* 280 */     if (itemstack != null && itemstack.getItem() == Items.bow) {
/* 281 */       this.tasks.addTask(4, (EntityAIBase)this.aiArrowAttack);
/*     */     } else {
/* 283 */       this.tasks.addTask(4, (EntityAIBase)this.aiAttackOnCollide);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
/* 291 */     EntityArrow entityarrow = new EntityArrow(this.worldObj, (EntityLivingBase)this, target, 1.6F, (14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
/* 292 */     int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
/* 293 */     int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
/* 294 */     entityarrow.setDamage((p_82196_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (this.worldObj.getDifficulty().getDifficultyId() * 0.11F));
/*     */     
/* 296 */     if (i > 0) {
/* 297 */       entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D);
/*     */     }
/*     */     
/* 300 */     if (j > 0) {
/* 301 */       entityarrow.setKnockbackStrength(j);
/*     */     }
/*     */     
/* 304 */     if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0 || getSkeletonType() == 1) {
/* 305 */       entityarrow.setFire(100);
/*     */     }
/*     */     
/* 308 */     playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 309 */     this.worldObj.spawnEntityInWorld((Entity)entityarrow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkeletonType() {
/* 316 */     return this.dataWatcher.getWatchableObjectByte(13);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkeletonType(int p_82201_1_) {
/* 323 */     this.dataWatcher.updateObject(13, Byte.valueOf((byte)p_82201_1_));
/* 324 */     this.isImmuneToFire = (p_82201_1_ == 1);
/*     */     
/* 326 */     if (p_82201_1_ == 1) {
/* 327 */       setSize(0.72F, 2.535F);
/*     */     } else {
/* 329 */       setSize(0.6F, 1.95F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 337 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 339 */     if (tagCompund.hasKey("SkeletonType", 99)) {
/* 340 */       int i = tagCompund.getByte("SkeletonType");
/* 341 */       setSkeletonType(i);
/*     */     } 
/*     */     
/* 344 */     setCombatTask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 351 */     super.writeEntityToNBT(tagCompound);
/* 352 */     tagCompound.setByte("SkeletonType", (byte)getSkeletonType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 359 */     super.setCurrentItemOrArmor(slotIn, stack);
/*     */     
/* 361 */     if (!this.worldObj.isRemote && slotIn == 0) {
/* 362 */       setCombatTask();
/*     */     }
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 367 */     return (getSkeletonType() == 1) ? super.getEyeHeight() : 1.74F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 374 */     return isChild() ? 0.0D : -0.35D;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntitySkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */