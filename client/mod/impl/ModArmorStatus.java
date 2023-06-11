package client.mod.impl;

import client.hud.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class ModArmorStatus extends HudMod {
    public ModArmorStatus() {
        super("Armor", 350, 170, false);
    }

    @Override
    public int getWidth() {
        return 64;
    }

    @Override
    public int getHeight() {
        return 64;
    }

    @Override
    public void draw() {
        for (int i = 0; i < ModArmorStatus.mc.thePlayer.inventory.armorInventory.length; ++i) {
            final ItemStack itemStack = ModArmorStatus.mc.thePlayer.inventory.armorInventory[i];
            this.renderItemStack(i, itemStack);
        }
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        this.renderItemStack(3, new ItemStack(Items.diamond_helmet));
        this.renderItemStack(2, new ItemStack(Items.diamond_chestplate));
        this.renderItemStack(1, new ItemStack(Items.diamond_leggings));
        this.renderItemStack(0, new ItemStack(Items.diamond_boots));
        super.renderDummy(mouseX, mouseY);
    }

    private void renderItemStack(final int i, final ItemStack is) {
        if (is == null) {
            return;
        }
        GL11.glPushMatrix();
        final int yAdd = -16 * i + 48;
        if (is.getItem().isDamageable()) {
            final double damage = (is.getMaxDamage() - is.getItemDamage());// / (double)is.getMaxDamage() * 100.0;
            ModArmorStatus.fr.drawStringWithShadow(String.format("%.0f", damage), (float) (this.x() + 20), (float) (this.y() + yAdd + 5), -1);
        }
        RenderHelper.enableGUIStandardItemLighting();
        ModArmorStatus.mc.getRenderItem().renderItemAndEffectIntoGUI(is, this.x(), this.y() + yAdd);
        GL11.glPopMatrix();
    }
}
