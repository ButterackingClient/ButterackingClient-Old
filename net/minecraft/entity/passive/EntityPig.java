/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIControlledByPlayer;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPig extends EntityAnimal {
/*     */   public EntityPig(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     setSize(0.9F, 0.9F);
/*  36 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  37 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  38 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/*  39 */     this.tasks.addTask(2, (EntityAIBase)(this.aiControlledByPlayer = new EntityAIControlledByPlayer((EntityLiving)this, 0.3F)));
/*  40 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  41 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, Items.carrot_on_a_stick, false));
/*  42 */     this.tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2D, Items.carrot, false));
/*  43 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  44 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  45 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  46 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */   private final EntityAIControlledByPlayer aiControlledByPlayer;
/*     */   protected void applyEntityAttributes() {
/*  50 */     super.applyEntityAttributes();
/*  51 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  52 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeSteered() {
/*  60 */     ItemStack itemstack = ((EntityPlayer)this.riddenByEntity).getHeldItem();
/*  61 */     return (itemstack != null && itemstack.getItem() == Items.carrot_on_a_stick);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  65 */     super.entityInit();
/*  66 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  73 */     super.writeEntityToNBT(tagCompound);
/*  74 */     tagCompound.setBoolean("Saddle", getSaddled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  81 */     super.readEntityFromNBT(tagCompund);
/*  82 */     setSaddled(tagCompund.getBoolean("Saddle"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  89 */     return "mob.pig.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  96 */     return "mob.pig.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 103 */     return "mob.pig.death";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 107 */     playSound("mob.pig.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 114 */     if (super.interact(player))
/* 115 */       return true; 
/* 116 */     if (!getSaddled() || this.worldObj.isRemote || (this.riddenByEntity != null && this.riddenByEntity != player)) {
/* 117 */       return false;
/*     */     }
/* 119 */     player.mountEntity((Entity)this);
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 125 */     return isBurning() ? Items.cooked_porkchop : Items.porkchop;
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
/* 136 */     int i = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 138 */     for (int j = 0; j < i; j++) {
/* 139 */       if (isBurning()) {
/* 140 */         dropItem(Items.cooked_porkchop, 1);
/*     */       } else {
/* 142 */         dropItem(Items.porkchop, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     if (getSaddled()) {
/* 147 */       dropItem(Items.saddle, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSaddled() {
/* 155 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaddled(boolean saddled) {
/* 162 */     if (saddled) {
/* 163 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
/*     */     } else {
/* 165 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 173 */     if (!this.worldObj.isRemote && !this.isDead) {
/* 174 */       EntityPigZombie entitypigzombie = new EntityPigZombie(this.worldObj);
/* 175 */       entitypigzombie.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
/* 176 */       entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 177 */       entitypigzombie.setNoAI(isAIDisabled());
/*     */       
/* 179 */       if (hasCustomName()) {
/* 180 */         entitypigzombie.setCustomNameTag(getCustomNameTag());
/* 181 */         entitypigzombie.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 184 */       this.worldObj.spawnEntityInWorld((Entity)entitypigzombie);
/* 185 */       setDead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 190 */     super.fall(distance, damageMultiplier);
/*     */     
/* 192 */     if (distance > 5.0F && this.riddenByEntity instanceof EntityPlayer) {
/* 193 */       ((EntityPlayer)this.riddenByEntity).triggerAchievement((StatBase)AchievementList.flyPig);
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityPig createChild(EntityAgeable ageable) {
/* 198 */     return new EntityPig(this.worldObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 206 */     return (stack != null && stack.getItem() == Items.carrot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAIControlledByPlayer getAIControlledByPlayer() {
/* 213 */     return this.aiControlledByPlayer;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */