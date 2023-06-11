package client.mod.impl;

import client.hud.HudMod;
import net.minecraft.client.*;
import client.*;

public class PingMod extends HudMod
{
    public PingMod() {
        super("Ping", 5, 65,false);
    }
    
    @Override
    public int getWidth() {
        return this.fr.getStringWidth("10 ms");
    }
    
    @Override
    public int getHeight() {
        return this.fr.FONT_HEIGHT;
    }
    
    @Override
    public void draw() {
        if (this.mc.getNetHandler() != null && Minecraft.getMinecraft().thePlayer != null && this.mc.getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()) != null) {
            this.fr.drawStringWithShadow(Client.I.m + Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + Client.I.s + " ms", x(), y(), -1);
        }
        super.draw();
    }
    
    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        this.fr.drawStringWithShadow(Client.I.m + "10" + Client.I.s + " ms", (float)this.x(), (float)this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
