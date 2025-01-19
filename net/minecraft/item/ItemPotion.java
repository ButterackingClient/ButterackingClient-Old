/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemPotion
/*     */   extends Item
/*     */ {
/*  30 */   private Map<Integer, List<PotionEffect>> effectCache = Maps.newHashMap();
/*  31 */   private static final Map<List<PotionEffect>, Integer> SUB_ITEMS_CACHE = Maps.newLinkedHashMap();
/*     */   
/*     */   public ItemPotion() {
/*  34 */     setMaxStackSize(1);
/*  35 */     setHasSubtypes(true);
/*  36 */     setMaxDamage(0);
/*  37 */     setCreativeTab(CreativeTabs.tabBrewing);
/*     */   }
/*     */   
/*     */   public List<PotionEffect> getEffects(ItemStack stack) {
/*  41 */     if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
/*  42 */       List<PotionEffect> list1 = Lists.newArrayList();
/*  43 */       NBTTagList nbttaglist = stack.getTagCompound().getTagList("CustomPotionEffects", 10);
/*     */       
/*  45 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  46 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  47 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*     */         
/*  49 */         if (potioneffect != null) {
/*  50 */           list1.add(potioneffect);
/*     */         }
/*     */       } 
/*     */       
/*  54 */       return list1;
/*     */     } 
/*  56 */     List<PotionEffect> list = this.effectCache.get(Integer.valueOf(stack.getMetadata()));
/*     */     
/*  58 */     if (list == null) {
/*  59 */       list = PotionHelper.getPotionEffects(stack.getMetadata(), false);
/*  60 */       this.effectCache.put(Integer.valueOf(stack.getMetadata()), list);
/*     */     } 
/*     */     
/*  63 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PotionEffect> getEffects(int meta) {
/*  68 */     List<PotionEffect> list = this.effectCache.get(Integer.valueOf(meta));
/*     */     
/*  70 */     if (list == null) {
/*  71 */       list = PotionHelper.getPotionEffects(meta, false);
/*  72 */       this.effectCache.put(Integer.valueOf(meta), list);
/*     */     } 
/*     */     
/*  75 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  83 */     if (!playerIn.capabilities.isCreativeMode) {
/*  84 */       stack.stackSize--;
/*     */     }
/*     */     
/*  87 */     if (!worldIn.isRemote) {
/*  88 */       List<PotionEffect> list = getEffects(stack);
/*     */       
/*  90 */       if (list != null) {
/*  91 */         for (PotionEffect potioneffect : list) {
/*  92 */           playerIn.addPotionEffect(new PotionEffect(potioneffect));
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/*  97 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */     
/*  99 */     if (!playerIn.capabilities.isCreativeMode) {
/* 100 */       if (stack.stackSize <= 0) {
/* 101 */         return new ItemStack(Items.glass_bottle);
/*     */       }
/*     */       
/* 104 */       playerIn.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
/*     */     } 
/*     */     
/* 107 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxItemUseDuration(ItemStack stack) {
/* 114 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction getItemUseAction(ItemStack stack) {
/* 121 */     return EnumAction.DRINK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 128 */     if (isSplash(itemStackIn.getMetadata())) {
/* 129 */       if (!playerIn.capabilities.isCreativeMode) {
/* 130 */         itemStackIn.stackSize--;
/*     */       }
/*     */       
/* 133 */       worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/*     */       
/* 135 */       if (!worldIn.isRemote) {
/* 136 */         worldIn.spawnEntityInWorld((Entity)new EntityPotion(worldIn, (EntityLivingBase)playerIn, itemStackIn));
/*     */       }
/*     */       
/* 139 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 140 */       return itemStackIn;
/*     */     } 
/* 142 */     playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
/* 143 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSplash(int meta) {
/* 151 */     return ((meta & 0x4000) != 0);
/*     */   }
/*     */   
/*     */   public int getColorFromDamage(int meta) {
/* 155 */     return PotionHelper.getLiquidColor(meta, false);
/*     */   }
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 159 */     return (renderPass > 0) ? 16777215 : getColorFromDamage(stack.getMetadata());
/*     */   }
/*     */   
/*     */   public boolean isEffectInstant(int meta) {
/* 163 */     List<PotionEffect> list = getEffects(meta);
/*     */     
/* 165 */     if (list != null && !list.isEmpty()) {
/* 166 */       for (PotionEffect potioneffect : list) {
/* 167 */         if (Potion.potionTypes[potioneffect.getPotionID()].isInstant()) {
/* 168 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 172 */       return false;
/*     */     } 
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/* 179 */     if (stack.getMetadata() == 0) {
/* 180 */       return StatCollector.translateToLocal("item.emptyPotion.name").trim();
/*     */     }
/* 182 */     String s = "";
/*     */     
/* 184 */     if (isSplash(stack.getMetadata())) {
/* 185 */       s = String.valueOf(StatCollector.translateToLocal("potion.prefix.grenade").trim()) + " ";
/*     */     }
/*     */     
/* 188 */     List<PotionEffect> list = Items.potionitem.getEffects(stack);
/*     */     
/* 190 */     if (list != null && !list.isEmpty()) {
/* 191 */       String s2 = ((PotionEffect)list.get(0)).getEffectName();
/* 192 */       s2 = String.valueOf(s2) + ".postfix";
/* 193 */       return String.valueOf(s) + StatCollector.translateToLocal(s2).trim();
/*     */     } 
/* 195 */     String s1 = PotionHelper.getPotionPrefix(stack.getMetadata());
/* 196 */     return String.valueOf(StatCollector.translateToLocal(s1).trim()) + " " + super.getItemStackDisplayName(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
/* 205 */     if (stack.getMetadata() != 0) {
/* 206 */       List<PotionEffect> list = Items.potionitem.getEffects(stack);
/* 207 */       HashMultimap hashMultimap = HashMultimap.create();
/*     */       
/* 209 */       if (list != null && !list.isEmpty()) {
/* 210 */         for (PotionEffect potioneffect : list) {
/* 211 */           String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
/* 212 */           Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
/* 213 */           Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();
/*     */           
/* 215 */           if (map != null && map.size() > 0) {
/* 216 */             for (Map.Entry<IAttribute, AttributeModifier> entry : map.entrySet()) {
/* 217 */               AttributeModifier attributemodifier = entry.getValue();
/* 218 */               AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
/* 219 */               hashMultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
/*     */             } 
/*     */           }
/*     */           
/* 223 */           if (potioneffect.getAmplifier() > 0) {
/* 224 */             s1 = String.valueOf(s1) + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
/*     */           }
/*     */           
/* 227 */           if (potioneffect.getDuration() > 20) {
/* 228 */             s1 = String.valueOf(s1) + " (" + Potion.getDurationString(potioneffect) + ")";
/*     */           }
/*     */           
/* 231 */           if (potion.isBadEffect()) {
/* 232 */             tooltip.add(EnumChatFormatting.RED + s1); continue;
/*     */           } 
/* 234 */           tooltip.add(EnumChatFormatting.GRAY + s1);
/*     */         } 
/*     */       } else {
/*     */         
/* 238 */         String s = StatCollector.translateToLocal("potion.empty").trim();
/* 239 */         tooltip.add(EnumChatFormatting.GRAY + s);
/*     */       } 
/*     */       
/* 242 */       if (!hashMultimap.isEmpty()) {
/* 243 */         tooltip.add("");
/* 244 */         tooltip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
/*     */         
/* 246 */         for (Map.Entry<String, AttributeModifier> entry1 : (Iterable<Map.Entry<String, AttributeModifier>>)hashMultimap.entries()) {
/* 247 */           double d1; AttributeModifier attributemodifier2 = entry1.getValue();
/* 248 */           double d0 = attributemodifier2.getAmount();
/*     */ 
/*     */           
/* 251 */           if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2) {
/* 252 */             d1 = attributemodifier2.getAmount();
/*     */           } else {
/* 254 */             d1 = attributemodifier2.getAmount() * 100.0D;
/*     */           } 
/*     */           
/* 257 */           if (d0 > 0.0D) {
/* 258 */             tooltip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey()) })); continue;
/* 259 */           }  if (d0 < 0.0D) {
/* 260 */             d1 *= -1.0D;
/* 261 */             tooltip.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey()) }));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasEffect(ItemStack stack) {
/* 269 */     List<PotionEffect> list = getEffects(stack);
/* 270 */     return (list != null && !list.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 277 */     super.getSubItems(itemIn, tab, subItems);
/*     */     
/* 279 */     if (SUB_ITEMS_CACHE.isEmpty()) {
/* 280 */       for (int i = 0; i <= 15; i++) {
/* 281 */         for (int j = 0; j <= 1; j++) {
/*     */           int lvt_6_1_;
/*     */           
/* 284 */           if (j == 0) {
/* 285 */             lvt_6_1_ = i | 0x2000;
/*     */           } else {
/* 287 */             lvt_6_1_ = i | 0x4000;
/*     */           } 
/*     */           
/* 290 */           for (int l = 0; l <= 2; l++) {
/* 291 */             int i1 = lvt_6_1_;
/*     */             
/* 293 */             if (l != 0) {
/* 294 */               if (l == 1) {
/* 295 */                 i1 = lvt_6_1_ | 0x20;
/* 296 */               } else if (l == 2) {
/* 297 */                 i1 = lvt_6_1_ | 0x40;
/*     */               } 
/*     */             }
/*     */             
/* 301 */             List<PotionEffect> list = PotionHelper.getPotionEffects(i1, false);
/*     */             
/* 303 */             if (list != null && !list.isEmpty()) {
/* 304 */               SUB_ITEMS_CACHE.put(list, Integer.valueOf(i1));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 311 */     Iterator<Integer> iterator = SUB_ITEMS_CACHE.values().iterator();
/*     */     
/* 313 */     while (iterator.hasNext()) {
/* 314 */       int j1 = ((Integer)iterator.next()).intValue();
/* 315 */       subItems.add(new ItemStack(itemIn, 1, j1));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */