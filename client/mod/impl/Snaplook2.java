package client.mod.impl;

import client.mod.*;
import client.event.impl.*;
import client.*;
import client.event.*;

public class Snaplook2 extends Mod {
    public Snaplook2() {
        super("TestMod", "Test Mod", Category.MISC);
    }

    @EventTarget
    public void onEnable() {
        if (mc.gameSettings.keySnplook.isKeyDown()) {
            this.mc.gameSettings.showDebugInfo = 1;
        } else {
            this.mc.gameSettings.showDebugInfo = 0;
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.mc.gameSettings.showDebugInfo = 0;
        super.onDisable();
    }
}
