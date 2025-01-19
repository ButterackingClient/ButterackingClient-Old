/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandStats
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "stats";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  36 */     return "commands.stats.usage";
/*     */   }
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     CommandResultStats commandresultstats;
/*  43 */     if (args.length < 1) {
/*  44 */       throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  48 */     if (args[0].equals("entity")) {
/*  49 */       flag = false;
/*     */     } else {
/*  51 */       if (!args[0].equals("block")) {
/*  52 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/*  55 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (flag) {
/*  61 */       if (args.length < 5) {
/*  62 */         throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  65 */       i = 4;
/*     */     } else {
/*  67 */       if (args.length < 3) {
/*  68 */         throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  71 */       i = 2;
/*     */     } 
/*     */     
/*  74 */     String s = args[i++];
/*     */     
/*  76 */     if ("set".equals(s)) {
/*  77 */       if (args.length < i + 3) {
/*  78 */         if (i == 5) {
/*  79 */           throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
/*     */         }
/*     */         
/*  82 */         throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
/*     */       } 
/*     */     } else {
/*  85 */       if (!"clear".equals(s)) {
/*  86 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/*  89 */       if (args.length < i + 1) {
/*  90 */         if (i == 5) {
/*  91 */           throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
/*     */         }
/*     */         
/*  94 */         throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     CommandResultStats.Type commandresultstats$type = CommandResultStats.Type.getTypeByName(args[i++]);
/*     */     
/* 100 */     if (commandresultstats$type == null) {
/* 101 */       throw new CommandException("commands.stats.failed", new Object[0]);
/*     */     }
/* 103 */     World world = sender.getEntityWorld();
/*     */ 
/*     */     
/* 106 */     if (flag) {
/* 107 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 108 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 110 */       if (tileentity == null) {
/* 111 */         throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 114 */       if (tileentity instanceof TileEntityCommandBlock) {
/* 115 */         commandresultstats = ((TileEntityCommandBlock)tileentity).getCommandResultStats();
/*     */       } else {
/* 117 */         if (!(tileentity instanceof TileEntitySign)) {
/* 118 */           throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */         }
/*     */         
/* 121 */         commandresultstats = ((TileEntitySign)tileentity).getStats();
/*     */       } 
/*     */     } else {
/* 124 */       Entity entity = getEntity(sender, args[1]);
/* 125 */       commandresultstats = entity.getCommandStats();
/*     */     } 
/*     */     
/* 128 */     if ("set".equals(s)) {
/* 129 */       String s1 = args[i++];
/* 130 */       String s2 = args[i];
/*     */       
/* 132 */       if (s1.length() == 0 || s2.length() == 0) {
/* 133 */         throw new CommandException("commands.stats.failed", new Object[0]);
/*     */       }
/*     */       
/* 136 */       CommandResultStats.setScoreBoardStat(commandresultstats, commandresultstats$type, s1, s2);
/* 137 */       notifyOperators(sender, this, "commands.stats.success", new Object[] { commandresultstats$type.getTypeName(), s2, s1 });
/* 138 */     } else if ("clear".equals(s)) {
/* 139 */       CommandResultStats.setScoreBoardStat(commandresultstats, commandresultstats$type, null, null);
/* 140 */       notifyOperators(sender, this, "commands.stats.cleared", new Object[] { commandresultstats$type.getTypeName() });
/*     */     } 
/*     */     
/* 143 */     if (flag) {
/* 144 */       BlockPos blockpos1 = parseBlockPos(sender, args, 1, false);
/* 145 */       TileEntity tileentity1 = world.getTileEntity(blockpos1);
/* 146 */       tileentity1.markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 153 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" }) : ((args.length == 2 && args[0].equals("entity")) ? getListOfStringsMatchingLastWord(args, func_175776_d()) : ((args.length >= 2 && args.length <= 4 && args[0].equals("block")) ? func_175771_a(args, 1, pos) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? (((args.length != 6 || !args[0].equals("entity")) && (args.length != 8 || !args[0].equals("block"))) ? null : getListOfStringsMatchingLastWord(args, func_175777_e())) : getListOfStringsMatchingLastWord(args, CommandResultStats.Type.getTypeNames())) : getListOfStringsMatchingLastWord(args, new String[] { "set", "clear" }))));
/*     */   }
/*     */   
/*     */   protected String[] func_175776_d() {
/* 157 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */   
/*     */   protected List<String> func_175777_e() {
/* 161 */     Collection<ScoreObjective> collection = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
/* 162 */     List<String> list = Lists.newArrayList();
/*     */     
/* 164 */     for (ScoreObjective scoreobjective : collection) {
/* 165 */       if (!scoreobjective.getCriteria().isReadOnly()) {
/* 166 */         list.add(scoreobjective.getName());
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 177 */     return (args.length > 0 && args[0].equals("entity") && index == 1);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */