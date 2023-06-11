package client.mod.impl;

import org.lwjgl.opengl.Display;

import client.mod.Category;
import client.mod.Mod;
import net.minecraft.client.Minecraft;

public class PerspectiveMod extends Mod {
    public PerspectiveMod() {
        super("", "", Category.MISC);
    }

    public static float cameraYaw;
    public static float cameraPitch;
    public static int previousePrespective;
    public static boolean perspectiveToggled;
    public static boolean returnOnRelease;

    static {
        PerspectiveMod.cameraYaw = 0.0f;
        PerspectiveMod.cameraPitch = 0.0f;
        PerspectiveMod.previousePrespective = 0;
        PerspectiveMod.perspectiveToggled = false;
        PerspectiveMod.returnOnRelease = true;
    }


    public static float getCameraYaw() {
        final Minecraft mc = Minecraft.getMinecraft();
        return PerspectiveMod.perspectiveToggled ? PerspectiveMod.cameraYaw : Minecraft.getMinecraft().thePlayer.rotationYaw;
    }

    public static float getCameraPitch() {
        final Minecraft mc = Minecraft.getMinecraft();
        return PerspectiveMod.perspectiveToggled ? PerspectiveMod.cameraPitch : Minecraft.getMinecraft().thePlayer.rotationPitch;
    }

    public static boolean overriderMouse() {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.inGameHasFocus && Display.isActive()) {
            if (!PerspectiveMod.perspectiveToggled) {
                return true;
            }
            mc.mouseHelper.mouseXYChange();
            final float f1 = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float f2 = f1 * f1 * f1 * 8.0f;
            final float f3 = mc.mouseHelper.deltaX * f2;
            final float f4 = mc.mouseHelper.deltaY * f2;
            PerspectiveMod.cameraYaw += f3 * 0.15f;
            PerspectiveMod.cameraPitch += f4 * 0.15f;
            if (PerspectiveMod.cameraPitch > 90.0f) {
                PerspectiveMod.cameraPitch = 90.0f;
            }
            if (PerspectiveMod.cameraPitch < -90.0f) {
                PerspectiveMod.cameraPitch = -90.0f;
            }
        }
        return false;
    }
}
