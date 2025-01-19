/*     */ package net.minecraft.scoreboard;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ScoreboardSaveData extends WorldSavedData {
/*  12 */   private static final Logger logger = LogManager.getLogger();
/*     */   private Scoreboard theScoreboard;
/*     */   private NBTTagCompound delayedInitNbt;
/*     */   
/*     */   public ScoreboardSaveData() {
/*  17 */     this("scoreboard");
/*     */   }
/*     */   
/*     */   public ScoreboardSaveData(String name) {
/*  21 */     super(name);
/*     */   }
/*     */   
/*     */   public void setScoreboard(Scoreboard scoreboardIn) {
/*  25 */     this.theScoreboard = scoreboardIn;
/*     */     
/*  27 */     if (this.delayedInitNbt != null) {
/*  28 */       readFromNBT(this.delayedInitNbt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  36 */     if (this.theScoreboard == null) {
/*  37 */       this.delayedInitNbt = nbt;
/*     */     } else {
/*  39 */       readObjectives(nbt.getTagList("Objectives", 10));
/*  40 */       readScores(nbt.getTagList("PlayerScores", 10));
/*     */       
/*  42 */       if (nbt.hasKey("DisplaySlots", 10)) {
/*  43 */         readDisplayConfig(nbt.getCompoundTag("DisplaySlots"));
/*     */       }
/*     */       
/*  46 */       if (nbt.hasKey("Teams", 9)) {
/*  47 */         readTeams(nbt.getTagList("Teams", 10));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readTeams(NBTTagList p_96498_1_) {
/*  53 */     for (int i = 0; i < p_96498_1_.tagCount(); i++) {
/*  54 */       NBTTagCompound nbttagcompound = p_96498_1_.getCompoundTagAt(i);
/*  55 */       String s = nbttagcompound.getString("Name");
/*     */       
/*  57 */       if (s.length() > 16) {
/*  58 */         s = s.substring(0, 16);
/*     */       }
/*     */       
/*  61 */       ScorePlayerTeam scoreplayerteam = this.theScoreboard.createTeam(s);
/*  62 */       String s1 = nbttagcompound.getString("DisplayName");
/*     */       
/*  64 */       if (s1.length() > 32) {
/*  65 */         s1 = s1.substring(0, 32);
/*     */       }
/*     */       
/*  68 */       scoreplayerteam.setTeamName(s1);
/*     */       
/*  70 */       if (nbttagcompound.hasKey("TeamColor", 8)) {
/*  71 */         scoreplayerteam.setChatFormat(EnumChatFormatting.getValueByName(nbttagcompound.getString("TeamColor")));
/*     */       }
/*     */       
/*  74 */       scoreplayerteam.setNamePrefix(nbttagcompound.getString("Prefix"));
/*  75 */       scoreplayerteam.setNameSuffix(nbttagcompound.getString("Suffix"));
/*     */       
/*  77 */       if (nbttagcompound.hasKey("AllowFriendlyFire", 99)) {
/*  78 */         scoreplayerteam.setAllowFriendlyFire(nbttagcompound.getBoolean("AllowFriendlyFire"));
/*     */       }
/*     */       
/*  81 */       if (nbttagcompound.hasKey("SeeFriendlyInvisibles", 99)) {
/*  82 */         scoreplayerteam.setSeeFriendlyInvisiblesEnabled(nbttagcompound.getBoolean("SeeFriendlyInvisibles"));
/*     */       }
/*     */       
/*  85 */       if (nbttagcompound.hasKey("NameTagVisibility", 8)) {
/*  86 */         Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(nbttagcompound.getString("NameTagVisibility"));
/*     */         
/*  88 */         if (team$enumvisible != null) {
/*  89 */           scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*     */         }
/*     */       } 
/*     */       
/*  93 */       if (nbttagcompound.hasKey("DeathMessageVisibility", 8)) {
/*  94 */         Team.EnumVisible team$enumvisible1 = Team.EnumVisible.func_178824_a(nbttagcompound.getString("DeathMessageVisibility"));
/*     */         
/*  96 */         if (team$enumvisible1 != null) {
/*  97 */           scoreplayerteam.setDeathMessageVisibility(team$enumvisible1);
/*     */         }
/*     */       } 
/*     */       
/* 101 */       func_96502_a(scoreplayerteam, nbttagcompound.getTagList("Players", 8));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_96502_a(ScorePlayerTeam p_96502_1_, NBTTagList p_96502_2_) {
/* 106 */     for (int i = 0; i < p_96502_2_.tagCount(); i++) {
/* 107 */       this.theScoreboard.addPlayerToTeam(p_96502_2_.getStringTagAt(i), p_96502_1_.getRegisteredName());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void readDisplayConfig(NBTTagCompound p_96504_1_) {
/* 112 */     for (int i = 0; i < 19; i++) {
/* 113 */       if (p_96504_1_.hasKey("slot_" + i, 8)) {
/* 114 */         String s = p_96504_1_.getString("slot_" + i);
/* 115 */         ScoreObjective scoreobjective = this.theScoreboard.getObjective(s);
/* 116 */         this.theScoreboard.setObjectiveInDisplaySlot(i, scoreobjective);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readObjectives(NBTTagList nbt) {
/* 122 */     for (int i = 0; i < nbt.tagCount(); i++) {
/* 123 */       NBTTagCompound nbttagcompound = nbt.getCompoundTagAt(i);
/* 124 */       IScoreObjectiveCriteria iscoreobjectivecriteria = IScoreObjectiveCriteria.INSTANCES.get(nbttagcompound.getString("CriteriaName"));
/*     */       
/* 126 */       if (iscoreobjectivecriteria != null) {
/* 127 */         String s = nbttagcompound.getString("Name");
/*     */         
/* 129 */         if (s.length() > 16) {
/* 130 */           s = s.substring(0, 16);
/*     */         }
/*     */         
/* 133 */         ScoreObjective scoreobjective = this.theScoreboard.addScoreObjective(s, iscoreobjectivecriteria);
/* 134 */         scoreobjective.setDisplayName(nbttagcompound.getString("DisplayName"));
/* 135 */         scoreobjective.setRenderType(IScoreObjectiveCriteria.EnumRenderType.func_178795_a(nbttagcompound.getString("RenderType")));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void readScores(NBTTagList nbt) {
/* 141 */     for (int i = 0; i < nbt.tagCount(); i++) {
/* 142 */       NBTTagCompound nbttagcompound = nbt.getCompoundTagAt(i);
/* 143 */       ScoreObjective scoreobjective = this.theScoreboard.getObjective(nbttagcompound.getString("Objective"));
/* 144 */       String s = nbttagcompound.getString("Name");
/*     */       
/* 146 */       if (s.length() > 40) {
/* 147 */         s = s.substring(0, 40);
/*     */       }
/*     */       
/* 150 */       Score score = this.theScoreboard.getValueFromObjective(s, scoreobjective);
/* 151 */       score.setScorePoints(nbttagcompound.getInteger("Score"));
/*     */       
/* 153 */       if (nbttagcompound.hasKey("Locked")) {
/* 154 */         score.setLocked(nbttagcompound.getBoolean("Locked"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound nbt) {
/* 163 */     if (this.theScoreboard == null) {
/* 164 */       logger.warn("Tried to save scoreboard without having a scoreboard...");
/*     */     } else {
/* 166 */       nbt.setTag("Objectives", (NBTBase)objectivesToNbt());
/* 167 */       nbt.setTag("PlayerScores", (NBTBase)scoresToNbt());
/* 168 */       nbt.setTag("Teams", (NBTBase)func_96496_a());
/* 169 */       func_96497_d(nbt);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected NBTTagList func_96496_a() {
/* 174 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 176 */     for (ScorePlayerTeam scoreplayerteam : this.theScoreboard.getTeams()) {
/* 177 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 178 */       nbttagcompound.setString("Name", scoreplayerteam.getRegisteredName());
/* 179 */       nbttagcompound.setString("DisplayName", scoreplayerteam.getTeamName());
/*     */       
/* 181 */       if (scoreplayerteam.getChatFormat().getColorIndex() >= 0) {
/* 182 */         nbttagcompound.setString("TeamColor", scoreplayerteam.getChatFormat().getFriendlyName());
/*     */       }
/*     */       
/* 185 */       nbttagcompound.setString("Prefix", scoreplayerteam.getColorPrefix());
/* 186 */       nbttagcompound.setString("Suffix", scoreplayerteam.getColorSuffix());
/* 187 */       nbttagcompound.setBoolean("AllowFriendlyFire", scoreplayerteam.getAllowFriendlyFire());
/* 188 */       nbttagcompound.setBoolean("SeeFriendlyInvisibles", scoreplayerteam.getSeeFriendlyInvisiblesEnabled());
/* 189 */       nbttagcompound.setString("NameTagVisibility", (scoreplayerteam.getNameTagVisibility()).internalName);
/* 190 */       nbttagcompound.setString("DeathMessageVisibility", (scoreplayerteam.getDeathMessageVisibility()).internalName);
/* 191 */       NBTTagList nbttaglist1 = new NBTTagList();
/*     */       
/* 193 */       for (String s : scoreplayerteam.getMembershipCollection()) {
/* 194 */         nbttaglist1.appendTag((NBTBase)new NBTTagString(s));
/*     */       }
/*     */       
/* 197 */       nbttagcompound.setTag("Players", (NBTBase)nbttaglist1);
/* 198 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 201 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   protected void func_96497_d(NBTTagCompound p_96497_1_) {
/* 205 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 206 */     boolean flag = false;
/*     */     
/* 208 */     for (int i = 0; i < 19; i++) {
/* 209 */       ScoreObjective scoreobjective = this.theScoreboard.getObjectiveInDisplaySlot(i);
/*     */       
/* 211 */       if (scoreobjective != null) {
/* 212 */         nbttagcompound.setString("slot_" + i, scoreobjective.getName());
/* 213 */         flag = true;
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     if (flag) {
/* 218 */       p_96497_1_.setTag("DisplaySlots", (NBTBase)nbttagcompound);
/*     */     }
/*     */   }
/*     */   
/*     */   protected NBTTagList objectivesToNbt() {
/* 223 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 225 */     for (ScoreObjective scoreobjective : this.theScoreboard.getScoreObjectives()) {
/* 226 */       if (scoreobjective.getCriteria() != null) {
/* 227 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 228 */         nbttagcompound.setString("Name", scoreobjective.getName());
/* 229 */         nbttagcompound.setString("CriteriaName", scoreobjective.getCriteria().getName());
/* 230 */         nbttagcompound.setString("DisplayName", scoreobjective.getDisplayName());
/* 231 */         nbttagcompound.setString("RenderType", scoreobjective.getRenderType().func_178796_a());
/* 232 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   protected NBTTagList scoresToNbt() {
/* 240 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 242 */     for (Score score : this.theScoreboard.getScores()) {
/* 243 */       if (score.getObjective() != null) {
/* 244 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 245 */         nbttagcompound.setString("Name", score.getPlayerName());
/* 246 */         nbttagcompound.setString("Objective", score.getObjective().getName());
/* 247 */         nbttagcompound.setInteger("Score", score.getScorePoints());
/* 248 */         nbttagcompound.setBoolean("Locked", score.isLocked());
/* 249 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     return nbttaglist;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\ScoreboardSaveData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */