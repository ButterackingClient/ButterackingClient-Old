/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityBanner
/*     */   extends TileEntity
/*     */ {
/*     */   private int baseColor;
/*     */   private NBTTagList patterns;
/*     */   private boolean field_175119_g;
/*     */   private List<EnumBannerPattern> patternList;
/*     */   private List<EnumDyeColor> colorList;
/*     */   private String patternResourceLocation;
/*     */   
/*     */   public void setItemValues(ItemStack stack) {
/*  34 */     this.patterns = null;
/*     */     
/*  36 */     if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*  37 */       NBTTagCompound nbttagcompound = stack.getTagCompound().getCompoundTag("BlockEntityTag");
/*     */       
/*  39 */       if (nbttagcompound.hasKey("Patterns")) {
/*  40 */         this.patterns = (NBTTagList)nbttagcompound.getTagList("Patterns", 10).copy();
/*     */       }
/*     */       
/*  43 */       if (nbttagcompound.hasKey("Base", 99)) {
/*  44 */         this.baseColor = nbttagcompound.getInteger("Base");
/*     */       } else {
/*  46 */         this.baseColor = stack.getMetadata() & 0xF;
/*     */       } 
/*     */     } else {
/*  49 */       this.baseColor = stack.getMetadata() & 0xF;
/*     */     } 
/*     */     
/*  52 */     this.patternList = null;
/*  53 */     this.colorList = null;
/*  54 */     this.patternResourceLocation = "";
/*  55 */     this.field_175119_g = true;
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  59 */     super.writeToNBT(compound);
/*  60 */     setBaseColorAndPatterns(compound, this.baseColor, this.patterns);
/*     */   }
/*     */   
/*     */   public static void setBaseColorAndPatterns(NBTTagCompound compound, int baseColorIn, NBTTagList patternsIn) {
/*  64 */     compound.setInteger("Base", baseColorIn);
/*     */     
/*  66 */     if (patternsIn != null) {
/*  67 */       compound.setTag("Patterns", (NBTBase)patternsIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  72 */     super.readFromNBT(compound);
/*  73 */     this.baseColor = compound.getInteger("Base");
/*  74 */     this.patterns = compound.getTagList("Patterns", 10);
/*  75 */     this.patternList = null;
/*  76 */     this.colorList = null;
/*  77 */     this.patternResourceLocation = null;
/*  78 */     this.field_175119_g = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/*  86 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  87 */     writeToNBT(nbttagcompound);
/*  88 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 6, nbttagcompound);
/*     */   }
/*     */   
/*     */   public int getBaseColor() {
/*  92 */     return this.baseColor;
/*     */   }
/*     */   
/*     */   public static int getBaseColor(ItemStack stack) {
/*  96 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/*  97 */     return (nbttagcompound != null && nbttagcompound.hasKey("Base")) ? nbttagcompound.getInteger("Base") : stack.getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPatterns(ItemStack stack) {
/* 104 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/* 105 */     return (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) ? nbttagcompound.getTagList("Patterns", 10).tagCount() : 0;
/*     */   }
/*     */   
/*     */   public List<EnumBannerPattern> getPatternList() {
/* 109 */     initializeBannerData();
/* 110 */     return this.patternList;
/*     */   }
/*     */   
/*     */   public NBTTagList getPatterns() {
/* 114 */     return this.patterns;
/*     */   }
/*     */   
/*     */   public List<EnumDyeColor> getColorList() {
/* 118 */     initializeBannerData();
/* 119 */     return this.colorList;
/*     */   }
/*     */   
/*     */   public String getPatternResourceLocation() {
/* 123 */     initializeBannerData();
/* 124 */     return this.patternResourceLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeBannerData() {
/* 132 */     if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null) {
/* 133 */       if (!this.field_175119_g) {
/* 134 */         this.patternResourceLocation = "";
/*     */       } else {
/* 136 */         this.patternList = Lists.newArrayList();
/* 137 */         this.colorList = Lists.newArrayList();
/* 138 */         this.patternList.add(EnumBannerPattern.BASE);
/* 139 */         this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
/* 140 */         this.patternResourceLocation = "b" + this.baseColor;
/*     */         
/* 142 */         if (this.patterns != null) {
/* 143 */           for (int i = 0; i < this.patterns.tagCount(); i++) {
/* 144 */             NBTTagCompound nbttagcompound = this.patterns.getCompoundTagAt(i);
/* 145 */             EnumBannerPattern tileentitybanner$enumbannerpattern = EnumBannerPattern.getPatternByID(nbttagcompound.getString("Pattern"));
/*     */             
/* 147 */             if (tileentitybanner$enumbannerpattern != null) {
/* 148 */               this.patternList.add(tileentitybanner$enumbannerpattern);
/* 149 */               int j = nbttagcompound.getInteger("Color");
/* 150 */               this.colorList.add(EnumDyeColor.byDyeDamage(j));
/* 151 */               this.patternResourceLocation = String.valueOf(this.patternResourceLocation) + tileentitybanner$enumbannerpattern.getPatternID() + j;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeBannerData(ItemStack stack) {
/* 163 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag", false);
/*     */     
/* 165 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9)) {
/* 166 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/* 168 */       if (nbttaglist.tagCount() > 0) {
/* 169 */         nbttaglist.removeTag(nbttaglist.tagCount() - 1);
/*     */         
/* 171 */         if (nbttaglist.hasNoTags()) {
/* 172 */           stack.getTagCompound().removeTag("BlockEntityTag");
/*     */           
/* 174 */           if (stack.getTagCompound().hasNoTags())
/* 175 */             stack.setTagCompound(null); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum EnumBannerPattern
/*     */   {
/* 183 */     BASE("base", "b"),
/* 184 */     SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
/* 185 */     SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
/* 186 */     SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
/* 187 */     SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
/* 188 */     STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
/* 189 */     STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
/* 190 */     STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
/* 191 */     STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
/* 192 */     STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
/* 193 */     STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
/* 194 */     STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
/* 195 */     STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
/* 196 */     STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
/* 197 */     CROSS("cross", "cr", "# #", " # ", "# #"),
/* 198 */     STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
/* 199 */     TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
/* 200 */     TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
/* 201 */     TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
/* 202 */     TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
/* 203 */     DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
/* 204 */     DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
/* 205 */     DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
/* 206 */     DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
/* 207 */     CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
/* 208 */     RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
/* 209 */     HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
/* 210 */     HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
/* 211 */     HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
/* 212 */     HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
/* 213 */     BORDER("border", "bo", "###", "# #", "###"),
/* 214 */     CURLY_BORDER("curly_border", "cbo", (String)new ItemStack(Blocks.vine)),
/* 215 */     CREEPER("creeper", "cre", (String)new ItemStack(Items.skull, 1, 4)),
/* 216 */     GRADIENT("gradient", "gra", "# #", " # ", " # "),
/* 217 */     GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
/* 218 */     BRICKS("bricks", "bri", (String)new ItemStack(Blocks.brick_block)),
/* 219 */     SKULL("skull", "sku", (String)new ItemStack(Items.skull, 1, 1)),
/* 220 */     FLOWER("flower", "flo", (String)new ItemStack((Block)Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())),
/* 221 */     MOJANG("mojang", "moj", (String)new ItemStack(Items.golden_apple, 1, 1));
/*     */     
/*     */     private String patternName;
/*     */     private String patternID;
/*     */     private String[] craftingLayers;
/*     */     private ItemStack patternCraftingStack;
/*     */     
/*     */     EnumBannerPattern(String name, String id) {
/* 229 */       this.craftingLayers = new String[3];
/* 230 */       this.patternName = name;
/* 231 */       this.patternID = id;
/*     */     }
/*     */ 
/*     */     
/*     */     EnumBannerPattern(String name, String id, ItemStack craftingItem) {
/* 236 */       this.patternCraftingStack = craftingItem;
/*     */     }
/*     */ 
/*     */     
/*     */     EnumBannerPattern(String name, String id, String craftingTop, String craftingMid, String craftingBot) {
/* 241 */       this.craftingLayers[0] = craftingTop;
/* 242 */       this.craftingLayers[1] = craftingMid;
/* 243 */       this.craftingLayers[2] = craftingBot;
/*     */     }
/*     */     
/*     */     public String getPatternName() {
/* 247 */       return this.patternName;
/*     */     }
/*     */     
/*     */     public String getPatternID() {
/* 251 */       return this.patternID;
/*     */     }
/*     */     
/*     */     public String[] getCraftingLayers() {
/* 255 */       return this.craftingLayers;
/*     */     }
/*     */     
/*     */     public boolean hasValidCrafting() {
/* 259 */       return !(this.patternCraftingStack == null && this.craftingLayers[0] == null);
/*     */     }
/*     */     
/*     */     public boolean hasCraftingStack() {
/* 263 */       return (this.patternCraftingStack != null);
/*     */     }
/*     */     
/*     */     public ItemStack getCraftingStack() {
/* 267 */       return this.patternCraftingStack; } public static EnumBannerPattern getPatternByID(String id) {
/*     */       byte b;
/*     */       int i;
/*     */       EnumBannerPattern[] arrayOfEnumBannerPattern;
/* 271 */       for (i = (arrayOfEnumBannerPattern = values()).length, b = 0; b < i; ) { EnumBannerPattern tileentitybanner$enumbannerpattern = arrayOfEnumBannerPattern[b];
/* 272 */         if (tileentitybanner$enumbannerpattern.patternID.equals(id)) {
/* 273 */           return tileentitybanner$enumbannerpattern;
/*     */         }
/*     */         b++; }
/*     */       
/* 277 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */