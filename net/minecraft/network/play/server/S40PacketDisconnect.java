/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class S40PacketDisconnect
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private IChatComponent reason;
/*    */   
/*    */   public S40PacketDisconnect() {}
/*    */   
/*    */   public S40PacketDisconnect(IChatComponent reasonIn) {
/* 17 */     this.reason = reasonIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 24 */     this.reason = buf.readChatComponent();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 31 */     buf.writeChatComponent(this.reason);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 38 */     handler.handleDisconnect(this);
/*    */   }
/*    */   
/*    */   public IChatComponent getReason() {
/* 42 */     return this.reason;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S40PacketDisconnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */