/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateClimber;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySpider extends EntityMob {
/*     */   public EntitySpider(World worldIn) {
/*  33 */     super(worldIn);
/*  34 */     setSize(1.4F, 0.9F);
/*  35 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  36 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4F));
/*  37 */     this.tasks.addTask(4, (EntityAIBase)new AISpiderAttack(this, (Class)EntityPlayer.class));
/*  38 */     this.tasks.addTask(4, (EntityAIBase)new AISpiderAttack(this, (Class)EntityIronGolem.class));
/*  39 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander(this, 0.8D));
/*  40 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  41 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  42 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  43 */     this.targetTasks.addTask(2, (EntityAIBase)new AISpiderTarget<>(this, EntityPlayer.class));
/*  44 */     this.targetTasks.addTask(3, (EntityAIBase)new AISpiderTarget<>(this, EntityIronGolem.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/*  51 */     return (this.height * 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/*  58 */     return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  62 */     super.entityInit();
/*  63 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  70 */     super.onUpdate();
/*     */     
/*  72 */     if (!this.worldObj.isRemote) {
/*  73 */       setBesideClimbableBlock(this.isCollidedHorizontally);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  78 */     super.applyEntityAttributes();
/*  79 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
/*  80 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  87 */     return "mob.spider.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  94 */     return "mob.spider.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 101 */     return "mob.spider.death";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 105 */     playSound("mob.spider.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 109 */     return Items.string;
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
/* 120 */     super.dropFewItems(wasRecentlyHit, lootingModifier);
/*     */     
/* 122 */     if (wasRecentlyHit && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + lootingModifier) > 0)) {
/* 123 */       dropItem(Items.spider_eye, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnLadder() {
/* 131 */     return isBesideClimbableBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInWeb() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 144 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */   
/*     */   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
/* 148 */     return (potioneffectIn.getPotionID() == Potion.poison.id) ? false : super.isPotionApplicable(potioneffectIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBesideClimbableBlock() {
/* 156 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBesideClimbableBlock(boolean p_70839_1_) {
/* 164 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 166 */     if (p_70839_1_) {
/* 167 */       b0 = (byte)(b0 | 0x1);
/*     */     } else {
/* 169 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 172 */     this.dataWatcher.updateObject(16, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 180 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 182 */     if (this.worldObj.rand.nextInt(100) == 0) {
/* 183 */       EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
/* 184 */       entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 185 */       entityskeleton.onInitialSpawn(difficulty, (IEntityLivingData)null);
/* 186 */       this.worldObj.spawnEntityInWorld((Entity)entityskeleton);
/* 187 */       entityskeleton.mountEntity((Entity)this);
/*     */     } 
/*     */     
/* 190 */     if (livingdata == null) {
/* 191 */       livingdata = new GroupData();
/*     */       
/* 193 */       if (this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1F * difficulty.getClampedAdditionalDifficulty()) {
/* 194 */         ((GroupData)livingdata).func_111104_a(this.worldObj.rand);
/*     */       }
/*     */     } 
/*     */     
/* 198 */     if (livingdata instanceof GroupData) {
/* 199 */       int i = ((GroupData)livingdata).potionEffectId;
/*     */       
/* 201 */       if (i > 0 && Potion.potionTypes[i] != null) {
/* 202 */         addPotionEffect(new PotionEffect(i, 2147483647));
/*     */       }
/*     */     } 
/*     */     
/* 206 */     return livingdata;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 210 */     return 0.65F;
/*     */   }
/*     */   
/*     */   static class AISpiderAttack extends EntityAIAttackOnCollide {
/*     */     public AISpiderAttack(EntitySpider spider, Class<? extends Entity> targetClass) {
/* 215 */       super(spider, targetClass, 1.0D, true);
/*     */     }
/*     */     
/*     */     public boolean continueExecuting() {
/* 219 */       float f = this.attacker.getBrightness(1.0F);
/*     */       
/* 221 */       if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
/* 222 */         this.attacker.setAttackTarget(null);
/* 223 */         return false;
/*     */       } 
/* 225 */       return super.continueExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     protected double func_179512_a(EntityLivingBase attackTarget) {
/* 230 */       return (4.0F + attackTarget.width);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISpiderTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget {
/*     */     public AISpiderTarget(EntitySpider spider, Class<T> classTarget) {
/* 236 */       super(spider, classTarget, true);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 240 */       float f = this.taskOwner.getBrightness(1.0F);
/* 241 */       return (f >= 0.5F) ? false : super.shouldExecute();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GroupData implements IEntityLivingData {
/*     */     public int potionEffectId;
/*     */     
/*     */     public void func_111104_a(Random rand) {
/* 249 */       int i = rand.nextInt(5);
/*     */       
/* 251 */       if (i <= 1) {
/* 252 */         this.potionEffectId = Potion.moveSpeed.id;
/* 253 */       } else if (i <= 2) {
/* 254 */         this.potionEffectId = Potion.damageBoost.id;
/* 255 */       } else if (i <= 3) {
/* 256 */         this.potionEffectId = Potion.regeneration.id;
/* 257 */       } else if (i <= 4) {
/* 258 */         this.potionEffectId = Potion.invisibility.id;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntitySpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */