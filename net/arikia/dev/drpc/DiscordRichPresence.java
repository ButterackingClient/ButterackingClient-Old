/*     */ package net.arikia.dev.drpc;
/*     */ 
/*     */ import com.sun.jna.Structure;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiscordRichPresence
/*     */   extends Structure
/*     */ {
/*     */   public String state;
/*     */   public String details;
/*     */   public long startTimestamp;
/*     */   public long endTimestamp;
/*     */   public String largeImageKey;
/*     */   public String largeImageText;
/*     */   public String smallImageKey;
/*     */   public String smallImageText;
/*     */   public String partyId;
/*     */   public int partySize;
/*     */   public int partyMax;
/*     */   @Deprecated
/*     */   public String matchSecret;
/*     */   public String spectateSecret;
/*     */   public String joinSecret;
/*     */   @Deprecated
/*     */   public int instance;
/*     */   
/*     */   public List<String> getFieldOrder() {
/*  79 */     return Arrays.asList(new String[] { "state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private DiscordRichPresence p;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder(String state) {
/*  98 */       this.p = new DiscordRichPresence();
/*  99 */       this.p.state = state;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setDetails(String details) {
/* 110 */       this.p.details = details;
/* 111 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setStartTimestamps(long start) {
/* 122 */       this.p.startTimestamp = start;
/* 123 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setEndTimestamp(long end) {
/* 134 */       this.p.endTimestamp = end;
/* 135 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setBigImage(String key, String text) {
/* 147 */       if (text != null && !text.equalsIgnoreCase("") && key == null) {
/* 148 */         throw new IllegalArgumentException("Image key must not be null when assigning a hover text.");
/*     */       }
/* 150 */       this.p.largeImageKey = key;
/* 151 */       this.p.largeImageText = text;
/* 152 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setSmallImage(String key, String text) {
/* 164 */       if (text != null && !text.equalsIgnoreCase("") && key == null) {
/* 165 */         throw new IllegalArgumentException("Image key must not be null when assigning a hover text.");
/*     */       }
/* 167 */       this.p.smallImageKey = key;
/* 168 */       this.p.smallImageText = text;
/* 169 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setParty(String party, int size, int max) {
/* 182 */       this.p.partyId = party;
/* 183 */       this.p.partySize = size;
/* 184 */       this.p.partyMax = max;
/* 185 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder setSecrets(String match, String join, String spectate) {
/* 193 */       this.p.matchSecret = match;
/* 194 */       this.p.joinSecret = join;
/* 195 */       this.p.spectateSecret = spectate;
/* 196 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setSecrets(String join, String spectate) {
/* 208 */       this.p.joinSecret = join;
/* 209 */       this.p.spectateSecret = spectate;
/* 210 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder setInstance(boolean i) {
/* 218 */       this.p.instance = i ? 1 : 0;
/* 219 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DiscordRichPresence build() {
/* 229 */       return this.p;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\arikia\dev\drpc\DiscordRichPresence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */