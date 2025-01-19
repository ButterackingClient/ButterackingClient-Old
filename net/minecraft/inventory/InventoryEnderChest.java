/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ 
/*    */ public class InventoryEnderChest
/*    */   extends InventoryBasic {
/*    */   public InventoryEnderChest() {
/* 13 */     super("container.enderchest", false, 27);
/*    */   }
/*    */   private TileEntityEnderChest associatedChest;
/*    */   public void setChestTileEntity(TileEntityEnderChest chestTileEntity) {
/* 17 */     this.associatedChest = chestTileEntity;
/*    */   }
/*    */   
/*    */   public void loadInventoryFromNBT(NBTTagList p_70486_1_) {
/* 21 */     for (int i = 0; i < getSizeInventory(); i++) {
/* 22 */       setInventorySlotContents(i, null);
/*    */     }
/*    */     
/* 25 */     for (int k = 0; k < p_70486_1_.tagCount(); k++) {
/* 26 */       NBTTagCompound nbttagcompound = p_70486_1_.getCompoundTagAt(k);
/* 27 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*    */       
/* 29 */       if (j >= 0 && j < getSizeInventory()) {
/* 30 */         setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public NBTTagList saveInventoryToNBT() {
/* 36 */     NBTTagList nbttaglist = new NBTTagList();
/*    */     
/* 38 */     for (int i = 0; i < getSizeInventory(); i++) {
/* 39 */       ItemStack itemstack = getStackInSlot(i);
/*    */       
/* 41 */       if (itemstack != null) {
/* 42 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 43 */         nbttagcompound.setByte("Slot", (byte)i);
/* 44 */         itemstack.writeToNBT(nbttagcompound);
/* 45 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     return nbttaglist;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 56 */     return (this.associatedChest != null && !this.associatedChest.canBeUsed(player)) ? false : super.isUseableByPlayer(player);
/*    */   }
/*    */   
/*    */   public void openInventory(EntityPlayer player) {
/* 60 */     if (this.associatedChest != null) {
/* 61 */       this.associatedChest.openChest();
/*    */     }
/*    */     
/* 64 */     super.openInventory(player);
/*    */   }
/*    */   
/*    */   public void closeInventory(EntityPlayer player) {
/* 68 */     if (this.associatedChest != null) {
/* 69 */       this.associatedChest.closeChest();
/*    */     }
/*    */     
/* 72 */     super.closeInventory(player);
/* 73 */     this.associatedChest = null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\InventoryEnderChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */