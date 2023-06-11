package client.config;

import client.*;
import client.hud.*;

import java.io.*;
import java.util.*;

public class Config {
    public File configFolder;
    public Configuration config;
    public Configuration configToSave;

    public Config() {
        this.configFolder = new File(Client.I.n);
        this.configToSave = ConfigurationAPI.newConfiguration(new File(String.valueOf(String.valueOf(Client.I.n)) + "/ModsConfig.json"));
    }

    public void saveModConfig() {
        if (!this.configFolder.exists()) {
            this.configFolder.mkdirs();
        }
        System.out.println("Saving Mods Config");
        for (final HudMod m : Client.I.hudManager.hudMods) {
            this.configToSave.set(String.valueOf(String.valueOf(m.name.toLowerCase())) + " x", m.x());
            this.configToSave.set(String.valueOf(String.valueOf(m.name.toLowerCase())) + " y", m.y());
            this.configToSave.set(String.valueOf(String.valueOf(m.name.toLowerCase())) + " enabled", m.isEnabled());
        }
        try {
            this.configToSave.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadModConfig() {
        try {
            this.config = ConfigurationAPI.loadExistingConfiguration(new File(String.valueOf(String.valueOf(Client.I.n)) + "/ModsConfig.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
