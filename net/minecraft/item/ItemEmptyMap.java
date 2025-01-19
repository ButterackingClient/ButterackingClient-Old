/*    */ package net.minecraft.item;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldSavedData;
/*    */ import net.minecraft.world.storage.MapData;
/*    */ 
/*    */ public class ItemEmptyMap extends ItemMapBase {
/*    */   protected ItemEmptyMap() {
/* 12 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 19 */     ItemStack itemstack = new ItemStack(Items.filled_map, 1, worldIn.getUniqueDataId("map"));
/* 20 */     String s = "map_" + itemstack.getMetadata();
/* 21 */     MapData mapdata = new MapData(s);
/* 22 */     worldIn.setItemData(s, (WorldSavedData)mapdata);
/* 23 */     mapdata.scale = 0;
/* 24 */     mapdata.calculateMapCenter(playerIn.posX, playerIn.posZ, mapdata.scale);
/* 25 */     mapdata.dimension = (byte)worldIn.provider.getDimensionId();
/* 26 */     mapdata.markDirty();
/* 27 */     itemStackIn.stackSize--;
/*    */     
/* 29 */     if (itemStackIn.stackSize <= 0) {
/* 30 */       return itemstack;
/*    */     }
/* 32 */     if (!playerIn.inventory.addItemStackToInventory(itemstack.copy())) {
/* 33 */       playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*    */     }
/*    */     
/* 36 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 37 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemEmptyMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */