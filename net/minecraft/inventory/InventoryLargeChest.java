/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.LockCode;
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
/*     */ public class InventoryLargeChest
/*     */   implements ILockableContainer
/*     */ {
/*     */   private String name;
/*     */   private ILockableContainer upperChest;
/*     */   private ILockableContainer lowerChest;
/*     */   
/*     */   public InventoryLargeChest(String nameIn, ILockableContainer upperChestIn, ILockableContainer lowerChestIn) {
/*  29 */     this.name = nameIn;
/*     */     
/*  31 */     if (upperChestIn == null) {
/*  32 */       upperChestIn = lowerChestIn;
/*     */     }
/*     */     
/*  35 */     if (lowerChestIn == null) {
/*  36 */       lowerChestIn = upperChestIn;
/*     */     }
/*     */     
/*  39 */     this.upperChest = upperChestIn;
/*  40 */     this.lowerChest = lowerChestIn;
/*     */     
/*  42 */     if (upperChestIn.isLocked()) {
/*  43 */       lowerChestIn.setLockCode(upperChestIn.getLockCode());
/*  44 */     } else if (lowerChestIn.isLocked()) {
/*  45 */       upperChestIn.setLockCode(lowerChestIn.getLockCode());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  53 */     return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPartOfLargeChest(IInventory inventoryIn) {
/*  60 */     return !(this.upperChest != inventoryIn && this.lowerChest != inventoryIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  67 */     return this.upperChest.hasCustomName() ? this.upperChest.getName() : (this.lowerChest.hasCustomName() ? this.lowerChest.getName() : this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  74 */     return !(!this.upperChest.hasCustomName() && !this.lowerChest.hasCustomName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/*  81 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  88 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  95 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.decrStackSize(index - this.upperChest.getSizeInventory(), count) : this.upperChest.decrStackSize(index, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 102 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.removeStackFromSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.removeStackFromSlot(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 109 */     if (index >= this.upperChest.getSizeInventory()) {
/* 110 */       this.lowerChest.setInventorySlotContents(index - this.upperChest.getSizeInventory(), stack);
/*     */     } else {
/* 112 */       this.upperChest.setInventorySlotContents(index, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 120 */     return this.upperChest.getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 128 */     this.upperChest.markDirty();
/* 129 */     this.lowerChest.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 136 */     return (this.upperChest.isUseableByPlayer(player) && this.lowerChest.isUseableByPlayer(player));
/*     */   }
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 140 */     this.upperChest.openInventory(player);
/* 141 */     this.lowerChest.openInventory(player);
/*     */   }
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 145 */     this.upperChest.closeInventory(player);
/* 146 */     this.lowerChest.closeInventory(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 157 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 164 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isLocked() {
/* 168 */     return !(!this.upperChest.isLocked() && !this.lowerChest.isLocked());
/*     */   }
/*     */   
/*     */   public void setLockCode(LockCode code) {
/* 172 */     this.upperChest.setLockCode(code);
/* 173 */     this.lowerChest.setLockCode(code);
/*     */   }
/*     */   
/*     */   public LockCode getLockCode() {
/* 177 */     return this.upperChest.getLockCode();
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 181 */     return this.upperChest.getGuiID();
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 185 */     return new ContainerChest((IInventory)playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 189 */     this.upperChest.clear();
/* 190 */     this.lowerChest.clear();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\InventoryLargeChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */