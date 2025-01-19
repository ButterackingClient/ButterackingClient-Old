/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import java.util.UUID;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.EntityAITasks;
/*      */ import net.minecraft.entity.ai.EntityJumpHelper;
/*      */ import net.minecraft.entity.ai.EntityLookHelper;
/*      */ import net.minecraft.entity.ai.EntityMoveHelper;
/*      */ import net.minecraft.entity.ai.EntitySenses;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityGhast;
/*      */ import net.minecraft.entity.passive.EntityTameable;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemSword;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.pathfinding.PathNavigate;
/*      */ import net.minecraft.pathfinding.PathNavigateGround;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.optifine.reflect.Reflector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class EntityLiving
/*      */   extends EntityLivingBase
/*      */ {
/*      */   public int livingSoundTime;
/*      */   protected int experienceValue;
/*      */   private EntityLookHelper lookHelper;
/*      */   protected EntityMoveHelper moveHelper;
/*      */   protected EntityJumpHelper jumpHelper;
/*      */   private EntityBodyHelper bodyHelper;
/*      */   protected PathNavigate navigator;
/*      */   protected final EntityAITasks tasks;
/*      */   protected final EntityAITasks targetTasks;
/*      */   private EntityLivingBase attackTarget;
/*      */   private EntitySenses senses;
/*   83 */   private ItemStack[] equipment = new ItemStack[5];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   protected float[] equipmentDropChances = new float[5];
/*      */ 
/*      */   
/*      */   private boolean canPickUpLoot;
/*      */ 
/*      */   
/*      */   private boolean persistenceRequired;
/*      */   
/*      */   private boolean isLeashed;
/*      */   
/*      */   private Entity leashedToEntity;
/*      */   
/*      */   private NBTTagCompound leashNBTTag;
/*      */   
/*  102 */   private UUID teamUuid = null;
/*  103 */   private String teamUuidString = null;
/*      */   
/*      */   public EntityLiving(World worldIn) {
/*  106 */     super(worldIn);
/*  107 */     this.tasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*  108 */     this.targetTasks = new EntityAITasks((worldIn != null && worldIn.theProfiler != null) ? worldIn.theProfiler : null);
/*  109 */     this.lookHelper = new EntityLookHelper(this);
/*  110 */     this.moveHelper = new EntityMoveHelper(this);
/*  111 */     this.jumpHelper = new EntityJumpHelper(this);
/*  112 */     this.bodyHelper = new EntityBodyHelper(this);
/*  113 */     this.navigator = getNewNavigator(worldIn);
/*  114 */     this.senses = new EntitySenses(this);
/*      */     
/*  116 */     for (int i = 0; i < this.equipmentDropChances.length; i++) {
/*  117 */       this.equipmentDropChances[i] = 0.085F;
/*      */     }
/*      */   }
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  122 */     super.applyEntityAttributes();
/*  123 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PathNavigate getNewNavigator(World worldIn) {
/*  130 */     return (PathNavigate)new PathNavigateGround(this, worldIn);
/*      */   }
/*      */   
/*      */   public EntityLookHelper getLookHelper() {
/*  134 */     return this.lookHelper;
/*      */   }
/*      */   
/*      */   public EntityMoveHelper getMoveHelper() {
/*  138 */     return this.moveHelper;
/*      */   }
/*      */   
/*      */   public EntityJumpHelper getJumpHelper() {
/*  142 */     return this.jumpHelper;
/*      */   }
/*      */   
/*      */   public PathNavigate getNavigator() {
/*  146 */     return this.navigator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntitySenses getEntitySenses() {
/*  153 */     return this.senses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityLivingBase getAttackTarget() {
/*  160 */     return this.attackTarget;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
/*  167 */     this.attackTarget = entitylivingbaseIn;
/*  168 */     Reflector.callVoid(Reflector.ForgeHooks_onLivingSetAttackTarget, new Object[] { this, entitylivingbaseIn });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
/*  175 */     return (cls != EntityGhast.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void eatGrassBonus() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  186 */     super.entityInit();
/*  187 */     this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTalkInterval() {
/*  194 */     return 80;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playLivingSound() {
/*  201 */     String s = getLivingSound();
/*      */     
/*  203 */     if (s != null) {
/*  204 */       playSound(s, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  212 */     super.onEntityUpdate();
/*  213 */     this.worldObj.theProfiler.startSection("mobBaseTick");
/*      */     
/*  215 */     if (isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
/*  216 */       this.livingSoundTime = -getTalkInterval();
/*  217 */       playLivingSound();
/*      */     } 
/*      */     
/*  220 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  227 */     if (this.experienceValue > 0) {
/*  228 */       int i = this.experienceValue;
/*  229 */       ItemStack[] aitemstack = getInventory();
/*      */       
/*  231 */       for (int j = 0; j < aitemstack.length; j++) {
/*  232 */         if (aitemstack[j] != null && this.equipmentDropChances[j] <= 1.0F) {
/*  233 */           i += 1 + this.rand.nextInt(3);
/*      */         }
/*      */       } 
/*      */       
/*  237 */       return i;
/*      */     } 
/*  239 */     return this.experienceValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnExplosionParticle() {
/*  247 */     if (this.worldObj.isRemote) {
/*  248 */       for (int i = 0; i < 20; i++) {
/*  249 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  250 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  251 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  252 */         double d3 = 10.0D;
/*  253 */         this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d0 * d3, this.posY + (this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d2 * d3, d0, d1, d2, new int[0]);
/*      */       } 
/*      */     } else {
/*  256 */       this.worldObj.setEntityState(this, (byte)20);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  261 */     if (id == 20) {
/*  262 */       spawnExplosionParticle();
/*      */     } else {
/*  264 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  272 */     if (Config.isSmoothWorld() && canSkipUpdate()) {
/*  273 */       onUpdateMinimal();
/*      */     } else {
/*  275 */       super.onUpdate();
/*      */       
/*  277 */       if (!this.worldObj.isRemote) {
/*  278 */         updateLeashedState();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/*  284 */     this.bodyHelper.updateRenderAngles();
/*  285 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLivingSound() {
/*  292 */     return null;
/*      */   }
/*      */   
/*      */   protected Item getDropItem() {
/*  296 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/*  307 */     Item item = getDropItem();
/*      */     
/*  309 */     if (item != null) {
/*  310 */       int i = this.rand.nextInt(3);
/*      */       
/*  312 */       if (lootingModifier > 0) {
/*  313 */         i += this.rand.nextInt(lootingModifier + 1);
/*      */       }
/*      */       
/*  316 */       for (int j = 0; j < i; j++) {
/*  317 */         dropItem(item, 1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  326 */     super.writeEntityToNBT(tagCompound);
/*  327 */     tagCompound.setBoolean("CanPickUpLoot", canPickUpLoot());
/*  328 */     tagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
/*  329 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  331 */     for (int i = 0; i < this.equipment.length; i++) {
/*  332 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */       
/*  334 */       if (this.equipment[i] != null) {
/*  335 */         this.equipment[i].writeToNBT(nbttagcompound);
/*      */       }
/*      */       
/*  338 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*      */     } 
/*      */     
/*  341 */     tagCompound.setTag("Equipment", (NBTBase)nbttaglist);
/*  342 */     NBTTagList nbttaglist1 = new NBTTagList();
/*      */     
/*  344 */     for (int j = 0; j < this.equipmentDropChances.length; j++) {
/*  345 */       nbttaglist1.appendTag((NBTBase)new NBTTagFloat(this.equipmentDropChances[j]));
/*      */     }
/*      */     
/*  348 */     tagCompound.setTag("DropChances", (NBTBase)nbttaglist1);
/*  349 */     tagCompound.setBoolean("Leashed", this.isLeashed);
/*      */     
/*  351 */     if (this.leashedToEntity != null) {
/*  352 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */       
/*  354 */       if (this.leashedToEntity instanceof EntityLivingBase) {
/*  355 */         nbttagcompound1.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
/*  356 */         nbttagcompound1.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
/*  357 */       } else if (this.leashedToEntity instanceof EntityHanging) {
/*  358 */         BlockPos blockpos = ((EntityHanging)this.leashedToEntity).getHangingPosition();
/*  359 */         nbttagcompound1.setInteger("X", blockpos.getX());
/*  360 */         nbttagcompound1.setInteger("Y", blockpos.getY());
/*  361 */         nbttagcompound1.setInteger("Z", blockpos.getZ());
/*      */       } 
/*      */       
/*  364 */       tagCompound.setTag("Leash", (NBTBase)nbttagcompound1);
/*      */     } 
/*      */     
/*  367 */     if (isAIDisabled()) {
/*  368 */       tagCompound.setBoolean("NoAI", isAIDisabled());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  376 */     super.readEntityFromNBT(tagCompund);
/*      */     
/*  378 */     if (tagCompund.hasKey("CanPickUpLoot", 1)) {
/*  379 */       setCanPickUpLoot(tagCompund.getBoolean("CanPickUpLoot"));
/*      */     }
/*      */     
/*  382 */     this.persistenceRequired = tagCompund.getBoolean("PersistenceRequired");
/*      */     
/*  384 */     if (tagCompund.hasKey("Equipment", 9)) {
/*  385 */       NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);
/*      */       
/*  387 */       for (int i = 0; i < this.equipment.length; i++) {
/*  388 */         this.equipment[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*      */       }
/*      */     } 
/*      */     
/*  392 */     if (tagCompund.hasKey("DropChances", 9)) {
/*  393 */       NBTTagList nbttaglist1 = tagCompund.getTagList("DropChances", 5);
/*      */       
/*  395 */       for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*  396 */         this.equipmentDropChances[j] = nbttaglist1.getFloatAt(j);
/*      */       }
/*      */     } 
/*      */     
/*  400 */     this.isLeashed = tagCompund.getBoolean("Leashed");
/*      */     
/*  402 */     if (this.isLeashed && tagCompund.hasKey("Leash", 10)) {
/*  403 */       this.leashNBTTag = tagCompund.getCompoundTag("Leash");
/*      */     }
/*      */     
/*  406 */     setNoAI(tagCompund.getBoolean("NoAI"));
/*      */   }
/*      */   
/*      */   public void setMoveForward(float p_70657_1_) {
/*  410 */     this.moveForward = p_70657_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/*  417 */     super.setAIMoveSpeed(speedIn);
/*  418 */     setMoveForward(speedIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  426 */     super.onLivingUpdate();
/*  427 */     this.worldObj.theProfiler.startSection("looting");
/*      */     
/*  429 */     if (!this.worldObj.isRemote && canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
/*  430 */       for (EntityItem entityitem : this.worldObj.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D))) {
/*  431 */         if (!entityitem.isDead && entityitem.getEntityItem() != null && !entityitem.cannotPickup()) {
/*  432 */           updateEquipmentIfNeeded(entityitem);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  437 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/*  445 */     ItemStack itemstack = itemEntity.getEntityItem();
/*  446 */     int i = getArmorPosition(itemstack);
/*      */     
/*  448 */     if (i > -1) {
/*  449 */       boolean flag = true;
/*  450 */       ItemStack itemstack1 = getEquipmentInSlot(i);
/*      */       
/*  452 */       if (itemstack1 != null) {
/*  453 */         if (i == 0) {
/*  454 */           if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
/*  455 */             flag = true;
/*  456 */           } else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
/*  457 */             ItemSword itemsword = (ItemSword)itemstack.getItem();
/*  458 */             ItemSword itemsword1 = (ItemSword)itemstack1.getItem();
/*      */             
/*  460 */             if (itemsword.getDamageVsEntity() != itemsword1.getDamageVsEntity()) {
/*  461 */               flag = (itemsword.getDamageVsEntity() > itemsword1.getDamageVsEntity());
/*      */             } else {
/*  463 */               flag = !(itemstack.getMetadata() <= itemstack1.getMetadata() && (!itemstack.hasTagCompound() || itemstack1.hasTagCompound()));
/*      */             } 
/*  465 */           } else if (itemstack.getItem() instanceof net.minecraft.item.ItemBow && itemstack1.getItem() instanceof net.minecraft.item.ItemBow) {
/*  466 */             flag = (itemstack.hasTagCompound() && !itemstack1.hasTagCompound());
/*      */           } else {
/*  468 */             flag = false;
/*      */           } 
/*  470 */         } else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
/*  471 */           flag = true;
/*  472 */         } else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor) {
/*  473 */           ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*  474 */           ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();
/*      */           
/*  476 */           if (itemarmor.damageReduceAmount != itemarmor1.damageReduceAmount) {
/*  477 */             flag = (itemarmor.damageReduceAmount > itemarmor1.damageReduceAmount);
/*      */           } else {
/*  479 */             flag = !(itemstack.getMetadata() <= itemstack1.getMetadata() && (!itemstack.hasTagCompound() || itemstack1.hasTagCompound()));
/*      */           } 
/*      */         } else {
/*  482 */           flag = false;
/*      */         } 
/*      */       }
/*      */       
/*  486 */       if (flag && func_175448_a(itemstack)) {
/*  487 */         if (itemstack1 != null && this.rand.nextFloat() - 0.1F < this.equipmentDropChances[i]) {
/*  488 */           entityDropItem(itemstack1, 0.0F);
/*      */         }
/*      */         
/*  491 */         if (itemstack.getItem() == Items.diamond && itemEntity.getThrower() != null) {
/*  492 */           EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(itemEntity.getThrower());
/*      */           
/*  494 */           if (entityplayer != null) {
/*  495 */             entityplayer.triggerAchievement((StatBase)AchievementList.diamondsToYou);
/*      */           }
/*      */         } 
/*      */         
/*  499 */         setCurrentItemOrArmor(i, itemstack);
/*  500 */         this.equipmentDropChances[i] = 2.0F;
/*  501 */         this.persistenceRequired = true;
/*  502 */         onItemPickup((Entity)itemEntity, 1);
/*  503 */         itemEntity.setDead();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean func_175448_a(ItemStack stack) {
/*  509 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDespawn() {
/*  516 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void despawnEntity() {
/*  523 */     Object object = null;
/*  524 */     Object object1 = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
/*  525 */     Object object2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
/*      */     
/*  527 */     if (this.persistenceRequired) {
/*  528 */       this.entityAge = 0;
/*  529 */     } else if ((this.entityAge & 0x1F) == 31 && (object = Reflector.call(Reflector.ForgeEventFactory_canEntityDespawn, new Object[] { this })) != object1) {
/*  530 */       if (object == object2) {
/*  531 */         this.entityAge = 0;
/*      */       } else {
/*  533 */         setDead();
/*      */       } 
/*      */     } else {
/*  536 */       EntityPlayer entityPlayer = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
/*      */       
/*  538 */       if (entityPlayer != null) {
/*  539 */         double d0 = ((Entity)entityPlayer).posX - this.posX;
/*  540 */         double d1 = ((Entity)entityPlayer).posY - this.posY;
/*  541 */         double d2 = ((Entity)entityPlayer).posZ - this.posZ;
/*  542 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  544 */         if (canDespawn() && d3 > 16384.0D) {
/*  545 */           setDead();
/*      */         }
/*      */         
/*  548 */         if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0D && canDespawn()) {
/*  549 */           setDead();
/*  550 */         } else if (d3 < 1024.0D) {
/*  551 */           this.entityAge = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void updateEntityActionState() {
/*  558 */     this.entityAge++;
/*  559 */     this.worldObj.theProfiler.startSection("checkDespawn");
/*  560 */     despawnEntity();
/*  561 */     this.worldObj.theProfiler.endSection();
/*  562 */     this.worldObj.theProfiler.startSection("sensing");
/*  563 */     this.senses.clearSensingCache();
/*  564 */     this.worldObj.theProfiler.endSection();
/*  565 */     this.worldObj.theProfiler.startSection("targetSelector");
/*  566 */     this.targetTasks.onUpdateTasks();
/*  567 */     this.worldObj.theProfiler.endSection();
/*  568 */     this.worldObj.theProfiler.startSection("goalSelector");
/*  569 */     this.tasks.onUpdateTasks();
/*  570 */     this.worldObj.theProfiler.endSection();
/*  571 */     this.worldObj.theProfiler.startSection("navigation");
/*  572 */     this.navigator.onUpdateNavigation();
/*  573 */     this.worldObj.theProfiler.endSection();
/*  574 */     this.worldObj.theProfiler.startSection("mob tick");
/*  575 */     updateAITasks();
/*  576 */     this.worldObj.theProfiler.endSection();
/*  577 */     this.worldObj.theProfiler.startSection("controls");
/*  578 */     this.worldObj.theProfiler.startSection("move");
/*  579 */     this.moveHelper.onUpdateMoveHelper();
/*  580 */     this.worldObj.theProfiler.endStartSection("look");
/*  581 */     this.lookHelper.onUpdateLook();
/*  582 */     this.worldObj.theProfiler.endStartSection("jump");
/*  583 */     this.jumpHelper.doJump();
/*  584 */     this.worldObj.theProfiler.endSection();
/*  585 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateAITasks() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVerticalFaceSpeed() {
/*  596 */     return 40;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void faceEntity(Entity entityIn, float p_70625_2_, float p_70625_3_) {
/*  603 */     double d2, d0 = entityIn.posX - this.posX;
/*  604 */     double d1 = entityIn.posZ - this.posZ;
/*      */ 
/*      */     
/*  607 */     if (entityIn instanceof EntityLivingBase) {
/*  608 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
/*  609 */       d2 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - this.posY + getEyeHeight();
/*      */     } else {
/*  611 */       d2 = ((entityIn.getEntityBoundingBox()).minY + (entityIn.getEntityBoundingBox()).maxY) / 2.0D - this.posY + getEyeHeight();
/*      */     } 
/*      */     
/*  614 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
/*  615 */     float f = (float)(MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
/*  616 */     float f1 = (float)-(MathHelper.atan2(d2, d3) * 180.0D / Math.PI);
/*  617 */     this.rotationPitch = updateRotation(this.rotationPitch, f1, p_70625_3_);
/*  618 */     this.rotationYaw = updateRotation(this.rotationYaw, f, p_70625_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
/*  625 */     float f = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
/*      */     
/*  627 */     if (f > p_70663_3_) {
/*  628 */       f = p_70663_3_;
/*      */     }
/*      */     
/*  631 */     if (f < -p_70663_3_) {
/*  632 */       f = -p_70663_3_;
/*      */     }
/*      */     
/*  635 */     return p_70663_1_ + f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnHere() {
/*  642 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNotColliding() {
/*  649 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRenderSizeModifier() {
/*  656 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSpawnedInChunk() {
/*  663 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/*  670 */     if (getAttackTarget() == null) {
/*  671 */       return 3;
/*      */     }
/*  673 */     int i = (int)(getHealth() - getMaxHealth() * 0.33F);
/*  674 */     i -= (3 - this.worldObj.getDifficulty().getDifficultyId()) * 4;
/*      */     
/*  676 */     if (i < 0) {
/*  677 */       i = 0;
/*      */     }
/*      */     
/*  680 */     return i + 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItem() {
/*  688 */     return this.equipment[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getEquipmentInSlot(int slotIn) {
/*  695 */     return this.equipment[slotIn];
/*      */   }
/*      */   
/*      */   public ItemStack getCurrentArmor(int slotIn) {
/*  699 */     return this.equipment[slotIn + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/*  706 */     this.equipment[slotIn] = stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/*  713 */     return this.equipment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
/*  724 */     for (int i = 0; i < (getInventory()).length; i++) {
/*  725 */       ItemStack itemstack = getEquipmentInSlot(i);
/*  726 */       boolean flag = (this.equipmentDropChances[i] > 1.0F);
/*      */       
/*  728 */       if (itemstack != null && (wasRecentlyHit || flag) && this.rand.nextFloat() - lootingModifier * 0.01F < this.equipmentDropChances[i]) {
/*  729 */         if (!flag && itemstack.isItemStackDamageable()) {
/*  730 */           int j = Math.max(itemstack.getMaxDamage() - 25, 1);
/*  731 */           int k = itemstack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(j) + 1);
/*      */           
/*  733 */           if (k > j) {
/*  734 */             k = j;
/*      */           }
/*      */           
/*  737 */           if (k < 1) {
/*  738 */             k = 1;
/*      */           }
/*      */           
/*  741 */           itemstack.setItemDamage(k);
/*      */         } 
/*      */         
/*  744 */         entityDropItem(itemstack, 0.0F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/*  753 */     if (this.rand.nextFloat() < 0.15F * difficulty.getClampedAdditionalDifficulty()) {
/*  754 */       int i = this.rand.nextInt(2);
/*  755 */       float f = (this.worldObj.getDifficulty() == EnumDifficulty.HARD) ? 0.1F : 0.25F;
/*      */       
/*  757 */       if (this.rand.nextFloat() < 0.095F) {
/*  758 */         i++;
/*      */       }
/*      */       
/*  761 */       if (this.rand.nextFloat() < 0.095F) {
/*  762 */         i++;
/*      */       }
/*      */       
/*  765 */       if (this.rand.nextFloat() < 0.095F) {
/*  766 */         i++;
/*      */       }
/*      */       
/*  769 */       for (int j = 3; j >= 0; j--) {
/*  770 */         ItemStack itemstack = getCurrentArmor(j);
/*      */         
/*  772 */         if (j < 3 && this.rand.nextFloat() < f) {
/*      */           break;
/*      */         }
/*      */         
/*  776 */         if (itemstack == null) {
/*  777 */           Item item = getArmorItemForSlot(j + 1, i);
/*      */           
/*  779 */           if (item != null) {
/*  780 */             setCurrentItemOrArmor(j + 1, new ItemStack(item));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static int getArmorPosition(ItemStack stack) {
/*  788 */     if (stack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && stack.getItem() != Items.skull) {
/*  789 */       if (stack.getItem() instanceof ItemArmor) {
/*  790 */         switch (((ItemArmor)stack.getItem()).armorType) {
/*      */           case 0:
/*  792 */             return 4;
/*      */           
/*      */           case 1:
/*  795 */             return 3;
/*      */           
/*      */           case 2:
/*  798 */             return 2;
/*      */           
/*      */           case 3:
/*  801 */             return 1;
/*      */         } 
/*      */       
/*      */       }
/*  805 */       return 0;
/*      */     } 
/*  807 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Item getArmorItemForSlot(int armorSlot, int itemTier) {
/*  815 */     switch (armorSlot) {
/*      */       case 4:
/*  817 */         if (itemTier == 0)
/*  818 */           return (Item)Items.leather_helmet; 
/*  819 */         if (itemTier == 1)
/*  820 */           return (Item)Items.golden_helmet; 
/*  821 */         if (itemTier == 2)
/*  822 */           return (Item)Items.chainmail_helmet; 
/*  823 */         if (itemTier == 3)
/*  824 */           return (Item)Items.iron_helmet; 
/*  825 */         if (itemTier == 4) {
/*  826 */           return (Item)Items.diamond_helmet;
/*      */         }
/*      */       
/*      */       case 3:
/*  830 */         if (itemTier == 0)
/*  831 */           return (Item)Items.leather_chestplate; 
/*  832 */         if (itemTier == 1)
/*  833 */           return (Item)Items.golden_chestplate; 
/*  834 */         if (itemTier == 2)
/*  835 */           return (Item)Items.chainmail_chestplate; 
/*  836 */         if (itemTier == 3)
/*  837 */           return (Item)Items.iron_chestplate; 
/*  838 */         if (itemTier == 4) {
/*  839 */           return (Item)Items.diamond_chestplate;
/*      */         }
/*      */       
/*      */       case 2:
/*  843 */         if (itemTier == 0)
/*  844 */           return (Item)Items.leather_leggings; 
/*  845 */         if (itemTier == 1)
/*  846 */           return (Item)Items.golden_leggings; 
/*  847 */         if (itemTier == 2)
/*  848 */           return (Item)Items.chainmail_leggings; 
/*  849 */         if (itemTier == 3)
/*  850 */           return (Item)Items.iron_leggings; 
/*  851 */         if (itemTier == 4) {
/*  852 */           return (Item)Items.diamond_leggings;
/*      */         }
/*      */       
/*      */       case 1:
/*  856 */         if (itemTier == 0)
/*  857 */           return (Item)Items.leather_boots; 
/*  858 */         if (itemTier == 1)
/*  859 */           return (Item)Items.golden_boots; 
/*  860 */         if (itemTier == 2)
/*  861 */           return (Item)Items.chainmail_boots; 
/*  862 */         if (itemTier == 3)
/*  863 */           return (Item)Items.iron_boots; 
/*  864 */         if (itemTier == 4) {
/*  865 */           return (Item)Items.diamond_boots;
/*      */         }
/*      */         break;
/*      */     } 
/*  869 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {
/*  877 */     float f = difficulty.getClampedAdditionalDifficulty();
/*      */     
/*  879 */     if (getHeldItem() != null && this.rand.nextFloat() < 0.25F * f) {
/*  880 */       EnchantmentHelper.addRandomEnchantment(this.rand, getHeldItem(), (int)(5.0F + f * this.rand.nextInt(18)));
/*      */     }
/*      */     
/*  883 */     for (int i = 0; i < 4; i++) {
/*  884 */       ItemStack itemstack = getCurrentArmor(i);
/*      */       
/*  886 */       if (itemstack != null && this.rand.nextFloat() < 0.5F * f) {
/*  887 */         EnchantmentHelper.addRandomEnchantment(this.rand, itemstack, (int)(5.0F + f * this.rand.nextInt(18)));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/*  897 */     getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
/*  898 */     return livingdata;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeSteered() {
/*  906 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enablePersistence() {
/*  913 */     this.persistenceRequired = true;
/*      */   }
/*      */   
/*      */   public void setEquipmentDropChance(int slotIn, float chance) {
/*  917 */     this.equipmentDropChances[slotIn] = chance;
/*      */   }
/*      */   
/*      */   public boolean canPickUpLoot() {
/*  921 */     return this.canPickUpLoot;
/*      */   }
/*      */   
/*      */   public void setCanPickUpLoot(boolean canPickup) {
/*  925 */     this.canPickUpLoot = canPickup;
/*      */   }
/*      */   
/*      */   public boolean isNoDespawnRequired() {
/*  929 */     return this.persistenceRequired;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean interactFirst(EntityPlayer playerIn) {
/*  936 */     if (getLeashed() && getLeashedToEntity() == playerIn) {
/*  937 */       clearLeashed(true, !playerIn.capabilities.isCreativeMode);
/*  938 */       return true;
/*      */     } 
/*  940 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*      */     
/*  942 */     if (itemstack != null && itemstack.getItem() == Items.lead && allowLeashing()) {
/*  943 */       if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
/*  944 */         setLeashedToEntity((Entity)playerIn, true);
/*  945 */         itemstack.stackSize--;
/*  946 */         return true;
/*      */       } 
/*      */       
/*  949 */       if (((EntityTameable)this).isOwner((EntityLivingBase)playerIn)) {
/*  950 */         setLeashedToEntity((Entity)playerIn, true);
/*  951 */         itemstack.stackSize--;
/*  952 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  956 */     if (interact(playerIn)) {
/*  957 */       return true;
/*      */     }
/*  959 */     return super.interactFirst(playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean interact(EntityPlayer player) {
/*  968 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLeashedState() {
/*  975 */     if (this.leashNBTTag != null) {
/*  976 */       recreateLeash();
/*      */     }
/*      */     
/*  979 */     if (this.isLeashed) {
/*  980 */       if (!isEntityAlive()) {
/*  981 */         clearLeashed(true, true);
/*      */       }
/*      */       
/*  984 */       if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
/*  985 */         clearLeashed(true, true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearLeashed(boolean sendPacket, boolean dropLead) {
/*  994 */     if (this.isLeashed) {
/*  995 */       this.isLeashed = false;
/*  996 */       this.leashedToEntity = null;
/*      */       
/*  998 */       if (!this.worldObj.isRemote && dropLead) {
/*  999 */         dropItem(Items.lead, 1);
/*      */       }
/*      */       
/* 1002 */       if (!this.worldObj.isRemote && sendPacket && this.worldObj instanceof WorldServer) {
/* 1003 */         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S1BPacketEntityAttach(1, this, null));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean allowLeashing() {
/* 1009 */     return (!getLeashed() && !(this instanceof net.minecraft.entity.monster.IMob));
/*      */   }
/*      */   
/*      */   public boolean getLeashed() {
/* 1013 */     return this.isLeashed;
/*      */   }
/*      */   
/*      */   public Entity getLeashedToEntity() {
/* 1017 */     return this.leashedToEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLeashedToEntity(Entity entityIn, boolean sendAttachNotification) {
/* 1024 */     this.isLeashed = true;
/* 1025 */     this.leashedToEntity = entityIn;
/*      */     
/* 1027 */     if (!this.worldObj.isRemote && sendAttachNotification && this.worldObj instanceof WorldServer) {
/* 1028 */       ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, (Packet)new S1BPacketEntityAttach(1, this, this.leashedToEntity));
/*      */     }
/*      */   }
/*      */   
/*      */   private void recreateLeash() {
/* 1033 */     if (this.isLeashed && this.leashNBTTag != null) {
/* 1034 */       if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
/* 1035 */         UUID uuid = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));
/*      */         
/* 1037 */         for (EntityLivingBase entitylivingbase : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D))) {
/* 1038 */           if (entitylivingbase.getUniqueID().equals(uuid)) {
/* 1039 */             this.leashedToEntity = entitylivingbase;
/*      */             break;
/*      */           } 
/*      */         } 
/* 1043 */       } else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
/* 1044 */         BlockPos blockpos = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
/* 1045 */         EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(this.worldObj, blockpos);
/*      */         
/* 1047 */         if (entityleashknot == null) {
/* 1048 */           entityleashknot = EntityLeashKnot.createKnot(this.worldObj, blockpos);
/*      */         }
/*      */         
/* 1051 */         this.leashedToEntity = entityleashknot;
/*      */       } else {
/* 1053 */         clearLeashed(false, true);
/*      */       } 
/*      */     }
/*      */     
/* 1057 */     this.leashNBTTag = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*      */     int i;
/* 1063 */     if (inventorySlot == 99) {
/* 1064 */       i = 0;
/*      */     } else {
/* 1066 */       i = inventorySlot - 100 + 1;
/*      */       
/* 1068 */       if (i < 0 || i >= this.equipment.length) {
/* 1069 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1073 */     if (itemStackIn == null || getArmorPosition(itemStackIn) == i || (i == 4 && itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock)) {
/* 1074 */       setCurrentItemOrArmor(i, itemStackIn);
/* 1075 */       return true;
/*      */     } 
/* 1077 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 1085 */     return (super.isServerWorld() && !isAIDisabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoAI(boolean disable) {
/* 1092 */     this.dataWatcher.updateObject(15, Byte.valueOf((byte)(disable ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAIDisabled() {
/* 1099 */     return (this.dataWatcher.getWatchableObjectByte(15) != 0);
/*      */   }
/*      */   
/*      */   private boolean canSkipUpdate() {
/* 1103 */     if (isChild())
/* 1104 */       return false; 
/* 1105 */     if (this.hurtTime > 0)
/* 1106 */       return false; 
/* 1107 */     if (this.ticksExisted < 20) {
/* 1108 */       return false;
/*      */     }
/* 1110 */     World world = getEntityWorld();
/*      */     
/* 1112 */     if (world == null)
/* 1113 */       return false; 
/* 1114 */     if (world.playerEntities.size() != 1) {
/* 1115 */       return false;
/*      */     }
/* 1117 */     Entity entity = world.playerEntities.get(0);
/* 1118 */     double d0 = Math.max(Math.abs(this.posX - entity.posX) - 16.0D, 0.0D);
/* 1119 */     double d1 = Math.max(Math.abs(this.posZ - entity.posZ) - 16.0D, 0.0D);
/* 1120 */     double d2 = d0 * d0 + d1 * d1;
/* 1121 */     return !isInRangeToRenderDist(d2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdateMinimal() {
/* 1127 */     this.entityAge++;
/*      */     
/* 1129 */     if (this instanceof net.minecraft.entity.monster.EntityMob) {
/* 1130 */       float f = getBrightness(1.0F);
/*      */       
/* 1132 */       if (f > 0.5F) {
/* 1133 */         this.entityAge += 2;
/*      */       }
/*      */     } 
/*      */     
/* 1137 */     despawnEntity();
/*      */   }
/*      */   
/*      */   public Team getTeam() {
/* 1141 */     UUID uuid = getUniqueID();
/*      */     
/* 1143 */     if (this.teamUuid != uuid) {
/* 1144 */       this.teamUuid = uuid;
/* 1145 */       this.teamUuidString = uuid.toString();
/*      */     } 
/*      */     
/* 1148 */     return (Team)this.worldObj.getScoreboard().getPlayersTeam(this.teamUuidString);
/*      */   }
/*      */   
/*      */   public enum SpawnPlacementType {
/* 1152 */     ON_GROUND,
/* 1153 */     IN_AIR,
/* 1154 */     IN_WATER;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */