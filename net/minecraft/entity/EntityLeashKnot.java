/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityLeashKnot
/*     */   extends EntityHanging {
/*     */   public EntityLeashKnot(World worldIn) {
/*  15 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityLeashKnot(World worldIn, BlockPos hangingPositionIn) {
/*  19 */     super(worldIn, hangingPositionIn);
/*  20 */     setPosition(hangingPositionIn.getX() + 0.5D, hangingPositionIn.getY() + 0.5D, hangingPositionIn.getZ() + 0.5D);
/*  21 */     float f = 0.125F;
/*  22 */     float f1 = 0.1875F;
/*  23 */     float f2 = 0.25F;
/*  24 */     setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875D, this.posY - 0.25D + 0.125D, this.posZ - 0.1875D, this.posX + 0.1875D, this.posY + 0.25D + 0.125D, this.posZ + 0.1875D));
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  28 */     super.entityInit();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateFacingWithBoundingBox(EnumFacing facingDirectionIn) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/*  38 */     return 9;
/*     */   }
/*     */   
/*     */   public int getHeightPixels() {
/*  42 */     return 9;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/*  46 */     return -0.0625F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  54 */     return (distance < 1024.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(Entity brokenEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
/*  69 */     return false;
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
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/*  88 */     ItemStack itemstack = playerIn.getHeldItem();
/*  89 */     boolean flag = false;
/*     */     
/*  91 */     if (itemstack != null && itemstack.getItem() == Items.lead && !this.worldObj.isRemote) {
/*  92 */       double d0 = 7.0D;
/*     */       
/*  94 */       for (EntityLiving entityliving : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + d0, this.posZ + d0))) {
/*  95 */         if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == playerIn) {
/*  96 */           entityliving.setLeashedToEntity(this, true);
/*  97 */           flag = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     if (!this.worldObj.isRemote && !flag) {
/* 103 */       setDead();
/*     */       
/* 105 */       if (playerIn.capabilities.isCreativeMode) {
/* 106 */         double d1 = 7.0D;
/*     */         
/* 108 */         for (EntityLiving entityliving1 : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - d1, this.posY - d1, this.posZ - d1, this.posX + d1, this.posY + d1, this.posZ + d1))) {
/* 109 */           if (entityliving1.getLeashed() && entityliving1.getLeashedToEntity() == this) {
/* 110 */             entityliving1.clearLeashed(true, false);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onValidSurface() {
/* 123 */     return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof net.minecraft.block.BlockFence;
/*     */   }
/*     */   
/*     */   public static EntityLeashKnot createKnot(World worldIn, BlockPos fence) {
/* 127 */     EntityLeashKnot entityleashknot = new EntityLeashKnot(worldIn, fence);
/* 128 */     entityleashknot.forceSpawn = true;
/* 129 */     worldIn.spawnEntityInWorld(entityleashknot);
/* 130 */     return entityleashknot;
/*     */   }
/*     */   
/*     */   public static EntityLeashKnot getKnotForPosition(World worldIn, BlockPos pos) {
/* 134 */     int i = pos.getX();
/* 135 */     int j = pos.getY();
/* 136 */     int k = pos.getZ();
/*     */     
/* 138 */     for (EntityLeashKnot entityleashknot : worldIn.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB(i - 1.0D, j - 1.0D, k - 1.0D, i + 1.0D, j + 1.0D, k + 1.0D))) {
/* 139 */       if (entityleashknot.getHangingPosition().equals(pos)) {
/* 140 */         return entityleashknot;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityLeashKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */