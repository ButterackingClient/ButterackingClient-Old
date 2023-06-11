package client.hud;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import client.*;

import java.awt.*;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class HudMod {
    public static Minecraft mc;
    public static FontRenderer fr;
    public String name;
    public boolean enabled;
    public DraggableComponent drag;
    public int x;
    public int y;

    public HudMod(final String name, final int x, final int y, final boolean enabled) {
        HudMod.mc = Minecraft.getMinecraft();
        HudMod.fr = HudMod.mc.fontRendererObj;
        this.name = name;
        try {
            this.setEnabled((boolean) Client.I.config.config.get(String.valueOf(String.valueOf(name.toLowerCase())) + " enabled"));
            this.x = (int) Client.I.config.config.get(String.valueOf(String.valueOf(String.valueOf(name.toLowerCase()))) + " x");
            this.y = (int) Client.I.config.config.get(String.valueOf(String.valueOf(String.valueOf(name.toLowerCase()))) + " y");
        } catch (NullPointerException e) {
            e.printStackTrace();
            this.x = x;
            this.y = y;
            this.enabled = enabled;
        }
        this.drag = new DraggableComponent(this.x, this.y, this.getWidth(), this.getHeight(), new Color(0, 0, 0, 0).getRGB());
    }

    public int getWidth() {
        return 80;
    }

    public int getHeight() {
        return 9;
    }

    public void draw() {
    }

    public void renderDummy(final int mouseX, final int mouseY) {
        this.drag.draw(mouseX, mouseY);
    }

    public static void drawRect(int left, int top, int right, int bottom, final int color) {
        if (left < right) {
            final int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final int j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f4, f5, f6, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public int x() {
        return this.drag.getxPosition();
    }

    public int y() {
        return this.drag.getyPosition();
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.setEnabled(!this.enabled);
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
