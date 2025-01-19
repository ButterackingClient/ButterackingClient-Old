/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockRedstoneDiode;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityHanging
/*     */   extends Entity
/*     */ {
/*     */   private int tickCounter1;
/*     */   protected BlockPos hangingPosition;
/*     */   public EnumFacing facingDirection;
/*     */   
/*     */   public EntityHanging(World worldIn) {
/*  24 */     super(worldIn);
/*  25 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityHanging(World worldIn, BlockPos hangingPositionIn) {
/*  29 */     this(worldIn);
/*  30 */     this.hangingPosition = hangingPositionIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {
/*  40 */     Validate.notNull(facingDirectionIn);
/*  41 */     Validate.isTrue(facingDirectionIn.getAxis().isHorizontal());
/*  42 */     this.facingDirection = facingDirectionIn;
/*  43 */     this.prevRotationYaw = this.rotationYaw = (this.facingDirection.getHorizontalIndex() * 90);
/*  44 */     updateBoundingBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateBoundingBox() {
/*  51 */     if (this.facingDirection != null) {
/*  52 */       double d0 = this.hangingPosition.getX() + 0.5D;
/*  53 */       double d1 = this.hangingPosition.getY() + 0.5D;
/*  54 */       double d2 = this.hangingPosition.getZ() + 0.5D;
/*  55 */       double d3 = 0.46875D;
/*  56 */       double d4 = func_174858_a(getWidthPixels());
/*  57 */       double d5 = func_174858_a(getHeightPixels());
/*  58 */       d0 -= this.facingDirection.getFrontOffsetX() * 0.46875D;
/*  59 */       d2 -= this.facingDirection.getFrontOffsetZ() * 0.46875D;
/*  60 */       d1 += d5;
/*  61 */       EnumFacing enumfacing = this.facingDirection.rotateYCCW();
/*  62 */       d0 += d4 * enumfacing.getFrontOffsetX();
/*  63 */       d2 += d4 * enumfacing.getFrontOffsetZ();
/*  64 */       this.posX = d0;
/*  65 */       this.posY = d1;
/*  66 */       this.posZ = d2;
/*  67 */       double d6 = getWidthPixels();
/*  68 */       double d7 = getHeightPixels();
/*  69 */       double d8 = getWidthPixels();
/*     */       
/*  71 */       if (this.facingDirection.getAxis() == EnumFacing.Axis.Z) {
/*  72 */         d8 = 1.0D;
/*     */       } else {
/*  74 */         d6 = 1.0D;
/*     */       } 
/*     */       
/*  77 */       d6 /= 32.0D;
/*  78 */       d7 /= 32.0D;
/*  79 */       d8 /= 32.0D;
/*  80 */       setEntityBoundingBox(new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
/*     */     } 
/*     */   }
/*     */   
/*     */   private double func_174858_a(int p_174858_1_) {
/*  85 */     return (p_174858_1_ % 32 == 0) ? 0.5D : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  92 */     this.prevPosX = this.posX;
/*  93 */     this.prevPosY = this.posY;
/*  94 */     this.prevPosZ = this.posZ;
/*     */     
/*  96 */     if (this.tickCounter1++ == 100 && !this.worldObj.isRemote) {
/*  97 */       this.tickCounter1 = 0;
/*     */       
/*  99 */       if (!this.isDead && !onValidSurface()) {
/* 100 */         setDead();
/* 101 */         onBroken((Entity)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onValidSurface() {
/* 110 */     if (!this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) {
/* 111 */       return false;
/*     */     }
/* 113 */     int i = Math.max(1, getWidthPixels() / 16);
/* 114 */     int j = Math.max(1, getHeightPixels() / 16);
/* 115 */     BlockPos blockpos = this.hangingPosition.offset(this.facingDirection.getOpposite());
/* 116 */     EnumFacing enumfacing = this.facingDirection.rotateYCCW();
/*     */     
/* 118 */     for (int k = 0; k < i; k++) {
/* 119 */       for (int l = 0; l < j; l++) {
/* 120 */         BlockPos blockpos1 = blockpos.offset(enumfacing, k).up(l);
/* 121 */         Block block = this.worldObj.getBlockState(blockpos1).getBlock();
/*     */         
/* 123 */         if (!block.getMaterial().isSolid() && !BlockRedstoneDiode.isRedstoneRepeaterBlockID(block)) {
/* 124 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     for (Entity entity : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox())) {
/* 130 */       if (entity instanceof EntityHanging) {
/* 131 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hitByEntity(Entity entityIn) {
/* 150 */     return (entityIn instanceof EntityPlayer) ? attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityIn), 0.0F) : false;
/*     */   }
/*     */   
/*     */   public EnumFacing getHorizontalFacing() {
/* 154 */     return this.facingDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 161 */     if (isEntityInvulnerable(source)) {
/* 162 */       return false;
/*     */     }
/* 164 */     if (!this.isDead && !this.worldObj.isRemote) {
/* 165 */       setDead();
/* 166 */       setBeenAttacked();
/* 167 */       onBroken(source.getEntity());
/*     */     } 
/*     */     
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(double x, double y, double z) {
/* 178 */     if (!this.worldObj.isRemote && !this.isDead && x * x + y * y + z * z > 0.0D) {
/* 179 */       setDead();
/* 180 */       onBroken((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVelocity(double x, double y, double z) {
/* 188 */     if (!this.worldObj.isRemote && !this.isDead && x * x + y * y + z * z > 0.0D) {
/* 189 */       setDead();
/* 190 */       onBroken((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 198 */     tagCompound.setByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
/* 199 */     tagCompound.setInteger("TileX", getHangingPosition().getX());
/* 200 */     tagCompound.setInteger("TileY", getHangingPosition().getY());
/* 201 */     tagCompound.setInteger("TileZ", getHangingPosition().getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*     */     EnumFacing enumfacing;
/* 208 */     this.hangingPosition = new BlockPos(tagCompund.getInteger("TileX"), tagCompund.getInteger("TileY"), tagCompund.getInteger("TileZ"));
/*     */ 
/*     */     
/* 211 */     if (tagCompund.hasKey("Direction", 99)) {
/* 212 */       enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Direction"));
/* 213 */       this.hangingPosition = this.hangingPosition.offset(enumfacing);
/* 214 */     } else if (tagCompund.hasKey("Facing", 99)) {
/* 215 */       enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Facing"));
/*     */     } else {
/* 217 */       enumfacing = EnumFacing.getHorizontal(tagCompund.getByte("Dir"));
/*     */     } 
/*     */     
/* 220 */     updateFacingWithBoundingBox(enumfacing);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int getWidthPixels();
/*     */ 
/*     */   
/*     */   public abstract int getHeightPixels();
/*     */ 
/*     */   
/*     */   public abstract void onBroken(Entity paramEntity);
/*     */   
/*     */   protected boolean shouldSetPosAfterLoading() {
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(double x, double y, double z) {
/* 240 */     this.posX = x;
/* 241 */     this.posY = y;
/* 242 */     this.posZ = z;
/* 243 */     BlockPos blockpos = this.hangingPosition;
/* 244 */     this.hangingPosition = new BlockPos(x, y, z);
/*     */     
/* 246 */     if (!this.hangingPosition.equals(blockpos)) {
/* 247 */       updateBoundingBox();
/* 248 */       this.isAirBorne = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public BlockPos getHangingPosition() {
/* 253 */     return this.hangingPosition;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityHanging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */