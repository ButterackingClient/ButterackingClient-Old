/*    */ package net.minecraft.util;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.EnumPacketDirection;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ 
/*    */ public class MessageSerializer
/*    */   extends MessageToByteEncoder<Packet>
/*    */ {
/* 21 */   private static final Logger logger = LogManager.getLogger();
/* 22 */   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.logMarkerPackets);
/*    */   private final EnumPacketDirection direction;
/*    */   
/*    */   public MessageSerializer(EnumPacketDirection direction) {
/* 26 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext p_encode_1_, Packet p_encode_2_, ByteBuf p_encode_3_) throws IOException, Exception {
/* 30 */     Integer integer = ((EnumConnectionState)p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get()).getPacketId(this.direction, p_encode_2_);
/*    */     
/* 32 */     if (logger.isDebugEnabled()) {
/* 33 */       logger.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", new Object[] { p_encode_1_.channel().attr(NetworkManager.attrKeyConnectionState).get(), integer, p_encode_2_.getClass().getName() });
/*    */     }
/*    */     
/* 36 */     if (integer == null) {
/* 37 */       throw new IOException("Can't serialize unregistered packet");
/*    */     }
/* 39 */     PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
/* 40 */     packetbuffer.writeVarIntToBuffer(integer.intValue());
/*    */     
/*    */     try {
/* 43 */       p_encode_2_.writePacketData(packetbuffer);
/* 44 */     } catch (Throwable throwable) {
/* 45 */       logger.error(throwable);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MessageSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */