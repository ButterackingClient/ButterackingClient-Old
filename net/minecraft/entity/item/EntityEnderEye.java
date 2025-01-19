/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
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
/*     */ public class EntityEnderEye
/*     */   extends Entity
/*     */ {
/*     */   private double targetX;
/*     */   private double targetY;
/*     */   private double targetZ;
/*     */   private int despawnTimer;
/*     */   private boolean shatterOrDrop;
/*     */   
/*     */   public EntityEnderEye(World worldIn) {
/*  31 */     super(worldIn);
/*  32 */     setSize(0.25F, 0.25F);
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
/*  43 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  45 */     if (Double.isNaN(d0)) {
/*  46 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  49 */     d0 *= 64.0D;
/*  50 */     return (distance < d0 * d0);
/*     */   }
/*     */   
/*     */   public EntityEnderEye(World worldIn, double x, double y, double z) {
/*  54 */     super(worldIn);
/*  55 */     this.despawnTimer = 0;
/*  56 */     setSize(0.25F, 0.25F);
/*  57 */     setPosition(x, y, z);
/*     */   }
/*     */   
/*     */   public void moveTowards(BlockPos p_180465_1_) {
/*  61 */     double d0 = p_180465_1_.getX();
/*  62 */     int i = p_180465_1_.getY();
/*  63 */     double d1 = p_180465_1_.getZ();
/*  64 */     double d2 = d0 - this.posX;
/*  65 */     double d3 = d1 - this.posZ;
/*  66 */     float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);
/*     */     
/*  68 */     if (f > 12.0F) {
/*  69 */       this.targetX = this.posX + d2 / f * 12.0D;
/*  70 */       this.targetZ = this.posZ + d3 / f * 12.0D;
/*  71 */       this.targetY = this.posY + 8.0D;
/*     */     } else {
/*  73 */       this.targetX = d0;
/*  74 */       this.targetY = i;
/*  75 */       this.targetZ = d1;
/*     */     } 
/*     */     
/*  78 */     this.despawnTimer = 0;
/*  79 */     this.shatterOrDrop = (this.rand.nextInt(5) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/*  86 */     this.motionX = x;
/*  87 */     this.motionY = y;
/*  88 */     this.motionZ = z;
/*     */     
/*  90 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*  91 */       float f = MathHelper.sqrt_double(x * x + z * z);
/*  92 */       this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * 180.0D / Math.PI);
/*  93 */       this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, f) * 180.0D / Math.PI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 101 */     this.lastTickPosX = this.posX;
/* 102 */     this.lastTickPosY = this.posY;
/* 103 */     this.lastTickPosZ = this.posZ;
/* 104 */     super.onUpdate();
/* 105 */     this.posX += this.motionX;
/* 106 */     this.posY += this.motionY;
/* 107 */     this.posZ += this.motionZ;
/* 108 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 109 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
/*     */     
/* 111 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 115 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
/* 116 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 119 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
/* 120 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 123 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
/* 124 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 127 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 128 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/*     */     
/* 130 */     if (!this.worldObj.isRemote) {
/* 131 */       double d0 = this.targetX - this.posX;
/* 132 */       double d1 = this.targetZ - this.posZ;
/* 133 */       float f1 = (float)Math.sqrt(d0 * d0 + d1 * d1);
/* 134 */       float f2 = (float)MathHelper.atan2(d1, d0);
/* 135 */       double d2 = f + (f1 - f) * 0.0025D;
/*     */       
/* 137 */       if (f1 < 1.0F) {
/* 138 */         d2 *= 0.8D;
/* 139 */         this.motionY *= 0.8D;
/*     */       } 
/*     */       
/* 142 */       this.motionX = Math.cos(f2) * d2;
/* 143 */       this.motionZ = Math.sin(f2) * d2;
/*     */       
/* 145 */       if (this.posY < this.targetY) {
/* 146 */         this.motionY += (1.0D - this.motionY) * 0.014999999664723873D;
/*     */       } else {
/* 148 */         this.motionY += (-1.0D - this.motionY) * 0.014999999664723873D;
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     float f3 = 0.25F;
/*     */     
/* 154 */     if (isInWater()) {
/* 155 */       for (int i = 0; i < 4; i++) {
/* 156 */         this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */       }
/*     */     } else {
/* 159 */       this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX - this.motionX * f3 + this.rand.nextDouble() * 0.6D - 0.3D, this.posY - this.motionY * f3 - 0.5D, this.posZ - this.motionZ * f3 + this.rand.nextDouble() * 0.6D - 0.3D, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */     } 
/*     */     
/* 162 */     if (!this.worldObj.isRemote) {
/* 163 */       setPosition(this.posX, this.posY, this.posZ);
/* 164 */       this.despawnTimer++;
/*     */       
/* 166 */       if (this.despawnTimer > 80 && !this.worldObj.isRemote) {
/* 167 */         setDead();
/*     */         
/* 169 */         if (this.shatterOrDrop) {
/* 170 */           this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.ender_eye)));
/*     */         } else {
/* 172 */           this.worldObj.playAuxSFX(2003, new BlockPos(this), 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/* 194 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/* 198 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackWithItem() {
/* 205 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityEnderEye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */