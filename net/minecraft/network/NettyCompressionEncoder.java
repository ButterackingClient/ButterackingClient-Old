/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.util.zip.Deflater;
/*    */ 
/*    */ public class NettyCompressionEncoder
/*    */   extends MessageToByteEncoder<ByteBuf> {
/* 10 */   private final byte[] buffer = new byte[8192];
/*    */   private final Deflater deflater;
/*    */   private int treshold;
/*    */   
/*    */   public NettyCompressionEncoder(int treshold) {
/* 15 */     this.treshold = treshold;
/* 16 */     this.deflater = new Deflater();
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) throws Exception {
/* 20 */     int i = p_encode_2_.readableBytes();
/* 21 */     PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
/*    */     
/* 23 */     if (i < this.treshold) {
/* 24 */       packetbuffer.writeVarIntToBuffer(0);
/* 25 */       packetbuffer.writeBytes(p_encode_2_);
/*    */     } else {
/* 27 */       byte[] abyte = new byte[i];
/* 28 */       p_encode_2_.readBytes(abyte);
/* 29 */       packetbuffer.writeVarIntToBuffer(abyte.length);
/* 30 */       this.deflater.setInput(abyte, 0, i);
/* 31 */       this.deflater.finish();
/*    */       
/* 33 */       while (!this.deflater.finished()) {
/* 34 */         int j = this.deflater.deflate(this.buffer);
/* 35 */         packetbuffer.writeBytes(this.buffer, 0, j);
/*    */       } 
/*    */       
/* 38 */       this.deflater.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setCompressionTreshold(int treshold) {
/* 43 */     this.treshold = treshold;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\NettyCompressionEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */