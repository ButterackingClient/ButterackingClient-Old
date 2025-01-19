/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBucketMilk extends Item {
/*    */   public ItemBucketMilk() {
/* 11 */     setMaxStackSize(1);
/* 12 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/* 20 */     if (!playerIn.capabilities.isCreativeMode) {
/* 21 */       stack.stackSize--;
/*    */     }
/*    */     
/* 24 */     if (!worldIn.isRemote) {
/* 25 */       playerIn.clearActivePotions();
/*    */     }
/*    */     
/* 28 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 29 */     return (stack.stackSize <= 0) ? new ItemStack(Items.bucket) : stack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxItemUseDuration(ItemStack stack) {
/* 36 */     return 32;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumAction getItemUseAction(ItemStack stack) {
/* 43 */     return EnumAction.DRINK;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 50 */     playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/* 51 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBucketMilk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */