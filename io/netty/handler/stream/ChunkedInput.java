package io.netty.handler.stream;

import io.netty.channel.ChannelHandlerContext;

public interface ChunkedInput<B> {
  boolean isEndOfInput() throws Exception;
  
  void close() throws Exception;
  
  B readChunk(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\stream\ChunkedInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */