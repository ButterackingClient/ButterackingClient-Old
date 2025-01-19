/*   */ package net.optifine.config;
/*   */ 
/*   */ import net.minecraft.enchantment.Enchantment;
/*   */ 
/*   */ public class ParserEnchantmentId implements IParserInt {
/*   */   public int parse(String str, int defVal) {
/* 7 */     Enchantment enchantment = Enchantment.getEnchantmentByLocation(str);
/* 8 */     return (enchantment == null) ? defVal : enchantment.effectId;
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\ParserEnchantmentId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */