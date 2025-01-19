package io.netty.handler.codec.spdy;

public interface SpdyHeadersFrame extends SpdyStreamFrame {
  boolean isInvalid();
  
  SpdyHeadersFrame setInvalid();
  
  boolean isTruncated();
  
  SpdyHeadersFrame setTruncated();
  
  SpdyHeaders headers();
  
  SpdyHeadersFrame setStreamId(int paramInt);
  
  SpdyHeadersFrame setLast(boolean paramBoolean);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\spdy\SpdyHeadersFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */