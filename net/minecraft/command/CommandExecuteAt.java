/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandExecuteAt
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "execute";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  26 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  33 */     return "commands.execute.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(final ICommandSender sender, String[] args) throws CommandException {
/*  40 */     if (args.length < 5) {
/*  41 */       throw new WrongUsageException("commands.execute.usage", new Object[0]);
/*     */     }
/*  43 */     final Entity entity = getEntity(sender, args[0], Entity.class);
/*  44 */     final double d0 = parseDouble(entity.posX, args[1], false);
/*  45 */     final double d1 = parseDouble(entity.posY, args[2], false);
/*  46 */     final double d2 = parseDouble(entity.posZ, args[3], false);
/*  47 */     final BlockPos blockpos = new BlockPos(d0, d1, d2);
/*  48 */     int i = 4;
/*     */     
/*  50 */     if ("detect".equals(args[4]) && args.length > 10) {
/*  51 */       World world = entity.getEntityWorld();
/*  52 */       double d3 = parseDouble(d0, args[5], false);
/*  53 */       double d4 = parseDouble(d1, args[6], false);
/*  54 */       double d5 = parseDouble(d2, args[7], false);
/*  55 */       Block block = getBlockByText(sender, args[8]);
/*  56 */       int k = parseInt(args[9], -1, 15);
/*  57 */       BlockPos blockpos1 = new BlockPos(d3, d4, d5);
/*  58 */       IBlockState iblockstate = world.getBlockState(blockpos1);
/*     */       
/*  60 */       if (iblockstate.getBlock() != block || (k >= 0 && iblockstate.getBlock().getMetaFromState(iblockstate) != k)) {
/*  61 */         throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getName() });
/*     */       }
/*     */       
/*  64 */       i = 10;
/*     */     } 
/*     */     
/*  67 */     String s = buildString(args, i);
/*  68 */     ICommandSender icommandsender = new ICommandSender() {
/*     */         public String getName() {
/*  70 */           return entity.getName();
/*     */         }
/*     */         
/*     */         public IChatComponent getDisplayName() {
/*  74 */           return entity.getDisplayName();
/*     */         }
/*     */         
/*     */         public void addChatMessage(IChatComponent component) {
/*  78 */           sender.addChatMessage(component);
/*     */         }
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  82 */           return sender.canCommandSenderUseCommand(permLevel, commandName);
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/*  86 */           return blockpos;
/*     */         }
/*     */         
/*     */         public Vec3 getPositionVector() {
/*  90 */           return new Vec3(d0, d1, d2);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/*  94 */           return entity.worldObj;
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/*  98 */           return entity;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/* 102 */           MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 103 */           return !(minecraftserver != null && !minecraftserver.worldServers[0].getGameRules().getBoolean("commandBlockOutput"));
/*     */         }
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 107 */           entity.setCommandStat(type, amount);
/*     */         }
/*     */       };
/* 110 */     ICommandManager icommandmanager = MinecraftServer.getServer().getCommandManager();
/*     */     
/*     */     try {
/* 113 */       int j = icommandmanager.executeCommand(icommandsender, s);
/*     */       
/* 115 */       if (j < 1) {
/* 116 */         throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { s });
/*     */       }
/* 118 */     } catch (Throwable var23) {
/* 119 */       throw new CommandException("commands.execute.failed", new Object[] { s, entity.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 125 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : ((args.length > 1 && args.length <= 4) ? func_175771_a(args, 1, pos) : ((args.length > 5 && args.length <= 8 && "detect".equals(args[4])) ? func_175771_a(args, 5, pos) : ((args.length == 9 && "detect".equals(args[4])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 132 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandExecuteAt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */