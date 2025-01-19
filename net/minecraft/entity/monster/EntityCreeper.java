/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAICreeperSwell;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityCreeper
/*     */   extends EntityMob
/*     */ {
/*     */   private int lastActiveTime;
/*     */   private int timeSinceIgnited;
/*  35 */   private int fuseTime = 30;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private int explosionRadius = 3;
/*  41 */   private int field_175494_bm = 0;
/*     */   
/*     */   public EntityCreeper(World worldIn) {
/*  44 */     super(worldIn);
/*  45 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  46 */     this.tasks.addTask(2, (EntityAIBase)new EntityAICreeperSwell(this));
/*  47 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
/*  48 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackOnCollide(this, 1.0D, false));
/*  49 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander(this, 0.8D));
/*  50 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  51 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  52 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  53 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  57 */     super.applyEntityAttributes();
/*  58 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFallHeight() {
/*  65 */     return (getAttackTarget() == null) ? 3 : (3 + (int)(getHealth() - 1.0F));
/*     */   }
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/*  69 */     super.fall(distance, damageMultiplier);
/*  70 */     this.timeSinceIgnited = (int)(this.timeSinceIgnited + distance * 1.5F);
/*     */     
/*  72 */     if (this.timeSinceIgnited > this.fuseTime - 5) {
/*  73 */       this.timeSinceIgnited = this.fuseTime - 5;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  78 */     super.entityInit();
/*  79 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)-1));
/*  80 */     this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
/*  81 */     this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  88 */     super.writeEntityToNBT(tagCompound);
/*     */     
/*  90 */     if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
/*  91 */       tagCompound.setBoolean("powered", true);
/*     */     }
/*     */     
/*  94 */     tagCompound.setShort("Fuse", (short)this.fuseTime);
/*  95 */     tagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
/*  96 */     tagCompound.setBoolean("ignited", hasIgnited());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 103 */     super.readEntityFromNBT(tagCompund);
/* 104 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)(tagCompund.getBoolean("powered") ? 1 : 0)));
/*     */     
/* 106 */     if (tagCompund.hasKey("Fuse", 99)) {
/* 107 */       this.fuseTime = tagCompund.getShort("Fuse");
/*     */     }
/*     */     
/* 110 */     if (tagCompund.hasKey("ExplosionRadius", 99)) {
/* 111 */       this.explosionRadius = tagCompund.getByte("ExplosionRadius");
/*     */     }
/*     */     
/* 114 */     if (tagCompund.getBoolean("ignited")) {
/* 115 */       ignite();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 123 */     if (isEntityAlive()) {
/* 124 */       this.lastActiveTime = this.timeSinceIgnited;
/*     */       
/* 126 */       if (hasIgnited()) {
/* 127 */         setCreeperState(1);
/*     */       }
/*     */       
/* 130 */       int i = getCreeperState();
/*     */       
/* 132 */       if (i > 0 && this.timeSinceIgnited == 0) {
/* 133 */         playSound("creeper.primed", 1.0F, 0.5F);
/*     */       }
/*     */       
/* 136 */       this.timeSinceIgnited += i;
/*     */       
/* 138 */       if (this.timeSinceIgnited < 0) {
/* 139 */         this.timeSinceIgnited = 0;
/*     */       }
/*     */       
/* 142 */       if (this.timeSinceIgnited >= this.fuseTime) {
/* 143 */         this.timeSinceIgnited = this.fuseTime;
/* 144 */         explode();
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 155 */     return "mob.creeper.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 162 */     return "mob.creeper.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 169 */     super.onDeath(cause);
/*     */     
/* 171 */     if (cause.getEntity() instanceof EntitySkeleton) {
/* 172 */       int i = Item.getIdFromItem(Items.record_13);
/* 173 */       int j = Item.getIdFromItem(Items.record_wait);
/* 174 */       int k = i + this.rand.nextInt(j - i + 1);
/* 175 */       dropItem(Item.getItemById(k), 1);
/* 176 */     } else if (cause.getEntity() instanceof EntityCreeper && cause.getEntity() != this && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
/* 177 */       ((EntityCreeper)cause.getEntity()).func_175493_co();
/* 178 */       entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 183 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPowered() {
/* 190 */     return (this.dataWatcher.getWatchableObjectByte(17) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCreeperFlashIntensity(float p_70831_1_) {
/* 197 */     return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 201 */     return Items.gunpowder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCreeperState() {
/* 208 */     return this.dataWatcher.getWatchableObjectByte(16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreeperState(int state) {
/* 215 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 222 */     super.onStruckByLightning(lightningBolt);
/* 223 */     this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean interact(EntityPlayer player) {
/* 230 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 232 */     if (itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
/* 233 */       this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
/* 234 */       player.swingItem();
/*     */       
/* 236 */       if (!this.worldObj.isRemote) {
/* 237 */         ignite();
/* 238 */         itemstack.damageItem(1, (EntityLivingBase)player);
/* 239 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void explode() {
/* 250 */     if (!this.worldObj.isRemote) {
/* 251 */       boolean flag = this.worldObj.getGameRules().getBoolean("mobGriefing");
/* 252 */       float f = getPowered() ? 2.0F : 1.0F;
/* 253 */       this.worldObj.createExplosion((Entity)this, this.posX, this.posY, this.posZ, this.explosionRadius * f, flag);
/* 254 */       setDead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasIgnited() {
/* 259 */     return (this.dataWatcher.getWatchableObjectByte(18) != 0);
/*     */   }
/*     */   
/*     */   public void ignite() {
/* 263 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAIEnabled() {
/* 270 */     return (this.field_175494_bm < 1 && this.worldObj.getGameRules().getBoolean("doMobLoot"));
/*     */   }
/*     */   
/*     */   public void func_175493_co() {
/* 274 */     this.field_175494_bm++;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityCreeper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */