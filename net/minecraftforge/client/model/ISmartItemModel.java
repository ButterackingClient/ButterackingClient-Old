package net.minecraftforge.client.model;

import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;

public interface ISmartItemModel extends IBakedModel {
  IBakedModel handleItemState(ItemStack paramItemStack);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraftforge\client\model\ISmartItemModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */