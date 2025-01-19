/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class TileEntityFlowerPot
/*    */   extends TileEntity {
/*    */   private Item flowerPotItem;
/*    */   private int flowerPotData;
/*    */   
/*    */   public TileEntityFlowerPot() {}
/*    */   
/*    */   public TileEntityFlowerPot(Item potItem, int potData) {
/* 17 */     this.flowerPotItem = potItem;
/* 18 */     this.flowerPotData = potData;
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 22 */     super.writeToNBT(compound);
/* 23 */     ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.flowerPotItem);
/* 24 */     compound.setString("Item", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 25 */     compound.setInteger("Data", this.flowerPotData);
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 29 */     super.readFromNBT(compound);
/*    */     
/* 31 */     if (compound.hasKey("Item", 8)) {
/* 32 */       this.flowerPotItem = Item.getByNameOrId(compound.getString("Item"));
/*    */     } else {
/* 34 */       this.flowerPotItem = Item.getItemById(compound.getInteger("Item"));
/*    */     } 
/*    */     
/* 37 */     this.flowerPotData = compound.getInteger("Data");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet getDescriptionPacket() {
/* 45 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 46 */     writeToNBT(nbttagcompound);
/* 47 */     nbttagcompound.removeTag("Item");
/* 48 */     nbttagcompound.setInteger("Item", Item.getIdFromItem(this.flowerPotItem));
/* 49 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 5, nbttagcompound);
/*    */   }
/*    */   
/*    */   public void setFlowerPotData(Item potItem, int potData) {
/* 53 */     this.flowerPotItem = potItem;
/* 54 */     this.flowerPotData = potData;
/*    */   }
/*    */   
/*    */   public Item getFlowerPotItem() {
/* 58 */     return this.flowerPotItem;
/*    */   }
/*    */   
/*    */   public int getFlowerPotData() {
/* 62 */     return this.flowerPotData;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityFlowerPot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */