/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import client.Client;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityArrow extends Entity implements IProjectile {
/*  35 */   private int xTile = -1;
/*  36 */   private int yTile = -1;
/*  37 */   private int zTile = -1;
/*     */ 
/*     */   
/*     */   private Block inTile;
/*     */ 
/*     */   
/*     */   private int inData;
/*     */ 
/*     */   
/*     */   private boolean inGround;
/*     */   
/*     */   public int canBePickedUp;
/*     */   
/*     */   public int arrowShake;
/*     */   
/*     */   public Entity shootingEntity;
/*     */   
/*     */   private int ticksInGround;
/*     */   
/*     */   private int ticksInAir;
/*     */   
/*  58 */   private double damage = 2.0D;
/*     */ 
/*     */   
/*     */   private int knockbackStrength;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn) {
/*  66 */     super(worldIn);
/*  67 */     this.renderDistanceWeight = 10.0D;
/*  68 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityArrow(World worldIn, double x, double y, double z) {
/*  72 */     super(worldIn);
/*  73 */     this.renderDistanceWeight = 10.0D;
/*  74 */     setSize(0.5F, 0.5F);
/*  75 */     setPosition(x, y, z);
/*     */   }
/*     */   
/*     */   public EntityArrow(World worldIn, EntityLivingBase shooter, EntityLivingBase target, float velocity, float innacuracy) {
/*  79 */     super(worldIn);
/*  80 */     this.renderDistanceWeight = 10.0D;
/*  81 */     this.shootingEntity = (Entity)shooter;
/*     */     
/*  83 */     if (shooter instanceof EntityPlayer) {
/*  84 */       this.canBePickedUp = 1;
/*     */     }
/*     */     
/*  87 */     this.posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
/*  88 */     double d0 = target.posX - shooter.posX;
/*  89 */     double d1 = (target.getEntityBoundingBox()).minY + (target.height / 3.0F) - this.posY;
/*  90 */     double d2 = target.posZ - shooter.posZ;
/*  91 */     double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/*     */     
/*  93 */     if (d3 >= 1.0E-7D) {
/*  94 */       float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
/*  95 */       float f1 = (float)-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI);
/*  96 */       double d4 = d0 / d3;
/*  97 */       double d5 = d2 / d3;
/*  98 */       setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f, f1);
/*  99 */       float f2 = (float)(d3 * 0.20000000298023224D);
/* 100 */       setThrowableHeading(d0, d1 + f2, d2, velocity, innacuracy);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EntityArrow(World worldIn, EntityLivingBase shooter, float velocity) {
/* 105 */     super(worldIn);
/* 106 */     this.renderDistanceWeight = 10.0D;
/* 107 */     this.shootingEntity = (Entity)shooter;
/*     */     
/* 109 */     if (shooter instanceof EntityPlayer) {
/* 110 */       this.canBePickedUp = 1;
/*     */     }
/*     */     
/* 113 */     setSize(0.5F, 0.5F);
/* 114 */     setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
/* 115 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/* 116 */     this.posY -= 0.10000000149011612D;
/* 117 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/* 118 */     setPosition(this.posX, this.posY, this.posZ);
/* 119 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 120 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
/* 121 */     this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F);
/* 122 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 126 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 133 */     float f = MathHelper.sqrt_double(x * x + y * y + z * z);
/* 134 */     x /= f;
/* 135 */     y /= f;
/* 136 */     z /= f;
/* 137 */     x += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 138 */     y += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 139 */     z += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : true) * 0.007499999832361937D * inaccuracy;
/* 140 */     x *= velocity;
/* 141 */     y *= velocity;
/* 142 */     z *= velocity;
/* 143 */     this.motionX = x;
/* 144 */     this.motionY = y;
/* 145 */     this.motionZ = z;
/* 146 */     float f1 = MathHelper.sqrt_double(x * x + z * z);
/* 147 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 148 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 180.0D / Math.PI);
/* 149 */     this.ticksInGround = 0;
/*     */   }
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 153 */     setPosition(x, y, z);
/* 154 */     setRotation(yaw, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 161 */     this.motionX = x;
/* 162 */     this.motionY = y;
/* 163 */     this.motionZ = z;
/*     */     
/* 165 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/* 166 */       float f = MathHelper.sqrt_double(x * x + z * z);
/* 167 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 168 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f) * 180.0D / Math.PI);
/* 169 */       this.prevRotationPitch = this.rotationPitch;
/* 170 */       this.prevRotationYaw = this.rotationYaw;
/* 171 */       setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 172 */       this.ticksInGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 181 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/* 182 */       float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 183 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/* 184 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 180.0D / Math.PI);
/*     */     } 
/*     */     
/* 187 */     BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
/* 188 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 189 */     Block block = iblockstate.getBlock();
/*     */     
/* 191 */     if (block.getMaterial() != Material.air) {
/* 192 */       block.setBlockBoundsBasedOnState((IBlockAccess)this.worldObj, blockpos);
/* 193 */       AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);
/*     */       
/* 195 */       if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ))) {
/* 196 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 200 */     if (this.arrowShake > 0) {
/* 201 */       this.arrowShake--;
/*     */     }
/*     */     
/* 204 */     if (this.inGround) {
/* 205 */       int j = block.getMetaFromState(iblockstate);
/*     */       
/* 207 */       if (block == this.inTile && j == this.inData) {
/* 208 */         this.ticksInGround++;
/*     */         
/* 210 */         if (this.ticksInGround >= 1200) {
/* 211 */           setDead();
/*     */         }
/*     */       } else {
/* 214 */         this.inGround = false;
/* 215 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 216 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 217 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 218 */         this.ticksInGround = 0;
/* 219 */         this.ticksInAir = 0;
/*     */       } 
/*     */     } else {
/* 222 */       this.ticksInAir++;
/* 223 */       Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 224 */       Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 225 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
/* 226 */       vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 227 */       vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 229 */       if (movingobjectposition != null) {
/* 230 */         vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 233 */       Entity entity = null;
/* 234 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 235 */       double d0 = 0.0D;
/*     */       
/* 237 */       for (int i = 0; i < list.size(); i++) {
/* 238 */         Entity entity1 = list.get(i);
/*     */         
/* 240 */         if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
/* 241 */           float f1 = 0.3F;
/* 242 */           AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/* 243 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
/*     */           
/* 245 */           if (movingobjectposition1 != null) {
/* 246 */             double d1 = vec31.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 248 */             if (d1 < d0 || d0 == 0.0D) {
/* 249 */               entity = entity1;
/* 250 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 256 */       if (entity != null) {
/* 257 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 260 */       if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
/* 261 */         EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
/*     */         
/* 263 */         if (entityplayer.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))) {
/* 264 */           movingobjectposition = null;
/*     */         }
/*     */       } 
/*     */       
/* 268 */       if (movingobjectposition != null) {
/* 269 */         Entity targetEntity = movingobjectposition.entityHit;
/* 270 */         if (movingobjectposition.entityHit != null) {
/*     */           DamageSource damagesource;
/* 272 */           float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 273 */           int l = MathHelper.ceiling_double_int(f2 * this.damage);
/*     */           
/* 275 */           if (getIsCritical()) {
/* 276 */             l += this.rand.nextInt(l / 2 + 2);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 281 */           if (this.shootingEntity == null) {
/* 282 */             damagesource = DamageSource.causeArrowDamage(this, this);
/*     */           } else {
/* 284 */             damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
/*     */           } 
/*     */           
/* 287 */           if (isBurning() && !(movingobjectposition.entityHit instanceof net.minecraft.entity.monster.EntityEnderman)) {
/* 288 */             movingobjectposition.entityHit.setFire(5);
/*     */           }
/*     */           
/* 291 */           if (movingobjectposition.entityHit.attackEntityFrom(damagesource, l)) {
/*     */             
/* 293 */             if (movingobjectposition.entityHit instanceof EntityLivingBase) {
/* 294 */               EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
/*     */               
/* 296 */               if (!this.worldObj.isRemote) {
/* 297 */                 entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
/*     */               }
/*     */               
/* 300 */               if (this.knockbackStrength > 0) {
/* 301 */                 float f7 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */                 
/* 303 */                 if (f7 > 0.0F) {
/* 304 */                   movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f7, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f7);
/*     */                 }
/*     */               } 
/*     */               
/* 308 */               if (this.shootingEntity instanceof EntityLivingBase) {
/* 309 */                 EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
/* 310 */                 EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, (Entity)entitylivingbase);
/*     */               } 
/*     */               
/* 313 */               if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
/* 314 */                 ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(6, 0.0F));
/*     */               }
/*     */             } 
/*     */             
/* 318 */             playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 319 */             onHit(targetEntity);
/* 320 */             if (!(movingobjectposition.entityHit instanceof net.minecraft.entity.monster.EntityEnderman)) {
/* 321 */               setDead();
/*     */             }
/*     */           } else {
/* 324 */             this.motionX *= -0.10000000149011612D;
/* 325 */             this.motionY *= -0.10000000149011612D;
/* 326 */             this.motionZ *= -0.10000000149011612D;
/* 327 */             this.rotationYaw += 180.0F;
/* 328 */             this.prevRotationYaw += 180.0F;
/* 329 */             this.ticksInAir = 0;
/*     */           } 
/*     */         } else {
/* 332 */           BlockPos blockpos1 = movingobjectposition.getBlockPos();
/* 333 */           this.xTile = blockpos1.getX();
/* 334 */           this.yTile = blockpos1.getY();
/* 335 */           this.zTile = blockpos1.getZ();
/* 336 */           IBlockState iblockstate1 = this.worldObj.getBlockState(blockpos1);
/* 337 */           this.inTile = iblockstate1.getBlock();
/* 338 */           this.inData = this.inTile.getMetaFromState(iblockstate1);
/* 339 */           this.motionX = (float)(movingobjectposition.hitVec.xCoord - this.posX);
/* 340 */           this.motionY = (float)(movingobjectposition.hitVec.yCoord - this.posY);
/* 341 */           this.motionZ = (float)(movingobjectposition.hitVec.zCoord - this.posZ);
/* 342 */           float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 343 */           this.posX -= this.motionX / f5 * 0.05000000074505806D;
/* 344 */           this.posY -= this.motionY / f5 * 0.05000000074505806D;
/* 345 */           this.posZ -= this.motionZ / f5 * 0.05000000074505806D;
/* 346 */           playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 347 */           onHit(targetEntity);
/* 348 */           this.inGround = true;
/* 349 */           this.arrowShake = 7;
/* 350 */           setIsCritical(false);
/*     */           
/* 352 */           if (this.inTile.getMaterial() != Material.air) {
/* 353 */             this.inTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate1, this);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 358 */       if (getIsCritical()) {
/* 359 */         for (int k = 0; k < 4; k++) {
/* 360 */           this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * k / 4.0D, this.posY + this.motionY * k / 4.0D, this.posZ + this.motionZ * k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
/*     */         }
/*     */       }
/*     */       
/* 364 */       this.posX += this.motionX;
/* 365 */       this.posY += this.motionY;
/* 366 */       this.posZ += this.motionZ;
/* 367 */       float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 368 */       this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */       
/* 370 */       for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f3) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */       
/* 374 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 375 */         this.prevRotationPitch += 360.0F;
/*     */       }
/*     */       
/* 378 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 379 */         this.prevRotationYaw -= 360.0F;
/*     */       }
/*     */       
/* 382 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 383 */         this.prevRotationYaw += 360.0F;
/*     */       }
/*     */       
/* 386 */       this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 387 */       this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 388 */       float f4 = 0.99F;
/* 389 */       float f6 = 0.05F;
/*     */       
/* 391 */       if (isInWater()) {
/* 392 */         for (int i1 = 0; i1 < 4; i1++) {
/* 393 */           float f8 = 0.25F;
/* 394 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f8, this.posY - this.motionY * f8, this.posZ - this.motionZ * f8, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */         
/* 397 */         f4 = 0.6F;
/*     */       } 
/*     */       
/* 400 */       if (isWet()) {
/* 401 */         extinguish();
/*     */       }
/*     */       
/* 404 */       this.motionX *= f4;
/* 405 */       this.motionY *= f4;
/* 406 */       this.motionZ *= f4;
/* 407 */       this.motionY -= f6;
/* 408 */       setPosition(this.posX, this.posY, this.posZ);
/* 409 */       doBlockCollisions();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 417 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 418 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 419 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 420 */     tagCompound.setShort("life", (short)this.ticksInGround);
/* 421 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 422 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 423 */     tagCompound.setByte("inData", (byte)this.inData);
/* 424 */     tagCompound.setByte("shake", (byte)this.arrowShake);
/* 425 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 426 */     tagCompound.setByte("pickup", (byte)this.canBePickedUp);
/* 427 */     tagCompound.setDouble("damage", this.damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 434 */     this.xTile = tagCompund.getShort("xTile");
/* 435 */     this.yTile = tagCompund.getShort("yTile");
/* 436 */     this.zTile = tagCompund.getShort("zTile");
/* 437 */     this.ticksInGround = tagCompund.getShort("life");
/*     */     
/* 439 */     if (tagCompund.hasKey("inTile", 8)) {
/* 440 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     } else {
/* 442 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 445 */     this.inData = tagCompund.getByte("inData") & 0xFF;
/* 446 */     this.arrowShake = tagCompund.getByte("shake") & 0xFF;
/* 447 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/*     */     
/* 449 */     if (tagCompund.hasKey("damage", 99)) {
/* 450 */       this.damage = tagCompund.getDouble("damage");
/*     */     }
/*     */     
/* 453 */     if (tagCompund.hasKey("pickup", 99)) {
/* 454 */       this.canBePickedUp = tagCompund.getByte("pickup");
/* 455 */     } else if (tagCompund.hasKey("player", 99)) {
/* 456 */       this.canBePickedUp = tagCompund.getBoolean("player") ? 1 : 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 464 */     if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
/* 465 */       boolean flag = !(this.canBePickedUp != 1 && (this.canBePickedUp != 2 || !entityIn.capabilities.isCreativeMode));
/*     */       
/* 467 */       if (this.canBePickedUp == 1 && !entityIn.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {
/* 468 */         flag = false;
/*     */       }
/*     */       
/* 471 */       if (flag) {
/* 472 */         playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 473 */         entityIn.onItemPickup(this, 1);
/* 474 */         setDead();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onHit(Entity targetEntity) {
/*     */     try {
/* 481 */       if ((Client.getInstance()).hudManager.bowdistance.isEnabled() && 
/* 482 */         this.shootingEntity instanceof EntityPlayerSP && targetEntity instanceof EntityPlayer && !(targetEntity instanceof EntityPlayerSP)) {
/* 483 */         EntityPlayerSP player = (Minecraft.getMinecraft()).thePlayer;
/* 484 */         int distance = (int)player.getDistanceToEntity(targetEntity);
/* 485 */         if (distance >= 8) {
/* 486 */           String text = String.valueOf((Client.getInstance()).colorbase) + "e" + "Shot " + (Client.getInstance()).colorbase + "f" + targetEntity.getName() + (Client.getInstance()).colorbase + "e" + " From " + distance + " Blocks Away!";
/* 487 */           player.addChatMessage((IChatComponent)new ChatComponentText(text));
/*     */         } else {
/* 489 */           System.out.println(distance);
/*     */         }
/*     */       
/*     */       } 
/* 493 */     } catch (Exception e) {
/* 494 */       System.out.println("something's null");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 503 */     return false;
/*     */   }
/*     */   
/*     */   public void setDamage(double damageIn) {
/* 507 */     this.damage = damageIn;
/*     */   }
/*     */   
/*     */   public double getDamage() {
/* 511 */     return this.damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKnockbackStrength(int knockbackStrengthIn) {
/* 518 */     this.knockbackStrength = knockbackStrengthIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackWithItem() {
/* 525 */     return false;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 529 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsCritical(boolean critical) {
/* 536 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 538 */     if (critical) {
/* 539 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/* 541 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsCritical() {
/* 549 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/* 550 */     return ((b0 & 0x1) != 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */