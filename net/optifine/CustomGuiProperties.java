/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.config.VillagerProfession;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ import net.optifine.util.StrUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomGuiProperties
/*     */ {
/*  43 */   private String fileName = null;
/*  44 */   private String basePath = null;
/*  45 */   private EnumContainer container = null;
/*  46 */   private Map<ResourceLocation, ResourceLocation> textureLocations = null;
/*  47 */   private NbtTagValue nbtName = null;
/*  48 */   private BiomeGenBase[] biomes = null;
/*  49 */   private RangeListInt heights = null;
/*  50 */   private Boolean large = null;
/*  51 */   private Boolean trapped = null;
/*  52 */   private Boolean christmas = null;
/*  53 */   private Boolean ender = null;
/*  54 */   private RangeListInt levels = null;
/*  55 */   private VillagerProfession[] professions = null;
/*  56 */   private EnumVariant[] variants = null;
/*  57 */   private EnumDyeColor[] colors = null;
/*  58 */   private static final EnumVariant[] VARIANTS_HORSE = new EnumVariant[] { EnumVariant.HORSE, EnumVariant.DONKEY, EnumVariant.MULE, EnumVariant.LLAMA };
/*  59 */   private static final EnumVariant[] VARIANTS_DISPENSER = new EnumVariant[] { EnumVariant.DISPENSER, EnumVariant.DROPPER };
/*  60 */   private static final EnumVariant[] VARIANTS_INVALID = new EnumVariant[0];
/*  61 */   private static final EnumDyeColor[] COLORS_INVALID = new EnumDyeColor[0];
/*  62 */   private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("textures/gui/container/anvil.png");
/*  63 */   private static final ResourceLocation BEACON_GUI_TEXTURE = new ResourceLocation("textures/gui/container/beacon.png");
/*  64 */   private static final ResourceLocation BREWING_STAND_GUI_TEXTURE = new ResourceLocation("textures/gui/container/brewing_stand.png");
/*  65 */   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
/*  66 */   private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");
/*  67 */   private static final ResourceLocation HORSE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/horse.png");
/*  68 */   private static final ResourceLocation DISPENSER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
/*  69 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*  70 */   private static final ResourceLocation FURNACE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/furnace.png");
/*  71 */   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
/*  72 */   private static final ResourceLocation INVENTORY_GUI_TEXTURE = new ResourceLocation("textures/gui/container/inventory.png");
/*  73 */   private static final ResourceLocation SHULKER_BOX_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
/*  74 */   private static final ResourceLocation VILLAGER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/villager.png");
/*     */   
/*     */   public CustomGuiProperties(Properties props, String path) {
/*  77 */     ConnectedParser connectedparser = new ConnectedParser("CustomGuis");
/*  78 */     this.fileName = connectedparser.parseName(path);
/*  79 */     this.basePath = connectedparser.parseBasePath(path);
/*  80 */     this.container = (EnumContainer)connectedparser.parseEnum(props.getProperty("container"), (Enum[])EnumContainer.values(), "container");
/*  81 */     this.textureLocations = parseTextureLocations(props, "texture", this.container, "textures/gui/", this.basePath);
/*  82 */     this.nbtName = connectedparser.parseNbtTagValue("name", props.getProperty("name"));
/*  83 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  84 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*  85 */     this.large = connectedparser.parseBooleanObject(props.getProperty("large"));
/*  86 */     this.trapped = connectedparser.parseBooleanObject(props.getProperty("trapped"));
/*  87 */     this.christmas = connectedparser.parseBooleanObject(props.getProperty("christmas"));
/*  88 */     this.ender = connectedparser.parseBooleanObject(props.getProperty("ender"));
/*  89 */     this.levels = connectedparser.parseRangeListInt(props.getProperty("levels"));
/*  90 */     this.professions = connectedparser.parseProfessions(props.getProperty("professions"));
/*  91 */     EnumVariant[] acustomguiproperties$enumvariant = getContainerVariants(this.container);
/*  92 */     this.variants = (EnumVariant[])connectedparser.parseEnums(props.getProperty("variants"), (Enum[])acustomguiproperties$enumvariant, "variants", (Enum[])VARIANTS_INVALID);
/*  93 */     this.colors = parseEnumDyeColors(props.getProperty("colors"));
/*     */   }
/*     */   
/*     */   private static EnumVariant[] getContainerVariants(EnumContainer cont) {
/*  97 */     return (cont == EnumContainer.HORSE) ? VARIANTS_HORSE : ((cont == EnumContainer.DISPENSER) ? VARIANTS_DISPENSER : new EnumVariant[0]);
/*     */   }
/*     */   
/*     */   private static EnumDyeColor[] parseEnumDyeColors(String str) {
/* 101 */     if (str == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     str = str.toLowerCase();
/* 105 */     String[] astring = Config.tokenize(str, " ");
/* 106 */     EnumDyeColor[] aenumdyecolor = new EnumDyeColor[astring.length];
/*     */     
/* 108 */     for (int i = 0; i < astring.length; i++) {
/* 109 */       String s = astring[i];
/* 110 */       EnumDyeColor enumdyecolor = parseEnumDyeColor(s);
/*     */       
/* 112 */       if (enumdyecolor == null) {
/* 113 */         warn("Invalid color: " + s);
/* 114 */         return COLORS_INVALID;
/*     */       } 
/*     */       
/* 117 */       aenumdyecolor[i] = enumdyecolor;
/*     */     } 
/*     */     
/* 120 */     return aenumdyecolor;
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumDyeColor parseEnumDyeColor(String str) {
/* 125 */     if (str == null) {
/* 126 */       return null;
/*     */     }
/* 128 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/*     */     
/* 130 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/* 131 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/*     */       
/* 133 */       if (enumdyecolor.getName().equals(str)) {
/* 134 */         return enumdyecolor;
/*     */       }
/*     */       
/* 137 */       if (enumdyecolor.getUnlocalizedName().equals(str)) {
/* 138 */         return enumdyecolor;
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation parseTextureLocation(String str, String basePath) {
/* 147 */     if (str == null) {
/* 148 */       return null;
/*     */     }
/* 150 */     str = str.trim();
/* 151 */     String s = TextureUtils.fixResourcePath(str, basePath);
/*     */     
/* 153 */     if (!s.endsWith(".png")) {
/* 154 */       s = String.valueOf(s) + ".png";
/*     */     }
/*     */     
/* 157 */     return new ResourceLocation(String.valueOf(basePath) + "/" + s);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<ResourceLocation, ResourceLocation> parseTextureLocations(Properties props, String property, EnumContainer container, String pathPrefix, String basePath) {
/* 162 */     Map<ResourceLocation, ResourceLocation> map = new HashMap<>();
/* 163 */     String s = props.getProperty(property);
/*     */     
/* 165 */     if (s != null) {
/* 166 */       ResourceLocation resourcelocation = getGuiTextureLocation(container);
/* 167 */       ResourceLocation resourcelocation1 = parseTextureLocation(s, basePath);
/*     */       
/* 169 */       if (resourcelocation != null && resourcelocation1 != null) {
/* 170 */         map.put(resourcelocation, resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 174 */     String s5 = String.valueOf(property) + ".";
/*     */     
/* 176 */     for (Object s10 : props.keySet()) {
/* 177 */       String s1 = (String)s10;
/* 178 */       if (s1.startsWith(s5)) {
/* 179 */         String s2 = s1.substring(s5.length());
/* 180 */         s2 = s2.replace('\\', '/');
/* 181 */         s2 = StrUtils.removePrefixSuffix(s2, "/", ".png");
/* 182 */         String s3 = String.valueOf(pathPrefix) + s2 + ".png";
/* 183 */         String s4 = props.getProperty(s1);
/* 184 */         ResourceLocation resourcelocation2 = new ResourceLocation(s3);
/* 185 */         ResourceLocation resourcelocation3 = parseTextureLocation(s4, basePath);
/* 186 */         map.put(resourcelocation2, resourcelocation3);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     return map;
/*     */   }
/*     */   
/*     */   private static ResourceLocation getGuiTextureLocation(EnumContainer container) {
/* 194 */     if (container == null) {
/* 195 */       return null;
/*     */     }
/* 197 */     switch (container) {
/*     */       case null:
/* 199 */         return ANVIL_GUI_TEXTURE;
/*     */       
/*     */       case BEACON:
/* 202 */         return BEACON_GUI_TEXTURE;
/*     */       
/*     */       case BREWING_STAND:
/* 205 */         return BREWING_STAND_GUI_TEXTURE;
/*     */       
/*     */       case CHEST:
/* 208 */         return CHEST_GUI_TEXTURE;
/*     */       
/*     */       case CRAFTING:
/* 211 */         return CRAFTING_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case CREATIVE:
/* 214 */         return null;
/*     */       
/*     */       case DISPENSER:
/* 217 */         return DISPENSER_GUI_TEXTURE;
/*     */       
/*     */       case ENCHANTMENT:
/* 220 */         return ENCHANTMENT_TABLE_GUI_TEXTURE;
/*     */       
/*     */       case FURNACE:
/* 223 */         return FURNACE_GUI_TEXTURE;
/*     */       
/*     */       case HOPPER:
/* 226 */         return HOPPER_GUI_TEXTURE;
/*     */       
/*     */       case HORSE:
/* 229 */         return HORSE_GUI_TEXTURE;
/*     */       
/*     */       case INVENTORY:
/* 232 */         return INVENTORY_GUI_TEXTURE;
/*     */       
/*     */       case SHULKER_BOX:
/* 235 */         return SHULKER_BOX_GUI_TEXTURE;
/*     */       
/*     */       case VILLAGER:
/* 238 */         return VILLAGER_GUI_TEXTURE;
/*     */     } 
/*     */     
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 247 */     if (this.fileName != null && this.fileName.length() > 0) {
/* 248 */       if (this.basePath == null) {
/* 249 */         warn("No base path found: " + path);
/* 250 */         return false;
/* 251 */       }  if (this.container == null) {
/* 252 */         warn("No container found: " + path);
/* 253 */         return false;
/* 254 */       }  if (this.textureLocations.isEmpty()) {
/* 255 */         warn("No texture found: " + path);
/* 256 */         return false;
/* 257 */       }  if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
/* 258 */         warn("Invalid professions or careers: " + path);
/* 259 */         return false;
/* 260 */       }  if (this.variants == VARIANTS_INVALID) {
/* 261 */         warn("Invalid variants: " + path);
/* 262 */         return false;
/* 263 */       }  if (this.colors == COLORS_INVALID) {
/* 264 */         warn("Invalid colors: " + path);
/* 265 */         return false;
/*     */       } 
/* 267 */       return true;
/*     */     } 
/*     */     
/* 270 */     warn("No name found: " + path);
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String str) {
/* 276 */     Config.warn("[CustomGuis] " + str);
/*     */   }
/*     */   
/*     */   private boolean matchesGeneral(EnumContainer ec, BlockPos pos, IBlockAccess blockAccess) {
/* 280 */     if (this.container != ec) {
/* 281 */       return false;
/*     */     }
/* 283 */     if (this.biomes != null) {
/* 284 */       BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(pos);
/*     */       
/* 286 */       if (!Matches.biome(biomegenbase, this.biomes)) {
/* 287 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 291 */     return !(this.heights != null && !this.heights.isInRange(pos.getY()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesPos(EnumContainer ec, BlockPos pos, IBlockAccess blockAccess, GuiScreen screen) {
/* 296 */     if (!matchesGeneral(ec, pos, blockAccess)) {
/* 297 */       return false;
/*     */     }
/* 299 */     if (this.nbtName != null) {
/* 300 */       String s = getName(screen);
/*     */       
/* 302 */       if (!this.nbtName.matchesValue(s)) {
/* 303 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 307 */     switch (ec) {
/*     */       case BEACON:
/* 309 */         return matchesBeacon(pos, blockAccess);
/*     */       
/*     */       case CHEST:
/* 312 */         return matchesChest(pos, blockAccess);
/*     */       
/*     */       case DISPENSER:
/* 315 */         return matchesDispenser(pos, blockAccess);
/*     */     } 
/*     */     
/* 318 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(GuiScreen screen) {
/* 324 */     IWorldNameable iworldnameable = getWorldNameable(screen);
/* 325 */     return (iworldnameable == null) ? null : iworldnameable.getDisplayName().getUnformattedText();
/*     */   }
/*     */   
/*     */   private static IWorldNameable getWorldNameable(GuiScreen screen) {
/* 329 */     return (screen instanceof net.minecraft.client.gui.inventory.GuiBeacon) ? getWorldNameable(screen, Reflector.GuiBeacon_tileBeacon) : ((screen instanceof net.minecraft.client.gui.inventory.GuiBrewingStand) ? getWorldNameable(screen, Reflector.GuiBrewingStand_tileBrewingStand) : ((screen instanceof net.minecraft.client.gui.inventory.GuiChest) ? getWorldNameable(screen, Reflector.GuiChest_lowerChestInventory) : ((screen instanceof GuiDispenser) ? (IWorldNameable)((GuiDispenser)screen).dispenserInventory : ((screen instanceof net.minecraft.client.gui.GuiEnchantment) ? getWorldNameable(screen, Reflector.GuiEnchantment_nameable) : ((screen instanceof net.minecraft.client.gui.inventory.GuiFurnace) ? getWorldNameable(screen, Reflector.GuiFurnace_tileFurnace) : ((screen instanceof net.minecraft.client.gui.GuiHopper) ? getWorldNameable(screen, Reflector.GuiHopper_hopperInventory) : null))))));
/*     */   }
/*     */   
/*     */   private static IWorldNameable getWorldNameable(GuiScreen screen, ReflectorField fieldInventory) {
/* 333 */     Object object = Reflector.getFieldValue(screen, fieldInventory);
/* 334 */     return !(object instanceof IWorldNameable) ? null : (IWorldNameable)object;
/*     */   }
/*     */   
/*     */   private boolean matchesBeacon(BlockPos pos, IBlockAccess blockAccess) {
/* 338 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 340 */     if (!(tileentity instanceof TileEntityBeacon)) {
/* 341 */       return false;
/*     */     }
/* 343 */     TileEntityBeacon tileentitybeacon = (TileEntityBeacon)tileentity;
/*     */     
/* 345 */     if (this.levels != null) {
/* 346 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 347 */       tileentitybeacon.writeToNBT(nbttagcompound);
/* 348 */       int i = nbttagcompound.getInteger("Levels");
/*     */       
/* 350 */       if (!this.levels.isInRange(i)) {
/* 351 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesChest(BlockPos pos, IBlockAccess blockAccess) {
/* 360 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 362 */     if (tileentity instanceof TileEntityChest) {
/* 363 */       TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 364 */       return matchesChest(tileentitychest, pos, blockAccess);
/* 365 */     }  if (tileentity instanceof TileEntityEnderChest) {
/* 366 */       TileEntityEnderChest tileentityenderchest = (TileEntityEnderChest)tileentity;
/* 367 */       return matchesEnderChest(tileentityenderchest, pos, blockAccess);
/*     */     } 
/* 369 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesChest(TileEntityChest tec, BlockPos pos, IBlockAccess blockAccess) {
/* 374 */     boolean flag = !(tec.adjacentChestXNeg == null && tec.adjacentChestXPos == null && tec.adjacentChestZNeg == null && tec.adjacentChestZPos == null);
/* 375 */     boolean flag1 = (tec.getChestType() == 1);
/* 376 */     boolean flag2 = CustomGuis.isChristmas;
/* 377 */     boolean flag3 = false;
/* 378 */     return matchesChest(flag, flag1, flag2, flag3);
/*     */   }
/*     */   
/*     */   private boolean matchesEnderChest(TileEntityEnderChest teec, BlockPos pos, IBlockAccess blockAccess) {
/* 382 */     return matchesChest(false, false, false, true);
/*     */   }
/*     */   
/*     */   private boolean matchesChest(boolean isLarge, boolean isTrapped, boolean isChristmas, boolean isEnder) {
/* 386 */     return (this.large != null && this.large.booleanValue() != isLarge) ? false : ((this.trapped != null && this.trapped.booleanValue() != isTrapped) ? false : ((this.christmas != null && this.christmas.booleanValue() != isChristmas) ? false : (!(this.ender != null && this.ender.booleanValue() != isEnder))));
/*     */   }
/*     */   
/*     */   private boolean matchesDispenser(BlockPos pos, IBlockAccess blockAccess) {
/* 390 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 392 */     if (!(tileentity instanceof TileEntityDispenser)) {
/* 393 */       return false;
/*     */     }
/* 395 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)tileentity;
/*     */     
/* 397 */     if (this.variants != null) {
/* 398 */       EnumVariant customguiproperties$enumvariant = getDispenserVariant(tileentitydispenser);
/*     */       
/* 400 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants)) {
/* 401 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 405 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumVariant getDispenserVariant(TileEntityDispenser ted) {
/* 410 */     return (ted instanceof net.minecraft.tileentity.TileEntityDropper) ? EnumVariant.DROPPER : EnumVariant.DISPENSER;
/*     */   }
/*     */   
/*     */   public boolean matchesEntity(EnumContainer ec, Entity entity, IBlockAccess blockAccess) {
/* 414 */     if (!matchesGeneral(ec, entity.getPosition(), blockAccess)) {
/* 415 */       return false;
/*     */     }
/* 417 */     if (this.nbtName != null) {
/* 418 */       String s = entity.getName();
/*     */       
/* 420 */       if (!this.nbtName.matchesValue(s)) {
/* 421 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 425 */     switch (ec) {
/*     */       case HORSE:
/* 427 */         return matchesHorse(entity, blockAccess);
/*     */       
/*     */       case VILLAGER:
/* 430 */         return matchesVillager(entity, blockAccess);
/*     */     } 
/*     */     
/* 433 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesVillager(Entity entity, IBlockAccess blockAccess) {
/* 439 */     if (!(entity instanceof EntityVillager)) {
/* 440 */       return false;
/*     */     }
/* 442 */     EntityVillager entityvillager = (EntityVillager)entity;
/*     */     
/* 444 */     if (this.professions != null) {
/* 445 */       int i = entityvillager.getProfession();
/* 446 */       int j = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, -1);
/*     */       
/* 448 */       if (j < 0) {
/* 449 */         return false;
/*     */       }
/*     */       
/* 452 */       boolean flag = false;
/*     */       
/* 454 */       for (int k = 0; k < this.professions.length; k++) {
/* 455 */         VillagerProfession villagerprofession = this.professions[k];
/*     */         
/* 457 */         if (villagerprofession.matches(i, j)) {
/* 458 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 463 */       if (!flag) {
/* 464 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 468 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesHorse(Entity entity, IBlockAccess blockAccess) {
/* 473 */     if (!(entity instanceof EntityHorse)) {
/* 474 */       return false;
/*     */     }
/* 476 */     EntityHorse entityhorse = (EntityHorse)entity;
/*     */     
/* 478 */     if (this.variants != null) {
/* 479 */       EnumVariant customguiproperties$enumvariant = getHorseVariant(entityhorse);
/*     */       
/* 481 */       if (!Config.equalsOne(customguiproperties$enumvariant, (Object[])this.variants)) {
/* 482 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumVariant getHorseVariant(EntityHorse entity) {
/* 491 */     int i = entity.getHorseType();
/*     */     
/* 493 */     switch (i) {
/*     */       case 0:
/* 495 */         return EnumVariant.HORSE;
/*     */       
/*     */       case 1:
/* 498 */         return EnumVariant.DONKEY;
/*     */       
/*     */       case 2:
/* 501 */         return EnumVariant.MULE;
/*     */     } 
/*     */     
/* 504 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumContainer getContainer() {
/* 509 */     return this.container;
/*     */   }
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc) {
/* 513 */     ResourceLocation resourcelocation = this.textureLocations.get(loc);
/* 514 */     return (resourcelocation == null) ? loc : resourcelocation;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 518 */     return "name: " + this.fileName + ", container: " + this.container + ", textures: " + this.textureLocations;
/*     */   }
/*     */   
/*     */   public enum EnumContainer {
/* 522 */     ANVIL,
/* 523 */     BEACON,
/* 524 */     BREWING_STAND,
/* 525 */     CHEST,
/* 526 */     CRAFTING,
/* 527 */     DISPENSER,
/* 528 */     ENCHANTMENT,
/* 529 */     FURNACE,
/* 530 */     HOPPER,
/* 531 */     HORSE,
/* 532 */     VILLAGER,
/* 533 */     SHULKER_BOX,
/* 534 */     CREATIVE,
/* 535 */     INVENTORY; static {
/*     */     
/* 537 */     } public static final EnumContainer[] VALUES = values();
/*     */   }
/*     */   
/*     */   private enum EnumVariant {
/* 541 */     HORSE,
/* 542 */     DONKEY,
/* 543 */     MULE,
/* 544 */     LLAMA,
/* 545 */     DISPENSER,
/* 546 */     DROPPER;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomGuiProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */