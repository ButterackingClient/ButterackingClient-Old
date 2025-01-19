/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
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
/*     */ public class InventoryCrafting
/*     */   implements IInventory
/*     */ {
/*     */   private final ItemStack[] stackList;
/*     */   private final int inventoryWidth;
/*     */   private final int inventoryHeight;
/*     */   private final Container eventHandler;
/*     */   
/*     */   public InventoryCrafting(Container eventHandlerIn, int width, int height) {
/*  27 */     int i = width * height;
/*  28 */     this.stackList = new ItemStack[i];
/*  29 */     this.eventHandler = eventHandlerIn;
/*  30 */     this.inventoryWidth = width;
/*  31 */     this.inventoryHeight = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  38 */     return this.stackList.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  45 */     return (index >= getSizeInventory()) ? null : this.stackList[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInRowAndColumn(int row, int column) {
/*  52 */     return (row >= 0 && row < this.inventoryWidth && column >= 0 && column <= this.inventoryHeight) ? getStackInSlot(row + column * this.inventoryWidth) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  59 */     return "container.crafting";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/*  73 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  80 */     if (this.stackList[index] != null) {
/*  81 */       ItemStack itemstack = this.stackList[index];
/*  82 */       this.stackList[index] = null;
/*  83 */       return itemstack;
/*     */     } 
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  93 */     if (this.stackList[index] != null) {
/*  94 */       if ((this.stackList[index]).stackSize <= count) {
/*  95 */         ItemStack itemstack1 = this.stackList[index];
/*  96 */         this.stackList[index] = null;
/*  97 */         this.eventHandler.onCraftMatrixChanged(this);
/*  98 */         return itemstack1;
/*     */       } 
/* 100 */       ItemStack itemstack = this.stackList[index].splitStack(count);
/*     */       
/* 102 */       if ((this.stackList[index]).stackSize == 0) {
/* 103 */         this.stackList[index] = null;
/*     */       }
/*     */       
/* 106 */       this.eventHandler.onCraftMatrixChanged(this);
/* 107 */       return itemstack;
/*     */     } 
/*     */     
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 118 */     this.stackList[index] = stack;
/* 119 */     this.eventHandler.onCraftMatrixChanged(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 126 */     return 64;
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
/* 140 */     return true;
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
/*     */   public void clear() {
/* 168 */     for (int i = 0; i < this.stackList.length; i++) {
/* 169 */       this.stackList[i] = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 174 */     return this.inventoryHeight;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 178 */     return this.inventoryWidth;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\InventoryCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */