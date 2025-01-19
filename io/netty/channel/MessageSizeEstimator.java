package io.netty.channel;

public interface MessageSizeEstimator {
  Handle newHandle();
  
  public static interface Handle {
    int size(Object param1Object);
  }
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\channel\MessageSizeEstimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */