package client.mod.impl;

import client.hud.*;
import client.*;

public class ModXYZ extends HudMod {
    public ModXYZ() {
        super("XYZ", 5, 25, false);
    }

    @Override
    public int getWidth() {
        return ModXYZ.fr.getStringWidth(/*"[XYZ] : */"100 / 100 / 100");
    }

    @Override
    public void draw() {
        ModXYZ.fr.drawStringWithShadow(this.getXYZString(), (float) this.x(), (float) this.y(), -1);
        super.draw();
    }

    private String getXYZString() {
        return String.format(Client.I.m + "%.0f " + Client.I.w + "/" + Client.I.m + " %.0f " + Client.I.s + "/" + Client.I.m + " %.0f", ModXYZ.mc.getRenderViewEntity().posX, ModXYZ.mc.getRenderViewEntity().getEntityBoundingBox().minY, ModXYZ.mc.getRenderViewEntity().posZ);
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        ModXYZ.fr.drawStringWithShadow(this.getXYZString(), (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
