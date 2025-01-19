package org.newdawn.slick.geom;

import java.io.Serializable;

public interface Triangulator extends Serializable {
  int getTriangleCount();
  
  float[] getTrianglePoint(int paramInt1, int paramInt2);
  
  void addPolyPoint(float paramFloat1, float paramFloat2);
  
  void startHole();
  
  boolean triangulate();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\newdawn\slick\geom\Triangulator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */