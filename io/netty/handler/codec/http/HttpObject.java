package io.netty.handler.codec.http;

import io.netty.handler.codec.DecoderResult;

public interface HttpObject {
  DecoderResult getDecoderResult();
  
  void setDecoderResult(DecoderResult paramDecoderResult);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\http\HttpObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */