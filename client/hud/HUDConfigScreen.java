package client.hud;

import net.minecraft.client.gui.*;
import client.*;

import java.util.*;

public class HUDConfigScreen extends GuiScreen {
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        for (final HudMod m : Client.I.hudManager.hudMods) {
            if (m.isEnabled()) {
                m.renderDummy(mouseX, mouseY);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
}
