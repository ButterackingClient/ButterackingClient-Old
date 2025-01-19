/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandServerKick
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 14 */     return "kick";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 21 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 28 */     return "commands.kick.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 35 */     if (args.length > 0 && args[0].length() > 1) {
/* 36 */       EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
/* 37 */       String s = "Kicked by an operator.";
/* 38 */       boolean flag = false;
/*    */       
/* 40 */       if (entityplayermp == null) {
/* 41 */         throw new PlayerNotFoundException();
/*    */       }
/* 43 */       if (args.length >= 2) {
/* 44 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/* 45 */         flag = true;
/*    */       } 
/*    */       
/* 48 */       entityplayermp.playerNetServerHandler.kickPlayerFromServer(s);
/*    */       
/* 50 */       if (flag) {
/* 51 */         notifyOperators(sender, this, "commands.kick.success.reason", new Object[] { entityplayermp.getName(), s });
/*    */       } else {
/* 53 */         notifyOperators(sender, this, "commands.kick.success", new Object[] { entityplayermp.getName() });
/*    */       } 
/*    */     } else {
/*    */       
/* 57 */       throw new WrongUsageException("commands.kick.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 62 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandServerKick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */