/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandHandler
/*     */   implements ICommandManager
/*     */ {
/*  20 */   private static final Logger logger = LogManager.getLogger();
/*  21 */   private final Map<String, ICommand> commandMap = Maps.newHashMap();
/*  22 */   private final Set<ICommand> commandSet = Sets.newHashSet();
/*  23 */   public static CommandHandler instance = new CommandHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeCommand(ICommandSender sender, String rawCommand) {
/*  35 */     rawCommand = rawCommand.trim();
/*     */     
/*  37 */     if (rawCommand.startsWith("/")) {
/*  38 */       rawCommand = rawCommand.substring(1);
/*     */     }
/*     */     
/*  41 */     String[] astring = rawCommand.split(" ");
/*  42 */     String s = astring[0];
/*  43 */     astring = dropFirstString(astring);
/*  44 */     ICommand icommand = this.commandMap.get(s);
/*  45 */     int i = getUsernameIndex(icommand, astring);
/*  46 */     int j = 0;
/*     */     
/*  48 */     if (icommand == null) {
/*  49 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
/*  50 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  51 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation);
/*  52 */     } else if (icommand.canCommandSenderUseCommand(sender)) {
/*  53 */       if (i > -1) {
/*  54 */         List<Entity> list = PlayerSelector.matchEntities(sender, astring[i], Entity.class);
/*  55 */         String s1 = astring[i];
/*  56 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */         
/*  58 */         for (Entity entity : list) {
/*  59 */           astring[i] = entity.getUniqueID().toString();
/*     */           
/*  61 */           if (tryExecute(sender, astring, icommand, rawCommand)) {
/*  62 */             j++;
/*     */           }
/*     */         } 
/*     */         
/*  66 */         astring[i] = s1;
/*     */       } else {
/*  68 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
/*     */         
/*  70 */         if (tryExecute(sender, astring, icommand, rawCommand)) {
/*  71 */           j++;
/*     */         }
/*     */       } 
/*     */     } else {
/*  75 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation("commands.generic.permission", new Object[0]);
/*  76 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.RED);
/*  77 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*     */     } 
/*     */     
/*  80 */     sender.setCommandStat(CommandResultStats.Type.SUCCESS_COUNT, j);
/*  81 */     return j;
/*     */   }
/*     */   
/*     */   protected boolean tryExecute(ICommandSender sender, String[] args, ICommand command, String input) {
/*     */     try {
/*  86 */       command.processCommand(sender, args);
/*  87 */       return true;
/*  88 */     } catch (WrongUsageException wrongusageexception) {
/*  89 */       ChatComponentTranslation chatcomponenttranslation2 = new ChatComponentTranslation("commands.generic.usage", new Object[] { new ChatComponentTranslation(wrongusageexception.getMessage(), wrongusageexception.getErrorObjects()) });
/*  90 */       chatcomponenttranslation2.getChatStyle().setColor(EnumChatFormatting.RED);
/*  91 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation2);
/*  92 */     } catch (CommandException commandexception) {
/*  93 */       ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/*  94 */       chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.RED);
/*  95 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation1);
/*  96 */     } catch (Throwable var9) {
/*  97 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
/*  98 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  99 */       sender.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 100 */       logger.warn("Couldn't process command: '" + input + "'");
/*     */     } 
/*     */     
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ICommand registerCommand(ICommand command) {
/* 110 */     this.commandMap.put(command.getCommandName(), command);
/* 111 */     this.commandSet.add(command);
/*     */     
/* 113 */     for (String s : command.getCommandAliases()) {
/* 114 */       ICommand icommand = this.commandMap.get(s);
/*     */       
/* 116 */       if (icommand == null || !icommand.getCommandName().equals(s)) {
/* 117 */         this.commandMap.put(s, command);
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] dropFirstString(String[] input) {
/* 128 */     String[] astring = new String[input.length - 1];
/* 129 */     System.arraycopy(input, 1, astring, 0, input.length - 1);
/* 130 */     return astring;
/*     */   }
/*     */   
/*     */   public List<String> getTabCompletionOptions(ICommandSender sender, String input, BlockPos pos) {
/* 134 */     String[] astring = input.split(" ", -1);
/* 135 */     String s = astring[0];
/*     */     
/* 137 */     if (astring.length == 1) {
/* 138 */       List<String> list = Lists.newArrayList();
/*     */       
/* 140 */       for (Map.Entry<String, ICommand> entry : this.commandMap.entrySet()) {
/* 141 */         if (CommandBase.doesStringStartWith(s, entry.getKey()) && ((ICommand)entry.getValue()).canCommandSenderUseCommand(sender)) {
/* 142 */           list.add(entry.getKey());
/*     */         }
/*     */       } 
/*     */       
/* 146 */       return list;
/*     */     } 
/* 148 */     if (astring.length > 1) {
/* 149 */       ICommand icommand = this.commandMap.get(s);
/*     */       
/* 151 */       if (icommand != null && icommand.canCommandSenderUseCommand(sender)) {
/* 152 */         return icommand.addTabCompletionOptions(sender, dropFirstString(astring), pos);
/*     */       }
/*     */     } 
/*     */     
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ICommand> getPossibleCommands(ICommandSender sender) {
/* 161 */     List<ICommand> list = Lists.newArrayList();
/*     */     
/* 163 */     for (ICommand icommand : this.commandSet) {
/* 164 */       if (icommand.canCommandSenderUseCommand(sender)) {
/* 165 */         list.add(icommand);
/*     */       }
/*     */     } 
/*     */     
/* 169 */     return list;
/*     */   }
/*     */   
/*     */   public Map<String, ICommand> getCommands() {
/* 173 */     return this.commandMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getUsernameIndex(ICommand command, String[] args) {
/* 180 */     if (command == null) {
/* 181 */       return -1;
/*     */     }
/* 183 */     for (int i = 0; i < args.length; i++) {
/* 184 */       if (command.isUsernameIndex(args, i) && PlayerSelector.matchesMultiplePlayers(args[i])) {
/* 185 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 189 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */