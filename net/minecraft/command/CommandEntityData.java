/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandEntityData
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 14 */     return "entitydata";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 21 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 28 */     return "commands.entitydata.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*    */     NBTTagCompound nbttagcompound2;
/* 35 */     if (args.length < 2) {
/* 36 */       throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
/*    */     }
/* 38 */     Entity entity = getEntity(sender, args[0]);
/*    */     
/* 40 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer) {
/* 41 */       throw new CommandException("commands.entitydata.noPlayers", new Object[] { entity.getDisplayName() });
/*    */     }
/* 43 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 44 */     entity.writeToNBT(nbttagcompound);
/* 45 */     NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*    */ 
/*    */     
/*    */     try {
/* 49 */       nbttagcompound2 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
/* 50 */     } catch (NBTException nbtexception) {
/* 51 */       throw new CommandException("commands.entitydata.tagError", new Object[] { nbtexception.getMessage() });
/*    */     } 
/*    */     
/* 54 */     nbttagcompound2.removeTag("UUIDMost");
/* 55 */     nbttagcompound2.removeTag("UUIDLeast");
/* 56 */     nbttagcompound.merge(nbttagcompound2);
/*    */     
/* 58 */     if (nbttagcompound.equals(nbttagcompound1)) {
/* 59 */       throw new CommandException("commands.entitydata.failed", new Object[] { nbttagcompound.toString() });
/*    */     }
/* 61 */     entity.readFromNBT(nbttagcompound);
/* 62 */     notifyOperators(sender, this, "commands.entitydata.success", new Object[] { nbttagcompound.toString() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 72 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandEntityData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */