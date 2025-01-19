package org.newdawn.slick.svg;

import org.newdawn.slick.geom.Transform;
import org.w3c.dom.Element;

public interface Loader {
  void loadChildren(Element paramElement, Transform paramTransform) throws ParsingException;
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\newdawn\slick\svg\Loader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */