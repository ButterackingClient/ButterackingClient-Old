/*    */ package net.arikia.dev.drpc;
/*    */ 
/*    */ import com.sun.jna.Structure;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.arikia.dev.drpc.callbacks.DisconnectedCallback;
/*    */ import net.arikia.dev.drpc.callbacks.ErroredCallback;
/*    */ import net.arikia.dev.drpc.callbacks.JoinGameCallback;
/*    */ import net.arikia.dev.drpc.callbacks.JoinRequestCallback;
/*    */ import net.arikia.dev.drpc.callbacks.ReadyCallback;
/*    */ import net.arikia.dev.drpc.callbacks.SpectateGameCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiscordEventHandlers
/*    */   extends Structure
/*    */ {
/*    */   public ReadyCallback ready;
/*    */   public DisconnectedCallback disconnected;
/*    */   public ErroredCallback errored;
/*    */   public JoinGameCallback joinGame;
/*    */   public SpectateGameCallback spectateGame;
/*    */   public JoinRequestCallback joinRequest;
/*    */   
/*    */   public List<String> getFieldOrder() {
/* 45 */     return Arrays.asList(new String[] { "ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Builder
/*    */   {
/* 53 */     DiscordEventHandlers h = new DiscordEventHandlers();
/*    */ 
/*    */     
/*    */     public Builder setReadyEventHandler(ReadyCallback r) {
/* 57 */       this.h.ready = r;
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setDisconnectedEventHandler(DisconnectedCallback d) {
/* 62 */       this.h.disconnected = d;
/* 63 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setErroredEventHandler(ErroredCallback e) {
/* 67 */       this.h.errored = e;
/* 68 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setJoinGameEventHandler(JoinGameCallback j) {
/* 72 */       this.h.joinGame = j;
/* 73 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setSpectateGameEventHandler(SpectateGameCallback s) {
/* 77 */       this.h.spectateGame = s;
/* 78 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setJoinRequestEventHandler(JoinRequestCallback j) {
/* 82 */       this.h.joinRequest = j;
/* 83 */       return this;
/*    */     }
/*    */     
/*    */     public DiscordEventHandlers build() {
/* 87 */       return this.h;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\arikia\dev\drpc\DiscordEventHandlers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */