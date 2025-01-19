/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTrigger
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "trigger";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  35 */     return "commands.trigger.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     EntityPlayerMP entityplayermp;
/*  42 */     if (args.length < 3) {
/*  43 */       throw new WrongUsageException("commands.trigger.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  47 */     if (sender instanceof EntityPlayerMP) {
/*  48 */       entityplayermp = (EntityPlayerMP)sender;
/*     */     } else {
/*  50 */       Entity entity = sender.getCommandSenderEntity();
/*     */       
/*  52 */       if (!(entity instanceof EntityPlayerMP)) {
/*  53 */         throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
/*     */       }
/*     */       
/*  56 */       entityplayermp = (EntityPlayerMP)entity;
/*     */     } 
/*     */     
/*  59 */     Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*  60 */     ScoreObjective scoreobjective = scoreboard.getObjective(args[0]);
/*     */     
/*  62 */     if (scoreobjective != null && scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
/*  63 */       int i = parseInt(args[2]);
/*     */       
/*  65 */       if (!scoreboard.entityHasObjective(entityplayermp.getName(), scoreobjective)) {
/*  66 */         throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */       }
/*  68 */       Score score = scoreboard.getValueFromObjective(entityplayermp.getName(), scoreobjective);
/*     */       
/*  70 */       if (score.isLocked()) {
/*  71 */         throw new CommandException("commands.trigger.disabled", new Object[] { args[0] });
/*     */       }
/*  73 */       if ("set".equals(args[1])) {
/*  74 */         score.setScorePoints(i);
/*     */       } else {
/*  76 */         if (!"add".equals(args[1])) {
/*  77 */           throw new CommandException("commands.trigger.invalidMode", new Object[] { args[1] });
/*     */         }
/*     */         
/*  80 */         score.increseScore(i);
/*     */       } 
/*     */       
/*  83 */       score.setLocked(true);
/*     */       
/*  85 */       if (entityplayermp.theItemInWorldManager.isCreative()) {
/*  86 */         notifyOperators(sender, this, "commands.trigger.success", new Object[] { args[0], args[1], args[2] });
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  91 */       throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  97 */     if (args.length == 1) {
/*  98 */       Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*  99 */       List<String> list = Lists.newArrayList();
/*     */       
/* 101 */       for (ScoreObjective scoreobjective : scoreboard.getScoreObjectives()) {
/* 102 */         if (scoreobjective.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
/* 103 */           list.add(scoreobjective.getName());
/*     */         }
/*     */       } 
/*     */       
/* 107 */       return getListOfStringsMatchingLastWord(args, list.<String>toArray(new String[list.size()]));
/*     */     } 
/* 109 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, new String[] { "add", "set" }) : null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */