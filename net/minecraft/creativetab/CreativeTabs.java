/*     */ package net.minecraft.creativetab;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public abstract class CreativeTabs {
/*  15 */   public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
/*  16 */   public static final CreativeTabs tabBlock = new CreativeTabs(0, "buildingBlocks") {
/*     */       public Item getTabIconItem() {
/*  18 */         return Item.getItemFromBlock(Blocks.brick_block);
/*     */       }
/*     */     };
/*  21 */   public static final CreativeTabs tabDecorations = new CreativeTabs(1, "decorations") {
/*     */       public Item getTabIconItem() {
/*  23 */         return Item.getItemFromBlock((Block)Blocks.double_plant);
/*     */       }
/*     */       
/*     */       public int getIconItemDamage() {
/*  27 */         return BlockDoublePlant.EnumPlantType.PAEONIA.getMeta();
/*     */       }
/*     */     };
/*  30 */   public static final CreativeTabs tabRedstone = new CreativeTabs(2, "redstone") {
/*     */       public Item getTabIconItem() {
/*  32 */         return Items.redstone;
/*     */       }
/*     */     };
/*  35 */   public static final CreativeTabs tabTransport = new CreativeTabs(3, "transportation") {
/*     */       public Item getTabIconItem() {
/*  37 */         return Item.getItemFromBlock(Blocks.golden_rail);
/*     */       }
/*     */     };
/*  40 */   public static final CreativeTabs tabMisc = (new CreativeTabs(4, "misc") {
/*     */       public Item getTabIconItem() {
/*  42 */         return Items.lava_bucket;
/*     */       }
/*  44 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.ALL });
/*  45 */   public static final CreativeTabs tabAllSearch = (new CreativeTabs(5, "search") {
/*     */       public Item getTabIconItem() {
/*  47 */         return Items.compass;
/*     */       }
/*  49 */     }).setBackgroundImageName("item_search.png");
/*  50 */   public static final CreativeTabs tabFood = new CreativeTabs(6, "food") {
/*     */       public Item getTabIconItem() {
/*  52 */         return Items.apple;
/*     */       }
/*     */     };
/*  55 */   public static final CreativeTabs tabTools = (new CreativeTabs(7, "tools") {
/*     */       public Item getTabIconItem() {
/*  57 */         return Items.iron_axe;
/*     */       }
/*  59 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE });
/*  60 */   public static final CreativeTabs tabCombat = (new CreativeTabs(8, "combat") {
/*     */       public Item getTabIconItem() {
/*  62 */         return Items.golden_sword;
/*     */       }
/*  64 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON });
/*  65 */   public static final CreativeTabs tabBrewing = new CreativeTabs(9, "brewing") {
/*     */       public Item getTabIconItem() {
/*  67 */         return (Item)Items.potionitem;
/*     */       }
/*     */     };
/*  70 */   public static final CreativeTabs tabMaterials = new CreativeTabs(10, "materials") {
/*     */       public Item getTabIconItem() {
/*  72 */         return Items.stick;
/*     */       }
/*     */     };
/*  75 */   public static final CreativeTabs tabInventory = (new CreativeTabs(11, "inventory") {
/*     */       public Item getTabIconItem() {
/*  77 */         return Item.getItemFromBlock((Block)Blocks.chest);
/*     */       }
/*  79 */     }).setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
/*     */ 
/*     */   
/*     */   private final int tabIndex;
/*     */   
/*     */   private final String tabLabel;
/*     */   
/*  86 */   private String theTexture = "items.png";
/*     */   
/*     */   private boolean hasScrollbar = true;
/*     */   
/*     */   private boolean drawTitle = true;
/*     */   
/*     */   private EnumEnchantmentType[] enchantmentTypes;
/*     */   
/*     */   private ItemStack iconItemStack;
/*     */   
/*     */   public CreativeTabs(int index, String label) {
/*  97 */     this.tabIndex = index;
/*  98 */     this.tabLabel = label;
/*  99 */     creativeTabArray[index] = this;
/*     */   }
/*     */   
/*     */   public int getTabIndex() {
/* 103 */     return this.tabIndex;
/*     */   }
/*     */   
/*     */   public String getTabLabel() {
/* 107 */     return this.tabLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedTabLabel() {
/* 114 */     return "itemGroup." + getTabLabel();
/*     */   }
/*     */   
/*     */   public ItemStack getIconItemStack() {
/* 118 */     if (this.iconItemStack == null) {
/* 119 */       this.iconItemStack = new ItemStack(getTabIconItem(), 1, getIconItemDamage());
/*     */     }
/*     */     
/* 122 */     return this.iconItemStack;
/*     */   }
/*     */   
/*     */   public abstract Item getTabIconItem();
/*     */   
/*     */   public int getIconItemDamage() {
/* 128 */     return 0;
/*     */   }
/*     */   
/*     */   public String getBackgroundImageName() {
/* 132 */     return this.theTexture;
/*     */   }
/*     */   
/*     */   public CreativeTabs setBackgroundImageName(String texture) {
/* 136 */     this.theTexture = texture;
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public boolean drawInForegroundOfTab() {
/* 141 */     return this.drawTitle;
/*     */   }
/*     */   
/*     */   public CreativeTabs setNoTitle() {
/* 145 */     this.drawTitle = false;
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   public boolean shouldHidePlayerInventory() {
/* 150 */     return this.hasScrollbar;
/*     */   }
/*     */   
/*     */   public CreativeTabs setNoScrollbar() {
/* 154 */     this.hasScrollbar = false;
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabColumn() {
/* 162 */     return this.tabIndex % 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTabInFirstRow() {
/* 169 */     return (this.tabIndex < 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
/* 176 */     return this.enchantmentTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... types) {
/* 183 */     this.enchantmentTypes = types;
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public boolean hasRelevantEnchantmentType(EnumEnchantmentType enchantmentType) {
/* 188 */     if (this.enchantmentTypes == null)
/* 189 */       return false;  byte b; int i;
/*     */     EnumEnchantmentType[] arrayOfEnumEnchantmentType;
/* 191 */     for (i = (arrayOfEnumEnchantmentType = this.enchantmentTypes).length, b = 0; b < i; ) { EnumEnchantmentType enumenchantmenttype = arrayOfEnumEnchantmentType[b];
/* 192 */       if (enumenchantmenttype == enchantmentType) {
/* 193 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayAllReleventItems(List<ItemStack> p_78018_1_) {
/* 205 */     for (Item item : Item.itemRegistry) {
/* 206 */       if (item != null && item.getCreativeTab() == this) {
/* 207 */         item.getSubItems(item, this, p_78018_1_);
/*     */       }
/*     */     } 
/*     */     
/* 211 */     if (getRelevantEnchantmentTypes() != null) {
/* 212 */       addEnchantmentBooksToList(p_78018_1_, getRelevantEnchantmentTypes());
/*     */     }
/*     */   }
/*     */   
/*     */   public void addEnchantmentBooksToList(List<ItemStack> itemList, EnumEnchantmentType... enchantmentType) {
/*     */     byte b;
/*     */     int i;
/*     */     Enchantment[] arrayOfEnchantment;
/* 220 */     for (i = (arrayOfEnchantment = Enchantment.enchantmentsBookList).length, b = 0; b < i; ) { Enchantment enchantment = arrayOfEnchantment[b];
/* 221 */       if (enchantment != null && enchantment.type != null) {
/* 222 */         boolean flag = false;
/*     */         
/* 224 */         for (int j = 0; j < enchantmentType.length && !flag; j++) {
/* 225 */           if (enchantment.type == enchantmentType[j]) {
/* 226 */             flag = true;
/*     */           }
/*     */         } 
/*     */         
/* 230 */         if (flag)
/* 231 */           itemList.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, enchantment.getMaxLevel()))); 
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\creativetab\CreativeTabs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */