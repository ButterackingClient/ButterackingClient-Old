package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;

public interface ImageData {
  int getDepth();
  
  int getWidth();
  
  int getHeight();
  
  int getTexWidth();
  
  int getTexHeight();
  
  ByteBuffer getImageBufferData();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\newdawn\slick\opengl\ImageData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */