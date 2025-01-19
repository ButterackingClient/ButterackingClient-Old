/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C10PacketCreativeInventoryAction
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private int slotId;
/*    */   private ItemStack stack;
/*    */   
/*    */   public C10PacketCreativeInventoryAction() {}
/*    */   
/*    */   public C10PacketCreativeInventoryAction(int slotIdIn, ItemStack stackIn) {
/* 18 */     this.slotId = slotIdIn;
/* 19 */     this.stack = (stackIn != null) ? stackIn.copy() : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 26 */     handler.processCreativeInventoryAction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 33 */     this.slotId = buf.readShort();
/* 34 */     this.stack = buf.readItemStackFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeShort(this.slotId);
/* 42 */     buf.writeItemStackToBuffer(this.stack);
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 46 */     return this.slotId;
/*    */   }
/*    */   
/*    */   public ItemStack getStack() {
/* 50 */     return this.stack;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C10PacketCreativeInventoryAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */