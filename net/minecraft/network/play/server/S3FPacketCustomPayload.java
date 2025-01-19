/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S3FPacketCustomPayload
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String channel;
/*    */   private PacketBuffer data;
/*    */   
/*    */   public S3FPacketCustomPayload() {}
/*    */   
/*    */   public S3FPacketCustomPayload(String channelName, PacketBuffer dataIn) {
/* 19 */     this.channel = channelName;
/* 20 */     this.data = dataIn;
/*    */     
/* 22 */     if (dataIn.writerIndex() > 1048576) {
/* 23 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
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
/* 34 */     if (i >= 0 && i <= 1048576) {
/* 35 */       this.data = new PacketBuffer(buf.readBytes(i));
/*    */     } else {
/* 37 */       throw new IOException("Payload may not be larger than 1048576 bytes");
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
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 53 */     handler.handleCustomPayload(this);
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


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S3FPacketCustomPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */