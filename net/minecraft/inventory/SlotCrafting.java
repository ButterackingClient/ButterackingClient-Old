/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemPickaxe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
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
/*     */ 
/*     */ public class SlotCrafting
/*     */   extends Slot
/*     */ {
/*     */   private final InventoryCrafting craftMatrix;
/*     */   private final EntityPlayer thePlayer;
/*     */   private int amountCrafted;
/*     */   
/*     */   public SlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory p_i45790_3_, int slotIndex, int xPosition, int yPosition) {
/*  31 */     super(p_i45790_3_, slotIndex, xPosition, yPosition);
/*  32 */     this.thePlayer = player;
/*  33 */     this.craftMatrix = craftingInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValid(ItemStack stack) {
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int amount) {
/*  48 */     if (getHasStack()) {
/*  49 */       this.amountCrafted += Math.min(amount, (getStack()).stackSize);
/*     */     }
/*     */     
/*  52 */     return super.decrStackSize(amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack, int amount) {
/*  60 */     this.amountCrafted += amount;
/*  61 */     onCrafting(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack) {
/*  68 */     if (this.amountCrafted > 0) {
/*  69 */       stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
/*     */     }
/*     */     
/*  72 */     this.amountCrafted = 0;
/*     */     
/*  74 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
/*  75 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildWorkBench);
/*     */     }
/*     */     
/*  78 */     if (stack.getItem() instanceof ItemPickaxe) {
/*  79 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildPickaxe);
/*     */     }
/*     */     
/*  82 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
/*  83 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildFurnace);
/*     */     }
/*     */     
/*  86 */     if (stack.getItem() instanceof net.minecraft.item.ItemHoe) {
/*  87 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildHoe);
/*     */     }
/*     */     
/*  90 */     if (stack.getItem() == Items.bread) {
/*  91 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.makeBread);
/*     */     }
/*     */     
/*  94 */     if (stack.getItem() == Items.cake) {
/*  95 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.bakeCake);
/*     */     }
/*     */     
/*  98 */     if (stack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)stack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD) {
/*  99 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildBetterPickaxe);
/*     */     }
/*     */     
/* 102 */     if (stack.getItem() instanceof net.minecraft.item.ItemSword) {
/* 103 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.buildSword);
/*     */     }
/*     */     
/* 106 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
/* 107 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.enchantments);
/*     */     }
/*     */     
/* 110 */     if (stack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
/* 111 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.bookcase);
/*     */     }
/*     */     
/* 114 */     if (stack.getItem() == Items.golden_apple && stack.getMetadata() == 1) {
/* 115 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.overpowered);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/* 120 */     onCrafting(stack);
/* 121 */     ItemStack[] aitemstack = CraftingManager.getInstance().func_180303_b(this.craftMatrix, playerIn.worldObj);
/*     */     
/* 123 */     for (int i = 0; i < aitemstack.length; i++) {
/* 124 */       ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
/* 125 */       ItemStack itemstack1 = aitemstack[i];
/*     */       
/* 127 */       if (itemstack != null) {
/* 128 */         this.craftMatrix.decrStackSize(i, 1);
/*     */       }
/*     */       
/* 131 */       if (itemstack1 != null)
/* 132 */         if (this.craftMatrix.getStackInSlot(i) == null) {
/* 133 */           this.craftMatrix.setInventorySlotContents(i, itemstack1);
/* 134 */         } else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack1)) {
/* 135 */           this.thePlayer.dropPlayerItemWithRandomChoice(itemstack1, false);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\SlotCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */