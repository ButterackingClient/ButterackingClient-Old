package kp.mcinterface;

import java.io.File;

public interface IMinecraftInterface {
  boolean isAllowedCharacter(char paramChar);
  
  boolean isKeyDown(int paramInt);
  
  boolean getEventButtonState();
  
  boolean isCtrlKeyDown();
  
  boolean isShiftKeyDown();
  
  boolean isAltKeyDown();
  
  boolean isOnGuiChat();
  
  File getMinecraftDir();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\mcinterface\IMinecraftInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */