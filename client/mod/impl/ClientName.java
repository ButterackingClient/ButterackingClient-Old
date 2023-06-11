package client.mod.impl;

import client.hud.*;
import client.*;

public class ClientName extends HudMod {
    public ClientName() {
        super("ClientName", 5, 5, false);
    }

    @Override
    public int getWidth() {
        return ClientName.fr.getStringWidth("[" + Client.I.n + "]");
    }

    @Override
    public void draw() {
        ClientName.fr.drawStringWithShadow(String.valueOf(String.valueOf(String.valueOf(Client.I.w))) + /*"[" + */Client.I.m + Client.I.n + Client.I.w /*+ "]"*/, (float) this.x(), (float) this.y(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        ClientName.fr.drawStringWithShadow(String.valueOf(String.valueOf(String.valueOf(Client.I.w))) +/* "[" + */Client.I.m + Client.I.n + Client.I.w /*+ "]"*/, (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
