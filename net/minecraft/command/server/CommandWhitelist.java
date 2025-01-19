/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ public class CommandWhitelist
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "whitelist";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  35 */     return "commands.whitelist.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  42 */     if (args.length < 1) {
/*  43 */       throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
/*     */     }
/*  45 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  47 */     if (args[0].equals("on")) {
/*  48 */       minecraftserver.getConfigurationManager().setWhiteListEnabled(true);
/*  49 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.enabled", new Object[0]);
/*  50 */     } else if (args[0].equals("off")) {
/*  51 */       minecraftserver.getConfigurationManager().setWhiteListEnabled(false);
/*  52 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.disabled", new Object[0]);
/*  53 */     } else if (args[0].equals("list")) {
/*  54 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.whitelist.list", new Object[] { Integer.valueOf((minecraftserver.getConfigurationManager().getWhitelistedPlayerNames()).length), Integer.valueOf((minecraftserver.getConfigurationManager().getAvailablePlayerDat()).length) }));
/*  55 */       String[] astring = minecraftserver.getConfigurationManager().getWhitelistedPlayerNames();
/*  56 */       sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])astring)));
/*  57 */     } else if (args[0].equals("add")) {
/*  58 */       if (args.length < 2) {
/*  59 */         throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  62 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[1]);
/*     */       
/*  64 */       if (gameprofile == null) {
/*  65 */         throw new CommandException("commands.whitelist.add.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/*  68 */       minecraftserver.getConfigurationManager().addWhitelistedPlayer(gameprofile);
/*  69 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.add.success", new Object[] { args[1] });
/*  70 */     } else if (args[0].equals("remove")) {
/*  71 */       if (args.length < 2) {
/*  72 */         throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
/*     */       }
/*     */       
/*  75 */       GameProfile gameprofile1 = minecraftserver.getConfigurationManager().getWhitelistedPlayers().getBannedProfile(args[1]);
/*     */       
/*  77 */       if (gameprofile1 == null) {
/*  78 */         throw new CommandException("commands.whitelist.remove.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/*  81 */       minecraftserver.getConfigurationManager().removePlayerFromWhitelist(gameprofile1);
/*  82 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.remove.success", new Object[] { args[1] });
/*  83 */     } else if (args[0].equals("reload")) {
/*  84 */       minecraftserver.getConfigurationManager().loadWhiteList();
/*  85 */       notifyOperators(sender, (ICommand)this, "commands.whitelist.reloaded", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  91 */     if (args.length == 1) {
/*  92 */       return getListOfStringsMatchingLastWord(args, new String[] { "on", "off", "list", "add", "remove", "reload" });
/*     */     }
/*  94 */     if (args.length == 2) {
/*  95 */       if (args[0].equals("remove")) {
/*  96 */         return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getWhitelistedPlayerNames());
/*     */       }
/*     */       
/*  99 */       if (args[0].equals("add")) {
/* 100 */         return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getPlayerProfileCache().getUsernames());
/*     */       }
/*     */     } 
/*     */     
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */