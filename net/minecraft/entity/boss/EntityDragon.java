/*     */ package net.minecraft.entity.boss;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityMultiPart;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.IMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntityDamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityDragon
/*     */   extends EntityLiving
/*     */   implements IBossDisplayData, IEntityMultiPart, IMob
/*     */ {
/*     */   public double targetX;
/*     */   public double targetY;
/*     */   public double targetZ;
/*  40 */   public double[][] ringBuffer = new double[64][3];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public int ringBufferIndex = -1;
/*     */ 
/*     */   
/*     */   public EntityDragonPart[] dragonPartArray;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartHead;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartBody;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartTail1;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartTail2;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartTail3;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartWing1;
/*     */ 
/*     */   
/*     */   public EntityDragonPart dragonPartWing2;
/*     */ 
/*     */   
/*     */   public float prevAnimTime;
/*     */ 
/*     */   
/*     */   public float animTime;
/*     */ 
/*     */   
/*     */   public boolean forceNewTarget;
/*     */ 
/*     */   
/*     */   public boolean slowed;
/*     */ 
/*     */   
/*     */   private Entity target;
/*     */ 
/*     */   
/*     */   public int deathTicks;
/*     */ 
/*     */   
/*     */   public EntityEnderCrystal healingEnderCrystal;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityDragon(World worldIn) {
/*  95 */     super(worldIn);
/*  96 */     this.dragonPartArray = new EntityDragonPart[] { this.dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F), this.dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F) };
/*  97 */     setHealth(getMaxHealth());
/*  98 */     setSize(16.0F, 8.0F);
/*  99 */     this.noClip = true;
/* 100 */     this.isImmuneToFire = true;
/* 101 */     this.targetY = 100.0D;
/* 102 */     this.ignoreFrustumCheck = true;
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 106 */     super.applyEntityAttributes();
/* 107 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 111 */     super.entityInit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_) {
/* 119 */     if (getHealth() <= 0.0F) {
/* 120 */       p_70974_2_ = 0.0F;
/*     */     }
/*     */     
/* 123 */     p_70974_2_ = 1.0F - p_70974_2_;
/* 124 */     int i = this.ringBufferIndex - p_70974_1_ * 1 & 0x3F;
/* 125 */     int j = this.ringBufferIndex - p_70974_1_ * 1 - 1 & 0x3F;
/* 126 */     double[] adouble = new double[3];
/* 127 */     double d0 = this.ringBuffer[i][0];
/* 128 */     double d1 = MathHelper.wrapAngleTo180_double(this.ringBuffer[j][0] - d0);
/* 129 */     adouble[0] = d0 + d1 * p_70974_2_;
/* 130 */     d0 = this.ringBuffer[i][1];
/* 131 */     d1 = this.ringBuffer[j][1] - d0;
/* 132 */     adouble[1] = d0 + d1 * p_70974_2_;
/* 133 */     adouble[2] = this.ringBuffer[i][2] + (this.ringBuffer[j][2] - this.ringBuffer[i][2]) * p_70974_2_;
/* 134 */     return adouble;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 142 */     if (this.worldObj.isRemote) {
/* 143 */       float f = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
/* 144 */       float f1 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
/*     */       
/* 146 */       if (f1 <= -0.3F && f >= -0.3F && !isSilent()) {
/* 147 */         this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
/*     */       }
/*     */     } 
/*     */     
/* 151 */     this.prevAnimTime = this.animTime;
/*     */     
/* 153 */     if (getHealth() <= 0.0F) {
/* 154 */       float f11 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 155 */       float f13 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/* 156 */       float f14 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 157 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f11, this.posY + 2.0D + f13, this.posZ + f14, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } else {
/* 159 */       updateDragonEnderCrystal();
/* 160 */       float f10 = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
/* 161 */       f10 *= (float)Math.pow(2.0D, this.motionY);
/*     */       
/* 163 */       if (this.slowed) {
/* 164 */         this.animTime += f10 * 0.5F;
/*     */       } else {
/* 166 */         this.animTime += f10;
/*     */       } 
/*     */       
/* 169 */       this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
/*     */       
/* 171 */       if (isAIDisabled()) {
/* 172 */         this.animTime = 0.5F;
/*     */       } else {
/* 174 */         if (this.ringBufferIndex < 0) {
/* 175 */           for (int i = 0; i < this.ringBuffer.length; i++) {
/* 176 */             this.ringBuffer[i][0] = this.rotationYaw;
/* 177 */             this.ringBuffer[i][1] = this.posY;
/*     */           } 
/*     */         }
/*     */         
/* 181 */         if (++this.ringBufferIndex == this.ringBuffer.length) {
/* 182 */           this.ringBufferIndex = 0;
/*     */         }
/*     */         
/* 185 */         this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
/* 186 */         this.ringBuffer[this.ringBufferIndex][1] = this.posY;
/*     */         
/* 188 */         if (this.worldObj.isRemote) {
/* 189 */           if (this.newPosRotationIncrements > 0) {
/* 190 */             double d10 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
/* 191 */             double d0 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
/* 192 */             double d1 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
/* 193 */             double d2 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
/* 194 */             this.rotationYaw = (float)(this.rotationYaw + d2 / this.newPosRotationIncrements);
/* 195 */             this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
/* 196 */             this.newPosRotationIncrements--;
/* 197 */             setPosition(d10, d0, d1);
/* 198 */             setRotation(this.rotationYaw, this.rotationPitch);
/*     */           } 
/*     */         } else {
/* 201 */           double d11 = this.targetX - this.posX;
/* 202 */           double d12 = this.targetY - this.posY;
/* 203 */           double d13 = this.targetZ - this.posZ;
/* 204 */           double d14 = d11 * d11 + d12 * d12 + d13 * d13;
/*     */           
/* 206 */           if (this.target != null) {
/* 207 */             this.targetX = this.target.posX;
/* 208 */             this.targetZ = this.target.posZ;
/* 209 */             double d3 = this.targetX - this.posX;
/* 210 */             double d5 = this.targetZ - this.posZ;
/* 211 */             double d7 = Math.sqrt(d3 * d3 + d5 * d5);
/* 212 */             double d8 = 0.4000000059604645D + d7 / 80.0D - 1.0D;
/*     */             
/* 214 */             if (d8 > 10.0D) {
/* 215 */               d8 = 10.0D;
/*     */             }
/*     */             
/* 218 */             this.targetY = (this.target.getEntityBoundingBox()).minY + d8;
/*     */           } else {
/* 220 */             this.targetX += this.rand.nextGaussian() * 2.0D;
/* 221 */             this.targetZ += this.rand.nextGaussian() * 2.0D;
/*     */           } 
/*     */           
/* 224 */           if (this.forceNewTarget || d14 < 100.0D || d14 > 22500.0D || this.isCollidedHorizontally || this.isCollidedVertically) {
/* 225 */             setNewTarget();
/*     */           }
/*     */           
/* 228 */           d12 /= MathHelper.sqrt_double(d11 * d11 + d13 * d13);
/* 229 */           float f17 = 0.6F;
/* 230 */           d12 = MathHelper.clamp_double(d12, -f17, f17);
/* 231 */           this.motionY += d12 * 0.10000000149011612D;
/* 232 */           this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
/* 233 */           double d4 = 180.0D - MathHelper.atan2(d11, d13) * 180.0D / Math.PI;
/* 234 */           double d6 = MathHelper.wrapAngleTo180_double(d4 - this.rotationYaw);
/*     */           
/* 236 */           if (d6 > 50.0D) {
/* 237 */             d6 = 50.0D;
/*     */           }
/*     */           
/* 240 */           if (d6 < -50.0D) {
/* 241 */             d6 = -50.0D;
/*     */           }
/*     */           
/* 244 */           Vec3 vec3 = (new Vec3(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ)).normalize();
/* 245 */           double d15 = -MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
/* 246 */           Vec3 vec31 = (new Vec3(MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), this.motionY, d15)).normalize();
/* 247 */           float f5 = ((float)vec31.dotProduct(vec3) + 0.5F) / 1.5F;
/*     */           
/* 249 */           if (f5 < 0.0F) {
/* 250 */             f5 = 0.0F;
/*     */           }
/*     */           
/* 253 */           this.randomYawVelocity *= 0.8F;
/* 254 */           float f6 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
/* 255 */           double d9 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;
/*     */           
/* 257 */           if (d9 > 40.0D) {
/* 258 */             d9 = 40.0D;
/*     */           }
/*     */           
/* 261 */           this.randomYawVelocity = (float)(this.randomYawVelocity + d6 * 0.699999988079071D / d9 / f6);
/* 262 */           this.rotationYaw += this.randomYawVelocity * 0.1F;
/* 263 */           float f7 = (float)(2.0D / (d9 + 1.0D));
/* 264 */           float f8 = 0.06F;
/* 265 */           moveFlying(0.0F, -1.0F, f8 * (f5 * f7 + 1.0F - f7));
/*     */           
/* 267 */           if (this.slowed) {
/* 268 */             moveEntity(this.motionX * 0.800000011920929D, this.motionY * 0.800000011920929D, this.motionZ * 0.800000011920929D);
/*     */           } else {
/* 270 */             moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */           } 
/*     */           
/* 273 */           Vec3 vec32 = (new Vec3(this.motionX, this.motionY, this.motionZ)).normalize();
/* 274 */           float f9 = ((float)vec32.dotProduct(vec31) + 1.0F) / 2.0F;
/* 275 */           f9 = 0.8F + 0.15F * f9;
/* 276 */           this.motionX *= f9;
/* 277 */           this.motionZ *= f9;
/* 278 */           this.motionY *= 0.9100000262260437D;
/*     */         } 
/*     */         
/* 281 */         this.renderYawOffset = this.rotationYaw;
/* 282 */         this.dragonPartHead.width = this.dragonPartHead.height = 3.0F;
/* 283 */         this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
/* 284 */         this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
/* 285 */         this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
/* 286 */         this.dragonPartBody.height = 3.0F;
/* 287 */         this.dragonPartBody.width = 5.0F;
/* 288 */         this.dragonPartWing1.height = 2.0F;
/* 289 */         this.dragonPartWing1.width = 4.0F;
/* 290 */         this.dragonPartWing2.height = 3.0F;
/* 291 */         this.dragonPartWing2.width = 4.0F;
/* 292 */         float f12 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
/* 293 */         float f2 = MathHelper.cos(f12);
/* 294 */         float f15 = -MathHelper.sin(f12);
/* 295 */         float f3 = this.rotationYaw * 3.1415927F / 180.0F;
/* 296 */         float f16 = MathHelper.sin(f3);
/* 297 */         float f4 = MathHelper.cos(f3);
/* 298 */         this.dragonPartBody.onUpdate();
/* 299 */         this.dragonPartBody.setLocationAndAngles(this.posX + (f16 * 0.5F), this.posY, this.posZ - (f4 * 0.5F), 0.0F, 0.0F);
/* 300 */         this.dragonPartWing1.onUpdate();
/* 301 */         this.dragonPartWing1.setLocationAndAngles(this.posX + (f4 * 4.5F), this.posY + 2.0D, this.posZ + (f16 * 4.5F), 0.0F, 0.0F);
/* 302 */         this.dragonPartWing2.onUpdate();
/* 303 */         this.dragonPartWing2.setLocationAndAngles(this.posX - (f4 * 4.5F), this.posY + 2.0D, this.posZ - (f16 * 4.5F), 0.0F, 0.0F);
/*     */         
/* 305 */         if (!this.worldObj.isRemote && this.hurtTime == 0) {
/* 306 */           collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing1.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/* 307 */           collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing2.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
/* 308 */           attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartHead.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D)));
/*     */         } 
/*     */         
/* 311 */         double[] adouble1 = getMovementOffsets(5, 1.0F);
/* 312 */         double[] adouble = getMovementOffsets(0, 1.0F);
/* 313 */         float f18 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
/* 314 */         float f19 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
/* 315 */         this.dragonPartHead.onUpdate();
/* 316 */         this.dragonPartHead.setLocationAndAngles(this.posX + (f18 * 5.5F * f2), this.posY + (adouble[1] - adouble1[1]) * 1.0D + (f15 * 5.5F), this.posZ - (f19 * 5.5F * f2), 0.0F, 0.0F);
/*     */         
/* 318 */         for (int j = 0; j < 3; j++) {
/* 319 */           EntityDragonPart entitydragonpart = null;
/*     */           
/* 321 */           if (j == 0) {
/* 322 */             entitydragonpart = this.dragonPartTail1;
/*     */           }
/*     */           
/* 325 */           if (j == 1) {
/* 326 */             entitydragonpart = this.dragonPartTail2;
/*     */           }
/*     */           
/* 329 */           if (j == 2) {
/* 330 */             entitydragonpart = this.dragonPartTail3;
/*     */           }
/*     */           
/* 333 */           double[] adouble2 = getMovementOffsets(12 + j * 2, 1.0F);
/* 334 */           float f20 = this.rotationYaw * 3.1415927F / 180.0F + simplifyAngle(adouble2[0] - adouble1[0]) * 3.1415927F / 180.0F * 1.0F;
/* 335 */           float f21 = MathHelper.sin(f20);
/* 336 */           float f22 = MathHelper.cos(f20);
/* 337 */           float f23 = 1.5F;
/* 338 */           float f24 = (j + 1) * 2.0F;
/* 339 */           entitydragonpart.onUpdate();
/* 340 */           entitydragonpart.setLocationAndAngles(this.posX - ((f16 * f23 + f21 * f24) * f2), this.posY + (adouble2[1] - adouble1[1]) * 1.0D - ((f24 + f23) * f15) + 1.5D, this.posZ + ((f4 * f23 + f22 * f24) * f2), 0.0F, 0.0F);
/*     */         } 
/*     */         
/* 343 */         if (!this.worldObj.isRemote) {
/* 344 */           this.slowed = destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox()) | destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateDragonEnderCrystal() {
/* 354 */     if (this.healingEnderCrystal != null) {
/* 355 */       if (this.healingEnderCrystal.isDead) {
/* 356 */         if (!this.worldObj.isRemote) {
/* 357 */           attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource(null), 10.0F);
/*     */         }
/*     */         
/* 360 */         this.healingEnderCrystal = null;
/* 361 */       } else if (this.ticksExisted % 10 == 0 && getHealth() < getMaxHealth()) {
/* 362 */         setHealth(getHealth() + 1.0F);
/*     */       } 
/*     */     }
/*     */     
/* 366 */     if (this.rand.nextInt(10) == 0) {
/* 367 */       float f = 32.0F;
/* 368 */       List<EntityEnderCrystal> list = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, getEntityBoundingBox().expand(f, f, f));
/* 369 */       EntityEnderCrystal entityendercrystal = null;
/* 370 */       double d0 = Double.MAX_VALUE;
/*     */       
/* 372 */       for (EntityEnderCrystal entityendercrystal1 : list) {
/* 373 */         double d1 = entityendercrystal1.getDistanceSqToEntity((Entity)this);
/*     */         
/* 375 */         if (d1 < d0) {
/* 376 */           d0 = d1;
/* 377 */           entityendercrystal = entityendercrystal1;
/*     */         } 
/*     */       } 
/*     */       
/* 381 */       this.healingEnderCrystal = entityendercrystal;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collideWithEntities(List<Entity> p_70970_1_) {
/* 389 */     double d0 = ((this.dragonPartBody.getEntityBoundingBox()).minX + (this.dragonPartBody.getEntityBoundingBox()).maxX) / 2.0D;
/* 390 */     double d1 = ((this.dragonPartBody.getEntityBoundingBox()).minZ + (this.dragonPartBody.getEntityBoundingBox()).maxZ) / 2.0D;
/*     */     
/* 392 */     for (Entity entity : p_70970_1_) {
/* 393 */       if (entity instanceof EntityLivingBase) {
/* 394 */         double d2 = entity.posX - d0;
/* 395 */         double d3 = entity.posZ - d1;
/* 396 */         double d4 = d2 * d2 + d3 * d3;
/* 397 */         entity.addVelocity(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attackEntitiesInList(List<Entity> p_70971_1_) {
/* 406 */     for (int i = 0; i < p_70971_1_.size(); i++) {
/* 407 */       Entity entity = p_70971_1_.get(i);
/*     */       
/* 409 */       if (entity instanceof EntityLivingBase) {
/* 410 */         entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 10.0F);
/* 411 */         applyEnchantments((EntityLivingBase)this, entity);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNewTarget() {
/* 420 */     this.forceNewTarget = false;
/* 421 */     List<EntityPlayer> list = Lists.newArrayList(this.worldObj.playerEntities);
/* 422 */     Iterator<EntityPlayer> iterator = list.iterator();
/*     */     
/* 424 */     while (iterator.hasNext()) {
/* 425 */       if (((EntityPlayer)iterator.next()).isSpectator()) {
/* 426 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */     
/* 430 */     if (this.rand.nextInt(2) == 0 && !list.isEmpty()) {
/* 431 */       this.target = (Entity)list.get(this.rand.nextInt(list.size()));
/*     */     } else {
/*     */       boolean flag; do {
/* 434 */         this.targetX = 0.0D;
/* 435 */         this.targetY = (70.0F + this.rand.nextFloat() * 50.0F);
/* 436 */         this.targetZ = 0.0D;
/* 437 */         this.targetX += (this.rand.nextFloat() * 120.0F - 60.0F);
/* 438 */         this.targetZ += (this.rand.nextFloat() * 120.0F - 60.0F);
/* 439 */         double d0 = this.posX - this.targetX;
/* 440 */         double d1 = this.posY - this.targetY;
/* 441 */         double d2 = this.posZ - this.targetZ;
/* 442 */         flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
/*     */       }
/* 444 */       while (!flag);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 449 */       this.target = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float simplifyAngle(double p_70973_1_) {
/* 457 */     return (float)MathHelper.wrapAngleTo180_double(p_70973_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_) {
/* 464 */     int i = MathHelper.floor_double(p_70972_1_.minX);
/* 465 */     int j = MathHelper.floor_double(p_70972_1_.minY);
/* 466 */     int k = MathHelper.floor_double(p_70972_1_.minZ);
/* 467 */     int l = MathHelper.floor_double(p_70972_1_.maxX);
/* 468 */     int i1 = MathHelper.floor_double(p_70972_1_.maxY);
/* 469 */     int j1 = MathHelper.floor_double(p_70972_1_.maxZ);
/* 470 */     boolean flag = false;
/* 471 */     boolean flag1 = false;
/*     */     
/* 473 */     for (int k1 = i; k1 <= l; k1++) {
/* 474 */       for (int l1 = j; l1 <= i1; l1++) {
/* 475 */         for (int i2 = k; i2 <= j1; i2++) {
/* 476 */           BlockPos blockpos = new BlockPos(k1, l1, i2);
/* 477 */           Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */           
/* 479 */           if (block.getMaterial() != Material.air) {
/* 480 */             if (block != Blocks.barrier && block != Blocks.obsidian && block != Blocks.end_stone && block != Blocks.bedrock && block != Blocks.command_block && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
/* 481 */               flag1 = !(!this.worldObj.setBlockToAir(blockpos) && !flag1);
/*     */             } else {
/* 483 */               flag = true;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 490 */     if (flag1) {
/* 491 */       double d0 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * this.rand.nextFloat();
/* 492 */       double d1 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * this.rand.nextFloat();
/* 493 */       double d2 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * this.rand.nextFloat();
/* 494 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 497 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean attackEntityFromPart(EntityDragonPart dragonPart, DamageSource source, float p_70965_3_) {
/* 501 */     if (dragonPart != this.dragonPartHead) {
/* 502 */       p_70965_3_ = p_70965_3_ / 4.0F + 1.0F;
/*     */     }
/*     */     
/* 505 */     float f = this.rotationYaw * 3.1415927F / 180.0F;
/* 506 */     float f1 = MathHelper.sin(f);
/* 507 */     float f2 = MathHelper.cos(f);
/* 508 */     this.targetX = this.posX + (f1 * 5.0F) + ((this.rand.nextFloat() - 0.5F) * 2.0F);
/* 509 */     this.targetY = this.posY + (this.rand.nextFloat() * 3.0F) + 1.0D;
/* 510 */     this.targetZ = this.posZ - (f2 * 5.0F) + ((this.rand.nextFloat() - 0.5F) * 2.0F);
/* 511 */     this.target = null;
/*     */     
/* 513 */     if (source.getEntity() instanceof EntityPlayer || source.isExplosion()) {
/* 514 */       attackDragonFrom(source, p_70965_3_);
/*     */     }
/*     */     
/* 517 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 524 */     if (source instanceof EntityDamageSource && ((EntityDamageSource)source).getIsThornsDamage()) {
/* 525 */       attackDragonFrom(source, amount);
/*     */     }
/*     */     
/* 528 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean attackDragonFrom(DamageSource source, float amount) {
/* 535 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillCommand() {
/* 542 */     setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDeathUpdate() {
/* 549 */     this.deathTicks++;
/*     */     
/* 551 */     if (this.deathTicks >= 180 && this.deathTicks <= 200) {
/* 552 */       float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 553 */       float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
/* 554 */       float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
/* 555 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 558 */     boolean flag = this.worldObj.getGameRules().getBoolean("doMobLoot");
/*     */     
/* 560 */     if (!this.worldObj.isRemote) {
/* 561 */       if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && flag) {
/* 562 */         int i = 1000;
/*     */         
/* 564 */         while (i > 0) {
/* 565 */           int k = EntityXPOrb.getXPSplit(i);
/* 566 */           i -= k;
/* 567 */           this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, k));
/*     */         } 
/*     */       } 
/*     */       
/* 571 */       if (this.deathTicks == 1) {
/* 572 */         this.worldObj.playBroadcastSound(1018, new BlockPos((Entity)this), 0);
/*     */       }
/*     */     } 
/*     */     
/* 576 */     moveEntity(0.0D, 0.10000000149011612D, 0.0D);
/* 577 */     this.renderYawOffset = this.rotationYaw += 20.0F;
/*     */     
/* 579 */     if (this.deathTicks == 200 && !this.worldObj.isRemote) {
/* 580 */       if (flag) {
/* 581 */         int j = 2000;
/*     */         
/* 583 */         while (j > 0) {
/* 584 */           int l = EntityXPOrb.getXPSplit(j);
/* 585 */           j -= l;
/* 586 */           this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, l));
/*     */         } 
/*     */       } 
/*     */       
/* 590 */       generatePortal(new BlockPos(this.posX, 64.0D, this.posZ));
/* 591 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generatePortal(BlockPos pos) {
/* 599 */     int i = 4;
/* 600 */     double d0 = 12.25D;
/* 601 */     double d1 = 6.25D;
/*     */     
/* 603 */     for (int j = -1; j <= 32; j++) {
/* 604 */       for (int k = -4; k <= 4; k++) {
/* 605 */         for (int l = -4; l <= 4; l++) {
/* 606 */           double d2 = (k * k + l * l);
/*     */           
/* 608 */           if (d2 <= 12.25D) {
/* 609 */             BlockPos blockpos = pos.add(k, j, l);
/*     */             
/* 611 */             if (j < 0) {
/* 612 */               if (d2 <= 6.25D) {
/* 613 */                 this.worldObj.setBlockState(blockpos, Blocks.bedrock.getDefaultState());
/*     */               }
/* 615 */             } else if (j > 0) {
/* 616 */               this.worldObj.setBlockState(blockpos, Blocks.air.getDefaultState());
/* 617 */             } else if (d2 > 6.25D) {
/* 618 */               this.worldObj.setBlockState(blockpos, Blocks.bedrock.getDefaultState());
/*     */             } else {
/* 620 */               this.worldObj.setBlockState(blockpos, Blocks.end_portal.getDefaultState());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 627 */     this.worldObj.setBlockState(pos, Blocks.bedrock.getDefaultState());
/* 628 */     this.worldObj.setBlockState(pos.up(), Blocks.bedrock.getDefaultState());
/* 629 */     BlockPos blockpos1 = pos.up(2);
/* 630 */     this.worldObj.setBlockState(blockpos1, Blocks.bedrock.getDefaultState());
/* 631 */     this.worldObj.setBlockState(blockpos1.west(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.EAST));
/* 632 */     this.worldObj.setBlockState(blockpos1.east(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.WEST));
/* 633 */     this.worldObj.setBlockState(blockpos1.north(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.SOUTH));
/* 634 */     this.worldObj.setBlockState(blockpos1.south(), Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.NORTH));
/* 635 */     this.worldObj.setBlockState(pos.up(3), Blocks.bedrock.getDefaultState());
/* 636 */     this.worldObj.setBlockState(pos.up(4), Blocks.dragon_egg.getDefaultState());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void despawnEntity() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity[] getParts() {
/* 649 */     return (Entity[])this.dragonPartArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 656 */     return false;
/*     */   }
/*     */   
/*     */   public World getWorld() {
/* 660 */     return this.worldObj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 667 */     return "mob.enderdragon.growl";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 674 */     return "mob.enderdragon.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 681 */     return 5.0F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\boss\EntityDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */