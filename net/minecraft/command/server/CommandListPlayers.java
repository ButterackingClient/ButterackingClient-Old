/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.CommandResultStats;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class CommandListPlayers
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "list";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 23 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 30 */     return "commands.players.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 37 */     int i = MinecraftServer.getServer().getCurrentPlayerCount();
/* 38 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.players.list", new Object[] { Integer.valueOf(i), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers()) }));
/* 39 */     sender.addChatMessage((IChatComponent)new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_181058_b((args.length > 0 && "uuids".equalsIgnoreCase(args[0])))));
/* 40 */     sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandListPlayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */