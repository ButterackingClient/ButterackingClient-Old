package net.minecraft.src;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class IconManager {
    public static final ResourceLocation ICON;

    static {
        ICON = new ResourceLocation("textures/kpicons.png");
    }

    public static void drawIcon(final int p_drawIcon_0_, final int p_drawIcon_1_, final int p_drawIcon_2_, final int p_drawIcon_3_, final int p_drawIcon_4_, final int p_drawIcon_5_) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        final double d0 = 0.125;
        final double d2 = d0 * p_drawIcon_4_;
        final double d3 = d0 * p_drawIcon_5_;
        Minecraft.getMinecraft().getTextureManager().bindTexture(IconManager.ICON);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(p_drawIcon_0_, p_drawIcon_1_, 0.0).tex(d2, d3).endVertex();
        worldrenderer.pos(p_drawIcon_0_, p_drawIcon_1_ + p_drawIcon_3_, 0.0).tex(d2, d3 + d0).endVertex();
        worldrenderer.pos(p_drawIcon_0_ + (double) p_drawIcon_2_, p_drawIcon_1_ + p_drawIcon_3_, 0.0).tex(d2 + d0, d3 + d0).endVertex();
        worldrenderer.pos(p_drawIcon_0_ + (double) p_drawIcon_2_, p_drawIcon_1_, 0.0).tex(d2 + d0, d3).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }
}
