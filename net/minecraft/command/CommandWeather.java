/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandWeather
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "weather";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 23 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 30 */     return "commands.weather.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 37 */     if (args.length >= 1 && args.length <= 2) {
/* 38 */       int i = (300 + (new Random()).nextInt(600)) * 20;
/*    */       
/* 40 */       if (args.length >= 2) {
/* 41 */         i = parseInt(args[1], 1, 1000000) * 20;
/*    */       }
/*    */       
/* 44 */       WorldServer worldServer = (MinecraftServer.getServer()).worldServers[0];
/* 45 */       WorldInfo worldinfo = worldServer.getWorldInfo();
/*    */       
/* 47 */       if ("clear".equalsIgnoreCase(args[0])) {
/* 48 */         worldinfo.setCleanWeatherTime(i);
/* 49 */         worldinfo.setRainTime(0);
/* 50 */         worldinfo.setThunderTime(0);
/* 51 */         worldinfo.setRaining(false);
/* 52 */         worldinfo.setThundering(false);
/* 53 */         notifyOperators(sender, this, "commands.weather.clear", new Object[0]);
/* 54 */       } else if ("rain".equalsIgnoreCase(args[0])) {
/* 55 */         worldinfo.setCleanWeatherTime(0);
/* 56 */         worldinfo.setRainTime(i);
/* 57 */         worldinfo.setThunderTime(i);
/* 58 */         worldinfo.setRaining(true);
/* 59 */         worldinfo.setThundering(false);
/* 60 */         notifyOperators(sender, this, "commands.weather.rain", new Object[0]);
/*    */       } else {
/* 62 */         if (!"thunder".equalsIgnoreCase(args[0])) {
/* 63 */           throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */         }
/*    */         
/* 66 */         worldinfo.setCleanWeatherTime(0);
/* 67 */         worldinfo.setRainTime(i);
/* 68 */         worldinfo.setThunderTime(i);
/* 69 */         worldinfo.setRaining(true);
/* 70 */         worldinfo.setThundering(true);
/* 71 */         notifyOperators(sender, this, "commands.weather.thunder", new Object[0]);
/*    */       } 
/*    */     } else {
/* 74 */       throw new WrongUsageException("commands.weather.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 79 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "clear", "rain", "thunder" }) : null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandWeather.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */