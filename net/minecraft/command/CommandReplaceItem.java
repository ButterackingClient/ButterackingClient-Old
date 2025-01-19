/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandReplaceItem
/*     */   extends CommandBase
/*     */ {
/*  23 */   private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  29 */     return "replaceitem";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  36 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  43 */     return "commands.replaceitem.usage";
/*     */   }
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     Item item;
/*  50 */     if (args.length < 1) {
/*  51 */       throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  55 */     if (args[0].equals("entity")) {
/*  56 */       flag = false;
/*     */     } else {
/*  58 */       if (!args[0].equals("block")) {
/*  59 */         throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */       }
/*     */       
/*  62 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  67 */     if (flag) {
/*  68 */       if (args.length < 6) {
/*  69 */         throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  72 */       i = 4;
/*     */     } else {
/*  74 */       if (args.length < 4) {
/*  75 */         throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  78 */       i = 2;
/*     */     } 
/*     */     
/*  81 */     int j = getSlotForShortcut(args[i++]);
/*     */ 
/*     */     
/*     */     try {
/*  85 */       item = getItemByText(sender, args[i]);
/*  86 */     } catch (NumberInvalidException numberinvalidexception) {
/*  87 */       if (Block.getBlockFromName(args[i]) != Blocks.air) {
/*  88 */         throw numberinvalidexception;
/*     */       }
/*     */       
/*  91 */       item = null;
/*     */     } 
/*     */     
/*  94 */     i++;
/*  95 */     int k = (args.length > i) ? parseInt(args[i++], 1, 64) : 1;
/*  96 */     int l = (args.length > i) ? parseInt(args[i++]) : 0;
/*  97 */     ItemStack itemstack = new ItemStack(item, k, l);
/*     */     
/*  99 */     if (args.length > i) {
/* 100 */       String s = getChatComponentFromNthArg(sender, args, i).getUnformattedText();
/*     */       
/*     */       try {
/* 103 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
/* 104 */       } catch (NBTException nbtexception) {
/* 105 */         throw new CommandException("commands.replaceitem.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     if (itemstack.getItem() == null) {
/* 110 */       itemstack = null;
/*     */     }
/*     */     
/* 113 */     if (flag) {
/* 114 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/* 115 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 116 */       World world = sender.getEntityWorld();
/* 117 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 119 */       if (tileentity == null || !(tileentity instanceof IInventory)) {
/* 120 */         throw new CommandException("commands.replaceitem.noContainer", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 123 */       IInventory iinventory = (IInventory)tileentity;
/*     */       
/* 125 */       if (j >= 0 && j < iinventory.getSizeInventory()) {
/* 126 */         iinventory.setInventorySlotContents(j, itemstack);
/*     */       }
/*     */     } else {
/* 129 */       Entity entity = getEntity(sender, args[1]);
/* 130 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/*     */       
/* 132 */       if (entity instanceof EntityPlayer) {
/* 133 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */       
/* 136 */       if (!entity.replaceItemInInventory(j, itemstack)) {
/* 137 */         throw new CommandException("commands.replaceitem.failed", new Object[] { Integer.valueOf(j), Integer.valueOf(k), (itemstack == null) ? "Air" : itemstack.getChatComponent() });
/*     */       }
/*     */       
/* 140 */       if (entity instanceof EntityPlayer) {
/* 141 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */     } 
/*     */     
/* 145 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/* 146 */     notifyOperators(sender, this, "commands.replaceitem.success", new Object[] { Integer.valueOf(j), Integer.valueOf(k), (itemstack == null) ? "Air" : itemstack.getChatComponent() });
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSlotForShortcut(String shortcut) throws CommandException {
/* 151 */     if (!SHORTCUTS.containsKey(shortcut)) {
/* 152 */       throw new CommandException("commands.generic.parameter.invalid", new Object[] { shortcut });
/*     */     }
/* 154 */     return ((Integer)SHORTCUTS.get(shortcut)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/* 159 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" }) : ((args.length == 2 && args[0].equals("entity")) ? getListOfStringsMatchingLastWord(args, getUsernames()) : ((args.length >= 2 && args.length <= 4 && args[0].equals("block")) ? func_175771_a(args, 1, pos) : (((args.length != 3 || !args[0].equals("entity")) && (args.length != 5 || !args[0].equals("block"))) ? (((args.length != 4 || !args[0].equals("entity")) && (args.length != 6 || !args[0].equals("block"))) ? null : getListOfStringsMatchingLastWord(args, Item.itemRegistry.getKeys())) : getListOfStringsMatchingLastWord(args, SHORTCUTS.keySet()))));
/*     */   }
/*     */   
/*     */   protected String[] getUsernames() {
/* 163 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 170 */     return (args.length > 0 && args[0].equals("entity") && index == 1);
/*     */   }
/*     */   
/*     */   static {
/* 174 */     for (int i = 0; i < 54; i++) {
/* 175 */       SHORTCUTS.put("slot.container." + i, Integer.valueOf(i));
/*     */     }
/*     */     
/* 178 */     for (int j = 0; j < 9; j++) {
/* 179 */       SHORTCUTS.put("slot.hotbar." + j, Integer.valueOf(j));
/*     */     }
/*     */     
/* 182 */     for (int k = 0; k < 27; k++) {
/* 183 */       SHORTCUTS.put("slot.inventory." + k, Integer.valueOf(9 + k));
/*     */     }
/*     */     
/* 186 */     for (int l = 0; l < 27; l++) {
/* 187 */       SHORTCUTS.put("slot.enderchest." + l, Integer.valueOf(200 + l));
/*     */     }
/*     */     
/* 190 */     for (int i1 = 0; i1 < 8; i1++) {
/* 191 */       SHORTCUTS.put("slot.villager." + i1, Integer.valueOf(300 + i1));
/*     */     }
/*     */     
/* 194 */     for (int j1 = 0; j1 < 15; j1++) {
/* 195 */       SHORTCUTS.put("slot.horse." + j1, Integer.valueOf(500 + j1));
/*     */     }
/*     */     
/* 198 */     SHORTCUTS.put("slot.weapon", Integer.valueOf(99));
/* 199 */     SHORTCUTS.put("slot.armor.head", Integer.valueOf(103));
/* 200 */     SHORTCUTS.put("slot.armor.chest", Integer.valueOf(102));
/* 201 */     SHORTCUTS.put("slot.armor.legs", Integer.valueOf(101));
/* 202 */     SHORTCUTS.put("slot.armor.feet", Integer.valueOf(100));
/* 203 */     SHORTCUTS.put("slot.horse.saddle", Integer.valueOf(400));
/* 204 */     SHORTCUTS.put("slot.horse.armor", Integer.valueOf(401));
/* 205 */     SHORTCUTS.put("slot.horse.chest", Integer.valueOf(499));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandReplaceItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */