/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.BlockRailPowered;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*     */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityMinecart
/*     */   extends Entity
/*     */   implements IWorldNameable
/*     */ {
/*     */   private boolean isInReverse;
/*     */   private String entityName;
/*  42 */   private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1 }, { 1 } }, { { -1, -1 }, { 1 } }, { { -1 }, { 1, -1 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1 } }, { { 0, 0, 1 }, { -1 } }, { { 0, 0, -1 }, { -1 } }, { { 0, 0, -1 }, { 1 } } };
/*     */   
/*     */   private int turnProgress;
/*     */   
/*     */   private double minecartX;
/*     */   
/*     */   private double minecartY;
/*     */   
/*     */   private double minecartZ;
/*     */   private double minecartYaw;
/*     */   private double minecartPitch;
/*     */   private double velocityX;
/*     */   private double velocityY;
/*     */   private double velocityZ;
/*     */   
/*     */   public EntityMinecart(World worldIn) {
/*  58 */     super(worldIn);
/*  59 */     this.preventEntitySpawning = true;
/*  60 */     setSize(0.98F, 0.7F);
/*     */   }
/*     */   
/*     */   public static EntityMinecart getMinecart(World worldIn, double x, double y, double z, EnumMinecartType type) {
/*  64 */     switch (type) {
/*     */       case null:
/*  66 */         return new EntityMinecartChest(worldIn, x, y, z);
/*     */       
/*     */       case FURNACE:
/*  69 */         return new EntityMinecartFurnace(worldIn, x, y, z);
/*     */       
/*     */       case TNT:
/*  72 */         return new EntityMinecartTNT(worldIn, x, y, z);
/*     */       
/*     */       case SPAWNER:
/*  75 */         return (EntityMinecart)new EntityMinecartMobSpawner(worldIn, x, y, z);
/*     */       
/*     */       case HOPPER:
/*  78 */         return new EntityMinecartHopper(worldIn, x, y, z);
/*     */       
/*     */       case COMMAND_BLOCK:
/*  81 */         return (EntityMinecart)new EntityMinecartCommandBlock(worldIn, x, y, z);
/*     */     } 
/*     */     
/*  84 */     return new EntityMinecartEmpty(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  97 */     this.dataWatcher.addObject(17, new Integer(0));
/*  98 */     this.dataWatcher.addObject(18, new Integer(1));
/*  99 */     this.dataWatcher.addObject(19, new Float(0.0F));
/* 100 */     this.dataWatcher.addObject(20, new Integer(0));
/* 101 */     this.dataWatcher.addObject(21, new Integer(6));
/* 102 */     this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/* 110 */     return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/* 124 */     return true;
/*     */   }
/*     */   
/*     */   public EntityMinecart(World worldIn, double x, double y, double z) {
/* 128 */     this(worldIn);
/* 129 */     setPosition(x, y, z);
/* 130 */     this.motionX = 0.0D;
/* 131 */     this.motionY = 0.0D;
/* 132 */     this.motionZ = 0.0D;
/* 133 */     this.prevPosX = x;
/* 134 */     this.prevPosY = y;
/* 135 */     this.prevPosZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/* 142 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 149 */     if (!this.worldObj.isRemote && !this.isDead) {
/* 150 */       if (isEntityInvulnerable(source)) {
/* 151 */         return false;
/*     */       }
/* 153 */       setRollingDirection(-getRollingDirection());
/* 154 */       setRollingAmplitude(10);
/* 155 */       setBeenAttacked();
/* 156 */       setDamage(getDamage() + amount * 10.0F);
/* 157 */       boolean flag = (source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode);
/*     */       
/* 159 */       if (flag || getDamage() > 40.0F) {
/* 160 */         if (this.riddenByEntity != null) {
/* 161 */           this.riddenByEntity.mountEntity(null);
/*     */         }
/*     */         
/* 164 */         if (flag && !hasCustomName()) {
/* 165 */           setDead();
/*     */         } else {
/* 167 */           killMinecart(source);
/*     */         } 
/*     */       } 
/*     */       
/* 171 */       return true;
/*     */     } 
/*     */     
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/* 179 */     setDead();
/*     */     
/* 181 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 182 */       ItemStack itemstack = new ItemStack(Items.minecart, 1);
/*     */       
/* 184 */       if (this.entityName != null) {
/* 185 */         itemstack.setStackDisplayName(this.entityName);
/*     */       }
/*     */       
/* 188 */       entityDropItem(itemstack, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performHurtAnimation() {
/* 196 */     setRollingDirection(-getRollingDirection());
/* 197 */     setRollingAmplitude(10);
/* 198 */     setDamage(getDamage() + getDamage() * 10.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 205 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 212 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 219 */     if (getRollingAmplitude() > 0) {
/* 220 */       setRollingAmplitude(getRollingAmplitude() - 1);
/*     */     }
/*     */     
/* 223 */     if (getDamage() > 0.0F) {
/* 224 */       setDamage(getDamage() - 1.0F);
/*     */     }
/*     */     
/* 227 */     if (this.posY < -64.0D) {
/* 228 */       kill();
/*     */     }
/*     */     
/* 231 */     if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
/* 232 */       this.worldObj.theProfiler.startSection("portal");
/* 233 */       MinecraftServer minecraftserver = ((WorldServer)this.worldObj).getMinecraftServer();
/* 234 */       int i = getMaxInPortalTime();
/*     */       
/* 236 */       if (this.inPortal) {
/* 237 */         if (minecraftserver.getAllowNether()) {
/* 238 */           if (this.ridingEntity == null && this.portalCounter++ >= i) {
/* 239 */             int j; this.portalCounter = i;
/* 240 */             this.timeUntilPortal = getPortalCooldown();
/*     */ 
/*     */             
/* 243 */             if (this.worldObj.provider.getDimensionId() == -1) {
/* 244 */               j = 0;
/*     */             } else {
/* 246 */               j = -1;
/*     */             } 
/*     */             
/* 249 */             travelToDimension(j);
/*     */           } 
/*     */           
/* 252 */           this.inPortal = false;
/*     */         } 
/*     */       } else {
/* 255 */         if (this.portalCounter > 0) {
/* 256 */           this.portalCounter -= 4;
/*     */         }
/*     */         
/* 259 */         if (this.portalCounter < 0) {
/* 260 */           this.portalCounter = 0;
/*     */         }
/*     */       } 
/*     */       
/* 264 */       if (this.timeUntilPortal > 0) {
/* 265 */         this.timeUntilPortal--;
/*     */       }
/*     */       
/* 268 */       this.worldObj.theProfiler.endSection();
/*     */     } 
/*     */     
/* 271 */     if (this.worldObj.isRemote) {
/* 272 */       if (this.turnProgress > 0) {
/* 273 */         double d4 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
/* 274 */         double d5 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
/* 275 */         double d6 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
/* 276 */         double d1 = MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw);
/* 277 */         this.rotationYaw = (float)(this.rotationYaw + d1 / this.turnProgress);
/* 278 */         this.rotationPitch = (float)(this.rotationPitch + (this.minecartPitch - this.rotationPitch) / this.turnProgress);
/* 279 */         this.turnProgress--;
/* 280 */         setPosition(d4, d5, d6);
/* 281 */         setRotation(this.rotationYaw, this.rotationPitch);
/*     */       } else {
/* 283 */         setPosition(this.posX, this.posY, this.posZ);
/* 284 */         setRotation(this.rotationYaw, this.rotationPitch);
/*     */       } 
/*     */     } else {
/* 287 */       this.prevPosX = this.posX;
/* 288 */       this.prevPosY = this.posY;
/* 289 */       this.prevPosZ = this.posZ;
/* 290 */       this.motionY -= 0.03999999910593033D;
/* 291 */       int k = MathHelper.floor_double(this.posX);
/* 292 */       int l = MathHelper.floor_double(this.posY);
/* 293 */       int i1 = MathHelper.floor_double(this.posZ);
/*     */       
/* 295 */       if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(k, l - 1, i1))) {
/* 296 */         l--;
/*     */       }
/*     */       
/* 299 */       BlockPos blockpos = new BlockPos(k, l, i1);
/* 300 */       IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/*     */       
/* 302 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/* 303 */         func_180460_a(blockpos, iblockstate);
/*     */         
/* 305 */         if (iblockstate.getBlock() == Blocks.activator_rail) {
/* 306 */           onActivatorRailPass(k, l, i1, ((Boolean)iblockstate.getValue((IProperty)BlockRailPowered.POWERED)).booleanValue());
/*     */         }
/*     */       } else {
/* 309 */         moveDerailedMinecart();
/*     */       } 
/*     */       
/* 312 */       doBlockCollisions();
/* 313 */       this.rotationPitch = 0.0F;
/* 314 */       double d0 = this.prevPosX - this.posX;
/* 315 */       double d2 = this.prevPosZ - this.posZ;
/*     */       
/* 317 */       if (d0 * d0 + d2 * d2 > 0.001D) {
/* 318 */         this.rotationYaw = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI);
/*     */         
/* 320 */         if (this.isInReverse) {
/* 321 */           this.rotationYaw += 180.0F;
/*     */         }
/*     */       } 
/*     */       
/* 325 */       double d3 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
/*     */       
/* 327 */       if (d3 < -170.0D || d3 >= 170.0D) {
/* 328 */         this.rotationYaw += 180.0F;
/* 329 */         this.isInReverse = !this.isInReverse;
/*     */       } 
/*     */       
/* 332 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */       
/* 334 */       for (Entity entity : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
/* 335 */         if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart) {
/* 336 */           entity.applyEntityCollision(this);
/*     */         }
/*     */       } 
/*     */       
/* 340 */       if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
/* 341 */         if (this.riddenByEntity.ridingEntity == this) {
/* 342 */           this.riddenByEntity.ridingEntity = null;
/*     */         }
/*     */         
/* 345 */         this.riddenByEntity = null;
/*     */       } 
/*     */       
/* 348 */       handleWaterMovement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getMaximumSpeed() {
/* 356 */     return 0.4D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void moveDerailedMinecart() {
/* 369 */     double d0 = getMaximumSpeed();
/* 370 */     this.motionX = MathHelper.clamp_double(this.motionX, -d0, d0);
/* 371 */     this.motionZ = MathHelper.clamp_double(this.motionZ, -d0, d0);
/*     */     
/* 373 */     if (this.onGround) {
/* 374 */       this.motionX *= 0.5D;
/* 375 */       this.motionY *= 0.5D;
/* 376 */       this.motionZ *= 0.5D;
/*     */     } 
/*     */     
/* 379 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/* 381 */     if (!this.onGround) {
/* 382 */       this.motionX *= 0.949999988079071D;
/* 383 */       this.motionY *= 0.949999988079071D;
/* 384 */       this.motionZ *= 0.949999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_) {
/* 390 */     this.fallDistance = 0.0F;
/* 391 */     Vec3 vec3 = func_70489_a(this.posX, this.posY, this.posZ);
/* 392 */     this.posY = p_180460_1_.getY();
/* 393 */     boolean flag = false;
/* 394 */     boolean flag1 = false;
/* 395 */     BlockRailBase blockrailbase = (BlockRailBase)p_180460_2_.getBlock();
/*     */     
/* 397 */     if (blockrailbase == Blocks.golden_rail) {
/* 398 */       flag = ((Boolean)p_180460_2_.getValue((IProperty)BlockRailPowered.POWERED)).booleanValue();
/* 399 */       flag1 = !flag;
/*     */     } 
/*     */     
/* 402 */     double d0 = 0.0078125D;
/* 403 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)p_180460_2_.getValue(blockrailbase.getShapeProperty());
/*     */     
/* 405 */     switch (blockrailbase$enumraildirection) {
/*     */       case null:
/* 407 */         this.motionX -= 0.0078125D;
/* 408 */         this.posY++;
/*     */         break;
/*     */       
/*     */       case ASCENDING_WEST:
/* 412 */         this.motionX += 0.0078125D;
/* 413 */         this.posY++;
/*     */         break;
/*     */       
/*     */       case ASCENDING_NORTH:
/* 417 */         this.motionZ += 0.0078125D;
/* 418 */         this.posY++;
/*     */         break;
/*     */       
/*     */       case ASCENDING_SOUTH:
/* 422 */         this.motionZ -= 0.0078125D;
/* 423 */         this.posY++;
/*     */         break;
/*     */     } 
/* 426 */     int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
/* 427 */     double d1 = (aint[1][0] - aint[0][0]);
/* 428 */     double d2 = (aint[1][2] - aint[0][2]);
/* 429 */     double d3 = Math.sqrt(d1 * d1 + d2 * d2);
/* 430 */     double d4 = this.motionX * d1 + this.motionZ * d2;
/*     */     
/* 432 */     if (d4 < 0.0D) {
/* 433 */       d1 = -d1;
/* 434 */       d2 = -d2;
/*     */     } 
/*     */     
/* 437 */     double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */     
/* 439 */     if (d5 > 2.0D) {
/* 440 */       d5 = 2.0D;
/*     */     }
/*     */     
/* 443 */     this.motionX = d5 * d1 / d3;
/* 444 */     this.motionZ = d5 * d2 / d3;
/*     */     
/* 446 */     if (this.riddenByEntity instanceof EntityLivingBase) {
/* 447 */       double d6 = ((EntityLivingBase)this.riddenByEntity).moveForward;
/*     */       
/* 449 */       if (d6 > 0.0D) {
/* 450 */         double d7 = -Math.sin((this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F));
/* 451 */         double d8 = Math.cos((this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F));
/* 452 */         double d9 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*     */         
/* 454 */         if (d9 < 0.01D) {
/* 455 */           this.motionX += d7 * 0.1D;
/* 456 */           this.motionZ += d8 * 0.1D;
/* 457 */           flag1 = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 462 */     if (flag1) {
/* 463 */       double d17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */       
/* 465 */       if (d17 < 0.03D) {
/* 466 */         this.motionX *= 0.0D;
/* 467 */         this.motionY *= 0.0D;
/* 468 */         this.motionZ *= 0.0D;
/*     */       } else {
/* 470 */         this.motionX *= 0.5D;
/* 471 */         this.motionY *= 0.0D;
/* 472 */         this.motionZ *= 0.5D;
/*     */       } 
/*     */     } 
/*     */     
/* 476 */     double d18 = 0.0D;
/* 477 */     double d19 = p_180460_1_.getX() + 0.5D + aint[0][0] * 0.5D;
/* 478 */     double d20 = p_180460_1_.getZ() + 0.5D + aint[0][2] * 0.5D;
/* 479 */     double d21 = p_180460_1_.getX() + 0.5D + aint[1][0] * 0.5D;
/* 480 */     double d10 = p_180460_1_.getZ() + 0.5D + aint[1][2] * 0.5D;
/* 481 */     d1 = d21 - d19;
/* 482 */     d2 = d10 - d20;
/*     */     
/* 484 */     if (d1 == 0.0D) {
/* 485 */       this.posX = p_180460_1_.getX() + 0.5D;
/* 486 */       d18 = this.posZ - p_180460_1_.getZ();
/* 487 */     } else if (d2 == 0.0D) {
/* 488 */       this.posZ = p_180460_1_.getZ() + 0.5D;
/* 489 */       d18 = this.posX - p_180460_1_.getX();
/*     */     } else {
/* 491 */       double d11 = this.posX - d19;
/* 492 */       double d12 = this.posZ - d20;
/* 493 */       d18 = (d11 * d1 + d12 * d2) * 2.0D;
/*     */     } 
/*     */     
/* 496 */     this.posX = d19 + d1 * d18;
/* 497 */     this.posZ = d20 + d2 * d18;
/* 498 */     setPosition(this.posX, this.posY, this.posZ);
/* 499 */     double d22 = this.motionX;
/* 500 */     double d23 = this.motionZ;
/*     */     
/* 502 */     if (this.riddenByEntity != null) {
/* 503 */       d22 *= 0.75D;
/* 504 */       d23 *= 0.75D;
/*     */     } 
/*     */     
/* 507 */     double d13 = getMaximumSpeed();
/* 508 */     d22 = MathHelper.clamp_double(d22, -d13, d13);
/* 509 */     d23 = MathHelper.clamp_double(d23, -d13, d13);
/* 510 */     moveEntity(d22, 0.0D, d23);
/*     */     
/* 512 */     if (aint[0][1] != 0 && MathHelper.floor_double(this.posX) - p_180460_1_.getX() == aint[0][0] && MathHelper.floor_double(this.posZ) - p_180460_1_.getZ() == aint[0][2]) {
/* 513 */       setPosition(this.posX, this.posY + aint[0][1], this.posZ);
/* 514 */     } else if (aint[1][1] != 0 && MathHelper.floor_double(this.posX) - p_180460_1_.getX() == aint[1][0] && MathHelper.floor_double(this.posZ) - p_180460_1_.getZ() == aint[1][2]) {
/* 515 */       setPosition(this.posX, this.posY + aint[1][1], this.posZ);
/*     */     } 
/*     */     
/* 518 */     applyDrag();
/* 519 */     Vec3 vec31 = func_70489_a(this.posX, this.posY, this.posZ);
/*     */     
/* 521 */     if (vec31 != null && vec3 != null) {
/* 522 */       double d14 = (vec3.yCoord - vec31.yCoord) * 0.05D;
/* 523 */       d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */       
/* 525 */       if (d5 > 0.0D) {
/* 526 */         this.motionX = this.motionX / d5 * (d5 + d14);
/* 527 */         this.motionZ = this.motionZ / d5 * (d5 + d14);
/*     */       } 
/*     */       
/* 530 */       setPosition(this.posX, vec31.yCoord, this.posZ);
/*     */     } 
/*     */     
/* 533 */     int j = MathHelper.floor_double(this.posX);
/* 534 */     int i = MathHelper.floor_double(this.posZ);
/*     */     
/* 536 */     if (j != p_180460_1_.getX() || i != p_180460_1_.getZ()) {
/* 537 */       d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 538 */       this.motionX = d5 * (j - p_180460_1_.getX());
/* 539 */       this.motionZ = d5 * (i - p_180460_1_.getZ());
/*     */     } 
/*     */     
/* 542 */     if (flag) {
/* 543 */       double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */       
/* 545 */       if (d15 > 0.01D) {
/* 546 */         double d16 = 0.06D;
/* 547 */         this.motionX += this.motionX / d15 * d16;
/* 548 */         this.motionZ += this.motionZ / d15 * d16;
/* 549 */       } else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/* 550 */         if (this.worldObj.getBlockState(p_180460_1_.west()).getBlock().isNormalCube()) {
/* 551 */           this.motionX = 0.02D;
/* 552 */         } else if (this.worldObj.getBlockState(p_180460_1_.east()).getBlock().isNormalCube()) {
/* 553 */           this.motionX = -0.02D;
/*     */         } 
/* 555 */       } else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/* 556 */         if (this.worldObj.getBlockState(p_180460_1_.north()).getBlock().isNormalCube()) {
/* 557 */           this.motionZ = 0.02D;
/* 558 */         } else if (this.worldObj.getBlockState(p_180460_1_.south()).getBlock().isNormalCube()) {
/* 559 */           this.motionZ = -0.02D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void applyDrag() {
/* 566 */     if (this.riddenByEntity != null) {
/* 567 */       this.motionX *= 0.996999979019165D;
/* 568 */       this.motionY *= 0.0D;
/* 569 */       this.motionZ *= 0.996999979019165D;
/*     */     } else {
/* 571 */       this.motionX *= 0.9599999785423279D;
/* 572 */       this.motionY *= 0.0D;
/* 573 */       this.motionZ *= 0.9599999785423279D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/* 581 */     this.posX = x;
/* 582 */     this.posY = y;
/* 583 */     this.posZ = z;
/* 584 */     float f = this.width / 2.0F;
/* 585 */     float f1 = this.height;
/* 586 */     setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
/*     */   }
/*     */   
/*     */   public Vec3 func_70495_a(double p_70495_1_, double p_70495_3_, double p_70495_5_, double p_70495_7_) {
/* 590 */     int i = MathHelper.floor_double(p_70495_1_);
/* 591 */     int j = MathHelper.floor_double(p_70495_3_);
/* 592 */     int k = MathHelper.floor_double(p_70495_5_);
/*     */     
/* 594 */     if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k))) {
/* 595 */       j--;
/*     */     }
/*     */     
/* 598 */     IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
/*     */     
/* 600 */     if (BlockRailBase.isRailBlock(iblockstate)) {
/* 601 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty());
/* 602 */       p_70495_3_ = j;
/*     */       
/* 604 */       if (blockrailbase$enumraildirection.isAscending()) {
/* 605 */         p_70495_3_ = (j + 1);
/*     */       }
/*     */       
/* 608 */       int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
/* 609 */       double d0 = (aint[1][0] - aint[0][0]);
/* 610 */       double d1 = (aint[1][2] - aint[0][2]);
/* 611 */       double d2 = Math.sqrt(d0 * d0 + d1 * d1);
/* 612 */       d0 /= d2;
/* 613 */       d1 /= d2;
/* 614 */       p_70495_1_ += d0 * p_70495_7_;
/* 615 */       p_70495_5_ += d1 * p_70495_7_;
/*     */       
/* 617 */       if (aint[0][1] != 0 && MathHelper.floor_double(p_70495_1_) - i == aint[0][0] && MathHelper.floor_double(p_70495_5_) - k == aint[0][2]) {
/* 618 */         p_70495_3_ += aint[0][1];
/* 619 */       } else if (aint[1][1] != 0 && MathHelper.floor_double(p_70495_1_) - i == aint[1][0] && MathHelper.floor_double(p_70495_5_) - k == aint[1][2]) {
/* 620 */         p_70495_3_ += aint[1][1];
/*     */       } 
/*     */       
/* 623 */       return func_70489_a(p_70495_1_, p_70495_3_, p_70495_5_);
/*     */     } 
/* 625 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 func_70489_a(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
/* 630 */     int i = MathHelper.floor_double(p_70489_1_);
/* 631 */     int j = MathHelper.floor_double(p_70489_3_);
/* 632 */     int k = MathHelper.floor_double(p_70489_5_);
/*     */     
/* 634 */     if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k))) {
/* 635 */       j--;
/*     */     }
/*     */     
/* 638 */     IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
/*     */     
/* 640 */     if (BlockRailBase.isRailBlock(iblockstate)) {
/* 641 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty());
/* 642 */       int[][] aint = matrix[blockrailbase$enumraildirection.getMetadata()];
/* 643 */       double d0 = 0.0D;
/* 644 */       double d1 = i + 0.5D + aint[0][0] * 0.5D;
/* 645 */       double d2 = j + 0.0625D + aint[0][1] * 0.5D;
/* 646 */       double d3 = k + 0.5D + aint[0][2] * 0.5D;
/* 647 */       double d4 = i + 0.5D + aint[1][0] * 0.5D;
/* 648 */       double d5 = j + 0.0625D + aint[1][1] * 0.5D;
/* 649 */       double d6 = k + 0.5D + aint[1][2] * 0.5D;
/* 650 */       double d7 = d4 - d1;
/* 651 */       double d8 = (d5 - d2) * 2.0D;
/* 652 */       double d9 = d6 - d3;
/*     */       
/* 654 */       if (d7 == 0.0D) {
/* 655 */         p_70489_1_ = i + 0.5D;
/* 656 */         d0 = p_70489_5_ - k;
/* 657 */       } else if (d9 == 0.0D) {
/* 658 */         p_70489_5_ = k + 0.5D;
/* 659 */         d0 = p_70489_1_ - i;
/*     */       } else {
/* 661 */         double d10 = p_70489_1_ - d1;
/* 662 */         double d11 = p_70489_5_ - d3;
/* 663 */         d0 = (d10 * d7 + d11 * d9) * 2.0D;
/*     */       } 
/*     */       
/* 666 */       p_70489_1_ = d1 + d7 * d0;
/* 667 */       p_70489_3_ = d2 + d8 * d0;
/* 668 */       p_70489_5_ = d3 + d9 * d0;
/*     */       
/* 670 */       if (d8 < 0.0D) {
/* 671 */         p_70489_3_++;
/*     */       }
/*     */       
/* 674 */       if (d8 > 0.0D) {
/* 675 */         p_70489_3_ += 0.5D;
/*     */       }
/*     */       
/* 678 */       return new Vec3(p_70489_1_, p_70489_3_, p_70489_5_);
/*     */     } 
/* 680 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 688 */     if (tagCompund.getBoolean("CustomDisplayTile")) {
/* 689 */       int i = tagCompund.getInteger("DisplayData");
/*     */       
/* 691 */       if (tagCompund.hasKey("DisplayTile", 8)) {
/* 692 */         Block block = Block.getBlockFromName(tagCompund.getString("DisplayTile"));
/*     */         
/* 694 */         if (block == null) {
/* 695 */           func_174899_a(Blocks.air.getDefaultState());
/*     */         } else {
/* 697 */           func_174899_a(block.getStateFromMeta(i));
/*     */         } 
/*     */       } else {
/* 700 */         Block block1 = Block.getBlockById(tagCompund.getInteger("DisplayTile"));
/*     */         
/* 702 */         if (block1 == null) {
/* 703 */           func_174899_a(Blocks.air.getDefaultState());
/*     */         } else {
/* 705 */           func_174899_a(block1.getStateFromMeta(i));
/*     */         } 
/*     */       } 
/*     */       
/* 709 */       setDisplayTileOffset(tagCompund.getInteger("DisplayOffset"));
/*     */     } 
/*     */     
/* 712 */     if (tagCompund.hasKey("CustomName", 8) && tagCompund.getString("CustomName").length() > 0) {
/* 713 */       this.entityName = tagCompund.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 721 */     if (hasDisplayTile()) {
/* 722 */       tagCompound.setBoolean("CustomDisplayTile", true);
/* 723 */       IBlockState iblockstate = getDisplayTile();
/* 724 */       ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(iblockstate.getBlock());
/* 725 */       tagCompound.setString("DisplayTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 726 */       tagCompound.setInteger("DisplayData", iblockstate.getBlock().getMetaFromState(iblockstate));
/* 727 */       tagCompound.setInteger("DisplayOffset", getDisplayTileOffset());
/*     */     } 
/*     */     
/* 730 */     if (this.entityName != null && this.entityName.length() > 0) {
/* 731 */       tagCompound.setString("CustomName", this.entityName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEntityCollision(Entity entityIn) {
/* 739 */     if (!this.worldObj.isRemote && 
/* 740 */       !entityIn.noClip && !this.noClip && 
/* 741 */       entityIn != this.riddenByEntity) {
/* 742 */       if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer) && !(entityIn instanceof net.minecraft.entity.monster.EntityIronGolem) && getMinecartType() == EnumMinecartType.RIDEABLE && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D && this.riddenByEntity == null && entityIn.ridingEntity == null) {
/* 743 */         entityIn.mountEntity(this);
/*     */       }
/*     */       
/* 746 */       double d0 = entityIn.posX - this.posX;
/* 747 */       double d1 = entityIn.posZ - this.posZ;
/* 748 */       double d2 = d0 * d0 + d1 * d1;
/*     */       
/* 750 */       if (d2 >= 9.999999747378752E-5D) {
/* 751 */         d2 = MathHelper.sqrt_double(d2);
/* 752 */         d0 /= d2;
/* 753 */         d1 /= d2;
/* 754 */         double d3 = 1.0D / d2;
/*     */         
/* 756 */         if (d3 > 1.0D) {
/* 757 */           d3 = 1.0D;
/*     */         }
/*     */         
/* 760 */         d0 *= d3;
/* 761 */         d1 *= d3;
/* 762 */         d0 *= 0.10000000149011612D;
/* 763 */         d1 *= 0.10000000149011612D;
/* 764 */         d0 *= (1.0F - this.entityCollisionReduction);
/* 765 */         d1 *= (1.0F - this.entityCollisionReduction);
/* 766 */         d0 *= 0.5D;
/* 767 */         d1 *= 0.5D;
/*     */         
/* 769 */         if (entityIn instanceof EntityMinecart) {
/* 770 */           double d4 = entityIn.posX - this.posX;
/* 771 */           double d5 = entityIn.posZ - this.posZ;
/* 772 */           Vec3 vec3 = (new Vec3(d4, 0.0D, d5)).normalize();
/* 773 */           Vec3 vec31 = (new Vec3(MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F), 0.0D, MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F))).normalize();
/* 774 */           double d6 = Math.abs(vec3.dotProduct(vec31));
/*     */           
/* 776 */           if (d6 < 0.800000011920929D) {
/*     */             return;
/*     */           }
/*     */           
/* 780 */           double d7 = entityIn.motionX + this.motionX;
/* 781 */           double d8 = entityIn.motionZ + this.motionZ;
/*     */           
/* 783 */           if (((EntityMinecart)entityIn).getMinecartType() == EnumMinecartType.FURNACE && getMinecartType() != EnumMinecartType.FURNACE) {
/* 784 */             this.motionX *= 0.20000000298023224D;
/* 785 */             this.motionZ *= 0.20000000298023224D;
/* 786 */             addVelocity(entityIn.motionX - d0, 0.0D, entityIn.motionZ - d1);
/* 787 */             entityIn.motionX *= 0.949999988079071D;
/* 788 */             entityIn.motionZ *= 0.949999988079071D;
/* 789 */           } else if (((EntityMinecart)entityIn).getMinecartType() != EnumMinecartType.FURNACE && getMinecartType() == EnumMinecartType.FURNACE) {
/* 790 */             entityIn.motionX *= 0.20000000298023224D;
/* 791 */             entityIn.motionZ *= 0.20000000298023224D;
/* 792 */             entityIn.addVelocity(this.motionX + d0, 0.0D, this.motionZ + d1);
/* 793 */             this.motionX *= 0.949999988079071D;
/* 794 */             this.motionZ *= 0.949999988079071D;
/*     */           } else {
/* 796 */             d7 /= 2.0D;
/* 797 */             d8 /= 2.0D;
/* 798 */             this.motionX *= 0.20000000298023224D;
/* 799 */             this.motionZ *= 0.20000000298023224D;
/* 800 */             addVelocity(d7 - d0, 0.0D, d8 - d1);
/* 801 */             entityIn.motionX *= 0.20000000298023224D;
/* 802 */             entityIn.motionZ *= 0.20000000298023224D;
/* 803 */             entityIn.addVelocity(d7 + d0, 0.0D, d8 + d1);
/*     */           } 
/*     */         } else {
/* 806 */           addVelocity(-d0, 0.0D, -d1);
/* 807 */           entityIn.addVelocity(d0 / 4.0D, 0.0D, d1 / 4.0D);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 816 */     this.minecartX = x;
/* 817 */     this.minecartY = y;
/* 818 */     this.minecartZ = z;
/* 819 */     this.minecartYaw = yaw;
/* 820 */     this.minecartPitch = pitch;
/* 821 */     this.turnProgress = posRotationIncrements + 2;
/* 822 */     this.motionX = this.velocityX;
/* 823 */     this.motionY = this.velocityY;
/* 824 */     this.motionZ = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 831 */     this.velocityX = this.motionX = x;
/* 832 */     this.velocityY = this.motionY = y;
/* 833 */     this.velocityZ = this.motionZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamage(float p_70492_1_) {
/* 841 */     this.dataWatcher.updateObject(19, Float.valueOf(p_70492_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamage() {
/* 849 */     return this.dataWatcher.getWatchableObjectFloat(19);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRollingAmplitude(int p_70497_1_) {
/* 856 */     this.dataWatcher.updateObject(17, Integer.valueOf(p_70497_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRollingAmplitude() {
/* 863 */     return this.dataWatcher.getWatchableObjectInt(17);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRollingDirection(int p_70494_1_) {
/* 870 */     this.dataWatcher.updateObject(18, Integer.valueOf(p_70494_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRollingDirection() {
/* 877 */     return this.dataWatcher.getWatchableObjectInt(18);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getDisplayTile() {
/* 883 */     return !hasDisplayTile() ? getDefaultDisplayTile() : Block.getStateById(getDataWatcher().getWatchableObjectInt(20));
/*     */   }
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/* 887 */     return Blocks.air.getDefaultState();
/*     */   }
/*     */   
/*     */   public int getDisplayTileOffset() {
/* 891 */     return !hasDisplayTile() ? getDefaultDisplayTileOffset() : getDataWatcher().getWatchableObjectInt(21);
/*     */   }
/*     */   
/*     */   public int getDefaultDisplayTileOffset() {
/* 895 */     return 6;
/*     */   }
/*     */   
/*     */   public void func_174899_a(IBlockState p_174899_1_) {
/* 899 */     getDataWatcher().updateObject(20, Integer.valueOf(Block.getStateId(p_174899_1_)));
/* 900 */     setHasDisplayTile(true);
/*     */   }
/*     */   
/*     */   public void setDisplayTileOffset(int p_94086_1_) {
/* 904 */     getDataWatcher().updateObject(21, Integer.valueOf(p_94086_1_));
/* 905 */     setHasDisplayTile(true);
/*     */   }
/*     */   
/*     */   public boolean hasDisplayTile() {
/* 909 */     return (getDataWatcher().getWatchableObjectByte(22) == 1);
/*     */   }
/*     */   
/*     */   public void setHasDisplayTile(boolean p_94096_1_) {
/* 913 */     getDataWatcher().updateObject(22, Byte.valueOf((byte)(p_94096_1_ ? 1 : 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomNameTag(String name) {
/* 920 */     this.entityName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 927 */     return (this.entityName != null) ? this.entityName : super.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 934 */     return (this.entityName != null);
/*     */   }
/*     */   
/*     */   public String getCustomNameTag() {
/* 938 */     return this.entityName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 945 */     if (hasCustomName()) {
/* 946 */       ChatComponentText chatcomponenttext = new ChatComponentText(this.entityName);
/* 947 */       chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 948 */       chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/* 949 */       return (IChatComponent)chatcomponenttext;
/*     */     } 
/* 951 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(getName(), new Object[0]);
/* 952 */     chatcomponenttranslation.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 953 */     chatcomponenttranslation.getChatStyle().setInsertion(getUniqueID().toString());
/* 954 */     return (IChatComponent)chatcomponenttranslation;
/*     */   }
/*     */   
/*     */   public abstract EnumMinecartType getMinecartType();
/*     */   
/* 959 */   public enum EnumMinecartType { RIDEABLE(0, "MinecartRideable"),
/* 960 */     CHEST(1, "MinecartChest"),
/* 961 */     FURNACE(2, "MinecartFurnace"),
/* 962 */     TNT(3, "MinecartTNT"),
/* 963 */     SPAWNER(4, "MinecartSpawner"),
/* 964 */     HOPPER(5, "MinecartHopper"),
/* 965 */     COMMAND_BLOCK(6, "MinecartCommandBlock");
/*     */     
/* 967 */     private static final Map<Integer, EnumMinecartType> ID_LOOKUP = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int networkID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumMinecartType[] arrayOfEnumMinecartType;
/* 990 */       for (i = (arrayOfEnumMinecartType = values()).length, b = 0; b < i; ) { EnumMinecartType entityminecart$enumminecarttype = arrayOfEnumMinecartType[b];
/* 991 */         ID_LOOKUP.put(Integer.valueOf(entityminecart$enumminecarttype.getNetworkID()), entityminecart$enumminecarttype);
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumMinecartType(int networkID, String name) {
/*     */       this.networkID = networkID;
/*     */       this.name = name;
/*     */     }
/*     */     
/*     */     public int getNetworkID() {
/*     */       return this.networkID;
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumMinecartType byNetworkID(int id) {
/*     */       EnumMinecartType entityminecart$enumminecarttype = ID_LOOKUP.get(Integer.valueOf(id));
/*     */       return (entityminecart$enumminecarttype == null) ? RIDEABLE : entityminecart$enumminecarttype;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */