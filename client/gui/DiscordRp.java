/*    */ package client.gui;
/*    */ 
/*    */ import client.Client;
/*    */ import net.arikia.dev.drpc.DiscordEventHandlers;
/*    */ import net.arikia.dev.drpc.DiscordRPC;
/*    */ import net.arikia.dev.drpc.DiscordRichPresence;
/*    */ import net.arikia.dev.drpc.DiscordUser;
/*    */ import net.arikia.dev.drpc.callbacks.ReadyCallback;
/*    */ 
/*    */ public class DiscordRp
/*    */ {
/*    */   private boolean running = true;
/*    */   private long created;
/*    */   
/*    */   public void start() {
/* 16 */     this.created = System.currentTimeMillis();
/* 17 */     DiscordEventHandlers handlers = (new DiscordEventHandlers.Builder()).setReadyEventHandler(new ReadyCallback()
/*    */         {
/*    */           public void apply(DiscordUser user) {
/* 20 */             System.out.println("Websome " + user.username + "#" + user.discriminator);
/* 21 */             DiscordRp.this.update("", "");
/*    */           }
/* 23 */         }).build();
/* 24 */     DiscordRPC.discordInitialize((Client.getInstance()).discordid, handlers, true);
/* 25 */     (new Thread("Discord RPC Callback")
/*    */       {
/*    */         public void run() {
/* 28 */           while (DiscordRp.this.running) {
/* 29 */             DiscordRPC.discordRunCallbacks();
/*    */           }
/*    */         }
/* 32 */       }).start();
/*    */   }
/*    */   
/*    */   public void shutdown() {
/* 36 */     this.running = false;
/* 37 */     DiscordRPC.discordShutdown();
/*    */   }
/*    */   
/*    */   public void update(String firstLine, String secondLine) {
/* 41 */     DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
/* 42 */     b.setBigImage((Client.getInstance()).discordl, "");
/* 43 */     b.setDetails(firstLine);
/* 44 */     b.setStartTimestamps(this.created);
/* 45 */     DiscordRPC.discordUpdatePresence(b.build());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\gui\DiscordRp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */