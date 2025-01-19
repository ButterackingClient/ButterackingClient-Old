/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandOp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 20 */     return "op";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 27 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 34 */     return "commands.op.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 41 */     if (args.length == 1 && args[0].length() > 0) {
/* 42 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 43 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 45 */       if (gameprofile == null) {
/* 46 */         throw new CommandException("commands.op.failed", new Object[] { args[0] });
/*    */       }
/* 48 */       minecraftserver.getConfigurationManager().addOp(gameprofile);
/* 49 */       notifyOperators(sender, (ICommand)this, "commands.op.success", new Object[] { args[0] });
/*    */     } else {
/*    */       
/* 52 */       throw new WrongUsageException("commands.op.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 57 */     if (args.length == 1) {
/* 58 */       String s = args[args.length - 1];
/* 59 */       List<String> list = Lists.newArrayList(); byte b; int i;
/*    */       GameProfile[] arrayOfGameProfile;
/* 61 */       for (i = (arrayOfGameProfile = MinecraftServer.getServer().getGameProfiles()).length, b = 0; b < i; ) { GameProfile gameprofile = arrayOfGameProfile[b];
/* 62 */         if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(gameprofile) && doesStringStartWith(s, gameprofile.getName())) {
/* 63 */           list.add(gameprofile.getName());
/*    */         }
/*    */         b++; }
/*    */       
/* 67 */       return list;
/*    */     } 
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */