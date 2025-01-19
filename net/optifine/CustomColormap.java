/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomColormap
/*     */   implements CustomColors.IColorizer {
/*  28 */   public String name = null;
/*  29 */   public String basePath = null;
/*  30 */   private int format = -1;
/*  31 */   private MatchBlock[] matchBlocks = null;
/*  32 */   private String source = null;
/*  33 */   private int color = -1;
/*  34 */   private int yVariance = 0;
/*  35 */   private int yOffset = 0;
/*  36 */   private int width = 0;
/*  37 */   private int height = 0;
/*  38 */   private int[] colors = null;
/*  39 */   private float[][] colorsRgb = null;
/*     */   private static final int FORMAT_UNKNOWN = -1;
/*     */   private static final int FORMAT_VANILLA = 0;
/*     */   private static final int FORMAT_GRID = 1;
/*     */   private static final int FORMAT_FIXED = 2;
/*     */   public static final String FORMAT_VANILLA_STRING = "vanilla";
/*     */   public static final String FORMAT_GRID_STRING = "grid";
/*     */   public static final String FORMAT_FIXED_STRING = "fixed";
/*  47 */   public static final String[] FORMAT_STRINGS = new String[] { "vanilla", "grid", "fixed" };
/*     */   public static final String KEY_FORMAT = "format";
/*     */   public static final String KEY_BLOCKS = "blocks";
/*     */   public static final String KEY_SOURCE = "source";
/*     */   public static final String KEY_COLOR = "color";
/*     */   public static final String KEY_Y_VARIANCE = "yVariance";
/*     */   public static final String KEY_Y_OFFSET = "yOffset";
/*     */   
/*     */   public CustomColormap(Properties props, String path, int width, int height, String formatDefault) {
/*  56 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/*  57 */     this.name = connectedparser.parseName(path);
/*  58 */     this.basePath = connectedparser.parseBasePath(path);
/*  59 */     this.format = parseFormat(props.getProperty("format", formatDefault));
/*  60 */     this.matchBlocks = connectedparser.parseMatchBlocks(props.getProperty("blocks"));
/*  61 */     this.source = parseTexture(props.getProperty("source"), path, this.basePath);
/*  62 */     this.color = ConnectedParser.parseColor(props.getProperty("color"), -1);
/*  63 */     this.yVariance = connectedparser.parseInt(props.getProperty("yVariance"), 0);
/*  64 */     this.yOffset = connectedparser.parseInt(props.getProperty("yOffset"), 0);
/*  65 */     this.width = width;
/*  66 */     this.height = height;
/*     */   }
/*     */   
/*     */   private int parseFormat(String str) {
/*  70 */     if (str == null) {
/*  71 */       return 0;
/*     */     }
/*  73 */     str = str.trim();
/*     */     
/*  75 */     if (str.equals("vanilla"))
/*  76 */       return 0; 
/*  77 */     if (str.equals("grid"))
/*  78 */       return 1; 
/*  79 */     if (str.equals("fixed")) {
/*  80 */       return 2;
/*     */     }
/*  82 */     warn("Unknown format: " + str);
/*  83 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/*  89 */     if (this.format != 0 && this.format != 1) {
/*  90 */       if (this.format != 2) {
/*  91 */         return false;
/*     */       }
/*     */       
/*  94 */       if (this.color < 0) {
/*  95 */         this.color = 16777215;
/*     */       }
/*     */     } else {
/*  98 */       if (this.source == null) {
/*  99 */         warn("Source not defined: " + path);
/* 100 */         return false;
/*     */       } 
/*     */       
/* 103 */       readColors();
/*     */       
/* 105 */       if (this.colors == null) {
/* 106 */         return false;
/*     */       }
/*     */       
/* 109 */       if (this.color < 0) {
/* 110 */         if (this.format == 0) {
/* 111 */           this.color = getColor(127, 127);
/*     */         }
/*     */         
/* 114 */         if (this.format == 1) {
/* 115 */           this.color = getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isValidMatchBlocks(String path) {
/* 124 */     if (this.matchBlocks == null) {
/* 125 */       this.matchBlocks = detectMatchBlocks();
/*     */       
/* 127 */       if (this.matchBlocks == null) {
/* 128 */         warn("Match blocks not defined: " + path);
/* 129 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   private MatchBlock[] detectMatchBlocks() {
/* 137 */     Block block = Block.getBlockFromName(this.name);
/*     */     
/* 139 */     if (block != null) {
/* 140 */       return new MatchBlock[] { new MatchBlock(Block.getIdFromBlock(block)) };
/*     */     }
/* 142 */     Pattern pattern = Pattern.compile("^block([0-9]+).*$");
/* 143 */     Matcher matcher = pattern.matcher(this.name);
/*     */     
/* 145 */     if (matcher.matches()) {
/* 146 */       String s = matcher.group(1);
/* 147 */       int i = Config.parseInt(s, -1);
/*     */       
/* 149 */       if (i >= 0) {
/* 150 */         return new MatchBlock[] { new MatchBlock(i) };
/*     */       }
/*     */     } 
/*     */     
/* 154 */     ConnectedParser connectedparser = new ConnectedParser("Colormap");
/* 155 */     MatchBlock[] amatchblock = connectedparser.parseMatchBlock(this.name);
/* 156 */     return (amatchblock != null) ? amatchblock : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readColors() {
/*     */     try {
/* 162 */       this.colors = null;
/*     */       
/* 164 */       if (this.source == null) {
/*     */         return;
/*     */       }
/*     */       
/* 168 */       String s = String.valueOf(this.source) + ".png";
/* 169 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 170 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 172 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */       
/* 176 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/*     */       
/* 178 */       if (bufferedimage == null) {
/*     */         return;
/*     */       }
/*     */       
/* 182 */       int i = bufferedimage.getWidth();
/* 183 */       int j = bufferedimage.getHeight();
/* 184 */       boolean flag = !(this.width >= 0 && this.width != i);
/* 185 */       boolean flag1 = !(this.height >= 0 && this.height != j);
/*     */       
/* 187 */       if (!flag || !flag1) {
/* 188 */         dbg("Non-standard palette size: " + i + "x" + j + ", should be: " + this.width + "x" + this.height + ", path: " + s);
/*     */       }
/*     */       
/* 191 */       this.width = i;
/* 192 */       this.height = j;
/*     */       
/* 194 */       if (this.width <= 0 || this.height <= 0) {
/* 195 */         warn("Invalid palette size: " + i + "x" + j + ", path: " + s);
/*     */         
/*     */         return;
/*     */       } 
/* 199 */       this.colors = new int[i * j];
/* 200 */       bufferedimage.getRGB(0, 0, i, j, this.colors, 0, i);
/* 201 */     } catch (IOException ioexception) {
/* 202 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void dbg(String str) {
/* 207 */     Config.dbg("CustomColors: " + str);
/*     */   }
/*     */   
/*     */   private static void warn(String str) {
/* 211 */     Config.warn("CustomColors: " + str);
/*     */   }
/*     */   
/*     */   private static String parseTexture(String texStr, String path, String basePath) {
/* 215 */     if (texStr != null) {
/* 216 */       texStr = texStr.trim();
/* 217 */       String s1 = ".png";
/*     */       
/* 219 */       if (texStr.endsWith(s1)) {
/* 220 */         texStr = texStr.substring(0, texStr.length() - s1.length());
/*     */       }
/*     */       
/* 223 */       texStr = fixTextureName(texStr, basePath);
/* 224 */       return texStr;
/*     */     } 
/* 226 */     String s = path;
/* 227 */     int i = path.lastIndexOf('/');
/*     */     
/* 229 */     if (i >= 0) {
/* 230 */       s = path.substring(i + 1);
/*     */     }
/*     */     
/* 233 */     int j = s.lastIndexOf('.');
/*     */     
/* 235 */     if (j >= 0) {
/* 236 */       s = s.substring(0, j);
/*     */     }
/*     */     
/* 239 */     s = fixTextureName(s, basePath);
/* 240 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixTextureName(String iconName, String basePath) {
/* 245 */     iconName = TextureUtils.fixResourcePath(iconName, basePath);
/*     */     
/* 247 */     if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/")) {
/* 248 */       iconName = String.valueOf(basePath) + "/" + iconName;
/*     */     }
/*     */     
/* 251 */     if (iconName.endsWith(".png")) {
/* 252 */       iconName = iconName.substring(0, iconName.length() - 4);
/*     */     }
/*     */     
/* 255 */     String s = "textures/blocks/";
/*     */     
/* 257 */     if (iconName.startsWith(s)) {
/* 258 */       iconName = iconName.substring(s.length());
/*     */     }
/*     */     
/* 261 */     if (iconName.startsWith("/")) {
/* 262 */       iconName = iconName.substring(1);
/*     */     }
/*     */     
/* 265 */     return iconName;
/*     */   }
/*     */   
/*     */   public boolean matchesBlock(BlockStateBase blockState) {
/* 269 */     return Matches.block(blockState, this.matchBlocks);
/*     */   }
/*     */   
/*     */   public int getColorRandom() {
/* 273 */     if (this.format == 2) {
/* 274 */       return this.color;
/*     */     }
/* 276 */     int i = CustomColors.random.nextInt(this.colors.length);
/* 277 */     return this.colors[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(int index) {
/* 282 */     index = Config.limit(index, 0, this.colors.length - 1);
/* 283 */     return this.colors[index] & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   public int getColor(int cx, int cy) {
/* 287 */     cx = Config.limit(cx, 0, this.width - 1);
/* 288 */     cy = Config.limit(cy, 0, this.height - 1);
/* 289 */     return this.colors[cy * this.width + cx] & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   public float[][] getColorsRgb() {
/* 293 */     if (this.colorsRgb == null) {
/* 294 */       this.colorsRgb = toRgb(this.colors);
/*     */     }
/*     */     
/* 297 */     return this.colorsRgb;
/*     */   }
/*     */   
/*     */   public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/* 301 */     return getColor(blockAccess, blockPos);
/*     */   }
/*     */   
/*     */   public int getColor(IBlockAccess blockAccess, BlockPos blockPos) {
/* 305 */     BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/* 306 */     return getColor(biomegenbase, blockPos);
/*     */   }
/*     */   
/*     */   public boolean isColorConstant() {
/* 310 */     return (this.format == 2);
/*     */   }
/*     */   
/*     */   public int getColor(BiomeGenBase biome, BlockPos blockPos) {
/* 314 */     return (this.format == 0) ? getColorVanilla(biome, blockPos) : ((this.format == 1) ? getColorGrid(biome, blockPos) : this.color);
/*     */   }
/*     */   
/*     */   public int getColorSmooth(IBlockAccess blockAccess, double x, double y, double z, int radius) {
/* 318 */     if (this.format == 2) {
/* 319 */       return this.color;
/*     */     }
/* 321 */     int i = MathHelper.floor_double(x);
/* 322 */     int j = MathHelper.floor_double(y);
/* 323 */     int k = MathHelper.floor_double(z);
/* 324 */     int l = 0;
/* 325 */     int i1 = 0;
/* 326 */     int j1 = 0;
/* 327 */     int k1 = 0;
/* 328 */     BlockPosM blockposm = new BlockPosM(0, 0, 0);
/*     */     
/* 330 */     for (int l1 = i - radius; l1 <= i + radius; l1++) {
/* 331 */       for (int i2 = k - radius; i2 <= k + radius; i2++) {
/* 332 */         blockposm.setXyz(l1, j, i2);
/* 333 */         int j2 = getColor(blockAccess, blockposm);
/* 334 */         l += j2 >> 16 & 0xFF;
/* 335 */         i1 += j2 >> 8 & 0xFF;
/* 336 */         j1 += j2 & 0xFF;
/* 337 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 341 */     int k2 = l / k1;
/* 342 */     int l2 = i1 / k1;
/* 343 */     int i3 = j1 / k1;
/* 344 */     return k2 << 16 | l2 << 8 | i3;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getColorVanilla(BiomeGenBase biome, BlockPos blockPos) {
/* 349 */     double d0 = MathHelper.clamp_float(biome.getFloatTemperature(blockPos), 0.0F, 1.0F);
/* 350 */     double d1 = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
/* 351 */     d1 *= d0;
/* 352 */     int i = (int)((1.0D - d0) * (this.width - 1));
/* 353 */     int j = (int)((1.0D - d1) * (this.height - 1));
/* 354 */     return getColor(i, j);
/*     */   }
/*     */   
/*     */   private int getColorGrid(BiomeGenBase biome, BlockPos blockPos) {
/* 358 */     int i = biome.biomeID;
/* 359 */     int j = blockPos.getY() - this.yOffset;
/*     */     
/* 361 */     if (this.yVariance > 0) {
/* 362 */       int k = blockPos.getX() << 16 + blockPos.getZ();
/* 363 */       int l = Config.intHash(k);
/* 364 */       int i1 = this.yVariance * 2 + 1;
/* 365 */       int j1 = (l & 0xFF) % i1 - this.yVariance;
/* 366 */       j += j1;
/*     */     } 
/*     */     
/* 369 */     return getColor(i, j);
/*     */   }
/*     */   
/*     */   public int getLength() {
/* 373 */     return (this.format == 2) ? 1 : this.colors.length;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 377 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 381 */     return this.height;
/*     */   }
/*     */   
/*     */   private static float[][] toRgb(int[] cols) {
/* 385 */     float[][] afloat = new float[cols.length][3];
/*     */     
/* 387 */     for (int i = 0; i < cols.length; i++) {
/* 388 */       int j = cols[i];
/* 389 */       float f = (j >> 16 & 0xFF) / 255.0F;
/* 390 */       float f1 = (j >> 8 & 0xFF) / 255.0F;
/* 391 */       float f2 = (j & 0xFF) / 255.0F;
/* 392 */       float[] afloat1 = afloat[i];
/* 393 */       afloat1[0] = f;
/* 394 */       afloat1[1] = f1;
/* 395 */       afloat1[2] = f2;
/*     */     } 
/*     */     
/* 398 */     return afloat;
/*     */   }
/*     */   
/*     */   public void addMatchBlock(MatchBlock mb) {
/* 402 */     if (this.matchBlocks == null) {
/* 403 */       this.matchBlocks = new MatchBlock[0];
/*     */     }
/*     */     
/* 406 */     this.matchBlocks = (MatchBlock[])Config.addObjectToArray((Object[])this.matchBlocks, mb);
/*     */   }
/*     */   
/*     */   public void addMatchBlock(int blockId, int metadata) {
/* 410 */     MatchBlock matchblock = getMatchBlock(blockId);
/*     */     
/* 412 */     if (matchblock != null) {
/* 413 */       if (metadata >= 0) {
/* 414 */         matchblock.addMetadata(metadata);
/*     */       }
/*     */     } else {
/* 417 */       addMatchBlock(new MatchBlock(blockId, metadata));
/*     */     } 
/*     */   }
/*     */   
/*     */   private MatchBlock getMatchBlock(int blockId) {
/* 422 */     if (this.matchBlocks == null) {
/* 423 */       return null;
/*     */     }
/* 425 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/* 426 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 428 */       if (matchblock.getBlockId() == blockId) {
/* 429 */         return matchblock;
/*     */       }
/*     */     } 
/*     */     
/* 433 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getMatchBlockIds() {
/* 438 */     if (this.matchBlocks == null) {
/* 439 */       return null;
/*     */     }
/* 441 */     Set<Integer> set = new HashSet();
/*     */     
/* 443 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/* 444 */       MatchBlock matchblock = this.matchBlocks[i];
/*     */       
/* 446 */       if (matchblock.getBlockId() >= 0) {
/* 447 */         set.add(Integer.valueOf(matchblock.getBlockId()));
/*     */       }
/*     */     } 
/*     */     
/* 451 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 452 */     int[] aint = new int[ainteger.length];
/*     */     
/* 454 */     for (int j = 0; j < ainteger.length; j++) {
/* 455 */       aint[j] = ainteger[j].intValue();
/*     */     }
/*     */     
/* 458 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 463 */     return this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", source: " + this.source;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomColormap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */