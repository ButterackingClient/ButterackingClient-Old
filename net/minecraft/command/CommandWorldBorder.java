/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ 
/*     */ 
/*     */ public class CommandWorldBorder
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  16 */     return "worldborder";
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
/*  30 */     return "commands.worldborder.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  37 */     if (args.length < 1) {
/*  38 */       throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */     }
/*  40 */     WorldBorder worldborder = getWorldBorder();
/*     */     
/*  42 */     if (args[0].equals("set")) {
/*  43 */       if (args.length != 2 && args.length != 3) {
/*  44 */         throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
/*     */       }
/*     */       
/*  47 */       double d0 = worldborder.getTargetSize();
/*  48 */       double d2 = parseDouble(args[1], 1.0D, 6.0E7D);
/*  49 */       long i = (args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L;
/*     */       
/*  51 */       if (i > 0L) {
/*  52 */         worldborder.setTransition(d0, d2, i);
/*     */         
/*  54 */         if (d0 > d2) {
/*  55 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         } else {
/*  57 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         } 
/*     */       } else {
/*  60 */         worldborder.setTransition(d2);
/*  61 */         notifyOperators(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }) });
/*     */       } 
/*  63 */     } else if (args[0].equals("add")) {
/*  64 */       if (args.length != 2 && args.length != 3) {
/*  65 */         throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  68 */       double d4 = worldborder.getDiameter();
/*  69 */       double d8 = d4 + parseDouble(args[1], -d4, 6.0E7D - d4);
/*  70 */       long i1 = worldborder.getTimeUntilTarget() + ((args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L);
/*     */       
/*  72 */       if (i1 > 0L) {
/*  73 */         worldborder.setTransition(d4, d8, i1);
/*     */         
/*  75 */         if (d4 > d8) {
/*  76 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(i1 / 1000L) });
/*     */         } else {
/*  78 */           notifyOperators(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(i1 / 1000L) });
/*     */         } 
/*     */       } else {
/*  81 */         worldborder.setTransition(d8);
/*  82 */         notifyOperators(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }) });
/*     */       } 
/*  84 */     } else if (args[0].equals("center")) {
/*  85 */       if (args.length != 3) {
/*  86 */         throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
/*     */       }
/*     */       
/*  89 */       BlockPos blockpos = sender.getPosition();
/*  90 */       double d1 = parseDouble(blockpos.getX() + 0.5D, args[1], true);
/*  91 */       double d3 = parseDouble(blockpos.getZ() + 0.5D, args[2], true);
/*  92 */       worldborder.setCenter(d1, d3);
/*  93 */       notifyOperators(sender, this, "commands.worldborder.center.success", new Object[] { Double.valueOf(d1), Double.valueOf(d3) });
/*  94 */     } else if (args[0].equals("damage")) {
/*  95 */       if (args.length < 2) {
/*  96 */         throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
/*     */       }
/*     */       
/*  99 */       if (args[1].equals("buffer")) {
/* 100 */         if (args.length != 3) {
/* 101 */           throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
/*     */         }
/*     */         
/* 104 */         double d5 = parseDouble(args[2], 0.0D);
/* 105 */         double d9 = worldborder.getDamageBuffer();
/* 106 */         worldborder.setDamageBuffer(d5);
/* 107 */         notifyOperators(sender, this, "commands.worldborder.damage.buffer.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d5) }), String.format("%.1f", new Object[] { Double.valueOf(d9) }) });
/* 108 */       } else if (args[1].equals("amount")) {
/* 109 */         if (args.length != 3) {
/* 110 */           throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
/*     */         }
/*     */         
/* 113 */         double d6 = parseDouble(args[2], 0.0D);
/* 114 */         double d10 = worldborder.getDamageAmount();
/* 115 */         worldborder.setDamageAmount(d6);
/* 116 */         notifyOperators(sender, this, "commands.worldborder.damage.amount.success", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d6) }), String.format("%.2f", new Object[] { Double.valueOf(d10) }) });
/*     */       } 
/* 118 */     } else if (args[0].equals("warning")) {
/* 119 */       if (args.length < 2) {
/* 120 */         throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
/*     */       }
/*     */       
/* 123 */       int j = parseInt(args[2], 0);
/*     */       
/* 125 */       if (args[1].equals("time")) {
/* 126 */         if (args.length != 3) {
/* 127 */           throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
/*     */         }
/*     */         
/* 130 */         int k = worldborder.getWarningTime();
/* 131 */         worldborder.setWarningTime(j);
/* 132 */         notifyOperators(sender, this, "commands.worldborder.warning.time.success", new Object[] { Integer.valueOf(j), Integer.valueOf(k) });
/* 133 */       } else if (args[1].equals("distance")) {
/* 134 */         if (args.length != 3) {
/* 135 */           throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
/*     */         }
/*     */         
/* 138 */         int l = worldborder.getWarningDistance();
/* 139 */         worldborder.setWarningDistance(j);
/* 140 */         notifyOperators(sender, this, "commands.worldborder.warning.distance.success", new Object[] { Integer.valueOf(j), Integer.valueOf(l) });
/*     */       } 
/*     */     } else {
/* 143 */       if (!args[0].equals("get")) {
/* 144 */         throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */       }
/*     */       
/* 147 */       double d7 = worldborder.getDiameter();
/* 148 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor_double(d7 + 0.5D));
/* 149 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.worldborder.get.success", new Object[] { String.format("%.0f", new Object[] { Double.valueOf(d7) }) }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected WorldBorder getWorldBorder() {
/* 155 */     return (MinecraftServer.getServer()).worldServers[0].getWorldBorder();
/*     */   }
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 159 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "set", "center", "damage", "warning", "add", "get" }) : ((args.length == 2 && args[0].equals("damage")) ? getListOfStringsMatchingLastWord(args, new String[] { "buffer", "amount" }) : ((args.length >= 2 && args.length <= 3 && args[0].equals("center")) ? func_181043_b(args, 1, pos) : ((args.length == 2 && args[0].equals("warning")) ? getListOfStringsMatchingLastWord(args, new String[] { "time", "distance" }) : null)));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandWorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */