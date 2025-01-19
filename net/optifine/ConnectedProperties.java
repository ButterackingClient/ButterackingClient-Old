/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.config.RangeInt;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.util.MathUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class ConnectedProperties
/*     */ {
/*  30 */   public String name = null;
/*  31 */   public String basePath = null;
/*  32 */   public MatchBlock[] matchBlocks = null;
/*  33 */   public int[] metadatas = null;
/*  34 */   public String[] matchTiles = null;
/*  35 */   public int method = 0;
/*  36 */   public String[] tiles = null;
/*  37 */   public int connect = 0;
/*  38 */   public int faces = 63;
/*  39 */   public BiomeGenBase[] biomes = null;
/*  40 */   public RangeListInt heights = null;
/*  41 */   public int renderPass = 0;
/*     */   public boolean innerSeams = false;
/*  43 */   public int[] ctmTileIndexes = null;
/*  44 */   public int width = 0;
/*  45 */   public int height = 0;
/*  46 */   public int[] weights = null;
/*  47 */   public int randomLoops = 0;
/*  48 */   public int symmetry = 1;
/*     */   public boolean linked = false;
/*  50 */   public NbtTagValue nbtName = null;
/*  51 */   public int[] sumWeights = null;
/*  52 */   public int sumAllWeights = 1;
/*  53 */   public TextureAtlasSprite[] matchTileIcons = null;
/*  54 */   public TextureAtlasSprite[] tileIcons = null;
/*  55 */   public MatchBlock[] connectBlocks = null;
/*  56 */   public String[] connectTiles = null;
/*  57 */   public TextureAtlasSprite[] connectTileIcons = null;
/*  58 */   public int tintIndex = -1;
/*  59 */   public IBlockState tintBlockState = Blocks.air.getDefaultState();
/*  60 */   public EnumWorldBlockLayer layer = null;
/*     */   public static final int METHOD_NONE = 0;
/*     */   public static final int METHOD_CTM = 1;
/*     */   public static final int METHOD_HORIZONTAL = 2;
/*     */   public static final int METHOD_TOP = 3;
/*     */   public static final int METHOD_RANDOM = 4;
/*     */   public static final int METHOD_REPEAT = 5;
/*     */   public static final int METHOD_VERTICAL = 6;
/*     */   public static final int METHOD_FIXED = 7;
/*     */   public static final int METHOD_HORIZONTAL_VERTICAL = 8;
/*     */   public static final int METHOD_VERTICAL_HORIZONTAL = 9;
/*     */   public static final int METHOD_CTM_COMPACT = 10;
/*     */   public static final int METHOD_OVERLAY = 11;
/*     */   public static final int METHOD_OVERLAY_FIXED = 12;
/*     */   public static final int METHOD_OVERLAY_RANDOM = 13;
/*     */   public static final int METHOD_OVERLAY_REPEAT = 14;
/*     */   public static final int METHOD_OVERLAY_CTM = 15;
/*     */   public static final int CONNECT_NONE = 0;
/*     */   public static final int CONNECT_BLOCK = 1;
/*     */   public static final int CONNECT_TILE = 2;
/*     */   public static final int CONNECT_MATERIAL = 3;
/*     */   public static final int CONNECT_UNKNOWN = 128;
/*     */   public static final int FACE_BOTTOM = 1;
/*     */   public static final int FACE_TOP = 2;
/*     */   public static final int FACE_NORTH = 4;
/*     */   public static final int FACE_SOUTH = 8;
/*     */   public static final int FACE_WEST = 16;
/*     */   public static final int FACE_EAST = 32;
/*     */   public static final int FACE_SIDES = 60;
/*     */   public static final int FACE_ALL = 63;
/*     */   public static final int FACE_UNKNOWN = 128;
/*     */   public static final int SYMMETRY_NONE = 1;
/*     */   public static final int SYMMETRY_OPPOSITE = 2;
/*     */   public static final int SYMMETRY_ALL = 6;
/*     */   public static final int SYMMETRY_UNKNOWN = 128;
/*     */   public static final String TILE_SKIP_PNG = "<skip>.png";
/*     */   public static final String TILE_DEFAULT_PNG = "<default>.png";
/*     */   
/*     */   public ConnectedProperties(Properties props, String path) {
/*  99 */     ConnectedParser connectedparser = new ConnectedParser("ConnectedTextures");
/* 100 */     this.name = connectedparser.parseName(path);
/* 101 */     this.basePath = connectedparser.parseBasePath(path);
/* 102 */     this.matchBlocks = connectedparser.parseMatchBlocks(props.getProperty("matchBlocks"));
/* 103 */     this.metadatas = connectedparser.parseIntList(props.getProperty("metadata"));
/* 104 */     this.matchTiles = parseMatchTiles(props.getProperty("matchTiles"));
/* 105 */     this.method = parseMethod(props.getProperty("method"));
/* 106 */     this.tiles = parseTileNames(props.getProperty("tiles"));
/* 107 */     this.connect = parseConnect(props.getProperty("connect"));
/* 108 */     this.faces = parseFaces(props.getProperty("faces"));
/* 109 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/* 110 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*     */     
/* 112 */     if (this.heights == null) {
/* 113 */       int i = connectedparser.parseInt(props.getProperty("minHeight"), -1);
/* 114 */       int j = connectedparser.parseInt(props.getProperty("maxHeight"), 1024);
/*     */       
/* 116 */       if (i != -1 || j != 1024) {
/* 117 */         this.heights = new RangeListInt(new RangeInt(i, j));
/*     */       }
/*     */     } 
/*     */     
/* 121 */     this.renderPass = connectedparser.parseInt(props.getProperty("renderPass"), -1);
/* 122 */     this.innerSeams = connectedparser.parseBoolean(props.getProperty("innerSeams"), false);
/* 123 */     this.ctmTileIndexes = parseCtmTileIndexes(props);
/* 124 */     this.width = connectedparser.parseInt(props.getProperty("width"), -1);
/* 125 */     this.height = connectedparser.parseInt(props.getProperty("height"), -1);
/* 126 */     this.weights = connectedparser.parseIntList(props.getProperty("weights"));
/* 127 */     this.randomLoops = connectedparser.parseInt(props.getProperty("randomLoops"), 0);
/* 128 */     this.symmetry = parseSymmetry(props.getProperty("symmetry"));
/* 129 */     this.linked = connectedparser.parseBoolean(props.getProperty("linked"), false);
/* 130 */     this.nbtName = connectedparser.parseNbtTagValue("name", props.getProperty("name"));
/* 131 */     this.connectBlocks = connectedparser.parseMatchBlocks(props.getProperty("connectBlocks"));
/* 132 */     this.connectTiles = parseMatchTiles(props.getProperty("connectTiles"));
/* 133 */     this.tintIndex = connectedparser.parseInt(props.getProperty("tintIndex"), -1);
/* 134 */     this.tintBlockState = connectedparser.parseBlockState(props.getProperty("tintBlock"), Blocks.air.getDefaultState());
/* 135 */     this.layer = connectedparser.parseBlockRenderLayer(props.getProperty("layer"), EnumWorldBlockLayer.CUTOUT_MIPPED);
/*     */   }
/*     */   
/*     */   private int[] parseCtmTileIndexes(Properties props) {
/* 139 */     if (this.tiles == null) {
/* 140 */       return null;
/*     */     }
/* 142 */     Map<Integer, Integer> map = new HashMap<>();
/*     */     
/* 144 */     for (Object object : props.keySet()) {
/* 145 */       if (object instanceof String) {
/* 146 */         String s = (String)object;
/* 147 */         String s1 = "ctm.";
/*     */         
/* 149 */         if (s.startsWith(s1)) {
/* 150 */           String s2 = s.substring(s1.length());
/* 151 */           String s3 = props.getProperty(s);
/*     */           
/* 153 */           if (s3 != null) {
/* 154 */             s3 = s3.trim();
/* 155 */             int i = Config.parseInt(s2, -1);
/*     */             
/* 157 */             if (i >= 0 && i <= 46) {
/* 158 */               int j = Config.parseInt(s3, -1);
/*     */               
/* 160 */               if (j >= 0 && j < this.tiles.length) {
/* 161 */                 map.put(Integer.valueOf(i), Integer.valueOf(j)); continue;
/*     */               } 
/* 163 */               Config.warn("Invalid CTM tile index: " + s3);
/*     */               continue;
/*     */             } 
/* 166 */             Config.warn("Invalid CTM index: " + s2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 173 */     if (map.isEmpty()) {
/* 174 */       return null;
/*     */     }
/* 176 */     int[] aint = new int[47];
/*     */     
/* 178 */     for (int k = 0; k < aint.length; k++) {
/* 179 */       aint[k] = -1;
/*     */       
/* 181 */       if (map.containsKey(Integer.valueOf(k))) {
/* 182 */         aint[k] = ((Integer)map.get(Integer.valueOf(k))).intValue();
/*     */       }
/*     */     } 
/*     */     
/* 186 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] parseMatchTiles(String str) {
/* 192 */     if (str == null) {
/* 193 */       return null;
/*     */     }
/* 195 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 197 */     for (int i = 0; i < astring.length; i++) {
/* 198 */       String s = astring[i];
/*     */       
/* 200 */       if (s.endsWith(".png")) {
/* 201 */         s = s.substring(0, s.length() - 4);
/*     */       }
/*     */       
/* 204 */       s = TextureUtils.fixResourcePath(s, this.basePath);
/* 205 */       astring[i] = s;
/*     */     } 
/*     */     
/* 208 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String parseName(String path) {
/* 213 */     String s = path;
/* 214 */     int i = path.lastIndexOf('/');
/*     */     
/* 216 */     if (i >= 0) {
/* 217 */       s = path.substring(i + 1);
/*     */     }
/*     */     
/* 220 */     int j = s.lastIndexOf('.');
/*     */     
/* 222 */     if (j >= 0) {
/* 223 */       s = s.substring(0, j);
/*     */     }
/*     */     
/* 226 */     return s;
/*     */   }
/*     */   
/*     */   private static String parseBasePath(String path) {
/* 230 */     int i = path.lastIndexOf('/');
/* 231 */     return (i < 0) ? "" : path.substring(0, i);
/*     */   }
/*     */   
/*     */   private String[] parseTileNames(String str) {
/* 235 */     if (str == null) {
/* 236 */       return null;
/*     */     }
/* 238 */     List<String> list = new ArrayList();
/* 239 */     String[] astring = Config.tokenize(str, " ,");
/*     */ 
/*     */     
/* 242 */     for (int i = 0; i < astring.length; i++) {
/* 243 */       String s = astring[i];
/*     */       
/* 245 */       if (s.contains("-")) {
/* 246 */         String[] astring1 = Config.tokenize(s, "-");
/*     */         
/* 248 */         if (astring1.length == 2) {
/* 249 */           int j = Config.parseInt(astring1[0], -1);
/* 250 */           int k = Config.parseInt(astring1[1], -1);
/*     */           
/* 252 */           if (j >= 0 && k >= 0) {
/* 253 */             if (j > k) {
/* 254 */               Config.warn("Invalid interval: " + s + ", when parsing: " + str);
/*     */             }
/*     */             else {
/*     */               
/* 258 */               int l = j;
/*     */ 
/*     */               
/* 261 */               while (l <= k) {
/*     */ 
/*     */ 
/*     */                 
/* 265 */                 list.add(String.valueOf(l));
/* 266 */                 l++;
/*     */               } 
/*     */             }  continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 272 */       list.add(s);
/*     */       continue;
/*     */     } 
/* 275 */     String[] astring2 = list.<String>toArray(new String[list.size()]);
/*     */     
/* 277 */     for (int i1 = 0; i1 < astring2.length; i1++) {
/* 278 */       String s1 = astring2[i1];
/* 279 */       s1 = TextureUtils.fixResourcePath(s1, this.basePath);
/*     */       
/* 281 */       if (!s1.startsWith(this.basePath) && !s1.startsWith("textures/") && !s1.startsWith("mcpatcher/")) {
/* 282 */         s1 = String.valueOf(this.basePath) + "/" + s1;
/*     */       }
/*     */       
/* 285 */       if (s1.endsWith(".png")) {
/* 286 */         s1 = s1.substring(0, s1.length() - 4);
/*     */       }
/*     */       
/* 289 */       if (s1.startsWith("/")) {
/* 290 */         s1 = s1.substring(1);
/*     */       }
/*     */       
/* 293 */       astring2[i1] = s1;
/*     */     } 
/*     */     
/* 296 */     return astring2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseSymmetry(String str) {
/* 301 */     if (str == null) {
/* 302 */       return 1;
/*     */     }
/* 304 */     str = str.trim();
/*     */     
/* 306 */     if (str.equals("opposite"))
/* 307 */       return 2; 
/* 308 */     if (str.equals("all")) {
/* 309 */       return 6;
/*     */     }
/* 311 */     Config.warn("Unknown symmetry: " + str);
/* 312 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseFaces(String str) {
/* 318 */     if (str == null) {
/* 319 */       return 63;
/*     */     }
/* 321 */     String[] astring = Config.tokenize(str, " ,");
/* 322 */     int i = 0;
/*     */     
/* 324 */     for (int j = 0; j < astring.length; j++) {
/* 325 */       String s = astring[j];
/* 326 */       int k = parseFace(s);
/* 327 */       i |= k;
/*     */     } 
/*     */     
/* 330 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseFace(String str) {
/* 335 */     str = str.toLowerCase();
/*     */     
/* 337 */     if (!str.equals("bottom") && !str.equals("down")) {
/* 338 */       if (!str.equals("top") && !str.equals("up")) {
/* 339 */         if (str.equals("north"))
/* 340 */           return 4; 
/* 341 */         if (str.equals("south"))
/* 342 */           return 8; 
/* 343 */         if (str.equals("east"))
/* 344 */           return 32; 
/* 345 */         if (str.equals("west"))
/* 346 */           return 16; 
/* 347 */         if (str.equals("sides"))
/* 348 */           return 60; 
/* 349 */         if (str.equals("all")) {
/* 350 */           return 63;
/*     */         }
/* 352 */         Config.warn("Unknown face: " + str);
/* 353 */         return 128;
/*     */       } 
/*     */       
/* 356 */       return 2;
/*     */     } 
/*     */     
/* 359 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseConnect(String str) {
/* 364 */     if (str == null) {
/* 365 */       return 0;
/*     */     }
/* 367 */     str = str.trim();
/*     */     
/* 369 */     if (str.equals("block"))
/* 370 */       return 1; 
/* 371 */     if (str.equals("tile"))
/* 372 */       return 2; 
/* 373 */     if (str.equals("material")) {
/* 374 */       return 3;
/*     */     }
/* 376 */     Config.warn("Unknown connect: " + str);
/* 377 */     return 128;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IProperty getProperty(String key, Collection properties) {
/* 383 */     for (Object iproperty0 : properties) {
/* 384 */       IProperty iproperty = (IProperty)iproperty0;
/* 385 */       if (key.equals(iproperty.getName())) {
/* 386 */         return iproperty;
/*     */       }
/*     */     } 
/*     */     
/* 390 */     return null;
/*     */   }
/*     */   
/*     */   private static int parseMethod(String str) {
/* 394 */     if (str == null) {
/* 395 */       return 1;
/*     */     }
/* 397 */     str = str.trim();
/*     */     
/* 399 */     if (!str.equals("ctm") && !str.equals("glass")) {
/* 400 */       if (str.equals("ctm_compact"))
/* 401 */         return 10; 
/* 402 */       if (!str.equals("horizontal") && !str.equals("bookshelf")) {
/* 403 */         if (str.equals("vertical"))
/* 404 */           return 6; 
/* 405 */         if (str.equals("top"))
/* 406 */           return 3; 
/* 407 */         if (str.equals("random"))
/* 408 */           return 4; 
/* 409 */         if (str.equals("repeat"))
/* 410 */           return 5; 
/* 411 */         if (str.equals("fixed"))
/* 412 */           return 7; 
/* 413 */         if (!str.equals("horizontal+vertical") && !str.equals("h+v")) {
/* 414 */           if (!str.equals("vertical+horizontal") && !str.equals("v+h")) {
/* 415 */             if (str.equals("overlay"))
/* 416 */               return 11; 
/* 417 */             if (str.equals("overlay_fixed"))
/* 418 */               return 12; 
/* 419 */             if (str.equals("overlay_random"))
/* 420 */               return 13; 
/* 421 */             if (str.equals("overlay_repeat"))
/* 422 */               return 14; 
/* 423 */             if (str.equals("overlay_ctm")) {
/* 424 */               return 15;
/*     */             }
/* 426 */             Config.warn("Unknown method: " + str);
/* 427 */             return 0;
/*     */           } 
/*     */           
/* 430 */           return 9;
/*     */         } 
/*     */         
/* 433 */         return 8;
/*     */       } 
/*     */       
/* 436 */       return 2;
/*     */     } 
/*     */     
/* 439 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 445 */     if (this.name != null && this.name.length() > 0) {
/* 446 */       if (this.basePath == null) {
/* 447 */         Config.warn("No base path found: " + path);
/* 448 */         return false;
/*     */       } 
/* 450 */       if (this.matchBlocks == null) {
/* 451 */         this.matchBlocks = detectMatchBlocks();
/*     */       }
/*     */       
/* 454 */       if (this.matchTiles == null && this.matchBlocks == null) {
/* 455 */         this.matchTiles = detectMatchTiles();
/*     */       }
/*     */       
/* 458 */       if (this.matchBlocks == null && this.matchTiles == null) {
/* 459 */         Config.warn("No matchBlocks or matchTiles specified: " + path);
/* 460 */         return false;
/* 461 */       }  if (this.method == 0) {
/* 462 */         Config.warn("No method: " + path);
/* 463 */         return false;
/* 464 */       }  if (this.tiles != null && this.tiles.length > 0) {
/* 465 */         if (this.connect == 0) {
/* 466 */           this.connect = detectConnect();
/*     */         }
/*     */         
/* 469 */         if (this.connect == 128) {
/* 470 */           Config.warn("Invalid connect in: " + path);
/* 471 */           return false;
/* 472 */         }  if (this.renderPass > 0) {
/* 473 */           Config.warn("Render pass not supported: " + this.renderPass);
/* 474 */           return false;
/* 475 */         }  if ((this.faces & 0x80) != 0) {
/* 476 */           Config.warn("Invalid faces in: " + path);
/* 477 */           return false;
/* 478 */         }  if ((this.symmetry & 0x80) != 0) {
/* 479 */           Config.warn("Invalid symmetry in: " + path);
/* 480 */           return false;
/*     */         } 
/* 482 */         switch (this.method) {
/*     */           case 1:
/* 484 */             return isValidCtm(path);
/*     */           
/*     */           case 2:
/* 487 */             return isValidHorizontal(path);
/*     */           
/*     */           case 3:
/* 490 */             return isValidTop(path);
/*     */           
/*     */           case 4:
/* 493 */             return isValidRandom(path);
/*     */           
/*     */           case 5:
/* 496 */             return isValidRepeat(path);
/*     */           
/*     */           case 6:
/* 499 */             return isValidVertical(path);
/*     */           
/*     */           case 7:
/* 502 */             return isValidFixed(path);
/*     */           
/*     */           case 8:
/* 505 */             return isValidHorizontalVertical(path);
/*     */           
/*     */           case 9:
/* 508 */             return isValidVerticalHorizontal(path);
/*     */           
/*     */           case 10:
/* 511 */             return isValidCtmCompact(path);
/*     */           
/*     */           case 11:
/* 514 */             return isValidOverlay(path);
/*     */           
/*     */           case 12:
/* 517 */             return isValidOverlayFixed(path);
/*     */           
/*     */           case 13:
/* 520 */             return isValidOverlayRandom(path);
/*     */           
/*     */           case 14:
/* 523 */             return isValidOverlayRepeat(path);
/*     */           
/*     */           case 15:
/* 526 */             return isValidOverlayCtm(path);
/*     */         } 
/*     */         
/* 529 */         Config.warn("Unknown method: " + path);
/* 530 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 534 */       Config.warn("No tiles specified: " + path);
/* 535 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 539 */     Config.warn("No name found: " + path);
/* 540 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int detectConnect() {
/* 545 */     return (this.matchBlocks != null) ? 1 : ((this.matchTiles != null) ? 2 : 128);
/*     */   }
/*     */   
/*     */   private MatchBlock[] detectMatchBlocks() {
/* 549 */     int[] aint = detectMatchBlockIds();
/*     */     
/* 551 */     if (aint == null) {
/* 552 */       return null;
/*     */     }
/* 554 */     MatchBlock[] amatchblock = new MatchBlock[aint.length];
/*     */     
/* 556 */     for (int i = 0; i < amatchblock.length; i++) {
/* 557 */       amatchblock[i] = new MatchBlock(aint[i]);
/*     */     }
/*     */     
/* 560 */     return amatchblock;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] detectMatchBlockIds() {
/* 565 */     if (!this.name.startsWith("block")) {
/* 566 */       return null;
/*     */     }
/* 568 */     int i = "block".length();
/*     */     
/*     */     int j;
/* 571 */     for (j = i; j < this.name.length(); j++) {
/* 572 */       char c0 = this.name.charAt(j);
/*     */       
/* 574 */       if (c0 < '0' || c0 > '9') {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 579 */     if (j == i) {
/* 580 */       return null;
/*     */     }
/* 582 */     String s = this.name.substring(i, j);
/* 583 */     int k = Config.parseInt(s, -1);
/* 584 */     (new int[1])[0] = k; return (k < 0) ? null : new int[1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] detectMatchTiles() {
/* 590 */     TextureAtlasSprite textureatlassprite = getIcon(this.name);
/* 591 */     (new String[1])[0] = this.name; return (textureatlassprite == null) ? null : new String[1];
/*     */   }
/*     */   
/*     */   private static TextureAtlasSprite getIcon(String iconName) {
/* 595 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/* 596 */     TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(iconName);
/*     */     
/* 598 */     if (textureatlassprite != null) {
/* 599 */       return textureatlassprite;
/*     */     }
/* 601 */     textureatlassprite = texturemap.getSpriteSafe("blocks/" + iconName);
/* 602 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidCtm(String path) {
/* 607 */     if (this.tiles == null) {
/* 608 */       this.tiles = parseTileNames("0-11 16-27 32-43 48-58");
/*     */     }
/*     */     
/* 611 */     if (this.tiles.length < 47) {
/* 612 */       Config.warn("Invalid tiles, must be at least 47: " + path);
/* 613 */       return false;
/*     */     } 
/* 615 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidCtmCompact(String path) {
/* 620 */     if (this.tiles == null) {
/* 621 */       this.tiles = parseTileNames("0-4");
/*     */     }
/*     */     
/* 624 */     if (this.tiles.length < 5) {
/* 625 */       Config.warn("Invalid tiles, must be at least 5: " + path);
/* 626 */       return false;
/*     */     } 
/* 628 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidOverlay(String path) {
/* 633 */     if (this.tiles == null) {
/* 634 */       this.tiles = parseTileNames("0-16");
/*     */     }
/*     */     
/* 637 */     if (this.tiles.length < 17) {
/* 638 */       Config.warn("Invalid tiles, must be at least 17: " + path);
/* 639 */       return false;
/* 640 */     }  if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
/* 641 */       return true;
/*     */     }
/* 643 */     Config.warn("Invalid overlay layer: " + this.layer);
/* 644 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidOverlayFixed(String path) {
/* 649 */     if (!isValidFixed(path))
/* 650 */       return false; 
/* 651 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
/* 652 */       return true;
/*     */     }
/* 654 */     Config.warn("Invalid overlay layer: " + this.layer);
/* 655 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidOverlayRandom(String path) {
/* 660 */     if (!isValidRandom(path))
/* 661 */       return false; 
/* 662 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
/* 663 */       return true;
/*     */     }
/* 665 */     Config.warn("Invalid overlay layer: " + this.layer);
/* 666 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidOverlayRepeat(String path) {
/* 671 */     if (!isValidRepeat(path))
/* 672 */       return false; 
/* 673 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
/* 674 */       return true;
/*     */     }
/* 676 */     Config.warn("Invalid overlay layer: " + this.layer);
/* 677 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidOverlayCtm(String path) {
/* 682 */     if (!isValidCtm(path))
/* 683 */       return false; 
/* 684 */     if (this.layer != null && this.layer != EnumWorldBlockLayer.SOLID) {
/* 685 */       return true;
/*     */     }
/* 687 */     Config.warn("Invalid overlay layer: " + this.layer);
/* 688 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidHorizontal(String path) {
/* 693 */     if (this.tiles == null) {
/* 694 */       this.tiles = parseTileNames("12-15");
/*     */     }
/*     */     
/* 697 */     if (this.tiles.length != 4) {
/* 698 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/* 699 */       return false;
/*     */     } 
/* 701 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidVertical(String path) {
/* 706 */     if (this.tiles == null) {
/* 707 */       Config.warn("No tiles defined for vertical: " + path);
/* 708 */       return false;
/* 709 */     }  if (this.tiles.length != 4) {
/* 710 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/* 711 */       return false;
/*     */     } 
/* 713 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidHorizontalVertical(String path) {
/* 718 */     if (this.tiles == null) {
/* 719 */       Config.warn("No tiles defined for horizontal+vertical: " + path);
/* 720 */       return false;
/* 721 */     }  if (this.tiles.length != 7) {
/* 722 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/* 723 */       return false;
/*     */     } 
/* 725 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidVerticalHorizontal(String path) {
/* 730 */     if (this.tiles == null) {
/* 731 */       Config.warn("No tiles defined for vertical+horizontal: " + path);
/* 732 */       return false;
/* 733 */     }  if (this.tiles.length != 7) {
/* 734 */       Config.warn("Invalid tiles, must be exactly 7: " + path);
/* 735 */       return false;
/*     */     } 
/* 737 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidRandom(String path) {
/* 742 */     if (this.tiles != null && this.tiles.length > 0) {
/* 743 */       if (this.weights != null) {
/* 744 */         if (this.weights.length > this.tiles.length) {
/* 745 */           Config.warn("More weights defined than tiles, trimming weights: " + path);
/* 746 */           int[] aint = new int[this.tiles.length];
/* 747 */           System.arraycopy(this.weights, 0, aint, 0, aint.length);
/* 748 */           this.weights = aint;
/*     */         } 
/*     */         
/* 751 */         if (this.weights.length < this.tiles.length) {
/* 752 */           Config.warn("Less weights defined than tiles, expanding weights: " + path);
/* 753 */           int[] aint1 = new int[this.tiles.length];
/* 754 */           System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
/* 755 */           int i = MathUtils.getAverage(this.weights);
/*     */           
/* 757 */           for (int j = this.weights.length; j < aint1.length; j++) {
/* 758 */             aint1[j] = i;
/*     */           }
/*     */           
/* 761 */           this.weights = aint1;
/*     */         } 
/*     */         
/* 764 */         this.sumWeights = new int[this.weights.length];
/* 765 */         int k = 0;
/*     */         
/* 767 */         for (int l = 0; l < this.weights.length; l++) {
/* 768 */           k += this.weights[l];
/* 769 */           this.sumWeights[l] = k;
/*     */         } 
/*     */         
/* 772 */         this.sumAllWeights = k;
/*     */         
/* 774 */         if (this.sumAllWeights <= 0) {
/* 775 */           Config.warn("Invalid sum of all weights: " + k);
/* 776 */           this.sumAllWeights = 1;
/*     */         } 
/*     */       } 
/*     */       
/* 780 */       if (this.randomLoops >= 0 && this.randomLoops <= 9) {
/* 781 */         return true;
/*     */       }
/* 783 */       Config.warn("Invalid randomLoops: " + this.randomLoops);
/* 784 */       return false;
/*     */     } 
/*     */     
/* 787 */     Config.warn("Tiles not defined: " + path);
/* 788 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidRepeat(String path) {
/* 793 */     if (this.tiles == null) {
/* 794 */       Config.warn("Tiles not defined: " + path);
/* 795 */       return false;
/* 796 */     }  if (this.width <= 0) {
/* 797 */       Config.warn("Invalid width: " + path);
/* 798 */       return false;
/* 799 */     }  if (this.height <= 0) {
/* 800 */       Config.warn("Invalid height: " + path);
/* 801 */       return false;
/* 802 */     }  if (this.tiles.length != this.width * this.height) {
/* 803 */       Config.warn("Number of tiles does not equal width x height: " + path);
/* 804 */       return false;
/*     */     } 
/* 806 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidFixed(String path) {
/* 811 */     if (this.tiles == null) {
/* 812 */       Config.warn("Tiles not defined: " + path);
/* 813 */       return false;
/* 814 */     }  if (this.tiles.length != 1) {
/* 815 */       Config.warn("Number of tiles should be 1 for method: fixed.");
/* 816 */       return false;
/*     */     } 
/* 818 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isValidTop(String path) {
/* 823 */     if (this.tiles == null) {
/* 824 */       this.tiles = parseTileNames("66");
/*     */     }
/*     */     
/* 827 */     if (this.tiles.length != 1) {
/* 828 */       Config.warn("Invalid tiles, must be exactly 1: " + path);
/* 829 */       return false;
/*     */     } 
/* 831 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateIcons(TextureMap textureMap) {
/* 836 */     if (this.matchTiles != null) {
/* 837 */       this.matchTileIcons = registerIcons(this.matchTiles, textureMap, false, false);
/*     */     }
/*     */     
/* 840 */     if (this.connectTiles != null) {
/* 841 */       this.connectTileIcons = registerIcons(this.connectTiles, textureMap, false, false);
/*     */     }
/*     */     
/* 844 */     if (this.tiles != null) {
/* 845 */       this.tileIcons = registerIcons(this.tiles, textureMap, true, !isMethodOverlay(this.method));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isMethodOverlay(int method) {
/* 850 */     switch (method) {
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/* 856 */         return true;
/*     */     } 
/*     */     
/* 859 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAtlasSprite[] registerIcons(String[] tileNames, TextureMap textureMap, boolean skipTiles, boolean defaultTiles) {
/* 864 */     if (tileNames == null) {
/* 865 */       return null;
/*     */     }
/* 867 */     List<TextureAtlasSprite> list = new ArrayList();
/*     */     
/* 869 */     for (int i = 0; i < tileNames.length; i++) {
/* 870 */       String s = tileNames[i];
/* 871 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 872 */       String s1 = resourcelocation.getResourceDomain();
/* 873 */       String s2 = resourcelocation.getResourcePath();
/*     */       
/* 875 */       if (!s2.contains("/")) {
/* 876 */         s2 = "textures/blocks/" + s2;
/*     */       }
/*     */       
/* 879 */       String s3 = String.valueOf(s2) + ".png";
/*     */       
/* 881 */       if (skipTiles && s3.endsWith("<skip>.png")) {
/* 882 */         list.add(null);
/* 883 */       } else if (defaultTiles && s3.endsWith("<default>.png")) {
/* 884 */         list.add(ConnectedTextures.SPRITE_DEFAULT);
/*     */       } else {
/* 886 */         ResourceLocation resourcelocation1 = new ResourceLocation(s1, s3);
/* 887 */         boolean flag = Config.hasResource(resourcelocation1);
/*     */         
/* 889 */         if (!flag) {
/* 890 */           Config.warn("File not found: " + s3);
/*     */         }
/*     */         
/* 893 */         String s4 = "textures/";
/* 894 */         String s5 = s2;
/*     */         
/* 896 */         if (s2.startsWith(s4)) {
/* 897 */           s5 = s2.substring(s4.length());
/*     */         }
/*     */         
/* 900 */         ResourceLocation resourcelocation2 = new ResourceLocation(s1, s5);
/* 901 */         TextureAtlasSprite textureatlassprite = textureMap.registerSprite(resourcelocation2);
/* 902 */         list.add(textureatlassprite);
/*     */       } 
/*     */     } 
/*     */     
/* 906 */     TextureAtlasSprite[] atextureatlassprite = list.<TextureAtlasSprite>toArray(new TextureAtlasSprite[list.size()]);
/* 907 */     return atextureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesBlockId(int blockId) {
/* 912 */     return Matches.blockId(blockId, this.matchBlocks);
/*     */   }
/*     */   
/*     */   public boolean matchesBlock(int blockId, int metadata) {
/* 916 */     return !Matches.block(blockId, metadata, this.matchBlocks) ? false : Matches.metadata(metadata, this.metadatas);
/*     */   }
/*     */   
/*     */   public boolean matchesIcon(TextureAtlasSprite icon) {
/* 920 */     return Matches.sprite(icon, this.matchTileIcons);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 924 */     return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", matchTiles: " + Config.arrayToString((Object[])this.matchTiles);
/*     */   }
/*     */   
/*     */   public boolean matchesBiome(BiomeGenBase biome) {
/* 928 */     return Matches.biome(biome, this.biomes);
/*     */   }
/*     */   
/*     */   public int getMetadataMax() {
/* 932 */     int i = -1;
/* 933 */     i = getMax(this.metadatas, i);
/*     */     
/* 935 */     if (this.matchBlocks != null) {
/* 936 */       for (int j = 0; j < this.matchBlocks.length; j++) {
/* 937 */         MatchBlock matchblock = this.matchBlocks[j];
/* 938 */         i = getMax(matchblock.getMetadatas(), i);
/*     */       } 
/*     */     }
/*     */     
/* 942 */     return i;
/*     */   }
/*     */   
/*     */   private int getMax(int[] mds, int max) {
/* 946 */     if (mds == null) {
/* 947 */       return max;
/*     */     }
/* 949 */     for (int i = 0; i < mds.length; i++) {
/* 950 */       int j = mds[i];
/*     */       
/* 952 */       if (j > max) {
/* 953 */         max = j;
/*     */       }
/*     */     } 
/*     */     
/* 957 */     return max;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\ConnectedProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */