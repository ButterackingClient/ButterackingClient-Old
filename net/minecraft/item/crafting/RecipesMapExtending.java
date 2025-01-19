/*    */ package net.minecraft.item.crafting;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.MapData;
/*    */ 
/*    */ public class RecipesMapExtending extends ShapedRecipes {
/*    */   public RecipesMapExtending() {
/* 12 */     super(3, 3, new ItemStack[] { new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack((Item)Items.filled_map, 0, 32767), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper) }new ItemStack((Item)Items.map, 0, 0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(InventoryCrafting inv, World worldIn) {
/* 19 */     if (!super.matches(inv, worldIn)) {
/* 20 */       return false;
/*    */     }
/* 22 */     ItemStack itemstack = null;
/*    */     
/* 24 */     for (int i = 0; i < inv.getSizeInventory() && itemstack == null; i++) {
/* 25 */       ItemStack itemstack1 = inv.getStackInSlot(i);
/*    */       
/* 27 */       if (itemstack1 != null && itemstack1.getItem() == Items.filled_map) {
/* 28 */         itemstack = itemstack1;
/*    */       }
/*    */     } 
/*    */     
/* 32 */     if (itemstack == null) {
/* 33 */       return false;
/*    */     }
/* 35 */     MapData mapdata = Items.filled_map.getMapData(itemstack, worldIn);
/* 36 */     return (mapdata == null) ? false : ((mapdata.scale < 4));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 45 */     ItemStack itemstack = null;
/*    */     
/* 47 */     for (int i = 0; i < inv.getSizeInventory() && itemstack == null; i++) {
/* 48 */       ItemStack itemstack1 = inv.getStackInSlot(i);
/*    */       
/* 50 */       if (itemstack1 != null && itemstack1.getItem() == Items.filled_map) {
/* 51 */         itemstack = itemstack1;
/*    */       }
/*    */     } 
/*    */     
/* 55 */     itemstack = itemstack.copy();
/* 56 */     itemstack.stackSize = 1;
/*    */     
/* 58 */     if (itemstack.getTagCompound() == null) {
/* 59 */       itemstack.setTagCompound(new NBTTagCompound());
/*    */     }
/*    */     
/* 62 */     itemstack.getTagCompound().setBoolean("map_is_scaling", true);
/* 63 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipesMapExtending.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */