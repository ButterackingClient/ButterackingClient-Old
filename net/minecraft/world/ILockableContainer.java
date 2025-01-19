package net.minecraft.world;

import net.minecraft.inventory.IInventory;

public interface ILockableContainer extends IInventory, IInteractionObject {
  boolean isLocked();
  
  void setLockCode(LockCode paramLockCode);
  
  LockCode getLockCode();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\ILockableContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */