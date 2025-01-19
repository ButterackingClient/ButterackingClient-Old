/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
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
/*     */ 
/*     */ 
/*     */ public class CommandFill
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "fill";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  30 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  37 */     return "commands.fill.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  44 */     if (args.length < 7) {
/*  45 */       throw new WrongUsageException("commands.fill.usage", new Object[0]);
/*     */     }
/*  47 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  48 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  49 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  50 */     Block block = CommandBase.getBlockByText(sender, args[6]);
/*  51 */     int i = 0;
/*     */     
/*  53 */     if (args.length >= 8) {
/*  54 */       i = parseInt(args[7], 0, 15);
/*     */     }
/*     */     
/*  57 */     BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
/*  58 */     BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
/*  59 */     int j = (blockpos3.getX() - blockpos2.getX() + 1) * (blockpos3.getY() - blockpos2.getY() + 1) * (blockpos3.getZ() - blockpos2.getZ() + 1);
/*     */     
/*  61 */     if (j > 32768)
/*  62 */       throw new CommandException("commands.fill.tooManyBlocks", new Object[] { Integer.valueOf(j), Integer.valueOf(32768) }); 
/*  63 */     if (blockpos2.getY() >= 0 && blockpos3.getY() < 256) {
/*  64 */       World world = sender.getEntityWorld();
/*     */       
/*  66 */       for (int k = blockpos2.getZ(); k < blockpos3.getZ() + 16; k += 16) {
/*  67 */         for (int l = blockpos2.getX(); l < blockpos3.getX() + 16; l += 16) {
/*  68 */           if (!world.isBlockLoaded(new BlockPos(l, blockpos3.getY() - blockpos2.getY(), k))) {
/*  69 */             throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  74 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  75 */       boolean flag = false;
/*     */       
/*  77 */       if (args.length >= 10 && block.hasTileEntity()) {
/*  78 */         String s = getChatComponentFromNthArg(sender, args, 9).getUnformattedText();
/*     */         
/*     */         try {
/*  81 */           nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  82 */           flag = true;
/*  83 */         } catch (NBTException nbtexception) {
/*  84 */           throw new CommandException("commands.fill.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/*  88 */       List<BlockPos> list = Lists.newArrayList();
/*  89 */       j = 0;
/*     */       
/*  91 */       for (int i1 = blockpos2.getZ(); i1 <= blockpos3.getZ(); i1++) {
/*  92 */         for (int j1 = blockpos2.getY(); j1 <= blockpos3.getY(); j1++) {
/*  93 */           for (int k1 = blockpos2.getX(); k1 <= blockpos3.getX(); k1++) {
/*  94 */             BlockPos blockpos4 = new BlockPos(k1, j1, i1);
/*     */             
/*  96 */             if (args.length >= 9) {
/*  97 */               if (!args[8].equals("outline") && !args[8].equals("hollow")) {
/*  98 */                 if (args[8].equals("destroy")) {
/*  99 */                   world.destroyBlock(blockpos4, true);
/* 100 */                 } else if (args[8].equals("keep")) {
/* 101 */                   if (!world.isAirBlock(blockpos4)) {
/*     */                     continue;
/*     */                   }
/* 104 */                 } else if (args[8].equals("replace") && !block.hasTileEntity()) {
/* 105 */                   if (args.length > 9) {
/* 106 */                     Block block1 = CommandBase.getBlockByText(sender, args[9]);
/*     */                     
/* 108 */                     if (world.getBlockState(blockpos4).getBlock() != block1) {
/*     */                       continue;
/*     */                     }
/*     */                   } 
/*     */                   
/* 113 */                   if (args.length > 10) {
/* 114 */                     int l1 = CommandBase.parseInt(args[10]);
/* 115 */                     IBlockState iblockstate = world.getBlockState(blockpos4);
/*     */                     
/* 117 */                     if (iblockstate.getBlock().getMetaFromState(iblockstate) != l1) {
/*     */                       continue;
/*     */                     }
/*     */                   } 
/*     */                 } 
/* 122 */               } else if (k1 != blockpos2.getX() && k1 != blockpos3.getX() && j1 != blockpos2.getY() && j1 != blockpos3.getY() && i1 != blockpos2.getZ() && i1 != blockpos3.getZ()) {
/* 123 */                 if (args[8].equals("hollow")) {
/* 124 */                   world.setBlockState(blockpos4, Blocks.air.getDefaultState(), 2);
/* 125 */                   list.add(blockpos4);
/*     */                 } 
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */             }
/*     */             
/* 132 */             TileEntity tileentity1 = world.getTileEntity(blockpos4);
/*     */             
/* 134 */             if (tileentity1 != null) {
/* 135 */               if (tileentity1 instanceof IInventory) {
/* 136 */                 ((IInventory)tileentity1).clear();
/*     */               }
/*     */               
/* 139 */               world.setBlockState(blockpos4, Blocks.barrier.getDefaultState(), (block == Blocks.barrier) ? 2 : 4);
/*     */             } 
/*     */             
/* 142 */             IBlockState iblockstate1 = block.getStateFromMeta(i);
/*     */             
/* 144 */             if (world.setBlockState(blockpos4, iblockstate1, 2)) {
/* 145 */               list.add(blockpos4);
/* 146 */               j++;
/*     */               
/* 148 */               if (flag) {
/* 149 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 151 */                 if (tileentity != null) {
/* 152 */                   nbttagcompound.setInteger("x", blockpos4.getX());
/* 153 */                   nbttagcompound.setInteger("y", blockpos4.getY());
/* 154 */                   nbttagcompound.setInteger("z", blockpos4.getZ());
/* 155 */                   tileentity.readFromNBT(nbttagcompound);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 163 */       for (BlockPos blockpos5 : list) {
/* 164 */         Block block2 = world.getBlockState(blockpos5).getBlock();
/* 165 */         world.notifyNeighborsRespectDebug(blockpos5, block2);
/*     */       } 
/*     */       
/* 168 */       if (j <= 0) {
/* 169 */         throw new CommandException("commands.fill.failed", new Object[0]);
/*     */       }
/* 171 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, j);
/* 172 */       notifyOperators(sender, this, "commands.fill.success", new Object[] { Integer.valueOf(j) });
/*     */     } else {
/*     */       
/* 175 */       throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 181 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length == 7) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : ((args.length == 9) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep", "hollow", "outline" }) : ((args.length == 10 && "replace".equals(args[8])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null))));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */