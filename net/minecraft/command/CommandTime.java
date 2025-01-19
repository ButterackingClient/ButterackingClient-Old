/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTime
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 14 */     return "time";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 21 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 28 */     return "commands.time.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 35 */     if (args.length > 1) {
/* 36 */       if (args[0].equals("set")) {
/*    */         int l;
/*    */         
/* 39 */         if (args[1].equals("day")) {
/* 40 */           l = 1000;
/* 41 */         } else if (args[1].equals("night")) {
/* 42 */           l = 13000;
/*    */         } else {
/* 44 */           l = parseInt(args[1], 0);
/*    */         } 
/*    */         
/* 47 */         setTime(sender, l);
/* 48 */         notifyOperators(sender, this, "commands.time.set", new Object[] { Integer.valueOf(l) });
/*    */         
/*    */         return;
/*    */       } 
/* 52 */       if (args[0].equals("add")) {
/* 53 */         int k = parseInt(args[1], 0);
/* 54 */         addTime(sender, k);
/* 55 */         notifyOperators(sender, this, "commands.time.added", new Object[] { Integer.valueOf(k) });
/*    */         
/*    */         return;
/*    */       } 
/* 59 */       if (args[0].equals("query")) {
/* 60 */         if (args[1].equals("daytime")) {
/* 61 */           int j = (int)(sender.getEntityWorld().getWorldTime() % 2147483647L);
/* 62 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, j);
/* 63 */           notifyOperators(sender, this, "commands.time.query", new Object[] { Integer.valueOf(j) });
/*    */           
/*    */           return;
/*    */         } 
/* 67 */         if (args[1].equals("gametime")) {
/* 68 */           int i = (int)(sender.getEntityWorld().getTotalWorldTime() % 2147483647L);
/* 69 */           sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, i);
/* 70 */           notifyOperators(sender, this, "commands.time.query", new Object[] { Integer.valueOf(i) });
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */     } 
/* 76 */     throw new WrongUsageException("commands.time.usage", new Object[0]);
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 80 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "query" }) : ((args.length == 2 && args[0].equals("set")) ? getListOfStringsMatchingLastWord(args, new String[] { "day", "night" }) : ((args.length == 2 && args[0].equals("query")) ? getListOfStringsMatchingLastWord(args, new String[] { "daytime", "gametime" }) : null));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setTime(ICommandSender sender, int time) {
/* 87 */     for (int i = 0; i < (MinecraftServer.getServer()).worldServers.length; i++) {
/* 88 */       (MinecraftServer.getServer()).worldServers[i].setWorldTime(time);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addTime(ICommandSender sender, int time) {
/* 96 */     for (int i = 0; i < (MinecraftServer.getServer()).worldServers.length; i++) {
/* 97 */       WorldServer worldserver = (MinecraftServer.getServer()).worldServers[i];
/* 98 */       worldserver.setWorldTime(worldserver.getWorldTime() + time);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */