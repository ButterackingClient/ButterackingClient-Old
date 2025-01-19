/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ public class CommandClearInventory
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 19 */     return "clear";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 26 */     return "commands.clear.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 33 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/* 40 */     EntityPlayerMP entityplayermp = (args.length == 0) ? getCommandSenderAsPlayer(sender) : getPlayer(sender, args[0]);
/* 41 */     Item item = (args.length >= 2) ? getItemByText(sender, args[1]) : null;
/* 42 */     int i = (args.length >= 3) ? parseInt(args[2], -1) : -1;
/* 43 */     int j = (args.length >= 4) ? parseInt(args[3], -1) : -1;
/* 44 */     NBTTagCompound nbttagcompound = null;
/*    */     
/* 46 */     if (args.length >= 5) {
/*    */       try {
/* 48 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 4));
/* 49 */       } catch (NBTException nbtexception) {
/* 50 */         throw new CommandException("commands.clear.tagError", new Object[] { nbtexception.getMessage() });
/*    */       } 
/*    */     }
/*    */     
/* 54 */     if (args.length >= 2 && item == null) {
/* 55 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getName() });
/*    */     }
/* 57 */     int k = entityplayermp.inventory.clearMatchingItems(item, i, j, nbttagcompound);
/* 58 */     entityplayermp.inventoryContainer.detectAndSendChanges();
/*    */     
/* 60 */     if (!entityplayermp.capabilities.isCreativeMode) {
/* 61 */       entityplayermp.updateHeldItem();
/*    */     }
/*    */     
/* 64 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/*    */     
/* 66 */     if (k == 0) {
/* 67 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getName() });
/*    */     }
/* 69 */     if (j == 0) {
/* 70 */       sender.addChatMessage((IChatComponent)new ChatComponentTranslation("commands.clear.testing", new Object[] { entityplayermp.getName(), Integer.valueOf(k) }));
/*    */     } else {
/* 72 */       notifyOperators(sender, this, "commands.clear.success", new Object[] { entityplayermp.getName(), Integer.valueOf(k) });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 79 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, func_147209_d()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys()) : null);
/*    */   }
/*    */   
/*    */   protected String[] func_147209_d() {
/* 83 */     return MinecraftServer.getServer().getAllUsernames();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 90 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandClearInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */