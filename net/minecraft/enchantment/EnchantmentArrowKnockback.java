/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentArrowKnockback extends Enchantment {
/*    */   public EnchantmentArrowKnockback(int enchID, ResourceLocation enchName, int enchWeight) {
/*  7 */     super(enchID, enchName, enchWeight, EnumEnchantmentType.BOW);
/*  8 */     setName("arrowKnockback");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 15 */     return 12 + (enchantmentLevel - 1) * 20;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 22 */     return getMinEnchantability(enchantmentLevel) + 25;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 29 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnchantmentArrowKnockback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */