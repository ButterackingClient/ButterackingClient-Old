/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityBoat
/*     */   extends Entity
/*     */ {
/*     */   private boolean isBoatEmpty;
/*     */   private double speedMultiplier;
/*     */   private int boatPosRotationIncrements;
/*     */   private double boatX;
/*     */   private double boatY;
/*     */   private double boatZ;
/*     */   private double boatYaw;
/*     */   private double boatPitch;
/*     */   private double velocityX;
/*     */   private double velocityY;
/*     */   private double velocityZ;
/*     */   
/*     */   public EntityBoat(World worldIn) {
/*  39 */     super(worldIn);
/*  40 */     this.isBoatEmpty = true;
/*  41 */     this.speedMultiplier = 0.07D;
/*  42 */     this.preventEntitySpawning = true;
/*  43 */     setSize(1.5F, 0.6F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  55 */     this.dataWatcher.addObject(17, new Integer(0));
/*  56 */     this.dataWatcher.addObject(18, new Integer(1));
/*  57 */     this.dataWatcher.addObject(19, new Float(0.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/*  65 */     return entityIn.getEntityBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox() {
/*  72 */     return getEntityBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/*  79 */     return true;
/*     */   }
/*     */   
/*     */   public EntityBoat(World worldIn, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
/*  83 */     this(worldIn);
/*  84 */     setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
/*  85 */     this.motionX = 0.0D;
/*  86 */     this.motionY = 0.0D;
/*  87 */     this.motionZ = 0.0D;
/*  88 */     this.prevPosX = p_i1705_2_;
/*  89 */     this.prevPosY = p_i1705_4_;
/*  90 */     this.prevPosZ = p_i1705_6_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/*  97 */     return -0.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 104 */     if (isEntityInvulnerable(source))
/* 105 */       return false; 
/* 106 */     if (!this.worldObj.isRemote && !this.isDead) {
/* 107 */       if (this.riddenByEntity != null && this.riddenByEntity == source.getEntity() && source instanceof net.minecraft.util.EntityDamageSourceIndirect) {
/* 108 */         return false;
/*     */       }
/* 110 */       setForwardDirection(-getForwardDirection());
/* 111 */       setTimeSinceHit(10);
/* 112 */       setDamageTaken(getDamageTaken() + amount * 10.0F);
/* 113 */       setBeenAttacked();
/* 114 */       boolean flag = (source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode);
/*     */       
/* 116 */       if (flag || getDamageTaken() > 40.0F) {
/* 117 */         if (this.riddenByEntity != null) {
/* 118 */           this.riddenByEntity.mountEntity(this);
/*     */         }
/*     */         
/* 121 */         if (!flag && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 122 */           dropItemWithOffset(Items.boat, 1, 0.0F);
/*     */         }
/*     */         
/* 125 */         setDead();
/*     */       } 
/*     */       
/* 128 */       return true;
/*     */     } 
/*     */     
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performHurtAnimation() {
/* 139 */     setForwardDirection(-getForwardDirection());
/* 140 */     setTimeSinceHit(10);
/* 141 */     setDamageTaken(getDamageTaken() * 11.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 148 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 153 */     this.prevPosX = this.posX = x;
/* 154 */     this.prevPosY = this.posY = y;
/* 155 */     this.prevPosZ = this.posZ = z;
/* 156 */     this.rotationYaw = yaw;
/* 157 */     this.rotationPitch = pitch;
/* 158 */     this.boatPosRotationIncrements = 0;
/* 159 */     setPosition(x, y, z);
/* 160 */     this.motionX = this.velocityX = 0.0D;
/* 161 */     this.motionY = this.velocityY = 0.0D;
/* 162 */     this.motionZ = this.velocityZ = 0.0D;
/*     */     
/* 164 */     if (this.isBoatEmpty) {
/* 165 */       this.boatPosRotationIncrements = posRotationIncrements + 5;
/*     */     } else {
/* 167 */       double d0 = x - this.posX;
/* 168 */       double d1 = y - this.posY;
/* 169 */       double d2 = z - this.posZ;
/* 170 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */       
/* 172 */       if (d3 <= 1.0D) {
/*     */         return;
/*     */       }
/*     */       
/* 176 */       this.boatPosRotationIncrements = 3;
/*     */     } 
/*     */     
/* 179 */     this.boatX = x;
/* 180 */     this.boatY = y;
/* 181 */     this.boatZ = z;
/* 182 */     this.boatYaw = yaw;
/* 183 */     this.boatPitch = pitch;
/* 184 */     this.motionX = this.velocityX;
/* 185 */     this.motionY = this.velocityY;
/* 186 */     this.motionZ = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 194 */     this.velocityX = this.motionX = x;
/* 195 */     this.velocityY = this.motionY = y;
/* 196 */     this.velocityZ = this.motionZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 203 */     super.onUpdate();
/*     */     
/* 205 */     if (getTimeSinceHit() > 0) {
/* 206 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 209 */     if (getDamageTaken() > 0.0F) {
/* 210 */       setDamageTaken(getDamageTaken() - 1.0F);
/*     */     }
/*     */     
/* 213 */     this.prevPosX = this.posX;
/* 214 */     this.prevPosY = this.posY;
/* 215 */     this.prevPosZ = this.posZ;
/* 216 */     int i = 5;
/* 217 */     double d0 = 0.0D;
/*     */     
/* 219 */     for (int j = 0; j < i; j++) {
/* 220 */       double d1 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * (j + 0) / i - 0.125D;
/* 221 */       double d3 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * (j + 1) / i - 0.125D;
/* 222 */       AxisAlignedBB axisalignedbb = new AxisAlignedBB((getEntityBoundingBox()).minX, d1, (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).maxX, d3, (getEntityBoundingBox()).maxZ);
/*     */       
/* 224 */       if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
/* 225 */         d0 += 1.0D / i;
/*     */       }
/*     */     } 
/*     */     
/* 229 */     double d9 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */     
/* 231 */     if (d9 > 0.2975D) {
/* 232 */       double d2 = Math.cos(this.rotationYaw * Math.PI / 180.0D);
/* 233 */       double d4 = Math.sin(this.rotationYaw * Math.PI / 180.0D);
/*     */       
/* 235 */       for (int k = 0; k < 1.0D + d9 * 60.0D; k++) {
/* 236 */         double d5 = (this.rand.nextFloat() * 2.0F - 1.0F);
/* 237 */         double d6 = (this.rand.nextInt(2) * 2 - 1) * 0.7D;
/*     */         
/* 239 */         if (this.rand.nextBoolean()) {
/* 240 */           double d7 = this.posX - d2 * d5 * 0.8D + d4 * d6;
/* 241 */           double d8 = this.posZ - d4 * d5 * 0.8D - d2 * d6;
/* 242 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, d7, this.posY - 0.125D, d8, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } else {
/* 244 */           double d24 = this.posX + d2 + d4 * d5 * 0.7D;
/* 245 */           double d25 = this.posZ + d4 - d2 * d5 * 0.7D;
/* 246 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, d24, this.posY - 0.125D, d25, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     if (this.worldObj.isRemote && this.isBoatEmpty) {
/* 252 */       if (this.boatPosRotationIncrements > 0) {
/* 253 */         double d12 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
/* 254 */         double d16 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
/* 255 */         double d19 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
/* 256 */         double d22 = MathHelper.wrapAngleTo180_double(this.boatYaw - this.rotationYaw);
/* 257 */         this.rotationYaw = (float)(this.rotationYaw + d22 / this.boatPosRotationIncrements);
/* 258 */         this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
/* 259 */         this.boatPosRotationIncrements--;
/* 260 */         setPosition(d12, d16, d19);
/* 261 */         setRotation(this.rotationYaw, this.rotationPitch);
/*     */       } else {
/* 263 */         double d13 = this.posX + this.motionX;
/* 264 */         double d17 = this.posY + this.motionY;
/* 265 */         double d20 = this.posZ + this.motionZ;
/* 266 */         setPosition(d13, d17, d20);
/*     */         
/* 268 */         if (this.onGround) {
/* 269 */           this.motionX *= 0.5D;
/* 270 */           this.motionY *= 0.5D;
/* 271 */           this.motionZ *= 0.5D;
/*     */         } 
/*     */         
/* 274 */         this.motionX *= 0.9900000095367432D;
/* 275 */         this.motionY *= 0.949999988079071D;
/* 276 */         this.motionZ *= 0.9900000095367432D;
/*     */       } 
/*     */     } else {
/* 279 */       if (d0 < 1.0D) {
/* 280 */         double d10 = d0 * 2.0D - 1.0D;
/* 281 */         this.motionY += 0.03999999910593033D * d10;
/*     */       } else {
/* 283 */         if (this.motionY < 0.0D) {
/* 284 */           this.motionY /= 2.0D;
/*     */         }
/*     */         
/* 287 */         this.motionY += 0.007000000216066837D;
/*     */       } 
/*     */       
/* 290 */       if (this.riddenByEntity instanceof EntityLivingBase) {
/* 291 */         EntityLivingBase entitylivingbase = (EntityLivingBase)this.riddenByEntity;
/* 292 */         float f = this.riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
/* 293 */         this.motionX += -Math.sin((f * 3.1415927F / 180.0F)) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
/* 294 */         this.motionZ += Math.cos((f * 3.1415927F / 180.0F)) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
/*     */       } 
/*     */       
/* 297 */       double d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */       
/* 299 */       if (d11 > 0.35D) {
/* 300 */         double d14 = 0.35D / d11;
/* 301 */         this.motionX *= d14;
/* 302 */         this.motionZ *= d14;
/* 303 */         d11 = 0.35D;
/*     */       } 
/*     */       
/* 306 */       if (d11 > d9 && this.speedMultiplier < 0.35D) {
/* 307 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/*     */         
/* 309 */         if (this.speedMultiplier > 0.35D) {
/* 310 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       } else {
/* 313 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/*     */         
/* 315 */         if (this.speedMultiplier < 0.07D) {
/* 316 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */       
/* 320 */       for (int i1 = 0; i1 < 4; i1++) {
/* 321 */         int l1 = MathHelper.floor_double(this.posX + ((i1 % 2) - 0.5D) * 0.8D);
/* 322 */         int i2 = MathHelper.floor_double(this.posZ + ((i1 / 2) - 0.5D) * 0.8D);
/*     */         
/* 324 */         for (int j2 = 0; j2 < 2; j2++) {
/* 325 */           int l = MathHelper.floor_double(this.posY) + j2;
/* 326 */           BlockPos blockpos = new BlockPos(l1, l, i2);
/* 327 */           Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */           
/* 329 */           if (block == Blocks.snow_layer) {
/* 330 */             this.worldObj.setBlockToAir(blockpos);
/* 331 */             this.isCollidedHorizontally = false;
/* 332 */           } else if (block == Blocks.waterlily) {
/* 333 */             this.worldObj.destroyBlock(blockpos, true);
/* 334 */             this.isCollidedHorizontally = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 339 */       if (this.onGround) {
/* 340 */         this.motionX *= 0.5D;
/* 341 */         this.motionY *= 0.5D;
/* 342 */         this.motionZ *= 0.5D;
/*     */       } 
/*     */       
/* 345 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */       
/* 347 */       if (this.isCollidedHorizontally && d9 > 0.2975D) {
/* 348 */         if (!this.worldObj.isRemote && !this.isDead) {
/* 349 */           setDead();
/*     */           
/* 351 */           if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 352 */             for (int j1 = 0; j1 < 3; j1++) {
/* 353 */               dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
/*     */             }
/*     */             
/* 356 */             for (int k1 = 0; k1 < 2; k1++) {
/* 357 */               dropItemWithOffset(Items.stick, 1, 0.0F);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 362 */         this.motionX *= 0.9900000095367432D;
/* 363 */         this.motionY *= 0.949999988079071D;
/* 364 */         this.motionZ *= 0.9900000095367432D;
/*     */       } 
/*     */       
/* 367 */       this.rotationPitch = 0.0F;
/* 368 */       double d15 = this.rotationYaw;
/* 369 */       double d18 = this.prevPosX - this.posX;
/* 370 */       double d21 = this.prevPosZ - this.posZ;
/*     */       
/* 372 */       if (d18 * d18 + d21 * d21 > 0.001D) {
/* 373 */         d15 = (float)(MathHelper.atan2(d21, d18) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 376 */       double d23 = MathHelper.wrapAngleTo180_double(d15 - this.rotationYaw);
/*     */       
/* 378 */       if (d23 > 20.0D) {
/* 379 */         d23 = 20.0D;
/*     */       }
/*     */       
/* 382 */       if (d23 < -20.0D) {
/* 383 */         d23 = -20.0D;
/*     */       }
/*     */       
/* 386 */       this.rotationYaw = (float)(this.rotationYaw + d23);
/* 387 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */       
/* 389 */       if (!this.worldObj.isRemote) {
/* 390 */         List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */         
/* 392 */         if (list != null && !list.isEmpty()) {
/* 393 */           for (int k2 = 0; k2 < list.size(); k2++) {
/* 394 */             Entity entity = list.get(k2);
/*     */             
/* 396 */             if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
/* 397 */               entity.applyEntityCollision(this);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 402 */         if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
/* 403 */           this.riddenByEntity = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateRiderPosition() {
/* 410 */     if (this.riddenByEntity != null) {
/* 411 */       double d0 = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
/* 412 */       double d1 = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.4D;
/* 413 */       this.riddenByEntity.setPosition(this.posX + d0, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 433 */     if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != playerIn) {
/* 434 */       return true;
/*     */     }
/* 436 */     if (!this.worldObj.isRemote) {
/* 437 */       playerIn.mountEntity(this);
/*     */     }
/*     */     
/* 440 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/* 445 */     if (onGroundIn) {
/* 446 */       if (this.fallDistance > 3.0F) {
/* 447 */         fall(this.fallDistance, 1.0F);
/*     */         
/* 449 */         if (!this.worldObj.isRemote && !this.isDead) {
/* 450 */           setDead();
/*     */           
/* 452 */           if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 453 */             for (int i = 0; i < 3; i++) {
/* 454 */               dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
/*     */             }
/*     */             
/* 457 */             for (int j = 0; j < 2; j++) {
/* 458 */               dropItemWithOffset(Items.stick, 1, 0.0F);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 463 */         this.fallDistance = 0.0F;
/*     */       } 
/* 465 */     } else if (this.worldObj.getBlockState((new BlockPos(this)).down()).getBlock().getMaterial() != Material.water && y < 0.0D) {
/* 466 */       this.fallDistance = (float)(this.fallDistance - y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamageTaken(float p_70266_1_) {
/* 474 */     this.dataWatcher.updateObject(19, Float.valueOf(p_70266_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamageTaken() {
/* 481 */     return this.dataWatcher.getWatchableObjectFloat(19);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeSinceHit(int p_70265_1_) {
/* 488 */     this.dataWatcher.updateObject(17, Integer.valueOf(p_70265_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimeSinceHit() {
/* 495 */     return this.dataWatcher.getWatchableObjectInt(17);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForwardDirection(int p_70269_1_) {
/* 502 */     this.dataWatcher.updateObject(18, Integer.valueOf(p_70269_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getForwardDirection() {
/* 509 */     return this.dataWatcher.getWatchableObjectInt(18);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsBoatEmpty(boolean p_70270_1_) {
/* 516 */     this.isBoatEmpty = p_70270_1_;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */