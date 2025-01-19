/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ 
/*     */ public class S3EPacketTeams
/*     */   implements Packet<INetHandlerPlayClient> {
/*  15 */   private String name = "";
/*  16 */   private String displayName = "";
/*  17 */   private String prefix = "";
/*  18 */   private String suffix = "";
/*     */   private String nameTagVisibility;
/*     */   private int color;
/*     */   private Collection<String> players;
/*     */   private int action;
/*     */   private int friendlyFlags;
/*     */   
/*     */   public S3EPacketTeams() {
/*  26 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  27 */     this.color = -1;
/*  28 */     this.players = Lists.newArrayList();
/*     */   }
/*     */   
/*     */   public S3EPacketTeams(ScorePlayerTeam teamIn, int actionIn) {
/*  32 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  33 */     this.color = -1;
/*  34 */     this.players = Lists.newArrayList();
/*  35 */     this.name = teamIn.getRegisteredName();
/*  36 */     this.action = actionIn;
/*     */     
/*  38 */     if (actionIn == 0 || actionIn == 2) {
/*  39 */       this.displayName = teamIn.getTeamName();
/*  40 */       this.prefix = teamIn.getColorPrefix();
/*  41 */       this.suffix = teamIn.getColorSuffix();
/*  42 */       this.friendlyFlags = teamIn.func_98299_i();
/*  43 */       this.nameTagVisibility = (teamIn.getNameTagVisibility()).internalName;
/*  44 */       this.color = teamIn.getChatFormat().getColorIndex();
/*     */     } 
/*     */     
/*  47 */     if (actionIn == 0) {
/*  48 */       this.players.addAll(teamIn.getMembershipCollection());
/*     */     }
/*     */   }
/*     */   
/*     */   public S3EPacketTeams(ScorePlayerTeam teamIn, Collection<String> playersIn, int actionIn) {
/*  53 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  54 */     this.color = -1;
/*  55 */     this.players = Lists.newArrayList();
/*     */     
/*  57 */     if (actionIn != 3 && actionIn != 4)
/*  58 */       throw new IllegalArgumentException("Method must be join or leave for player constructor"); 
/*  59 */     if (playersIn != null && !playersIn.isEmpty()) {
/*  60 */       this.action = actionIn;
/*  61 */       this.name = teamIn.getRegisteredName();
/*  62 */       this.players.addAll(playersIn);
/*     */     } else {
/*  64 */       throw new IllegalArgumentException("Players cannot be null/empty");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  72 */     this.name = buf.readStringFromBuffer(16);
/*  73 */     this.action = buf.readByte();
/*     */     
/*  75 */     if (this.action == 0 || this.action == 2) {
/*  76 */       this.displayName = buf.readStringFromBuffer(32);
/*  77 */       this.prefix = buf.readStringFromBuffer(16);
/*  78 */       this.suffix = buf.readStringFromBuffer(16);
/*  79 */       this.friendlyFlags = buf.readByte();
/*  80 */       this.nameTagVisibility = buf.readStringFromBuffer(32);
/*  81 */       this.color = buf.readByte();
/*     */     } 
/*     */     
/*  84 */     if (this.action == 0 || this.action == 3 || this.action == 4) {
/*  85 */       int i = buf.readVarIntFromBuffer();
/*     */       
/*  87 */       for (int j = 0; j < i; j++) {
/*  88 */         this.players.add(buf.readStringFromBuffer(40));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  97 */     buf.writeString(this.name);
/*  98 */     buf.writeByte(this.action);
/*     */     
/* 100 */     if (this.action == 0 || this.action == 2) {
/* 101 */       buf.writeString(this.displayName);
/* 102 */       buf.writeString(this.prefix);
/* 103 */       buf.writeString(this.suffix);
/* 104 */       buf.writeByte(this.friendlyFlags);
/* 105 */       buf.writeString(this.nameTagVisibility);
/* 106 */       buf.writeByte(this.color);
/*     */     } 
/*     */     
/* 109 */     if (this.action == 0 || this.action == 3 || this.action == 4) {
/* 110 */       buf.writeVarIntToBuffer(this.players.size());
/*     */       
/* 112 */       for (String s : this.players) {
/* 113 */         buf.writeString(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 122 */     handler.handleTeams(this);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 126 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 130 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 134 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public String getSuffix() {
/* 138 */     return this.suffix;
/*     */   }
/*     */   
/*     */   public Collection<String> getPlayers() {
/* 142 */     return this.players;
/*     */   }
/*     */   
/*     */   public int getAction() {
/* 146 */     return this.action;
/*     */   }
/*     */   
/*     */   public int getFriendlyFlags() {
/* 150 */     return this.friendlyFlags;
/*     */   }
/*     */   
/*     */   public int getColor() {
/* 154 */     return this.color;
/*     */   }
/*     */   
/*     */   public String getNameTagVisibility() {
/* 158 */     return this.nameTagVisibility;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S3EPacketTeams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */