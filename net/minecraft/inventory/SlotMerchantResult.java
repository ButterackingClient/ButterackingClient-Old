/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotMerchantResult
/*     */   extends Slot
/*     */ {
/*     */   private final InventoryMerchant theMerchantInventory;
/*     */   private EntityPlayer thePlayer;
/*     */   private int field_75231_g;
/*     */   private final IMerchant theMerchant;
/*     */   
/*     */   public SlotMerchantResult(EntityPlayer player, IMerchant merchant, InventoryMerchant merchantInventory, int slotIndex, int xPosition, int yPosition) {
/*  27 */     super(merchantInventory, slotIndex, xPosition, yPosition);
/*  28 */     this.thePlayer = player;
/*  29 */     this.theMerchant = merchant;
/*  30 */     this.theMerchantInventory = merchantInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValid(ItemStack stack) {
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int amount) {
/*  45 */     if (getHasStack()) {
/*  46 */       this.field_75231_g += Math.min(amount, (getStack()).stackSize);
/*     */     }
/*     */     
/*  49 */     return super.decrStackSize(amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack, int amount) {
/*  57 */     this.field_75231_g += amount;
/*  58 */     onCrafting(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack) {
/*  65 */     stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
/*  66 */     this.field_75231_g = 0;
/*     */   }
/*     */   
/*     */   public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/*  70 */     onCrafting(stack);
/*  71 */     MerchantRecipe merchantrecipe = this.theMerchantInventory.getCurrentRecipe();
/*     */     
/*  73 */     if (merchantrecipe != null) {
/*  74 */       ItemStack itemstack = this.theMerchantInventory.getStackInSlot(0);
/*  75 */       ItemStack itemstack1 = this.theMerchantInventory.getStackInSlot(1);
/*     */       
/*  77 */       if (doTrade(merchantrecipe, itemstack, itemstack1) || doTrade(merchantrecipe, itemstack1, itemstack)) {
/*  78 */         this.theMerchant.useRecipe(merchantrecipe);
/*  79 */         playerIn.triggerAchievement(StatList.timesTradedWithVillagerStat);
/*     */         
/*  81 */         if (itemstack != null && itemstack.stackSize <= 0) {
/*  82 */           itemstack = null;
/*     */         }
/*     */         
/*  85 */         if (itemstack1 != null && itemstack1.stackSize <= 0) {
/*  86 */           itemstack1 = null;
/*     */         }
/*     */         
/*  89 */         this.theMerchantInventory.setInventorySlotContents(0, itemstack);
/*  90 */         this.theMerchantInventory.setInventorySlotContents(1, itemstack1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean doTrade(MerchantRecipe trade, ItemStack firstItem, ItemStack secondItem) {
/*  96 */     ItemStack itemstack = trade.getItemToBuy();
/*  97 */     ItemStack itemstack1 = trade.getSecondItemToBuy();
/*     */     
/*  99 */     if (firstItem != null && firstItem.getItem() == itemstack.getItem()) {
/* 100 */       if (itemstack1 != null && secondItem != null && itemstack1.getItem() == secondItem.getItem()) {
/* 101 */         firstItem.stackSize -= itemstack.stackSize;
/* 102 */         secondItem.stackSize -= itemstack1.stackSize;
/* 103 */         return true;
/*     */       } 
/*     */       
/* 106 */       if (itemstack1 == null && secondItem == null) {
/* 107 */         firstItem.stackSize -= itemstack.stackSize;
/* 108 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\SlotMerchantResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */