package io.netty.channel;

import io.netty.util.ReferenceCounted;

public interface AddressedEnvelope<M, A extends java.net.SocketAddress> extends ReferenceCounted {
  M content();
  
  A sender();
  
  A recipient();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\channel\AddressedEnvelope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */