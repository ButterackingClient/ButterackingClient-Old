/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ZlibEncoder
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   protected ZlibEncoder() {
/* 29 */     super(false);
/*    */   }
/*    */   
/*    */   public abstract boolean isClosed();
/*    */   
/*    */   public abstract ChannelFuture close();
/*    */   
/*    */   public abstract ChannelFuture close(ChannelPromise paramChannelPromise);
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\compression\ZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */