package io.netty.util;

public interface ReferenceCounted {
  int refCnt();
  
  ReferenceCounted retain();
  
  ReferenceCounted retain(int paramInt);
  
  boolean release();
  
  boolean release(int paramInt);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\nett\\util\ReferenceCounted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */