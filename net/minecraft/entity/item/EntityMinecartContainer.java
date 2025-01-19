/*     */ package net.minecraft.entity.item;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.LockCode;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {
/*  15 */   private ItemStack[] minecartContainerItems = new ItemStack[36];
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dropContentsWhenDead = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World worldIn) {
/*  24 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityMinecartContainer(World worldIn, double x, double y, double z) {
/*  28 */     super(worldIn, x, y, z);
/*     */   }
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  32 */     super.killMinecart(source);
/*     */     
/*  34 */     if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*  35 */       InventoryHelper.dropInventoryItems(this.worldObj, this, (IInventory)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  43 */     return this.minecartContainerItems[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  50 */     if (this.minecartContainerItems[index] != null) {
/*  51 */       if ((this.minecartContainerItems[index]).stackSize <= count) {
/*  52 */         ItemStack itemstack1 = this.minecartContainerItems[index];
/*  53 */         this.minecartContainerItems[index] = null;
/*  54 */         return itemstack1;
/*     */       } 
/*  56 */       ItemStack itemstack = this.minecartContainerItems[index].splitStack(count);
/*     */       
/*  58 */       if ((this.minecartContainerItems[index]).stackSize == 0) {
/*  59 */         this.minecartContainerItems[index] = null;
/*     */       }
/*     */       
/*  62 */       return itemstack;
/*     */     } 
/*     */     
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  73 */     if (this.minecartContainerItems[index] != null) {
/*  74 */       ItemStack itemstack = this.minecartContainerItems[index];
/*  75 */       this.minecartContainerItems[index] = null;
/*  76 */       return itemstack;
/*     */     } 
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/*  86 */     this.minecartContainerItems[index] = stack;
/*     */     
/*  88 */     if (stack != null && stack.stackSize > getInventoryStackLimit()) {
/*  89 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 104 */     return this.isDead ? false : ((player.getDistanceSqToEntity(this) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 124 */     return hasCustomName() ? getCustomNameTag() : "container.minecart";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 131 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void travelToDimension(int dimensionId) {
/* 138 */     this.dropContentsWhenDead = false;
/* 139 */     super.travelToDimension(dimensionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 146 */     if (this.dropContentsWhenDead) {
/* 147 */       InventoryHelper.dropInventoryItems(this.worldObj, this, (IInventory)this);
/*     */     }
/*     */     
/* 150 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 157 */     super.writeEntityToNBT(tagCompound);
/* 158 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 160 */     for (int i = 0; i < this.minecartContainerItems.length; i++) {
/* 161 */       if (this.minecartContainerItems[i] != null) {
/* 162 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 163 */         nbttagcompound.setByte("Slot", (byte)i);
/* 164 */         this.minecartContainerItems[i].writeToNBT(nbttagcompound);
/* 165 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     tagCompound.setTag("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 176 */     super.readEntityFromNBT(tagCompund);
/* 177 */     NBTTagList nbttaglist = tagCompund.getTagList("Items", 10);
/* 178 */     this.minecartContainerItems = new ItemStack[getSizeInventory()];
/*     */     
/* 180 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 181 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 182 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 184 */       if (j >= 0 && j < this.minecartContainerItems.length) {
/* 185 */         this.minecartContainerItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactFirst(EntityPlayer playerIn) {
/* 194 */     if (!this.worldObj.isRemote) {
/* 195 */       playerIn.displayGUIChest((IInventory)this);
/*     */     }
/*     */     
/* 198 */     return true;
/*     */   }
/*     */   
/*     */   protected void applyDrag() {
/* 202 */     int i = 15 - Container.calcRedstoneFromInventory((IInventory)this);
/* 203 */     float f = 0.98F + i * 0.001F;
/* 204 */     this.motionX *= f;
/* 205 */     this.motionY *= 0.0D;
/* 206 */     this.motionZ *= f;
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 210 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 217 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isLocked() {
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLockCode(LockCode code) {}
/*     */   
/*     */   public LockCode getLockCode() {
/* 228 */     return LockCode.EMPTY_CODE;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 232 */     for (int i = 0; i < this.minecartContainerItems.length; i++)
/* 233 */       this.minecartContainerItems[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityMinecartContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */