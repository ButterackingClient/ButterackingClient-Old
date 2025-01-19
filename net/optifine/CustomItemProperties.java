/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockPart;
/*     */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*     */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*     */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.IParserInt;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.config.ParserEnchantmentId;
/*     */ import net.optifine.config.RangeInt;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.util.StrUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CustomItemProperties
/*     */ {
/*  48 */   public String name = null;
/*  49 */   public String basePath = null;
/*  50 */   public int type = 1;
/*  51 */   public int[] items = null;
/*  52 */   public String texture = null;
/*  53 */   public Map<String, String> mapTextures = null;
/*  54 */   public String model = null;
/*  55 */   public Map<String, String> mapModels = null;
/*  56 */   public RangeListInt damage = null;
/*     */   public boolean damagePercent = false;
/*  58 */   public int damageMask = 0;
/*  59 */   public RangeListInt stackSize = null;
/*  60 */   public RangeListInt enchantmentIds = null;
/*  61 */   public RangeListInt enchantmentLevels = null;
/*  62 */   public NbtTagValue[] nbtTagValues = null;
/*  63 */   public int hand = 0;
/*  64 */   public int blend = 1;
/*  65 */   public float speed = 0.0F;
/*  66 */   public float rotation = 0.0F;
/*  67 */   public int layer = 0;
/*  68 */   public float duration = 1.0F;
/*  69 */   public int weight = 0;
/*  70 */   public ResourceLocation textureLocation = null;
/*  71 */   public Map mapTextureLocations = null;
/*  72 */   public TextureAtlasSprite sprite = null;
/*  73 */   public Map mapSprites = null;
/*  74 */   public IBakedModel bakedModelTexture = null;
/*  75 */   public Map<String, IBakedModel> mapBakedModelsTexture = null;
/*  76 */   public IBakedModel bakedModelFull = null;
/*  77 */   public Map<String, IBakedModel> mapBakedModelsFull = null;
/*  78 */   private int textureWidth = 0;
/*  79 */   private int textureHeight = 0;
/*     */   public static final int TYPE_UNKNOWN = 0;
/*     */   public static final int TYPE_ITEM = 1;
/*     */   public static final int TYPE_ENCHANTMENT = 2;
/*     */   public static final int TYPE_ARMOR = 3;
/*     */   public static final int HAND_ANY = 0;
/*     */   public static final int HAND_MAIN = 1;
/*     */   public static final int HAND_OFF = 2;
/*     */   public static final String INVENTORY = "inventory";
/*     */   
/*     */   public CustomItemProperties(Properties props, String path) {
/*  90 */     this.name = parseName(path);
/*  91 */     this.basePath = parseBasePath(path);
/*  92 */     this.type = parseType(props.getProperty("type"));
/*  93 */     this.items = parseItems(props.getProperty("items"), props.getProperty("matchItems"));
/*  94 */     this.mapModels = parseModels(props, this.basePath);
/*  95 */     this.model = parseModel(props.getProperty("model"), path, this.basePath, this.type, this.mapModels);
/*  96 */     this.mapTextures = parseTextures(props, this.basePath);
/*  97 */     boolean flag = (this.mapModels == null && this.model == null);
/*  98 */     this.texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"), path, this.basePath, this.type, this.mapTextures, flag);
/*  99 */     String s = props.getProperty("damage");
/*     */     
/* 101 */     if (s != null) {
/* 102 */       this.damagePercent = s.contains("%");
/* 103 */       s = s.replace("%", "");
/* 104 */       this.damage = parseRangeListInt(s);
/* 105 */       this.damageMask = parseInt(props.getProperty("damageMask"), 0);
/*     */     } 
/*     */     
/* 108 */     this.stackSize = parseRangeListInt(props.getProperty("stackSize"));
/* 109 */     this.enchantmentIds = parseRangeListInt(props.getProperty("enchantmentIDs"), (IParserInt)new ParserEnchantmentId());
/* 110 */     this.enchantmentLevels = parseRangeListInt(props.getProperty("enchantmentLevels"));
/* 111 */     this.nbtTagValues = parseNbtTagValues(props);
/* 112 */     this.hand = parseHand(props.getProperty("hand"));
/* 113 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/* 114 */     this.speed = parseFloat(props.getProperty("speed"), 0.0F);
/* 115 */     this.rotation = parseFloat(props.getProperty("rotation"), 0.0F);
/* 116 */     this.layer = parseInt(props.getProperty("layer"), 0);
/* 117 */     this.weight = parseInt(props.getProperty("weight"), 0);
/* 118 */     this.duration = parseFloat(props.getProperty("duration"), 1.0F);
/*     */   }
/*     */   
/*     */   private static String parseName(String path) {
/* 122 */     String s = path;
/* 123 */     int i = path.lastIndexOf('/');
/*     */     
/* 125 */     if (i >= 0) {
/* 126 */       s = path.substring(i + 1);
/*     */     }
/*     */     
/* 129 */     int j = s.lastIndexOf('.');
/*     */     
/* 131 */     if (j >= 0) {
/* 132 */       s = s.substring(0, j);
/*     */     }
/*     */     
/* 135 */     return s;
/*     */   }
/*     */   
/*     */   private static String parseBasePath(String path) {
/* 139 */     int i = path.lastIndexOf('/');
/* 140 */     return (i < 0) ? "" : path.substring(0, i);
/*     */   }
/*     */   
/*     */   private int parseType(String str) {
/* 144 */     if (str == null)
/* 145 */       return 1; 
/* 146 */     if (str.equals("item"))
/* 147 */       return 1; 
/* 148 */     if (str.equals("enchantment"))
/* 149 */       return 2; 
/* 150 */     if (str.equals("armor")) {
/* 151 */       return 3;
/*     */     }
/* 153 */     Config.warn("Unknown method: " + str);
/* 154 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] parseItems(String str, String str2) {
/* 159 */     if (str == null) {
/* 160 */       str = str2;
/*     */     }
/*     */     
/* 163 */     if (str == null) {
/* 164 */       return null;
/*     */     }
/* 166 */     str = str.trim();
/* 167 */     Set<Integer> set = new TreeSet();
/* 168 */     String[] astring = Config.tokenize(str, " ");
/*     */ 
/*     */     
/* 171 */     for (int i = 0; i < astring.length; i++) {
/* 172 */       String s = astring[i];
/* 173 */       int j = Config.parseInt(s, -1);
/*     */       
/* 175 */       if (j >= 0) {
/* 176 */         set.add(new Integer(j)); continue;
/*     */       } 
/* 178 */       if (s.contains("-")) {
/* 179 */         String[] astring1 = Config.tokenize(s, "-");
/*     */         
/* 181 */         if (astring1.length == 2) {
/* 182 */           int k = Config.parseInt(astring1[0], -1);
/* 183 */           int l = Config.parseInt(astring1[1], -1);
/*     */           
/* 185 */           if (k >= 0 && l >= 0) {
/* 186 */             int i1 = Math.min(k, l);
/* 187 */             int j1 = Math.max(k, l);
/* 188 */             int k1 = i1;
/*     */ 
/*     */             
/* 191 */             while (k1 <= j1) {
/*     */ 
/*     */ 
/*     */               
/* 195 */               set.add(new Integer(k1));
/* 196 */               k1++;
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 202 */       Item item = Item.getByNameOrId(s);
/*     */       
/* 204 */       if (item == null) {
/* 205 */         Config.warn("Item not found: " + s);
/*     */       } else {
/* 207 */         int i2 = Item.getIdFromItem(item);
/*     */         
/* 209 */         if (i2 <= 0) {
/* 210 */           Config.warn("Item not found: " + s);
/*     */         } else {
/* 212 */           set.add(new Integer(i2));
/*     */         } 
/*     */       } 
/*     */       
/*     */       continue;
/*     */     } 
/* 218 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 219 */     int[] aint = new int[ainteger.length];
/*     */     
/* 221 */     for (int l1 = 0; l1 < aint.length; l1++) {
/* 222 */       aint[l1] = ainteger[l1].intValue();
/*     */     }
/*     */     
/* 225 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String parseTexture(String texStr, String texStr2, String texStr3, String path, String basePath, int type, Map<String, String> mapTexs, boolean textureFromPath) {
/* 230 */     if (texStr == null) {
/* 231 */       texStr = texStr2;
/*     */     }
/*     */     
/* 234 */     if (texStr == null) {
/* 235 */       texStr = texStr3;
/*     */     }
/*     */     
/* 238 */     if (texStr != null) {
/* 239 */       String s2 = ".png";
/*     */       
/* 241 */       if (texStr.endsWith(s2)) {
/* 242 */         texStr = texStr.substring(0, texStr.length() - s2.length());
/*     */       }
/*     */       
/* 245 */       texStr = fixTextureName(texStr, basePath);
/* 246 */       return texStr;
/* 247 */     }  if (type == 3) {
/* 248 */       return null;
/*     */     }
/* 250 */     if (mapTexs != null) {
/* 251 */       String s = mapTexs.get("texture.bow_standby");
/*     */       
/* 253 */       if (s != null) {
/* 254 */         return s;
/*     */       }
/*     */     } 
/*     */     
/* 258 */     if (!textureFromPath) {
/* 259 */       return null;
/*     */     }
/* 261 */     String s1 = path;
/* 262 */     int i = path.lastIndexOf('/');
/*     */     
/* 264 */     if (i >= 0) {
/* 265 */       s1 = path.substring(i + 1);
/*     */     }
/*     */     
/* 268 */     int j = s1.lastIndexOf('.');
/*     */     
/* 270 */     if (j >= 0) {
/* 271 */       s1 = s1.substring(0, j);
/*     */     }
/*     */     
/* 274 */     s1 = fixTextureName(s1, basePath);
/* 275 */     return s1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map parseTextures(Properties props, String basePath) {
/* 281 */     String s = "texture.";
/* 282 */     Map map = getMatchingProperties(props, s);
/*     */     
/* 284 */     if (map.size() <= 0) {
/* 285 */       return null;
/*     */     }
/* 287 */     Set set = map.keySet();
/* 288 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*     */     
/* 290 */     for (Object e : set) {
/* 291 */       String s1 = (String)e;
/* 292 */       String s2 = (String)map.get(s1);
/* 293 */       s2 = fixTextureName(s2, basePath);
/* 294 */       map1.put(s1, s2);
/*     */     } 
/*     */     
/* 297 */     return map1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixTextureName(String iconName, String basePath) {
/* 302 */     iconName = TextureUtils.fixResourcePath(iconName, basePath);
/*     */     
/* 304 */     if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/")) {
/* 305 */       iconName = String.valueOf(basePath) + "/" + iconName;
/*     */     }
/*     */     
/* 308 */     if (iconName.endsWith(".png")) {
/* 309 */       iconName = iconName.substring(0, iconName.length() - 4);
/*     */     }
/*     */     
/* 312 */     if (iconName.startsWith("/")) {
/* 313 */       iconName = iconName.substring(1);
/*     */     }
/*     */     
/* 316 */     return iconName;
/*     */   }
/*     */   
/*     */   private static String parseModel(String modelStr, String path, String basePath, int type, Map<String, String> mapModelNames) {
/* 320 */     if (modelStr != null) {
/* 321 */       String s1 = ".json";
/*     */       
/* 323 */       if (modelStr.endsWith(s1)) {
/* 324 */         modelStr = modelStr.substring(0, modelStr.length() - s1.length());
/*     */       }
/*     */       
/* 327 */       modelStr = fixModelName(modelStr, basePath);
/* 328 */       return modelStr;
/* 329 */     }  if (type == 3) {
/* 330 */       return null;
/*     */     }
/* 332 */     if (mapModelNames != null) {
/* 333 */       String s = mapModelNames.get("model.bow_standby");
/*     */       
/* 335 */       if (s != null) {
/* 336 */         return s;
/*     */       }
/*     */     } 
/*     */     
/* 340 */     return modelStr;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map parseModels(Properties props, String basePath) {
/* 345 */     String s = "model.";
/* 346 */     Map map = getMatchingProperties(props, s);
/*     */     
/* 348 */     if (map.size() <= 0) {
/* 349 */       return null;
/*     */     }
/* 351 */     Set set = map.keySet();
/* 352 */     Map<Object, Object> map1 = new LinkedHashMap<>();
/*     */     
/* 354 */     for (Object e : set) {
/* 355 */       String s1 = (String)e;
/* 356 */       String s2 = (String)map.get(s1);
/* 357 */       s2 = fixModelName(s2, basePath);
/* 358 */       map1.put(s1, s2);
/*     */     } 
/*     */     
/* 361 */     return map1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixModelName(String modelName, String basePath) {
/* 366 */     modelName = TextureUtils.fixResourcePath(modelName, basePath);
/* 367 */     boolean flag = !(!modelName.startsWith("block/") && !modelName.startsWith("item/"));
/*     */     
/* 369 */     if (!modelName.startsWith(basePath) && !flag && !modelName.startsWith("mcpatcher/")) {
/* 370 */       modelName = String.valueOf(basePath) + "/" + modelName;
/*     */     }
/*     */     
/* 373 */     String s = ".json";
/*     */     
/* 375 */     if (modelName.endsWith(s)) {
/* 376 */       modelName = modelName.substring(0, modelName.length() - s.length());
/*     */     }
/*     */     
/* 379 */     if (modelName.startsWith("/")) {
/* 380 */       modelName = modelName.substring(1);
/*     */     }
/*     */     
/* 383 */     return modelName;
/*     */   }
/*     */   
/*     */   private int parseInt(String str, int defVal) {
/* 387 */     if (str == null) {
/* 388 */       return defVal;
/*     */     }
/* 390 */     str = str.trim();
/* 391 */     int i = Config.parseInt(str, -2147483648);
/*     */     
/* 393 */     if (i == Integer.MIN_VALUE) {
/* 394 */       Config.warn("Invalid integer: " + str);
/* 395 */       return defVal;
/*     */     } 
/* 397 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float parseFloat(String str, float defVal) {
/* 403 */     if (str == null) {
/* 404 */       return defVal;
/*     */     }
/* 406 */     str = str.trim();
/* 407 */     float f = Config.parseFloat(str, Float.MIN_VALUE);
/*     */     
/* 409 */     if (f == Float.MIN_VALUE) {
/* 410 */       Config.warn("Invalid float: " + str);
/* 411 */       return defVal;
/*     */     } 
/* 413 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RangeListInt parseRangeListInt(String str) {
/* 419 */     return parseRangeListInt(str, null);
/*     */   }
/*     */   
/*     */   private RangeListInt parseRangeListInt(String str, IParserInt parser) {
/* 423 */     if (str == null) {
/* 424 */       return null;
/*     */     }
/* 426 */     String[] astring = Config.tokenize(str, " ");
/* 427 */     RangeListInt rangelistint = new RangeListInt();
/*     */     
/* 429 */     for (int i = 0; i < astring.length; i++) {
/* 430 */       String s = astring[i];
/*     */       
/* 432 */       if (parser != null) {
/* 433 */         int j = parser.parse(s, -2147483648);
/*     */         
/* 435 */         if (j != Integer.MIN_VALUE) {
/* 436 */           rangelistint.addRange(new RangeInt(j, j));
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 441 */       RangeInt rangeint = parseRangeInt(s);
/*     */       
/* 443 */       if (rangeint == null) {
/* 444 */         Config.warn("Invalid range list: " + str);
/* 445 */         return null;
/*     */       } 
/*     */       
/* 448 */       rangelistint.addRange(rangeint);
/*     */       continue;
/*     */     } 
/* 451 */     return rangelistint;
/*     */   }
/*     */ 
/*     */   
/*     */   private RangeInt parseRangeInt(String str) {
/* 456 */     if (str == null) {
/* 457 */       return null;
/*     */     }
/* 459 */     str = str.trim();
/* 460 */     int i = str.length() - str.replace("-", "").length();
/*     */     
/* 462 */     if (i > 1) {
/* 463 */       Config.warn("Invalid range: " + str);
/* 464 */       return null;
/*     */     } 
/* 466 */     String[] astring = Config.tokenize(str, "- ");
/* 467 */     int[] aint = new int[astring.length];
/*     */     
/* 469 */     for (int j = 0; j < astring.length; j++) {
/* 470 */       String s = astring[j];
/* 471 */       int k = Config.parseInt(s, -1);
/*     */       
/* 473 */       if (k < 0) {
/* 474 */         Config.warn("Invalid range: " + str);
/* 475 */         return null;
/*     */       } 
/*     */       
/* 478 */       aint[j] = k;
/*     */     } 
/*     */     
/* 481 */     if (aint.length == 1) {
/* 482 */       int i1 = aint[0];
/*     */       
/* 484 */       if (str.startsWith("-"))
/* 485 */         return new RangeInt(0, i1); 
/* 486 */       if (str.endsWith("-")) {
/* 487 */         return new RangeInt(i1, 65535);
/*     */       }
/* 489 */       return new RangeInt(i1, i1);
/*     */     } 
/* 491 */     if (aint.length == 2) {
/* 492 */       int l = Math.min(aint[0], aint[1]);
/* 493 */       int j1 = Math.max(aint[0], aint[1]);
/* 494 */       return new RangeInt(l, j1);
/*     */     } 
/* 496 */     Config.warn("Invalid range: " + str);
/* 497 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NbtTagValue[] parseNbtTagValues(Properties props) {
/* 504 */     String s = "nbt.";
/* 505 */     Map map = getMatchingProperties(props, s);
/*     */     
/* 507 */     if (map.size() <= 0) {
/* 508 */       return null;
/*     */     }
/* 510 */     List<NbtTagValue> list = new ArrayList();
/*     */     
/* 512 */     for (Object e : map.keySet()) {
/* 513 */       String s1 = (String)e;
/* 514 */       String s2 = (String)map.get(s1);
/* 515 */       String s3 = s1.substring(s.length());
/* 516 */       NbtTagValue nbttagvalue = new NbtTagValue(s3, s2);
/* 517 */       list.add(nbttagvalue);
/*     */     } 
/*     */     
/* 520 */     NbtTagValue[] anbttagvalue = list.<NbtTagValue>toArray(new NbtTagValue[list.size()]);
/* 521 */     return anbttagvalue;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map getMatchingProperties(Properties props, String keyPrefix) {
/* 526 */     Map<Object, Object> map = new LinkedHashMap<>();
/*     */     
/* 528 */     for (Object e : props.keySet()) {
/* 529 */       String s = (String)e;
/* 530 */       String s1 = props.getProperty(s);
/*     */       
/* 532 */       if (s.startsWith(keyPrefix)) {
/* 533 */         map.put(s, s1);
/*     */       }
/*     */     } 
/*     */     
/* 537 */     return map;
/*     */   }
/*     */   
/*     */   private int parseHand(String str) {
/* 541 */     if (str == null) {
/* 542 */       return 0;
/*     */     }
/* 544 */     str = str.toLowerCase();
/*     */     
/* 546 */     if (str.equals("any"))
/* 547 */       return 0; 
/* 548 */     if (str.equals("main"))
/* 549 */       return 1; 
/* 550 */     if (str.equals("off")) {
/* 551 */       return 2;
/*     */     }
/* 553 */     Config.warn("Invalid hand: " + str);
/* 554 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 560 */     if (this.name != null && this.name.length() > 0) {
/* 561 */       if (this.basePath == null) {
/* 562 */         Config.warn("No base path found: " + path);
/* 563 */         return false;
/* 564 */       }  if (this.type == 0) {
/* 565 */         Config.warn("No type defined: " + path);
/* 566 */         return false;
/*     */       } 
/* 568 */       if (this.type == 1 || this.type == 3) {
/* 569 */         if (this.items == null) {
/* 570 */           this.items = detectItems();
/*     */         }
/*     */         
/* 573 */         if (this.items == null) {
/* 574 */           Config.warn("No items defined: " + path);
/* 575 */           return false;
/*     */         } 
/*     */       } 
/*     */       
/* 579 */       if (this.texture == null && this.mapTextures == null && this.model == null && this.mapModels == null) {
/* 580 */         Config.warn("No texture or model specified: " + path);
/* 581 */         return false;
/* 582 */       }  if (this.type == 2 && this.enchantmentIds == null) {
/* 583 */         Config.warn("No enchantmentIDs specified: " + path);
/* 584 */         return false;
/*     */       } 
/* 586 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 590 */     Config.warn("No name found: " + path);
/* 591 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] detectItems() {
/* 596 */     Item item = Item.getByNameOrId(this.name);
/*     */     
/* 598 */     if (item == null) {
/* 599 */       return null;
/*     */     }
/* 601 */     int i = Item.getIdFromItem(item);
/* 602 */     (new int[1])[0] = i; return (i <= 0) ? null : new int[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateIcons(TextureMap textureMap) {
/* 607 */     if (this.texture != null) {
/* 608 */       this.textureLocation = getTextureLocation(this.texture);
/*     */       
/* 610 */       if (this.type == 1) {
/* 611 */         ResourceLocation resourcelocation = getSpriteLocation(this.textureLocation);
/* 612 */         this.sprite = textureMap.registerSprite(resourcelocation);
/*     */       } 
/*     */     } 
/*     */     
/* 616 */     if (this.mapTextures != null) {
/* 617 */       this.mapTextureLocations = new HashMap<>();
/* 618 */       this.mapSprites = new HashMap<>();
/*     */       
/* 620 */       for (String s : this.mapTextures.keySet()) {
/* 621 */         String s1 = this.mapTextures.get(s);
/* 622 */         ResourceLocation resourcelocation1 = getTextureLocation(s1);
/* 623 */         this.mapTextureLocations.put(s, resourcelocation1);
/*     */         
/* 625 */         if (this.type == 1) {
/* 626 */           ResourceLocation resourcelocation2 = getSpriteLocation(resourcelocation1);
/* 627 */           TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation2);
/* 628 */           this.mapSprites.put(s, textureatlassprite);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ResourceLocation getTextureLocation(String texName) {
/* 635 */     if (texName == null) {
/* 636 */       return null;
/*     */     }
/* 638 */     ResourceLocation resourcelocation = new ResourceLocation(texName);
/* 639 */     String s = resourcelocation.getResourceDomain();
/* 640 */     String s1 = resourcelocation.getResourcePath();
/*     */     
/* 642 */     if (!s1.contains("/")) {
/* 643 */       s1 = "textures/items/" + s1;
/*     */     }
/*     */     
/* 646 */     String s2 = String.valueOf(s1) + ".png";
/* 647 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s2);
/* 648 */     boolean flag = Config.hasResource(resourcelocation1);
/*     */     
/* 650 */     if (!flag) {
/* 651 */       Config.warn("File not found: " + s2);
/*     */     }
/*     */     
/* 654 */     return resourcelocation1;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getSpriteLocation(ResourceLocation resLoc) {
/* 659 */     String s = resLoc.getResourcePath();
/* 660 */     s = StrUtils.removePrefix(s, "textures/");
/* 661 */     s = StrUtils.removeSuffix(s, ".png");
/* 662 */     ResourceLocation resourcelocation = new ResourceLocation(resLoc.getResourceDomain(), s);
/* 663 */     return resourcelocation;
/*     */   }
/*     */   
/*     */   public void updateModelTexture(TextureMap textureMap, ItemModelGenerator itemModelGenerator) {
/* 667 */     if (this.texture != null || this.mapTextures != null) {
/* 668 */       String[] astring = getModelTextures();
/* 669 */       boolean flag = isUseTint();
/* 670 */       this.bakedModelTexture = makeBakedModel(textureMap, itemModelGenerator, astring, flag);
/*     */       
/* 672 */       if (this.type == 1 && this.mapTextures != null) {
/* 673 */         for (String s : this.mapTextures.keySet()) {
/* 674 */           String s1 = this.mapTextures.get(s);
/* 675 */           String s2 = StrUtils.removePrefix(s, "texture.");
/*     */           
/* 677 */           if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield")) {
/* 678 */             String[] astring1 = { s1 };
/* 679 */             IBakedModel ibakedmodel = makeBakedModel(textureMap, itemModelGenerator, astring1, flag);
/*     */             
/* 681 */             if (this.mapBakedModelsTexture == null) {
/* 682 */               this.mapBakedModelsTexture = new HashMap<>();
/*     */             }
/*     */             
/* 685 */             this.mapBakedModelsTexture.put(s2, ibakedmodel);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isUseTint() {
/* 693 */     return true;
/*     */   }
/*     */   
/*     */   private static IBakedModel makeBakedModel(TextureMap textureMap, ItemModelGenerator itemModelGenerator, String[] textures, boolean useTint) {
/* 697 */     String[] astring = new String[textures.length];
/*     */     
/* 699 */     for (int i = 0; i < astring.length; i++) {
/* 700 */       String s = textures[i];
/* 701 */       astring[i] = StrUtils.removePrefix(s, "textures/");
/*     */     } 
/*     */     
/* 704 */     ModelBlock modelblock = makeModelBlock(astring);
/* 705 */     ModelBlock modelblock1 = itemModelGenerator.makeItemModel(textureMap, modelblock);
/* 706 */     IBakedModel ibakedmodel = bakeModel(textureMap, modelblock1, useTint);
/* 707 */     return ibakedmodel;
/*     */   }
/*     */   
/*     */   private String[] getModelTextures() {
/* 711 */     if (this.type == 1 && this.items.length == 1) {
/* 712 */       Item item = Item.getItemById(this.items[0]);
/*     */       
/* 714 */       if (item == Items.potionitem && this.damage != null && this.damage.getCountRanges() > 0) {
/* 715 */         RangeInt rangeint = this.damage.getRange(0);
/* 716 */         int i = rangeint.getMin();
/* 717 */         boolean flag = ((i & 0x4000) != 0);
/* 718 */         String s5 = getMapTexture(this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
/* 719 */         String s6 = null;
/*     */         
/* 721 */         if (flag) {
/* 722 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
/*     */         } else {
/* 724 */           s6 = getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "items/potion_bottle_drinkable");
/*     */         } 
/*     */         
/* 727 */         return new String[] { s5, s6 };
/*     */       } 
/*     */       
/* 730 */       if (item instanceof ItemArmor) {
/* 731 */         ItemArmor itemarmor = (ItemArmor)item;
/*     */         
/* 733 */         if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
/* 734 */           String s = "leather";
/* 735 */           String s1 = "helmet";
/*     */           
/* 737 */           if (itemarmor.armorType == 0) {
/* 738 */             s1 = "helmet";
/*     */           }
/*     */           
/* 741 */           if (itemarmor.armorType == 1) {
/* 742 */             s1 = "chestplate";
/*     */           }
/*     */           
/* 745 */           if (itemarmor.armorType == 2) {
/* 746 */             s1 = "leggings";
/*     */           }
/*     */           
/* 749 */           if (itemarmor.armorType == 3) {
/* 750 */             s1 = "boots";
/*     */           }
/*     */           
/* 753 */           String s2 = String.valueOf(s) + "_" + s1;
/* 754 */           String s3 = getMapTexture(this.mapTextures, "texture." + s2, "items/" + s2);
/* 755 */           String s4 = getMapTexture(this.mapTextures, "texture." + s2 + "_overlay", "items/" + s2 + "_overlay");
/* 756 */           return new String[] { s3, s4 };
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 761 */     return new String[] { this.texture };
/*     */   }
/*     */   
/*     */   private String getMapTexture(Map<String, String> map, String key, String def) {
/* 765 */     if (map == null) {
/* 766 */       return def;
/*     */     }
/* 768 */     String s = map.get(key);
/* 769 */     return (s == null) ? def : s;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ModelBlock makeModelBlock(String[] modelTextures) {
/* 774 */     StringBuffer stringbuffer = new StringBuffer();
/* 775 */     stringbuffer.append("{\"parent\": \"builtin/generated\",\"textures\": {");
/*     */     
/* 777 */     for (int i = 0; i < modelTextures.length; i++) {
/* 778 */       String s = modelTextures[i];
/*     */       
/* 780 */       if (i > 0) {
/* 781 */         stringbuffer.append(", ");
/*     */       }
/*     */       
/* 784 */       stringbuffer.append("\"layer" + i + "\": \"" + s + "\"");
/*     */     } 
/*     */     
/* 787 */     stringbuffer.append("}}");
/* 788 */     String s1 = stringbuffer.toString();
/* 789 */     ModelBlock modelblock = ModelBlock.deserialize(s1);
/* 790 */     return modelblock;
/*     */   }
/*     */   
/*     */   private static IBakedModel bakeModel(TextureMap textureMap, ModelBlock modelBlockIn, boolean useTint) {
/* 794 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/* 795 */     boolean flag = false;
/* 796 */     String s = modelBlockIn.resolveTextureName("particle");
/* 797 */     TextureAtlasSprite textureatlassprite = textureMap.getAtlasSprite((new ResourceLocation(s)).toString());
/* 798 */     SimpleBakedModel.Builder simplebakedmodel$builder = (new SimpleBakedModel.Builder(modelBlockIn)).setTexture(textureatlassprite);
/*     */     
/* 800 */     for (BlockPart blockpart : modelBlockIn.getElements()) {
/* 801 */       for (EnumFacing enumfacing : blockpart.mapFaces.keySet()) {
/* 802 */         BlockPartFace blockpartface = (BlockPartFace)blockpart.mapFaces.get(enumfacing);
/*     */         
/* 804 */         if (!useTint) {
/* 805 */           blockpartface = new BlockPartFace(blockpartface.cullFace, -1, blockpartface.texture, blockpartface.blockFaceUV);
/*     */         }
/*     */         
/* 808 */         String s1 = modelBlockIn.resolveTextureName(blockpartface.texture);
/* 809 */         TextureAtlasSprite textureatlassprite1 = textureMap.getAtlasSprite((new ResourceLocation(s1)).toString());
/* 810 */         BakedQuad bakedquad = makeBakedQuad(blockpart, blockpartface, textureatlassprite1, enumfacing, modelrotation, flag);
/*     */         
/* 812 */         if (blockpartface.cullFace == null) {
/* 813 */           simplebakedmodel$builder.addGeneralQuad(bakedquad); continue;
/*     */         } 
/* 815 */         simplebakedmodel$builder.addFaceQuad(modelrotation.rotateFace(blockpartface.cullFace), bakedquad);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 820 */     return simplebakedmodel$builder.makeBakedModel();
/*     */   }
/*     */   
/*     */   private static BakedQuad makeBakedQuad(BlockPart blockPart, BlockPartFace blockPartFace, TextureAtlasSprite textureAtlasSprite, EnumFacing enumFacing, ModelRotation modelRotation, boolean uvLocked) {
/* 824 */     FaceBakery facebakery = new FaceBakery();
/* 825 */     return facebakery.makeBakedQuad(blockPart.positionFrom, blockPart.positionTo, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.partRotation, uvLocked, blockPart.shade);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 829 */     return this.basePath + "/" + this.name + ", type: " + this.type + ", items: [" + Config.arrayToString(this.items) + "], textture: " + this.texture;
/*     */   }
/*     */   
/*     */   public float getTextureWidth(TextureManager textureManager) {
/* 833 */     if (this.textureWidth <= 0) {
/* 834 */       if (this.textureLocation != null) {
/* 835 */         ITextureObject itextureobject = textureManager.getTexture(this.textureLocation);
/* 836 */         int i = itextureobject.getGlTextureId();
/* 837 */         int j = GlStateManager.getBoundTexture();
/* 838 */         GlStateManager.bindTexture(i);
/* 839 */         this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
/* 840 */         GlStateManager.bindTexture(j);
/*     */       } 
/*     */       
/* 843 */       if (this.textureWidth <= 0) {
/* 844 */         this.textureWidth = 16;
/*     */       }
/*     */     } 
/*     */     
/* 848 */     return this.textureWidth;
/*     */   }
/*     */   
/*     */   public float getTextureHeight(TextureManager textureManager) {
/* 852 */     if (this.textureHeight <= 0) {
/* 853 */       if (this.textureLocation != null) {
/* 854 */         ITextureObject itextureobject = textureManager.getTexture(this.textureLocation);
/* 855 */         int i = itextureobject.getGlTextureId();
/* 856 */         int j = GlStateManager.getBoundTexture();
/* 857 */         GlStateManager.bindTexture(i);
/* 858 */         this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
/* 859 */         GlStateManager.bindTexture(j);
/*     */       } 
/*     */       
/* 862 */       if (this.textureHeight <= 0) {
/* 863 */         this.textureHeight = 16;
/*     */       }
/*     */     } 
/*     */     
/* 867 */     return this.textureHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getBakedModel(ResourceLocation modelLocation, boolean fullModel) {
/*     */     IBakedModel ibakedmodel;
/*     */     Map<String, IBakedModel> map;
/* 874 */     if (fullModel) {
/* 875 */       ibakedmodel = this.bakedModelFull;
/* 876 */       map = this.mapBakedModelsFull;
/*     */     } else {
/* 878 */       ibakedmodel = this.bakedModelTexture;
/* 879 */       map = this.mapBakedModelsTexture;
/*     */     } 
/*     */     
/* 882 */     if (modelLocation != null && map != null) {
/* 883 */       String s = modelLocation.getResourcePath();
/* 884 */       IBakedModel ibakedmodel1 = map.get(s);
/*     */       
/* 886 */       if (ibakedmodel1 != null) {
/* 887 */         return ibakedmodel1;
/*     */       }
/*     */     } 
/*     */     
/* 891 */     return ibakedmodel;
/*     */   }
/*     */   
/*     */   public void loadModels(ModelBakery modelBakery) {
/* 895 */     if (this.model != null) {
/* 896 */       loadItemModel(modelBakery, this.model);
/*     */     }
/*     */     
/* 899 */     if (this.type == 1 && this.mapModels != null) {
/* 900 */       for (String s : this.mapModels.keySet()) {
/* 901 */         String s1 = this.mapModels.get(s);
/* 902 */         String s2 = StrUtils.removePrefix(s, "model.");
/*     */         
/* 904 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield")) {
/* 905 */           loadItemModel(modelBakery, s1);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateModelsFull() {
/* 912 */     ModelManager modelmanager = Config.getModelManager();
/* 913 */     IBakedModel ibakedmodel = modelmanager.getMissingModel();
/*     */     
/* 915 */     if (this.model != null) {
/* 916 */       ResourceLocation resourcelocation = getModelLocation(this.model);
/* 917 */       ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/* 918 */       this.bakedModelFull = modelmanager.getModel(modelresourcelocation);
/*     */       
/* 920 */       if (this.bakedModelFull == ibakedmodel) {
/* 921 */         Config.warn("Custom Items: Model not found " + modelresourcelocation.getResourcePath());
/* 922 */         this.bakedModelFull = null;
/*     */       } 
/*     */     } 
/*     */     
/* 926 */     if (this.type == 1 && this.mapModels != null) {
/* 927 */       for (String s : this.mapModels.keySet()) {
/* 928 */         String s1 = this.mapModels.get(s);
/* 929 */         String s2 = StrUtils.removePrefix(s, "model.");
/*     */         
/* 931 */         if (s2.startsWith("bow") || s2.startsWith("fishing_rod") || s2.startsWith("shield")) {
/* 932 */           ResourceLocation resourcelocation1 = getModelLocation(s1);
/* 933 */           ModelResourceLocation modelresourcelocation1 = new ModelResourceLocation(resourcelocation1, "inventory");
/* 934 */           IBakedModel ibakedmodel1 = modelmanager.getModel(modelresourcelocation1);
/*     */           
/* 936 */           if (ibakedmodel1 == ibakedmodel) {
/* 937 */             Config.warn("Custom Items: Model not found " + modelresourcelocation1.getResourcePath()); continue;
/*     */           } 
/* 939 */           if (this.mapBakedModelsFull == null) {
/* 940 */             this.mapBakedModelsFull = new HashMap<>();
/*     */           }
/*     */           
/* 943 */           this.mapBakedModelsFull.put(s2, ibakedmodel1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadItemModel(ModelBakery modelBakery, String model) {
/* 951 */     ResourceLocation resourcelocation = getModelLocation(model);
/* 952 */     ModelResourceLocation modelresourcelocation = new ModelResourceLocation(resourcelocation, "inventory");
/*     */     
/* 954 */     if (Reflector.ModelLoader.exists()) {
/*     */       try {
/* 956 */         Object object = Reflector.ModelLoader_VanillaLoader_INSTANCE.getValue();
/* 957 */         checkNull(object, "vanillaLoader is null");
/* 958 */         Object object1 = Reflector.call(object, Reflector.ModelLoader_VanillaLoader_loadModel, new Object[] { modelresourcelocation });
/* 959 */         checkNull(object1, "iModel is null");
/* 960 */         Map<ModelResourceLocation, Object> map = (Map)Reflector.getFieldValue(modelBakery, Reflector.ModelLoader_stateModels);
/* 961 */         checkNull(map, "stateModels is null");
/* 962 */         map.put(modelresourcelocation, object1);
/* 963 */         Set set = (Set)Reflector.getFieldValue(modelBakery, Reflector.ModelLoader_textures);
/* 964 */         checkNull(set, "registryTextures is null");
/* 965 */         Collection collection = (Collection)Reflector.call(object1, Reflector.IModel_getTextures, new Object[0]);
/* 966 */         checkNull(collection, "modelTextures is null");
/* 967 */         set.addAll(collection);
/* 968 */       } catch (Exception exception) {
/* 969 */         Config.warn("Error registering model with ModelLoader: " + modelresourcelocation + ", " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */     } else {
/* 972 */       modelBakery.loadItemModel(resourcelocation.toString(), (ResourceLocation)modelresourcelocation, resourcelocation);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkNull(Object obj, String msg) throws NullPointerException {
/* 977 */     if (obj == null) {
/* 978 */       throw new NullPointerException(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   private static ResourceLocation getModelLocation(String modelName) {
/* 983 */     return (Reflector.ModelLoader.exists() && !modelName.startsWith("mcpatcher/") && !modelName.startsWith("optifine/")) ? new ResourceLocation("models/" + modelName) : new ResourceLocation(modelName);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomItemProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */