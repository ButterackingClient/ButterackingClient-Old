/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Scoreboard
/*     */ {
/*  16 */   private final Map<String, ScoreObjective> scoreObjectives = Maps.newHashMap();
/*  17 */   private final Map<IScoreObjectiveCriteria, List<ScoreObjective>> scoreObjectiveCriterias = Maps.newHashMap();
/*  18 */   private final Map<String, Map<ScoreObjective, Score>> entitiesScoreObjectives = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  23 */   private final ScoreObjective[] objectiveDisplaySlots = new ScoreObjective[19];
/*  24 */   private final Map<String, ScorePlayerTeam> teams = Maps.newHashMap();
/*  25 */   private final Map<String, ScorePlayerTeam> teamMemberships = Maps.newHashMap();
/*  26 */   private static String[] field_178823_g = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreObjective getObjective(String name) {
/*  32 */     return this.scoreObjectives.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreObjective addScoreObjective(String name, IScoreObjectiveCriteria criteria) {
/*  39 */     if (name.length() > 16) {
/*  40 */       throw new IllegalArgumentException("The objective name '" + name + "' is too long!");
/*     */     }
/*  42 */     ScoreObjective scoreobjective = getObjective(name);
/*     */     
/*  44 */     if (scoreobjective != null) {
/*  45 */       throw new IllegalArgumentException("An objective with the name '" + name + "' already exists!");
/*     */     }
/*  47 */     scoreobjective = new ScoreObjective(this, name, criteria);
/*  48 */     List<ScoreObjective> list = this.scoreObjectiveCriterias.get(criteria);
/*     */     
/*  50 */     if (list == null) {
/*  51 */       list = Lists.newArrayList();
/*  52 */       this.scoreObjectiveCriterias.put(criteria, list);
/*     */     } 
/*     */     
/*  55 */     list.add(scoreobjective);
/*  56 */     this.scoreObjectives.put(name, scoreobjective);
/*  57 */     onScoreObjectiveAdded(scoreobjective);
/*  58 */     return scoreobjective;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ScoreObjective> getObjectivesFromCriteria(IScoreObjectiveCriteria criteria) {
/*  64 */     Collection<ScoreObjective> collection = this.scoreObjectiveCriterias.get(criteria);
/*  65 */     return (collection == null) ? Lists.newArrayList() : Lists.newArrayList(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean entityHasObjective(String name, ScoreObjective p_178819_2_) {
/*  72 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/*  74 */     if (map == null) {
/*  75 */       return false;
/*     */     }
/*  77 */     Score score = map.get(p_178819_2_);
/*  78 */     return (score != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Score getValueFromObjective(String name, ScoreObjective objective) {
/*  86 */     if (name.length() > 40) {
/*  87 */       throw new IllegalArgumentException("The player name '" + name + "' is too long!");
/*     */     }
/*  89 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/*  91 */     if (map == null) {
/*  92 */       map = Maps.newHashMap();
/*  93 */       this.entitiesScoreObjectives.put(name, map);
/*     */     } 
/*     */     
/*  96 */     Score score = map.get(objective);
/*     */     
/*  98 */     if (score == null) {
/*  99 */       score = new Score(this, objective, name);
/* 100 */       map.put(objective, score);
/*     */     } 
/*     */     
/* 103 */     return score;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Score> getSortedScores(ScoreObjective objective) {
/* 108 */     List<Score> list = Lists.newArrayList();
/*     */     
/* 110 */     for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values()) {
/* 111 */       Score score = map.get(objective);
/*     */       
/* 113 */       if (score != null) {
/* 114 */         list.add(score);
/*     */       }
/*     */     } 
/*     */     
/* 118 */     Collections.sort(list, Score.scoreComparator);
/* 119 */     return list;
/*     */   }
/*     */   
/*     */   public Collection<ScoreObjective> getScoreObjectives() {
/* 123 */     return this.scoreObjectives.values();
/*     */   }
/*     */   
/*     */   public Collection<String> getObjectiveNames() {
/* 127 */     return this.entitiesScoreObjectives.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObjectiveFromEntity(String name, ScoreObjective objective) {
/* 134 */     if (objective == null) {
/* 135 */       Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.remove(name);
/*     */       
/* 137 */       if (map != null) {
/* 138 */         func_96516_a(name);
/*     */       }
/*     */     } else {
/* 141 */       Map<ScoreObjective, Score> map2 = this.entitiesScoreObjectives.get(name);
/*     */       
/* 143 */       if (map2 != null) {
/* 144 */         Score score = map2.remove(objective);
/*     */         
/* 146 */         if (map2.size() < 1) {
/* 147 */           Map<ScoreObjective, Score> map1 = this.entitiesScoreObjectives.remove(name);
/*     */           
/* 149 */           if (map1 != null) {
/* 150 */             func_96516_a(name);
/*     */           }
/* 152 */         } else if (score != null) {
/* 153 */           func_178820_a(name, objective);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<Score> getScores() {
/* 160 */     Collection<Map<ScoreObjective, Score>> collection = this.entitiesScoreObjectives.values();
/* 161 */     List<Score> list = Lists.newArrayList();
/*     */     
/* 163 */     for (Map<ScoreObjective, Score> map : collection) {
/* 164 */       list.addAll(map.values());
/*     */     }
/*     */     
/* 167 */     return list;
/*     */   }
/*     */   
/*     */   public Map<ScoreObjective, Score> getObjectivesForEntity(String name) {
/* 171 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/* 173 */     if (map == null) {
/* 174 */       map = Maps.newHashMap();
/*     */     }
/*     */     
/* 177 */     return map;
/*     */   }
/*     */   
/*     */   public void removeObjective(ScoreObjective p_96519_1_) {
/* 181 */     this.scoreObjectives.remove(p_96519_1_.getName());
/*     */     
/* 183 */     for (int i = 0; i < 19; i++) {
/* 184 */       if (getObjectiveInDisplaySlot(i) == p_96519_1_) {
/* 185 */         setObjectiveInDisplaySlot(i, null);
/*     */       }
/*     */     } 
/*     */     
/* 189 */     List<ScoreObjective> list = this.scoreObjectiveCriterias.get(p_96519_1_.getCriteria());
/*     */     
/* 191 */     if (list != null) {
/* 192 */       list.remove(p_96519_1_);
/*     */     }
/*     */     
/* 195 */     for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values()) {
/* 196 */       map.remove(p_96519_1_);
/*     */     }
/*     */     
/* 199 */     onScoreObjectiveRemoved(p_96519_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveInDisplaySlot(int p_96530_1_, ScoreObjective p_96530_2_) {
/* 206 */     this.objectiveDisplaySlots[p_96530_1_] = p_96530_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreObjective getObjectiveInDisplaySlot(int p_96539_1_) {
/* 213 */     return this.objectiveDisplaySlots[p_96539_1_];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam getTeam(String p_96508_1_) {
/* 220 */     return this.teams.get(p_96508_1_);
/*     */   }
/*     */   
/*     */   public ScorePlayerTeam createTeam(String name) {
/* 224 */     if (name.length() > 16) {
/* 225 */       throw new IllegalArgumentException("The team name '" + name + "' is too long!");
/*     */     }
/* 227 */     ScorePlayerTeam scoreplayerteam = getTeam(name);
/*     */     
/* 229 */     if (scoreplayerteam != null) {
/* 230 */       throw new IllegalArgumentException("A team with the name '" + name + "' already exists!");
/*     */     }
/* 232 */     scoreplayerteam = new ScorePlayerTeam(this, name);
/* 233 */     this.teams.put(name, scoreplayerteam);
/* 234 */     broadcastTeamCreated(scoreplayerteam);
/* 235 */     return scoreplayerteam;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTeam(ScorePlayerTeam p_96511_1_) {
/* 244 */     this.teams.remove(p_96511_1_.getRegisteredName());
/*     */     
/* 246 */     for (String s : p_96511_1_.getMembershipCollection()) {
/* 247 */       this.teamMemberships.remove(s);
/*     */     }
/*     */     
/* 250 */     func_96513_c(p_96511_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String player, String newTeam) {
/* 257 */     if (player.length() > 40)
/* 258 */       throw new IllegalArgumentException("The player name '" + player + "' is too long!"); 
/* 259 */     if (!this.teams.containsKey(newTeam)) {
/* 260 */       return false;
/*     */     }
/* 262 */     ScorePlayerTeam scoreplayerteam = getTeam(newTeam);
/*     */     
/* 264 */     if (getPlayersTeam(player) != null) {
/* 265 */       removePlayerFromTeams(player);
/*     */     }
/*     */     
/* 268 */     this.teamMemberships.put(player, scoreplayerteam);
/* 269 */     scoreplayerteam.getMembershipCollection().add(player);
/* 270 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removePlayerFromTeams(String p_96524_1_) {
/* 275 */     ScorePlayerTeam scoreplayerteam = getPlayersTeam(p_96524_1_);
/*     */     
/* 277 */     if (scoreplayerteam != null) {
/* 278 */       removePlayerFromTeam(p_96524_1_, scoreplayerteam);
/* 279 */       return true;
/*     */     } 
/* 281 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_) {
/* 290 */     if (getPlayersTeam(p_96512_1_) != p_96512_2_) {
/* 291 */       throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + p_96512_2_.getRegisteredName() + "'.");
/*     */     }
/* 293 */     this.teamMemberships.remove(p_96512_1_);
/* 294 */     p_96512_2_.getMembershipCollection().remove(p_96512_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getTeamNames() {
/* 299 */     return this.teams.keySet();
/*     */   }
/*     */   
/*     */   public Collection<ScorePlayerTeam> getTeams() {
/* 303 */     return this.teams.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam getPlayersTeam(String p_96509_1_) {
/* 310 */     return this.teamMemberships.get(p_96509_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onObjectiveDisplayNameChanged(ScoreObjective p_96532_1_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveRemoved(ScoreObjective p_96533_1_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96536_a(Score p_96536_1_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96516_a(String p_96516_1_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_178820_a(String p_178820_1_, ScoreObjective p_178820_2_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamCreated(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTeamUpdate(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96513_c(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getObjectiveDisplaySlot(int p_96517_0_) {
/* 353 */     switch (p_96517_0_) {
/*     */       case 0:
/* 355 */         return "list";
/*     */       
/*     */       case 1:
/* 358 */         return "sidebar";
/*     */       
/*     */       case 2:
/* 361 */         return "belowName";
/*     */     } 
/*     */     
/* 364 */     if (p_96517_0_ >= 3 && p_96517_0_ <= 18) {
/* 365 */       EnumChatFormatting enumchatformatting = EnumChatFormatting.FromID(p_96517_0_ - 3);
/*     */       
/* 367 */       if (enumchatformatting != null && enumchatformatting != EnumChatFormatting.RESET) {
/* 368 */         return "sidebar.team." + enumchatformatting.getFriendlyName();
/*     */       }
/*     */     } 
/*     */     
/* 372 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getObjectiveDisplaySlotNumber(String p_96537_0_) {
/* 380 */     if (p_96537_0_.equalsIgnoreCase("list"))
/* 381 */       return 0; 
/* 382 */     if (p_96537_0_.equalsIgnoreCase("sidebar"))
/* 383 */       return 1; 
/* 384 */     if (p_96537_0_.equalsIgnoreCase("belowName")) {
/* 385 */       return 2;
/*     */     }
/* 387 */     if (p_96537_0_.startsWith("sidebar.team.")) {
/* 388 */       String s = p_96537_0_.substring("sidebar.team.".length());
/* 389 */       EnumChatFormatting enumchatformatting = EnumChatFormatting.getValueByName(s);
/*     */       
/* 391 */       if (enumchatformatting != null && enumchatformatting.getColorIndex() >= 0) {
/* 392 */         return enumchatformatting.getColorIndex() + 3;
/*     */       }
/*     */     } 
/*     */     
/* 396 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getDisplaySlotStrings() {
/* 401 */     if (field_178823_g == null) {
/* 402 */       field_178823_g = new String[19];
/*     */       
/* 404 */       for (int i = 0; i < 19; i++) {
/* 405 */         field_178823_g[i] = getObjectiveDisplaySlot(i);
/*     */       }
/*     */     } 
/*     */     
/* 409 */     return field_178823_g;
/*     */   }
/*     */   
/*     */   public void func_181140_a(Entity p_181140_1_) {
/* 413 */     if (p_181140_1_ != null && !(p_181140_1_ instanceof net.minecraft.entity.player.EntityPlayer) && !p_181140_1_.isEntityAlive()) {
/* 414 */       String s = p_181140_1_.getUniqueID().toString();
/* 415 */       removeObjectiveFromEntity(s, null);
/* 416 */       removePlayerFromTeams(s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\Scoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */