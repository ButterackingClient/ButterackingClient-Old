/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ public class CommandCompare
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  18 */     return "testforblocks";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  25 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  32 */     return "commands.compare.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  39 */     if (args.length < 9) {
/*  40 */       throw new WrongUsageException("commands.compare.usage", new Object[0]);
/*     */     }
/*  42 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  43 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  44 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  45 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  46 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  47 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.func_175896_b()));
/*  48 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  50 */     if (i > 524288)
/*  51 */       throw new CommandException("commands.compare.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(524288) }); 
/*  52 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*  53 */       World world = sender.getEntityWorld();
/*     */       
/*  55 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1)) {
/*  56 */         boolean flag = false;
/*     */         
/*  58 */         if (args.length > 9 && args[9].equals("masked")) {
/*  59 */           flag = true;
/*     */         }
/*     */         
/*  62 */         i = 0;
/*  63 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*  64 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*  65 */         BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */         
/*  67 */         for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; j++) {
/*  68 */           for (int k = structureboundingbox.minY; k <= structureboundingbox.maxY; k++) {
/*  69 */             for (int l = structureboundingbox.minX; l <= structureboundingbox.maxX; l++) {
/*  70 */               blockpos$mutableblockpos.set(l, k, j);
/*  71 */               blockpos$mutableblockpos1.set(l + blockpos3.getX(), k + blockpos3.getY(), j + blockpos3.getZ());
/*  72 */               boolean flag1 = false;
/*  73 */               IBlockState iblockstate = world.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */               
/*  75 */               if (!flag || iblockstate.getBlock() != Blocks.air) {
/*  76 */                 if (iblockstate == world.getBlockState((BlockPos)blockpos$mutableblockpos1)) {
/*  77 */                   TileEntity tileentity = world.getTileEntity((BlockPos)blockpos$mutableblockpos);
/*  78 */                   TileEntity tileentity1 = world.getTileEntity((BlockPos)blockpos$mutableblockpos1);
/*     */                   
/*  80 */                   if (tileentity != null && tileentity1 != null) {
/*  81 */                     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  82 */                     tileentity.writeToNBT(nbttagcompound);
/*  83 */                     nbttagcompound.removeTag("x");
/*  84 */                     nbttagcompound.removeTag("y");
/*  85 */                     nbttagcompound.removeTag("z");
/*  86 */                     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  87 */                     tileentity1.writeToNBT(nbttagcompound1);
/*  88 */                     nbttagcompound1.removeTag("x");
/*  89 */                     nbttagcompound1.removeTag("y");
/*  90 */                     nbttagcompound1.removeTag("z");
/*     */                     
/*  92 */                     if (!nbttagcompound.equals(nbttagcompound1)) {
/*  93 */                       flag1 = true;
/*     */                     }
/*  95 */                   } else if (tileentity != null) {
/*  96 */                     flag1 = true;
/*     */                   } 
/*     */                 } else {
/*  99 */                   flag1 = true;
/*     */                 } 
/*     */                 
/* 102 */                 i++;
/*     */                 
/* 104 */                 if (flag1) {
/* 105 */                   throw new CommandException("commands.compare.failed", new Object[0]);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 112 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 113 */         notifyOperators(sender, this, "commands.compare.success", new Object[] { Integer.valueOf(i) });
/*     */       } else {
/* 115 */         throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */       } 
/*     */     } else {
/* 118 */       throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 124 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? func_175771_a(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? func_175771_a(args, 6, pos) : ((args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "masked", "all" }) : null)));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandCompare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */