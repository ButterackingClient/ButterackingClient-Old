/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerNotFoundException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class CommandMessage
/*    */   extends CommandBase {
/*    */   public List<String> getCommandAliases() {
/* 20 */     return Arrays.asList(new String[] { "w", "msg" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandName() {
/* 27 */     return "tell";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 34 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 41 */     return "commands.message.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 48 */     if (args.length < 2) {
/* 49 */       throw new WrongUsageException("commands.message.usage", new Object[0]);
/*    */     }
/* 51 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*    */     
/* 53 */     if (entityPlayerMP == sender) {
/* 54 */       throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
/*    */     }
/* 56 */     IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 1, !(sender instanceof net.minecraft.entity.player.EntityPlayer));
/* 57 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.message.display.incoming", new Object[] { sender.getDisplayName(), ichatcomponent.createCopy() });
/* 58 */     ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.message.display.outgoing", new Object[] { entityPlayerMP.getDisplayName(), ichatcomponent.createCopy() });
/* 59 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 60 */     chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 61 */     entityPlayerMP.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 62 */     sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 68 */     return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 75 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */