/*     */ package net.minecraft.entity.projectile;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.WeightedRandomFishable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class EntityFishHook extends Entity {
/*  34 */   private static final List<WeightedRandomFishable> JUNK = Arrays.asList(new WeightedRandomFishable[] { (new WeightedRandomFishable(new ItemStack((Item)Items.leather_boots), 10)).setMaxDamagePercent(0.9F), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack((Item)Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), (new WeightedRandomFishable(new ItemStack((Item)Items.fishing_rod), 2)).setMaxDamagePercent(0.9F), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, EnumDyeColor.BLACK.getDyeDamage()), 1), new WeightedRandomFishable(new ItemStack((Block)Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10) });
/*  35 */   private static final List<WeightedRandomFishable> TREASURE = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), (new WeightedRandomFishable(new ItemStack((Item)Items.bow), 1)).setMaxDamagePercent(0.25F).setEnchantable(), (new WeightedRandomFishable(new ItemStack((Item)Items.fishing_rod), 1)).setMaxDamagePercent(0.25F).setEnchantable(), (new WeightedRandomFishable(new ItemStack(Items.book), 1)).setEnchantable() });
/*  36 */   private static final List<WeightedRandomFishable> FISH = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getMetadata()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()), 13) });
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private Block inTile;
/*     */   private boolean inGround;
/*     */   public int shake;
/*     */   public EntityPlayer angler;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   private int ticksCatchable;
/*     */   private int ticksCaughtDelay;
/*     */   private int ticksCatchableDelay;
/*     */   private float fishApproachAngle;
/*     */   public Entity caughtEntity;
/*     */   private int fishPosRotationIncrements;
/*     */   private double fishX;
/*     */   private double fishY;
/*     */   private double fishZ;
/*     */   private double fishYaw;
/*     */   private double fishPitch;
/*     */   private double clientMotionX;
/*     */   private double clientMotionY;
/*     */   private double clientMotionZ;
/*     */   
/*     */   public static List<WeightedRandomFishable> func_174855_j() {
/*  62 */     return FISH;
/*     */   }
/*     */   
/*     */   public EntityFishHook(World worldIn) {
/*  66 */     super(worldIn);
/*  67 */     this.xTile = -1;
/*  68 */     this.yTile = -1;
/*  69 */     this.zTile = -1;
/*  70 */     setSize(0.25F, 0.25F);
/*  71 */     this.ignoreFrustumCheck = true;
/*     */   }
/*     */   
/*     */   public EntityFishHook(World worldIn, double x, double y, double z, EntityPlayer anglerIn) {
/*  75 */     this(worldIn);
/*  76 */     setPosition(x, y, z);
/*  77 */     this.ignoreFrustumCheck = true;
/*  78 */     this.angler = anglerIn;
/*  79 */     anglerIn.fishEntity = this;
/*     */   }
/*     */   
/*     */   public EntityFishHook(World worldIn, EntityPlayer fishingPlayer) {
/*  83 */     super(worldIn);
/*  84 */     this.xTile = -1;
/*  85 */     this.yTile = -1;
/*  86 */     this.zTile = -1;
/*  87 */     this.ignoreFrustumCheck = true;
/*  88 */     this.angler = fishingPlayer;
/*  89 */     this.angler.fishEntity = this;
/*  90 */     setSize(0.25F, 0.25F);
/*  91 */     setLocationAndAngles(fishingPlayer.posX, fishingPlayer.posY + fishingPlayer.getEyeHeight(), fishingPlayer.posZ, fishingPlayer.rotationYaw, fishingPlayer.rotationPitch);
/*  92 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  93 */     this.posY -= 0.10000000149011612D;
/*  94 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  95 */     setPosition(this.posX, this.posY, this.posZ);
/*  96 */     float f = 0.4F;
/*  97 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  98 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  99 */     this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F) * f);
/* 100 */     handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 111 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/* 113 */     if (Double.isNaN(d0)) {
/* 114 */       d0 = 4.0D;
/*     */     }
/*     */     
/* 117 */     d0 *= 64.0D;
/* 118 */     return (distance < d0 * d0);
/*     */   }
/*     */   
/*     */   public void handleHookCasting(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_) {
/* 122 */     float f = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
/* 123 */     p_146035_1_ /= f;
/* 124 */     p_146035_3_ /= f;
/* 125 */     p_146035_5_ /= f;
/* 126 */     p_146035_1_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 127 */     p_146035_3_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 128 */     p_146035_5_ += this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
/* 129 */     p_146035_1_ *= p_146035_7_;
/* 130 */     p_146035_3_ *= p_146035_7_;
/* 131 */     p_146035_5_ *= p_146035_7_;
/* 132 */     this.motionX = p_146035_1_;
/* 133 */     this.motionY = p_146035_3_;
/* 134 */     this.motionZ = p_146035_5_;
/* 135 */     float f1 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
/* 136 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(p_146035_1_, p_146035_5_) * 180.0D / Math.PI);
/* 137 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(p_146035_3_, f1) * 180.0D / Math.PI);
/* 138 */     this.ticksInGround = 0;
/*     */   }
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 142 */     this.fishX = x;
/* 143 */     this.fishY = y;
/* 144 */     this.fishZ = z;
/* 145 */     this.fishYaw = yaw;
/* 146 */     this.fishPitch = pitch;
/* 147 */     this.fishPosRotationIncrements = posRotationIncrements;
/* 148 */     this.motionX = this.clientMotionX;
/* 149 */     this.motionY = this.clientMotionY;
/* 150 */     this.motionZ = this.clientMotionZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 157 */     this.clientMotionX = this.motionX = x;
/* 158 */     this.clientMotionY = this.motionY = y;
/* 159 */     this.clientMotionZ = this.motionZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 166 */     super.onUpdate();
/*     */     
/* 168 */     if (this.fishPosRotationIncrements > 0) {
/* 169 */       double d7 = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
/* 170 */       double d8 = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
/* 171 */       double d9 = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
/* 172 */       double d1 = MathHelper.wrapAngleTo180_double(this.fishYaw - this.rotationYaw);
/* 173 */       this.rotationYaw = (float)(this.rotationYaw + d1 / this.fishPosRotationIncrements);
/* 174 */       this.rotationPitch = (float)(this.rotationPitch + (this.fishPitch - this.rotationPitch) / this.fishPosRotationIncrements);
/* 175 */       this.fishPosRotationIncrements--;
/* 176 */       setPosition(d7, d8, d9);
/* 177 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */     } else {
/* 179 */       if (!this.worldObj.isRemote) {
/* 180 */         ItemStack itemstack = this.angler.getCurrentEquippedItem();
/*     */         
/* 182 */         if (this.angler.isDead || !this.angler.isEntityAlive() || itemstack == null || itemstack.getItem() != Items.fishing_rod || getDistanceSqToEntity((Entity)this.angler) > 1024.0D) {
/* 183 */           setDead();
/* 184 */           this.angler.fishEntity = null;
/*     */           
/*     */           return;
/*     */         } 
/* 188 */         if (this.caughtEntity != null) {
/* 189 */           if (!this.caughtEntity.isDead) {
/* 190 */             this.posX = this.caughtEntity.posX;
/* 191 */             double d17 = this.caughtEntity.height;
/* 192 */             this.posY = (this.caughtEntity.getEntityBoundingBox()).minY + d17 * 0.8D;
/* 193 */             this.posZ = this.caughtEntity.posZ;
/*     */             
/*     */             return;
/*     */           } 
/* 197 */           this.caughtEntity = null;
/*     */         } 
/*     */       } 
/*     */       
/* 201 */       if (this.shake > 0) {
/* 202 */         this.shake--;
/*     */       }
/*     */       
/* 205 */       if (this.inGround) {
/* 206 */         if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/* 207 */           this.ticksInGround++;
/*     */           
/* 209 */           if (this.ticksInGround == 1200) {
/* 210 */             setDead();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 216 */         this.inGround = false;
/* 217 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 218 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 219 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 220 */         this.ticksInGround = 0;
/* 221 */         this.ticksInAir = 0;
/*     */       } else {
/* 223 */         this.ticksInAir++;
/*     */       } 
/*     */       
/* 226 */       Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 227 */       Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 228 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3);
/* 229 */       vec31 = new Vec3(this.posX, this.posY, this.posZ);
/* 230 */       vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 232 */       if (movingobjectposition != null) {
/* 233 */         vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 236 */       Entity entity = null;
/* 237 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 238 */       double d0 = 0.0D;
/*     */       
/* 240 */       for (int i = 0; i < list.size(); i++) {
/* 241 */         Entity entity1 = list.get(i);
/*     */         
/* 243 */         if (entity1.canBeCollidedWith() && (entity1 != this.angler || this.ticksInAir >= 5)) {
/* 244 */           float f = 0.3F;
/* 245 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
/* 246 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec31, vec3);
/*     */           
/* 248 */           if (movingobjectposition1 != null) {
/* 249 */             double d2 = vec31.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 251 */             if (d2 < d0 || d0 == 0.0D) {
/* 252 */               entity = entity1;
/* 253 */               d0 = d2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 259 */       if (entity != null) {
/* 260 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 263 */       if (movingobjectposition != null) {
/* 264 */         if (movingobjectposition.entityHit != null) {
/* 265 */           if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, (Entity)this.angler), 0.0F)) {
/* 266 */             this.caughtEntity = movingobjectposition.entityHit;
/*     */           }
/*     */         } else {
/* 269 */           this.inGround = true;
/*     */         } 
/*     */       }
/*     */       
/* 273 */       if (!this.inGround) {
/* 274 */         moveEntity(this.motionX, this.motionY, this.motionZ);
/* 275 */         float f5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 276 */         this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */         
/* 278 */         for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f5) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */         
/* 282 */         while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 283 */           this.prevRotationPitch += 360.0F;
/*     */         }
/*     */         
/* 286 */         while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 287 */           this.prevRotationYaw -= 360.0F;
/*     */         }
/*     */         
/* 290 */         while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 291 */           this.prevRotationYaw += 360.0F;
/*     */         }
/*     */         
/* 294 */         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 295 */         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 296 */         float f6 = 0.92F;
/*     */         
/* 298 */         if (this.onGround || this.isCollidedHorizontally) {
/* 299 */           f6 = 0.5F;
/*     */         }
/*     */         
/* 302 */         int j = 5;
/* 303 */         double d10 = 0.0D;
/*     */         
/* 305 */         for (int k = 0; k < j; k++) {
/* 306 */           AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
/* 307 */           double d3 = axisalignedbb1.maxY - axisalignedbb1.minY;
/* 308 */           double d4 = axisalignedbb1.minY + d3 * k / j;
/* 309 */           double d5 = axisalignedbb1.minY + d3 * (k + 1) / j;
/* 310 */           AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb1.minX, d4, axisalignedbb1.minZ, axisalignedbb1.maxX, d5, axisalignedbb1.maxZ);
/*     */           
/* 312 */           if (this.worldObj.isAABBInMaterial(axisalignedbb2, Material.water)) {
/* 313 */             d10 += 1.0D / j;
/*     */           }
/*     */         } 
/*     */         
/* 317 */         if (!this.worldObj.isRemote && d10 > 0.0D) {
/* 318 */           WorldServer worldserver = (WorldServer)this.worldObj;
/* 319 */           int l = 1;
/* 320 */           BlockPos blockpos = (new BlockPos(this)).up();
/*     */           
/* 322 */           if (this.rand.nextFloat() < 0.25F && this.worldObj.isRainingAt(blockpos)) {
/* 323 */             l = 2;
/*     */           }
/*     */           
/* 326 */           if (this.rand.nextFloat() < 0.5F && !this.worldObj.canSeeSky(blockpos)) {
/* 327 */             l--;
/*     */           }
/*     */           
/* 330 */           if (this.ticksCatchable > 0) {
/* 331 */             this.ticksCatchable--;
/*     */             
/* 333 */             if (this.ticksCatchable <= 0) {
/* 334 */               this.ticksCaughtDelay = 0;
/* 335 */               this.ticksCatchableDelay = 0;
/*     */             } 
/* 337 */           } else if (this.ticksCatchableDelay > 0) {
/* 338 */             this.ticksCatchableDelay -= l;
/*     */             
/* 340 */             if (this.ticksCatchableDelay <= 0) {
/* 341 */               this.motionY -= 0.20000000298023224D;
/* 342 */               playSound("random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 343 */               float f8 = MathHelper.floor_double((getEntityBoundingBox()).minY);
/* 344 */               worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX, (f8 + 1.0F), this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 345 */               worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, this.posX, (f8 + 1.0F), this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 346 */               this.ticksCatchable = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
/*     */             } else {
/* 348 */               this.fishApproachAngle = (float)(this.fishApproachAngle + this.rand.nextGaussian() * 4.0D);
/* 349 */               float f7 = this.fishApproachAngle * 0.017453292F;
/* 350 */               float f10 = MathHelper.sin(f7);
/* 351 */               float f11 = MathHelper.cos(f7);
/* 352 */               double d13 = this.posX + (f10 * this.ticksCatchableDelay * 0.1F);
/* 353 */               double d15 = (MathHelper.floor_double((getEntityBoundingBox()).minY) + 1.0F);
/* 354 */               double d16 = this.posZ + (f11 * this.ticksCatchableDelay * 0.1F);
/* 355 */               Block block1 = worldserver.getBlockState(new BlockPos((int)d13, (int)d15 - 1, (int)d16)).getBlock();
/*     */               
/* 357 */               if (block1 == Blocks.water || block1 == Blocks.flowing_water) {
/* 358 */                 if (this.rand.nextFloat() < 0.15F) {
/* 359 */                   worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d13, d15 - 0.10000000149011612D, d16, 1, f10, 0.1D, f11, 0.0D, new int[0]);
/*     */                 }
/*     */                 
/* 362 */                 float f3 = f10 * 0.04F;
/* 363 */                 float f4 = f11 * 0.04F;
/* 364 */                 worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d13, d15, d16, 0, f4, 0.01D, -f3, 1.0D, new int[0]);
/* 365 */                 worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d13, d15, d16, 0, -f4, 0.01D, f3, 1.0D, new int[0]);
/*     */               } 
/*     */             } 
/* 368 */           } else if (this.ticksCaughtDelay > 0) {
/* 369 */             this.ticksCaughtDelay -= l;
/* 370 */             float f1 = 0.15F;
/*     */             
/* 372 */             if (this.ticksCaughtDelay < 20) {
/* 373 */               f1 = (float)(f1 + (20 - this.ticksCaughtDelay) * 0.05D);
/* 374 */             } else if (this.ticksCaughtDelay < 40) {
/* 375 */               f1 = (float)(f1 + (40 - this.ticksCaughtDelay) * 0.02D);
/* 376 */             } else if (this.ticksCaughtDelay < 60) {
/* 377 */               f1 = (float)(f1 + (60 - this.ticksCaughtDelay) * 0.01D);
/*     */             } 
/*     */             
/* 380 */             if (this.rand.nextFloat() < f1) {
/* 381 */               float f9 = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F) * 0.017453292F;
/* 382 */               float f2 = MathHelper.randomFloatClamp(this.rand, 25.0F, 60.0F);
/* 383 */               double d12 = this.posX + (MathHelper.sin(f9) * f2 * 0.1F);
/* 384 */               double d14 = (MathHelper.floor_double((getEntityBoundingBox()).minY) + 1.0F);
/* 385 */               double d6 = this.posZ + (MathHelper.cos(f9) * f2 * 0.1F);
/* 386 */               Block block = worldserver.getBlockState(new BlockPos((int)d12, (int)d14 - 1, (int)d6)).getBlock();
/*     */               
/* 388 */               if (block == Blocks.water || block == Blocks.flowing_water) {
/* 389 */                 worldserver.spawnParticle(EnumParticleTypes.WATER_SPLASH, d12, d14, d6, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
/*     */               }
/*     */             } 
/*     */             
/* 393 */             if (this.ticksCaughtDelay <= 0) {
/* 394 */               this.fishApproachAngle = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F);
/* 395 */               this.ticksCatchableDelay = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
/*     */             } 
/*     */           } else {
/* 398 */             this.ticksCaughtDelay = MathHelper.getRandomIntegerInRange(this.rand, 100, 900);
/* 399 */             this.ticksCaughtDelay -= EnchantmentHelper.getLureModifier((EntityLivingBase)this.angler) * 20 * 5;
/*     */           } 
/*     */           
/* 402 */           if (this.ticksCatchable > 0) {
/* 403 */             this.motionY -= (this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2D;
/*     */           }
/*     */         } 
/*     */         
/* 407 */         double d11 = d10 * 2.0D - 1.0D;
/* 408 */         this.motionY += 0.03999999910593033D * d11;
/*     */         
/* 410 */         if (d10 > 0.0D) {
/* 411 */           f6 = (float)(f6 * 0.9D);
/* 412 */           this.motionY *= 0.8D;
/*     */         } 
/*     */         
/* 415 */         this.motionX *= f6;
/* 416 */         this.motionY *= f6;
/* 417 */         this.motionZ *= f6;
/* 418 */         setPosition(this.posX, this.posY, this.posZ);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 427 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 428 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 429 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 430 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 431 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 432 */     tagCompound.setByte("shake", (byte)this.shake);
/* 433 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 440 */     this.xTile = tagCompund.getShort("xTile");
/* 441 */     this.yTile = tagCompund.getShort("yTile");
/* 442 */     this.zTile = tagCompund.getShort("zTile");
/*     */     
/* 444 */     if (tagCompund.hasKey("inTile", 8)) {
/* 445 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     } else {
/* 447 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 450 */     this.shake = tagCompund.getByte("shake") & 0xFF;
/* 451 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/*     */   }
/*     */   
/*     */   public int handleHookRetraction() {
/* 455 */     if (this.worldObj.isRemote) {
/* 456 */       return 0;
/*     */     }
/* 458 */     int i = 0;
/*     */     
/* 460 */     if (this.caughtEntity != null) {
/* 461 */       double d0 = this.angler.posX - this.posX;
/* 462 */       double d2 = this.angler.posY - this.posY;
/* 463 */       double d4 = this.angler.posZ - this.posZ;
/* 464 */       double d6 = MathHelper.sqrt_double(d0 * d0 + d2 * d2 + d4 * d4);
/* 465 */       double d8 = 0.1D;
/* 466 */       this.caughtEntity.motionX += d0 * d8;
/* 467 */       this.caughtEntity.motionY += d2 * d8 + MathHelper.sqrt_double(d6) * 0.08D;
/* 468 */       this.caughtEntity.motionZ += d4 * d8;
/* 469 */       i = 3;
/* 470 */     } else if (this.ticksCatchable > 0) {
/* 471 */       EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, getFishingResult());
/* 472 */       double d1 = this.angler.posX - this.posX;
/* 473 */       double d3 = this.angler.posY - this.posY;
/* 474 */       double d5 = this.angler.posZ - this.posZ;
/* 475 */       double d7 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
/* 476 */       double d9 = 0.1D;
/* 477 */       entityitem.motionX = d1 * d9;
/* 478 */       entityitem.motionY = d3 * d9 + MathHelper.sqrt_double(d7) * 0.08D;
/* 479 */       entityitem.motionZ = d5 * d9;
/* 480 */       this.worldObj.spawnEntityInWorld((Entity)entityitem);
/* 481 */       this.angler.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5D, this.angler.posZ + 0.5D, this.rand.nextInt(6) + 1));
/* 482 */       i = 1;
/*     */     } 
/*     */     
/* 485 */     if (this.inGround) {
/* 486 */       i = 2;
/*     */     }
/*     */     
/* 489 */     setDead();
/* 490 */     this.angler.fishEntity = null;
/* 491 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack getFishingResult() {
/* 496 */     float f = this.worldObj.rand.nextFloat();
/* 497 */     int i = EnchantmentHelper.getLuckOfSeaModifier((EntityLivingBase)this.angler);
/* 498 */     int j = EnchantmentHelper.getLureModifier((EntityLivingBase)this.angler);
/* 499 */     float f1 = 0.1F - i * 0.025F - j * 0.01F;
/* 500 */     float f2 = 0.05F + i * 0.01F - j * 0.01F;
/* 501 */     f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
/* 502 */     f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);
/*     */     
/* 504 */     if (f < f1) {
/* 505 */       this.angler.triggerAchievement(StatList.junkFishedStat);
/* 506 */       return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, JUNK)).getItemStack(this.rand);
/*     */     } 
/* 508 */     f -= f1;
/*     */     
/* 510 */     if (f < f2) {
/* 511 */       this.angler.triggerAchievement(StatList.treasureFishedStat);
/* 512 */       return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, TREASURE)).getItemStack(this.rand);
/*     */     } 
/* 514 */     float f3 = f - f2;
/* 515 */     this.angler.triggerAchievement(StatList.fishCaughtStat);
/* 516 */     return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, FISH)).getItemStack(this.rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 525 */     super.setDead();
/*     */     
/* 527 */     if (this.angler != null)
/* 528 */       this.angler.fishEntity = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityFishHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */