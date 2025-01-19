/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerHorseInventory extends Container {
/*    */   private IInventory horseInventory;
/*    */   
/*    */   public ContainerHorseInventory(IInventory playerInventory, IInventory horseInventoryIn, final EntityHorse horse, EntityPlayer player) {
/* 13 */     this.horseInventory = horseInventoryIn;
/* 14 */     this.theHorse = horse;
/* 15 */     int i = 3;
/* 16 */     horseInventoryIn.openInventory(player);
/* 17 */     int j = (i - 4) * 18;
/* 18 */     addSlotToContainer(new Slot(horseInventoryIn, 0, 8, 18) {
/*    */           public boolean isItemValid(ItemStack stack) {
/* 20 */             return (super.isItemValid(stack) && stack.getItem() == Items.saddle && !getHasStack());
/*    */           }
/*    */         });
/* 23 */     addSlotToContainer(new Slot(horseInventoryIn, 1, 8, 36) {
/*    */           public boolean isItemValid(ItemStack stack) {
/* 25 */             return (super.isItemValid(stack) && horse.canWearArmor() && EntityHorse.isArmorItem(stack.getItem()));
/*    */           }
/*    */           
/*    */           public boolean canBeHovered() {
/* 29 */             return horse.canWearArmor();
/*    */           }
/*    */         });
/*    */     
/* 33 */     if (horse.isChested()) {
/* 34 */       for (int k = 0; k < i; k++) {
/* 35 */         for (int l = 0; l < 5; l++) {
/* 36 */           addSlotToContainer(new Slot(horseInventoryIn, 2 + l + k * 5, 80 + l * 18, 18 + k * 18));
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 41 */     for (int i1 = 0; i1 < 3; i1++) {
/* 42 */       for (int k1 = 0; k1 < 9; k1++) {
/* 43 */         addSlotToContainer(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + j));
/*    */       }
/*    */     } 
/*    */     
/* 47 */     for (int j1 = 0; j1 < 9; j1++)
/* 48 */       addSlotToContainer(new Slot(playerInventory, j1, 8 + j1 * 18, 160 + j)); 
/*    */   }
/*    */   private EntityHorse theHorse;
/*    */   
/*    */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 53 */     return (this.horseInventory.isUseableByPlayer(playerIn) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity((Entity)playerIn) < 8.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 60 */     ItemStack itemstack = null;
/* 61 */     Slot slot = this.inventorySlots.get(index);
/*    */     
/* 63 */     if (slot != null && slot.getHasStack()) {
/* 64 */       ItemStack itemstack1 = slot.getStack();
/* 65 */       itemstack = itemstack1.copy();
/*    */       
/* 67 */       if (index < this.horseInventory.getSizeInventory()) {
/* 68 */         if (!mergeItemStack(itemstack1, this.horseInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
/* 69 */           return null;
/*    */         }
/* 71 */       } else if (getSlot(1).isItemValid(itemstack1) && !getSlot(1).getHasStack()) {
/* 72 */         if (!mergeItemStack(itemstack1, 1, 2, false)) {
/* 73 */           return null;
/*    */         }
/* 75 */       } else if (getSlot(0).isItemValid(itemstack1)) {
/* 76 */         if (!mergeItemStack(itemstack1, 0, 1, false)) {
/* 77 */           return null;
/*    */         }
/* 79 */       } else if (this.horseInventory.getSizeInventory() <= 2 || !mergeItemStack(itemstack1, 2, this.horseInventory.getSizeInventory(), false)) {
/* 80 */         return null;
/*    */       } 
/*    */       
/* 83 */       if (itemstack1.stackSize == 0) {
/* 84 */         slot.putStack(null);
/*    */       } else {
/* 86 */         slot.onSlotChanged();
/*    */       } 
/*    */     } 
/*    */     
/* 90 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onContainerClosed(EntityPlayer playerIn) {
/* 97 */     super.onContainerClosed(playerIn);
/* 98 */     this.horseInventory.closeInventory(playerIn);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerHorseInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */