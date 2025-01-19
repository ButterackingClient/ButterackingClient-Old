/*     */ package net.minecraft.entity.monster;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityEndermite extends EntityMob {
/*  21 */   private int lifetime = 0;
/*     */   private boolean playerSpawned = false;
/*     */   
/*     */   public EntityEndermite(World worldIn) {
/*  25 */     super(worldIn);
/*  26 */     this.experienceValue = 3;
/*  27 */     setSize(0.4F, 0.3F);
/*  28 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  29 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
/*  30 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  31 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  32 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  33 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  34 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/*  38 */     return 0.1F;
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  42 */     super.applyEntityAttributes();
/*  43 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  44 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*  45 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  60 */     return "mob.silverfish.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  67 */     return "mob.silverfish.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  74 */     return "mob.silverfish.kill";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  78 */     playSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  89 */     super.readEntityFromNBT(tagCompund);
/*  90 */     this.lifetime = tagCompund.getInteger("Lifetime");
/*  91 */     this.playerSpawned = tagCompund.getBoolean("PlayerSpawned");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  98 */     super.writeEntityToNBT(tagCompound);
/*  99 */     tagCompound.setInteger("Lifetime", this.lifetime);
/* 100 */     tagCompound.setBoolean("PlayerSpawned", this.playerSpawned);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 107 */     this.renderYawOffset = this.rotationYaw;
/* 108 */     super.onUpdate();
/*     */   }
/*     */   
/*     */   public boolean isSpawnedByPlayer() {
/* 112 */     return this.playerSpawned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnedByPlayer(boolean spawnedByPlayer) {
/* 119 */     this.playerSpawned = spawnedByPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 127 */     super.onLivingUpdate();
/*     */     
/* 129 */     if (this.worldObj.isRemote) {
/* 130 */       for (int i = 0; i < 2; i++) {
/* 131 */         this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     } else {
/* 134 */       if (!isNoDespawnRequired()) {
/* 135 */         this.lifetime++;
/*     */       }
/*     */       
/* 138 */       if (this.lifetime >= 2400) {
/* 139 */         setDead();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 155 */     if (super.getCanSpawnHere()) {
/* 156 */       EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 5.0D);
/* 157 */       return (entityplayer == null);
/*     */     } 
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 167 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityEndermite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */