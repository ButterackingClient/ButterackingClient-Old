/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerNotFoundException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.IPBanEntry;
/*    */ import net.minecraft.server.management.UserListEntry;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class CommandBanIp extends CommandBase {
/* 20 */   public static final Pattern field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandName() {
/* 26 */     return "ban-ip";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 33 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/* 40 */     return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 47 */     return "commands.banip.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 54 */     if (args.length >= 1 && args[0].length() > 1) {
/* 55 */       IChatComponent ichatcomponent = (args.length >= 2) ? getChatComponentFromNthArg(sender, args, 1) : null;
/* 56 */       Matcher matcher = field_147211_a.matcher(args[0]);
/*    */       
/* 58 */       if (matcher.matches()) {
/* 59 */         func_147210_a(sender, args[0], (ichatcomponent == null) ? null : ichatcomponent.getUnformattedText());
/*    */       } else {
/* 61 */         EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(args[0]);
/*    */         
/* 63 */         if (entityplayermp == null) {
/* 64 */           throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
/*    */         }
/*    */         
/* 67 */         func_147210_a(sender, entityplayermp.getPlayerIP(), (ichatcomponent == null) ? null : ichatcomponent.getUnformattedText());
/*    */       } 
/*    */     } else {
/* 70 */       throw new WrongUsageException("commands.banip.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 75 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */   
/*    */   protected void func_147210_a(ICommandSender sender, String address, String reason) {
/* 79 */     IPBanEntry ipbanentry = new IPBanEntry(address, null, sender.getName(), null, reason);
/* 80 */     MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry((UserListEntry)ipbanentry);
/* 81 */     List<EntityPlayerMP> list = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(address);
/* 82 */     String[] astring = new String[list.size()];
/* 83 */     int i = 0;
/*    */     
/* 85 */     for (EntityPlayerMP entityplayermp : list) {
/* 86 */       entityplayermp.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
/* 87 */       astring[i++] = entityplayermp.getName();
/*    */     } 
/*    */     
/* 90 */     if (list.isEmpty()) {
/* 91 */       notifyOperators(sender, (ICommand)this, "commands.banip.success", new Object[] { address });
/*    */     } else {
/* 93 */       notifyOperators(sender, (ICommand)this, "commands.banip.success.players", new Object[] { address, joinNiceString((Object[])astring) });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandBanIp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */