package client.mod.impl;

import client.hud.*;
import org.lwjgl.opengl.*;

import java.awt.*;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import client.*;

public class ModKeystrokes extends HudMod {
    private KeystokesMode mode;

    public ModKeystrokes() {
        super("KeyStrokes", 78, 35, false);
        this.mode = KeystokesMode.WASD_JUMP_MOUSE;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @Override
    public int getWidth() {
        return 60;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        Key[] keys;
        for (int length = (keys = this.mode.getKeys()).length, i = 0; i < length; ++i) {
            final Key key = keys[i];
            final int textWidth = ModKeystrokes.fr.getStringWidth(key.getName());
            Gui.drawRect(this.x() + key.getX(), this.y() + key.getY(), this.x() + key.getX() + key.getWidth(), this.y() + key.getY() + key.getHeight(), key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 120).getRGB());
            ModKeystrokes.fr.drawStringWithShadow(key.getName(), (float) (this.x() + key.getX() + key.getWidth() / 2 - textWidth / 2), (float) (this.y() + key.getY() + key.getHeight() / 2 - 4), key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderDummy(final int mouseX, final int mouseY) {
        GL11.glPushMatrix();
        Key[] keys;
        for (int length = (keys = this.mode.getKeys()).length, i = 0; i < length; ++i) {
            final Key key = keys[i];
            final int textWidth = ModKeystrokes.fr.getStringWidth(key.getName());
            Gui.drawRect(this.x() + key.getX(), this.y() + key.getY(), this.x() + key.getX() + key.getWidth(), this.y() + key.getY() + key.getHeight(), key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 120).getRGB());
            ModKeystrokes.fr.drawStringWithShadow(key.getName(), (float) (this.x() + key.getX() + key.getWidth() / 2 - textWidth / 2), (float) (this.y() + key.getY() + key.getHeight() / 2 - 4), key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
        }
        GL11.glPopMatrix();
        super.renderDummy(mouseX, mouseY);
    }

    public enum KeystokesMode {
        WASD("WASD", 0, "WASD", 0, "WASD", 0, "WASD", 0, new Key[]{Key.W, Key.A, Key.S, Key.D}),
        WASD_MOUSE("WASD_MOUSE", 1, "WASD_MOUSE", 1, "WASD_MOUSE", 1, "WASD_MOUSE", 1, new Key[]{Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB}),
        WASD_JUMP("WASD_JUMP", 2, "WASD_JUMP", 2, "WASD_JUMP", 2, "WASD_JUMP", 2, new Key[]{Key.W, Key.A, Key.S, Key.D, Key.Jump1}),
        WASD_JUMP_MOUSE("WASD_JUMP_MOUSE", 3, "WASD_JUMP_MOUSE", 3, "WASD_JUMP_MOUSE", 3, "WASD_JUMP_MOUSE", 3, new Key[]{Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, Key.Jump2});

        private final Key[] keys;
        private int width;
        private int height;

        private KeystokesMode(final String s4, final int n4, final String s3, final int n3, final String s2, final int n2, final String s, final int n, final Key... keysIn) {
            this.keys = keysIn;
            Key[] keys;
            for (int length = (keys = this.keys).length, i = 0; i < length; ++i) {
                final Key key = keys[i];
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());
            }
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        public Key[] getKeys() {
            return this.keys;
        }
    }

    public static class Key {
        public static Minecraft mc;
        private static final Key W;
        private static final Key A;
        private static final Key S;
        private static final Key D;
        private static final Key LMB;
        private static final Key RMB;
        private static final Key Jump1;
        private static final Key Jump2;
        private final String name;
        private final KeyBinding keyBind;
        private final int x;
        private final int y;
        private final int w;
        private final int h;

        static {
            Key.mc = Minecraft.getMinecraft();
            W = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "W", Key.mc.gameSettings.keyBindLeft, 21, 1, 18, 18);
            A = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "A", Key.mc.gameSettings.keyBindBack, 1, 21, 18, 18);
            S = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "S", Key.mc.gameSettings.keyBindRight, 21, 21, 18, 18);
            D = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "D", Key.mc.gameSettings.keyBindJump, 41, 21, 18, 18);
            LMB = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "LMB", Key.mc.gameSettings.keyBindPickBlock, 1, 41, 28, 18);
            RMB = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "RMB", Key.mc.gameSettings.keyBindDrop, 31, 41, 28, 18);
            Jump1 = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "----", Key.mc.gameSettings.keyBindSneak, 1, 41, 58, 18);
            Jump2 = new Key(String.valueOf(String.valueOf(String.valueOf(String.valueOf(Client.I.m)))) + "----", Key.mc.gameSettings.keyBindSneak, 1, 61, 58, 18);
        }

        public Key(final String name, final KeyBinding keyBind, final int x, final int y, final int w, final int h) {
            this.name = name;
            this.keyBind = keyBind;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public boolean isDown() {
            return this.keyBind.isKeyDown();
        }

        public int getHeight() {
            return this.h;
        }

        public int getWidth() {
            return this.w;
        }

        public String getName() {
            return this.name;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }
}
