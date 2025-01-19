/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBlockData
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 17 */     return "blockdata";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 24 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 31 */     return "commands.blockdata.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*    */     NBTTagCompound nbttagcompound2;
/* 38 */     if (args.length < 4) {
/* 39 */       throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
/*    */     }
/* 41 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/* 42 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/* 43 */     World world = sender.getEntityWorld();
/*    */     
/* 45 */     if (!world.isBlockLoaded(blockpos)) {
/* 46 */       throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
/*    */     }
/* 48 */     TileEntity tileentity = world.getTileEntity(blockpos);
/*    */     
/* 50 */     if (tileentity == null) {
/* 51 */       throw new CommandException("commands.blockdata.notValid", new Object[0]);
/*    */     }
/* 53 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 54 */     tileentity.writeToNBT(nbttagcompound);
/* 55 */     NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*    */ 
/*    */     
/*    */     try {
/* 59 */       nbttagcompound2 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(sender, args, 3).getUnformattedText());
/* 60 */     } catch (NBTException nbtexception) {
/* 61 */       throw new CommandException("commands.blockdata.tagError", new Object[] { nbtexception.getMessage() });
/*    */     } 
/*    */     
/* 64 */     nbttagcompound.merge(nbttagcompound2);
/* 65 */     nbttagcompound.setInteger("x", blockpos.getX());
/* 66 */     nbttagcompound.setInteger("y", blockpos.getY());
/* 67 */     nbttagcompound.setInteger("z", blockpos.getZ());
/*    */     
/* 69 */     if (nbttagcompound.equals(nbttagcompound1)) {
/* 70 */       throw new CommandException("commands.blockdata.failed", new Object[] { nbttagcompound.toString() });
/*    */     }
/* 72 */     tileentity.readFromNBT(nbttagcompound);
/* 73 */     tileentity.markDirty();
/* 74 */     world.markBlockForUpdate(blockpos);
/* 75 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 76 */     notifyOperators(sender, this, "commands.blockdata.success", new Object[] { nbttagcompound.toString() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 84 */     return (args.length > 0 && args.length <= 3) ? func_175771_a(args, 0, pos) : null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandBlockData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */