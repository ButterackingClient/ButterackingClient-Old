/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.DecoderException;
/*    */ import java.util.List;
/*    */ import java.util.zip.DataFormatException;
/*    */ import java.util.zip.Inflater;
/*    */ 
/*    */ public class NettyCompressionDecoder
/*    */   extends ByteToMessageDecoder {
/*    */   private final Inflater inflater;
/*    */   private int treshold;
/*    */   
/*    */   public NettyCompressionDecoder(int treshold) {
/* 18 */     this.treshold = treshold;
/* 19 */     this.inflater = new Inflater();
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws DataFormatException, Exception {
/* 23 */     if (p_decode_2_.readableBytes() != 0) {
/* 24 */       PacketBuffer packetbuffer = new PacketBuffer(p_decode_2_);
/* 25 */       int i = packetbuffer.readVarIntFromBuffer();
/*    */       
/* 27 */       if (i == 0) {
/* 28 */         p_decode_3_.add(packetbuffer.readBytes(packetbuffer.readableBytes()));
/*    */       } else {
/* 30 */         if (i < this.treshold) {
/* 31 */           throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.treshold);
/*    */         }
/*    */         
/* 34 */         if (i > 2097152) {
/* 35 */           throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + 2097152);
/*    */         }
/*    */         
/* 38 */         byte[] abyte = new byte[packetbuffer.readableBytes()];
/* 39 */         packetbuffer.readBytes(abyte);
/* 40 */         this.inflater.setInput(abyte);
/* 41 */         byte[] abyte1 = new byte[i];
/* 42 */         this.inflater.inflate(abyte1);
/* 43 */         p_decode_3_.add(Unpooled.wrappedBuffer(abyte1));
/* 44 */         this.inflater.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setCompressionTreshold(int treshold) {
/* 50 */     this.treshold = treshold;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\NettyCompressionDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */