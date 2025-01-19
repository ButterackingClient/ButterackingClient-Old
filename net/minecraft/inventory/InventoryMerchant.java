/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ 
/*     */ public class InventoryMerchant implements IInventory {
/*     */   private final IMerchant theMerchant;
/*  14 */   private ItemStack[] theInventory = new ItemStack[3];
/*     */   private final EntityPlayer thePlayer;
/*     */   private MerchantRecipe currentRecipe;
/*     */   private int currentRecipeIndex;
/*     */   
/*     */   public InventoryMerchant(EntityPlayer thePlayerIn, IMerchant theMerchantIn) {
/*  20 */     this.thePlayer = thePlayerIn;
/*  21 */     this.theMerchant = theMerchantIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  28 */     return this.theInventory.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  35 */     return this.theInventory[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  42 */     if (this.theInventory[index] != null) {
/*  43 */       if (index == 2) {
/*  44 */         ItemStack itemstack2 = this.theInventory[index];
/*  45 */         this.theInventory[index] = null;
/*  46 */         return itemstack2;
/*  47 */       }  if ((this.theInventory[index]).stackSize <= count) {
/*  48 */         ItemStack itemstack1 = this.theInventory[index];
/*  49 */         this.theInventory[index] = null;
/*     */         
/*  51 */         if (inventoryResetNeededOnSlotChange(index)) {
/*  52 */           resetRecipeAndSlots();
/*     */         }
/*     */         
/*  55 */         return itemstack1;
/*     */       } 
/*  57 */       ItemStack itemstack = this.theInventory[index].splitStack(count);
/*     */       
/*  59 */       if ((this.theInventory[index]).stackSize == 0) {
/*  60 */         this.theInventory[index] = null;
/*     */       }
/*     */       
/*  63 */       if (inventoryResetNeededOnSlotChange(index)) {
/*  64 */         resetRecipeAndSlots();
/*     */       }
/*     */       
/*  67 */       return itemstack;
/*     */     } 
/*     */     
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inventoryResetNeededOnSlotChange(int p_70469_1_) {
/*  78 */     return !(p_70469_1_ != 0 && p_70469_1_ != 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  85 */     if (this.theInventory[index] != null) {
/*  86 */       ItemStack itemstack = this.theInventory[index];
/*  87 */       this.theInventory[index] = null;
/*  88 */       return itemstack;
/*     */     } 
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/*  98 */     this.theInventory[index] = stack;
/*     */     
/* 100 */     if (stack != null && stack.stackSize > getInventoryStackLimit()) {
/* 101 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 104 */     if (inventoryResetNeededOnSlotChange(index)) {
/* 105 */       resetRecipeAndSlots();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 113 */     return "mob.villager";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 127 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 134 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 141 */     return (this.theMerchant.getCustomer() == player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 162 */     resetRecipeAndSlots();
/*     */   }
/*     */   
/*     */   public void resetRecipeAndSlots() {
/* 166 */     this.currentRecipe = null;
/* 167 */     ItemStack itemstack = this.theInventory[0];
/* 168 */     ItemStack itemstack1 = this.theInventory[1];
/*     */     
/* 170 */     if (itemstack == null) {
/* 171 */       itemstack = itemstack1;
/* 172 */       itemstack1 = null;
/*     */     } 
/*     */     
/* 175 */     if (itemstack == null) {
/* 176 */       setInventorySlotContents(2, null);
/*     */     } else {
/* 178 */       MerchantRecipeList merchantrecipelist = this.theMerchant.getRecipes(this.thePlayer);
/*     */       
/* 180 */       if (merchantrecipelist != null) {
/* 181 */         MerchantRecipe merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack, itemstack1, this.currentRecipeIndex);
/*     */         
/* 183 */         if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled()) {
/* 184 */           this.currentRecipe = merchantrecipe;
/* 185 */           setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
/* 186 */         } else if (itemstack1 != null) {
/* 187 */           merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack1, itemstack, this.currentRecipeIndex);
/*     */           
/* 189 */           if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled()) {
/* 190 */             this.currentRecipe = merchantrecipe;
/* 191 */             setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
/*     */           } else {
/* 193 */             setInventorySlotContents(2, null);
/*     */           } 
/*     */         } else {
/* 196 */           setInventorySlotContents(2, null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     this.theMerchant.verifySellingItem(getStackInSlot(2));
/*     */   }
/*     */   
/*     */   public MerchantRecipe getCurrentRecipe() {
/* 205 */     return this.currentRecipe;
/*     */   }
/*     */   
/*     */   public void setCurrentRecipeIndex(int currentRecipeIndexIn) {
/* 209 */     this.currentRecipeIndex = currentRecipeIndexIn;
/* 210 */     resetRecipeAndSlots();
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 214 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 221 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 225 */     for (int i = 0; i < this.theInventory.length; i++)
/* 226 */       this.theInventory[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\InventoryMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */