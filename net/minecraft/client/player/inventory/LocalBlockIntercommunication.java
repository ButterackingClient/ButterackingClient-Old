/*    */ package net.minecraft.client.player.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.IInteractionObject;
/*    */ 
/*    */ public class LocalBlockIntercommunication implements IInteractionObject {
/*    */   private String guiID;
/*    */   private IChatComponent displayName;
/*    */   
/*    */   public LocalBlockIntercommunication(String guiIdIn, IChatComponent displayNameIn) {
/* 14 */     this.guiID = guiIdIn;
/* 15 */     this.displayName = displayNameIn;
/*    */   }
/*    */   
/*    */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 19 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 26 */     return this.displayName.getUnformattedText();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasCustomName() {
/* 33 */     return true;
/*    */   }
/*    */   
/*    */   public String getGuiID() {
/* 37 */     return this.guiID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDisplayName() {
/* 44 */     return this.displayName;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\player\inventory\LocalBlockIntercommunication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */