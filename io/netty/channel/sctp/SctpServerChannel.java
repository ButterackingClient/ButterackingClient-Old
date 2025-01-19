package io.netty.channel.sctp;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ServerChannel;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;

public interface SctpServerChannel extends ServerChannel {
  SctpServerChannelConfig config();
  
  InetSocketAddress localAddress();
  
  Set<InetSocketAddress> allLocalAddresses();
  
  ChannelFuture bindAddress(InetAddress paramInetAddress);
  
  ChannelFuture bindAddress(InetAddress paramInetAddress, ChannelPromise paramChannelPromise);
  
  ChannelFuture unbindAddress(InetAddress paramInetAddress);
  
  ChannelFuture unbindAddress(InetAddress paramInetAddress, ChannelPromise paramChannelPromise);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\channel\sctp\SctpServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */