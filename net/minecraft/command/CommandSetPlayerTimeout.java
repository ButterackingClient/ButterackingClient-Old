/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ 
/*    */ public class CommandSetPlayerTimeout
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 10 */     return "setidletimeout";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 17 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 24 */     return "commands.setidletimeout.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 31 */     if (args.length != 1) {
/* 32 */       throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
/*    */     }
/* 34 */     int i = parseInt(args[0], 0);
/* 35 */     MinecraftServer.getServer().setPlayerIdleTimeout(i);
/* 36 */     notifyOperators(sender, this, "commands.setidletimeout.success", new Object[] { Integer.valueOf(i) });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandSetPlayerTimeout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */