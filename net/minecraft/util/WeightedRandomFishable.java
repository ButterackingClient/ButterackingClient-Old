/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class WeightedRandomFishable
/*    */   extends WeightedRandom.Item {
/*    */   private final ItemStack returnStack;
/*    */   private float maxDamagePercent;
/*    */   private boolean enchantable;
/*    */   
/*    */   public WeightedRandomFishable(ItemStack returnStackIn, int itemWeightIn) {
/* 14 */     super(itemWeightIn);
/* 15 */     this.returnStack = returnStackIn;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack(Random random) {
/* 19 */     ItemStack itemstack = this.returnStack.copy();
/*    */     
/* 21 */     if (this.maxDamagePercent > 0.0F) {
/* 22 */       int i = (int)(this.maxDamagePercent * this.returnStack.getMaxDamage());
/* 23 */       int j = itemstack.getMaxDamage() - random.nextInt(random.nextInt(i) + 1);
/*    */       
/* 25 */       if (j > i) {
/* 26 */         j = i;
/*    */       }
/*    */       
/* 29 */       if (j < 1) {
/* 30 */         j = 1;
/*    */       }
/*    */       
/* 33 */       itemstack.setItemDamage(j);
/*    */     } 
/*    */     
/* 36 */     if (this.enchantable) {
/* 37 */       EnchantmentHelper.addRandomEnchantment(random, itemstack, 30);
/*    */     }
/*    */     
/* 40 */     return itemstack;
/*    */   }
/*    */   
/*    */   public WeightedRandomFishable setMaxDamagePercent(float maxDamagePercentIn) {
/* 44 */     this.maxDamagePercent = maxDamagePercentIn;
/* 45 */     return this;
/*    */   }
/*    */   
/*    */   public WeightedRandomFishable setEnchantable() {
/* 49 */     this.enchantable = true;
/* 50 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\WeightedRandomFishable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */