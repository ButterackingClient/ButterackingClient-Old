/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerHopper extends Container {
/*    */   private final IInventory hopperInventory;
/*    */   
/*    */   public ContainerHopper(InventoryPlayer playerInventory, IInventory hopperInventoryIn, EntityPlayer player) {
/* 11 */     this.hopperInventory = hopperInventoryIn;
/* 12 */     hopperInventoryIn.openInventory(player);
/* 13 */     int i = 51;
/*    */     
/* 15 */     for (int j = 0; j < hopperInventoryIn.getSizeInventory(); j++) {
/* 16 */       addSlotToContainer(new Slot(hopperInventoryIn, j, 44 + j * 18, 20));
/*    */     }
/*    */     
/* 19 */     for (int l = 0; l < 3; l++) {
/* 20 */       for (int k = 0; k < 9; k++) {
/* 21 */         addSlotToContainer(new Slot((IInventory)playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + i));
/*    */       }
/*    */     } 
/*    */     
/* 25 */     for (int i1 = 0; i1 < 9; i1++) {
/* 26 */       addSlotToContainer(new Slot((IInventory)playerInventory, i1, 8 + i1 * 18, 58 + i));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 31 */     return this.hopperInventory.isUseableByPlayer(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 38 */     ItemStack itemstack = null;
/* 39 */     Slot slot = this.inventorySlots.get(index);
/*    */     
/* 41 */     if (slot != null && slot.getHasStack()) {
/* 42 */       ItemStack itemstack1 = slot.getStack();
/* 43 */       itemstack = itemstack1.copy();
/*    */       
/* 45 */       if (index < this.hopperInventory.getSizeInventory()) {
/* 46 */         if (!mergeItemStack(itemstack1, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
/* 47 */           return null;
/*    */         }
/* 49 */       } else if (!mergeItemStack(itemstack1, 0, this.hopperInventory.getSizeInventory(), false)) {
/* 50 */         return null;
/*    */       } 
/*    */       
/* 53 */       if (itemstack1.stackSize == 0) {
/* 54 */         slot.putStack(null);
/*    */       } else {
/* 56 */         slot.onSlotChanged();
/*    */       } 
/*    */     } 
/*    */     
/* 60 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onContainerClosed(EntityPlayer playerIn) {
/* 67 */     super.onContainerClosed(playerIn);
/* 68 */     this.hopperInventory.closeInventory(playerIn);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */