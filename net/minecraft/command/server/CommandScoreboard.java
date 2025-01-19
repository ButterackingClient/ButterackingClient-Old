/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.SyntaxErrorException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ public class CommandScoreboard
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  41 */     return "scoreboard";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  48 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  55 */     return "commands.scoreboard.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  62 */     if (!func_175780_b(sender, args)) {
/*  63 */       if (args.length < 1) {
/*  64 */         throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*     */       }
/*  66 */       if (args[0].equalsIgnoreCase("objectives")) {
/*  67 */         if (args.length == 1) {
/*  68 */           throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*     */         }
/*     */         
/*  71 */         if (args[1].equalsIgnoreCase("list")) {
/*  72 */           listObjectives(sender);
/*  73 */         } else if (args[1].equalsIgnoreCase("add")) {
/*  74 */           if (args.length < 4) {
/*  75 */             throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*     */           }
/*     */           
/*  78 */           addObjective(sender, args, 2);
/*  79 */         } else if (args[1].equalsIgnoreCase("remove")) {
/*  80 */           if (args.length != 3) {
/*  81 */             throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
/*     */           }
/*     */           
/*  84 */           removeObjective(sender, args[2]);
/*     */         } else {
/*  86 */           if (!args[1].equalsIgnoreCase("setdisplay")) {
/*  87 */             throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*     */           }
/*     */           
/*  90 */           if (args.length != 3 && args.length != 4) {
/*  91 */             throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
/*     */           }
/*     */           
/*  94 */           setObjectiveDisplay(sender, args, 2);
/*     */         } 
/*  96 */       } else if (args[0].equalsIgnoreCase("players")) {
/*  97 */         if (args.length == 1) {
/*  98 */           throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*     */         }
/*     */         
/* 101 */         if (args[1].equalsIgnoreCase("list")) {
/* 102 */           if (args.length > 3) {
/* 103 */             throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
/*     */           }
/*     */           
/* 106 */           listPlayers(sender, args, 2);
/* 107 */         } else if (args[1].equalsIgnoreCase("add")) {
/* 108 */           if (args.length < 5) {
/* 109 */             throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
/*     */           }
/*     */           
/* 112 */           setPlayer(sender, args, 2);
/* 113 */         } else if (args[1].equalsIgnoreCase("remove")) {
/* 114 */           if (args.length < 5) {
/* 115 */             throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
/*     */           }
/*     */           
/* 118 */           setPlayer(sender, args, 2);
/* 119 */         } else if (args[1].equalsIgnoreCase("set")) {
/* 120 */           if (args.length < 5) {
/* 121 */             throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
/*     */           }
/*     */           
/* 124 */           setPlayer(sender, args, 2);
/* 125 */         } else if (args[1].equalsIgnoreCase("reset")) {
/* 126 */           if (args.length != 3 && args.length != 4) {
/* 127 */             throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
/*     */           }
/*     */           
/* 130 */           resetPlayers(sender, args, 2);
/* 131 */         } else if (args[1].equalsIgnoreCase("enable")) {
/* 132 */           if (args.length != 4) {
/* 133 */             throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
/*     */           }
/*     */           
/* 136 */           func_175779_n(sender, args, 2);
/* 137 */         } else if (args[1].equalsIgnoreCase("test")) {
/* 138 */           if (args.length != 5 && args.length != 6) {
/* 139 */             throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
/*     */           }
/*     */           
/* 142 */           func_175781_o(sender, args, 2);
/*     */         } else {
/* 144 */           if (!args[1].equalsIgnoreCase("operation")) {
/* 145 */             throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*     */           }
/*     */           
/* 148 */           if (args.length != 7) {
/* 149 */             throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
/*     */           }
/*     */           
/* 152 */           func_175778_p(sender, args, 2);
/*     */         } 
/*     */       } else {
/* 155 */         if (!args[0].equalsIgnoreCase("teams")) {
/* 156 */           throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*     */         }
/*     */         
/* 159 */         if (args.length == 1) {
/* 160 */           throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*     */         }
/*     */         
/* 163 */         if (args[1].equalsIgnoreCase("list")) {
/* 164 */           if (args.length > 3) {
/* 165 */             throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
/*     */           }
/*     */           
/* 168 */           listTeams(sender, args, 2);
/* 169 */         } else if (args[1].equalsIgnoreCase("add")) {
/* 170 */           if (args.length < 3) {
/* 171 */             throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*     */           }
/*     */           
/* 174 */           addTeam(sender, args, 2);
/* 175 */         } else if (args[1].equalsIgnoreCase("remove")) {
/* 176 */           if (args.length != 3) {
/* 177 */             throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
/*     */           }
/*     */           
/* 180 */           removeTeam(sender, args, 2);
/* 181 */         } else if (args[1].equalsIgnoreCase("empty")) {
/* 182 */           if (args.length != 3) {
/* 183 */             throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
/*     */           }
/*     */           
/* 186 */           emptyTeam(sender, args, 2);
/* 187 */         } else if (args[1].equalsIgnoreCase("join")) {
/* 188 */           if (args.length < 4 && (args.length != 3 || !(sender instanceof net.minecraft.entity.player.EntityPlayer))) {
/* 189 */             throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
/*     */           }
/*     */           
/* 192 */           joinTeam(sender, args, 2);
/* 193 */         } else if (args[1].equalsIgnoreCase("leave")) {
/* 194 */           if (args.length < 3 && !(sender instanceof net.minecraft.entity.player.EntityPlayer)) {
/* 195 */             throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
/*     */           }
/*     */           
/* 198 */           leaveTeam(sender, args, 2);
/*     */         } else {
/* 200 */           if (!args[1].equalsIgnoreCase("option")) {
/* 201 */             throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*     */           }
/*     */           
/* 204 */           if (args.length != 4 && args.length != 5) {
/* 205 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */           }
/*     */           
/* 208 */           setTeamOption(sender, args, 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_175780_b(ICommandSender p_175780_1_, String[] p_175780_2_) throws CommandException {
/* 216 */     int i = -1;
/*     */     
/* 218 */     for (int j = 0; j < p_175780_2_.length; j++) {
/* 219 */       if (isUsernameIndex(p_175780_2_, j) && "*".equals(p_175780_2_[j])) {
/* 220 */         if (i >= 0) {
/* 221 */           throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
/*     */         }
/*     */         
/* 224 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 228 */     if (i < 0) {
/* 229 */       return false;
/*     */     }
/* 231 */     List<String> list1 = Lists.newArrayList(getScoreboard().getObjectiveNames());
/* 232 */     String s = p_175780_2_[i];
/* 233 */     List<String> list = Lists.newArrayList();
/*     */     
/* 235 */     for (String s1 : list1) {
/* 236 */       p_175780_2_[i] = s1;
/*     */       
/*     */       try {
/* 239 */         processCommand(p_175780_1_, p_175780_2_);
/* 240 */         list.add(s1);
/* 241 */       } catch (CommandException commandexception) {
/* 242 */         ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/* 243 */         chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/* 244 */         p_175780_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     p_175780_2_[i] = s;
/* 249 */     p_175780_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */     
/* 251 */     if (list.size() == 0) {
/* 252 */       throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
/*     */     }
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Scoreboard getScoreboard() {
/* 260 */     return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*     */   }
/*     */   
/*     */   protected ScoreObjective getObjective(String name, boolean edit) throws CommandException {
/* 264 */     Scoreboard scoreboard = getScoreboard();
/* 265 */     ScoreObjective scoreobjective = scoreboard.getObjective(name);
/*     */     
/* 267 */     if (scoreobjective == null)
/* 268 */       throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { name }); 
/* 269 */     if (edit && scoreobjective.getCriteria().isReadOnly()) {
/* 270 */       throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { name });
/*     */     }
/* 272 */     return scoreobjective;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScorePlayerTeam getTeam(String name) throws CommandException {
/* 277 */     Scoreboard scoreboard = getScoreboard();
/* 278 */     ScorePlayerTeam scoreplayerteam = scoreboard.getTeam(name);
/*     */     
/* 280 */     if (scoreplayerteam == null) {
/* 281 */       throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { name });
/*     */     }
/* 283 */     return scoreplayerteam;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addObjective(ICommandSender sender, String[] args, int index) throws CommandException {
/* 288 */     String s = args[index++];
/* 289 */     String s1 = args[index++];
/* 290 */     Scoreboard scoreboard = getScoreboard();
/* 291 */     IScoreObjectiveCriteria iscoreobjectivecriteria = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.INSTANCES.get(s1);
/*     */     
/* 293 */     if (iscoreobjectivecriteria == null)
/* 294 */       throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { s1 }); 
/* 295 */     if (scoreboard.getObjective(s) != null)
/* 296 */       throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { s }); 
/* 297 */     if (s.length() > 16)
/* 298 */       throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { s, Integer.valueOf(16) }); 
/* 299 */     if (s.length() == 0) {
/* 300 */       throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*     */     }
/* 302 */     if (args.length > index) {
/* 303 */       String s2 = getChatComponentFromNthArg(sender, args, index).getUnformattedText();
/*     */       
/* 305 */       if (s2.length() > 32) {
/* 306 */         throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { s2, Integer.valueOf(32) });
/*     */       }
/*     */       
/* 309 */       if (s2.length() > 0) {
/* 310 */         scoreboard.addScoreObjective(s, iscoreobjectivecriteria).setDisplayName(s2);
/*     */       } else {
/* 312 */         scoreboard.addScoreObjective(s, iscoreobjectivecriteria);
/*     */       } 
/*     */     } else {
/* 315 */       scoreboard.addScoreObjective(s, iscoreobjectivecriteria);
/*     */     } 
/*     */     
/* 318 */     notifyOperators(sender, (ICommand)this, "commands.scoreboard.objectives.add.success", new Object[] { s });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addTeam(ICommandSender sender, String[] args, int index) throws CommandException {
/* 323 */     String s = args[index++];
/* 324 */     Scoreboard scoreboard = getScoreboard();
/*     */     
/* 326 */     if (scoreboard.getTeam(s) != null)
/* 327 */       throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { s }); 
/* 328 */     if (s.length() > 16)
/* 329 */       throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { s, Integer.valueOf(16) }); 
/* 330 */     if (s.length() == 0) {
/* 331 */       throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*     */     }
/* 333 */     if (args.length > index) {
/* 334 */       String s1 = getChatComponentFromNthArg(sender, args, index).getUnformattedText();
/*     */       
/* 336 */       if (s1.length() > 32) {
/* 337 */         throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { s1, Integer.valueOf(32) });
/*     */       }
/*     */       
/* 340 */       if (s1.length() > 0) {
/* 341 */         scoreboard.createTeam(s).setTeamName(s1);
/*     */       } else {
/* 343 */         scoreboard.createTeam(s);
/*     */       } 
/*     */     } else {
/* 346 */       scoreboard.createTeam(s);
/*     */     } 
/*     */     
/* 349 */     notifyOperators(sender, (ICommand)this, "commands.scoreboard.teams.add.success", new Object[] { s });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setTeamOption(ICommandSender sender, String[] args, int index) throws CommandException {
/* 354 */     ScorePlayerTeam scoreplayerteam = getTeam(args[index++]);
/*     */     
/* 356 */     if (scoreplayerteam != null) {
/* 357 */       String s = args[index++].toLowerCase();
/*     */       
/* 359 */       if (!s.equalsIgnoreCase("color") && !s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles") && !s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility"))
/* 360 */         throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]); 
/* 361 */       if (args.length == 4) {
/* 362 */         if (s.equalsIgnoreCase("color"))
/* 363 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) }); 
/* 364 */         if (!s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 365 */           if (!s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility")) {
/* 366 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */           }
/* 368 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*     */         } 
/*     */         
/* 371 */         throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*     */       } 
/*     */       
/* 374 */       String s1 = args[index];
/*     */       
/* 376 */       if (s.equalsIgnoreCase("color")) {
/* 377 */         EnumChatFormatting enumchatformatting = EnumChatFormatting.getValueByName(s1);
/*     */         
/* 379 */         if (enumchatformatting == null || enumchatformatting.isFancyStyling()) {
/* 380 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false)) });
/*     */         }
/*     */         
/* 383 */         scoreplayerteam.setChatFormat(enumchatformatting);
/* 384 */         scoreplayerteam.setNamePrefix(enumchatformatting.toString());
/* 385 */         scoreplayerteam.setNameSuffix(EnumChatFormatting.RESET.toString());
/* 386 */       } else if (s.equalsIgnoreCase("friendlyfire")) {
/* 387 */         if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false")) {
/* 388 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*     */         }
/*     */         
/* 391 */         scoreplayerteam.setAllowFriendlyFire(s1.equalsIgnoreCase("true"));
/* 392 */       } else if (s.equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 393 */         if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false")) {
/* 394 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*     */         }
/*     */         
/* 397 */         scoreplayerteam.setSeeFriendlyInvisiblesEnabled(s1.equalsIgnoreCase("true"));
/* 398 */       } else if (s.equalsIgnoreCase("nametagVisibility")) {
/* 399 */         Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(s1);
/*     */         
/* 401 */         if (team$enumvisible == null) {
/* 402 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*     */         }
/*     */         
/* 405 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/* 406 */       } else if (s.equalsIgnoreCase("deathMessageVisibility")) {
/* 407 */         Team.EnumVisible team$enumvisible1 = Team.EnumVisible.func_178824_a(s1);
/*     */         
/* 409 */         if (team$enumvisible1 == null) {
/* 410 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.func_178825_a()) });
/*     */         }
/*     */         
/* 413 */         scoreplayerteam.setDeathMessageVisibility(team$enumvisible1);
/*     */       } 
/*     */       
/* 416 */       notifyOperators(sender, (ICommand)this, "commands.scoreboard.teams.option.success", new Object[] { s, scoreplayerteam.getRegisteredName(), s1 });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeTeam(ICommandSender p_147194_1_, String[] p_147194_2_, int p_147194_3_) throws CommandException {
/* 422 */     Scoreboard scoreboard = getScoreboard();
/* 423 */     ScorePlayerTeam scoreplayerteam = getTeam(p_147194_2_[p_147194_3_]);
/*     */     
/* 425 */     if (scoreplayerteam != null) {
/* 426 */       scoreboard.removeTeam(scoreplayerteam);
/* 427 */       notifyOperators(p_147194_1_, (ICommand)this, "commands.scoreboard.teams.remove.success", new Object[] { scoreplayerteam.getRegisteredName() });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void listTeams(ICommandSender p_147186_1_, String[] p_147186_2_, int p_147186_3_) throws CommandException {
/* 432 */     Scoreboard scoreboard = getScoreboard();
/*     */     
/* 434 */     if (p_147186_2_.length > p_147186_3_) {
/* 435 */       ScorePlayerTeam scoreplayerteam = getTeam(p_147186_2_[p_147186_3_]);
/*     */       
/* 437 */       if (scoreplayerteam == null) {
/*     */         return;
/*     */       }
/*     */       
/* 441 */       Collection<String> collection = scoreplayerteam.getMembershipCollection();
/* 442 */       p_147186_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*     */       
/* 444 */       if (collection.size() <= 0) {
/* 445 */         throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { scoreplayerteam.getRegisteredName() });
/*     */       }
/*     */       
/* 448 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/* 449 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 450 */       p_147186_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 451 */       p_147186_1_.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString(collection.toArray())));
/*     */     } else {
/* 453 */       Collection<ScorePlayerTeam> collection1 = scoreboard.getTeams();
/* 454 */       p_147186_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection1.size());
/*     */       
/* 456 */       if (collection1.size() <= 0) {
/* 457 */         throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
/*     */       }
/*     */       
/* 460 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(collection1.size()) });
/* 461 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 462 */       p_147186_1_.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*     */       
/* 464 */       for (ScorePlayerTeam scoreplayerteam1 : collection1) {
/* 465 */         p_147186_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { scoreplayerteam1.getRegisteredName(), scoreplayerteam1.getTeamName(), Integer.valueOf(scoreplayerteam1.getMembershipCollection().size()) }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void joinTeam(ICommandSender p_147190_1_, String[] p_147190_2_, int p_147190_3_) throws CommandException {
/* 471 */     Scoreboard scoreboard = getScoreboard();
/* 472 */     String s = p_147190_2_[p_147190_3_++];
/* 473 */     Set<String> set = Sets.newHashSet();
/* 474 */     Set<String> set1 = Sets.newHashSet();
/*     */     
/* 476 */     if (p_147190_1_ instanceof net.minecraft.entity.player.EntityPlayer && p_147190_3_ == p_147190_2_.length) {
/* 477 */       String s4 = getCommandSenderAsPlayer(p_147190_1_).getName();
/*     */       
/* 479 */       if (scoreboard.addPlayerToTeam(s4, s)) {
/* 480 */         set.add(s4);
/*     */       } else {
/* 482 */         set1.add(s4);
/*     */       } 
/*     */     } else {
/* 485 */       while (p_147190_3_ < p_147190_2_.length) {
/* 486 */         String s1 = p_147190_2_[p_147190_3_++];
/*     */         
/* 488 */         if (s1.startsWith("@")) {
/* 489 */           for (Entity entity : func_175763_c(p_147190_1_, s1)) {
/* 490 */             String s3 = getEntityName(p_147190_1_, entity.getUniqueID().toString());
/*     */             
/* 492 */             if (scoreboard.addPlayerToTeam(s3, s)) {
/* 493 */               set.add(s3); continue;
/*     */             } 
/* 495 */             set1.add(s3);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 499 */         String s2 = getEntityName(p_147190_1_, s1);
/*     */         
/* 501 */         if (scoreboard.addPlayerToTeam(s2, s)) {
/* 502 */           set.add(s2); continue;
/*     */         } 
/* 504 */         set1.add(s2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 510 */     if (!set.isEmpty()) {
/* 511 */       p_147190_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/* 512 */       notifyOperators(p_147190_1_, (ICommand)this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(set.size()), s, joinNiceString(set.toArray((Object[])new String[set.size()])) });
/*     */     } 
/*     */     
/* 515 */     if (!set1.isEmpty()) {
/* 516 */       throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(set1.size()), s, joinNiceString(set1.toArray(new String[set1.size()])) });
/*     */     }
/*     */   }
/*     */   
/*     */   protected void leaveTeam(ICommandSender p_147199_1_, String[] p_147199_2_, int p_147199_3_) throws CommandException {
/* 521 */     Scoreboard scoreboard = getScoreboard();
/* 522 */     Set<String> set = Sets.newHashSet();
/* 523 */     Set<String> set1 = Sets.newHashSet();
/*     */     
/* 525 */     if (p_147199_1_ instanceof net.minecraft.entity.player.EntityPlayer && p_147199_3_ == p_147199_2_.length) {
/* 526 */       String s3 = getCommandSenderAsPlayer(p_147199_1_).getName();
/*     */       
/* 528 */       if (scoreboard.removePlayerFromTeams(s3)) {
/* 529 */         set.add(s3);
/*     */       } else {
/* 531 */         set1.add(s3);
/*     */       } 
/*     */     } else {
/* 534 */       while (p_147199_3_ < p_147199_2_.length) {
/* 535 */         String s = p_147199_2_[p_147199_3_++];
/*     */         
/* 537 */         if (s.startsWith("@")) {
/* 538 */           for (Entity entity : func_175763_c(p_147199_1_, s)) {
/* 539 */             String s2 = getEntityName(p_147199_1_, entity.getUniqueID().toString());
/*     */             
/* 541 */             if (scoreboard.removePlayerFromTeams(s2)) {
/* 542 */               set.add(s2); continue;
/*     */             } 
/* 544 */             set1.add(s2);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 548 */         String s1 = getEntityName(p_147199_1_, s);
/*     */         
/* 550 */         if (scoreboard.removePlayerFromTeams(s1)) {
/* 551 */           set.add(s1); continue;
/*     */         } 
/* 553 */         set1.add(s1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 559 */     if (!set.isEmpty()) {
/* 560 */       p_147199_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/* 561 */       notifyOperators(p_147199_1_, (ICommand)this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(set.size()), joinNiceString(set.toArray((Object[])new String[set.size()])) });
/*     */     } 
/*     */     
/* 564 */     if (!set1.isEmpty()) {
/* 565 */       throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(set1.size()), joinNiceString(set1.toArray(new String[set1.size()])) });
/*     */     }
/*     */   }
/*     */   
/*     */   protected void emptyTeam(ICommandSender p_147188_1_, String[] p_147188_2_, int p_147188_3_) throws CommandException {
/* 570 */     Scoreboard scoreboard = getScoreboard();
/* 571 */     ScorePlayerTeam scoreplayerteam = getTeam(p_147188_2_[p_147188_3_]);
/*     */     
/* 573 */     if (scoreplayerteam != null) {
/* 574 */       Collection<String> collection = Lists.newArrayList(scoreplayerteam.getMembershipCollection());
/* 575 */       p_147188_1_.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, collection.size());
/*     */       
/* 577 */       if (collection.isEmpty()) {
/* 578 */         throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { scoreplayerteam.getRegisteredName() });
/*     */       }
/* 580 */       for (String s : collection) {
/* 581 */         scoreboard.removePlayerFromTeam(s, scoreplayerteam);
/*     */       }
/*     */       
/* 584 */       notifyOperators(p_147188_1_, (ICommand)this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeObjective(ICommandSender p_147191_1_, String p_147191_2_) throws CommandException {
/* 590 */     Scoreboard scoreboard = getScoreboard();
/* 591 */     ScoreObjective scoreobjective = getObjective(p_147191_2_, false);
/* 592 */     scoreboard.removeObjective(scoreobjective);
/* 593 */     notifyOperators(p_147191_1_, (ICommand)this, "commands.scoreboard.objectives.remove.success", new Object[] { p_147191_2_ });
/*     */   }
/*     */   
/*     */   protected void listObjectives(ICommandSender p_147196_1_) throws CommandException {
/* 597 */     Scoreboard scoreboard = getScoreboard();
/* 598 */     Collection<ScoreObjective> collection = scoreboard.getScoreObjectives();
/*     */     
/* 600 */     if (collection.size() <= 0) {
/* 601 */       throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
/*     */     }
/* 603 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(collection.size()) });
/* 604 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 605 */     p_147196_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*     */     
/* 607 */     for (ScoreObjective scoreobjective : collection) {
/* 608 */       p_147196_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { scoreobjective.getName(), scoreobjective.getDisplayName(), scoreobjective.getCriteria().getName() }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setObjectiveDisplay(ICommandSender p_147198_1_, String[] p_147198_2_, int p_147198_3_) throws CommandException {
/* 614 */     Scoreboard scoreboard = getScoreboard();
/* 615 */     String s = p_147198_2_[p_147198_3_++];
/* 616 */     int i = Scoreboard.getObjectiveDisplaySlotNumber(s);
/* 617 */     ScoreObjective scoreobjective = null;
/*     */     
/* 619 */     if (p_147198_2_.length == 4) {
/* 620 */       scoreobjective = getObjective(p_147198_2_[p_147198_3_], false);
/*     */     }
/*     */     
/* 623 */     if (i < 0) {
/* 624 */       throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { s });
/*     */     }
/* 626 */     scoreboard.setObjectiveInDisplaySlot(i, scoreobjective);
/*     */     
/* 628 */     if (scoreobjective != null) {
/* 629 */       notifyOperators(p_147198_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getObjectiveDisplaySlot(i), scoreobjective.getName() });
/*     */     } else {
/* 631 */       notifyOperators(p_147198_1_, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getObjectiveDisplaySlot(i) });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void listPlayers(ICommandSender p_147195_1_, String[] p_147195_2_, int p_147195_3_) throws CommandException {
/* 637 */     Scoreboard scoreboard = getScoreboard();
/*     */     
/* 639 */     if (p_147195_2_.length > p_147195_3_) {
/* 640 */       String s = getEntityName(p_147195_1_, p_147195_2_[p_147195_3_]);
/* 641 */       Map<ScoreObjective, Score> map = scoreboard.getObjectivesForEntity(s);
/* 642 */       p_147195_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, map.size());
/*     */       
/* 644 */       if (map.size() <= 0) {
/* 645 */         throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { s });
/*     */       }
/*     */       
/* 648 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(map.size()), s });
/* 649 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 650 */       p_147195_1_.addChatMessage((IChatComponent)chatcomponenttranslation);
/*     */       
/* 652 */       for (Score score : map.values()) {
/* 653 */         p_147195_1_.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(score.getScorePoints()), score.getObjective().getDisplayName(), score.getObjective().getName() }));
/*     */       } 
/*     */     } else {
/* 656 */       Collection<String> collection = scoreboard.getObjectiveNames();
/* 657 */       p_147195_1_.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*     */       
/* 659 */       if (collection.size() <= 0) {
/* 660 */         throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
/*     */       }
/*     */       
/* 663 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(collection.size()) });
/* 664 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/* 665 */       p_147195_1_.addChatMessage((IChatComponent)chatcomponenttranslation1);
/* 666 */       p_147195_1_.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString(collection.toArray())));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setPlayer(ICommandSender p_147197_1_, String[] p_147197_2_, int p_147197_3_) throws CommandException {
/* 671 */     String s = p_147197_2_[p_147197_3_ - 1];
/* 672 */     int i = p_147197_3_;
/* 673 */     String s1 = getEntityName(p_147197_1_, p_147197_2_[p_147197_3_++]);
/*     */     
/* 675 */     if (s1.length() > 40) {
/* 676 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s1, Integer.valueOf(40) });
/*     */     }
/* 678 */     ScoreObjective scoreobjective = getObjective(p_147197_2_[p_147197_3_++], true);
/* 679 */     int j = s.equalsIgnoreCase("set") ? parseInt(p_147197_2_[p_147197_3_++]) : parseInt(p_147197_2_[p_147197_3_++], 0);
/*     */     
/* 681 */     if (p_147197_2_.length > p_147197_3_) {
/* 682 */       Entity entity = getEntity(p_147197_1_, p_147197_2_[i]);
/*     */       
/*     */       try {
/* 685 */         NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(buildString(p_147197_2_, p_147197_3_));
/* 686 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 687 */         entity.writeToNBT(nbttagcompound1);
/*     */         
/* 689 */         if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true)) {
/* 690 */           throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { s1 });
/*     */         }
/* 692 */       } catch (NBTException nbtexception) {
/* 693 */         throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/* 697 */     Scoreboard scoreboard = getScoreboard();
/* 698 */     Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/*     */     
/* 700 */     if (s.equalsIgnoreCase("set")) {
/* 701 */       score.setScorePoints(j);
/* 702 */     } else if (s.equalsIgnoreCase("add")) {
/* 703 */       score.increseScore(j);
/*     */     } else {
/* 705 */       score.decreaseScore(j);
/*     */     } 
/*     */     
/* 708 */     notifyOperators(p_147197_1_, (ICommand)this, "commands.scoreboard.players.set.success", new Object[] { scoreobjective.getName(), s1, Integer.valueOf(score.getScorePoints()) });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resetPlayers(ICommandSender p_147187_1_, String[] p_147187_2_, int p_147187_3_) throws CommandException {
/* 713 */     Scoreboard scoreboard = getScoreboard();
/* 714 */     String s = getEntityName(p_147187_1_, p_147187_2_[p_147187_3_++]);
/*     */     
/* 716 */     if (p_147187_2_.length > p_147187_3_) {
/* 717 */       ScoreObjective scoreobjective = getObjective(p_147187_2_[p_147187_3_++], false);
/* 718 */       scoreboard.removeObjectiveFromEntity(s, scoreobjective);
/* 719 */       notifyOperators(p_147187_1_, (ICommand)this, "commands.scoreboard.players.resetscore.success", new Object[] { scoreobjective.getName(), s });
/*     */     } else {
/* 721 */       scoreboard.removeObjectiveFromEntity(s, null);
/* 722 */       notifyOperators(p_147187_1_, (ICommand)this, "commands.scoreboard.players.reset.success", new Object[] { s });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_175779_n(ICommandSender p_175779_1_, String[] p_175779_2_, int p_175779_3_) throws CommandException {
/* 727 */     Scoreboard scoreboard = getScoreboard();
/* 728 */     String s = getPlayerName(p_175779_1_, p_175779_2_[p_175779_3_++]);
/*     */     
/* 730 */     if (s.length() > 40) {
/* 731 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*     */     }
/* 733 */     ScoreObjective scoreobjective = getObjective(p_175779_2_[p_175779_3_], false);
/*     */     
/* 735 */     if (scoreobjective.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
/* 736 */       throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { scoreobjective.getName() });
/*     */     }
/* 738 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/* 739 */     score.setLocked(false);
/* 740 */     notifyOperators(p_175779_1_, (ICommand)this, "commands.scoreboard.players.enable.success", new Object[] { scoreobjective.getName(), s });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_175781_o(ICommandSender p_175781_1_, String[] p_175781_2_, int p_175781_3_) throws CommandException {
/* 746 */     Scoreboard scoreboard = getScoreboard();
/* 747 */     String s = getEntityName(p_175781_1_, p_175781_2_[p_175781_3_++]);
/*     */     
/* 749 */     if (s.length() > 40) {
/* 750 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*     */     }
/* 752 */     ScoreObjective scoreobjective = getObjective(p_175781_2_[p_175781_3_++], false);
/*     */     
/* 754 */     if (!scoreboard.entityHasObjective(s, scoreobjective)) {
/* 755 */       throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { scoreobjective.getName(), s });
/*     */     }
/* 757 */     int i = p_175781_2_[p_175781_3_].equals("*") ? Integer.MIN_VALUE : parseInt(p_175781_2_[p_175781_3_]);
/* 758 */     p_175781_3_++;
/* 759 */     int j = (p_175781_3_ < p_175781_2_.length && !p_175781_2_[p_175781_3_].equals("*")) ? parseInt(p_175781_2_[p_175781_3_], i) : Integer.MAX_VALUE;
/* 760 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/*     */     
/* 762 */     if (score.getScorePoints() >= i && score.getScorePoints() <= j) {
/* 763 */       notifyOperators(p_175781_1_, (ICommand)this, "commands.scoreboard.players.test.success", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*     */     } else {
/* 765 */       throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_175778_p(ICommandSender p_175778_1_, String[] p_175778_2_, int p_175778_3_) throws CommandException {
/* 772 */     Scoreboard scoreboard = getScoreboard();
/* 773 */     String s = getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
/* 774 */     ScoreObjective scoreobjective = getObjective(p_175778_2_[p_175778_3_++], true);
/* 775 */     String s1 = p_175778_2_[p_175778_3_++];
/* 776 */     String s2 = getEntityName(p_175778_1_, p_175778_2_[p_175778_3_++]);
/* 777 */     ScoreObjective scoreobjective1 = getObjective(p_175778_2_[p_175778_3_], false);
/*     */     
/* 779 */     if (s.length() > 40)
/* 780 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) }); 
/* 781 */     if (s2.length() > 40) {
/* 782 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s2, Integer.valueOf(40) });
/*     */     }
/* 784 */     Score score = scoreboard.getValueFromObjective(s, scoreobjective);
/*     */     
/* 786 */     if (!scoreboard.entityHasObjective(s2, scoreobjective1)) {
/* 787 */       throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { scoreobjective1.getName(), s2 });
/*     */     }
/* 789 */     Score score1 = scoreboard.getValueFromObjective(s2, scoreobjective1);
/*     */     
/* 791 */     if (s1.equals("+=")) {
/* 792 */       score.setScorePoints(score.getScorePoints() + score1.getScorePoints());
/* 793 */     } else if (s1.equals("-=")) {
/* 794 */       score.setScorePoints(score.getScorePoints() - score1.getScorePoints());
/* 795 */     } else if (s1.equals("*=")) {
/* 796 */       score.setScorePoints(score.getScorePoints() * score1.getScorePoints());
/* 797 */     } else if (s1.equals("/=")) {
/* 798 */       if (score1.getScorePoints() != 0) {
/* 799 */         score.setScorePoints(score.getScorePoints() / score1.getScorePoints());
/*     */       }
/* 801 */     } else if (s1.equals("%=")) {
/* 802 */       if (score1.getScorePoints() != 0) {
/* 803 */         score.setScorePoints(score.getScorePoints() % score1.getScorePoints());
/*     */       }
/* 805 */     } else if (s1.equals("=")) {
/* 806 */       score.setScorePoints(score1.getScorePoints());
/* 807 */     } else if (s1.equals("<")) {
/* 808 */       score.setScorePoints(Math.min(score.getScorePoints(), score1.getScorePoints()));
/* 809 */     } else if (s1.equals(">")) {
/* 810 */       score.setScorePoints(Math.max(score.getScorePoints(), score1.getScorePoints()));
/*     */     } else {
/* 812 */       if (!s1.equals("><")) {
/* 813 */         throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { s1 });
/*     */       }
/*     */       
/* 816 */       int i = score.getScorePoints();
/* 817 */       score.setScorePoints(score1.getScorePoints());
/* 818 */       score1.setScorePoints(i);
/*     */     } 
/*     */     
/* 821 */     notifyOperators(p_175778_1_, (ICommand)this, "commands.scoreboard.players.operation.success", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 827 */     if (args.length == 1) {
/* 828 */       return getListOfStringsMatchingLastWord(args, new String[] { "objectives", "players", "teams" });
/*     */     }
/* 830 */     if (args[0].equalsIgnoreCase("objectives")) {
/* 831 */       if (args.length == 2) {
/* 832 */         return getListOfStringsMatchingLastWord(args, new String[] { "list", "add", "remove", "setdisplay" });
/*     */       }
/*     */       
/* 835 */       if (args[1].equalsIgnoreCase("add")) {
/* 836 */         if (args.length == 4) {
/* 837 */           Set<String> set = IScoreObjectiveCriteria.INSTANCES.keySet();
/* 838 */           return getListOfStringsMatchingLastWord(args, set);
/*     */         } 
/* 840 */       } else if (args[1].equalsIgnoreCase("remove")) {
/* 841 */         if (args.length == 3) {
/* 842 */           return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*     */         }
/* 844 */       } else if (args[1].equalsIgnoreCase("setdisplay")) {
/* 845 */         if (args.length == 3) {
/* 846 */           return getListOfStringsMatchingLastWord(args, Scoreboard.getDisplaySlotStrings());
/*     */         }
/*     */         
/* 849 */         if (args.length == 4) {
/* 850 */           return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*     */         }
/*     */       } 
/* 853 */     } else if (args[0].equalsIgnoreCase("players")) {
/* 854 */       if (args.length == 2) {
/* 855 */         return getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "remove", "reset", "list", "enable", "test", "operation" });
/*     */       }
/*     */       
/* 858 */       if (!args[1].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("reset")) {
/* 859 */         if (args[1].equalsIgnoreCase("enable")) {
/* 860 */           if (args.length == 3) {
/* 861 */             return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*     */           }
/*     */           
/* 864 */           if (args.length == 4) {
/* 865 */             return getListOfStringsMatchingLastWord(args, func_175782_e());
/*     */           }
/* 867 */         } else if (!args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("test")) {
/* 868 */           if (args[1].equalsIgnoreCase("operation")) {
/* 869 */             if (args.length == 3) {
/* 870 */               return getListOfStringsMatchingLastWord(args, getScoreboard().getObjectiveNames());
/*     */             }
/*     */             
/* 873 */             if (args.length == 4) {
/* 874 */               return getListOfStringsMatchingLastWord(args, func_147184_a(true));
/*     */             }
/*     */             
/* 877 */             if (args.length == 5) {
/* 878 */               return getListOfStringsMatchingLastWord(args, new String[] { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><" });
/*     */             }
/*     */             
/* 881 */             if (args.length == 6) {
/* 882 */               return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*     */             }
/*     */             
/* 885 */             if (args.length == 7) {
/* 886 */               return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*     */             }
/*     */           } 
/*     */         } else {
/* 890 */           if (args.length == 3) {
/* 891 */             return getListOfStringsMatchingLastWord(args, getScoreboard().getObjectiveNames());
/*     */           }
/*     */           
/* 894 */           if (args.length == 4 && args[1].equalsIgnoreCase("test")) {
/* 895 */             return getListOfStringsMatchingLastWord(args, func_147184_a(false));
/*     */           }
/*     */         } 
/*     */       } else {
/* 899 */         if (args.length == 3) {
/* 900 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*     */         }
/*     */         
/* 903 */         if (args.length == 4) {
/* 904 */           return getListOfStringsMatchingLastWord(args, func_147184_a(true));
/*     */         }
/*     */       } 
/* 907 */     } else if (args[0].equalsIgnoreCase("teams")) {
/* 908 */       if (args.length == 2) {
/* 909 */         return getListOfStringsMatchingLastWord(args, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
/*     */       }
/*     */       
/* 912 */       if (args[1].equalsIgnoreCase("join")) {
/* 913 */         if (args.length == 3) {
/* 914 */           return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*     */         }
/*     */         
/* 917 */         if (args.length >= 4) {
/* 918 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*     */         }
/*     */       } else {
/* 921 */         if (args[1].equalsIgnoreCase("leave")) {
/* 922 */           return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*     */         }
/*     */         
/* 925 */         if (!args[1].equalsIgnoreCase("empty") && !args[1].equalsIgnoreCase("list") && !args[1].equalsIgnoreCase("remove")) {
/* 926 */           if (args[1].equalsIgnoreCase("option")) {
/* 927 */             if (args.length == 3) {
/* 928 */               return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*     */             }
/*     */             
/* 931 */             if (args.length == 4) {
/* 932 */               return getListOfStringsMatchingLastWord(args, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility" });
/*     */             }
/*     */             
/* 935 */             if (args.length == 5) {
/* 936 */               if (args[3].equalsIgnoreCase("color")) {
/* 937 */                 return getListOfStringsMatchingLastWord(args, EnumChatFormatting.getValidValues(true, false));
/*     */               }
/*     */               
/* 940 */               if (args[3].equalsIgnoreCase("nametagVisibility") || args[3].equalsIgnoreCase("deathMessageVisibility")) {
/* 941 */                 return getListOfStringsMatchingLastWord(args, Team.EnumVisible.func_178825_a());
/*     */               }
/*     */               
/* 944 */               if (args[3].equalsIgnoreCase("friendlyfire") || args[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 945 */                 return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*     */               }
/*     */             } 
/*     */           } 
/* 949 */         } else if (args.length == 3) {
/* 950 */           return getListOfStringsMatchingLastWord(args, getScoreboard().getTeamNames());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 955 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> func_147184_a(boolean p_147184_1_) {
/* 960 */     Collection<ScoreObjective> collection = getScoreboard().getScoreObjectives();
/* 961 */     List<String> list = Lists.newArrayList();
/*     */     
/* 963 */     for (ScoreObjective scoreobjective : collection) {
/* 964 */       if (!p_147184_1_ || !scoreobjective.getCriteria().isReadOnly()) {
/* 965 */         list.add(scoreobjective.getName());
/*     */       }
/*     */     } 
/*     */     
/* 969 */     return list;
/*     */   }
/*     */   
/*     */   protected List<String> func_175782_e() {
/* 973 */     Collection<ScoreObjective> collection = getScoreboard().getScoreObjectives();
/* 974 */     List<String> list = Lists.newArrayList();
/*     */     
/* 976 */     for (ScoreObjective scoreobjective : collection) {
/* 977 */       if (scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
/* 978 */         list.add(scoreobjective.getName());
/*     */       }
/*     */     } 
/*     */     
/* 982 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 989 */     return !args[0].equalsIgnoreCase("players") ? (args[0].equalsIgnoreCase("teams") ? ((index == 2)) : false) : ((args.length > 1 && args[1].equalsIgnoreCase("operation")) ? (!(index != 2 && index != 5)) : ((index == 2)));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */