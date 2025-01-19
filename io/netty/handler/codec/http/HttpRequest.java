package io.netty.handler.codec.http;

public interface HttpRequest extends HttpMessage {
  HttpMethod getMethod();
  
  HttpRequest setMethod(HttpMethod paramHttpMethod);
  
  String getUri();
  
  HttpRequest setUri(String paramString);
  
  HttpRequest setProtocolVersion(HttpVersion paramHttpVersion);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\handler\codec\http\HttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */