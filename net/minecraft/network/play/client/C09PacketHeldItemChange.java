/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C09PacketHeldItemChange
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private int slotId;
/*    */   
/*    */   public C09PacketHeldItemChange() {}
/*    */   
/*    */   public C09PacketHeldItemChange(int slotId) {
/* 16 */     this.slotId = slotId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 23 */     this.slotId = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 30 */     buf.writeShort(this.slotId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 37 */     handler.processHeldItemChange(this);
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 41 */     return this.slotId;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C09PacketHeldItemChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */