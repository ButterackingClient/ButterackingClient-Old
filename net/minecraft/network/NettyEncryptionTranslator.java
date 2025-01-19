/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.ShortBufferException;
/*    */ 
/*    */ public class NettyEncryptionTranslator
/*    */ {
/*    */   private final Cipher cipher;
/* 11 */   private byte[] field_150505_b = new byte[0];
/* 12 */   private byte[] field_150506_c = new byte[0];
/*    */   
/*    */   protected NettyEncryptionTranslator(Cipher cipherIn) {
/* 15 */     this.cipher = cipherIn;
/*    */   }
/*    */   
/*    */   private byte[] func_150502_a(ByteBuf buf) {
/* 19 */     int i = buf.readableBytes();
/*    */     
/* 21 */     if (this.field_150505_b.length < i) {
/* 22 */       this.field_150505_b = new byte[i];
/*    */     }
/*    */     
/* 25 */     buf.readBytes(this.field_150505_b, 0, i);
/* 26 */     return this.field_150505_b;
/*    */   }
/*    */   
/*    */   protected ByteBuf decipher(ChannelHandlerContext ctx, ByteBuf buffer) throws ShortBufferException {
/* 30 */     int i = buffer.readableBytes();
/* 31 */     byte[] abyte = func_150502_a(buffer);
/* 32 */     ByteBuf bytebuf = ctx.alloc().heapBuffer(this.cipher.getOutputSize(i));
/* 33 */     bytebuf.writerIndex(this.cipher.update(abyte, 0, i, bytebuf.array(), bytebuf.arrayOffset()));
/* 34 */     return bytebuf;
/*    */   }
/*    */   
/*    */   protected void cipher(ByteBuf in, ByteBuf out) throws ShortBufferException {
/* 38 */     int i = in.readableBytes();
/* 39 */     byte[] abyte = func_150502_a(in);
/* 40 */     int j = this.cipher.getOutputSize(i);
/*    */     
/* 42 */     if (this.field_150506_c.length < j) {
/* 43 */       this.field_150506_c = new byte[j];
/*    */     }
/*    */     
/* 46 */     out.writeBytes(this.field_150506_c, 0, this.cipher.update(abyte, 0, i, this.field_150506_c));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\NettyEncryptionTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */