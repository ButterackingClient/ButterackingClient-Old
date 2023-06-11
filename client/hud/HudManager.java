package client.hud;

import client.mod.impl.*;
import client.mod.impl.Timer;

import java.util.*;

public class HudManager {
	public <T extends HudMod> T getMod(Class<T> clazz) {
		  return (T) hudMods.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
		}
    public ArrayList<HudMod> hudMods;
    public ClientName cn;
    public ModArmorStatus mas;
    public ModBiom mb;
    public ModCPS mcps;
    public ModFPS mf;
    public ModKeystrokes mk;
    public ModPotionStatus mp;
    public ModXYZ mx;
    public PackDisplay pd;
    public ToggleSprintHud tsh;
    public Cape c;
    public Timer t;
    public Cape2 c2;
    public Blockoutline bl;
    public HitColor h;
    public AutoGG gg;
    public CustomGui cg;
    public OldSneak os;
    public ModPerspective ps;
    public Snaplook sl;
    public WIngs w;
    public WIngs2 w2;
    public PingMod p;
    public Oldanimations oa;
    public ClearChat cc;

    public HudManager() {
        (this.hudMods = new ArrayList<HudMod>()).add(this.tsh = new ToggleSprintHud());
        this.hudMods.add(this.cn = new ClientName());
        this.hudMods.add(this.mas = new ModArmorStatus());
        this.hudMods.add(this.mb = new ModBiom());
        this.hudMods.add(this.mcps = new ModCPS());
        this.hudMods.add(this.mf = new ModFPS());
        this.hudMods.add(this.mk = new ModKeystrokes());
        this.hudMods.add(this.mp = new ModPotionStatus());
        this.hudMods.add(this.mx = new ModXYZ());
        this.hudMods.add(this.pd = new PackDisplay());
        this.hudMods.add(this.c = new Cape());
        this.hudMods.add(this.t = new Timer());
        this.hudMods.add(this.c2 = new Cape2());
        this.hudMods.add(this.bl = new Blockoutline());
        this.hudMods.add(this.h = new HitColor());
        this.hudMods.add(this.gg = new AutoGG());
        this.hudMods.add(this.cg = new CustomGui());
        this.hudMods.add(this.os = new OldSneak());
        this.hudMods.add(this.sl = new Snaplook());
        this.hudMods.add(this.w = new WIngs());
        this.hudMods.add(this.w2 = new WIngs2());
        this.hudMods.add(this.p = new PingMod());
        this.hudMods.add(this.oa = new Oldanimations());
        this.hudMods.add(this.cc = new ClearChat());
    }

    public void renderMods() {
        for (final HudMod m : this.hudMods) {
            if (m.isEnabled()) {
                m.draw();
            }
        }
    }
}
