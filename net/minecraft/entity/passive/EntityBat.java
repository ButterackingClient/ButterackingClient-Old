/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityBat
/*     */   extends EntityAmbientCreature
/*     */ {
/*     */   private BlockPos spawnPosition;
/*     */   
/*     */   public EntityBat(World worldIn) {
/*  22 */     super(worldIn);
/*  23 */     setSize(0.5F, 0.9F);
/*  24 */     setIsBatHanging(true);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  28 */     super.entityInit();
/*  29 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  36 */     return 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundPitch() {
/*  43 */     return super.getSoundPitch() * 0.95F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  50 */     return (getIsBatHanging() && this.rand.nextInt(4) != 0) ? null : "mob.bat.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  57 */     return "mob.bat.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  64 */     return "mob.bat.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {}
/*     */ 
/*     */   
/*     */   protected void collideWithNearbyEntities() {}
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  81 */     super.applyEntityAttributes();
/*  82 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
/*     */   }
/*     */   
/*     */   public boolean getIsBatHanging() {
/*  86 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setIsBatHanging(boolean isHanging) {
/*  90 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/*  92 */     if (isHanging) {
/*  93 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/*  95 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 103 */     super.onUpdate();
/*     */ 
/*     */     
/* 106 */     this.motionX = this.motionY = this.motionZ = 0.0D;
/* 107 */     this.posY = MathHelper.floor_double(this.posY) + 1.0D - this.height;
/*     */     
/* 109 */     this.motionY *= 0.6000000238418579D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 114 */     super.updateAITasks();
/* 115 */     BlockPos blockpos = new BlockPos((Entity)this);
/* 116 */     BlockPos blockpos1 = blockpos.up();
/*     */     
/* 118 */     if (getIsBatHanging()) {
/* 119 */       if (!this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube()) {
/* 120 */         setIsBatHanging(false);
/* 121 */         this.worldObj.playAuxSFXAtEntity(null, 1015, blockpos, 0);
/*     */       } else {
/* 123 */         if (this.rand.nextInt(200) == 0) {
/* 124 */           this.rotationYawHead = this.rand.nextInt(360);
/*     */         }
/*     */         
/* 127 */         if (this.worldObj.getClosestPlayerToEntity((Entity)this, 4.0D) != null) {
/* 128 */           setIsBatHanging(false);
/* 129 */           this.worldObj.playAuxSFXAtEntity(null, 1015, blockpos, 0);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 133 */       if (this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
/* 134 */         this.spawnPosition = null;
/*     */       }
/*     */       
/* 137 */       if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((int)this.posX, (int)this.posY, (int)this.posZ) < 4.0D) {
/* 138 */         this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
/*     */       }
/*     */       
/* 141 */       double d0 = this.spawnPosition.getX() + 0.5D - this.posX;
/* 142 */       double d1 = this.spawnPosition.getY() + 0.1D - this.posY;
/* 143 */       double d2 = this.spawnPosition.getZ() + 0.5D - this.posZ;
/* 144 */       this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
/* 145 */       this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
/* 146 */       this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
/* 147 */       float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
/* 148 */       float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
/* 149 */       this.moveForward = 0.5F;
/* 150 */       this.rotationYaw += f1;
/*     */       
/* 152 */       if (this.rand.nextInt(100) == 0 && this.worldObj.getBlockState(blockpos1).getBlock().isNormalCube()) {
/* 153 */         setIsBatHanging(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {}
/*     */ 
/*     */   
/*     */   public boolean doesEntityNotTriggerPressurePlate() {
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 183 */     if (isEntityInvulnerable(source)) {
/* 184 */       return false;
/*     */     }
/* 186 */     if (!this.worldObj.isRemote && getIsBatHanging()) {
/* 187 */       setIsBatHanging(false);
/*     */     }
/*     */     
/* 190 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 198 */     super.readEntityFromNBT(tagCompund);
/* 199 */     this.dataWatcher.updateObject(16, Byte.valueOf(tagCompund.getByte("BatFlags")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 206 */     super.writeEntityToNBT(tagCompound);
/* 207 */     tagCompound.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 214 */     BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */     
/* 216 */     if (blockpos.getY() >= this.worldObj.getSeaLevel()) {
/* 217 */       return false;
/*     */     }
/* 219 */     int i = this.worldObj.getLightFromNeighbors(blockpos);
/* 220 */     int j = 4;
/*     */     
/* 222 */     if (isDateAroundHalloween(this.worldObj.getCurrentDate())) {
/* 223 */       j = 7;
/* 224 */     } else if (this.rand.nextBoolean()) {
/* 225 */       return false;
/*     */     } 
/*     */     
/* 228 */     return (i > this.rand.nextInt(j)) ? false : super.getCanSpawnHere();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDateAroundHalloween(Calendar p_175569_1_) {
/* 233 */     return !((p_175569_1_.get(2) + 1 != 10 || p_175569_1_.get(5) < 20) && (p_175569_1_.get(2) + 1 != 11 || p_175569_1_.get(5) > 3));
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 237 */     return this.height / 2.0F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */