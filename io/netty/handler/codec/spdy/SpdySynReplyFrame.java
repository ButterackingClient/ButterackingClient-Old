package io.netty.handler.codec.spdy;

public interface SpdySynReplyFrame extends SpdyHeadersFrame {
  SpdySynReplyFrame setStreamId(int paramInt);
  
  SpdySynReplyFrame setLast(boolean paramBoolean);
  
  SpdySynReplyFrame setInvalid();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\spdy\SpdySynReplyFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */