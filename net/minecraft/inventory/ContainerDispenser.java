/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerDispenser extends Container {
/*    */   private IInventory dispenserInventory;
/*    */   
/*    */   public ContainerDispenser(IInventory playerInventory, IInventory dispenserInventoryIn) {
/* 10 */     this.dispenserInventory = dispenserInventoryIn;
/*    */     
/* 12 */     for (int i = 0; i < 3; i++) {
/* 13 */       for (int j = 0; j < 3; j++) {
/* 14 */         addSlotToContainer(new Slot(dispenserInventoryIn, j + i * 3, 62 + j * 18, 17 + i * 18));
/*    */       }
/*    */     } 
/*    */     
/* 18 */     for (int k = 0; k < 3; k++) {
/* 19 */       for (int i1 = 0; i1 < 9; i1++) {
/* 20 */         addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
/*    */       }
/*    */     } 
/*    */     
/* 24 */     for (int l = 0; l < 9; l++) {
/* 25 */       addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 30 */     return this.dispenserInventory.isUseableByPlayer(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 37 */     ItemStack itemstack = null;
/* 38 */     Slot slot = this.inventorySlots.get(index);
/*    */     
/* 40 */     if (slot != null && slot.getHasStack()) {
/* 41 */       ItemStack itemstack1 = slot.getStack();
/* 42 */       itemstack = itemstack1.copy();
/*    */       
/* 44 */       if (index < 9) {
/* 45 */         if (!mergeItemStack(itemstack1, 9, 45, true)) {
/* 46 */           return null;
/*    */         }
/* 48 */       } else if (!mergeItemStack(itemstack1, 0, 9, false)) {
/* 49 */         return null;
/*    */       } 
/*    */       
/* 52 */       if (itemstack1.stackSize == 0) {
/* 53 */         slot.putStack(null);
/*    */       } else {
/* 55 */         slot.onSlotChanged();
/*    */       } 
/*    */       
/* 58 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 59 */         return null;
/*    */       }
/*    */       
/* 62 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*    */     } 
/*    */     
/* 65 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */