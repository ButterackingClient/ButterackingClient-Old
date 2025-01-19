/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ 
/*     */ public class ContainerFurnace extends Container {
/*     */   private final IInventory tileFurnace;
/*     */   private int cookTime;
/*     */   private int totalCookTime;
/*     */   private int furnaceBurnTime;
/*     */   private int currentItemBurnTime;
/*     */   
/*     */   public ContainerFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory) {
/*  17 */     this.tileFurnace = furnaceInventory;
/*  18 */     addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17));
/*  19 */     addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 56, 53));
/*  20 */     addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 116, 35));
/*     */     
/*  22 */     for (int i = 0; i < 3; i++) {
/*  23 */       for (int j = 0; j < 9; j++) {
/*  24 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  28 */     for (int k = 0; k < 9; k++) {
/*  29 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  34 */     super.onCraftGuiOpened(listener);
/*  35 */     listener.sendAllWindowProperties(this, this.tileFurnace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  42 */     super.detectAndSendChanges();
/*     */     
/*  44 */     for (int i = 0; i < this.crafters.size(); i++) {
/*  45 */       ICrafting icrafting = this.crafters.get(i);
/*     */       
/*  47 */       if (this.cookTime != this.tileFurnace.getField(2)) {
/*  48 */         icrafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
/*     */       }
/*     */       
/*  51 */       if (this.furnaceBurnTime != this.tileFurnace.getField(0)) {
/*  52 */         icrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
/*     */       }
/*     */       
/*  55 */       if (this.currentItemBurnTime != this.tileFurnace.getField(1)) {
/*  56 */         icrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
/*     */       }
/*     */       
/*  59 */       if (this.totalCookTime != this.tileFurnace.getField(3)) {
/*  60 */         icrafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
/*     */       }
/*     */     } 
/*     */     
/*  64 */     this.cookTime = this.tileFurnace.getField(2);
/*  65 */     this.furnaceBurnTime = this.tileFurnace.getField(0);
/*  66 */     this.currentItemBurnTime = this.tileFurnace.getField(1);
/*  67 */     this.totalCookTime = this.tileFurnace.getField(3);
/*     */   }
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/*  71 */     this.tileFurnace.setField(id, data);
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  75 */     return this.tileFurnace.isUseableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  82 */     ItemStack itemstack = null;
/*  83 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  85 */     if (slot != null && slot.getHasStack()) {
/*  86 */       ItemStack itemstack1 = slot.getStack();
/*  87 */       itemstack = itemstack1.copy();
/*     */       
/*  89 */       if (index == 2) {
/*  90 */         if (!mergeItemStack(itemstack1, 3, 39, true)) {
/*  91 */           return null;
/*     */         }
/*     */         
/*  94 */         slot.onSlotChange(itemstack1, itemstack);
/*  95 */       } else if (index != 1 && index != 0) {
/*  96 */         if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null) {
/*  97 */           if (!mergeItemStack(itemstack1, 0, 1, false)) {
/*  98 */             return null;
/*     */           }
/* 100 */         } else if (TileEntityFurnace.isItemFuel(itemstack1)) {
/* 101 */           if (!mergeItemStack(itemstack1, 1, 2, false)) {
/* 102 */             return null;
/*     */           }
/* 104 */         } else if (index >= 3 && index < 30) {
/* 105 */           if (!mergeItemStack(itemstack1, 30, 39, false)) {
/* 106 */             return null;
/*     */           }
/* 108 */         } else if (index >= 30 && index < 39 && !mergeItemStack(itemstack1, 3, 30, false)) {
/* 109 */           return null;
/*     */         } 
/* 111 */       } else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/* 112 */         return null;
/*     */       } 
/*     */       
/* 115 */       if (itemstack1.stackSize == 0) {
/* 116 */         slot.putStack(null);
/*     */       } else {
/* 118 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 121 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 122 */         return null;
/*     */       }
/*     */       
/* 125 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 128 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */