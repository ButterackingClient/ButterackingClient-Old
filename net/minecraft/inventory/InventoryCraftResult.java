/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ public class InventoryCraftResult
/*     */   implements IInventory
/*     */ {
/*  13 */   private ItemStack[] stackResult = new ItemStack[1];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  19 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  26 */     return this.stackResult[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  33 */     return "Result";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/*  47 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  54 */     if (this.stackResult[0] != null) {
/*  55 */       ItemStack itemstack = this.stackResult[0];
/*  56 */       this.stackResult[0] = null;
/*  57 */       return itemstack;
/*     */     } 
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  67 */     if (this.stackResult[0] != null) {
/*  68 */       ItemStack itemstack = this.stackResult[0];
/*  69 */       this.stackResult[0] = null;
/*  70 */       return itemstack;
/*     */     } 
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/*  80 */     this.stackResult[0] = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/*  87 */     return 64;
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
/* 101 */     return true;
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
/* 114 */     return true;
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 118 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 125 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 129 */     for (int i = 0; i < this.stackResult.length; i++)
/* 130 */       this.stackResult[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\InventoryCraftResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */