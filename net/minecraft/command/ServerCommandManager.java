/*     */ package net.minecraft.command;
/*     */ import net.minecraft.command.server.CommandBroadcast;
/*     */ import net.minecraft.command.server.CommandPardonIp;
/*     */ import net.minecraft.command.server.CommandSummon;
/*     */ import net.minecraft.command.server.CommandTestForBlock;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class ServerCommandManager extends CommandHandler implements IAdminCommand {
/*     */   public ServerCommandManager() {
/*  14 */     registerCommand(new CommandTime());
/*  15 */     registerCommand(new CommandGameMode());
/*  16 */     registerCommand(new CommandDifficulty());
/*  17 */     registerCommand(new CommandDefaultGameMode());
/*  18 */     registerCommand(new CommandKill());
/*  19 */     registerCommand(new CommandToggleDownfall());
/*  20 */     registerCommand(new CommandWeather());
/*  21 */     registerCommand(new CommandXP());
/*  22 */     registerCommand((ICommand)new CommandTeleport());
/*  23 */     registerCommand(new CommandGive());
/*  24 */     registerCommand(new CommandReplaceItem());
/*  25 */     registerCommand(new CommandStats());
/*  26 */     registerCommand(new CommandEffect());
/*  27 */     registerCommand(new CommandEnchant());
/*  28 */     registerCommand(new CommandParticle());
/*  29 */     registerCommand((ICommand)new CommandEmote());
/*  30 */     registerCommand(new CommandShowSeed());
/*  31 */     registerCommand(new CommandHelp());
/*  32 */     registerCommand(new CommandDebug());
/*  33 */     registerCommand((ICommand)new CommandMessage());
/*  34 */     registerCommand((ICommand)new CommandBroadcast());
/*  35 */     registerCommand(new CommandSetSpawnpoint());
/*  36 */     registerCommand((ICommand)new CommandSetDefaultSpawnpoint());
/*  37 */     registerCommand(new CommandGameRule());
/*  38 */     registerCommand(new CommandClearInventory());
/*  39 */     registerCommand((ICommand)new CommandTestFor());
/*  40 */     registerCommand(new CommandSpreadPlayers());
/*  41 */     registerCommand(new CommandPlaySound());
/*  42 */     registerCommand((ICommand)new CommandScoreboard());
/*  43 */     registerCommand(new CommandExecuteAt());
/*  44 */     registerCommand(new CommandTrigger());
/*  45 */     registerCommand((ICommand)new CommandAchievement());
/*  46 */     registerCommand((ICommand)new CommandSummon());
/*  47 */     registerCommand((ICommand)new CommandSetBlock());
/*  48 */     registerCommand(new CommandFill());
/*  49 */     registerCommand(new CommandClone());
/*  50 */     registerCommand(new CommandCompare());
/*  51 */     registerCommand(new CommandBlockData());
/*  52 */     registerCommand((ICommand)new CommandTestForBlock());
/*  53 */     registerCommand((ICommand)new CommandMessageRaw());
/*  54 */     registerCommand(new CommandWorldBorder());
/*  55 */     registerCommand(new CommandTitle());
/*  56 */     registerCommand(new CommandEntityData());
/*     */     
/*  58 */     if (MinecraftServer.getServer().isDedicatedServer()) {
/*  59 */       registerCommand((ICommand)new CommandOp());
/*  60 */       registerCommand((ICommand)new CommandDeOp());
/*  61 */       registerCommand((ICommand)new CommandStop());
/*  62 */       registerCommand((ICommand)new CommandSaveAll());
/*  63 */       registerCommand((ICommand)new CommandSaveOff());
/*  64 */       registerCommand((ICommand)new CommandSaveOn());
/*  65 */       registerCommand((ICommand)new CommandBanIp());
/*  66 */       registerCommand((ICommand)new CommandPardonIp());
/*  67 */       registerCommand((ICommand)new CommandBanPlayer());
/*  68 */       registerCommand((ICommand)new CommandListBans());
/*  69 */       registerCommand((ICommand)new CommandPardonPlayer());
/*  70 */       registerCommand(new CommandServerKick());
/*  71 */       registerCommand((ICommand)new CommandListPlayers());
/*  72 */       registerCommand((ICommand)new CommandWhitelist());
/*  73 */       registerCommand(new CommandSetPlayerTimeout());
/*     */     } else {
/*  75 */       registerCommand((ICommand)new CommandPublishLocalServer());
/*     */     } 
/*     */     
/*  78 */     CommandBase.setAdminCommander(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyOperators(ICommandSender sender, ICommand command, int flags, String msgFormat, Object... msgParams) {
/*  85 */     boolean flag = true;
/*  86 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  88 */     if (!sender.sendCommandFeedback()) {
/*  89 */       flag = false;
/*     */     }
/*     */     
/*  92 */     ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.admin", new Object[] { sender.getName(), new ChatComponentTranslation(msgFormat, msgParams) });
/*  93 */     chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
/*  94 */     chatComponentTranslation.getChatStyle().setItalic(Boolean.valueOf(true));
/*     */     
/*  96 */     if (flag) {
/*  97 */       for (EntityPlayer entityplayer : minecraftserver.getConfigurationManager().getPlayerList()) {
/*  98 */         if (entityplayer != sender && minecraftserver.getConfigurationManager().canSendCommands(entityplayer.getGameProfile()) && command.canCommandSenderUseCommand(sender)) {
/*  99 */           boolean flag1 = (sender instanceof MinecraftServer && MinecraftServer.getServer().shouldBroadcastConsoleToOps());
/* 100 */           boolean flag2 = (sender instanceof net.minecraft.network.rcon.RConConsoleSource && MinecraftServer.getServer().shouldBroadcastRconToOps());
/*     */           
/* 102 */           if (flag1 || flag2 || (!(sender instanceof net.minecraft.network.rcon.RConConsoleSource) && !(sender instanceof MinecraftServer))) {
/* 103 */             entityplayer.addChatMessage((IChatComponent)chatComponentTranslation);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 109 */     if (sender != minecraftserver && minecraftserver.worldServers[0].getGameRules().getBoolean("logAdminCommands")) {
/* 110 */       minecraftserver.addChatMessage((IChatComponent)chatComponentTranslation);
/*     */     }
/*     */     
/* 113 */     boolean flag3 = minecraftserver.worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*     */     
/* 115 */     if (sender instanceof CommandBlockLogic) {
/* 116 */       flag3 = ((CommandBlockLogic)sender).shouldTrackOutput();
/*     */     }
/*     */     
/* 119 */     if (((flags & 0x1) != 1 && flag3) || sender instanceof MinecraftServer)
/* 120 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation(msgFormat, msgParams)); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\ServerCommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */