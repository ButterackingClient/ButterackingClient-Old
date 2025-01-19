/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ public class CommandXP
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 14 */     return "xp";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 21 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 28 */     return "commands.xp.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 35 */     if (args.length <= 0) {
/* 36 */       throw new WrongUsageException("commands.xp.usage", new Object[0]);
/*    */     }
/* 38 */     String s = args[0];
/* 39 */     boolean flag = !(!s.endsWith("l") && !s.endsWith("L"));
/*    */     
/* 41 */     if (flag && s.length() > 1) {
/* 42 */       s = s.substring(0, s.length() - 1);
/*    */     }
/*    */     
/* 45 */     int i = parseInt(s);
/* 46 */     boolean flag1 = (i < 0);
/*    */     
/* 48 */     if (flag1) {
/* 49 */       i *= -1;
/*    */     }
/*    */     
/* 52 */     EntityPlayerMP entityPlayerMP = (args.length > 1) ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
/*    */     
/* 54 */     if (flag) {
/* 55 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceLevel);
/*    */       
/* 57 */       if (flag1) {
/* 58 */         entityPlayerMP.addExperienceLevel(-i);
/* 59 */         notifyOperators(sender, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*    */       } else {
/* 61 */         entityPlayerMP.addExperienceLevel(i);
/* 62 */         notifyOperators(sender, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*    */       } 
/*    */     } else {
/* 65 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceTotal);
/*    */       
/* 67 */       if (flag1) {
/* 68 */         throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
/*    */       }
/*    */       
/* 71 */       entityPlayerMP.addExperience(i);
/* 72 */       notifyOperators(sender, this, "commands.xp.success", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 78 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : null;
/*    */   }
/*    */   
/*    */   protected String[] getAllUsernames() {
/* 82 */     return MinecraftServer.getServer().getAllUsernames();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 89 */     return (index == 1);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandXP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */