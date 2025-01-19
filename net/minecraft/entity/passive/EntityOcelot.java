/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIOcelotAttack;
/*     */ import net.minecraft.entity.ai.EntityAIOcelotSit;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITargetNonTamed;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityOcelot
/*     */   extends EntityTameable {
/*     */   private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
/*     */   private EntityAITempt aiTempt;
/*     */   
/*     */   public EntityOcelot(World worldIn) {
/*  43 */     super(worldIn);
/*  44 */     setSize(0.6F, 0.7F);
/*  45 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  46 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  47 */     this.tasks.addTask(2, (EntityAIBase)this.aiSit);
/*  48 */     this.tasks.addTask(3, (EntityAIBase)(this.aiTempt = new EntityAITempt((EntityCreature)this, 0.6D, Items.fish, true)));
/*  49 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
/*  50 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIOcelotSit(this, 0.8D));
/*  51 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.3F));
/*  52 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIOcelotAttack((EntityLiving)this));
/*  53 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIMate(this, 0.8D));
/*  54 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
/*  55 */     this.tasks.addTask(11, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 10.0F));
/*  56 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  60 */     super.entityInit();
/*  61 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void updateAITasks() {
/*  65 */     if (getMoveHelper().isUpdating()) {
/*  66 */       double d0 = getMoveHelper().getSpeed();
/*     */       
/*  68 */       if (d0 == 0.6D) {
/*  69 */         setSneaking(true);
/*  70 */         setSprinting(false);
/*  71 */       } else if (d0 == 1.33D) {
/*  72 */         setSneaking(false);
/*  73 */         setSprinting(true);
/*     */       } else {
/*  75 */         setSneaking(false);
/*  76 */         setSprinting(false);
/*     */       } 
/*     */     } else {
/*  79 */       setSneaking(false);
/*  80 */       setSprinting(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/*  88 */     return (!isTamed() && this.ticksExisted > 2400);
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  92 */     super.applyEntityAttributes();
/*  93 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  94 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 104 */     super.writeEntityToNBT(tagCompound);
/* 105 */     tagCompound.setInteger("CatType", getTameSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 112 */     super.readEntityFromNBT(tagCompund);
/* 113 */     setTameSkin(tagCompund.getInteger("CatType"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 120 */     return isTamed() ? (isInLove() ? "mob.cat.purr" : ((this.rand.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 127 */     return "mob.cat.hitt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 134 */     return "mob.cat.hitt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 141 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 145 */     return Items.leather;
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 149 */     return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 156 */     if (isEntityInvulnerable(source)) {
/* 157 */       return false;
/*     */     }
/* 159 */     this.aiSit.setSitting(false);
/* 160 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 178 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 180 */     if (isTamed()) {
/* 181 */       if (isOwner((EntityLivingBase)player) && !this.worldObj.isRemote && !isBreedingItem(itemstack)) {
/* 182 */         this.aiSit.setSitting(!isSitting());
/*     */       }
/* 184 */     } else if (this.aiTempt.isRunning() && itemstack != null && itemstack.getItem() == Items.fish && player.getDistanceSqToEntity((Entity)this) < 9.0D) {
/* 185 */       if (!player.capabilities.isCreativeMode) {
/* 186 */         itemstack.stackSize--;
/*     */       }
/*     */       
/* 189 */       if (itemstack.stackSize <= 0) {
/* 190 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*     */       }
/*     */       
/* 193 */       if (!this.worldObj.isRemote) {
/* 194 */         if (this.rand.nextInt(3) == 0) {
/* 195 */           setTamed(true);
/* 196 */           setTameSkin(1 + this.worldObj.rand.nextInt(3));
/* 197 */           setOwnerId(player.getUniqueID().toString());
/* 198 */           playTameEffect(true);
/* 199 */           this.aiSit.setSitting(true);
/* 200 */           this.worldObj.setEntityState((Entity)this, (byte)7);
/*     */         } else {
/* 202 */           playTameEffect(false);
/* 203 */           this.worldObj.setEntityState((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 207 */       return true;
/*     */     } 
/*     */     
/* 210 */     return super.interact(player);
/*     */   }
/*     */   
/*     */   public EntityOcelot createChild(EntityAgeable ageable) {
/* 214 */     EntityOcelot entityocelot = new EntityOcelot(this.worldObj);
/*     */     
/* 216 */     if (isTamed()) {
/* 217 */       entityocelot.setOwnerId(getOwnerId());
/* 218 */       entityocelot.setTamed(true);
/* 219 */       entityocelot.setTameSkin(getTameSkin());
/*     */     } 
/*     */     
/* 222 */     return entityocelot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 230 */     return (stack != null && stack.getItem() == Items.fish);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 237 */     if (otherAnimal == this)
/* 238 */       return false; 
/* 239 */     if (!isTamed())
/* 240 */       return false; 
/* 241 */     if (!(otherAnimal instanceof EntityOcelot)) {
/* 242 */       return false;
/*     */     }
/* 244 */     EntityOcelot entityocelot = (EntityOcelot)otherAnimal;
/* 245 */     return !entityocelot.isTamed() ? false : ((isInLove() && entityocelot.isInLove()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTameSkin() {
/* 250 */     return this.dataWatcher.getWatchableObjectByte(18);
/*     */   }
/*     */   
/*     */   public void setTameSkin(int skinId) {
/* 254 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)skinId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 261 */     return (this.worldObj.rand.nextInt(3) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 268 */     if (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox())) {
/* 269 */       BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */       
/* 271 */       if (blockpos.getY() < this.worldObj.getSeaLevel()) {
/* 272 */         return false;
/*     */       }
/*     */       
/* 275 */       Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*     */       
/* 277 */       if (block == Blocks.grass || block.getMaterial() == Material.leaves) {
/* 278 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 282 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 289 */     return hasCustomName() ? getCustomNameTag() : (isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getName());
/*     */   }
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 293 */     super.setTamed(tamed);
/*     */   }
/*     */   
/*     */   protected void setupTamedAI() {
/* 297 */     if (this.avoidEntity == null) {
/* 298 */       this.avoidEntity = new EntityAIAvoidEntity((EntityCreature)this, EntityPlayer.class, 16.0F, 0.8D, 1.33D);
/*     */     }
/*     */     
/* 301 */     this.tasks.removeTask((EntityAIBase)this.avoidEntity);
/*     */     
/* 303 */     if (!isTamed()) {
/* 304 */       this.tasks.addTask(4, (EntityAIBase)this.avoidEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 313 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 315 */     if (this.worldObj.rand.nextInt(7) == 0) {
/* 316 */       for (int i = 0; i < 2; i++) {
/* 317 */         EntityOcelot entityocelot = new EntityOcelot(this.worldObj);
/* 318 */         entityocelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 319 */         entityocelot.setGrowingAge(-24000);
/* 320 */         this.worldObj.spawnEntityInWorld((Entity)entityocelot);
/*     */       } 
/*     */     }
/*     */     
/* 324 */     return livingdata;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */