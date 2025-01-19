/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemGlassBottle extends Item {
/*    */   public ItemGlassBottle() {
/* 14 */     setCreativeTab(CreativeTabs.tabBrewing);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 21 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/*    */     
/* 23 */     if (movingobjectposition == null) {
/* 24 */       return itemStackIn;
/*    */     }
/* 26 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 27 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*    */       
/* 29 */       if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
/* 30 */         return itemStackIn;
/*    */       }
/*    */       
/* 33 */       if (!playerIn.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, itemStackIn)) {
/* 34 */         return itemStackIn;
/*    */       }
/*    */       
/* 37 */       if (worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.water) {
/* 38 */         itemStackIn.stackSize--;
/* 39 */         playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*    */         
/* 41 */         if (itemStackIn.stackSize <= 0) {
/* 42 */           return new ItemStack(Items.potionitem);
/*    */         }
/*    */         
/* 45 */         if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
/* 46 */           playerIn.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemGlassBottle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */