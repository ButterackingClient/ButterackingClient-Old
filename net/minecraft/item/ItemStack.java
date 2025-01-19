/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentDurability;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public final class ItemStack
/*     */ {
/*  39 */   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
/*     */ 
/*     */   
/*     */   public int stackSize;
/*     */ 
/*     */   
/*     */   public int animationsToGo;
/*     */ 
/*     */   
/*     */   private Item item;
/*     */ 
/*     */   
/*     */   private NBTTagCompound stackTagCompound;
/*     */ 
/*     */   
/*     */   private int itemDamage;
/*     */   
/*     */   private EntityItemFrame itemFrame;
/*     */   
/*     */   private Block canDestroyCacheBlock;
/*     */   
/*     */   private boolean canDestroyCacheResult;
/*     */   
/*     */   private Block canPlaceOnCacheBlock;
/*     */   
/*     */   private boolean canPlaceOnCacheResult;
/*     */ 
/*     */   
/*     */   public ItemStack(Block blockIn) {
/*  68 */     this(blockIn, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Block blockIn, int amount) {
/*  72 */     this(blockIn, amount, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Block blockIn, int amount, int meta) {
/*  76 */     this(Item.getItemFromBlock(blockIn), amount, meta);
/*     */   }
/*     */   
/*     */   public ItemStack(Item itemIn) {
/*  80 */     this(itemIn, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Item itemIn, int amount) {
/*  84 */     this(itemIn, amount, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Item itemIn, int amount, int meta) {
/*  88 */     this.canDestroyCacheBlock = null;
/*  89 */     this.canDestroyCacheResult = false;
/*  90 */     this.canPlaceOnCacheBlock = null;
/*  91 */     this.canPlaceOnCacheResult = false;
/*  92 */     this.item = itemIn;
/*  93 */     this.stackSize = amount;
/*  94 */     this.itemDamage = meta;
/*     */     
/*  96 */     if (this.itemDamage < 0) {
/*  97 */       this.itemDamage = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static ItemStack loadItemStackFromNBT(NBTTagCompound nbt) {
/* 102 */     ItemStack itemstack = new ItemStack();
/* 103 */     itemstack.readFromNBT(nbt);
/* 104 */     return (itemstack.getItem() != null) ? itemstack : null;
/*     */   }
/*     */   
/*     */   private ItemStack() {
/* 108 */     this.canDestroyCacheBlock = null;
/* 109 */     this.canDestroyCacheResult = false;
/* 110 */     this.canPlaceOnCacheBlock = null;
/* 111 */     this.canPlaceOnCacheResult = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int amount) {
/* 118 */     ItemStack itemstack = new ItemStack(this.item, amount, this.itemDamage);
/*     */     
/* 120 */     if (this.stackTagCompound != null) {
/* 121 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*     */     }
/*     */     
/* 124 */     this.stackSize -= amount;
/* 125 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 132 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 140 */     boolean flag = getItem().onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
/*     */     
/* 142 */     if (flag) {
/* 143 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*     */     }
/*     */     
/* 146 */     return flag;
/*     */   }
/*     */   
/*     */   public float getStrVsBlock(Block blockIn) {
/* 150 */     return getItem().getStrVsBlock(this, blockIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack useItemRightClick(World worldIn, EntityPlayer playerIn) {
/* 158 */     return getItem().onItemRightClick(this, worldIn, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(World worldIn, EntityPlayer playerIn) {
/* 165 */     return getItem().onItemUseFinish(this, worldIn, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/* 172 */     ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
/* 173 */     nbt.setString("id", (resourcelocation == null) ? "minecraft:air" : resourcelocation.toString());
/* 174 */     nbt.setByte("Count", (byte)this.stackSize);
/* 175 */     nbt.setShort("Damage", (short)this.itemDamage);
/*     */     
/* 177 */     if (this.stackTagCompound != null) {
/* 178 */       nbt.setTag("tag", (NBTBase)this.stackTagCompound);
/*     */     }
/*     */     
/* 181 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 188 */     if (nbt.hasKey("id", 8)) {
/* 189 */       this.item = Item.getByNameOrId(nbt.getString("id"));
/*     */     } else {
/* 191 */       this.item = Item.getItemById(nbt.getShort("id"));
/*     */     } 
/*     */     
/* 194 */     this.stackSize = nbt.getByte("Count");
/* 195 */     this.itemDamage = nbt.getShort("Damage");
/*     */     
/* 197 */     if (this.itemDamage < 0) {
/* 198 */       this.itemDamage = 0;
/*     */     }
/*     */     
/* 201 */     if (nbt.hasKey("tag", 10)) {
/* 202 */       this.stackTagCompound = nbt.getCompoundTag("tag");
/*     */       
/* 204 */       if (this.item != null) {
/* 205 */         this.item.updateItemStackNBT(this.stackTagCompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 214 */     return getItem().getItemStackLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackable() {
/* 221 */     return (getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemStackDamageable() {
/* 228 */     return (this.item == null) ? false : ((this.item.getMaxDamage() <= 0) ? false : (!(hasTagCompound() && getTagCompound().getBoolean("Unbreakable"))));
/*     */   }
/*     */   
/*     */   public boolean getHasSubtypes() {
/* 232 */     return this.item.getHasSubtypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemDamaged() {
/* 239 */     return (isItemStackDamageable() && this.itemDamage > 0);
/*     */   }
/*     */   
/*     */   public int getItemDamage() {
/* 243 */     return this.itemDamage;
/*     */   }
/*     */   
/*     */   public int getMetadata() {
/* 247 */     return this.itemDamage;
/*     */   }
/*     */   
/*     */   public void setItemDamage(int meta) {
/* 251 */     this.itemDamage = meta;
/*     */     
/* 253 */     if (this.itemDamage < 0) {
/* 254 */       this.itemDamage = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDamage() {
/* 262 */     return this.item.getMaxDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attemptDamageItem(int amount, Random rand) {
/* 272 */     if (!isItemStackDamageable()) {
/* 273 */       return false;
/*     */     }
/* 275 */     if (amount > 0) {
/* 276 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
/* 277 */       int j = 0;
/*     */       
/* 279 */       for (int k = 0; i > 0 && k < amount; k++) {
/* 280 */         if (EnchantmentDurability.negateDamage(this, i, rand)) {
/* 281 */           j++;
/*     */         }
/*     */       } 
/*     */       
/* 285 */       amount -= j;
/*     */       
/* 287 */       if (amount <= 0) {
/* 288 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 292 */     this.itemDamage += amount;
/* 293 */     return (this.itemDamage > getMaxDamage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageItem(int amount, EntityLivingBase entityIn) {
/* 301 */     if ((!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).capabilities.isCreativeMode) && 
/* 302 */       isItemStackDamageable() && 
/* 303 */       attemptDamageItem(amount, entityIn.getRNG())) {
/* 304 */       entityIn.renderBrokenItemStack(this);
/* 305 */       this.stackSize--;
/*     */       
/* 307 */       if (entityIn instanceof EntityPlayer) {
/* 308 */         EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 309 */         entityplayer.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
/*     */         
/* 311 */         if (this.stackSize == 0 && getItem() instanceof ItemBow) {
/* 312 */           entityplayer.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */       
/* 316 */       if (this.stackSize < 0) {
/* 317 */         this.stackSize = 0;
/*     */       }
/*     */       
/* 320 */       this.itemDamage = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
/* 330 */     boolean flag = this.item.hitEntity(this, entityIn, (EntityLivingBase)playerIn);
/*     */     
/* 332 */     if (flag) {
/* 333 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyed(World worldIn, Block blockIn, BlockPos pos, EntityPlayer playerIn) {
/* 341 */     boolean flag = this.item.onBlockDestroyed(this, worldIn, blockIn, pos, (EntityLivingBase)playerIn);
/*     */     
/* 343 */     if (flag) {
/* 344 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(Block blockIn) {
/* 352 */     return this.item.canHarvestBlock(blockIn);
/*     */   }
/*     */   
/*     */   public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn) {
/* 356 */     return this.item.itemInteractionForEntity(this, playerIn, entityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack copy() {
/* 363 */     ItemStack itemstack = new ItemStack(this.item, this.stackSize, this.itemDamage);
/*     */     
/* 365 */     if (this.stackTagCompound != null) {
/* 366 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*     */     }
/*     */     
/* 369 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
/* 373 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? ((stackA.stackTagCompound == null && stackB.stackTagCompound != null) ? false : (!(stackA.stackTagCompound != null && !stackA.stackTagCompound.equals(stackB.stackTagCompound)))) : false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
/* 380 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? stackA.isItemStackEqual(stackB) : false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isItemStackEqual(ItemStack other) {
/* 387 */     return (this.stackSize != other.stackSize) ? false : ((this.item != other.item) ? false : ((this.itemDamage != other.itemDamage) ? false : ((this.stackTagCompound == null && other.stackTagCompound != null) ? false : (!(this.stackTagCompound != null && !this.stackTagCompound.equals(other.stackTagCompound))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
/* 394 */     return (stackA == null && stackB == null) ? true : ((stackA != null && stackB != null) ? stackA.isItemEqual(stackB) : false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemEqual(ItemStack other) {
/* 402 */     return (other != null && this.item == other.item && this.itemDamage == other.itemDamage);
/*     */   }
/*     */   
/*     */   public String getUnlocalizedName() {
/* 406 */     return this.item.getUnlocalizedName(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack copyItemStack(ItemStack stack) {
/* 413 */     return (stack == null) ? null : stack.copy();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 417 */     return String.valueOf(this.stackSize) + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAnimation(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
/* 425 */     if (this.animationsToGo > 0) {
/* 426 */       this.animationsToGo--;
/*     */     }
/*     */     
/* 429 */     this.item.onUpdate(this, worldIn, entityIn, inventorySlot, isCurrentItem);
/*     */   }
/*     */   
/*     */   public void onCrafting(World worldIn, EntityPlayer playerIn, int amount) {
/* 433 */     playerIn.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], amount);
/* 434 */     this.item.onCreated(this, worldIn, playerIn);
/*     */   }
/*     */   
/*     */   public boolean getIsItemStackEqual(ItemStack p_179549_1_) {
/* 438 */     return isItemStackEqual(p_179549_1_);
/*     */   }
/*     */   
/*     */   public int getMaxItemUseDuration() {
/* 442 */     return getItem().getMaxItemUseDuration(this);
/*     */   }
/*     */   
/*     */   public EnumAction getItemUseAction() {
/* 446 */     return getItem().getItemUseAction(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerStoppedUsing(World worldIn, EntityPlayer playerIn, int timeLeft) {
/* 453 */     getItem().onPlayerStoppedUsing(this, worldIn, playerIn, timeLeft);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTagCompound() {
/* 460 */     return (this.stackTagCompound != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getTagCompound() {
/* 467 */     return this.stackTagCompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getSubCompound(String key, boolean create) {
/* 474 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10))
/* 475 */       return this.stackTagCompound.getCompoundTag(key); 
/* 476 */     if (create) {
/* 477 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 478 */       setTagInfo(key, (NBTBase)nbttagcompound);
/* 479 */       return nbttagcompound;
/*     */     } 
/* 481 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList getEnchantmentTagList() {
/* 486 */     return (this.stackTagCompound == null) ? null : this.stackTagCompound.getTagList("ench", 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTagCompound(NBTTagCompound nbt) {
/* 493 */     this.stackTagCompound = nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 500 */     String s = getItem().getItemStackDisplayName(this);
/*     */     
/* 502 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
/* 503 */       NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*     */       
/* 505 */       if (nbttagcompound.hasKey("Name", 8)) {
/* 506 */         s = nbttagcompound.getString("Name");
/*     */       }
/*     */     } 
/*     */     
/* 510 */     return s;
/*     */   }
/*     */   
/*     */   public ItemStack setStackDisplayName(String displayName) {
/* 514 */     if (this.stackTagCompound == null) {
/* 515 */       this.stackTagCompound = new NBTTagCompound();
/*     */     }
/*     */     
/* 518 */     if (!this.stackTagCompound.hasKey("display", 10)) {
/* 519 */       this.stackTagCompound.setTag("display", (NBTBase)new NBTTagCompound());
/*     */     }
/*     */     
/* 522 */     this.stackTagCompound.getCompoundTag("display").setString("Name", displayName);
/* 523 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCustomName() {
/* 530 */     if (this.stackTagCompound != null && 
/* 531 */       this.stackTagCompound.hasKey("display", 10)) {
/* 532 */       NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/* 533 */       nbttagcompound.removeTag("Name");
/*     */       
/* 535 */       if (nbttagcompound.hasNoTags()) {
/* 536 */         this.stackTagCompound.removeTag("display");
/*     */         
/* 538 */         if (this.stackTagCompound.hasNoTags()) {
/* 539 */           setTagCompound(null);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDisplayName() {
/* 550 */     return (this.stackTagCompound == null) ? false : (!this.stackTagCompound.hasKey("display", 10) ? false : this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8));
/*     */   }
/*     */   
/*     */   public List<String> getTooltip(EntityPlayer playerIn, boolean advanced) {
/* 554 */     List<String> list = Lists.newArrayList();
/* 555 */     String s = getDisplayName();
/*     */     
/* 557 */     if (hasDisplayName()) {
/* 558 */       s = EnumChatFormatting.ITALIC + s;
/*     */     }
/*     */     
/* 561 */     s = String.valueOf(s) + EnumChatFormatting.RESET;
/*     */     
/* 563 */     if (advanced) {
/* 564 */       String s1 = "";
/*     */       
/* 566 */       if (s.length() > 0) {
/* 567 */         s = String.valueOf(s) + " (";
/* 568 */         s1 = ")";
/*     */       } 
/*     */       
/* 571 */       int i = Item.getIdFromItem(this.item);
/*     */       
/* 573 */       if (getHasSubtypes()) {
/* 574 */         s = String.valueOf(s) + String.format("#%04d/%d%s", new Object[] { Integer.valueOf(i), Integer.valueOf(this.itemDamage), s1 });
/*     */       } else {
/* 576 */         s = String.valueOf(s) + String.format("#%04d%s", new Object[] { Integer.valueOf(i), s1 });
/*     */       } 
/* 578 */     } else if (!hasDisplayName() && this.item == Items.filled_map) {
/* 579 */       s = String.valueOf(s) + " #" + this.itemDamage;
/*     */     } 
/*     */     
/* 582 */     list.add(s);
/* 583 */     int i1 = 0;
/*     */     
/* 585 */     if (hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99)) {
/* 586 */       i1 = this.stackTagCompound.getInteger("HideFlags");
/*     */     }
/*     */     
/* 589 */     if ((i1 & 0x20) == 0) {
/* 590 */       this.item.addInformation(this, playerIn, list, advanced);
/*     */     }
/*     */     
/* 593 */     if (hasTagCompound()) {
/* 594 */       if ((i1 & 0x1) == 0) {
/* 595 */         NBTTagList nbttaglist = getEnchantmentTagList();
/*     */         
/* 597 */         if (nbttaglist != null) {
/* 598 */           for (int j = 0; j < nbttaglist.tagCount(); j++) {
/* 599 */             int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/* 600 */             int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/*     */             
/* 602 */             if (Enchantment.getEnchantmentById(k) != null) {
/* 603 */               list.add(Enchantment.getEnchantmentById(k).getTranslatedName(l));
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 609 */       if (this.stackTagCompound.hasKey("display", 10)) {
/* 610 */         NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*     */         
/* 612 */         if (nbttagcompound.hasKey("color", 3)) {
/* 613 */           if (advanced) {
/* 614 */             list.add("Color: #" + Integer.toHexString(nbttagcompound.getInteger("color")).toUpperCase());
/*     */           } else {
/* 616 */             list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
/*     */           } 
/*     */         }
/*     */         
/* 620 */         if (nbttagcompound.getTagId("Lore") == 9) {
/* 621 */           NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);
/*     */           
/* 623 */           if (nbttaglist1.tagCount() > 0) {
/* 624 */             for (int j1 = 0; j1 < nbttaglist1.tagCount(); j1++) {
/* 625 */               list.add(EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.ITALIC + nbttaglist1.getStringTagAt(j1));
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 632 */     Multimap<String, AttributeModifier> multimap = getAttributeModifiers();
/*     */     
/* 634 */     if (!multimap.isEmpty() && (i1 & 0x2) == 0) {
/* 635 */       list.add("");
/*     */       
/* 637 */       for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)multimap.entries()) {
/* 638 */         double d1; AttributeModifier attributemodifier = entry.getValue();
/* 639 */         double d0 = attributemodifier.getAmount();
/*     */         
/* 641 */         if (attributemodifier.getID() == Item.itemModifierUUID) {
/* 642 */           d0 += EnchantmentHelper.getModifierForCreature(this, EnumCreatureAttribute.UNDEFINED);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 647 */         if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
/* 648 */           d1 = d0;
/*     */         } else {
/* 650 */           d1 = d0 * 100.0D;
/*     */         } 
/*     */         
/* 653 */         if (d0 > 0.0D) {
/* 654 */           list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) })); continue;
/* 655 */         }  if (d0 < 0.0D) {
/* 656 */           d1 *= -1.0D;
/* 657 */           list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier.getOperation(), new Object[] { DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) }));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 662 */     if (hasTagCompound() && getTagCompound().getBoolean("Unbreakable") && (i1 & 0x4) == 0) {
/* 663 */       list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
/*     */     }
/*     */     
/* 666 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (i1 & 0x8) == 0) {
/* 667 */       NBTTagList nbttaglist2 = this.stackTagCompound.getTagList("CanDestroy", 8);
/*     */       
/* 669 */       if (nbttaglist2.tagCount() > 0) {
/* 670 */         list.add("");
/* 671 */         list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));
/*     */         
/* 673 */         for (int k1 = 0; k1 < nbttaglist2.tagCount(); k1++) {
/* 674 */           Block block = Block.getBlockFromName(nbttaglist2.getStringTagAt(k1));
/*     */           
/* 676 */           if (block != null) {
/* 677 */             list.add(EnumChatFormatting.DARK_GRAY + block.getLocalizedName());
/*     */           } else {
/* 679 */             list.add(EnumChatFormatting.DARK_GRAY + "missingno");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 685 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (i1 & 0x10) == 0) {
/* 686 */       NBTTagList nbttaglist3 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*     */       
/* 688 */       if (nbttaglist3.tagCount() > 0) {
/* 689 */         list.add("");
/* 690 */         list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));
/*     */         
/* 692 */         for (int l1 = 0; l1 < nbttaglist3.tagCount(); l1++) {
/* 693 */           Block block1 = Block.getBlockFromName(nbttaglist3.getStringTagAt(l1));
/*     */           
/* 695 */           if (block1 != null) {
/* 696 */             list.add(EnumChatFormatting.DARK_GRAY + block1.getLocalizedName());
/*     */           } else {
/* 698 */             list.add(EnumChatFormatting.DARK_GRAY + "missingno");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 704 */     if (advanced) {
/* 705 */       if (isItemDamaged()) {
/* 706 */         list.add("Durability: " + (getMaxDamage() - getItemDamage()) + " / " + getMaxDamage());
/*     */       }
/*     */       
/* 709 */       list.add(EnumChatFormatting.DARK_GRAY + ((ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString());
/*     */       
/* 711 */       if (hasTagCompound()) {
/* 712 */         list.add(EnumChatFormatting.DARK_GRAY + "NBT: " + getTagCompound().getKeySet().size() + " tag(s)");
/*     */       }
/*     */     } 
/*     */     
/* 716 */     return list;
/*     */   }
/*     */   
/*     */   public boolean hasEffect() {
/* 720 */     return getItem().hasEffect(this);
/*     */   }
/*     */   
/*     */   public EnumRarity getRarity() {
/* 724 */     return getItem().getRarity(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemEnchantable() {
/* 731 */     return !getItem().isItemTool(this) ? false : (!isItemEnchanted());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEnchantment(Enchantment ench, int level) {
/* 738 */     if (this.stackTagCompound == null) {
/* 739 */       setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 742 */     if (!this.stackTagCompound.hasKey("ench", 9)) {
/* 743 */       this.stackTagCompound.setTag("ench", (NBTBase)new NBTTagList());
/*     */     }
/*     */     
/* 746 */     NBTTagList nbttaglist = this.stackTagCompound.getTagList("ench", 10);
/* 747 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 748 */     nbttagcompound.setShort("id", (short)ench.effectId);
/* 749 */     nbttagcompound.setShort("lvl", (short)(byte)level);
/* 750 */     nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemEnchanted() {
/* 757 */     return (this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9));
/*     */   }
/*     */   
/*     */   public void setTagInfo(String key, NBTBase value) {
/* 761 */     if (this.stackTagCompound == null) {
/* 762 */       setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 765 */     this.stackTagCompound.setTag(key, value);
/*     */   }
/*     */   
/*     */   public boolean canEditBlocks() {
/* 769 */     return getItem().canItemEditBlocks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnItemFrame() {
/* 776 */     return (this.itemFrame != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemFrame(EntityItemFrame frame) {
/* 783 */     this.itemFrame = frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityItemFrame getItemFrame() {
/* 790 */     return this.itemFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRepairCost() {
/* 797 */     return (hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRepairCost(int cost) {
/* 804 */     if (!hasTagCompound()) {
/* 805 */       this.stackTagCompound = new NBTTagCompound();
/*     */     }
/*     */     
/* 808 */     this.stackTagCompound.setInteger("RepairCost", cost);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getAttributeModifiers() {
/*     */     Multimap<String, AttributeModifier> multimap;
/* 814 */     if (hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
/* 815 */       HashMultimap hashMultimap = HashMultimap.create();
/* 816 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);
/*     */       
/* 818 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 819 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 820 */         AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttagcompound);
/*     */         
/* 822 */         if (attributemodifier != null && attributemodifier.getID().getLeastSignificantBits() != 0L && attributemodifier.getID().getMostSignificantBits() != 0L) {
/* 823 */           hashMultimap.put(nbttagcompound.getString("AttributeName"), attributemodifier);
/*     */         }
/*     */       } 
/*     */     } else {
/* 827 */       multimap = getItem().getItemAttributeModifiers();
/*     */     } 
/*     */     
/* 830 */     return multimap;
/*     */   }
/*     */   
/*     */   public void setItem(Item newItem) {
/* 834 */     this.item = newItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getChatComponent() {
/* 841 */     ChatComponentText chatcomponenttext = new ChatComponentText(getDisplayName());
/*     */     
/* 843 */     if (hasDisplayName()) {
/* 844 */       chatcomponenttext.getChatStyle().setItalic(Boolean.valueOf(true));
/*     */     }
/*     */     
/* 847 */     IChatComponent ichatcomponent = (new ChatComponentText("[")).appendSibling((IChatComponent)chatcomponenttext).appendText("]");
/*     */     
/* 849 */     if (this.item != null) {
/* 850 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 851 */       writeToNBT(nbttagcompound);
/* 852 */       ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, (IChatComponent)new ChatComponentText(nbttagcompound.toString())));
/* 853 */       ichatcomponent.getChatStyle().setColor((getRarity()).rarityColor);
/*     */     } 
/*     */     
/* 856 */     return ichatcomponent;
/*     */   }
/*     */   
/*     */   public boolean canDestroy(Block blockIn) {
/* 860 */     if (blockIn == this.canDestroyCacheBlock) {
/* 861 */       return this.canDestroyCacheResult;
/*     */     }
/* 863 */     this.canDestroyCacheBlock = blockIn;
/*     */     
/* 865 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
/* 866 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanDestroy", 8);
/*     */       
/* 868 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 869 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*     */         
/* 871 */         if (block == blockIn) {
/* 872 */           this.canDestroyCacheResult = true;
/* 873 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 878 */     this.canDestroyCacheResult = false;
/* 879 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceOn(Block blockIn) {
/* 884 */     if (blockIn == this.canPlaceOnCacheBlock) {
/* 885 */       return this.canPlaceOnCacheResult;
/*     */     }
/* 887 */     this.canPlaceOnCacheBlock = blockIn;
/*     */     
/* 889 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
/* 890 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*     */       
/* 892 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 893 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*     */         
/* 895 */         if (block == blockIn) {
/* 896 */           this.canPlaceOnCacheResult = true;
/* 897 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 902 */     this.canPlaceOnCacheResult = false;
/* 903 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */