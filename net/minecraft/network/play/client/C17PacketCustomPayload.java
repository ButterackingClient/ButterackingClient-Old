/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C17PacketCustomPayload
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private String channel;
/*    */   private PacketBuffer data;
/*    */   
/*    */   public C17PacketCustomPayload() {}
/*    */   
/*    */   public C17PacketCustomPayload(String channelIn, PacketBuffer dataIn) {
/* 19 */     this.channel = channelIn;
/* 20 */     this.data = dataIn;
/*    */     
/* 22 */     if (dataIn.writerIndex() > 32767) {
/* 23 */       throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 31 */     this.channel = buf.readStringFromBuffer(20);
/* 32 */     int i = buf.readableBytes();
/*    */     
/* 34 */     if (i >= 0 && i <= 32767) {
/* 35 */       this.data = new PacketBuffer(buf.readBytes(i));
/*    */     } else {
/* 37 */       throw new IOException("Payload may not be larger than 32767 bytes");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 45 */     buf.writeString(this.channel);
/* 46 */     buf.writeBytes((ByteBuf)this.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 53 */     handler.processVanilla250Packet(this);
/*    */   }
/*    */   
/*    */   public String getChannelName() {
/* 57 */     return this.channel;
/*    */   }
/*    */   
/*    */   public PacketBuffer getBufferData() {
/* 61 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C17PacketCustomPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */