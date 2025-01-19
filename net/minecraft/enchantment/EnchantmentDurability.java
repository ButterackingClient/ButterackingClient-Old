/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentDurability
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentDurability(int enchID, ResourceLocation enchName, int enchWeight) {
/* 11 */     super(enchID, enchName, enchWeight, EnumEnchantmentType.BREAKABLE);
/* 12 */     setName("durability");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 19 */     return 5 + (enchantmentLevel - 1) * 8;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 26 */     return super.getMinEnchantability(enchantmentLevel) + 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 33 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canApply(ItemStack stack) {
/* 40 */     return stack.isItemStackDamageable() ? true : super.canApply(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean negateDamage(ItemStack p_92097_0_, int p_92097_1_, Random p_92097_2_) {
/* 49 */     return (p_92097_0_.getItem() instanceof net.minecraft.item.ItemArmor && p_92097_2_.nextFloat() < 0.6F) ? false : ((p_92097_2_.nextInt(p_92097_1_ + 1) > 0));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnchantmentDurability.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */