/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWitch extends EntityMob implements IRangedAttackMob {
/*  32 */   private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
/*  33 */   private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, 0)).setSaved(false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final Item[] witchDrops = new Item[] { Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
/*     */ 
/*     */ 
/*     */   
/*     */   private int witchAttackTimer;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityWitch(World worldIn) {
/*  47 */     super(worldIn);
/*  48 */     setSize(0.6F, 1.95F);
/*  49 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  50 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIArrowAttack(this, 1.0D, 60, 10.0F));
/*  51 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  52 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  53 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  54 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  55 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  59 */     super.entityInit();
/*  60 */     getDataWatcher().addObject(21, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAggressive(boolean aggressive) {
/*  88 */     getDataWatcher().updateObject(21, Byte.valueOf((byte)(aggressive ? 1 : 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAggressive() {
/*  95 */     return (getDataWatcher().getWatchableObjectByte(21) == 1);
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  99 */     super.applyEntityAttributes();
/* 100 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
/* 101 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 109 */     if (!this.worldObj.isRemote) {
/* 110 */       if (getAggressive()) {
/* 111 */         if (this.witchAttackTimer-- <= 0) {
/* 112 */           setAggressive(false);
/* 113 */           ItemStack itemstack = getHeldItem();
/* 114 */           setCurrentItemOrArmor(0, null);
/*     */           
/* 116 */           if (itemstack != null && itemstack.getItem() == Items.potionitem) {
/* 117 */             List<PotionEffect> list = Items.potionitem.getEffects(itemstack);
/*     */             
/* 119 */             if (list != null) {
/* 120 */               for (PotionEffect potioneffect : list) {
/* 121 */                 addPotionEffect(new PotionEffect(potioneffect));
/*     */               }
/*     */             }
/*     */           } 
/*     */           
/* 126 */           getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(MODIFIER);
/*     */         } 
/*     */       } else {
/* 129 */         int i = -1;
/*     */         
/* 131 */         if (this.rand.nextFloat() < 0.15F && isInsideOfMaterial(Material.water) && !isPotionActive(Potion.waterBreathing)) {
/* 132 */           i = 8237;
/* 133 */         } else if (this.rand.nextFloat() < 0.15F && isBurning() && !isPotionActive(Potion.fireResistance)) {
/* 134 */           i = 16307;
/* 135 */         } else if (this.rand.nextFloat() < 0.05F && getHealth() < getMaxHealth()) {
/* 136 */           i = 16341;
/* 137 */         } else if (this.rand.nextFloat() < 0.25F && getAttackTarget() != null && !isPotionActive(Potion.moveSpeed) && getAttackTarget().getDistanceSqToEntity((Entity)this) > 121.0D) {
/* 138 */           i = 16274;
/* 139 */         } else if (this.rand.nextFloat() < 0.25F && getAttackTarget() != null && !isPotionActive(Potion.moveSpeed) && getAttackTarget().getDistanceSqToEntity((Entity)this) > 121.0D) {
/* 140 */           i = 16274;
/*     */         } 
/*     */         
/* 143 */         if (i > -1) {
/* 144 */           setCurrentItemOrArmor(0, new ItemStack((Item)Items.potionitem, 1, i));
/* 145 */           this.witchAttackTimer = getHeldItem().getMaxItemUseDuration();
/* 146 */           setAggressive(true);
/* 147 */           IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 148 */           iattributeinstance.removeModifier(MODIFIER);
/* 149 */           iattributeinstance.applyModifier(MODIFIER);
/*     */         } 
/*     */       } 
/*     */       
/* 153 */       if (this.rand.nextFloat() < 7.5E-4F) {
/* 154 */         this.worldObj.setEntityState((Entity)this, (byte)15);
/*     */       }
/*     */     } 
/*     */     
/* 158 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 162 */     if (id == 15) {
/* 163 */       for (int i = 0; i < this.rand.nextInt(35) + 10; i++) {
/* 164 */         this.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842D, (getEntityBoundingBox()).maxY + 0.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } else {
/* 167 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
/* 175 */     damage = super.applyPotionDamageCalculations(source, damage);
/*     */     
/* 177 */     if (source.getEntity() == this) {
/* 178 */       damage = 0.0F;
/*     */     }
/*     */     
/* 181 */     if (source.isMagicDamage()) {
/* 182 */       damage = (float)(damage * 0.15D);
/*     */     }
/*     */     
/* 185 */     return damage;
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
/* 196 */     int i = this.rand.nextInt(3) + 1;
/*     */     
/* 198 */     for (int j = 0; j < i; j++) {
/* 199 */       int k = this.rand.nextInt(3);
/* 200 */       Item item = witchDrops[this.rand.nextInt(witchDrops.length)];
/*     */       
/* 202 */       if (lootingModifier > 0) {
/* 203 */         k += this.rand.nextInt(lootingModifier + 1);
/*     */       }
/*     */       
/* 206 */       for (int l = 0; l < k; l++) {
/* 207 */         dropItem(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
/* 216 */     if (!getAggressive()) {
/* 217 */       EntityPotion entitypotion = new EntityPotion(this.worldObj, (EntityLivingBase)this, 32732);
/* 218 */       double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
/* 219 */       entitypotion.rotationPitch -= -20.0F;
/* 220 */       double d1 = target.posX + target.motionX - this.posX;
/* 221 */       double d2 = d0 - this.posY;
/* 222 */       double d3 = target.posZ + target.motionZ - this.posZ;
/* 223 */       float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
/*     */       
/* 225 */       if (f >= 8.0F && !target.isPotionActive(Potion.moveSlowdown)) {
/* 226 */         entitypotion.setPotionDamage(32698);
/* 227 */       } else if (target.getHealth() >= 8.0F && !target.isPotionActive(Potion.poison)) {
/* 228 */         entitypotion.setPotionDamage(32660);
/* 229 */       } else if (f <= 3.0F && !target.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25F) {
/* 230 */         entitypotion.setPotionDamage(32696);
/*     */       } 
/*     */       
/* 233 */       entitypotion.setThrowableHeading(d1, d2 + (f * 0.2F), d3, 0.75F, 8.0F);
/* 234 */       this.worldObj.spawnEntityInWorld((Entity)entitypotion);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 239 */     return 1.62F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */