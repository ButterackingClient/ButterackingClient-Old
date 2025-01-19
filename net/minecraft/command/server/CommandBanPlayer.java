/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.UserListBansEntry;
/*    */ import net.minecraft.server.management.UserListEntry;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBanPlayer
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 22 */     return "ban";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 29 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 36 */     return "commands.ban.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/* 43 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 50 */     if (args.length >= 1 && args[0].length() > 0) {
/* 51 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 52 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 54 */       if (gameprofile == null) {
/* 55 */         throw new CommandException("commands.ban.failed", new Object[] { args[0] });
/*    */       }
/* 57 */       String s = null;
/*    */       
/* 59 */       if (args.length >= 2) {
/* 60 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/*    */       }
/*    */       
/* 63 */       UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, null, sender.getName(), null, s);
/* 64 */       minecraftserver.getConfigurationManager().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/* 65 */       EntityPlayerMP entityplayermp = minecraftserver.getConfigurationManager().getPlayerByUsername(args[0]);
/*    */       
/* 67 */       if (entityplayermp != null) {
/* 68 */         entityplayermp.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
/*    */       }
/*    */       
/* 71 */       notifyOperators(sender, (ICommand)this, "commands.ban.success", new Object[] { args[0] });
/*    */     } else {
/*    */       
/* 74 */       throw new WrongUsageException("commands.ban.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 79 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandBanPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */