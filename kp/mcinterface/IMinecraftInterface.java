package kp.mcinterface;

import java.io.*;

public interface IMinecraftInterface
{
    boolean isAllowedCharacter(final char p0);
    
    boolean isKeyDown(final int p0);
    
    boolean getEventButtonState();
    
    boolean isCtrlKeyDown();
    
    boolean isShiftKeyDown();
    
    boolean isAltKeyDown();
    
    boolean isOnGuiChat();
    
    File getMinecraftDir();
}
