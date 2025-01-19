/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.SyntaxErrorException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentProcessor;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandMessageRaw
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 24 */     return "tellraw";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 31 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 38 */     return "commands.tellraw.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 45 */     if (args.length < 2) {
/* 46 */       throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
/*    */     }
/* 48 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/* 49 */     String s = buildString(args, 1);
/*    */     
/*    */     try {
/* 52 */       IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 53 */       entityPlayerMP.addChatMessage(ChatComponentProcessor.processComponent(sender, ichatcomponent, (Entity)entityPlayerMP));
/* 54 */     } catch (JsonParseException jsonparseexception) {
/* 55 */       Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
/* 56 */       throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 62 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 69 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandMessageRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */