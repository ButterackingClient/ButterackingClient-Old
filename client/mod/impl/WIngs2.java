package client.mod.impl;

import client.hud.*;

public class WIngs2 extends HudMod {
    public WIngs2() {
        super("Dragon Wings", 500000, 5000000, false);
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
