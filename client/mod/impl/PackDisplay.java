package client.mod.impl;

import client.hud.*;
import net.minecraft.src.*;
import client.*;

public class PackDisplay extends HudMod {
    public PackDisplay() {
        super("PackDisplay", 5, 75, false);
    }

    @Override
    public void draw() {
        String pack = "";
        if (!Config.getResourcePackNames().equalsIgnoreCase("Default")) {
            pack = Config.getResourcePackNames().split(",")[Config.getResourcePacks().length - 1];
        } else {
            pack = "Default";
        }
        if (!Config.getResourcePackNames().equals("Default")) {
            PackDisplay.fr.drawStringWithShadow(/*String.valueOf(String.valueOf(String.valueOf(Client.I.w))) + "[" + Client.I.m + "Pack" + Client.I.w + "] : " + */Client.I.rw + pack, (float) this.x(), (float) this.y(), -1);
        } else {
            PackDisplay.fr.drawStringWithShadow("", (float) this.x(), (float) this.y(), -1);
        }
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        PackDisplay.fr.drawStringWithShadow(/*String.valueOf(String.valueOf(String.valueOf(Client.I.w))) + "[" + Client.I.m + "Pack" + Client.I.w + "] : " + */Client.I.rw + "Default", (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }

    @Override
    public int getWidth() {
        return PackDisplay.fr.getStringWidth("[Pack] : Default");
    }

    @Override
    public int getHeight() {
        return PackDisplay.fr.FONT_HEIGHT;
    }
}
