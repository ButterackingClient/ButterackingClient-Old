package com.sun.jna;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public interface InvocationMapper {
  InvocationHandler getInvocationHandler(NativeLibrary paramNativeLibrary, Method paramMethod);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\com\sun\jna\InvocationMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */