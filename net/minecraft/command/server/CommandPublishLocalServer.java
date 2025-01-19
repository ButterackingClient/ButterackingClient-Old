/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ public class CommandPublishLocalServer
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 14 */     return "publish";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 21 */     return "commands.publish.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 28 */     String s = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);
/*    */     
/* 30 */     if (s != null) {
/* 31 */       notifyOperators(sender, (ICommand)this, "commands.publish.started", new Object[] { s });
/*    */     } else {
/* 33 */       notifyOperators(sender, (ICommand)this, "commands.publish.failed", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandPublishLocalServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */