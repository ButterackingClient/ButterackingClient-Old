package io.netty.handler.codec.serialization;

public interface ClassResolver {
  Class<?> resolve(String paramString) throws ClassNotFoundException;
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\serialization\ClassResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */