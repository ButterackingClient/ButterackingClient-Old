/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ public class CommandListBans
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 18 */     return "banlist";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 25 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/* 32 */     return ((MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer()) && super.canCommandSenderUseCommand(sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 39 */     return "commands.banlist.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 46 */     if (args.length >= 1 && args[0].equalsIgnoreCase("ips")) {
/* 47 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.banlist.ips", new Object[] { Integer.valueOf((MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()).length) }));
/* 48 */       sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys())));
/*    */     } else {
/* 50 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.banlist.players", new Object[] { Integer.valueOf((MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()).length) }));
/* 51 */       sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys())));
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 56 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "players", "ips" }) : null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandListBans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */