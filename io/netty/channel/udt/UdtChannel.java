package io.netty.channel.udt;

import io.netty.channel.Channel;
import java.net.InetSocketAddress;

public interface UdtChannel extends Channel {
  UdtChannelConfig config();
  
  InetSocketAddress localAddress();
  
  InetSocketAddress remoteAddress();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\channe\\udt\UdtChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */