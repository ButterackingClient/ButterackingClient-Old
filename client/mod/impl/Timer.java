package client.mod.impl;

import client.hud.*;
import client.*;
import client.timer.*;

public class Timer extends HudMod {
    public Timer() {
        super("timer", 5, 55, false);
    }

    @Override
    public int getWidth() {
        return Timer.fr.getStringWidth("Playing 1 : 0 : 0");
    }

    @Override
    public void draw() {
        Timer.fr.drawStringWithShadow(String.valueOf(String.valueOf(String.valueOf(Client.I.m))) + "Playing " + Client.I.s + DeltaTimer.D.hours + " : " + DeltaTimer.D.minutes + " : " + DeltaTimer.D.seconds, (float) this.x(), (float) this.y(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        Timer.fr.drawStringWithShadow(String.valueOf(String.valueOf(String.valueOf(Client.I.m))) + "Playing" + Client.I.s + " 1 : 0 : 0", (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
