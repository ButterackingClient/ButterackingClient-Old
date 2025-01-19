/*     */ package net.minecraft.village;
/*     */ 
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MerchantRecipe
/*     */ {
/*     */   private ItemStack itemToBuy;
/*     */   private ItemStack secondItemToBuy;
/*     */   private ItemStack itemToSell;
/*     */   private int toolUses;
/*     */   private int maxTradeUses;
/*     */   private boolean rewardsExp;
/*     */   
/*     */   public MerchantRecipe(NBTTagCompound tagCompound) {
/*  35 */     readFromTags(tagCompound);
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, ItemStack buy2, ItemStack sell) {
/*  39 */     this(buy1, buy2, sell, 0, 7);
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, ItemStack buy2, ItemStack sell, int toolUsesIn, int maxTradeUsesIn) {
/*  43 */     this.itemToBuy = buy1;
/*  44 */     this.secondItemToBuy = buy2;
/*  45 */     this.itemToSell = sell;
/*  46 */     this.toolUses = toolUsesIn;
/*  47 */     this.maxTradeUses = maxTradeUsesIn;
/*  48 */     this.rewardsExp = true;
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, ItemStack sell) {
/*  52 */     this(buy1, null, sell);
/*     */   }
/*     */   
/*     */   public MerchantRecipe(ItemStack buy1, Item sellItem) {
/*  56 */     this(buy1, new ItemStack(sellItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemToBuy() {
/*  63 */     return this.itemToBuy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSecondItemToBuy() {
/*  70 */     return this.secondItemToBuy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSecondItemToBuy() {
/*  77 */     return (this.secondItemToBuy != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemToSell() {
/*  84 */     return this.itemToSell;
/*     */   }
/*     */   
/*     */   public int getToolUses() {
/*  88 */     return this.toolUses;
/*     */   }
/*     */   
/*     */   public int getMaxTradeUses() {
/*  92 */     return this.maxTradeUses;
/*     */   }
/*     */   
/*     */   public void incrementToolUses() {
/*  96 */     this.toolUses++;
/*     */   }
/*     */   
/*     */   public void increaseMaxTradeUses(int increment) {
/* 100 */     this.maxTradeUses += increment;
/*     */   }
/*     */   
/*     */   public boolean isRecipeDisabled() {
/* 104 */     return (this.toolUses >= this.maxTradeUses);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compensateToolUses() {
/* 112 */     this.toolUses = this.maxTradeUses;
/*     */   }
/*     */   
/*     */   public boolean getRewardsExp() {
/* 116 */     return this.rewardsExp;
/*     */   }
/*     */   
/*     */   public void readFromTags(NBTTagCompound tagCompound) {
/* 120 */     NBTTagCompound nbttagcompound = tagCompound.getCompoundTag("buy");
/* 121 */     this.itemToBuy = ItemStack.loadItemStackFromNBT(nbttagcompound);
/* 122 */     NBTTagCompound nbttagcompound1 = tagCompound.getCompoundTag("sell");
/* 123 */     this.itemToSell = ItemStack.loadItemStackFromNBT(nbttagcompound1);
/*     */     
/* 125 */     if (tagCompound.hasKey("buyB", 10)) {
/* 126 */       this.secondItemToBuy = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("buyB"));
/*     */     }
/*     */     
/* 129 */     if (tagCompound.hasKey("uses", 99)) {
/* 130 */       this.toolUses = tagCompound.getInteger("uses");
/*     */     }
/*     */     
/* 133 */     if (tagCompound.hasKey("maxUses", 99)) {
/* 134 */       this.maxTradeUses = tagCompound.getInteger("maxUses");
/*     */     } else {
/* 136 */       this.maxTradeUses = 7;
/*     */     } 
/*     */     
/* 139 */     if (tagCompound.hasKey("rewardExp", 1)) {
/* 140 */       this.rewardsExp = tagCompound.getBoolean("rewardExp");
/*     */     } else {
/* 142 */       this.rewardsExp = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public NBTTagCompound writeToTags() {
/* 147 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 148 */     nbttagcompound.setTag("buy", (NBTBase)this.itemToBuy.writeToNBT(new NBTTagCompound()));
/* 149 */     nbttagcompound.setTag("sell", (NBTBase)this.itemToSell.writeToNBT(new NBTTagCompound()));
/*     */     
/* 151 */     if (this.secondItemToBuy != null) {
/* 152 */       nbttagcompound.setTag("buyB", (NBTBase)this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */     
/* 155 */     nbttagcompound.setInteger("uses", this.toolUses);
/* 156 */     nbttagcompound.setInteger("maxUses", this.maxTradeUses);
/* 157 */     nbttagcompound.setBoolean("rewardExp", this.rewardsExp);
/* 158 */     return nbttagcompound;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\village\MerchantRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */