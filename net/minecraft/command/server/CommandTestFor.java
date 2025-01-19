/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTUtil;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class CommandTestFor
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 22 */     return "testfor";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 29 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 36 */     return "commands.testfor.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 43 */     if (args.length < 1) {
/* 44 */       throw new WrongUsageException("commands.testfor.usage", new Object[0]);
/*    */     }
/* 46 */     Entity entity = getEntity(sender, args[0]);
/* 47 */     NBTTagCompound nbttagcompound = null;
/*    */     
/* 49 */     if (args.length >= 2) {
/*    */       try {
/* 51 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 1));
/* 52 */       } catch (NBTException nbtexception) {
/* 53 */         throw new CommandException("commands.testfor.tagError", new Object[] { nbtexception.getMessage() });
/*    */       } 
/*    */     }
/*    */     
/* 57 */     if (nbttagcompound != null) {
/* 58 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 59 */       entity.writeToNBT(nbttagcompound1);
/*    */       
/* 61 */       if (!NBTUtil.func_181123_a((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true)) {
/* 62 */         throw new CommandException("commands.testfor.failure", new Object[] { entity.getName() });
/*    */       }
/*    */     } 
/*    */     
/* 66 */     notifyOperators(sender, (ICommand)this, "commands.testfor.success", new Object[] { entity.getName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 74 */     return (index == 0);
/*    */   }
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 78 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\server\CommandTestFor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */