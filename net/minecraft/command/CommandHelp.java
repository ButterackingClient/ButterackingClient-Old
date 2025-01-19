/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandHelp
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "help";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  36 */     return "commands.help.usage";
/*     */   }
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  40 */     return Arrays.asList(new String[] { "?" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  47 */     List<ICommand> list = getSortedPossibleCommands(sender);
/*  48 */     int i = 7;
/*  49 */     int j = (list.size() - 1) / 7;
/*  50 */     int k = 0;
/*     */     
/*     */     try {
/*  53 */       k = (args.length == 0) ? 0 : (parseInt(args[0], 1, j + 1) - 1);
/*  54 */     } catch (NumberInvalidException numberinvalidexception) {
/*  55 */       Map<String, ICommand> map = getCommands();
/*  56 */       ICommand icommand = map.get(args[0]);
/*     */       
/*  58 */       if (icommand != null) {
/*  59 */         throw new WrongUsageException(icommand.getCommandUsage(sender), new Object[0]);
/*     */       }
/*     */       
/*  62 */       if (MathHelper.parseIntWithDefault(args[0], -1) != -1) {
/*  63 */         throw numberinvalidexception;
/*     */       }
/*     */       
/*  66 */       throw new CommandNotFoundException();
/*     */     } 
/*     */     
/*  69 */     int l = Math.min((k + 1) * 7, list.size());
/*  70 */     ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.help.header", new Object[] { Integer.valueOf(k + 1), Integer.valueOf(j + 1) });
/*  71 */     chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
/*  72 */     sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*     */     
/*  74 */     for (int i1 = k * 7; i1 < l; i1++) {
/*  75 */       ICommand icommand1 = list.get(i1);
/*  76 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(icommand1.getCommandUsage(sender), new Object[0]);
/*  77 */       chatcomponenttranslation.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + icommand1.getCommandName() + " "));
/*  78 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation);
/*     */     } 
/*     */     
/*  81 */     if (k == 0 && sender instanceof net.minecraft.entity.player.EntityPlayer) {
/*  82 */       ChatComponentTranslation chatcomponenttranslation2 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
/*  83 */       chatcomponenttranslation2.getChatStyle().setColor(EnumChatFormatting.GREEN);
/*  84 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation2);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List<ICommand> getSortedPossibleCommands(ICommandSender p_71534_1_) {
/*  89 */     List<ICommand> list = MinecraftServer.getServer().getCommandManager().getPossibleCommands(p_71534_1_);
/*  90 */     Collections.sort(list);
/*  91 */     return list;
/*     */   }
/*     */   
/*     */   protected Map<String, ICommand> getCommands() {
/*  95 */     return MinecraftServer.getServer().getCommandManager().getCommands();
/*     */   }
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  99 */     if (args.length == 1) {
/* 100 */       Set<String> set = getCommands().keySet();
/* 101 */       return getListOfStringsMatchingLastWord(args, set.<String>toArray(new String[set.size()]));
/*     */     } 
/* 103 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandHelp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */