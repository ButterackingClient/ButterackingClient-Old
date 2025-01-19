/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ public class CommandSaveOn
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 14 */     return "save-on";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 21 */     return "commands.save-on.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 28 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 29 */     boolean flag = false;
/*    */     
/* 31 */     for (int i = 0; i < minecraftserver.worldServers.length; i++) {
/* 32 */       if (minecraftserver.worldServers[i] != null) {
/* 33 */         WorldServer worldserver = minecraftserver.worldServers[i];
/*    */         
/* 35 */         if (worldserver.disableLevelSaving) {
/* 36 */           worldserver.disableLevelSaving = false;
/* 37 */           flag = true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     if (flag) {
/* 43 */       notifyOperators(sender, (ICommand)this, "commands.save.enabled", new Object[0]);
/*    */     } else {
/* 45 */       throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandSaveOn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */