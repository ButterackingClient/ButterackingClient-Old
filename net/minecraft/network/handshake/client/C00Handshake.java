/*    */ package net.minecraft.network.handshake.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*    */ 
/*    */ public class C00Handshake
/*    */   implements Packet<INetHandlerHandshakeServer> {
/*    */   private int protocolVersion;
/*    */   private String ip;
/*    */   private int port;
/*    */   private EnumConnectionState requestedState;
/*    */   
/*    */   public C00Handshake() {}
/*    */   
/*    */   public C00Handshake(int version, String ip, int port, EnumConnectionState requestedState) {
/* 20 */     this.protocolVersion = version;
/* 21 */     this.ip = ip;
/* 22 */     this.port = port;
/* 23 */     this.requestedState = requestedState;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.protocolVersion = buf.readVarIntFromBuffer();
/* 31 */     this.ip = buf.readStringFromBuffer(255);
/* 32 */     this.port = buf.readUnsignedShort();
/* 33 */     this.requestedState = EnumConnectionState.getById(buf.readVarIntFromBuffer());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 40 */     buf.writeVarIntToBuffer(this.protocolVersion);
/* 41 */     buf.writeString(this.ip);
/* 42 */     buf.writeShort(this.port);
/* 43 */     buf.writeVarIntToBuffer(this.requestedState.getId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerHandshakeServer handler) {
/* 50 */     handler.processHandshake(this);
/*    */   }
/*    */   
/*    */   public EnumConnectionState getRequestedState() {
/* 54 */     return this.requestedState;
/*    */   }
/*    */   
/*    */   public int getProtocolVersion() {
/* 58 */     return this.protocolVersion;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\handshake\client\C00Handshake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */