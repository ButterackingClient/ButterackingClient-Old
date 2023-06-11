package client.gui;

import net.arikia.dev.drpc.callbacks.*;
import net.arikia.dev.drpc.*;

public class DiscordRp {
    private boolean running;
    private long created;

    public DiscordRp() {
        this.running = true;
    }

    public void start() {
        this.created = System.currentTimeMillis();
        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(final DiscordUser user) {
                System.out.println("Websome " + user.username + "#" + user.discriminator);
                DiscordRp.this.update("", "");
            }
        }).build();
        DiscordRPC.discordInitialize("1096728438171258980", handlers, true);
        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while (DiscordRp.this.running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        this.running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(final String firstLine, final String secondLine) {
        final DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
        b.setBigImage("logo", "");
        b.setDetails(firstLine);
        b.setStartTimestamps(this.created);
        DiscordRPC.discordUpdatePresence(b.build());
    }
}
