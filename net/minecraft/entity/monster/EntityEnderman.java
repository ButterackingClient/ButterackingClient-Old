/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackOnCollide;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityEnderman
/*     */   extends EntityMob
/*     */ {
/*  45 */   private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
/*  46 */   private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448D, 0)).setSaved(false);
/*  47 */   private static final Set<Block> carriableBlocks = Sets.newIdentityHashSet();
/*     */   private boolean isAggressive;
/*     */   
/*     */   public EntityEnderman(World worldIn) {
/*  51 */     super(worldIn);
/*  52 */     setSize(0.6F, 2.9F);
/*  53 */     this.stepHeight = 1.0F;
/*  54 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  55 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackOnCollide(this, 1.0D, false));
/*  56 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  57 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  58 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  59 */     this.tasks.addTask(10, new AIPlaceBlock(this));
/*  60 */     this.tasks.addTask(11, new AITakeBlock(this));
/*  61 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  62 */     this.targetTasks.addTask(2, (EntityAIBase)new AIFindPlayer(this));
/*  63 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate<EntityEndermite>() {
/*     */             public boolean apply(EntityEndermite p_apply_1_) {
/*  65 */               return p_apply_1_.isSpawnedByPlayer();
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  71 */     super.applyEntityAttributes();
/*  72 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
/*  73 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
/*  74 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
/*  75 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  79 */     super.entityInit();
/*  80 */     this.dataWatcher.addObject(16, new Short((short)0));
/*  81 */     this.dataWatcher.addObject(17, new Byte((byte)0));
/*  82 */     this.dataWatcher.addObject(18, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  89 */     super.writeEntityToNBT(tagCompound);
/*  90 */     IBlockState iblockstate = getHeldBlockState();
/*  91 */     tagCompound.setShort("carried", (short)Block.getIdFromBlock(iblockstate.getBlock()));
/*  92 */     tagCompound.setShort("carriedData", (short)iblockstate.getBlock().getMetaFromState(iblockstate));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*     */     IBlockState iblockstate;
/*  99 */     super.readEntityFromNBT(tagCompund);
/*     */ 
/*     */     
/* 102 */     if (tagCompund.hasKey("carried", 8)) {
/* 103 */       iblockstate = Block.getBlockFromName(tagCompund.getString("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
/*     */     } else {
/* 105 */       iblockstate = Block.getBlockById(tagCompund.getShort("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
/*     */     } 
/*     */     
/* 108 */     setHeldBlockState(iblockstate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldAttackPlayer(EntityPlayer player) {
/* 115 */     ItemStack itemstack = player.inventory.armorInventory[3];
/*     */     
/* 117 */     if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
/* 118 */       return false;
/*     */     }
/* 120 */     Vec3 vec3 = player.getLook(1.0F).normalize();
/* 121 */     Vec3 vec31 = new Vec3(this.posX - player.posX, (getEntityBoundingBox()).minY + (this.height / 2.0F) - player.posY + player.getEyeHeight(), this.posZ - player.posZ);
/* 122 */     double d0 = vec31.lengthVector();
/* 123 */     vec31 = vec31.normalize();
/* 124 */     double d1 = vec3.dotProduct(vec31);
/* 125 */     return (d1 > 1.0D - 0.025D / d0) ? player.canEntityBeSeen((Entity)this) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 130 */     return 2.55F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 138 */     if (this.worldObj.isRemote) {
/* 139 */       for (int i = 0; i < 2; i++) {
/* 140 */         this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     
/* 144 */     this.isJumping = false;
/* 145 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/* 149 */     if (isWet()) {
/* 150 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*     */     }
/*     */     
/* 153 */     if (isScreaming() && !this.isAggressive && this.rand.nextInt(100) == 0) {
/* 154 */       setScreaming(false);
/*     */     }
/*     */     
/* 157 */     if (this.worldObj.isDaytime()) {
/* 158 */       float f = getBrightness(1.0F);
/*     */       
/* 160 */       if (f > 0.5F && this.worldObj.canSeeSky(new BlockPos((Entity)this)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
/* 161 */         setAttackTarget(null);
/* 162 */         setScreaming(false);
/* 163 */         this.isAggressive = false;
/* 164 */         teleportRandomly();
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportRandomly() {
/* 175 */     double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 176 */     double d1 = this.posY + (this.rand.nextInt(64) - 32);
/* 177 */     double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 178 */     return teleportTo(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportToEntity(Entity p_70816_1_) {
/* 185 */     Vec3 vec3 = new Vec3(this.posX - p_70816_1_.posX, (getEntityBoundingBox()).minY + (this.height / 2.0F) - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
/* 186 */     vec3 = vec3.normalize();
/* 187 */     double d0 = 16.0D;
/* 188 */     double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
/* 189 */     double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
/* 190 */     double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
/* 191 */     return teleportTo(d1, d2, d3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportTo(double x, double y, double z) {
/* 198 */     double d0 = this.posX;
/* 199 */     double d1 = this.posY;
/* 200 */     double d2 = this.posZ;
/* 201 */     this.posX = x;
/* 202 */     this.posY = y;
/* 203 */     this.posZ = z;
/* 204 */     boolean flag = false;
/* 205 */     BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
/*     */     
/* 207 */     if (this.worldObj.isBlockLoaded(blockpos)) {
/* 208 */       boolean flag1 = false;
/*     */       
/* 210 */       while (!flag1 && blockpos.getY() > 0) {
/* 211 */         BlockPos blockpos1 = blockpos.down();
/* 212 */         Block block = this.worldObj.getBlockState(blockpos1).getBlock();
/*     */         
/* 214 */         if (block.getMaterial().blocksMovement()) {
/* 215 */           flag1 = true; continue;
/*     */         } 
/* 217 */         this.posY--;
/* 218 */         blockpos = blockpos1;
/*     */       } 
/*     */ 
/*     */       
/* 222 */       if (flag1) {
/* 223 */         setPositionAndUpdate(this.posX, this.posY, this.posZ);
/*     */         
/* 225 */         if (this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox())) {
/* 226 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     if (!flag) {
/* 232 */       setPosition(d0, d1, d2);
/* 233 */       return false;
/*     */     } 
/* 235 */     int i = 128;
/*     */     
/* 237 */     for (int j = 0; j < i; j++) {
/* 238 */       double d6 = j / (i - 1.0D);
/* 239 */       float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 240 */       float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 241 */       float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
/* 242 */       double d3 = d0 + (this.posX - d0) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 243 */       double d4 = d1 + (this.posY - d1) * d6 + this.rand.nextDouble() * this.height;
/* 244 */       double d5 = d2 + (this.posZ - d2) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
/* 245 */       this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, f, f1, f2, new int[0]);
/*     */     } 
/*     */     
/* 248 */     this.worldObj.playSoundEffect(d0, d1, d2, "mob.endermen.portal", 1.0F, 1.0F);
/* 249 */     playSound("mob.endermen.portal", 1.0F, 1.0F);
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 258 */     return isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 265 */     return "mob.endermen.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 272 */     return "mob.endermen.death";
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 276 */     return Items.ender_pearl;
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
/* 287 */     Item item = getDropItem();
/*     */     
/* 289 */     if (item != null) {
/* 290 */       int i = this.rand.nextInt(2 + lootingModifier);
/*     */       
/* 292 */       for (int j = 0; j < i; j++) {
/* 293 */         dropItem(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeldBlockState(IBlockState state) {
/* 302 */     this.dataWatcher.updateObject(16, Short.valueOf((short)(Block.getStateId(state) & 0xFFFF)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getHeldBlockState() {
/* 309 */     return Block.getStateById(this.dataWatcher.getWatchableObjectShort(16) & 0xFFFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 316 */     if (isEntityInvulnerable(source)) {
/* 317 */       return false;
/*     */     }
/* 319 */     if (source.getEntity() == null || !(source.getEntity() instanceof EntityEndermite)) {
/* 320 */       if (!this.worldObj.isRemote) {
/* 321 */         setScreaming(true);
/*     */       }
/*     */       
/* 324 */       if (source instanceof net.minecraft.util.EntityDamageSource && source.getEntity() instanceof EntityPlayer) {
/* 325 */         if (source.getEntity() instanceof EntityPlayerMP && ((EntityPlayerMP)source.getEntity()).theItemInWorldManager.isCreative()) {
/* 326 */           setScreaming(false);
/*     */         } else {
/* 328 */           this.isAggressive = true;
/*     */         } 
/*     */       }
/*     */       
/* 332 */       if (source instanceof net.minecraft.util.EntityDamageSourceIndirect) {
/* 333 */         this.isAggressive = false;
/*     */         
/* 335 */         for (int i = 0; i < 64; i++) {
/* 336 */           if (teleportRandomly()) {
/* 337 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 341 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 345 */     boolean flag = super.attackEntityFrom(source, amount);
/*     */     
/* 347 */     if (source.isUnblockable() && this.rand.nextInt(10) != 0) {
/* 348 */       teleportRandomly();
/*     */     }
/*     */     
/* 351 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScreaming() {
/* 356 */     return (this.dataWatcher.getWatchableObjectByte(18) > 0);
/*     */   }
/*     */   
/*     */   public void setScreaming(boolean screaming) {
/* 360 */     this.dataWatcher.updateObject(18, Byte.valueOf((byte)(screaming ? 1 : 0)));
/*     */   }
/*     */   
/*     */   static {
/* 364 */     carriableBlocks.add(Blocks.grass);
/* 365 */     carriableBlocks.add(Blocks.dirt);
/* 366 */     carriableBlocks.add(Blocks.sand);
/* 367 */     carriableBlocks.add(Blocks.gravel);
/* 368 */     carriableBlocks.add(Blocks.yellow_flower);
/* 369 */     carriableBlocks.add(Blocks.red_flower);
/* 370 */     carriableBlocks.add(Blocks.brown_mushroom);
/* 371 */     carriableBlocks.add(Blocks.red_mushroom);
/* 372 */     carriableBlocks.add(Blocks.tnt);
/* 373 */     carriableBlocks.add(Blocks.cactus);
/* 374 */     carriableBlocks.add(Blocks.clay);
/* 375 */     carriableBlocks.add(Blocks.pumpkin);
/* 376 */     carriableBlocks.add(Blocks.melon_block);
/* 377 */     carriableBlocks.add(Blocks.mycelium);
/*     */   }
/*     */   
/*     */   static class AIFindPlayer extends EntityAINearestAttackableTarget {
/*     */     private EntityPlayer player;
/*     */     private int field_179450_h;
/*     */     private int field_179451_i;
/*     */     private EntityEnderman enderman;
/*     */     
/*     */     public AIFindPlayer(EntityEnderman p_i45842_1_) {
/* 387 */       super(p_i45842_1_, EntityPlayer.class, true);
/* 388 */       this.enderman = p_i45842_1_;
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 392 */       double d0 = getTargetDistance();
/* 393 */       List<EntityPlayer> list = this.taskOwner.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), this.targetEntitySelector);
/* 394 */       Collections.sort(list, (Comparator<? super EntityPlayer>)this.theNearestAttackableTargetSorter);
/*     */       
/* 396 */       if (list.isEmpty()) {
/* 397 */         return false;
/*     */       }
/* 399 */       this.player = list.get(0);
/* 400 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 405 */       this.field_179450_h = 5;
/* 406 */       this.field_179451_i = 0;
/*     */     }
/*     */     
/*     */     public void resetTask() {
/* 410 */       this.player = null;
/* 411 */       this.enderman.setScreaming(false);
/* 412 */       IAttributeInstance iattributeinstance = this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 413 */       iattributeinstance.removeModifier(EntityEnderman.attackingSpeedBoostModifier);
/* 414 */       super.resetTask();
/*     */     }
/*     */     
/*     */     public boolean continueExecuting() {
/* 418 */       if (this.player != null) {
/* 419 */         if (!this.enderman.shouldAttackPlayer(this.player)) {
/* 420 */           return false;
/*     */         }
/* 422 */         this.enderman.isAggressive = true;
/* 423 */         this.enderman.faceEntity((Entity)this.player, 10.0F, 10.0F);
/* 424 */         return true;
/*     */       } 
/*     */       
/* 427 */       return super.continueExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 432 */       if (this.player != null) {
/* 433 */         if (--this.field_179450_h <= 0) {
/* 434 */           this.targetEntity = (EntityLivingBase)this.player;
/* 435 */           this.player = null;
/* 436 */           super.startExecuting();
/* 437 */           this.enderman.playSound("mob.endermen.stare", 1.0F, 1.0F);
/* 438 */           this.enderman.setScreaming(true);
/* 439 */           IAttributeInstance iattributeinstance = this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 440 */           iattributeinstance.applyModifier(EntityEnderman.attackingSpeedBoostModifier);
/*     */         } 
/*     */       } else {
/* 443 */         if (this.targetEntity != null) {
/* 444 */           if (this.targetEntity instanceof EntityPlayer && this.enderman.shouldAttackPlayer((EntityPlayer)this.targetEntity)) {
/* 445 */             if (this.targetEntity.getDistanceSqToEntity((Entity)this.enderman) < 16.0D) {
/* 446 */               this.enderman.teleportRandomly();
/*     */             }
/*     */             
/* 449 */             this.field_179451_i = 0;
/* 450 */           } else if (this.targetEntity.getDistanceSqToEntity((Entity)this.enderman) > 256.0D && this.field_179451_i++ >= 30 && this.enderman.teleportToEntity((Entity)this.targetEntity)) {
/* 451 */             this.field_179451_i = 0;
/*     */           } 
/*     */         }
/*     */         
/* 455 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIPlaceBlock extends EntityAIBase {
/*     */     private EntityEnderman enderman;
/*     */     
/*     */     public AIPlaceBlock(EntityEnderman p_i45843_1_) {
/* 464 */       this.enderman = p_i45843_1_;
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 468 */       return !this.enderman.worldObj.getGameRules().getBoolean("mobGriefing") ? false : ((this.enderman.getHeldBlockState().getBlock().getMaterial() == Material.air) ? false : ((this.enderman.getRNG().nextInt(2000) == 0)));
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 472 */       Random random = this.enderman.getRNG();
/* 473 */       World world = this.enderman.worldObj;
/* 474 */       int i = MathHelper.floor_double(this.enderman.posX - 1.0D + random.nextDouble() * 2.0D);
/* 475 */       int j = MathHelper.floor_double(this.enderman.posY + random.nextDouble() * 2.0D);
/* 476 */       int k = MathHelper.floor_double(this.enderman.posZ - 1.0D + random.nextDouble() * 2.0D);
/* 477 */       BlockPos blockpos = new BlockPos(i, j, k);
/* 478 */       Block block = world.getBlockState(blockpos).getBlock();
/* 479 */       Block block1 = world.getBlockState(blockpos.down()).getBlock();
/*     */       
/* 481 */       if (func_179474_a(world, blockpos, this.enderman.getHeldBlockState().getBlock(), block, block1)) {
/* 482 */         world.setBlockState(blockpos, this.enderman.getHeldBlockState(), 3);
/* 483 */         this.enderman.setHeldBlockState(Blocks.air.getDefaultState());
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean func_179474_a(World worldIn, BlockPos p_179474_2_, Block p_179474_3_, Block p_179474_4_, Block p_179474_5_) {
/* 488 */       return !p_179474_3_.canPlaceBlockAt(worldIn, p_179474_2_) ? false : ((p_179474_4_.getMaterial() != Material.air) ? false : ((p_179474_5_.getMaterial() == Material.air) ? false : p_179474_5_.isFullCube()));
/*     */     }
/*     */   }
/*     */   
/*     */   static class AITakeBlock extends EntityAIBase {
/*     */     private EntityEnderman enderman;
/*     */     
/*     */     public AITakeBlock(EntityEnderman p_i45841_1_) {
/* 496 */       this.enderman = p_i45841_1_;
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 500 */       return !this.enderman.worldObj.getGameRules().getBoolean("mobGriefing") ? false : ((this.enderman.getHeldBlockState().getBlock().getMaterial() != Material.air) ? false : ((this.enderman.getRNG().nextInt(20) == 0)));
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 504 */       Random random = this.enderman.getRNG();
/* 505 */       World world = this.enderman.worldObj;
/* 506 */       int i = MathHelper.floor_double(this.enderman.posX - 2.0D + random.nextDouble() * 4.0D);
/* 507 */       int j = MathHelper.floor_double(this.enderman.posY + random.nextDouble() * 3.0D);
/* 508 */       int k = MathHelper.floor_double(this.enderman.posZ - 2.0D + random.nextDouble() * 4.0D);
/* 509 */       BlockPos blockpos = new BlockPos(i, j, k);
/* 510 */       IBlockState iblockstate = world.getBlockState(blockpos);
/* 511 */       Block block = iblockstate.getBlock();
/*     */       
/* 513 */       if (EntityEnderman.carriableBlocks.contains(block)) {
/* 514 */         this.enderman.setHeldBlockState(iblockstate);
/* 515 */         world.setBlockState(blockpos, Blocks.air.getDefaultState());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */