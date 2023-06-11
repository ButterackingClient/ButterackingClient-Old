package kp.mcinterface;

import java.io.*;

public class DefaultMCInterface implements IMinecraftInterface
{
    @Override
    public boolean isAllowedCharacter(final char c) {
        return c != '¡×' && c >= ' ' && c != '\u007f';
    }
    
    @Override
    public boolean isKeyDown(final int key) {
        return false;
    }
    
    @Override
    public boolean isCtrlKeyDown() {
        return false;
    }
    
    @Override
    public boolean isShiftKeyDown() {
        return false;
    }
    
    @Override
    public boolean isAltKeyDown() {
        return false;
    }
    
    @Override
    public boolean isOnGuiChat() {
        return false;
    }
    
    @Override
    public File getMinecraftDir() {
        return null;
    }
    
    @Override
    public boolean getEventButtonState() {
        return false;
    }
}
