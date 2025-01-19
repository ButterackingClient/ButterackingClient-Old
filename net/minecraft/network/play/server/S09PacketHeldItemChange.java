/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S09PacketHeldItemChange
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int heldItemHotbarIndex;
/*    */   
/*    */   public S09PacketHeldItemChange() {}
/*    */   
/*    */   public S09PacketHeldItemChange(int hotbarIndexIn) {
/* 16 */     this.heldItemHotbarIndex = hotbarIndexIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 23 */     this.heldItemHotbarIndex = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 30 */     buf.writeByte(this.heldItemHotbarIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 37 */     handler.handleHeldItemChange(this);
/*    */   }
/*    */   
/*    */   public int getHeldItemHotbarIndex() {
/* 41 */     return this.heldItemHotbarIndex;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S09PacketHeldItemChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */