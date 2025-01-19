package io.netty.channel.nio;

import java.nio.channels.SelectionKey;

public interface NioTask<C extends java.nio.channels.SelectableChannel> {
  void channelReady(C paramC, SelectionKey paramSelectionKey) throws Exception;
  
  void channelUnregistered(C paramC, Throwable paramThrowable) throws Exception;
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\channel\nio\NioTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */