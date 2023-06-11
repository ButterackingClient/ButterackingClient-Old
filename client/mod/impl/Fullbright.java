package client.mod.impl;

import client.Client;
import client.mod.*;

public class Fullbright extends Mod {
    public Fullbright() {
        super("", "", Category.MISC);
    }

    @Override
    public void onEnable() {
        mc.gameSettings.saturation = 25.0F;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.saturation = 0;
        super.onDisable();
    }
}
