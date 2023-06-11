package client.mod.impl;

import client.mod.*;
import client.event.impl.*;
import client.*;
import client.event.*;

public class ToggleSprint extends Mod {
    public ToggleSprint() {
        super("TestMod", "Test Mod", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (this.isEnabled() && Client.I.hudManager.tsh.isEnabled()) {
            if (this.mc.gameSettings.keyBindLeft.isKeyDown() && !this.mc.thePlayer.isSneaking()) {
                this.mc.thePlayer.setSprinting(true);
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.setSprinting(false);
    }
}
