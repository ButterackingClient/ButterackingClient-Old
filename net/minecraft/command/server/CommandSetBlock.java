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
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class CommandSetBlock
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  26 */     return "setblock";
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
/*  40 */     return "commands.setblock.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  47 */     if (args.length < 4) {
/*  48 */       throw new WrongUsageException("commands.setblock.usage", new Object[0]);
/*     */     }
/*  50 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  51 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  52 */     Block block = CommandBase.getBlockByText(sender, args[3]);
/*  53 */     int i = 0;
/*     */     
/*  55 */     if (args.length >= 5) {
/*  56 */       i = parseInt(args[4], 0, 15);
/*     */     }
/*     */     
/*  59 */     World world = sender.getEntityWorld();
/*     */     
/*  61 */     if (!world.isBlockLoaded(blockpos)) {
/*  62 */       throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */     }
/*  64 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  65 */     boolean flag = false;
/*     */     
/*  67 */     if (args.length >= 7 && block.hasTileEntity()) {
/*  68 */       String s = getChatComponentFromNthArg(sender, args, 6).getUnformattedText();
/*     */       
/*     */       try {
/*  71 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  72 */         flag = true;
/*  73 */       } catch (NBTException nbtexception) {
/*  74 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     if (args.length >= 6) {
/*  79 */       if (args[5].equals("destroy")) {
/*  80 */         world.destroyBlock(blockpos, true);
/*     */         
/*  82 */         if (block == Blocks.air) {
/*  83 */           notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */           return;
/*     */         } 
/*  86 */       } else if (args[5].equals("keep") && !world.isAirBlock(blockpos)) {
/*  87 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/*  91 */     TileEntity tileentity1 = world.getTileEntity(blockpos);
/*     */     
/*  93 */     if (tileentity1 != null) {
/*  94 */       if (tileentity1 instanceof IInventory) {
/*  95 */         ((IInventory)tileentity1).clear();
/*     */       }
/*     */       
/*  98 */       world.setBlockState(blockpos, Blocks.air.getDefaultState(), (block == Blocks.air) ? 2 : 4);
/*     */     } 
/*     */     
/* 101 */     IBlockState iblockstate = block.getStateFromMeta(i);
/*     */     
/* 103 */     if (!world.setBlockState(blockpos, iblockstate, 2)) {
/* 104 */       throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */     }
/* 106 */     if (flag) {
/* 107 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 109 */       if (tileentity != null) {
/* 110 */         nbttagcompound.setInteger("x", blockpos.getX());
/* 111 */         nbttagcompound.setInteger("y", blockpos.getY());
/* 112 */         nbttagcompound.setInteger("z", blockpos.getZ());
/* 113 */         tileentity.readFromNBT(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     world.notifyNeighborsRespectDebug(blockpos, iblockstate.getBlock());
/* 118 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 119 */     notifyOperators(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 126 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length == 4) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : ((args.length == 6) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep" }) : null));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandSetBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */