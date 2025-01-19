/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.block.BlockFurnace;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartFurnace
/*     */   extends EntityMinecart {
/*     */   private int fuel;
/*     */   
/*     */   public EntityMinecartFurnace(World worldIn) {
/*  23 */     super(worldIn);
/*     */   }
/*     */   public double pushX; public double pushZ;
/*     */   public EntityMinecartFurnace(World worldIn, double x, double y, double z) {
/*  27 */     super(worldIn, x, y, z);
/*     */   }
/*     */   
/*     */   public EntityMinecart.EnumMinecartType getMinecartType() {
/*  31 */     return EntityMinecart.EnumMinecartType.FURNACE;
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  35 */     super.entityInit();
/*  36 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  43 */     super.onUpdate();
/*     */     
/*  45 */     if (this.fuel > 0) {
/*  46 */       this.fuel--;
/*     */     }
/*     */     
/*  49 */     if (this.fuel <= 0) {
/*  50 */       this.pushX = this.pushZ = 0.0D;
/*     */     }
/*     */     
/*  53 */     setMinecartPowered((this.fuel > 0));
/*     */     
/*  55 */     if (isMinecartPowered() && this.rand.nextInt(4) == 0) {
/*  56 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getMaximumSpeed() {
/*  64 */     return 0.2D;
/*     */   }
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  68 */     super.killMinecart(source);
/*     */     
/*  70 */     if (!source.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*  71 */       entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void func_180460_a(BlockPos p_180460_1_, IBlockState p_180460_2_) {
/*  76 */     super.func_180460_a(p_180460_1_, p_180460_2_);
/*  77 */     double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*     */     
/*  79 */     if (d0 > 1.0E-4D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D) {
/*  80 */       d0 = MathHelper.sqrt_double(d0);
/*  81 */       this.pushX /= d0;
/*  82 */       this.pushZ /= d0;
/*     */       
/*  84 */       if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D) {
/*  85 */         this.pushX = 0.0D;
/*  86 */         this.pushZ = 0.0D;
/*     */       } else {
/*  88 */         double d1 = d0 / getMaximumSpeed();
/*  89 */         this.pushX *= d1;
/*  90 */         this.pushZ *= d1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void applyDrag() {
/*  96 */     double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*     */     
/*  98 */     if (d0 > 1.0E-4D) {
/*  99 */       d0 = MathHelper.sqrt_double(d0);
/* 100 */       this.pushX /= d0;
/* 101 */       this.pushZ /= d0;
/* 102 */       double d1 = 1.0D;
/* 103 */       this.motionX *= 0.800000011920929D;
/* 104 */       this.motionY *= 0.0D;
/* 105 */       this.motionZ *= 0.800000011920929D;
/* 106 */       this.motionX += this.pushX * d1;
/* 107 */       this.motionZ += this.pushZ * d1;
/*     */     } else {
/* 109 */       this.motionX *= 0.9800000190734863D;
/* 110 */       this.motionY *= 0.0D;
/* 111 */       this.motionZ *= 0.9800000190734863D;
/*     */     } 
/*     */     
/* 114 */     super.applyDrag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 121 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*     */     
/* 123 */     if (itemstack != null && itemstack.getItem() == Items.coal) {
/* 124 */       if (!playerIn.capabilities.isCreativeMode && --itemstack.stackSize == 0) {
/* 125 */         playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
/*     */       }
/*     */       
/* 128 */       this.fuel += 3600;
/*     */     } 
/*     */     
/* 131 */     this.pushX = this.posX - playerIn.posX;
/* 132 */     this.pushZ = this.posZ - playerIn.posZ;
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 140 */     super.writeEntityToNBT(tagCompound);
/* 141 */     tagCompound.setDouble("PushX", this.pushX);
/* 142 */     tagCompound.setDouble("PushZ", this.pushZ);
/* 143 */     tagCompound.setShort("Fuel", (short)this.fuel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 150 */     super.readEntityFromNBT(tagCompund);
/* 151 */     this.pushX = tagCompund.getDouble("PushX");
/* 152 */     this.pushZ = tagCompund.getDouble("PushZ");
/* 153 */     this.fuel = tagCompund.getShort("Fuel");
/*     */   }
/*     */   
/*     */   protected boolean isMinecartPowered() {
/* 157 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   protected void setMinecartPowered(boolean p_94107_1_) {
/* 161 */     if (p_94107_1_) {
/* 162 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) | 0x1)));
/*     */     } else {
/* 164 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(this.dataWatcher.getWatchableObjectByte(16) & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/* 169 */     return (isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace).getDefaultState().withProperty((IProperty)BlockFurnace.FACING, (Comparable)EnumFacing.NORTH);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityMinecartFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */