/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerChest extends Container {
/*    */   private IInventory lowerChestInventory;
/*    */   private int numRows;
/*    */   
/*    */   public ContainerChest(IInventory playerInventory, IInventory chestInventory, EntityPlayer player) {
/* 11 */     this.lowerChestInventory = chestInventory;
/* 12 */     this.numRows = chestInventory.getSizeInventory() / 9;
/* 13 */     chestInventory.openInventory(player);
/* 14 */     int i = (this.numRows - 4) * 18;
/*    */     
/* 16 */     for (int j = 0; j < this.numRows; j++) {
/* 17 */       for (int k = 0; k < 9; k++) {
/* 18 */         addSlotToContainer(new Slot(chestInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
/*    */       }
/*    */     } 
/*    */     
/* 22 */     for (int l = 0; l < 3; l++) {
/* 23 */       for (int j1 = 0; j1 < 9; j1++) {
/* 24 */         addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
/*    */       }
/*    */     } 
/*    */     
/* 28 */     for (int i1 = 0; i1 < 9; i1++) {
/* 29 */       addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 34 */     return this.lowerChestInventory.isUseableByPlayer(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 41 */     ItemStack itemstack = null;
/* 42 */     Slot slot = this.inventorySlots.get(index);
/*    */     
/* 44 */     if (slot != null && slot.getHasStack()) {
/* 45 */       ItemStack itemstack1 = slot.getStack();
/* 46 */       itemstack = itemstack1.copy();
/*    */       
/* 48 */       if (index < this.numRows * 9) {
/* 49 */         if (!mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
/* 50 */           return null;
/*    */         }
/* 52 */       } else if (!mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
/* 53 */         return null;
/*    */       } 
/*    */       
/* 56 */       if (itemstack1.stackSize == 0) {
/* 57 */         slot.putStack(null);
/*    */       } else {
/* 59 */         slot.onSlotChanged();
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onContainerClosed(EntityPlayer playerIn) {
/* 70 */     super.onContainerClosed(playerIn);
/* 71 */     this.lowerChestInventory.closeInventory(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IInventory getLowerChestInventory() {
/* 78 */     return this.lowerChestInventory;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */