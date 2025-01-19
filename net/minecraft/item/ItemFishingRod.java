/*    */ package net.minecraft.item;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.projectile.EntityFishHook;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemFishingRod extends Item {
/*    */   public ItemFishingRod() {
/* 11 */     setMaxDamage(64);
/* 12 */     setMaxStackSize(1);
/* 13 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull3D() {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldRotateAroundWhenRendering() {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 35 */     if (playerIn.fishEntity != null) {
/* 36 */       int i = playerIn.fishEntity.handleHookRetraction();
/* 37 */       itemStackIn.damageItem(i, (EntityLivingBase)playerIn);
/* 38 */       playerIn.swingItem();
/*    */     } else {
/* 40 */       worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*    */       
/* 42 */       if (!worldIn.isRemote) {
/* 43 */         worldIn.spawnEntityInWorld((Entity)new EntityFishHook(worldIn, playerIn));
/*    */       }
/*    */       
/* 46 */       playerIn.swingItem();
/* 47 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*    */     } 
/*    */     
/* 50 */     return itemStackIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isItemTool(ItemStack stack) {
/* 57 */     return super.isItemTool(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemEnchantability() {
/* 64 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemFishingRod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */