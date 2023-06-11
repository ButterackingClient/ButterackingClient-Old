package client.mod;

import net.minecraft.client.*;
import client.*;
import client.event.*;

public class Mod {
    public Minecraft mc;
    public String name;
    public String description;
    public boolean enabled;
    public Category category;

    public Mod(final String name, final String description, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    public void onEnable() {
        final EventManager eventManager = Client.I.eventManager;
        EventManager.register(this);
    }

    public void onDisable() {
        final EventManager eventManager = Client.I.eventManager;
        EventManager.unregister(this);
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggle() {
        this.setEnabled(!this.enabled);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isToggled() {
        return this.enabled;
    }

    public boolean isDisabled() {
        return !this.enabled;
    }
}
