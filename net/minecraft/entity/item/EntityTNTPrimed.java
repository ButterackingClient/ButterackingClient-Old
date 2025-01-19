/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class EntityTNTPrimed
/*     */   extends Entity
/*     */ {
/*     */   public int fuse;
/*     */   private EntityLivingBase tntPlacedBy;
/*     */   
/*     */   public EntityTNTPrimed(World worldIn) {
/*  17 */     super(worldIn);
/*  18 */     this.preventEntitySpawning = true;
/*  19 */     setSize(0.98F, 0.98F);
/*     */   }
/*     */   
/*     */   public EntityTNTPrimed(World worldIn, double x, double y, double z, EntityLivingBase igniter) {
/*  23 */     this(worldIn);
/*  24 */     setPosition(x, y, z);
/*  25 */     float f = (float)(Math.random() * Math.PI * 2.0D);
/*  26 */     this.motionX = (-((float)Math.sin(f)) * 0.02F);
/*  27 */     this.motionY = 0.20000000298023224D;
/*  28 */     this.motionZ = (-((float)Math.cos(f)) * 0.02F);
/*  29 */     this.fuse = 80;
/*  30 */     this.prevPosX = x;
/*  31 */     this.prevPosY = y;
/*  32 */     this.prevPosZ = z;
/*  33 */     this.tntPlacedBy = igniter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/*  51 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  58 */     this.prevPosX = this.posX;
/*  59 */     this.prevPosY = this.posY;
/*  60 */     this.prevPosZ = this.posZ;
/*  61 */     this.motionY -= 0.03999999910593033D;
/*  62 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  63 */     this.motionX *= 0.9800000190734863D;
/*  64 */     this.motionY *= 0.9800000190734863D;
/*  65 */     this.motionZ *= 0.9800000190734863D;
/*     */     
/*  67 */     if (this.onGround) {
/*  68 */       this.motionX *= 0.699999988079071D;
/*  69 */       this.motionZ *= 0.699999988079071D;
/*  70 */       this.motionY *= -0.5D;
/*     */     } 
/*     */     
/*  73 */     if (this.fuse-- <= 0) {
/*  74 */       setDead();
/*     */       
/*  76 */       if (!this.worldObj.isRemote) {
/*  77 */         explode();
/*     */       }
/*     */     } else {
/*  80 */       handleWaterMovement();
/*  81 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void explode() {
/*  86 */     float f = 4.0F;
/*  87 */     this.worldObj.createExplosion(this, this.posX, this.posY + (this.height / 16.0F), this.posZ, f, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  94 */     tagCompound.setByte("Fuse", (byte)this.fuse);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 101 */     this.fuse = tagCompund.getByte("Fuse");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getTntPlacedBy() {
/* 108 */     return this.tntPlacedBy;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 112 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityTNTPrimed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */