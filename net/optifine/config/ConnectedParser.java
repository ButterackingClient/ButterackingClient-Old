/*     */ package net.optifine.config;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.ConnectedProperties;
/*     */ import net.optifine.util.EntityUtils;
/*     */ 
/*     */ public class ConnectedParser
/*     */ {
/*  32 */   private String context = null;
/*  33 */   public static final VillagerProfession[] PROFESSIONS_INVALID = new VillagerProfession[0];
/*  34 */   public static final EnumDyeColor[] DYE_COLORS_INVALID = new EnumDyeColor[0];
/*  35 */   private static final INameGetter<Enum> NAME_GETTER_ENUM = new INameGetter<Enum>() {
/*     */       public String getName(Enum en) {
/*  37 */         return en.name();
/*     */       }
/*     */     };
/*  40 */   private static final INameGetter<EnumDyeColor> NAME_GETTER_DYE_COLOR = new INameGetter<EnumDyeColor>() {
/*     */       public String getName(EnumDyeColor col) {
/*  42 */         return col.getName();
/*     */       }
/*     */     };
/*     */   
/*     */   public ConnectedParser(String context) {
/*  47 */     this.context = context;
/*     */   }
/*     */   
/*     */   public String parseName(String path) {
/*  51 */     String s = path;
/*  52 */     int i = path.lastIndexOf('/');
/*     */     
/*  54 */     if (i >= 0) {
/*  55 */       s = path.substring(i + 1);
/*     */     }
/*     */     
/*  58 */     int j = s.lastIndexOf('.');
/*     */     
/*  60 */     if (j >= 0) {
/*  61 */       s = s.substring(0, j);
/*     */     }
/*     */     
/*  64 */     return s;
/*     */   }
/*     */   
/*     */   public String parseBasePath(String path) {
/*  68 */     int i = path.lastIndexOf('/');
/*  69 */     return (i < 0) ? "" : path.substring(0, i);
/*     */   }
/*     */   
/*     */   public MatchBlock[] parseMatchBlocks(String propMatchBlocks) {
/*  73 */     if (propMatchBlocks == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     List list = new ArrayList();
/*  77 */     String[] astring = Config.tokenize(propMatchBlocks, " ");
/*     */     
/*  79 */     for (int i = 0; i < astring.length; i++) {
/*  80 */       String s = astring[i];
/*  81 */       MatchBlock[] amatchblock = parseMatchBlock(s);
/*     */       
/*  83 */       if (amatchblock != null) {
/*  84 */         list.addAll(Arrays.asList(amatchblock));
/*     */       }
/*     */     } 
/*     */     
/*  88 */     MatchBlock[] amatchblock1 = (MatchBlock[])list.toArray((Object[])new MatchBlock[list.size()]);
/*  89 */     return amatchblock1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState parseBlockState(String str, IBlockState def) {
/*  94 */     MatchBlock[] amatchblock = parseMatchBlock(str);
/*     */     
/*  96 */     if (amatchblock == null)
/*  97 */       return def; 
/*  98 */     if (amatchblock.length != 1) {
/*  99 */       return def;
/*     */     }
/* 101 */     MatchBlock matchblock = amatchblock[0];
/* 102 */     int i = matchblock.getBlockId();
/* 103 */     Block block = Block.getBlockById(i);
/* 104 */     return block.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public MatchBlock[] parseMatchBlock(String blockStr) {
/* 109 */     if (blockStr == null) {
/* 110 */       return null;
/*     */     }
/* 112 */     blockStr = blockStr.trim();
/*     */     
/* 114 */     if (blockStr.length() <= 0) {
/* 115 */       return null;
/*     */     }
/* 117 */     String[] astring = Config.tokenize(blockStr, ":");
/* 118 */     String s = "minecraft";
/* 119 */     int i = 0;
/*     */     
/* 121 */     if (astring.length > 1 && isFullBlockName(astring)) {
/* 122 */       s = astring[0];
/* 123 */       i = 1;
/*     */     } else {
/* 125 */       s = "minecraft";
/* 126 */       i = 0;
/*     */     } 
/*     */     
/* 129 */     String s1 = astring[i];
/* 130 */     String[] astring1 = Arrays.<String>copyOfRange(astring, i + 1, astring.length);
/* 131 */     Block[] ablock = parseBlockPart(s, s1);
/*     */     
/* 133 */     if (ablock == null) {
/* 134 */       return null;
/*     */     }
/* 136 */     MatchBlock[] amatchblock = new MatchBlock[ablock.length];
/*     */     
/* 138 */     for (int j = 0; j < ablock.length; j++) {
/* 139 */       Block block = ablock[j];
/* 140 */       int k = Block.getIdFromBlock(block);
/* 141 */       int[] aint = null;
/*     */       
/* 143 */       if (astring1.length > 0) {
/* 144 */         aint = parseBlockMetadatas(block, astring1);
/*     */         
/* 146 */         if (aint == null) {
/* 147 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 151 */       MatchBlock matchblock = new MatchBlock(k, aint);
/* 152 */       amatchblock[j] = matchblock;
/*     */     } 
/*     */     
/* 155 */     return amatchblock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullBlockName(String[] parts) {
/* 162 */     if (parts.length < 2) {
/* 163 */       return false;
/*     */     }
/* 165 */     String s = parts[1];
/* 166 */     return (s.length() < 1) ? false : (startsWithDigit(s) ? false : (!s.contains("=")));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean startsWithDigit(String str) {
/* 171 */     if (str == null)
/* 172 */       return false; 
/* 173 */     if (str.length() < 1) {
/* 174 */       return false;
/*     */     }
/* 176 */     char c0 = str.charAt(0);
/* 177 */     return Character.isDigit(c0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block[] parseBlockPart(String domain, String blockPart) {
/* 182 */     if (startsWithDigit(blockPart)) {
/* 183 */       int[] aint = parseIntList(blockPart);
/*     */       
/* 185 */       if (aint == null) {
/* 186 */         return null;
/*     */       }
/* 188 */       Block[] ablock1 = new Block[aint.length];
/*     */       
/* 190 */       for (int j = 0; j < aint.length; j++) {
/* 191 */         int i = aint[j];
/* 192 */         Block block1 = Block.getBlockById(i);
/*     */         
/* 194 */         if (block1 == null) {
/* 195 */           warn("Block not found for id: " + i);
/* 196 */           return null;
/*     */         } 
/*     */         
/* 199 */         ablock1[j] = block1;
/*     */       } 
/*     */       
/* 202 */       return ablock1;
/*     */     } 
/*     */     
/* 205 */     String s = String.valueOf(domain) + ":" + blockPart;
/* 206 */     Block block = Block.getBlockFromName(s);
/*     */     
/* 208 */     if (block == null) {
/* 209 */       warn("Block not found for name: " + s);
/* 210 */       return null;
/*     */     } 
/* 212 */     Block[] ablock = { block };
/* 213 */     return ablock;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] parseBlockMetadatas(Block block, String[] params) {
/* 219 */     if (params.length <= 0) {
/* 220 */       return null;
/*     */     }
/* 222 */     String s = params[0];
/*     */     
/* 224 */     if (startsWithDigit(s)) {
/* 225 */       int[] aint = parseIntList(s);
/* 226 */       return aint;
/*     */     } 
/* 228 */     IBlockState iblockstate = block.getDefaultState();
/* 229 */     Collection collection = iblockstate.getPropertyNames();
/* 230 */     Map<IProperty, List<Comparable>> map = new HashMap<>();
/*     */     
/* 232 */     for (int i = 0; i < params.length; i++) {
/* 233 */       String s1 = params[i];
/*     */       
/* 235 */       if (s1.length() > 0) {
/* 236 */         String[] astring = Config.tokenize(s1, "=");
/*     */         
/* 238 */         if (astring.length != 2) {
/* 239 */           warn("Invalid block property: " + s1);
/* 240 */           return null;
/*     */         } 
/*     */         
/* 243 */         String s2 = astring[0];
/* 244 */         String s3 = astring[1];
/* 245 */         IProperty iproperty = ConnectedProperties.getProperty(s2, collection);
/*     */         
/* 247 */         if (iproperty == null) {
/* 248 */           warn("Property not found: " + s2 + ", block: " + block);
/* 249 */           return null;
/*     */         } 
/*     */         
/* 252 */         List<Comparable> list = map.get(s2);
/*     */         
/* 254 */         if (list == null) {
/* 255 */           list = new ArrayList<>();
/* 256 */           map.put(iproperty, list);
/*     */         } 
/*     */         
/* 259 */         String[] astring1 = Config.tokenize(s3, ",");
/*     */         
/* 261 */         for (int j = 0; j < astring1.length; j++) {
/* 262 */           String s4 = astring1[j];
/* 263 */           Comparable comparable = parsePropertyValue(iproperty, s4);
/*     */           
/* 265 */           if (comparable == null) {
/* 266 */             warn("Property value not found: " + s4 + ", property: " + s2 + ", block: " + block);
/* 267 */             return null;
/*     */           } 
/*     */           
/* 270 */           list.add(comparable);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 275 */     if (map.isEmpty()) {
/* 276 */       return null;
/*     */     }
/* 278 */     List<Integer> list1 = new ArrayList<>();
/*     */     
/* 280 */     for (int k = 0; k < 16; k++) {
/* 281 */       int l = k;
/*     */       
/*     */       try {
/* 284 */         IBlockState iblockstate1 = getStateFromMeta(block, l);
/*     */         
/* 286 */         if (matchState(iblockstate1, map)) {
/* 287 */           list1.add(Integer.valueOf(l));
/*     */         }
/* 289 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (list1.size() == 16) {
/* 295 */       return null;
/*     */     }
/* 297 */     int[] aint1 = new int[list1.size()];
/*     */     
/* 299 */     for (int i1 = 0; i1 < aint1.length; i1++) {
/* 300 */       aint1[i1] = ((Integer)list1.get(i1)).intValue();
/*     */     }
/*     */     
/* 303 */     return aint1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IBlockState getStateFromMeta(Block block, int md) {
/*     */     try {
/* 312 */       IBlockState iblockstate = block.getStateFromMeta(md);
/*     */       
/* 314 */       if (block == Blocks.double_plant && md > 7) {
/* 315 */         IBlockState iblockstate1 = block.getStateFromMeta(md & 0x7);
/* 316 */         iblockstate = iblockstate.withProperty((IProperty)BlockDoublePlant.VARIANT, iblockstate1.getValue((IProperty)BlockDoublePlant.VARIANT));
/*     */       } 
/*     */       
/* 319 */       return iblockstate;
/* 320 */     } catch (IllegalArgumentException var5) {
/* 321 */       return block.getDefaultState();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Comparable parsePropertyValue(IProperty prop, String valStr) {
/* 326 */     Class oclass = prop.getValueClass();
/* 327 */     Comparable comparable = parseValue(valStr, oclass);
/*     */     
/* 329 */     if (comparable == null) {
/* 330 */       Collection collection = prop.getAllowedValues();
/* 331 */       comparable = getPropertyValue(valStr, collection);
/*     */     } 
/*     */     
/* 334 */     return comparable;
/*     */   }
/*     */   
/*     */   public static Comparable getPropertyValue(String value, Collection propertyValues) {
/* 338 */     for (Object comparable0 : propertyValues) {
/* 339 */       Comparable comparable = (Comparable)comparable0;
/* 340 */       if (getValueName(comparable).equals(value)) {
/* 341 */         return comparable;
/*     */       }
/*     */     } 
/*     */     
/* 345 */     return null;
/*     */   }
/*     */   
/*     */   private static Object getValueName(Comparable obj) {
/* 349 */     if (obj instanceof IStringSerializable) {
/* 350 */       IStringSerializable istringserializable = (IStringSerializable)obj;
/* 351 */       return istringserializable.getName();
/*     */     } 
/* 353 */     return obj.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Comparable parseValue(String str, Class<String> cls) {
/* 358 */     if (cls == String.class)
/* 359 */       return str; 
/* 360 */     if (cls == Boolean.class)
/* 361 */       return Boolean.valueOf(str); 
/* 362 */     if (cls == Float.class)
/* 363 */       return Float.valueOf(str); 
/* 364 */     if (cls == Double.class)
/* 365 */       return Double.valueOf(str); 
/* 366 */     if (cls == Integer.class) {
/* 367 */       return Integer.valueOf(str);
/*     */     }
/* 369 */     return (cls == Long.class) ? Long.valueOf(str) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchState(IBlockState bs, Map<IProperty, List<Comparable>> mapPropValues) {
/* 374 */     for (IProperty iproperty : mapPropValues.keySet()) {
/* 375 */       List<Comparable> list = mapPropValues.get(iproperty);
/* 376 */       Comparable comparable = bs.getValue(iproperty);
/*     */       
/* 378 */       if (comparable == null) {
/* 379 */         return false;
/*     */       }
/*     */       
/* 382 */       if (!list.contains(comparable)) {
/* 383 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 387 */     return true;
/*     */   }
/*     */   
/*     */   public BiomeGenBase[] parseBiomes(String str) {
/* 391 */     if (str == null) {
/* 392 */       return null;
/*     */     }
/* 394 */     str = str.trim();
/* 395 */     boolean flag = false;
/*     */     
/* 397 */     if (str.startsWith("!")) {
/* 398 */       flag = true;
/* 399 */       str = str.substring(1);
/*     */     } 
/*     */     
/* 402 */     String[] astring = Config.tokenize(str, " ");
/* 403 */     List<BiomeGenBase> list = new ArrayList();
/*     */     
/* 405 */     for (int i = 0; i < astring.length; i++) {
/* 406 */       String s = astring[i];
/* 407 */       BiomeGenBase biomegenbase = findBiome(s);
/*     */       
/* 409 */       if (biomegenbase == null) {
/* 410 */         warn("Biome not found: " + s);
/*     */       } else {
/* 412 */         list.add(biomegenbase);
/*     */       } 
/*     */     } 
/*     */     
/* 416 */     if (flag) {
/* 417 */       List<BiomeGenBase> list1 = new ArrayList<>(Arrays.asList(BiomeGenBase.getBiomeGenArray()));
/* 418 */       list1.removeAll(list);
/* 419 */       list = list1;
/*     */     } 
/*     */     
/* 422 */     BiomeGenBase[] abiomegenbase = list.<BiomeGenBase>toArray(new BiomeGenBase[list.size()]);
/* 423 */     return abiomegenbase;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeGenBase findBiome(String biomeName) {
/* 428 */     biomeName = biomeName.toLowerCase();
/*     */     
/* 430 */     if (biomeName.equals("nether")) {
/* 431 */       return BiomeGenBase.hell;
/*     */     }
/* 433 */     BiomeGenBase[] abiomegenbase = BiomeGenBase.getBiomeGenArray();
/*     */     
/* 435 */     for (int i = 0; i < abiomegenbase.length; i++) {
/* 436 */       BiomeGenBase biomegenbase = abiomegenbase[i];
/*     */       
/* 438 */       if (biomegenbase != null) {
/* 439 */         String s = biomegenbase.biomeName.replace(" ", "").toLowerCase();
/*     */         
/* 441 */         if (s.equals(biomeName)) {
/* 442 */           return biomegenbase;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 447 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int parseInt(String str, int defVal) {
/* 452 */     if (str == null) {
/* 453 */       return defVal;
/*     */     }
/* 455 */     str = str.trim();
/* 456 */     int i = Config.parseInt(str, -1);
/*     */     
/* 458 */     if (i < 0) {
/* 459 */       warn("Invalid number: " + str);
/* 460 */       return defVal;
/*     */     } 
/* 462 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] parseIntList(String str) {
/* 468 */     if (str == null) {
/* 469 */       return null;
/*     */     }
/* 471 */     List<Integer> list = new ArrayList<>();
/* 472 */     String[] astring = Config.tokenize(str, " ,");
/*     */     
/* 474 */     for (int i = 0; i < astring.length; i++) {
/* 475 */       String s = astring[i];
/*     */       
/* 477 */       if (s.contains("-")) {
/* 478 */         String[] astring1 = Config.tokenize(s, "-");
/*     */         
/* 480 */         if (astring1.length != 2) {
/* 481 */           warn("Invalid interval: " + s + ", when parsing: " + str);
/*     */         } else {
/* 483 */           int k = Config.parseInt(astring1[0], -1);
/* 484 */           int l = Config.parseInt(astring1[1], -1);
/*     */           
/* 486 */           if (k >= 0 && l >= 0 && k <= l) {
/* 487 */             for (int i1 = k; i1 <= l; i1++) {
/* 488 */               list.add(Integer.valueOf(i1));
/*     */             }
/*     */           } else {
/* 491 */             warn("Invalid interval: " + s + ", when parsing: " + str);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 495 */         int j = Config.parseInt(s, -1);
/*     */         
/* 497 */         if (j < 0) {
/* 498 */           warn("Invalid number: " + s + ", when parsing: " + str);
/*     */         } else {
/* 500 */           list.add(Integer.valueOf(j));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 505 */     int[] aint = new int[list.size()];
/*     */     
/* 507 */     for (int j1 = 0; j1 < aint.length; j1++) {
/* 508 */       aint[j1] = ((Integer)list.get(j1)).intValue();
/*     */     }
/*     */     
/* 511 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] parseFaces(String str, boolean[] defVal) {
/* 516 */     if (str == null) {
/* 517 */       return defVal;
/*     */     }
/* 519 */     EnumSet<EnumFacing> enumset = EnumSet.allOf(EnumFacing.class);
/* 520 */     String[] astring = Config.tokenize(str, " ,");
/*     */     
/* 522 */     for (int i = 0; i < astring.length; i++) {
/* 523 */       String s = astring[i];
/*     */       
/* 525 */       if (s.equals("sides")) {
/* 526 */         enumset.add(EnumFacing.NORTH);
/* 527 */         enumset.add(EnumFacing.SOUTH);
/* 528 */         enumset.add(EnumFacing.WEST);
/* 529 */         enumset.add(EnumFacing.EAST);
/* 530 */       } else if (s.equals("all")) {
/* 531 */         enumset.addAll(Arrays.asList(EnumFacing.VALUES));
/*     */       } else {
/* 533 */         EnumFacing enumfacing = parseFace(s);
/*     */         
/* 535 */         if (enumfacing != null) {
/* 536 */           enumset.add(enumfacing);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 541 */     boolean[] aboolean = new boolean[EnumFacing.VALUES.length];
/*     */     
/* 543 */     for (int j = 0; j < aboolean.length; j++) {
/* 544 */       aboolean[j] = enumset.contains(EnumFacing.VALUES[j]);
/*     */     }
/*     */     
/* 547 */     return aboolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing parseFace(String str) {
/* 552 */     str = str.toLowerCase();
/*     */     
/* 554 */     if (!str.equals("bottom") && !str.equals("down")) {
/* 555 */       if (!str.equals("top") && !str.equals("up")) {
/* 556 */         if (str.equals("north"))
/* 557 */           return EnumFacing.NORTH; 
/* 558 */         if (str.equals("south"))
/* 559 */           return EnumFacing.SOUTH; 
/* 560 */         if (str.equals("east"))
/* 561 */           return EnumFacing.EAST; 
/* 562 */         if (str.equals("west")) {
/* 563 */           return EnumFacing.WEST;
/*     */         }
/* 565 */         Config.warn("Unknown face: " + str);
/* 566 */         return null;
/*     */       } 
/*     */       
/* 569 */       return EnumFacing.UP;
/*     */     } 
/*     */     
/* 572 */     return EnumFacing.DOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dbg(String str) {
/* 577 */     Config.dbg(this.context + ": " + str);
/*     */   }
/*     */   
/*     */   public void warn(String str) {
/* 581 */     Config.warn(this.context + ": " + str);
/*     */   }
/*     */   
/*     */   public RangeListInt parseRangeListInt(String str) {
/* 585 */     if (str == null) {
/* 586 */       return null;
/*     */     }
/* 588 */     RangeListInt rangelistint = new RangeListInt();
/* 589 */     String[] astring = Config.tokenize(str, " ,");
/*     */     
/* 591 */     for (int i = 0; i < astring.length; i++) {
/* 592 */       String s = astring[i];
/* 593 */       RangeInt rangeint = parseRangeInt(s);
/*     */       
/* 595 */       if (rangeint == null) {
/* 596 */         return null;
/*     */       }
/*     */       
/* 599 */       rangelistint.addRange(rangeint);
/*     */     } 
/*     */     
/* 602 */     return rangelistint;
/*     */   }
/*     */ 
/*     */   
/*     */   private RangeInt parseRangeInt(String str) {
/* 607 */     if (str == null)
/* 608 */       return null; 
/* 609 */     if (str.indexOf('-') >= 0) {
/* 610 */       String[] astring = Config.tokenize(str, "-");
/*     */       
/* 612 */       if (astring.length != 2) {
/* 613 */         warn("Invalid range: " + str);
/* 614 */         return null;
/*     */       } 
/* 616 */       int j = Config.parseInt(astring[0], -1);
/* 617 */       int k = Config.parseInt(astring[1], -1);
/*     */       
/* 619 */       if (j >= 0 && k >= 0) {
/* 620 */         return new RangeInt(j, k);
/*     */       }
/* 622 */       warn("Invalid range: " + str);
/* 623 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 627 */     int i = Config.parseInt(str, -1);
/*     */     
/* 629 */     if (i < 0) {
/* 630 */       warn("Invalid integer: " + str);
/* 631 */       return null;
/*     */     } 
/* 633 */     return new RangeInt(i, i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean parseBoolean(String str, boolean defVal) {
/* 639 */     if (str == null) {
/* 640 */       return defVal;
/*     */     }
/* 642 */     String s = str.toLowerCase().trim();
/*     */     
/* 644 */     if (s.equals("true"))
/* 645 */       return true; 
/* 646 */     if (s.equals("false")) {
/* 647 */       return false;
/*     */     }
/* 649 */     warn("Invalid boolean: " + str);
/* 650 */     return defVal;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean parseBooleanObject(String str) {
/* 656 */     if (str == null) {
/* 657 */       return null;
/*     */     }
/* 659 */     String s = str.toLowerCase().trim();
/*     */     
/* 661 */     if (s.equals("true"))
/* 662 */       return Boolean.TRUE; 
/* 663 */     if (s.equals("false")) {
/* 664 */       return Boolean.FALSE;
/*     */     }
/* 666 */     warn("Invalid boolean: " + str);
/* 667 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseColor(String str, int defVal) {
/* 673 */     if (str == null) {
/* 674 */       return defVal;
/*     */     }
/* 676 */     str = str.trim();
/*     */     
/*     */     try {
/* 679 */       int i = Integer.parseInt(str, 16) & 0xFFFFFF;
/* 680 */       return i;
/* 681 */     } catch (NumberFormatException var3) {
/* 682 */       return defVal;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseColor4(String str, int defVal) {
/* 688 */     if (str == null) {
/* 689 */       return defVal;
/*     */     }
/* 691 */     str = str.trim();
/*     */     
/*     */     try {
/* 694 */       int i = (int)(Long.parseLong(str, 16) & 0xFFFFFFFFFFFFFFFFL);
/* 695 */       return i;
/* 696 */     } catch (NumberFormatException var3) {
/* 697 */       return defVal;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer parseBlockRenderLayer(String str, EnumWorldBlockLayer def) {
/* 703 */     if (str == null) {
/* 704 */       return def;
/*     */     }
/* 706 */     str = str.toLowerCase().trim();
/* 707 */     EnumWorldBlockLayer[] aenumworldblocklayer = EnumWorldBlockLayer.values();
/*     */     
/* 709 */     for (int i = 0; i < aenumworldblocklayer.length; i++) {
/* 710 */       EnumWorldBlockLayer enumworldblocklayer = aenumworldblocklayer[i];
/*     */       
/* 712 */       if (str.equals(enumworldblocklayer.name().toLowerCase())) {
/* 713 */         return enumworldblocklayer;
/*     */       }
/*     */     } 
/*     */     
/* 717 */     return def;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T parseObject(String str, Object[] objs, INameGetter<T> nameGetter, String property) {
/* 722 */     if (str == null) {
/* 723 */       return null;
/*     */     }
/* 725 */     String s = str.toLowerCase().trim();
/*     */     
/* 727 */     for (int i = 0; i < objs.length; i++) {
/* 728 */       T t = (T)objs[i];
/* 729 */       String s1 = nameGetter.getName(t);
/*     */       
/* 731 */       if (s1 != null && s1.toLowerCase().equals(s)) {
/* 732 */         return t;
/*     */       }
/*     */     } 
/*     */     
/* 736 */     warn("Invalid " + property + ": " + str);
/* 737 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] parseObjects(String str, Object[] objs, INameGetter nameGetter, String property, Object[] errValue) {
/* 742 */     if (str == null) {
/* 743 */       return null;
/*     */     }
/* 745 */     str = str.toLowerCase().trim();
/* 746 */     String[] astring = Config.tokenize(str, " ");
/* 747 */     Object[] at = (Object[])Array.newInstance(objs.getClass().getComponentType(), astring.length);
/*     */     
/* 749 */     for (int i = 0; i < astring.length; i++) {
/* 750 */       String s = astring[i];
/* 751 */       T t = parseObject(s, (T[])objs, nameGetter, property);
/*     */       
/* 753 */       if (t == null) {
/* 754 */         return (T[])errValue;
/*     */       }
/*     */       
/* 757 */       at[i] = t;
/*     */     } 
/*     */     
/* 760 */     return (T[])at;
/*     */   }
/*     */ 
/*     */   
/*     */   public Enum parseEnum(String str, Enum[] enums, String property) {
/* 765 */     return parseObject(str, enums, NAME_GETTER_ENUM, property);
/*     */   }
/*     */   
/*     */   public Enum[] parseEnums(String str, Enum[] enums, String property, Enum[] errValue) {
/* 769 */     return parseObjects(str, enums, NAME_GETTER_ENUM, property, errValue);
/*     */   }
/*     */   
/*     */   public EnumDyeColor[] parseDyeColors(String str, String property, EnumDyeColor[] errValue) {
/* 773 */     return parseObjects(str, EnumDyeColor.values(), NAME_GETTER_DYE_COLOR, property, errValue);
/*     */   }
/*     */   
/*     */   public Weather[] parseWeather(String str, String property, Weather[] errValue) {
/* 777 */     return parseObjects(str, Weather.values(), NAME_GETTER_ENUM, property, errValue);
/*     */   }
/*     */   
/*     */   public NbtTagValue parseNbtTagValue(String path, String value) {
/* 781 */     return (path != null && value != null) ? new NbtTagValue(path, value) : null;
/*     */   }
/*     */   
/*     */   public VillagerProfession[] parseProfessions(String profStr) {
/* 785 */     if (profStr == null) {
/* 786 */       return null;
/*     */     }
/* 788 */     List<VillagerProfession> list = new ArrayList<>();
/* 789 */     String[] astring = Config.tokenize(profStr, " ");
/*     */     
/* 791 */     for (int i = 0; i < astring.length; i++) {
/* 792 */       String s = astring[i];
/* 793 */       VillagerProfession villagerprofession = parseProfession(s);
/*     */       
/* 795 */       if (villagerprofession == null) {
/* 796 */         warn("Invalid profession: " + s);
/* 797 */         return PROFESSIONS_INVALID;
/*     */       } 
/*     */       
/* 800 */       list.add(villagerprofession);
/*     */     } 
/*     */     
/* 803 */     if (list.isEmpty()) {
/* 804 */       return null;
/*     */     }
/* 806 */     VillagerProfession[] avillagerprofession = list.<VillagerProfession>toArray(new VillagerProfession[list.size()]);
/* 807 */     return avillagerprofession;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private VillagerProfession parseProfession(String str) {
/* 813 */     str = str.toLowerCase();
/* 814 */     String[] astring = Config.tokenize(str, ":");
/*     */     
/* 816 */     if (astring.length > 2) {
/* 817 */       return null;
/*     */     }
/* 819 */     String s = astring[0];
/* 820 */     String s1 = null;
/*     */     
/* 822 */     if (astring.length > 1) {
/* 823 */       s1 = astring[1];
/*     */     }
/*     */     
/* 826 */     int i = parseProfessionId(s);
/*     */     
/* 828 */     if (i < 0) {
/* 829 */       return null;
/*     */     }
/* 831 */     int[] aint = null;
/*     */     
/* 833 */     if (s1 != null) {
/* 834 */       aint = parseCareerIds(i, s1);
/*     */       
/* 836 */       if (aint == null) {
/* 837 */         return null;
/*     */       }
/*     */     } 
/*     */     
/* 841 */     return new VillagerProfession(i, aint);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseProfessionId(String str) {
/* 847 */     int i = Config.parseInt(str, -1);
/* 848 */     return (i >= 0) ? i : (str.equals("farmer") ? 0 : (str.equals("librarian") ? 1 : (str.equals("priest") ? 2 : (str.equals("blacksmith") ? 3 : (str.equals("butcher") ? 4 : (str.equals("nitwit") ? 5 : -1))))));
/*     */   }
/*     */   
/*     */   private static int[] parseCareerIds(int prof, String str) {
/* 852 */     Set<Integer> set = new HashSet<>();
/* 853 */     String[] astring = Config.tokenize(str, ",");
/*     */     
/* 855 */     for (int i = 0; i < astring.length; i++) {
/* 856 */       String s = astring[i];
/* 857 */       int j = parseCareerId(prof, s);
/*     */       
/* 859 */       if (j < 0) {
/* 860 */         return null;
/*     */       }
/*     */       
/* 863 */       set.add(Integer.valueOf(j));
/*     */     } 
/*     */     
/* 866 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 867 */     int[] aint = new int[ainteger.length];
/*     */     
/* 869 */     for (int k = 0; k < aint.length; k++) {
/* 870 */       aint[k] = ainteger[k].intValue();
/*     */     }
/*     */     
/* 873 */     return aint;
/*     */   }
/*     */   
/*     */   private static int parseCareerId(int prof, String str) {
/* 877 */     int i = Config.parseInt(str, -1);
/*     */     
/* 879 */     if (i >= 0) {
/* 880 */       return i;
/*     */     }
/* 882 */     if (prof == 0) {
/* 883 */       if (str.equals("farmer")) {
/* 884 */         return 1;
/*     */       }
/*     */       
/* 887 */       if (str.equals("fisherman")) {
/* 888 */         return 2;
/*     */       }
/*     */       
/* 891 */       if (str.equals("shepherd")) {
/* 892 */         return 3;
/*     */       }
/*     */       
/* 895 */       if (str.equals("fletcher")) {
/* 896 */         return 4;
/*     */       }
/*     */     } 
/*     */     
/* 900 */     if (prof == 1) {
/* 901 */       if (str.equals("librarian")) {
/* 902 */         return 1;
/*     */       }
/*     */       
/* 905 */       if (str.equals("cartographer")) {
/* 906 */         return 2;
/*     */       }
/*     */     } 
/*     */     
/* 910 */     if (prof == 2 && str.equals("cleric")) {
/* 911 */       return 1;
/*     */     }
/* 913 */     if (prof == 3) {
/* 914 */       if (str.equals("armor")) {
/* 915 */         return 1;
/*     */       }
/*     */       
/* 918 */       if (str.equals("weapon")) {
/* 919 */         return 2;
/*     */       }
/*     */       
/* 922 */       if (str.equals("tool")) {
/* 923 */         return 3;
/*     */       }
/*     */     } 
/*     */     
/* 927 */     if (prof == 4) {
/* 928 */       if (str.equals("butcher")) {
/* 929 */         return 1;
/*     */       }
/*     */       
/* 932 */       if (str.equals("leather")) {
/* 933 */         return 2;
/*     */       }
/*     */     } 
/*     */     
/* 937 */     return (prof == 5 && str.equals("nitwit")) ? 1 : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] parseItems(String str) {
/* 943 */     str = str.trim();
/* 944 */     Set<Integer> set = new TreeSet<>();
/* 945 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 947 */     for (int i = 0; i < astring.length; i++) {
/* 948 */       String s = astring[i];
/* 949 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 950 */       Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
/*     */       
/* 952 */       if (item == null) {
/* 953 */         warn("Item not found: " + s);
/*     */       } else {
/* 955 */         int j = Item.getIdFromItem(item);
/*     */         
/* 957 */         if (j < 0) {
/* 958 */           warn("Item has no ID: " + item + ", name: " + s);
/*     */         } else {
/* 960 */           set.add(new Integer(j));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 965 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 966 */     int[] aint = Config.toPrimitive(ainteger);
/* 967 */     return aint;
/*     */   }
/*     */   
/*     */   public int[] parseEntities(String str) {
/* 971 */     str = str.trim();
/* 972 */     Set<Integer> set = new TreeSet<>();
/* 973 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 975 */     for (int i = 0; i < astring.length; i++) {
/* 976 */       String s = astring[i];
/* 977 */       int j = EntityUtils.getEntityIdByName(s);
/*     */       
/* 979 */       if (j < 0) {
/* 980 */         warn("Entity not found: " + s);
/*     */       } else {
/* 982 */         set.add(new Integer(j));
/*     */       } 
/*     */     } 
/*     */     
/* 986 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 987 */     int[] aint = Config.toPrimitive(ainteger);
/* 988 */     return aint;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\ConnectedParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */