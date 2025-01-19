/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ 
/*     */ 
/*     */ public class ContainerBrewingStand
/*     */   extends Container
/*     */ {
/*     */   private IInventory tileBrewingStand;
/*     */   private final Slot theSlot;
/*     */   private int brewTime;
/*     */   
/*     */   public ContainerBrewingStand(InventoryPlayer playerInventory, IInventory tileBrewingStandIn) {
/*  19 */     this.tileBrewingStand = tileBrewingStandIn;
/*  20 */     addSlotToContainer(new Potion(playerInventory.player, tileBrewingStandIn, 0, 56, 46));
/*  21 */     addSlotToContainer(new Potion(playerInventory.player, tileBrewingStandIn, 1, 79, 53));
/*  22 */     addSlotToContainer(new Potion(playerInventory.player, tileBrewingStandIn, 2, 102, 46));
/*  23 */     this.theSlot = addSlotToContainer(new Ingredient(tileBrewingStandIn, 3, 79, 17));
/*     */     
/*  25 */     for (int i = 0; i < 3; i++) {
/*  26 */       for (int j = 0; j < 9; j++) {
/*  27 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  31 */     for (int k = 0; k < 9; k++) {
/*  32 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  37 */     super.onCraftGuiOpened(listener);
/*  38 */     listener.sendAllWindowProperties(this, this.tileBrewingStand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  45 */     super.detectAndSendChanges();
/*     */     
/*  47 */     for (int i = 0; i < this.crafters.size(); i++) {
/*  48 */       ICrafting icrafting = this.crafters.get(i);
/*     */       
/*  50 */       if (this.brewTime != this.tileBrewingStand.getField(0)) {
/*  51 */         icrafting.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getField(0));
/*     */       }
/*     */     } 
/*     */     
/*  55 */     this.brewTime = this.tileBrewingStand.getField(0);
/*     */   }
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/*  59 */     this.tileBrewingStand.setField(id, data);
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  63 */     return this.tileBrewingStand.isUseableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  70 */     ItemStack itemstack = null;
/*  71 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  73 */     if (slot != null && slot.getHasStack()) {
/*  74 */       ItemStack itemstack1 = slot.getStack();
/*  75 */       itemstack = itemstack1.copy();
/*     */       
/*  77 */       if ((index < 0 || index > 2) && index != 3) {
/*  78 */         if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(itemstack1)) {
/*  79 */           if (!mergeItemStack(itemstack1, 3, 4, false)) {
/*  80 */             return null;
/*     */           }
/*  82 */         } else if (Potion.canHoldPotion(itemstack)) {
/*  83 */           if (!mergeItemStack(itemstack1, 0, 3, false)) {
/*  84 */             return null;
/*     */           }
/*  86 */         } else if (index >= 4 && index < 31) {
/*  87 */           if (!mergeItemStack(itemstack1, 31, 40, false)) {
/*  88 */             return null;
/*     */           }
/*  90 */         } else if (index >= 31 && index < 40) {
/*  91 */           if (!mergeItemStack(itemstack1, 4, 31, false)) {
/*  92 */             return null;
/*     */           }
/*  94 */         } else if (!mergeItemStack(itemstack1, 4, 40, false)) {
/*  95 */           return null;
/*     */         } 
/*     */       } else {
/*  98 */         if (!mergeItemStack(itemstack1, 4, 40, true)) {
/*  99 */           return null;
/*     */         }
/*     */         
/* 102 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       } 
/*     */       
/* 105 */       if (itemstack1.stackSize == 0) {
/* 106 */         slot.putStack(null);
/*     */       } else {
/* 108 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 111 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 112 */         return null;
/*     */       }
/*     */       
/* 115 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 118 */     return itemstack;
/*     */   }
/*     */   
/*     */   class Ingredient extends Slot {
/*     */     public Ingredient(IInventory inventoryIn, int index, int xPosition, int yPosition) {
/* 123 */       super(inventoryIn, index, xPosition, yPosition);
/*     */     }
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 127 */       return (stack != null) ? stack.getItem().isPotionIngredient(stack) : false;
/*     */     }
/*     */     
/*     */     public int getSlotStackLimit() {
/* 131 */       return 64;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Potion extends Slot {
/*     */     private EntityPlayer player;
/*     */     
/*     */     public Potion(EntityPlayer playerIn, IInventory inventoryIn, int index, int xPosition, int yPosition) {
/* 139 */       super(inventoryIn, index, xPosition, yPosition);
/* 140 */       this.player = playerIn;
/*     */     }
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 144 */       return canHoldPotion(stack);
/*     */     }
/*     */     
/*     */     public int getSlotStackLimit() {
/* 148 */       return 1;
/*     */     }
/*     */     
/*     */     public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/* 152 */       if (stack.getItem() == Items.potionitem && stack.getMetadata() > 0) {
/* 153 */         this.player.triggerAchievement((StatBase)AchievementList.potion);
/*     */       }
/*     */       
/* 156 */       super.onPickupFromSlot(playerIn, stack);
/*     */     }
/*     */     
/*     */     public static boolean canHoldPotion(ItemStack stack) {
/* 160 */       return (stack != null && (stack.getItem() == Items.potionitem || stack.getItem() == Items.glass_bottle));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */