/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UnknownSocksRequest
/*    */   extends SocksRequest
/*    */ {
/*    */   public UnknownSocksRequest() {
/* 30 */     super(SocksRequestType.UNKNOWN);
/*    */   }
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\socks\UnknownSocksRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */