/*    */ package net.minecraft.client.player.inventory;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.InventoryBasic;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.ILockableContainer;
/*    */ import net.minecraft.world.LockCode;
/*    */ 
/*    */ public class ContainerLocalMenu
/*    */   extends InventoryBasic
/*    */   implements ILockableContainer {
/*    */   private String guiID;
/* 17 */   private Map<Integer, Integer> field_174895_b = Maps.newHashMap();
/*    */   
/*    */   public ContainerLocalMenu(String id, IChatComponent title, int slotCount) {
/* 20 */     super(title, slotCount);
/* 21 */     this.guiID = id;
/*    */   }
/*    */   
/*    */   public int getField(int id) {
/* 25 */     return this.field_174895_b.containsKey(Integer.valueOf(id)) ? ((Integer)this.field_174895_b.get(Integer.valueOf(id))).intValue() : 0;
/*    */   }
/*    */   
/*    */   public void setField(int id, int value) {
/* 29 */     this.field_174895_b.put(Integer.valueOf(id), Integer.valueOf(value));
/*    */   }
/*    */   
/*    */   public int getFieldCount() {
/* 33 */     return this.field_174895_b.size();
/*    */   }
/*    */   
/*    */   public boolean isLocked() {
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLockCode(LockCode code) {}
/*    */   
/*    */   public LockCode getLockCode() {
/* 44 */     return LockCode.EMPTY_CODE;
/*    */   }
/*    */   
/*    */   public String getGuiID() {
/* 48 */     return this.guiID;
/*    */   }
/*    */   
/*    */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 52 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\player\inventory\ContainerLocalMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */