package net.minecraft.src;

import kp.ui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class VanillaSimpleButtonRenderer implements SimpleButton.IButtonRenderer {
    @Override
    public void render(final SimpleButton p_render_1_) {
        final FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        final int i = fontrenderer.FONT_HEIGHT;
        final int j = fontrenderer.getStringWidth(p_render_1_.text);
        final int k = p_render_1_.hover ? -2144128205 : Integer.MIN_VALUE;
        final int l = p_render_1_.useState ? 12 : 0;
        Gui.drawRect(p_render_1_.x, p_render_1_.y, p_render_1_.x + p_render_1_.width + l, p_render_1_.y + p_render_1_.height, k);
        fontrenderer.drawString(p_render_1_.text, p_render_1_.x + (p_render_1_.width - j) / 2 + l, p_render_1_.y + (p_render_1_.height - i) / 2, -1);
        if (p_render_1_.useState) {
            IconManager.drawIcon(p_render_1_.x, p_render_1_.y, p_render_1_.height, p_render_1_.height, 1, 0);
            if (p_render_1_.state) {
                GlStateManager.color(0.0f, 1.0f, 0.0f);
                IconManager.drawIcon(p_render_1_.x, p_render_1_.y, p_render_1_.height, p_render_1_.height, 0, 0);
            }
        }
    }
}
