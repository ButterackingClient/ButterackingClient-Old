/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ public class CommandToggleDownfall
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 11 */     return "toggledownfall";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 18 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 25 */     return "commands.downfall.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 32 */     toggleDownfall();
/* 33 */     notifyOperators(sender, this, "commands.downfall.success", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void toggleDownfall() {
/* 40 */     WorldInfo worldinfo = (MinecraftServer.getServer()).worldServers[0].getWorldInfo();
/* 41 */     worldinfo.setRaining(!worldinfo.isRaining());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandToggleDownfall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */