/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.world.World;
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
/*     */ public class ItemFood
/*     */   extends Item
/*     */ {
/*     */   public final int itemUseDuration;
/*     */   private final int healAmount;
/*     */   private final float saturationModifier;
/*     */   private final boolean isWolfsFavoriteMeat;
/*     */   private boolean alwaysEdible;
/*     */   private int potionId;
/*     */   private int potionDuration;
/*     */   private int potionAmplifier;
/*     */   private float potionEffectProbability;
/*     */   
/*     */   public ItemFood(int amount, float saturation, boolean isWolfFood) {
/*  52 */     this.itemUseDuration = 32;
/*  53 */     this.healAmount = amount;
/*  54 */     this.isWolfsFavoriteMeat = isWolfFood;
/*  55 */     this.saturationModifier = saturation;
/*  56 */     setCreativeTab(CreativeTabs.tabFood);
/*     */   }
/*     */   
/*     */   public ItemFood(int amount, boolean isWolfFood) {
/*  60 */     this(amount, 0.6F, isWolfFood);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  68 */     stack.stackSize--;
/*  69 */     playerIn.getFoodStats().addStats(this, stack);
/*  70 */     worldIn.playSoundAtEntity((Entity)playerIn, "random.burp", 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
/*  71 */     onFoodEaten(stack, worldIn, playerIn);
/*  72 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*  73 */     return stack;
/*     */   }
/*     */   
/*     */   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
/*  77 */     if (!worldIn.isRemote && this.potionId > 0 && worldIn.rand.nextFloat() < this.potionEffectProbability) {
/*  78 */       player.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/*  86 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/*  93 */     return EnumAction.EAT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 100 */     if (playerIn.canEat(this.alwaysEdible)) {
/* 101 */       playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/*     */     }
/*     */     
/* 104 */     return itemStackIn;
/*     */   }
/*     */   
/*     */   public int getHealAmount(ItemStack stack) {
/* 108 */     return this.healAmount;
/*     */   }
/*     */   
/*     */   public float getSaturationModifier(ItemStack stack) {
/* 112 */     return this.saturationModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWolfsFavoriteMeat() {
/* 119 */     return this.isWolfsFavoriteMeat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemFood setPotionEffect(int id, int duration, int amplifier, float probability) {
/* 127 */     this.potionId = id;
/* 128 */     this.potionDuration = duration;
/* 129 */     this.potionAmplifier = amplifier;
/* 130 */     this.potionEffectProbability = probability;
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemFood setAlwaysEdible() {
/* 138 */     this.alwaysEdible = true;
/* 139 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemFood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */