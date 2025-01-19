package io.netty.handler.codec.http;

public interface FullHttpMessage extends HttpMessage, LastHttpContent {
  FullHttpMessage copy();
  
  FullHttpMessage retain(int paramInt);
  
  FullHttpMessage retain();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\http\FullHttpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */