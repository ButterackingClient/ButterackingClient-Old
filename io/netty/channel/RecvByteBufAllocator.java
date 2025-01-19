package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public interface RecvByteBufAllocator {
  Handle newHandle();
  
  public static interface Handle {
    ByteBuf allocate(ByteBufAllocator param1ByteBufAllocator);
    
    int guess();
    
    void record(int param1Int);
  }
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\channel\RecvByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */