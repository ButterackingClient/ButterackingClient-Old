/*    */ package net.minecraft.item;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityExpBottle;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemExpBottle extends Item {
/*    */   public ItemExpBottle() {
/* 11 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */   
/*    */   public boolean hasEffect(ItemStack stack) {
/* 15 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 22 */     if (!playerIn.capabilities.isCreativeMode) {
/* 23 */       itemStackIn.stackSize--;
/*    */     }
/*    */     
/* 26 */     worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 28 */     if (!worldIn.isRemote) {
/* 29 */       worldIn.spawnEntityInWorld((Entity)new EntityExpBottle(worldIn, (EntityLivingBase)playerIn));
/*    */     }
/*    */     
/* 32 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 33 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemExpBottle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */