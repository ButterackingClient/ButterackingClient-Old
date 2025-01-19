/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentArrowFire extends Enchantment {
/*    */   public EnchantmentArrowFire(int enchID, ResourceLocation enchName, int enchWeight) {
/*  7 */     super(enchID, enchName, enchWeight, EnumEnchantmentType.BOW);
/*  8 */     setName("arrowFire");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 15 */     return 20;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 22 */     return 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 29 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnchantmentArrowFire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */