package client.mod.impl;

import client.hud.*;

public class OldSneak extends HudMod {
    public OldSneak() {
        super("1.7 Sneaking", 500000, 5000000, false);
    }

    @Override
    public int getWidth() {
        return ClientName.fr.getStringWidth("");
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        super.renderDummy(mouseX, mouseY);
    }
}
