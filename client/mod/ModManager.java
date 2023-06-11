package client.mod;

import client.mod.impl.*;

import java.util.*;

public class ModManager {
    public ToggleSprint toggleSprint;
    public Fullbright fullbright;
    public ArrayList<Mod> mods;
    public Snaplook2 perspectiveMod;

    public ModManager() {
        (this.mods = new ArrayList<Mod>()).add(this.toggleSprint = new ToggleSprint());
        this.mods.add(this.fullbright = new Fullbright());
        this.mods.add(this.perspectiveMod = new Snaplook2());
    }
}
