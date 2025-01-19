/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerMerchant
/*     */   extends Container
/*     */ {
/*     */   private IMerchant theMerchant;
/*     */   private InventoryMerchant merchantInventory;
/*     */   private final World theWorld;
/*     */   
/*     */   public ContainerMerchant(InventoryPlayer playerInventory, IMerchant merchant, World worldIn) {
/*  22 */     this.theMerchant = merchant;
/*  23 */     this.theWorld = worldIn;
/*  24 */     this.merchantInventory = new InventoryMerchant(playerInventory.player, merchant);
/*  25 */     addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
/*  26 */     addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
/*  27 */     addSlotToContainer(new SlotMerchantResult(playerInventory.player, merchant, this.merchantInventory, 2, 120, 53));
/*     */     
/*  29 */     for (int i = 0; i < 3; i++) {
/*  30 */       for (int j = 0; j < 9; j++) {
/*  31 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  35 */     for (int k = 0; k < 9; k++) {
/*  36 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public InventoryMerchant getMerchantInventory() {
/*  41 */     return this.merchantInventory;
/*     */   }
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  45 */     super.onCraftGuiOpened(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  52 */     super.detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/*  59 */     this.merchantInventory.resetRecipeAndSlots();
/*  60 */     super.onCraftMatrixChanged(inventoryIn);
/*     */   }
/*     */   
/*     */   public void setCurrentRecipeIndex(int currentRecipeIndex) {
/*  64 */     this.merchantInventory.setCurrentRecipeIndex(currentRecipeIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {}
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  71 */     return (this.theMerchant.getCustomer() == playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  78 */     ItemStack itemstack = null;
/*  79 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  81 */     if (slot != null && slot.getHasStack()) {
/*  82 */       ItemStack itemstack1 = slot.getStack();
/*  83 */       itemstack = itemstack1.copy();
/*     */       
/*  85 */       if (index == 2) {
/*  86 */         if (!mergeItemStack(itemstack1, 3, 39, true)) {
/*  87 */           return null;
/*     */         }
/*     */         
/*  90 */         slot.onSlotChange(itemstack1, itemstack);
/*  91 */       } else if (index != 0 && index != 1) {
/*  92 */         if (index >= 3 && index < 30) {
/*  93 */           if (!mergeItemStack(itemstack1, 30, 39, false)) {
/*  94 */             return null;
/*     */           }
/*  96 */         } else if (index >= 30 && index < 39 && !mergeItemStack(itemstack1, 3, 30, false)) {
/*  97 */           return null;
/*     */         } 
/*  99 */       } else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/* 100 */         return null;
/*     */       } 
/*     */       
/* 103 */       if (itemstack1.stackSize == 0) {
/* 104 */         slot.putStack(null);
/*     */       } else {
/* 106 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 109 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 110 */         return null;
/*     */       }
/*     */       
/* 113 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 116 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 123 */     super.onContainerClosed(playerIn);
/* 124 */     this.theMerchant.setCustomer(null);
/* 125 */     super.onContainerClosed(playerIn);
/*     */     
/* 127 */     if (!this.theWorld.isRemote) {
/* 128 */       ItemStack itemstack = this.merchantInventory.removeStackFromSlot(0);
/*     */       
/* 130 */       if (itemstack != null) {
/* 131 */         playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       }
/*     */       
/* 134 */       itemstack = this.merchantInventory.removeStackFromSlot(1);
/*     */       
/* 136 */       if (itemstack != null)
/* 137 */         playerIn.dropPlayerItemWithRandomChoice(itemstack, false); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */