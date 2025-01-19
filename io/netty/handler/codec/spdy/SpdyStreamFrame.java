package io.netty.handler.codec.spdy;

public interface SpdyStreamFrame extends SpdyFrame {
  int streamId();
  
  SpdyStreamFrame setStreamId(int paramInt);
  
  boolean isLast();
  
  SpdyStreamFrame setLast(boolean paramBoolean);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\spdy\SpdyStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */