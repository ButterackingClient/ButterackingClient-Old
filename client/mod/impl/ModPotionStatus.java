package client.mod.impl;

import client.hud.*;
import net.minecraft.client.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;

import java.util.*;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class ModPotionStatus extends HudMod {
    protected float zLevelFloat;

    public ModPotionStatus() {
        super("PotionsStatus", 5, 95, false);
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }

    @Override
    public void draw() {
        final int offsetX = 21;
        final int offsetY = 14;
        final int i = 80;
        int i2 = 16;
        final Collection<PotionEffect> collection = Minecraft.getMinecraft().thePlayer.getActivePotionEffects();
        if (!collection.isEmpty()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableLighting();
            int l = 33;
            if (collection.size() > 5) {
                l = 132 / (collection.size() - 1);
            }
            for (final PotionEffect potioneffect : Minecraft.getMinecraft().thePlayer.getActivePotionEffects()) {
                final Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                if (potion.hasStatusIcon()) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                    final int i3 = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(this.x() + 21 - 20, this.y() + i2 - 14, 0 + i3 % 8 * 18, 198 + i3 / 8 * 18, 18, 18);
                }
                String s1 = I18n.format(potion.getName(), new Object[0]);
                if (potioneffect.getAmplifier() == 1) {
                    s1 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s1)))))) + " " + I18n.format("enchantment.level.2", new Object[0]);
                } else if (potioneffect.getAmplifier() == 2) {
                    s1 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s1)))))) + " " + I18n.format("enchantment.level.3", new Object[0]);
                } else if (potioneffect.getAmplifier() == 3) {
                    s1 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s1)))))) + " " + I18n.format("enchantment.level.4", new Object[0]);
                }
                ModPotionStatus.fr.drawString(s1, (float) (this.x() + 21), (float) (this.y() + i2 - 14), 16777215, true);
                final String s2 = Potion.getDurationString(potioneffect);
                ModPotionStatus.fr.drawString(s2, (float) (this.x() + 21), (float) (this.y() + i2 + 10 - 14), 8355711, true);
                i2 += l;
            }
        }
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        final int offsetX = 21;
        final int offsetY = 14;
        final int i = 80;
        int i2 = 16;
        final PotionEffect[] potionEffects = {new PotionEffect(Potion.moveSpeed.id, 1200, 0), new PotionEffect(Potion.damageBoost.id, 1200, 0), new PotionEffect(Potion.fireResistance.id, 1200, 0)};
        int l = 33;
        if (potionEffects.length > 5) {
            l = 132 / (potionEffects.length - 1);
        }
        PotionEffect[] array;
        for (int length = (array = potionEffects).length, j = 0; j < length; ++j) {
            final PotionEffect potioneffect = array[j];
            final Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (potion.hasStatusIcon()) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                final int i3 = potion.getStatusIconIndex();
                this.drawTexturedModalRect(this.x() + 21 - 20, this.y() + i2 - 14, 0 + i3 % 8 * 18, 198 + i3 / 8 * 18, 18, 18);
            }
            String s1 = I18n.format(potion.getName(), new Object[0]);
            if (potioneffect.getAmplifier() == 1) {
                s1 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s1)))))) + " " + I18n.format("enchantment.level.2", new Object[0]);
            } else if (potioneffect.getAmplifier() == 2) {
                s1 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s1)))))) + " " + I18n.format("enchantment.level.3", new Object[0]);
            } else if (potioneffect.getAmplifier() == 3) {
                s1 = String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(s1)))))) + " " + I18n.format("enchantment.level.4", new Object[0]);
            }
            ModPotionStatus.fr.drawString(s1, (float) (this.x() + 21), (float) (this.y() + i2 - 14), 16777215, true);
            final String s2 = Potion.getDurationString(potioneffect);
            ModPotionStatus.fr.drawString(s2, (float) (this.x() + 21), (float) (this.y() + i2 + 10 - 14), 8355711, true);
            i2 += l;
        }
        super.renderDummy(mouseX, mouseY);
    }

    public void drawTexturedModalRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height) {
        final float f = 0.00390625f;
        final float f2 = 0.00390625f;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x + 0, y + height, this.zLevelFloat).tex((textureX + 0) * 0.00390625f, (textureY + height) * 0.00390625f).endVertex();
        worldrenderer.pos(x + width, y + height, this.zLevelFloat).tex((textureX + width) * 0.00390625f, (textureY + height) * 0.00390625f).endVertex();
        worldrenderer.pos(x + width, y + 0, this.zLevelFloat).tex((textureX + width) * 0.00390625f, (textureY + 0) * 0.00390625f).endVertex();
        worldrenderer.pos(x + 0, y + 0, this.zLevelFloat).tex((textureX + 0) * 0.00390625f, (textureY + 0) * 0.00390625f).endVertex();
        tessellator.draw();
    }
}
