/*      */ package net.optifine;
/*      */ 
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockRedstoneWire;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMonsterPlacer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.ColorizerFoliage;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.config.MatchBlock;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.util.EntityUtils;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ import net.optifine.util.StrUtils;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*      */ import org.apache.commons.lang3.tuple.Pair;
/*      */ 
/*      */ public class CustomColors
/*      */ {
/*   56 */   private static String paletteFormatDefault = "vanilla";
/*   57 */   private static CustomColormap waterColors = null;
/*   58 */   private static CustomColormap foliagePineColors = null;
/*   59 */   private static CustomColormap foliageBirchColors = null;
/*   60 */   private static CustomColormap swampFoliageColors = null;
/*   61 */   private static CustomColormap swampGrassColors = null;
/*   62 */   private static CustomColormap[] colorsBlockColormaps = null;
/*   63 */   private static CustomColormap[][] blockColormaps = null;
/*   64 */   private static CustomColormap skyColors = null;
/*   65 */   private static CustomColorFader skyColorFader = new CustomColorFader();
/*   66 */   private static CustomColormap fogColors = null;
/*   67 */   private static CustomColorFader fogColorFader = new CustomColorFader();
/*   68 */   private static CustomColormap underwaterColors = null;
/*   69 */   private static CustomColorFader underwaterColorFader = new CustomColorFader();
/*   70 */   private static CustomColormap underlavaColors = null;
/*   71 */   private static CustomColorFader underlavaColorFader = new CustomColorFader();
/*   72 */   private static LightMapPack[] lightMapPacks = null;
/*   73 */   private static int lightmapMinDimensionId = 0;
/*   74 */   private static CustomColormap redstoneColors = null;
/*   75 */   private static CustomColormap xpOrbColors = null;
/*   76 */   private static int xpOrbTime = -1;
/*   77 */   private static CustomColormap durabilityColors = null;
/*   78 */   private static CustomColormap stemColors = null;
/*   79 */   private static CustomColormap stemMelonColors = null;
/*   80 */   private static CustomColormap stemPumpkinColors = null;
/*   81 */   private static CustomColormap myceliumParticleColors = null;
/*      */   private static boolean useDefaultGrassFoliageColors = true;
/*   83 */   private static int particleWaterColor = -1;
/*   84 */   private static int particlePortalColor = -1;
/*   85 */   private static int lilyPadColor = -1;
/*   86 */   private static int expBarTextColor = -1;
/*   87 */   private static int bossTextColor = -1;
/*   88 */   private static int signTextColor = -1;
/*   89 */   private static Vec3 fogColorNether = null;
/*   90 */   private static Vec3 fogColorEnd = null;
/*   91 */   private static Vec3 skyColorEnd = null;
/*   92 */   private static int[] spawnEggPrimaryColors = null;
/*   93 */   private static int[] spawnEggSecondaryColors = null;
/*   94 */   private static float[][] wolfCollarColors = null;
/*   95 */   private static float[][] sheepColors = null;
/*   96 */   private static int[] textColors = null;
/*   97 */   private static int[] mapColorsOriginal = null;
/*   98 */   private static int[] potionColors = null;
/*   99 */   private static final IBlockState BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
/*  100 */   private static final IBlockState BLOCK_STATE_WATER = Blocks.water.getDefaultState();
/*  101 */   public static Random random = new Random();
/*  102 */   private static final IColorizer COLORIZER_GRASS = new IColorizer() {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/*  104 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  105 */         return (CustomColors.swampGrassColors != null && biomegenbase == BiomeGenBase.swampland) ? CustomColors.swampGrassColors.getColor(biomegenbase, blockPos) : biomegenbase.getGrassColorAtPos(blockPos);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  109 */         return false;
/*      */       }
/*      */     };
/*  112 */   private static final IColorizer COLORIZER_FOLIAGE = new IColorizer() {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/*  114 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  115 */         return (CustomColors.swampFoliageColors != null && biomegenbase == BiomeGenBase.swampland) ? CustomColors.swampFoliageColors.getColor(biomegenbase, blockPos) : biomegenbase.getFoliageColorAtPos(blockPos);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  119 */         return false;
/*      */       }
/*      */     };
/*  122 */   private static final IColorizer COLORIZER_FOLIAGE_PINE = new IColorizer() {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/*  124 */         return (CustomColors.foliagePineColors != null) ? CustomColors.foliagePineColors.getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorPine();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  128 */         return (CustomColors.foliagePineColors == null);
/*      */       }
/*      */     };
/*  131 */   private static final IColorizer COLORIZER_FOLIAGE_BIRCH = new IColorizer() {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/*  133 */         return (CustomColors.foliageBirchColors != null) ? CustomColors.foliageBirchColors.getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorBirch();
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  137 */         return (CustomColors.foliageBirchColors == null);
/*      */       }
/*      */     };
/*  140 */   private static final IColorizer COLORIZER_WATER = new IColorizer() {
/*      */       public int getColor(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
/*  142 */         BiomeGenBase biomegenbase = CustomColors.getColorBiome(blockAccess, blockPos);
/*  143 */         return (CustomColors.waterColors != null) ? CustomColors.waterColors.getColor(biomegenbase, blockPos) : (Reflector.ForgeBiome_getWaterColorMultiplier.exists() ? Reflector.callInt(biomegenbase, Reflector.ForgeBiome_getWaterColorMultiplier, new Object[0]) : biomegenbase.waterColorMultiplier);
/*      */       }
/*      */       
/*      */       public boolean isColorConstant() {
/*  147 */         return false;
/*      */       }
/*      */     };
/*      */   
/*      */   public static void update() {
/*  152 */     paletteFormatDefault = "vanilla";
/*  153 */     waterColors = null;
/*  154 */     foliageBirchColors = null;
/*  155 */     foliagePineColors = null;
/*  156 */     swampGrassColors = null;
/*  157 */     swampFoliageColors = null;
/*  158 */     skyColors = null;
/*  159 */     fogColors = null;
/*  160 */     underwaterColors = null;
/*  161 */     underlavaColors = null;
/*  162 */     redstoneColors = null;
/*  163 */     xpOrbColors = null;
/*  164 */     xpOrbTime = -1;
/*  165 */     durabilityColors = null;
/*  166 */     stemColors = null;
/*  167 */     myceliumParticleColors = null;
/*  168 */     lightMapPacks = null;
/*  169 */     particleWaterColor = -1;
/*  170 */     particlePortalColor = -1;
/*  171 */     lilyPadColor = -1;
/*  172 */     expBarTextColor = -1;
/*  173 */     bossTextColor = -1;
/*  174 */     signTextColor = -1;
/*  175 */     fogColorNether = null;
/*  176 */     fogColorEnd = null;
/*  177 */     skyColorEnd = null;
/*  178 */     colorsBlockColormaps = null;
/*  179 */     blockColormaps = null;
/*  180 */     useDefaultGrassFoliageColors = true;
/*  181 */     spawnEggPrimaryColors = null;
/*  182 */     spawnEggSecondaryColors = null;
/*  183 */     wolfCollarColors = null;
/*  184 */     sheepColors = null;
/*  185 */     textColors = null;
/*  186 */     setMapColors(mapColorsOriginal);
/*  187 */     potionColors = null;
/*  188 */     paletteFormatDefault = getValidProperty("mcpatcher/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
/*  189 */     String s = "mcpatcher/colormap/";
/*  190 */     String[] astring = { "water.png", "watercolorX.png" };
/*  191 */     waterColors = getCustomColors(s, astring, 256, 256);
/*  192 */     updateUseDefaultGrassFoliageColors();
/*      */     
/*  194 */     if (Config.isCustomColors()) {
/*  195 */       String[] astring1 = { "pine.png", "pinecolor.png" };
/*  196 */       foliagePineColors = getCustomColors(s, astring1, 256, 256);
/*  197 */       String[] astring2 = { "birch.png", "birchcolor.png" };
/*  198 */       foliageBirchColors = getCustomColors(s, astring2, 256, 256);
/*  199 */       String[] astring3 = { "swampgrass.png", "swampgrasscolor.png" };
/*  200 */       swampGrassColors = getCustomColors(s, astring3, 256, 256);
/*  201 */       String[] astring4 = { "swampfoliage.png", "swampfoliagecolor.png" };
/*  202 */       swampFoliageColors = getCustomColors(s, astring4, 256, 256);
/*  203 */       String[] astring5 = { "sky0.png", "skycolor0.png" };
/*  204 */       skyColors = getCustomColors(s, astring5, 256, 256);
/*  205 */       String[] astring6 = { "fog0.png", "fogcolor0.png" };
/*  206 */       fogColors = getCustomColors(s, astring6, 256, 256);
/*  207 */       String[] astring7 = { "underwater.png", "underwatercolor.png" };
/*  208 */       underwaterColors = getCustomColors(s, astring7, 256, 256);
/*  209 */       String[] astring8 = { "underlava.png", "underlavacolor.png" };
/*  210 */       underlavaColors = getCustomColors(s, astring8, 256, 256);
/*  211 */       String[] astring9 = { "redstone.png", "redstonecolor.png" };
/*  212 */       redstoneColors = getCustomColors(s, astring9, 16, 1);
/*  213 */       xpOrbColors = getCustomColors(String.valueOf(s) + "xporb.png", -1, -1);
/*  214 */       durabilityColors = getCustomColors(String.valueOf(s) + "durability.png", -1, -1);
/*  215 */       String[] astring10 = { "stem.png", "stemcolor.png" };
/*  216 */       stemColors = getCustomColors(s, astring10, 8, 1);
/*  217 */       stemPumpkinColors = getCustomColors(String.valueOf(s) + "pumpkinstem.png", 8, 1);
/*  218 */       stemMelonColors = getCustomColors(String.valueOf(s) + "melonstem.png", 8, 1);
/*  219 */       String[] astring11 = { "myceliumparticle.png", "myceliumparticlecolor.png" };
/*  220 */       myceliumParticleColors = getCustomColors(s, astring11, -1, -1);
/*  221 */       Pair<LightMapPack[], Integer> pair = parseLightMapPacks();
/*  222 */       lightMapPacks = (LightMapPack[])pair.getLeft();
/*  223 */       lightmapMinDimensionId = ((Integer)pair.getRight()).intValue();
/*  224 */       readColorProperties("mcpatcher/color.properties");
/*  225 */       blockColormaps = readBlockColormaps(new String[] { String.valueOf(s) + "custom/", String.valueOf(s) + "blocks/" }, colorsBlockColormaps, 256, 256);
/*  226 */       updateUseDefaultGrassFoliageColors();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String getValidProperty(String fileName, String key, String[] validValues, String valDef) {
/*      */     try {
/*  232 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  233 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  235 */       if (inputstream == null) {
/*  236 */         return valDef;
/*      */       }
/*  238 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  239 */       propertiesOrdered.load(inputstream);
/*  240 */       inputstream.close();
/*  241 */       String s = propertiesOrdered.getProperty(key);
/*      */       
/*  243 */       if (s == null) {
/*  244 */         return valDef;
/*      */       }
/*  246 */       List<String> list = Arrays.asList(validValues);
/*      */       
/*  248 */       if (!list.contains(s)) {
/*  249 */         warn("Invalid value: " + key + "=" + s);
/*  250 */         warn("Expected values: " + Config.arrayToString((Object[])validValues));
/*  251 */         return valDef;
/*      */       } 
/*  253 */       dbg(key + "=" + s);
/*  254 */       return s;
/*      */ 
/*      */     
/*      */     }
/*  258 */     catch (FileNotFoundException var9) {
/*  259 */       return valDef;
/*  260 */     } catch (IOException ioexception) {
/*  261 */       ioexception.printStackTrace();
/*  262 */       return valDef;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
/*  267 */     String s = "mcpatcher/lightmap/world";
/*  268 */     String s1 = ".png";
/*  269 */     String[] astring = ResUtils.collectFiles(s, s1);
/*  270 */     Map<Integer, String> map = new HashMap<>();
/*      */     
/*  272 */     for (int i = 0; i < astring.length; i++) {
/*  273 */       String s2 = astring[i];
/*  274 */       String s3 = StrUtils.removePrefixSuffix(s2, s, s1);
/*  275 */       int j = Config.parseInt(s3, -2147483648);
/*      */       
/*  277 */       if (j == Integer.MIN_VALUE) {
/*  278 */         warn("Invalid dimension ID: " + s3 + ", path: " + s2);
/*      */       } else {
/*  280 */         map.put(Integer.valueOf(j), s2);
/*      */       } 
/*      */     } 
/*      */     
/*  284 */     Set<Integer> set = map.keySet();
/*  285 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/*  286 */     Arrays.sort((Object[])ainteger);
/*      */     
/*  288 */     if (ainteger.length <= 0) {
/*  289 */       return (Pair<LightMapPack[], Integer>)new ImmutablePair(null, Integer.valueOf(0));
/*      */     }
/*  291 */     int j1 = ainteger[0].intValue();
/*  292 */     int k1 = ainteger[ainteger.length - 1].intValue();
/*  293 */     int k = k1 - j1 + 1;
/*  294 */     CustomColormap[] acustomcolormap = new CustomColormap[k];
/*      */     
/*  296 */     for (int l = 0; l < ainteger.length; l++) {
/*  297 */       Integer integer = ainteger[l];
/*  298 */       String s4 = map.get(integer);
/*  299 */       CustomColormap customcolormap = getCustomColors(s4, -1, -1);
/*      */       
/*  301 */       if (customcolormap != null) {
/*  302 */         if (customcolormap.getWidth() < 16) {
/*  303 */           warn("Invalid lightmap width: " + customcolormap.getWidth() + ", path: " + s4);
/*      */         } else {
/*  305 */           int i1 = integer.intValue() - j1;
/*  306 */           acustomcolormap[i1] = customcolormap;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  311 */     LightMapPack[] alightmappack = new LightMapPack[acustomcolormap.length];
/*      */     
/*  313 */     for (int l1 = 0; l1 < acustomcolormap.length; l1++) {
/*  314 */       CustomColormap customcolormap3 = acustomcolormap[l1];
/*      */       
/*  316 */       if (customcolormap3 != null) {
/*  317 */         String s5 = customcolormap3.name;
/*  318 */         String s6 = customcolormap3.basePath;
/*  319 */         CustomColormap customcolormap1 = getCustomColors(String.valueOf(s6) + "/" + s5 + "_rain.png", -1, -1);
/*  320 */         CustomColormap customcolormap2 = getCustomColors(String.valueOf(s6) + "/" + s5 + "_thunder.png", -1, -1);
/*  321 */         LightMap lightmap = new LightMap(customcolormap3);
/*  322 */         LightMap lightmap1 = (customcolormap1 != null) ? new LightMap(customcolormap1) : null;
/*  323 */         LightMap lightmap2 = (customcolormap2 != null) ? new LightMap(customcolormap2) : null;
/*  324 */         LightMapPack lightmappack = new LightMapPack(lightmap, lightmap1, lightmap2);
/*  325 */         alightmappack[l1] = lightmappack;
/*      */       } 
/*      */     } 
/*      */     
/*  329 */     return (Pair<LightMapPack[], Integer>)new ImmutablePair(alightmappack, Integer.valueOf(j1));
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getTextureHeight(String path, int defHeight) {
/*      */     try {
/*  335 */       InputStream inputstream = Config.getResourceStream(new ResourceLocation(path));
/*      */       
/*  337 */       if (inputstream == null) {
/*  338 */         return defHeight;
/*      */       }
/*  340 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*  341 */       inputstream.close();
/*  342 */       return (bufferedimage == null) ? defHeight : bufferedimage.getHeight();
/*      */     }
/*  344 */     catch (IOException var4) {
/*  345 */       return defHeight;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void readColorProperties(String fileName) {
/*      */     try {
/*  351 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  352 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */       
/*  354 */       if (inputstream == null) {
/*      */         return;
/*      */       }
/*      */       
/*  358 */       dbg("Loading " + fileName);
/*  359 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  360 */       propertiesOrdered.load(inputstream);
/*  361 */       inputstream.close();
/*  362 */       particleWaterColor = readColor((Properties)propertiesOrdered, new String[] { "particle.water", "drop.water" });
/*  363 */       particlePortalColor = readColor((Properties)propertiesOrdered, "particle.portal");
/*  364 */       lilyPadColor = readColor((Properties)propertiesOrdered, "lilypad");
/*  365 */       expBarTextColor = readColor((Properties)propertiesOrdered, "text.xpbar");
/*  366 */       bossTextColor = readColor((Properties)propertiesOrdered, "text.boss");
/*  367 */       signTextColor = readColor((Properties)propertiesOrdered, "text.sign");
/*  368 */       fogColorNether = readColorVec3((Properties)propertiesOrdered, "fog.nether");
/*  369 */       fogColorEnd = readColorVec3((Properties)propertiesOrdered, "fog.end");
/*  370 */       skyColorEnd = readColorVec3((Properties)propertiesOrdered, "sky.end");
/*  371 */       colorsBlockColormaps = readCustomColormaps((Properties)propertiesOrdered, fileName);
/*  372 */       spawnEggPrimaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.shell.", "Spawn egg shell");
/*  373 */       spawnEggSecondaryColors = readSpawnEggColors((Properties)propertiesOrdered, fileName, "egg.spots.", "Spawn egg spot");
/*  374 */       wolfCollarColors = readDyeColors((Properties)propertiesOrdered, fileName, "collar.", "Wolf collar");
/*  375 */       sheepColors = readDyeColors((Properties)propertiesOrdered, fileName, "sheep.", "Sheep");
/*  376 */       textColors = readTextColors((Properties)propertiesOrdered, fileName, "text.code.", "Text");
/*  377 */       int[] aint = readMapColors((Properties)propertiesOrdered, fileName, "map.", "Map");
/*      */       
/*  379 */       if (aint != null) {
/*  380 */         if (mapColorsOriginal == null) {
/*  381 */           mapColorsOriginal = getMapColors();
/*      */         }
/*      */         
/*  384 */         setMapColors(aint);
/*      */       } 
/*      */       
/*  387 */       potionColors = readPotionColors((Properties)propertiesOrdered, fileName, "potion.", "Potion");
/*  388 */       xpOrbTime = Config.parseInt(propertiesOrdered.getProperty("xporb.time"), -1);
/*  389 */     } catch (FileNotFoundException var5) {
/*      */       return;
/*  391 */     } catch (IOException ioexception) {
/*  392 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static CustomColormap[] readCustomColormaps(Properties props, String fileName) {
/*  397 */     List<CustomColormap> list = new ArrayList();
/*  398 */     String s = "palette.block.";
/*  399 */     Map<Object, Object> map = new HashMap<>();
/*      */     
/*  401 */     for (Object e : props.keySet()) {
/*  402 */       String s1 = (String)e;
/*  403 */       String s2 = props.getProperty(s1);
/*      */       
/*  405 */       if (s1.startsWith(s)) {
/*  406 */         map.put(s1, s2);
/*      */       }
/*      */     } 
/*      */     
/*  410 */     String[] astring = (String[])map.keySet().toArray((Object[])new String[map.size()]);
/*      */     
/*  412 */     for (int j = 0; j < astring.length; j++) {
/*  413 */       String s6 = astring[j];
/*  414 */       String s3 = props.getProperty(s6);
/*  415 */       dbg("Block palette: " + s6 + " = " + s3);
/*  416 */       String s4 = s6.substring(s.length());
/*  417 */       String s5 = TextureUtils.getBasePath(fileName);
/*  418 */       s4 = TextureUtils.fixResourcePath(s4, s5);
/*  419 */       CustomColormap customcolormap = getCustomColors(s4, 256, 256);
/*      */       
/*  421 */       if (customcolormap == null) {
/*  422 */         warn("Colormap not found: " + s4);
/*      */       } else {
/*  424 */         ConnectedParser connectedparser = new ConnectedParser("CustomColors");
/*  425 */         MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s3);
/*      */         
/*  427 */         if (amatchblock != null && amatchblock.length > 0) {
/*  428 */           for (int i = 0; i < amatchblock.length; i++) {
/*  429 */             MatchBlock matchblock = amatchblock[i];
/*  430 */             customcolormap.addMatchBlock(matchblock);
/*      */           } 
/*      */           
/*  433 */           list.add(customcolormap);
/*      */         } else {
/*  435 */           warn("Invalid match blocks: " + s3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  440 */     if (list.size() <= 0) {
/*  441 */       return null;
/*      */     }
/*  443 */     CustomColormap[] acustomcolormap = list.<CustomColormap>toArray(new CustomColormap[list.size()]);
/*  444 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap[][] readBlockColormaps(String[] basePaths, CustomColormap[] basePalettes, int width, int height) {
/*  449 */     String[] astring = ResUtils.collectFiles(basePaths, new String[] { ".properties" });
/*  450 */     Arrays.sort((Object[])astring);
/*  451 */     List list = new ArrayList();
/*      */     
/*  453 */     for (int i = 0; i < astring.length; i++) {
/*  454 */       String s = astring[i];
/*  455 */       dbg("Block colormap: " + s);
/*      */       
/*      */       try {
/*  458 */         ResourceLocation resourcelocation = new ResourceLocation("minecraft", s);
/*  459 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*      */         
/*  461 */         if (inputstream == null) {
/*  462 */           warn("File not found: " + s);
/*      */         } else {
/*  464 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  465 */           propertiesOrdered.load(inputstream);
/*  466 */           inputstream.close();
/*  467 */           CustomColormap customcolormap = new CustomColormap((Properties)propertiesOrdered, s, width, height, paletteFormatDefault);
/*      */           
/*  469 */           if (customcolormap.isValid(s) && customcolormap.isValidMatchBlocks(s)) {
/*  470 */             addToBlockList(customcolormap, list);
/*      */           }
/*      */         } 
/*  473 */       } catch (FileNotFoundException var12) {
/*  474 */         warn("File not found: " + s);
/*  475 */       } catch (Exception exception) {
/*  476 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/*  480 */     if (basePalettes != null) {
/*  481 */       for (int j = 0; j < basePalettes.length; j++) {
/*  482 */         CustomColormap customcolormap1 = basePalettes[j];
/*  483 */         addToBlockList(customcolormap1, list);
/*      */       } 
/*      */     }
/*      */     
/*  487 */     if (list.size() <= 0) {
/*  488 */       return null;
/*      */     }
/*  490 */     CustomColormap[][] acustomcolormap = blockListToArray(list);
/*  491 */     return acustomcolormap;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToBlockList(CustomColormap cm, List blockList) {
/*  496 */     int[] aint = cm.getMatchBlockIds();
/*      */     
/*  498 */     if (aint != null && aint.length > 0) {
/*  499 */       for (int i = 0; i < aint.length; i++) {
/*  500 */         int j = aint[i];
/*      */         
/*  502 */         if (j < 0) {
/*  503 */           warn("Invalid block ID: " + j);
/*      */         } else {
/*  505 */           addToList(cm, blockList, j);
/*      */         } 
/*      */       } 
/*      */     } else {
/*  509 */       warn("No match blocks: " + Config.arrayToString(aint));
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void addToList(CustomColormap cm, List<List> list, int id) {
/*  514 */     while (id >= list.size()) {
/*  515 */       list.add(null);
/*      */     }
/*      */     
/*  518 */     List<CustomColormap> list1 = list.get(id);
/*      */     
/*  520 */     if (list1 == null) {
/*  521 */       list1 = new ArrayList();
/*  522 */       list.set(id, list1);
/*      */     } 
/*      */     
/*  525 */     list1.add(cm);
/*      */   }
/*      */   
/*      */   private static CustomColormap[][] blockListToArray(List<List> list) {
/*  529 */     CustomColormap[][] acustomcolormap = new CustomColormap[list.size()][];
/*      */     
/*  531 */     for (int i = 0; i < list.size(); i++) {
/*  532 */       List list1 = list.get(i);
/*      */       
/*  534 */       if (list1 != null) {
/*  535 */         CustomColormap[] acustomcolormap1 = (CustomColormap[])list1.toArray((Object[])new CustomColormap[list1.size()]);
/*  536 */         acustomcolormap[i] = acustomcolormap1;
/*      */       } 
/*      */     } 
/*      */     
/*  540 */     return acustomcolormap;
/*      */   }
/*      */   
/*      */   private static int readColor(Properties props, String[] names) {
/*  544 */     for (int i = 0; i < names.length; i++) {
/*  545 */       String s = names[i];
/*  546 */       int j = readColor(props, s);
/*      */       
/*  548 */       if (j >= 0) {
/*  549 */         return j;
/*      */       }
/*      */     } 
/*      */     
/*  553 */     return -1;
/*      */   }
/*      */   
/*      */   private static int readColor(Properties props, String name) {
/*  557 */     String s = props.getProperty(name);
/*      */     
/*  559 */     if (s == null) {
/*  560 */       return -1;
/*      */     }
/*  562 */     s = s.trim();
/*  563 */     int i = parseColor(s);
/*      */     
/*  565 */     if (i < 0) {
/*  566 */       warn("Invalid color: " + name + " = " + s);
/*  567 */       return i;
/*      */     } 
/*  569 */     dbg(String.valueOf(name) + " = " + s);
/*  570 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int parseColor(String str) {
/*  576 */     if (str == null) {
/*  577 */       return -1;
/*      */     }
/*  579 */     str = str.trim();
/*      */     
/*      */     try {
/*  582 */       int i = Integer.parseInt(str, 16) & 0xFFFFFF;
/*  583 */       return i;
/*  584 */     } catch (NumberFormatException var2) {
/*  585 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 readColorVec3(Properties props, String name) {
/*  591 */     int i = readColor(props, name);
/*      */     
/*  593 */     if (i < 0) {
/*  594 */       return null;
/*      */     }
/*  596 */     int j = i >> 16 & 0xFF;
/*  597 */     int k = i >> 8 & 0xFF;
/*  598 */     int l = i & 0xFF;
/*  599 */     float f = j / 255.0F;
/*  600 */     float f1 = k / 255.0F;
/*  601 */     float f2 = l / 255.0F;
/*  602 */     return new Vec3(f, f1, f2);
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomColormap getCustomColors(String basePath, String[] paths, int width, int height) {
/*  607 */     for (int i = 0; i < paths.length; i++) {
/*  608 */       String s = paths[i];
/*  609 */       s = String.valueOf(basePath) + s;
/*  610 */       CustomColormap customcolormap = getCustomColors(s, width, height);
/*      */       
/*  612 */       if (customcolormap != null) {
/*  613 */         return customcolormap;
/*      */       }
/*      */     } 
/*      */     
/*  617 */     return null;
/*      */   }
/*      */   
/*      */   public static CustomColormap getCustomColors(String pathImage, int width, int height) {
/*      */     try {
/*  622 */       ResourceLocation resourcelocation = new ResourceLocation(pathImage);
/*      */       
/*  624 */       if (!Config.hasResource(resourcelocation)) {
/*  625 */         return null;
/*      */       }
/*  627 */       dbg("Colormap " + pathImage);
/*  628 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  629 */       String s = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
/*  630 */       ResourceLocation resourcelocation1 = new ResourceLocation(s);
/*      */       
/*  632 */       if (Config.hasResource(resourcelocation1)) {
/*  633 */         InputStream inputstream = Config.getResourceStream(resourcelocation1);
/*  634 */         propertiesOrdered.load(inputstream);
/*  635 */         inputstream.close();
/*  636 */         dbg("Colormap properties: " + s);
/*      */       } else {
/*  638 */         propertiesOrdered.put("format", paletteFormatDefault);
/*  639 */         propertiesOrdered.put("source", pathImage);
/*  640 */         s = pathImage;
/*      */       } 
/*      */       
/*  643 */       CustomColormap customcolormap = new CustomColormap((Properties)propertiesOrdered, s, width, height, paletteFormatDefault);
/*  644 */       return !customcolormap.isValid(s) ? null : customcolormap;
/*      */     }
/*  646 */     catch (Exception exception) {
/*  647 */       exception.printStackTrace();
/*  648 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateUseDefaultGrassFoliageColors() {
/*  653 */     useDefaultGrassFoliageColors = (foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && Config.isSwampColors() && Config.isSmoothBiomes());
/*      */   }
/*      */   public static int getColorMultiplier(BakedQuad quad, IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
/*      */     IColorizer customcolors$icolorizer;
/*  657 */     Block block = blockState.getBlock();
/*  658 */     IBlockState iblockstate = renderEnv.getBlockState();
/*      */     
/*  660 */     if (blockColormaps != null) {
/*  661 */       if (!quad.hasTintIndex()) {
/*  662 */         if (block == Blocks.grass) {
/*  663 */           iblockstate = BLOCK_STATE_DIRT;
/*      */         }
/*      */         
/*  666 */         if (block == Blocks.redstone_wire) {
/*  667 */           return -1;
/*      */         }
/*      */       } 
/*      */       
/*  671 */       if (block == Blocks.double_plant && renderEnv.getMetadata() >= 8) {
/*  672 */         blockPos = blockPos.down();
/*  673 */         iblockstate = blockAccess.getBlockState(blockPos);
/*      */       } 
/*      */       
/*  676 */       CustomColormap customcolormap = getBlockColormap(iblockstate);
/*      */       
/*  678 */       if (customcolormap != null) {
/*  679 */         if (Config.isSmoothBiomes() && !customcolormap.isColorConstant()) {
/*  680 */           return getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolormap, renderEnv.getColorizerBlockPosM());
/*      */         }
/*      */         
/*  683 */         return customcolormap.getColor(blockAccess, blockPos);
/*      */       } 
/*      */     } 
/*      */     
/*  687 */     if (!quad.hasTintIndex())
/*  688 */       return -1; 
/*  689 */     if (block == Blocks.waterlily)
/*  690 */       return getLilypadColorMultiplier(blockAccess, blockPos); 
/*  691 */     if (block == Blocks.redstone_wire)
/*  692 */       return getRedstoneColor(renderEnv.getBlockState()); 
/*  693 */     if (block instanceof net.minecraft.block.BlockStem)
/*  694 */       return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv); 
/*  695 */     if (useDefaultGrassFoliageColors) {
/*  696 */       return -1;
/*      */     }
/*  698 */     int i = renderEnv.getMetadata();
/*      */ 
/*      */     
/*  701 */     if (block != Blocks.grass && block != Blocks.tallgrass && block != Blocks.double_plant) {
/*  702 */       if (block == Blocks.double_plant) {
/*  703 */         customcolors$icolorizer = COLORIZER_GRASS;
/*      */         
/*  705 */         if (i >= 8) {
/*  706 */           blockPos = blockPos.down();
/*      */         }
/*  708 */       } else if (block == Blocks.leaves) {
/*  709 */         switch (i & 0x3) {
/*      */           case 0:
/*  711 */             customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */             break;
/*      */           
/*      */           case 1:
/*  715 */             customcolors$icolorizer = COLORIZER_FOLIAGE_PINE;
/*      */             break;
/*      */           
/*      */           case 2:
/*  719 */             customcolors$icolorizer = COLORIZER_FOLIAGE_BIRCH;
/*      */             break;
/*      */           
/*      */           default:
/*  723 */             customcolors$icolorizer = COLORIZER_FOLIAGE; break;
/*      */         } 
/*  725 */       } else if (block == Blocks.leaves2) {
/*  726 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       } else {
/*  728 */         if (block != Blocks.vine) {
/*  729 */           return -1;
/*      */         }
/*      */         
/*  732 */         customcolors$icolorizer = COLORIZER_FOLIAGE;
/*      */       } 
/*      */     } else {
/*  735 */       customcolors$icolorizer = COLORIZER_GRASS;
/*      */     } 
/*      */     
/*  738 */     return (Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolors$icolorizer, renderEnv.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(iblockstate, blockAccess, blockPos);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static BiomeGenBase getColorBiome(IBlockAccess blockAccess, BlockPos blockPos) {
/*  743 */     BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(blockPos);
/*      */     
/*  745 */     if (biomegenbase == BiomeGenBase.swampland && !Config.isSwampColors()) {
/*  746 */       biomegenbase = BiomeGenBase.plains;
/*      */     }
/*      */     
/*  749 */     return biomegenbase;
/*      */   }
/*      */   
/*      */   private static CustomColormap getBlockColormap(IBlockState blockState) {
/*  753 */     if (blockColormaps == null)
/*  754 */       return null; 
/*  755 */     if (!(blockState instanceof BlockStateBase)) {
/*  756 */       return null;
/*      */     }
/*  758 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  759 */     int i = blockstatebase.getBlockId();
/*      */     
/*  761 */     if (i >= 0 && i < blockColormaps.length) {
/*  762 */       CustomColormap[] acustomcolormap = blockColormaps[i];
/*      */       
/*  764 */       if (acustomcolormap == null) {
/*  765 */         return null;
/*      */       }
/*  767 */       for (int j = 0; j < acustomcolormap.length; j++) {
/*  768 */         CustomColormap customcolormap = acustomcolormap[j];
/*      */         
/*  770 */         if (customcolormap.matchesBlock(blockstatebase)) {
/*  771 */           return customcolormap;
/*      */         }
/*      */       } 
/*      */       
/*  775 */       return null;
/*      */     } 
/*      */     
/*  778 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getSmoothColorMultiplier(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos, IColorizer colorizer, BlockPosM blockPosM) {
/*  784 */     int i = 0;
/*  785 */     int j = 0;
/*  786 */     int k = 0;
/*  787 */     int l = blockPos.getX();
/*  788 */     int i1 = blockPos.getY();
/*  789 */     int j1 = blockPos.getZ();
/*  790 */     BlockPosM blockposm = blockPosM;
/*      */     
/*  792 */     for (int k1 = l - 1; k1 <= l + 1; k1++) {
/*  793 */       for (int l1 = j1 - 1; l1 <= j1 + 1; l1++) {
/*  794 */         blockposm.setXyz(k1, i1, l1);
/*  795 */         int i2 = colorizer.getColor(blockState, blockAccess, blockposm);
/*  796 */         i += i2 >> 16 & 0xFF;
/*  797 */         j += i2 >> 8 & 0xFF;
/*  798 */         k += i2 & 0xFF;
/*      */       } 
/*      */     } 
/*      */     
/*  802 */     int j2 = i / 9;
/*  803 */     int k2 = j / 9;
/*  804 */     int l2 = k / 9;
/*  805 */     return j2 << 16 | k2 << 8 | l2;
/*      */   }
/*      */   
/*      */   public static int getFluidColor(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, RenderEnv renderEnv) {
/*  809 */     Block block = blockState.getBlock();
/*  810 */     IColorizer customcolors$icolorizer = getBlockColormap(blockState);
/*      */     
/*  812 */     if (customcolors$icolorizer == null && blockState.getBlock().getMaterial() == Material.water) {
/*  813 */       customcolors$icolorizer = COLORIZER_WATER;
/*      */     }
/*      */     
/*  816 */     return (customcolors$icolorizer == null) ? block.colorMultiplier(blockAccess, blockPos, 0) : ((Config.isSmoothBiomes() && !customcolors$icolorizer.isColorConstant()) ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, customcolors$icolorizer, renderEnv.getColorizerBlockPosM()) : customcolors$icolorizer.getColor(blockState, blockAccess, blockPos));
/*      */   }
/*      */   
/*      */   public static void updatePortalFX(EntityFX fx) {
/*  820 */     if (particlePortalColor >= 0) {
/*  821 */       int i = particlePortalColor;
/*  822 */       int j = i >> 16 & 0xFF;
/*  823 */       int k = i >> 8 & 0xFF;
/*  824 */       int l = i & 0xFF;
/*  825 */       float f = j / 255.0F;
/*  826 */       float f1 = k / 255.0F;
/*  827 */       float f2 = l / 255.0F;
/*  828 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateMyceliumFX(EntityFX fx) {
/*  833 */     if (myceliumParticleColors != null) {
/*  834 */       int i = myceliumParticleColors.getColorRandom();
/*  835 */       int j = i >> 16 & 0xFF;
/*  836 */       int k = i >> 8 & 0xFF;
/*  837 */       int l = i & 0xFF;
/*  838 */       float f = j / 255.0F;
/*  839 */       float f1 = k / 255.0F;
/*  840 */       float f2 = l / 255.0F;
/*  841 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getRedstoneColor(IBlockState blockState) {
/*  846 */     if (redstoneColors == null) {
/*  847 */       return -1;
/*      */     }
/*  849 */     int i = getRedstoneLevel(blockState, 15);
/*  850 */     int j = redstoneColors.getColor(i);
/*  851 */     return j;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateReddustFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z) {
/*  856 */     if (redstoneColors != null) {
/*  857 */       IBlockState iblockstate = blockAccess.getBlockState(new BlockPos(x, y, z));
/*  858 */       int i = getRedstoneLevel(iblockstate, 15);
/*  859 */       int j = redstoneColors.getColor(i);
/*  860 */       int k = j >> 16 & 0xFF;
/*  861 */       int l = j >> 8 & 0xFF;
/*  862 */       int i1 = j & 0xFF;
/*  863 */       float f = k / 255.0F;
/*  864 */       float f1 = l / 255.0F;
/*  865 */       float f2 = i1 / 255.0F;
/*  866 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getRedstoneLevel(IBlockState state, int def) {
/*  871 */     Block block = state.getBlock();
/*      */     
/*  873 */     if (!(block instanceof BlockRedstoneWire)) {
/*  874 */       return def;
/*      */     }
/*  876 */     Object object = state.getValue((IProperty)BlockRedstoneWire.POWER);
/*      */     
/*  878 */     if (!(object instanceof Integer)) {
/*  879 */       return def;
/*      */     }
/*  881 */     Integer integer = (Integer)object;
/*  882 */     return integer.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getXpOrbTimer(float timer) {
/*  888 */     if (xpOrbTime <= 0) {
/*  889 */       return timer;
/*      */     }
/*  891 */     float f = 628.0F / xpOrbTime;
/*  892 */     return timer * f;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getXpOrbColor(float timer) {
/*  897 */     if (xpOrbColors == null) {
/*  898 */       return -1;
/*      */     }
/*  900 */     int i = (int)Math.round(((MathHelper.sin(timer) + 1.0F) * (xpOrbColors.getLength() - 1)) / 2.0D);
/*  901 */     int j = xpOrbColors.getColor(i);
/*  902 */     return j;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getDurabilityColor(int dur255) {
/*  907 */     if (durabilityColors == null) {
/*  908 */       return -1;
/*      */     }
/*  910 */     int i = dur255 * durabilityColors.getLength() / 255;
/*  911 */     int j = durabilityColors.getColor(i);
/*  912 */     return j;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateWaterFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z, RenderEnv renderEnv) {
/*  917 */     if (waterColors != null || blockColormaps != null || particleWaterColor >= 0) {
/*  918 */       BlockPos blockpos = new BlockPos(x, y, z);
/*  919 */       renderEnv.reset(BLOCK_STATE_WATER, blockpos);
/*  920 */       int i = getFluidColor(blockAccess, BLOCK_STATE_WATER, blockpos, renderEnv);
/*  921 */       int j = i >> 16 & 0xFF;
/*  922 */       int k = i >> 8 & 0xFF;
/*  923 */       int l = i & 0xFF;
/*  924 */       float f = j / 255.0F;
/*  925 */       float f1 = k / 255.0F;
/*  926 */       float f2 = l / 255.0F;
/*      */       
/*  928 */       if (particleWaterColor >= 0) {
/*  929 */         int i1 = particleWaterColor >> 16 & 0xFF;
/*  930 */         int j1 = particleWaterColor >> 8 & 0xFF;
/*  931 */         int k1 = particleWaterColor & 0xFF;
/*  932 */         f *= i1 / 255.0F;
/*  933 */         f1 *= j1 / 255.0F;
/*  934 */         f2 *= k1 / 255.0F;
/*      */       } 
/*      */       
/*  937 */       fx.setRBGColorF(f, f1, f2);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getLilypadColorMultiplier(IBlockAccess blockAccess, BlockPos blockPos) {
/*  942 */     return (lilyPadColor < 0) ? Blocks.waterlily.colorMultiplier(blockAccess, blockPos) : lilyPadColor;
/*      */   }
/*      */   
/*      */   private static Vec3 getFogColorNether(Vec3 col) {
/*  946 */     return (fogColorNether == null) ? col : fogColorNether;
/*      */   }
/*      */   
/*      */   private static Vec3 getFogColorEnd(Vec3 col) {
/*  950 */     return (fogColorEnd == null) ? col : fogColorEnd;
/*      */   }
/*      */   
/*      */   private static Vec3 getSkyColorEnd(Vec3 col) {
/*  954 */     return (skyColorEnd == null) ? col : skyColorEnd;
/*      */   }
/*      */   
/*      */   public static Vec3 getSkyColor(Vec3 skyColor3d, IBlockAccess blockAccess, double x, double y, double z) {
/*  958 */     if (skyColors == null) {
/*  959 */       return skyColor3d;
/*      */     }
/*  961 */     int i = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
/*  962 */     int j = i >> 16 & 0xFF;
/*  963 */     int k = i >> 8 & 0xFF;
/*  964 */     int l = i & 0xFF;
/*  965 */     float f = j / 255.0F;
/*  966 */     float f1 = k / 255.0F;
/*  967 */     float f2 = l / 255.0F;
/*  968 */     float f3 = (float)skyColor3d.xCoord / 0.5F;
/*  969 */     float f4 = (float)skyColor3d.yCoord / 0.66275F;
/*  970 */     float f5 = (float)skyColor3d.zCoord;
/*  971 */     f *= f3;
/*  972 */     f1 *= f4;
/*  973 */     f2 *= f5;
/*  974 */     Vec3 vec3 = skyColorFader.getColor(f, f1, f2);
/*  975 */     return vec3;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Vec3 getFogColor(Vec3 fogColor3d, IBlockAccess blockAccess, double x, double y, double z) {
/*  980 */     if (fogColors == null) {
/*  981 */       return fogColor3d;
/*      */     }
/*  983 */     int i = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
/*  984 */     int j = i >> 16 & 0xFF;
/*  985 */     int k = i >> 8 & 0xFF;
/*  986 */     int l = i & 0xFF;
/*  987 */     float f = j / 255.0F;
/*  988 */     float f1 = k / 255.0F;
/*  989 */     float f2 = l / 255.0F;
/*  990 */     float f3 = (float)fogColor3d.xCoord / 0.753F;
/*  991 */     float f4 = (float)fogColor3d.yCoord / 0.8471F;
/*  992 */     float f5 = (float)fogColor3d.zCoord;
/*  993 */     f *= f3;
/*  994 */     f1 *= f4;
/*  995 */     f2 *= f5;
/*  996 */     Vec3 vec3 = fogColorFader.getColor(f, f1, f2);
/*  997 */     return vec3;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getUnderwaterColor(IBlockAccess blockAccess, double x, double y, double z) {
/* 1002 */     return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
/*      */   }
/*      */   
/*      */   public static Vec3 getUnderlavaColor(IBlockAccess blockAccess, double x, double y, double z) {
/* 1006 */     return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
/*      */   }
/*      */   
/*      */   public static Vec3 getUnderFluidColor(IBlockAccess blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader) {
/* 1010 */     if (underFluidColors == null) {
/* 1011 */       return null;
/*      */     }
/* 1013 */     int i = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
/* 1014 */     int j = i >> 16 & 0xFF;
/* 1015 */     int k = i >> 8 & 0xFF;
/* 1016 */     int l = i & 0xFF;
/* 1017 */     float f = j / 255.0F;
/* 1018 */     float f1 = k / 255.0F;
/* 1019 */     float f2 = l / 255.0F;
/* 1020 */     Vec3 vec3 = underFluidColorFader.getColor(f, f1, f2);
/* 1021 */     return vec3;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getStemColorMultiplier(Block blockStem, IBlockAccess blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
/* 1026 */     CustomColormap customcolormap = stemColors;
/*      */     
/* 1028 */     if (blockStem == Blocks.pumpkin_stem && stemPumpkinColors != null) {
/* 1029 */       customcolormap = stemPumpkinColors;
/*      */     }
/*      */     
/* 1032 */     if (blockStem == Blocks.melon_stem && stemMelonColors != null) {
/* 1033 */       customcolormap = stemMelonColors;
/*      */     }
/*      */     
/* 1036 */     if (customcolormap == null) {
/* 1037 */       return -1;
/*      */     }
/* 1039 */     int i = renderEnv.getMetadata();
/* 1040 */     return customcolormap.getColor(i);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision, float partialTicks) {
/* 1045 */     if (world == null)
/* 1046 */       return false; 
/* 1047 */     if (lightMapPacks == null) {
/* 1048 */       return false;
/*      */     }
/* 1050 */     int i = world.provider.getDimensionId();
/* 1051 */     int j = i - lightmapMinDimensionId;
/*      */     
/* 1053 */     if (j >= 0 && j < lightMapPacks.length) {
/* 1054 */       LightMapPack lightmappack = lightMapPacks[j];
/* 1055 */       return (lightmappack == null) ? false : lightmappack.updateLightmap(world, torchFlickerX, lmColors, nightvision, partialTicks);
/*      */     } 
/* 1057 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Vec3 getWorldFogColor(Vec3 fogVec, World world, Entity renderViewEntity, float partialTicks) {
/*      */     Minecraft minecraft;
/* 1063 */     int i = world.provider.getDimensionId();
/*      */     
/* 1065 */     switch (i) {
/*      */       case -1:
/* 1067 */         fogVec = getFogColorNether(fogVec);
/*      */         break;
/*      */       
/*      */       case 0:
/* 1071 */         minecraft = Minecraft.getMinecraft();
/* 1072 */         fogVec = getFogColor(fogVec, (IBlockAccess)minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D, renderViewEntity.posZ);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1076 */         fogVec = getFogColorEnd(fogVec);
/*      */         break;
/*      */     } 
/* 1079 */     return fogVec;
/*      */   }
/*      */   public static Vec3 getWorldSkyColor(Vec3 skyVec, World world, Entity renderViewEntity, float partialTicks) {
/*      */     Minecraft minecraft;
/* 1083 */     int i = world.provider.getDimensionId();
/*      */     
/* 1085 */     switch (i) {
/*      */       case 0:
/* 1087 */         minecraft = Minecraft.getMinecraft();
/* 1088 */         skyVec = getSkyColor(skyVec, (IBlockAccess)minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D, renderViewEntity.posZ);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1092 */         skyVec = getSkyColorEnd(skyVec);
/*      */         break;
/*      */     } 
/* 1095 */     return skyVec;
/*      */   }
/*      */   
/*      */   private static int[] readSpawnEggColors(Properties props, String fileName, String prefix, String logName) {
/* 1099 */     List<Integer> list = new ArrayList<>();
/* 1100 */     Set set = props.keySet();
/* 1101 */     int i = 0;
/*      */     
/* 1103 */     for (Object e : set) {
/* 1104 */       String s = (String)e;
/* 1105 */       String s1 = props.getProperty(s);
/*      */       
/* 1107 */       if (s.startsWith(prefix)) {
/* 1108 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1109 */         int j = EntityUtils.getEntityIdByName(s2);
/*      */         
/* 1111 */         if (j < 0) {
/* 1112 */           warn("Invalid spawn egg name: " + s); continue;
/*      */         } 
/* 1114 */         int k = parseColor(s1);
/*      */         
/* 1116 */         if (k < 0) {
/* 1117 */           warn("Invalid spawn egg color: " + s + " = " + s1); continue;
/*      */         } 
/* 1119 */         while (list.size() <= j) {
/* 1120 */           list.add(Integer.valueOf(-1));
/*      */         }
/*      */         
/* 1123 */         list.set(j, Integer.valueOf(k));
/* 1124 */         i++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     if (i <= 0) {
/* 1131 */       return null;
/*      */     }
/* 1133 */     dbg(String.valueOf(logName) + " colors: " + i);
/* 1134 */     int[] aint = new int[list.size()];
/*      */     
/* 1136 */     for (int l = 0; l < aint.length; l++) {
/* 1137 */       aint[l] = ((Integer)list.get(l)).intValue();
/*      */     }
/*      */     
/* 1140 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getSpawnEggColor(ItemMonsterPlacer item, ItemStack itemStack, int layer, int color) {
/* 1145 */     int i = itemStack.getMetadata();
/* 1146 */     int[] aint = (layer == 0) ? spawnEggPrimaryColors : spawnEggSecondaryColors;
/*      */     
/* 1148 */     if (aint == null)
/* 1149 */       return color; 
/* 1150 */     if (i >= 0 && i < aint.length) {
/* 1151 */       int j = aint[i];
/* 1152 */       return (j < 0) ? color : j;
/*      */     } 
/* 1154 */     return color;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getColorFromItemStack(ItemStack itemStack, int layer, int color) {
/* 1159 */     if (itemStack == null) {
/* 1160 */       return color;
/*      */     }
/* 1162 */     Item item = itemStack.getItem();
/* 1163 */     return (item == null) ? color : ((item instanceof ItemMonsterPlacer) ? getSpawnEggColor((ItemMonsterPlacer)item, itemStack, layer, color) : color);
/*      */   }
/*      */ 
/*      */   
/*      */   private static float[][] readDyeColors(Properties props, String fileName, String prefix, String logName) {
/* 1168 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/* 1169 */     Map<String, EnumDyeColor> map = new HashMap<>();
/*      */     
/* 1171 */     for (int i = 0; i < aenumdyecolor.length; i++) {
/* 1172 */       EnumDyeColor enumdyecolor = aenumdyecolor[i];
/* 1173 */       map.put(enumdyecolor.getName(), enumdyecolor);
/*      */     } 
/*      */     
/* 1176 */     float[][] afloat1 = new float[aenumdyecolor.length][];
/* 1177 */     int k = 0;
/*      */     
/* 1179 */     for (Object e : props.keySet()) {
/* 1180 */       String s = (String)e;
/* 1181 */       String s1 = props.getProperty(s);
/*      */       
/* 1183 */       if (s.startsWith(prefix)) {
/* 1184 */         String s2 = StrUtils.removePrefix(s, prefix);
/*      */         
/* 1186 */         if (s2.equals("lightBlue")) {
/* 1187 */           s2 = "light_blue";
/*      */         }
/*      */         
/* 1190 */         EnumDyeColor enumdyecolor1 = map.get(s2);
/* 1191 */         int j = parseColor(s1);
/*      */         
/* 1193 */         if (enumdyecolor1 != null && j >= 0) {
/* 1194 */           float[] afloat = { (j >> 16 & 0xFF) / 255.0F, (j >> 8 & 0xFF) / 255.0F, (j & 0xFF) / 255.0F };
/* 1195 */           afloat1[enumdyecolor1.ordinal()] = afloat;
/* 1196 */           k++; continue;
/*      */         } 
/* 1198 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1203 */     if (k <= 0) {
/* 1204 */       return null;
/*      */     }
/* 1206 */     dbg(String.valueOf(logName) + " colors: " + k);
/* 1207 */     return afloat1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static float[] getDyeColors(EnumDyeColor dye, float[][] dyeColors, float[] colors) {
/* 1212 */     if (dyeColors == null)
/* 1213 */       return colors; 
/* 1214 */     if (dye == null) {
/* 1215 */       return colors;
/*      */     }
/* 1217 */     float[] afloat = dyeColors[dye.ordinal()];
/* 1218 */     return (afloat == null) ? colors : afloat;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float[] getWolfCollarColors(EnumDyeColor dye, float[] colors) {
/* 1223 */     return getDyeColors(dye, wolfCollarColors, colors);
/*      */   }
/*      */   
/*      */   public static float[] getSheepColors(EnumDyeColor dye, float[] colors) {
/* 1227 */     return getDyeColors(dye, sheepColors, colors);
/*      */   }
/*      */   
/*      */   private static int[] readTextColors(Properties props, String fileName, String prefix, String logName) {
/* 1231 */     int[] aint = new int[32];
/* 1232 */     Arrays.fill(aint, -1);
/* 1233 */     int i = 0;
/*      */     
/* 1235 */     for (Object e : props.keySet()) {
/* 1236 */       String s = (String)e;
/* 1237 */       String s1 = props.getProperty(s);
/*      */       
/* 1239 */       if (s.startsWith(prefix)) {
/* 1240 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1241 */         int j = Config.parseInt(s2, -1);
/* 1242 */         int k = parseColor(s1);
/*      */         
/* 1244 */         if (j >= 0 && j < aint.length && k >= 0) {
/* 1245 */           aint[j] = k;
/* 1246 */           i++; continue;
/*      */         } 
/* 1248 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1253 */     if (i <= 0) {
/* 1254 */       return null;
/*      */     }
/* 1256 */     dbg(String.valueOf(logName) + " colors: " + i);
/* 1257 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getTextColor(int index, int color) {
/* 1262 */     if (textColors == null)
/* 1263 */       return color; 
/* 1264 */     if (index >= 0 && index < textColors.length) {
/* 1265 */       int i = textColors[index];
/* 1266 */       return (i < 0) ? color : i;
/*      */     } 
/* 1268 */     return color;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readMapColors(Properties props, String fileName, String prefix, String logName) {
/* 1273 */     int[] aint = new int[MapColor.mapColorArray.length];
/* 1274 */     Arrays.fill(aint, -1);
/* 1275 */     int i = 0;
/*      */     
/* 1277 */     for (Object o : props.keySet()) {
/* 1278 */       String s = (String)o;
/* 1279 */       String s1 = props.getProperty(s);
/*      */       
/* 1281 */       if (s.startsWith(prefix)) {
/* 1282 */         String s2 = StrUtils.removePrefix(s, prefix);
/* 1283 */         int j = getMapColorIndex(s2);
/* 1284 */         int k = parseColor(s1);
/*      */         
/* 1286 */         if (j >= 0 && j < aint.length && k >= 0) {
/* 1287 */           aint[j] = k;
/* 1288 */           i++; continue;
/*      */         } 
/* 1290 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1295 */     if (i <= 0) {
/* 1296 */       return null;
/*      */     }
/* 1298 */     dbg(String.valueOf(logName) + " colors: " + i);
/* 1299 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int[] readPotionColors(Properties props, String fileName, String prefix, String logName) {
/* 1304 */     int[] aint = new int[Potion.potionTypes.length];
/* 1305 */     Arrays.fill(aint, -1);
/* 1306 */     int i = 0;
/*      */     
/* 1308 */     for (Object e : props.keySet()) {
/* 1309 */       String s = (String)e;
/* 1310 */       String s1 = props.getProperty(s);
/*      */       
/* 1312 */       if (s.startsWith(prefix)) {
/* 1313 */         int j = getPotionId(s);
/* 1314 */         int k = parseColor(s1);
/*      */         
/* 1316 */         if (j >= 0 && j < aint.length && k >= 0) {
/* 1317 */           aint[j] = k;
/* 1318 */           i++; continue;
/*      */         } 
/* 1320 */         warn("Invalid color: " + s + " = " + s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1325 */     if (i <= 0) {
/* 1326 */       return null;
/*      */     }
/* 1328 */     dbg(String.valueOf(logName) + " colors: " + i);
/* 1329 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPotionId(String name) {
/* 1334 */     if (name.equals("potion.water")) {
/* 1335 */       return 0;
/*      */     }
/* 1337 */     Potion[] apotion = Potion.potionTypes;
/*      */     
/* 1339 */     for (int i = 0; i < apotion.length; i++) {
/* 1340 */       Potion potion = apotion[i];
/*      */       
/* 1342 */       if (potion != null && potion.getName().equals(name)) {
/* 1343 */         return potion.getId();
/*      */       }
/*      */     } 
/*      */     
/* 1347 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getPotionColor(int potionId, int color) {
/* 1352 */     if (potionColors == null)
/* 1353 */       return color; 
/* 1354 */     if (potionId >= 0 && potionId < potionColors.length) {
/* 1355 */       int i = potionColors[potionId];
/* 1356 */       return (i < 0) ? color : i;
/*      */     } 
/* 1358 */     return color;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getMapColorIndex(String name) {
/* 1363 */     return (name == null) ? -1 : (name.equals("air") ? MapColor.airColor.colorIndex : (name.equals("grass") ? MapColor.grassColor.colorIndex : (name.equals("sand") ? MapColor.sandColor.colorIndex : (name.equals("cloth") ? MapColor.clothColor.colorIndex : (name.equals("tnt") ? MapColor.tntColor.colorIndex : (name.equals("ice") ? MapColor.iceColor.colorIndex : (name.equals("iron") ? MapColor.ironColor.colorIndex : (name.equals("foliage") ? MapColor.foliageColor.colorIndex : (name.equals("clay") ? MapColor.clayColor.colorIndex : (name.equals("dirt") ? MapColor.dirtColor.colorIndex : (name.equals("stone") ? MapColor.stoneColor.colorIndex : (name.equals("water") ? MapColor.waterColor.colorIndex : (name.equals("wood") ? MapColor.woodColor.colorIndex : (name.equals("quartz") ? MapColor.quartzColor.colorIndex : (name.equals("gold") ? MapColor.goldColor.colorIndex : (name.equals("diamond") ? MapColor.diamondColor.colorIndex : (name.equals("lapis") ? MapColor.lapisColor.colorIndex : (name.equals("emerald") ? MapColor.emeraldColor.colorIndex : (name.equals("podzol") ? MapColor.obsidianColor.colorIndex : (name.equals("netherrack") ? MapColor.netherrackColor.colorIndex : ((!name.equals("snow") && !name.equals("white")) ? ((!name.equals("adobe") && !name.equals("orange")) ? (name.equals("magenta") ? MapColor.magentaColor.colorIndex : ((!name.equals("light_blue") && !name.equals("lightBlue")) ? (name.equals("yellow") ? MapColor.yellowColor.colorIndex : (name.equals("lime") ? MapColor.limeColor.colorIndex : (name.equals("pink") ? MapColor.pinkColor.colorIndex : (name.equals("gray") ? MapColor.grayColor.colorIndex : (name.equals("silver") ? MapColor.silverColor.colorIndex : (name.equals("cyan") ? MapColor.cyanColor.colorIndex : (name.equals("purple") ? MapColor.purpleColor.colorIndex : (name.equals("blue") ? MapColor.blueColor.colorIndex : (name.equals("brown") ? MapColor.brownColor.colorIndex : (name.equals("green") ? MapColor.greenColor.colorIndex : (name.equals("red") ? MapColor.redColor.colorIndex : (name.equals("black") ? MapColor.blackColor.colorIndex : -1)))))))))))) : MapColor.lightBlueColor.colorIndex)) : MapColor.adobeColor.colorIndex) : MapColor.snowColor.colorIndex)))))))))))))))))))));
/*      */   }
/*      */   
/*      */   private static int[] getMapColors() {
/* 1367 */     MapColor[] amapcolor = MapColor.mapColorArray;
/* 1368 */     int[] aint = new int[amapcolor.length];
/* 1369 */     Arrays.fill(aint, -1);
/*      */     
/* 1371 */     for (int i = 0; i < amapcolor.length && i < aint.length; i++) {
/* 1372 */       MapColor mapcolor = amapcolor[i];
/*      */       
/* 1374 */       if (mapcolor != null) {
/* 1375 */         aint[i] = mapcolor.colorValue;
/*      */       }
/*      */     } 
/*      */     
/* 1379 */     return aint;
/*      */   }
/*      */   
/*      */   private static void setMapColors(int[] colors) {
/* 1383 */     if (colors != null) {
/* 1384 */       MapColor[] amapcolor = MapColor.mapColorArray;
/* 1385 */       boolean flag = false;
/*      */       
/* 1387 */       for (int i = 0; i < amapcolor.length && i < colors.length; i++) {
/* 1388 */         MapColor mapcolor = amapcolor[i];
/*      */         
/* 1390 */         if (mapcolor != null) {
/* 1391 */           int j = colors[i];
/*      */           
/* 1393 */           if (j >= 0 && mapcolor.colorValue != j) {
/* 1394 */             mapcolor.colorValue = j;
/* 1395 */             flag = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1400 */       if (flag) {
/* 1401 */         Minecraft.getMinecraft().getTextureManager().reloadBannerTextures();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void dbg(String str) {
/* 1407 */     Config.dbg("CustomColors: " + str);
/*      */   }
/*      */   
/*      */   private static void warn(String str) {
/* 1411 */     Config.warn("CustomColors: " + str);
/*      */   }
/*      */   
/*      */   public static int getExpBarTextColor(int color) {
/* 1415 */     return (expBarTextColor < 0) ? color : expBarTextColor;
/*      */   }
/*      */   
/*      */   public static int getBossTextColor(int color) {
/* 1419 */     return (bossTextColor < 0) ? color : bossTextColor;
/*      */   }
/*      */   
/*      */   public static int getSignTextColor(int color) {
/* 1423 */     return (signTextColor < 0) ? color : signTextColor;
/*      */   }
/*      */   
/*      */   public static interface IColorizer {
/*      */     int getColor(IBlockState param1IBlockState, IBlockAccess param1IBlockAccess, BlockPos param1BlockPos);
/*      */     
/*      */     boolean isColorConstant();
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */