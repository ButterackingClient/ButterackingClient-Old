/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ 
/*     */ public class ItemEnchantedBook extends Item {
/*     */   public boolean hasEffect(ItemStack stack) {
/*  17 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemTool(ItemStack stack) {
/*  24 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRarity getRarity(ItemStack stack) {
/*  31 */     return (getEnchantments(stack).tagCount() > 0) ? EnumRarity.UNCOMMON : super.getRarity(stack);
/*     */   }
/*     */   
/*     */   public NBTTagList getEnchantments(ItemStack stack) {
/*  35 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  36 */     return (nbttagcompound != null && nbttagcompound.hasKey("StoredEnchantments", 9)) ? (NBTTagList)nbttagcompound.getTag("StoredEnchantments") : new NBTTagList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  43 */     super.addInformation(stack, playerIn, tooltip, advanced);
/*  44 */     NBTTagList nbttaglist = getEnchantments(stack);
/*     */     
/*  46 */     if (nbttaglist != null) {
/*  47 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  48 */         int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  49 */         int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */         
/*  51 */         if (Enchantment.getEnchantmentById(j) != null) {
/*  52 */           tooltip.add(Enchantment.getEnchantmentById(j).getTranslatedName(k));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEnchantment(ItemStack stack, EnchantmentData enchantment) {
/*  62 */     NBTTagList nbttaglist = getEnchantments(stack);
/*  63 */     boolean flag = true;
/*     */     
/*  65 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  66 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*     */       
/*  68 */       if (nbttagcompound.getShort("id") == enchantment.enchantmentobj.effectId) {
/*  69 */         if (nbttagcompound.getShort("lvl") < enchantment.enchantmentLevel) {
/*  70 */           nbttagcompound.setShort("lvl", (short)enchantment.enchantmentLevel);
/*     */         }
/*     */         
/*  73 */         flag = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  78 */     if (flag) {
/*  79 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  80 */       nbttagcompound1.setShort("id", (short)enchantment.enchantmentobj.effectId);
/*  81 */       nbttagcompound1.setShort("lvl", (short)enchantment.enchantmentLevel);
/*  82 */       nbttaglist.appendTag((NBTBase)nbttagcompound1);
/*     */     } 
/*     */     
/*  85 */     if (!stack.hasTagCompound()) {
/*  86 */       stack.setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/*  89 */     stack.getTagCompound().setTag("StoredEnchantments", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEnchantedItemStack(EnchantmentData data) {
/*  96 */     ItemStack itemstack = new ItemStack(this);
/*  97 */     addEnchantment(itemstack, data);
/*  98 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void getAll(Enchantment enchantment, List<ItemStack> list) {
/* 102 */     for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
/* 103 */       list.add(getEnchantedItemStack(new EnchantmentData(enchantment, i)));
/*     */     }
/*     */   }
/*     */   
/*     */   public WeightedRandomChestContent getRandom(Random rand) {
/* 108 */     return getRandom(rand, 1, 1, 1);
/*     */   }
/*     */   
/*     */   public WeightedRandomChestContent getRandom(Random rand, int minChance, int maxChance, int weight) {
/* 112 */     ItemStack itemstack = new ItemStack(Items.book, 1, 0);
/* 113 */     EnchantmentHelper.addRandomEnchantment(rand, itemstack, 30);
/* 114 */     return new WeightedRandomChestContent(itemstack, minChance, maxChance, weight);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemEnchantedBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */