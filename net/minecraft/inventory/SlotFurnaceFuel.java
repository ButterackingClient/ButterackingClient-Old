/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntityFurnace;
/*    */ 
/*    */ public class SlotFurnaceFuel extends Slot {
/*    */   public SlotFurnaceFuel(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
/*  9 */     super(inventoryIn, slotIndex, xPosition, yPosition);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isItemValid(ItemStack stack) {
/* 16 */     return !(!TileEntityFurnace.isItemFuel(stack) && !isBucket(stack));
/*    */   }
/*    */   
/*    */   public int getItemStackLimit(ItemStack stack) {
/* 20 */     return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
/*    */   }
/*    */   
/*    */   public static boolean isBucket(ItemStack stack) {
/* 24 */     return (stack != null && stack.getItem() != null && stack.getItem() == Items.bucket);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\SlotFurnaceFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */