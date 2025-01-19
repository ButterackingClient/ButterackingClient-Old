/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantmentHelper
/*     */ {
/*  27 */   private static final Random enchantmentRand = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private static final ModifierDamage enchantmentModifierDamage = new ModifierDamage(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final ModifierLiving enchantmentModifierLiving = new ModifierLiving(null);
/*  38 */   private static final HurtIterator ENCHANTMENT_ITERATOR_HURT = new HurtIterator(null);
/*  39 */   private static final DamageIterator ENCHANTMENT_ITERATOR_DAMAGE = new DamageIterator(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentLevel(int enchID, ItemStack stack) {
/*  45 */     if (stack == null) {
/*  46 */       return 0;
/*     */     }
/*  48 */     NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */     
/*  50 */     if (nbttaglist == null) {
/*  51 */       return 0;
/*     */     }
/*  53 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  54 */       int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  55 */       int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */       
/*  57 */       if (j == enchID) {
/*  58 */         return k;
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<Integer, Integer> getEnchantments(ItemStack stack) {
/*  68 */     Map<Integer, Integer> map = Maps.newLinkedHashMap();
/*  69 */     NBTTagList nbttaglist = (stack.getItem() == Items.enchanted_book) ? Items.enchanted_book.getEnchantments(stack) : stack.getEnchantmentTagList();
/*     */     
/*  71 */     if (nbttaglist != null) {
/*  72 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  73 */         int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/*  74 */         int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*  75 */         map.put(Integer.valueOf(j), Integer.valueOf(k));
/*     */       } 
/*     */     }
/*     */     
/*  79 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setEnchantments(Map<Integer, Integer> enchMap, ItemStack stack) {
/*  86 */     NBTTagList nbttaglist = new NBTTagList();
/*  87 */     Iterator<Integer> iterator = enchMap.keySet().iterator();
/*     */     
/*  89 */     while (iterator.hasNext()) {
/*  90 */       int i = ((Integer)iterator.next()).intValue();
/*  91 */       Enchantment enchantment = Enchantment.getEnchantmentById(i);
/*     */       
/*  93 */       if (enchantment != null) {
/*  94 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  95 */         nbttagcompound.setShort("id", (short)i);
/*  96 */         nbttagcompound.setShort("lvl", (short)((Integer)enchMap.get(Integer.valueOf(i))).intValue());
/*  97 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */         
/*  99 */         if (stack.getItem() == Items.enchanted_book) {
/* 100 */           Items.enchanted_book.addEnchantment(stack, new EnchantmentData(enchantment, ((Integer)enchMap.get(Integer.valueOf(i))).intValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     if (nbttaglist.tagCount() > 0) {
/* 106 */       if (stack.getItem() != Items.enchanted_book) {
/* 107 */         stack.setTagInfo("ench", (NBTBase)nbttaglist);
/*     */       }
/* 109 */     } else if (stack.hasTagCompound()) {
/* 110 */       stack.getTagCompound().removeTag("ench");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMaxEnchantmentLevel(int enchID, ItemStack[] stacks) {
/* 118 */     if (stacks == null) {
/* 119 */       return 0;
/*     */     }
/* 121 */     int i = 0; byte b; int j;
/*     */     ItemStack[] arrayOfItemStack;
/* 123 */     for (j = (arrayOfItemStack = stacks).length, b = 0; b < j; ) { ItemStack itemstack = arrayOfItemStack[b];
/* 124 */       int k = getEnchantmentLevel(enchID, itemstack);
/*     */       
/* 126 */       if (k > i) {
/* 127 */         i = k;
/*     */       }
/*     */       b++; }
/*     */     
/* 131 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyEnchantmentModifier(IModifier modifier, ItemStack stack) {
/* 139 */     if (stack != null) {
/* 140 */       NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */       
/* 142 */       if (nbttaglist != null) {
/* 143 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 144 */           int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/* 145 */           int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */           
/* 147 */           if (Enchantment.getEnchantmentById(j) != null) {
/* 148 */             modifier.calculateModifier(Enchantment.getEnchantmentById(j), k);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void applyEnchantmentModifierArray(IModifier modifier, ItemStack[] stacks) {
/*     */     byte b;
/*     */     int i;
/*     */     ItemStack[] arrayOfItemStack;
/* 159 */     for (i = (arrayOfItemStack = stacks).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/* 160 */       applyEnchantmentModifier(modifier, itemstack);
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentModifierDamage(ItemStack[] stacks, DamageSource source) {
/* 168 */     enchantmentModifierDamage.damageModifier = 0;
/* 169 */     enchantmentModifierDamage.source = source;
/* 170 */     applyEnchantmentModifierArray(enchantmentModifierDamage, stacks);
/*     */     
/* 172 */     if (enchantmentModifierDamage.damageModifier > 25) {
/* 173 */       enchantmentModifierDamage.damageModifier = 25;
/* 174 */     } else if (enchantmentModifierDamage.damageModifier < 0) {
/* 175 */       enchantmentModifierDamage.damageModifier = 0;
/*     */     } 
/*     */     
/* 178 */     return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
/*     */   }
/*     */   
/*     */   public static float getModifierForCreature(ItemStack p_152377_0_, EnumCreatureAttribute p_152377_1_) {
/* 182 */     enchantmentModifierLiving.livingModifier = 0.0F;
/* 183 */     enchantmentModifierLiving.entityLiving = p_152377_1_;
/* 184 */     applyEnchantmentModifier(enchantmentModifierLiving, p_152377_0_);
/* 185 */     return enchantmentModifierLiving.livingModifier;
/*     */   }
/*     */   
/*     */   public static void applyThornEnchantments(EntityLivingBase p_151384_0_, Entity p_151384_1_) {
/* 189 */     ENCHANTMENT_ITERATOR_HURT.attacker = p_151384_1_;
/* 190 */     ENCHANTMENT_ITERATOR_HURT.user = p_151384_0_;
/*     */     
/* 192 */     if (p_151384_0_ != null) {
/* 193 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getInventory());
/*     */     }
/*     */     
/* 196 */     if (p_151384_1_ instanceof net.minecraft.entity.player.EntityPlayer) {
/* 197 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getHeldItem());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void applyArthropodEnchantments(EntityLivingBase p_151385_0_, Entity p_151385_1_) {
/* 202 */     ENCHANTMENT_ITERATOR_DAMAGE.user = p_151385_0_;
/* 203 */     ENCHANTMENT_ITERATOR_DAMAGE.target = p_151385_1_;
/*     */     
/* 205 */     if (p_151385_0_ != null) {
/* 206 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getInventory());
/*     */     }
/*     */     
/* 209 */     if (p_151385_0_ instanceof net.minecraft.entity.player.EntityPlayer) {
/* 210 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getHeldItem());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getKnockbackModifier(EntityLivingBase player) {
/* 218 */     return getEnchantmentLevel(Enchantment.knockback.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFireAspectModifier(EntityLivingBase player) {
/* 225 */     return getEnchantmentLevel(Enchantment.fireAspect.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getRespiration(Entity player) {
/* 232 */     return getMaxEnchantmentLevel(Enchantment.respiration.effectId, player.getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDepthStriderModifier(Entity player) {
/* 239 */     return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, player.getInventory());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEfficiencyModifier(EntityLivingBase player) {
/* 246 */     return getEnchantmentLevel(Enchantment.efficiency.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getSilkTouchModifier(EntityLivingBase player) {
/* 253 */     return (getEnchantmentLevel(Enchantment.silkTouch.effectId, player.getHeldItem()) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFortuneModifier(EntityLivingBase player) {
/* 260 */     return getEnchantmentLevel(Enchantment.fortune.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLuckOfSeaModifier(EntityLivingBase player) {
/* 267 */     return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLureModifier(EntityLivingBase player) {
/* 274 */     return getEnchantmentLevel(Enchantment.lure.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLootingModifier(EntityLivingBase player) {
/* 281 */     return getEnchantmentLevel(Enchantment.looting.effectId, player.getHeldItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getAquaAffinityModifier(EntityLivingBase player) {
/* 288 */     return (getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, player.getInventory()) > 0); } public static ItemStack getEnchantedItem(Enchantment p_92099_0_, EntityLivingBase p_92099_1_) {
/*     */     byte b;
/*     */     int i;
/*     */     ItemStack[] arrayOfItemStack;
/* 292 */     for (i = (arrayOfItemStack = p_92099_1_.getInventory()).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/* 293 */       if (itemstack != null && getEnchantmentLevel(p_92099_0_.effectId, itemstack) > 0) {
/* 294 */         return itemstack;
/*     */       }
/*     */       b++; }
/*     */     
/* 298 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcItemStackEnchantability(Random rand, int enchantNum, int power, ItemStack stack) {
/* 306 */     Item item = stack.getItem();
/* 307 */     int i = item.getItemEnchantability();
/*     */     
/* 309 */     if (i <= 0) {
/* 310 */       return 0;
/*     */     }
/* 312 */     if (power > 15) {
/* 313 */       power = 15;
/*     */     }
/*     */     
/* 316 */     int j = rand.nextInt(8) + 1 + (power >> 1) + rand.nextInt(power + 1);
/* 317 */     return (enchantNum == 0) ? Math.max(j / 3, 1) : ((enchantNum == 1) ? (j * 2 / 3 + 1) : Math.max(j, power * 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack addRandomEnchantment(Random p_77504_0_, ItemStack p_77504_1_, int p_77504_2_) {
/* 325 */     List<EnchantmentData> list = buildEnchantmentList(p_77504_0_, p_77504_1_, p_77504_2_);
/* 326 */     boolean flag = (p_77504_1_.getItem() == Items.book);
/*     */     
/* 328 */     if (flag) {
/* 329 */       p_77504_1_.setItem((Item)Items.enchanted_book);
/*     */     }
/*     */     
/* 332 */     if (list != null) {
/* 333 */       for (EnchantmentData enchantmentdata : list) {
/* 334 */         if (flag) {
/* 335 */           Items.enchanted_book.addEnchantment(p_77504_1_, enchantmentdata); continue;
/*     */         } 
/* 337 */         p_77504_1_.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 342 */     return p_77504_1_;
/*     */   }
/*     */   
/*     */   public static List<EnchantmentData> buildEnchantmentList(Random randomIn, ItemStack itemStackIn, int p_77513_2_) {
/* 346 */     Item item = itemStackIn.getItem();
/* 347 */     int i = item.getItemEnchantability();
/*     */     
/* 349 */     if (i <= 0) {
/* 350 */       return null;
/*     */     }
/* 352 */     i /= 2;
/* 353 */     i = 1 + randomIn.nextInt((i >> 1) + 1) + randomIn.nextInt((i >> 1) + 1);
/* 354 */     int j = i + p_77513_2_;
/* 355 */     float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
/* 356 */     int k = (int)(j * (1.0F + f) + 0.5F);
/*     */     
/* 358 */     if (k < 1) {
/* 359 */       k = 1;
/*     */     }
/*     */     
/* 362 */     List<EnchantmentData> list = null;
/* 363 */     Map<Integer, EnchantmentData> map = mapEnchantmentData(k, itemStackIn);
/*     */     
/* 365 */     if (map != null && !map.isEmpty()) {
/* 366 */       EnchantmentData enchantmentdata = (EnchantmentData)WeightedRandom.getRandomItem(randomIn, map.values());
/*     */       
/* 368 */       if (enchantmentdata != null) {
/* 369 */         list = Lists.newArrayList();
/* 370 */         list.add(enchantmentdata);
/*     */         
/* 372 */         for (int l = k; randomIn.nextInt(50) <= l; l >>= 1) {
/* 373 */           Iterator<Integer> iterator = map.keySet().iterator();
/*     */           
/* 375 */           while (iterator.hasNext()) {
/* 376 */             Integer integer = iterator.next();
/* 377 */             boolean flag = true;
/*     */             
/* 379 */             for (EnchantmentData enchantmentdata1 : list) {
/* 380 */               if (!enchantmentdata1.enchantmentobj.canApplyTogether(Enchantment.getEnchantmentById(integer.intValue()))) {
/* 381 */                 flag = false;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 386 */             if (!flag) {
/* 387 */               iterator.remove();
/*     */             }
/*     */           } 
/*     */           
/* 391 */           if (!map.isEmpty()) {
/* 392 */             EnchantmentData enchantmentdata2 = (EnchantmentData)WeightedRandom.getRandomItem(randomIn, map.values());
/* 393 */             list.add(enchantmentdata2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 399 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<Integer, EnchantmentData> mapEnchantmentData(int p_77505_0_, ItemStack p_77505_1_) {
/* 404 */     Item item = p_77505_1_.getItem();
/* 405 */     Map<Integer, EnchantmentData> map = null;
/* 406 */     boolean flag = (p_77505_1_.getItem() == Items.book); byte b; int i;
/*     */     Enchantment[] arrayOfEnchantment;
/* 408 */     for (i = (arrayOfEnchantment = Enchantment.enchantmentsBookList).length, b = 0; b < i; ) { Enchantment enchantment = arrayOfEnchantment[b];
/* 409 */       if (enchantment != null && (enchantment.type.canEnchantItem(item) || flag)) {
/* 410 */         for (int j = enchantment.getMinLevel(); j <= enchantment.getMaxLevel(); j++) {
/* 411 */           if (p_77505_0_ >= enchantment.getMinEnchantability(j) && p_77505_0_ <= enchantment.getMaxEnchantability(j)) {
/* 412 */             if (map == null) {
/* 413 */               map = Maps.newHashMap();
/*     */             }
/*     */             
/* 416 */             map.put(Integer.valueOf(enchantment.effectId), new EnchantmentData(enchantment, j));
/*     */           } 
/*     */         } 
/*     */       }
/*     */       b++; }
/*     */     
/* 422 */     return map;
/*     */   }
/*     */   
/*     */   static final class DamageIterator
/*     */     implements IModifier {
/*     */     public EntityLivingBase user;
/*     */     public Entity target;
/*     */     
/*     */     private DamageIterator() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 433 */       enchantmentIn.onEntityDamaged(this.user, this.target, enchantmentLevel);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class HurtIterator
/*     */     implements IModifier {
/*     */     public EntityLivingBase user;
/*     */     public Entity attacker;
/*     */     
/*     */     private HurtIterator() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 445 */       enchantmentIn.onUserHurt(this.user, this.attacker, enchantmentLevel);
/*     */     }
/*     */   }
/*     */   
/*     */   static interface IModifier {
/*     */     void calculateModifier(Enchantment param1Enchantment, int param1Int);
/*     */   }
/*     */   
/*     */   static final class ModifierDamage
/*     */     implements IModifier {
/*     */     public int damageModifier;
/*     */     public DamageSource source;
/*     */     
/*     */     private ModifierDamage() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 461 */       this.damageModifier += enchantmentIn.calcModifierDamage(enchantmentLevel, this.source);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class ModifierLiving
/*     */     implements IModifier {
/*     */     public float livingModifier;
/*     */     public EnumCreatureAttribute entityLiving;
/*     */     
/*     */     private ModifierLiving() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 473 */       this.livingModifier += enchantmentIn.calcDamageByCreature(enchantmentLevel, this.entityLiving);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnchantmentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */