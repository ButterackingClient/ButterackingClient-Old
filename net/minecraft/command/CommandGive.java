/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ public class CommandGive
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "give";
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
/*  33 */     return "commands.give.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  40 */     if (args.length < 2) {
/*  41 */       throw new WrongUsageException("commands.give.usage", new Object[0]);
/*     */     }
/*  43 */     EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
/*  44 */     Item item = getItemByText(sender, args[1]);
/*  45 */     int i = (args.length >= 3) ? parseInt(args[2], 1, 256) : 1;
/*  46 */     int j = (args.length >= 4) ? parseInt(args[3]) : 0;
/*  47 */     ItemStack itemstack = new ItemStack(item, i, j);
/*     */     
/*  49 */     if (args.length >= 5) {
/*  50 */       String s = getChatComponentFromNthArg(sender, args, 4).getUnformattedText();
/*     */       
/*     */       try {
/*  53 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
/*  54 */       } catch (NBTException nbtexception) {
/*  55 */         throw new CommandException("commands.give.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  59 */     boolean flag = ((EntityPlayer)entityPlayerMP).inventory.addItemStackToInventory(itemstack);
/*     */     
/*  61 */     if (flag) {
/*  62 */       ((EntityPlayer)entityPlayerMP).worldObj.playSoundAtEntity((Entity)entityPlayerMP, "random.pop", 0.2F, ((entityPlayerMP.getRNG().nextFloat() - entityPlayerMP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  63 */       ((EntityPlayer)entityPlayerMP).inventoryContainer.detectAndSendChanges();
/*     */     } 
/*     */     
/*  66 */     if (flag && itemstack.stackSize <= 0) {
/*  67 */       itemstack.stackSize = 1;
/*  68 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i);
/*  69 */       EntityItem entityitem1 = entityPlayerMP.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       
/*  71 */       if (entityitem1 != null) {
/*  72 */         entityitem1.func_174870_v();
/*     */       }
/*     */     } else {
/*  75 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i - itemstack.stackSize);
/*  76 */       EntityItem entityitem = entityPlayerMP.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */       
/*  78 */       if (entityitem != null) {
/*  79 */         entityitem.setNoPickupDelay();
/*  80 */         entityitem.setOwner(entityPlayerMP.getName());
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     notifyOperators(sender, this, "commands.give.success", new Object[] { itemstack.getChatComponent(), Integer.valueOf(i), entityPlayerMP.getName() });
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  89 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, getPlayers()) : ((args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys()) : null);
/*     */   }
/*     */   
/*     */   protected String[] getPlayers() {
/*  93 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 100 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandGive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */