/*    */ package net.minecraft.util;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.CorruptedFrameException;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public class MessageDeserializer2
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws Exception {
/* 15 */     p_decode_2_.markReaderIndex();
/* 16 */     byte[] abyte = new byte[3];
/*    */     
/* 18 */     for (int i = 0; i < abyte.length; i++) {
/* 19 */       if (!p_decode_2_.isReadable()) {
/* 20 */         p_decode_2_.resetReaderIndex();
/*    */         
/*    */         return;
/*    */       } 
/* 24 */       abyte[i] = p_decode_2_.readByte();
/*    */       
/* 26 */       if (abyte[i] >= 0) {
/* 27 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.wrappedBuffer(abyte));
/*    */         
/*    */         try {
/* 30 */           int j = packetbuffer.readVarIntFromBuffer();
/*    */           
/* 32 */           if (p_decode_2_.readableBytes() >= j) {
/* 33 */             p_decode_3_.add(p_decode_2_.readBytes(j));
/*    */             
/*    */             return;
/*    */           } 
/* 37 */           p_decode_2_.resetReaderIndex();
/*    */         } finally {
/* 39 */           packetbuffer.release();
/*    */         } 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 46 */     throw new CorruptedFrameException("length wider than 21-bit");
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MessageDeserializer2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */