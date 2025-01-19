/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentLootBonus extends Enchantment {
/*    */   protected EnchantmentLootBonus(int p_i45767_1_, ResourceLocation p_i45767_2_, int p_i45767_3_, EnumEnchantmentType p_i45767_4_) {
/*  7 */     super(p_i45767_1_, p_i45767_2_, p_i45767_3_, p_i45767_4_);
/*    */     
/*  9 */     if (p_i45767_4_ == EnumEnchantmentType.DIGGER) {
/* 10 */       setName("lootBonusDigger");
/* 11 */     } else if (p_i45767_4_ == EnumEnchantmentType.FISHING_ROD) {
/* 12 */       setName("lootBonusFishing");
/*    */     } else {
/* 14 */       setName("lootBonus");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 22 */     return 15 + (enchantmentLevel - 1) * 9;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 29 */     return super.getMinEnchantability(enchantmentLevel) + 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 36 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canApplyTogether(Enchantment ench) {
/* 43 */     return (super.canApplyTogether(ench) && ench.effectId != silkTouch.effectId);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnchantmentLootBonus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */