/*    */ package net.minecraft.item;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.projectile.EntitySnowball;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSnowball extends Item {
/*    */   public ItemSnowball() {
/* 11 */     this.maxStackSize = 16;
/* 12 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 19 */     if (!playerIn.capabilities.isCreativeMode) {
/* 20 */       itemStackIn.stackSize--;
/*    */     }
/*    */     
/* 23 */     worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */     
/* 25 */     if (!worldIn.isRemote) {
/* 26 */       worldIn.spawnEntityInWorld((Entity)new EntitySnowball(worldIn, (EntityLivingBase)playerIn));
/*    */     }
/*    */     
/* 29 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 30 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSnowball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */