package client.gui.click;

import client.*;
import client.hud.*;

import java.awt.*;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class Modbutton {
    public int x;
    public int y;
    public int w;
    public int h;
    public HudMod m;

    public Modbutton(final int x, final int y, final int w, final int h, final HudMod m) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.m = m;
    }

    public void draw(final int mouseX, final int mouseY) {
    	if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h){
        	Gui.drawRect(this.x, this.y, this.x + this.w, this.y + this.h, new Color(100, 100, 100).getRGB());
    	}
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.m.name, this.x + 2, this.y + 2, this.getColor());
    }

    public int getColor() {
        if (this.m.isEnabled()) {
            return new Color(0, 255, 0, 255).getRGB();
        }
        return new Color(255, 0, 0, 255).getRGB();
    }

    public void onClick(final int mouseX, final int mouseY, final int button) {
        if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h) {
            if (this.m.isEnabled()) {
                this.m.setEnabled(false);
            } else {
                this.m.setEnabled(true);
            }
        }
    }
}
