package net.minecraft.src;

import kp.mcinterface.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

import java.io.*;

import org.lwjgl.input.*;

public class VanillaInterface implements IMinecraftInterface {
    @Override
    public boolean isAllowedCharacter(final char p_isAllowedCharacter_1_) {
        return ChatAllowedCharacters.isAllowedCharacter(p_isAllowedCharacter_1_);
    }

    @Override
    public boolean isKeyDown(final int p_isKeyDown_1_) {
        return Keyboard.isKeyDown(p_isKeyDown_1_);
    }

    @Override
    public boolean isCtrlKeyDown() {
        return GuiScreen.isCtrlKeyDown();
    }

    @Override
    public boolean isShiftKeyDown() {
        return GuiScreen.isShiftKeyDown();
    }

    @Override
    public boolean isAltKeyDown() {
        return GuiScreen.isAltKeyDown();
    }

    @Override
    public boolean isOnGuiChat() {
        return Minecraft.getMinecraft().currentScreen instanceof GuiChat;
    }

    @Override
    public File getMinecraftDir() {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public boolean getEventButtonState() {
        return Mouse.getEventButtonState();
    }
}
