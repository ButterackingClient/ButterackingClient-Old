/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentArrowDamage extends Enchantment {
/*    */   public EnchantmentArrowDamage(int enchID, ResourceLocation enchName, int enchWeight) {
/*  7 */     super(enchID, enchName, enchWeight, EnumEnchantmentType.BOW);
/*  8 */     setName("arrowDamage");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 15 */     return 1 + (enchantmentLevel - 1) * 10;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 22 */     return getMinEnchantability(enchantmentLevel) + 15;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 29 */     return 5;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnchantmentArrowDamage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */