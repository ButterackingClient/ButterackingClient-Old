package client.mod.impl;

import client.hud.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import client.*;

public class ModFPS extends HudMod {
    public ModFPS() {
        super("Fps", 5, 35, false);
    }

    @Override
    public int getWidth() {
        final FontRenderer fr = ModFPS.fr;
        final StringBuilder sb = new StringBuilder("[Fps] : ");
        final Minecraft mc = ModFPS.mc;
        return fr.getStringWidth(sb.append(Minecraft.getDebugFPS()).toString());
    }

    @Override
    public void draw() {
        ModFPS.fr.drawStringWithShadow(Client.I.m + Minecraft.getDebugFPS() + Client.I.s + " fps", (float) this.x(), (float) this.y(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        ModFPS.fr.drawStringWithShadow(Client.I.m + Minecraft.getDebugFPS()+ Client.I.s + " fps", (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
