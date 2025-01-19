package org.newdawn.slick;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public interface ShapeFill {
  Color colorAt(Shape paramShape, float paramFloat1, float paramFloat2);
  
  Vector2f getOffsetAt(Shape paramShape, float paramFloat1, float paramFloat2);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\newdawn\slick\ShapeFill.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */