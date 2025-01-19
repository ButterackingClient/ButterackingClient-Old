/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumEnchantmentType
/*    */ {
/* 11 */   ALL,
/* 12 */   ARMOR,
/* 13 */   ARMOR_FEET,
/* 14 */   ARMOR_LEGS,
/* 15 */   ARMOR_TORSO,
/* 16 */   ARMOR_HEAD,
/* 17 */   WEAPON,
/* 18 */   DIGGER,
/* 19 */   FISHING_ROD,
/* 20 */   BREAKABLE,
/* 21 */   BOW;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canEnchantItem(Item p_77557_1_) {
/* 27 */     if (this == ALL)
/* 28 */       return true; 
/* 29 */     if (this == BREAKABLE && p_77557_1_.isDamageable())
/* 30 */       return true; 
/* 31 */     if (p_77557_1_ instanceof ItemArmor) {
/* 32 */       if (this == ARMOR) {
/* 33 */         return true;
/*    */       }
/* 35 */       ItemArmor itemarmor = (ItemArmor)p_77557_1_;
/* 36 */       return (itemarmor.armorType == 0) ? ((this == ARMOR_HEAD)) : ((itemarmor.armorType == 2) ? ((this == ARMOR_LEGS)) : ((itemarmor.armorType == 1) ? ((this == ARMOR_TORSO)) : ((itemarmor.armorType == 3) ? ((this == ARMOR_FEET)) : false)));
/*    */     } 
/*    */     
/* 39 */     return (p_77557_1_ instanceof net.minecraft.item.ItemSword) ? ((this == WEAPON)) : ((p_77557_1_ instanceof net.minecraft.item.ItemTool) ? ((this == DIGGER)) : ((p_77557_1_ instanceof net.minecraft.item.ItemBow) ? ((this == BOW)) : ((p_77557_1_ instanceof net.minecraft.item.ItemFishingRod) ? ((this == FISHING_ROD)) : false)));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\enchantment\EnumEnchantmentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */