/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ContainerWorkbench
/*     */   extends Container
/*     */ {
/*  15 */   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
/*  16 */   public IInventory craftResult = new InventoryCraftResult();
/*     */ 
/*     */   
/*     */   private World worldObj;
/*     */   
/*     */   private BlockPos pos;
/*     */ 
/*     */   
/*     */   public ContainerWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
/*  25 */     this.worldObj = worldIn;
/*  26 */     this.pos = posIn;
/*  27 */     addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
/*     */     
/*  29 */     for (int i = 0; i < 3; i++) {
/*  30 */       for (int j = 0; j < 3; j++) {
/*  31 */         addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  35 */     for (int k = 0; k < 3; k++) {
/*  36 */       for (int i1 = 0; i1 < 9; i1++) {
/*  37 */         addSlotToContainer(new Slot((IInventory)playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
/*     */       }
/*     */     } 
/*     */     
/*  41 */     for (int l = 0; l < 9; l++) {
/*  42 */       addSlotToContainer(new Slot((IInventory)playerInventory, l, 8 + l * 18, 142));
/*     */     }
/*     */     
/*  45 */     onCraftMatrixChanged(this.craftMatrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/*  52 */     this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/*  59 */     super.onContainerClosed(playerIn);
/*     */     
/*  61 */     if (!this.worldObj.isRemote) {
/*  62 */       for (int i = 0; i < 9; i++) {
/*  63 */         ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);
/*     */         
/*  65 */         if (itemstack != null) {
/*  66 */           playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  73 */     return (this.worldObj.getBlockState(this.pos).getBlock() != Blocks.crafting_table) ? false : ((playerIn.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  80 */     ItemStack itemstack = null;
/*  81 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  83 */     if (slot != null && slot.getHasStack()) {
/*  84 */       ItemStack itemstack1 = slot.getStack();
/*  85 */       itemstack = itemstack1.copy();
/*     */       
/*  87 */       if (index == 0) {
/*  88 */         if (!mergeItemStack(itemstack1, 10, 46, true)) {
/*  89 */           return null;
/*     */         }
/*     */         
/*  92 */         slot.onSlotChange(itemstack1, itemstack);
/*  93 */       } else if (index >= 10 && index < 37) {
/*  94 */         if (!mergeItemStack(itemstack1, 37, 46, false)) {
/*  95 */           return null;
/*     */         }
/*  97 */       } else if (index >= 37 && index < 46) {
/*  98 */         if (!mergeItemStack(itemstack1, 10, 37, false)) {
/*  99 */           return null;
/*     */         }
/* 101 */       } else if (!mergeItemStack(itemstack1, 10, 46, false)) {
/* 102 */         return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 126 */     return (slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */