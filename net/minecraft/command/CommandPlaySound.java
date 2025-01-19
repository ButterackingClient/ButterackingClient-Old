/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ public class CommandPlaySound
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  16 */     return "playsound";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  23 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  30 */     return "commands.playsound.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  37 */     if (args.length < 2) {
/*  38 */       throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
/*     */     }
/*  40 */     int i = 0;
/*  41 */     String s = args[i++];
/*  42 */     EntityPlayerMP entityplayermp = getPlayer(sender, args[i++]);
/*  43 */     Vec3 vec3 = sender.getPositionVector();
/*  44 */     double d0 = vec3.xCoord;
/*     */     
/*  46 */     if (args.length > i) {
/*  47 */       d0 = parseDouble(d0, args[i++], true);
/*     */     }
/*     */     
/*  50 */     double d1 = vec3.yCoord;
/*     */     
/*  52 */     if (args.length > i) {
/*  53 */       d1 = parseDouble(d1, args[i++], 0, 0, false);
/*     */     }
/*     */     
/*  56 */     double d2 = vec3.zCoord;
/*     */     
/*  58 */     if (args.length > i) {
/*  59 */       d2 = parseDouble(d2, args[i++], true);
/*     */     }
/*     */     
/*  62 */     double d3 = 1.0D;
/*     */     
/*  64 */     if (args.length > i) {
/*  65 */       d3 = parseDouble(args[i++], 0.0D, 3.4028234663852886E38D);
/*     */     }
/*     */     
/*  68 */     double d4 = 1.0D;
/*     */     
/*  70 */     if (args.length > i) {
/*  71 */       d4 = parseDouble(args[i++], 0.0D, 2.0D);
/*     */     }
/*     */     
/*  74 */     double d5 = 0.0D;
/*     */     
/*  76 */     if (args.length > i) {
/*  77 */       d5 = parseDouble(args[i], 0.0D, 1.0D);
/*     */     }
/*     */     
/*  80 */     double d6 = (d3 > 1.0D) ? (d3 * 16.0D) : 16.0D;
/*  81 */     double d7 = entityplayermp.getDistance(d0, d1, d2);
/*     */     
/*  83 */     if (d7 > d6) {
/*  84 */       if (d5 <= 0.0D) {
/*  85 */         throw new CommandException("commands.playsound.playerTooFar", new Object[] { entityplayermp.getName() });
/*     */       }
/*     */       
/*  88 */       double d8 = d0 - entityplayermp.posX;
/*  89 */       double d9 = d1 - entityplayermp.posY;
/*  90 */       double d10 = d2 - entityplayermp.posZ;
/*  91 */       double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
/*     */       
/*  93 */       if (d11 > 0.0D) {
/*  94 */         d0 = entityplayermp.posX + d8 / d11 * 2.0D;
/*  95 */         d1 = entityplayermp.posY + d9 / d11 * 2.0D;
/*  96 */         d2 = entityplayermp.posZ + d10 / d11 * 2.0D;
/*     */       } 
/*     */       
/*  99 */       d3 = d5;
/*     */     } 
/*     */     
/* 102 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S29PacketSoundEffect(s, d0, d1, d2, (float)d3, (float)d4));
/* 103 */     notifyOperators(sender, this, "commands.playsound.success", new Object[] { s, entityplayermp.getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 108 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length > 2 && args.length <= 5) ? func_175771_a(args, 2, pos) : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 115 */     return (index == 1);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandPlaySound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */