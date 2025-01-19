/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.GameRules;
/*    */ 
/*    */ public class CommandGameRule
/*    */   extends CommandBase {
/*    */   public String getCommandName() {
/* 17 */     return "gamerule";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 24 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 31 */     return "commands.gamerule.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*    */     String s2;
/* 38 */     GameRules gamerules = getGameRules();
/* 39 */     String s = (args.length > 0) ? args[0] : "";
/* 40 */     String s1 = (args.length > 1) ? buildString(args, 1) : "";
/*    */     
/* 42 */     switch (args.length) {
/*    */       case 0:
/* 44 */         sender.addChatMessage((IChatComponent)new ChatComponentText(joinNiceString((Object[])gamerules.getRules())));
/*    */         return;
/*    */       
/*    */       case 1:
/* 48 */         if (!gamerules.hasRule(s)) {
/* 49 */           throw new CommandException("commands.gamerule.norule", new Object[] { s });
/*    */         }
/*    */         
/* 52 */         s2 = gamerules.getString(s);
/* 53 */         sender.addChatMessage((new ChatComponentText(s)).appendText(" = ").appendText(s2));
/* 54 */         sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gamerules.getInt(s));
/*    */         return;
/*    */     } 
/*    */     
/* 58 */     if (gamerules.areSameType(s, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(s1) && !"false".equals(s1)) {
/* 59 */       throw new CommandException("commands.generic.boolean.invalid", new Object[] { s1 });
/*    */     }
/*    */     
/* 62 */     gamerules.setOrCreateGameRule(s, s1);
/* 63 */     func_175773_a(gamerules, s);
/* 64 */     notifyOperators(sender, this, "commands.gamerule.success", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_175773_a(GameRules rules, String p_175773_1_) {
/* 69 */     if ("reducedDebugInfo".equals(p_175773_1_)) {
/* 70 */       byte b0 = (byte)(rules.getBoolean(p_175773_1_) ? 22 : 23);
/*    */       
/* 72 */       for (EntityPlayerMP entityplayermp : MinecraftServer.getServer().getConfigurationManager().getPlayerList()) {
/* 73 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)new S19PacketEntityStatus((Entity)entityplayermp, b0));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 79 */     if (args.length == 1) {
/* 80 */       return getListOfStringsMatchingLastWord(args, getGameRules().getRules());
/*    */     }
/* 82 */     if (args.length == 2) {
/* 83 */       GameRules gamerules = getGameRules();
/*    */       
/* 85 */       if (gamerules.areSameType(args[0], GameRules.ValueType.BOOLEAN_VALUE)) {
/* 86 */         return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*    */       }
/*    */     } 
/*    */     
/* 90 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private GameRules getGameRules() {
/* 98 */     return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandGameRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */