/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityChicken extends EntityAnimal {
/*     */   public float wingRotation;
/*  29 */   public float wingRotDelta = 1.0F;
/*     */   
/*     */   public float destPos;
/*     */   public float field_70884_g;
/*     */   public float field_70888_h;
/*     */   public int timeUntilNextEgg;
/*     */   public boolean chickenJockey;
/*     */   
/*     */   public EntityChicken(World worldIn) {
/*  38 */     super(worldIn);
/*  39 */     setSize(0.4F, 0.7F);
/*  40 */     this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
/*  41 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  42 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.4D));
/*  43 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  44 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.wheat_seeds, false));
/*  45 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  46 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  47 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  48 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/*  52 */     return this.height;
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  56 */     super.applyEntityAttributes();
/*  57 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
/*  58 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  66 */     super.onLivingUpdate();
/*  67 */     this.field_70888_h = this.wingRotation;
/*  68 */     this.field_70884_g = this.destPos;
/*  69 */     this.destPos = (float)(this.destPos + (this.onGround ? -1 : 4) * 0.3D);
/*  70 */     this.destPos = MathHelper.clamp_float(this.destPos, 0.0F, 1.0F);
/*     */     
/*  72 */     if (!this.onGround && this.wingRotDelta < 1.0F) {
/*  73 */       this.wingRotDelta = 1.0F;
/*     */     }
/*     */     
/*  76 */     this.wingRotDelta = (float)(this.wingRotDelta * 0.9D);
/*     */     
/*  78 */     if (!this.onGround && this.motionY < 0.0D) {
/*  79 */       this.motionY *= 0.6D;
/*     */     }
/*     */     
/*  82 */     this.wingRotation += this.wingRotDelta * 2.0F;
/*     */     
/*  84 */     if (!this.worldObj.isRemote && !isChild() && !isChickenJockey() && --this.timeUntilNextEgg <= 0) {
/*  85 */       playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*  86 */       dropItem(Items.egg, 1);
/*  87 */       this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  98 */     return "mob.chicken.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 105 */     return "mob.chicken.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 112 */     return "mob.chicken.hurt";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 116 */     playSound("mob.chicken.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 120 */     return Items.feather;
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
/* 131 */     int i = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 133 */     for (int j = 0; j < i; j++) {
/* 134 */       dropItem(Items.feather, 1);
/*     */     }
/*     */     
/* 137 */     if (isBurning()) {
/* 138 */       dropItem(Items.cooked_chicken, 1);
/*     */     } else {
/* 140 */       dropItem(Items.chicken, 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EntityChicken createChild(EntityAgeable ageable) {
/* 145 */     return new EntityChicken(this.worldObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 153 */     return (stack != null && stack.getItem() == Items.wheat_seeds);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 160 */     super.readEntityFromNBT(tagCompund);
/* 161 */     this.chickenJockey = tagCompund.getBoolean("IsChickenJockey");
/*     */     
/* 163 */     if (tagCompund.hasKey("EggLayTime")) {
/* 164 */       this.timeUntilNextEgg = tagCompund.getInteger("EggLayTime");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 172 */     return isChickenJockey() ? 10 : super.getExperiencePoints(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 179 */     super.writeEntityToNBT(tagCompound);
/* 180 */     tagCompound.setBoolean("IsChickenJockey", this.chickenJockey);
/* 181 */     tagCompound.setInteger("EggLayTime", this.timeUntilNextEgg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 188 */     return (isChickenJockey() && this.riddenByEntity == null);
/*     */   }
/*     */   
/*     */   public void updateRiderPosition() {
/* 192 */     super.updateRiderPosition();
/* 193 */     float f = MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F);
/* 194 */     float f1 = MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F);
/* 195 */     float f2 = 0.1F;
/* 196 */     float f3 = 0.0F;
/* 197 */     this.riddenByEntity.setPosition(this.posX + (f2 * f), this.posY + (this.height * 0.5F) + this.riddenByEntity.getYOffset() + f3, this.posZ - (f2 * f1));
/*     */     
/* 199 */     if (this.riddenByEntity instanceof EntityLivingBase) {
/* 200 */       ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChickenJockey() {
/* 208 */     return this.chickenJockey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChickenJockey(boolean jockey) {
/* 215 */     this.chickenJockey = jockey;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */