/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityXPOrb
/*     */   extends Entity
/*     */ {
/*     */   public int xpColor;
/*     */   public int xpOrbAge;
/*     */   public int delayBeforeCanPickup;
/*  27 */   private int xpOrbHealth = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   private int xpValue;
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityPlayer closestPlayer;
/*     */ 
/*     */ 
/*     */   
/*     */   private int xpTargetColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityXPOrb(World worldIn, double x, double y, double z, int expValue) {
/*  45 */     super(worldIn);
/*  46 */     setSize(0.5F, 0.5F);
/*  47 */     setPosition(x, y, z);
/*  48 */     this.rotationYaw = (float)(Math.random() * 360.0D);
/*  49 */     this.motionX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  50 */     this.motionY = ((float)(Math.random() * 0.2D) * 2.0F);
/*  51 */     this.motionZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  52 */     this.xpValue = expValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  60 */     return false;
/*     */   }
/*     */   
/*     */   public EntityXPOrb(World worldIn) {
/*  64 */     super(worldIn);
/*  65 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  72 */     float f = 0.5F;
/*  73 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/*  74 */     int i = super.getBrightnessForRender(partialTicks);
/*  75 */     int j = i & 0xFF;
/*  76 */     int k = i >> 16 & 0xFF;
/*  77 */     j += (int)(f * 15.0F * 16.0F);
/*     */     
/*  79 */     if (j > 240) {
/*  80 */       j = 240;
/*     */     }
/*     */     
/*  83 */     return j | k << 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  90 */     super.onUpdate();
/*     */     
/*  92 */     if (this.delayBeforeCanPickup > 0) {
/*  93 */       this.delayBeforeCanPickup--;
/*     */     }
/*     */     
/*  96 */     this.prevPosX = this.posX;
/*  97 */     this.prevPosY = this.posY;
/*  98 */     this.prevPosZ = this.posZ;
/*  99 */     this.motionY -= 0.029999999329447746D;
/*     */     
/* 101 */     if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
/* 102 */       this.motionY = 0.20000000298023224D;
/* 103 */       this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 104 */       this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 105 */       playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
/*     */     } 
/*     */     
/* 108 */     pushOutOfBlocks(this.posX, ((getEntityBoundingBox()).minY + (getEntityBoundingBox()).maxY) / 2.0D, this.posZ);
/* 109 */     double d0 = 8.0D;
/*     */     
/* 111 */     if (this.xpTargetColor < this.xpColor - 20 + getEntityId() % 100) {
/* 112 */       if (this.closestPlayer == null || this.closestPlayer.getDistanceSqToEntity(this) > d0 * d0) {
/* 113 */         this.closestPlayer = this.worldObj.getClosestPlayerToEntity(this, d0);
/*     */       }
/*     */       
/* 116 */       this.xpTargetColor = this.xpColor;
/*     */     } 
/*     */     
/* 119 */     if (this.closestPlayer != null && this.closestPlayer.isSpectator()) {
/* 120 */       this.closestPlayer = null;
/*     */     }
/*     */     
/* 123 */     if (this.closestPlayer != null) {
/* 124 */       double d1 = (this.closestPlayer.posX - this.posX) / d0;
/* 125 */       double d2 = (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / d0;
/* 126 */       double d3 = (this.closestPlayer.posZ - this.posZ) / d0;
/* 127 */       double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/* 128 */       double d5 = 1.0D - d4;
/*     */       
/* 130 */       if (d5 > 0.0D) {
/* 131 */         d5 *= d5;
/* 132 */         this.motionX += d1 / d4 * d5 * 0.1D;
/* 133 */         this.motionY += d2 / d4 * d5 * 0.1D;
/* 134 */         this.motionZ += d3 / d4 * d5 * 0.1D;
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 139 */     float f = 0.98F;
/*     */     
/* 141 */     if (this.onGround) {
/* 142 */       f = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.98F;
/*     */     }
/*     */     
/* 145 */     this.motionX *= f;
/* 146 */     this.motionY *= 0.9800000190734863D;
/* 147 */     this.motionZ *= f;
/*     */     
/* 149 */     if (this.onGround) {
/* 150 */       this.motionY *= -0.8999999761581421D;
/*     */     }
/*     */     
/* 153 */     this.xpColor++;
/* 154 */     this.xpOrbAge++;
/*     */     
/* 156 */     if (this.xpOrbAge >= 6000) {
/* 157 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleWaterMovement() {
/* 165 */     return this.worldObj.handleMaterialAcceleration(getEntityBoundingBox(), Material.water, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dealFireDamage(int amount) {
/* 173 */     attackEntityFrom(DamageSource.inFire, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 180 */     if (isEntityInvulnerable(source)) {
/* 181 */       return false;
/*     */     }
/* 183 */     setBeenAttacked();
/* 184 */     this.xpOrbHealth = (int)(this.xpOrbHealth - amount);
/*     */     
/* 186 */     if (this.xpOrbHealth <= 0) {
/* 187 */       setDead();
/*     */     }
/*     */     
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 198 */     tagCompound.setShort("Health", (short)(byte)this.xpOrbHealth);
/* 199 */     tagCompound.setShort("Age", (short)this.xpOrbAge);
/* 200 */     tagCompound.setShort("Value", (short)this.xpValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 207 */     this.xpOrbHealth = tagCompund.getShort("Health") & 0xFF;
/* 208 */     this.xpOrbAge = tagCompund.getShort("Age");
/* 209 */     this.xpValue = tagCompund.getShort("Value");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 216 */     if (!this.worldObj.isRemote && 
/* 217 */       this.delayBeforeCanPickup == 0 && entityIn.xpCooldown == 0) {
/* 218 */       entityIn.xpCooldown = 2;
/* 219 */       this.worldObj.playSoundAtEntity((Entity)entityIn, "random.orb", 0.1F, 0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
/* 220 */       entityIn.onItemPickup(this, 1);
/* 221 */       entityIn.addExperience(this.xpValue);
/* 222 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXpValue() {
/* 231 */     return this.xpValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureByXP() {
/* 239 */     return (this.xpValue >= 2477) ? 10 : ((this.xpValue >= 1237) ? 9 : ((this.xpValue >= 617) ? 8 : ((this.xpValue >= 307) ? 7 : ((this.xpValue >= 149) ? 6 : ((this.xpValue >= 73) ? 5 : ((this.xpValue >= 37) ? 4 : ((this.xpValue >= 17) ? 3 : ((this.xpValue >= 7) ? 2 : ((this.xpValue >= 3) ? 1 : 0)))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getXPSplit(int expValue) {
/* 246 */     return (expValue >= 2477) ? 2477 : ((expValue >= 1237) ? 1237 : ((expValue >= 617) ? 617 : ((expValue >= 307) ? 307 : ((expValue >= 149) ? 149 : ((expValue >= 73) ? 73 : ((expValue >= 37) ? 37 : ((expValue >= 17) ? 17 : ((expValue >= 7) ? 7 : ((expValue >= 3) ? 3 : 1)))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackWithItem() {
/* 253 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityXPOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */