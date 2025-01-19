/*     */ package net.minecraft.item;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBow extends Item {
/*  13 */   public static final String[] bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
/*     */   
/*     */   public ItemBow() {
/*  16 */     this.maxStackSize = 1;
/*  17 */     setMaxDamage(384);
/*  18 */     setCreativeTab(CreativeTabs.tabCombat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {
/*  25 */     boolean flag = !(!playerIn.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) <= 0);
/*     */     
/*  27 */     if (flag || playerIn.inventory.hasItem(Items.arrow)) {
/*  28 */       int i = getMaxItemUseDuration(stack) - timeLeft;
/*  29 */       float f = i / 20.0F;
/*  30 */       f = (f * f + f * 2.0F) / 3.0F;
/*     */       
/*  32 */       if (f < 0.1D) {
/*     */         return;
/*     */       }
/*     */       
/*  36 */       if (f > 1.0F) {
/*  37 */         f = 1.0F;
/*     */       }
/*     */       
/*  40 */       EntityArrow entityarrow = new EntityArrow(worldIn, (EntityLivingBase)playerIn, f * 2.0F);
/*     */       
/*  42 */       if (f == 1.0F) {
/*  43 */         entityarrow.setIsCritical(true);
/*     */       }
/*     */       
/*  46 */       int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
/*     */       
/*  48 */       if (j > 0) {
/*  49 */         entityarrow.setDamage(entityarrow.getDamage() + j * 0.5D + 0.5D);
/*     */       }
/*     */       
/*  52 */       int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
/*     */       
/*  54 */       if (k > 0) {
/*  55 */         entityarrow.setKnockbackStrength(k);
/*     */       }
/*     */       
/*  58 */       if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
/*  59 */         entityarrow.setFire(100);
/*     */       }
/*     */       
/*  62 */       stack.damageItem(1, (EntityLivingBase)playerIn);
/*  63 */       worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*     */       
/*  65 */       if (flag) {
/*  66 */         entityarrow.canBePickedUp = 2;
/*     */       } else {
/*  68 */         playerIn.inventory.consumeInventoryItem(Items.arrow);
/*     */       } 
/*     */       
/*  71 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */       
/*  73 */       if (!worldIn.isRemote) {
/*  74 */         worldIn.spawnEntityInWorld((Entity)entityarrow);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  84 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/*  91 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/*  98 */     return EnumAction.BOW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 105 */     if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(Items.arrow)) {
/* 106 */       playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/*     */     }
/*     */     
/* 109 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 116 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */