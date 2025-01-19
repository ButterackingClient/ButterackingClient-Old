/*      */ package net.optifine;
/*      */ 
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.EnumMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockPane;
/*      */ import net.minecraft.block.BlockStainedGlassPane;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.config.Matches;
/*      */ import net.optifine.model.BlockModelUtils;
/*      */ import net.optifine.model.ListQuadsOverlay;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.ResUtils;
/*      */ import net.optifine.util.TileEntityUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectedTextures
/*      */ {
/*   47 */   private static Map[] spriteQuadMaps = null;
/*   48 */   private static Map[] spriteQuadFullMaps = null;
/*   49 */   private static Map[][] spriteQuadCompactMaps = null;
/*   50 */   private static ConnectedProperties[][] blockProperties = null;
/*   51 */   private static ConnectedProperties[][] tileProperties = null;
/*      */   private static boolean multipass = false;
/*      */   protected static final int UNKNOWN = -1;
/*      */   protected static final int Y_NEG_DOWN = 0;
/*      */   protected static final int Y_POS_UP = 1;
/*      */   protected static final int Z_NEG_NORTH = 2;
/*      */   protected static final int Z_POS_SOUTH = 3;
/*      */   protected static final int X_NEG_WEST = 4;
/*      */   protected static final int X_POS_EAST = 5;
/*      */   private static final int Y_AXIS = 0;
/*      */   private static final int Z_AXIS = 1;
/*      */   private static final int X_AXIS = 2;
/*   63 */   public static final IBlockState AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
/*   64 */   private static TextureAtlasSprite emptySprite = null;
/*   65 */   private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH };
/*   66 */   private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH };
/*   67 */   private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[] { BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP };
/*   68 */   private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP };
/*   69 */   private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP };
/*   70 */   private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[] { BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP };
/*   71 */   private static final BlockDir[] SIDES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.UP, BlockDir.DOWN };
/*   72 */   private static final BlockDir[] SIDES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.UP, BlockDir.DOWN };
/*   73 */   private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[] { BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST };
/*   74 */   private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[] { BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST };
/*   75 */   private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[] { BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST };
/*   76 */   private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[] { BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST };
/*   77 */   private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[] { BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH };
/*   78 */   private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[] { BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH };
/*   79 */   private static final BlockDir[] EDGES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.UP_EAST, BlockDir.UP_WEST, BlockDir.DOWN_EAST, BlockDir.DOWN_WEST };
/*   80 */   private static final BlockDir[] EDGES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.UP_SOUTH, BlockDir.UP_NORTH, BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH };
/*   81 */   public static final TextureAtlasSprite SPRITE_DEFAULT = new TextureAtlasSprite("<default>");
/*      */   
/*      */   public static BakedQuad[] getConnectedTexture(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BakedQuad quad, RenderEnv renderEnv) {
/*   84 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*   86 */     if (textureatlassprite == null) {
/*   87 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     }
/*   89 */     Block block = blockState.getBlock();
/*      */     
/*   91 */     if (skipConnectedTexture(blockAccess, blockState, blockPos, quad, renderEnv)) {
/*   92 */       quad = getQuad(emptySprite, quad);
/*   93 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     } 
/*   95 */     EnumFacing enumfacing = quad.getFace();
/*   96 */     BakedQuad[] abakedquad = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, enumfacing, quad, renderEnv);
/*   97 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean skipConnectedTexture(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BakedQuad quad, RenderEnv renderEnv) {
/*  103 */     Block block = blockState.getBlock();
/*      */     
/*  105 */     if (block instanceof BlockPane) {
/*  106 */       TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */       
/*  108 */       if (textureatlassprite.getIconName().startsWith("minecraft:blocks/glass_pane_top")) {
/*  109 */         IBlockState iblockstate1 = blockAccess.getBlockState(blockPos.offset(quad.getFace()));
/*  110 */         return (iblockstate1 == blockState);
/*      */       } 
/*      */     } 
/*      */     
/*  114 */     if (block instanceof BlockPane) {
/*  115 */       EnumFacing enumfacing = quad.getFace();
/*      */       
/*  117 */       if (enumfacing != EnumFacing.UP && enumfacing != EnumFacing.DOWN) {
/*  118 */         return false;
/*      */       }
/*      */       
/*  121 */       if (!quad.isFaceQuad()) {
/*  122 */         return false;
/*      */       }
/*      */       
/*  125 */       BlockPos blockpos = blockPos.offset(quad.getFace());
/*  126 */       IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/*      */       
/*  128 */       if (iblockstate.getBlock() != block) {
/*  129 */         return false;
/*      */       }
/*      */       
/*  132 */       if (block == Blocks.stained_glass_pane && iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR) != blockState.getValue((IProperty)BlockStainedGlassPane.COLOR)) {
/*  133 */         return false;
/*      */       }
/*      */       
/*  136 */       iblockstate = iblockstate.getBlock().getActualState(iblockstate, blockAccess, blockpos);
/*  137 */       double d0 = quad.getMidX();
/*      */       
/*  139 */       if (d0 < 0.4D) {
/*  140 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.WEST)).booleanValue()) {
/*  141 */           return true;
/*      */         }
/*  143 */       } else if (d0 > 0.6D) {
/*  144 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.EAST)).booleanValue()) {
/*  145 */           return true;
/*      */         }
/*      */       } else {
/*  148 */         double d1 = quad.getMidZ();
/*      */         
/*  150 */         if (d1 < 0.4D) {
/*  151 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.NORTH)).booleanValue()) {
/*  152 */             return true;
/*      */           }
/*      */         } else {
/*  155 */           if (d1 <= 0.6D) {
/*  156 */             return true;
/*      */           }
/*      */           
/*  159 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.SOUTH)).booleanValue()) {
/*  160 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  166 */     return false;
/*      */   }
/*      */   
/*      */   protected static BakedQuad[] getQuads(TextureAtlasSprite sprite, BakedQuad quadIn, RenderEnv renderEnv) {
/*  170 */     if (sprite == null)
/*  171 */       return null; 
/*  172 */     if (sprite == SPRITE_DEFAULT) {
/*  173 */       return renderEnv.getArrayQuadsCtm(quadIn);
/*      */     }
/*  175 */     BakedQuad bakedquad = getQuad(sprite, quadIn);
/*  176 */     BakedQuad[] abakedquad = renderEnv.getArrayQuadsCtm(bakedquad);
/*  177 */     return abakedquad;
/*      */   }
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad getQuad(TextureAtlasSprite sprite, BakedQuad quadIn) {
/*  182 */     if (spriteQuadMaps == null) {
/*  183 */       return quadIn;
/*      */     }
/*  185 */     int i = sprite.getIndexInMap();
/*      */     
/*  187 */     if (i >= 0 && i < spriteQuadMaps.length) {
/*  188 */       Map<Object, Object> map = spriteQuadMaps[i];
/*      */       
/*  190 */       if (map == null) {
/*  191 */         map = new IdentityHashMap<>(1);
/*  192 */         spriteQuadMaps[i] = map;
/*      */       } 
/*      */       
/*  195 */       BakedQuad bakedquad = (BakedQuad)map.get(quadIn);
/*      */       
/*  197 */       if (bakedquad == null) {
/*  198 */         bakedquad = makeSpriteQuad(quadIn, sprite);
/*  199 */         map.put(quadIn, bakedquad);
/*      */       } 
/*      */       
/*  202 */       return bakedquad;
/*      */     } 
/*  204 */     return quadIn;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized BakedQuad getQuadFull(TextureAtlasSprite sprite, BakedQuad quadIn, int tintIndex) {
/*  210 */     if (spriteQuadFullMaps == null)
/*  211 */       return null; 
/*  212 */     if (sprite == null) {
/*  213 */       return null;
/*      */     }
/*  215 */     int i = sprite.getIndexInMap();
/*      */     
/*  217 */     if (i >= 0 && i < spriteQuadFullMaps.length) {
/*  218 */       Map<EnumFacing, Object> map = spriteQuadFullMaps[i];
/*      */       
/*  220 */       if (map == null) {
/*  221 */         map = new EnumMap<>(EnumFacing.class);
/*  222 */         spriteQuadFullMaps[i] = map;
/*      */       } 
/*      */       
/*  225 */       EnumFacing enumfacing = quadIn.getFace();
/*  226 */       BakedQuad bakedquad = (BakedQuad)map.get(enumfacing);
/*      */       
/*  228 */       if (bakedquad == null) {
/*  229 */         bakedquad = BlockModelUtils.makeBakedQuad(enumfacing, sprite, tintIndex);
/*  230 */         map.put(enumfacing, bakedquad);
/*      */       } 
/*      */       
/*  233 */       return bakedquad;
/*      */     } 
/*  235 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad makeSpriteQuad(BakedQuad quad, TextureAtlasSprite sprite) {
/*  241 */     int[] aint = (int[])quad.getVertexData().clone();
/*  242 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  244 */     for (int i = 0; i < 4; i++) {
/*  245 */       fixVertex(aint, i, textureatlassprite, sprite);
/*      */     }
/*      */     
/*  248 */     BakedQuad bakedquad = new BakedQuad(aint, quad.getTintIndex(), quad.getFace(), sprite);
/*  249 */     return bakedquad;
/*      */   }
/*      */   
/*      */   private static void fixVertex(int[] data, int vertex, TextureAtlasSprite spriteFrom, TextureAtlasSprite spriteTo) {
/*  253 */     int i = data.length / 4;
/*  254 */     int j = i * vertex;
/*  255 */     float f = Float.intBitsToFloat(data[j + 4]);
/*  256 */     float f1 = Float.intBitsToFloat(data[j + 4 + 1]);
/*  257 */     double d0 = spriteFrom.getSpriteU16(f);
/*  258 */     double d1 = spriteFrom.getSpriteV16(f1);
/*  259 */     data[j + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(d0));
/*  260 */     data[j + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(d1));
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureMultiPass(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing side, BakedQuad quad, RenderEnv renderEnv) {
/*  264 */     BakedQuad[] abakedquad = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
/*      */     
/*  266 */     if (!multipass)
/*  267 */       return abakedquad; 
/*  268 */     if (abakedquad.length == 1 && abakedquad[0] == quad) {
/*  269 */       return abakedquad;
/*      */     }
/*  271 */     List<BakedQuad> list = renderEnv.getListQuadsCtmMultipass(abakedquad);
/*      */     
/*  273 */     for (int i = 0; i < list.size(); i++) {
/*  274 */       BakedQuad bakedquad = list.get(i);
/*  275 */       BakedQuad bakedquad1 = bakedquad;
/*      */       
/*  277 */       for (int j = 0; j < 3; j++) {
/*  278 */         BakedQuad[] abakedquad1 = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, bakedquad1, false, j + 1, renderEnv);
/*      */         
/*  280 */         if (abakedquad1.length != 1 || abakedquad1[0] == bakedquad1) {
/*      */           break;
/*      */         }
/*      */         
/*  284 */         bakedquad1 = abakedquad1[0];
/*      */       } 
/*      */       
/*  287 */       list.set(i, bakedquad1);
/*      */     } 
/*      */     
/*  290 */     for (int k = 0; k < abakedquad.length; k++) {
/*  291 */       abakedquad[k] = list.get(k);
/*      */     }
/*      */     
/*  294 */     return abakedquad;
/*      */   }
/*      */ 
/*      */   
/*      */   public static BakedQuad[] getConnectedTextureSingle(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, BakedQuad quad, boolean checkBlocks, int pass, RenderEnv renderEnv) {
/*  299 */     Block block = blockState.getBlock();
/*      */     
/*  301 */     if (!(blockState instanceof BlockStateBase)) {
/*  302 */       return renderEnv.getArrayQuadsCtm(quad);
/*      */     }
/*  304 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  305 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  307 */     if (tileProperties != null) {
/*  308 */       int i = textureatlassprite.getIndexInMap();
/*      */       
/*  310 */       if (i >= 0 && i < tileProperties.length) {
/*  311 */         ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */         
/*  313 */         if (aconnectedproperties != null) {
/*  314 */           int j = getSide(facing);
/*      */           
/*  316 */           for (int k = 0; k < aconnectedproperties.length; k++) {
/*  317 */             ConnectedProperties connectedproperties = aconnectedproperties[k];
/*      */             
/*  319 */             if (connectedproperties != null && connectedproperties.matchesBlockId(blockstatebase.getBlockId())) {
/*  320 */               BakedQuad[] abakedquad = getConnectedTexture(connectedproperties, blockAccess, blockstatebase, blockPos, j, quad, pass, renderEnv);
/*      */               
/*  322 */               if (abakedquad != null) {
/*  323 */                 return abakedquad;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  331 */     if (blockProperties != null && checkBlocks) {
/*  332 */       int l = renderEnv.getBlockId();
/*      */       
/*  334 */       if (l >= 0 && l < blockProperties.length) {
/*  335 */         ConnectedProperties[] aconnectedproperties1 = blockProperties[l];
/*      */         
/*  337 */         if (aconnectedproperties1 != null) {
/*  338 */           int i1 = getSide(facing);
/*      */           
/*  340 */           for (int j1 = 0; j1 < aconnectedproperties1.length; j1++) {
/*  341 */             ConnectedProperties connectedproperties1 = aconnectedproperties1[j1];
/*      */             
/*  343 */             if (connectedproperties1 != null && connectedproperties1.matchesIcon(textureatlassprite)) {
/*  344 */               BakedQuad[] abakedquad1 = getConnectedTexture(connectedproperties1, blockAccess, blockstatebase, blockPos, i1, quad, pass, renderEnv);
/*      */               
/*  346 */               if (abakedquad1 != null) {
/*  347 */                 return abakedquad1;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  355 */     return renderEnv.getArrayQuadsCtm(quad);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getSide(EnumFacing facing) {
/*  360 */     if (facing == null) {
/*  361 */       return -1;
/*      */     }
/*  363 */     switch (facing) {
/*      */       case null:
/*  365 */         return 0;
/*      */       
/*      */       case UP:
/*  368 */         return 1;
/*      */       
/*      */       case EAST:
/*  371 */         return 5;
/*      */       
/*      */       case WEST:
/*  374 */         return 4;
/*      */       
/*      */       case NORTH:
/*  377 */         return 2;
/*      */       
/*      */       case SOUTH:
/*  380 */         return 3;
/*      */     } 
/*      */     
/*  383 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static EnumFacing getFacing(int side) {
/*  389 */     switch (side) {
/*      */       case 0:
/*  391 */         return EnumFacing.DOWN;
/*      */       
/*      */       case 1:
/*  394 */         return EnumFacing.UP;
/*      */       
/*      */       case 2:
/*  397 */         return EnumFacing.NORTH;
/*      */       
/*      */       case 3:
/*  400 */         return EnumFacing.SOUTH;
/*      */       
/*      */       case 4:
/*  403 */         return EnumFacing.WEST;
/*      */       
/*      */       case 5:
/*  406 */         return EnumFacing.EAST;
/*      */     } 
/*      */     
/*  409 */     return EnumFacing.UP;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTexture(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side, BakedQuad quad, int pass, RenderEnv renderEnv) {
/*  414 */     int i = 0;
/*  415 */     int j = blockState.getMetadata();
/*  416 */     int k = j;
/*  417 */     Block block = blockState.getBlock();
/*      */     
/*  419 */     if (block instanceof net.minecraft.block.BlockRotatedPillar) {
/*  420 */       i = getWoodAxis(side, j);
/*      */       
/*  422 */       if (cp.getMetadataMax() <= 3) {
/*  423 */         k = j & 0x3;
/*      */       }
/*      */     } 
/*      */     
/*  427 */     if (block instanceof net.minecraft.block.BlockQuartz) {
/*  428 */       i = getQuartzAxis(side, j);
/*      */       
/*  430 */       if (cp.getMetadataMax() <= 2 && k > 2) {
/*  431 */         k = 2;
/*      */       }
/*      */     } 
/*      */     
/*  435 */     if (!cp.matchesBlock(blockState.getBlockId(), k)) {
/*  436 */       return null;
/*      */     }
/*  438 */     if (side >= 0 && cp.faces != 63) {
/*  439 */       int l = side;
/*      */       
/*  441 */       if (i != 0) {
/*  442 */         l = fixSideByAxis(side, i);
/*      */       }
/*      */       
/*  445 */       if ((1 << l & cp.faces) == 0) {
/*  446 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  450 */     int i1 = blockPos.getY();
/*      */     
/*  452 */     if (cp.heights != null && !cp.heights.isInRange(i1)) {
/*  453 */       return null;
/*      */     }
/*  455 */     if (cp.biomes != null) {
/*  456 */       BiomeGenBase biomegenbase = blockAccess.getBiomeGenForCoords(blockPos);
/*      */       
/*  458 */       if (!cp.matchesBiome(biomegenbase)) {
/*  459 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  463 */     if (cp.nbtName != null) {
/*  464 */       String s = TileEntityUtils.getTileEntityName(blockAccess, blockPos);
/*      */       
/*  466 */       if (!cp.nbtName.matchesValue(s)) {
/*  467 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  471 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*      */     
/*  473 */     switch (cp.method) {
/*      */       case 1:
/*  475 */         return getQuads(getConnectedTextureCtm(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j, renderEnv), quad, renderEnv);
/*      */       
/*      */       case 2:
/*  478 */         return getQuads(getConnectedTextureHorizontal(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 3:
/*  481 */         return getQuads(getConnectedTextureTop(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 4:
/*  484 */         return getQuads(getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side), quad, renderEnv);
/*      */       
/*      */       case 5:
/*  487 */         return getQuads(getConnectedTextureRepeat(cp, blockPos, side), quad, renderEnv);
/*      */       
/*      */       case 6:
/*  490 */         return getQuads(getConnectedTextureVertical(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 7:
/*  493 */         return getQuads(getConnectedTextureFixed(cp), quad, renderEnv);
/*      */       
/*      */       case 8:
/*  496 */         return getQuads(getConnectedTextureHorizontalVertical(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 9:
/*  499 */         return getQuads(getConnectedTextureVerticalHorizontal(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, textureatlassprite, j), quad, renderEnv);
/*      */       
/*      */       case 10:
/*  502 */         if (pass == 0) {
/*  503 */           return getConnectedTextureCtmCompact(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */         }
/*      */       
/*      */       default:
/*  507 */         return null;
/*      */       
/*      */       case 11:
/*  510 */         return getConnectedTextureOverlay(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */       
/*      */       case 12:
/*  513 */         return getConnectedTextureOverlayFixed(cp, quad, renderEnv);
/*      */       
/*      */       case 13:
/*  516 */         return getConnectedTextureOverlayRandom(cp, blockAccess, blockState, blockPos, side, quad, renderEnv);
/*      */       
/*      */       case 14:
/*  519 */         return getConnectedTextureOverlayRepeat(cp, blockPos, side, quad, renderEnv);
/*      */       case 15:
/*      */         break;
/*  522 */     }  return getConnectedTextureOverlayCtm(cp, blockAccess, (IBlockState)blockState, blockPos, i, side, quad, j, renderEnv);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int fixSideByAxis(int side, int vertAxis) {
/*  529 */     switch (vertAxis) {
/*      */       case 0:
/*  531 */         return side;
/*      */       
/*      */       case 1:
/*  534 */         switch (side) {
/*      */           case 0:
/*  536 */             return 2;
/*      */           
/*      */           case 1:
/*  539 */             return 3;
/*      */           
/*      */           case 2:
/*  542 */             return 1;
/*      */           
/*      */           case 3:
/*  545 */             return 0;
/*      */         } 
/*      */         
/*  548 */         return side;
/*      */ 
/*      */       
/*      */       case 2:
/*  552 */         switch (side) {
/*      */           case 0:
/*  554 */             return 4;
/*      */           
/*      */           case 1:
/*  557 */             return 5;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/*  562 */             return side;
/*      */           
/*      */           case 4:
/*  565 */             return 1;
/*      */           case 5:
/*      */             break;
/*  568 */         }  return 0;
/*      */     } 
/*      */ 
/*      */     
/*  572 */     return side;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getWoodAxis(int side, int metadata) {
/*  577 */     int i = (metadata & 0xC) >> 2;
/*      */     
/*  579 */     switch (i) {
/*      */       case 1:
/*  581 */         return 2;
/*      */       
/*      */       case 2:
/*  584 */         return 1;
/*      */     } 
/*      */     
/*  587 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getQuartzAxis(int side, int metadata) {
/*  592 */     switch (metadata) {
/*      */       case 3:
/*  594 */         return 2;
/*      */       
/*      */       case 4:
/*  597 */         return 1;
/*      */     } 
/*      */     
/*  600 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side) {
/*  605 */     if (cp.tileIcons.length == 1) {
/*  606 */       return cp.tileIcons[0];
/*      */     }
/*  608 */     int i = side / cp.symmetry * cp.symmetry;
/*      */     
/*  610 */     if (cp.linked) {
/*  611 */       BlockPos blockpos = blockPos.down();
/*      */       
/*  613 */       for (IBlockState iblockstate = blockAccess.getBlockState(blockpos); iblockstate.getBlock() == blockState.getBlock(); iblockstate = blockAccess.getBlockState(blockpos)) {
/*  614 */         blockPos = blockpos;
/*  615 */         blockpos = blockpos.down();
/*      */         
/*  617 */         if (blockpos.getY() < 0) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  623 */     int l = Config.getRandom(blockPos, i) & Integer.MAX_VALUE;
/*      */     
/*  625 */     for (int i1 = 0; i1 < cp.randomLoops; i1++) {
/*  626 */       l = Config.intHash(l);
/*      */     }
/*      */     
/*  629 */     int j1 = 0;
/*      */     
/*  631 */     if (cp.weights == null) {
/*  632 */       j1 = l % cp.tileIcons.length;
/*      */     } else {
/*  634 */       int j = l % cp.sumAllWeights;
/*  635 */       int[] aint = cp.sumWeights;
/*      */       
/*  637 */       for (int k = 0; k < aint.length; k++) {
/*  638 */         if (j < aint[k]) {
/*  639 */           j1 = k;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  645 */     return cp.tileIcons[j1];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties cp) {
/*  650 */     return cp.tileIcons[0];
/*      */   }
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties cp, BlockPos blockPos, int side) {
/*  654 */     if (cp.tileIcons.length == 1) {
/*  655 */       return cp.tileIcons[0];
/*      */     }
/*  657 */     int i = blockPos.getX();
/*  658 */     int j = blockPos.getY();
/*  659 */     int k = blockPos.getZ();
/*  660 */     int l = 0;
/*  661 */     int i1 = 0;
/*      */     
/*  663 */     switch (side) {
/*      */       case 0:
/*  665 */         l = i;
/*  666 */         i1 = -k - 1;
/*      */         break;
/*      */       
/*      */       case 1:
/*  670 */         l = i;
/*  671 */         i1 = k;
/*      */         break;
/*      */       
/*      */       case 2:
/*  675 */         l = -i - 1;
/*  676 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 3:
/*  680 */         l = i;
/*  681 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 4:
/*  685 */         l = k;
/*  686 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 5:
/*  690 */         l = -k - 1;
/*  691 */         i1 = -j; break;
/*  692 */     }  l %= 
/*      */       
/*  694 */       cp.width;
/*  695 */     i1 %= cp.height;
/*      */     
/*  697 */     if (l < 0) {
/*  698 */       l += cp.width;
/*      */     }
/*      */     
/*  701 */     if (i1 < 0) {
/*  702 */       i1 += cp.height;
/*      */     }
/*      */     
/*  705 */     int j1 = i1 * cp.width + l;
/*  706 */     return cp.tileIcons[j1];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata, RenderEnv renderEnv) {
/*  711 */     int i = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
/*  712 */     return cp.tileIcons[i];
/*      */   }
/*      */   
/*      */   private static synchronized BakedQuad[] getConnectedTextureCtmCompact(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*  716 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*  717 */     int i = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, textureatlassprite, metadata, renderEnv);
/*  718 */     return ConnectedTexturesCompact.getConnectedTextureCtmCompact(i, cp, side, quad, renderEnv);
/*      */   }
/*      */   private static BakedQuad[] getConnectedTextureOverlay(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*      */     Object dirEdges;
/*  722 */     if (!quad.isFullQuad()) {
/*  723 */       return null;
/*      */     }
/*  725 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*  726 */     BlockDir[] ablockdir = getSideDirections(side, vertAxis);
/*  727 */     boolean[] aboolean = renderEnv.getBorderFlags();
/*      */     
/*  729 */     for (int i = 0; i < 4; i++) {
/*  730 */       aboolean[i] = isNeighbourOverlay(cp, blockAccess, blockState, ablockdir[i].offset(blockPos), side, textureatlassprite, metadata);
/*      */     }
/*      */     
/*  733 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */ 
/*      */     
/*  737 */     try { if (!aboolean[0] || !aboolean[1] || !aboolean[2] || !aboolean[3]) {
/*  738 */         if (aboolean[0] && aboolean[1] && aboolean[2]) {
/*  739 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[5], quad, cp.tintIndex), cp.tintBlockState);
/*  740 */           Object object = null;
/*  741 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  744 */         if (aboolean[0] && aboolean[2] && aboolean[3]) {
/*  745 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[6], quad, cp.tintIndex), cp.tintBlockState);
/*  746 */           Object object = null;
/*  747 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  750 */         if (aboolean[1] && aboolean[2] && aboolean[3]) {
/*  751 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[12], quad, cp.tintIndex), cp.tintBlockState);
/*  752 */           Object object = null;
/*  753 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  756 */         if (aboolean[0] && aboolean[1] && aboolean[3]) {
/*  757 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[13], quad, cp.tintIndex), cp.tintBlockState);
/*  758 */           Object object = null;
/*  759 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  762 */         BlockDir[] ablockdir1 = getEdgeDirections(side, vertAxis);
/*  763 */         boolean[] aboolean1 = renderEnv.getBorderFlags2();
/*      */         
/*  765 */         for (int j = 0; j < 4; j++) {
/*  766 */           aboolean1[j] = isNeighbourOverlay(cp, blockAccess, blockState, ablockdir1[j].offset(blockPos), side, textureatlassprite, metadata);
/*      */         }
/*      */         
/*  769 */         if (aboolean[1] && aboolean[2]) {
/*  770 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[3], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  772 */           if (aboolean1[3]) {
/*  773 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  776 */           Object object4 = null;
/*  777 */           return (BakedQuad[])object4;
/*      */         } 
/*      */         
/*  780 */         if (aboolean[0] && aboolean[2]) {
/*  781 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[4], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  783 */           if (aboolean1[2]) {
/*  784 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  787 */           Object object3 = null;
/*  788 */           return (BakedQuad[])object3;
/*      */         } 
/*      */         
/*  791 */         if (aboolean[1] && aboolean[3]) {
/*  792 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[10], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  794 */           if (aboolean1[1]) {
/*  795 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  798 */           Object object2 = null;
/*  799 */           return (BakedQuad[])object2;
/*      */         } 
/*      */         
/*  802 */         if (aboolean[0] && aboolean[3]) {
/*  803 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[11], quad, cp.tintIndex), cp.tintBlockState);
/*      */           
/*  805 */           if (aboolean1[0]) {
/*  806 */             listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
/*      */           }
/*      */           
/*  809 */           Object object1 = null;
/*  810 */           return (BakedQuad[])object1;
/*      */         } 
/*      */         
/*  813 */         boolean[] aboolean2 = renderEnv.getBorderFlags3();
/*      */         
/*  815 */         for (int k = 0; k < 4; k++) {
/*  816 */           aboolean2[k] = isNeighbourMatching(cp, blockAccess, blockState, ablockdir[k].offset(blockPos), side, textureatlassprite, metadata);
/*      */         }
/*      */         
/*  819 */         if (aboolean[0]) {
/*  820 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[9], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  823 */         if (aboolean[1]) {
/*  824 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[7], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  827 */         if (aboolean[2]) {
/*  828 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[1], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  831 */         if (aboolean[3]) {
/*  832 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[15], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  835 */         if (aboolean1[0] && (aboolean2[1] || aboolean2[2]) && !aboolean[1] && !aboolean[2]) {
/*  836 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  839 */         if (aboolean1[1] && (aboolean2[0] || aboolean2[2]) && !aboolean[0] && !aboolean[2]) {
/*  840 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  843 */         if (aboolean1[2] && (aboolean2[1] || aboolean2[3]) && !aboolean[1] && !aboolean[3]) {
/*  844 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  847 */         if (aboolean1[3] && (aboolean2[0] || aboolean2[3]) && !aboolean[0] && !aboolean[3]) {
/*  848 */           listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
/*      */         }
/*      */         
/*  851 */         Object object5 = null;
/*  852 */         return (BakedQuad[])object5;
/*      */       } 
/*      */       
/*  855 */       listquadsoverlay.addQuad(getQuadFull(cp.tileIcons[8], quad, cp.tintIndex), cp.tintBlockState); }
/*      */     finally
/*      */     
/*  858 */     { if (listquadsoverlay.size() > 0)
/*  859 */         renderEnv.setOverlaysRendered(true);  }  if (listquadsoverlay.size() > 0) renderEnv.setOverlaysRendered(true);
/*      */ 
/*      */ 
/*      */     
/*  863 */     return (BakedQuad[])dirEdges;
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayFixed(ConnectedProperties cp, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/*  868 */     if (!quad.isFullQuad()) {
/*  869 */       return null;
/*      */     }
/*  871 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */     
/*      */     try {
/*  875 */       TextureAtlasSprite textureatlassprite = getConnectedTextureFixed(cp);
/*      */       
/*  877 */       if (textureatlassprite != null) {
/*  878 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/*  881 */       object = null;
/*      */     } finally {
/*  883 */       if (listquadsoverlay.size() > 0) {
/*  884 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/*  888 */     return (BakedQuad[])object;
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayRandom(ConnectedProperties cp, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/*  893 */     if (!quad.isFullQuad()) {
/*  894 */       return null;
/*      */     }
/*  896 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */     
/*      */     try {
/*  900 */       TextureAtlasSprite textureatlassprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
/*      */       
/*  902 */       if (textureatlassprite != null) {
/*  903 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/*  906 */       object = null;
/*      */     } finally {
/*  908 */       if (listquadsoverlay.size() > 0) {
/*  909 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/*  913 */     return (BakedQuad[])object;
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayRepeat(ConnectedProperties cp, BlockPos blockPos, int side, BakedQuad quad, RenderEnv renderEnv) {
/*      */     Object object;
/*  918 */     if (!quad.isFullQuad()) {
/*  919 */       return null;
/*      */     }
/*  921 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */     
/*      */     try {
/*  925 */       TextureAtlasSprite textureatlassprite = getConnectedTextureRepeat(cp, blockPos, side);
/*      */       
/*  927 */       if (textureatlassprite != null) {
/*  928 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/*  931 */       object = null;
/*      */     } finally {
/*  933 */       if (listquadsoverlay.size() > 0) {
/*  934 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/*  938 */     return (BakedQuad[])object;
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlayCtm(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, BakedQuad quad, int metadata, RenderEnv renderEnv) {
/*      */     Object object;
/*  943 */     if (!quad.isFullQuad()) {
/*  944 */       return null;
/*      */     }
/*  946 */     ListQuadsOverlay listquadsoverlay = renderEnv.getListQuadsOverlay(cp.layer);
/*      */ 
/*      */     
/*      */     try {
/*  950 */       TextureAtlasSprite textureatlassprite = getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad.getSprite(), metadata, renderEnv);
/*      */       
/*  952 */       if (textureatlassprite != null) {
/*  953 */         listquadsoverlay.addQuad(getQuadFull(textureatlassprite, quad, cp.tintIndex), cp.tintBlockState);
/*      */       }
/*      */       
/*  956 */       object = null;
/*      */     } finally {
/*  958 */       if (listquadsoverlay.size() > 0) {
/*  959 */         renderEnv.setOverlaysRendered(true);
/*      */       }
/*      */     } 
/*      */     
/*  963 */     return (BakedQuad[])object;
/*      */   }
/*      */ 
/*      */   
/*      */   private static BlockDir[] getSideDirections(int side, int vertAxis) {
/*  968 */     switch (side) {
/*      */       case 0:
/*  970 */         return SIDES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/*  973 */         return SIDES_Y_POS_UP;
/*      */       
/*      */       case 2:
/*  976 */         if (vertAxis == 1) {
/*  977 */           return SIDES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/*  980 */         return SIDES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/*  983 */         return SIDES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/*  986 */         return SIDES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/*  989 */         if (vertAxis == 2) {
/*  990 */           return SIDES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/*  993 */         return SIDES_X_POS_EAST;
/*      */     } 
/*      */     
/*  996 */     throw new IllegalArgumentException("Unknown side: " + side);
/*      */   }
/*      */ 
/*      */   
/*      */   private static BlockDir[] getEdgeDirections(int side, int vertAxis) {
/* 1001 */     switch (side) {
/*      */       case 0:
/* 1003 */         return EDGES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/* 1006 */         return EDGES_Y_POS_UP;
/*      */       
/*      */       case 2:
/* 1009 */         if (vertAxis == 1) {
/* 1010 */           return EDGES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/* 1013 */         return EDGES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/* 1016 */         return EDGES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/* 1019 */         return EDGES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/* 1022 */         if (vertAxis == 2) {
/* 1023 */           return EDGES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/* 1026 */         return EDGES_X_POS_EAST;
/*      */     } 
/*      */     
/* 1029 */     throw new IllegalArgumentException("Unknown side: " + side);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static Map[][] getSpriteQuadCompactMaps() {
/* 1034 */     return spriteQuadCompactMaps;
/*      */   }
/*      */   
/*      */   private static int getConnectedTextureCtmIndex(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata, RenderEnv renderEnv) {
/* 1038 */     boolean[] aboolean = renderEnv.getBorderFlags();
/*      */     
/* 1040 */     switch (side) {
/*      */       case 0:
/* 1042 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1043 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1044 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1045 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */         
/* 1047 */         if (cp.innerSeams) {
/* 1048 */           BlockPos blockpos6 = blockPos.down();
/* 1049 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos6.west(), side, icon, metadata));
/* 1050 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos6.east(), side, icon, metadata));
/* 1051 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos6.north(), side, icon, metadata));
/* 1052 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos6.south(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1058 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1059 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1060 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1061 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */         
/* 1063 */         if (cp.innerSeams) {
/* 1064 */           BlockPos blockpos5 = blockPos.up();
/* 1065 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos5.west(), side, icon, metadata));
/* 1066 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos5.east(), side, icon, metadata));
/* 1067 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos5.south(), side, icon, metadata));
/* 1068 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos5.north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1074 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1075 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1076 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1077 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1079 */         if (cp.innerSeams) {
/* 1080 */           BlockPos blockpos4 = blockPos.north();
/* 1081 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos4.east(), side, icon, metadata));
/* 1082 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos4.west(), side, icon, metadata));
/* 1083 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos4.down(), side, icon, metadata));
/* 1084 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos4.up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1087 */         if (vertAxis == 1) {
/* 1088 */           switchValues(0, 1, aboolean);
/* 1089 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1095 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1096 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1097 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1098 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1100 */         if (cp.innerSeams) {
/* 1101 */           BlockPos blockpos3 = blockPos.south();
/* 1102 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos3.west(), side, icon, metadata));
/* 1103 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos3.east(), side, icon, metadata));
/* 1104 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos3.down(), side, icon, metadata));
/* 1105 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos3.up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1111 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1112 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1113 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1114 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1116 */         if (cp.innerSeams) {
/* 1117 */           BlockPos blockpos2 = blockPos.west();
/* 1118 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos2.north(), side, icon, metadata));
/* 1119 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos2.south(), side, icon, metadata));
/* 1120 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos2.down(), side, icon, metadata));
/* 1121 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos2.up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1127 */         aboolean[0] = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1128 */         aboolean[1] = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1129 */         aboolean[2] = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1130 */         aboolean[3] = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         
/* 1132 */         if (cp.innerSeams) {
/* 1133 */           BlockPos blockpos = blockPos.east();
/* 1134 */           aboolean[0] = (aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos.south(), side, icon, metadata));
/* 1135 */           aboolean[1] = (aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos.north(), side, icon, metadata));
/* 1136 */           aboolean[2] = (aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos.down(), side, icon, metadata));
/* 1137 */           aboolean[3] = (aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos.up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1140 */         if (vertAxis == 2) {
/* 1141 */           switchValues(0, 1, aboolean);
/* 1142 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1146 */     int i = 0;
/*      */     
/* 1148 */     if ((aboolean[0] & (aboolean[1] ? 0 : 1) & (aboolean[2] ? 0 : 1) & (aboolean[3] ? 0 : 1)) != 0) {
/* 1149 */       i = 3;
/* 1150 */     } else if (((aboolean[0] ? 0 : 1) & aboolean[1] & (aboolean[2] ? 0 : 1) & (aboolean[3] ? 0 : 1)) != 0) {
/* 1151 */       i = 1;
/* 1152 */     } else if (((aboolean[0] ? 0 : 1) & (aboolean[1] ? 0 : 1) & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/* 1153 */       i = 12;
/* 1154 */     } else if (((aboolean[0] ? 0 : 1) & (aboolean[1] ? 0 : 1) & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/* 1155 */       i = 36;
/* 1156 */     } else if ((aboolean[0] & aboolean[1] & (aboolean[2] ? 0 : 1) & (aboolean[3] ? 0 : 1)) != 0) {
/* 1157 */       i = 2;
/* 1158 */     } else if (((aboolean[0] ? 0 : 1) & (aboolean[1] ? 0 : 1) & aboolean[2] & aboolean[3]) != 0) {
/* 1159 */       i = 24;
/* 1160 */     } else if ((aboolean[0] & (aboolean[1] ? 0 : 1) & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/* 1161 */       i = 15;
/* 1162 */     } else if ((aboolean[0] & (aboolean[1] ? 0 : 1) & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/* 1163 */       i = 39;
/* 1164 */     } else if (((aboolean[0] ? 0 : 1) & aboolean[1] & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/* 1165 */       i = 13;
/* 1166 */     } else if (((aboolean[0] ? 0 : 1) & aboolean[1] & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/* 1167 */       i = 37;
/* 1168 */     } else if (((aboolean[0] ? 0 : 1) & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/* 1169 */       i = 25;
/* 1170 */     } else if ((aboolean[0] & (aboolean[1] ? 0 : 1) & aboolean[2] & aboolean[3]) != 0) {
/* 1171 */       i = 27;
/* 1172 */     } else if ((aboolean[0] & aboolean[1] & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/* 1173 */       i = 38;
/* 1174 */     } else if ((aboolean[0] & aboolean[1] & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/* 1175 */       i = 14;
/* 1176 */     } else if ((aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/* 1177 */       i = 26;
/*      */     } 
/*      */     
/* 1180 */     if (i == 0)
/* 1181 */       return i; 
/* 1182 */     if (!Config.isConnectedTexturesFancy()) {
/* 1183 */       return i;
/*      */     }
/* 1185 */     switch (side) {
/*      */       case 0:
/* 1187 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().north(), side, icon, metadata);
/* 1188 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().north(), side, icon, metadata);
/* 1189 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().south(), side, icon, metadata);
/* 1190 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().south(), side, icon, metadata);
/*      */         
/* 1192 */         if (cp.innerSeams) {
/* 1193 */           BlockPos blockpos11 = blockPos.down();
/* 1194 */           aboolean[0] = !(!aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos11.east().north(), side, icon, metadata));
/* 1195 */           aboolean[1] = !(!aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos11.west().north(), side, icon, metadata));
/* 1196 */           aboolean[2] = !(!aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos11.east().south(), side, icon, metadata));
/* 1197 */           aboolean[3] = !(!aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos11.west().south(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/* 1203 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().south(), side, icon, metadata);
/* 1204 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().south(), side, icon, metadata);
/* 1205 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().north(), side, icon, metadata);
/* 1206 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().north(), side, icon, metadata);
/*      */         
/* 1208 */         if (cp.innerSeams) {
/* 1209 */           BlockPos blockpos10 = blockPos.up();
/* 1210 */           aboolean[0] = !(!aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos10.east().south(), side, icon, metadata));
/* 1211 */           aboolean[1] = !(!aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos10.west().south(), side, icon, metadata));
/* 1212 */           aboolean[2] = !(!aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos10.east().north(), side, icon, metadata));
/* 1213 */           aboolean[3] = !(!aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos10.west().north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 2:
/* 1219 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().down(), side, icon, metadata);
/* 1220 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().down(), side, icon, metadata);
/* 1221 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().up(), side, icon, metadata);
/* 1222 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().up(), side, icon, metadata);
/*      */         
/* 1224 */         if (cp.innerSeams) {
/* 1225 */           BlockPos blockpos9 = blockPos.north();
/* 1226 */           aboolean[0] = !(!aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos9.west().down(), side, icon, metadata));
/* 1227 */           aboolean[1] = !(!aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos9.east().down(), side, icon, metadata));
/* 1228 */           aboolean[2] = !(!aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos9.west().up(), side, icon, metadata));
/* 1229 */           aboolean[3] = !(!aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos9.east().up(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1232 */         if (vertAxis == 1) {
/* 1233 */           switchValues(0, 3, aboolean);
/* 1234 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1240 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().down(), side, icon, metadata);
/* 1241 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().down(), side, icon, metadata);
/* 1242 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.east().up(), side, icon, metadata);
/* 1243 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.west().up(), side, icon, metadata);
/*      */         
/* 1245 */         if (cp.innerSeams) {
/* 1246 */           BlockPos blockpos8 = blockPos.south();
/* 1247 */           aboolean[0] = !(!aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos8.east().down(), side, icon, metadata));
/* 1248 */           aboolean[1] = !(!aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos8.west().down(), side, icon, metadata));
/* 1249 */           aboolean[2] = !(!aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos8.east().up(), side, icon, metadata));
/* 1250 */           aboolean[3] = !(!aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos8.west().up(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1256 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().south(), side, icon, metadata);
/* 1257 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().north(), side, icon, metadata);
/* 1258 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().south(), side, icon, metadata);
/* 1259 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().north(), side, icon, metadata);
/*      */         
/* 1261 */         if (cp.innerSeams) {
/* 1262 */           BlockPos blockpos7 = blockPos.west();
/* 1263 */           aboolean[0] = !(!aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos7.down().south(), side, icon, metadata));
/* 1264 */           aboolean[1] = !(!aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos7.down().north(), side, icon, metadata));
/* 1265 */           aboolean[2] = !(!aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos7.up().south(), side, icon, metadata));
/* 1266 */           aboolean[3] = !(!aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos7.up().north(), side, icon, metadata));
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1272 */         aboolean[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().north(), side, icon, metadata);
/* 1273 */         aboolean[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.down().south(), side, icon, metadata);
/* 1274 */         aboolean[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().north(), side, icon, metadata);
/* 1275 */         aboolean[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.up().south(), side, icon, metadata);
/*      */         
/* 1277 */         if (cp.innerSeams) {
/* 1278 */           BlockPos blockpos1 = blockPos.east();
/* 1279 */           aboolean[0] = !(!aboolean[0] && !isNeighbour(cp, blockAccess, blockState, blockpos1.down().north(), side, icon, metadata));
/* 1280 */           aboolean[1] = !(!aboolean[1] && !isNeighbour(cp, blockAccess, blockState, blockpos1.down().south(), side, icon, metadata));
/* 1281 */           aboolean[2] = !(!aboolean[2] && !isNeighbour(cp, blockAccess, blockState, blockpos1.up().north(), side, icon, metadata));
/* 1282 */           aboolean[3] = !(!aboolean[3] && !isNeighbour(cp, blockAccess, blockState, blockpos1.up().south(), side, icon, metadata));
/*      */         } 
/*      */         
/* 1285 */         if (vertAxis == 2) {
/* 1286 */           switchValues(0, 3, aboolean);
/* 1287 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1291 */     if (i == 13 && aboolean[0]) {
/* 1292 */       i = 4;
/* 1293 */     } else if (i == 15 && aboolean[1]) {
/* 1294 */       i = 5;
/* 1295 */     } else if (i == 37 && aboolean[2]) {
/* 1296 */       i = 16;
/* 1297 */     } else if (i == 39 && aboolean[3]) {
/* 1298 */       i = 17;
/* 1299 */     } else if (i == 14 && aboolean[0] && aboolean[1]) {
/* 1300 */       i = 7;
/* 1301 */     } else if (i == 25 && aboolean[0] && aboolean[2]) {
/* 1302 */       i = 6;
/* 1303 */     } else if (i == 27 && aboolean[3] && aboolean[1]) {
/* 1304 */       i = 19;
/* 1305 */     } else if (i == 38 && aboolean[3] && aboolean[2]) {
/* 1306 */       i = 18;
/* 1307 */     } else if (i == 14 && !aboolean[0] && aboolean[1]) {
/* 1308 */       i = 31;
/* 1309 */     } else if (i == 25 && aboolean[0] && !aboolean[2]) {
/* 1310 */       i = 30;
/* 1311 */     } else if (i == 27 && !aboolean[3] && aboolean[1]) {
/* 1312 */       i = 41;
/* 1313 */     } else if (i == 38 && aboolean[3] && !aboolean[2]) {
/* 1314 */       i = 40;
/* 1315 */     } else if (i == 14 && aboolean[0] && !aboolean[1]) {
/* 1316 */       i = 29;
/* 1317 */     } else if (i == 25 && !aboolean[0] && aboolean[2]) {
/* 1318 */       i = 28;
/* 1319 */     } else if (i == 27 && aboolean[3] && !aboolean[1]) {
/* 1320 */       i = 43;
/* 1321 */     } else if (i == 38 && !aboolean[3] && aboolean[2]) {
/* 1322 */       i = 42;
/* 1323 */     } else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/* 1324 */       i = 46;
/* 1325 */     } else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/* 1326 */       i = 9;
/* 1327 */     } else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/* 1328 */       i = 21;
/* 1329 */     } else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/* 1330 */       i = 8;
/* 1331 */     } else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/* 1332 */       i = 20;
/* 1333 */     } else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/* 1334 */       i = 11;
/* 1335 */     } else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/* 1336 */       i = 22;
/* 1337 */     } else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/* 1338 */       i = 23;
/* 1339 */     } else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/* 1340 */       i = 10;
/* 1341 */     } else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/* 1342 */       i = 34;
/* 1343 */     } else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/* 1344 */       i = 35;
/* 1345 */     } else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
/* 1346 */       i = 32;
/* 1347 */     } else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/* 1348 */       i = 33;
/* 1349 */     } else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/* 1350 */       i = 44;
/* 1351 */     } else if (i == 26 && !aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/* 1352 */       i = 45;
/*      */     } 
/*      */     
/* 1355 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void switchValues(int ix1, int ix2, boolean[] arr) {
/* 1360 */     boolean flag = arr[ix1];
/* 1361 */     arr[ix1] = arr[ix2];
/* 1362 */     arr[ix2] = flag;
/*      */   }
/*      */   
/*      */   private static boolean isNeighbourOverlay(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1366 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/*      */     
/* 1368 */     if (!isFullCubeModel(iblockstate)) {
/* 1369 */       return false;
/*      */     }
/* 1371 */     if (cp.connectBlocks != null) {
/* 1372 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1374 */       if (!Matches.block(blockstatebase.getBlockId(), blockstatebase.getMetadata(), cp.connectBlocks)) {
/* 1375 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1379 */     if (cp.connectTileIcons != null) {
/* 1380 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, iblockstate, side);
/*      */       
/* 1382 */       if (!Config.isSameOne(textureatlassprite, (Object[])cp.connectTileIcons)) {
/* 1383 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1387 */     IBlockState iblockstate1 = iblockaccess.getBlockState(blockPos.offset(getFacing(side)));
/* 1388 */     return iblockstate1.getBlock().isOpaqueCube() ? false : ((side == 1 && iblockstate1.getBlock() == Blocks.snow_layer) ? false : (!isNeighbour(cp, iblockaccess, blockState, blockPos, iblockstate, side, icon, metadata)));
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isFullCubeModel(IBlockState state) {
/* 1393 */     if (state.getBlock().isFullCube()) {
/* 1394 */       return true;
/*      */     }
/* 1396 */     Block block = state.getBlock();
/* 1397 */     return (block instanceof net.minecraft.block.BlockGlass) ? true : (block instanceof net.minecraft.block.BlockStainedGlass);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbourMatching(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1402 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/*      */     
/* 1404 */     if (iblockstate == AIR_DEFAULT_STATE) {
/* 1405 */       return false;
/*      */     }
/* 1407 */     if (cp.matchBlocks != null && iblockstate instanceof BlockStateBase) {
/* 1408 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1410 */       if (!cp.matchesBlock(blockstatebase.getBlockId(), blockstatebase.getMetadata())) {
/* 1411 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1415 */     if (cp.matchTileIcons != null) {
/* 1416 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, iblockstate, side);
/*      */       
/* 1418 */       if (textureatlassprite != icon) {
/* 1419 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1423 */     IBlockState iblockstate1 = iblockaccess.getBlockState(blockPos.offset(getFacing(side)));
/* 1424 */     return iblockstate1.getBlock().isOpaqueCube() ? false : (!(side == 1 && iblockstate1.getBlock() == Blocks.snow_layer));
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
/* 1429 */     IBlockState iblockstate = iblockaccess.getBlockState(blockPos);
/* 1430 */     return isNeighbour(cp, iblockaccess, blockState, blockPos, iblockstate, side, icon, metadata);
/*      */   }
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, IBlockState neighbourState, int side, TextureAtlasSprite icon, int metadata) {
/* 1434 */     if (blockState == neighbourState)
/* 1435 */       return true; 
/* 1436 */     if (cp.connect == 2) {
/* 1437 */       if (neighbourState == null)
/* 1438 */         return false; 
/* 1439 */       if (neighbourState == AIR_DEFAULT_STATE) {
/* 1440 */         return false;
/*      */       }
/* 1442 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(iblockaccess, blockState, blockPos, neighbourState, side);
/* 1443 */       return (textureatlassprite == icon);
/*      */     } 
/* 1445 */     if (cp.connect == 3)
/* 1446 */       return (neighbourState == null) ? false : ((neighbourState == AIR_DEFAULT_STATE) ? false : ((neighbourState.getBlock().getMaterial() == blockState.getBlock().getMaterial()))); 
/* 1447 */     if (!(neighbourState instanceof BlockStateBase)) {
/* 1448 */       return false;
/*      */     }
/* 1450 */     BlockStateBase blockstatebase = (BlockStateBase)neighbourState;
/* 1451 */     Block block = blockstatebase.getBlock();
/* 1452 */     int i = blockstatebase.getMetadata();
/* 1453 */     return (block == blockState.getBlock() && i == metadata);
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getNeighbourIcon(IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, IBlockState neighbourState, int side) {
/* 1458 */     neighbourState = neighbourState.getBlock().getActualState(neighbourState, iblockaccess, blockPos);
/* 1459 */     IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(neighbourState);
/*      */     
/* 1461 */     if (ibakedmodel == null) {
/* 1462 */       return null;
/*      */     }
/* 1464 */     if (Reflector.ForgeBlock_getExtendedState.exists()) {
/* 1465 */       neighbourState = (IBlockState)Reflector.call(neighbourState.getBlock(), Reflector.ForgeBlock_getExtendedState, new Object[] { neighbourState, iblockaccess, blockPos });
/*      */     }
/*      */     
/* 1468 */     EnumFacing enumfacing = getFacing(side);
/* 1469 */     List<BakedQuad> list = ibakedmodel.getFaceQuads(enumfacing);
/*      */     
/* 1471 */     if (list == null) {
/* 1472 */       return null;
/*      */     }
/* 1474 */     if (Config.isBetterGrass()) {
/* 1475 */       list = BetterGrass.getFaceQuads(iblockaccess, neighbourState, blockPos, enumfacing, list);
/*      */     }
/*      */     
/* 1478 */     if (list.size() > 0) {
/* 1479 */       BakedQuad bakedquad1 = list.get(0);
/* 1480 */       return bakedquad1.getSprite();
/*      */     } 
/* 1482 */     List<BakedQuad> list1 = ibakedmodel.getGeneralQuads();
/*      */     
/* 1484 */     if (list1 == null) {
/* 1485 */       return null;
/*      */     }
/* 1487 */     for (int i = 0; i < list1.size(); i++) {
/* 1488 */       BakedQuad bakedquad = list1.get(i);
/*      */       
/* 1490 */       if (bakedquad.getFace() == enumfacing) {
/* 1491 */         return bakedquad.getSprite();
/*      */       }
/*      */     } 
/*      */     
/* 1495 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 1505 */     boolean flag = false;
/* 1506 */     boolean flag1 = false;
/*      */ 
/*      */     
/* 1509 */     switch (vertAxis) {
/*      */       case 0:
/* 1511 */         switch (side) {
/*      */           case 0:
/* 1513 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1514 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1518 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1519 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1523 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1524 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1528 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1529 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1533 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1534 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1538 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1539 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/* 1546 */         switch (side) {
/*      */           case 0:
/* 1548 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/* 1549 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1553 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1554 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1558 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1559 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1563 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1564 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1568 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1569 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1573 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1574 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/* 1581 */         switch (side) {
/*      */           case 0:
/* 1583 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1584 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1588 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1589 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1593 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1594 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1598 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1599 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1603 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1604 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1608 */             flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1609 */             flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata); break;
/*      */         } 
/*      */         break;
/*      */     } 
/* 1613 */     int i = 3;
/*      */     
/* 1615 */     if (flag) {
/* 1616 */       if (flag1) {
/* 1617 */         i = 1;
/*      */       } else {
/* 1619 */         i = 2;
/*      */       } 
/* 1621 */     } else if (flag1) {
/* 1622 */       i = 0;
/*      */     } else {
/* 1624 */       i = 3;
/*      */     } 
/*      */     
/* 1627 */     return cp.tileIcons[i];
/*      */   }
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 1631 */     boolean flag = false;
/* 1632 */     boolean flag1 = false;
/*      */     
/* 1634 */     switch (vertAxis) {
/*      */       case 0:
/* 1636 */         if (side == 1) {
/* 1637 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1638 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata); break;
/* 1639 */         }  if (side == 0) {
/* 1640 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/* 1641 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata); break;
/*      */         } 
/* 1643 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1644 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1650 */         if (side == 3) {
/* 1651 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1652 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata); break;
/* 1653 */         }  if (side == 2) {
/* 1654 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1655 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata); break;
/*      */         } 
/* 1657 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/* 1658 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.north(), side, icon, metadata);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1664 */         if (side == 5) {
/* 1665 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/* 1666 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata); break;
/* 1667 */         }  if (side == 4) {
/* 1668 */           flag = isNeighbour(cp, blockAccess, blockState, blockPos.down(), side, icon, metadata);
/* 1669 */           flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata); break;
/*      */         } 
/* 1671 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.west(), side, icon, metadata);
/* 1672 */         flag1 = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */         break;
/*      */     } 
/*      */     
/* 1676 */     int i = 3;
/*      */     
/* 1678 */     if (flag) {
/* 1679 */       if (flag1) {
/* 1680 */         i = 1;
/*      */       } else {
/* 1682 */         i = 2;
/*      */       } 
/* 1684 */     } else if (flag1) {
/* 1685 */       i = 0;
/*      */     } else {
/* 1687 */       i = 3;
/*      */     } 
/*      */     
/* 1690 */     return cp.tileIcons[i];
/*      */   }
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 1694 */     TextureAtlasSprite[] atextureatlassprite = cp.tileIcons;
/* 1695 */     TextureAtlasSprite textureatlassprite = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/*      */     
/* 1697 */     if (textureatlassprite != null && textureatlassprite != icon && textureatlassprite != atextureatlassprite[3]) {
/* 1698 */       return textureatlassprite;
/*      */     }
/* 1700 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/* 1701 */     return (textureatlassprite1 == atextureatlassprite[0]) ? atextureatlassprite[4] : ((textureatlassprite1 == atextureatlassprite[1]) ? atextureatlassprite[5] : ((textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1));
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 1706 */     TextureAtlasSprite[] atextureatlassprite = cp.tileIcons;
/* 1707 */     TextureAtlasSprite textureatlassprite = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/*      */     
/* 1709 */     if (textureatlassprite != null && textureatlassprite != icon && textureatlassprite != atextureatlassprite[3]) {
/* 1710 */       return textureatlassprite;
/*      */     }
/* 1712 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
/* 1713 */     return (textureatlassprite1 == atextureatlassprite[0]) ? atextureatlassprite[4] : ((textureatlassprite1 == atextureatlassprite[1]) ? atextureatlassprite[5] : ((textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1));
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties cp, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
/* 1718 */     boolean flag = false;
/*      */     
/* 1720 */     switch (vertAxis) {
/*      */       case 0:
/* 1722 */         if (side == 1 || side == 0) {
/* 1723 */           return null;
/*      */         }
/*      */         
/* 1726 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.up(), side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1730 */         if (side == 3 || side == 2) {
/* 1731 */           return null;
/*      */         }
/*      */         
/* 1734 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.south(), side, icon, metadata);
/*      */         break;
/*      */       
/*      */       case 2:
/* 1738 */         if (side == 5 || side == 4) {
/* 1739 */           return null;
/*      */         }
/*      */         
/* 1742 */         flag = isNeighbour(cp, blockAccess, blockState, blockPos.east(), side, icon, metadata);
/*      */         break;
/*      */     } 
/* 1745 */     if (flag) {
/* 1746 */       return cp.tileIcons[0];
/*      */     }
/* 1748 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap) {
/* 1753 */     blockProperties = null;
/* 1754 */     tileProperties = null;
/* 1755 */     spriteQuadMaps = null;
/* 1756 */     spriteQuadCompactMaps = null;
/*      */     
/* 1758 */     if (Config.isConnectedTextures()) {
/* 1759 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*      */       
/* 1761 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/* 1762 */         IResourcePack iresourcepack = airesourcepack[i];
/* 1763 */         updateIcons(textureMap, iresourcepack);
/*      */       } 
/*      */       
/* 1766 */       updateIcons(textureMap, (IResourcePack)Config.getDefaultResourcePack());
/* 1767 */       ResourceLocation resourcelocation = new ResourceLocation("mcpatcher/ctm/default/empty");
/* 1768 */       emptySprite = textureMap.registerSprite(resourcelocation);
/* 1769 */       spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
/* 1770 */       spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
/* 1771 */       spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
/*      */       
/* 1773 */       if (blockProperties.length <= 0) {
/* 1774 */         blockProperties = null;
/*      */       }
/*      */       
/* 1777 */       if (tileProperties.length <= 0) {
/* 1778 */         tileProperties = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void updateIconEmpty(TextureMap textureMap) {}
/*      */   
/*      */   public static void updateIcons(TextureMap textureMap, IResourcePack rp) {
/* 1787 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
/* 1788 */     Arrays.sort((Object[])astring);
/* 1789 */     List list = makePropertyList(tileProperties);
/* 1790 */     List list1 = makePropertyList(blockProperties);
/*      */     
/* 1792 */     for (int i = 0; i < astring.length; i++) {
/* 1793 */       String s = astring[i];
/* 1794 */       Config.dbg("ConnectedTextures: " + s);
/*      */       
/*      */       try {
/* 1797 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1798 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/*      */         
/* 1800 */         if (inputstream == null) {
/* 1801 */           Config.warn("ConnectedTextures file not found: " + s);
/*      */         } else {
/* 1803 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 1804 */           propertiesOrdered.load(inputstream);
/* 1805 */           inputstream.close();
/* 1806 */           ConnectedProperties connectedproperties = new ConnectedProperties((Properties)propertiesOrdered, s);
/*      */           
/* 1808 */           if (connectedproperties.isValid(s)) {
/* 1809 */             connectedproperties.updateIcons(textureMap);
/* 1810 */             addToTileList(connectedproperties, list);
/* 1811 */             addToBlockList(connectedproperties, list1);
/*      */           } 
/*      */         } 
/* 1814 */       } catch (FileNotFoundException var11) {
/* 1815 */         Config.warn("ConnectedTextures file not found: " + s);
/* 1816 */       } catch (Exception exception) {
/* 1817 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/* 1821 */     blockProperties = propertyListToArray(list1);
/* 1822 */     tileProperties = propertyListToArray(list);
/* 1823 */     multipass = detectMultipass();
/* 1824 */     Config.dbg("Multipass connected textures: " + multipass);
/*      */   }
/*      */   
/*      */   private static List makePropertyList(ConnectedProperties[][] propsArr) {
/* 1828 */     List<List> list = new ArrayList();
/*      */     
/* 1830 */     if (propsArr != null) {
/* 1831 */       for (int i = 0; i < propsArr.length; i++) {
/* 1832 */         ConnectedProperties[] aconnectedproperties = propsArr[i];
/* 1833 */         List list1 = null;
/*      */         
/* 1835 */         if (aconnectedproperties != null) {
/* 1836 */           list1 = new ArrayList(Arrays.asList((Object[])aconnectedproperties));
/*      */         }
/*      */         
/* 1839 */         list.add(list1);
/*      */       } 
/*      */     }
/*      */     
/* 1843 */     return list;
/*      */   }
/*      */   
/*      */   private static boolean detectMultipass() {
/* 1847 */     List list = new ArrayList();
/*      */     
/* 1849 */     for (int i = 0; i < tileProperties.length; i++) {
/* 1850 */       ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */       
/* 1852 */       if (aconnectedproperties != null) {
/* 1853 */         list.addAll(Arrays.asList(aconnectedproperties));
/*      */       }
/*      */     } 
/*      */     
/* 1857 */     for (int k = 0; k < blockProperties.length; k++) {
/* 1858 */       ConnectedProperties[] aconnectedproperties2 = blockProperties[k];
/*      */       
/* 1860 */       if (aconnectedproperties2 != null) {
/* 1861 */         list.addAll(Arrays.asList(aconnectedproperties2));
/*      */       }
/*      */     } 
/*      */     
/* 1865 */     ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list.toArray((Object[])new ConnectedProperties[list.size()]);
/* 1866 */     Set set1 = new HashSet();
/* 1867 */     Set<?> set = new HashSet();
/*      */     
/* 1869 */     for (int j = 0; j < aconnectedproperties1.length; j++) {
/* 1870 */       ConnectedProperties connectedproperties = aconnectedproperties1[j];
/*      */       
/* 1872 */       if (connectedproperties.matchTileIcons != null) {
/* 1873 */         set1.addAll(Arrays.asList(connectedproperties.matchTileIcons));
/*      */       }
/*      */       
/* 1876 */       if (connectedproperties.tileIcons != null) {
/* 1877 */         set.addAll(Arrays.asList(connectedproperties.tileIcons));
/*      */       }
/*      */     } 
/*      */     
/* 1881 */     set1.retainAll(set);
/* 1882 */     return !set1.isEmpty();
/*      */   }
/*      */   
/*      */   private static ConnectedProperties[][] propertyListToArray(List<List> list) {
/* 1886 */     ConnectedProperties[][] aconnectedproperties = new ConnectedProperties[list.size()][];
/*      */     
/* 1888 */     for (int i = 0; i < list.size(); i++) {
/* 1889 */       List list2 = list.get(i);
/*      */       
/* 1891 */       if (list2 != null) {
/* 1892 */         ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list2.toArray((Object[])new ConnectedProperties[list2.size()]);
/* 1893 */         aconnectedproperties[i] = aconnectedproperties1;
/*      */       } 
/*      */     } 
/*      */     
/* 1897 */     return aconnectedproperties;
/*      */   }
/*      */   
/*      */   private static void addToTileList(ConnectedProperties cp, List tileList) {
/* 1901 */     if (cp.matchTileIcons != null) {
/* 1902 */       for (int i = 0; i < cp.matchTileIcons.length; i++) {
/* 1903 */         TextureAtlasSprite textureatlassprite = cp.matchTileIcons[i];
/*      */         
/* 1905 */         if (!(textureatlassprite instanceof TextureAtlasSprite)) {
/* 1906 */           Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + textureatlassprite + ", name: " + textureatlassprite.getIconName());
/*      */         } else {
/* 1908 */           int j = textureatlassprite.getIndexInMap();
/*      */           
/* 1910 */           if (j < 0) {
/* 1911 */             Config.warn("Invalid tile ID: " + j + ", icon: " + textureatlassprite.getIconName());
/*      */           } else {
/* 1913 */             addToList(cp, tileList, j);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static void addToBlockList(ConnectedProperties cp, List blockList) {
/* 1921 */     if (cp.matchBlocks != null) {
/* 1922 */       for (int i = 0; i < cp.matchBlocks.length; i++) {
/* 1923 */         int j = cp.matchBlocks[i].getBlockId();
/*      */         
/* 1925 */         if (j < 0) {
/* 1926 */           Config.warn("Invalid block ID: " + j);
/*      */         } else {
/* 1928 */           addToList(cp, blockList, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static void addToList(ConnectedProperties cp, List<List> list, int id) {
/* 1935 */     while (id >= list.size()) {
/* 1936 */       list.add(null);
/*      */     }
/*      */     
/* 1939 */     List<ConnectedProperties> list1 = list.get(id);
/*      */     
/* 1941 */     if (list1 == null) {
/* 1942 */       list1 = new ArrayList();
/* 1943 */       list.set(id, list1);
/*      */     } 
/*      */     
/* 1946 */     list1.add(cp);
/*      */   }
/*      */   
/*      */   private static String[] getDefaultCtmPaths() {
/* 1950 */     List<String> list = new ArrayList();
/* 1951 */     String s = "mcpatcher/ctm/default/";
/*      */     
/* 1953 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
/* 1954 */       list.add(String.valueOf(s) + "glass.properties");
/* 1955 */       list.add(String.valueOf(s) + "glasspane.properties");
/*      */     } 
/*      */     
/* 1958 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
/* 1959 */       list.add(String.valueOf(s) + "bookshelf.properties");
/*      */     }
/*      */     
/* 1962 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
/* 1963 */       list.add(String.valueOf(s) + "sandstone.properties");
/*      */     }
/*      */     
/* 1966 */     String[] astring = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
/*      */     
/* 1968 */     for (int i = 0; i < astring.length; i++) {
/* 1969 */       String s1 = astring[i];
/*      */       
/* 1971 */       if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + s1 + ".png"))) {
/* 1972 */         list.add(String.valueOf(s) + i + "_glass_" + s1 + "/glass_" + s1 + ".properties");
/* 1973 */         list.add(String.valueOf(s) + i + "_glass_" + s1 + "/glass_pane_" + s1 + ".properties");
/*      */       } 
/*      */     } 
/*      */     
/* 1977 */     String[] astring1 = list.<String>toArray(new String[list.size()]);
/* 1978 */     return astring1;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\ConnectedTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */