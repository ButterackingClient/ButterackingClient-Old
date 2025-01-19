/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Slot
/*     */ {
/*     */   private final int slotIndex;
/*     */   public final IInventory inventory;
/*     */   public int slotNumber;
/*     */   public int xDisplayPosition;
/*     */   public int yDisplayPosition;
/*     */   
/*     */   public Slot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
/*  33 */     this.inventory = inventoryIn;
/*  34 */     this.slotIndex = index;
/*  35 */     this.xDisplayPosition = xPosition;
/*  36 */     this.yDisplayPosition = yPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
/*  43 */     if (p_75220_1_ != null && p_75220_2_ != null && 
/*  44 */       p_75220_1_.getItem() == p_75220_2_.getItem()) {
/*  45 */       int i = p_75220_2_.stackSize - p_75220_1_.stackSize;
/*     */       
/*  47 */       if (i > 0) {
/*  48 */         onCrafting(p_75220_1_, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack, int amount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/*  68 */     onSlotChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValid(ItemStack stack) {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStack() {
/*  82 */     return this.inventory.getStackInSlot(this.slotIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHasStack() {
/*  89 */     return (getStack() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStack(ItemStack stack) {
/*  96 */     this.inventory.setInventorySlotContents(this.slotIndex, stack);
/*  97 */     onSlotChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSlotChanged() {
/* 104 */     this.inventory.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlotStackLimit() {
/* 112 */     return this.inventory.getInventoryStackLimit();
/*     */   }
/*     */   
/*     */   public int getItemStackLimit(ItemStack stack) {
/* 116 */     return getSlotStackLimit();
/*     */   }
/*     */   
/*     */   public String getSlotTexture() {
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int amount) {
/* 128 */     return this.inventory.decrStackSize(this.slotIndex, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHere(IInventory inv, int slotIn) {
/* 135 */     return (inv == this.inventory && slotIn == this.slotIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTakeStack(EntityPlayer playerIn) {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHovered() {
/* 150 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\Slot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */