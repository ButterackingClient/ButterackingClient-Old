/*     */ package net.minecraft.village;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ 
/*     */ public class MerchantRecipeList
/*     */   extends ArrayList<MerchantRecipe> {
/*     */   public MerchantRecipeList() {}
/*     */   
/*     */   public MerchantRecipeList(NBTTagCompound compound) {
/*  17 */     readRecipiesFromTags(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MerchantRecipe canRecipeBeUsed(ItemStack p_77203_1_, ItemStack p_77203_2_, int p_77203_3_) {
/*  24 */     if (p_77203_3_ > 0 && p_77203_3_ < size()) {
/*  25 */       MerchantRecipe merchantrecipe1 = get(p_77203_3_);
/*  26 */       return (!func_181078_a(p_77203_1_, merchantrecipe1.getItemToBuy()) || ((p_77203_2_ != null || merchantrecipe1.hasSecondItemToBuy()) && (!merchantrecipe1.hasSecondItemToBuy() || !func_181078_a(p_77203_2_, merchantrecipe1.getSecondItemToBuy()))) || p_77203_1_.stackSize < (merchantrecipe1.getItemToBuy()).stackSize || (merchantrecipe1.hasSecondItemToBuy() && p_77203_2_.stackSize < (merchantrecipe1.getSecondItemToBuy()).stackSize)) ? null : merchantrecipe1;
/*     */     } 
/*  28 */     for (int i = 0; i < size(); i++) {
/*  29 */       MerchantRecipe merchantrecipe = get(i);
/*     */       
/*  31 */       if (func_181078_a(p_77203_1_, merchantrecipe.getItemToBuy()) && p_77203_1_.stackSize >= (merchantrecipe.getItemToBuy()).stackSize && ((!merchantrecipe.hasSecondItemToBuy() && p_77203_2_ == null) || (merchantrecipe.hasSecondItemToBuy() && func_181078_a(p_77203_2_, merchantrecipe.getSecondItemToBuy()) && p_77203_2_.stackSize >= (merchantrecipe.getSecondItemToBuy()).stackSize))) {
/*  32 */         return merchantrecipe;
/*     */       }
/*     */     } 
/*     */     
/*  36 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_181078_a(ItemStack p_181078_1_, ItemStack p_181078_2_) {
/*  41 */     return (ItemStack.areItemsEqual(p_181078_1_, p_181078_2_) && (!p_181078_2_.hasTagCompound() || (p_181078_1_.hasTagCompound() && NBTUtil.func_181123_a((NBTBase)p_181078_2_.getTagCompound(), (NBTBase)p_181078_1_.getTagCompound(), false))));
/*     */   }
/*     */   
/*     */   public void writeToBuf(PacketBuffer buffer) {
/*  45 */     buffer.writeByte((byte)(size() & 0xFF));
/*     */     
/*  47 */     for (int i = 0; i < size(); i++) {
/*  48 */       MerchantRecipe merchantrecipe = get(i);
/*  49 */       buffer.writeItemStackToBuffer(merchantrecipe.getItemToBuy());
/*  50 */       buffer.writeItemStackToBuffer(merchantrecipe.getItemToSell());
/*  51 */       ItemStack itemstack = merchantrecipe.getSecondItemToBuy();
/*  52 */       buffer.writeBoolean((itemstack != null));
/*     */       
/*  54 */       if (itemstack != null) {
/*  55 */         buffer.writeItemStackToBuffer(itemstack);
/*     */       }
/*     */       
/*  58 */       buffer.writeBoolean(merchantrecipe.isRecipeDisabled());
/*  59 */       buffer.writeInt(merchantrecipe.getToolUses());
/*  60 */       buffer.writeInt(merchantrecipe.getMaxTradeUses());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MerchantRecipeList readFromBuf(PacketBuffer buffer) throws IOException {
/*  65 */     MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
/*  66 */     int i = buffer.readByte() & 0xFF;
/*     */     
/*  68 */     for (int j = 0; j < i; j++) {
/*  69 */       ItemStack itemstack = buffer.readItemStackFromBuffer();
/*  70 */       ItemStack itemstack1 = buffer.readItemStackFromBuffer();
/*  71 */       ItemStack itemstack2 = null;
/*     */       
/*  73 */       if (buffer.readBoolean()) {
/*  74 */         itemstack2 = buffer.readItemStackFromBuffer();
/*     */       }
/*     */       
/*  77 */       boolean flag = buffer.readBoolean();
/*  78 */       int k = buffer.readInt();
/*  79 */       int l = buffer.readInt();
/*  80 */       MerchantRecipe merchantrecipe = new MerchantRecipe(itemstack, itemstack2, itemstack1, k, l);
/*     */       
/*  82 */       if (flag) {
/*  83 */         merchantrecipe.compensateToolUses();
/*     */       }
/*     */       
/*  86 */       merchantrecipelist.add(merchantrecipe);
/*     */     } 
/*     */     
/*  89 */     return merchantrecipelist;
/*     */   }
/*     */   
/*     */   public void readRecipiesFromTags(NBTTagCompound compound) {
/*  93 */     NBTTagList nbttaglist = compound.getTagList("Recipes", 10);
/*     */     
/*  95 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  96 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  97 */       add(new MerchantRecipe(nbttagcompound));
/*     */     } 
/*     */   }
/*     */   
/*     */   public NBTTagCompound getRecipiesAsTags() {
/* 102 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 103 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 105 */     for (int i = 0; i < size(); i++) {
/* 106 */       MerchantRecipe merchantrecipe = get(i);
/* 107 */       nbttaglist.appendTag((NBTBase)merchantrecipe.writeToTags());
/*     */     } 
/*     */     
/* 110 */     nbttagcompound.setTag("Recipes", (NBTBase)nbttaglist);
/* 111 */     return nbttagcompound;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\village\MerchantRecipeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */