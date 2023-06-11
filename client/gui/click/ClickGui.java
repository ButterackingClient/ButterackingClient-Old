package client.gui.click;

import client.*;
import client.hud.*;
import net.minecraft.client.gui.*;

import java.util.*;
import java.awt.Color;
import java.io.*;

public class ClickGui extends GuiScreen {
    ArrayList<Modbutton> modbutton;
    public static FontRenderer fr;
    public HudManager m;

    public ClickGui() {
        this.modbutton = new ArrayList<Modbutton>();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.modbutton.add(new Modbutton(10, 10, mc.fontRendererObj.getStringWidth(Client.I.hudManager.cn.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.cn));
        this.modbutton.add(new Modbutton(10, 25, mc.fontRendererObj.getStringWidth(Client.I.hudManager.mb.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.mb));
        this.modbutton.add(new Modbutton(10, 40, mc.fontRendererObj.getStringWidth(Client.I.hudManager.mx.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.mx));
        this.modbutton.add(new Modbutton(10, 55, mc.fontRendererObj.getStringWidth(Client.I.hudManager.mf.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.mf));
        this.modbutton.add(new Modbutton(10, 70, mc.fontRendererObj.getStringWidth(Client.I.hudManager.mcps.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.mcps));
        this.modbutton.add(new Modbutton(10, 85, mc.fontRendererObj.getStringWidth(Client.I.hudManager.t.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.t));
        this.modbutton.add(new Modbutton(10, 100, mc.fontRendererObj.getStringWidth(Client.I.hudManager.p.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.p));
        this.modbutton.add(new Modbutton(10, 115, mc.fontRendererObj.getStringWidth(Client.I.hudManager.pd.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.pd));
        this.modbutton.add(new Modbutton(10, 130, mc.fontRendererObj.getStringWidth(Client.I.hudManager.tsh.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.tsh));
        this.modbutton.add(new Modbutton(10, 145, mc.fontRendererObj.getStringWidth(Client.I.hudManager.mas.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.mas));
        this.modbutton.add(new Modbutton(10, 160, mc.fontRendererObj.getStringWidth(Client.I.hudManager.mk.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.mk));
        this.modbutton.add(new Modbutton(10, 175, mc.fontRendererObj.getStringWidth(Client.I.hudManager.mp.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.mp));
        this.modbutton.add(new Modbutton(10, 190, mc.fontRendererObj.getStringWidth(Client.I.hudManager.c.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.c));
        this.modbutton.add(new Modbutton(10, 205, mc.fontRendererObj.getStringWidth(Client.I.hudManager.c2.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.c2));
        this.modbutton.add(new Modbutton(10, 220, mc.fontRendererObj.getStringWidth(Client.I.hudManager.bl.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.bl));
        this.modbutton.add(new Modbutton(90, 10, mc.fontRendererObj.getStringWidth(Client.I.hudManager.h.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.h));
        this.modbutton.add(new Modbutton(90, 25, mc.fontRendererObj.getStringWidth(Client.I.hudManager.gg.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.gg));
        this.modbutton.add(new Modbutton(90, 40, mc.fontRendererObj.getStringWidth(Client.I.hudManager.cg.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.cg));
        this.modbutton.add(new Modbutton(90, 55, mc.fontRendererObj.getStringWidth(Client.I.hudManager.os.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.os));
        this.modbutton.add(new Modbutton(90, 70, mc.fontRendererObj.getStringWidth(Client.I.hudManager.oa.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.oa));
        this.modbutton.add(new Modbutton(90, 85, mc.fontRendererObj.getStringWidth(Client.I.hudManager.w.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.w));
        this.modbutton.add(new Modbutton(90, 100, mc.fontRendererObj.getStringWidth(Client.I.hudManager.w2.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.w2));
        this.modbutton.add(new Modbutton(90, 115, mc.fontRendererObj.getStringWidth(Client.I.hudManager.sl.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.sl));
        this.modbutton.add(new Modbutton(90, 130, mc.fontRendererObj.getStringWidth(Client.I.hudManager.cc.name) + 4, mc.fontRendererObj.FONT_HEIGHT + 2, Client.I.hudManager.cc));
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        super.drawScreen(mouseX, mouseY, partialTicks);
        Gui.drawRect(5, 5, 170, 235, new Color(0, 0, 0, 180).getRGB());
        for (final Modbutton m : this.modbutton) {
            m.draw(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (final Modbutton m : this.modbutton) {
            m.onClick(mouseX, mouseY, mouseButton);
        }
    }
}
