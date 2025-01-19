/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*     */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*     */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*     */ import net.minecraft.network.play.server.S3EPacketTeams;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class ServerScoreboard
/*     */   extends Scoreboard
/*     */ {
/*     */   private final MinecraftServer scoreboardMCServer;
/*  20 */   private final Set<ScoreObjective> field_96553_b = Sets.newHashSet();
/*     */   private ScoreboardSaveData scoreboardSaveData;
/*     */   
/*     */   public ServerScoreboard(MinecraftServer mcServer) {
/*  24 */     this.scoreboardMCServer = mcServer;
/*     */   }
/*     */   
/*     */   public void func_96536_a(Score p_96536_1_) {
/*  28 */     super.func_96536_a(p_96536_1_);
/*     */     
/*  30 */     if (this.field_96553_b.contains(p_96536_1_.getObjective())) {
/*  31 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_96536_1_));
/*     */     }
/*     */     
/*  34 */     markSaveDataDirty();
/*     */   }
/*     */   
/*     */   public void func_96516_a(String p_96516_1_) {
/*  38 */     super.func_96516_a(p_96516_1_);
/*  39 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_96516_1_));
/*  40 */     markSaveDataDirty();
/*     */   }
/*     */   
/*     */   public void func_178820_a(String p_178820_1_, ScoreObjective p_178820_2_) {
/*  44 */     super.func_178820_a(p_178820_1_, p_178820_2_);
/*  45 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3CPacketUpdateScore(p_178820_1_, p_178820_2_));
/*  46 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveInDisplaySlot(int p_96530_1_, ScoreObjective p_96530_2_) {
/*  53 */     ScoreObjective scoreobjective = getObjectiveInDisplaySlot(p_96530_1_);
/*  54 */     super.setObjectiveInDisplaySlot(p_96530_1_, p_96530_2_);
/*     */     
/*  56 */     if (scoreobjective != p_96530_2_ && scoreobjective != null) {
/*  57 */       if (func_96552_h(scoreobjective) > 0) {
/*  58 */         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
/*     */       } else {
/*  60 */         sendDisplaySlotRemovalPackets(scoreobjective);
/*     */       } 
/*     */     }
/*     */     
/*  64 */     if (p_96530_2_ != null) {
/*  65 */       if (this.field_96553_b.contains(p_96530_2_)) {
/*  66 */         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3DPacketDisplayScoreboard(p_96530_1_, p_96530_2_));
/*     */       } else {
/*  68 */         func_96549_e(p_96530_2_);
/*     */       } 
/*     */     }
/*     */     
/*  72 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String player, String newTeam) {
/*  79 */     if (super.addPlayerToTeam(player, newTeam)) {
/*  80 */       ScorePlayerTeam scoreplayerteam = getTeam(newTeam);
/*  81 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(scoreplayerteam, Arrays.asList(new String[] { player }, ), 3));
/*  82 */       markSaveDataDirty();
/*  83 */       return true;
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_) {
/*  94 */     super.removePlayerFromTeam(p_96512_1_, p_96512_2_);
/*  95 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(p_96512_2_, Arrays.asList(new String[] { p_96512_1_ }, ), 4));
/*  96 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn) {
/* 103 */     super.onScoreObjectiveAdded(scoreObjectiveIn);
/* 104 */     markSaveDataDirty();
/*     */   }
/*     */   
/*     */   public void onObjectiveDisplayNameChanged(ScoreObjective p_96532_1_) {
/* 108 */     super.onObjectiveDisplayNameChanged(p_96532_1_);
/*     */     
/* 110 */     if (this.field_96553_b.contains(p_96532_1_)) {
/* 111 */       this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3BPacketScoreboardObjective(p_96532_1_, 2));
/*     */     }
/*     */     
/* 114 */     markSaveDataDirty();
/*     */   }
/*     */   
/*     */   public void onScoreObjectiveRemoved(ScoreObjective p_96533_1_) {
/* 118 */     super.onScoreObjectiveRemoved(p_96533_1_);
/*     */     
/* 120 */     if (this.field_96553_b.contains(p_96533_1_)) {
/* 121 */       sendDisplaySlotRemovalPackets(p_96533_1_);
/*     */     }
/*     */     
/* 124 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamCreated(ScorePlayerTeam playerTeam) {
/* 131 */     super.broadcastTeamCreated(playerTeam);
/* 132 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 0));
/* 133 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTeamUpdate(ScorePlayerTeam playerTeam) {
/* 140 */     super.sendTeamUpdate(playerTeam);
/* 141 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 2));
/* 142 */     markSaveDataDirty();
/*     */   }
/*     */   
/*     */   public void func_96513_c(ScorePlayerTeam playerTeam) {
/* 146 */     super.func_96513_c(playerTeam);
/* 147 */     this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers((Packet)new S3EPacketTeams(playerTeam, 1));
/* 148 */     markSaveDataDirty();
/*     */   }
/*     */   
/*     */   public void func_96547_a(ScoreboardSaveData p_96547_1_) {
/* 152 */     this.scoreboardSaveData = p_96547_1_;
/*     */   }
/*     */   
/*     */   protected void markSaveDataDirty() {
/* 156 */     if (this.scoreboardSaveData != null) {
/* 157 */       this.scoreboardSaveData.markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Packet> func_96550_d(ScoreObjective p_96550_1_) {
/* 162 */     List<Packet> list = Lists.newArrayList();
/* 163 */     list.add(new S3BPacketScoreboardObjective(p_96550_1_, 0));
/*     */     
/* 165 */     for (int i = 0; i < 19; i++) {
/* 166 */       if (getObjectiveInDisplaySlot(i) == p_96550_1_) {
/* 167 */         list.add(new S3DPacketDisplayScoreboard(i, p_96550_1_));
/*     */       }
/*     */     } 
/*     */     
/* 171 */     for (Score score : getSortedScores(p_96550_1_)) {
/* 172 */       list.add(new S3CPacketUpdateScore(score));
/*     */     }
/*     */     
/* 175 */     return list;
/*     */   }
/*     */   
/*     */   public void func_96549_e(ScoreObjective p_96549_1_) {
/* 179 */     List<Packet> list = func_96550_d(p_96549_1_);
/*     */     
/* 181 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().getPlayerList()) {
/* 182 */       for (Packet packet : list) {
/* 183 */         entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 187 */     this.field_96553_b.add(p_96549_1_);
/*     */   }
/*     */   
/*     */   public List<Packet> func_96548_f(ScoreObjective p_96548_1_) {
/* 191 */     List<Packet> list = Lists.newArrayList();
/* 192 */     list.add(new S3BPacketScoreboardObjective(p_96548_1_, 1));
/*     */     
/* 194 */     for (int i = 0; i < 19; i++) {
/* 195 */       if (getObjectiveInDisplaySlot(i) == p_96548_1_) {
/* 196 */         list.add(new S3DPacketDisplayScoreboard(i, p_96548_1_));
/*     */       }
/*     */     } 
/*     */     
/* 200 */     return list;
/*     */   }
/*     */   
/*     */   public void sendDisplaySlotRemovalPackets(ScoreObjective p_96546_1_) {
/* 204 */     List<Packet> list = func_96548_f(p_96546_1_);
/*     */     
/* 206 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getConfigurationManager().getPlayerList()) {
/* 207 */       for (Packet packet : list) {
/* 208 */         entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 212 */     this.field_96553_b.remove(p_96546_1_);
/*     */   }
/*     */   
/*     */   public int func_96552_h(ScoreObjective p_96552_1_) {
/* 216 */     int i = 0;
/*     */     
/* 218 */     for (int j = 0; j < 19; j++) {
/* 219 */       if (getObjectiveInDisplaySlot(j) == p_96552_1_) {
/* 220 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 224 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\ServerScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */