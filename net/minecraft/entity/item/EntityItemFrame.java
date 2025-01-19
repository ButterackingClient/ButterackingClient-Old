/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class EntityItemFrame
/*     */   extends EntityHanging
/*     */ {
/*  21 */   private float itemDropChance = 1.0F;
/*     */   
/*     */   public EntityItemFrame(World worldIn) {
/*  24 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityItemFrame(World worldIn, BlockPos p_i45852_2_, EnumFacing p_i45852_3_) {
/*  28 */     super(worldIn, p_i45852_2_);
/*  29 */     updateFacingWithBoundingBox(p_i45852_3_);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  33 */     getDataWatcher().addObjectByDataType(8, 5);
/*  34 */     getDataWatcher().addObject(9, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public float getCollisionBorderSize() {
/*  38 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  45 */     if (isEntityInvulnerable(source))
/*  46 */       return false; 
/*  47 */     if (!source.isExplosion() && getDisplayedItem() != null) {
/*  48 */       if (!this.worldObj.isRemote) {
/*  49 */         dropItemOrSelf(source.getEntity(), false);
/*  50 */         setDisplayedItem((ItemStack)null);
/*     */       } 
/*     */       
/*  53 */       return true;
/*     */     } 
/*  55 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/*  60 */     return 12;
/*     */   }
/*     */   
/*     */   public int getHeightPixels() {
/*  64 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  72 */     double d0 = 16.0D;
/*  73 */     d0 = d0 * 64.0D * this.renderDistanceWeight;
/*  74 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(Entity brokenEntity) {
/*  81 */     dropItemOrSelf(brokenEntity, true);
/*     */   }
/*     */   
/*     */   public void dropItemOrSelf(Entity p_146065_1_, boolean p_146065_2_) {
/*  85 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*  86 */       ItemStack itemstack = getDisplayedItem();
/*     */       
/*  88 */       if (p_146065_1_ instanceof EntityPlayer) {
/*  89 */         EntityPlayer entityplayer = (EntityPlayer)p_146065_1_;
/*     */         
/*  91 */         if (entityplayer.capabilities.isCreativeMode) {
/*  92 */           removeFrameFromMap(itemstack);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  97 */       if (p_146065_2_) {
/*  98 */         entityDropItem(new ItemStack(Items.item_frame), 0.0F);
/*     */       }
/*     */       
/* 101 */       if (itemstack != null && this.rand.nextFloat() < this.itemDropChance) {
/* 102 */         itemstack = itemstack.copy();
/* 103 */         removeFrameFromMap(itemstack);
/* 104 */         entityDropItem(itemstack, 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeFrameFromMap(ItemStack p_110131_1_) {
/* 113 */     if (p_110131_1_ != null) {
/* 114 */       if (p_110131_1_.getItem() == Items.filled_map) {
/* 115 */         MapData mapdata = ((ItemMap)p_110131_1_.getItem()).getMapData(p_110131_1_, this.worldObj);
/* 116 */         mapdata.mapDecorations.remove("frame-" + getEntityId());
/*     */       } 
/*     */       
/* 119 */       p_110131_1_.setItemFrame(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack getDisplayedItem() {
/* 124 */     return getDataWatcher().getWatchableObjectItemStack(8);
/*     */   }
/*     */   
/*     */   public void setDisplayedItem(ItemStack p_82334_1_) {
/* 128 */     setDisplayedItemWithUpdate(p_82334_1_, true);
/*     */   }
/*     */   
/*     */   private void setDisplayedItemWithUpdate(ItemStack p_174864_1_, boolean p_174864_2_) {
/* 132 */     if (p_174864_1_ != null) {
/* 133 */       p_174864_1_ = p_174864_1_.copy();
/* 134 */       p_174864_1_.stackSize = 1;
/* 135 */       p_174864_1_.setItemFrame(this);
/*     */     } 
/*     */     
/* 138 */     getDataWatcher().updateObject(8, p_174864_1_);
/* 139 */     getDataWatcher().setObjectWatched(8);
/*     */     
/* 141 */     if (p_174864_2_ && this.hangingPosition != null) {
/* 142 */       this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRotation() {
/* 150 */     return getDataWatcher().getWatchableObjectByte(9);
/*     */   }
/*     */   
/*     */   public void setItemRotation(int p_82336_1_) {
/* 154 */     func_174865_a(p_82336_1_, true);
/*     */   }
/*     */   
/*     */   private void func_174865_a(int p_174865_1_, boolean p_174865_2_) {
/* 158 */     getDataWatcher().updateObject(9, Byte.valueOf((byte)(p_174865_1_ % 8)));
/*     */     
/* 160 */     if (p_174865_2_ && this.hangingPosition != null) {
/* 161 */       this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 169 */     if (getDisplayedItem() != null) {
/* 170 */       tagCompound.setTag("Item", (NBTBase)getDisplayedItem().writeToNBT(new NBTTagCompound()));
/* 171 */       tagCompound.setByte("ItemRotation", (byte)getRotation());
/* 172 */       tagCompound.setFloat("ItemDropChance", this.itemDropChance);
/*     */     } 
/*     */     
/* 175 */     super.writeEntityToNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 182 */     NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Item");
/*     */     
/* 184 */     if (nbttagcompound != null && !nbttagcompound.hasNoTags()) {
/* 185 */       setDisplayedItemWithUpdate(ItemStack.loadItemStackFromNBT(nbttagcompound), false);
/* 186 */       func_174865_a(tagCompund.getByte("ItemRotation"), false);
/*     */       
/* 188 */       if (tagCompund.hasKey("ItemDropChance", 99)) {
/* 189 */         this.itemDropChance = tagCompund.getFloat("ItemDropChance");
/*     */       }
/*     */       
/* 192 */       if (tagCompund.hasKey("Direction")) {
/* 193 */         func_174865_a(getRotation() * 2, false);
/*     */       }
/*     */     } 
/*     */     
/* 197 */     super.readEntityFromNBT(tagCompund);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 204 */     if (getDisplayedItem() == null) {
/* 205 */       ItemStack itemstack = playerIn.getHeldItem();
/*     */       
/* 207 */       if (itemstack != null && !this.worldObj.isRemote) {
/* 208 */         setDisplayedItem(itemstack);
/*     */         
/* 210 */         if (!playerIn.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
/* 211 */           playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
/*     */         }
/*     */       } 
/* 214 */     } else if (!this.worldObj.isRemote) {
/* 215 */       setItemRotation(getRotation() + 1);
/*     */     } 
/*     */     
/* 218 */     return true;
/*     */   }
/*     */   
/*     */   public int func_174866_q() {
/* 222 */     return (getDisplayedItem() == null) ? 0 : (getRotation() % 8 + 1);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityItemFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */