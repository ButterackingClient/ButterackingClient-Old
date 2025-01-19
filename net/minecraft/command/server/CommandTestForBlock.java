/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.NumberInvalidException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandTestForBlock
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  26 */     return "testforblock";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  33 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  40 */     return "commands.testforblock.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  47 */     if (args.length < 4) {
/*  48 */       throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
/*     */     }
/*  50 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  51 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  52 */     Block block = Block.getBlockFromName(args[3]);
/*     */     
/*  54 */     if (block == null) {
/*  55 */       throw new NumberInvalidException("commands.setblock.notFound", new Object[] { args[3] });
/*     */     }
/*  57 */     int i = -1;
/*     */     
/*  59 */     if (args.length >= 5) {
/*  60 */       i = parseInt(args[4], -1, 15);
/*     */     }
/*     */     
/*  63 */     World world = sender.getEntityWorld();
/*     */     
/*  65 */     if (!world.isBlockLoaded(blockpos)) {
/*  66 */       throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
/*     */     }
/*  68 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  69 */     boolean flag = false;
/*     */     
/*  71 */     if (args.length >= 6 && block.hasTileEntity()) {
/*  72 */       String s = getChatComponentFromNthArg(sender, args, 5).getUnformattedText();
/*     */       
/*     */       try {
/*  75 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  76 */         flag = true;
/*  77 */       } catch (NBTException nbtexception) {
/*  78 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     IBlockState iblockstate = world.getBlockState(blockpos);
/*  83 */     Block block1 = iblockstate.getBlock();
/*     */     
/*  85 */     if (block1 != block) {
/*  86 */       throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), block1.getLocalizedName(), block.getLocalizedName() });
/*     */     }
/*  88 */     if (i > -1) {
/*  89 */       int j = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */       
/*  91 */       if (j != i) {
/*  92 */         throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), Integer.valueOf(j), Integer.valueOf(i) });
/*     */       }
/*     */     } 
/*     */     
/*  96 */     if (flag) {
/*  97 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/*  99 */       if (tileentity == null) {
/* 100 */         throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 103 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 104 */       tileentity.writeToNBT(nbttagcompound1);
/*     */       
/* 106 */       if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true)) {
/* 107 */         throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */     } 
/*     */     
/* 111 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 112 */     notifyOperators(sender, (ICommand)this, "commands.testforblock.success", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 120 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length == 4) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandTestForBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */