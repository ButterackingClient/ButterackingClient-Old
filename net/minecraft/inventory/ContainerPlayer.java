/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ 
/*     */ 
/*     */ public class ContainerPlayer
/*     */   extends Container
/*     */ {
/*  16 */   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
/*  17 */   public IInventory craftResult = new InventoryCraftResult();
/*     */ 
/*     */   
/*     */   public boolean isLocalWorld;
/*     */   
/*     */   private final EntityPlayer thePlayer;
/*     */ 
/*     */   
/*     */   public ContainerPlayer(InventoryPlayer playerInventory, boolean localWorld, EntityPlayer player) {
/*  26 */     this.isLocalWorld = localWorld;
/*  27 */     this.thePlayer = player;
/*  28 */     addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 144, 36));
/*     */     
/*  30 */     for (int i = 0; i < 2; i++) {
/*  31 */       for (int j = 0; j < 2; j++) {
/*  32 */         addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 88 + j * 18, 26 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  36 */     for (int k = 0; k < 4; k++) {
/*  37 */       final int k_f = k;
/*  38 */       addSlotToContainer(new Slot((IInventory)playerInventory, playerInventory.getSizeInventory() - 1 - k, 8, 8 + k * 18) {
/*     */             public int getSlotStackLimit() {
/*  40 */               return 1;
/*     */             }
/*     */             
/*     */             public boolean isItemValid(ItemStack stack) {
/*  44 */               return (stack == null) ? false : ((stack.getItem() instanceof ItemArmor) ? ((((ItemArmor)stack.getItem()).armorType == k_f)) : ((stack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && stack.getItem() != Items.skull) ? false : ((k_f == 0))));
/*     */             }
/*     */             
/*     */             public String getSlotTexture() {
/*  48 */               return ItemArmor.EMPTY_SLOT_NAMES[k_f];
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  53 */     for (int l = 0; l < 3; l++) {
/*  54 */       for (int j1 = 0; j1 < 9; j1++) {
/*  55 */         addSlotToContainer(new Slot((IInventory)playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  59 */     for (int i1 = 0; i1 < 9; i1++) {
/*  60 */       addSlotToContainer(new Slot((IInventory)playerInventory, i1, 8 + i1 * 18, 142));
/*     */     }
/*     */     
/*  63 */     onCraftMatrixChanged(this.craftMatrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/*  70 */     this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/*  77 */     super.onContainerClosed(playerIn);
/*     */     
/*  79 */     for (int i = 0; i < 4; i++) {
/*  80 */       ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);
/*     */       
/*  82 */       if (itemstack != null) {
/*  83 */         playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       }
/*     */     } 
/*     */     
/*  87 */     this.craftResult.setInventorySlotContents(0, null);
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  98 */     ItemStack itemstack = null;
/*  99 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 101 */     if (slot != null && slot.getHasStack()) {
/* 102 */       ItemStack itemstack1 = slot.getStack();
/* 103 */       itemstack = itemstack1.copy();
/*     */       
/* 105 */       if (index == 0) {
/* 106 */         if (!mergeItemStack(itemstack1, 9, 45, true)) {
/* 107 */           return null;
/*     */         }
/*     */         
/* 110 */         slot.onSlotChange(itemstack1, itemstack);
/* 111 */       } else if (index >= 1 && index < 5) {
/* 112 */         if (!mergeItemStack(itemstack1, 9, 45, false)) {
/* 113 */           return null;
/*     */         }
/* 115 */       } else if (index >= 5 && index < 9) {
/* 116 */         if (!mergeItemStack(itemstack1, 9, 45, false)) {
/* 117 */           return null;
/*     */         }
/* 119 */       } else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) {
/* 120 */         int i = 5 + ((ItemArmor)itemstack.getItem()).armorType;
/*     */         
/* 122 */         if (!mergeItemStack(itemstack1, i, i + 1, false)) {
/* 123 */           return null;
/*     */         }
/* 125 */       } else if (index >= 9 && index < 36) {
/* 126 */         if (!mergeItemStack(itemstack1, 36, 45, false)) {
/* 127 */           return null;
/*     */         }
/* 129 */       } else if (index >= 36 && index < 45) {
/* 130 */         if (!mergeItemStack(itemstack1, 9, 36, false)) {
/* 131 */           return null;
/*     */         }
/* 133 */       } else if (!mergeItemStack(itemstack1, 9, 45, false)) {
/* 134 */         return null;
/*     */       } 
/*     */       
/* 137 */       if (itemstack1.stackSize == 0) {
/* 138 */         slot.putStack(null);
/*     */       } else {
/* 140 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 143 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 144 */         return null;
/*     */       }
/*     */       
/* 147 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 150 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 158 */     return (slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */