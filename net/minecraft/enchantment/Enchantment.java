/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*  19 */   private static final Enchantment[] enchantmentsList = new Enchantment[256];
/*     */   public static final Enchantment[] enchantmentsBookList;
/*  21 */   private static final Map<ResourceLocation, Enchantment> locationEnchantments = Maps.newHashMap();
/*  22 */   public static final Enchantment protection = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   public static final Enchantment fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
/*  28 */   public static final Enchantment featherFalling = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final Enchantment blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
/*  34 */   public static final Enchantment projectileProtection = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
/*  35 */   public static final Enchantment respiration = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
/*  41 */   public static final Enchantment thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
/*  42 */   public static final Enchantment depthStrider = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
/*  43 */   public static final Enchantment sharpness = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
/*  44 */   public static final Enchantment smite = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
/*  45 */   public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
/*  46 */   public static final Enchantment knockback = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final Enchantment fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final Enchantment looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final Enchantment efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final Enchantment silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final Enchantment unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final Enchantment fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final Enchantment power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final Enchantment punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final Enchantment flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final Enchantment infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
/*  99 */   public static final Enchantment luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
/* 100 */   public static final Enchantment lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
/*     */ 
/*     */ 
/*     */   
/*     */   public final int effectId;
/*     */ 
/*     */   
/*     */   private final int weight;
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType type;
/*     */ 
/*     */   
/*     */   protected String name;
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enchantment getEnchantmentById(int enchID) {
/* 118 */     return (enchID >= 0 && enchID < enchantmentsList.length) ? enchantmentsList[enchID] : null;
/*     */   }
/*     */   
/*     */   protected Enchantment(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType) {
/* 122 */     this.effectId = enchID;
/* 123 */     this.weight = enchWeight;
/* 124 */     this.type = enchType;
/*     */     
/* 126 */     if (enchantmentsList[enchID] != null) {
/* 127 */       throw new IllegalArgumentException("Duplicate enchantment id!");
/*     */     }
/* 129 */     enchantmentsList[enchID] = this;
/* 130 */     locationEnchantments.put(enchName, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Enchantment getEnchantmentByLocation(String location) {
/* 138 */     return locationEnchantments.get(new ResourceLocation(location));
/*     */   }
/*     */   
/*     */   public static Set<ResourceLocation> func_181077_c() {
/* 142 */     return locationEnchantments.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeight() {
/* 150 */     return this.weight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLevel() {
/* 157 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/* 164 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEnchantability(int enchantmentLevel) {
/* 171 */     return 1 + enchantmentLevel * 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnchantability(int enchantmentLevel) {
/* 178 */     return getMinEnchantability(enchantmentLevel) + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calcModifierDamage(int level, DamageSource source) {
/* 185 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
/* 193 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApplyTogether(Enchantment ench) {
/* 200 */     return (this != ench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enchantment setName(String enchName) {
/* 207 */     this.name = enchName;
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 215 */     return "enchantment." + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedName(int level) {
/* 222 */     String s = StatCollector.translateToLocal(getName());
/* 223 */     return String.valueOf(s) + " " + StatCollector.translateToLocal("enchantment.level." + level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApply(ItemStack stack) {
/* 230 */     return this.type.canEnchantItem(stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 247 */     List<Enchantment> list = Lists.newArrayList(); byte b; int i;
/*     */     Enchantment[] arrayOfEnchantment;
/* 249 */     for (i = (arrayOfEnchantment = enchantmentsList).length, b = 0; b < i; ) { Enchantment enchantment = arrayOfEnchantment[b];
/* 250 */       if (enchantment != null) {
/* 251 */         list.add(enchantment);
/*     */       }
/*     */       b++; }
/*     */     
/* 255 */     enchantmentsBookList = list.<Enchantment>toArray(new Enchantment[list.size()]);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\Enchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */