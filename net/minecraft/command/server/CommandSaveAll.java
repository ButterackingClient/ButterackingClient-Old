/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.MinecraftException;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ public class CommandSaveAll
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 17 */     return "save-all";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 24 */     return "commands.save.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 31 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 32 */     sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.start", new Object[0]));
/*    */     
/* 34 */     if (minecraftserver.getConfigurationManager() != null) {
/* 35 */       minecraftserver.getConfigurationManager().saveAllPlayerData();
/*    */     }
/*    */     
/*    */     try {
/* 39 */       for (int i = 0; i < minecraftserver.worldServers.length; i++) {
/* 40 */         if (minecraftserver.worldServers[i] != null) {
/* 41 */           WorldServer worldserver = minecraftserver.worldServers[i];
/* 42 */           boolean flag = worldserver.disableLevelSaving;
/* 43 */           worldserver.disableLevelSaving = false;
/* 44 */           worldserver.saveAllChunks(true, null);
/* 45 */           worldserver.disableLevelSaving = flag;
/*    */         } 
/*    */       } 
/*    */       
/* 49 */       if (args.length > 0 && "flush".equals(args[0])) {
/* 50 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
/*    */         
/* 52 */         for (int j = 0; j < minecraftserver.worldServers.length; j++) {
/* 53 */           if (minecraftserver.worldServers[j] != null) {
/* 54 */             WorldServer worldserver1 = minecraftserver.worldServers[j];
/* 55 */             boolean flag1 = worldserver1.disableLevelSaving;
/* 56 */             worldserver1.disableLevelSaving = false;
/* 57 */             worldserver1.saveChunkData();
/* 58 */             worldserver1.disableLevelSaving = flag1;
/*    */           } 
/*    */         } 
/*    */         
/* 62 */         sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
/*    */       } 
/* 64 */     } catch (MinecraftException minecraftexception) {
/* 65 */       notifyOperators(sender, (ICommand)this, "commands.save.failed", new Object[] { minecraftexception.getMessage() });
/*    */       
/*    */       return;
/*    */     } 
/* 69 */     notifyOperators(sender, (ICommand)this, "commands.save.success", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandSaveAll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */