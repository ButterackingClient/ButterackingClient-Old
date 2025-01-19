/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemAppleGold
/*    */   extends ItemFood {
/*    */   public ItemAppleGold(int amount, float saturation, boolean isWolfFood) {
/* 13 */     super(amount, saturation, isWolfFood);
/* 14 */     setHasSubtypes(true);
/*    */   }
/*    */   
/*    */   public boolean hasEffect(ItemStack stack) {
/* 18 */     return (stack.getMetadata() > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumRarity getRarity(ItemStack stack) {
/* 25 */     return (stack.getMetadata() == 0) ? EnumRarity.RARE : EnumRarity.EPIC;
/*    */   }
/*    */   
/*    */   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
/* 29 */     if (!worldIn.isRemote) {
/* 30 */       player.addPotionEffect(new PotionEffect(Potion.absorption.id, 2400, 0));
/*    */     }
/*    */     
/* 33 */     if (stack.getMetadata() > 0) {
/* 34 */       if (!worldIn.isRemote) {
/* 35 */         player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
/* 36 */         player.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
/* 37 */         player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
/*    */       } 
/*    */     } else {
/* 40 */       super.onFoodEaten(stack, worldIn, player);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 48 */     subItems.add(new ItemStack(itemIn, 1, 0));
/* 49 */     subItems.add(new ItemStack(itemIn, 1, 1));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemAppleGold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */