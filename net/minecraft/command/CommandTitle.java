/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S45PacketTitle;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandTitle extends CommandBase {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  24 */     return "title";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  38 */     return "commands.title.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  45 */     if (args.length < 2) {
/*  46 */       throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */     }
/*  48 */     if (args.length < 3) {
/*  49 */       if ("title".equals(args[1]) || "subtitle".equals(args[1])) {
/*  50 */         throw new WrongUsageException("commands.title.usage.title", new Object[0]);
/*     */       }
/*     */       
/*  53 */       if ("times".equals(args[1])) {
/*  54 */         throw new WrongUsageException("commands.title.usage.times", new Object[0]);
/*     */       }
/*     */     } 
/*     */     
/*  58 */     EntityPlayerMP entityplayermp = getPlayer(sender, args[0]);
/*  59 */     S45PacketTitle.Type s45packettitle$type = S45PacketTitle.Type.byName(args[1]);
/*     */     
/*  61 */     if (s45packettitle$type != S45PacketTitle.Type.CLEAR && s45packettitle$type != S45PacketTitle.Type.RESET)
/*  62 */     { if (s45packettitle$type == S45PacketTitle.Type.TIMES) {
/*  63 */         if (args.length != 5) {
/*  64 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*  66 */         int i = parseInt(args[2]);
/*  67 */         int j = parseInt(args[3]);
/*  68 */         int k = parseInt(args[4]);
/*  69 */         S45PacketTitle s45packettitle2 = new S45PacketTitle(i, j, k);
/*  70 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle2);
/*  71 */         notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */       } else {
/*  73 */         IChatComponent ichatcomponent; if (args.length < 3) {
/*  74 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*  76 */         String s = buildString(args, 2);
/*     */ 
/*     */         
/*     */         try {
/*  80 */           ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/*  81 */         } catch (JsonParseException jsonparseexception) {
/*  82 */           Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
/*  83 */           throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*     */         } 
/*     */         
/*  86 */         S45PacketTitle s45packettitle1 = new S45PacketTitle(s45packettitle$type, ChatComponentProcessor.processComponent(sender, ichatcomponent, (Entity)entityplayermp));
/*  87 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle1);
/*  88 */         notifyOperators(sender, this, "commands.title.success", new Object[0]);
/*     */       }  }
/*  90 */     else { if (args.length != 2) {
/*  91 */         throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */       }
/*  93 */       S45PacketTitle s45packettitle = new S45PacketTitle(s45packettitle$type, null);
/*  94 */       entityplayermp.playerNetServerHandler.sendPacket((Packet)s45packettitle);
/*  95 */       notifyOperators(sender, this, "commands.title.success", new Object[0]); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 101 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, S45PacketTitle.Type.getNames()) : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 108 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */