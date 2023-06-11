package client.mod.impl;

import client.hud.*;
import client.*;

public class ToggleSprintHud extends HudMod {
    public ToggleSprintHud() {
        super("ToggleSprint", 5, 85, false);
    }

    @Override
    public int getWidth() {
        return ToggleSprintHud.fr.getStringWidth("Sprinting (toggled)");
    }

    @Override
    public void draw() {
        if (Client.I.modManager.toggleSprint.isToggled()) {
            ToggleSprintHud.fr.drawStringWithShadow(String.valueOf(String.valueOf(String.valueOf(Client.I.w))) + /*"[" +*/ Client.I.m + "Sprinting (toggled)" + Client.I.w/* + "]"*/, (float) this.x(), (float) this.y(), -1);
        }
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        ToggleSprintHud.fr.drawStringWithShadow(String.valueOf(String.valueOf(String.valueOf(Client.I.w))) + /*"[" +*/ Client.I.m + "Sprinting (toggled)" + Client.I.w/* + "]"*/, (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
