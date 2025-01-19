/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerBeacon
/*     */   extends Container
/*     */ {
/*     */   private IInventory tileBeacon;
/*     */   private final BeaconSlot beaconSlot;
/*     */   
/*     */   public ContainerBeacon(IInventory playerInventory, IInventory tileBeaconIn) {
/*  16 */     this.tileBeacon = tileBeaconIn;
/*  17 */     addSlotToContainer(this.beaconSlot = new BeaconSlot(tileBeaconIn, 0, 136, 110));
/*  18 */     int i = 36;
/*  19 */     int j = 137;
/*     */     
/*  21 */     for (int k = 0; k < 3; k++) {
/*  22 */       for (int l = 0; l < 9; l++) {
/*  23 */         addSlotToContainer(new Slot(playerInventory, l + k * 9 + 9, i + l * 18, j + k * 18));
/*     */       }
/*     */     } 
/*     */     
/*  27 */     for (int i1 = 0; i1 < 9; i1++) {
/*  28 */       addSlotToContainer(new Slot(playerInventory, i1, i + i1 * 18, 58 + j));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  33 */     super.onCraftGuiOpened(listener);
/*  34 */     listener.sendAllWindowProperties(this, this.tileBeacon);
/*     */   }
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/*  38 */     this.tileBeacon.setField(id, data);
/*     */   }
/*     */   
/*     */   public IInventory func_180611_e() {
/*  42 */     return this.tileBeacon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/*  49 */     super.onContainerClosed(playerIn);
/*     */     
/*  51 */     if (playerIn != null && !playerIn.worldObj.isRemote) {
/*  52 */       ItemStack itemstack = this.beaconSlot.decrStackSize(this.beaconSlot.getSlotStackLimit());
/*     */       
/*  54 */       if (itemstack != null) {
/*  55 */         playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  61 */     return this.tileBeacon.isUseableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  68 */     ItemStack itemstack = null;
/*  69 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  71 */     if (slot != null && slot.getHasStack()) {
/*  72 */       ItemStack itemstack1 = slot.getStack();
/*  73 */       itemstack = itemstack1.copy();
/*     */       
/*  75 */       if (index == 0) {
/*  76 */         if (!mergeItemStack(itemstack1, 1, 37, true)) {
/*  77 */           return null;
/*     */         }
/*     */         
/*  80 */         slot.onSlotChange(itemstack1, itemstack);
/*  81 */       } else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(itemstack1) && itemstack1.stackSize == 1) {
/*  82 */         if (!mergeItemStack(itemstack1, 0, 1, false)) {
/*  83 */           return null;
/*     */         }
/*  85 */       } else if (index >= 1 && index < 28) {
/*  86 */         if (!mergeItemStack(itemstack1, 28, 37, false)) {
/*  87 */           return null;
/*     */         }
/*  89 */       } else if (index >= 28 && index < 37) {
/*  90 */         if (!mergeItemStack(itemstack1, 1, 28, false)) {
/*  91 */           return null;
/*     */         }
/*  93 */       } else if (!mergeItemStack(itemstack1, 1, 37, false)) {
/*  94 */         return null;
/*     */       } 
/*     */       
/*  97 */       if (itemstack1.stackSize == 0) {
/*  98 */         slot.putStack(null);
/*     */       } else {
/* 100 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 103 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 104 */         return null;
/*     */       }
/*     */       
/* 107 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 110 */     return itemstack;
/*     */   }
/*     */   
/*     */   class BeaconSlot extends Slot {
/*     */     public BeaconSlot(IInventory p_i1801_2_, int p_i1801_3_, int p_i1801_4_, int p_i1801_5_) {
/* 115 */       super(p_i1801_2_, p_i1801_3_, p_i1801_4_, p_i1801_5_);
/*     */     }
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 119 */       return (stack == null) ? false : (!(stack.getItem() != Items.emerald && stack.getItem() != Items.diamond && stack.getItem() != Items.gold_ingot && stack.getItem() != Items.iron_ingot));
/*     */     }
/*     */     
/*     */     public int getSlotStackLimit() {
/* 123 */       return 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */