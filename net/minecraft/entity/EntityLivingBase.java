/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.passive.EntityWolf;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagShort;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.potion.PotionHelper;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.CombatTracker;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ 
/*      */ public abstract class EntityLivingBase
/*      */   extends Entity
/*      */ {
/*   57 */   private static final UUID sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
/*   58 */   private static final AttributeModifier sprintingSpeedBoostModifier = (new AttributeModifier(sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.30000001192092896D, 2)).setSaved(false);
/*      */   private BaseAttributeMap attributeMap;
/*   60 */   private final CombatTracker _combatTracker = new CombatTracker(this);
/*   61 */   private final Map<Integer, PotionEffect> activePotionsMap = Maps.newHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   66 */   private final ItemStack[] previousEquipment = new ItemStack[5];
/*      */ 
/*      */   
/*      */   public boolean isSwingInProgress;
/*      */ 
/*      */   
/*      */   public int swingProgressInt;
/*      */ 
/*      */   
/*      */   public int arrowHitTimer;
/*      */ 
/*      */   
/*      */   public int hurtTime;
/*      */ 
/*      */   
/*      */   public int maxHurtTime;
/*      */ 
/*      */   
/*      */   public float attackedAtYaw;
/*      */ 
/*      */   
/*      */   public int deathTime;
/*      */ 
/*      */   
/*      */   public float prevSwingProgress;
/*      */ 
/*      */   
/*      */   public float swingProgress;
/*      */ 
/*      */   
/*      */   public float prevLimbSwingAmount;
/*      */ 
/*      */   
/*      */   public float limbSwingAmount;
/*      */ 
/*      */   
/*      */   public float limbSwing;
/*      */   
/*  104 */   public int maxHurtResistantTime = 20;
/*      */ 
/*      */   
/*      */   public float prevCameraPitch;
/*      */ 
/*      */   
/*      */   public float cameraPitch;
/*      */ 
/*      */   
/*      */   public float randomUnused2;
/*      */   
/*      */   public float randomUnused1;
/*      */   
/*      */   public float renderYawOffset;
/*      */   
/*      */   public float prevRenderYawOffset;
/*      */   
/*      */   public float rotationYawHead;
/*      */   
/*      */   public float prevRotationYawHead;
/*      */   
/*  125 */   public float jumpMovementFactor = 0.02F;
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityPlayer attackingPlayer;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int recentlyHit;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean dead;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int entityAge;
/*      */ 
/*      */ 
/*      */   
/*      */   protected float prevOnGroundSpeedFactor;
/*      */ 
/*      */ 
/*      */   
/*      */   protected float onGroundSpeedFactor;
/*      */ 
/*      */ 
/*      */   
/*      */   protected float movedDistance;
/*      */ 
/*      */ 
/*      */   
/*      */   protected float prevMovedDistance;
/*      */ 
/*      */ 
/*      */   
/*      */   protected float unused180;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int scoreValue;
/*      */ 
/*      */ 
/*      */   
/*      */   protected float lastDamage;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isJumping;
/*      */ 
/*      */ 
/*      */   
/*      */   public float moveStrafing;
/*      */ 
/*      */   
/*      */   public float moveForward;
/*      */ 
/*      */   
/*      */   protected float randomYawVelocity;
/*      */ 
/*      */   
/*      */   protected int newPosRotationIncrements;
/*      */ 
/*      */   
/*      */   protected double newPosX;
/*      */ 
/*      */   
/*      */   protected double newPosY;
/*      */ 
/*      */   
/*      */   protected double newPosZ;
/*      */ 
/*      */   
/*      */   protected double newRotationYaw;
/*      */ 
/*      */   
/*      */   protected double newRotationPitch;
/*      */ 
/*      */   
/*      */   private boolean potionsNeedUpdate = true;
/*      */ 
/*      */   
/*      */   private EntityLivingBase entityLivingToAttack;
/*      */ 
/*      */   
/*      */   private int revengeTimer;
/*      */ 
/*      */   
/*      */   private EntityLivingBase lastAttacker;
/*      */ 
/*      */   
/*      */   private int lastAttackerTime;
/*      */ 
/*      */   
/*      */   private float landMovementFactor;
/*      */ 
/*      */   
/*      */   private int jumpTicks;
/*      */ 
/*      */   
/*      */   private float absorptionAmount;
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  230 */     attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
/*      */   }
/*      */   
/*      */   public EntityLivingBase(World worldIn) {
/*  234 */     super(worldIn);
/*  235 */     applyEntityAttributes();
/*  236 */     setHealth(getMaxHealth());
/*  237 */     this.preventEntitySpawning = true;
/*  238 */     this.randomUnused1 = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
/*  239 */     setPosition(this.posX, this.posY, this.posZ);
/*  240 */     this.randomUnused2 = (float)Math.random() * 12398.0F;
/*  241 */     this.rotationYaw = (float)(Math.random() * Math.PI * 2.0D);
/*  242 */     this.rotationYawHead = this.rotationYaw;
/*  243 */     this.stepHeight = 0.6F;
/*      */   }
/*      */   
/*      */   protected void entityInit() {
/*  247 */     this.dataWatcher.addObject(7, Integer.valueOf(0));
/*  248 */     this.dataWatcher.addObject(8, Byte.valueOf((byte)0));
/*  249 */     this.dataWatcher.addObject(9, Byte.valueOf((byte)0));
/*  250 */     this.dataWatcher.addObject(6, Float.valueOf(1.0F));
/*      */   }
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  254 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
/*  255 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
/*  256 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
/*      */   }
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/*  260 */     if (!isInWater()) {
/*  261 */       handleWaterMovement();
/*      */     }
/*      */     
/*  264 */     if (!this.worldObj.isRemote && this.fallDistance > 3.0F && onGroundIn) {
/*  265 */       IBlockState iblockstate = this.worldObj.getBlockState(pos);
/*  266 */       Block block = iblockstate.getBlock();
/*  267 */       float f = MathHelper.ceiling_float_int(this.fallDistance - 3.0F);
/*      */       
/*  269 */       if (block.getMaterial() != Material.air) {
/*  270 */         double d0 = Math.min(0.2F + f / 15.0F, 10.0F);
/*      */         
/*  272 */         if (d0 > 2.5D) {
/*  273 */           d0 = 2.5D;
/*      */         }
/*      */         
/*  276 */         int i = (int)(150.0D * d0);
/*  277 */         ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(iblockstate) });
/*      */       } 
/*      */     } 
/*      */     
/*  281 */     super.updateFallState(y, onGroundIn, blockIn, pos);
/*      */   }
/*      */   
/*      */   public boolean canBreatheUnderwater() {
/*  285 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  292 */     this.prevSwingProgress = this.swingProgress;
/*  293 */     super.onEntityUpdate();
/*  294 */     this.worldObj.theProfiler.startSection("livingEntityBaseTick");
/*  295 */     boolean flag = this instanceof EntityPlayer;
/*      */     
/*  297 */     if (isEntityAlive()) {
/*  298 */       if (isEntityInsideOpaqueBlock()) {
/*  299 */         attackEntityFrom(DamageSource.inWall, 1.0F);
/*  300 */       } else if (flag && !this.worldObj.getWorldBorder().contains(getEntityBoundingBox())) {
/*  301 */         double d0 = this.worldObj.getWorldBorder().getClosestDistance(this) + this.worldObj.getWorldBorder().getDamageBuffer();
/*      */         
/*  303 */         if (d0 < 0.0D) {
/*  304 */           attackEntityFrom(DamageSource.inWall, Math.max(1, MathHelper.floor_double(-d0 * this.worldObj.getWorldBorder().getDamageAmount())));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  309 */     if (isImmuneToFire() || this.worldObj.isRemote) {
/*  310 */       extinguish();
/*      */     }
/*      */     
/*  313 */     boolean flag1 = (flag && ((EntityPlayer)this).capabilities.disableDamage);
/*      */     
/*  315 */     if (isEntityAlive()) {
/*  316 */       if (isInsideOfMaterial(Material.water)) {
/*  317 */         if (!canBreatheUnderwater() && !isPotionActive(Potion.waterBreathing.id) && !flag1) {
/*  318 */           setAir(decreaseAirSupply(getAir()));
/*      */           
/*  320 */           if (getAir() == -20) {
/*  321 */             setAir(0);
/*      */             
/*  323 */             for (int i = 0; i < 8; i++) {
/*  324 */               float f = this.rand.nextFloat() - this.rand.nextFloat();
/*  325 */               float f1 = this.rand.nextFloat() - this.rand.nextFloat();
/*  326 */               float f2 = this.rand.nextFloat() - this.rand.nextFloat();
/*  327 */               this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f, this.posY + f1, this.posZ + f2, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */             } 
/*      */             
/*  330 */             attackEntityFrom(DamageSource.drown, 2.0F);
/*      */           } 
/*      */         } 
/*      */         
/*  334 */         if (!this.worldObj.isRemote && isRiding() && this.ridingEntity instanceof EntityLivingBase) {
/*  335 */           mountEntity((Entity)null);
/*      */         }
/*      */       } else {
/*  338 */         setAir(300);
/*      */       } 
/*      */     }
/*      */     
/*  342 */     if (isEntityAlive() && isWet()) {
/*  343 */       extinguish();
/*      */     }
/*      */     
/*  346 */     this.prevCameraPitch = this.cameraPitch;
/*      */     
/*  348 */     if (this.hurtTime > 0) {
/*  349 */       this.hurtTime--;
/*      */     }
/*      */     
/*  352 */     if (this.hurtResistantTime > 0 && !(this instanceof net.minecraft.entity.player.EntityPlayerMP)) {
/*  353 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  356 */     if (getHealth() <= 0.0F) {
/*  357 */       onDeathUpdate();
/*      */     }
/*      */     
/*  360 */     if (this.recentlyHit > 0) {
/*  361 */       this.recentlyHit--;
/*      */     } else {
/*  363 */       this.attackingPlayer = null;
/*      */     } 
/*      */     
/*  366 */     if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
/*  367 */       this.lastAttacker = null;
/*      */     }
/*      */     
/*  370 */     if (this.entityLivingToAttack != null) {
/*  371 */       if (!this.entityLivingToAttack.isEntityAlive()) {
/*  372 */         setRevengeTarget((EntityLivingBase)null);
/*  373 */       } else if (this.ticksExisted - this.revengeTimer > 100) {
/*  374 */         setRevengeTarget((EntityLivingBase)null);
/*      */       } 
/*      */     }
/*      */     
/*  378 */     updatePotionEffects();
/*  379 */     this.prevMovedDistance = this.movedDistance;
/*  380 */     this.prevRenderYawOffset = this.renderYawOffset;
/*  381 */     this.prevRotationYawHead = this.rotationYawHead;
/*  382 */     this.prevRotationYaw = this.rotationYaw;
/*  383 */     this.prevRotationPitch = this.rotationPitch;
/*  384 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChild() {
/*  391 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onDeathUpdate() {
/*  398 */     this.deathTime++;
/*      */     
/*  400 */     if (this.deathTime == 20) {
/*  401 */       if (!this.worldObj.isRemote && (this.recentlyHit > 0 || isPlayer()) && canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
/*  402 */         int i = getExperiencePoints(this.attackingPlayer);
/*      */         
/*  404 */         while (i > 0) {
/*  405 */           int j = EntityXPOrb.getXPSplit(i);
/*  406 */           i -= j;
/*  407 */           this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
/*      */         } 
/*      */       } 
/*      */       
/*  411 */       setDead();
/*      */       
/*  413 */       for (int k = 0; k < 20; k++) {
/*  414 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  415 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  416 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  417 */         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, d0, d1, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDropLoot() {
/*  426 */     return !isChild();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int decreaseAirSupply(int p_70682_1_) {
/*  433 */     int i = EnchantmentHelper.getRespiration(this);
/*  434 */     return (i > 0 && this.rand.nextInt(i + 1) > 0) ? p_70682_1_ : (p_70682_1_ - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  441 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/*  448 */     return false;
/*      */   }
/*      */   
/*      */   public Random getRNG() {
/*  452 */     return this.rand;
/*      */   }
/*      */   
/*      */   public EntityLivingBase getAITarget() {
/*  456 */     return this.entityLivingToAttack;
/*      */   }
/*      */   
/*      */   public int getRevengeTimer() {
/*  460 */     return this.revengeTimer;
/*      */   }
/*      */   
/*      */   public void setRevengeTarget(EntityLivingBase livingBase) {
/*  464 */     this.entityLivingToAttack = livingBase;
/*  465 */     this.revengeTimer = this.ticksExisted;
/*      */   }
/*      */   
/*      */   public EntityLivingBase getLastAttacker() {
/*  469 */     return this.lastAttacker;
/*      */   }
/*      */   
/*      */   public int getLastAttackerTime() {
/*  473 */     return this.lastAttackerTime;
/*      */   }
/*      */   
/*      */   public void setLastAttacker(Entity entityIn) {
/*  477 */     if (entityIn instanceof EntityLivingBase) {
/*  478 */       this.lastAttacker = (EntityLivingBase)entityIn;
/*      */     } else {
/*  480 */       this.lastAttacker = null;
/*      */     } 
/*      */     
/*  483 */     this.lastAttackerTime = this.ticksExisted;
/*      */   }
/*      */   
/*      */   public int getAge() {
/*  487 */     return this.entityAge;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  494 */     tagCompound.setFloat("HealF", getHealth());
/*  495 */     tagCompound.setShort("Health", (short)(int)Math.ceil(getHealth()));
/*  496 */     tagCompound.setShort("HurtTime", (short)this.hurtTime);
/*  497 */     tagCompound.setInteger("HurtByTimestamp", this.revengeTimer);
/*  498 */     tagCompound.setShort("DeathTime", (short)this.deathTime);
/*  499 */     tagCompound.setFloat("AbsorptionAmount", getAbsorptionAmount()); byte b; int i;
/*      */     ItemStack[] arrayOfItemStack;
/*  501 */     for (i = (arrayOfItemStack = getInventory()).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/*  502 */       if (itemstack != null) {
/*  503 */         this.attributeMap.removeAttributeModifiers(itemstack.getAttributeModifiers());
/*      */       }
/*      */       b++; }
/*      */     
/*  507 */     tagCompound.setTag("Attributes", (NBTBase)SharedMonsterAttributes.writeBaseAttributeMapToNBT(getAttributeMap()));
/*      */     
/*  509 */     for (i = (arrayOfItemStack = getInventory()).length, b = 0; b < i; ) { ItemStack itemstack1 = arrayOfItemStack[b];
/*  510 */       if (itemstack1 != null) {
/*  511 */         this.attributeMap.applyAttributeModifiers(itemstack1.getAttributeModifiers());
/*      */       }
/*      */       b++; }
/*      */     
/*  515 */     if (!this.activePotionsMap.isEmpty()) {
/*  516 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/*  518 */       for (PotionEffect potioneffect : this.activePotionsMap.values()) {
/*  519 */         nbttaglist.appendTag((NBTBase)potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*      */       }
/*      */       
/*  522 */       tagCompound.setTag("ActiveEffects", (NBTBase)nbttaglist);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  530 */     setAbsorptionAmount(tagCompund.getFloat("AbsorptionAmount"));
/*      */     
/*  532 */     if (tagCompund.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote) {
/*  533 */       SharedMonsterAttributes.setAttributeModifiers(getAttributeMap(), tagCompund.getTagList("Attributes", 10));
/*      */     }
/*      */     
/*  536 */     if (tagCompund.hasKey("ActiveEffects", 9)) {
/*  537 */       NBTTagList nbttaglist = tagCompund.getTagList("ActiveEffects", 10);
/*      */       
/*  539 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  540 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  541 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*      */         
/*  543 */         if (potioneffect != null) {
/*  544 */           this.activePotionsMap.put(Integer.valueOf(potioneffect.getPotionID()), potioneffect);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  549 */     if (tagCompund.hasKey("HealF", 99)) {
/*  550 */       setHealth(tagCompund.getFloat("HealF"));
/*      */     } else {
/*  552 */       NBTBase nbtbase = tagCompund.getTag("Health");
/*      */       
/*  554 */       if (nbtbase == null) {
/*  555 */         setHealth(getMaxHealth());
/*  556 */       } else if (nbtbase.getId() == 5) {
/*  557 */         setHealth(((NBTTagFloat)nbtbase).getFloat());
/*  558 */       } else if (nbtbase.getId() == 2) {
/*  559 */         setHealth(((NBTTagShort)nbtbase).getShort());
/*      */       } 
/*      */     } 
/*      */     
/*  563 */     this.hurtTime = tagCompund.getShort("HurtTime");
/*  564 */     this.deathTime = tagCompund.getShort("DeathTime");
/*  565 */     this.revengeTimer = tagCompund.getInteger("HurtByTimestamp");
/*      */   }
/*      */   
/*      */   protected void updatePotionEffects() {
/*  569 */     Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
/*      */     
/*  571 */     while (iterator.hasNext()) {
/*  572 */       Integer integer = iterator.next();
/*  573 */       PotionEffect potioneffect = this.activePotionsMap.get(integer);
/*      */       
/*  575 */       if (!potioneffect.onUpdate(this)) {
/*  576 */         if (!this.worldObj.isRemote) {
/*  577 */           iterator.remove();
/*  578 */           onFinishedPotionEffect(potioneffect);
/*      */         }  continue;
/*  580 */       }  if (potioneffect.getDuration() % 600 == 0) {
/*  581 */         onChangedPotionEffect(potioneffect, false);
/*      */       }
/*      */     } 
/*      */     
/*  585 */     if (this.potionsNeedUpdate) {
/*  586 */       if (!this.worldObj.isRemote) {
/*  587 */         updatePotionMetadata();
/*      */       }
/*      */       
/*  590 */       this.potionsNeedUpdate = false;
/*      */     } 
/*      */     
/*  593 */     int i = this.dataWatcher.getWatchableObjectInt(7);
/*  594 */     boolean flag1 = (this.dataWatcher.getWatchableObjectByte(8) > 0);
/*      */     
/*  596 */     if (i > 0) {
/*  597 */       int j; boolean flag = false;
/*      */       
/*  599 */       if (!isInvisible()) {
/*  600 */         flag = this.rand.nextBoolean();
/*      */       } else {
/*  602 */         flag = (this.rand.nextInt(15) == 0);
/*      */       } 
/*      */       
/*  605 */       if (flag1) {
/*  606 */         j = flag & ((this.rand.nextInt(5) == 0) ? 1 : 0);
/*      */       }
/*      */       
/*  609 */       if (j != 0 && i > 0) {
/*  610 */         double d0 = (i >> 16 & 0xFF) / 255.0D;
/*  611 */         double d1 = (i >> 8 & 0xFF) / 255.0D;
/*  612 */         double d2 = (i >> 0 & 0xFF) / 255.0D;
/*  613 */         this.worldObj.spawnParticle(flag1 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/*  623 */     if (this.activePotionsMap.isEmpty()) {
/*  624 */       resetPotionEffectMetadata();
/*  625 */       setInvisible(false);
/*      */     } else {
/*  627 */       int i = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
/*  628 */       this.dataWatcher.updateObject(8, Byte.valueOf((byte)(PotionHelper.getAreAmbient(this.activePotionsMap.values()) ? 1 : 0)));
/*  629 */       this.dataWatcher.updateObject(7, Integer.valueOf(i));
/*  630 */       setInvisible(isPotionActive(Potion.invisibility.id));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetPotionEffectMetadata() {
/*  638 */     this.dataWatcher.updateObject(8, Byte.valueOf((byte)0));
/*  639 */     this.dataWatcher.updateObject(7, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   public void clearActivePotions() {
/*  643 */     Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
/*      */     
/*  645 */     while (iterator.hasNext()) {
/*  646 */       Integer integer = iterator.next();
/*  647 */       PotionEffect potioneffect = this.activePotionsMap.get(integer);
/*      */       
/*  649 */       if (!this.worldObj.isRemote) {
/*  650 */         iterator.remove();
/*  651 */         onFinishedPotionEffect(potioneffect);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Collection<PotionEffect> getActivePotionEffects() {
/*  657 */     return this.activePotionsMap.values();
/*      */   }
/*      */   
/*      */   public boolean isPotionActive(int potionId) {
/*  661 */     return this.activePotionsMap.containsKey(Integer.valueOf(potionId));
/*      */   }
/*      */   
/*      */   public boolean isPotionActive(Potion potionIn) {
/*  665 */     return this.activePotionsMap.containsKey(Integer.valueOf(potionIn.id));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PotionEffect getActivePotionEffect(Potion potionIn) {
/*  672 */     return this.activePotionsMap.get(Integer.valueOf(potionIn.id));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPotionEffect(PotionEffect potioneffectIn) {
/*  679 */     if (isPotionApplicable(potioneffectIn)) {
/*  680 */       if (this.activePotionsMap.containsKey(Integer.valueOf(potioneffectIn.getPotionID()))) {
/*  681 */         ((PotionEffect)this.activePotionsMap.get(Integer.valueOf(potioneffectIn.getPotionID()))).combine(potioneffectIn);
/*  682 */         onChangedPotionEffect(this.activePotionsMap.get(Integer.valueOf(potioneffectIn.getPotionID())), true);
/*      */       } else {
/*  684 */         this.activePotionsMap.put(Integer.valueOf(potioneffectIn.getPotionID()), potioneffectIn);
/*  685 */         onNewPotionEffect(potioneffectIn);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
/*  691 */     if (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
/*  692 */       int i = potioneffectIn.getPotionID();
/*      */       
/*  694 */       if (i == Potion.regeneration.id || i == Potion.poison.id) {
/*  695 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  699 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityUndead() {
/*  706 */     return (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePotionEffectClient(int potionId) {
/*  713 */     this.activePotionsMap.remove(Integer.valueOf(potionId));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePotionEffect(int potionId) {
/*  720 */     PotionEffect potioneffect = this.activePotionsMap.remove(Integer.valueOf(potionId));
/*      */     
/*  722 */     if (potioneffect != null) {
/*  723 */       onFinishedPotionEffect(potioneffect);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/*  728 */     this.potionsNeedUpdate = true;
/*      */     
/*  730 */     if (!this.worldObj.isRemote) {
/*  731 */       Potion.potionTypes[id.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     }
/*      */   }
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/*  736 */     this.potionsNeedUpdate = true;
/*      */     
/*  738 */     if (p_70695_2_ && !this.worldObj.isRemote) {
/*  739 */       Potion.potionTypes[id.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), id.getAmplifier());
/*  740 */       Potion.potionTypes[id.getPotionID()].applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect effect) {
/*  745 */     this.potionsNeedUpdate = true;
/*      */     
/*  747 */     if (!this.worldObj.isRemote) {
/*  748 */       Potion.potionTypes[effect.getPotionID()].removeAttributesModifiersFromEntity(this, getAttributeMap(), effect.getAmplifier());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void heal(float healAmount) {
/*  756 */     float f = getHealth();
/*      */     
/*  758 */     if (f > 0.0F) {
/*  759 */       setHealth(f + healAmount);
/*      */     }
/*      */   }
/*      */   
/*      */   public final float getHealth() {
/*  764 */     return this.dataWatcher.getWatchableObjectFloat(6);
/*      */   }
/*      */   
/*      */   public void setHealth(float health) {
/*  768 */     this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(health, 0.0F, getMaxHealth())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  775 */     if (isEntityInvulnerable(source))
/*  776 */       return false; 
/*  777 */     if (this.worldObj.isRemote) {
/*  778 */       return false;
/*      */     }
/*  780 */     this.entityAge = 0;
/*      */     
/*  782 */     if (getHealth() <= 0.0F)
/*  783 */       return false; 
/*  784 */     if (source.isFireDamage() && isPotionActive(Potion.fireResistance)) {
/*  785 */       return false;
/*      */     }
/*  787 */     if ((source == DamageSource.anvil || source == DamageSource.fallingBlock) && getEquipmentInSlot(4) != null) {
/*  788 */       getEquipmentInSlot(4).damageItem((int)(amount * 4.0F + this.rand.nextFloat() * amount * 2.0F), this);
/*  789 */       amount *= 0.75F;
/*      */     } 
/*      */     
/*  792 */     this.limbSwingAmount = 1.5F;
/*  793 */     boolean flag = true;
/*      */     
/*  795 */     if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F) {
/*  796 */       if (amount <= this.lastDamage) {
/*  797 */         return false;
/*      */       }
/*      */       
/*  800 */       damageEntity(source, amount - this.lastDamage);
/*  801 */       this.lastDamage = amount;
/*  802 */       flag = false;
/*      */     } else {
/*  804 */       this.lastDamage = amount;
/*  805 */       this.hurtResistantTime = this.maxHurtResistantTime;
/*  806 */       damageEntity(source, amount);
/*  807 */       this.hurtTime = this.maxHurtTime = 10;
/*      */     } 
/*      */     
/*  810 */     this.attackedAtYaw = 0.0F;
/*  811 */     Entity entity = source.getEntity();
/*      */     
/*  813 */     if (entity != null) {
/*  814 */       if (entity instanceof EntityLivingBase) {
/*  815 */         setRevengeTarget((EntityLivingBase)entity);
/*      */       }
/*      */       
/*  818 */       if (entity instanceof EntityPlayer) {
/*  819 */         this.recentlyHit = 100;
/*  820 */         this.attackingPlayer = (EntityPlayer)entity;
/*  821 */       } else if (entity instanceof EntityWolf) {
/*  822 */         EntityWolf entitywolf = (EntityWolf)entity;
/*      */         
/*  824 */         if (entitywolf.isTamed()) {
/*  825 */           this.recentlyHit = 100;
/*  826 */           this.attackingPlayer = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  831 */     if (flag) {
/*  832 */       this.worldObj.setEntityState(this, (byte)2);
/*      */       
/*  834 */       if (source != DamageSource.drown) {
/*  835 */         setBeenAttacked();
/*      */       }
/*      */       
/*  838 */       if (entity != null) {
/*  839 */         double d1 = entity.posX - this.posX;
/*      */         
/*      */         double d0;
/*  842 */         for (d0 = entity.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
/*  843 */           d1 = (Math.random() - Math.random()) * 0.01D;
/*      */         }
/*      */         
/*  846 */         this.attackedAtYaw = (float)(MathHelper.atan2(d0, d1) * 180.0D / Math.PI - this.rotationYaw);
/*  847 */         knockBack(entity, amount, d1, d0);
/*      */       } else {
/*  849 */         this.attackedAtYaw = ((int)(Math.random() * 2.0D) * 180);
/*      */       } 
/*      */     } 
/*      */     
/*  853 */     if (getHealth() <= 0.0F) {
/*  854 */       String s = getDeathSound();
/*      */       
/*  856 */       if (flag && s != null) {
/*  857 */         playSound(s, getSoundVolume(), getSoundPitch());
/*      */       }
/*      */       
/*  860 */       onDeath(source);
/*      */     } else {
/*  862 */       String s1 = getHurtSound();
/*      */       
/*  864 */       if (flag && s1 != null) {
/*  865 */         playSound(s1, getSoundVolume(), getSoundPitch());
/*      */       }
/*      */     } 
/*      */     
/*  869 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderBrokenItemStack(ItemStack stack) {
/*  878 */     playSound("random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);
/*      */     
/*  880 */     for (int i = 0; i < 5; i++) {
/*  881 */       Vec3 vec3 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*  882 */       vec3 = vec3.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  883 */       vec3 = vec3.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  884 */       double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/*  885 */       Vec3 vec31 = new Vec3((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/*  886 */       vec31 = vec31.rotatePitch(-this.rotationPitch * 3.1415927F / 180.0F);
/*  887 */       vec31 = vec31.rotateYaw(-this.rotationYaw * 3.1415927F / 180.0F);
/*  888 */       vec31 = vec31.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*  889 */       this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, new int[] { Item.getIdFromItem(stack.getItem()) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  897 */     Entity entity = cause.getEntity();
/*  898 */     EntityLivingBase entitylivingbase = getAttackingEntity();
/*      */     
/*  900 */     if (this.scoreValue >= 0 && entitylivingbase != null) {
/*  901 */       entitylivingbase.addToPlayerScore(this, this.scoreValue);
/*      */     }
/*      */     
/*  904 */     if (entity != null) {
/*  905 */       entity.onKillEntity(this);
/*      */     }
/*      */     
/*  908 */     this.dead = true;
/*  909 */     getCombatTracker().reset();
/*      */     
/*  911 */     if (!this.worldObj.isRemote) {
/*  912 */       int i = 0;
/*      */       
/*  914 */       if (entity instanceof EntityPlayer) {
/*  915 */         i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
/*      */       }
/*      */       
/*  918 */       if (canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
/*  919 */         dropFewItems((this.recentlyHit > 0), i);
/*  920 */         dropEquipment((this.recentlyHit > 0), i);
/*      */         
/*  922 */         if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025F + i * 0.01F) {
/*  923 */           addRandomDrop();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  928 */     this.worldObj.setEntityState(this, (byte)3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void knockBack(Entity entityIn, float p_70653_2_, double p_70653_3_, double p_70653_5_) {
/*  945 */     if (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
/*  946 */       this.isAirBorne = true;
/*  947 */       float f = MathHelper.sqrt_double(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
/*  948 */       float f1 = 0.4F;
/*  949 */       this.motionX /= 2.0D;
/*  950 */       this.motionY /= 2.0D;
/*  951 */       this.motionZ /= 2.0D;
/*  952 */       this.motionX -= p_70653_3_ / f * f1;
/*  953 */       this.motionY += f1;
/*  954 */       this.motionZ -= p_70653_5_ / f * f1;
/*      */       
/*  956 */       if (this.motionY > 0.4000000059604645D) {
/*  957 */         this.motionY = 0.4000000059604645D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  966 */     return "game.neutral.hurt";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  973 */     return "game.neutral.die";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addRandomDrop() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnLadder() {
/*  996 */     int i = MathHelper.floor_double(this.posX);
/*  997 */     int j = MathHelper.floor_double((getEntityBoundingBox()).minY);
/*  998 */     int k = MathHelper.floor_double(this.posZ);
/*  999 */     Block block = this.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();
/* 1000 */     return ((block == Blocks.ladder || block == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).isSpectator()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 1007 */     return (!this.isDead && getHealth() > 0.0F);
/*      */   }
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1011 */     super.fall(distance, damageMultiplier);
/* 1012 */     PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
/* 1013 */     float f = (potioneffect != null) ? (potioneffect.getAmplifier() + 1) : 0.0F;
/* 1014 */     int i = MathHelper.ceiling_float_int((distance - 3.0F - f) * damageMultiplier);
/*      */     
/* 1016 */     if (i > 0) {
/* 1017 */       playSound(getFallSoundString(i), 1.0F, 1.0F);
/* 1018 */       attackEntityFrom(DamageSource.fall, i);
/* 1019 */       int j = MathHelper.floor_double(this.posX);
/* 1020 */       int k = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/* 1021 */       int l = MathHelper.floor_double(this.posZ);
/* 1022 */       Block block = this.worldObj.getBlockState(new BlockPos(j, k, l)).getBlock();
/*      */       
/* 1024 */       if (block.getMaterial() != Material.air) {
/* 1025 */         Block.SoundType block$soundtype = block.stepSound;
/* 1026 */         playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.5F, block$soundtype.getFrequency() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String getFallSoundString(int damageValue) {
/* 1032 */     return (damageValue > 4) ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {
/* 1039 */     this.hurtTime = this.maxHurtTime = 10;
/* 1040 */     this.attackedAtYaw = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/* 1047 */     int i = 0; byte b; int j;
/*      */     ItemStack[] arrayOfItemStack;
/* 1049 */     for (j = (arrayOfItemStack = getInventory()).length, b = 0; b < j; ) { ItemStack itemstack = arrayOfItemStack[b];
/* 1050 */       if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
/* 1051 */         int k = ((ItemArmor)itemstack.getItem()).damageReduceAmount;
/* 1052 */         i += k;
/*      */       } 
/*      */       b++; }
/*      */     
/* 1056 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageArmor(float p_70675_1_) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyArmorCalculations(DamageSource source, float damage) {
/* 1066 */     if (!source.isUnblockable()) {
/* 1067 */       int i = 25 - getTotalArmorValue();
/* 1068 */       float f = damage * i;
/* 1069 */       damageArmor(damage);
/* 1070 */       damage = f / 25.0F;
/*      */     } 
/*      */     
/* 1073 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
/* 1080 */     if (source.isDamageAbsolute()) {
/* 1081 */       return damage;
/*      */     }
/* 1083 */     if (isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
/* 1084 */       int i = (getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
/* 1085 */       int j = 25 - i;
/* 1086 */       float f = damage * j;
/* 1087 */       damage = f / 25.0F;
/*      */     } 
/*      */     
/* 1090 */     if (damage <= 0.0F) {
/* 1091 */       return 0.0F;
/*      */     }
/* 1093 */     int k = EnchantmentHelper.getEnchantmentModifierDamage(getInventory(), source);
/*      */     
/* 1095 */     if (k > 20) {
/* 1096 */       k = 20;
/*      */     }
/*      */     
/* 1099 */     if (k > 0 && k <= 20) {
/* 1100 */       int l = 25 - k;
/* 1101 */       float f1 = damage * l;
/* 1102 */       damage = f1 / 25.0F;
/*      */     } 
/*      */     
/* 1105 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1115 */     if (!isEntityInvulnerable(damageSrc)) {
/* 1116 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1117 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1118 */       float f = damageAmount;
/* 1119 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1120 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1122 */       if (damageAmount != 0.0F) {
/* 1123 */         float f1 = getHealth();
/* 1124 */         setHealth(f1 - damageAmount);
/* 1125 */         getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
/* 1126 */         setAbsorptionAmount(getAbsorptionAmount() - damageAmount);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CombatTracker getCombatTracker() {
/* 1135 */     return this._combatTracker;
/*      */   }
/*      */   
/*      */   public EntityLivingBase getAttackingEntity() {
/* 1139 */     return (this._combatTracker.func_94550_c() != null) ? this._combatTracker.func_94550_c() : ((this.attackingPlayer != null) ? (EntityLivingBase)this.attackingPlayer : ((this.entityLivingToAttack != null) ? this.entityLivingToAttack : null));
/*      */   }
/*      */   
/*      */   public final float getMaxHealth() {
/* 1143 */     return (float)getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getArrowCountInEntity() {
/* 1150 */     return this.dataWatcher.getWatchableObjectByte(9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setArrowCountInEntity(int count) {
/* 1157 */     this.dataWatcher.updateObject(9, Byte.valueOf((byte)count));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getArmSwingAnimationEnd() {
/* 1165 */     return isPotionActive(Potion.digSpeed) ? (6 - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1) : (isPotionActive(Potion.digSlowdown) ? (6 + (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void swingItem() {
/* 1172 */     if (!this.isSwingInProgress || this.swingProgressInt >= getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
/* 1173 */       this.swingProgressInt = -1;
/* 1174 */       this.isSwingInProgress = true;
/*      */       
/* 1176 */       if (this.worldObj instanceof WorldServer) {
/* 1177 */         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S0BPacketAnimation(this, 0));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/* 1183 */     if (id == 2) {
/* 1184 */       this.limbSwingAmount = 1.5F;
/* 1185 */       this.hurtResistantTime = this.maxHurtResistantTime;
/* 1186 */       this.hurtTime = this.maxHurtTime = 10;
/* 1187 */       this.attackedAtYaw = 0.0F;
/* 1188 */       String s = getHurtSound();
/*      */       
/* 1190 */       if (s != null) {
/* 1191 */         playSound(getHurtSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */       
/* 1194 */       attackEntityFrom(DamageSource.generic, 0.0F);
/* 1195 */     } else if (id == 3) {
/* 1196 */       String s1 = getDeathSound();
/*      */       
/* 1198 */       if (s1 != null) {
/* 1199 */         playSound(getDeathSound(), getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */       
/* 1202 */       setHealth(0.0F);
/* 1203 */       onDeath(DamageSource.generic);
/*      */     } else {
/* 1205 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/* 1213 */     attackEntityFrom(DamageSource.outOfWorld, 4.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateArmSwingProgress() {
/* 1220 */     int i = getArmSwingAnimationEnd();
/*      */     
/* 1222 */     if (this.isSwingInProgress) {
/* 1223 */       this.swingProgressInt++;
/*      */       
/* 1225 */       if (this.swingProgressInt >= i) {
/* 1226 */         this.swingProgressInt = 0;
/* 1227 */         this.isSwingInProgress = false;
/*      */       } 
/*      */     } else {
/* 1230 */       this.swingProgressInt = 0;
/*      */     } 
/*      */     
/* 1233 */     this.swingProgress = this.swingProgressInt / i;
/*      */   }
/*      */   
/*      */   public IAttributeInstance getEntityAttribute(IAttribute attribute) {
/* 1237 */     return getAttributeMap().getAttributeInstance(attribute);
/*      */   }
/*      */   
/*      */   public BaseAttributeMap getAttributeMap() {
/* 1241 */     if (this.attributeMap == null) {
/* 1242 */       this.attributeMap = (BaseAttributeMap)new ServersideAttributeMap();
/*      */     }
/*      */     
/* 1245 */     return this.attributeMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumCreatureAttribute getCreatureAttribute() {
/* 1252 */     return EnumCreatureAttribute.UNDEFINED;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getHeldItem();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getEquipmentInSlot(int paramInt);
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack getCurrentArmor(int paramInt);
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void setCurrentItemOrArmor(int paramInt, ItemStack paramItemStack);
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 1276 */     super.setSprinting(sprinting);
/* 1277 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/*      */     
/* 1279 */     if (iattributeinstance.getModifier(sprintingSpeedBoostModifierUUID) != null) {
/* 1280 */       iattributeinstance.removeModifier(sprintingSpeedBoostModifier);
/*      */     }
/*      */     
/* 1283 */     if (sprinting) {
/* 1284 */       iattributeinstance.applyModifier(sprintingSpeedBoostModifier);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract ItemStack[] getInventory();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/* 1297 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundPitch() {
/* 1304 */     return isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/* 1311 */     return (getHealth() <= 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismountEntity(Entity entityIn) {
/* 1318 */     double d0 = entityIn.posX;
/* 1319 */     double d1 = (entityIn.getEntityBoundingBox()).minY + entityIn.height;
/* 1320 */     double d2 = entityIn.posZ;
/* 1321 */     int i = 1;
/*      */     
/* 1323 */     for (int j = -i; j <= i; j++) {
/* 1324 */       for (int k = -i; k < i; k++) {
/* 1325 */         if (j != 0 || k != 0) {
/* 1326 */           int l = (int)(this.posX + j);
/* 1327 */           int i1 = (int)(this.posZ + k);
/* 1328 */           AxisAlignedBB axisalignedbb = getEntityBoundingBox().offset(j, 1.0D, k);
/*      */           
/* 1330 */           if (this.worldObj.getCollisionBoxes(axisalignedbb).isEmpty()) {
/* 1331 */             if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(l, (int)this.posY, i1))) {
/* 1332 */               setPositionAndUpdate(this.posX + j, this.posY + 1.0D, this.posZ + k);
/*      */               
/*      */               return;
/*      */             } 
/* 1336 */             if (World.doesBlockHaveSolidTopSurface((IBlockAccess)this.worldObj, new BlockPos(l, (int)this.posY - 1, i1)) || this.worldObj.getBlockState(new BlockPos(l, (int)this.posY - 1, i1)).getBlock().getMaterial() == Material.water) {
/* 1337 */               d0 = this.posX + j;
/* 1338 */               d1 = this.posY + 1.0D;
/* 1339 */               d2 = this.posZ + k;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1346 */     setPositionAndUpdate(d0, d1, d2);
/*      */   }
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 1350 */     return false;
/*      */   }
/*      */   
/*      */   protected float getJumpUpwardsMotion() {
/* 1354 */     return 0.42F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void jump() {
/* 1361 */     this.motionY = getJumpUpwardsMotion();
/*      */     
/* 1363 */     if (isPotionActive(Potion.jump)) {
/* 1364 */       this.motionY += ((getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
/*      */     }
/*      */     
/* 1367 */     if (isSprinting()) {
/* 1368 */       float f = this.rotationYaw * 0.017453292F;
/* 1369 */       this.motionX -= (MathHelper.sin(f) * 0.2F);
/* 1370 */       this.motionZ += (MathHelper.cos(f) * 0.2F);
/*      */     } 
/*      */     
/* 1373 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAITick() {
/* 1380 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */   
/*      */   protected void handleJumpLava() {
/* 1384 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntityWithHeading(float strafe, float forward) {
/* 1391 */     if (isServerWorld()) {
/* 1392 */       if (!isInWater() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
/* 1393 */         if (!isInLava() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
/* 1394 */           float f5, f4 = 0.91F;
/*      */           
/* 1396 */           if (this.onGround) {
/* 1397 */             f4 = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*      */           }
/*      */           
/* 1400 */           float f = 0.16277136F / f4 * f4 * f4;
/*      */ 
/*      */           
/* 1403 */           if (this.onGround) {
/* 1404 */             f5 = getAIMoveSpeed() * f;
/*      */           } else {
/* 1406 */             f5 = this.jumpMovementFactor;
/*      */           } 
/*      */           
/* 1409 */           moveFlying(strafe, forward, f5);
/* 1410 */           f4 = 0.91F;
/*      */           
/* 1412 */           if (this.onGround) {
/* 1413 */             f4 = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.91F;
/*      */           }
/*      */           
/* 1416 */           if (isOnLadder()) {
/* 1417 */             float f6 = 0.15F;
/* 1418 */             this.motionX = MathHelper.clamp_double(this.motionX, -f6, f6);
/* 1419 */             this.motionZ = MathHelper.clamp_double(this.motionZ, -f6, f6);
/* 1420 */             this.fallDistance = 0.0F;
/*      */             
/* 1422 */             if (this.motionY < -0.15D) {
/* 1423 */               this.motionY = -0.15D;
/*      */             }
/*      */             
/* 1426 */             boolean flag = (isSneaking() && this instanceof EntityPlayer);
/*      */             
/* 1428 */             if (flag && this.motionY < 0.0D) {
/* 1429 */               this.motionY = 0.0D;
/*      */             }
/*      */           } 
/*      */           
/* 1433 */           moveEntity(this.motionX, this.motionY, this.motionZ);
/*      */           
/* 1435 */           if (this.isCollidedHorizontally && isOnLadder()) {
/* 1436 */             this.motionY = 0.2D;
/*      */           }
/*      */           
/* 1439 */           if (this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, 0, (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded())) {
/* 1440 */             if (this.posY > 0.0D) {
/* 1441 */               this.motionY = -0.1D;
/*      */             } else {
/* 1443 */               this.motionY = 0.0D;
/*      */             } 
/*      */           } else {
/* 1446 */             this.motionY -= 0.08D;
/*      */           } 
/*      */           
/* 1449 */           this.motionY *= 0.9800000190734863D;
/* 1450 */           this.motionX *= f4;
/* 1451 */           this.motionZ *= f4;
/*      */         } else {
/* 1453 */           double d1 = this.posY;
/* 1454 */           moveFlying(strafe, forward, 0.02F);
/* 1455 */           moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1456 */           this.motionX *= 0.5D;
/* 1457 */           this.motionY *= 0.5D;
/* 1458 */           this.motionZ *= 0.5D;
/* 1459 */           this.motionY -= 0.02D;
/*      */           
/* 1461 */           if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d1, this.motionZ)) {
/* 1462 */             this.motionY = 0.30000001192092896D;
/*      */           }
/*      */         } 
/*      */       } else {
/* 1466 */         double d0 = this.posY;
/* 1467 */         float f1 = 0.8F;
/* 1468 */         float f2 = 0.02F;
/* 1469 */         float f3 = EnchantmentHelper.getDepthStriderModifier(this);
/*      */         
/* 1471 */         if (f3 > 3.0F) {
/* 1472 */           f3 = 3.0F;
/*      */         }
/*      */         
/* 1475 */         if (!this.onGround) {
/* 1476 */           f3 *= 0.5F;
/*      */         }
/*      */         
/* 1479 */         if (f3 > 0.0F) {
/* 1480 */           f1 += (0.54600006F - f1) * f3 / 3.0F;
/* 1481 */           f2 += (getAIMoveSpeed() * 1.0F - f2) * f3 / 3.0F;
/*      */         } 
/*      */         
/* 1484 */         moveFlying(strafe, forward, f2);
/* 1485 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 1486 */         this.motionX *= f1;
/* 1487 */         this.motionY *= 0.800000011920929D;
/* 1488 */         this.motionZ *= f1;
/* 1489 */         this.motionY -= 0.02D;
/*      */         
/* 1491 */         if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ)) {
/* 1492 */           this.motionY = 0.30000001192092896D;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1497 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 1498 */     double d2 = this.posX - this.prevPosX;
/* 1499 */     double d3 = this.posZ - this.prevPosZ;
/* 1500 */     float f7 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4.0F;
/*      */     
/* 1502 */     if (f7 > 1.0F) {
/* 1503 */       f7 = 1.0F;
/*      */     }
/*      */     
/* 1506 */     this.limbSwingAmount += (f7 - this.limbSwingAmount) * 0.4F;
/* 1507 */     this.limbSwing += this.limbSwingAmount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 1514 */     return this.landMovementFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/* 1521 */     this.landMovementFactor = speedIn;
/*      */   }
/*      */   
/*      */   public boolean attackEntityAsMob(Entity entityIn) {
/* 1525 */     setLastAttacker(entityIn);
/* 1526 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 1533 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/* 1540 */     super.onUpdate();
/*      */     
/* 1542 */     if (!this.worldObj.isRemote) {
/* 1543 */       int i = getArrowCountInEntity();
/*      */       
/* 1545 */       if (i > 0) {
/* 1546 */         if (this.arrowHitTimer <= 0) {
/* 1547 */           this.arrowHitTimer = 20 * (30 - i);
/*      */         }
/*      */         
/* 1550 */         this.arrowHitTimer--;
/*      */         
/* 1552 */         if (this.arrowHitTimer <= 0) {
/* 1553 */           setArrowCountInEntity(i - 1);
/*      */         }
/*      */       } 
/*      */       
/* 1557 */       for (int j = 0; j < 5; j++) {
/* 1558 */         ItemStack itemstack = this.previousEquipment[j];
/* 1559 */         ItemStack itemstack1 = getEquipmentInSlot(j);
/*      */         
/* 1561 */         if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/* 1562 */           ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S04PacketEntityEquipment(getEntityId(), j, itemstack1));
/*      */           
/* 1564 */           if (itemstack != null) {
/* 1565 */             this.attributeMap.removeAttributeModifiers(itemstack.getAttributeModifiers());
/*      */           }
/*      */           
/* 1568 */           if (itemstack1 != null) {
/* 1569 */             this.attributeMap.applyAttributeModifiers(itemstack1.getAttributeModifiers());
/*      */           }
/*      */           
/* 1572 */           this.previousEquipment[j] = (itemstack1 == null) ? null : itemstack1.copy();
/*      */         } 
/*      */       } 
/*      */       
/* 1576 */       if (this.ticksExisted % 20 == 0) {
/* 1577 */         getCombatTracker().reset();
/*      */       }
/*      */     } 
/*      */     
/* 1581 */     onLivingUpdate();
/* 1582 */     double d0 = this.posX - this.prevPosX;
/* 1583 */     double d1 = this.posZ - this.prevPosZ;
/* 1584 */     float f = (float)(d0 * d0 + d1 * d1);
/* 1585 */     float f1 = this.renderYawOffset;
/* 1586 */     float f2 = 0.0F;
/* 1587 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 1588 */     float f3 = 0.0F;
/*      */     
/* 1590 */     if (f > 0.0025000002F) {
/* 1591 */       f3 = 1.0F;
/* 1592 */       f2 = (float)Math.sqrt(f) * 3.0F;
/* 1593 */       f1 = (float)MathHelper.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
/*      */     } 
/*      */     
/* 1596 */     if (this.swingProgress > 0.0F) {
/* 1597 */       f1 = this.rotationYaw;
/*      */     }
/*      */     
/* 1600 */     if (!this.onGround) {
/* 1601 */       f3 = 0.0F;
/*      */     }
/*      */     
/* 1604 */     this.onGroundSpeedFactor += (f3 - this.onGroundSpeedFactor) * 0.3F;
/* 1605 */     this.worldObj.theProfiler.startSection("headTurn");
/* 1606 */     f2 = updateDistance(f1, f2);
/* 1607 */     this.worldObj.theProfiler.endSection();
/* 1608 */     this.worldObj.theProfiler.startSection("rangeChecks");
/*      */     
/* 1610 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 1611 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1614 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 1615 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1618 */     while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
/* 1619 */       this.prevRenderYawOffset -= 360.0F;
/*      */     }
/*      */     
/* 1622 */     while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
/* 1623 */       this.prevRenderYawOffset += 360.0F;
/*      */     }
/*      */     
/* 1626 */     while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
/* 1627 */       this.prevRotationPitch -= 360.0F;
/*      */     }
/*      */     
/* 1630 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 1631 */       this.prevRotationPitch += 360.0F;
/*      */     }
/*      */     
/* 1634 */     while (this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
/* 1635 */       this.prevRotationYawHead -= 360.0F;
/*      */     }
/*      */     
/* 1638 */     while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
/* 1639 */       this.prevRotationYawHead += 360.0F;
/*      */     }
/*      */     
/* 1642 */     this.worldObj.theProfiler.endSection();
/* 1643 */     this.movedDistance += f2;
/*      */   }
/*      */   
/*      */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/* 1647 */     float f = MathHelper.wrapAngleTo180_float(p_110146_1_ - this.renderYawOffset);
/* 1648 */     this.renderYawOffset += f * 0.3F;
/* 1649 */     float f1 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
/* 1650 */     boolean flag = !(f1 >= -90.0F && f1 < 90.0F);
/*      */     
/* 1652 */     if (f1 < -75.0F) {
/* 1653 */       f1 = -75.0F;
/*      */     }
/*      */     
/* 1656 */     if (f1 >= 75.0F) {
/* 1657 */       f1 = 75.0F;
/*      */     }
/*      */     
/* 1660 */     this.renderYawOffset = this.rotationYaw - f1;
/*      */     
/* 1662 */     if (f1 * f1 > 2500.0F) {
/* 1663 */       this.renderYawOffset += f1 * 0.2F;
/*      */     }
/*      */     
/* 1666 */     if (flag) {
/* 1667 */       p_110146_2_ *= -1.0F;
/*      */     }
/*      */     
/* 1670 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/* 1678 */     if (this.jumpTicks > 0) {
/* 1679 */       this.jumpTicks--;
/*      */     }
/*      */     
/* 1682 */     if (this.newPosRotationIncrements > 0) {
/* 1683 */       double d0 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
/* 1684 */       double d1 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
/* 1685 */       double d2 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
/* 1686 */       double d3 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
/* 1687 */       this.rotationYaw = (float)(this.rotationYaw + d3 / this.newPosRotationIncrements);
/* 1688 */       this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
/* 1689 */       this.newPosRotationIncrements--;
/* 1690 */       setPosition(d0, d1, d2);
/* 1691 */       setRotation(this.rotationYaw, this.rotationPitch);
/* 1692 */     } else if (!isServerWorld()) {
/* 1693 */       this.motionX *= 0.98D;
/* 1694 */       this.motionY *= 0.98D;
/* 1695 */       this.motionZ *= 0.98D;
/*      */     } 
/*      */     
/* 1698 */     if (Math.abs(this.motionX) < 0.005D) {
/* 1699 */       this.motionX = 0.0D;
/*      */     }
/*      */     
/* 1702 */     if (Math.abs(this.motionY) < 0.005D) {
/* 1703 */       this.motionY = 0.0D;
/*      */     }
/*      */     
/* 1706 */     if (Math.abs(this.motionZ) < 0.005D) {
/* 1707 */       this.motionZ = 0.0D;
/*      */     }
/*      */     
/* 1710 */     this.worldObj.theProfiler.startSection("ai");
/*      */     
/* 1712 */     if (isMovementBlocked()) {
/* 1713 */       this.isJumping = false;
/* 1714 */       this.moveStrafing = 0.0F;
/* 1715 */       this.moveForward = 0.0F;
/* 1716 */       this.randomYawVelocity = 0.0F;
/* 1717 */     } else if (isServerWorld()) {
/* 1718 */       this.worldObj.theProfiler.startSection("newAi");
/* 1719 */       updateEntityActionState();
/* 1720 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/* 1723 */     this.worldObj.theProfiler.endSection();
/* 1724 */     this.worldObj.theProfiler.startSection("jump");
/*      */     
/* 1726 */     if (this.isJumping) {
/* 1727 */       if (isInWater()) {
/* 1728 */         updateAITick();
/* 1729 */       } else if (isInLava()) {
/* 1730 */         handleJumpLava();
/* 1731 */       } else if (this.onGround && this.jumpTicks == 0) {
/* 1732 */         jump();
/* 1733 */         this.jumpTicks = 10;
/*      */       } 
/*      */     } else {
/* 1736 */       this.jumpTicks = 0;
/*      */     } 
/*      */     
/* 1739 */     this.worldObj.theProfiler.endSection();
/* 1740 */     this.worldObj.theProfiler.startSection("travel");
/* 1741 */     this.moveStrafing *= 0.98F;
/* 1742 */     this.moveForward *= 0.98F;
/* 1743 */     this.randomYawVelocity *= 0.9F;
/* 1744 */     moveEntityWithHeading(this.moveStrafing, this.moveForward);
/* 1745 */     this.worldObj.theProfiler.endSection();
/* 1746 */     this.worldObj.theProfiler.startSection("push");
/*      */     
/* 1748 */     if (!this.worldObj.isRemote) {
/* 1749 */       collideWithNearbyEntities();
/*      */     }
/*      */     
/* 1752 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {}
/*      */   
/*      */   protected void collideWithNearbyEntities() {
/* 1759 */     List<Entity> list = this.worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
/*      */             public boolean apply(Entity p_apply_1_) {
/* 1761 */               return p_apply_1_.canBePushed();
/*      */             }
/*      */           }));
/*      */     
/* 1765 */     if (!list.isEmpty()) {
/* 1766 */       for (int i = 0; i < list.size(); i++) {
/* 1767 */         Entity entity = list.get(i);
/* 1768 */         collideWithEntity(entity);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void collideWithEntity(Entity entityIn) {
/* 1774 */     entityIn.applyEntityCollision(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/* 1781 */     if (this.ridingEntity != null && entityIn == null) {
/* 1782 */       if (!this.worldObj.isRemote) {
/* 1783 */         dismountEntity(this.ridingEntity);
/*      */       }
/*      */       
/* 1786 */       if (this.ridingEntity != null) {
/* 1787 */         this.ridingEntity.riddenByEntity = null;
/*      */       }
/*      */       
/* 1790 */       this.ridingEntity = null;
/*      */     } else {
/* 1792 */       super.mountEntity(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 1800 */     super.updateRidden();
/* 1801 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 1802 */     this.onGroundSpeedFactor = 0.0F;
/* 1803 */     this.fallDistance = 0.0F;
/*      */   }
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 1807 */     this.newPosX = x;
/* 1808 */     this.newPosY = y;
/* 1809 */     this.newPosZ = z;
/* 1810 */     this.newRotationYaw = yaw;
/* 1811 */     this.newRotationPitch = pitch;
/* 1812 */     this.newPosRotationIncrements = posRotationIncrements;
/*      */   }
/*      */   
/*      */   public void setJumping(boolean jumping) {
/* 1816 */     this.isJumping = jumping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
/* 1823 */     if (!p_71001_1_.isDead && !this.worldObj.isRemote) {
/* 1824 */       EntityTracker entitytracker = ((WorldServer)this.worldObj).getEntityTracker();
/*      */       
/* 1826 */       if (p_71001_1_ instanceof net.minecraft.entity.item.EntityItem) {
/* 1827 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */       
/* 1830 */       if (p_71001_1_ instanceof net.minecraft.entity.projectile.EntityArrow) {
/* 1831 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */       
/* 1834 */       if (p_71001_1_ instanceof EntityXPOrb) {
/* 1835 */         entitytracker.sendToAllTrackingEntity(p_71001_1_, (Packet)new S0DPacketCollectItem(p_71001_1_.getEntityId(), getEntityId()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canEntityBeSeen(Entity entityIn) {
/* 1844 */     return (this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ), new Vec3(entityIn.posX, entityIn.posY + entityIn.getEyeHeight(), entityIn.posZ)) == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLookVec() {
/* 1851 */     return getLook(1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLook(float partialTicks) {
/* 1858 */     if (partialTicks == 1.0F) {
/* 1859 */       return getVectorForRotation(this.rotationPitch, this.rotationYawHead);
/*      */     }
/* 1861 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 1862 */     float f1 = this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * partialTicks;
/* 1863 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSwingProgress(float partialTickTime) {
/* 1871 */     float f = this.swingProgress - this.prevSwingProgress;
/*      */     
/* 1873 */     if (f < 0.0F) {
/* 1874 */       f++;
/*      */     }
/*      */     
/* 1877 */     return this.prevSwingProgress + f * partialTickTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 1884 */     return !this.worldObj.isRemote;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1891 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 1898 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 1905 */     this.velocityChanged = (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue());
/*      */   }
/*      */   
/*      */   public float getRotationYawHead() {
/* 1909 */     return this.rotationYawHead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {
/* 1916 */     this.rotationYawHead = rotation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderYawOffset(float offset) {
/* 1925 */     this.renderYawOffset = offset;
/*      */   }
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 1929 */     return this.absorptionAmount;
/*      */   }
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 1933 */     if (amount < 0.0F) {
/* 1934 */       amount = 0.0F;
/*      */     }
/*      */     
/* 1937 */     this.absorptionAmount = amount;
/*      */   }
/*      */   
/*      */   public Team getTeam() {
/* 1941 */     return (Team)this.worldObj.getScoreboard().getPlayersTeam(getUniqueID().toString());
/*      */   }
/*      */   
/*      */   public boolean isOnSameTeam(EntityLivingBase otherEntity) {
/* 1945 */     return isOnTeam(otherEntity.getTeam());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnTeam(Team teamIn) {
/* 1952 */     return (getTeam() != null) ? getTeam().isSameTeam(teamIn) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void markPotionsDirty() {
/* 1968 */     this.potionsNeedUpdate = true;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */