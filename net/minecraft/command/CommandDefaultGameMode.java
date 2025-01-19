/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ 
/*    */ public class CommandDefaultGameMode
/*    */   extends CommandGameMode
/*    */ {
/*    */   public String getCommandName() {
/* 13 */     return "defaultgamemode";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 20 */     return "commands.defaultgamemode.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 27 */     if (args.length <= 0) {
/* 28 */       throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
/*    */     }
/* 30 */     WorldSettings.GameType worldsettings$gametype = getGameModeFromCommand(sender, args[0]);
/* 31 */     setGameType(worldsettings$gametype);
/* 32 */     notifyOperators(sender, this, "commands.defaultgamemode.success", new Object[] { new ChatComponentTranslation("gameMode." + worldsettings$gametype.getName(), new Object[0]) });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setGameType(WorldSettings.GameType gameMode) {
/* 37 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 38 */     minecraftserver.setGameType(gameMode);
/*    */     
/* 40 */     if (minecraftserver.getForceGamemode())
/* 41 */       for (EntityPlayerMP entityplayermp : MinecraftServer.getServer().getConfigurationManager().getPlayerList()) {
/* 42 */         entityplayermp.setGameType(gameMode);
/* 43 */         entityplayermp.fallDistance = 0.0F;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandDefaultGameMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */