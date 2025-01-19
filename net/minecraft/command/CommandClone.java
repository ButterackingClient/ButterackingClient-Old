/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandClone
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  24 */     return "clone";
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
/*  38 */     return "commands.clone.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  45 */     if (args.length < 9) {
/*  46 */       throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */     }
/*  48 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  49 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  50 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  51 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  52 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  53 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.func_175896_b()));
/*  54 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  56 */     if (i > 32768) {
/*  57 */       throw new CommandException("commands.clone.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(32768) });
/*     */     }
/*  59 */     boolean flag = false;
/*  60 */     Block block = null;
/*  61 */     int j = -1;
/*     */     
/*  63 */     if ((args.length < 11 || (!args[10].equals("force") && !args[10].equals("move"))) && structureboundingbox.intersectsWith(structureboundingbox1)) {
/*  64 */       throw new CommandException("commands.clone.noOverlap", new Object[0]);
/*     */     }
/*  66 */     if (args.length >= 11 && args[10].equals("move")) {
/*  67 */       flag = true;
/*     */     }
/*     */     
/*  70 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*  71 */       World world = sender.getEntityWorld();
/*     */       
/*  73 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1)) {
/*  74 */         boolean flag1 = false;
/*     */         
/*  76 */         if (args.length >= 10) {
/*  77 */           if (args[9].equals("masked")) {
/*  78 */             flag1 = true;
/*  79 */           } else if (args[9].equals("filtered")) {
/*  80 */             if (args.length < 12) {
/*  81 */               throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */             }
/*     */             
/*  84 */             block = getBlockByText(sender, args[11]);
/*     */             
/*  86 */             if (args.length >= 13) {
/*  87 */               j = parseInt(args[12], 0, 15);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/*  92 */         List<StaticCloneData> list = Lists.newArrayList();
/*  93 */         List<StaticCloneData> list1 = Lists.newArrayList();
/*  94 */         List<StaticCloneData> list2 = Lists.newArrayList();
/*  95 */         LinkedList<BlockPos> linkedlist = Lists.newLinkedList();
/*  96 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*     */         
/*  98 */         for (int k = structureboundingbox.minZ; k <= structureboundingbox.maxZ; k++) {
/*  99 */           for (int l = structureboundingbox.minY; l <= structureboundingbox.maxY; l++) {
/* 100 */             for (int i1 = structureboundingbox.minX; i1 <= structureboundingbox.maxX; i1++) {
/* 101 */               BlockPos blockpos4 = new BlockPos(i1, l, k);
/* 102 */               BlockPos blockpos5 = blockpos4.add((Vec3i)blockpos3);
/* 103 */               IBlockState iblockstate = world.getBlockState(blockpos4);
/*     */               
/* 105 */               if ((!flag1 || iblockstate.getBlock() != Blocks.air) && (block == null || (iblockstate.getBlock() == block && (j < 0 || iblockstate.getBlock().getMetaFromState(iblockstate) == j)))) {
/* 106 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 108 */                 if (tileentity != null) {
/* 109 */                   NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 110 */                   tileentity.writeToNBT(nbttagcompound);
/* 111 */                   list1.add(new StaticCloneData(blockpos5, iblockstate, nbttagcompound));
/* 112 */                   linkedlist.addLast(blockpos4);
/* 113 */                 } else if (!iblockstate.getBlock().isFullBlock() && !iblockstate.getBlock().isFullCube()) {
/* 114 */                   list2.add(new StaticCloneData(blockpos5, iblockstate, null));
/* 115 */                   linkedlist.addFirst(blockpos4);
/*     */                 } else {
/* 117 */                   list.add(new StaticCloneData(blockpos5, iblockstate, null));
/* 118 */                   linkedlist.addLast(blockpos4);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 125 */         if (flag) {
/* 126 */           for (BlockPos blockpos6 : linkedlist) {
/* 127 */             TileEntity tileentity1 = world.getTileEntity(blockpos6);
/*     */             
/* 129 */             if (tileentity1 instanceof IInventory) {
/* 130 */               ((IInventory)tileentity1).clear();
/*     */             }
/*     */             
/* 133 */             world.setBlockState(blockpos6, Blocks.barrier.getDefaultState(), 2);
/*     */           } 
/*     */           
/* 136 */           for (BlockPos blockpos7 : linkedlist) {
/* 137 */             world.setBlockState(blockpos7, Blocks.air.getDefaultState(), 3);
/*     */           }
/*     */         } 
/*     */         
/* 141 */         List<StaticCloneData> list3 = Lists.newArrayList();
/* 142 */         list3.addAll(list);
/* 143 */         list3.addAll(list1);
/* 144 */         list3.addAll(list2);
/* 145 */         List<StaticCloneData> list4 = Lists.reverse(list3);
/*     */         
/* 147 */         for (StaticCloneData commandclone$staticclonedata : list4) {
/* 148 */           TileEntity tileentity2 = world.getTileEntity(commandclone$staticclonedata.pos);
/*     */           
/* 150 */           if (tileentity2 instanceof IInventory) {
/* 151 */             ((IInventory)tileentity2).clear();
/*     */           }
/*     */           
/* 154 */           world.setBlockState(commandclone$staticclonedata.pos, Blocks.barrier.getDefaultState(), 2);
/*     */         } 
/*     */         
/* 157 */         i = 0;
/*     */         
/* 159 */         for (StaticCloneData commandclone$staticclonedata1 : list3) {
/* 160 */           if (world.setBlockState(commandclone$staticclonedata1.pos, commandclone$staticclonedata1.blockState, 2)) {
/* 161 */             i++;
/*     */           }
/*     */         } 
/*     */         
/* 165 */         for (StaticCloneData commandclone$staticclonedata2 : list1) {
/* 166 */           TileEntity tileentity3 = world.getTileEntity(commandclone$staticclonedata2.pos);
/*     */           
/* 168 */           if (commandclone$staticclonedata2.compound != null && tileentity3 != null) {
/* 169 */             commandclone$staticclonedata2.compound.setInteger("x", commandclone$staticclonedata2.pos.getX());
/* 170 */             commandclone$staticclonedata2.compound.setInteger("y", commandclone$staticclonedata2.pos.getY());
/* 171 */             commandclone$staticclonedata2.compound.setInteger("z", commandclone$staticclonedata2.pos.getZ());
/* 172 */             tileentity3.readFromNBT(commandclone$staticclonedata2.compound);
/* 173 */             tileentity3.markDirty();
/*     */           } 
/*     */           
/* 176 */           world.setBlockState(commandclone$staticclonedata2.pos, commandclone$staticclonedata2.blockState, 2);
/*     */         } 
/*     */         
/* 179 */         for (StaticCloneData commandclone$staticclonedata3 : list4) {
/* 180 */           world.notifyNeighborsRespectDebug(commandclone$staticclonedata3.pos, commandclone$staticclonedata3.blockState.getBlock());
/*     */         }
/*     */         
/* 183 */         List<NextTickListEntry> list5 = world.func_175712_a(structureboundingbox, false);
/*     */         
/* 185 */         if (list5 != null) {
/* 186 */           for (NextTickListEntry nextticklistentry : list5) {
/* 187 */             if (structureboundingbox.isVecInside((Vec3i)nextticklistentry.position)) {
/* 188 */               BlockPos blockpos8 = nextticklistentry.position.add((Vec3i)blockpos3);
/* 189 */               world.scheduleBlockUpdate(blockpos8, nextticklistentry.getBlock(), (int)(nextticklistentry.scheduledTime - world.getWorldInfo().getWorldTotalTime()), nextticklistentry.priority);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 194 */         if (i <= 0) {
/* 195 */           throw new CommandException("commands.clone.failed", new Object[0]);
/*     */         }
/* 197 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 198 */         notifyOperators(sender, this, "commands.clone.success", new Object[] { Integer.valueOf(i) });
/*     */       } else {
/*     */         
/* 201 */         throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */       } 
/*     */     } else {
/* 204 */       throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 212 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? func_175771_a(args, 6, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "masked", "filtered" }) : ((args.length == 11) ? getListOfStringsMatchingLastWord(args, new String[] { "normal", "force", "move" }) : ((args.length == 12 && "filtered".equals(args[9])) ? getListOfStringsMatchingLastWord(args, Block.blockRegistry.getKeys()) : null)))));
/*     */   }
/*     */   
/*     */   static class StaticCloneData {
/*     */     public final BlockPos pos;
/*     */     public final IBlockState blockState;
/*     */     public final NBTTagCompound compound;
/*     */     
/*     */     public StaticCloneData(BlockPos posIn, IBlockState stateIn, NBTTagCompound compoundIn) {
/* 221 */       this.pos = posIn;
/* 222 */       this.blockState = stateIn;
/* 223 */       this.compound = compoundIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandClone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */