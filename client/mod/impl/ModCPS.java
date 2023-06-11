package client.mod.impl;

import client.hud.*;

import java.util.*;

import org.lwjgl.input.*;
import client.*;

import java.util.function.*;

public class ModCPS extends HudMod {
    public List<Long> clicks;
    public boolean wasPressed;
    public long lastPressed;
    private List<Long> clicks2;
    private boolean wasPressed2;
    private long lastPressed2;

    public ModCPS() {
        super("CPS", 5, 45, false);
        this.clicks = new ArrayList<Long>();
        this.clicks2 = new ArrayList<Long>();
    }

    @Override
    public int getWidth() {
        return ModCPS.fr.getStringWidth("[Cps] : 10");
    }

    @Override
    public void draw() {
        final boolean pressed = Mouse.isButtonDown(0);
        final boolean rpressed = Mouse.isButtonDown(1);
        if (pressed != this.wasPressed) {
            this.lastPressed = System.currentTimeMillis();
            if (this.wasPressed = pressed) {
                this.clicks.add(this.lastPressed);
            }
        }
        if (rpressed != this.wasPressed2) {
            this.lastPressed2 = System.currentTimeMillis() + 10L;
            if (this.wasPressed2 = rpressed) {
                this.clicks2.add(this.lastPressed2);
            }
        }
        ModCPS.fr.drawStringWithShadow(Client.I.m + this.getCPS() + Client.I.s + " | "+ Client.I.m  + this.getCPS2(), (float) this.x(), (float) this.y(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        ModCPS.fr.drawStringWithShadow(Client.I.m +  this.getCPS() + Client.I.s + " | " + Client.I.m + this.getCPS2(), (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }

    public int getCPS() {
        final long time = System.currentTimeMillis();
        this.clicks.removeIf(new Predicate<Long>() {
            @Override
            public boolean test(final Long aLong) {
                return aLong + 1000L < time;
            }
        });
        return this.clicks.size();
    }

    public int getCPS2() {
        final long time2 = System.currentTimeMillis();
        this.clicks2.removeIf(new Predicate<Long>() {
            @Override
            public boolean test(final Long aLong2) {
                return aLong2 + 1000L < time2;
            }
        });
        return this.clicks2.size();
    }
}
