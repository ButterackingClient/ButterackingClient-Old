/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ public class CommandAchievement
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  26 */     return "achievement";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  33 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  40 */     return "commands.achievement.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  47 */     if (args.length < 2) {
/*  48 */       throw new WrongUsageException("commands.achievement.usage", new Object[0]);
/*     */     }
/*  50 */     final StatBase statbase = StatList.getOneShotStat(args[1]);
/*     */     
/*  52 */     if (statbase == null && !args[1].equals("*")) {
/*  53 */       throw new CommandException("commands.achievement.unknownAchievement", new Object[] { args[1] });
/*     */     }
/*  55 */     final EntityPlayerMP entityplayermp = (args.length >= 3) ? getPlayer(sender, args[2]) : getCommandSenderAsPlayer(sender);
/*  56 */     boolean flag = args[0].equalsIgnoreCase("give");
/*  57 */     boolean flag1 = args[0].equalsIgnoreCase("take");
/*     */     
/*  59 */     if (flag || flag1) {
/*  60 */       if (statbase == null) {
/*  61 */         if (flag) {
/*  62 */           for (Achievement achievement4 : AchievementList.achievementList) {
/*  63 */             entityplayermp.triggerAchievement((StatBase)achievement4);
/*     */           }
/*     */           
/*  66 */           notifyOperators(sender, (ICommand)this, "commands.achievement.give.success.all", new Object[] { entityplayermp.getName() });
/*  67 */         } else if (flag1) {
/*  68 */           for (Achievement achievement5 : Lists.reverse(AchievementList.achievementList)) {
/*  69 */             entityplayermp.func_175145_a((StatBase)achievement5);
/*     */           }
/*     */           
/*  72 */           notifyOperators(sender, (ICommand)this, "commands.achievement.take.success.all", new Object[] { entityplayermp.getName() });
/*     */         } 
/*     */       } else {
/*  75 */         if (statbase instanceof Achievement) {
/*  76 */           Achievement achievement = (Achievement)statbase;
/*     */           
/*  78 */           if (flag) {
/*  79 */             if (entityplayermp.getStatFile().hasAchievementUnlocked(achievement)) {
/*  80 */               throw new CommandException("commands.achievement.alreadyHave", new Object[] { entityplayermp.getName(), statbase.createChatComponent() });
/*     */             }
/*     */             
/*     */             List<Achievement> list;
/*     */             
/*  85 */             for (list = Lists.newArrayList(); achievement.parentAchievement != null && !entityplayermp.getStatFile().hasAchievementUnlocked(achievement.parentAchievement); achievement = achievement.parentAchievement) {
/*  86 */               list.add(achievement.parentAchievement);
/*     */             }
/*     */             
/*  89 */             for (Achievement achievement1 : Lists.reverse(list)) {
/*  90 */               entityplayermp.triggerAchievement((StatBase)achievement1);
/*     */             }
/*  92 */           } else if (flag1) {
/*  93 */             if (!entityplayermp.getStatFile().hasAchievementUnlocked(achievement)) {
/*  94 */               throw new CommandException("commands.achievement.dontHave", new Object[] { entityplayermp.getName(), statbase.createChatComponent() });
/*     */             }
/*     */             
/*  97 */             List<Achievement> list1 = Lists.newArrayList((Iterator)Iterators.filter(AchievementList.achievementList.iterator(), new Predicate<Achievement>() {
/*     */                     public boolean apply(Achievement p_apply_1_) {
/*  99 */                       return (entityplayermp.getStatFile().hasAchievementUnlocked(p_apply_1_) && p_apply_1_ != statbase);
/*     */                     }
/*     */                   }));
/* 102 */             List<Achievement> list2 = Lists.newArrayList(list1);
/*     */             
/* 104 */             for (Achievement achievement2 : list1) {
/* 105 */               Achievement achievement3 = achievement2;
/*     */               
/*     */               boolean flag2;
/* 108 */               for (flag2 = false; achievement3 != null; achievement3 = achievement3.parentAchievement) {
/* 109 */                 if (achievement3 == statbase) {
/* 110 */                   flag2 = true;
/*     */                 }
/*     */               } 
/*     */               
/* 114 */               if (!flag2) {
/* 115 */                 for (achievement3 = achievement2; achievement3 != null; achievement3 = achievement3.parentAchievement) {
/* 116 */                   list2.remove(achievement2);
/*     */                 }
/*     */               }
/*     */             } 
/*     */             
/* 121 */             for (Achievement achievement6 : list2) {
/* 122 */               entityplayermp.func_175145_a((StatBase)achievement6);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 127 */         if (flag) {
/* 128 */           entityplayermp.triggerAchievement(statbase);
/* 129 */           notifyOperators(sender, (ICommand)this, "commands.achievement.give.success.one", new Object[] { entityplayermp.getName(), statbase.createChatComponent() });
/* 130 */         } else if (flag1) {
/* 131 */           entityplayermp.func_175145_a(statbase);
/* 132 */           notifyOperators(sender, (ICommand)this, "commands.achievement.take.success.one", new Object[] { statbase.createChatComponent(), entityplayermp.getName() });
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 141 */     if (args.length == 1)
/* 142 */       return getListOfStringsMatchingLastWord(args, new String[] { "give", "take" }); 
/* 143 */     if (args.length != 2) {
/* 144 */       return (args.length == 3) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*     */     }
/* 146 */     List<String> list = Lists.newArrayList();
/*     */     
/* 148 */     for (StatBase statbase : StatList.allStats) {
/* 149 */       list.add(statbase.statId);
/*     */     }
/*     */     
/* 152 */     return getListOfStringsMatchingLastWord(args, list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 160 */     return (index == 2);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandAchievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */