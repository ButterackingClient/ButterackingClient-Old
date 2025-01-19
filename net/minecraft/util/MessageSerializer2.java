/*    */ package net.minecraft.util;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public class MessageSerializer2 extends MessageToByteEncoder<ByteBuf> {
/*    */   protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) throws Exception {
/* 10 */     int i = p_encode_2_.readableBytes();
/* 11 */     int j = PacketBuffer.getVarIntSize(i);
/*    */     
/* 13 */     if (j > 3) {
/* 14 */       throw new IllegalArgumentException("unable to fit " + i + " into " + '\003');
/*    */     }
/* 16 */     PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
/* 17 */     packetbuffer.ensureWritable(j + i);
/* 18 */     packetbuffer.writeVarIntToBuffer(i);
/* 19 */     packetbuffer.writeBytes(p_encode_2_, p_encode_2_.readerIndex(), i);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MessageSerializer2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */