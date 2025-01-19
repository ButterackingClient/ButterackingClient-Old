/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public abstract class EntityThrowable
/*     */   extends Entity
/*     */   implements IProjectile {
/*  24 */   private int xTile = -1;
/*  25 */   private int yTile = -1;
/*  26 */   private int zTile = -1;
/*     */   
/*     */   private Block inTile;
/*     */   
/*     */   protected boolean inGround;
/*     */   
/*     */   public int throwableShake;
/*     */   
/*     */   private EntityLivingBase thrower;
/*     */   private String throwerName;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   
/*     */   public EntityThrowable(World worldIn) {
/*  40 */     super(worldIn);
/*  41 */     setSize(0.25F, 0.25F);
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
/*  52 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  54 */     if (Double.isNaN(d0)) {
/*  55 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  58 */     d0 *= 64.0D;
/*  59 */     return (distance < d0 * d0);
/*     */   }
/*     */   
/*     */   public EntityThrowable(World worldIn, EntityLivingBase throwerIn) {
/*  63 */     super(worldIn);
/*  64 */     this.thrower = throwerIn;
/*  65 */     setSize(0.25F, 0.25F);
/*  66 */     setLocationAndAngles(throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight(), throwerIn.posZ, throwerIn.rotationYaw, throwerIn.rotationPitch);
/*  67 */     this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  68 */     this.posY -= 0.10000000149011612D;
/*  69 */     this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  70 */     setPosition(this.posX, this.posY, this.posZ);
/*  71 */     float f = 0.4F;
/*  72 */     this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  73 */     this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * f);
/*  74 */     this.motionY = (-MathHelper.sin((this.rotationPitch + getInaccuracy()) / 180.0F * 3.1415927F) * f);
/*  75 */     setThrowableHeading(this.motionX, this.motionY, this.motionZ, getVelocity(), 1.0F);
/*     */   }
/*     */   
/*     */   public EntityThrowable(World worldIn, double x, double y, double z) {
/*  79 */     super(worldIn);
/*  80 */     this.ticksInGround = 0;
/*  81 */     setSize(0.25F, 0.25F);
/*  82 */     setPosition(x, y, z);
/*     */   }
/*     */   
/*     */   protected float getVelocity() {
/*  86 */     return 1.5F;
/*     */   }
/*     */   
/*     */   protected float getInaccuracy() {
/*  90 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/*  97 */     float f = MathHelper.sqrt_double(x * x + y * y + z * z);
/*  98 */     x /= f;
/*  99 */     y /= f;
/* 100 */     z /= f;
/* 101 */     x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 102 */     y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 103 */     z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 104 */     x *= velocity;
/* 105 */     y *= velocity;
/* 106 */     z *= velocity;
/* 107 */     this.motionX = x;
/* 108 */     this.motionY = y;
/* 109 */     this.motionZ = z;
/* 110 */     float f1 = MathHelper.sqrt_double(x * x + z * z);
/* 111 */     this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 112 */     this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 180.0D / Math.PI);
/* 113 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 120 */     this.motionX = x;
/* 121 */     this.motionY = y;
/* 122 */     this.motionZ = z;
/*     */     
/* 124 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/* 125 */       float f = MathHelper.sqrt_double(x * x + z * z);
/* 126 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/* 127 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f) * 180.0D / Math.PI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 135 */     this.lastTickPosX = this.posX;
/* 136 */     this.lastTickPosY = this.posY;
/* 137 */     this.lastTickPosZ = this.posZ;
/* 138 */     super.onUpdate();
/*     */     
/* 140 */     if (this.throwableShake > 0) {
/* 141 */       this.throwableShake--;
/*     */     }
/*     */     
/* 144 */     if (this.inGround) {
/* 145 */       if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/* 146 */         this.ticksInGround++;
/*     */         
/* 148 */         if (this.ticksInGround == 1200) {
/* 149 */           setDead();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 155 */       this.inGround = false;
/* 156 */       this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 157 */       this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 158 */       this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 159 */       this.ticksInGround = 0;
/* 160 */       this.ticksInAir = 0;
/*     */     } else {
/* 162 */       this.ticksInAir++;
/*     */     } 
/*     */     
/* 165 */     Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 166 */     Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 167 */     MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
/* 168 */     vec3 = new Vec3(this.posX, this.posY, this.posZ);
/* 169 */     vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */     
/* 171 */     if (movingobjectposition != null) {
/* 172 */       vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */     }
/*     */     
/* 175 */     if (!this.worldObj.isRemote) {
/* 176 */       Entity entity = null;
/* 177 */       List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
/* 178 */       double d0 = 0.0D;
/* 179 */       EntityLivingBase entitylivingbase = getThrower();
/*     */       
/* 181 */       for (int j = 0; j < list.size(); j++) {
/* 182 */         Entity entity1 = list.get(j);
/*     */         
/* 184 */         if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase || this.ticksInAir >= 5)) {
/* 185 */           float f = 0.3F;
/* 186 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
/* 187 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
/*     */           
/* 189 */           if (movingobjectposition1 != null) {
/* 190 */             double d1 = vec3.squareDistanceTo(movingobjectposition1.hitVec);
/*     */             
/* 192 */             if (d1 < d0 || d0 == 0.0D) {
/* 193 */               entity = entity1;
/* 194 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 200 */       if (entity != null) {
/* 201 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */     } 
/*     */     
/* 205 */     if (movingobjectposition != null) {
/* 206 */       if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.portal) {
/* 207 */         setPortal(movingobjectposition.getBlockPos());
/*     */       } else {
/* 209 */         onImpact(movingobjectposition);
/*     */       } 
/*     */     }
/*     */     
/* 213 */     this.posX += this.motionX;
/* 214 */     this.posY += this.motionY;
/* 215 */     this.posZ += this.motionZ;
/* 216 */     float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 217 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */     
/* 219 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 223 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 224 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 227 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 228 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 231 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 232 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 235 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 236 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 237 */     float f2 = 0.99F;
/* 238 */     float f3 = getGravityVelocity();
/*     */     
/* 240 */     if (isInWater()) {
/* 241 */       for (int i = 0; i < 4; i++) {
/* 242 */         float f4 = 0.25F;
/* 243 */         this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f4, this.posY - this.motionY * f4, this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */       } 
/*     */       
/* 246 */       f2 = 0.8F;
/*     */     } 
/*     */     
/* 249 */     this.motionX *= f2;
/* 250 */     this.motionY *= f2;
/* 251 */     this.motionZ *= f2;
/* 252 */     this.motionY -= f3;
/* 253 */     setPosition(this.posX, this.posY, this.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getGravityVelocity() {
/* 260 */     return 0.03F;
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
/* 272 */     tagCompound.setShort("xTile", (short)this.xTile);
/* 273 */     tagCompound.setShort("yTile", (short)this.yTile);
/* 274 */     tagCompound.setShort("zTile", (short)this.zTile);
/* 275 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
/* 276 */     tagCompound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 277 */     tagCompound.setByte("shake", (byte)this.throwableShake);
/* 278 */     tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/*     */     
/* 280 */     if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof net.minecraft.entity.player.EntityPlayer) {
/* 281 */       this.throwerName = this.thrower.getName();
/*     */     }
/*     */     
/* 284 */     tagCompound.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 291 */     this.xTile = tagCompund.getShort("xTile");
/* 292 */     this.yTile = tagCompund.getShort("yTile");
/* 293 */     this.zTile = tagCompund.getShort("zTile");
/*     */     
/* 295 */     if (tagCompund.hasKey("inTile", 8)) {
/* 296 */       this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
/*     */     } else {
/* 298 */       this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 301 */     this.throwableShake = tagCompund.getByte("shake") & 0xFF;
/* 302 */     this.inGround = (tagCompund.getByte("inGround") == 1);
/* 303 */     this.thrower = null;
/* 304 */     this.throwerName = tagCompund.getString("ownerName");
/*     */     
/* 306 */     if (this.throwerName != null && this.throwerName.length() == 0) {
/* 307 */       this.throwerName = null;
/*     */     }
/*     */     
/* 310 */     this.thrower = getThrower();
/*     */   }
/*     */   
/*     */   public EntityLivingBase getThrower() {
/* 314 */     if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
/* 315 */       this.thrower = (EntityLivingBase)this.worldObj.getPlayerEntityByName(this.throwerName);
/*     */       
/* 317 */       if (this.thrower == null && this.worldObj instanceof WorldServer) {
/*     */         try {
/* 319 */           Entity entity = ((WorldServer)this.worldObj).getEntityFromUuid(UUID.fromString(this.throwerName));
/*     */           
/* 321 */           if (entity instanceof EntityLivingBase) {
/* 322 */             this.thrower = (EntityLivingBase)entity;
/*     */           }
/* 324 */         } catch (Throwable var2) {
/* 325 */           this.thrower = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 330 */     return this.thrower;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */