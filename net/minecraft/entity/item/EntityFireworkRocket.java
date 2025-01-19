/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityFireworkRocket
/*     */   extends Entity
/*     */ {
/*     */   private int fireworkAge;
/*     */   private int lifetime;
/*     */   
/*     */   public EntityFireworkRocket(World worldIn) {
/*  22 */     super(worldIn);
/*  23 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  27 */     this.dataWatcher.addObjectByDataType(8, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  35 */     return (distance < 4096.0D);
/*     */   }
/*     */   
/*     */   public EntityFireworkRocket(World worldIn, double x, double y, double z, ItemStack givenItem) {
/*  39 */     super(worldIn);
/*  40 */     this.fireworkAge = 0;
/*  41 */     setSize(0.25F, 0.25F);
/*  42 */     setPosition(x, y, z);
/*  43 */     int i = 1;
/*     */     
/*  45 */     if (givenItem != null && givenItem.hasTagCompound()) {
/*  46 */       this.dataWatcher.updateObject(8, givenItem);
/*  47 */       NBTTagCompound nbttagcompound = givenItem.getTagCompound();
/*  48 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Fireworks");
/*     */       
/*  50 */       if (nbttagcompound1 != null) {
/*  51 */         i += nbttagcompound1.getByte("Flight");
/*     */       }
/*     */     } 
/*     */     
/*  55 */     this.motionX = this.rand.nextGaussian() * 0.001D;
/*  56 */     this.motionZ = this.rand.nextGaussian() * 0.001D;
/*  57 */     this.motionY = 0.05D;
/*  58 */     this.lifetime = 10 * i + this.rand.nextInt(6) + this.rand.nextInt(7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/*  65 */     this.motionX = x;
/*  66 */     this.motionY = y;
/*  67 */     this.motionZ = z;
/*     */     
/*  69 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*  70 */       float f = MathHelper.sqrt_double(x * x + z * z);
/*  71 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/*  72 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f) * 180.0D / Math.PI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  80 */     this.lastTickPosX = this.posX;
/*  81 */     this.lastTickPosY = this.posY;
/*  82 */     this.lastTickPosZ = this.posZ;
/*  83 */     super.onUpdate();
/*  84 */     this.motionX *= 1.15D;
/*  85 */     this.motionZ *= 1.15D;
/*  86 */     this.motionY += 0.04D;
/*  87 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  88 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  89 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */     
/*  91 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/*  95 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/*  96 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/*  99 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 100 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 103 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 104 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 107 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 108 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/*     */     
/* 110 */     if (this.fireworkAge == 0 && !isSilent()) {
/* 111 */       this.worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
/*     */     }
/*     */     
/* 114 */     this.fireworkAge++;
/*     */     
/* 116 */     if (this.worldObj.isRemote && this.fireworkAge % 2 < 2) {
/* 117 */       this.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY - 0.3D, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D, new int[0]);
/*     */     }
/*     */     
/* 120 */     if (!this.worldObj.isRemote && this.fireworkAge > this.lifetime) {
/* 121 */       this.worldObj.setEntityState(this, (byte)17);
/* 122 */       setDead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 127 */     if (id == 17 && this.worldObj.isRemote) {
/* 128 */       ItemStack itemstack = this.dataWatcher.getWatchableObjectItemStack(8);
/* 129 */       NBTTagCompound nbttagcompound = null;
/*     */       
/* 131 */       if (itemstack != null && itemstack.hasTagCompound()) {
/* 132 */         nbttagcompound = itemstack.getTagCompound().getCompoundTag("Fireworks");
/*     */       }
/*     */       
/* 135 */       this.worldObj.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, nbttagcompound);
/*     */     } 
/*     */     
/* 138 */     super.handleStatusUpdate(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 145 */     tagCompound.setInteger("Life", this.fireworkAge);
/* 146 */     tagCompound.setInteger("LifeTime", this.lifetime);
/* 147 */     ItemStack itemstack = this.dataWatcher.getWatchableObjectItemStack(8);
/*     */     
/* 149 */     if (itemstack != null) {
/* 150 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 151 */       itemstack.writeToNBT(nbttagcompound);
/* 152 */       tagCompound.setTag("FireworksItem", (NBTBase)nbttagcompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 160 */     this.fireworkAge = tagCompund.getInteger("Life");
/* 161 */     this.lifetime = tagCompund.getInteger("LifeTime");
/* 162 */     NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("FireworksItem");
/*     */     
/* 164 */     if (nbttagcompound != null) {
/* 165 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       
/* 167 */       if (itemstack != null) {
/* 168 */         this.dataWatcher.updateObject(8, itemstack);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/* 177 */     return super.getBrightness(partialTicks);
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 181 */     return super.getBrightnessForRender(partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackWithItem() {
/* 188 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityFireworkRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */