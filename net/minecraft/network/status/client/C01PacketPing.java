/*    */ package net.minecraft.network.status.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.status.INetHandlerStatusServer;
/*    */ 
/*    */ public class C01PacketPing
/*    */   implements Packet<INetHandlerStatusServer> {
/*    */   private long clientTime;
/*    */   
/*    */   public C01PacketPing() {}
/*    */   
/*    */   public C01PacketPing(long ping) {
/* 16 */     this.clientTime = ping;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 23 */     this.clientTime = buf.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 30 */     buf.writeLong(this.clientTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerStatusServer handler) {
/* 37 */     handler.processPing(this);
/*    */   }
/*    */   
/*    */   public long getClientTime() {
/* 41 */     return this.clientTime;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\status\client\C01PacketPing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */