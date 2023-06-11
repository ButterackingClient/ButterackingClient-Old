package client.mod.impl;

import client.hud.*;
import net.minecraft.util.*;
import client.*;
import net.minecraft.world.chunk.*;

public class ModBiom extends HudMod {
    public ModBiom() {
        super("Biome", 5, 15, false);
    }

    @Override
    public int getWidth() {
        return ModBiom.fr.getStringWidth("[Biome] : Desert");
    }

    @Override
    public void draw() {
        final BlockPos blockpos = new BlockPos(ModBiom.mc.getRenderViewEntity().posX, ModBiom.mc.getRenderViewEntity().getEntityBoundingBox().minY, ModBiom.mc.getRenderViewEntity().posZ);
        final Chunk chunk = ModBiom.mc.theWorld.getChunkFromBlockCoords(blockpos);
        ModBiom.fr.drawStringWithShadow( Client.I.m + chunk.getBiome(blockpos, ModBiom.mc.theWorld.getWorldChunkManager()).biomeName, (float) this.x(), (float) this.y(), -1);
        super.draw();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        ModBiom.fr.drawStringWithShadow(Client.I.m + "Desert", (float) this.x(), (float) this.y(), -1);
        super.renderDummy(mouseX, mouseY);
    }
}
