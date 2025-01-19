/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ public class ScorePlayerTeam
/*     */   extends Team
/*     */ {
/*     */   private final Scoreboard theScoreboard;
/*     */   private final String registeredName;
/*  13 */   private final Set<String> membershipSet = Sets.newHashSet();
/*     */   private String teamNameSPT;
/*  15 */   private String namePrefixSPT = "";
/*  16 */   private String colorSuffix = "";
/*     */   private boolean allowFriendlyFire = true;
/*     */   private boolean canSeeFriendlyInvisibles = true;
/*  19 */   private Team.EnumVisible nameTagVisibility = Team.EnumVisible.ALWAYS;
/*  20 */   private Team.EnumVisible deathMessageVisibility = Team.EnumVisible.ALWAYS;
/*  21 */   private EnumChatFormatting chatFormat = EnumChatFormatting.RESET;
/*     */   
/*     */   public ScorePlayerTeam(Scoreboard theScoreboardIn, String name) {
/*  24 */     this.theScoreboard = theScoreboardIn;
/*  25 */     this.registeredName = name;
/*  26 */     this.teamNameSPT = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRegisteredName() {
/*  33 */     return this.registeredName;
/*     */   }
/*     */   
/*     */   public String getTeamName() {
/*  37 */     return this.teamNameSPT;
/*     */   }
/*     */   
/*     */   public void setTeamName(String name) {
/*  41 */     if (name == null) {
/*  42 */       throw new IllegalArgumentException("Name cannot be null");
/*     */     }
/*  44 */     this.teamNameSPT = name;
/*  45 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getMembershipCollection() {
/*  50 */     return this.membershipSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColorPrefix() {
/*  57 */     return this.namePrefixSPT;
/*     */   }
/*     */   
/*     */   public void setNamePrefix(String prefix) {
/*  61 */     if (prefix == null) {
/*  62 */       throw new IllegalArgumentException("Prefix cannot be null");
/*     */     }
/*  64 */     this.namePrefixSPT = prefix;
/*  65 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColorSuffix() {
/*  73 */     return this.colorSuffix;
/*     */   }
/*     */   
/*     */   public void setNameSuffix(String suffix) {
/*  77 */     this.colorSuffix = suffix;
/*  78 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */   
/*     */   public String formatString(String input) {
/*  82 */     return String.valueOf(getColorPrefix()) + input + getColorSuffix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatPlayerName(Team p_96667_0_, String p_96667_1_) {
/*  89 */     return (p_96667_0_ == null) ? p_96667_1_ : p_96667_0_.formatString(p_96667_1_);
/*     */   }
/*     */   
/*     */   public boolean getAllowFriendlyFire() {
/*  93 */     return this.allowFriendlyFire;
/*     */   }
/*     */   
/*     */   public void setAllowFriendlyFire(boolean friendlyFire) {
/*  97 */     this.allowFriendlyFire = friendlyFire;
/*  98 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */   
/*     */   public boolean getSeeFriendlyInvisiblesEnabled() {
/* 102 */     return this.canSeeFriendlyInvisibles;
/*     */   }
/*     */   
/*     */   public void setSeeFriendlyInvisiblesEnabled(boolean friendlyInvisibles) {
/* 106 */     this.canSeeFriendlyInvisibles = friendlyInvisibles;
/* 107 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */   
/*     */   public Team.EnumVisible getNameTagVisibility() {
/* 111 */     return this.nameTagVisibility;
/*     */   }
/*     */   
/*     */   public Team.EnumVisible getDeathMessageVisibility() {
/* 115 */     return this.deathMessageVisibility;
/*     */   }
/*     */   
/*     */   public void setNameTagVisibility(Team.EnumVisible p_178772_1_) {
/* 119 */     this.nameTagVisibility = p_178772_1_;
/* 120 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */   
/*     */   public void setDeathMessageVisibility(Team.EnumVisible p_178773_1_) {
/* 124 */     this.deathMessageVisibility = p_178773_1_;
/* 125 */     this.theScoreboard.sendTeamUpdate(this);
/*     */   }
/*     */   
/*     */   public int func_98299_i() {
/* 129 */     int i = 0;
/*     */     
/* 131 */     if (getAllowFriendlyFire()) {
/* 132 */       i |= 0x1;
/*     */     }
/*     */     
/* 135 */     if (getSeeFriendlyInvisiblesEnabled()) {
/* 136 */       i |= 0x2;
/*     */     }
/*     */     
/* 139 */     return i;
/*     */   }
/*     */   
/*     */   public void func_98298_a(int p_98298_1_) {
/* 143 */     setAllowFriendlyFire(((p_98298_1_ & 0x1) > 0));
/* 144 */     setSeeFriendlyInvisiblesEnabled(((p_98298_1_ & 0x2) > 0));
/*     */   }
/*     */   
/*     */   public void setChatFormat(EnumChatFormatting p_178774_1_) {
/* 148 */     this.chatFormat = p_178774_1_;
/*     */   }
/*     */   
/*     */   public EnumChatFormatting getChatFormat() {
/* 152 */     return this.chatFormat;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\ScorePlayerTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */