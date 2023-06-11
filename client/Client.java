package client;

import net.minecraft.client.*;
import client.mod.*;
import client.mod.impl.PerspectiveMod;

import org.lwjgl.input.Keyboard;

import client.config.*;
import client.gui.*;
import client.timer.*;
import kp.input.KoreanIME;
import client.event.impl.*;
import client.hud.*;
import net.minecraft.client.gui.*;
import client.gui.click.*;
import client.event.*;

public class Client {
    public String m;
    public String s;
    public String w;
    public String rw;
    public String c;
    public String r;
    public String n;
    public String nn;
    public static Client I;
    public Minecraft mc;
    public EventManager eventManager;
    public ModManager modManager;
    public HudManager hudManager;
    private DiscordRp discordrp;
    public Config config;
    public int spr;
    public int spb;
    public int spg;
    public float pr;
    public float pb;
    public float pg;
    public boolean iskpneeded;

    static {
        Client.I = new Client();
    }

    public Client() {
    	this.iskpneeded = true;
        this.m = "§5";
        this.s = "§7";
        this.w = "§7";
        this.rw = "§f";
        this.c = "!";
        this.r = "§f";
        this.n = "Butteracking Client";
        this.mc = Minecraft.getMinecraft();
        this.discordrp = new DiscordRp();
        this.spr = 133;
        this.spb = 255;
        this.spg = 0;
        this.pr = 0.4F;
        this.pb = 1.0F;
        this.pg = 0.0F;
    }

    public void init() {
        this.discordrp.start();
    }

    public void splashstart() {
        SplashProgress.setProgress(1, "Client - Starting");
    }

    public void startup() {
        DeltaTimer.D.stop();
        this.eventManager = new EventManager();
        (this.config = new Config()).loadModConfig();
        this.modManager = new ModManager();
        this.hudManager = new HudManager();
        System.out.println(this.c);
        EventManager.register(this);
    }

    public void shutdown() {
        System.out.println(this.c);
        this.config.saveModConfig();
        EventManager.unregister(this);
        this.discordrp.shutdown();
    }

    public DiscordRp getDiscordrp() {
        return this.discordrp;
    }

    @EventTarget
    public void onTick(final ClientTick event) {
    	if (this.iskpneeded == false) {
    		KoreanIME.enabled = false;
    	}
        if (this.mc.gameSettings.keyBindInventory.isPressed()) {
            this.modManager.toggleSprint.toggle();
        }
        if (this.mc.gameSettings.keydraghud.isPressed()) {
            this.mc.displayGuiScreen(new HUDConfigScreen());
        }
        if (this.mc.gameSettings.keyfullbright.isPressed()) {
            this.modManager.fullbright.toggle();
        }
        if (this.mc.gameSettings.keyclickgui.isKeyDown()) {
            this.mc.displayGuiScreen(new ClickGui());
        }
    }
    @EventTarget
    public void keyev(KeyEvent e) { 
    	if (this.hudManager.sl.isEnabled()) {
    		boolean yee = false;
    		final Minecraft mc = Minecraft.getMinecraft();
            if (e.getKey() == mc.gameSettings.keySnplook.getKeyCode()) {
                if (Keyboard.getEventKeyState()) {
                    yee = !yee;
                    if (yee) {
                        PerspectiveMod.previousePrespective = mc.gameSettings.showDebugInfo;
                        mc.gameSettings.showDebugInfo = 1;
                    }
                    else {
                        mc.gameSettings.showDebugInfo = PerspectiveMod.previousePrespective;
                    }
                }
                else if (PerspectiveMod.returnOnRelease) {
                    yee = false;
                    mc.gameSettings.showDebugInfo = PerspectiveMod.previousePrespective;
                }
            }
            if (Keyboard.getEventKey() == mc.gameSettings.keyBindTogglePerspective.getKeyCode()) {
                yee = false;
            }
        }
    }
    
}
