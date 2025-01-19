/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.ShortBufferException;
/*    */ 
/*    */ public class NettyEncryptingEncoder
/*    */   extends MessageToByteEncoder<ByteBuf> {
/*    */   private final NettyEncryptionTranslator encryptionCodec;
/*    */   
/*    */   public NettyEncryptingEncoder(Cipher cipher) {
/* 14 */     this.encryptionCodec = new NettyEncryptionTranslator(cipher);
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) throws ShortBufferException, Exception {
/* 18 */     this.encryptionCodec.cipher(p_encode_2_, p_encode_3_);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\NettyEncryptingEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */