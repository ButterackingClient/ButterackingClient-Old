/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.DecoderResult;
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
/*    */ public class DefaultHttpObject
/*    */   implements HttpObject
/*    */ {
/* 22 */   private DecoderResult decoderResult = DecoderResult.SUCCESS;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DecoderResult getDecoderResult() {
/* 30 */     return this.decoderResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDecoderResult(DecoderResult decoderResult) {
/* 35 */     if (decoderResult == null) {
/* 36 */       throw new NullPointerException("decoderResult");
/*    */     }
/* 38 */     this.decoderResult = decoderResult;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\http\DefaultHttpObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */