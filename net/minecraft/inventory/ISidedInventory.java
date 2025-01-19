package net.minecraft.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface ISidedInventory extends IInventory {
  int[] getSlotsForFace(EnumFacing paramEnumFacing);
  
  boolean canInsertItem(int paramInt, ItemStack paramItemStack, EnumFacing paramEnumFacing);
  
  boolean canExtractItem(int paramInt, ItemStack paramItemStack, EnumFacing paramEnumFacing);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ISidedInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */