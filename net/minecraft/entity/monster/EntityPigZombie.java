/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPigZombie extends EntityZombie {
/*  24 */   private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
/*  25 */   private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
/*     */ 
/*     */   
/*     */   private int angerLevel;
/*     */ 
/*     */   
/*     */   private int randomSoundDelay;
/*     */ 
/*     */   
/*     */   private UUID angerTargetUUID;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPigZombie(World worldIn) {
/*  39 */     super(worldIn);
/*  40 */     this.isImmuneToFire = true;
/*     */   }
/*     */   
/*     */   public void setRevengeTarget(EntityLivingBase livingBase) {
/*  44 */     super.setRevengeTarget(livingBase);
/*     */     
/*  46 */     if (livingBase != null) {
/*  47 */       this.angerTargetUUID = livingBase.getUniqueID();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void applyEntityAI() {
/*  52 */     this.targetTasks.addTask(1, (EntityAIBase)new AIHurtByAggressor(this));
/*  53 */     this.targetTasks.addTask(2, (EntityAIBase)new AITargetAggressor(this));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  57 */     super.applyEntityAttributes();
/*  58 */     getEntityAttribute(reinforcementChance).setBaseValue(0.0D);
/*  59 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*  60 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  67 */     super.onUpdate();
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/*  71 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*     */     
/*  73 */     if (isAngry()) {
/*  74 */       if (!isChild() && !iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
/*  75 */         iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
/*     */       }
/*     */       
/*  78 */       this.angerLevel--;
/*  79 */     } else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
/*  80 */       iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
/*     */     } 
/*     */     
/*  83 */     if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
/*  84 */       playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
/*     */     }
/*     */     
/*  87 */     if (this.angerLevel > 0 && this.angerTargetUUID != null && getAITarget() == null) {
/*  88 */       EntityPlayer entityplayer = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
/*  89 */       setRevengeTarget((EntityLivingBase)entityplayer);
/*  90 */       this.attackingPlayer = entityplayer;
/*  91 */       this.recentlyHit = getRevengeTimer();
/*     */     } 
/*     */     
/*  94 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 101 */     return (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 108 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 115 */     super.writeEntityToNBT(tagCompound);
/* 116 */     tagCompound.setShort("Anger", (short)this.angerLevel);
/*     */     
/* 118 */     if (this.angerTargetUUID != null) {
/* 119 */       tagCompound.setString("HurtBy", this.angerTargetUUID.toString());
/*     */     } else {
/* 121 */       tagCompound.setString("HurtBy", "");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 129 */     super.readEntityFromNBT(tagCompund);
/* 130 */     this.angerLevel = tagCompund.getShort("Anger");
/* 131 */     String s = tagCompund.getString("HurtBy");
/*     */     
/* 133 */     if (s.length() > 0) {
/* 134 */       this.angerTargetUUID = UUID.fromString(s);
/* 135 */       EntityPlayer entityplayer = this.worldObj.getPlayerEntityByUUID(this.angerTargetUUID);
/* 136 */       setRevengeTarget((EntityLivingBase)entityplayer);
/*     */       
/* 138 */       if (entityplayer != null) {
/* 139 */         this.attackingPlayer = entityplayer;
/* 140 */         this.recentlyHit = getRevengeTimer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 149 */     if (isEntityInvulnerable(source)) {
/* 150 */       return false;
/*     */     }
/* 152 */     Entity entity = source.getEntity();
/*     */     
/* 154 */     if (entity instanceof EntityPlayer) {
/* 155 */       becomeAngryAt(entity);
/*     */     }
/*     */     
/* 158 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void becomeAngryAt(Entity p_70835_1_) {
/* 166 */     this.angerLevel = 400 + this.rand.nextInt(400);
/* 167 */     this.randomSoundDelay = this.rand.nextInt(40);
/*     */     
/* 169 */     if (p_70835_1_ instanceof EntityLivingBase) {
/* 170 */       setRevengeTarget((EntityLivingBase)p_70835_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isAngry() {
/* 175 */     return (this.angerLevel > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 182 */     return "mob.zombiepig.zpig";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 189 */     return "mob.zombiepig.zpighurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 196 */     return "mob.zombiepig.zpigdeath";
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
/* 207 */     int i = this.rand.nextInt(2 + lootingModifier);
/*     */     
/* 209 */     for (int j = 0; j < i; j++) {
/* 210 */       dropItem(Items.rotten_flesh, 1);
/*     */     }
/*     */     
/* 213 */     i = this.rand.nextInt(2 + lootingModifier);
/*     */     
/* 215 */     for (int k = 0; k < i; k++) {
/* 216 */       dropItem(Items.gold_nugget, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRandomDrop() {
/* 231 */     dropItem(Items.gold_ingot, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 238 */     setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 246 */     super.onInitialSpawn(difficulty, livingdata);
/* 247 */     setVillager(false);
/* 248 */     return livingdata;
/*     */   }
/*     */   
/*     */   static class AIHurtByAggressor extends EntityAIHurtByTarget {
/*     */     public AIHurtByAggressor(EntityPigZombie p_i45828_1_) {
/* 253 */       super(p_i45828_1_, true, new Class[0]);
/*     */     }
/*     */     
/*     */     protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
/* 257 */       super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
/*     */       
/* 259 */       if (creatureIn instanceof EntityPigZombie)
/* 260 */         ((EntityPigZombie)creatureIn).becomeAngryAt((Entity)entityLivingBaseIn); 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AITargetAggressor
/*     */     extends EntityAINearestAttackableTarget<EntityPlayer> {
/*     */     public AITargetAggressor(EntityPigZombie p_i45829_1_) {
/* 267 */       super(p_i45829_1_, EntityPlayer.class, true);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 271 */       return (((EntityPigZombie)this.taskOwner).isAngry() && super.shouldExecute());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityPigZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */