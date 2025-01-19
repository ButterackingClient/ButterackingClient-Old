/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityFireball extends Entity {
/*  21 */   private int xTile = -1;
/*  22 */   private int yTile = -1;
/*  23 */   private int zTile = -1;
/*     */   private Block inTile;
/*     */   private boolean inGround;
/*     */   public EntityLivingBase shootingEntity;
/*     */   private int ticksAlive;
/*     */   private int ticksInAir;
/*     */   public double accelerationX;
/*     */   public double accelerationY;
/*     */   public double accelerationZ;
/*     */   
/*     */   public EntityFireball(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     setSize(1.0F, 1.0F);
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
/*  46 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  48 */     if (Double.isNaN(d0)) {
/*  49 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  52 */     d0 *= 64.0D;
/*  53 */     return (distance < d0 * d0);
/*     */   }
/*     */   
/*     */   public EntityFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/*  57 */     super(worldIn);
/*  58 */     setSize(1.0F, 1.0F);
/*  59 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*  60 */     setPosition(x, y, z);
/*  61 */     double d0 = MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
/*  62 */     this.accelerationX = accelX / d0 * 0.1D;
/*  63 */     this.accelerationY = accelY / d0 * 0.1D;
/*  64 */     this.accelerationZ = accelZ / d0 * 0.1D;
/*     */   }
/*     */   
/*     */   public EntityFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/*  68 */     super(worldIn);
/*  69 */     this.shootingEntity = shooter;
/*  70 */     setSize(1.0F, 1.0F);
/*  71 */     setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
/*  72 */     setPosition(this.posX, this.posY, this.posZ);
/*  73 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/*  74 */     accelX += this.rand.nextGaussian() * 0.4D;
/*  75 */     accelY += this.rand.nextGaussian() * 0.4D;
/*  76 */     accelZ += this.rand.nextGaussian() * 0.4D;
/*  77 */     double d0 = MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
/*  78 */     this.accelerationX = accelX / d0 * 0.1D;
/*  79 */     this.accelerationY = accelY / d0 * 0.1D;
/*  80 */     this.accelerationZ = accelZ / d0 * 0.1D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  87 */     if (this.worldObj.isRemote || ((this.shootingEntity == null || !this.shootingEntity.isDead) && this.worldObj.isBlockLoaded(new BlockPos(this)))) {
/*  88 */       super.onUpdate();
/*  89 */       setFire(1);
/*     */       
/*  91 */       if (this.inGround) {
/*  92 */         if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/*  93 */           this.ticksAlive++;
/*     */           
/*  95 */           if (this.ticksAlive == 600) {
/*  96 */             setDead();
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 102 */         this.inGround = false;
/* 103 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 104 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 105 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 106 */         this.ticksAlive = 0;
/* 107 */         this.ticksInAir = 0;
/*     */       } else {
/* 109 */         this.ticksInAir++;
/*     */       } 
/*     */       
/* 112 */       Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 113 */       Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 114 */       MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
/* 115 */       vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 116 */       vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 118 */       if (movingobjectposition != null) {
/* 119 */         vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */       }
/*     */       
/* 122 */       Entity entity = null;
/* 123 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 124 */       double d0 = 0.0D;
/*     */       
/* 126 */       for (int i = 0; i < list.size(); i++) {
/* 127 */         Entity entity1 = list.get(i);
/*     */         
/* 129 */         if (entity1.canBeCollidedWith() && (!entity1.isEntityEqual((Entity)this.shootingEntity) || this.ticksInAir >= 25)) {
/* 130 */           float f = 0.3F;
/* 131 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
/* 132 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
/*     */           
/* 134 */           if (movingobjectposition1 != null) {
/* 135 */             double d1 = vec3.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 137 */             if (d1 < d0 || d0 == 0.0D) {
/* 138 */               entity = entity1;
/* 139 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       if (entity != null) {
/* 146 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 149 */       if (movingobjectposition != null) {
/* 150 */         onImpact(movingobjectposition);
/*     */       }
/*     */       
/* 153 */       this.posX += this.motionX;
/* 154 */       this.posY += this.motionY;
/* 155 */       this.posZ += this.motionZ;
/* 156 */       float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 157 */       this.rotationYaw = (float)(MathHelper.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;
/*     */       
/* 159 */       for (this.rotationPitch = (float)(MathHelper.atan2(f1, this.motionY) * 180.0D / Math.PI) - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */       
/* 163 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 164 */         this.prevRotationPitch += 360.0F;
/*     */       }
/*     */       
/* 167 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 168 */         this.prevRotationYaw -= 360.0F;
/*     */       }
/*     */       
/* 171 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 172 */         this.prevRotationYaw += 360.0F;
/*     */       }
/*     */       
/* 175 */       this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 176 */       this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 177 */       float f2 = getMotionFactor();
/*     */       
/* 179 */       if (isInWater()) {
/* 180 */         for (int j = 0; j < 4; j++) {
/* 181 */           float f3 = 0.25F;
/* 182 */           this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */         
/* 185 */         f2 = 0.8F;
/*     */       } 
/*     */       
/* 188 */       this.motionX += this.accelerationX;
/* 189 */       this.motionY += this.accelerationY;
/* 190 */       this.motionZ += this.accelerationZ;
/* 191 */       this.motionX *= f2;
/* 192 */       this.motionY *= f2;
/* 193 */       this.motionZ *= f2;
/* 194 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 195 */       setPosition(this.posX, this.posY, this.posZ);
/*     */     } else {
/* 197 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getMotionFactor() {
/* 205 */     return 0.95F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onImpact(MovingObjectPosition paramMovingObjectPosition);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 217 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 218 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 219 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 220 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 221 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 222 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 223 */     tagCompound.setTag("direction", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 230 */     this.xTile = tagCompund.getShort("xTile");
/* 231 */     this.yTile = tagCompund.getShort("yTile");
/* 232 */     this.zTile = tagCompund.getShort("zTile");
/*     */     
/* 234 */     if (tagCompund.hasKey("inTile", 8)) {
/* 235 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     } else {
/* 237 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 240 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/*     */     
/* 242 */     if (tagCompund.hasKey("direction", 9)) {
/* 243 */       NBTTagList nbttaglist = tagCompund.getTagList("direction", 6);
/* 244 */       this.motionX = nbttaglist.getDoubleAt(0);
/* 245 */       this.motionY = nbttaglist.getDoubleAt(1);
/* 246 */       this.motionZ = nbttaglist.getDoubleAt(2);
/*     */     } else {
/* 248 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 256 */     return true;
/*     */   }
/*     */   
/*     */   public float getCollisionBorderSize() {
/* 260 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 267 */     if (isEntityInvulnerable(source)) {
/* 268 */       return false;
/*     */     }
/* 270 */     setBeenAttacked();
/*     */     
/* 272 */     if (source.getEntity() != null) {
/* 273 */       Vec3 vec3 = source.getEntity().getLookVec();
/*     */       
/* 275 */       if (vec3 != null) {
/* 276 */         this.motionX = vec3.xCoord;
/* 277 */         this.motionY = vec3.yCoord;
/* 278 */         this.motionZ = vec3.zCoord;
/* 279 */         this.accelerationX = this.motionX * 0.1D;
/* 280 */         this.accelerationY = this.motionY * 0.1D;
/* 281 */         this.accelerationZ = this.motionZ * 0.1D;
/*     */       } 
/*     */       
/* 284 */       if (source.getEntity() instanceof EntityLivingBase) {
/* 285 */         this.shootingEntity = (EntityLivingBase)source.getEntity();
/*     */       }
/*     */       
/* 288 */       return true;
/*     */     } 
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/* 299 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 303 */     return 15728880;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */