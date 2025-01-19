package net.optifine.http;

public interface HttpListener {
  void finished(HttpRequest paramHttpRequest, HttpResponse paramHttpResponse);
  
  void failed(HttpRequest paramHttpRequest, Exception paramException);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\http\HttpListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */