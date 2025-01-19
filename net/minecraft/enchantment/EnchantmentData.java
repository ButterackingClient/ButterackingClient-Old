/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.WeightedRandom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentData
/*    */   extends WeightedRandom.Item
/*    */ {
/*    */   public final Enchantment enchantmentobj;
/*    */   public final int enchantmentLevel;
/*    */   
/*    */   public EnchantmentData(Enchantment enchantmentObj, int enchLevel) {
/* 17 */     super(enchantmentObj.getWeight());
/* 18 */     this.enchantmentobj = enchantmentObj;
/* 19 */     this.enchantmentLevel = enchLevel;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnchantmentData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */