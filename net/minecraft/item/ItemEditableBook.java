/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemEditableBook extends Item {
/*     */   public ItemEditableBook() {
/*  23 */     setMaxStackSize(1);
/*     */   }
/*     */   
/*     */   public static boolean validBookTagContents(NBTTagCompound nbt) {
/*  27 */     if (!ItemWritableBook.isNBTValid(nbt))
/*  28 */       return false; 
/*  29 */     if (!nbt.hasKey("title", 8)) {
/*  30 */       return false;
/*     */     }
/*  32 */     String s = nbt.getString("title");
/*  33 */     return (s != null && s.length() <= 32) ? nbt.hasKey("author", 8) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGeneration(ItemStack book) {
/*  41 */     return book.getTagCompound().getInteger("generation");
/*     */   }
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  45 */     if (stack.hasTagCompound()) {
/*  46 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  47 */       String s = nbttagcompound.getString("title");
/*     */       
/*  49 */       if (!StringUtils.isNullOrEmpty(s)) {
/*  50 */         return s;
/*     */       }
/*     */     } 
/*     */     
/*  54 */     return super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/*  61 */     if (stack.hasTagCompound()) {
/*  62 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*  63 */       String s = nbttagcompound.getString("author");
/*     */       
/*  65 */       if (!StringUtils.isNullOrEmpty(s)) {
/*  66 */         tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted("book.byAuthor", new Object[] { s }));
/*     */       }
/*     */       
/*  69 */       tooltip.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  77 */     if (!worldIn.isRemote) {
/*  78 */       resolveContents(itemStackIn, playerIn);
/*     */     }
/*     */     
/*  81 */     playerIn.displayGUIBook(itemStackIn);
/*  82 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*  83 */     return itemStackIn;
/*     */   }
/*     */   
/*     */   private void resolveContents(ItemStack stack, EntityPlayer player) {
/*  87 */     if (stack != null && stack.getTagCompound() != null) {
/*  88 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/*  90 */       if (!nbttagcompound.getBoolean("resolved")) {
/*  91 */         nbttagcompound.setBoolean("resolved", true);
/*     */         
/*  93 */         if (validBookTagContents(nbttagcompound)) {
/*  94 */           NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);
/*     */           
/*  96 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  97 */             ChatComponentText chatComponentText; String s = nbttaglist.getStringTagAt(i);
/*     */ 
/*     */             
/*     */             try {
/* 101 */               IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 102 */               ichatcomponent = ChatComponentProcessor.processComponent((ICommandSender)player, ichatcomponent, (Entity)player);
/* 103 */             } catch (Exception var9) {
/* 104 */               chatComponentText = new ChatComponentText(s);
/*     */             } 
/*     */             
/* 107 */             nbttaglist.set(i, (NBTBase)new NBTTagString(IChatComponent.Serializer.componentToJson((IChatComponent)chatComponentText)));
/*     */           } 
/*     */           
/* 110 */           nbttagcompound.setTag("pages", (NBTBase)nbttaglist);
/*     */           
/* 112 */           if (player instanceof EntityPlayerMP && player.getCurrentEquippedItem() == stack) {
/* 113 */             Slot slot = player.openContainer.getSlotFromInventory((IInventory)player.inventory, player.inventory.currentItem);
/* 114 */             ((EntityPlayerMP)player).playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(0, slot.slotNumber, stack));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 122 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemEditableBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */